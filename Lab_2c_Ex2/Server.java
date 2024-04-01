package Lab_2c_Ex2;
import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        final int PORT = 8080; // Port number for the server

        try {
            // Create a server socket
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server started on port " + PORT);

            // Wait for client connection
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected: " + clientSocket.getInetAddress());

            // Create input and output streams for communication with the client
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));

            // Read client messages and send responses
            String message;
            while (true) {
                // Read message from client
                if ((message = in.readLine()) != null) {
                    System.out.println("Client: " + message);
                    if (message.equalsIgnoreCase("disconnect")) {
                        break;
                    }
                }

                // Read message from console and send to client
                String consoleMessage;
                if ((consoleMessage = consoleInput.readLine()) != null) {
                    out.println(consoleMessage);
                    if (consoleMessage.equalsIgnoreCase("disconnect")) {
                        break;
                    }
                }
            }

            // Close resources
            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();
            consoleInput.close();

        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }
}
