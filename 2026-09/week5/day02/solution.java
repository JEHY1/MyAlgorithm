import java.util.*;

class Solution {
    static final int WIDTH = 50;
    static final int HEIGHT = 50;
    static final int CELL_SIZE = WIDTH * HEIGHT + 1;
    
    String[] grid;
    int[] parent;
    Map<Integer, List<Integer>> subSet;
    
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
        subSet = new HashMap<>();
        
        for(int i = 1; i < CELL_SIZE; i++){
            parent[i] = i;
            subSet.put(i, new ArrayList<>());
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

        subSet.computeIfAbsent(a, k -> new ArrayList<>()).add(b);
        List<Integer> list = subSet.remove(b);
        subSet.get(a).addAll(list);
    }
    
    private void update(int r, int c, String val){
        int idx = getIdx(r, c);
        int root = find(idx);
        
        grid[root] = val;
    }
    
    private void update(String val1, String val2){
        Set<Integer> list = subSet.keySet();
        for(int i : list){
            if(grid[i] != null && grid[i].equals(val1)){
                grid[i] = val2;
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
        
        List<Integer> list = subSet.get(root);
        if(list != null){
            for(int i : list){
                parent[i] = i;
                subSet.put(i, new ArrayList<>());
            }
        }
        
        list.clear();
        grid[idx] = val;
    }
    
    private String print(int r, int c){
        int idx = getIdx(r, c);
        int root = find(idx);
        
        return grid[root];
    }
    
    private int getIdx(int r, int c){
        return (r - 1) * WIDTH + c;
    }
}