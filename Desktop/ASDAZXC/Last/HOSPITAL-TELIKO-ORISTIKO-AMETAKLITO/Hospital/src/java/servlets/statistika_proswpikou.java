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
@WebServlet(name = "statistika_proswpikou", urlPatterns = {"/statistika_proswpikou"})
public class statistika_proswpikou extends HttpServlet {
  
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
            
            String type = request.getParameter("staff_type");
            String start = request.getParameter("start");
            String finish = request.getParameter("finish");
            
            String sql="";
            if (type.matches("doctor")) {
                System.out.println("doctor");
                sql= "SELECT id,doctor.name, COUNT(*) FROM `efimeria`,doctor WHERE (doctor.id=efimeria.doctor_1 or doctor.id=efimeria.doctor_2) and "
                    + "(`hmeromhnia`>='"+start+"' and `hmeromhnia`<='"+finish+"') group by id";
            }else if(type.matches("nurse")){
                System.out.println("nurse");
                sql= "SELECT id,nurse.name,COUNT(*) FROM `efimeria`,nurse WHERE (nurse.id=efimeria.nurse_1 or nurse.id=efimeria.nurse_2 or nurse.id=efimeria.nurse_3) and "
                    + "(`hmeromhnia`>='"+start+"' and `hmeromhnia`<='"+finish+"') group by id";
           }else if(type.matches("admin")){
                System.out.println("admin");
                sql= "SELECT id,admin.name,COUNT(*) FROM `efimeria`,admin \n" + "WHERE (admin.id=efimeria.admin) and (`hmeromhnia`>='"+start+"' and "
                    + "`hmeromhnia`<='"+finish+"')\n" + "group by id";
           }
            
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/hospital";
            String username = "root";
            String password = "";
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, username, password);//DriverManager.getConnection(url + ":" + port + "/" + db + "?characterEncoding=UTF-8", username, password);

            Statement st5 = con.createStatement();
            ResultSet rs5 = st5.executeQuery(sql);   
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet statistika_proswpikou</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h2>Efhmeries stuff tupou:" + type + "</h2>");
            
            out.println("<table>"); 
                out.println("<tr>");
                out.println("<th>Staff id</th>");
                out.println("<th>Name</th>");
                out.println("<th>Counter efhmeriwn</th>"); 
            out.println("</tr>");
            
            while(rs5.next()){
                   int id = rs5.getInt(1);
                   String name = rs5.getString(2);
                   int counter = rs5.getInt(3);
                                     
                   out.println("<tr>");
                   out.println("<td>"+id+"</td>");
                   out.println("<td>"+name+"</td>");
                   out.println("<td>"+counter+"</td>");
               out.println("</tr>");             
            }
            
            out.println("</table>");
            
            out.println("</body>");
            out.println("</html>");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(statistika_proswpikou.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(statistika_proswpikou.class.getName()).log(Level.SEVERE, null, ex);
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
