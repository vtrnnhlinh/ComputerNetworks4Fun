package Lab_2c_Ex2;
import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        final String SERVER_ADDRESS = "localhost"; // Server address
        final int PORT = 8080; // Port number for the server

        try {
            // Create a socket to connect to the server
            Socket socket = new Socket(SERVER_ADDRESS, PORT);

            // Create input and output streams for communication with the server
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));

            // Create a thread to handle incoming messages from the server
            Thread serverListener = new Thread(() -> {
                try {
                    String serverMessage;
                    while ((serverMessage = in.readLine()) != null) {
                        System.out.println("Server: " + serverMessage);
                        if (serverMessage.equalsIgnoreCase("Server: Disconnecting...")) {
                            break;
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Error reading from server: " + e.getMessage());
                }
            });
            serverListener.start();

            // Read messages from console and send to server
            String message;
            while ((message = consoleInput.readLine()) != null) {
                out.println(message);
                if (message.equalsIgnoreCase("disconnect")) {
                    break;
                }
            }

            // Close resources
            in.close();
            out.close();
            socket.close();
            consoleInput.close();

        } catch (UnknownHostException e) {
            System.out.println("Unknown host: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("I/O error: " + e.getMessage());
        }
    }
}
