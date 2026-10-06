import java.util.Arrays;

public class Solution {
	
	final static int WIN = 1;
	final static int LOSE = 0;

  int edgeCount;
	int[] head;
	int[] to;
	int[] next;
	int[] result;
  int canWin;
	int canLose;

	public int solution(int n, int[][] results) {
		int answer = 0;
		init(n, results);

		for(int v = 1; v <= n; v++) {
			if(canGetValidResult(n, v)) {
				answer++;
			}
		}
		
		return answer;
	}
	
	public void init(int n, int[][] results) {
		int E = results.length;
		edgeCount = 0;
		head = new int[n + 1];
		Arrays.fill(head, -1);
		to = new int[2 * E];
		next = new int[2 * E];
		result = new int[2 * E];
		
		for(int[] result : results) {
			int v1 = result[0];
			int v2 = result[1];
			
			addEdge(v1, v2, WIN);
			addEdge(v2, v1, LOSE);
		}
	}
	
	public void addEdge(int from, int to, int result) {
		this.to[edgeCount] = to;
		this.result[edgeCount] = result;
		this.next[edgeCount] = this.head[from];
		this.head[from] = edgeCount++;
	}
	
	public boolean canGetValidResult(int n, int start) {
		canWin = 0;
		canLose = 0;
		boolean[] visited = new boolean[n + 1];
		dfs(start, WIN, visited);
		dfs(start, LOSE, visited);
		
		return canWin + canLose == n - 1;
	}
	
	public void dfs(int currentV, int findResult, boolean[] visited) {
		for(int edgeIdx = head[currentV]; edgeIdx != -1; edgeIdx = next[edgeIdx]) {
			int nextV = to[edgeIdx];
			int result = this.result[edgeIdx];
			
			if(visited[nextV]) {
				continue;
			}
			
			if(result != findResult) {
				continue;
			}
			
			if(findResult == WIN) {
				canWin++;
			}
			else if(findResult == LOSE) {
				canLose++;
			}
			
			visited[nextV] = true;
			dfs(nextV, findResult, visited);
		}
	}
}