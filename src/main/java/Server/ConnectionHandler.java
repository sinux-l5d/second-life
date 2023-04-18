package Server;

import Common.Message.Message;
import Common.OrderBook;
import Server.Command.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectionHandler implements Runnable {
    public static final String END_OF_RESPONSE_MARKER = "<EOR>";

    private final Socket clientSocket;
    private final Server server;
    private PrintWriter out;
    private String username;

    public ConnectionHandler(Socket clientSocket, Server server) {
        this.clientSocket = clientSocket;
        this.server = server;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }

    public boolean isConnected() {
        return username != null;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
            this.out = out;

            out.println("Welcome to SecondLife!");
            String inputLine;
            do {
                out.println(END_OF_RESPONSE_MARKER);
                inputLine = in.readLine();

                System.out.println("Received: " + inputLine);

                Message msg;
                try {
                    msg = Message.fromSerialized(inputLine.trim());
                } catch (WrongParamsException e) {
                    System.err.println("Wrong parameters: " + e.getMessage());
                    out.println(e.getMessage());
                    continue;
                } catch (IllegalArgumentException e) {
                    System.err.println("Invalid message: " + e.getMessage());
                    out.println("Invalid or unsupported message");
                    continue;
                }

                Command cmd;
                try {
                    cmd = CommandFactory.fromMessage(msg, OrderBook.getInstance(), this, server);
                } catch (NotARequestException e) {
                    System.err.println("Invalid request: " + e.getMessage());
                    out.println("Invalid or unsupported command");
                    continue;
                } catch (IllegalArgumentException e) {
                    System.err.println("Invalid command: " + e.getMessage());
                    out.println("Invalid or unsupported command");
                    continue;
                }

                if (!isConnected() && !(cmd instanceof UserCommand || cmd instanceof EndCommand)) {
                    System.err.println("User must be connected to issue commands");
                    out.println("You must connect before issuing commands.");
                    continue;
                }

                out.println(cmd.execute().toString());
            } while (inputLine != null);
        } catch (IOException e) {
            System.err.println("Error handling client connection: " + e.getMessage());
        } finally {
            close();
        }
    }

    public void close() {
        try {
            clientSocket.close();
            server.removeClient(this);
        } catch (IOException e) {
            System.err.println("Error closing client socket: " + e.getMessage());
        }
    }

    public void send(Message msg) {
        if (out != null) out.println(msg.toString());
    }
}
