package net;

import java.net.Socket;
import java.util.Scanner;

public class Client {
    private int port = 2500;
    private ClientConnections clientConnections;

    public Client() {
        try {
            Socket socket = new Socket("localhost", port);
            clientConnections = new ClientConnections(this, socket);
            clientConnections.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void write() {
        Thread thread = new Thread(() -> {
            String text;
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Enter something");
                while (!scanner.hasNextLine()) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                text = scanner.nextLine();
                clientConnections.send(text);
            }
        });
        thread.start();
    }

    public void writeChat(String txt) {
        Thread thread = new Thread(() -> {
            clientConnections.send(txt);
        });
        thread.start();
    }

    public void sendToServer(String txt) {
        Thread thread = new Thread(() -> {
            clientConnections.send(txt);
        });
        thread.start();
    }
}
