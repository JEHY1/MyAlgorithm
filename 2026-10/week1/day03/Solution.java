public class Solution {
	
    static final int MAX_VALID_COUNT = 200000000;
    
    int k;
    int[] arr;
    
	public int solution(int[] stones, int k) {
        init(stones, k);
		return getMin(0, MAX_VALID_COUNT);
	}
    
    private void init(int[] stones, int k){
        arr = stones;
        this.k = k;
    }
    
    private boolean valid(int mid){
		int blankCount = 0;
        
        for(int i = 0; i < arr.length; i++) {
			if(arr[i] < mid) {
				blankCount++;
				if(blankCount == k) {
					return false;
				}
			}
			else {
				blankCount = 0;
			}
		}
        
        return true;
    }
    
	private int getMin(int start, int end) {
		if(start == end) {			
			return start;
		}
				
		int mid = (start + end + 1) / 2;
		
		if(valid(mid)) {           
			return getMin(mid, end);
		}
		else {
			return getMin(start, mid - 1);
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