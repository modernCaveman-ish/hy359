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

@WebServlet(name = "statistika_episkepswn_xron_diast", urlPatterns = {"/statistika_episkepswn_xron_diast"})
public class statistika_episkepswn_xron_diast extends HttpServlet {

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

            String type = request.getParameter("type");
            String start = request.getParameter("start");
            String finish = "";
            String sql = "";
            String sql1="", sql2="", sql3 = "";
            if (type.contains("month")) {
                finish = request.getParameter("finish");
                sql = "select\n"
                        + "(SELECT count(*) FROM `episkepsi` where  episkepsi.imerominia>='" + start + "' and episkepsi.imerominia<='" + finish + "') as total,\n"
                        + "(SELECT COUNT(*) FROM `episkepsi` WHERE `exitirio` != `imerominia` and episkepsi.imerominia>='" + start + "' and episkepsi.imerominia<='" + finish + "') as noshleutikan,\n"
                        + "(SELECT COUNT(*) FROM `episkepsi` WHERE `exitirio` = `imerominia` and episkepsi.imerominia>='" + start + "' and episkepsi.imerominia<='" + finish + "') as efygan";
                
                sql1 = "SELECT DISTINCT astheneia FROM `episkepsi` WHERE `imerominia` >= '" + start + "' AND `imerominia` <= '" + finish + "'";
                sql2 = "SELECT DISTINCT `exetasi` FROM `ekane_exetaseis` WHERE `imerominia` >= '" + start + "' AND `imerominia` <= '" + finish + "'";
                sql3 = "SELECT DISTINCT `drugs_name` FROM `pire_agogi` WHERE `imerominia` >= '" + start + "' AND `imerominia` <= '" + finish + "'";
                
                
            } else {
                sql = "select\n"
                        + "(SELECT count(*) FROM `episkepsi` where episkepsi.imerominia='" + start + "') as total,\n"
                        + "(SELECT COUNT(*) FROM `episkepsi` WHERE `exitirio` != `imerominia` and episkepsi.imerominia='" + start + "') as noshleutikan,\n"
                        + "(SELECT COUNT(*) FROM `episkepsi` WHERE `exitirio` = `imerominia` and episkepsi.imerominia='" + start + "') as efygan,"
                        + "(select distinct imerominia from episkepsi where episkepsi.imerominia='" + start + "') as date\n";
                
                sql1 = "SELECT DISTINCT astheneia FROM `episkepsi` WHERE `imerominia` = '" + start + "'";
                sql2 = "SELECT DISTINCT `exetasi` FROM `ekane_exetaseis` WHERE `imerominia` = '" + start + "'";
                sql3 = "SELECT DISTINCT `drugs_name` FROM `pire_agogi` WHERE `imerominia` = '" + start + "'";
            }

            System.out.println("sql:" + sql);

            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/hospital";
            String username = "root";
            String password = "";
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, username, password);//DriverManager.getConnection(url + ":" + port + "/" + db + "?characterEncoding=UTF-8", username, password);

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet statistika_episkepswn_xron_diast</title>");
            out.println("</head>");
            out.println("<body>");
            if (type.contains("day")) {
                out.println("<h2>Statistika efhmeriwn gia thn hmeromhnia: " + start + "</h2>");
            } else {
                out.println("<h2>Statistika efhmeriwn gia to diasthma: " + start + " - " + finish + "</h2>");
            }

            out.println("<table>");
            out.println("<tr>");
            if (type.contains("day")) {
                out.println("<th>Imerominia</th>");
            }
            out.println("<th>Synolo</th>");
            out.println("<th>Noshleuthkan</th>");
            out.println("<th>Efygan</th>");
            out.println("</tr>");

            while (rs.next()) {
                int sunolo = rs.getInt(1);
                int noshleutikan = rs.getInt(2);
                int efygan = rs.getInt(3);
                out.println("<tr>");
                String dateRs = "";
                if (type.contains("day")) {
                    dateRs = rs.getString(4);
                    out.println("<td>" + dateRs + "</td>");
                }
                out.println("<td>" + sunolo + "</td>");
                out.println("<td>" + noshleutikan + "</td>");
                out.println("<td>" + efygan + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");

            out.println("<h3>Astheneies poy diagnwsthkan</h3>");
            
            Statement st1 = con.createStatement();
            ResultSet rs1 = st1.executeQuery(sql1);
            
             while (rs1.next()) {
                String astheneia = rs1.getString(1);
                out.println("<h4>"+astheneia+"<h4>");
             }
            

            out.println("<h3>Eksetaseis poy diegaxthikan</h3>");

            Statement st2 = con.createStatement();
            ResultSet rs2 = st2.executeQuery(sql2);
            
             while (rs2.next()) {
                String exetaseis = rs2.getString(1);
                out.println("<h4>"+exetaseis+"<h4>");
             }
            
            out.println("<h3>Farmaka pou syntagografithikan</h3>");
            
            Statement st3 = con.createStatement();
            ResultSet rs3 = st3.executeQuery(sql3);
            
             while (rs3.next()) {
                String farmaka = rs3.getString(1);
                out.println("<h4>"+farmaka+"<h4>");
             }

            out.println("</body>");
            out.println("</html>");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(statistika_episkepswn_xron_diast.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(statistika_episkepswn_xron_diast.class.getName()).log(Level.SEVERE, null, ex);
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
