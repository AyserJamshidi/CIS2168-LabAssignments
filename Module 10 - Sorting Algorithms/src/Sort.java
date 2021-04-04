import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Sort {

	private void interchange(int[] givenArray, int i, int j) {
		int valueContainer = givenArray[i];

		givenArray[i] = givenArray[j];
		givenArray[j] = valueContainer;
	}

	private void interchange(List<Integer> givenList, int i, int j) {
		int valueContainer = givenList.get(i);

		givenList.set(i, givenList.get(j));
		givenList.set(j, valueContainer);
	}

	public void selection(int[] givenArray) { // O(n^2)
		for (int i = 0; i < givenArray.length - 1; i++) { // Loops through the entire array once, O(n)
			int curSmallestIndex = i;

			// Finds the smallest number starting at index i, saving it into curSmallestIndex
			for (int j = i; j < givenArray.length; j++)
				if (givenArray[j] < givenArray[curSmallestIndex])
					curSmallestIndex = j;

			interchange(givenArray, i, curSmallestIndex); // swap the values at index i and curSmallestIndex
		}
	}

	public void bubble(int[] givenArray) { // O(n^2)
		for (int i = 0, orderedAmount = 0; i < givenArray.length - orderedAmount; i++) // Loop from the beginning up until the sorted numbers
			for (int j = 0; j < (givenArray.length - orderedAmount - 1); j++) // Loop from the beginning up until the sorted numbers - 1
				if (givenArray[j] > givenArray[j + 1]) // if the left number is greater than the right number, swap them
					interchange(givenArray, j, j + 1); // swap the adjacent values
	}

	public void insertion(int[] givenArray) { // O(n^2)
		for (int i = 1; i < givenArray.length; i++) // Loops through n-1 elements of the array starting at the second element
			for (int j = i; j > 0 && givenArray[j] < givenArray[j - 1]; j--) // If our current number is less than the number before it
				interchange(givenArray, j, j - 1); // swap the adjacent values
	}

	public void quick(int[] givenArray) {
		quick(givenArray, 0, givenArray.length - 1);
	}

	private void quick(int[] givenArray, int first, int last) { // Average O(n * log(n)), Worst O(n^2)
		if (givenArray.length < 2) // We can't sort this array
			return;

		if (Math.abs(first - last) < 2) { // If the distance between the indexes is only 2 elements
			if (last > first) // Sometimes last is < first or < 0...
				if (givenArray[first] > givenArray[last])
					interchange(givenArray, first, last);
		} else if (first < last) { // Anything > 2 so we require partitioning
			int pivIndex = quicksortPartition(givenArray, first, last);

			quick(givenArray, first, pivIndex - 1); // Sort the left half
			quick(givenArray, pivIndex + 1, last); // Sort the right half
		}
	}

	private int quicksortPartition(int[] givenArray, int up, int down) {
		int first = up;
		int pivot = givenArray[up++];

		do {
			while (up < givenArray.length && givenArray[up] <= pivot)
				up++;

			while (down >= 0 && givenArray[down] > pivot)
				down--;

			if (up < down)
				interchange(givenArray, up, down);

		} while (up <= down);

		if (first != down) // Save very little time by avoiding useless swaps
			interchange(givenArray, first, down);

		return down;
	}

	public List<Integer> merge(List<Integer> list) {
		if (list.size() <= 1)
			return list;

		List<Integer> left = sublist(list, 0, (list.size() / 2) - 1);
		List<Integer> right = sublist(list, list.size() / 2, list.size() - 1);

		left = mergehalf(left); // Sort left half
		right = mergehalf(right); // Sort right half

		list = mergeLists(left, right);

		return list;
	}

	private List<Integer> mergehalf(List<Integer> list) {
		List<Integer> outputList = new LinkedList<>();

		outputList.add(list.get(0)); // We always have at least 1 element in the list, so add it.

		if (list.size() == 2) { // We can do it this way because adding to head/tail is still constant
			int secondElement = list.get(1);

			if (list.get(0) > secondElement)
				outputList.add(0, secondElement);
			else
				outputList.add(secondElement);
		}

		return outputList;
	}

	private List<Integer> mergeLists(List<Integer> listOne, List<Integer> listTwo) {
		List<Integer> outputList = new LinkedList<>(); // Use linkedlist cause adding will always be constant time



		return outputList;
	}

	private List<Integer> sublist(List<Integer> list, int start, int end) {
		List<Integer> outputList = new LinkedList<>();

		while (start < end)
			outputList.add(list.get(start++));

		return outputList;
	}
}