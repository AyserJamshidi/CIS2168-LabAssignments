import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class RegexStuff {
	public static void main(String[] args) {
		try {
			ArrayList<String> words = wordList("Module 0 - Extra Credit/src/words.txt");

			System.out.println("--------------------- Chapter 1 ---------------------");
			System.out.println("1. I like animals. How many words contain the word ’cat’ or ’dog’ in them?");
			System.out.println(regexSearch(words, "cat|dog")); // Cat or dog

			System.out.println("\n2. Four letters words are supposedly naughty. How many four letter words are there?");
			System.out.println(regexSearch(words, "^\\w{4}$")); // Word with length 4

			int ingEnding = regexSearch(words, "ing$"); // ing at the end
			int ionEnding = regexSearch(words, "ion$"); // ion at the end
			System.out.println("\n3. Do more words end in “ing” or “ion?”");
			System.out.println((ingEnding > ionEnding) ? "ing" : "ion");

			System.out.println("\n4. How many words contain a “q” not immediately followed by a “u.”");
			System.out.println(regexSearch(words, "q[^u]")); // q, excluding u as the next character

			System.out.println("\n5. How many words have no vowels?");
			System.out.println(regexSearch(words, "\\b[^aeiou]+\\b")); // exclude all vowels, being greedy to scan entire word

			System.out.println("\n6. How many words with two vowels in a row are there?");
			System.out.println(regexSearch(words, "[aeiou]{2}")); // include only vowels for the length of 2

			System.out.println("\n7. How many words with at least two vowels are there? The vowels need not be adjacent, like in the previous problem.");
			System.out.println(regexSearch(words, "[aeiou].*?[aeiou]")); // include a vowel, then another spanning the entire string

			System.out.println("\n--------------------- Chapter 2 ---------------------");
			System.out.println("1. What is the difference between .* and .*? ?");
			System.out.println(".* will span the entirety of the given string, collecting all of the matches whereas" +
					"\n.*? will stop at the first match of the given pattern");

			System.out.println("\n2. How would you write a regex that matches the full name of someone whose last name is Nakamoto? \n" +
					"You can assume that the first name that comes before it will always be one word that begins with a capital letter. \n" +
					"The regex must match the following:"
					+ "\n• ’Satoshi Nakamoto’\n" +
					"• ’Alice Nakamoto’\n" +
					"• ’RoboCop Nakamoto’\n" +

					"\nbut not the following:" +
					"\n• ’satoshi Nakamoto’ (where the first name is not capitalized)" +
					"\n• ’Mr. Nakamoto’ (where the preceding word has a non-letter character)" +
					"\n• ’Nakamoto’ (which has no first name)" +
					"\n• ’Satoshi nakamoto’ (where Nakamoto is not capitalized)");
			System.out.println("[A-Z].*?[a-z] Nakamoto");
			System.out.println("\n[A-Z] ensure the first letter is capital");
			System.out.println(".*? makes the search lazy and ensure at this point we just look for the smallest possible matches");
			System.out.println("[a-z] Nakamoto finalizes the search by taking into account only lowercase letters, a space, followed by Nakamoto");

			System.out.println("\n3. Create a regex which matches the strings twenty, twenty-one, twenty-three, ... ,ninety-nine");
			System.out.println("(twenty|thirty|forty|fifty|sixty|seventy|eighty|ninety)?(-(one|two|three|four|five|six|seven|eight|nine))?");
		} catch (FileNotFoundException ex) {
			System.out.println("We couldn't find the file...");
		}
	}

	public static ArrayList<String> wordList(String file) throws FileNotFoundException {
		Scanner scan = new Scanner(new File(file));
		ArrayList<String> outputList = new ArrayList<>();

		while (scan.hasNext())
			outputList.add(scan.next());

		return outputList;
	}

	public static int regexSearch(ArrayList<String> words, String regexSearchTerms) throws FileNotFoundException {
		Pattern pattern = Pattern.compile(regexSearchTerms, Pattern.CASE_INSENSITIVE);
		int outputValue = 0;

		for (String currentWord : words) // Loop every word in the given list
			if (pattern.matcher(currentWord).find()) // If the regex pattern matches at least once in the current word
				outputValue++;

		return outputValue;
	}
}
