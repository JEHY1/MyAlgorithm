import java.util.*;

class Solution {
    
    int n;
    int gemKind;
    String[] gems;
    
    public int[] solution(String[] gems) {
        init(gems);
        int[] answer = getRange();
        return answer;
    }
    
    private void init(String[] gems){
        this.gems = gems;
        n = gems.length;
        Set<String> gemSet = new HashSet<>();
        for(String gem : gems){
            gemSet.add(gem);
        }
        gemKind = gemSet.size();
    }
    
    private int[] getRange(){
        Map<String, Integer> includeGems = new HashMap<>();
        int front = -1;
        int end = 0;
        
        int minCount = Integer.MAX_VALUE;
        int selFront = -1;
        int selEnd = -1;
        
        while(end <= n - 1){
            if(includeGems.size() == gemKind){
                if(front - end + 1 < minCount){
                    minCount = front - end + 1;
                    selFront = front;
                    selEnd = end;
                    
                    if(minCount == gemKind){
                        return new int[] {selEnd + 1, selFront + 1};
                    }
                }
                
                int selGemIncludeCount = includeGems.get(gems[end]);
                if(selGemIncludeCount == 1){
                    includeGems.remove(gems[end]);
                }
                else{
                    includeGems.put(gems[end], selGemIncludeCount - 1);
                }
                end++;
            }
            else{
                if(front < n - 1){
                    front++;    
                    String selGem = gems[front];
                    if(includeGems.containsKey(selGem)){
                        int count = includeGems.get(selGem);
                        includeGems.put(selGem, count + 1);
                    }
                    else{
                        includeGems.put(selGem, 1);
                    }
                }
                else{
                    break;
                }
            }
        }
        
        return new int[] {selEnd + 1, selFront + 1};
    }
}