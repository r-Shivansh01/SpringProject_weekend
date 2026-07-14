# MediFlow Microservices

9 Spring Boot services, Spring Boot 3.3.5 / Spring Cloud 2023.0.3 / Java 17.

| Service              | Port | Eureka name          |
|----------------------|------|-----------------------|
| eureka-server         | 8761 | EUREKA-SERVER         |
| api-gateway             | 8080 | API-GATEWAY           |
| auth-service            | 8087 | AUTH-SERVICE          |
| patient-service        | 8081 | PATIENT-SERVICE       |
| doctor-service          | 8082 | DOCTOR-SERVICE        |
| appointment-service     | 8083 | APPOINTMENT-SERVICE   |
| prescription-service    | 8084 | PRESCRIPTION-SERVICE  |
| billing-service          | 8085 | BILLING-SERVICE       |
| notification-service     | 8086 | NOTIFICATION-SERVICE  |

## Import into Spring Tool Suite

1. Unzip this archive anywhere.
2. In STS: **File → Import → Maven → Existing Maven Projects**.
3. Set "Root Directory" to the unzipped `mediflow-microservices` folder and tick all 9 pom.xml entries it finds, then **Finish**.
   (Each service is a standalone Maven project — no parent/multi-module pom, so they import as 9 separate projects.)
4. Let Maven download dependencies (first import will take a few minutes). `api-gateway` pulls Spring Cloud Gateway + a reactive (WebFlux) stack, which is a slightly bigger download than the other services.

## Run order (important)

Eureka must be up first so the others can register with it. `auth-service` should come up before `api-gateway` so the gateway's JWT check has somewhere to call — the gateway won't crash if it's not up yet, but every protected request will fail with 503 until it is.

1. Run `eureka-server` → wait until you can open **http://localhost:8761** and see the dashboard.
2. Run `auth-service`.
3. Run the remaining 6 backend services in any order: `patient-service`, `doctor-service`, `appointment-service`, `prescription-service`, `billing-service`, `notification-service`.
4. Give each service ~10-20s to register — refresh http://localhost:8761 and confirm all 7 app names appear under "Instances currently registered with Eureka".
5. Run `api-gateway` last, once at least `auth-service` is visible in Eureka.

Each service is otherwise independent — right-click the project → **Run As → Spring Boot App**, or run each `*Application.java`'s `main` method.

## API Gateway

`api-gateway` (port 8080, Spring Cloud Gateway, reactive/WebFlux) is the single entry point in front of all 7 backend services. Every MediFlow service already owns a distinct top-level path, so the gateway just forwards by path — no rewriting:

| Path               | Forwards to           | Auth required? |
|--------------------|------------------------|----------------|
| `/auth/**`          | auth-service            | No — public    |
| `/patients/**`      | patient-service          | Yes            |
| `/doctors/**`       | doctor-service            | Yes            |
| `/appointments/**`  | appointment-service       | Yes            |
| `/prescriptions/**` | prescription-service      | Yes            |
| `/bills/**`         | billing-service            | Yes            |
| `/notifications/**` | notification-service       | Yes            |

**How enforcement works** (`JwtAuthGlobalFilter`, a Spring Cloud Gateway `GlobalFilter`):

1. A request comes in to the gateway on port 8080.
2. If its path isn't one of the six protected prefixes above (e.g. it's `/auth/**` or the gateway's own landing page), it's forwarded immediately, no check.
3. Otherwise the filter looks for `Authorization: Bearer <token>`. Missing/malformed → **401**, request never leaves the gateway.
4. If present, the gateway calls `POST http://AUTH-SERVICE/auth/validate` (resolved via Eureka + Spring Cloud LoadBalancer, not a hardcoded host:port) with `{"token": "<token>"}`.
5. `valid: false` → **401**. auth-service unreachable/times out → **503**. `valid: true` → the request is forwarded to the target service, with `X-Auth-Email` / `X-Auth-Role` headers attached for the backend to use later if it wants to.

Route wiring lives in `GatewayRoutesConfig` (Java `RouteLocatorBuilder`, not YAML, so routes stay readable); the filter is global so it doesn't need to be attached per-route.

**Try it**: open **http://localhost:8080/**. Sign in with one of the demo accounts (form posts to `/auth/login` through the gateway), then hit "Test" next to any service in the directory — you'll see 401 before signing in and 200 after, proving the token round-trips through auth-service on every call.

**Known limitation (expected for local dev)**: the gateway only guards its own port. Hitting a backend service directly on its own port (e.g. `http://localhost:8081/patients`) still works with no token, since the individual services never had auth added to their own controllers — only the gateway checks. For a real deployment you'd block direct access to the backend ports (firewall / security group / Docker network isolation) so the gateway is the only path in from outside.

## Auth service

`auth-service` (port 8087) is a standalone login/registration microservice:

- `POST /auth/register` — create an account (`fullName`, `email`, `password`, `role` — one of `ADMIN`, `DOCTOR`, `PATIENT`, `STAFF`). Passwords are hashed with BCrypt, never stored or returned in plaintext.
- `POST /auth/login` — verify credentials and issue a signed JWT (HS256, 1 hour expiry by default — see `jwt.expiration-ms` in `application.properties`).
- `POST /auth/validate` — body `{"token": "..."}`, returns whether a token is valid plus the embedded email/role. This is what `api-gateway` calls on every protected request.
- `GET /auth/users`, `GET /auth/users/{id}`, `DELETE /auth/users/{id}` — manage registered accounts.

Three demo accounts are seeded on first run (shown on both the auth-service login page and the gateway's login page):

| Email                        | Password    | Role    |
|-------------------------------|-------------|---------|
| admin@mediflow.com            | admin123    | ADMIN   |
| ananya.gupta@mediflow.com      | doctor123   | DOCTOR  |
| rahul@example.com              | patient123  | PATIENT |

## Web UIs

Every service (except `eureka-server`, which has its own built-in dashboard) ships a small self-contained UI at its root URL — no extra setup needed, it's served automatically as a static page:

| Service              | UI URL                        | Theme  |
|-----------------------|-------------------------------|--------|
| api-gateway              | http://localhost:8080/         | Slate  |
| auth-service            | http://localhost:8087/         | Indigo |
| patient-service        | http://localhost:8081/         | Teal   |
| doctor-service          | http://localhost:8082/         | Violet |
| appointment-service     | http://localhost:8083/         | Orange |
| prescription-service    | http://localhost:8084/         | Green  |
| billing-service          | http://localhost:8085/         | Amber  |
| notification-service     | http://localhost:8086/         | Rose   |

Each UI is a single HTML file (embedded CSS/JS, no build step, no external dependencies). `api-gateway`'s UI is a login form (that authenticates through the gateway) plus a directory of every service with a one-click "Test" button showing live 401→200 enforcement. `auth-service`'s UI is a login/register card with a live table of registered users. The other six create/edit/delete records, filter lists, and trigger service-specific actions (update appointment status, pay/cancel a bill, resend a failed notification) — and each still talks straight to its own REST API on its own origin, unaffected by the gateway. Since `appointment-service`, `prescription-service`, `billing-service`, and `notification-service` reference patients/doctors/appointments by ID, it's easiest to create patients and doctors first, then use their IDs when booking appointments, billing, prescribing, or notifying.

## Notes

- Each service has its own in-memory H2 database (`create-drop`), so data resets on every restart. H2 console is enabled per-service at `/h2-console`.
- Inter-service calls (e.g. billing-service looking up a patient) use Spring Cloud OpenFeign + Eureka client-side discovery directly between services — the gateway isn't in that path, it's only for external/browser-facing traffic.
- If a service logs Eureka connection-refused warnings for the first ~30 seconds, that's normal — it's retrying registration until `eureka-server` is reachable. `api-gateway` will similarly log connection errors from `JwtAuthGlobalFilter` if `auth-service` isn't registered yet; they stop once it is.
- `auth-service` issues JWTs but doesn't enforce them anywhere yet — treat `jwt.secret` in its `application.properties` as a placeholder to replace before any real deployment. `api-gateway` doesn't need its own copy of that secret, since it delegates validation to auth-service over HTTP rather than verifying the signature itself.
- `api-gateway` uses the reactive stack (`spring-cloud-starter-gateway`, which pulls in `spring-boot-starter-webflux`). Don't add `spring-boot-starter-web` to its `pom.xml` — Spring Cloud Gateway requires a purely reactive classpath and won't start correctly with the servlet stack also present.
