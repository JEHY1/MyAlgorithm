import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Solution {

    public static void main(String[] args) throws IOException{
        new Solution().solution();
    }

    int N;
    int S;
    int[] W;
    int[] Q;

    int[][] dp;

    public void solution() throws IOException{
        init();
        int answer = bottomUp();
        System.out.println(answer);
    }

    private void init() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim(), " ");

        N = Integer.parseInt(st.nextToken());
        S = Integer.parseInt(st.nextToken());
        W = new int[N];
        Q = new int[N];
        dp = new int[N][S + 1];

        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine().trim(), " ");

            W[i] = Integer.parseInt(st.nextToken());
            Q[i] = Integer.parseInt(st.nextToken());

            Arrays.fill(dp[i], -1);
        }
    }

    private int bottomUp(){
        int[] prev = new int[S + 1];
        int[] next = new int[S + 1];

        for(int s = 0; s <= S; s++){
            prev[s] = s / W[0] + (Q[0] == 0 ? 1 : 0);
        }

        for(int i = 1; i < N; i++){
            for(int s = 0; s <= S; s++){
                if(Q[i] * W[i] > s){
                    next[s] = 0;
                    continue;
                }

                next[s] = ((s - W[i] < 0 ? 0 : next[s - W[i]]) + prev[s - Q[i] * W[i]]) % 1000000009;
            }

            int[] temp = prev;
            prev = next;
            next = temp;
        }

        return prev[S];
    }

    private int dp(int range, int limit){
        if(limit < 0){
            return 0;
        }

        if(range == 0){
            return dp[0][limit] = (limit / W[0]) + (Q[0] == 0 ? 1 : 0);
        }

        if(dp[range][limit] != -1){
            return dp[range][limit];
        }

        int use = W[range] * Q[range];

        return dp[range][limit] = dp(range, limit - W[range]) + dp(range - 1, limit - W[range] * Q[range]);
    }
}