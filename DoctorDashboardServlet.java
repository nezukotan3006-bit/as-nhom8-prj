package controller;

import dao.AppointmentDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Appointment;
import model.User;

@WebServlet(name = "DoctorDashboardServlet", urlPatterns = {"/DoctorDashboard"})
public class DoctorDashboardServlet extends HttpServlet {
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        User doctor = (User) session.getAttribute("account");
        
        if (doctor == null || !"DOCTOR".equals(doctor.getRole())) {
            response.sendRedirect(request.getContextPath() + "/view/login.jsp");
            return;
        }

        String filterDate = request.getParameter("date");
        
        AppointmentDAO dao = new AppointmentDAO();
        List<Appointment> list = dao.getAppointmentsByDoctor(doctor.getUserId(), filterDate);

        request.setAttribute("appointments", list);
        request.getRequestDispatcher("/view/doctordashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Doctor Dashboard Servlet";
    }
}