import java.io.*;
import java.net.*;

// Multithreaded client
public class MultiThreadedClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345);
            System.out.println("Connected to server");

            // Start a new thread for receiving messages from the server
            Thread receiveThread = new Thread(new ReceiveMessages(socket));
            receiveThread.start();

            // Handle user input in main thread
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            String input;
            while ((input = userInput.readLine()) != null) {
                writer.println(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ReceiveMessages implements Runnable {
        private final Socket socket;

        ReceiveMessages(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println("Received from server: " + line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
