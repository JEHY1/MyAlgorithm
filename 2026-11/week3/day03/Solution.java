import java.util.*;

class Solution {
    
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};
    
    boolean[][] rect;
    boolean[][] line;
    int startX;
    int startY;
    int goalX;
    int goalY;
    
    public int solution(int[][] rectangle, int characterX, int characterY, int itemX, int itemY) {
        init(rectangle, characterX, characterY, itemX, itemY);
        int answer = getMinDist();
        return answer;
    }
    
    private void init(int[][] rectangles, int characterX, int characterY, int itemX, int itemY) {
        startX = characterX * 2;
        startY = characterY * 2;
        goalX = itemX * 2;
        goalY = itemY * 2;
        
        rect = new boolean[102][102];
        line = new boolean[102][102];
        
        for(int[] rectangle : rectangles){
            draw(rectangle);
        }
    }
    
    private void draw(int[] rectangle){
        int startX = rectangle[0] * 2;
        int endX = rectangle[2] * 2;
        int startY = rectangle[1] * 2;
        int endY = rectangle[3] * 2;
        
        for(int y = startY; y <= endY; y++){
            for(int x = startX; x <= endX; x++){
                if(y != startY && y != endY && x != startX && x != endX){
                    line[y][x] = false;
                }                
                
                if(rect[y][x]){
                    continue;
                }
                
                rect[y][x] = true;
                
                if(y == startY || y == endY || x == startX || x == endX){
                    line[y][x] = true;
                }                
            }
        }        
    }
    
    private int getMinDist() {
        boolean[][] visited = new boolean[101][101];
        Queue<int[]> queue = new ArrayDeque<>();
        
        visited[startY][startX] = true;
        queue.offer(new int[] {startX, startY, 0});
        
        while(!queue.isEmpty()){
            int[] state = queue.poll();
            int cx = state[0];
            int cy = state[1];
            int cost = state[2];
            
            for(int d = 0; d < 4; d++){
                int nx = cx + dx[d];
                int ny = cy + dy[d];
                
                if(!line[ny][nx]){
                    continue;
                }
                
                if(visited[ny][nx]){
                    continue;
                }
                
                if(goalX == nx && goalY == ny){
                    return (cost + 1) / 2;
                }
                
                visited[ny][nx] = true;
                queue.offer(new int[] {nx, ny, cost + 1});
            }
        }
        
        return -1;
    }
}