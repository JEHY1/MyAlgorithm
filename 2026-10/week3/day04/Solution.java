import java.util.*;

class Solution {
    
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};
    
    int n;
    int[] parent;
    int[] size;
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
        parent = new int[n * n];
        size = new int[n * n];
        this.height = height;
        
        for(int i = 0; i < n * n; i++){
            parent[i] = i;
            size[i] = 1;
        }
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
            union(0, compressP);
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
    
    private int find(int x){
        if(x == parent[x]){
            return x;
        }
        
        return parent[x] = find(parent[x]);
    }
    
    private void union(int a, int b){
        a = find(a);
        b = find(b);
        
        if(a == b){
            return;
        }
        
        if(size[a] < size[b]){
            int temp = a;
            a = b;
            b = temp;
        }
        
        parent[b] = a;
        size[a] += size[b];
    }
}