///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com;
//
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//public class addServer extends HttpServlet {
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        // Parse request parameters
//        String ipadd = request.getParameter("ip");
////        String portno = request.getParameter("port");
////        String status = "active";
//
//        Connection conn = null;
//        Statement st = null;
//        ResultSet rs = null;
//        
//        response.setContentType("text/plain");
//        response.setCharacterEncoding("UTF-8");
//
//        try {
//            Class.forName("org.apache.derby.jdbc.ClientDriver");
//            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/sample", "siva", "goku");
//        
//            if (conn != null) {
//                
//                PreparedStatement ps = conn.prepareStatement("insert into logs values (?)");
////                String query = "insert into logs values ('127.0.0.1','5000','active')";
////                String query = "insert into logs values ( '"+ipadd+"')";
//                ps.setString(1, ipadd);
////                st.setString(2, portno);
////                st.setString(3, status);
//                int result = ps.executeUpdate();
//                if(result > 0){
//                    response.getWriter().write("values added");
//                }
//            } else {
//                throw new SQLException("Connection is null");
//            }
//        } catch (SQLException e) {
//            System.out.println("Error");
//            e.printStackTrace();
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(addServer.class.getName()).log(Level.SEVERE, null, ex);
//            System.out.println("Errorclass not loaded");
//        }
//        
//        try {
//            if (conn != null) {
//                
//                st = conn.createStatement();
//                String query = "select * from logs";
//                rs = st.executeQuery(query);
//                System.out.println("Values Added in the Database");
//                while (rs.next()) {
//                    String ipFromDB = rs.getString(1);
//                    if (ipFromDB != null && ipFromDB.equals("127.0.0.1")) { 
//                        
//                    }
//                }
//            } else {
//                throw new SQLException("Connection is null");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } 
//        finally {
//            try {
//                if (rs != null) rs.close();
//                if (st != null) st.close(); 
//                if (conn != null) conn.close(); 
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//        }
//    }
//    @Override
//    public void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        doPost(request, response);
//    }
//}
package com;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class addServer extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String ipadd = request.getParameter("ip");
        String portno = request.getParameter("port");
        String status = "active";
        
        String username = "siva";
        String password = "goku";
        String url = "jdbc:derby://localhost:1527/logii";
        
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            response.getWriter().write("Error:3 " + ex.getMessage());
        }
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String query = "INSERT INTO logs (ipaddress,port,state) VALUES (?,?,?)";
            try (PreparedStatement pst = conn.prepareStatement(query)) {
                pst.setString(1, ipadd);
                pst.setString(2, portno);
                pst.setString(3, status);
                int rowsAffected = pst.executeUpdate();
                if (rowsAffected > 0) {
                    response.getWriter().write("Values added successfully");
                } else {
                    response.getWriter().write("Failed to add values");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.getWriter().write("Error: " + e.getMessage());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("Error2: " + e.getMessage());
        }
//        response.getWriter().write(ipadd+" "+portno);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Allow GET requests to do the same as POST (if that's desired behavior)
        doPost(request, response);
    }
}