package com;

import java.io.IOException;
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author siva-pt7595
 */
public class viewlog extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
        doPost(req,res);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setIntHeader("Refresh", 5); 
        PrintWriter out = response.getWriter();

        String logFilePath = "C:\\Users\\siva-pt7595\\Documents\\emulatorgenerated.log";

        BufferedReader reader = new BufferedReader(new FileReader(logFilePath));
        StringBuilder logContent = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            logContent.append(line);
            logContent.append("<br>");
        }
        reader.close();
        out.println("<center><h2>Logs</h2></center>");
        
        out.println("<p style='font-size:120%;margin-left:10px;'>"+logContent.toString()+"</p>");
        out.println("<center><button style='padding: 10px 20px; background-color: #4CAF50; color: white; border: none; border-radius: 5px; text-align: center; text-decoration: none; display: inline-block; font-size: 16px; margin-top: 20px;'><a href='http://localhost:8080/sample/' style='color: white; text-decoration: none;'>HOME</a></button></center>");
        out.close();
    }
}
