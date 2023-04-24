package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.*;

public class MyServer {
    private static final Logger LOGGER = Logger.getLogger(MyServer.class.getName());
    private ServerSocket serverSocket;

    public static void main(String[] args) {
        MyServer server = new MyServer();
        server.start(5555);
    }

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            LOGGER.log(Level.INFO, "Server started on port {0}", port);
            while (true)
                new EchoClientHandler(serverSocket.accept()).start();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error when starting Server", e);
            e.printStackTrace();
        } finally {
            stop();
        }
    }

    public void stop() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error when stoping server", e);
            e.printStackTrace();
        }
    }

    private static class EchoClientHandler extends Thread {
        private final Socket clientSocket;
        private ObjectOutputStream out;
        private ObjectInputStream in;

        public EchoClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try {
                out = new ObjectOutputStream(clientSocket.getOutputStream());
                in = new ObjectInputStream(clientSocket.getInputStream());
                out.writeObject("Server: ready");
                out.flush();
                Object inMessage;
                Integer n = 0;
                boolean escapeFlag = false;
                while ((inMessage = in.readObject()) != null && !escapeFlag) {
                    try {
                        if (inMessage instanceof  Integer){
                            out.writeObject("Server: ready for messages");
                            n = (Integer) inMessage;
                            out.flush();
                        }
                        else if (inMessage instanceof Message mess){
                            LOGGER.log(Level.INFO, "Serever:  {0}", mess.toString());
                            n--;
                            if (n == 0){
                                escapeFlag = true;
                                break;
                            }
                        }
                    } catch (EOFException e) {
                        LOGGER.log(Level.SEVERE, "End of input stream reached", e);
                        break;
                    } catch (Exception e){
                        LOGGER.log(Level.SEVERE, "blad w petli run!"+ e, e);
                        break;
                    }
                }
                out.writeObject("finished");
                in.close();
                out.close();
                clientSocket.close();

            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Error when serving client", e);
            } catch (Exception e){
                LOGGER.log(Level.SEVERE, "Zamykam polaczenie", e);
            }
        }
    }

}