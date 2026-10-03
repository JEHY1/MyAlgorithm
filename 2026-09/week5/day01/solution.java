import java.util.*;

class Solution {
    
    ArrayDeque<Integer> topRow;
    ArrayDeque<Integer> bottomRow;
    ArrayDeque<Integer> leftColumn;
    ArrayDeque<Integer> rightColumn;
    
    public int[][] solution(int[][] rc, String[] operations) {
        int H = rc.length;
        int W = rc[0].length;
        int[][] answer = new int[H][W];
        
        ArrayDeque<ArrayDeque<Integer>> board = new ArrayDeque<>();
        for(int i = 0; i < H; i++){
            ArrayDeque<Integer> queue = new ArrayDeque<>();
            for(int val : rc[i]){
                queue.offer(val);
            }
            
            board.offer(queue);
        }
        
        topRow = board.peek();
        bottomRow = board.peekLast();
        leftColumn = new ArrayDeque<>();
        rightColumn = new ArrayDeque<>();
        
        for(int r = 0; r < H; r++){
            leftColumn.offer(rc[r][0]);
            rightColumn.offer(rc[r][W - 1]);
        }
        
        for(String operation : operations){
            if(operation.equals("Rotate")){
                rotate();
            }
            else if(operation.equals("ShiftRow")){
                shiftRow(board);
            }
        }
        
        for(int c = 0; c < W; c++){
            answer[0][c] = topRow.poll();
            answer[H - 1][c] = bottomRow.poll();
        }
        
        for(int r = 0; r < H; r++){
            answer[r][0] = leftColumn.poll();
            answer[r][W - 1] = rightColumn.poll();
        }
        
        board.poll();
        
        for(int r = 1; r <= H - 2; r++){
            Queue<Integer> queue = board.poll();
            queue.poll();

            for(int c = 1; c <= W - 2; c++){
                answer[r][c] = queue.poll();
            }
        }
        
        return answer;
    }
    
    private void rotate(){       
        topRow.pollLast();
        int topRowLastVal = topRow.peekLast();
        
        rightColumn.pollLast();
        int rightColumnLastVal = rightColumn.peekLast();
        
        bottomRow.poll();
        int bottomRowFirstVal = bottomRow.peek();
        
        leftColumn.poll();
        int leftColumnFirstVal = leftColumn.peek();
        
        topRow.offerFirst(leftColumnFirstVal);
        rightColumn.offerFirst(topRowLastVal);
        bottomRow.offer(rightColumnLastVal);
        leftColumn.offer(bottomRowFirstVal);
    }
    
    private void shiftRow(ArrayDeque<ArrayDeque<Integer>> board){
        topRow = bottomRow;
        board.offerFirst(board.pollLast());
        bottomRow = board.peekLast();
        bottomRow.poll();
        bottomRow.pollLast();
        
        leftColumn.offerFirst(leftColumn.pollLast());
        rightColumn.offerFirst(rightColumn.pollLast());
        
        bottomRow.offerFirst(leftColumn.peekLast());
        bottomRow.offer(rightColumn.peekLast());
    }
}