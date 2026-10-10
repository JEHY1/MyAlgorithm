import java.util.*;

class Solution {
    int n;
    int[][] graph;
    
    public int solution(int n, int[][] costs){
        init(n, costs);
        
        return getMinDist();
    }
    
    private void init(int n, int[][] costs){
        this.n = n;
        graph = new int[n][n];
        
        for(int[] cost : costs){
            int v1 = cost[0];
            int v2 = cost[1];
            int weight = cost[2];
            
            graph[v1][v2] = weight;
            graph[v2][v1] = weight;
        }
    }
    
    private int getMinDist(){
        int[] minDistArr = new int[n];
        Arrays.fill(minDistArr, Integer.MAX_VALUE);
        
        minDistArr[0] = 0;
        int totalDist = 0;
        
        for(int size = 0; size < n; size++){
            int minDist = Integer.MAX_VALUE;
            int selV = -1;
            
            for(int v = 0; v < n; v++){
                if(minDistArr[v] != -1 && minDistArr[v] < minDist){
                    selV = v;
                    minDist = minDistArr[v];
                }
            }
            
            totalDist += minDist;
            minDistArr[selV] = -1;
            
            for(int v = 0; v < n; v++){
                if(minDistArr[v] == -1 || graph[selV][v] == 0){
                    continue;
                }
                
                minDistArr[v] = Math.min(minDistArr[v], graph[selV][v]);
            }
        }
        
        return totalDist;
    }
    
    
//     int answer;
//     int[] parent;
//     Queue<int[]>edges;
//     List<int[]>[] graph;
//     boolean[] visited;
    
//     public int solution(int n, int[][] costs) {
//         init(n, costs);
        
//         visited[0] = true;
//         int visitedCount = 1;
//         edges.addAll(graph[0]);
        
//         while(!edges.isEmpty() && visitedCount != n){
//             int[] edge = edges.poll();
//             int to = edge[0];
//             int weigth = edge[1];
            
//             if(!visited[to]){
//                 visited[to] = true;
//                 edges.addAll(graph[to]);
//                 answer += weigth;
//             }
//         }

//         return answer;
//     }

//     private void init(int n, int[][] costs){
//         answer = 0;
//         visited = new boolean[n];
//         edges = new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1]));
//         graph = new ArrayList[n];
//         for(int i = 0; i < n; i++){
//             graph[i] = new ArrayList<>();
//         }
        
//         for(int[] cost : costs){
//             int v1 = cost[0];
//             int v2 = cost[1];
//             int weight = cost[2];
            
//             graph[v1].add(new int[] {v2, weight});
//             graph[v2].add(new int[] {v1, weight});
//         }
//     }
}