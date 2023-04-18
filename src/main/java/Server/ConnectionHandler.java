package Server;

import Common.Message.Message;
import Common.OrderBook;
import Server.Command.Command;
import Server.Command.NotARequestException;
import Server.Command.UserCommand;
import Server.Command.WrongParamsException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectionHandler implements Runnable {
    private final Socket clientSocket;
    private String username;

    public ConnectionHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isConnected() {
        return username != null;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            out.println("Welcome to SecondLife!");
            String inputLine;
            do {
                inputLine = in.readLine();

                System.out.println("Received: " + inputLine);

                Message msg;
                try {
                    System.out.println("Deserializing message: " + inputLine.trim());
                    msg = Message.fromSerialized(inputLine.trim());
                    System.out.println("done");
                } catch (WrongParamsException e) {
                    System.err.println("Wrong parameters: " + e.getMessage());
                    out.println(e.getMessage());
                    continue;
                } catch (IllegalArgumentException e) {
                    System.err.println("Invalid message: " + e.getMessage());
                    out.println("Invalid or unsupported message");
                    continue;
                }
                System.out.println("Message type: " + msg.getClass().getSimpleName());
                Command cmd;
                try {
                    cmd = CommandFactory.fromMessage(msg, OrderBook.getInstance(), this);
                } catch (NotARequestException e) {
                    System.err.println("Invalid request: " + e.getMessage());
                    out.println("Invalid or unsupported command");
                    continue;
                } catch (IllegalArgumentException e) {
                    System.err.println("Invalid command: " + e.getMessage());
                    out.println("Invalid or unsupported command");
                    continue;
                }
                System.out.println("Executing command: " + cmd.getClass().getSimpleName());
                System.out.println("Is connected: " + isConnected());
                if (!isConnected() && !(cmd instanceof UserCommand)) {
                    System.err.println("User must be connected to issue commands");
                    out.println("You must connect before issuing commands.");
                    continue;
                }

                out.println(cmd.execute().toString());

            } while (inputLine != null);
        } catch (IOException e) {
            System.err.println("Error handling client connection: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing client socket: " + e.getMessage());
            }
        }
    }
}
