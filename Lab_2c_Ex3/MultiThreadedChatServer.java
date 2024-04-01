import java.io.*;
import java.net.*;
import java.util.*;

public class MultiThreadedChatServer {
    private static Set<ClientHandler> clients = new HashSet<>();

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(12345);
            System.out.println("Server started");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                ClientHandler clientHandler = new ClientHandler(socket);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
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
        }
    }

    public static void broadcastMessage(String message, ClientHandler excludeClient) {
        for (ClientHandler client : clients) {
            if (client != excludeClient) {
                client.sendMessage(message);
            }
        }
    }
}

class ClientHandler implements Runnable {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String username;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            username = in.readLine();
            System.out.println(username + " joined the chat"); // Debug statement
    
            MultiThreadedChatServer.broadcastMessage(username + " joined the chat", this);
    
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received from " + username + ": " + inputLine); // Debug statement
                MultiThreadedChatServer.broadcastMessage(username + ": " + inputLine, this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            MultiThreadedChatServer.broadcastMessage(username + " left the chat", this);
        }
    }
    
    

    public void sendMessage(String message) {
        out.println(message);
    }
}
