import java.util.Arrays;

public class SortingAlgorithm {
	public static Invoice[] quickSort(Invoice[] list) {
		Invoice[] result = Arrays.copyOf(list, list.length);
		quickSortRecursive(result, 0, result.length-1);
		return result;
	}
	
	private static void quickSortRecursive(Invoice list[], int low, int high) {
		int key = partition(list, low, high);
		if (low < key-1) {
			quickSortRecursive(list, low, key-1);
		}
		if (key < high) {
			quickSortRecursive(list, key, high);
		}
	}
	
	private static int partition(Invoice[] list, int left, int right){
		int i = left, j = right;
		Invoice temp;
		Invoice pivot = list[(left + right)/2];
		
		while (i <= j) {
			while (list[i].compareTo(pivot) < 0) {
				i++;
			}
			while (list[j].compareTo(pivot) > 0) {
				j--;
			}
			if (i <= j) {
				temp = list[i];
				list[i] = list[j];
				list[j] = temp;
				i++;
				j--;
			}
		}
		return i;
	}
}
