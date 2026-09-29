import java.util.*;

class Solution {
    
    ArrayDeque<Integer> topQueue;
    ArrayDeque<Integer> bottomQueue;
    ArrayDeque<Integer> leftQueue;
    ArrayDeque<Integer> rightQueue;
    
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
        
        topQueue = board.peek();
        bottomQueue = board.peekLast();
        leftQueue = new ArrayDeque<>();
        rightQueue = new ArrayDeque<>();
        
        for(int r = 0; r < H; r++){
            leftQueue.offer(rc[r][0]);
            rightQueue.offer(rc[r][W - 1]);
        }
        
        for(String operation : operations){
            if(operation.equals("Rotate")){
                rotate(board, H, W);
            }
            else{
                shiftRow(board);
            }
        }
        
        for(int c = 0; c < W; c++){
            answer[0][c] = topQueue.poll();
            answer[H - 1][c] = bottomQueue.poll();
        }
        
        for(int r = 0; r < H; r++){
            answer[r][0] = leftQueue.poll();
            answer[r][W - 1] = rightQueue.poll();
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
    
    private void rotate(ArrayDeque<ArrayDeque<Integer>> board, int H, int W){       
        topQueue.pollLast();
        int topQueueLastVal = topQueue.peekLast();
        
        rightQueue.pollLast();
        int rightQueueLastVal = rightQueue.peekLast();
        
        bottomQueue.poll();
        int bottomQueueFirstVal = bottomQueue.peek();
        
        leftQueue.poll();
        int leftQueueFirstVal = leftQueue.peek();
        
        topQueue.offerFirst(leftQueueFirstVal);
        rightQueue.offerFirst(topQueueLastVal);
        bottomQueue.offer(rightQueueLastVal);
        leftQueue.offer(bottomQueueFirstVal);
        
    }
    
    private void shiftRow(ArrayDeque<ArrayDeque<Integer>> board){
        topQueue = bottomQueue;
        board.offerFirst(board.pollLast());
        bottomQueue = board.peekLast();
        bottomQueue.poll();
        bottomQueue.pollLast();
        
        leftQueue.offerFirst(leftQueue.pollLast());
        rightQueue.offerFirst(rightQueue.pollLast());
        
        bottomQueue.offerFirst(leftQueue.peekLast());
        bottomQueue.offer(rightQueue.peekLast());
    }
}