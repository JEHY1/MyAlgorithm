import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Solution {

    public static void main(String[] args) throws IOException {
        new Solution().solution();
    }

    int edgeCount;
    int reverseEdgeCount;
    int V;
    int E;

    int[] head;
    int[] next;
    int[] to;

    int[] reverseHead;
    int[] reverseNext;
    int[] reverseTo;

    public void solution() throws IOException {
        init();
        Deque<Integer> completeVisitOrder = getCompleteVisitOrder();
        List<List<Integer>> SCCSizeList = getSCCList(completeVisitOrder);
        printAnswer(SCCSizeList);
    }

    private void init() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        V = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());
        edgeCount = 0;
        reverseEdgeCount = 0;

        head = new int[V + 1];
        Arrays.fill(head, -1);
        next = new int[E];
        to = new int[E];

        reverseHead = new int[V + 1];
        Arrays.fill(reverseHead, -1);
        reverseNext = new int[E];
        reverseTo = new int[E];

        for(int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine().trim());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());

            addEdge(from, to);
            reverseGraphAddEdge(to, from);
        }
    }

    private void addEdge(int from, int to) {
        this.to[edgeCount] = to;
        next[edgeCount] = head[from];
        head[from] = edgeCount++;
    }

    private void reverseGraphAddEdge(int from, int to) {
        reverseTo[reverseEdgeCount] = to;
        reverseNext[reverseEdgeCount] = reverseHead[from];
        reverseHead[from] = reverseEdgeCount++;
    }

    private Deque<Integer> getCompleteVisitOrder() {
        boolean[] visited = new boolean[V + 1];
        Deque<Integer> stack = new ArrayDeque<>();
        for(int v = 1; v <= V; v++) {
            if(!visited[v]) {
                dfs(v, visited, stack);
            }
        }

        return stack;
    }

    private void dfs(int start, boolean[] visited, Deque<Integer> stack) {
        Deque<Integer> dfsStack = new ArrayDeque<>();
        int[] currentEdge = new int[V + 1];

        visited[start] = true;
        currentEdge[start] = head[start];
        dfsStack.push(start);

        while(!dfsStack.isEmpty()) {
            int node = dfsStack.peek();
            int edgeIdx = currentEdge[node];

            if(edgeIdx == -1) {
                stack.push(dfsStack.pop());
                continue;
            }

            currentEdge[node] = next[edgeIdx];

            int nextNode = to[edgeIdx];

            if(visited[nextNode]) {
                continue;
            }

            visited[nextNode] = true;
            currentEdge[nextNode] = head[nextNode];
            dfsStack.push(nextNode);
        }
    }

    private List<List<Integer>> getSCCList(Deque<Integer> stack) {
        List<List<Integer>> list = new ArrayList<>();
        boolean[] visited = new boolean[V + 1];
        while(!stack.isEmpty()) {
            int node = stack.pop();
            if(visited[node]) {
                continue;
            }

            List<Integer> nodes = getCSSNodes(node, visited);
            nodes.sort(Integer::compareTo);
            list.add(nodes);
        }

        return list;
    }

    private List<Integer> getCSSNodes(int node, boolean[] visited) {
        List<Integer> nodes = new ArrayList<>();

        Deque<Integer> dfsStack = new ArrayDeque<>();
        visited[node] = true;
        dfsStack.push(node);

        while(!dfsStack.isEmpty()) {
            node = dfsStack.pop();
            nodes.add(node);

            for(int edgeIdx = reverseHead[node]; edgeIdx != -1; edgeIdx = reverseNext[edgeIdx]) {
                int nextNode = reverseTo[edgeIdx];
                if(visited[nextNode]) {
                    continue;
                }

                visited[nextNode] = true;
                dfsStack.push(nextNode);
            }
        }

        return nodes;
    }

    private void printAnswer(List<List<Integer>> SCCList) {
        StringBuilder sb = new StringBuilder();
        SCCList.sort((a, b) -> Integer.compare(a.get(0), b.get(0)));
        sb.append(SCCList.size()).append('\n');
        for(List<Integer> nodes : SCCList) {
            for(int i : nodes) {
                sb.append(i).append(' ');
            }
            sb.append(-1).append('\n');
        }

        System.out.println(sb.toString());
    }
 }