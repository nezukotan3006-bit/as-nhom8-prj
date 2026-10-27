package dao;

import context.DBContext;
import model.Appointment;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO extends DBContext {

    // 1. Thêm lịch hẹn mới từ phía bệnh nhân
    public boolean insertAppointment(Appointment app) {
        String sql = "INSERT INTO Appointments (PatientID, DoctorID, ServiceID, AppointmentDate, SlotTime, Status, Notes) "
                   + "VALUES (?, ?, ?, ?, ?, 'Pending', ?)";
        try {
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, app.getPatientId());
            ps.setInt(2, app.getDoctorId());
            ps.setInt(3, app.getServiceId());
            ps.setDate(4, app.getAppointmentDate());
            ps.setString(5, app.getSlotTime());
            ps.setString(6, app.getNotes() != null ? app.getNotes() : "Đặt lịch trực tuyến");
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 2. Lấy danh sách lịch hẹn của Bệnh nhân (có JOIN lấy Tên Bác sĩ và Tên Dịch vụ)
    public List<Appointment> getAppointmentsByPatient(int patientId) {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT a.*, d.FullName AS DoctorName, s.ServiceName AS ServiceName "
                   + "FROM Appointments a "
                   + "JOIN Users d ON a.DoctorID = d.UserID "
                   + "JOIN Services s ON a.ServiceID = s.ServiceID "
                   + "WHERE a.PatientID = ? "
                   + "ORDER BY a.AppointmentDate DESC";
        try {
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, patientId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Appointment app = new Appointment();
                app.setAppointmentId(rs.getInt("AppointmentID"));
                app.setPatientId(rs.getInt("PatientID"));
                app.setDoctorId(rs.getInt("DoctorID"));
                app.setServiceId(rs.getInt("ServiceID"));
                app.setAppointmentDate(rs.getDate("AppointmentDate"));
                app.setSlotTime(rs.getString("SlotTime"));
                app.setStatus(rs.getString("Status"));
                app.setNotes(rs.getString("Notes"));
                app.setDoctorName(rs.getString("DoctorName"));
                app.setServiceName(rs.getString("ServiceName"));
                list.add(app);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 3. Lấy danh sách lịch hẹn của Bác sĩ (có hỗ trợ lọc theo ngày tùy chọn)
    public List<Appointment> getAppointmentsByDoctor(int doctorId, String filterDate) {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT a.*, p.FullName AS PatientName, s.ServiceName AS ServiceName "
                   + "FROM Appointments a "
                   + "JOIN Users p ON a.PatientID = p.UserID "
                   + "JOIN Services s ON a.ServiceID = s.ServiceID "
                   + "WHERE a.DoctorID = ?";
        
        boolean hasDate = (filterDate != null && !filterDate.trim().isEmpty());
        if (hasDate) {
            sql += " AND a.AppointmentDate = ?";
        }
        sql += " ORDER BY a.AppointmentDate DESC";

        try {
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, doctorId);
            if (hasDate) {
                ps.setString(2, filterDate);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Appointment app = new Appointment();
                app.setAppointmentId(rs.getInt("AppointmentID"));
                app.setPatientId(rs.getInt("PatientID"));
                app.setDoctorId(rs.getInt("DoctorID"));
                app.setServiceId(rs.getInt("ServiceID"));
                app.setAppointmentDate(rs.getDate("AppointmentDate"));
                app.setSlotTime(rs.getString("SlotTime"));
                app.setStatus(rs.getString("Status"));
                app.setNotes(rs.getString("Notes"));
                app.setServiceName(rs.getString("ServiceName"));
                list.add(app);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 4. Lấy danh sách lịch hẹn của Bác sĩ (chỉ truyền vào ID, không lọc ngày)
    public List<Appointment> getAppointmentsByDoctor(int doctorId) {
        return getAppointmentsByDoctor(doctorId, null);
    }
}