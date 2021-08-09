package projects.gabeross.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Server {
    private int portNo;
    private Set<String> users = new HashSet<>();
    private Set<UserThread> userThreads = new HashSet<>();

    public Server (int portNo) {
        this.portNo = portNo;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(this.portNo)) {
            System.out.println("Starting server on port " + this.portNo);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("No client connected");

                UserThread newUser = new UserThread(socket, this);
                this.userThreads.add(newUser);
                newUser.run();

            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Broadcasts message to all users connected to server
    void broadcast(String message, UserThread excludedUser) {
        for (UserThread user : this.userThreads) {
            if (user != excludedUser) {
                user.sendMessage(message);
            }
        }
    }

    void addUserName(String userName) {
        this.users.add(userName);
    }

    void removeUser(String userName, UserThread userThread) {
        boolean removed = this.users.remove(userName);
        if (removed) {
            this.userThreads.remove(userThread);
            System.out.println("User " + userName + " has left the server.");
        }
    }

    Set<String> getUserNames() {
        return this.users;
    }

    boolean hasUsers() {
        return !this.users.isEmpty();
    }

}











