import java.util.*;

class Solution {
    
    static final int MAX_BLOCK_COUNT = 200;
    
    int N;
    int[][] board;
    
    boolean[] initialBlockExists;
    int[] blockMaxRow;
    int[] blockMinCol;
    int[] blockMaxCol;
    int[] blockMinRow;
    
    int[] next;
    int[] prev;
    int head;
    
    public int solution(int[][] board) {
        init(board);
        
        int answer = getValidBlockCount();
        return answer;
    }

    private void init(int[][] board){        
        next = new int[MAX_BLOCK_COUNT + 1];
        prev = new int[MAX_BLOCK_COUNT + 1];
        head = -1;
        
        N = board.length;
        this.board = board;
        initialBlockExists = new boolean[MAX_BLOCK_COUNT + 1];
        blockMaxRow = new int[MAX_BLOCK_COUNT + 1];
        blockMinCol = new int[MAX_BLOCK_COUNT + 1];
        blockMaxCol = new int[MAX_BLOCK_COUNT + 1];
        blockMinRow = new int[MAX_BLOCK_COUNT + 1];
        Arrays.fill(blockMinCol, Integer.MAX_VALUE);
        Arrays.fill(blockMaxCol, -1);
        Arrays.fill(blockMinRow, Integer.MAX_VALUE);
        
        for(int r = 0; r < N; r++){
            for(int c = 0; c < N; c++){
                if(board[r][c] != 0){
                    int blockNum = board[r][c];
                    if(!initialBlockExists[blockNum]){
                        addBlock(blockNum);
                    }
                    
                    initialBlockExists[blockNum] = true;
                    
                    blockMinRow[blockNum] = Math.min(blockMinRow[blockNum], r);
                    blockMaxRow[blockNum] = r;
                    blockMinCol[blockNum] = Math.min(blockMinCol[blockNum], c);
                    blockMaxCol[blockNum] = Math.max(blockMaxCol[blockNum], c);                         
                }
            }
        }
    }
    
    private void addBlock(int blockNum){
        int firstBlock = head;                        
        next[blockNum] = firstBlock;   
        head = blockNum;
        prev[blockNum] = -1;
        
        if(firstBlock != -1){
            prev[firstBlock] = blockNum;
        }    
    }
    
    private void unlinkBlock(int blockNum){
        int nextBlock = next[blockNum];
        int prevBlock = prev[blockNum];
        
        if(nextBlock != -1){
            prev[nextBlock] = prevBlock;
        }
        
        if(prevBlock != -1){
            next[prevBlock] = nextBlock;
        }
        else{
            head = nextBlock;
        }
    }
    
    private int getValidBlockCount(){
        int validBlockCount = 0;
        
        int blockNum = head;
        
        while(blockNum != -1){
            if(checkBlockValid(blockNum)){                
                unlinkBlock(blockNum);
                removeBlock(blockNum);
                validBlockCount++;           
                blockNum = head;
            }
            else{
                blockNum = next[blockNum];
            }
        }
        
        return validBlockCount;
    }
    
    private boolean checkBlockValid(int blockNum){
        int r1 = blockMaxRow[blockNum];
        int c1 = blockMinCol[blockNum];
        int c2 = blockMaxCol[blockNum];
        int r2 = blockMinRow[blockNum];
        
        for(int c = c1; c <= c2; c++){
            for(int r = r1; r >= r2; r--){
                if(board[r][c] == 0){
                    if(!isValidColumn(r, c)){
                        return false;
                    }
                    
                    break;
                }
                else if(board[r][c] != blockNum){
                    return false;
                }
            }
            
        }
        
        return true;
    }
    
    private void removeBlock(int blockNum){
        int startR = blockMaxRow[blockNum];
        int startC = blockMinCol[blockNum];
        int endC = blockMaxCol[blockNum];
        int endR = blockMinRow[blockNum];
        
        for(int r = startR; r >= endR; r--){
            for(int c = startC; c <= endC; c++){
                board[r][c] = 0;
            }
        }
    }
    
    private boolean isValidColumn(int r, int c){
        for(int row = r - 1; row >= 0; row--){
            if(board[row][c] != 0){
                return false;
            }
        }
        
        return true;
    }
}