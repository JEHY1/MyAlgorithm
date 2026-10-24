class Solution {
    public int[] solution(int e, int[] starts) {
        init(e);
        calcAppearCount();
        calcMaxAppearCount();
        int[] answer = getAnswer(starts);
        return answer;
    }
    
    int e;
    int[] appearCount;
    int[] maxAppearNumber;
    
    private void init(int e){
        this.e = e;
        appearCount = new int[e + 1];
        maxAppearNumber = new int[e + 1];
    }
    
    private void calcAppearCount(){
        for(int i = 1; i <= e; i++){
            for(int j = 1; j <= e / i; j++){
                appearCount[i * j]++;
            }
        }
    }
    
    private void calcMaxAppearCount(){
        maxAppearNumber[e] = e;
        
        for(int i = e - 1; i > 0; i--){
            int prevMaxAppearNum = maxAppearNumber[i + 1];
            maxAppearNumber[i] = appearCount[i] >= appearCount[prevMaxAppearNum] ? i : prevMaxAppearNum;
        }
    }
        
    private int[] getAnswer(int[] start){
        int size = start.length;
        int[] answer = new int[size];
        
        for(int i = 0; i < size; i++){
            answer[i] = maxAppearNumber[start[i]];
        }
        
        return answer;
    }
}