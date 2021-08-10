package projects.gabeross.server;

import projects.gabeross.server.Server;

public class StartServer {
    // Start the server
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Syntax: java Server <port number>");
            System.exit(0);
        }
        int portNo = Integer.parseInt(args[0]);
        Server server = new Server(portNo);
        server.start();

    }
}
