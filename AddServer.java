package com.servlet;

import com.Client;
import com.Server;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddServer extends HttpServlet {
    private static final String INSERT_QUERY = "INSERT INTO AddLogs (port) VALUES (?)";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String portno = request.getParameter("port");
        String message;

        try (Connection conn = getConnection();
             PreparedStatement pst = conn.prepareStatement(INSERT_QUERY)) {
            pst.setString(1, portno);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                Thread serverThread = new Thread(() -> startServer(portno));
                Thread clientThread = new Thread(() -> startClient(portno));
                serverThread.start();
                clientThread.start();
                message = "Server added successfully";
            } else {
                message = "Failed to add Server";
            }
        } catch (SQLException e) {
            message = "Error: " + e.getMessage();
            log("SQLException occurred", e);
        }

        response.getWriter().write(message);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    private void startServer(String portno) {
        Server server = new Server(portno, "C:\\Users\\siva-pt7595\\Desktop\\test.log");
        server.start();
    }

    private void startClient(String portno) {
        Client client = new Client(Integer.parseInt(portno));
        client.start();
    }

    private Connection getConnection() throws SQLException {
        String username = "siva";
        String password = "goku";
        String url = "jdbc:derby://localhost:1527/logii";
        return DriverManager.getConnection(url, username, password);
    }
}
