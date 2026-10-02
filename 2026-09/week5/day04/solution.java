
class Solution {

    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    int[][] board;
    int W;
    int H;

    public int solution(int[][] board, int[] aloc, int[] bloc) {
        this.H = board.length;
        this.W = board[0].length;
        this.board = board;

        int[] answer = dfs(aloc[0], aloc[1], bloc[0], bloc[1], 0);
        return answer[1];
    }

    private int[] dfs(int r0, int c0, int r1, int c1, int turn){

        int player = turn % 2;
        int r = player == 0 ? r0 : r1;
        int c = player == 0 ? c0 : c1;

        if(board[r][c] == 0){
            return new int[] {player, turn};
        }

        boolean canWin = false;
        int validTurn = 0;
        int selDir = -1;

        for(int d = 0; d < 4; d++){
            int nr = r + dr[d];
            int nc = c + dc[d];
            int[] dfsReturn;

            if(nr < 0 || nc < 0 || nr >= H || nc >= W){
                continue;
            }
            if(board[nr][nc] == 0){
                continue;
            }

            board[r][c] = 0;

            if(player == 0){
                dfsReturn = dfs(nr, nc, r1, c1, turn + 1);
            }
            else{
                dfsReturn = dfs(r0, c0, nr, nc, turn + 1);
            }

            int loser = dfsReturn[0];
            int useTurn = dfsReturn[1];

            if(loser != player){
                if(!canWin){
                    canWin = true;
                    validTurn = useTurn;
                    selDir = d;
                }
                else{
                    if(useTurn < validTurn){
                        validTurn = useTurn;
                        selDir = d;
                    }
                }
            }
            else{
                if(!canWin){
                    if(useTurn > validTurn){
                        validTurn = useTurn;
                        selDir = d;
                    }
                }
            }
        }

        //갈 수 있는곳이 없는 경우
        if(selDir == -1){
            return new int[] {player, turn};
        }

        board[r][c] = 1;

        return new int[] {canWin ? player ^ 1 : player, validTurn};
    }
}