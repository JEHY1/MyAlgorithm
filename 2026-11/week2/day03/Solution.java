import java.util.Arrays;

class Solution {
    
    int[][] dice;
    int n;
    int maxWin;
    int selectedDice;
    int filter;
    
    public int[] solution(int[][] dice) {
        init(dice);
        combination(0, 0, 0);
        int[] answer = maskToArray(selectedDice);
        System.out.println(Arrays.toString(answer));
        return answer;
    }
    
    private void init(int[][] dice){
        n = dice.length;
        this.dice = dice;
        maxWin = 0;
        selectedDice = 0;
        filter = (1 << n) - 1;
    }
    
    private void combination(int depth, int selCount, int mask){
        if(selCount == n / 2){
            int winCount = calcWinCount(mask);
            if(winCount > maxWin){
                maxWin = winCount;
                selectedDice = mask;                
            }
            
            return;
        }
        
        if(depth == n){
            return;
        }
        
        combination(depth + 1, selCount + 1, mask | (1 << depth));
        combination(depth + 1, selCount, mask);
    }
    
    private int calcWinCount(int mask){
        int reverseMask = ~mask & filter;
        
        int[][] sumResult = getCombiResult(mask, reverseMask, 0, 0, new int[501][501]);
        
        int winCount = 0;
        
        for(int i = 1; i <= 500; i++){            
            sumResult[1][i] += sumResult[1][i - 1];
        }
        
        for(int AScore = 1; AScore <= 500; AScore++){            
            winCount += sumResult[1][AScore - 1] * sumResult[0][AScore];
        }
        
        return winCount;
    }
    
    private int[][] getCombiResult(int AUserMask, int BUserMask, int AUserSum, int BUserSum, int[][] result){
        if(AUserMask == 0 && BUserMask == 0){
            result[0][AUserSum]++;
            result[1][BUserSum]++;
            return null;
        }
        
        int AUserSel = AUserMask & -AUserMask;
        int BUserSel = BUserMask & -BUserMask;
        int AUserSelIndex = Integer.numberOfTrailingZeros(AUserSel);
        int BUserSelIndex = Integer.numberOfTrailingZeros(BUserSel);
        
        for(int i = 0; i < 6; i++){
            getCombiResult(AUserMask ^ AUserSel, BUserMask ^ BUserSel, AUserSum + dice[AUserSelIndex][i], BUserSum + dice[BUserSelIndex][i], result);
        }
        
        return result;
    }
    
    private int[] maskToArray(int mask){    	
        int[] returnVal = new int[n / 2];
        int index = 0;
        while(mask != 0){
            int sel = mask & - mask;
            int selIndex = Integer.numberOfTrailingZeros(sel);
            mask ^= sel;
            
            returnVal[index++] = selIndex + 1;
        }
        
        return returnVal;
    }
}