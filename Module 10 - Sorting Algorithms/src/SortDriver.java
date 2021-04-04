import java.util.Arrays;

public class SortDriver {
	public static void main(String[] args) {
		Sort sorter = new Sort();

		int[] unsortedArray = {8,47,89,2,3,4,7,128,93,4,71,23,41,23,4};
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

		unsortedArrayCopy = Arrays.copyOf(unsortedArray, unsortedArray.length);
		System.out.println("\nmergesort BEFORE: " + Arrays.toString(unsortedArrayCopy));
		sorter.quick(unsortedArrayCopy);
		System.out.println("mergesort AFTER: " + Arrays.toString(unsortedArrayCopy));
	}
}