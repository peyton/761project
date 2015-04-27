package ruil.invertedindex;

import java.util.ArrayList;

public class BinarySearch {
	
	public static int searchFirstGreater(ArrayList<Short> sorted, int target){
		int low = 0, high = sorted.size()-1;
		while (low != high) {
		    int mid = (low + high) / 2; // Or a fancy way to avoid int overflow
		    if (sorted.get(mid) < target) {
		        /* This index, and everything below it, must not be the first element
		         * greater than what we're looking for because this element is no greater
		         * than the element.
		         */
		        low = mid + 1;
		    }else {
		        /* This element is at least as large as the element, so anything after it can't
		         * be the first element that's at least as large.
		         */
		        high = mid;
		    }
		}
		/* Now, low and high both point to the element in question. */
		return low;
	}

	public static void main(String[] args) {

	}

}
