import java.util.*;

class Solution {
    public int solution(int[][] game_board, int[][] table) {
        init(game_board, table);
        int answer = getMax();
        return answer;
    }
    
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};
    
    int n;
    Queue<Set<Long>> blank;
    Queue<Set<Long>> block;
    
    private void init(int[][] game_board, int[][] table){
        n = game_board.length;
        blank = getNomalizationPieces(game_board, 0);
        block = getNomalizationPieces(table, 1);
    }
    
    private int getMax(){
        int answer = 0;
        while(!block.isEmpty()){
            Set<Long> nomalizationBlock = block.poll();
            
            tar:for(int i = 0; i < blank.size(); i++){
                Set<Long> nomalizationBlank = blank.poll();
                if(nomalizationBlank.size() != nomalizationBlock.size()){
                    blank.offer(nomalizationBlank);
                    continue;
                }
                
                for(int cnt = 0; cnt < 4; cnt++){
                    if(nomalizationBlank.size() == nomalizationBlock.size() && nomalizationBlank.containsAll(nomalizationBlock)){
                        answer += nomalizationBlock.size();
                        break tar;
                    }
                    
                    rotate(nomalizationBlock);
                }
                
                blank.offer(nomalizationBlank);
            }
        }
        
        return answer;
    }
    
    private Queue<Set<Long>> getNomalizationPieces(int[][] board, int val){
        Queue<int[]> queue = new ArrayDeque<>();
        boolean[][] visited = new boolean[n][n];
        Queue<Set<Long>> pieceQueue = new ArrayDeque<>();
        
        for(int r = 0; r < n; r++){
            for(int c = 0; c < n; c++){
                if(visited[r][c]){
                    continue;
                }
                
                if(board[r][c] != val){
                    continue;
                }
                
                Set<Long> nomalizationPiece = new HashSet<>();
                
                queue.offer(new int[] {r, c});
                visited[r][c] = true;
                
                while(!queue.isEmpty()){
                    int[] state = queue.poll();
                    int cr = state[0];
                    int cc = state[1];
                    
                    long key = ((long)(cr - r) << 32) | ((long)(cc - c) & 0xffffffffL);
                    nomalizationPiece.add(key);                    
                    
                    for(int d = 0; d < 4; d++){
                        int nr = cr + dr[d];
                        int nc = cc + dc[d];
                        
                        if(nr < 0 || nc < 0 || nr >= n || nc >= n){
                            continue;
                        }                        
                        
                        if(visited[nr][nc]){
                            continue;
                        }
                        
                        if(board[nr][nc] != val){
                            continue;
                        }

                        visited[nr][nc] = true;
                        queue.offer(new int[] {nr, nc});
                    }
                }
                    
                pieceQueue.offer(nomalizationPiece);
            }
        }
        
        return pieceQueue;
    }
    
    private void rotate(Set<Long> nomalizationPiece){
        int offsetR = 0;
        int offsetC = 0;
        
        List<int[]> tempList = new ArrayList<>();
        Iterator<Long> iter = nomalizationPiece.iterator();
        
        while(!nomalizationPiece.isEmpty()){

            long key = iter.next();
            iter.remove();
            
            int r = (int)(key >> 32);
            int c = (int)key;
            int temp = r;
            r = c;
            c = -temp;
            
            if(r < offsetR){
                offsetR = r;
                offsetC = c;
            }
            else if(r == offsetR){
                offsetC = Math.min(offsetC, c);
            }
            
            tempList.add(new int[]{r, c});            
        }
        
        for(int[] p : tempList){
            int r = p[0] - offsetR;
            int c = p[1] - offsetC;
            long key = ((long)r << 32) | ((long)c & 0xffffffffL);
            
            nomalizationPiece.add(key);
        }
    }
}