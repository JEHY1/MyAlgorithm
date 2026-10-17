import java.util.*;

class Solution {
    int n;
    boolean[][] widthBoard;
    boolean[][] heightBoard;
    
    public int[][] solution(int n, int[][] build_frame) {
        init(n);
        
        for(int[] command : build_frame){
            build(command);            
        }
        
        int[][] answer = makeAnswer();
        return answer;
    }
    
    private void init(int n){
        this.n = n;
        widthBoard = new boolean[n + 1][n + 1];
        heightBoard = new boolean[n + 1][n + 1];
    }
    
    public void build(int[] command){
        int x = command[0];
        int y = command[1];
        int struct = command[2];
        int behavior = command[3];
        
        if(struct == 0){
            if(behavior == 0){
                removeHeight(x, y);
            }
            else{
                addHeight(x, y);
            }
        }
        else{
            if(behavior == 0){
                removeWidth(x, y);
            }
            else{
                addWidth(x, y);
            }
        }
    }
    
    private boolean isWidthValid(int x, int y){
        //아래 기둥 //오른쪽 아래 기둥 //양쪽 보
        return (y - 1 >= 0 && heightBoard[y - 1][x]) 
           || (y - 1 >= 0 && x + 1 <= n && heightBoard[y - 1][x + 1]) 
           || (x + 1 <= n && x - 1 >= 0 && widthBoard[y][x - 1] && widthBoard[y][x + 1]);
    }
    
    private boolean isHeightValid(int x, int y){
        //바닥인 경우 //아래 기둥이 있는 경우 // 아래 보 있는 경우 // 왼쪽 아래 보 있는 경우
        return (y == 0) 
           || (heightBoard[y - 1][x]) 
           || (widthBoard[y][x]) 
           || (x - 1 >= 0 && widthBoard[y][x - 1]);
    }    
    
    private void addWidth(int x, int y){
        if(isWidthValid(x, y)){
            widthBoard[y][x] = true;            
        }
    }
    
    private void addHeight(int x, int y){
        if(isHeightValid(x, y)){
            heightBoard[y][x] = true;
        }
    }
    
    private void removeWidth(int x, int y){
        if(!widthBoard[y][x]){
            return;
        }
        
        widthBoard[y][x] = false;

        //위 기둥 검사 //오른쪽 위 기둥 검사 //왼쪽 보 검사 //오른쪽 보 검사
        if((heightBoard[y][x] && !isHeightValid(x, y)) 
           || (x + 1 <= n && (heightBoard[y][x + 1] && !isHeightValid(x + 1, y))) 
           || (x - 1 >= 0 && (widthBoard[y][x - 1] && ! isWidthValid(x - 1, y))) 
           || (x + 1 <= n && (widthBoard[y][x + 1] && !isWidthValid(x + 1, y)))){
            
            widthBoard[y][x] = true;
        }
    }
    
    private void removeHeight(int x, int y){
        if(!heightBoard[y][x]){
            return;
        }
        heightBoard[y][x] = false;
        
        //위 기둥 검사 //왼쪽 위 보 검사 //위 보 검사
        if((y + 1 <= n && heightBoard[y + 1][x] && !isHeightValid(x, y + 1)) 
           || (x - 1 >= 0 && y + 1 <= n && widthBoard[y + 1][x - 1] && !isWidthValid(x - 1, y + 1)) 
           || (y + 1 <= n && widthBoard[y + 1][x] && !isWidthValid(x, y + 1))){
            
            heightBoard[y][x] = true;
        }
    }
    
    private int[][] makeAnswer(){
        List<int[]> list = new ArrayList<>();
        
        for(int x = 0; x <= n; x++){
            for(int y = 0; y <= n; y++){
                if(heightBoard[y][x]){
                    list.add(new int[] {x, y, 0});
                }
                
                if(widthBoard[y][x]){
                    list.add(new int[] {x, y, 1});
                }
            }
        }
        
        return list.toArray(new int[0][]);
    }
}