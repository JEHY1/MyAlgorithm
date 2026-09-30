import java.util.*;

class Solution {
    static final int WIDTH = 50;
    static final int HEIGHT = 50;
    static final int CELL_SIZE = (WIDTH + 1) * (HEIGHT + 1);
    
    String[] grid;
    int[] parent;
    
    public String[] solution(String[] commands) {
        init();
        List<String> list = new ArrayList<>();

        for(String command : commands){
            StringTokenizer st = new StringTokenizer(command, " ");
            switch(st.nextToken()){
                case "UPDATE" :
                    {
                        if(st.countTokens() == 3){
                            int r = Integer.parseInt(st.nextToken());
                            int c = Integer.parseInt(st.nextToken());
                            String value = st.nextToken();
                            update(r, c, value);
                        }
                        else{
                            String value1 = st.nextToken();
                            String value2 = st.nextToken();
                            update(value1, value2);
                        }
                        break;   
                    }
                case "MERGE" :
                    int r1 = Integer.parseInt(st.nextToken());
                    int c1 = Integer.parseInt(st.nextToken());
                    int r2 = Integer.parseInt(st.nextToken());
                    int c2 = Integer.parseInt(st.nextToken());
                    
                    merge(r1, c1, r2, c2);
                    break;
                case "UNMERGE" :
                    {
                        int r = Integer.parseInt(st.nextToken());
                        int c = Integer.parseInt(st.nextToken());                    

                        unmerge(r, c);
                        break; 
                    }
                case "PRINT" :
                    int r = Integer.parseInt(st.nextToken());
                    int c = Integer.parseInt(st.nextToken());                    
                    
                    String result = print(r, c);
                    list.add(result == null ? "EMPTY" : result);
                    break;
            }
        }
        
        String[] answer = new String[list.size()];
        for(int i = 0; i < list.size(); i++){
            answer[i] = list.get(i);
        }
        
        return answer;
    }
    
    private void init(){
        grid = new String[CELL_SIZE];
        parent = new int[CELL_SIZE];
        
        for(int i = 1; i < CELL_SIZE; i++){
            parent[i] = i;
        }
    }
    
    private int find(int x){
        if(x == parent[x]){
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
                
        parent[b] = a;
        if(grid[a] == null && grid[b] != null){
            grid[a] = grid[b];
            grid[b] = null;
        }
        else if(grid[a] != null && grid[b] != null){
            grid[b] = null;
        }
    }
    
    private void update(int r, int c, String val){
        int idx = getIdx(r, c);
        int root = find(idx);
        
        grid[root] = val;
    }
    
    private void update(String val1, String val2){
        for(int i = 0; i < CELL_SIZE; i++){
            int root = find(i);
            if(grid[root] != null && grid[root].equals(val1)){
                grid[root] = val2;
            }
        }
    }
    
    private void merge(int r1, int c1, int r2, int c2){
        int idx1 = getIdx(r1, c1);
        int idx2 = getIdx(r2, c2);
        
        union(idx1, idx2);
    }
    
    private void unmerge(int r, int c){
        int idx = getIdx(r, c);
        int root = find(idx);
        String val = grid[root];
        grid[root] = null;
        
        Queue<Integer> queue = new ArrayDeque<>();
        
        for(int i = 0; i < CELL_SIZE; i++){
            if(find(i) == root){
                queue.offer(i);
            }
        }
        
        while(!queue.isEmpty()){
            int i = queue.poll();
            parent[i] = i;
        }
        
        grid[idx] = val;
    }
    
    private String print(int r, int c){
        int idx = getIdx(r, c);
        int root = find(idx);
        
        return grid[root];
    }
    
    private int getIdx(int r, int c){
        return (r - 1) * HEIGHT + c;
    }
}