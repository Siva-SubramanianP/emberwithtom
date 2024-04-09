/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        String port = request.getParameter("port");
        try (FileWriter file = new FileWriter("D:\\Learning\\emberwithtom\\logfile.log")) {
            
            
            String[] inputs = {"error occur while running","warning process still in buildup","urgent call the clients","immediate action need to be taken","error occur while handling script","warning file is not opened","immediate switch the network as public","Hi normal message","urgent help needed from the team","urgent help needed to the file server","warning the file is opened","error handle the exception","error process the thread sequentially","warning this takes too much time","urgent modify server to function efficiently","immediate alert the process thread","error occur in the client","immediate alert the file","error handle the thread correctly","immediate solve the problem","urgent call the clients","over"};  
            int index = 0;
            
            for(int i=0;i<1000;i++){
                if(index > 20){
                    index =0;
                }
                if(inputs[index].equals("over")){
                    break;
                }
                else{
                    try {
                       String addFile = inputs[index];
                       String formattedInput = format(addFile, client, port);
                        file.write(formattedInput);
                    } catch (IOException e) {    
                        e.printStackTrace();
                    }
                }
                index++;
            }         
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter out = response.getWriter();
        response.setContentType("text/plain");
        out.write("File was successfully written");
        
    }
    public static String format(String msg, String client, String port) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMM dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String[] pri = priority(msg);
        // return "<" + pri[1] + ">" + dtf.format(now) + " " + client.getInetAddress() + " " + port + " " +"Received from "+name+" Writing "+writeTofile.name + " " + "dameon." + pri[0] + ": " + msg + "\n";
        return "<" + pri[1] + "> " + dtf.format(now) + " " + client+ " " + port + " "+ "dameon." + pri[0] + ": " + msg + "\n";
    }

    public static String[] priority(String msg){
        String pri;
        int pv;
        String[] ans = new String[2];
        if (msg.toLowerCase().contains("error")) {
            pri = "ERROR";
            pv = 3;
        } else if (msg.toLowerCase().contains("warning")) {
            pri = "WARNING";
            pv = 4;
        } else if (msg.toLowerCase().contains("immediate")) {
            pri = "ALERTS";
            pv = 1;
        } else if (msg.toLowerCase().contains("urgent")) {
            pri = "EMERGENCY";
            pv = 0;
        } else {
            pri = "INFO";
            pv = 6;
        }
        ans[0] = pri;
        ans[1] = String.valueOf(pv);
        return ans;
    }
}