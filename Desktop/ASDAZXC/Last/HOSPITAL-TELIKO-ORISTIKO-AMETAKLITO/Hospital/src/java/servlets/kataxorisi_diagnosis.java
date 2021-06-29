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
@WebServlet(name = "kataxorisi_diagnosis", urlPatterns = {"/kataxorisi_diagnosis"})
public class kataxorisi_diagnosis extends HttpServlet {

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
            String hmerominia = request.getParameter("hmerominia");
            String astheneia = request.getParameter("astheneia");
                     
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/hospital";
            String username = "root";
            String password = "";
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, username, password);//DriverManager.getConnection(url + ":" + port + "/" + db + "?characterEncoding=UTF-8", username, password);
       
            String sq4 = "UPDATE `episkepsi` SET `astheneia`= '"+astheneia+"' WHERE `imerominia`= '"+hmerominia+"' and`amka`= "+amka+" ";
            System.out.println("sql:" + sq4);

            Statement st = con.createStatement();
            int rsResult1 = st.executeUpdate(sq4);
           
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Kataxwrisis Episkepseis kai Simptwmatwn</title>");
            out.println("</head>");
            out.println("<body>");
            
            if (rsResult1==1) {
                out.println("<h1>Egine diagnwsi tou asthenous:</h1>");
                out.println("<h4>Hmerominia:"+hmerominia+"</h4>");
                out.println("<h4>AMKA asthenous:"+amka+"</h4>");
                out.println("<h4>Exei tin asthenia :"+astheneia+"</h4>");
                
            }else{
                out.println("<h1>Den mporesame na katagrapsoume tn diagnwsi</h1>");
            }
            
            out.println("</body>");
            out.println("</html>");
            con.close();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(kataxorisi_diagnosis.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(kataxorisi_diagnosis.class.getName()).log(Level.SEVERE, null, ex);
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
