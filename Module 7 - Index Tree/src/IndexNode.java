import java.util.ArrayList;
import java.util.List;

public class IndexNode {

	String word; // The word for this entry

	int occurrences; // The number of occurrences for this word

	List<Integer> list; // A list of line numbers for this word.

	IndexNode left;
	IndexNode right;

	// Constructors
	// Constructor should take in a word and a line number
	// it should initialize the list and set occurrences to 1
	IndexNode(String word, int lineNumber) {
		this.word = word;
		this.occurrences = 1;

		this.list = new ArrayList<>();
		this.list.add(lineNumber);
	}

	// Complete This
	// return the word, the number of occurrences, and the lines it appears on.
	// string must be one line
	public String toString() {
		return word + " occurred " + occurrences + " times on lines " + list;
	}
}