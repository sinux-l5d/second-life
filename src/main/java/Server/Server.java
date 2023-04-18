package Server;

import Common.Message.Message;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Server {
    private final int port;
    private final ThreadPoolManager threadPoolManager;
    private final Set<ConnectionHandler> clients = ConcurrentHashMap.newKeySet();

    public Server(int port, int maxThreads) {
        this.port = port;
        this.threadPoolManager = new ThreadPoolManager(maxThreads);
    }

    // Private so only main can call it
    private void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected from " + clientSocket.getInetAddress());

                ConnectionHandler connectionHandler = new ConnectionHandler(clientSocket, this);
                clients.add(connectionHandler);
                threadPoolManager.submitTask(connectionHandler);
            }
        } catch (IOException e) {
            System.err.println("Error starting the server: " + e.getMessage());
        }
    }

    public void removeClient(ConnectionHandler client) {
        clients.remove(client);
    }

    public void broadcast(Message message) {
        clients.forEach(client -> {
            if (client.isConnected()) client.send(message);
        });
    }

    private void monitor() {
        while (true) {
            var connected = clients.stream()
                    .filter(ConnectionHandler::isConnected)
                    .count();

            var usernames = clients.stream()
                    .filter(ConnectionHandler::isConnected)
                    .map(ConnectionHandler::getUsername)
                    .collect(Collectors.joining(", "));

            System.out.println("Connected users (" + connected + "/" + clients.size() + "): " + usernames);

            try {
                Thread.sleep(5000);
            } catch (InterruptedException ignored) {}
        }
    }

    private void shutdown() {
        clients.forEach(ConnectionHandler::close);
        threadPoolManager.shutdown();
    }

    public static void main(String[] args) {
        int port = 12345;
        Server server = new Server(port, 5);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down server...");
            server.shutdown();
        }));

        new Thread(server::monitor).start();
        server.start();
    }
}
