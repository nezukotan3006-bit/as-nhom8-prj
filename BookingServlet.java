package controller;

import dao.AppointmentDAO;
import model.Appointment;
import model.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "BookingServlet", urlPatterns = {"/patient/book"})
public class BookingServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        request.getRequestDispatcher("/view/booking.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        User account = (User) session.getAttribute("account");

        if (account == null) {
            response.sendRedirect(request.getContextPath() + "/view/login.jsp");
            return;
        }

        try {
            int doctorId = Integer.parseInt(request.getParameter("doctorId"));
            int serviceId = Integer.parseInt(request.getParameter("serviceId"));
            String dateStr = request.getParameter("appointmentDate");
            String slotTime = request.getParameter("appointmentTime");

            // Sử dụng java.sql.Date tường minh để không bị lỗi biên dịch
            java.sql.Date appointmentDate = java.sql.Date.valueOf(dateStr);
            int patientId = account.getUserId();

            Appointment app = new Appointment();
            app.setPatientId(patientId);
            app.setDoctorId(doctorId);
            app.setServiceId(serviceId);
            app.setAppointmentDate(appointmentDate);
            app.setSlotTime(slotTime);
            app.setStatus("Pending");

            AppointmentDAO dao = new AppointmentDAO();
            boolean success = dao.insertAppointment(app);

            if (success) {
                response.sendRedirect(request.getContextPath() + "/PatientHistory");
            } else {
                request.setAttribute("error", "Đặt lịch thất bại, vui lòng thử lại!");
                request.getRequestDispatcher("/view/booking.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi dữ liệu đầu vào!");
            request.getRequestDispatcher("/view/booking.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Booking Servlet";
    }
}