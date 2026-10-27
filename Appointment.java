package model;

import java.sql.Date;

public class Appointment {
    private int appointmentId;
    private int patientId;
    private int doctorId;
    private int serviceId;
    private Date appointmentDate;
    private String slotTime;
    private String status;
    private String notes;
    private String doctorName;
    private String serviceName;

    // Hàm khởi tạo mặc định không tham số
    public Appointment() {
    }

    // Hàm khởi tạo đầy đủ tham số
    public Appointment(int appointmentId, int patientId, int doctorId, int serviceId, Date appointmentDate, String slotTime, String status, String notes, String doctorName, String serviceName) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.serviceId = serviceId;
        this.appointmentDate = appointmentDate;
        this.slotTime = slotTime;
        this.status = status;
        this.notes = notes;
        this.doctorName = doctorName;
        this.serviceName = serviceName;
    }

    // --- Getter và Setter ---

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getSlotTime() {
        return slotTime;
    }

    public void setSlotTime(String slotTime) {
        this.slotTime = slotTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}