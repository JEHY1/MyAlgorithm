import java.util.*;

class Solution {
    static int[] dr = {1, 0, 0, -1};
    static int[] dc = {0, -1, 1, 0};
    static char[] mapper = {'d', 'l', 'r', 'u'};
    
    int n;
    int m;
    int minDist;
    int endR;
    int endC;
    
    public String solution(int n, int m, int x, int y, int r, int c, int k) {
        init(n, m, x, y, r, c);
        String answer = getPath(x, y, k);
        return answer;
    }
    
    public void init(int n, int m, int x, int y, int r, int c){
        this.n = n;
        this.m = m;
        minDist = Math.abs(r - x) + Math.abs(c - y);
        endR = r;
        endC = c;
    }
    
    private String getPath(int x, int y, int k){
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
                int dist = Math.abs(endR - nr) + Math.abs(endC - nc);
                
                if(nr < 1 || nc < 1 || nr > n || nc > m){
                    continue;
                }
                
                if(dist > k - currentDist - 1){
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