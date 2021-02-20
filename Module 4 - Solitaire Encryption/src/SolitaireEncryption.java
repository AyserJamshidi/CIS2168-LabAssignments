import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SolitaireEncryption {

	public static void main(String[] args) {
		for (String curArg : args)
			switch (curArg) {
				case "-d": { // Decrypt
					System.out.print("Enter encrypted message: ");
					String userInput = getMessage();

					System.out.println("Encrypted: " + userInput + "\n Original: " + messageCrypt(userInput, null));
					break;
				}
				case "-e": { // Encrypt
					List<Integer> loadedHalfDeck = loadHalfDeck();
					HalfDeck halfDeck;

					if (loadedHalfDeck != null) {
						halfDeck = new HalfDeck(loadedHalfDeck);
						System.out.println("Loaded deck: " + loadedHalfDeck);
					} else
						halfDeck = new HalfDeck();

					System.out.print("\nEnter message: ");
					String userInput = getMessage();
					System.out.println("Encrypted: " + messageCrypt(userInput, halfDeck) + "\n Original: " + userInput);
					break;
				}
			}
	}

	public static List<Integer> loadHalfDeck() {
		try {
			Scanner file = new Scanner(new File("Module 4 - Solitaire Encryption/src/halfdeck.txt"));
			List<Integer> loadedHalfDeck = new ArrayList<>();

			while (file.hasNext())
				loadedHalfDeck.add(file.nextInt());

			return loadedHalfDeck;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String getMessage() {
		Scanner in = new Scanner(System.in);
		String rawInput = in.nextLine(); // Acquire next line as input
		StringBuilder cleanedInput = new StringBuilder(); // Cleaned container
		cleanedInput.append(rawInput.replaceAll("\\W", "").toUpperCase()); // Remove jump and append

		// If not a multiple of 5, keep adding X until it is.
		for (int i = 0; i < cleanedInput.length() % 5; i++)
			cleanedInput.append("X");

		return cleanedInput.toString();
	}

	public static String messageCrypt(String givenMessage, HalfDeck givenHalfDeck) {
		String cleanedMessage = cleanString(givenMessage);
		StringBuilder outputString = new StringBuilder();
		List<Integer> keystream = new ArrayList<>();

		if (givenHalfDeck != null) {
			for (int i = 0; i < cleanedMessage.length(); i++) {
				int possibleKey = getKey(givenHalfDeck);

				if (!givenHalfDeck.isJoker(possibleKey)) {
					keystream.add(possibleKey);
					outputString.append(letterCryption(cleanedMessage.charAt(i), possibleKey, true));
				} else
					i--;
			}

			System.out.println("Keystream: " + keystream.toString().replaceAll("[,\\[\\]]", ""));
		} else {
			keystream = loadKeystream();

			for (int i = 0; i < cleanedMessage.length(); i++)
				outputString.append(letterCryption(cleanedMessage.charAt(i), keystream.get(i), false));
		}

		return outputString.toString();
	}

	private static char letterCryption(char letter, int key, boolean isEncrypting) {
		int outputNum = letter - '@' + (isEncrypting ? key : 26 - key);
		if (outputNum > 26)
			outputNum -= 26;

		return (char) (outputNum + '@');
	}

	/** Executes steps 1 through 5 of the Soltaire Encryption
	 *
	 * @param halfDeck The desired half deck to use
	 * @return
	 */
	public static int getKey(HalfDeck halfDeck) { // calls the steps methods
		halfDeck.shiftRight(halfDeck.getCardPosition(27), 1); // Step 1
//		System.out.println("Step 1: " + halfDeck);
		halfDeck.shiftRight(halfDeck.getCardPosition(28), 2); // Step 2
//		System.out.println("Step 2: " + halfDeck);
		halfDeck.jokerTripleCut(); // Step 3
//		System.out.println("Step 3: " + halfDeck);
		halfDeck.shiftAmountOfCards(); // Step 4
//		System.out.println("Step 4: " + halfDeck);
		return halfDeck.nextCardValue(); // Step 5
	}

	private static List<Integer> loadKeystream() {
		List<Integer> outputKeystream = new ArrayList<>();
		try {
			Scanner scan = new Scanner(new File("Module 4 - Solitaire Encryption/src/keystream.txt"));

			while (scan.hasNext())
				outputKeystream.add(scan.nextInt());

			scan.close();
		} catch (Exception e) {
			if (e instanceof FileNotFoundException)
				System.out.println("keystream.txt not found");
			else
				System.out.println("Something happened while loading the keystream values! Are they all integers?");

			System.exit(1);
		}

		return outputKeystream;
	}

	private static String cleanString(String givenMessage) {
		StringBuilder outputString = new StringBuilder(givenMessage.replaceAll("\\W", "").toUpperCase());

		for (int i = 0; i < outputString.length() % 5; i++)
			outputString.append("X");

		return outputString.toString();
	}
}