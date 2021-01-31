import java.util.ArrayList;
import java.util.List;

public class ArrayListEx {

	public static void main(String[] args) {
		// 2.1 Uniqueness
		List<String> testList = new ArrayList<>();
		testList.add("Test");
		testList.add("test");
		System.out.println("Unique == " + unique(testList)); // Expect TRUE
		testList.add("Test");
		System.out.println("Unique == " + unique(testList)); // Expect FALSE

		// 2.2 All Multiples
		List<Integer> testListTwo = new ArrayList<>();
		testListTwo.add(1);
		testListTwo.add(2);
		testListTwo.add(5);
		testListTwo.add(10);
		testListTwo.add(13);
		testListTwo.add(15);
		testListTwo.add(125);
		System.out.println("allMultilpes == " + allMultiples(testListTwo, 2));

		// 2.3 All Strings of Size
		System.out.println("allStringsOfSize == " + allStringsOfSize(testList, 5)); // Expect nothing
		System.out.println("allStringsOfSize == " + allStringsOfSize(testList, 4)); // Expect original list

		// 2.4 isPermutation
		List<String> testListThree = new ArrayList<>();
		testListThree.add("Test");
		testListThree.add("Test");
		testListThree.add("test");
		System.out.println("isPermutation == " + isPermutation(testList, testListThree)); // Expect true
		testListThree.remove("Test");
		System.out.println("isPermutation == " + isPermutation(testList, testListThree)); // Expect false

		// 2.5 String To List of Words
		System.out.println("stringToListOfWords == " + stringToListOfWords("Hello!     This is a test.")); // Expect [Hello, This, is, a, test]

		// 2.6 Remove All Instances
		removeAllInstances(testList, "Test");
		System.out.println("removeAllInstances == " + testList); // Expect [test]
	}

	public static <E> boolean unique(List<E> givenList) {
		for (int currentItem = 0; currentItem < givenList.size(); currentItem++) // Loop through every item in the list.
			for (int nextItem = currentItem + 1; nextItem < givenList.size(); nextItem++) // Loop through every item AFTER the current item.
				if (givenList.get(currentItem) == givenList.get(nextItem))
					return false;

		return true;
	}

	public static List<Integer> allMultiples(List<Integer> givenList, int divisor) {
		List<Integer> outputList = new ArrayList<>();

		for (int curNumber : givenList) // Loop every number in the given list
			if (curNumber % divisor == 0) // Check if curNumber is divislbe by divisor.
				outputList.add(curNumber);

		return outputList;
	}

	public static List<String> allStringsOfSize(List<String> givenList, int length) {
		List<String> outputList = new ArrayList<>();

		for (String curString : givenList) // Loop through every string in the list.
			if (curString.length() == length) // If current string's length is the same as the given length, add it.
				outputList.add(curString);

		return outputList;
	}

	public static <E> boolean isPermutation(List<E> givenListOne, List<E> givenListTwo) {
		if (givenListOne.size() != givenListTwo.size() || givenListOne.isEmpty()) // Check if both objects can possibly be permutations.
			return false;

		List<E> listTwoCopy = new ArrayList<>(givenListTwo); // Copy the second list so we can modify it without losing data.

		for (E curObject : givenListOne) // Loop through every object in the first list.
			if (!listTwoCopy.remove(curObject)) // Try to remove the first list's object from list two copy.
				return false; // If remove returns false, it didn't have it.

		return true; // All of our checks have passed.  They are permutations.
	}

	public static List<String> stringToListOfWords(String givenString) {
		List<String> outputList = new ArrayList<>();

		for (String curString : givenString.split("\\s+")) // Loop through every word split by spaces.
			outputList.add(curString.replaceAll("\\W", "")); // Add the word while also replacing any non-word character.

		return outputList;
	}

	public static <E> void removeAllInstances(List<E> givenList, E givenItem) {
		while (givenList.contains(givenItem)) // Keep looping until the list no longer contains this object.
			givenList.remove(givenItem); // Removal of object.
	}
}