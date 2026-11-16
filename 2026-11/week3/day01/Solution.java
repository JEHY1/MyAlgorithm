import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

class Solution{
    public static void main(String[] args) throws IOException {
        new Solution().solution();
    }

    static final int OFFSET = 1;
    int N;
    int initialState;
    int maxVisitCount;
    int[] order;
    int[][] dp;

    public void solution() throws IOException {
        init();
        int answer = dp(0, initialState);
        System.out.println(answer);
    }

    private void init() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine().trim());
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        initialState = (1 << (a - OFFSET)) | (1 << (b - OFFSET));

        maxVisitCount = Integer.parseInt(br.readLine().trim());
        order = new int[maxVisitCount];
        for(int i = 0; i < maxVisitCount; i++){
            order[i] = Integer.parseInt(br.readLine().trim());
        }

        dp = new int[maxVisitCount + 1][1 << N];
        for(int i = 0; i <= maxVisitCount; i++){
            Arrays.fill(dp[i], -1);
        }
    }


    private int dp(int depth, int stateMask) {
        if (depth == maxVisitCount) {
            return 0;
        }

        if (dp[depth][stateMask] != -1) {
            return dp[depth][stateMask];
        }

        int currentSel = order[depth];
        int targetBit = 1 << (currentSel - 1);

        int minDist = Integer.MAX_VALUE;

        for (int bits = stateMask; bits != 0; bits &= bits - 1) {
            int bit = bits & -bits;

            int currentPos = Integer.numberOfTrailingZeros(bit) + 1;

            int nextState = (stateMask ^ bit) | targetBit;

            int moveCost = Math.abs(currentPos - currentSel);

            minDist = Math.min(minDist, moveCost + dp(depth + 1, nextState));
        }

        return dp[depth][stateMask] = minDist;
    }
}