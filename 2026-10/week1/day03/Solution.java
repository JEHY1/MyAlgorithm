public class Solution {
	
	static final int MAX_SKIP_COUNT = 200000000;
	
	public int solution(int[] stones, int k) {
		return getMin(stones, 0, MAX_SKIP_COUNT, k);
	}
    
	private int getMin(int[] arr, int start, int end, int k) {
		boolean valid = true;
		int blankCount = 0;
		
		if(start == end) {			
			for(int i = 0; i < arr.length; i++) {
				if(arr[i] - start < 0) {
					blankCount++;
					if(blankCount == k) {
						return start - 1;
					}
				}
				else {
					blankCount = 0;
				}
			}
			return start;
		}
				
		int mid = (start + end) / 2;

		for(int i = 0; i < arr.length; i++) {
			if(arr[i] - mid < 0) {
				blankCount++;
				if(blankCount == k) {
					valid = false;
					break;
				}
			}
			else {
				blankCount = 0;
			}
		}
		
		if(valid) {
			return getMin(arr, mid + 1, end, k);
		}
		else {
			return getMin(arr, start, mid, k);
		}
	}
}