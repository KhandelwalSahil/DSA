package Graph;

import java.util.*;

public class DisjointSet {
    List<Integer> rank = new ArrayList<>();
    List<Integer> parent = new ArrayList<>();
    List<Integer> size = new ArrayList<>();
    public DisjointSet(int n) {
        for (int i = 0; i <= n; i++) {
            rank.add(0);
            parent.add(i);
            size.add(1);
        }
    }

    public int findUPar(int node) {
        if (node == parent.get(node)) {
            return node;
        }
        int ulp = findUPar(parent.get(node));
        parent.set(node, ulp);
        return parent.get(node);
    }

    public void unionByRank(int u, int v) {
        int ulp_u = findUPar(u);
        int ulp_v = findUPar(v);
        if (ulp_u == ulp_v) return;
        if (rank.get(ulp_u) < rank.get(ulp_v)) {
            parent.set(ulp_u, ulp_v);
        } else if (rank.get(ulp_v) < rank.get(ulp_u)) {
            parent.set(ulp_v, ulp_u);
        } else {
            parent.set(ulp_v, ulp_u);
            int rankU = rank.get(ulp_u);
            rank.set(ulp_u, rankU + 1);
        }
    }

    public void unionBySize(int u, int v) {
        int ulp_u = findUPar(u);
        int ulp_v = findUPar(v);
        if (ulp_u == ulp_v) return;
        if (size.get(ulp_u) < size.get(ulp_v)) {
            parent.set(ulp_u, ulp_v);
            size.set(ulp_v, size.get(ulp_v) + size.get(ulp_u));
        } else {
            parent.set(ulp_v, ulp_u);
            size.set(ulp_u, size.get(ulp_u) + size.get(ulp_v));
        }
    }
}
class Edge implements Comparable<Edge> {
    int src, dest, weight;
    Edge(int _src, int _dest, int _wt) {
        this.src = _src; this.dest = _dest; this.weight = _wt;
    }
    // Comparator function used for
    // sorting edgesbased on their weight
    public int compareTo(Edge compareEdge) {
        return this.weight - compareEdge.weight;
    }
}

class SolutionMakeConnected {
    public int makeConnected(int n, int[][] connections) {
        DisjointSet ds = new DisjointSet(n);
        int extraEdge = 0;

        for (int i = 0; i < connections.length; i++) {
            int u = connections[i][0];
            int v = connections[i][1];
            if (ds.findUPar(u) != ds.findUPar(v)) {
                ds.unionByRank(u, v);
            }
            else {
                extraEdge++;
            }
        }
        int noOfComp = 0;
        for (int i = 0; i < n; i++) {
            if (ds.parent.get(i) == i) noOfComp++;
        }
        int edgesNeed = noOfComp - 1;
        if (edgesNeed <= extraEdge) return edgesNeed;
        return -1;
    }
}

class SolutionRemoveStones {
    public int removeStones(int[][] stones) {
        int maxRow = 0;
        int maxCol = 0;
        for (int i = 0; i < stones.length; i++) {
            maxRow = Math.max(maxRow, stones[i][0]);
            maxCol = Math.max(maxCol, stones[i][1]);
        }
        DisjointSet ds = new DisjointSet(maxRow + maxCol + 1);
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < stones.length; i++) {
            int row = stones[i][0];
            int col = stones[i][1] + maxRow + 1;
            if (ds.findUPar(row) != ds.findUPar(col)) {
                ds.unionByRank(row, col);
                set.add(row);
                set.add(col);
            }
        }
        int noOfGrps = 0;
        for (int i : set) {
            if (ds.findUPar(i) == i) {
                noOfGrps++;
            }
        }
        return stones.length - noOfGrps;
    }
}

class SolutionAccountsMerge {
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        Map<String, Integer> mailToIndex = new HashMap<>();
        int n = accounts.size();
        DisjointSet ds = new DisjointSet(n);
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < accounts.get(i).size(); j++) {
                String mail = accounts.get(i).get(j);
                if (mailToIndex.get(mail) != null) {
                    ds.unionByRank(i, mailToIndex.get(mail));
                }
                else {
                    mailToIndex.put(mail, i);
                }
            }
        }
        List<String>[] mergedMails = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            mergedMails[i] = new ArrayList<>();
        }
        for (Map.Entry<String, Integer> it : mailToIndex.entrySet()) {
            String mail = it.getKey();
            int node = ds.findUPar(it.getValue());
            mergedMails[node].add(mail);
        }
        List<List<String>> ans = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (mergedMails[i].size() == 0) {
                continue;
            }
            Collections.sort(mergedMails[i]);
            List<String> pick = new ArrayList<>();
            pick.add(accounts.get(i).get(0));
            for (String mail : mergedMails[i]) {
                pick.add(mail);
            }
            ans.add(pick);
        }
        return ans;
    }
}

class SolutionNumIslands {
    // Function to find the number of islands.
    public static int numIslands(char[][] grid) {
        int n = 0;
        Map<Integer, List<Integer>> m = new HashMap<>();
        int cX = grid.length;
        int cY = grid[0].length;
        for (int i = 0; i < cX; i++) {
            for (int j = 0; j < cY; j++) {
                if (grid[i][j] == '1') {
                    n++;
                    grid[i][j] = (char) n;
                }
            }
        }
        int[] x = {-1, 0, 1, 1, 1, 0, -1, -1};
        int[] y = {1, 1, 1, 0, -1, -1, -1, 0};
        for (int i = 0; i < cX; i++) {
            for (int j = 0; j < cY; j++) {
                if (grid[i][j] != '0') {
                    for (int dir = 0; dir < 8; dir++) {
                        int newX = i + x[dir];
                        int newY = j + y[dir];
                        if (newX >= 0 && newX < cX && newY >= 0 && newY < cY) {
                            int u = Integer.valueOf(grid[i][j]);
                            int v = Integer.valueOf(grid[newX][newY]);
                            if (grid[newX][newY] != '0') {
                                if (m.get(u) == null) {
                                    m.put(u, new ArrayList<>());
                                }
                                m.get(u).add(v);
                            }
                        }
                    }
                }
            }
        }
        DisjointSet ds = new DisjointSet(n);
        Set<Integer> s = new HashSet<>();
        for (int u : m.keySet()) {
            for (int v : m.get(u)) {
                if (ds.findUPar(u) != ds.findUPar(v)) {
                    ds.unionByRank(u, v);
                    s.add(u);
                    s.add(v);
                }
            }
        }
        int ans = 0;
        for (int i : s) {
            if (ds.findUPar(i) != i) {
                ans++;
            }
        }
        return n - ans;
    }
}

class SolutionLargestIsland {
    public int largestIsland(int[][] grid) {
        int n = grid.length;
        DisjointSet ds = new DisjointSet(n*n);
        int[] dx = {-1, 0, 1, 0};
        int[] dy = {0, -1, 0, 1};
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) {
                    continue;
                }
                for (int dir = 0; dir < 4; dir++) {
                    int nR = i + dx[dir];
                    int nC = j + dy[dir];
                    if (nR >= 0 && nR < n && nC >= 0 && nC < n && grid[nR][nC] == 1) {
                        int node = i*n + j;
                        int adjNode = nR*n + nC;
                        ds.unionBySize(node, adjNode);
                    }
                }
            }
        }
        int mx = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    continue;
                }
                Set<Integer> parents = new HashSet<>();
                for (int dir = 0; dir < 4; dir++) {
                    int nR = i + dx[dir];
                    int nC = j + dy[dir];
                    if (nR >= 0 && nR < n && nC >= 0 && nC < n && grid[nR][nC] == 1) {
                        int node = nR*n + nC;
                        parents.add(ds.findUPar(node));
                    }
                }
                int cur = 0;
                for (int val : parents) {
                    cur += ds.size.get(val);
                }
                mx = Math.max(mx, cur+1);
            }
        }
        for (int i = 0; i < n*n; i++) {
            mx = Math.max(mx, ds.size.get(ds.findUPar(i)));
        }
        return mx;
    }
}

class SolutionSwimInWater {
    public int swimInWater(int[][] grid) {
        int n = grid.length;
        DisjointSet ds = new DisjointSet(n*n);
        int time = 0;
        while (ds.findUPar(0) != ds.findUPar(n*n-1)) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] > time) continue;
                    if (i + 1 < n && grid[i+1][j] <= time) ds.unionBySize(i*n+j, (i+1)*n+j);
                    if (j + 1 < n && grid[i][j+1] <= time) ds.unionBySize(i*n+j, i*n+j+1);
                }
            }
            time++;
        }
        return time-1;
    }
}

class Solution {
    private int timer = 1;
    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        int[] visited = new int[n];
        int[] time = new int[n];
        int[] minTime = new int[n];
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
        for (List<Integer> edge : connections) {
            int u = edge.get(0);
            int v = edge.get(1);
            adj.get(u).add(v);
        }
        List<List<Integer>> ans = new ArrayList<>();
        dfsCriticalConnections(0, -1, n, visited, time, minTime, adj, ans);
        return ans;
    }

    private void dfsCriticalConnections(int node, int parent, int n, int[] visited, int[] time, int[] minTime, List<List<Integer>> adj, List<List<Integer>> ans) {
        time[node] = timer;
        minTime[node] = timer;
        timer++;
        visited[node] = 1;
        for (int i : adj.get(node)) {
            if (i == parent) continue;
            if (visited[i] == 0) {
                dfsCriticalConnections(i, node, n, visited, time, minTime, adj, ans);
                if (minTime[i] > time[node]) {
                    ans.add(Arrays.asList(i, node));
                }
            }
            else {
                minTime[node] = Math.min(minTime[node], minTime[i]);
            }
        }
    }
}

class Solution1 {
    // Function to find the number of islands.
    int[] dx = new int[]{1,1,1,-1,-1,-1,0,0};
    int[] dy = new int[]{0,1,-1,-1,0,1,-1,1};
    public int numIslands(char[][] grid) {
        int ans = 0;
        int n = grid.length;
        int m = grid[0].length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == '1') {
                    dfs(i, j, grid);
                    ans++;
                }
            }
        }
        return ans;
    }


    public void dfs(int i, int j, char[][] grid) {
        grid[i][j] = 'X';
        for (int dir = 0; dir < 8; dir++) {
            int nI = dx[dir] + i;
            int nJ = dy[dir] + j;
            if (nI < 0 || nJ < 0 || nI >= grid.length || nJ >= grid[0].length) {
                continue;
            }
            dfs(nI, nJ, grid);
        }
    }
}