package DP;

import java.util.*;

public class DynamicProgramming {
}

class SolutionFib {

    static long mod = 1000000007;
    static long topDown(int n) {
        long[] dp = new long[n+1];
        Arrays.fill(dp, -1);
        return fibTopDown(n, dp);
    }

    private static long fibTopDown(int n, long[] dp) {
        if (n <= 1) return n;
        if (dp[n] != -1) return dp[n];
        return dp[n] = (fibTopDown(n-1, dp)%mod + fibTopDown(n-2, dp)%mod)%mod;
    }

    static long bottomUp(int n) {
        long prev1 = 0, prev = 1;
        for (int i = 2; i <= n; i++) {
            long cur = (prev%mod + prev1%mod)%mod;
            prev1 = prev;
            prev = cur;
        }
        return prev%mod;
    }
}

class SolutionMinEnergy {
    // jump from 0 to n-1, 1 or 2 steps, arr[j]-arr[i] -> energy consume -> min
    public int minimumEnergy(int arr[],int N){
//        return minimumEnergyRec(arr, N-1);
        return minimumEnergyDP(arr, N);
    }

    private int minimumEnergyDP(int[] arr, int n) {
        int prev = 0;
        int prev1 = 0;
        for (int i = 1; i < n; i++) {
            int left = prev + Math.abs(arr[i] - arr[i-1]);
            int right = Integer.MAX_VALUE;
            if (i > 1) {
                right = prev1 + Math.abs(arr[i] - arr[i-2]);
            }
            prev1 = prev;
            prev = Math.min(left, right);
        }
        return prev;
    }

    private int minimumEnergyRec(int[] arr, int n) {
        if (n == 0) return 0;
        int left = Math.abs(arr[n-1] - arr[n]) + minimumEnergyRec(arr, n-1);
        int right = Integer.MAX_VALUE;
        if (n > 1) {
            right = Math.abs(arr[n-2] - arr[n]) + minimumEnergyRec(arr, n-2);
        }
        return Math.min(left, right);
    }
}

class SolutionMinCost {
    public int minimizeCost(int arr[],int n,int k){
       int[] dp = new int[n];
       dp[0] = 0;
       for (int i = 1; i < n; i++) {
           int minSteps = Integer.MAX_VALUE;
           for (int j = 1; j <= k; j++) {
               if (i-j >= 0) {
                   minSteps = Math.min(minSteps, dp[i-j] + Math.abs(arr[i] - arr[i-j]));
               }
           }
           dp[i] = minSteps;
       }
       return dp[n-1];
    }
}

class SolutionFindMaxSum {
    int findMaxSum(int arr[], int n) {
        return findMaxSumRec(arr, n-1);
    }

    private int findMaxSumRec(int[] arr, int n) {
        if (n < 0) return 0;
        if (n == 0) return arr[n];
        int incl = arr[n] + findMaxSumRec(arr, n - 2);
        int excl = findMaxSumRec(arr, n - 1);
        return Math.max(incl, excl);
    }

    private int findMaxSumDP(int[] arr, int n) {
        int prev = arr[0], prev2 = 0;
        for (int i = 1; i < n; i++) {
            int cur = Math.max(arr[i] + prev2, prev);
            prev2 = prev;
            prev = cur;
        }
        return prev;
    }
}

class SolutionMaximumPoints {
    public int maximumPoints(int points[][],int N){
        return maxPoints(points, N-1, -1);
    }

    private int maxPoints(int[][] points, int n, int i) {
        if (n == 0) {
            int maxi = Integer.MIN_VALUE;
            for (int j = 0; j < 3; j++) {
                if (j != i) maxi = Math.max(maxi, points[0][j]);
            }
            return maxi;
        }
        int maxPoints = Integer.MIN_VALUE;
        for (int j = 0; j < 3; j++) {
            if (i != j) maxPoints = Math.max(maxPoints, points[n][j] + maxPoints(points, n-1, j));
        }
        return maxPoints;
    }

    private int maxPointsDP(int[][] points, int n) {
        // with space optimization
        int[] prev = new int[4];
        prev[0] = Math.max(points[0][1], points[0][2]);
        prev[1] = Math.max(points[0][0], points[0][2]);
        prev[2] = Math.max(points[0][1], points[0][0]);
        prev[3] = Math.max(points[0][1], Math.max(points[0][2], points[0][0]));

        for (int day = 1; day < n; day++) {
            int[] temp = new int[4];
            for (int last = 0; last < 4; last++) {
                temp[last] = 0;
                for (int task = 0; task < 3; task++) {
                    if (task != last) {
                        temp[last] = Math.max(temp[last], prev[task] + points[day][task]);
                    }
                }
            }
            prev = temp;
        }
        return prev[3];
    }
}

class SolutionGridUniquePath {
    public int uniquePaths(int m, int n) {
        // not space optimized
        int[][] dp = new int[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 && j == 0) {
                    dp[i][j] = 1;
                }
                else {
                    int up = 0, left = 0;
                    if (i > 0) {
                        up = dp[i-1][j];
                    }
                    if (j > 0) {
                        left = dp[i][j-1];
                    }
                    dp[i][j] = up + left;
                }
            }
        }
        return dp[m-1][n-1];
    }
}

class SolutionUniquePathObs {
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        // space optimized
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        int[] prev = new int[n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (obstacleGrid[i][j] == 1) obstacleGrid[i][j] = -1;
            }
        }
        for (int i = 0; i < m; i++) {
            int[] temp = new int[n];
            for (int j = 0; j < n; j++) {
                if (i == 0 && j == 0) {
                    temp[j] = 1;
                }
                else if (i > 0 && j > 0 && obstacleGrid[i][j] == -1) {
                    temp[j] = 0;
                }
                else {
                    int up = 0, left = 0;
                    if (i > 0) {
                        up = prev[j];
                    }
                    if (j > 0) {
                        left = temp[j-1];
                    }
                    temp[j] = up + left;
                }
            }
            prev = temp;
        }
        return prev[n-1];
    }
}

class SolutionMinPathSum {
    public int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        return minPathSumRec(grid, m-1, n-1);
    }

    private int minPathSumRec(int[][] grid, int m, int n) {
        if (m == 0 && n == 0) return grid[0][0];
        int up = Integer.MAX_VALUE;
        int left = Integer.MAX_VALUE;
        if (m - 1 >= 0) {
            up = grid[m][n] + minPathSumRec(grid, m-1, n);
        }
        if (n - 1 >= 0) {
            left = grid[m][n] + minPathSumRec(grid, m, n-1);
        }
        return Math.min(up, left);
    }

    private int minPathSumDP(int[][] grid, int m, int n) {
        // can be space optimized as previous one
        int[][] dp = new int[m][n];
        dp[0][0] = grid[0][0];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 && j == 0) {
                    dp[i][j] = grid[0][0];
                }
                else {
                    int up = Integer.MAX_VALUE;
                    int left = Integer.MAX_VALUE;
                    if (i - 1 >= 0) {
                        up = grid[i][j] + dp[i-1][j];
                    }
                    if (j - 1 >= 0) {
                        left = grid[i][j] + dp[i][j-1];
                    }
                    dp[i][j] = Math.min(up, left);
                }
            }
        }
        return dp[m-1][n-1];
    }
}

class SolutionMinimumTriangle {
    public int minimumTotal(List<List<Integer>> triangle) {
        return minimumTotalRec(triangle, 0, 0, triangle.size());
    }

    private int minimumTotalRec(List<List<Integer>> triangle, int i, int j, int n) {
        if (i == n-1) {
            return triangle.get(i).get(j);
        }
        int down = triangle.get(i).get(j) + minimumTotalRec(triangle, i+1, j, n);
        int diagonal = triangle.get(i).get(j) + minimumTotalRec(triangle, i+1, j+1, n);
        return Math.min(down, diagonal);
    }

    private int minimumTotalDP(List<List<Integer>> triangle, int i, int j, int n) {
        int[] prev = new int[n];
        for (int col = 0; col < n; col++) {
            // for n-1 th row
            prev[col] = triangle.get(n-1).get(col);
        }
        for (int row = n-2; row >= 0; row--) {
            int[] cur = new int[n];
            for (int col = row; col >= 0; col--) {
                int down = triangle.get(row).get(col) + prev[col];
                int diagonal = triangle.get(row).get(col) + prev[col+1];
                cur[col] = Math.min(down, diagonal);
            }
            prev = cur;
        }
        return prev[0];
    }
}

class SolutionMinFallingPathSum {
    public int minFallingPathSum(int[][] matrix) {
        return minimumTotalDP(matrix, 0, 0, matrix.length);
    }

    private int minimumTotalDP(int[][] matrix, int i, int j, int n) {
        int[] prev = new int[n];
        for (int col = 0; col < n; col++) {
            // for n-1 th row
            prev[col] = matrix[n-1][col];
        }
        for (int row = n-2; row >= 0; row--) {
            int[] cur = new int[n];
            for (int col = n-1; col >= 0; col--) {
                int down = matrix[row][col] + prev[col];
                int diagonalLeft = Integer.MAX_VALUE, diagonalRight = Integer.MAX_VALUE;
                if (col+1 < n) diagonalRight = matrix[row][col] + prev[col+1];
                if (col-1 >= 0) diagonalLeft = matrix[row][col] + prev[col-1];
                cur[col] = Math.min(down, Math.min(diagonalLeft, diagonalRight));
            }
            prev = cur;
        }
        int mini = Integer.MAX_VALUE;
        for (int k : prev) {
            mini = Math.min(mini, k);
        }
        return mini;
    }
}

class SolutionNinjaSolve {
    public int solve(int n, int m, int grid[][]) {
        int[][] front = new int[m][m];
        int[][] cur = new int[m][m];
        for (int j1 = 0; j1 < m; j1++) {
            for (int j2 = 0; j2 < m; j2++) {
                if (j1 == j2) front[j1][j2] = grid[n - 1][j1];
                else front[j1][j2] = grid[n - 1][j1] + grid[n - 1][j2];
            }
        }
        for (int i = n - 2; i >= 0; i--) {
            for (int j1 = 0; j1 < m; j1++) {
                for (int j2 = 0; j2 < m; j2++) {
                    int maxi = (int) -1e8;
                    for (int dj1 = -1; dj1 <= 0; dj1++) {
                        for (int dj2 = -1; dj2 <= 0; dj2++) {
                            int value = 0;
                            if (j1 == j2) {
                                value = grid[i][j1];
                            } else {
                                value = grid[i][j1] + grid[i][j2];
                            }
                            if (j1 + dj1 >= 0 && j1 + dj1 < m && j2 + dj2 >= 0 && j2 + dj2 < m) {
                                value += front[j1 + dj1][j2 + dj2];
                            } else {
                                value += (int) -1e8;
                            }
                            maxi = Math.max(maxi, value);
                        }
                    }
                    front[j1][j2] = maxi;
                }
            }
            front = cur;
        }
        return front[0][m - 1];
    }
}

class SolutionSubsetSum {

    static Boolean isSubsetSum(int N, int arr[], int sum){
//        return isSubsetSumRec(N-1, sum, arr);
        return isSubsetSumDP(N, sum, arr);
    }

    private static Boolean isSubsetSumRec(int n, int target, int[] arr) {
        if (n == 0) {
            return arr[0] == target;
        }
        if (target == 0) {
            return true;
        }
        boolean notTaken = isSubsetSumRec(n-1, target, arr);
        boolean taken = false;
        if (target >= arr[n]) {
            taken = isSubsetSumRec(n-1, target-arr[n], arr);
        }
        return notTaken || taken;
    }

    private static Boolean isSubsetSumDP(int n, int target, int[] arr) {
        boolean[] prev = new boolean[target+1];
        prev[0] = true;

        if (target >= arr[0]) prev[arr[0]] = true;
        for (int i = 1; i < n; i++) {
            boolean[] cur = new boolean[target+1];
            cur[0] = true;
            for (int j = 1; j <= target; j++) {
                boolean notTaken = prev[j];
                boolean taken = false;
                if (j >= arr[i]) {
                    taken = prev[j-arr[i]];
                }
                cur[j] = taken||notTaken;
            }
            prev = cur;
        }
        return prev[target];
    }
}

class SolutionCanPartition {
    public boolean canPartition(int[] nums) {
        int sum = 0;
        for (int i : nums) {
            sum += i;
        }
        if (sum % 2 != 0) return false;
        return isSubsetSumDP(nums.length, sum/2, nums);
    }

    private static Boolean isSubsetSumDP(int n, int target, int[] arr) {
        boolean[] prev = new boolean[target+1];
        prev[0] = true;

        if (target >= arr[0]) prev[arr[0]] = true;
        for (int i = 1; i < n; i++) {
            boolean[] cur = new boolean[target+1];
            cur[0] = true;
            for (int j = 1; j <= target; j++) {
                boolean notTaken = prev[j];
                boolean taken = false;
                if (j >= arr[i]) {
                    taken = prev[j-arr[i]];
                }
                cur[j] = taken||notTaken;
            }
            prev = cur;
        }
        return prev[target];
    }
}

class SolutionMinDifference {
    public int minimumDifference(int[] nums) {
        int sum = 0;
        for (int i : nums) {
            sum += i;
        }
        boolean[] prev = isSubsetSumDP(nums.length, sum, nums);
        int mini = Integer.MAX_VALUE;
        for (int s = 0; s<= sum/2; s++) {
            if (prev[s]) {
                mini = Math.min(mini, Math.abs(sum-s)-s);
            }
        }
        return mini;
    }

    private static boolean[] isSubsetSumDP(int n, int target, int[] arr) {
        boolean[] prev = new boolean[target+1];
        prev[0] = true;

        if (target >= arr[0]) prev[arr[0]] = true;
        for (int i = 1; i < n; i++) {
            boolean[] cur = new boolean[target+1];
            cur[0] = true;
            for (int j = 1; j <= target; j++) {
                boolean notTaken = prev[j];
                boolean taken = false;
                if (j >= arr[i]) {
                    taken = prev[j-arr[i]];
                }
                cur[j] = taken||notTaken;
            }
            prev = cur;
        }
        return prev;
    }
}

class SolutionPerfectSum {

    public int perfectSum(int arr[],int n, int sum)
    {
//        return perfectSumRec(arr, n-1, sum);
        return perfectSumDP(arr, n, sum);
    }

    private int perfectSumRec(int[] arr, int n, int sum) {
        if (sum == 0) return 1;
        if (n == 0) return sum == arr[0] ? 1 : 0;
        return perfectSumRec(arr, n-1, sum-arr[n]) + perfectSumRec(arr, n-1, sum);
    }

    private int perfectSumDP(int[] arr, int n, int sum) {
        int[] prev = new int[sum+1];
        prev[0] = 1;
        int mod = 1000000007;
        // if (sum >= arr[0])
        prev[arr[0]] = 1;
        if (arr[0] == 0) prev[0] = 2;
        for (int i = 1; i < n; i++) {
            int[] cur = new int[sum+1];
            cur[0] = 1;
            for (int j = 0; j <= sum; j++) {
                int notTaken = prev[j];
                int taken = 0;
                if (j >= arr[i]) {
                    taken = prev[j-arr[i]];
                }
                cur[j] = (taken%mod + notTaken%mod)%mod;
            }
            prev = cur;
        }
        return prev[sum];
    }
}

class SolutionCountPartitionsWithDiffD{

    public int countPartitions(int n, int d, int arr[]){
        int sum = 0;
        for (int i : arr) {
            sum += i;
        }
        if (sum-d < 0 || (sum-d) % 2 == 1) return 0;
        return isSubsetSumDP(n, (sum-d)/2, arr);

    }

    private static int isSubsetSumDP(int n, int target, int[] arr) {
        int[] prev = new int[target+1];
        prev[0] = 1;
        int mod = 1000000007;
        if (arr[0] != 0 && arr[0] <= target) prev[arr[0]] = 1;
        if (arr[0] == 0) prev[0] = 2;
        for (int i = 1; i < n; i++) {
            int[] cur = new int[target+1];
            cur[0] = 1;
            for (int j = 0; j <= target; j++) {
                int notTaken = prev[j];
                int taken = 0;
                if (j >= arr[i]) {
                    taken = prev[j-arr[i]];
                }
                cur[j] = (taken + notTaken)%mod;
            }
            prev = cur;
        }
        return prev[target];
    }
}

class SolutionKnapSack
{
    //Function to return max value that can be put in knapsack of capacity W.
    static int knapSack(int W, int wt[], int val[], int n)
    {
//        return knapSackRec(W, wt, val, n-1);
        return knapSackDP(W, wt, val, n);
    }

    private static int knapSackDP(int mW, int[] wt, int[] val, int n) {
        int[][] dp = new int[n][mW+1];
        for (int w = wt[0]; w <= mW; w++) dp[0][w] = val[0];
        for (int i = 1; i < n; i++) {
            for (int w = 0; w <= mW; w++) {
                int notTaken = dp[i-1][w];
                int taken = Integer.MIN_VALUE;
                if (wt[i] <= w) taken = val[i-1] + dp[i][w-wt[i]];
                dp[i][w] = Math.max(notTaken, taken);
            }
        }
        return dp[n-1][mW];
    }

    private static int knapSackRec(int w, int[] wt, int[] val, int n) {
        if (n == 0) {
            if (wt[0] <= w) return val[0];
            else return 0;
        }
        int notTaken = knapSackRec(w, wt, val, n-1);
        int taken = Integer.MIN_VALUE;
        if (wt[n] <= w) taken = val[n] + knapSackRec(w - wt[n], wt, val, n-1);
        return Math.max(notTaken, taken);
    }
}

class SolutionCoinChange {
    public int coinChange(int[] coins, int amount) {
//        return coinChangeRec(coins, amount, coins.length-1);
        return coinChangeDP(coins, amount, coins.length);
    }

    private int coinChangeDP(int[] coins, int amount, int n) {
        int[][] dp = new int[n][amount+1];
        for (int t = 0; t <= amount; t++) {
            if (t % coins[0] == 0) dp[0][t] = t / coins[0];
            else dp[0][t] = (int)1e9;
        }

        for (int i = 1; i < n; i++) {
            for (int t = 0; t <= amount; t++) {
                int notTaken = dp[i-1][t];
                int taken = Integer.MAX_VALUE;
                if (coins[i] <= t) taken = 1 + dp[i][t-coins[i]];
                dp[i][t] = Math.min(notTaken, taken);
            }
        }
        int ans = dp[n-1][amount];
        if (ans >= (int)1e9) return -1;
        return ans;
    }

    private int coinChangeRec(int[] coins, int amount, int n) {
        if (n == 0) {
            if (amount % coins[0] == 0) return amount/coins[0];
            return (int)1e9;
        }
        int notTaken = coinChangeRec(coins, amount, n-1);
        int taken = Integer.MAX_VALUE;
        if (coins[n] <= amount) taken = 1 + coinChangeRec(coins, amount-coins[n], n);
        return Math.min(notTaken, taken);
    }
}

class SolutionFindTargetSumWays {
    public int findTargetSumWays(int[] nums, int target) {
        return findTargetSumWaysRec(nums, target, nums.length-1);
    }

    private int findTargetSumWaysRec(int[] nums, int target, int n) {
        if (target == 0) {
            return 1;
        }
        if (n < 0) {
            return 0;
        }
        int plus = findTargetSumWaysRec(nums, target+nums[n], n-1);
        int minus = findTargetSumWaysRec(nums, target-nums[n], n-1);
        return plus+minus;
    }
}

class SolutionCoinChangeNoOfPossibleWays {
    public int change(int amount, int[] coins) {
        return coinChangeRec(coins, amount, coins.length-1);
    }

    private int coinChangeDP(int[] coins, int amount, int n) {
        int[][] dp = new int[n][amount+1];
        for (int t = 0; t <= amount; t++) {
            if (t % coins[0] == 0) dp[0][t] = 1;
            else dp[0][t] = 0;
        }

        for (int i = 1; i < n; i++) {
            for (int t = 0; t <= amount; t++) {
                int notTaken = dp[i-1][t];
                int taken = 0;
                if (coins[i] <= t) taken = dp[i][t-coins[i]];
                dp[i][t] = taken + notTaken;
            }
        }
        int ans = dp[n-1][amount];
        return ans;
    }

    private int coinChangeRec(int[] coins, int amount, int n) {
        if (n == 0) {
            if (amount % coins[0] == 0) return 1;
            return 0;
        }
        int notTaken = coinChangeRec(coins, amount, n-1);
        int taken = 0;
        if (coins[n] <= amount) taken = coinChangeRec(coins, amount-coins[n], n);
        return taken + notTaken;
    }
}

class SolutionKnapSackUnbounded {
    static int knapSack(int N, int W, int val[], int wt[])
    {
//        return knapSackRec(N-1, W, val, wt);
        return knapSackDP(N, W, val, wt);
    }

    private static int knapSackDP(int n, int mW, int[] val, int[] wt) {
        int[][] dp = new int[n][mW+1];
        for (int w = wt[0]; w <= mW; w++) {
            dp[0][w] = val[0]*((int)w/wt[0]);
        }
        for (int i = 1; i < n; i++) {
            for (int w = 0; w <= mW; w++) {
                int notTaken = dp[i-1][w];
                int taken = Integer.MIN_VALUE;
                if (wt[i] <= w) taken = val[i] + dp[i][w-wt[i]];
                dp[i][w] = Math.max(notTaken,taken);
            }
        }
        return dp[n-1][mW];
    }

    private static int knapSackRec(int n, int w, int[] val, int[] wt) {
        if (n == 0) {
            if (wt[0] <= w) return val[0]*((int)w/wt[0]);
            return 0;
        }
        int notTaken = knapSackRec(n-1, w, val, wt);
        int taken = Integer.MIN_VALUE;
        if (wt[n] <= w) taken = val[n] + knapSackRec(n, w-wt[n], val, wt);
        return Math.max(notTaken, taken);
    }
}

class SolutionCuttingRod {
    public int cutRod(int price[], int n) {
//        return cutRodRec(n-1, n, price);
        return cutRodDP(n, price);
    }

    private int cutRodDP(int n, int[] price) {
        int[][] dp =new int[n][n+1];
        for (int i = 0; i <= n; i++) dp[0][i] = i*price[0];

        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= n; j++) {
                int notTaken = dp[i-1][j];
                int taken = Integer.MIN_VALUE;
                if ((i+1) <= j) taken = price[i] + dp[i][j-(i+1)];
                dp[i][j] = Math.max(notTaken, taken);
            }
        }
        return dp[n-1][n];
    }

    private int cutRodRec(int i, int n, int[] price) {
        if (i == 0) return n*price[i];
        int notTaken = cutRodRec(i-1, n, price);
        int taken = Integer.MIN_VALUE;
        int rodLength = i+1;
        if (rodLength <= n) taken = price[i] + cutRodRec(i, n-rodLength, price);
        return Math.max(notTaken, taken);
    }
}

class SolutionLCSLength {
    public int longestCommonSubsequence(String text1, String text2) {
        int n = text1.length();
        int m = text2.length();
//        return lcsRec(n, m, text1, text2); // shifting of index applied
        return lcsDP(n, m, text1, text2); // shifting of index applied
    }

    private int lcsDP(int n, int m, String text1, String text2) {
        int[][] dp = new int[n+1][m+1];
        for (int i = 0; i <= n; i++) dp[i][0] = 0;
        for (int i = 0; i <= m; i++) dp[0][i] = 0;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (text1.charAt(i-1) == text2.charAt(j-1)) dp[i][j] = 1 + dp[i-1][j-1];
                else dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
            }
        }
        return dp[n][m];
    }

    private int lcsRec(int n, int m, String text1, String text2) {
        if (n == 0 || m == 0) return 0;
        if (text1.charAt(n-1) == text2.charAt(m-1)) return 1 + lcsRec(n-1, m-1, text1, text2);
        return Math.max(lcsRec(n-1, m, text1, text2), lcsRec(n, m-1, text1, text2));
    }
}

class SolutionLCSPrint
{
    public List<String> all_longest_common_subsequences(String s, String t)
    {
        return lcsDP(s.length(), t.length(), s, t);
    }

    private List<String> lcsDP(int n, int m, String text1, String text2) {
        int[][] dp = new int[n+1][m+1];
        for (int i = 0; i <= n; i++) dp[i][0] = 0;
        for (int i = 0; i <= m; i++) dp[0][i] = 0;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (text1.charAt(i-1) == text2.charAt(j-1)) dp[i][j] = 1 + dp[i-1][j-1];
                else dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
            }
        }
        Set<String> ans = new HashSet<>();
        char[] temp = new char[dp[n][m]];
        lcsPrint(n, m, text1, text2, dp, ans, temp, dp[n][m]-1);
        List<String> ansList = new ArrayList<>();
        for (String s : ans) {
            ans.add(s);
        }
        Collections.sort(ansList);
        return ansList;
    }

    private void lcsPrint(int n, int m, String text1, String text2, int[][] dp, Set<String> ans, char[] s, int index) {
        if (n == 0 || m == 0) {
            ans.add(new String(s));
            return;
        }
        if (text1.charAt(n-1) == text2.charAt(m-1)) {
            s[index] = text1.charAt(n-1);
            index--;
            lcsPrint(n-1, m-1, text1, text2, dp, ans, s, index);
            index++;
        }
        else {
            if (dp[n-1][m] == dp[n][m-1]) {
                lcsPrint(n-1, m, text1, text2, dp, ans, s, index);
                lcsPrint(n, m-1, text1, text2, dp, ans, s, index);
            }
            else if (dp[n-1][m] > dp[n][m-1]) {
                lcsPrint(n-1, m, text1, text2, dp, ans, s, index);
            }
            else {
                lcsPrint(n, m-1, text1 , text2, dp, ans , s, index);
            }
        }
    }
}

class SolutionLCSubStr {
    int longestCommonSubstr(String S1, String S2, int n, int m){
        return lcsDP(S1.length(), S2.length(), S1, S2);
    }

    private int lcsDP(int n, int m, String text1, String text2) {
        int[][] dp = new int[n+1][m+1];
        for (int i = 0; i <= n; i++) dp[i][0] = 0;
        for (int i = 0; i <= m; i++) dp[0][i] = 0;
        int ans = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (text1.charAt(i-1) == text2.charAt(j-1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                    ans = Math.max(ans, dp[i][j]);
                }
                else dp[i][j] = 0;
            }
        }
         return ans;
    }
}

class SolutionSuperSeq {
    public String shortestCommonSupersequence(String str1, String str2) {
        int n = str1.length();
        int m = str2.length();
        return lcsDP(n, m ,str1, str2);
    }

    private String lcsDP(int n, int m, String text1, String text2) {
        int[][] dp = new int[n+1][m+1];
        for (int i = 0; i <= n; i++) dp[i][0] = 0;
        for (int i = 0; i <= m; i++) dp[0][i] = 0;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (text1.charAt(i-1) == text2.charAt(j-1)) dp[i][j] = 1 + dp[i-1][j-1];
                else dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
            }
        }

        String ans = "";
        int i = n, j = m;
        while (i > 0 && j > 0) {
            if (text1.charAt(i-1) == text2.charAt(j-1)) {
                ans += text1.charAt(i-1);
                i--;j--;
            }
            else if (dp[i-1][j] > dp[i][j-1]) {
                ans += text1.charAt(i-1);
                i--;
            }
            else {
                ans += text2.charAt(j-1);
                j--;
            }
        }

        while (i > 0) {
            ans += text1.charAt(i-1);
            i--;
        }

        while (j > 0) {
            ans += text2.charAt(j-1);
            j--;
        }
        StringBuilder sb = new StringBuilder(ans);
        sb.reverse();
        return sb.toString();
    }
}

class SolutionNumDistinctSubSequence {
    public int numDistinct(String s, String t) {
        int n = s.length();
        int m = t.length();
//        return numDistinctRec(n, m, s, t); // shifting of index
        return numDistinctDP(n, m, s, t); // shifting of index
    }

    private int numDistinctDP(int n, int m, String s, String t) {
        int[][] dp = new int[n+1][m+1];
        for (int i = 0; i <= n; i++) dp[i][0] = 1;
        for (int i = 1; i <= m; i++) dp[0][i] = 0;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (s.charAt(i-1) == t.charAt(j-1)) {
                    dp[i][j] = dp[i-1][j-1] + dp[i-1][j];
                }
                else {
                    dp[i][j] = dp[i-1][j];
                }
            }
        }
        return dp[n][m];
    }

    private int numDistinctRec(int n, int m, String s, String t) {
        if (m == 0) return 1;
        if (n == 0) return 0;

        if (s.charAt(n-1) == t.charAt(m-1)) {
            return numDistinctRec(n-1, m-1, s, t) + numDistinctRec(n-1, m, s, t);
        }
        else {
            return numDistinctRec(n-1, m, s, t);
        }
    }
}

class SolutionMinDistance {
    public int minDistance(String s, String t) {
        int n = s.length();
        int m = t.length();
//        return minDistanceRec(n, m, s, t); // shifting of index
        return minDistanceDP(n, m, s, t); // shifting of index
    }

    private int minDistanceRec(int n, int m, String s, String t) {
        if (n == 0) return m;
        if (m == 0) return n;

        if (s.charAt(n-1) == t.charAt(m-1)) return minDistanceRec(n-1, m-1, s, t);
        else {
            return 1 + Math.min(minDistanceRec(n-1, m-1, s, t), Math.min(minDistanceRec(n-1, m, s, t), minDistanceRec(n, m-1, s, t)));
        }
    }

    private int minDistanceDP(int n, int m, String s, String t) {
        int[][] dp = new int[n+1][m+1];
        for (int i = 0; i <= n; i++) dp[i][0] = i;
        for (int i = 0; i <= m; i++) dp[0][i] = i;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (s.charAt(i-1) == t.charAt(j-1)) dp[i][j] = dp[i-1][j-1];
                else {
                    dp[i][j] = 1 + Math.min(dp[i][j-1], Math.min(dp[i-1][j-1], dp[i-1][j]));
                }
            }
        }
        return dp[n][m];
    }
}

class SolutionWildcardMatching {
    public boolean isMatch(String s, String t) {
        int n = s.length();
        int m = t.length();
//        return isMatchRec(m, n, t, s); // shifting of index
        return isMatchDP(m, n, t, s); // shifting of index
    }

    private boolean isMatchRec(int n, int m, String s, String t) {
        if (n == 0 && m == 0) return true;
        if (n == 0 && m > 0) return false;
        if (n > 0 && m == 0) {
            for (int i = 0; i < n; i++) {
                if (s.charAt(i) == '*') continue;
                return false;
            }
            return true;
        }
        if (s.charAt(n-1) == t.charAt(m-1) || s.charAt(n-1) == '?') return isMatchRec(n-1, m-1, s, t);
        if (s.charAt(n-1) == '*') return isMatchRec(n-1, m, s, t) || isMatchRec(n, m-1, s, t);
        return false;
    }

    private boolean isMatchDP(int n, int m, String s, String t) {
        boolean[][] dp = new boolean[n+1][m+1];
        dp[0][0] = true;
        for (int j = 1; j <= m; j++) dp[0][j] = false;
        for (int i = 1; i <= n; i++) {
            boolean flag = true;
            for (int k = 1; k <= i; k++) {
                if (s.charAt(k-1) != '*') {
                    flag = false;
                    break;
                }
            }
            dp[i][0] = flag;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (s.charAt(i-1) == t.charAt(j-1) || s.charAt(i-1) == '?') dp[i][j] = dp[i-1][j-1];
                else if (s.charAt(i-1) == '*') dp[i][j] = dp[i-1][j] || dp[i][j-1];
                else dp[i][j] = false;
            }
        }
        return dp[n][m];
    }
}

class SolutionMaxProfitUnlTxn {
    public int maxProfit(int[] prices) {
//        return maxProfitRec(0, 1, prices); // 0 index 1 for can buy
        return maxProfitDP(prices); // 0 index 1 for can buy
    }

    private int maxProfitDP(int[] prices) {
        int n = prices.length;
        int[][] dp = new int[n+1][2];

        dp[n][0] = 0;
        dp[n][1] = 0;
        for (int i = n-1; i >= 0; i--) {
            for (int buy = 0; buy <= 1; buy++) {
                int profit = 0;
                if (buy == 1) {
                    profit = Math.max(-prices[i] + dp[i+1][0], dp[i+1][1]);
                }
                else {
                    profit = Math.max(prices[i] + dp[i+1][1], dp[i+1][0]);
                }
                dp[i][buy] = profit;
            }
        }
        return dp[0][1];
    }

    private int maxProfitRec(int i, int canBuy, int[] prices) {
        if (i == prices.length) return 0;
        int profit = 0;
        if (canBuy == 1) {
            profit = Math.max(-prices[i] + maxProfitRec(i+1, 0, prices), maxProfitRec(i+1, 1, prices));
        }
        else {
            profit = Math.max(prices[i] + maxProfitRec(i+1, 1, prices), maxProfitRec(i+1, 0, prices));
        }
        return profit;
    }
}

class SolutionMaxStockProfitTwoTxns {
    public int maxProfit(int[] prices) {
//        return maxProfitRec(0, 1, 2, prices); // 0 index 1 for can buy
        return maxProfitDP(prices); // 0 index 1 for can buy
    }

    private int maxProfitDP(int[] prices) {
        int n = prices.length;
        int[][][] dp = new int[n+1][2][3];

        for (int i = n-1; i >= 0; i--) {
            for (int buy = 0; buy <= 1; buy++) {
                for (int cap = 1; cap <=2 ; cap++) {
                    int profit = 0;
                    if (buy == 1) {
                        profit = Math.max(-prices[i] + dp[i+1][0][cap], dp[i+1][1][cap]);
                    }
                    else {
                        profit = Math.max(prices[i] + dp[i+1][1][cap-1], dp[i+1][0][cap]);
                    }
                    dp[i][buy][cap] = profit;
                }
            }
        }
        return dp[0][1][2];
    }

    private int maxProfitRec(int i, int canBuy, int cap, int[] prices) {
        if (i == prices.length) return 0;
        if (cap == 0) return 0;
        int profit = 0;
        if (canBuy == 1) {
            profit = Math.max(-prices[i] + maxProfitRec(i+1, 0, cap,  prices), maxProfitRec(i+1, 1, cap, prices));
        }
        else {
            profit = Math.max(prices[i] + maxProfitRec(i+1, 1, cap-1, prices), maxProfitRec(i+1, 0, cap, prices));
        }
        return profit;
    }
}

class SolutionMaxProfitStockKTxns {
    public int maxProfit(int k, int[] prices) {
        return maxProfitDP(prices, k);
    }

    private int maxProfitDP(int[] prices, int k) {
        int n = prices.length;
        int[][][] dp = new int[n+1][2][k+1];

        for (int i = n-1; i >= 0; i--) {
            for (int buy = 0; buy <= 1; buy++) {
                for (int cap = 1; cap <=k ; cap++) {
                    int profit = 0;
                    if (buy == 1) {
                        profit = Math.max(-prices[i] + dp[i+1][0][cap], dp[i+1][1][cap]);
                    }
                    else {
                        profit = Math.max(prices[i] + dp[i+1][1][cap-1], dp[i+1][0][cap]);
                    }
                    dp[i][buy][cap] = profit;
                }
            }
        }
        return dp[0][1][k];
    }
}

class SolutionMaxProfitStocksCooldownPeriod {
    public int maxProfit(int[] prices) {
        return maxProfitDP(prices);
    }

    private int maxProfitDP(int[] prices) {
        int n = prices.length;
        int[][] dp = new int[n+2][2];

        dp[n][0] = 0;
        dp[n][1] = 0;
        for (int i = n-1; i >= 0; i--) {
            for (int buy = 0; buy <= 1; buy++) {
                int profit = 0;
                if (buy == 1) {
                    profit = Math.max(-prices[i] + dp[i+1][0], dp[i+1][1]);
                }
                else {
                    profit = Math.max(prices[i] + dp[i+2][1], dp[i+1][0]);
                }
                dp[i][buy] = profit;
            }
        }
        return dp[0][1];
    }

    private int maxProfitRec(int i, int canBuy, int[] prices) {
        if (i == prices.length) return 0;
        int profit = 0;
        if (canBuy == 1) {
            profit = Math.max(-prices[i] + maxProfitRec(i+1, 0, prices), maxProfitRec(i+1, 1, prices));
        }
        else {
            profit = Math.max(prices[i] + maxProfitRec(i+2, 1, prices), maxProfitRec(i+1, 0, prices));
        }
        return profit;
    }
}

class SolutionLengthOfLIS {
    public int lengthOfLIS(int[] nums) {
//        return lengthOfLISRec(0, -1, nums);
        return lengthOfLISDP(nums);
    }

    private int lengthOfLISDP(int[] nums) {
        int n = nums.length;
        int[][] dp = new int[n+1][n+1];
        // since 0 only, can avoid base cdn
        for (int i = n-1; i >= 0; i--) {
            for (int prev = i-1; prev >= -1; prev--) {
                int len = dp[i+1][prev+1];
                if (prev == -1 || nums[i] > nums[prev]) {
                    len = Math.max(len, 1+dp[i+1][i+1]);
                }
                dp[i][prev+1] = len;
            }
        }
        return dp[0][0];
    }

    private int lengthOfLISRec(int i, int prev, int[] nums) {
        if (i == nums.length) return 0;
        int len = lengthOfLISRec(i+1, prev, nums); // not taken
        if (prev == -1 || nums[i] > nums[prev]) {
            len = Math.max(len, 1+lengthOfLISRec(i+1, i, nums));
        }
        return len;
    }
}

class SolutionLISPrint {
    public ArrayList<Integer> longestIncreasingSubsequence(int N, int arr[]){
        return lisPrintDP(N, arr);
    }

    private ArrayList<Integer> lisPrintDP(int n, int[] arr) {
        int[] dp = new int[n];
        int[] hash = new int[n];
        Arrays.fill(dp, 1);
        ArrayList<Integer> ans = new ArrayList<>();
        int mx = 1;
        int lastIndex = 0;
        for (int i = 0; i < n; i++) {
            hash[i] = i;
            for (int prev = 0; prev <= i-1; prev++)
            {
                if (arr[i] > arr[prev] && 1+dp[prev] > dp[i]) {
                    dp[i] = 1+dp[prev];
                    hash[i] = prev;
                }
            }
            if (dp[i] > mx) {
                mx = dp[i];
                lastIndex = i;
            }
        }
        ans.add(arr[lastIndex]);
        while (hash[lastIndex] != lastIndex) {
            lastIndex = hash[lastIndex];
            ans.add(arr[lastIndex]);
        }
        Collections.reverse(ans);
        return ans;
    }
}

class SolutionDivSubset {
    public List<Integer> largestDivisibleSubset(int[] nums) {
        Arrays.sort(nums);
        return lisPrintDP(nums.length, nums);
    }

    private ArrayList<Integer> lisPrintDP(int n, int[] arr) {
        int[] dp = new int[n];
        int[] hash = new int[n];
        Arrays.fill(dp, 1);
        ArrayList<Integer> ans = new ArrayList<>();
        int mx = 1;
        int lastIndex = 0;
        for (int i = 0; i < n; i++) {
            hash[i] = i;
            for (int prev = 0; prev <= i-1; prev++)
            {
                if ((arr[i]%arr[prev] == 0 || arr[prev]%arr[i] == 0) && 1+dp[prev] > dp[i]) {
                    dp[i] = 1+dp[prev];
                    hash[i] = prev;
                }
            }
            if (dp[i] > mx) {
                mx = dp[i];
                lastIndex = i;
            }
        }
        ans.add(arr[lastIndex]);
        while (hash[lastIndex] != lastIndex) {
            lastIndex = hash[lastIndex];
            ans.add(arr[lastIndex]);
        }
        Collections.reverse(ans);
        return ans;
    }
}

class SolutionLongestStringChain {
    public int longestStrChain(String[] words) {
        Arrays.sort(words, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.length() - o2.length();
            }
        });
//        return longestStrChainRec(words, 0, -1);
        return longestStrChainDP(words, words.length);
    }

    private int longestStrChainRec(String[] words, int i, int prev) {
        if (i == words.length) return 0;
        int len = longestStrChainRec(words, i+1, prev); // not taken
        if (prev == -1 || onlyOneCharDifference(words[prev], words[i])) {
            len = Math.max(len, 1+longestStrChainRec(words, i+1, i));
        }
        return len;
    }

    private boolean onlyOneCharDifference(String prev, String word) {
        if (word.length() - prev.length() != 1) return false;
        for (int i = 0; i < word.length(); i++) {
            if (prev.equals(word.substring(0,i) + word.substring(i+1))) return true;
        }
        return false;
    }

    private int longestStrChainDP(String[] words, int n) {
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        int mx = 1;
        for (int i = 0; i < n; i++) {
            for (int prev = 0; prev <= i-1; prev++)
            {
                if (onlyOneCharDifference(words[prev], words[i]) && 1+dp[prev] > dp[i]) {
                    dp[i] = 1+dp[prev];
                }
            }
            if (dp[i] > mx) {
                mx = dp[i];
            }
        }
        return mx;
    }
}

class SolutionBitonic
{
    public int LongestBitonicSequence(int[] nums)
    {
        return bitonicDP(nums, nums.length);
    }

    private int bitonicDP(int[] nums, int n) {
        int[] dp = new int[n];
        int[] dpRev = new int[n];

        Arrays.fill(dp, 1);
        Arrays.fill(dpRev, 1);

        for (int i = 1; i < n; i++) {
            for (int prev = 0; prev < i; prev++) {
                if (nums[i] > nums[prev] && 1+dp[prev] > dp[i]) {
                    dp[i] = 1+dp[prev];
                }
            }
        }

        for (int i = n-1; i >= 0; i--) {
            for (int prev = n-1; prev > i; prev--) {
                if (nums[prev] < nums[i] && 1+dpRev[prev] > dpRev[i]) {
                    dpRev[i] = 1+dpRev[prev];
                }
            }
        }

        int mx = 1;
        for (int i = 0; i < n; i++) {
            mx = Math.max(mx, dp[i]+dpRev[i]-1);
        }
        return mx;
    }
}

class SolutionNoOfLIS {
    public int findNumberOfLIS(int[] nums) {
        return findNumberOfLISDP(nums, nums.length);
    }

    private int findNumberOfLISDP(int[] nums, int n) {
        int[] dp = new int[n];
        int[] count = new int[n];

        Arrays.fill(dp, 1);
        Arrays.fill(count, 1);
        int mx = 1;

        for (int i = 1; i < n; i++) {
            for (int prev = 0; prev < i; prev++) {
                if (nums[i] > nums[prev] && 1+dp[prev] > dp[i]) {
                    dp[i] = 1+dp[prev];
                    count[i] = count[prev];
                }
                else if (nums[i] > nums[prev] && 1+dp[prev] == dp[i]) {
                    count[i] += count[prev];
                }
            }
            if (dp[i] > mx) {
                mx = dp[i];
            }
        }
        int noOfLIS = 0;
        for (int i = 0; i < n; i++) {
            if (dp[i] == mx) {
                noOfLIS += count[i];
            }
        }
        return noOfLIS;
    }
}

class SolutionMCM {
    static int matrixMultiplication(int N, int arr[])
    {
//        return mcmRec(1, N-1, arr);
        int[][] dp = new int[N][N];
        for (int i = 0; i < N; i++) {
            Arrays.fill(dp[i], -1);
        }
//        return mcmRecMem(1, N-1, arr, dp);
        return mcmRecDP(N, arr);
    }

    private static int mcmRecDP(int n, int[] arr) {
        int[][] dp = new int[n][n];

        for (int i = 1; i < n; i++) {
            dp[i][i] = 0;
        }

        for(int i = n-1; i >= 1; i--) {
            for (int j = i+1; j < n; j++) {
                int mn = (int)1e9;
                for (int k = i; k <= j-1; k++) {
                    int steps = arr[i-1]*arr[k]*arr[j] + dp[i][k] + dp[k+1][j];
                    mn = Math.min(mn, steps);
                }
                dp[i][j] = mn;
            }
        }
        return dp[1][n-1];
    }

    private static int mcmRec(int i, int j, int[] arr) {
        if (i == j) return 0;
        int mn = (int)1e9;
        for (int k = i; k <= j-1; k++) {
            int steps = (arr[i-1]*arr[k]*arr[j]) + mcmRec(i, k, arr) + mcmRec(k+1, j ,arr);
            mn = Math.min(mn, steps);
        }
        return mn;
    }

    private static int mcmRecMem(int i, int j, int[] arr, int[][] dp) {
        if (i == j) return 0;
        if (dp[i][j] != -1) return dp[i][j];
        int mn = (int)1e9;
        for (int k = i; k <= j-1; k++) {
            int steps = (arr[i-1]*arr[k]*arr[j]) + mcmRec(i, k, arr) + mcmRec(k+1, j ,arr);
            mn = Math.min(mn, steps);
        }
        dp[i][j] = mn;
        return mn;
    }
}

class SolutionMCMRodCutting {
    public int minCost(int n, int[] cuts) {
        int c = cuts.length;
        int[] cut = new int[c+2];
        cut[0] = 0;
        cut[c+1] = n;
        for (int i = 1; i <= c; i++) {
            cut[i] = cuts[i-1];
        }
        Arrays.sort(cut);
//        return minCostRec(1, c, cut);
        return minCostDP(n, c, cut);
    }

    private int minCostDP(int n, int c, int[] cut) {
        int[][] dp = new int[c+2][c+2];

        for (int i = c; i >= 1; i--) {
            for (int j = 1; j <= c; j++) {
                if (i > j) continue;
                int mn = (int)1e9;
                for (int ind = i; ind <= j; ind++) {
                    int cost = cut[j+1]-cut[i-1]+dp[i][ind-1]+dp[ind+1][j];
                    mn = Math.min(mn, cost);
                }
                dp[i][j] = mn;
            }
        }
        return dp[1][c];
    }

    private int minCostRec(int i, int j, int[] cuts) {
        if (i > j) {
            return 0;
        }

        int mn = (int)1e9;
        for (int ind = i; ind <= j; ind++) {
            int cost = cuts[j+1]-cuts[i-1]+minCostRec(i, ind-1, cuts)+minCostRec(ind+1, j, cuts);
            mn = Math.min(mn, cost);
        }
        return mn;
    }
}

class SolutionBurstBallons {
    public int maxCoins(int[] nums) {
        int n = nums.length;
        int[] num = new int[n+2];
        num[0] = 1;
        num[n+1] = 1;
        for (int i = 1; i <= n; i++) {
            num[i] = nums[i-1];
        }
//        return maxCoinsRec(1, n, num);
        return maxCoinsDP(n, num);
    }

    private int maxCoinsDP(int n, int[] nums) {
        int[][] dp = new int[n+2][n+2];

        for (int i = n; i >= 1; i--) {
            for (int j = 1; j <= n; j++) {
                if (i > j) continue;
                int mx = 0;
                for (int ind = i; ind <= j; ind++) {
                    int cost = nums[i-1]*nums[ind]*nums[j+1] + dp[i][ind-1] + dp[ind+1][j];
                    mx = Math.max(mx, cost);
                }
                dp[i][j] = mx;
            }
        }
        return dp[1][n];
    }

    private int maxCoinsRec(int i, int j, int[] nums) {
        if (i > j) {
            return 0;
        }

        int mx = 0;
        for (int ind = i; ind <= j; ind++) {
            int cost = nums[i-1]*nums[ind]*nums[j+1] + maxCoinsRec(i, ind-1, nums) + maxCoinsRec(ind+1, j, nums);
            mx = Math.max(mx, cost);
        }
        return mx;
    }
}

class SolutionPalindrome {
    public int minCut(String s) {
        int n = s.length();
//        return minCutRec(0, s, n);
        return minCutDP(s, n);
    }

    private int minCutDP(String s, int n) {
        int[] dp = new int[n+1];

        for (int i = n-1; i >= 0; i--) {
            int mnCost = Integer.MAX_VALUE;
            for (int j = i; j < n; j++) {
                if (isPalindrome(s.substring(i, j+1))) {
                    int cost = 1 + dp[j+1];
                    mnCost = Math.min(mnCost, cost);
                }
            }
            dp[i] = mnCost;
        }
        return dp[0]-1;
    }

    private int minCutRec(int i, String s, int n) {
        if (i == n) return 0;
        String temp = "";
        int mnCost = Integer.MAX_VALUE;
        for (int j = i; j < n; j++) {
            temp += s.charAt(i);
            if (isPalindrome(temp)) {
                int cost = 1 + minCutRec(j+1, s, n);
                mnCost = Math.min(mnCost, cost);
            }
        }
        return mnCost;
    }

    private boolean isPalindrome(String s) {
        int i = 0;
        int j = s.length()-1;
        while (i < j) {
            if (s.charAt(i) != s.charAt(j)) return false;
            i++;
            j--;
        }
        return true;
    }
}

class SolutionMaxSumAfterPartitioning {
    public int maxSumAfterPartitioning(int[] arr, int k) {
//        return maxSumAfterPartitioningRec(0, arr.length, k, arr);
        return maxSumAfterPartitioningDP(arr.length, k, arr);
    }

    private int maxSumAfterPartitioningDP(int n, int k, int[] arr) {
        int[] dp = new int[n+1];

        for (int i = n-1; i >= 0; i--) {
            int mxAns = Integer.MIN_VALUE;
            int len = 0;
            int mx = Integer.MIN_VALUE;
            for (int j = i; j < Math.min(i+k, n); j++) {
                len++;
                mx = Math.max(mx, arr[j]);
                int sum = (len*mx) + dp[j+1];
                mxAns = Math.max(mxAns, sum);
            }
            dp[i] = mxAns;
        }
        return dp[0];
    }

    private int maxSumAfterPartitioningRec(int i, int n, int k, int[] arr) {
        if (i == n) return 0;

        int mxAns = Integer.MIN_VALUE;
        int len = 0;
        int mx = Integer.MIN_VALUE;
        for (int j = i; j < Math.min(i+k, n); j++) {
            len++;
            mx = Math.max(mx, arr[j]);
            int sum = (len*mx) + maxSumAfterPartitioningRec(j+1, n, k, arr);
            mxAns = Math.max(mxAns, sum);
        }
        return mxAns;
    }
}

class SolutionEggDrop
{
    //Function to find minimum number of attempts needed in
    //order to find the critical floor.
    static int eggDrop(int n, int k)
    {
//       return eggDropRec(n, k);
       return eggDropDP(n, k);
    }

    private static int eggDropDP(int n, int k) {
        int[][] dp = new int[n+1][k+1];
        for (int i = 0; i <= n; i++) {
            dp[i][0] = 0;
            dp[i][1] = 1;
        }

        for (int j = 0; j <= k; j++) {
            dp[1][j] = j;
        }

        for (int i = 2; i <= n; i++) {
            for (int j = 2; j <= k; j++) {
                int mn = Integer.MAX_VALUE;
                for (int floor = 1; floor <= j; floor++) {
                    int breaks = 1+ dp[i-1][floor-1];
                    int notBreaks = 1+ dp[i][j-floor];
                    mn = Math.min(mn, Math.max(breaks, notBreaks));
                }
                dp[i][j] = mn;
            }
        }
        return dp[n][k];
    }

    private static int eggDropRec(int egg, int floors) {
        if (egg == 1) return floors;
        if (floors == 0 || floors == 1) return floors;

        int mn = Integer.MAX_VALUE;
        for (int floor = 1; floor <= floors; floor++) {
            int breaks = 1+ eggDropRec(egg-1, floor-1);
            int notBreaks = 1+ eggDropRec(egg, floors-floor);
            mn = Math.min(mn, Math.max(breaks, notBreaks));
        }
        return mn;
    }
}

class SolutionTotalPalindromicSubsequence
{
    long countPS(String str)
    {
        int n = str.length();
//        return countPSRec(0, n-1, str);
        return countPSDP(n, str);
    }

    private long countPSDP(int n, String str) {
        long[][] dp = new long[n+1][n+1];
        long mod = 1000000007;
        for (int i = 0; i <= n; i++) dp[i][i] = 1;

        for (int i = n-1; i >= 0; i--) {
            for (int j = i+1; j < n; j++) {
                if (str.charAt(i) == str.charAt(j)) {
                    dp[i][j] = (1 + dp[i+1][j] + dp[i][j-1])%mod;
                }
                else {
                    dp[i][j] = (dp[i+1][j] + dp[i][j-1] - dp[i+1][j-1])%mod;
                }
            }
        }
        return dp[0][n-1];
    }

    private long countPSRec(int i, int j, String str) {
        if (i > j) return 0;
        if (i  == j) return 1;
        else if (str.charAt(i) == str.charAt(j)) {
            return 1 + countPSRec(i+1, j, str) + countPSRec(i, j-1, str);
        }
        else {
            return countPSRec(i+1, j, str) + countPSRec(i, j-1, str) - countPSRec(i+1, j-1, str);
        }
    }
}