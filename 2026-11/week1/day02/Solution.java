import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main{
    public static void main(String[] args) throws IOException{
        new Main().solution();
    }

    static final int INF = Integer.MAX_VALUE / 5;

    int n;
    int[][] graph;
    int[][] dp;

    private void solution() throws IOException {
        init();
        dp(0, (1 << n - 1) - 1);
        int answer = dp[0][(1 << n - 1) - 1];

        System.out.println(answer);
    }

    private void init() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine().trim());
        graph = new int[n][n];

        for(int i = 0; i < n; i++){
            StringTokenizer st = new StringTokenizer(br.readLine().trim(), " ");
            for(int j = 0; j < n; j++){
                graph[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        dp = new int[n][1 << (n - 1)];
    }

    private void dp(int visitedNode, int visited){
        if(visited == 0){
            dp[visitedNode][0] = graph[visitedNode][0] == 0 ? INF : graph[visitedNode][0];
            return;
        }

        if(dp[visitedNode][visited] != 0){
            return;
        }

        int minDist = Integer.MAX_VALUE;
        int repeater = visited;

        while(repeater != 0){
            int sel = repeater & -repeater;
            int selNode = Integer.numberOfTrailingZeros(sel) + 1;
            repeater ^= sel;

            dp(selNode, visited ^ sel);
            int visitedNodeToSelNode = graph[visitedNode][selNode] == 0 ? INF : graph[visitedNode][selNode];
            minDist = Math.min(minDist, visitedNodeToSelNode + dp[selNode][visited ^ sel]);
        }

        dp[visitedNode][visited] = minDist;
    }
}