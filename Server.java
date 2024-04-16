package com;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Server extends Thread {
    private final String port;
    private final String location;
    private final FileWriter fileWriter;
    

    public Server(String port, String location) {
        this.port = port;
        this.location = location;
        try {
            this.fileWriter = new FileWriter(location,true);
        } catch (IOException e) {
            throw new RuntimeException("Error initializing FileWriter", e);
        }
    }

    @Override
    public void run() {
        startServer(port);
    }

    public void startServer(String port) {
        try {
            ServerSocket server = new ServerSocket(Integer.parseInt(port));
            System.out.println("Server Started for the port " + port);
            while (true) {
                Socket socket = server.accept();
                System.out.println("Connection accepted from " + socket.getInetAddress() + " on port " + port);
                handleClient(socket, port);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleClient(Socket client, String port) {
        try (DataInputStream in = new DataInputStream(new BufferedInputStream(client.getInputStream()))) {
            String input;
            for (int i = 0; i < 1000; i++) {
//            while(true){
                input = in.readUTF();
                String formattedInput = format(input, client, port);
                view.queue.offer(formattedInput);
                fileWriter.write(formattedInput);
                fileWriter.flush();
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String format(String msg, Socket client, String port) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMM dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String[] pri = priority(msg);
        return "<" + pri[1] + "> " + dtf.format(now) + " " + client.getInetAddress() + " " + port + " " + " " + "dameon." + pri[0] + ": " + msg + "\n";
    }

    public String[] priority(String msg) {
        String pri = "";
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
