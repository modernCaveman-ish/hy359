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
@WebServlet(name = "covid_report", urlPatterns = {"/covid_report"})
public class covid_report extends HttpServlet {

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

            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/hospital";
            String username = "root";
            String password = "";
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, username, password);//DriverManager.getConnection(url + ":" + port + "/" + db + "?characterEncoding=UTF-8", username, password);

            String sql = " SELECT patient.amka,patient.name,patient.dieuthinsi,'den exei' as xronia_nosimata, episkepsi.imerominia,episkepsi.exitirio FROM patient"
                    + ",episkepsi WHERE patient.amka = episkepsi.amka and `astheneia`='covid-19'"
                    + "and patient"
                    + ".amka not in(select amka from xronia_nosimata"
                    + ")UNION SELECT patient.amka,patient.name,patient.dieuthinsi"
                    + ", GROUP_CONCAT(nosima SEPARATOR ', ') as xronia_nosimata, episkepsi.imerominia"
                    + ",episkepsi.exitirio FROM patient,episkepsi"
                    + ",xronia_nosimata WHERE patient.amka = episkepsi.amka and `astheneia`='covid-19'"
                    + "and xronia_nosimata.amka = episkepsi.amka "
                    + "group by xronia_nosimata.amka ";

            Statement st5 = con.createStatement();
            ResultSet rs5 = st5.executeQuery(sql);

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet statistika_proswpikou</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h2>Covid-19 report</h2>");

            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Amka</th>");
            out.println("<th>Name</th>");
            out.println("<th>Dieuthinsi</th>");
            out.println("<th>Xronia Nosimata</th>");
            out.println("<th>Eisagwgi</th>");
            out.println("<th>Exitirio</th>");
            out.println("</tr>");

            while (rs5.next()) {
                int amka = rs5.getInt(1);
                String name = rs5.getString(2);
                String dieuthinsi = rs5.getString(3);
                String xronia_nosimata = rs5.getString(4);
                String imeromia = rs5.getString(5);
                String exitirio = rs5.getString(6);

                out.println("<tr>");
                out.println("<td>" + amka + "</td>");
                out.println("<td>" + name + "</td>");
                out.println("<td>" + dieuthinsi + "</td>");
                out.println("<td>" + xronia_nosimata + "</td>");
                out.println("<td>" + imeromia + "</td>");
                out.println("<td>" + exitirio + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");

            out.println("</body>");
            out.println("</html>");
        } catch (SQLException ex) {
            Logger.getLogger(covid_report.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(covid_report.class.getName()).log(Level.SEVERE, null, ex);
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
