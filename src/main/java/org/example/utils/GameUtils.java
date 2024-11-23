package org.example.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class GameUtils {
    private ArrayList<String> word = new ArrayList<>();
    private ArrayList<String> buzzWord = new ArrayList<>();
    private ArrayList<String> addedChars = new ArrayList<>();
    private int charsOpened = 0;
    private boolean gameIsOver = false;

    public GameUtils() {
        this.getNewWord();
        this.getNewBuzzWord();
    }

    private void getNewWord() {
        String localWord = null;
        File file = new File("src/main/resources/ascii.txt");
        try (Scanner scanner = new Scanner(file)) {
            List<String> lines = Arrays.asList(
                    scanner.useDelimiter("\\Z").next().split(" "));
            localWord = lines.get((int) (Math.random() * lines.size()));
        } catch (Exception e) {
            System.out.println("There is an exception in file loading");
        }

        for (char character : localWord.toCharArray()) {
            this.word.add(Character.toString(character));
        }
    }

    private void getNewBuzzWord() {
        for (String character : this.word) {
            this.buzzWord.add("*");
        }
    }

    public void modifyCurrentBuzzWord(String userInput) {
        for (int i = 0; i < this.word.size(); i++) {
            if (this.word.get(i).equals(userInput.toLowerCase())) {
                this.buzzWord.set(i, userInput.toLowerCase());
                this.charsOpened++;
                this.gameIsOver = this.word.size() == charsOpened;
            }
        }
    }

    public void printBuzzWord() {
        StringBuilder result = new StringBuilder();

        for (String ch : this.buzzWord) {
            if (ch != "*") {
                result.append("|" + ch + "| ");
            } else {
                result.append("|*| ");
            }
        }

        System.out.println(result);
    }

    public boolean previouslyGuessedThisLetter(String userInput) {
        return this.addedChars.contains(userInput.toLowerCase());
    }

    public boolean isCorrectAnswer(String userInput) {
        if (this.word.contains(userInput)) {
            this.modifyCurrentBuzzWord(userInput);
            addedChars.add(userInput);
            return true;
        }
        addedChars.add(userInput);
        return false;
    }

    public boolean isGameWon() {
        return this.gameIsOver;
    }

}

