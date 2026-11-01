import java.util.*;

class Solution {
    
    int rockCount;
    int[] dist;
    int distance;
    int n;
    
    public int solution(int distance, int[] rocks, int n) {
        init(distance, rocks, n);
        int answer = getMinDist(1, distance);
        return answer;
    }
    
    private void init(int distance, int[] rocks, int n){
        this.n = n;
        this.distance = distance;
        rockCount = rocks.length;
        dist = new int[rockCount + 1];
        
        Arrays.sort(rocks);        
        for(int i = 1; i < rockCount; i++){
            dist[i] = rocks[i] - rocks[i - 1];
        }
        dist[0] = rocks[0];
        dist[rockCount] = distance - rocks[rockCount - 1];
    }
    
    private boolean isValidAnswer(int answer){
        int val = 0;
        int removeCount = 0;
        for(int i = 0; i <= rockCount; i++){
            val += dist[i];
            
            if(val < answer){
                if(removeCount == n){
                    return false;
                }
                
                removeCount++;
            }
            else{
                val = 0;
            }
        }
        
        return true;
    }
    
    private int getMinDist(int start, int end){
        if(start == end){
            return start;
        }
        
        int mid = start + (end - start + 1) / 2;
        
        if(isValidAnswer(mid)){
            return getMinDist(mid, end);
        }
        else{
            return getMinDist(start, mid - 1);
        }
    }
}