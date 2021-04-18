
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SortDriver {

	private static boolean DEBUG_MODE = true;

	private static int SAMPLE_SIZE = 100;
	private static int ARRAY_SIZES = 1000;
	private static int AMOUNT_OF_DIFFERENT_SORTS = 3;

	public static void main(String[] args) throws IOException {
		Sort sorter = new Sort(new DataCollection());

		if (DEBUG_MODE)
			debug(sorter);
		else
			performanceReview(sorter);
	}

	private static void performanceReview(Sort sorter) throws IOException {
		FileWriter csv;
		try {
			csv = new FileWriter("Module 10 - Sorting Algorithms/performance.csv");
		} catch (Exception ex) {
			csv = new FileWriter("performance.csv");
		}

		csv.append(",Time,Comparisons,Exchanges");

		for (int i = 64; i <= 1024; i = i * 2) {
			csv.append("\n");
			csv.append("arr size: ").append(String.valueOf(i)).append("\n");
			for (int j = 0; j < SAMPLE_SIZE; j++) {
				csv.append("Insertion,");

				int[] arr1 = randomArray(i);

				sorter.data().start();
				sorter.insertion(arr1);
				sorter.data().stop();

				csv.append(String.valueOf(sorter.data().getRuntime())).append(",");
				csv.append(String.valueOf(sorter.data().getComparisons())).append(",");
				csv.append(String.valueOf(sorter.data().getExchanges())).append(",");
				csv.append("\n");

				sorter.data().reset(); // Reset the data collector variables
			}
			csv.append("\n\n");

			for (int j = 0; j < SAMPLE_SIZE; j++) {
				csv.append("Quick,");

				int[] arr1 = randomArray(i);

				sorter.data().start();
				sorter.quick(arr1);
				sorter.data().stop();

				csv.append(String.valueOf(sorter.data().getRuntime())).append(",");
				csv.append(String.valueOf(sorter.data().getComparisons())).append(",");
				csv.append(String.valueOf(sorter.data().getExchanges())).append(",");
				csv.append("\n");

				sorter.data().reset(); // Reset the data collector variables
			}
			csv.append("\n\n");

			for (int j = 0; j < SAMPLE_SIZE; j++) {
				csv.append("Merge,");

				int[] arr1 = randomArray(i);

				sorter.data().start();
				sorter.merge(arr1);
				sorter.data().stop();

				csv.append(String.valueOf(sorter.data().getRuntime())).append(",");
				csv.append(String.valueOf(sorter.data().getComparisons())).append(",");
				csv.append(String.valueOf(sorter.data().getExchanges())).append(",");
				csv.append("\n");

				sorter.data().reset(); // Reset the data collector variables
			}
			csv.append("\n\n");
		}

		csv.close();
	}

	private static void debug(Sort sorter) {
		int[] unsortedArray = { 8, 47, 89, 2, 3, 4, 7, 128, 93, 4, 71, 23, 41, 23, 4 };
		int[] unsortedArrayCopy;

		unsortedArrayCopy = Arrays.copyOf(unsortedArray, unsortedArray.length);
		System.out.println("selectionSort BEFORE: " + Arrays.toString(unsortedArrayCopy));
		sorter.selection(unsortedArrayCopy);
		System.out.println("selectionSort AFTER: " + Arrays.toString(unsortedArrayCopy));

		unsortedArrayCopy = Arrays.copyOf(unsortedArray, unsortedArray.length);
		System.out.println("\nbubbleSort BEFORE: " + Arrays.toString(unsortedArrayCopy));
		sorter.bubble(unsortedArrayCopy);
		System.out.println("bubbleSort AFTER: " + Arrays.toString(unsortedArrayCopy));

		unsortedArrayCopy = Arrays.copyOf(unsortedArray, unsortedArray.length);
		System.out.println("\ninsertionSort BEFORE: " + Arrays.toString(unsortedArrayCopy));
		sorter.insertion(unsortedArrayCopy);
		System.out.println("insertionSort AFTER: " + Arrays.toString(unsortedArrayCopy));

		unsortedArrayCopy = Arrays.copyOf(unsortedArray, unsortedArray.length);
		System.out.println("\nquicksort BEFORE: " + Arrays.toString(unsortedArrayCopy));
		sorter.quick(unsortedArrayCopy);
		System.out.println("quicksort AFTER: " + Arrays.toString(unsortedArrayCopy));

		List<Integer> unsortedList = new LinkedList<>();
		for (int curNum : new int[] { 8, 47, 89, 2, 3, 4, 7, 128, 93, 4, 71, 23, 41, 23, 4 })
			unsortedList.add(curNum);

		System.out.println("\nmergesort BEFORE: " + unsortedList.toString());
		unsortedList = sorter.merge(unsortedList);
		System.out.println("mergesort AFTER: " + unsortedList.toString());
	}

	private static int[] randomArray(int size) {
		int[] outputArray = new int[size];

		for (int i = 0; i < size; i++)
			outputArray[i] = new Random(System.nanoTime()).nextInt(size - 1) + 1;

		return outputArray;
	}

	private static int[] descendingArray(int size) {
		int[] outputArray = new int[size];

		while (size > 0)
			outputArray[--size] = size + 1;

		return outputArray;
	}

	private static int[] powersOfTwo(int size) {
		int[] outputArray = new int[size];

		for (int i = 0; i < size; i++)
			outputArray[i] = (int) Math.pow(2, i);

		return outputArray;
	}

	private static long averageOfList(List<Long> givenList) {
		long total = 0;

		for (long curNum : givenList)
			total += curNum;

		return total / givenList.size();
	}
}