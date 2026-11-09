import java.util.*;
import java.io.*;

class Solution{
    public static void main(String[] args) throws IOException{
        new Solution().solution();
    }

    int n;
    int[][] nutrient;
    int[] limits;

    public void solution() throws IOException{
        init();
        dfs(1, 0, new int[4], 0);
        if(minCost == Integer.MAX_VALUE){
            System.out.println(-1);
        }
        else{
            System.out.println(minCost);
            printSelMask();    
        }
    }

    private void init() throws IOException{
        minCost = Integer.MAX_VALUE;
        sel = 0;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine().trim());
        nutrient = new int[n + 1][5];
        limits = new int[4];

        StringTokenizer st = new StringTokenizer(br.readLine().trim(), " ");
        for(int i = 0; i < 4; i++){
            limits[i] = Integer.parseInt(st.nextToken());
        }

        for(int i = 1; i <= n; i++){
            st = new StringTokenizer(br.readLine().trim(), " ");
            for(int j = 0; j < 5; j++){
                nutrient[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }

    int minCost;
    int sel;

    private void dfs(int depth, int mask, int[] nutrientSum, int costSum){
        if(limits[0] <= nutrientSum[0] && limits[1] <= nutrientSum[1] && limits[2] <= nutrientSum[2] && limits[3] <= nutrientSum[3]){
            if(minCost > costSum){
                minCost = costSum;
                sel = mask;
            }

            return;
        }

        if(depth > n){
            return;
        }

        for(int i = 0; i < 4; i++){
            nutrientSum[i] += nutrient[depth][i];
        }
        dfs(depth + 1, mask | (1 << depth), nutrientSum, costSum + nutrient[depth][4]);
        for(int i = 0; i < 4; i++){
            nutrientSum[i] -= nutrient[depth][i];
        }

        dfs(depth + 1, mask, nutrientSum, costSum);
    }

    private void printSelMask(){
        StringBuilder sb = new StringBuilder();
        while(sel != 0){
            int selBit = sel & -sel;
            int index = Integer.numberOfTrailingZeros(selBit);
            sel ^= selBit;

            sb.append(index).append(' ');
        }

        System.out.println(sb.toString());
    }
}