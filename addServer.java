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
                    response.getWriter().write("Server added successfully");
                } else {
                    response.getWriter().write("Failed to add Server");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.getWriter().write("Error: " + e.getMessage());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("Error2: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}