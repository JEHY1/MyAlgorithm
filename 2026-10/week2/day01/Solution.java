import java.util.*;

class Solution {
    
    int V;
    int E;
    
    int edgeCount;
    int[] head;
    int[] next;
    int[] to;
    int[] nodeVal;
    
    public long solution(int[] a, int[][] edges) {
        init(a, edges);

        return getMinCalcCount();
    }
    
    private long getMinCalcCount(){
        
        int stackCount = 0;
        int[] stack = new int[V];
        stack[stackCount++] = 0;
        
        int orderCount = 0;
        int[] order = new int[V];
        order[orderCount++] = 0;
        
        int[] parent = new int[V];
        Arrays.fill(parent, -1);
        parent[0] = 0;
        
        //순서 정하기
        while(stackCount > 0){
            int currentV = stack[--stackCount];
            for(int edgeIdx = head[currentV]; edgeIdx != -1; edgeIdx = next[edgeIdx]){
                int nextV = to[edgeIdx];
                
                if(parent[nextV] != -1){
                    continue;
                }
                
                parent[nextV] = currentV;
                order[orderCount++] = nextV;
                stack[stackCount++] = nextV;
            }
        }
        
        long[] vSum = new long[V];
        vSum[0] = nodeVal[0];
        long calcCount = 0;
        
        for(int orderIdx = orderCount - 1; orderIdx > 0; orderIdx--){
            int currentV = order[orderIdx];
            vSum[currentV] += nodeVal[currentV];
            calcCount += Math.abs(vSum[currentV]);
            vSum[parent[currentV]] += vSum[currentV];
        }
        
        return vSum[0] == 0 ? calcCount : -1;
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
    }
    
    private void addEdge(int from, int to){
        this.to[edgeCount] = to;
        next[edgeCount] = head[from];
        head[from] = edgeCount++;
    }
}