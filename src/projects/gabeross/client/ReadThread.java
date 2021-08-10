package projects.gabeross.client;

import projects.gabeross.Templates.LanThread;
import projects.gabeross.server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

// Reads input from the server and writes to client's console
public class ReadThread extends Thread implements LanThread {

    private BufferedReader reader;
    private Socket socket;
    private Client client;

    public ReadThread(Socket socket, Client client) {
        this.socket = socket;
        this.client = client;

        try {
            InputStream input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));
        } catch (IOException e) {
            System.out.println("IO Exception while creating read thread: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            try {
                String response = reader.readLine();
                System.out.println("\n" + response);

                if (client.getUserName() != null) {
                    System.out.println("[" + client.getUserName() + "]");
                }
            } catch (IOException e) {
                System.out.println("IO Exception while reading stream: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
