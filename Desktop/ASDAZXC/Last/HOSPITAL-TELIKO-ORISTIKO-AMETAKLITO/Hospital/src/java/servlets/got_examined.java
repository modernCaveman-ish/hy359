/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Stelios Zampoulakis
 */
@WebServlet(name = "got_examined", urlPatterns = {"/got_examined"})
public class got_examined extends HttpServlet {

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
            throws ServletException, IOException, SQLException, ParseException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */

            System.out.println("FINALY INTO THE SERVLET GAMW TH MANA SOY");
            System.out.println("KSEKINA TWRA GAMW TO SOI SOY");
            response.setContentType("text/html;charset=UTF-8");

            //ANTE NA TELEIWNOYME
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                System.out.println("mySQL driver was not found, now exits...");
                System.exit(1); // EXIT_FAILURE
            }
            System.out.println("CONNECTED TO THE FKIN DATABASE....");
            System.out.println("EPITELOYS GAMW TH MANA SOY EPITELOYS!!!!");

            String URL = "jdbc:mysql://localhost:3306/hospital";
            String username = "root";
            String pass = "";

            Connection con = null;
            try {
                con = DriverManager.getConnection(URL, username, pass);
            } catch (Exception e) {
                System.out.println("Error with DriverManager.getConnection, url maybe null or just not right...");
                System.exit(1);
            }
            con = DriverManager.getConnection(URL, username, pass);
            
            int amka = Integer.parseInt(request.getParameter("amka"));

            System.out.println(amka);

            //NOW SEARCH FOR PATIENT WITH THIS AMKA GAMW
            String query = "SELECT * FROM `got_examined` WHERE amka = '" + amka + "'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            if (rs.next()) {
                System.out.println("PATIENT WITH AMKA: " + rs.getInt("amka") + " WAS FOUND GAMW TO XRISTO SOY EPITELOYS");
                /* TODO output your page here. You may use following sample code. */
                /* TODO output your page here. You may use following sample code. */
                //Printing stuff
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet got_examined</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Servlet got_examined at " + request.getContextPath() + "</h1>");
                out.println("<h2>Oi astheneis sto got_examined afti th stigmi</h2>");
                String search_url = "SELECT * FROM `got_examined`";
                ResultSet rs_2 = st.executeQuery(search_url);
                out.println("<h4>Name&emsp;Illness</h4>");
                out.println("<h4>The import you are trying to do already exists....\n Now printing the whole list...</h4>");

                while (rs_2.next()) {
                    out.println("<h5>" + rs_2.getInt("amka") + "&emsp;" + rs_2.getDate("date") + "&emsp;" + rs_2.getString("exetasi") + "&emsp;" + rs_2.getString("result") + "</h5>");
                }
                System.out.println("edw pera");
                out.println("<div>" + "sql:" + query + "</div>");
                out.println("</body>");
                out.println("</html>");

                System.exit(0);
            } 
            else if (!rs.next()) {
                //Enter the new entry and then print all the data
                //Den yparxei ara bale ton sth lista pls
                System.out.println("O asthenis den brethike sth lista being_treated ara tha ginei eisagwgh");
                //Data to be inserted
                amka = Integer.parseInt(request.getParameter("amka"));
                String exetasi = request.getParameter("exetasi");
                String result = request.getParameter("result");

                String dateIn = request.getParameter("date_selector");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                Date date = new Date();
                System.out.println("edw?");

                date = sdf.parse(dateIn);
                System.out.println("edw oxi?");
                //TODO PREPEI NA FTIAXTEI H MLKIA EDW, THA KANW AYRIO COPY PASTE KWDIKA POY NA DOYLEUEI NA TELEIWNEI H ISTORIA
                query = "INSERT INTO `got_examined`(`amka`, `date` ,`exetasi`, `result`) VALUES (" + amka + ",'" + dateIn + "', '" + exetasi + "' , '" + result + "' )";
                
                Statement stmt = con.createStatement();
                int rs_3 = stmt.executeUpdate(query);
                System.out.println("sql:" + query);
                System.out.println("O asthenhs me amka " + amka + " kataxwrithike sth lista");
                
                
            }

            // Printing stuff
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet got_examined</title>");
            out.println("</head>");
            out.println("<body>");
            // out.println("<h1>Servlet got_examined at " + request.getContextPath() +
            // "</h1>");
            out.println("<h2>Oi astheneis sto got_examined afti th stigmi</h2>");
            String url_search = "SELECT * FROM `got_examined`";
            Statement st_2 = con.createStatement();
            ResultSet rs_4 = st_2.executeQuery(url_search);
            out.println("<h4>Name&emsp;Illness</h4>");
            out.println("<h4>Now printing the whole list...</h4>");

            while (rs_4.next()) {
                System.out.println(rs_4.getInt("amka"));
                out.println("<h5>" + rs_4.getInt("amka") + "&emsp;" + rs_4.getDate("date") + "&emsp;"
                        + rs_4.getString("exetasi") + "&emsp;" + rs_4.getString("result") + "</h5>");
            }
            System.out.println("edw pera");
            out.println("<div>" + "sql:" + query + "</div>");
            out.println("</body>");
            out.println("</html>");

            //Ok brethike
            System.out.println("Ok brethike");
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
            throws IOException, ServletException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(got_examined.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(got_examined.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            throws IOException {
        try {
            processRequest(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(got_examined.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(got_examined.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(got_examined.class.getName()).log(Level.SEVERE, null, ex);
        }
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
