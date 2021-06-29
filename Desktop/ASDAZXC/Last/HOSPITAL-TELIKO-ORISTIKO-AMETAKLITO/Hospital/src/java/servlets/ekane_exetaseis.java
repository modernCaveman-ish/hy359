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
import java.text.ParseException;
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
@WebServlet(name = "ekane_exetaseis", urlPatterns = {"/ekane_exetaseis"})
public class ekane_exetaseis extends HttpServlet {

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
            

            String exetasi = request.getParameter("exetasi");
            String apotelesma = request.getParameter("apotelesma");
            int amka = 0;
            amka = Integer.parseInt(request.getParameter("amka"));
            String date_string = request.getParameter("hmerominia");
           
            
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/hospital";
            String username = "root";
            String password = "";
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, username, password);//DriverManager.getConnection(url + ":" + port + "/" + db + "?characterEncoding=UTF-8", username, password);

            
            
            /* 
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            
            try {
                date = new SimpleDateFormat("yyyy-MM-dd").parse(date_string);
                //Print out all the given data
                System.out.println(date_string + " " + amka + " " + exetasi + " " + apotelesma);
            } catch (ParseException e) {
                // TODO: fill the parseException gap
                System.out.println("Date failed to be parsed...\nNow exit");
                System.exit(1);
            }
            */
           
            //So far it works
            //Now do the insertion to database
            String query = "INSERT INTO `ekane_exetaseis`(`exetasi`, `imerominia`, `amka`, `apotelesma`)"
                    + " VALUES (" + "'" + exetasi + "'" + "," + date_string + "," + amka + "," + "'" + apotelesma + "'" + ")";
            //print query to the console
            System.out.println("sql:" + query);

            Statement st = con.createStatement();
            int rs = st.executeUpdate(query);

            //if rs==0 then i guess it did its job
            if (rs == 0) {
                System.out.println("Query executed...");
            

            //PrintWriter time
          //  response.setContentType("text/html");
         /*   PrintWriter writter = response.getWriter();
            writter.println("<div>" + query + "</div>");
            writter.println("<div>Egine h eisagwgh sth vash thw exetashs</div>");*/
         out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>ekane exetaseis</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("mphkan oi exetaseis sthn vash");
         
         
            out.println("</body>");
            out.println("</html>");
}
            try {
                con.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ekane_exetaseis.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ekane_exetaseis.class.getName()).log(Level.SEVERE, null, ex);
        }

        //TODO PRINT ALL THE ELEMENTS OF THE ekane_exetaseis DATABASE
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
