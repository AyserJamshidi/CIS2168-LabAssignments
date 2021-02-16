import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SolitaireEncryption {

	public static void main(String[] args) {
		HalfDeck halfDeck = new HalfDeck();
		List<Integer> keystreamValues = new ArrayList<>();
		StringBuilder encryptedInput = new StringBuilder(), decryptedInput = new StringBuilder();
		String convertedInput = getMessage(); // User's cleaned up input

		System.out.println("Converted: " + convertedInput);

		// Encryption
		for (int i = 0; i < convertedInput.length(); i++) {
			int possibleKey = getKey(halfDeck);

			if (!halfDeck.isJoker(possibleKey)) {
				keystreamValues.add(possibleKey);
				encryptedInput.append(cryption(convertedInput.charAt(i), keystreamValues.get(i), true));
			} else
				i--;
		}

		System.out.println("Encrypted: " + encryptedInput);

		// Decryption
		for (int i = 0; i < encryptedInput.length(); i++)
			decryptedInput.append(cryption(encryptedInput.charAt(i), keystreamValues.get(i), false));

		System.out.println("Decrypted: " + decryptedInput);
		System.out.println("Keystream: " + keystreamValues);
	}

	public static String getMessage() {
		Scanner in = new Scanner(System.in);
		String rawInput = in.nextLine(); // Acquire next line as input

		System.out.println("Original: " + rawInput);

		StringBuilder convertedInput = new StringBuilder(); // Converted container
		convertedInput.append(rawInput.replaceAll("\\W", "").toUpperCase()); // Remove jump and append

		// If not a multiple of 5, keep adding X until it is.
		for (int i = 0; i < convertedInput.length() % 5; i++)
			convertedInput.append("X");

		return convertedInput.toString();
	}

	public static char cryption(char letter, int key, boolean isEncrypting) {
		int outputNum = letter - '@' + (isEncrypting ? key : 26 - key);
		if (outputNum > 26)
			outputNum -= 26;

		return (char) (outputNum + '@');
	}

	public static int getKey(HalfDeck halfDeck) { // calls the steps methods
		halfDeck.shiftRight(halfDeck.getCardPosition(27), 1); // Step 1
		halfDeck.shiftRight(halfDeck.getCardPosition(28), 2); // Step 2
		halfDeck.jokerTripleCut(); // Step 3
		halfDeck.shiftAmountOfCards(); // Step 4
		return halfDeck.nextCardValue(); // Step 5
	}
}