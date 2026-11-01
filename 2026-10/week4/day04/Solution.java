import java.util.*;

class Solution {
    
    Queue<int[]> queue;
    long k;
    int n;
    boolean[] isRemove;
    
    public int solution(int[] food_times, long k) {
        init(food_times, k);
        int answer = getNextFoodNum();
        return answer;
    }
    
    private void init(int[] food_times, long k){
        this.k = k;
        n = food_times.length;
        
        queue = new PriorityQueue<>((a, b) -> {
            if(a[1] == b[1]){
                return Integer.compare(a[0], b[0]);
            }            
            
            return Integer.compare(a[1], b[1]);
        });    
        
        for(int i = 0; i < n; i++){
            queue.offer(new int[] {i + 1, food_times[i]});
        }
        
        isRemove = new boolean[n + 1];
    }
    
    private int getNextFoodNum(){
        int foodConsumption = 0;

        while(!queue.isEmpty()){
            int[] state = queue.poll();
            int foodNum = state[0];
            int amount = state[1] - foodConsumption;
            
            if(amount == 0){
                isRemove[foodNum] = true;
                continue;
            }
            
            long cycleAmount = (long)amount * (queue.size() + 1);
            
            if(cycleAmount <= k){
                foodConsumption += amount;
                k -= cycleAmount;
                isRemove[foodNum] = true;
            }
            else{
                k %= (queue.size() + 1);
                queue.offer(state);
                break;
            }
        }
        
        if(queue.isEmpty()){
            return -1;
        }
        
        for(int i = 1; i <= n; i++){
            if(isRemove[i]){
                continue;
            }
            if(k == 0){
                return i;
            }
            
            k--;
        }
        
        return -2;
    }
}          