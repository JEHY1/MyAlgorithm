import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.StringTokenizer;

public class Solution {
    public static void main(String[] args) throws IOException {
        new Solution().solution();
    }

    int edgeCount;
    int E;
    int V;
    int[] head;
    int[] to;
    int[] next;
    int maxN;
    int[][] map;
    int[] depth;

    public void solution() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        init(br);
        makeMap();
        solve(br);
    }

    public void solve(BufferedReader br) throws IOException {
        int M = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < M; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine().trim());
            int node1 = Integer.parseInt(st.nextToken());
            int node2 = Integer.parseInt(st.nextToken());
            sb.append(getLCA(node1, node2)).append('\n');
        }

        System.out.println(sb.toString());
    }

    private void init(BufferedReader br) throws IOException {
        edgeCount = 0;
        V = Integer.parseInt(br.readLine().trim());
        E = V - 1;
        head = new int[V + 1];
        Arrays.fill(head, -1);
        next = new int[2 * E];
        to = new int[2 * E];

        for(int i = 0; i < E; i++){
            StringTokenizer st = new StringTokenizer(br.readLine().trim());
            int node1 = Integer.parseInt(st.nextToken());
            int node2 = Integer.parseInt(st.nextToken());

            addEdge(node1, node2);
            addEdge(node2, node1);
        }

        depth = new int[V + 1];
        maxN = Integer.numberOfTrailingZeros(Integer.highestOneBit(V));
        map = new int[V + 1][maxN + 1];
    }

    private void addEdge(int from, int to) {
        this.to[edgeCount] = to;
        next[edgeCount] = head[from];
        head[from] = edgeCount++;
    }

    private void makeMap() {
        Deque<int[]> stack = new ArrayDeque<>();
        int[] path = new int[V];

        path[0] = 1;
        stack.push(new int[] {1, 0});

        while(!stack.isEmpty()) {
            int[] state = stack.pop();
            int node = state[0];
            int depth = state[1];
            path[depth] = node;
            this.depth[node] = depth;

            for(int n = 0, upDepth = 1; upDepth <= depth; upDepth *= 2, n++) {
                map[node][n] = path[depth - upDepth];
            }

            for(int edgeIdx = head[node]; edgeIdx != -1; edgeIdx = next[edgeIdx]) {
                int nextNode = to[edgeIdx];

                if(depth != 0 && nextNode == path[depth - 1]){
                    continue;
                }

                stack.push(new int[] {nextNode, depth + 1});
            }
        }
    }

    private int getUpDepthMove(int node, int upDepth) {
        while(upDepth != 0) {
            int sel = upDepth & -upDepth;
            int n = Integer.numberOfTrailingZeros(sel);
            upDepth ^= sel;

            node = map[node][n];
        }

        return node;
    }

    private int getLCA(int node1, int node2) {
        if(depth[node1] < depth[node2]) {
            int temp = node1;
            node1 = node2;
            node2 = temp;
        }

        int diff = depth[node1] - depth[node2];
        node1 = getUpDepthMove(node1, diff);

        if(node1 == node2) {
            return node1;
        }

        for(int n = maxN; n >= 0; n--) {
            if(map[node1][n] != map[node2][n]) {
                node1 = map[node1][n];
                node2 = map[node2][n];
            }
        }

        return map[node1][0];
    }
}