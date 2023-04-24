package org.example;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyClient {
    private static final Logger LOGGER = Logger.getLogger(MyClient.class.getName());


    private Socket clientSocket;
    private static ObjectOutputStream out;
    private static ObjectInputStream in;

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        MyClient client1 = new MyClient();
        client1.startConnection("127.0.0.1", 5555);
        Thread.sleep(2000);
        var response1 = client1.sendInteger(3);
        LOGGER.log(Level.INFO, "Response:  {0}", response1);
        Thread.sleep(2000);

        client1.sendMessage(new Message(1, "siema"));
        Thread.sleep(2000);
        client1.sendMessage(new Message(2, "aaaaaaa"));
        Thread.sleep(2000);
        client1.sendMessage(new Message(3, "bbbbbb"));
        Thread.sleep(2000);


        LOGGER.log(Level.INFO, "Response:  {0}", in.readObject());
        while(true){
            Thread.sleep(1000);
        }
    }

    public void startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
            var resp = (String) (in.readObject());
            LOGGER.log(Level.INFO, "Response:  {0}", resp);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error when starting connection", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    public String sendInteger(Integer integer) {
        try {
            out.writeObject(integer);
            out.flush();
            return (String) in.readObject();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error when sending connection", e);
            return null;
        }
    }
    public void sendMessage(Message msg) {
        try {
            out.writeObject(msg);
            out.flush();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error when sending connection", e);
        }
    }

    public void stopConnection() {
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error when closing connection", e);
        }

    }
}