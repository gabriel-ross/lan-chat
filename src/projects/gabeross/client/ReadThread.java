package projects.gabeross.client;

import projects.gabeross.Templates.LanThread;
import projects.gabeross.server.Server;

import java.io.BufferedReader;
import java.net.Socket;

// Reads input from the server and writes to client's console
public class ReadThread extends Thread implements LanThread {

    private BufferedReader reader;
    private Socket socket;
    private Client client;

    public ReadThread(Socket socket, Client client) {
    }
}
