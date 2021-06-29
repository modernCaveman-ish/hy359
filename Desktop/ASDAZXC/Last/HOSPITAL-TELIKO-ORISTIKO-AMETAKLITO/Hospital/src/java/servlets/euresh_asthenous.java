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
@WebServlet(name = "euresh_asthenous", urlPatterns = {"/euresh_asthenous"})
public class euresh_asthenous extends HttpServlet {

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

            String amka = request.getParameter("amka");
            String xronia_nosimata = request.getParameter("nosima");

            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/hospital";
            String username = "root";
            String password = "";
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, username, password);//DriverManager.getConnection(url + ":" + port + "/" + db + "?characterEncoding=UTF-8", username, password);

            String sql = "SELECT * FROM `patient` WHERE `amka`=" + amka;
            String sq2 = "SELECT `amka`, `nosima` FROM `xronia_nosimata` WHERE `amka`=" + amka;
            
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            //ResultSet rs = st.executeQuery(sq2);

            Statement st2 = con.createStatement();
            ResultSet rs2 = st2.executeQuery(sq2);
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet euresh_asthenous</title>");
            out.println("</head>");
            out.println("<body>");
            
            if (rs.next()) {
                String asfaleia = rs.getString(2);
                String name = rs.getString(3);
                String dieuthinsi = rs.getString(4);
               
                out.println("<h3>O asthenis yparxei me ta stoixeia:</h3>");
                out.println("<h5>Amka:" + amka + "</h5>");
                out.println("<h5>Name:" + name + "</h5>");
                out.println("<h5>Asfaleia:" + asfaleia + "</h5>");
                out.println("<h5>Dieuthinsi:" + dieuthinsi + "</h5>");
               
               while(rs2.next()){
                   String nosimata = rs2.getString(2);
                   
                   out.println("<h5>Nosima:" + nosimata + "</h5>");
               }
               out.println("<h2>Gia kataxwrisi episkepsis kai simptwmatwn pigainete edw:</h2>");
            
            out.println("<a href=\"episkepsh\">EPISKEPSH</a>");
               
            } else {
                out.println("<h2>O asthenis den yparxei sto systhma. </h2>");
                out.println("<h2>Gia eisagwgh pathste ton apokatw syndesmo:</h2>");
                out.println("<a href=\"eisagwgh.html\">Eisagwghi asthenous sthn vasi</a>");
            }
                rs.close();
                rs2.close();
                con.close();
            
            out.println("</body>");
            out.println("</html>");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(euresh_asthenous.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(euresh_asthenous.class.getName()).log(Level.SEVERE, null, ex);
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
