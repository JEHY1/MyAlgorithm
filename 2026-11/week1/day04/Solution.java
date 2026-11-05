        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStreamReader;

        public class Solution{
            public static void main(String[] args) throws IOException{
                new Solution().solution();
            }

            int n;
            boolean[][] board;
            boolean[] diagonalVisited;
            int maxCount;

            private void solution() throws IOException{
                init();
                int answer = dfs(0, 0);
                System.out.println(answer);
            }

            private void init() throws IOException{
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                n = Integer.parseInt(br.readLine().trim());

                board = new boolean[n][n];

                for(int i = 0; i < n; i++){
                    String input = br.readLine().trim();

                    for(int j = 0; j < n; j++){
                        board[i][j] = input.charAt(2 * j) == '1';
                    }
                }

                diagonalVisited = new boolean[2 * n];
                maxCount = 0;
            }

            private int dfs(int depth, int selCount){
                if(depth == 2 * n - 1){
                    maxCount = selCount;
                    return selCount;
                }

                if(maxCount >= selCount + (2 * n - 1 - depth)){
                    return -1;
                }

                int startR = depth < n ? n - depth - 1 : 0;
                int startC = depth < n ? 0 : depth - n + 1;

                int maxCount = dfs(depth + 1, selCount);

                for(int i = 0; i < n; i++){
                    int nr = startR + i;
                    int nc = startC + i;

                    if(nr >= n || nc >= n){
                        break;
                    }

                    if(!board[nr][nc]){
                        continue;
                    }

                    if(diagonalVisited[nr + nc]){
                        continue;
                    }

                    diagonalVisited[nr + nc] = true;
                    maxCount = Math.max(maxCount, dfs(depth + 1, selCount + 1));
                    diagonalVisited[nr + nc] = false;
                }

                return maxCount;
            }
        }