import java.util.*;

class Solution {

    int[][] matrix_sizes;
    int[][] dp;
    int n;

    public int solution(int[][] matrix_sizes) {
        init(matrix_sizes);
        return dp(0, n - 1);
    }

    private void init(int[][] matrix_sizes) {
        this.matrix_sizes = matrix_sizes;
        n = matrix_sizes.length;
        dp = new int[n][n];

        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], -1);
        }
    }

    private int dp(int start, int end) {
        if (start == end) {
            return 0;
        }

        if (dp[start][end] != -1) {
            return dp[start][end];
        }

        int minValue = Integer.MAX_VALUE;

        for (int mid = start; mid < end; mid++) {
            int frontValue = dp(start, mid);
            int backValue = dp(mid + 1, end);
            int cost = frontValue + backValue + matrix_sizes[start][0] * matrix_sizes[mid][1] * matrix_sizes[end][1];
            minValue = Math.min(minValue, cost);
        }

        return dp[start][end] = minValue;
    }
}