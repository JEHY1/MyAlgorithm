class Solution {
    public int solution(int n, int[][] results){
        int answer = 0;
        int[][] resultBoard = new int[n + 1][n + 1];
        
        for(int[] result : results){
            int winner = result[0];
            int loser = result[1];
            
            resultBoard[winner][loser] = 1;
            resultBoard[loser][winner] = -1;
        }
        
        for(int k = 1; k <= n; k++){
            for(int i = 1; i <= n; i++){
                for(int j = 1; j <= n; j++){
                    if(resultBoard[i][k] == 1 && resultBoard[k][j] == 1){
                        resultBoard[i][j] = 1;
                        resultBoard[j][i] = -1;
                    }
                }
            }
        }
        
        for(int i = 1; i <= n; i++){
            int count = 0;
            
            for(int j = 1; j <= n; j++){
                if(resultBoard[i][j] != 0){
                    count++;
                }
            }
            
            if(count == n - 1){
                answer++;
            }    
        }
        
        return answer;
    }
    
//     public int solution(int n, int[][] results) {
//         boolean[][] winBoard = new boolean[n + 1][n + 1];
//         for(int[] result : results){
//             winBoard[result[0]][result[1]] = true;
//         }
        
//         for(int k = 1; k <= n; k++){
//             for(int i = 1; i <= n; i++){
//                 for(int j = 1; j <= n; j++){
//                     if(k == i || i == j || j == k){
//                         continue;
//                     }
                    
//                     if(winBoard[i][k] && winBoard[k][j]){
//                         winBoard[i][j] = true;
//                     }
//                 }
//             }
//         }
        
//         int[][] winLoseCount = new int[n + 1][2];
//         for(int i = 1; i <= n; i++){
//             for(int j = 1; j <= n; j++){
//                 if(winBoard[i][j]){
//                     winLoseCount[i][0]++;
//                     winLoseCount[j][1]++;
//                 }
//             }
//         }
        
//         int answer = 0;
//         for(int i = 1; i <= n; i++){
//             if(winLoseCount[i][0] + winLoseCount[i][1] == n - 1){
//                 answer++;
//             }
//         }
        
//         return answer;
//     }
}