package Graph;

import org.springframework.util.CollectionUtils;

import java.util.*;

public class Graph {

    static int numProvinces(ArrayList<ArrayList<Integer>> adj, int V) {
        boolean[] visited = new boolean[V];
        int counter = 0;
        Queue<Integer> q =new LinkedList<>();
        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                visited[i] = true;
                q.add(i);
                counter++;
                while (!q.isEmpty()) {
                    int node = q.remove();
                    for (int j = 0; j < V; j++) {
                        if (adj.get(node).get(j) == 1 && !visited[j]) {
                            q.add(j);
                            visited[j] = true;
                        }
                    }
                }
            }
        }
        return counter;
    }

    public ArrayList<Integer> bfsOfGraph(int V, ArrayList<ArrayList<Integer>> adj) {
        boolean[] visited = new boolean[V];
        ArrayList<Integer> ans = new ArrayList<>();
        Queue<Integer> q =new LinkedList<>();
        visited[0] = true;
        q.add(0);
        while (!q.isEmpty()) {
            int node = q.remove();
            ans.add(node);
            for (int j = 0; j < adj.get(node).size(); j++) {
                if (!visited[adj.get(node).get(j)]) {
                    q.add(adj.get(node).get(j));
                    visited[adj.get(node).get(j)] = true;
                }
            }
        }
        return ans;
    }

    public ArrayList<Integer> dfsOfGraph(int V, ArrayList<ArrayList<Integer>> adj) {
        boolean[] visited = new boolean[V];
        ArrayList<Integer> ans = new ArrayList<>();
        dfs(0, V, adj, ans, visited);
        return ans;
    }

    private void dfs(int node, int v, ArrayList<ArrayList<Integer>> adj, ArrayList<Integer> ans, boolean[] visited) {
        visited[node] = true;
        ans.add(node);
        for (int i = 0; i < adj.get(node).size(); i++) {
            if (!visited[adj.get(node).get(i)]) {
                dfs(adj.get(node).get(i), v, adj, ans, visited);
            }
        }
    }

    public int[][] floodFill(int[][] image, int sr, int sc, int color) {
        int m = image.length;
        int n = image[0].length;
        floodFillDFS(image, sr, sc, color, image[sr][sc], m, n);
        return image;
    }

    private void floodFillDFS(int[][] image, int sr, int sc, int color, int orgColor, int m, int n) {
        if ((sr >= 0 && sr < m && sc >= 0 && sc <n) && image[sr][sc] != 0 && image[sr][sc] != color && image[sr][sc] == orgColor) {
            image[sr][sc] = color;
        }
        else {
            return;
        }
        floodFillDFS(image,sr+1, sc, color, orgColor, m, n);
        floodFillDFS(image,sr-1, sc, color, orgColor, m, n);
        floodFillDFS(image,sr, sc+1, color, orgColor, m, n);
        floodFillDFS(image,sr, sc-1, color, orgColor, m, n);
    }

    public boolean isCycle(int V, ArrayList<ArrayList<Integer>> adj) {
        boolean[] visited = new boolean[V];
        for (int i = 0; i < adj.size(); i++) {
            if (!visited[i] && isCycleDetect(V, adj, visited, i, -1)) {
                return true;
            }
        }
        return false;
    }

    private boolean isCycleDetect(int v, ArrayList<ArrayList<Integer>> adj, boolean[] visited, int i, int parent) {
        visited[i] = true;
        for (int j : adj.get(i)) {
            if (!visited[j] && isCycleDetect(v, adj, visited, j, i)) {
                return true;
            }
            if (j != parent) {
                return true;
            }
        }
        return false;
    }

    private boolean isCycleDetectDirected(int v, ArrayList<ArrayList<Integer>> adj, boolean[] visited, boolean[] pathVisited, int i) {
        visited[i] = true;
        pathVisited[i] = true;
        for (int j : adj.get(i)) {
            if (!visited[j]) {
                if (isCycleDetectDirected(v, adj, visited, pathVisited, j)) return true;
            }
            else if (pathVisited[j]) return true;
        }
        pathVisited[i] = false;
        return false;
    }

    public boolean isCyclic(int V, ArrayList<ArrayList<Integer>> adj) {
        boolean[] visited = new boolean[V];
        boolean[] pathVisited = new boolean[V];
        for (int i = 0; i < adj.size(); i++) {
            if (!visited[i] && isCycleDetectDirected(V, adj, visited, pathVisited, i)) {
                return true;
            }
        }
        return false;
    }

    public static int[][] updateMatrix(int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;
        int[][] ans = new int[m][n];
        Queue<int[]> q = new LinkedList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (mat[i][j] == 0) {
                    q.add(new int[]{i, j});
                    ans[i][j] = 0;
                }
                else {
                    ans[i][j] = -1;
                }
            }
        }
        int[] dx = {1,0,-1,0};
        int[] dy = {0,1,0,-1};
        while (!q.isEmpty()) {
            int r = q.peek()[0];
            int c = q.remove()[1];
            for (int i = 0; i < 4; i++) {
                int nR = r + dx[i];
                int nC = c + dy[i];
                if (nR >= 0 && nC >= 0 && nR < m && nC < n && ans[nR][nC] == -1) {
                    ans[nR][nC] = 1 + ans[r][c];
                    q.add(new int[]{nR, nC});
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
//        updateMatrix(new int[][]{{1,1,0,0,1,0,0,1,1,0},{1,0,0,1,0,1,1,1,1,1},{1,1,1,0,0,1,1,1,1,0},{0,1,1,1,0,1,1,1,1,1},{0,0,1,1,1,1,1,1,1,0},{1,1,1,1,1,1,0,1,1,1},{0,1,1,1,1,1,1,0,0,1},{1,1,1,1,1,0,0,1,1,1},{0,1,0,1,1,0,1,1,1,1},{1,1,1,0,1,0,1,1,1,1}});
//        eventualSafeNodes(new int[][]{{1,2},{2,3},{5},{0},{5},{},{}});
//        shortestPathBinaryMatrix(new int[][]{{0,0,0},{1,1,0},{1,1,1}});
//        minimumEffortPath(new int[][]{{1,2,2},{3,8,2},{5,3,5}});
        findTheCity(5, new int[][]{{0,1,2},{0,4,8},{1,2,3},{1,4,2},{2,3,1},{3,4,1}}, 2);
    }

    public void solve(char[][] board) {
        int m = board.length;
        int n = board[0].length;

        int[][] visited = new int[m][n];
        Queue<int[]> q = new LinkedList<>();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 || j == 0 || i == m-1 || j == n-1) {
                    visited[i][j] = 1;
                    q.add(new int[]{i,j});
                }
            }
        }

        int[] dirX = {-1,0,1,0};
        int[] dirY = {0,1,0,-1};
        while (!q.isEmpty()) {
            int x = q.peek()[0];
            int y = q.remove()[1];

            for (int i = 0; i < 4; i++) {
                int nX = x + dirX[i];
                int nY = y + dirY[i];
                if (nX >= 0 && nY >= 0 && nX < m && nY < n && visited[nX][nY] == 0 && board[nX][nY] =='O') {
                    q.add(new int[]{nX, nY});
                    visited[nX][nY] = 1;
                }
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (visited[i][j] == 0) {
                    visited[i][j] = 1;
                    board[i][j] = 'X';
                }
            }
        }
    }

    public int numEnclaves(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        int[][] visited = new int[m][n];
        Queue<int[]> q = new LinkedList<>();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 || j == 0 || i == m-1 || j == n-1) {
                    visited[i][j] = 1;
                    q.add(new int[]{i,j});
                }
            }
        }

        int[] dirX = {-1,0,1,0};
        int[] dirY = {0,1,0,-1};
        while (!q.isEmpty()) {
            int x = q.peek()[0];
            int y = q.remove()[1];

            for (int i = 0; i < 4; i++) {
                int nX = x + dirX[i];
                int nY = y + dirY[i];
                if (nX >= 0 && nY >= 0 && nX < m && nY < n && visited[nX][nY] == 0 && grid[nX][nY] == 1) {
                    q.add(new int[]{nX, nY});
                    visited[nX][nY] = 1;
                }
            }
        }
        int ans = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (visited[i][j] == 0 && grid[i][j] == 1) {
                    ans++;
                }
            }
        }
        return ans;
    }

    static class Pair<T,P> {
        T first;
        P second;
        public Pair(T f, P s) {
            this.first = f;
            this.second = s;
        }
    }

    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Queue<Pair<String, Integer>> q = new LinkedList<>();
        q.add(new Pair<String, Integer>(beginWord, 1));
        Set<String> st = new HashSet<>();
        for (String word : wordList) st.add(word);
        st.remove(beginWord);
        while (!q.isEmpty()) {
            String word = q.peek().first;
            int steps = q.remove().second;
            if (word.equals(endWord)) return steps;
            for (int i = 0; i < word.length(); i++) {
                for (char c = 'a'; c <= 'z'; c++) {
                    char[] replacedCharArray = word.toCharArray();
                    replacedCharArray[i] = c;
                    String newWord = new String(replacedCharArray);
                    if (st.contains(newWord)) {
                        st.remove(newWord);
                        q.add(new Pair<String, Integer>(newWord, steps+1));
                    }
                }
            }
        }
        return 0;
    }

    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        List<List<String>> ans = new ArrayList<>();
        Set<String> st = new HashSet<>();
        for (String word : wordList) st.add(word);
        Queue<List<String>> q = new LinkedList<>();
        List<String> ls = new ArrayList<>();
        ls.add(beginWord);
        q.add(ls);
        List<String> usedWords = new ArrayList<>();
        usedWords.add(beginWord);
        int level = 0;
        while (!q.isEmpty()) {
            List<String> list = q.remove();
            if (list.size() > level) {
                for (int i = level; i < usedWords.size(); i++) st.remove(usedWords.get(level));
                level++;
            }
            String word = list.get(list.size()-1);
            if (word.equals(endWord)) {
                if (ans.size() == 0) {
                    ans.add(list);
                }
                else if (ans.get(0).size() == list.size()) ans.add(list);
            }
            for (int i = 0; i < word.length(); i++) {
                for (char c = 'a'; c <= 'z'; c++) {
                    char[] replacedCharArray = word.toCharArray();
                    replacedCharArray[i] = c;
                    String newWord = new String(replacedCharArray);
                    if (st.contains(newWord)) {
                        List<String> temp = new ArrayList<>(list);
                        temp.add(newWord);
                        q.add(temp);
                        usedWords.add(newWord);
                    }
                }
            }
        }
        return ans;
    }

    int countDistinctIslands(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        boolean[][] visited = new boolean[m][n];
        Set<List<String>> ans = new HashSet<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1 && visited[i][j] == false) {
                    List<String> pick = new ArrayList<>();
                    dfsDistIslands(grid, i, j, i, j, visited, m, n, pick, ans);
                    ans.add(pick);
                }
            }
        }
        return ans.size();
    }



    private void dfsDistIslands(int[][] grid, int i, int j, int iOrg, int jOrg, boolean[][] visited, int m, int n, List<String> pick, Set<List<String>> ans) {
        // normalize let's say we have 1 at 1,1 and 1,2 -> normalized -> 0,0 and 0,1
        // now let's say we have  at 2,3 and 2,4 -> normalized 0,0 and 0,1 (same solution, count once)
        visited[i][j] = true;
        pick.add((i - iOrg) + "-" + (j - jOrg));
        int[] dx = {-1,0,1,0};
        int[] dy = {0,1,0,-1};

        for (int x = 0; x < 4; x++) {
            int nI = i + dx[x];
            int nJ = j + dy[x];
            if (nI >= 0 && nJ >= 0 && nI < m && nJ < n && grid[nI][nJ] == 1 && !visited[nI][nJ]) {
                dfsDistIslands(grid, nI, nJ, iOrg, jOrg, visited, m, n, pick, ans);
            }
        }
    }

    public boolean isBipartite(int[][] graph) {
        int n = graph.length;
        int[] color = new int[n];
        for (int i = 0; i < n; i++) {
            color[i] = -1;
        }
        for (int i = 0; i < n; i++) {
            if (color[i] == -1) {
                if (!dfsBipartite(graph, i, 0, color)) return false;
            }
        }
        return true;
    }

    private boolean dfsBipartite(int[][] graph, int i, int col, int[] color) {
        color[i] = col;
        for (int j : graph[i]) {
            if (color[graph[i][j]] == -1) {
                if (dfsBipartite(graph, j, 1-i, color)) return false;
            }
            else if (color[graph[i][j]] == col) return false;
        }
        return true;
    }

    static int[] topoSort(int V, ArrayList<ArrayList<Integer>> adj)
    {
        Stack<Integer> s = new Stack<>();
        boolean[] visited = new boolean[adj.size()];
        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                topoSortUtil(adj, i, s, visited);
            }
        }
        int ans[] = new int[V];
        int i = 0;
        while (!s.empty()) {
            ans[i++] = s.pop();
        }
        return ans;
    }

    private static void topoSortUtil(ArrayList<ArrayList<Integer>> adj, int i, Stack<Integer> s, boolean[] visited) {
        visited[i] = true;
        for (int j : adj.get(i)) {
            if (!visited[j]) {
                topoSortUtil(adj, j, s, visited);
            }
        }
        s.push(i);
    }

    public boolean canFinish(int n, int[][] p) {
        // detect if cycle present or is graph DAG
        int[] degree = new int[n];
        Map<Integer, List<Integer>> adj = new HashMap<>();
        for (int i = 0; i < p.length; i++) {
            if (adj.get(p[i][1]) == null) adj.put(p[i][1], new ArrayList<>());
            adj.get(p[i][1]).add(p[i][0]);
        }
        for (int i = 0; i < p.length; i++) {
            degree[p[i][0]]++;
        }
        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (degree[i] == 0) q.add(i);
        }
        int ans = 0;
        while (!q.isEmpty()) {
            int node = q.remove();
            ans++;
            for (int i : adj.get(node)) {
                degree[i]--;
                if (degree[i] == 0) q.add(i);
            }
        }
        if (ans == n) return true;
        return false;
    }

    public static List<Integer> eventualSafeNodes(int[][] graph) {
        int n = graph.length;
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < n; i++) {
                boolean[] visited = new boolean[n];
                boolean[] pathVisited = new boolean[n];
                if (isCyclicNode(i, graph, visited, pathVisited)) {
                    continue;
                }
                ans.add(i);
        }
        return ans;
    }

    private static boolean isCyclicNode(int i, int[][] graph, boolean[] visited, boolean[] pathVisited) {
        visited[i] = true;
        pathVisited[i] = true;
        for (int j : graph[i]) {
            if (!visited[j]) {
                if (isCyclicNode(j, graph, visited, pathVisited)) {
                    return true;
                }
            }
            else if (pathVisited[j]) {
                return true;
            }
        }
        pathVisited[i] = false;
        return false;
    }

    public static String findOrder(String [] dict, int N, int K)
    {
        ArrayList<ArrayList<Integer>> list = new ArrayList<>();
        for (int i = 0; i < K; i++) {
            list.add(new ArrayList<>());
        }

        for (int i = 0; i < N-1; i++) {
            String s1 = dict[i];
            String s2 = dict[i+1];
            for (int j = 0; j < Math.min(s1.length(), s2.length()); j++) {
                if (s1.charAt(j) != s2.charAt(j)) {
                    list.get(s1.charAt(j) - 'a').add(s2.charAt(j) - 'a');
                }
            }
        }
        int[] ansInt = topoSort(K, list);
        String ans = "";
        for (int i : ansInt) {
            ans += (char)(i+(int)'a');
        }
        return ans;
    }

    // shortest path

    public int[] shortestPath(int[][] edges,int n,int m ,int src) {
        List<List<Integer>> adj = new ArrayList<>();
        int[] ans  = new int[n];
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
            ans[i] = Integer.MAX_VALUE;
        }
        for (int i = 0; i < m; i++) {
            adj.get(edges[i][0]).add(edges[i][1]);
            adj.get(edges[i][1]).add(edges[i][0]);
        }

        Queue<Integer> q = new LinkedList<>();
        q.add(src);
        ans[src] = 0;
        while (!q.isEmpty()) {
            int node = q.remove();
            for (int i : adj.get(node)) {
                if (ans[node] + 1 < ans[i]) {
                    ans[i] = ans[node] + 1;
                    q.add(i);
                }
            }
        }
        for (int i = 0; i < n; i++) {
            if (ans[i] == Integer.MAX_VALUE) ans[i] = -1;
        }
        return ans;
    }

    public int[] shortestPath(int n,int m, int[][] edges) {
        List<List<Pair<Integer, Integer>>> adj = new ArrayList<>();
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            List<Pair<Integer, Integer>> list = new ArrayList<>();
            adj.add(list);
            ans[i] = Integer.MAX_VALUE;
        }

        for (int i = 0; i < m; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            int w = edges[i][2];
            adj.get(u).add(new Pair<Integer, Integer>(v, w));
        }

        boolean[] vis = new boolean[n];
        Stack<Integer> s = new Stack<>();
        for (int i = 0; i < n; i++) {
            if (vis[i] == false) {
                topoSortSP(vis, adj, i, s);
            }
        }
        ans[0] = 0;
        while (!s.empty()) {
            int node = s.pop();
            for (Pair<Integer, Integer> p : adj.get(node)) {
                int v = p.first;
                int w = p.second;
                if (ans[node] + w < ans[v]) {
                    ans[v] = ans[node] + w;
                }
            }
        }
        for (int i = 0; i < n; i++) {
            if (ans[i] == Integer.MAX_VALUE) {
                ans[i] = -1;
            }
        }
        return ans;
    }

    private void topoSortSP(boolean[] vis, List<List<Pair<Integer, Integer>>> adj, int i, Stack<Integer> s) {
        vis[i] = true;
        for (Pair<Integer, Integer> p : adj.get(i)) {
            if (vis[p.first] == false) {
                topoSortSP(vis, adj, p.first, s);
            }
        }
        s.push(i);
    }

    static int[] dijkstra(int n, ArrayList<ArrayList<ArrayList<Integer>>> adj, int S)
    {
        PriorityQueue<Pair<Integer, Integer>> pq = new PriorityQueue<>((x,y) -> x.first-y.first); // (weight, node)
        int[] dist = new int[n+1];
        int[] parent = new int[n+1];
        for (int i = 0; i < n; i++) {
            dist[i] = Integer.MAX_VALUE;
            parent[i] = i;
        }
        pq.add(new Pair<Integer, Integer>(0,1));
        while (!pq.isEmpty()) {
            int wt = pq.peek().first;
            int node = pq.remove().second;
            for (ArrayList l : adj.get(node)) {
                int edWt = (int) l.get(1);
                int edNode = (int) l.get(0);
                if (wt + edWt < dist[edNode]) {
                    dist[edNode] = wt + edWt;
                    pq.add(new Pair<Integer, Integer>(wt + edWt, edNode));
                    parent[edNode] = node;
                }
            }
        }

        List<Integer> path = new ArrayList<>();
        if (dist[n] == Integer.MAX_VALUE) {
            path.add(-1);
        }
        else {
            int node = n;
            while (parent[node] != node) {
                path.add(node);
                node = parent[node];
            }
            path.add(1);
            Collections.reverse(path);
        }
        int[] ans = new int[path.size()];
        for (int i = 0; i < path.size(); i++) {
            ans[i] = path.get(i);
        }
        return ans;
    }

    public static int shortestPathBinaryMatrix(int[][] grid) {
        int n = grid.length*grid.length;
        int dim = grid.length;
        int[] ans  = new int[n];
        for (int i = 0; i < n; i++) {
            ans[i] = Integer.MAX_VALUE;
        }

        Queue<Integer> q = new LinkedList<>();
        q.add(0);
        ans[0] = 0;
        int[] dx = {-1,0,1,1,1,0,-1,-1};
        int[] dy = {-1,-1,-1,0,1,1,1,0};
        while (!q.isEmpty()) {
            int node = q.remove();
            int row = node/dim;
            int col = node%dim;
            for (int i = 0; i < 8; i++) {
                int nRow = row + dx[i];
                int nCol = col + dy[i];
                if (nRow >= 0 && nRow < dim && nCol >= 0 && nCol < dim && grid[nRow][nCol] == 0) {
                    int newNode = dim*nRow + nCol;
                    if (ans[node] + 1 < ans[newNode]) {
                        ans[newNode] = ans[node] + 1;
                        q.add(newNode);
                    }
                }
            }
        }
        if (ans[n-1] == -1) return -1;
        return ans[n-1]+1;
    }

    class PairT {
        int diffW;
        int r;
        int c;

        public PairT(int diffW, int r, int c) {
            this.diffW = diffW;
            this.r = r;
            this.c = c;
        }
    }

    public int minimumEffortPath(int[][] heights) {
        int n = heights.length;
        int m = heights[0].length;
        int[][] dist = new int[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                dist[i][j] = (int)(1e9);
            }
        }
        PriorityQueue<PairT> pq = new PriorityQueue<>((x,y) -> x.diffW-y.diffW);
        pq.add(new PairT(0,0,0));
        int[] dx = {-1,0,1,0};
        int[] dy = {0,1,0,-1};
        while (!pq.isEmpty()) {
            PairT pT = pq.remove();
            int wt = pT.diffW;
            int row = pT.r;
            int col = pT.c;

            if (row == n-1 && col == m-1) return wt;
            for (int i = 0; i < 4; i++) {
                int nRow = row + dx[i];
                int nCol = col + dy[i];
                int diffW = Math.max(wt, Math.abs(dist[nRow][nCol]-dist[row][col]));
                if (diffW < dist[nRow][nCol]) {
                    dist[nRow][nCol] = diffW;
                    pq.add(new PairT(diffW, nRow, nCol));
                }
            }
        }
        return -1;
    }

    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        Map<Integer, List<int[]>> adj = new HashMap<>();
        for (int i = 0; i < n; i++) {
            adj.put(i, new ArrayList<>());
        }
        for (int[] flight : flights) {
            int u = flight[0];
            int v = flight[1];
            int w = flight[2];
            adj.get(u).add(new int[]{v, w});
        }

        PriorityQueue<int[]> pq = new PriorityQueue<>((x,y) -> x[1]-y[1]); // node, distance, moves
        int[] dist = new int[n];
        Arrays.fill(dist, -1);
        int[] maxMovesUptoNode = new int[n];
        Arrays.fill(maxMovesUptoNode, Integer.MAX_VALUE);
        pq.add(new int[]{src, 0, 0});
        dist[src] = 0;

        while (!pq.isEmpty()) {
            int[] cur = pq.remove();
            int node = cur[0];
            int tillNowCost = cur[1];
            int moves = cur[2];

            if (maxMovesUptoNode[node] < moves) continue;
            maxMovesUptoNode[node] = moves;

            for (int[] flight : adj.get(node)) {
                int to = flight[0];
                int cost = flight[1] + tillNowCost;

                if (dist[to] == -1 || dist[to] < cost) {
                    dist[to] = cost;
                }

                if (moves < k) {
                    pq.add(new int[]{to, cost, moves+1});
                }
            }
        }
        return dist[dst];
    }

    public int networkDelayTime(int[][] times, int n, int k) {
        Map<Integer, List<int[]>> adj = new HashMap<>();
        for (int i = 0; i < n; i++) {
            adj.put(i, new ArrayList<>());
        }
        for (int[] list : times) {
            int u = list[0];
            int v = list[1];
            int w = list[2];
            adj.get(u).add(new int[]{v, w});
        }

        Set<Integer> visited = new HashSet<>();
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        pq.add(new int[]{k, 0});
        int ans = 0;

        while (!pq.isEmpty()) {
            int[] cur = pq.remove();
            int node = cur[0];
            int wt = cur[1];
            if (visited.contains(node)) continue;
            visited.add(node);
            ans = wt;
            for (int[] val : adj.get(node)) {
                pq.add(new int[]{val[0], val[1]});
            }
        }
        return visited.size() == n ? ans : -1;
    }

    public int countPaths(int n, int[][] roads) {
        Map<Integer, List<int[]>> adj = new HashMap<>();
        for (int i = 0; i < n; i++) {
            adj.put(i, new ArrayList<>());
        }
        int[] dist = new int[n];
        int[] ways = new int[n];
        for (int i = 0; i < n; i++) {
            dist[i] = Integer.MAX_VALUE;
            ways[i] = 0;
        }
        for (int[] list : roads) {
            int u = list[0];
            int v = list[1];
            int w = list[2];
            adj.get(u).add(new int[]{v, w});
            adj.get(v).add(new int[]{u, w});
        }
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        pq.add(new int[]{0, 0});
        dist[0] = 0;
        ways[0] = 1;
        Map<Integer, Integer> m = new HashMap<>();
        int mod = (int)(1e9+7);
        while (!pq.isEmpty()) {
            int[] cur = pq.remove();
            int node = cur[0];
            int wt = cur[1];
            for (int[] val : adj.get(node)) {
                int to = val[0];
                int newDist = wt + val[1];
                if (newDist < dist[to]) {
                    dist[to] = newDist;
                    pq.add(new int[]{to, newDist});
                    ways[to] = ways[node];
                }
                else if (newDist == dist[to]) {
                    ways[to] = (ways[to] + ways[node])%mod;
                }
            }
        }

        return ways[n-1]%mod;
    }

    int minimumMultiplications(int[] arr, int start, int end) {
        int[] dist = new int[10000];
        int mod = 100000;
        Arrays.fill(dist, Integer.MAX_VALUE);
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{start, 0}); // {node, steps}
        dist[start] = 0;
        while (!q.isEmpty()) {
            int[] cur = q.remove();
            int node = cur[0];
            int steps = cur[1];
            if (node == end) {
                return steps;
            }
            for (int i : arr) {
                int newNode = (node*i) % mod;
                if (steps+1 < dist[newNode]) {
                    dist[newNode] = steps + 1;
                    q.add(new int[]{newNode, steps+1});
                }
            }
        }
        return -1;
    }

    static int[] bellman_ford(int V, ArrayList<ArrayList<Integer>> edges, int S) {
        int[] dist = new int[V];
        Arrays.fill(dist, (int)(1e8));
        dist[S] = 0;

        for (int i = 0; i < V-1; i++) {
            for (ArrayList<Integer> edge : edges) {
                int u = edge.get(0);
                int v = edge.get(1);
                int w = edge.get(2);
                if (dist[u] != (1e8) && dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w;
                }
            }
        }
        for (ArrayList<Integer> edge : edges) {
            int u = edge.get(0);
            int v = edge.get(1);
            int w = edge.get(2);
            if (dist[u] != (1e8) && dist[u] + w < dist[v]) {
                return new int[]{-1};
            }
        }
        return dist;
    }

    public void shortest_distance(int[][] matrix)
    {
        int n = matrix.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == -1) {
                    matrix[i][j] = (int)(1e8);
                }
                if (i == j) {
                    matrix[i][j] = 0;
                }
            }
        }
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    matrix[i][j] = Math.min(matrix[i][j], matrix[i][k] + matrix[k][j]);
                }
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == (int)(1e8)) {
                    matrix[i][j] = -1;
                }
            }
        }
    }

    public static int findTheCity(int n, int[][] edges, int distanceThreshold) {
        int[][] matrix = new int[n][n];
        for (int[] edge : edges) {
            matrix[edge[0]][edge[1]] = edge[2];
            matrix[edge[1]][edge[0]] = edge[2];
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][j] = (int)(1e8);
                }
                if (i == j) {
                    matrix[i][j] = 0;
                }
            }
        }
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    matrix[i][j] = Math.min(matrix[i][j], matrix[i][k] + matrix[k][j]);
                }
            }
        }
        int minCount = Integer.MAX_VALUE;
        int ans = 0;
        for (int i = 0; i < n; i++) {
            int count = 0;
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] < distanceThreshold) {
                    count++;
                }
            }
            if (count <= minCount) {
                minCount = count;
                ans = i;
            }
        }
        return ans;
    }

    static int spanningTree(int V, int E, int edges[][]){
        // prim's algo
        int[] vis = new int[V];
        int sum = 0;
        Map<Integer, List<int[]>> m = new HashMap<>();
        for (int i = 0; i < V; i++) {
            m.put(i, new ArrayList<>());
        }
        for (int[] edge : edges) {
            m.get(edge[0]).add(new int[]{edge[0], edge[1]});
            m.get(edge[1]).add(new int[]{edge[0], edge[2]});
        }
        PriorityQueue<int[]> pq = new PriorityQueue<>((x,y) -> x[0]-y[0]);
        pq.add(new int[]{0,0});
        while (!pq.isEmpty()) {
            int w = pq.peek()[0];
            int node = pq.remove()[1];
            if (vis[node] == 1) continue;
            vis[node] = 1;
            sum += w;
            for (int[] edge : m.get(node)) {
                if (vis[edge[0]] == 0) {
                    pq.add(new int[]{edge[1], edge[0]});
                }
            }
        }
        return sum;
    }
}

