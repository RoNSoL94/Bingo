package net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientConnections extends Thread {

    private BufferedReader in;
    private PrintWriter out;
    private Client client;
    private Socket socket;
    public static String[] array = new String[90];
    private int counter = 0;
    private static boolean start = false;
    boolean isRunning = true;
    public static String maxConnections;
    public static String chat;
    public static int ctr;
    public static String wins = "";

    public ClientConnections(Client client, Socket socket) {
        this.client = client;
        this.socket = socket;
    }


    public void run() {
        String data;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            while (isRunning) {
                if ((data = in.readLine()) != null) {
                    String tmp = (data.length() >= 4 ? data.substring(0, 4) : "");
                    if (tmp.equalsIgnoreCase("//m:") && !start) {
                        maxConnections = data.substring(4);
                        System.out.println("maxCOnnections client: " + maxConnections);
                        start = true;
                    }
                    if (counter < 89 && tmp.equals("")) {
                        array[counter] = data;
                        counter++;
                    }
                    if(data.length() > 2 && start && !tmp.equalsIgnoreCase("//m:") && !data.startsWith("S")) {
                        chat = data;
                        ctr = 1;
                    }

                    if(data.startsWith("S")) {
                        String control = data.substring(0, 7).trim();
                        if (control.trim().equals("System:") && start) {
                            wins = data;
                        }
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
                socket.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void send(Object data) {
        out.println(data);
    }
}



















