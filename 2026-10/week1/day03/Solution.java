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

// import java.util.Arrays;

// public class Solution {
	
// 	int[] parent;
// 	int[] size;
	
// 	public int solution(int[] stones, int k) {
// 		init(stones.length);
// 		int answer = 0;
// 		int[][] sortedStones = new int[stones.length][];
// 		for(int i = 0; i < stones.length; i++) {
// 			sortedStones[i] = new int[] {i, stones[i]};
// 		}
// 		Arrays.sort(sortedStones, (a, b) -> Integer.compare(a[1], b[1]));
		
// 		for(int[] sortedStone : sortedStones) {
// 			answer = sortedStone[1];
// 			int idx = sortedStone[0];
// 			stones[idx] = 0;
			
// 			if(idx - 1 != -1 && stones[idx - 1] == 0) {
// 				union(idx, idx - 1);					
// 			}
			
// 			if(idx + 1 != stones.length && stones[idx + 1] == 0) {
// 				union(idx, idx + 1);
// 			}
			
// 			if(size[find(idx)] >= k) {
// 				return answer;
// 			}
// 		}
// 		return -1;
// 	}

// 	private void init(int n) {
// 		parent = new int[n];
// 		size = new int[n];
		
// 		for(int i = 0; i < n; i++) {
// 			parent[i] = i;
// 			size[i] = 1;
// 		}
// 	}
	
// 	private int find(int x) {
// 		if(parent[x] == x) {
// 			return x;
// 		}
		
// 		return parent[x] = find(parent[x]);
// 	}
	
// 	private int union(int a, int b) {
// 		a = find(a);
// 		b = find(b);
		
// 		if(a == b) {
// 			return size[a];
// 		}
		
// 		if(size[a] < size[b]) {
// 			int temp = a;
// 			a = b;
// 			b = temp;
// 		}
		
// 		parent[b] = a;
// 		return size[a] += size[b];
// 	}
// }