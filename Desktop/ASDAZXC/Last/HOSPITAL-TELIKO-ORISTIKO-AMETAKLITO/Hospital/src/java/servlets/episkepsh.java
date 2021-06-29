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
@WebServlet(name = "episkepsh", urlPatterns = {"/episkepsh"})
public class episkepsh extends HttpServlet {

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
            out.println("<title>Kataxwrisis Episkepseis kai Simptwmatwn</title>");
            out.println("</head>");
            out.println("<body>");

            out.println("<h2>Kataxwrisis Episkepsis kai Simptwmatwn</h2>");
            out.println("<form action=\"http://localhost:8084/Hospital/kataxorisi_episkepsis\" METHOD=POST>");
            
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String today = df.format(new Date());
            System.out.println("formatted:"+today);

            out.println("<div>----------------------------------------------</div>");
            out.println("Date <input type=\"date\" name=\"hmerominia\" value=\""+today+"\">");
            out.println("<div>----------------------------------------------</div>");
            out.println("amka <input type=\"text\" name=\"amka\" value=\"\">");
            out.println("<div>----------------------------------------------</div>");
            out.println("simptwma <input type=\"text\" name=\"symptwma\" value=\"\">");
            out.println("<div>----------------------------------------------</div>");
            out.println("<h5>parapompi se giatro</h5>");
            out.println(" <select  name = \"doctorID\" id = \"doctorID\">");
            
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/hospital";
            String username = "root";
            String password = "";
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, username, password);//DriverManager.getConnection(url + ":" + port + "/" + db + "?characterEncoding=UTF-8", username, password);

            String sql = "SELECT `id`,`name`,`eidikotita` FROM `doctor`,`efimeria` where efimeria.hmeromhnia='"+today+"' and (doctor.id=efimeria.doctor_1 OR efimeria.doctor_2=doctor.id)";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while(rs.next()){
                   
               int doctorID = rs.getInt(1);
               String name = rs.getString(2);
               String eidik = rs.getString(3);

               out.println("<option value=\""+doctorID+"\">"+name+" - "+eidik+"</option>");
               }
                        
            out.println(" </select>");
            out.println("<div>----------------------------------------------</div>");
            out.println("<input type=\"SUBMIT\" value=\"Submit\">");
            out.println(" </form>");            
           
            out.println("<h2>Kataxwrisi Diagnosis</h2>");
            out.println("<form action=\"http://localhost:8084/Hospital/kataxorisi_anthrwpinou_dinamikou\" METHOD=POST>");
 
            out.println("<div>----------------------------------------------</div>");
            out.println("Date <input type=\"date\" name=\"hmerominia\" value=\""+today+"\">");
            out.println("<div>----------------------------------------------</div>");
            out.println("amka <input type=\"text\" name=\"amka\" value=\"\">");
            out.println("<div>----------------------------------------------</div>");
            out.println(" <select  name = \"astheneia\" id = \"astheneia\">");
            
           // Connection con = DriverManager.getConnection(url, username, password);//DriverManager.getConnection(url + ":" + port + "/" + db + "?characterEncoding=UTF-8", username, password);

            String sql2 = "SELECT * FROM `astheneia`";
            Statement st2 = con.createStatement();
            ResultSet rs2 = st2.executeQuery(sql2);
            
            while(rs2.next()){
                   
                   String nameAsth = rs2.getString(1);
                   
                   out.println("<option value=\""+nameAsth+"\">"+nameAsth+"</option>");
               }
                        
            out.println(" </select>");
            out.println("<div>----------------------------------------------</div>");
            out.println("<input type=\"SUBMIT\" value=\"Submit\">");
            out.println(" </form>");
            
            out.println("<h2>Diexagwgi Exetasis</h2>");
            out.println("<form action=  " + "got_examined" + " METHOD=POST>");
            
            out.println("<div>----------------------------------------------</div>");
            out.println("Date <input type=\"date\" name=\"hmerominia\" value=\""+today+"\">");
            out.println("<div>----------------------------------------------</div>");
            out.println("amka <input type=\"text\" name=\"amka\" value=\"\">");
            out.println("<div>----------------------------------------------</div>");
            out.println("exetasi <input type=\"text\" name=\"exetasi\" value=\"\">");
            out.println("<div>----------------------------------------------</div>");
            out.println("apotelesma <input type=\"text\" name=\"result\" value=\"\">");
            out.println("<div>----------------------------------------------</div>");
        
            out.println("<input type=\"SUBMIT\" value=\"Submit\">");
            out.println(" </form>");            
            
            out.println("</body>");
            out.println("</html>");
            con.close();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(episkepsh.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(episkepsh.class.getName()).log(Level.SEVERE, null, ex);
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
