import java.util.*;

class Solution {
    
    int n;
    int[][] logInfo;
    
    public int solution(String[] lines) {
        init(lines);
        int answer = getMax();
        
        return answer;
    }
    
    private int getMax(){
        Queue<int[]> waitQueue = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));
        Queue<int[]> includeQueue = new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1]));
        for(int i = 0; i < n; i++){
            waitQueue.offer(logInfo[i]);
        }
        
        int max = 0;
        int startTime = 0;
        int endTime = 999;
        
        while(!waitQueue.isEmpty()){
            int[] state = waitQueue.poll();
            endTime = state[0];
            startTime = endTime - 999;
            
            includeQueue.offer(state);
            
            while(!includeQueue.isEmpty()){
                int[] state2 = includeQueue.peek();
                int logEnd = state2[1];
                
                if(startTime > logEnd){
                    includeQueue.poll();
                }
                else{
                    break;
                }
            }
            
            max = Math.max(max, includeQueue.size());
        }
        
        return max;
    }
    
    private void init(String[] lines){
        n = lines.length;
        logInfo = new int[n][];
        for(int i = 0; i < n; i++){
            logInfo[i] = parse(lines[i]);
        }
    }
    
    private int[] parse(String log){
        StringTokenizer st = new StringTokenizer(log, " ");
        st.nextToken();
        String end = st.nextToken();
        String period = st.nextToken();
        
        int endMilliSec = timeToMilliSec(end);
        int periodMilliSec = periodToMilliSec(period);
        
        return new int[] {endMilliSec - periodMilliSec + 1, endMilliSec};
    }
    
    private int timeToMilliSec(String end){
        StringTokenizer st = new StringTokenizer(end, ":");
        int hour = Integer.parseInt(st.nextToken());
        int minute = Integer.parseInt(st.nextToken());
        String[] splitSecond = st.nextToken().split("\\.");
        int sec = Integer.parseInt(splitSecond[0]) * 1000;
        int milliSec = Integer.parseInt((splitSecond[1] + "000").substring(0, 3));
        int second = sec + milliSec;

        return hour * 3600000 + minute * 60000 + second;
    }
    
    private int periodToMilliSec(String period){
        period = period.replace("s", "");
        String[] splitPeriod = period.split("\\.");
        int sec = Integer.parseInt(splitPeriod[0]) * 1000;
        int milliSec = 0;
        if(splitPeriod.length == 2){
            milliSec = Integer.parseInt((splitPeriod[1] + "000").substring(0, 3));
        }
        return sec + milliSec;
    }
}