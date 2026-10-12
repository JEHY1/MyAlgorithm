import java.util.*;

class Solution {
    
    int V;
    int E;
    
    int edgeCount;
    int[] head;
    int[] next;
    int[] to;
    int[] nodeVal;
    
    boolean[] visited;
    long totalCalcCount;

    public long solution(int[] a, int[][] edges) {
        init(a, edges);

        return getMinCalcCount() == 0 ? totalCalcCount : -1;
    }
    
    private long getMinCalcCount(){
        visited[0] = true;
        return dfs(0);
    }
        
    private long dfs(int currentV){
        long returnVal = nodeVal[currentV];
        
        for(int edgeIdx = head[currentV]; edgeIdx != -1; edgeIdx = next[edgeIdx]){
            int nextV = to[edgeIdx];
            
            if(visited[nextV]){
                continue;
            }
            
            visited[nextV] = true;
            
            long val = dfs(nextV);
            totalCalcCount += Math.abs(val);
            returnVal += val;
        }

        return returnVal;
    }
    
    private void init(int[] a, int[][] edges){
        V = a.length;
        E = edges.length;
        
        edgeCount = 0;
        head = new int[V];
        Arrays.fill(head, -1);
        next = new int[2 * E];
        to = new int[2 * E];
        nodeVal = a;
        
        for(int[] edge : edges){
            addEdge(edge[0], edge[1]);
            addEdge(edge[1], edge[0]);
        }
        
        visited = new boolean[V];   
        totalCalcCount = 0;
    }
    
    private void addEdge(int from, int to){
        this.to[edgeCount] = to;
        next[edgeCount] = head[from];
        head[from] = edgeCount++;
    }
}