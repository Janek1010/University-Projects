package org.example;

import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws IOException, InterruptedException {
        Socket serverSocket = new Socket("localhost", 5000);
        System.out.println("Connected to server.");

        BufferedReader in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
        PrintWriter out = new PrintWriter(serverSocket.getOutputStream(), true);

        for (int i = 0; i < 100000; i++) {
            Thread.sleep(100);
            int number = (int) (Math.random() * 301);

            if ((number % 3) == 0){
                if ((number % 2)==0){
                    out.println(number);
                    String inn = in.readLine();
                    int response = Integer.parseInt(inn);
                    if (response == -2){
                        System.out.println("pierwszy raz wyswietlam "+number);
                        continue;
                    }
                }
            }
            System.out.println(number);
        }
        Thread.sleep(100000000);
        in.close();
        out.close();
        serverSocket.close();
    }
}

