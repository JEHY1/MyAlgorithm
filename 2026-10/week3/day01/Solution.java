import java.util.*;

class Solution {
    static int[] dr = {1, 0, 0, -1};
    static int[] dc = {0, -1, 1, 0};
    static char[] mapper = {'d', 'l', 'r', 'u'};
    int n;
    int m;
    int[][] distMap;
    int minDist;
    
    public String solution(int n, int m, int x, int y, int r, int c, int k) {
        init(n, m, x, y, r, c);
        String answer = getPath(n, m, x, y, r, c, k);
        return answer;
    }
    
    public void init(int n, int m, int x, int y, int r, int c){
        this.n = n;
        this.m = m;
        distMap = new int[n + 1][m + 1];
        
        for(int row = 1; row <= n; row++){
            for (int col = 1; col <= m; col++){
                distMap[row][col] = Math.abs(r - row) + Math.abs(c - col);
            }
        }
        
        minDist = Math.abs(r - x) + Math.abs(c - y);
    }
    
    private String getPath(int n, int m, int x, int y, int r, int c, int k){
        if((k - minDist) % 2 != 0 || minDist > k){
            return "impossible";
        }
        
        StringBuilder minPath = new StringBuilder();
        int currentDist = 0;
        int cr = x;
        int cc = y;
        
        while(k != currentDist){
            for(int d = 0; d < 4; d++){
                int nr = cr + dr[d];
                int nc = cc + dc[d];
                
                if(nr < 1 || nc < 1 || nr > n || nc > m){
                    continue;
                }
                
                if(distMap[nr][nc] > k - currentDist - 1){
                    continue;
                }
                
                cr = nr;
                cc = nc;
                minPath.append(mapper[d]);
                currentDist++;
                break;
            }
        }
        
        return minPath.toString();
    }
}