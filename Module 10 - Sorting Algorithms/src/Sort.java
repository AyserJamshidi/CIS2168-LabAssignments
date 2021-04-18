import java.util.*;
import java.util.stream.Collectors;

public class Sort {

	private DataCollection dataCollect;

	public Sort() {
		this.dataCollect = null;
	}

	public Sort(DataCollection dataCollect) {
		this.dataCollect = dataCollect;
	}

	private void interchange(int[] givenArray, int i, int j) {
		int valueContainer = givenArray[i];

		givenArray[i] = givenArray[j];
		givenArray[j] = valueContainer;
		dataCollect.addExchange(2);
	}

	private void interchange(List<Integer> givenList, int i, int j) {
		int valueContainer = givenList.get(i);

		givenList.set(i, givenList.get(j));
		givenList.set(j, valueContainer);
		dataCollect.addExchange(2);
	}

	public void selection(int[] givenArray) { // O(n^2)
		for (int i = 0; i < givenArray.length - 1; i++) { // Loops through the entire array once, O(n)
			int curSmallestIndex = i;

			// Finds the smallest number starting at index i, saving it into
			// curSmallestIndex
			for (int j = i; j < givenArray.length; j++)
				if (givenArray[j] < givenArray[curSmallestIndex])
					curSmallestIndex = j;

			interchange(givenArray, i, curSmallestIndex); // swap the values at index i and curSmallestIndex
		}
	}

	public void bubble(int[] givenArray) { // O(n^2)
		for (int i = 0, orderedAmount = 0; i < givenArray.length - orderedAmount; i++) // Loop from the beginning up
			// until the sorted numbers
			for (int j = 0; j < (givenArray.length - orderedAmount - 1); j++) // Loop from the beginning up until the
				// sorted numbers - 1
				if (givenArray[j] > givenArray[j + 1]) // if the left number is greater than the right number, swap them
					interchange(givenArray, j, j + 1); // swap the adjacent values
	}

	public void insertion(int[] givenArray) { // O(n^2)
		addComparison(); // Add one for the final comparison when we leave the for-loop
		for (int i = 1; i < givenArray.length; i++) { // Loops through n-1 elements of the array starting at the second
			// element
			addComparison(2); // Add 1 for the initial loop comparison and another 1 for the loop below

			for (int j = i; j > 0; j--) {// If our current number is less than the number before it
				addComparison();

				if (givenArray[j] < givenArray[j - 1])
					interchange(givenArray, j, j - 1); // swap the adjacent values
			}
		}
	}

	public void quick(int[] givenArray) {
		quick(givenArray, 0, givenArray.length - 1);
	}

	private void quick(int[] givenArray, int first, int last) { // Average O(n * log(n)), Worst O(n^2)
		addComparison();
		if (givenArray.length < 2) // We can't sort this array
			return;

		addComparison();
		if (Math.abs(first - last) < 2) { // If the distance between the indexes is only 2 elements

			addComparison();
			if (last > first) { // Sometimes last is < first or < 0...

				addComparison();
				if (givenArray[first] > givenArray[last])
					interchange(givenArray, first, last);
			}
		} else {
			addComparison();
			if (first < last) { // Anything > 2 so we require partitioning
				int pivIndex = quicksortPartition(givenArray, first, last);

				quick(givenArray, first, pivIndex - 1); // Sort the left half
				quick(givenArray, pivIndex + 1, last); // Sort the right half
			}
		}
	}

	private int quicksortPartition(int[] givenArray, int up, int down) {
		int first = up;
		int pivot = givenArray[up++];

		do {
			addComparison();
			while (up < givenArray.length && givenArray[up] <= pivot) {
				addComparison(2);
				up++;
			}

			addComparison();
			while (down >= 0 && givenArray[down] > pivot) {
				addComparison(2);
				down--;
			}

			addComparison();
			if (up < down)
				interchange(givenArray, up, down);

			addComparison();
		} while (up <= down);

		addComparison();
		if (first != down) // Save very little time by avoiding useless swaps
			interchange(givenArray, first, down);

		return down;
	}

	/**
	 * Mergesort wrapper
	 */
	public List<Integer> merge(int[] array) {
		List<Integer> list = Arrays.stream(array).boxed().collect(Collectors.toList()); // Turns the int array into an
		// Integer list
		return merge(list);
	}

	/**
	 * Mergesort
	 */
	public List<Integer> merge(List<Integer> list) { // O(n log(n))
		addComparison();
		if (list.size() <= 1)
			return list;

		List<Integer> left = sublist(list, 0, (list.size() / 2) - 1);
		List<Integer> right = sublist(list, list.size() / 2, list.size() - 1);

		left = merge(left); // Sort left half
		right = merge(right); // Sort right half

		list = mergeLists(left, right);

		return list;
	}

	/**
	 * merges listOne and listTwo in ascending order.
	 */
	private List<Integer> mergeLists(List<Integer> listOne, List<Integer> listTwo) {
		List<Integer> outputList = new ArrayList<>(); // Use linked list cause adding will always be constant time

		int oneIndex = 0, twoIndex = 0;

		addComparison();
		while (outputList.size() != (listOne.size() + listTwo.size())) { // While we haven't iterated both lists completely
			addComparison(2);
			if (oneIndex < listOne.size() && twoIndex < listTwo.size()) { // We have two numbers!
				addComparison();
				if (listOne.get(oneIndex) < listTwo.get(twoIndex)) // If left num is less than right num
					outputList.add(listOne.get(oneIndex++));
				else // Right num was less than left
					outputList.add(listTwo.get(twoIndex++));
			} else {
				addComparison();
				if (oneIndex < listOne.size()) // We ONLY have list one numbers
					outputList.add(listOne.get(oneIndex++));

				addComparison();
				if (twoIndex < listTwo.size()) // We ONLY have list two numbers
					outputList.add(listTwo.get(twoIndex++));
			}
		}

		return outputList;
	}

	/**
	 * Returns a new list containing the elements from bounds [start, end];
	 */
	private List<Integer> sublist(List<Integer> list, int start, int end) {
		List<Integer> outputList = new ArrayList<>();

		addComparison();
		while (start <= end) {
			addComparison();
			outputList.add(list.get(start++));
		}

		return outputList;
	}

	private void addComparison() {
		addComparison(1);
	}

	private void addExchange() {
		addExchange(1);
	}

	private void addComparison(int i) {
		if (dataCollect != null)
			dataCollect.addComparison(i);
	}

	private void addExchange(int i) {
		if (dataCollect != null)
			dataCollect.addExchange(i);
	}

	public DataCollection data() {
		return dataCollect;
	}
}