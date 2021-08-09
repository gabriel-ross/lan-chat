package projects.gabeross.server;

import projects.gabeross.Templates.LanThread;

import java.io.*;
import java.net.Socket;


public class UserThread extends Thread implements LanThread {
    /*
    * Class is responsible for reading in messages from one client and broadcasting
      them to all other clients.
    * It first provides the new client with a list of all other clients connected, then
      notifies all other clients that a new client has connected and updates their
      user lists.
     */

    private Socket socket;
    private Server server;
    private PrintWriter writer;

    // constructor will take 2 args: socket and server
    public UserThread(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        try {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            OutputStream output = this.socket.getOutputStream();
            writer = new PrintWriter(output, true);

            printUsers();

            String userName = reader.readLine();
            this.server.addUserName(userName);

            String serverMessage = "New user has connected to the server! Welcome, " + userName;
            this.server.broadcast(serverMessage, this);

            String clientMessage;
            do {
                clientMessage = reader.readLine();
                serverMessage = "[" + userName + "]: " + clientMessage;
                this.server.broadcast(serverMessage, this);
            } while (!clientMessage.equals("signoff"));

            // Disconnect from server
            this.server.removeUser(userName, this);
            socket.close();

            serverMessage = userName + " has disconnected.";
            this.server.broadcast(serverMessage, this);

        } catch (IOException e) {
            System.out.println("Error in UserThread: " + e);
            e.printStackTrace();
        }
    }

    void printUsers() {
        if (server.hasUsers()) {
            writer.println("Users: " + this.server.getUserNames());
        } else {
            writer.println("No other users connected at this time.");
        }
    }

    void sendMessage(String message) {
        writer.println(message);
    }
}
