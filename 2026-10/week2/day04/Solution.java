import java.util.*;

class Solution {
    
    int n;
    int m;
    int[][] key;
    int[][] lock;
    int holeCount;
    List<int[]> keyIdxList;
    
    public boolean solution(int[][] key, int[][] lock) {
        init(key, lock);
        
        return check();
    }
    
    private void init(int [][] key, int[][] lock){
        this.n = lock.length;
        this.m = key.length;
        this.key = key;
        this.lock = lock;
        holeCount = 0;
        for(int r = 0; r < n; r++){
            for(int c = 0; c < n; c++){
                if(lock[r][c] == 0){
                    holeCount++;
                }
            }
        }
        
        keyIdxList = new ArrayList<>();
        for(int r = 0; r < m; r++){
            for(int c = 0; c < m; c++){
                if(key[r][c] == 1){
                    keyIdxList.add(new int[]{r, c});
                }
            }
        }
    }
    
    private void rotationKey(){
        for(int[] keyIdx : keyIdxList){
            int temp = keyIdx[0];
            keyIdx[0] = -keyIdx[1] + (m - 1);
            keyIdx[1] = temp;            
        }
    }
    
    private boolean isValid(List<int[]> keyIdxList, int offsetR, int offsetC){
        int matchCount = 0;
        for(int[] keyIdx : keyIdxList){
            int keyRow = keyIdx[0] + offsetR;
            int keyCol = keyIdx[1] + offsetC;
            
            if(keyRow < 0 || keyCol < 0 || keyRow >= n || keyCol >= n){
                continue;
            }
            
            if(lock[keyRow][keyCol] == 0){
                matchCount++;
            }
            else{
                return false;
            }
        }
        
        return matchCount == holeCount;
    }
    
    private boolean check(){
        if(holeCount == 0){
            return true;
        }

        if(keyIdxList.size() < holeCount){
            return false;
        }

        for(int i = 0; i < 4; i++){
            for(int offsetR = 1 - m; offsetR < n; offsetR++){
                for(int offsetC = 1 - m; offsetC < n; offsetC++){
                    if(isValid(keyIdxList, offsetR, offsetC)){
                        return true;
                    }
                }
            }
            
            rotationKey();
        }
        
        return false;
    }
}