package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private static final String PROMPT = ">>> ";
    private final BufferedReader in;
    private final PrintWriter out;

    public Client(String host, int port) throws IOException {
        Socket socket = new Socket(host, port);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
    }

    public void start() throws IOException {
        // Start a listener thread to handle incoming messages from the server
        new Thread(this::listenForServerMessages).start();

        // Read user input and send commands to the server
        try (BufferedReader userInputReader = new BufferedReader(new InputStreamReader(System.in))) {
            String userInput;
            while ((userInput = userInputReader.readLine()) != null) {
                out.println(userInput);
            }
        }
    }

    private void listenForServerMessages() {
        String serverMessage;
        try {
            while ((serverMessage = in.readLine()) != null) {
                System.out.println(serverMessage);
                System.out.print(PROMPT);
            }
        } catch (IOException e) {
            System.err.println("Error reading server message: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        String host = "localhost";
        int port = 12345;

        try {
            Client client = new Client(host, port);
            client.start();
        } catch (IOException e) {
            System.err.println("Error connecting to the server: " + e.getMessage());
        }
    }
}