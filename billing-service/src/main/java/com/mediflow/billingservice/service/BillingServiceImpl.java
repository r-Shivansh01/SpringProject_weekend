package com.mediflow.billingservice.service;

import com.mediflow.billingservice.client.AppointmentClient;
import com.mediflow.billingservice.client.DoctorClient;
import com.mediflow.billingservice.client.PatientClient;
import com.mediflow.billingservice.dto.*;
import com.mediflow.billingservice.entity.Bill;
import com.mediflow.billingservice.entity.BillStatus;
import com.mediflow.billingservice.exception.InvalidBillException;
import com.mediflow.billingservice.exception.ResourceNotFoundException;
import com.mediflow.billingservice.mapper.BillMapper;
import com.mediflow.billingservice.repository.BillRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class BillingServiceImpl implements BillingService {

    private final BillRepository billRepository;
    private final AppointmentClient appointmentClient;
    private final PatientClient patientClient;
    private final DoctorClient doctorClient;

    public BillingServiceImpl(
            BillRepository billRepository,
            AppointmentClient appointmentClient,
            PatientClient patientClient,
            DoctorClient doctorClient) {

        this.billRepository = billRepository;
        this.appointmentClient = appointmentClient;
        this.patientClient = patientClient;
        this.doctorClient = doctorClient;
    }

    @Override
    public BillResponse createBill(BillRequest request) {

        if (billRepository.existsByAppointmentId(
                request.getAppointmentId())) {

            throw new InvalidBillException(
                    "A bill already exists for appointment id: "
                            + request.getAppointmentId()
            );
        }

        AppointmentResponse appointment =
                appointmentClient.getAppointmentById(
                        request.getAppointmentId()
                );

        if ("CANCELLED".equalsIgnoreCase(appointment.getStatus())) {
            throw new InvalidBillException(
                    "Cannot create a bill for a cancelled appointment"
            );
        }

        PatientResponse patient =
                patientClient.getPatientById(
                        appointment.getPatientId()
                );

        DoctorResponse doctor =
                doctorClient.getDoctorById(
                        appointment.getDoctorId()
                );

        if (doctor.getConsultationFee() == null) {
            throw new InvalidBillException(
                    "Doctor consultation fee is unavailable"
            );
        }

        BigDecimal consultationFee =
                BigDecimal.valueOf(doctor.getConsultationFee());

        BigDecimal additionalCharges =
                request.getAdditionalCharges() == null
                        ? BigDecimal.ZERO
                        : request.getAdditionalCharges();

        BigDecimal totalAmount =
                consultationFee.add(additionalCharges);

        Bill bill = new Bill();

        bill.setAppointmentId(appointment.getId());
        bill.setPatientId(appointment.getPatientId());
        bill.setDoctorId(appointment.getDoctorId());
        bill.setConsultationFee(consultationFee);
        bill.setAdditionalCharges(additionalCharges);
        bill.setTotalAmount(totalAmount);
        bill.setStatus(BillStatus.PENDING);
        bill.setCreatedAt(LocalDateTime.now());

        Bill savedBill = billRepository.save(bill);

        return BillMapper.toResponse(
                savedBill,
                patient,
                doctor
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<BillResponse> getAllBills() {

        return billRepository.findAll()
                .stream()
                .map(this::buildResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public BillResponse getBillById(Long id) {

        return buildResponse(findBill(id));
    }

    @Override
    @Transactional(readOnly = true)
    public BillResponse getBillByAppointmentId(
            Long appointmentId) {

        appointmentClient.getAppointmentById(appointmentId);

        Bill bill = billRepository
                .findByAppointmentId(appointmentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Bill not found for appointment id: "
                                        + appointmentId
                        )
                );

        return buildResponse(bill);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BillResponse> getBillsByPatientId(
            Long patientId) {

        patientClient.getPatientById(patientId);

        return billRepository.findByPatientId(patientId)
                .stream()
                .map(this::buildResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BillResponse> getBillsByStatus(
            BillStatus status) {

        return billRepository.findByStatus(status)
                .stream()
                .map(this::buildResponse)
                .toList();
    }

    @Override
    public BillResponse payBill(Long id) {

        Bill bill = findBill(id);

        if (bill.getStatus() == BillStatus.PAID) {
            throw new InvalidBillException(
                    "Bill is already paid"
            );
        }

        if (bill.getStatus() == BillStatus.CANCELLED) {
            throw new InvalidBillException(
                    "Cancelled bill cannot be paid"
            );
        }

        bill.setStatus(BillStatus.PAID);
        bill.setPaidAt(LocalDateTime.now());

        Bill updatedBill = billRepository.save(bill);

        return buildResponse(updatedBill);
    }

    @Override
    public BillResponse cancelBill(Long id) {

        Bill bill = findBill(id);

        if (bill.getStatus() == BillStatus.PAID) {
            throw new InvalidBillException(
                    "Paid bill cannot be cancelled"
            );
        }

        if (bill.getStatus() == BillStatus.CANCELLED) {
            throw new InvalidBillException(
                    "Bill is already cancelled"
            );
        }

        bill.setStatus(BillStatus.CANCELLED);

        Bill updatedBill = billRepository.save(bill);

        return buildResponse(updatedBill);
    }

    @Override
    public void deleteBill(Long id) {

        Bill bill = findBill(id);

        if (bill.getStatus() == BillStatus.PAID) {
            throw new InvalidBillException(
                    "Paid bill cannot be deleted"
            );
        }

        billRepository.delete(bill);
    }

    private Bill findBill(Long id) {

        return billRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Bill not found with id: " + id
                        )
                );
    }

    private BillResponse buildResponse(Bill bill) {

        PatientResponse patient =
                patientClient.getPatientById(
                        bill.getPatientId()
                );

        DoctorResponse doctor =
                doctorClient.getDoctorById(
                        bill.getDoctorId()
                );

        return BillMapper.toResponse(
                bill,
                patient,
                doctor
        );
    }
}
