import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Main{
    public static void main(String[] args) throws IOException {
        new Main().solution();
    }

    int N;
    int[] arr;

    public void solution() throws IOException {
        init();
        arr[0] = 1;
        dfs(1);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < N; i++){
            sb.append(arr[i]);
        }
        System.out.println(sb.toString());
    }

    private void init() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine().trim());
        arr = new int[N];
    }

    private boolean dfs(int depth){
        if(depth == N){
            return true;
        }

        for(int i = 1; i <= 3; i++){
            if(i == arr[depth - 1]){
                continue;
            }

            arr[depth] = i;

            if(checkArray(depth)){
                if(dfs(depth + 1)){
                    return true;
                }
            }
        }

        return false;
    }

    private boolean checkArray(int lastIndex){
        int maxLength = (lastIndex + 1) / 2;

        for(int length = 2; length <= maxLength; length++){
            if(!isValid(length, lastIndex)){
                return false;
            }
        }

        return true;
    }

    private boolean isValid(int length, int lastIndex){
        for(int i = 0; i < length; i++){
            if(arr[lastIndex - i] != arr[lastIndex - i - length]){
                return true;
            }
        }

        return false;
    }
}