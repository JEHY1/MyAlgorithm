import java.util.*;

class Solution {
    
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};
    
    int n;
    int height;
    int[][] land;
    
    public int solution(int[][] land, int height) {
        init(land, height);
        int answer = getMinCost();
        return answer;
    }
    
    private void init(int[][] land, int height){
        this.land = land;
        n = land.length;
        this.height = height;
    }
    
    private int getMinCost(){
        int unionCount = 0;
        int totalCost = 0;
        boolean[] visited = new boolean[n * n];
        Queue<int[]> queue = new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1]));
        
        queue.offer(new int[] {0, 0});
        
        while(!queue.isEmpty()){
            int[] state = queue.poll();
            int compressP = state[0];
            int cr = state[0] / n;
            int cc = state[0] % n;
            int cost = state[1];
            
            if(visited[compressP]){
                continue;
            }
            
            totalCost += cost;
            visited[compressP] = true;
            unionCount++;

            if(unionCount == n * n){
                return totalCost;
            }
            
            for(int d = 0; d < 4; d++){
                int nr = cr + dr[d];
                int nc = cc + dc[d];
                int nextCompressP = nr * n + nc;
                
                if(nr < 0 || nc < 0 || nr >= n || nc >= n){
                    continue;
                }
                
                if(visited[nextCompressP]){
                    continue;
                }
                
                int diff = Math.abs(land[nr][nc] - land[cr][cc]);
                int nextCost = diff > height ? diff : 0;
                            
                queue.offer(new int[] {nextCompressP, nextCost});
            }
        }
        
        return -1;
    } 
}