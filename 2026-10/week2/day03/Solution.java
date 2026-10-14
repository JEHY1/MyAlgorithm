import java.util.*;

class Solution {
    
    static final int SHEEP = 0;
    static final int WOLF = 1;
    
    int V;
    int E;
    
    int edgeCount;
    int[] head;
    int[] next;
    int[] to;    
    int[] info;
    
    public int solution(int[] info, int[][] edges) {
        init(info, edges);
        
        int answer = getMaxCount();
        System.out.println(answer);
        
        return answer;
    }
    
    private void init(int[] info, int[][] edges){
        this.info = info;
        V = info.length;
        E = edges.length;
        
        edgeCount = 0;
        head = new int[V];
        Arrays.fill(head, -1);
        next = new int[E];
        to = new int[E];
        
        for(int[] edge : edges){
            addEdge(edge[0], edge[1]);
        }
    }
    
    private void addEdge(int from, int to){
        this.to[edgeCount] = to;
        next[edgeCount] = head[from];
        head[from] = edgeCount++;
    }
    
    private int getMaxCount(){
        int maxCount = 1;
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{0, 0, 1, 0});
        
        while(!queue.isEmpty()){
            int[] state = queue.poll();
            int currentV = state[0];
            int mask = state[1];
            int sheepCount = state[2];
            int wolfCount = state[3];
            
            for(int edgeIdx = head[currentV]; edgeIdx != -1; edgeIdx = next[edgeIdx]){
                int nextV = to[edgeIdx];
                mask ^= (1 << nextV);
            }
            
            int repeatMask = mask;
            while(repeatMask != 0){
                int sel = repeatMask & -repeatMask;
                repeatMask ^= sel;
                int nextV = Integer.numberOfTrailingZeros(sel);
                int animal = info[nextV];
                int nextMask = mask ^ sel;                               
                
                if(animal == SHEEP){                	
                    maxCount = Math.max(maxCount, sheepCount + 1);
                    queue.offer(new int[] {nextV, nextMask, sheepCount + 1, wolfCount});
                }
                else if(animal == WOLF){
                    if(sheepCount - 1 > wolfCount){
                    	queue.offer(new int[] {nextV, nextMask, sheepCount, wolfCount + 1});
                    }
                }                
            }
        }
        
        return maxCount;
    }
}