import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.StringTokenizer;

public class Solution {

	public static void main(String[] args) throws IOException {
		new Solution().process();
	}
	
	int N;
	int M;
	
	int[] nodeInSCC;		
	
	int edgeCount;
	int[] head;
	int[] next;
	int[] to;
	
	int reverseGraphEdgeCount;
	int[] reverseGraphHead;
	int[] reverseGraphNext;
	int[] reverseGraphTo;
	
	public void process() throws IOException {
		init();
		Deque<Integer> visitDoneOrder = getVisitDoneOrder();
		submitSCC(visitDoneOrder);
		boolean result = checkSAT();
		
		System.out.println(result ? 1 : 0);
	}
	
	public void init() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine().trim());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		nodeInSCC = new int[N * 2 + 1];
		
		edgeCount = 0;
		reverseGraphEdgeCount = 0;
		head = new int[N * 2 + 1];
		reverseGraphHead = new int[N * 2 + 1];
		Arrays.fill(head, -1);
		Arrays.fill(reverseGraphHead, -1);
		next = new int[2 * M];
		reverseGraphNext = new int[2 * M];
		to = new int[2 * M];
		reverseGraphTo = new int[2 * M];
		
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine().trim());
			int riteral1 = Integer.parseInt(st.nextToken());
			int riteral2 = Integer.parseInt(st.nextToken());
			
			exchange(riteral1, riteral2);
		}
	}
	
	public void addEdge(int from, int to) {
		this.to[edgeCount] = to;
		next[edgeCount] = head[from];
		head[from] = edgeCount++;
	}
	
	public void reverseGraphAddEdge(int from, int to) {
		reverseGraphTo[reverseGraphEdgeCount] = to;
		reverseGraphNext[reverseGraphEdgeCount] = reverseGraphHead[from];
		reverseGraphHead[from] = reverseGraphEdgeCount++;
	}
	
	public void exchange(int riteral1, int riteral2) {
		int from = getMappingedRiteral(-riteral1);
		int to = getMappingedRiteral(riteral2);
		
		addEdge(from, to);
		reverseGraphAddEdge(to, from);
		
		from = getMappingedRiteral(-riteral2);
		to = getMappingedRiteral(riteral1);
		
		addEdge(from, to);
		reverseGraphAddEdge(to, from);		
	}
	
	public int getMappingedRiteral(int riteral) {
		return riteral > 0 ? riteral : riteral + 2 * N + 1;
	}
	
	public Deque<Integer> getVisitDoneOrder() {
		Deque<Integer> visitDoneOrder = new ArrayDeque<>();
		
		Deque<Integer> dfsStack = new ArrayDeque<>();
		boolean[] visited = new boolean[2 * N + 1];
		
		int[] currentEdge = new int[2 * N + 1];
		Arrays.fill(currentEdge, -2);
		
		for(int i = 1; i <= 2 * N; i++) {
			if(visited[i]) {
				continue;
			}
			
			visited[i] = true;
			dfsStack.push(i);
			
			while(!dfsStack.isEmpty()) {
				int node = dfsStack.peek();
				
				if(currentEdge[node] == -1) {
					visitDoneOrder.push(node);
					dfsStack.pop();
					continue;
				}
				
				if(currentEdge[node] == -2) {
					currentEdge[node] = head[node];
				}
				
				for(int edgeIdx = currentEdge[node]; edgeIdx != -1; edgeIdx = next[edgeIdx]) {
					int nextNode = to[edgeIdx];
					currentEdge[node] = next[edgeIdx];
					
					if(!visited[nextNode]) {
						visited[nextNode] = true;
						dfsStack.push(nextNode);
						break;
					}					
				}							
			}
		}
		
		return visitDoneOrder;
	}
	
	public void submitSCC(Deque<Integer> visitDoneOrder) {
		int SCCNum = 0;
		Deque<Integer> dfsStack = new ArrayDeque<>();
		boolean[] visited = new boolean[2 * N + 1];			
		
		while(!visitDoneOrder.isEmpty()) {
			int node = visitDoneOrder.pop();
			
			if(visited[node]) {
				continue;
			}
			
			visited[node] = true;
			dfsStack.push(node);
			
			while(!dfsStack.isEmpty()) {
				int currentNode = dfsStack.pop();
				nodeInSCC[currentNode] = SCCNum;
				
				for(int edgeIdx = reverseGraphHead[currentNode]; edgeIdx != -1; edgeIdx = reverseGraphNext[edgeIdx]) {
					int nextNode = reverseGraphTo[edgeIdx];
					
					if(visited[nextNode]) {
						continue;
					}
					
					visited[nextNode] = true;
					dfsStack.push(nextNode);
				}
			}
			
			SCCNum++;
		}
		
	}
	
	public boolean checkSAT() {
		for(int i = 1; i <= N; i++) {
			if(nodeInSCC[i] == nodeInSCC[2 * N - i + 1]) {
				return false;
			}
		}
		
		return true;
	}
}