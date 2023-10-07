// Chuck Norris Jokes App
//
//        The Chuck Norris Jokes App is a Java application that allows users to fetch and enjoy Chuck Norris jokes, save their favorite jokes, and more. It demonstrates how to make API requests, process JSON data, and implement basic user interactions in a console-based application.
//
//         Features
//
//        - Fetch Chuck Norris jokes from various categories.
//        - Specify the number of jokes to retrieve.
//        - Add jokes to your favorites.
//        - View your favorite jokes.
//        - Simple and interactive text-based user interface.
//
//         Prerequisites
//
//        - Java Development Kit (JDK) installed on your computer.
//        - Basic knowledge of Java programming.
//
//         Getting Started
//
//        1. Clone this repository to your local machine:
//
//        ```bash
//        git clone https://github.com/your-username/chuck-norris-jokes-app.git


import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class EnhancedChuckNorrisJokesApp {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
//
//            System.out.println("Chuck Norris Jokes App");

            // Ask for the user's name
            System.out.print("Enter your name: ");
            String userName = scanner.nextLine();

            System.out.println("Hello, " + userName + "! Welcome to the Chuck Norris Jokes App");

            Map<String, String> favorites = new HashMap<>(); // Store favorites as a map (joke -> category)

            while (true) {
                System.out.println("\nMenu:");
                System.out.println("1. Get Chuck Norris Jokes");
                System.out.println("2. View Favorites");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        // Allow the user to select a joke category
                        System.out.print("Enter a joke category (e.g., animal, celebrity, dev, food, history, money, movie, music, political, science, sport): ");
                        String category = scanner.nextLine();

                        // Allow the user to specify the number of jokes
                        System.out.print("Enter the number of jokes to retrieve: ");
                        int numberOfJokes = scanner.nextInt();

                        // Fetch and display the specified number of jokes
                        for (int i = 0; i < numberOfJokes; i++) {
                            String joke = fetchChuckNorrisJoke(category);

                            // Display the joke
                            System.out.println("\nChuck Norris Joke " + (i + 1) + ":");
                            System.out.println(joke);

                            // Allow the user to add the joke to favorites
                            System.out.print("Do you want to add this joke to favorites? (yes/no): ");
                            String addToFavorites = scanner.next();
                            if (addToFavorites.equalsIgnoreCase("yes")) {
                                favorites.put(joke, category);
                                System.out.println("Joke added to favorites.");
                            }
                        }
                        break;

                    case 2:
                        // Display the user's favorite jokes
                        if (favorites.isEmpty()) {
                            System.out.println("You have no favorite jokes.");
                        } else {
                            System.out.println("Your Favorite Jokes:");
                            for (Map.Entry<String, String> entry : favorites.entrySet()) {
                                System.out.println("Category: " + entry.getValue());
                                System.out.println(entry.getKey());
                                System.out.println();
                            }
                        }
                        break;

                    case 3:
                        // Exit the program
                        System.out.println("Goodbye!");
                        scanner.close();
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String fetchChuckNorrisJoke(String category) throws IOException {
        // Construct the URL for the Chuck Norris Jokes API
        String apiUrl = "https://api.chucknorris.io/jokes/random?category=" + category;
        StringBuilder response = new StringBuilder();

        try {
            // Create a URL object
            URL url = new URL(apiUrl);

            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set the request method to GET
            connection.setRequestMethod("GET");

            // Get the response code
            int responseCode = connection.getResponseCode();

            // Check if the response code is 200 (OK)
            if (responseCode == 200) {
                // Read the response from the API
                InputStream responseStream = connection.getInputStream();
                Scanner apiScanner = new Scanner(responseStream);
                while (apiScanner.hasNextLine()) {
                    response.append(apiScanner.nextLine());
                }
                apiScanner.close();

                // Parse the JSON response using org.json library
                JSONObject json = new JSONObject(response.toString());

                // Extract and return the Chuck Norris joke
                return json.getString("value");
            } else {
                System.out.println("Failed to retrieve Chuck Norris joke. Response code: " + responseCode);
            }

            // Close the connection
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Failed to retrieve Chuck Norris joke.";
    }
}
