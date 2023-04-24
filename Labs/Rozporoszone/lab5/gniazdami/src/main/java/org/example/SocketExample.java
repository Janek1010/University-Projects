package org.example;

import java.io.*;
import java.net.*;
import java.util.LinkedList;
import java.util.List;

public class SocketExample {
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(5000);

        Socket clientSocket = serverSocket.accept();

        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        List<Integer> checked =  new LinkedList<>();

        Thread checking = new Thread(() -> {
            while (true) {
                try {
                    if (in.ready()) {
                        int check = Integer.parseInt(in.readLine());
                        if (checked.contains(check)){
                            out.println(-1);
                        }else {
                            out.println(-2);
                            checked.add(check);
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        checking.start();
        for (int i = 0; i < 100000; i++) {
            Thread.sleep(100);
            int number = (int) (Math.random() * 301);

            if ((number % 2 ==0)){
                if ((number% 3) ==  0){
                    if (!checked.contains(number)){
                        System.out.println("Pierwszy raz wyswietlam "+number);
                        checked.add(number);
                        continue;
                    }
                }
            }
            System.out.println(number);
            checked.add(number);
        }
        Thread.sleep(100000000);
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }
}
