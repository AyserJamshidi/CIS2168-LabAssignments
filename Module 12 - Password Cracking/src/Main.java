import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

	static String KEYLOG_FILE_DIR = "Module 12 - Password Cracking/src/keylog.txt";

	public static void main(String[] args) {
		try {
			Scanner file = new Scanner(new File(KEYLOG_FILE_DIR));

			// Contains Keys (every possible digit as a passcode) and an associated HashSet (every possible number that comes AFTER this key)
			HashMap<Integer, HashSet<Integer>> numsAfter = getNumbersAfterMapping(file);

			int[] finalKey = new int[numsAfter.size()]; // Create an array for the final key the size of the amount of keys in the map

			// For every key in the map, assign the key to the proper position
			for (int currentKey : numsAfter.keySet())
				finalKey[numsAfter.size() - numsAfter.get(currentKey).size() - 1] = currentKey;

			// Pretty-print the output
			System.out.println(Arrays.toString(finalKey).replaceAll("[], \\[]", ""));
			
		} catch (FileNotFoundException e) {
			System.out.println("Couldn't find the given file!");
		}
	}

	/*
	 * Takes in a keylog scanner and returns a HashMap that contains every found digit as a key and every digit
	 * that comes after the key in a HashSet
	 */
	private static HashMap<Integer, HashSet<Integer>> getNumbersAfterMapping(Scanner keylog) {
		HashMap<Integer, HashSet<Integer>> outputMap = new HashMap<>();

		while (keylog.hasNextLine()) { // Loop every line in the key file
			String curLine = keylog.nextLine(); // Store the current line

			for (int i = 0; i < curLine.length(); i++) { // Loop every char in the current line as we parse each char as an integer
				int currentKey = Character.getNumericValue(curLine.charAt(i)); // Said parsed integer
				boolean hashsetExists = outputMap.containsKey(currentKey); // Boolean we use for later

				// If hashsetExists is true, currentSet will be the existing HashSet for the current key, otherwise, create a new Hashset
				HashSet<Integer> currentSet = (hashsetExists ? outputMap.get(currentKey) : new HashSet<>());

				// Put every digit after this index into the current key's HashSet
				for (int j = i + 1; j < curLine.length(); j++)
					currentSet.add(Character.getNumericValue(curLine.charAt(j))); // Add every number after this one to the set

				// If we created a new HashSet then store it for the current key
				if (!hashsetExists)
					outputMap.put(currentKey, currentSet);
			}
		}

		return outputMap;
	}
}
