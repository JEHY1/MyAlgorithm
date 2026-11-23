import java.util.*;

class Solution {

    private final int INF = Integer.MAX_VALUE / 101;

    int startAlp;
    int startCop;
    int maxAlp;
    int maxCop;
    int[] alp_req;
    int[] cop_req;
    int[] alp_rwd;
    int[] cop_rwd;
    int[] cost;
    int[][] dp;
    int size;
    int answer;

    public int solution(int alp, int cop, int[][] problems) {
        init(alp, cop, problems);
        dp(maxAlp + 30, maxCop + 30);
        return answer;
    }

    private void init(int alp, int cop, int[][] problems) {
        answer = Integer.MAX_VALUE;
        startAlp = alp;
        startCop = cop;
        size = problems.length;
        alp_req = new int[size];
        cop_req = new int[size];
        alp_rwd = new int[size];
        cop_rwd = new int[size];
        cost = new int[size];
        maxAlp = 0;
        maxCop = 0;
        for(int i = 0; i < size; i++){
            alp_req[i] = problems[i][0];
            cop_req[i] = problems[i][1];
            alp_rwd[i] = problems[i][2];
            cop_rwd[i] = problems[i][3];
            cost[i] = problems[i][4];

            maxAlp = Math.max(maxAlp, alp_req[i]);
            maxCop = Math.max(maxCop, cop_req[i]);
        }

        maxAlp = Math.max(maxAlp, startAlp);
        maxCop = Math.max(maxCop, startCop);
        dp = new int[maxAlp + 31][maxCop + 31];

        for(int i = 0; i <= maxAlp + 30; i++){
            Arrays.fill(dp[i], -1);
        }
    }

    private int dp (int alp, int cop) {
        if(alp <= startAlp && cop <= startCop){
            return dp[startAlp][startCop] = 0;
        }

        if(dp[Math.max(alp, startAlp)][Math.max(cop, startCop)] != -1){
            return dp[Math.max(alp, startAlp)][Math.max(cop, startCop)];
        }

        int minCost = INF;
        for(int problem = 0; problem < size; problem++){
            if(alp_rwd[problem] == 0 && cop_rwd[problem] == 0){
                continue;
            }
            if(isValid(problem, alp, cop)){
                if(alp <= startAlp && cop_rwd[problem] == 0){
                    continue;
                }
                else if(cop <= startCop && alp_rwd[problem] == 0){
                    continue;
                }
                minCost = Math.min(minCost, dp(alp - alp_rwd[problem], cop - cop_rwd[problem]) + cost[problem]);
            }
        }

        if(alp > startAlp){
            minCost = Math.min(minCost, dp(alp - 1, cop) + 1);
        }
        if(cop > startCop){
            minCost = Math.min(minCost, dp(alp, cop - 1) + 1);
        }

        if(alp >= maxAlp && cop >= maxCop) {
            answer = Math.min(answer, minCost);
        }

        return dp[Math.max(alp, startAlp)][Math.max(cop, startCop)] = minCost;
    }

    private boolean isValid(int problem, int alp, int cop){
        alp -= alp_rwd[problem];
        cop -= cop_rwd[problem];
        alp = Math.max(alp, startAlp);
        cop = Math.max(cop, startCop);

        return alp >= alp_req[problem] && cop >= cop_req[problem];
    }
}