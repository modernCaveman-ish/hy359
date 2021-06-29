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
@WebServlet(name = "erwthseis", urlPatterns = {"/erwthseis"})
public class erwthseis extends HttpServlet {

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
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet erwthseis</title>");            
            out.println("</head>");
            out.println("<body>");
            
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String today = df.format(new Date());
            System.out.println("formatted:"+today);
            
            out.println("<h2>Katastash efhmerias: "+ today +"</h2>");
            
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/hospital";
            String username = "root";
            String password = "";
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, username, password);//DriverManager.getConnection(url + ":" + port + "/" + db + "?characterEncoding=UTF-8", username, password);

            String sql = "SELECT * FROM `episkepsi` WHERE `imerominia` = '"+today+"'";
            String sq2 = "SELECT COUNT(*) FROM `episkepsi` WHERE `imerominia` = '"+today+"'";
            String sq3 = "SELECT COUNT(*) FROM `episkepsi` WHERE `imerominia` = '"+today+"' and `exitirio` != `imerominia`";
            String sq4 = "SELECT COUNT(*) FROM `episkepsi` WHERE `imerominia` = '"+today+"' and `exitirio` = `imerominia`";
            
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
          
           out.println("<table>"); 
                out.println("<tr>");
                out.println("<th>Amka</th>");
                out.println("<th>Astheneia</th>");
                out.println("<th>Therapwn Iatros</th>");
                out.println("<th>Simptwma</th>"); 
                out.println("<th>Exitirio</th>");  
            out.println("</tr>");
   
            while(rs.next()){
                   int amka = rs.getInt(2);
                   String astheneia = rs.getString(3);
                   String giatros = rs.getString(4);
                   String simptwma = rs.getString(5);
                   String exitirio = rs.getString(6);
                                     
                   out.println("<tr>");
                   out.println("<td>"+amka+"</td>");
                   out.println("<td>"+astheneia+"</td>");
                   out.println("<td>"+giatros+"</td>");
                   out.println("<td>"+simptwma+"</td>");
                   out.println("<td>"+exitirio+"</td>");
               out.println("</tr>");             
            }
                   
            out.println("</table>");
            
            Statement st2 = con.createStatement();
            ResultSet rs2 = st2.executeQuery(sq2);
            if(rs2.next()){
                int sinolo = rs2.getInt(1);
                out.println("<h5>Sinolo episkepsewn: " +sinolo+ "</h5>");
            }
            
            Statement st3 = con.createStatement();
            ResultSet rs3 = st3.executeQuery(sq3);
             if(rs3.next()){
                int exitirio = rs3.getInt(1);
                out.println("<h5>Sinolo asthenwn pou den nosileutikan: " +exitirio+ "</h5>");
            }
             
            Statement st4 = con.createStatement(); 
            ResultSet rs4 = st4.executeQuery(sq4);
            if(rs4.next()){
               int asthenwn = rs4.getInt(1);
               out.println("<h5>Sinolo asthenwn pou nosileuontai: " +asthenwn+ "</h5>");
            }
     
            out.println("</body>");
            out.println("</html>");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(erwthseis.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(erwthseis.class.getName()).log(Level.SEVERE, null, ex);
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
