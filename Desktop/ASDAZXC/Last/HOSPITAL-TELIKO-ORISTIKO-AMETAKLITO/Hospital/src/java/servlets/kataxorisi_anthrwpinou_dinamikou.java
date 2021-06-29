/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author kosta
 */
@WebServlet(name = "kataxorisi_anthrwpinou_dinamikou", urlPatterns = {"/kataxorisi_anthrwpinou_dinamikou"})
public class kataxorisi_anthrwpinou_dinamikou extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet kataxorisi_anthrwpinou_dinamikou</title>");
            out.println("</head>");
            out.println("<body>");

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String today = df.format(new Date());
            System.out.println("formatted:" + today);

            out.println("<h2>Pioi exoun simera vardia imera: " + today + "</h2>");

            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/hospital";
            String username = "root";
            String password = "";
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, username, password);//DriverManager.getConnection(url + ":" + port + "/" + db + "?characterEncoding=UTF-8", username, password);

            String sql = "SELECT * FROM `efimeria` WHERE `hmeromhnia`='" + today + "'";

            Statement st = con.createStatement();
            ResultSet rs5 = st.executeQuery(sql);

            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Doctor1</th>");
            out.println("<th>Doctor2</th>");
            out.println("<th>Nurse1</th>");
            out.println("<th>Nurse2</th>");
            out.println("<th>Nurse3</th>");
            out.println("<th>Admin</th>");
            out.println("</tr>");

            while (rs5.next()) {
                int doctor1 = rs5.getInt(1);
                int doctor2 = rs5.getInt(2);
                int nurse1 = rs5.getInt(3);
                int nurse2 = rs5.getInt(4);
                int nurse3 = rs5.getInt(5);
                int admin = rs5.getInt(6);

                out.println("<tr>");
                out.println("<td>" + doctor1 + "</td>");
                out.println("<td>" + doctor2 + "</td>");
                out.println("<td>" + nurse1 + "</td>");
                out.println("<td>" + nurse2 + "</td>");
                out.println("<td>" + nurse3 + "</td>");
                out.println("<td>" + admin + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");

            out.println("<h2>Statistika Proswpikou</h2>");

            out.println("<form action=\"http://localhost:8084/Hospital/statistika_proswpikou\" METHOD=POST>");

            out.println("<div>----------------------------------------------</div>");
            out.println("Epilexte typo ergazomenou:");
            out.println(" <select name = \"staff_type\" id = \"staff_type\">");
            out.println("<option value=\"doctor\">doctor</option>");
            out.println("<option value=\"nurse\">nurse</option>");
            out.println("<option value=\"admin\">admin</option>");
            out.println(" </select>");
            out.println("<div>----------------------------------------------</div>");
            out.println("Date start <input type=\"date\" name=\"start\" value=\"\">");
            out.println("<div>----------------------------------------------</div>");
            out.println("Date finish <input type=\"date\" name=\"finish\" value=\"\">");
            out.println("<div>----------------------------------------------</div>");
            out.println("<input type=\"SUBMIT\" value=\"Submit\">");
            out.println(" </form>");

            out.println("</body>");
            out.println("</html>");

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(kataxorisi_anthrwpinou_dinamikou.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(kataxorisi_anthrwpinou_dinamikou.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
