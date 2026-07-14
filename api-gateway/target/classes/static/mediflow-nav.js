(function () {
  var services = [
    { name: 'Gateway / Login', port: 8080 },
    { name: 'Patients',        port: 8081 },
    { name: 'Doctors',         port: 8082 },
    { name: 'Appointments',    port: 8083 },
    { name: 'Prescriptions',   port: 8084 },
    { name: 'Billing',         port: 8085 },
    { name: 'Notifications',   port: 8086 },
    { name: 'Auth / Users',    port: 8087 },
  ];
  var TOKEN_KEY = 'mediflow_token';
  var USER_KEY = 'mediflow_user';

  // 1) Pick up a token/user handed off via the URL from another service's nav link.
  //    (localStorage is per-origin, and each service is its own port/origin, so a
  //    plain localStorage write on one page can't be seen by another page's origin.
  //    Passing the token once as a query param on the link, then re-saving it into
  //    the destination origin's own localStorage, is what makes "stay logged in
  //    while clicking around" work across ports.)
  var params = new URLSearchParams(location.search);
  if (params.get('mf_token')) {
    localStorage.setItem(TOKEN_KEY, params.get('mf_token'));
    if (params.get('mf_user')) localStorage.setItem(USER_KEY, params.get('mf_user'));
    params.delete('mf_token');
    params.delete('mf_user');
    var clean = location.pathname + (params.toString() ? '?' + params.toString() : '') + location.hash;
    history.replaceState({}, '', clean);
  }

  function getToken() { return localStorage.getItem(TOKEN_KEY); }
  function getUser() { try { return JSON.parse(localStorage.getItem(USER_KEY) || 'null'); } catch (e) { return null; } }
  function setSession(token, user) {
    localStorage.setItem(TOKEN_KEY, token);
    localStorage.setItem(USER_KEY, JSON.stringify(user || null));
    render();
  }
  function clearSession() {
    localStorage.removeItem(TOKEN_KEY);
    localStorage.removeItem(USER_KEY);
    render();
  }
  window.MediFlowAuth = { getToken: getToken, getUser: getUser, setSession: setSession, clearSession: clearSession };

  // 2) Auto-attach the bearer token to this page's own (relative-path) API calls.
  var origFetch = window.fetch.bind(window);
  window.fetch = function (input, init) {
    init = init || {};
    var url = typeof input === 'string' ? input : (input && input.url) || '';
    var isRelative = url.indexOf('/') === 0;
    var t = getToken();
    if (t && isRelative) {
      init.headers = Object.assign({}, init.headers, { Authorization: 'Bearer ' + t });
    }
    return origFetch(input, init);
  };

  // 3) Nav bar UI.
  var style = document.createElement('style');
  style.textContent =
    '.mf-nav{display:flex;align-items:center;gap:4px;flex-wrap:wrap;background:#0f172a;' +
    'padding:10px 20px;font-family:"Segoe UI",system-ui,-apple-system,sans-serif;}' +
    '.mf-nav a{color:#cbd5e1;text-decoration:none;font-size:13px;padding:7px 12px;border-radius:6px;transition:.15s;}' +
    '.mf-nav a:hover{background:#1e293b;color:#fff;}' +
    '.mf-nav a.active{background:#334155;color:#fff;font-weight:600;}' +
    '.mf-nav .mf-spacer{flex:1;}' +
    '.mf-nav .mf-user{color:#94a3b8;font-size:12px;margin-right:4px;white-space:nowrap;}' +
    '.mf-nav .mf-logout{cursor:pointer;color:#fca5a5;}' +
    '.mf-nav .mf-logout:hover{background:#450a0a;}';
  document.head.appendChild(style);

  function buildUrl(port) {
    var t = getToken(), u = getUser();
    var qs = '';
    if (t) qs = '?mf_token=' + encodeURIComponent(t) + (u ? '&mf_user=' + encodeURIComponent(JSON.stringify(u)) : '');
    return 'http://localhost:' + port + '/' + qs;
  }

  function render() {
    var nav = document.getElementById('mediflowNav');
    if (!nav) {
      nav = document.createElement('div');
      nav.id = 'mediflowNav';
      nav.className = 'mf-nav';
      var header = document.querySelector('header');
      if (header && header.parentNode) {
        header.insertAdjacentElement('afterend', nav);
      } else {
        document.body.insertBefore(nav, document.body.firstChild);
      }
    }
    var t = getToken(), u = getUser();
    var currentPort = location.port || (location.protocol === 'https:' ? '443' : '80');
    nav.innerHTML =
      services.map(function (s) {
        var active = String(currentPort) === String(s.port) ? ' active' : '';
        return '<a class="' + active.trim() + '" href="' + buildUrl(s.port) + '">' + s.name + '</a>';
      }).join('') +
      '<span class="mf-spacer"></span>' +
      (t
        ? '<span class="mf-user">' + (u ? (u.fullName + ' · ' + u.role) : 'Signed in') + '</span>' +
          '<a class="mf-logout" id="mfLogoutBtn">Log out</a>'
        : '<span class="mf-user">Not signed in</span>');
    if (t) {
      var btn = document.getElementById('mfLogoutBtn');
      if (btn) {
        btn.addEventListener('click', function () {
          clearSession();
          location.href = buildUrl(8080).split('?')[0];
        });
      }
    }
  }

  if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', render);
  } else {
    render();
  }
})();
