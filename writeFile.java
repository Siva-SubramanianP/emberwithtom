/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;
import java.io.*;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class writeFile extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String client = request.getParameter("ip");
        PrintWriter out = response.getWriter();
        response.setContentType("text/plain");
        String path="D:\\Learning\\emberwithtom\\logfile.log";
        FileWriter file = new FileWriter(path);
        
        String logFilePath = "C:\\Users\\siva-pt7595\\Documents\\serverwrite.log";
        BufferedReader reader = new BufferedReader(new FileReader(logFilePath));
        String line;
        while ((line = reader.readLine()) != null) {
            if(line.toString().contains(client)){
                file.write(line+"\n");
            }
        }
        reader.close();
        file.close();
        
        BufferedReader in = new BufferedReader(new FileReader(path));
        if(in.readLine()!=null){
            out.println("File was Written Successfully");
        }
        else{
            out.println("No Client named "+client);
        }
   
    }
}