package org.example;

import org.example.utils.GameUtils;

import java.util.Scanner;

public class Game {

    private final Scanner scanner = new Scanner(System.in);
    private boolean userQuitedGame = false;
    private boolean isWon;

    public void start() {
        System.out.print("To Start The Game Press 'Enter'");
        String input = scanner.nextLine().toLowerCase();

        if (input.isEmpty()) {
            System.out.println("Game Started");
            play();
        } else {
            System.out.println("Press 'Y' If You Want To Quit");
            input = scanner.nextLine().toLowerCase();
            if (input.equals("y")) {
                System.out.println("You Quited The Game");
                scanner.close();
                return;
            } else {
                start();
            }
        }

        if (userQuitedGame) {
            scanner.close();
            return;
        }

        if (isWon) {
            System.out.println("You won the game! Congratulations! Wanna try again?");
            handleUserResponse();
        } else {
            System.out.println("You lost the game! Wanna try again?");
            handleUserResponse();
        }
    }

    private void play() {
        System.out.println("It's Time To Guess The Letters!");
        GameUtils gameUtils = new GameUtils();

        this.isWon = false;
        int currentNumberOfMistakes = 0;
        while (currentNumberOfMistakes < 7) {
            if (gameUtils.isGameWon()) {
                this.isWon = true;
                break;
            }
            gameUtils.printBuzzWord();
            System.out.println("Enter a letter: ");
            String letter = scanner.nextLine().toLowerCase();
            if (gameUtils.previouslyGuessedThisLetter(letter)) {
                System.out.println("You're already entered this letter. Press 'Enter' to try again or 'Q' to exit.");
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("q")) {
                    userQuitedGame = true;
                    break;
                } else {
                    continue;
                }

            }

            if (letter.length() > 1) {
                System.out.println("You need to enter only one letter. Press 'Enter' to try again or 'Q' to exit.");
                String input = scanner.nextLine();
                if (input.isEmpty()) {
                    continue;
                }
                if (input.equals("q")) {
                    break;
                }
            }

            if (gameUtils.isCorrectAnswer(letter)) {
                System.out.println("You're right!");
            } else {
                System.out.println("You're Wrong!");
                currentNumberOfMistakes += 1;
                AsciiPictures.drawHangMan(currentNumberOfMistakes);
            }
        }
    }

    private void handleUserResponse() {
        System.out.println("Press 'Enter' to try again or 'Q' to exit.");
        String input = scanner.nextLine();
        while (!input.equalsIgnoreCase("q") && !input.isEmpty()) {
            System.out.println("Press 'Enter' to try again or 'Q' to exit.");
            input = scanner.nextLine();
        }
        if (input.isEmpty()) {
            this.play();
        } else {
            System.out.println("Thank you for play!");
            userQuitedGame = true;
        }
    }
}
