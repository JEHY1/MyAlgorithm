import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution{
    public static void main(String[] args) throws IOException{
        new Solution().solution();
    }

    int N;
    int[] board;

    public void solution() throws IOException{
        init();
        int answer = combi((1 << N) - 1, 0);
        System.out.println(answer);
    }

    private int combi(int mask, int row){
        if(row == N){
            return 1;
        }

        int repeater = mask & board[row];
        int count = 0;

        while(repeater != 0){
            int sel = repeater & -repeater;
            repeater ^= sel;
            count += combi(mask ^ sel, row + 1);
        }

        return count;
    }

    private void init() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine().trim());
        board = new int[N];

        for(int r = 0; r < N; r++){
            String input = br.readLine().trim();
            int line = 0;
            for(int c = 0; c < N; c++){
                line <<= 1;
                if(input.charAt(c) == '.'){
                    line |= 1;
                }
            }
            board[r] = line;
        }
    }
}