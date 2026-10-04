import java.util.*;

class Solution {
    
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};
    
    static final int H = 4;
    static final int W = 4;
    
    int answer;
    int cardCount;
    int[][] cardIdMapper;
    int[][] board;
    Queue<int[]> queue;
    boolean[][][] visited;
    
    public int solution(int[][] board, int r, int c) {        
        init(board);

        int initalMask = (1 << cardCount) - 1;
        int initalHavingCard = 0;
        int initalBehaviorCount = 0;
        int initalHavingCardId = -1;
        
        if(board[r][c] != 0){
            int card = board[r][c];
            initalMask ^= 1 << cardIdMapper[r][c];
            initalHavingCard = card;
            initalHavingCardId = cardIdMapper[r][c];
            initalBehaviorCount = 1;
        }
        
        visited[r][c][initalMask] = true;
        queue.offer(new int[] {r, c, initalMask, initalHavingCard, initalHavingCardId, initalBehaviorCount});
        
        while(!queue.isEmpty()){
            int[] state = queue.poll();
            int cr = state[0];
            int cc = state[1];
            int mask = state[2];
            int havingCard = state[3];
            int havingCardId = state[4];
            int behaviorCount = state[5];
            
            for(int d = 0; d < 4; d++){
                int nr = cr + dr[d];
                int nc = cc + dc[d];
                int point = dash(cr, cc, d, mask, havingCardId);
                int nr2 = point >> 16;
                int nc2 = point & 0xffff;
                
                if(bfsDepth(nr, nc, mask, havingCard, havingCardId, behaviorCount)){
                    return answer;
                }
                if(bfsDepth(nr2, nc2, mask, havingCard, havingCardId, behaviorCount)){
                    return answer;
                }
            }
        }
        
        return answer;
    }

    private void init(int[][] board){
        this.board = board;
        answer = 0;
        this.cardCount = 0;
        cardIdMapper = new int[4][4];

        for(int row = 0; row < 4; row++){
            Arrays.fill(cardIdMapper[row], -1);
        }
        
        for(int row = 0; row < H; row++){
            for(int col = 0; col < W; col++){
                int card = board[row][col];
                if(card != 0){
                    cardIdMapper[row][col] = cardCount++;
                }
            }
        }

        int maskSize = 1 << cardCount;
        visited = new boolean[H][W][maskSize];
        queue = new ArrayDeque<>();
    }
    
    private boolean bfsDepth(int nr, int nc, int mask, int havingCard, int havingCardId, int behaviorCount){
        int nextMask = mask;
        int nextHavingCard = havingCard;
        int nexthavingCardId = havingCardId;
        int nextbehaviorCount = behaviorCount + 1;

        if(nr < 0 || nc < 0 || nr >= H || nc >= W){
            return false;
        }

        if(board[nr][nc] != 0){
            int card = board[nr][nc];
            if(havingCard == card && cardIdMapper[nr][nc] != havingCardId){
                nextHavingCard = 0;
                nexthavingCardId = -1;
                nextbehaviorCount++;
                
                nextMask ^= (1 << cardIdMapper[nr][nc]);
            }
            else if(havingCard == 0){
                if((nextMask & (1 << cardIdMapper[nr][nc])) != 0){
                    nextHavingCard = card;
                    nexthavingCardId = cardIdMapper[nr][nc];
                    nextbehaviorCount++;
                    nextMask ^= (1 << cardIdMapper[nr][nc]);
                }
            }

            if(nextMask == 0){
                answer = nextbehaviorCount;
                return true;
            }
        }

        if(visited[nr][nc][nextMask]){
            return false;
        }

        visited[nr][nc][nextMask] = true;
        queue.offer(new int[] {nr, nc, nextMask, nextHavingCard, nexthavingCardId, nextbehaviorCount});
        
        return false;
    }
    
    private int dash(int r, int c, int d, int mask, int havingCardId){
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
                if(cardIdMapper[r][c] == havingCardId || (mask & (1 << cardIdMapper[r][c])) != 0){
                    return r << 16 | c;
                }
            }
        }
    }
}