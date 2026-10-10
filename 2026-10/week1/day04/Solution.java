public class Solution {

    public static void main(String[] args){
        int answer = new Solution().solution(new String[] {"1", "-", "3", "+", "5", "-", "8"});
        System.out.println(answer);
    }

    int[] numbers;
    int n;
    char[] calc;

    public int solution(String[] arr) {
        n = arr.length / 2 + 1;
        numbers = new int[n];
        calc = new char[n - 1];

        numbers[0] = Integer.parseInt(arr[0]);
        for (int i = 1; i < n; i++) {
            calc[i - 1] = arr[2 * i - 1].charAt(0);
            numbers[i] = Integer.parseInt(arr[2 * i]);
        }

        minDp = new int[n][n];
        maxDp = new int[n][n];
        visitedDp = new boolean[n][n];

        dp(0, n - 1);

        return maxDp[0][n - 1];
    }

    int[][] minDp;
    int[][] maxDp;
    boolean[][] visitedDp;
    private void dp(int start, int end){

        if(start == end){
            minDp[start][end] = numbers[start];
            maxDp[start][end] = numbers[start];
            return;
        }
        
        if(visitedDp[start][end]){
            return;
        }

        int minResult = Integer.MAX_VALUE;
        int maxResult = Integer.MIN_VALUE;

        for(int mid = start; mid < end; mid++){
            int minVal = 0;
            int maxVal = 0;
            dp(start, mid);
            dp(mid + 1, end);
            int frontMin = minDp[start][mid];
            int frontMax = maxDp[start][mid];
            int backMin = minDp[mid + 1][end];
            int backMax = maxDp[mid + 1][end];
            
            if(calc[mid] == '+'){
                maxVal = frontMax + backMax;
                minVal = frontMin + backMin;
            }
            else if(calc[mid] == '-'){
                maxVal = frontMax - backMin;
                minVal = frontMin - backMax;
            }

            maxResult = Math.max(maxResult, maxVal);
            minResult = Math.min(minResult, minVal);
        }

        visitedDp[start][end] = true;
        maxDp[start][end] = maxResult;
        minDp[start][end] = minResult;
    }
}