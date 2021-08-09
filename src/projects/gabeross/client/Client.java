package projects.gabeross.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    private String host;
    private int port;
    private String userName;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() {
        try {
            Socket socket = new Socket(host, port);

            System.out.println("Connected to server: " + host);

            new ReadThread(socket, this).run();
            new WriteThread(socket, this).run();

        } catch (UnknownHostException e) {
            System.out.println("Host not found: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("I/O exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public static void main(String[] args) {
        if (args.length < 2) return;

        String host = args[0];
        int port = Integer.parseInt(args[1]);

        Client client = new Client(host, port);
        client.start();
    }
}
