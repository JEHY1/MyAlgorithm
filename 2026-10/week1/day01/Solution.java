import java.util.*;

class Solution {
    
    int answer;
    int[] parent;
    Queue<int[]>edges;
    List<int[]>[] graph;
    boolean[] visited;
    
    public int solution(int n, int[][] costs) {
        init(n, costs);
        
        visited[0] = true;
        int visitedCount = 1;
        edges.addAll(graph[0]);
        
        while(!edges.isEmpty() && visitedCount != n){
            int[] edge = edges.poll();
            int to = edge[1];
            int weigth = edge[2];
            
            if(!visited[to]){
                visited[to] = true;
                edges.addAll(graph[to]);
                answer += weigth;
            }
        }

        return answer;
    }

    private void init(int n, int[][] costs){
        answer = 0;
        visited = new boolean[n];
        edges = new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1]));
        graph = new ArrayList[n];
        for(int i = 0; i < n; i++){
            graph[i] = new ArrayList<>();
        }
        
        for(int[] cost : costs){
            int v1 = cost[0];
            int v2 = cost[1];
            int weight = cost[2];
            
            graph[v1].add(new int[] {v2, weight});
            graph[v2].add(new int[] {v1, weight});
        }
    }
}