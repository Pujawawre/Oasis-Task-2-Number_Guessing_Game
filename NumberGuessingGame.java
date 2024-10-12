import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {

    // An ArrayList to store the number of attempts for each game
    static ArrayList<Integer> scoreBoard = new ArrayList<>(); 

    // The main method starts the game by calling the menu method
    public static void main(String[] args) {
        NumberGuessingGame game = new NumberGuessingGame();
        game.menu();
    }

    // Displays the main menu and processes user input for menu options
    public void menu() {
        Scanner input = new Scanner(System.in);  // Create a Scanner object to read user input
        while (true) {  // Infinite loop to continuously display the menu until the user chooses to exit
            System.out.println("\n====================");
            System.out.println("<<<<<<<<<<<<<< Welcome to the Number Guessing Game! >>>>>>>>>>>>>>");
            System.out.println("1) Play the Game");
            System.out.println("2) Score Board");
            System.out.println("3) Clear Score Board");
            System.out.println("4) Exit the Game");
            System.out.println("====================");
            try {
                System.out.print("Please select an option: ");
                int menuOption = input.nextInt();  // Read the user's choice
                switch (menuOption) {  // Handle different menu options
                    case 1:
                        chooseDifficulty(input);  // Play the game with difficulty selection
                        break;
                    case 2:
                        displayScoreBoard();  // Display the scoreboard
                        break;
                    case 3:
                        clearScoreBoard();  // Clear the scoreboard
                        break;
                    case 4:
                        if (confirmExit(input)) {  // Confirm before exiting
                            System.out.println("\nThanks for playing! Goodbye!");
                            System.exit(0);  // Exit the game
                        }
                        break;
                    default:
                        System.err.println("Invalid option. Please try again.");  // Handle invalid input
                }
            } catch (InputMismatchException e) {  // Handle invalid input types (e.g., non-numeric input)
                System.err.println("Invalid input. Please enter a number.");
                input.next();  // Clear invalid input to avoid infinite loop
            }
        }
    }

    // Allows the user to choose the difficulty level, which determines the number range for guessing
    public void chooseDifficulty(Scanner input) {
        System.out.println("\nChoose difficulty level:");
        System.out.println("1) Easy (1-10)");
        System.out.println("2) Medium (1-50)");
        System.out.println("3) Hard (1-100)");
        int difficulty = input.nextInt();  // Read the user's choice for difficulty
        int numberRange;
        switch (difficulty) {
            case 1:
                numberRange = 10;  // Easy difficulty with number range 1-10
                break;
            case 2:
                numberRange = 50;  // Medium difficulty with number range 1-50
                break;
            case 3:
                numberRange = 100;  // Hard difficulty with number range 1-100
                break;
            default:
                System.err.println("Invalid difficulty. Defaulting to Medium.");
                numberRange = 50;  // Default to Medium if input is invalid
        }
        playGame(input, numberRange);  // Start the game with the selected difficulty
    }

    // Starts the guessing game with the given number range
    public void playGame(Scanner input, int numberRange) {
        int randomNumber = randomNumber(numberRange);  // Generate a random number within the range
        int guessCount = guessNumber(randomNumber, input);  // Count the number of guesses it takes to get the right number
        scoreBoard.add(guessCount);  // Add the result to the scoreboard
        retryOption(input, numberRange);  // Offer the player the option to play again
    }

    // Generates a random number between 1 and the given range
    public int randomNumber(int numberRange) {
        Random random = new Random();
        return random.nextInt(numberRange) + 1;  // Random number between 1 and the range (inclusive)
    }

    // Handles the guessing process, keeps track of attempts, and provides hints
    public int guessNumber(int randomNumber, Scanner input) {
        int userGuess;
        int guessCount = 0;  // Track the number of attempts
        
        System.out.println("\nA random number has been selected. Try to guess it!");
        
        // Loop until the user guesses the correct number
        do {
            System.out.print("Enter your guess: ");
            userGuess = input.nextInt();  // Read the user's guess
            guessCount++;  // Increment the attempt counter
            if (userGuess > randomNumber) {
                System.out.println("Lower! Try again.");  // Provide a hint if the guess is too high
            } else if (userGuess < randomNumber) {
                System.out.println("Higher! Try again.");  // Provide a hint if the guess is too low
            }
        } while (userGuess != randomNumber);  // Continue until the correct number is guessed
        
        System.out.println("🎉 Correct! You guessed the number in " + guessCount + " tries!");  // Congratulate the user

        return guessCount;  // Return the number of attempts
    }

    // Asks the user if they want to play again with the same range
    public void retryOption(Scanner input, int numberRange) {
        System.out.println("\nWould you like to play again with the same range? (y/n)");
        char choice = input.next().toLowerCase().charAt(0);  // Read user's choice
        if (choice == 'y') {
            playGame(input, numberRange);  // Restart the game with the same range if the user chooses 'y'
        }
    }

    // Displays the scoreboard, showing the top 5 fastest games
    public void displayScoreBoard() {
        System.out.println("\n====================");
        System.out.println("🏆 Score Board 🏆");
        System.out.println("====================");
        if (scoreBoard.isEmpty()) {  // If no games have been played, display a message
            System.out.println("No games played yet.");
            return;
        }
        
        Collections.sort(scoreBoard);  // Sort the scoreboard in ascending order (lowest attempts first)
        System.out.println("Your top 5 fastest games are:");
        for (int i = 0; i < Math.min(5, scoreBoard.size()); i++) {  // Display only the top 5 scores
            System.out.println((i+1) + ") Finished in " + scoreBoard.get(i) + " tries");
        }
    }

    // Clears the scoreboard by emptying the list
    public void clearScoreBoard() {
        scoreBoard.clear();  // Clear the ArrayList
        System.out.println("Scoreboard has been cleared.");  // Inform the user that the scoreboard was cleared
    }

    // Confirms with the user before exiting the game
    public boolean confirmExit(Scanner input) {
        System.out.println("\nAre you sure you want to exit? (y/n)");
        char choice = input.next().toLowerCase().charAt(0);  // Read user's input for confirmation
        return choice == 'y';  // Return true if the user confirms, otherwise false
    }
}
