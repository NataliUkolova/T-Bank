import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;


public class GameManager {
    private final String[] words = {"word", "hangman", "game"};
    private String word;
    private int attempts = 5;
    private Map<Integer, Character> guessedLetters = new HashMap<>();


    public void startGame() {
        Scanner sc = new Scanner(System.in);
        word = getRandomWord();
        System.out.println("Игра началась. Попробуйте угадать букву: ");
        System.out.println(wordDrawer());
        while (!gameIsEnded()) {
            String letter;
            do {
                System.out.print("Введите букву или 'exit' для выхода: ");
                letter = sc.next();
                if (letter.equalsIgnoreCase("exit")) {
                    System.out.println("Игра завершена.");
                    sc.close();
                    return;
                }
                if (letter.length() != 1) {
                    System.out.println("Ошибка: Введите ровно один символ.");
                }
            } while (letter.length() != 1);

            if (letterFinder(letter.charAt(0))) {
                System.out.println("Угадали!");
                System.out.println(wordDrawer());
            } else {
                attempts--;
                System.out.println("Неверно, попыток осталось: " + attempts);
                System.out.println("Слово: " + wordDrawer());
            }
        }

        sc.close();
    }

   
    private String getRandomWord() {
        Random random = new Random();
        int randomIndex = random.nextInt(words.length);
        return words[randomIndex];
    }

 
    private boolean gameIsEnded() {
        boolean victory = false;
        boolean loose = false;
        if (guessedLetters.size() == word.length()) {
            victory = true;
            System.out.println("Игра закончена. Вы победили!");
            return victory;
        } else {
            if (attempts == 0) {
                loose = true;
                System.out.println("Все попытки потрачены. Игра окончена");
                return loose;
            }
        }

        return victory;
    }

    private boolean letterFinder(char letter) {
        boolean letterWasFounded = false;
        char[] letters = word.toCharArray();
        for (int i = 0; i < letters.length; i++) {
            if (letter == letters[i]) {
                letterWasFounded = true;
                guessedLetters.put(i, letter);
            }
        }
        return letterWasFounded;
    }

 
    private String wordDrawer() {
        String hiddenWord = "*".repeat(word.length());
        if (guessedLetters.size() != word.length() && !guessedLetters.isEmpty()) {
            char[] letters = hiddenWord.toCharArray();
            for (Map.Entry<Integer, Character> entry : guessedLetters.entrySet()) {
                int index = entry.getKey();
                char letter = entry.getValue();
                if (index >= 0 && index < letters.length) {
                    letters[index] = letter;
                }
            }
            hiddenWord = new String(letters);
        }else if (guessedLetters.size() == word.length()){
            hiddenWord = word;
        }
        return hiddenWord;
    }


}
