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

//        System.out.println(Arrays.toString(numbers));
//        System.out.println(Arrays.toString(calc));

        minDp = new int[n][n];
        maxDp = new int[n][n];
        visitedDp = new boolean[n][n];

        int answer = dp(0, n - 1, GET_MAX);

//        System.out.println(Arrays.deepToString(minDp));
//        System.out.println(Arrays.deepToString(maxDp));

        return answer;
    }

    static final int GET_MAX = 1;
    static final int GET_MIN = 0;

    int[][] minDp;
    int[][] maxDp;
    boolean[][] visitedDp;
    private int dp(int start, int end, int getWhat){

        if(start == end){
            return numbers[start];
        }

        if(visitedDp[start][end]){
            if(getWhat == GET_MAX){
                return maxDp[start][end];
            }
            else if(getWhat == GET_MIN){
                return minDp[start][end];
            }
        }

        int minResult = Integer.MAX_VALUE;
        int maxResult = Integer.MIN_VALUE;

        for(int mid = start; mid < end; mid++){
            int minVal = 0;
            int maxVal = 0;
            if(calc[mid] == '+'){
                maxVal = dp(start, mid, GET_MAX) + dp(mid + 1, end, GET_MAX);
                minVal = dp(start, mid, GET_MIN) + dp(mid + 1, end, GET_MIN);
            }
            else if(calc[mid] == '-'){
                maxVal = dp(start, mid, GET_MAX) - dp(mid + 1, end, GET_MIN);
                minVal = dp(start, mid, GET_MIN) - dp(mid + 1, end, GET_MAX);
            }

            maxResult = Math.max(maxResult, maxVal);
            minResult = Math.min(minResult, minVal);
        }

        visitedDp[start][end] = true;
        maxDp[start][end] = maxResult;
        minDp[start][end] = minResult;

        if(getWhat == GET_MAX){
            return maxResult;
        }
        else if(getWhat == GET_MIN){
            return minResult;
        }

        return -1;
    }
}