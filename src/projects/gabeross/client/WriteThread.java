package projects.gabeross.client;

import projects.gabeross.Templates.LanThread;

import java.io.Console;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

// Reads user input and writes input to server
public class WriteThread extends Thread implements LanThread {

    private PrintWriter writer;
    private Socket socket;
    private Client client;

    public WriteThread(Socket socket, Client client) {
        this.socket = socket;
        this.client = client;

        try {
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
        } catch (IOException e) {
            System.out.println("IO Exception while creating write thread: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void run() {

        Console console = System.console();

        String userName = console.readLine("\nEnter a username:");
        client.setUserName(userName);
        writer.println(userName);

        String text;

        do {
            text = console.readLine("[" + userName + "]: ");
            writer.println(text);
        } while (!text.equals("signoff"));

        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("IO Exception while writing to server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
