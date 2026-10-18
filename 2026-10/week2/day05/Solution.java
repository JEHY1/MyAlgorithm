import java.util.*;

class Solution {
    
    int[] parent;
    int n;
    long k;
    Map<Long, Integer> mapper;
    long[] biggestRoomNumber;
    int[] size;

    public long[] solution(long k, long[] room_number) {
        init(k, room_number);
        long[] answer = new long[n];
        
        for(int i = 0; i < n; i++){
            answer[i] = assign(i, room_number[i]);        
        }
        
        return answer;
    }

    private void init(long k, long[] room_number){
        this.k = k;
        this.n = room_number.length;
        parent = new int[n];
        size = new int[n];
        for(int i = 0; i < n; i++){
            parent[i] = i;
            size[i] = 1;
        }
        biggestRoomNumber = new long[n];
        mapper = new HashMap<>();
    }
    
    private long assign(int i, long want){
        if(mapper.containsKey(want)){
            int root = find(mapper.get(want));
            long changedWant = biggestRoomNumber[root] + 1;
            biggestRoomNumber[i] = changedWant;
            mapper.put(changedWant, i);
            union(i, mapper.get(want));
            
            if(mapper.containsKey(changedWant + 1)){
                union(i, mapper.get(changedWant + 1));
            }
            
            return changedWant;
        }
        else{
            mapper.put(want, i);
            biggestRoomNumber[i] = want;
            
            if(mapper.containsKey(want - 1)){
                union(i, mapper.get(want - 1));                
            }

            if(mapper.containsKey(want + 1)){
                union(i, mapper.get(want + 1));
            }
            return want;
        }
    }
    
    private int find(int x){
        if(parent[x] == x){
            return x;
        }
        
        return parent[x] = find(parent[x]);
    }
    
    private void union(int a, int b){
        a = find(a);
        b = find(b);
        
        if(a == b){
            return;
        }

        if(size[a] < size[b]){
            int temp = a;
            a = b;
            b = temp;
        }
        
        parent[b] = a;
        biggestRoomNumber[a] = Math.max(biggestRoomNumber[a], biggestRoomNumber[b]);
        size[a] += size[b];
    }
}