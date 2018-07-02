public class SortingAlgorithm {
	public static InvoiceList<Invoice> quickSort(InvoiceList<Invoice> list) {
		InvoiceList<Invoice> result = new InvoiceList<Invoice>(list);
		quickSortRecursive(result, 0, result.getSize()-1);
		return result;
	}
	
	private static void quickSortRecursive(InvoiceList<Invoice> result, int low, int high) {
		int key = partition(result, low, high);
		if (low < key-1) {
			quickSortRecursive(result, low, key-1);
		}
		if (key < high) {
			quickSortRecursive(result, key, high);
		}
	}
	
	private static int partition(InvoiceList<Invoice> result, int left, int right){
		int i = left, j = right;
		Invoice temp;
		int pivotIndex = (left + right)/2;
		Invoice pivot = result.get(pivotIndex);
		
		while (i <= j) {
			while (result.get(i).compareTo(pivot) < 0) {
				i++;
			}
			while (result.get(j).compareTo(pivot) > 0) {
				j--;
			}
			if (i <= j) {
				temp = result.get(i);
				result.alterAtIndex(i, result.get(j));
				result.alterAtIndex(j, temp);
				i++;
				j--;
			}
		}
		return i;
	}
}
