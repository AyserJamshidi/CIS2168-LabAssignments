import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// Your class. Notice how it has no generics.
// This is because we use generics when we have no idea what kind of data we are getting
// Here we know we are getting two pieces of data:  a string and a line number
public class IndexTree {

	// This is your root 
	// again, your root does not use generics because you know your nodes
	// hold strings, an int, and a list of integers
	private IndexNode root;

	// Make your constructor
	// It doesn't need to do anything
	IndexTree() {
		root = null;
	}

	// complete the methods below

	// this is your wrapper method
	// it takes in two pieces of data rather than one
	// call your recursive add method
	public void add(String word, int lineNumber) {
		this.root = add(root, word, lineNumber);
	}

	// your recursive method for add
	// Think about how this is slightly different the the regular add method
	// When you add the word to the index, if it already exists, 
	// you want to  add it to the IndexNode that already exists
	// otherwise make a new indexNode
	private IndexNode add(IndexNode root, String word, int lineNumber) {
		if (root == null)
			return new IndexNode(word, lineNumber);

		int comparison = word.compareTo(root.word);

		if (comparison == 0) { // Current node/root
			root.occurrences++;
			root.list.add(lineNumber);
		} else if (comparison < 0) // Left branch
			root.left = add(root.left, word, lineNumber);
		else // Right branch
			root.right = add(root.right, word, lineNumber);

		return root;
	}

	// returns true if the word is in the index
	public boolean contains(String word) {
		return contains(this.root, word);
	}

	private boolean contains(IndexNode root, String word) {
		if (root == null)
			return false;

		int comparison = word.compareTo(root.word);

		if (comparison == 0)
			return true;
		else if (comparison < 0)
			return contains(root.left, word);
		else
			return contains(root.right, word);
	}

	// call your recursive method
	// use book as guide
	public void delete(String word) {
		root = delete(root, word);
	}

	// your recursive case
	// remove the word and all the entries for the word
	// This should be no different than the regular technique.
	private IndexNode delete(IndexNode root, String word) {
		if (root == null)
			return null;

		int comparison = word.compareTo(root.word);

		if (comparison == 0) { // Deleting this
			if (root.left == null && root.right == null) { // This is a leaf as it has no children
				return null; // Sets root to null
			} else if (root.left != null && root.right == null) {
				return root.left;
			} else if (root.left == null) {
				return root.right;
			} else { // This root has two children..
				IndexNode current = root.left;
				while (current.right != null)
					current = current.right;

				root.word = current.word;
				root.left = delete(root.left, root.word);
				return root;
			}
		} else if (comparison < 0) // Less than 0, not the right root
			root.left = delete(root.left, word);
		else // Greater than 0, not the right root
			root.right = delete(root.right, word);

		return root;
	}

//	public String toStringQA() {
//		StringBuilder output = new StringBuilder();
//		preorderTraverse(root, 1, output);
//		return output.toString();
//	}
//
//	private void preorderTraverse(IndexNode root, int depth, StringBuilder output) {
//		for (int i = 1; i < depth; i++)
//			output.append(" ");
//
//		if (root == null)
//			output.append("\n");
//		else {
//			output.append(root.toString()).append("\n");
//			preorderTraverse(root.left, depth + 1, output);
//			preorderTraverse(root.right, depth + 1, output);
//		}
//	}

	public void printIndex() {
		printIndex(root);
	}

	// prints all the words in the index in inorder order
	// To successfully print it out
	// this should print out each word followed by the number of occurrences and the list of all occurrences
	// each word and its data gets its own line
	private void printIndex(IndexNode root) {
		if (root == null)
			return;

		printIndex(root.left);
		System.out.println(root);
		printIndex(root.right);
	}

	public static void main(String[] args) throws FileNotFoundException {
		Scanner scan = new Scanner(new File("Module 7 - Index Tree/src/pg100.txt"));
		IndexTree index = new IndexTree();
		int curLineNumber = 1;

		// add all the words to the tree
		while (scan.hasNextLine()) {
			String curLine = scan.nextLine();
			curLine = curLine.replaceAll("'", "").replaceAll("\\W+", " ").replaceAll("\\d", "");

			for (String curWord : curLine.split("\\s+"))
				if (curWord.length() != 0)
					index.add(curWord, curLineNumber++);
		}


		// print out the index
		index.printIndex();

		try {
			Thread.sleep(1000);
		} catch (Exception ex) {}

		System.out.println("Deleting...");
		// test removing a word from the index
		index.delete("zwaggerd");
		index.delete("zounds");
		index.delete("zodiacs");

		try {
			Thread.sleep(1000);
		} catch (Exception ex) {}


		// print out the index again
		index.printIndex();
	}
}