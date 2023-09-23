package Recursion;

import java.util.*;

public class Basic {

    static String pattern = "**********";

//    public static void main(String[] args) {
//        int n = 5;
//        String str = "abba1";
//        printNos(n);
//        System.out.println(pattern);
//        printNosRev(n);
//        System.out.println(pattern);
//        System.out.println(sumOfFirstNNumbers(n));
//        System.out.println(pattern);
//        System.out.println(factorial(n));
//        System.out.println(pattern);
//        System.out.println((str + " is palindrome: " + isPalindrome(str, 0, 4)));
//        System.out.println(pattern);
//
//        Map<Integer, Integer> m = new HashMap<Integer, Integer>();
//        m.put(0,0);
//        m.put(1,1);
//        fibonacciNos(n, m);
//        System.out.println(m);
//    }

    private static void printNos(int n) {
        if (n == 0) {
            return;
        }
        printNos(n-1);
        System.out.println(n);
    }

    private static void printNosRev(int n) {
        if (n == 0) {
            return;
        }
        System.out.println(n);
        printNosRev(n-1);
    }

    private static int sumOfFirstNNumbers(int n) {
        if (n == 0) {
            return 0;
        }
        return n + sumOfFirstNNumbers(n-1);
    }

    private static int factorial(int n) {
        if (n == 0 || n == 1) {
            return 1;
        }
        return n*factorial(n-1);
    }

    private static boolean isPalindrome(String str, int start, int end) {
        if (start < end) {
            if (str.charAt(start) == str.charAt(end)) {
                return true;
            }
            return false;
        }
        if (start >= end) {
            return true;
        }
        return isPalindrome(str, start+1, end-1);
    }

    private static int fibonacciNos(int n, Map<Integer, Integer> m) {
        if (n <= 0) {
            return 0;
        }
        if (m.containsKey(n)) {
            return m.get(n);
        }
        int x = fibonacciNos(n-1, m) + fibonacciNos(n-2, m);
        m.put(n, x);
        return x;
    }

    public static void main1(String[] args) {
//        int[] nums = {1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1};
//        search(nums, 2);
//        System.out.println();
//        String[] strs = {"flower","flow","flight"};
//        longestCommonPrefix(strs);
//        beautySum("aabcb");
        System.out.println(myPow(5, 3));
    }

    public static void rotate(int[] nums, int k) {
        int n = nums.length;
        k = k%n;
        int j = n-k;
        int cur = 0;
        for (int i = j; i < n; i++) {
            int temp = nums[cur];
            nums[cur] = nums[j];
            int x = cur;
            while (x+k <= j) {
                int temp1 = nums[x+k];
                nums[x+k] = temp;
                temp = temp1;
                x = x+k;
            }
            cur++;
        }
    }

    public static int subarraySum(int[] nums, int k) {
        int n = nums.length;
        int start = 0;
        int end = -1;
        int sum = 0;
        int ans = 0;
        while (start < n) {
            while (end+1 < n && (sum + nums[end+1] <= k)) {
                end++;
                sum += nums[end];
            }
            if (sum == k && end != -1) {
                ans++;
                int x = end;
                while(x >= 0 && nums[x] == 0) {
                    x--;
                    ans++;
                }
            }
            sum -= nums[start];
            start++;
        }
        return ans;
    }

    public int maxSubArray(int[] nums) {
        int msf = Integer.MIN_VALUE;
        int s = 0;
        int meh = 0;

        for (int i = 0; i < nums.length; i++) {
            meh += nums[i];
            if (msf < meh) {
                msf = meh;
            }
            if (meh < 0) {
                meh = 0;
                s = i+1;
            }
        }
        return msf;
    }

    public static void nextPermutation(int[] nums) {
        Set<Integer> set = new HashSet<Integer>();
        for (int num : nums) {
            set.add(num);
        }
        System.out.println(set);
        int n = nums.length;
        int sI = n-1;
        int start = 0;
        for (int i = n-1; i > 0; i--) {
            if (nums[i] > nums[i-1]) {
                int temp = nums[sI];
                nums[sI] = nums[i-1];
                nums[i-1] = temp;
                start = i;
                break;
            }
            if (nums[sI] > nums[i]) {
                sI = i;
            }
        }
        if (start == n) {
            return;
        }
        ArrayList<Integer> list = new ArrayList<Integer>();
        Collections.reverse(list);
        Arrays.sort(nums, start, n-1);
    }

    public static void rotate(int[][] m) {
        int n = m.length;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int temp = m[i][j];
                m[i][j] = m[j][i];
                m[j][i] = temp;
            }
        }
        // for (int i = 0; i < n; i++) {
        //     int start = 0;
        //     int end = n-1;
        //     while (start < end) {
        //         int temp = m[i][start];
        //         m[i][start] = m[i][end];
        //         m[i][end] = temp;
        //         start++;
        //         end--;
        //     }
        // }
    }

    public static List<List<Integer>> pascalTriangle(int numRows) {
        List<List<Integer>> list = new ArrayList();
        List<Integer> l = new ArrayList();
        l.add(1);
        list.add(l);
        for (int i = 2; i <= numRows; i++) {
            List<Integer> l1 = new ArrayList();
            l1.add(1);
            for (int j = 1; j < i-1; j++) {
                l1.add(list.get(i-2).get(j-1) + list.get(i-2).get(j));
            }
            l1.add(1);
            list.add(l1);
        }
        return list;
    }

    public static List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> list = new ArrayList();
        Arrays.sort(nums);
        int n = nums.length;
        for (int i = 0; i < n-3; i++) {
            if (i != 0 && nums[i] == nums[i-1]) {
                continue;
            }
            for (int j = i+1; j < n-2; j++) {
                int sum = nums[i]+nums[j];
                int low = j+1;
                int high = n-1;
                if (j != i+1 && nums[j] == nums[j-1]) {
                    continue;
                }
                while (low < high) {
                    if (sum + nums[low] + nums[high] == target) {
                        List<Integer> l = new ArrayList();
                        l.add(nums[i]);
                        l.add(nums[j]);
                        l.add(nums[low]);
                        l.add(nums[high]);
                        list.add(l);
                        low++;
                        while (low < n && nums[low] == nums[low-1]) {
                            low++;
                        }
                    }
                    else if (sum + nums[low] + nums[high] < 0) {
                        low++;
                        while (low < n && nums[low] == nums[low-1]) {
                            low++;
                        }
                    }
                    else {
                        high--;
                        while (high >= 0 && nums[high] == nums[high+1]) {
                            high--;
                        }
                    }
                }
            }
        }
        return list;
    }

    public static int reversePairs(int[] nums) {
        return mergeSort(nums, 0, nums.length-1);
    }

    public static int mergeSort(int[] nums, int low, int high) {
        if (low < high) {
            int mid = (low+high)/2;
            int count = mergeSort(nums, low, mid);
            count += mergeSort(nums, mid+1, high);
            count += merge(nums, low, mid, high);
            return count;
        }
        return 0;
    }

    public static int merge(int[] nums, int low, int mid, int high) {
        int[] temp = new int[high-low+1];
        int k = 0;
        int i = low;
        int j = mid+1;
        int count = 0;

        while (i <= mid && j <= high) {
            if (nums[i] <= nums[j]) {
                temp[k++] = nums[i++];
            }
            else {
                if (nums[i] > 2*nums[j]) {
                    count += (mid-i+1);
                }
                temp[k++] = nums[j++];
            }
        }
        while (i <= mid) {
            temp[k++] = nums[i++];
        }
        while (j <= high) {
            temp[k++] = nums[j++];
        }
        for (i = low; i <= high; i++) {
            nums[i] = temp[i-low];
        }
        return count;
    }

    public static boolean search(int[] nums, int target) {
        int n = nums.length;
        if (n == 1) {
            return nums[0] == target ? true : false;
        }
        int p = pivot(nums);
        if (p == -1) {
            p = 0;
        }
        int low, high;
        if (target <= nums[p] && target >= nums[0]) {
            low = 0;
            high = p;
        }
        else {
            low = p+1;
            high = n-1;
        }
        while (low <= high) {
            int mid = (low+high)/2;
            while (mid < n-1 && mid <= high && nums[mid] == nums[mid+1]) {
                mid++;
            }
            if (nums[mid] == target) {
                return true;
            }
            else if (nums[mid] > target) {
                high = mid-1;
            }
            else {
                low = mid+1;
            }
        }
        return false;
    }

    public static int pivot(int[] nums) {
        int n = nums.length;
        int low = 0;
        int high = n-1;
        while (low <= high) {
            int mid = (low+high)/2;
            int temp = mid;
            while (mid < n-2 && mid <= high && nums[mid] == nums[mid+1]) {
                mid++;
            }
            if (nums[mid] > nums[mid+1]) {
                return mid;
            }
            else if (nums[mid] > nums[n-1]) {
                low = temp+1;
            }
            else {
                high = temp-1;
            }
        }
        return -1;
    }

    public static String longestCommonPrefix(String[] strs) {
        int shortest = 1000;
        for (String str : strs) {
            shortest = Math.min(str.length(), shortest);
        }
        String ans = "";
        for (int i = 0; i < shortest; i++) {
            boolean common = true;
            String commonStr = "";
            for (String str : strs) {
                if (commonStr.length() == 0) {
                    commonStr = String.valueOf(str.charAt(i));
                }
                else if (!commonStr.equals(String.valueOf(str.charAt(i)))) {
                    common = false;
                    break;
                }
            }
            if (!common) {
                break;
            }
            ans += commonStr;
        }
        return ans;
    }

    public static boolean rotateString(String s, String goal) {
        int n = goal.length();
        int x = -1;
        for (int i = n-1; i >= 0; i--) {
            if (goal.substring(i, n).equalsIgnoreCase(s.substring(0, n-i))) {
                x = i;
            }
        }
        if (x == -1) {
            return false;
        }
        String ans = goal.substring(x) + goal.substring(0, x);
        return ans.equalsIgnoreCase(s);
    }

    public static String frequencySort(String s) {
        Map<Character, Integer> m = new HashMap<Character, Integer>();
        for (char c : s.toCharArray()) {
            m.put(c, m.getOrDefault(c, 0)+1);
        }
        String ans = "";
        List<Map.Entry<Character,Integer>> entries = new ArrayList(m.entrySet());
        entries.sort(Map.Entry.comparingByValue());
        for (Map.Entry<Character, Integer> map : entries) {
            String str = new String(new char[map.getValue()]).replace("\0", String.valueOf(map.getKey()));
            ans = str+ans;
        }
        return ans;
    }

    public static int beautySum(String s) {
        int n = s.length();
        int sum = 0;
        for (int i = 0; i < n-1; i++) {
            Map<Character, Integer> m = new HashMap();
            for (int j = i+1; j < n; j++) {
                int x = m.getOrDefault(s.charAt(j), 0);
                m.put(s.charAt(j), x+1);
            }
            int mx = -1, mn = 1000;
            for (Map.Entry<Character, Integer> e : m.entrySet()) {
                mx = Math.max(mx, e.getValue());
                mn = Math.min(mn, e.getValue());
            }
            sum += (mx-mn);
        }
        return sum;
    }

    static double myPow(double x, int n) {
        if (x == 0) {
            return 0;
        }
        if (n == 0) {
            return 1;
        }
        return x*myPow(x,n-1);
    }

    public static void main2(String[] args) {

        addOperators("123", 6);
    }

    public static List<String> generateBinaryStrings(int n) {
        List<String> ans = new ArrayList<>();
        generateBinaryStringsRec(n, "", ans);
        return ans;
    }

    private static void generateBinaryStringsRec(int n, String s, List<String> ans) {
        if (n == 0) {
            ans.add(s);
            return;
        }
        generateBinaryStringsRec(n-1, s+'0', ans);
        if (s.length() > 0 && s.charAt(s.length()-1) != '1') generateBinaryStringsRec(n-1, s+'1', ans);
    }

    public static List<String> generateParenthesis(int n) {
        List<String> ans = new ArrayList<>();
        generateParenthesisRec(n, n, "", ans);
        return ans;
    }

    private static void generateParenthesisRec(int o, int c, String s, List<String> ans) {
        if (o > c) {
            return;
        }
        if (o == 0 && c == 0) {
            ans.add(s);
        }
        if (o > 0) generateParenthesisRec(o-1, c, s+'(', ans);
        if (c > 0) generateParenthesisRec(o, c-1, s+')', ans);
    }

    public static List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> pick = new ArrayList<>();
        subsetsRec(0, nums, pick, ans);
        return ans;
    }

    private static void subsetsRec(int i, int[] nums, List<Integer> pick, List<List<Integer>> ans) {
        ans.add(new ArrayList<>(pick));
        if (pick.size() == nums.length) {
            return;
        }
        for (int start = i; start < nums.length; start++) {
            pick.add(nums[start]);
            subsetsRec(start+1, nums, pick, ans);
            pick.remove(pick.size()-1);
        }
    }

    public static int perfectSum(int arr[],int n, int sum)
    {
        return perfectSumRec(0, arr, n, sum);

    }

    private static int perfectSumRec(int i, int[] arr, int n, int sum) {
        if (i == n) {
            return 0;
        }
        if (sum == 0) {
            return 1;
        }
        return perfectSumRec(i+1, arr, n, sum-arr[i]) + perfectSumRec(i+1, arr, n, sum);
    }

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> comb = new ArrayList<>();
        combinationSumRec(0, candidates, target, comb, ans);
        return ans;
    }

    private void combinationSumRec(int i, int[] candidates, int target, List<Integer> comb, List<List<Integer>> ans) {
        if (i == candidates.length) {
            if (target == 0) {
                ans.add(new ArrayList<>(comb));
            }
            return;
        }
        if (target-candidates[i] >= 0) {
            comb.add(candidates[i]);
            combinationSumRec(i+1, candidates, target-candidates[i], comb, ans);
            comb.remove(comb.size()-1);
            combinationSumRec(i+1, candidates, target, comb, ans);
        }
    }

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> comb = new ArrayList<>();
        combinationSum2Rec(0, candidates, target, comb, ans);
        return ans;
    }

    private void combinationSum2Rec(int i, int[] candidates, int target, List<Integer> comb, List<List<Integer>> ans) {
        if (i == candidates.length) {
            if (target == 0) {
                ans.add(new ArrayList<>(comb));
            }
            return;
        }
        if (target-candidates[i] >= 0) {
            comb.add(candidates[i]);
            combinationSumRec(i+1, candidates, target-candidates[i], comb, ans);
            comb.remove(comb.size()-1);
            combinationSumRec(i+1, candidates, target, comb, ans);

            Arrays.sort(candidates);
        }
    }

    ArrayList<Integer> subsetSums(ArrayList<Integer> arr, int N){
        ArrayList<Integer> ans = new ArrayList<>();
        ArrayList<Integer> pick = new ArrayList<>();
        subsetSumsRec(0, arr, N, pick, ans);
        return ans;
    }

    private void subsetSumsRec(int i, ArrayList<Integer> arr, int n, ArrayList<Integer> pick, List<Integer> ans) {
        if (i == n) {
            int sum = 0;
            for (int x : pick) {
                sum += x;
            }
            ans.add(sum);
        }
        pick.add(arr.get(i));
        subsetSumsRec(i+1, arr, n, pick, ans);
        pick.remove(pick.size()-1);
        subsetSumsRec(i+1, arr, n, pick, ans);
    }

    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> pick = new ArrayList<>();
        subsetsWithDupRec(0, nums, pick, ans);
        return ans;
    }

    private void subsetsWithDupRec(int ind, int[] nums, List<Integer> pick, List<List<Integer>> ans) {
        ans.add(new ArrayList<>(pick));
        if (ind == nums.length) {
            return;
        }
        for (int i = ind; i < nums.length; i++) {
            pick.add(nums[i]);
            subsetsWithDupRec(i+1, nums, pick, ans);
            pick.remove(pick.size()-1);
        }
    }

    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> pick = new ArrayList<>();
        combinationSum3Rec(1, n, k, pick, ans);
        return ans;
    }

    private void combinationSum3Rec(int ind, int n, int k, List<Integer> pick, List<List<Integer>> ans) {
        if (pick.size() == k) {
            int sum = 0;
            for (int i = 0; i < k; i++) {
                sum += pick.get(i);
            }
            if (sum == n) {
                ans.add(new ArrayList<>(pick));
            }
            return;
        }
        for(int i = ind; i < 10; i++) {
            pick.add(i);
            combinationSum3Rec(i+1, n, k, pick, ans);
            pick.remove(pick.size()-1);
        }
    }

    public static List<String> letterCombinations(String digits) {
        Map<Character, String> phone = new HashMap<>();
        phone.put('2', "abc");
        phone.put('3', "def");
        phone.put('4', "ghi");
        phone.put('5', "jkl");
        phone.put('6', "mno");
        phone.put('7', "pqrs");
        phone.put('8', "tuv");
        phone.put('9', "wxyz");
        List<String> ans = new ArrayList<>();
        String pick = "";
        letterCombinationsRec(digits, phone, 0, pick, ans);
        return ans;
    }

    private static void letterCombinationsRec(String digits, Map<Character, String> phone, int ind, String pick, List<String> ans) {
        if (ind == digits.length()) {
            ans.add(pick);
            return;
        }
        String s = phone.getOrDefault(digits.charAt(ind), "");
        for (int i = 0; i < s.length(); i++) {
            letterCombinationsRec(digits, phone, ind+1, pick+s.charAt(i), ans);
        }
    }

    public List<List<String>> partition(String s) {
        List<List<String>> ans = new ArrayList<>();
        List<String> pick = new ArrayList<>();
        partitionRec(0, s, pick, ans);
        return ans;
    }

    private void partitionRec(int ind, String s, List<String> pick, List<List<String>> ans) {
        if (ind == s.length()) {
            ans.add(new ArrayList<>(pick));
            return;
        }
        for (int i = ind; i < s.length(); i++) {
            if (isPalindromeString(s.substring(ind, i+1))) {
                pick.add(s.substring(ind, i+1));
                partitionRec(i+1, s, pick, ans);
                pick.remove(pick.size()-1);
            }
        }
    }

    private boolean isPalindromeString(String substring) {
        int start = 0;
        int end = substring.length();
        while (start < end) {
            if (substring.charAt(start) != substring.charAt(end)) {
                return false;
            }
            start++;
            end--;
        }
        return true;
    }

    public boolean exist(char[][] board, String word) {
        int m = board.length;
        int n = board[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == word.charAt(0)) {
                    existRec(i, j, m, n, 0, word, board);
                }
            }
        }
        return existRec(0, 0, m, n, 0, word, board);
    }

    private boolean existRec(int i, int j, int m, int n, int index, String word, char[][] board) {
        if (i >= m || j >= n || i < 0 || j < 0 || board[i][j] != word.charAt(index)) {
            return false;
        }

        char c = board[i][j];
        board[i][j] = '&';
        if (existRec(i, j+1, m, n, index+1, word, board)
            || existRec(i+1, j, m, n, index+1, word, board)
            || existRec(i, j-1, m, n, index+1, word, board)
            || existRec(i-1, j, m, n, index+1, word, board)) {
            return true;
        }
        board[i][j] = c;
        return false;
    }

    public List<List<String>> solveNQueens(int n) {
        List<List<String>> ans = new ArrayList<>();
        char[][] board = new char[n][n];
        String s = new String(new char[n]).replace('\0', '.');
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = '.';
            }
        }
        int[] row = new int[n];
        int[] lDia = new int[2*n-1];
        int[] uDia = new int[2*n-1];

        solveNQueensRec(0, n, row, lDia, uDia, board, ans);
        return ans;
     }

    private void solveNQueensRec(int col, int n, int[] row, int[] lDia, int[] uDia, char[][] board, List<List<String>> ans) {
        if (col == n) {
            ans.add(createBoard(board));
            return;
        }
        for (int i = 0; i < n; i++) {
            if (row[i] == 0 && lDia[i+col] == 0 && uDia[n-1+col-i] == 0) {
                board[i][col] = 'Q';
                row[i] = 1; lDia[i+col] = 1; uDia[n-1+col-i] = 1;
                solveNQueensRec(col+1, n , row, lDia, uDia, board, ans);
                board[i][col] = '.';
                row[i] = 0; lDia[i+col] = 0; uDia[n-1+col-i] = 0;
            }
        }
    }

    private List<String> createBoard(char[][] board) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            String s = "";
            for (int j = 0; j < board.length; j++) {
                s += board[i][j];
            }
            list.add(s);
        }
        return list;
    }

    public boolean wordBreak(String s, List<String> wordDict) {
        return wordBreakRec(s, wordDict, 0, new boolean[s.length()]);
    }

    private boolean wordBreakRec(String s, List<String> wordDict, int index, boolean[] booleans) {
        if (index == s.length()) {
            return true;
        }
        if (booleans[index]) {
            return true;
        }
        for (String i : wordDict) {
            if (index + i.length() <= s.length() && s.substring(index, index + i.length()).equals(i)
                && wordBreakRec(s, wordDict, index+i.length(), booleans)) {
                booleans[index] = true;
                return true;
            }
        }
        return false;
    }

    public static List<String> addOperators(String num, int target) {
        List<String> ans = new ArrayList<>();
        String pick = "";
        List<Character> op = new ArrayList<>();
        op.add('+');
        op.add('-');
        op.add('*');
        op.add('/');
        addOperatorsRec(0, num, op, target, pick, ans);
        return ans;
    }

    private static void addOperatorsRec(int i, String num, List<Character> op, int target, String pick, List<String> ans) {
        if (pick.length() == 2*num.length()-1) {
            if (evaluateTarget(pick, target)) {
                ans.add(pick);
            }
            return;
        }
        pick += num.charAt(i);
        if (i < num.length()-1) {
            for (char c : op) {
                addOperatorsRec(i+1, num, op, target, pick + c, ans);
            }
        }
        else if (i == num.length()-1) {
            addOperatorsRec(i, num, op, target, pick, ans);
        }
    }

    private static boolean evaluateTarget(String picks, int target) {
        String pick = picks;
        for (int i = 0; i < pick.length(); i++) {
            if (pick.charAt(i) == '*') {
                char op1 = pick.charAt(i-1);
                char op2 = pick.charAt(i+1);
                String res = String.valueOf(Integer.parseInt(String.valueOf(op1))*Integer.parseInt(String.valueOf(op2)));
                pick = pick.substring(0, i-1) + res + (i + 2 < pick.length() ? pick.substring(i+2) : "");
            }
            else if (pick.charAt(i) == '/') {
                char op1 = pick.charAt(i-1);
                char op2 = pick.charAt(i+1);
                String res = String.valueOf(Integer.parseInt(String.valueOf(op1))/Integer.parseInt(String.valueOf(op2)));
                pick = pick.substring(0, i-1) + res + (i + 2 < pick.length() ? pick.substring(i+2) : "");
            }
        }
        for (int i = 0; i < pick.length(); i++) {
            if (pick.charAt(i) == '+') {
                char op1 = pick.charAt(i-1);
                char op2 = pick.charAt(i+1);
                String res = String.valueOf(Integer.parseInt(String.valueOf(op1))+Integer.parseInt(String.valueOf(op2)));
                pick = pick.substring(0, i-1) + res + (i + 2 < pick.length() ? pick.substring(i+2) : "");
            }
            else if (pick.charAt(i) == '-') {
                char op1 = pick.charAt(i-1);
                char op2 = pick.charAt(i+1);
                String res = String.valueOf(Integer.parseInt(String.valueOf(op1))-Integer.parseInt(String.valueOf(op2)));
                pick = pick.substring(0, i-1) + res + (i + 2 < pick.length() ? pick.substring(i+2) : "");
            }
        }
        while (pick.length() != 1) {
            return evaluateTarget(pick, target);
        }
        return Integer.valueOf(pick) == target;
    }

    static void bitManipulation(int num, int i) {
        System.out.println(String.valueOf((num>>i)&1) + " " + String.valueOf((1<<i)|num) + " " + String.valueOf(~(1<<i)&num));
    }

    public int divide(int dividend, int divisor) {
        if (dividend == divisor) {
            return 1;
        }
        boolean isPositive = (dividend >= 0 == divisor >= 0);
        int a = Math.abs(dividend);
        int b = Math.abs(divisor);
        int ans = 0;
        while (a >= b) {
            int q = 0;
            while (a >= b*(1<<q+1)) {
                q++;
            }
            ans += 1<<q;
            a -= b<<q;
        }
        if (ans == (1<<31) && isPositive) {
            return Integer.MAX_VALUE;
        }
        return isPositive ? ans : -ans;
    }

    public int[] twoOddNum(int Arr[], int N)
    {
        int xor = 0;
        for (int i = 0; i < N; i++) {
            xor = xor ^ Arr[i];
        }
        int counter = 0;
        int temp = xor;
        while(temp != 0) {
            if ((temp&1) != 0) {
                break;
            }
            counter++;
            temp = temp>>1;
        }
        int test = 1<<counter;
        int a = 0;
        int b = 0;
        for (int i = 0; i < N; i++) {
            if ((Arr[i]&test) == 0) {
                a = a^Arr[i];
            }
            else {
                b = b^Arr[i];
            }
        }
        int[] ans = new int[]{a,b};
        return ans;
    }

    public int countPrimes(int n) {
        boolean[] isPrime = new boolean[n];
        for (int i = 2; i < n; i++) {
            isPrime[i] = true;
        }
        int count = 0;
        for (int i = 2; i < n; i++) {
            if (isPrime[i]) {
                count++;
                for (int j = i; j*j < n; j = j + i) {
                    isPrime[j] = false;
                }
            }
        }
        return count;
    }

    public int lengthOfLongestSubstring(String s) {
        int start = 0, end = -1, n = s.length();
        String pick = "";
        int maxLength = 0;
        while (start < n) {
            while (end+1 < n && !pick.contains(String.valueOf(s.charAt(end+1)))){
                pick += s.charAt(++end);
            }
            maxLength = Math.max(maxLength, pick.length());
            pick = pick.substring(start+1);
            start++;
        }
        return maxLength;
    }

    public static void main(String[] args) {
        minWindow("ADOBECODEBANC" , "ABC");
    }

    public static int longestOnes(int[] nums, int k) {
        int start = 0, end = -1, n = nums.length;
        int count = 0;
        int maxLength = 0;
        int c = k;
        int secondZero = -1;
        while (start < n) {
            while (end + 1 < n) {
                if (nums[end+1] == 1) {
                    count++;
                    end++;
                }
                else if (k > 0) {
                    if (k == c-1) {
                        secondZero = end+1;
                    }
                    k--;
                    count++;
                    end++;
                }
                else {
                    break;
                }
            }
            maxLength = Math.max(maxLength, count);
            int newStart = secondZero == -1 ? ++start : secondZero;
            count = end - start + 1;
            start = newStart;
            k = 1;
            secondZero = -1;
        }
        return maxLength;
    }

    public static int totalFruits(int N, int[] fruits) {
        int start = 0;
        Map<Integer, Integer> m = new HashMap<>();
        m.put(fruits[0], 1);
        int maxLength = 0;
        int first = fruits[0], second = -1;
        for (int end = 1; end < N; end++) {
            if (m.containsKey(fruits[end])) {
                m.put(fruits[end], m.get(fruits[end])+1);
            }
            else if (second == -1) {
                m.put(fruits[end], 1);
                second = fruits[end];
            }
            else {
                maxLength = Math.max(m.get(first) + m.get(second), maxLength);
                m.remove(first);
                first = second;
                second = -1;
                end--;
            }
        }
        maxLength = Math.max(m.get(first) + m.get(second), maxLength);
        return maxLength;
    }

    public static int numSubarraysWithSum(int[] nums, int goal) {
        int start = 0, end = 0, n = nums.length;
        int totalSub = 0, curSum = 0;
        while (end < n) {
            curSum += nums[end];
            if (curSum < goal) {
                end++;
            }
            else if (curSum == goal) {
                totalSub++;
                end++;
                if (end == n) {
                    while (start < n && nums[start] == 0) {
                        start++;
                        totalSub++;
                    }
                }
            }
            else if (curSum > goal) {
                while (curSum > goal) {
                    curSum -= nums[start];
                    curSum -= nums[end];
                    start++;
                }
            }
        }
        return totalSub;
    }

    public static int numberOfSubstrings(String s) {
        int start = 0, n = s.length(), ans = 0, k = 3;
        Map<Character, Integer> m = new HashMap<>();
        for (int end = 0; end < n; end++) {
            if (m.size() != 3) m.put(s.charAt(end), m.getOrDefault(s.charAt(end), 0) + 1);
            if (m.size() == 3) {
                ans += n-end;
                int x = m.get(s.charAt(start)) -1;
                if (x == 0) {
                    m.remove(s.charAt(start));
                } else {
                    m.put(s.charAt(start), x);
                }
                start++;
            }
        }
        return ans;
    }

    public int subarraysWithKDistinct(int[] nums, int k) {
        return atMost(nums, k) - atMost(nums, k - 1);
    }

    private int atMost(int[] nums, int i) {
        int start = 0, ans = 0, n = nums.length;
        Map<Integer, Integer> m = new HashMap<>();
        for (int end = 0; end < n; end++) {
            m.put(nums[end], m.getOrDefault(nums[end], 0) + 1);
            while (start < n && m.size() > i) {
                int x = m.get(nums[start]) -1;
                if (x == 0) {
                    m.remove(nums[start]);
                } else {
                    m.put(nums[start], x);
                }
                start++;
            }
            ans = ans + end - start +1;
        }
        return ans;
    }

    public static String minWindow(String s, String t) {
        int start = 0, end = 0, n = s.length(), m = t.length();
        Map<Character, Integer> mp = new HashMap<>();
        for (char c : t.toCharArray()) {
            mp.put(c, mp.getOrDefault(c, 0)+1);
        }
        String ans = "", pick = "";
        int minL = Integer.MAX_VALUE;
        int count = mp.size();
        for (end = 0; end <= n; end++) {
            while (count == 0) {
                if (mp.get(s.charAt(start)) != null) {
                    int x = mp.get(s.charAt(start));
                    x++;
                    mp.put(s.charAt(start), x);
                    if (x > 0) {
                        count++;
                    }
                }
                pick = pick.substring(1);
                start++;
                if (count == 0) {
                    if (pick.length() < minL) {
                        ans = pick;
                        minL = pick.length();
                    }
                }
            }
            if (end < n) {
                if (mp.get(s.charAt(end)) != null) {
                    int x = mp.get(s.charAt(end));
                    x--;
                    if (x == 0) count--;
                    mp.put(s.charAt(end), x);
                }
                pick += s.charAt(end);
                if (count == 0) {
                    if (pick.length() < minL) {
                        ans = pick;
                        minL = pick.length();
                    }
                }
            }
        }
        return ans;
    }
}
