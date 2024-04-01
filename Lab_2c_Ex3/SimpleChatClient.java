import java.io.*;
import java.net.*;

public class SimpleChatClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter your username: ");
            String username = consoleInput.readLine();
            out.println(username); // Send username to the server

            System.out.println("Connected to server. Start chatting (type 'exit' to quit)");

            String message;
            while (true) {
                message = consoleInput.readLine();
                out.println(message);
                if (message.equalsIgnoreCase("exit")) {
                    break;
                }
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
