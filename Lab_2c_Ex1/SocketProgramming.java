package Lab_2c_Ex1;
import java.io.*;
import java.net.*;

public class SocketProgramming {
    public static void main(String[] args) {
        String url = "https://www.google.com/"; // Change this URL to the website you want to download

        try {
            // Create a URL object
            URL websiteUrl = new URL(url);

            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) websiteUrl.openConnection();

            // Set request method
            connection.setRequestMethod("GET");

            // Get input stream from the connection
            InputStream inputStream = connection.getInputStream();

            // Create a BufferedReader to read the input stream
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            // Create a FileWriter to write the webpage content to a file
            FileWriter fileWriter = new FileWriter("website.html"); // You can change the filename

            // Read lines from the BufferedReader and write them to the file
            String line;
            while ((line = reader.readLine()) != null) {
                fileWriter.write(line + "\n");
            }

            // Close resources
            reader.close();
            fileWriter.close();

            // Output success message
            System.out.println("Homepage downloaded successfully.");

        } catch (MalformedURLException e) {
            System.out.println("Invalid URL: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error downloading homepage: " + e.getMessage());
        }
    }
}
