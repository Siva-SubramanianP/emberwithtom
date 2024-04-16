package com;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client extends Thread {
    private final int port;

    public Client(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try (Socket socket = new Socket("127.0.0.1", port);
             DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {
            System.out.println("Socket Connected for port " + port);

            String[] inputs = {"error occur while running", "warning process still in buildup", "urgent call the clients", "immediate action need to be taken", "error occur while handling script", "warning file is not opened", "immediate switch the network as public", "Hi normal message", "urgent help needed from the team", "urgent help needed to the file server", "warning the file is opened", "error handle the exception", "error process the thread sequentially", "warning this takes too much time", "urgent modify server to function efficiently", "immediate alert the process thread", "error occur in the client", "immediate alert the file", "error handle the thread correctly", "immediate solve the problem", "urgent call the clients", "over"};
            int index = 0;
            while(true){
                if (index > 20) {
                    index = 0;
                }
                if (inputs[index].equals("over")) {
                    break;
                } else {
                    out.writeUTF(inputs[index]);
                    Thread.sleep(500);
                }
                index++;
            }
            System.out.println("Client " + port + " Finished sending message");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
