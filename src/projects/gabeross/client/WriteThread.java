package projects.gabeross.client;

import projects.gabeross.Templates.LanThread;

import java.net.Socket;

// Reads user input and writes input to server
public class WriteThread extends Thread implements LanThread {
    public WriteThread(Socket socket, Client client) {
    }
}
