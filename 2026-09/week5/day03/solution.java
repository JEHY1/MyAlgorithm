import java.util.*;

class Solution {
    
    static final int CARD_FAIR = 7;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};
    
    static final int H = 4;
    static final int W = 4;
    
    int answer;
    
    public int solution(int[][] board, int r, int c) {        
        answer = 0;
        
        int cardCount = 0;
        int[] cardMapper = new int[14];
        Arrays.fill(cardMapper, -1);
        
        for(int row = 0; row < H; row++){
            for(int col = 0; col < W; col++){
                int card = board[row][col];
                if(card != 0){
                    if(cardMapper[card] == -1){
                        cardMapper[card] = cardCount++;
                    }
                    else{
                        cardMapper[card + CARD_FAIR] = cardCount++;
                        board[row][col] += CARD_FAIR;
                    }
                }
            }
        }
        
        int resetMask = (1 << cardCount) - 1;
        int resetHavingCard = 0;
        int resetBehavierCount = 0;
        boolean[][][] visited = new boolean[H][W][resetMask + 1];
        Queue<int[]> queue = new ArrayDeque<>();
        
        if(board[r][c] != 0){
            int card = board[r][c];
            resetMask ^= 1 << cardMapper[card];
            resetHavingCard = card;
            resetBehavierCount = 1;
            answer++;
        }
        
        visited[r][c][resetMask] = true;
        queue.offer(new int[] {r, c, resetMask, resetHavingCard, resetBehavierCount});
        
        while(!queue.isEmpty()){
            int[] state = queue.poll();
            int cr = state[0];
            int cc = state[1];
            int mask = state[2];
            int havingCard = state[3];
            int behavierCount = state[4];
            
            for(int d = 0; d < 4; d++){
                int nr = cr + dr[d];
                int nc = cc + dc[d];
                int point = dash(board, cr, cc, d, mask, havingCard, cardMapper);
                int nr2 = point >> 16;
                int nc2 = point & 0xffff;
                
                if(bfsDepth(board, queue, visited, nr, nc, mask, havingCard, behavierCount, cardMapper)){
                    return answer;
                }
                if(bfsDepth(board, queue, visited, nr2, nc2, mask, havingCard, behavierCount, cardMapper)){
                    return answer;
                }
            }
        }
        
        return answer;
    }
    
    private boolean bfsDepth(int[][] board, Queue<int[]> queue, boolean[][][] visited, int nr, int nc, int mask, int havingCard, int behavierCount, int[] mapper){
        int nextMask = mask;
        int nextHavingCard = havingCard;
        int nextBehavierCount = behavierCount + 1;

        if(nr < 0 || nc < 0 || nr >= H || nc >= W){
            return false;
        }

        if(board[nr][nc] != 0){
            int card = board[nr][nc];
            if(havingCard != card && havingCard % CARD_FAIR == card % CARD_FAIR){
                nextHavingCard = 0;
                nextBehavierCount++;
                
                nextMask ^= (1 << mapper[card]);
            }
            else if(havingCard == 0){
                if((nextMask & (1 << mapper[card])) != 0){
                    nextHavingCard = card;
                    nextBehavierCount++;
                    nextMask ^= (1 << mapper[card]);
                }
            }

            if(nextMask == 0){
                answer = nextBehavierCount;
                return true;
            }
        }

        if(visited[nr][nc][nextMask]){
            return false;
        }

        visited[nr][nc][nextMask] = true;
        queue.offer(new int[] {nr, nc, nextMask, nextHavingCard, nextBehavierCount});
        
        return false;
    }
    
    private int dash(int[][] board, int r, int c, int d, int mask, int havingCard, int[] mapper){
        while(true){
            r += dr[d];
            c += dc[d];
            
            if(r < 0 || c < 0 || r >= H || c >= W){
                r -= dr[d];
                c -= dc[d];
                return r << 16 | c;
            }
            
            int card = board[r][c];
            
            if(card != 0){
                if(card == havingCard || (mask & (1 << mapper[card])) != 0)
                return r << 16 | c;
            }
        }
    }
}