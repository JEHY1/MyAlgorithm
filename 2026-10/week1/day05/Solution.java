import java.util.*;

class Solution {

    int n;
    int edgeCount;
    int[] head;
    int[] next;
    int[] to;
    
    Queue<Integer> queue;
    int[] dist;
    boolean isDuflicate;

    public int solution(int n, int[][] edges) {
        int answer = 0;
        init(n, edges);

        answer = getMaxVal();
        return answer;
    }

    private void init(int n, int[][] edges){
        this.n = n;
        edgeCount = 0;
        head = new int[n + 1];
        Arrays.fill(head, - 1);
        next = new int[2 * n];
        to = new int[2 * n];

        for(int[] edge : edges){
            addEdge(edge[0], edge[1]);
            addEdge(edge[1], edge[0]);
        }
        
        queue = new ArrayDeque<>();
        dist = new int[n + 1];
    }

    private void addEdge(int from, int to){
        this.to[edgeCount] = to;
        next[edgeCount] = head[from];
        head[from] = edgeCount++;
    }

    private int getMaxVal(){
        int v1 = getEndVertex(1);
        int v2 = getEndVertex(v1);
        int diameter = dist[v2];
        
        if(!isDuflicate){
            getEndVertex(v2);
        }
        
        return isDuflicate ? diameter : diameter - 1;
    }
    
    private int getEndVertex(int start){
        Arrays.fill(dist, -1);
        dist[start] = 0;
        queue.offer(start);
        
        int height = -1;
        int endVertex = -1;
            
        while(!queue.isEmpty()){
            int currentV = queue.poll();
            
            for(int edgeIdx = head[currentV]; edgeIdx != -1; edgeIdx = next[edgeIdx]){
                int nextV = to[edgeIdx];
                
                if(dist[nextV] != -1){
                    continue;
                }
                
                dist[nextV] = dist[currentV] + 1;
                queue.offer(nextV);
                
                isDuflicate = height == dist[nextV];
                height = dist[nextV];
                endVertex = nextV;
            }
        }
        
        return endVertex;
    }
}