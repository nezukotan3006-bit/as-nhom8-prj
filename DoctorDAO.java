package dao;

import context.DBContext;
import model.Appointment;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO extends DBContext {

    public List<Appointment> getAppointmentsByDoctor(int doctorId, String date) {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT a.AppointmentID, a.PatientID, a.DoctorID, a.ServiceID, "
                   + "a.AppointmentDate, a.SlotTime, a.Status, a.Notes, "
                   + "u.FullName AS DoctorName, s.ServiceName AS ServiceName "
                   + "FROM Appointments a "
                   + "JOIN Users u ON a.DoctorID = u.UserID "
                   + "JOIN Services s ON a.ServiceID = s.ServiceID "
                   + "WHERE a.DoctorID = ?";
        
        if (date != null && !date.trim().isEmpty()) {
            sql += " AND a.AppointmentDate = ?";
        }
        
        sql += " ORDER BY a.AppointmentDate DESC, a.SlotTime ASC";

        try {
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, doctorId);
            
            if (date != null && !date.trim().isEmpty()) {
                ps.setString(2, date);
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
                
                app.setDoctorName(rs.getString("DoctorName"));
                app.setServiceName(rs.getString("ServiceName"));
                
                list.add(app);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}