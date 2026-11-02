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


//조직사각형
// import java.io.BufferedReader;
// import java.io.IOException;
// import java.io.InputStreamReader;
// import java.math.BigInteger;
// import java.util.PriorityQueue;
// import java.util.Queue;
// import java.util.StringTokenizer;

// public class Main{
//     public static void main(String[] args) throws IOException{
//         new Main().solution();
//     }
//     BigInteger[] line;
//     int N;
//     int K;

//     Queue<Integer>[] queue;

//     private void solution() throws IOException{
//         init();
//         String answer = getPermutation();
//         System.out.println(answer);
//     }

//     private void init() throws IOException{
//         BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//         StringTokenizer st = new StringTokenizer(br.readLine().trim(), " ");
//         N = Integer.parseInt(st.nextToken());
//         K = Integer.parseInt(st.nextToken());
//         line = new BigInteger[4];

//         st = new StringTokenizer(br.readLine().trim(), " ");
//         queue = new PriorityQueue[4];

//         for(int i = 0; i < 4; i++){
//             line[i] = BigInteger.valueOf(Long.parseLong(st.nextToken()));
//             queue[i] = new PriorityQueue<>((a, b) -> a > b ? -1 : 1);
//         }

//         for(int i = 0; i < N; i++){
//             st = new StringTokenizer(br.readLine().trim(), " ");
//             int card = (int)st.nextToken().charAt(0) - 65;
//             int value = Integer.parseInt(st.nextToken());

//             queue[card].offer(value);
//         }
//     }

//     private String getPermutation(){
//         StringBuilder sb = new StringBuilder();

//         for(int i = 0; i < K; i++){
//             BigInteger maxVal = BigInteger.ZERO;
//             int selCard = -1;
//             int selValue = -1;

//             for(int j = 0; j < 4; j++){
//                 if(!queue[j].isEmpty()){
//                     int val = queue[j].peek();
//                     line[j] = line[j].add(BigInteger.valueOf(val));

//                     BigInteger result = line[0].multiply(line[1]).multiply(line[2]).multiply(line[3]);
//                     if(result.compareTo(maxVal) > 0){
//                         maxVal = result;
//                         selCard = j;
//                         selValue = val;
//                     }

//                     line[j] = line[j].subtract(BigInteger.valueOf(val));
//                 }
//             }

//             queue[selCard].poll();
//             line[selCard] = line[selCard].add(BigInteger.valueOf(selValue));
//             sb.append((char)(selCard + 65)).append(' ').append(selValue).append('\n');
//         }

//         return sb.toString();
//     }
// }