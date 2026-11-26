import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Solution {

    public static void main(String[] args) throws IOException {
        new Solution().solution();
    }

    String pattern;
    String T;
    int patternLength;
    int[] LPS;

    private void solution() throws IOException {
        init();
        makeLPS();
        List<Integer> indexList = findPatternIndex();
        printAnswer(indexList);
    }

    private void init() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        T = br.readLine();
        pattern = br.readLine();
        patternLength = pattern.length();
        LPS = new int[patternLength];
    }

    private void makeLPS() {
        int matchCount= 0;
        char[] patternArr = pattern.toCharArray();

        for(int i = 1; i < patternLength; i++) {
            while(matchCount > 0 && patternArr[matchCount] != patternArr[i]){
                matchCount = LPS[matchCount - 1];
            }

            if(patternArr[matchCount] == patternArr[i]) {
                LPS[i] = ++matchCount;
            }
        }
    }

    private List<Integer> findPatternIndex() {
        List<Integer> indexList = new ArrayList<>();

        char[] sArr = T.toCharArray();
        char[] patternArr = pattern.toCharArray();
        int matchCount = 0;

        for(int i = 0; i < sArr.length; i++) {
            while(matchCount > 0 && sArr[i] != patternArr[matchCount]) {
                matchCount = LPS[matchCount - 1];
            }

            if(sArr[i] == patternArr[matchCount]) {
                matchCount++;
                if(matchCount == patternLength) {
                    indexList.add(i - patternLength + 2);
                    matchCount = LPS[matchCount - 1];
                }
            }
        }

        return indexList;
    }

    private void printAnswer(List<Integer> indexList){
        StringBuilder sb = new StringBuilder();
        sb.append(indexList.size()).append('\n');
        for(int i : indexList) {
            sb.append(i).append(' ');
        }
        System.out.println(sb.toString());
    }
}