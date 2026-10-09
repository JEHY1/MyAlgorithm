import java.util.*;

class Solution {

    int n;
    int edgeCount;
    int[] head;
    int[] next;
    int[] to;

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
    }

    private void addEdge(int from, int to){
        this.to[edgeCount] = to;
        next[edgeCount] = head[from];
        head[from] = edgeCount++;
    }

    private int getMaxVal(){
        boolean[] visited = new boolean[n + 1];
        Queue<Integer> queue = new ArrayDeque<>();
        
        visited[1] = true;
        queue.offer(1);
        int start = -1;
        
        while(!queue.isEmpty()){
            int currentV = queue.poll();
            
            for(int edgeIdx = head[currentV]; edgeIdx != -1; edgeIdx = next[edgeIdx]){
                int nextV = to[edgeIdx];
                if(visited[nextV]){
                    continue;
                }
                
                visited[nextV] = true;
                queue.offer(nextV);
                start = nextV;
            }
        }
        
        Queue<int[]> queue2 = new ArrayDeque<>();
        Arrays.fill(visited, false);
        
        visited[start] = true;
        queue2.offer(new int[] {start, 0});
        
        boolean isDuflicate = false;
        int diameter = 0;
        
        while(!queue2.isEmpty()){
            int[] state = queue2.poll();
            int currentV = state[0];
            int dist = state[1];
            
            for(int edgeIdx = head[currentV]; edgeIdx != -1; edgeIdx = next[edgeIdx]){
                int nextV = to[edgeIdx];
                
                if(visited[nextV]){
                    continue;
                }
                
                visited[nextV] = true;
                isDuflicate = diameter == dist + 1;
                diameter = dist + 1;
                
                start = nextV;
                queue2.offer(new int[] {nextV, dist + 1});
            }
        }
        
        if(!isDuflicate){
            Arrays.fill(visited, false);
            queue2.clear();
            
            visited[start] = true;
            queue2.offer(new int[] {start, 0});
            
            while(!queue2.isEmpty()){
                int[] state = queue2.poll();
                int currentV = state[0];
                int dist = state[1];

                for(int edgeIdx = head[currentV]; edgeIdx != -1; edgeIdx = next[edgeIdx]){
                    int nextV = to[edgeIdx];

                    if(visited[nextV]){
                        continue;
                    }

                    visited[nextV] = true;
                    isDuflicate = diameter == dist + 1;
                    diameter = dist + 1;

                    start = nextV;
                    queue2.offer(new int[] {nextV, dist + 1});
                }
            }
        }
        
        return isDuflicate ? diameter : diameter - 1;
    }
}