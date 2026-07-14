package com.mediflow.billingservice.controller;

import com.mediflow.billingservice.dto.BillRequest;
import com.mediflow.billingservice.dto.BillResponse;
import com.mediflow.billingservice.entity.BillStatus;
import com.mediflow.billingservice.service.BillingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bills")
public class BillController {

    private final BillingService billingService;

    public BillController(BillingService billingService) {
        this.billingService = billingService;
    }

    @PostMapping
    public ResponseEntity<BillResponse> createBill(
            @Valid @RequestBody BillRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(billingService.createBill(request));
    }

    @GetMapping
    public ResponseEntity<List<BillResponse>> getAllBills() {

        return ResponseEntity.ok(
                billingService.getAllBills()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<BillResponse> getBillById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                billingService.getBillById(id)
        );
    }

    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<BillResponse> getBillByAppointmentId(
            @PathVariable Long appointmentId) {

        return ResponseEntity.ok(
                billingService.getBillByAppointmentId(appointmentId)
        );
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<BillResponse>> getBillsByPatientId(
            @PathVariable Long patientId) {

        return ResponseEntity.ok(
                billingService.getBillsByPatientId(patientId)
        );
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<BillResponse>> getBillsByStatus(
            @PathVariable BillStatus status) {

        return ResponseEntity.ok(
                billingService.getBillsByStatus(status)
        );
    }

    @PutMapping("/{id}/pay")
    public ResponseEntity<BillResponse> payBill(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                billingService.payBill(id)
        );
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<BillResponse> cancelBill(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                billingService.cancelBill(id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBill(
            @PathVariable Long id) {

        billingService.deleteBill(id);

        return ResponseEntity.noContent().build();
    }
}
