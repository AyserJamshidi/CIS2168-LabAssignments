import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class CheatersHangman {

	public static void main(String[] args) throws FileNotFoundException {
		// Things that don't really change (except the dictionary)
		Scanner dictionary = new Scanner(new File("Module 9 - Cheater's Hangman/src/words.txt")); // Step 1
		final Set<Character> guessedLetters = new HashSet<>(); // User's guessed letters
		final Map<Integer, Character> correctLetters = new HashMap<>(); // Added by us so we can choose a word on the fly

		// User input
		int desiredWordSize = 0;
		List<String> wordFamily = null;

		while (wordFamily == null) {
			desiredWordSize = integerQuestion("How many letters do you want the word to be?"); // Step 2
			wordFamily = filterDictionary(dictionary, desiredWordSize); // Contains only the words we can cheat with

			if (wordFamily.size() == 0) {
				System.out.println("We couldn't find any words in our dictionary with " + desiredWordSize + " letters!");

				// Reset the dictionary as scanner must always start from the beginning
				dictionary = new Scanner(new File("Module 9 - Cheater's Hangman/src/words.txt"));
				wordFamily = null;
			}
		}

		int maxGuessAttempts = integerQuestion("How many guesses would you like to have?"); // Step 3

		// Loop for the actual hangman game
		while ((correctLetters.size() != desiredWordSize)  // While our correct letters doesn't match the desired word size
				&& (maxGuessAttempts - (guessedLetters.size() - correctLetters.size()) > 0)) { // AND we still have attempts left
			System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

			stepA(correctLetters, guessedLetters, desiredWordSize, maxGuessAttempts);
//			System.out.println("wordFamily Size: " + wordFamily.size());
			char guessedLetter = stepB(guessedLetters);
			boolean guessedCorrectly = stepC(guessedLetter, wordFamily);

			if (guessedCorrectly) {
				String chosenWord = wordFamily.get(0);

				for (int i = 0; i < chosenWord.length(); i++)
					if (chosenWord.charAt(i) == guessedLetter)
						correctLetters.put(i, guessedLetter);
			}
		}

		// Win/Lose output
		results(wordFamily, guessedLetters, correctLetters, maxGuessAttempts);
	}

	public static void results(List<String> wordFamily, Set<Character> guessedLetters, Map<Integer, Character> correctLetters, int maxGuessAttempts) {
		if ((maxGuessAttempts - (guessedLetters.size() - correctLetters.size())) == 0) { // You lose!
			StringBuilder outputString = new StringBuilder();

			outputString.append("You've ran out of guesses!  The word was ");

			if (wordFamily.size() > 1)
				outputString.append(wordFamily.get(new Random().nextInt(wordFamily.size())));
			else
				outputString.append(wordFamily.get(0));

			System.out.println(outputString.toString());
		} else
			System.out.println("We win! The word was " + wordFamily.get(0));
	}

	public static boolean stepC(char guessedLetter, List<String> wordFamily) {
		// If the current word exists in the list and it doesn't contain the character, remove it
		for (int i = 0; i < wordFamily.size(); i++) {
			String curString = wordFamily.get(i);

			if (wordFamily.size() <= 1)
				return true;

			if (curString.indexOf(guessedLetter) != -1) {
				wordFamily.remove(curString);
				i--;
			}
		}

		return false;
	}

	public static char stepB(Set<Character> guessedLetters) {
		Scanner input = new Scanner(System.in);

		for (; ; ) {
			System.out.print("Next guess: ");
			String letterContainer = input.next().toUpperCase();

			if (letterContainer.length() != 1) {
				System.out.println("Please enter a single letter!");
			} else {
				char letter = letterContainer.charAt(0);

				if (!guessedLetters.add(letter))
					System.out.println("You've already guessed the letter " + letterContainer);
				else
					return letter;
			}
		}
	}

	public static void stepA(Map<Integer, Character> correctLetters, Set<Character> guessedLetters, int desiredWordSize, int maxGuessAttempts) {
		StringBuilder revealedLetterOutput = new StringBuilder();

		// Loops desiredWordSize times to display each individual letter or a blank space if not guessed properly.
		for (int i = 0; i < desiredWordSize; i++) {
			if (correctLetters.containsKey(i))
				revealedLetterOutput.append(correctLetters.get(i)).append(" ");
			else
				revealedLetterOutput.append("_ ");
		}

		System.out.println(revealedLetterOutput.toString());
		System.out.println("Wrong guesses: " + (guessedLetters.size() - correctLetters.size()));
		System.out.println("Remaining attempts: " + (maxGuessAttempts - (guessedLetters.size() - correctLetters.size())));
	}

	public static List<String> filterDictionary(Scanner wordList, int requiredLength) {
		List<String> wordFamily = new LinkedList<>();

		while (wordList.hasNext()) {
			String curWord = wordList.next();

			if (curWord.length() == requiredLength)
				wordFamily.add(curWord.toUpperCase());
		}

		return wordFamily;
	}

	public static int integerQuestion(String message) {
		Scanner input = new Scanner(System.in);

		for (; ; )
			try {
				System.out.print(message + " ");
				return input.nextInt();
			} catch (Exception ex) {
				System.out.println("That's not a valid number, please enter only integers!");
				input.next(); // Move past this incorrect token
			}
	}
}
