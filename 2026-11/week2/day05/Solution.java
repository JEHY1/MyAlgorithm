import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Solution{
    public static void main(String[] args) throws IOException {
        new Main().solution();
    }

    int[][] board;
    int[] rowMask;
    int[] columnMask;
    int[] bucketMask;
    private static final int FILTER = (1 << 10) - 2;

    public void solution() throws IOException {
        init();
        dfs(0);
        printBoard();
    }

    private void init() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        board = new int[9][9];
        rowMask = new int[9];
        columnMask = new int[9];
        bucketMask = new int[9];

        for(int r = 0; r < 9; r++){
            String input = br.readLine().trim();

            for(int c = 0; c < 9; c++){
                board[r][c] = input.charAt(c * 2) - '0';
                int num = board[r][c];
                rowMask[r] ^= (1 << num);
                columnMask[c] ^= (1 << num);
                int bucketIndex = (r / 3) * 3 + (c / 3);
                bucketMask[bucketIndex] ^= (1 << num);
            }
        }
    }

    private boolean dfs(int depth){
        if(depth == 81){
            return true;
        }

        int row = depth / 9;
        int col = depth % 9;
        int bucketIndex = (row / 3) * 3 + (col / 3);

        if(board[row][col] == 0){
            int mask = rowMask[row] | columnMask[col] | bucketMask[bucketIndex];
            int repeater = ~mask & FILTER;

            while(repeater != 0) {
                int sel = repeater & -repeater;
                int selNum = Integer.numberOfTrailingZeros(sel);
                repeater ^= sel;

                int prevRowMask = rowMask[row];
                int prevColMask = columnMask[col];
                int prevBucketMask = bucketMask[bucketIndex];

                rowMask[row] |= sel;
                columnMask[col] |= sel;
                bucketMask[bucketIndex] |= sel;
                board[row][col] = selNum;

                if(dfs(depth + 1)){
                    return true;
                }

                rowMask[row] = prevRowMask;
                columnMask[col] = prevColMask;
                bucketMask[bucketIndex] = prevBucketMask;
                board[row][col] = 0;
            }
        }
        else{
            return dfs(depth + 1);
        }

        return false;
    }

    private void printBoard() {
        StringBuilder sb = new StringBuilder();

        for(int r = 0; r < 9; r++){
            for(int c = 0; c < 9; c++){
                sb.append(board[r][c]).append(' ');
            }
            sb.append('\n');
        }

        System.out.println(sb.toString());
    }
}