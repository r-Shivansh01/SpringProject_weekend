package com.mediflow.billingservice.service;

import com.mediflow.billingservice.dto.BillRequest;
import com.mediflow.billingservice.dto.BillResponse;
import com.mediflow.billingservice.entity.BillStatus;

import java.util.List;

public interface BillingService {

    BillResponse createBill(BillRequest request);

    List<BillResponse> getAllBills();

    BillResponse getBillById(Long id);

    BillResponse getBillByAppointmentId(Long appointmentId);

    List<BillResponse> getBillsByPatientId(Long patientId);

    List<BillResponse> getBillsByStatus(BillStatus status);

    BillResponse payBill(Long id);

    BillResponse cancelBill(Long id);

    void deleteBill(Long id);
}
