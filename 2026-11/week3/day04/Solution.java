import java.util.*;

class Solution {
    
    static final int IMPOSSIBLE = -1;
    static final int NO_VALUE = -2;
    
    Set<String>[] strs;
    int[] dp;
    String t;
    
    public int solution(String[] strs, String t) {
        init(strs, t);
        int answer = dp(t.length());
        return answer;
    }
    
    private void init(String[] strs, String t) {
        this.t = t;
        this.strs = new Set[6];
        for(int i = 1; i <= 5; i++){
            this.strs[i] = new HashSet<>();
        }
        
        for(String str : strs){
            this.strs[str.length()].add(str);
        }
        
        dp = new int[t.length() + 1];
        Arrays.fill(dp, NO_VALUE);
    }
    
    private int dp(int length) {
        if(length == 0) {
            return dp[0] = 0;
        }
        
        if(dp[length] != NO_VALUE){
            return dp[length];
        }
        
        int minCount = Integer.MAX_VALUE;
        for(int i = 1; i <= 5; i++) {
            int start = length - i;
            int end = length;
            
            if(start < 0) {
                break;
            }
            
            if(dp(length - i) == IMPOSSIBLE){
                continue;
            }
            
            String subString = t.substring(start, end);
            
            if(strs[i].contains(subString)) {
                minCount = Math.min(minCount, dp(length - i) + 1);    
            }                    
        }
        
        if(minCount == Integer.MAX_VALUE) {
            return dp[length] = IMPOSSIBLE;
        }
        
        return dp[length] = minCount;
    }
}