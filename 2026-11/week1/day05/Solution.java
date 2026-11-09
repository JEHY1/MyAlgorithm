import java.io.*;

class Solution{
    public static void main(String[] args) throws IOException{
        new Solution().solution();
    }

    int n;
    int filter;

    public void solution() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine().trim());
        filter = (1 << n) - 1;
        int answer = dfs(0, 0, 0, 0);
        System.out.println(answer);
    }

    private int dfs(int row, int columnMask, int leftDiagonalMask, int rightDiagonalMask){
        if(row == n){
            return 1;
        }

        int repeater = (reverseAndFilterMask(columnMask) & reverseAndFilterMask(leftDiagonalMask) & reverseAndFilterMask(rightDiagonalMask));

        int count = 0;
        while(repeater != 0){
            int sel = repeater & -repeater;
            repeater ^= sel;

            count += dfs(row + 1, columnMask ^ sel, (leftDiagonalMask ^ sel) << 1, (rightDiagonalMask ^ sel) >> 1);
        }

        return count;
    }

    private int reverseAndFilterMask(int mask){
        return ~mask & filter;
    }
}