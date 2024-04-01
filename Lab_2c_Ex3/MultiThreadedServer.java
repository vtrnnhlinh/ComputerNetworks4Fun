import java.io.*;
import java.net.*;
import java.util.concurrent.*;

// Multithreaded server
public class MultiThreadedServer {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        ExecutorService executor = Executors.newFixedThreadPool(10); // Pool of threads for handling client connections

        try {
            serverSocket = new ServerSocket(12345);
            System.out.println("Server started");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket);

                // Create a new thread to handle the client
                executor.execute(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            executor.shutdown();
        }
    }

    static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)
            ) {
                // Handle client communication here
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println("Received from client: " + line);
                    writer.println("Server received: " + line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
