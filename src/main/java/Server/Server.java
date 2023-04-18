package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final int port;
    private final ThreadPoolManager threadPoolManager;

    public Server(int port, int maxThreads) {
        this.port = port;
        this.threadPoolManager = new ThreadPoolManager(maxThreads);
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected from " + clientSocket.getInetAddress());
                ConnectionHandler connectionHandler = new ConnectionHandler(clientSocket);
                threadPoolManager.submitTask(connectionHandler);
            }
        } catch (IOException e) {
            System.err.println("Error starting the server: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        int port = 12345;
        Server server = new Server(port, 5);
        server.start();
    }
}
