package Tree;

import javafx.beans.binding.IntegerBinding;
import javafx.util.Pair;

import java.util.*;

public class BinaryTree {

    static class Node {
        int data;
        Node left;
        Node right;

        public Node(int data) {
            this.data = data;
            left = right = null;
        }
    }

    public static Node insert(Node root, int data) {
        if (root == null) {
            root = new Node(data);
        }
        else {
            if (data <= root.data) {
                root.left = insert(root.left, data);
            }
            else {
                root.right = insert(root.right, data);
            }
        }
        return root;
    }

    public static boolean search (Node root, int data) {
        if (root == null) return false;
        else if (root.data == data) return true;
        else if (root.data < data) return search(root.right, data);
        else return search(root.left, data);
    }

    public static boolean isBST(Node root, int minV, int maxV) {
        if (root == null) return true;
        else if (root.data > minV && root.data <= maxV && isBST(root.left, minV, root.data) && isBST(root.right, root.data, maxV)) return true;
        else return false;
    }

    public static Node delete (Node root, int data) {
        if (root == null) return root;
        else if (data < root.data) root.left = delete(root.left, data);
        else if (data > root.data) root.right = delete(root.right, data);
        else {
            // no child
            if (root.left == null && root.right == null) {
                root = null;
                return root;
            }
            else if (root.left == null && root.right != null) {
                root = root.right;
                return root;
            }
            else if (root.right == null && root.left != null) {
                root = root.left;
                return root;
            }
            else {
                Node temp = findMin(root.right);
                root.data = temp.data;
                root.right = delete(root.right, temp.data);
            }
        }
        return root;
    }

    private static Node findMin(Node root) {
        while (root.left != null) {
            root = root.left;
        }
        return root;
    }

    static void inOrder(Node root) {
        if (root == null) return;
        inOrder(root.left);
        System.out.println(root.data + " ");
        inOrder(root.right);
    }

    static void preOrder(Node root) {
        if (root == null) return;
        System.out.println(root.data + " ");
        preOrder(root.left);
        preOrder(root.right);
    }

    static void postOrder(Node root) {
        if (root == null) return;
        postOrder(root.left);
        postOrder(root.right);
        System.out.println(root.data + " ");
    }

    public static void main(String[] args) {
        Node root = null;
        root = insert(root, 2);
        root = insert(root, 1);
        root = insert(root, 3);
        lowestCommonAncestor(root, root.left, root.right);
        isNStraightHand(new int[]{1,2,3,4,5,6}, 2);
//        root = insert(root, 6);
//        root =  insert(root, 7);
//        root = insert(root, 1);
//        inOrder(root);
//        System.out.println(isBST(root, Integer.MIN_VALUE, Integer.MAX_VALUE));
//        System.out.println(search(root, 2));
//        delete(root, 2);
//        inOrder(root);
//        System.out.println(search(root, 2));
    }

    static ArrayList<Integer> preorder(Node root)
    {
        ArrayList<Integer> list = new ArrayList<>();
        preOrderRec(root, list);
        return list;
    }

    private static void preOrderRec(Node root, List<Integer> list) {
        if (root == null) return;
        list.add(root.data);
        preOrderRec(root.left, list);
        preOrderRec(root.right, list);
    }

    public List<List<Integer>> levelOrder(Node root) {
        List<List<Integer>> list = new ArrayList<>();
        if (root == null) return list;
        Queue<Node> q = new LinkedList<>();
        q.add(root);
        while (!q.isEmpty()) {
            List<Integer> l = new ArrayList<>();
            int staticSize  = q.size();
            for (int i = 0; i < staticSize; i++) {
                Node node = q.remove();
                l.add(node.data);
                if (node.left != null) q.add(node.left);
                if (node.right != null) q.add(node.right);
            }
            list.add(l);
        }
        return list;
    }

    public List<Integer> preorderTraversal(Node root) {
        // iterative
        List<Integer> l = new ArrayList<>();
        if (root == null) return l;
        Stack<Node> s = new Stack<>();
        s.push(root);
        while (!s.empty()) {
            Node node = s.pop();
            l.add(node.data);
            if (node.right != null) s.push(node.right);
            if (node.left != null) s.push(node.left);
        }
        return l;
    }

    public List<Integer> inorderTraversal(Node root) {
        // iterative
        List<Integer> l = new ArrayList<>();
        if (root == null) return l;
        Stack<Node> s = new Stack<>();
        Node cur = root;
        while (true) {
            if (cur != null) {
                s.push(cur);
                cur = cur.left;
            }
            else {
                if (s.empty()) break;
                cur = s.pop();
                l.add(cur.data);
                cur = cur.right;
            }
        }
        return l;
    }

    public List<Integer> postorderTraversal(Node cur) {
        // iterative
        List<Integer> l = new ArrayList<>();
        if (cur == null) return l;
        Stack<Node> s = new Stack<>();
        while (!s.empty()) {
            if (cur != null) {
                s.push(cur);
                cur = cur.left;
            }
            else {
                Node temp = s.peek().right;
                if (temp == null) {
                    temp = s.pop();
                    l.add(temp.data);
                    while (!s.empty() && temp == s.peek().right) {
                        temp = s.pop();
                        l.add(temp.data);
                    }
                }
                else {
                    cur = temp;
                }
            }
        }
        return l;
    }

    public int maxDepth(Node root) {
        if (root == null) return 0;
        return Math.max(maxDepth(root.left), maxDepth(root.right))+1;
    }

    public boolean isBalanced(Node root) {
        if (root == null) return true;
        int left = maxDepth(root.left);
        int right = maxDepth(root.right);
        if (Math.abs(left-right) <= 1 && isBalanced(root.left) && isBalanced(root.right)) return true;
        return false;
    }

    // distance between two nodes which have longest path in tree
    public int diameterOfBinaryTree(Node root) {
        if (root == null) return 0;
        int left = maxDepth(root.left);
        int right = maxDepth(root.right);
        int lD = diameterOfBinaryTree(root.left);
        int rD = diameterOfBinaryTree(root.right);
        return Math.max(left+right-1, Math.max(lD, rD));
    }

    int maxi = Integer.MIN_VALUE;

    public int maxPathSum(Node root) {
        maxPathSumRec(root);
        return maxi;
    }

    public int maxPathSumRec(Node root) {
        if (root == null) return 0;
        int left = Math.max(0, maxPathSumRec(root.left));
        int right = Math.max(0, maxPathSumRec(root.right));
        maxi = Math.max(maxi, left+right+root.data);
        return root.data+Math.max(left, right);
    }

    public List<List<Integer>> zigzagLevelOrder(Node root) {
        List<List<Integer>> ans = new ArrayList<>();
        Stack<Pair<Node, Integer>> s = new Stack();
        if (root == null) return ans;
        s.push(new Pair<>(root, 0));
        while (!s.empty()) {
            int staticSize = s.size();
            List<Integer> pick = new ArrayList<>();
            Queue<Pair<Node, Integer>> q = new LinkedList<>();
            for (int i = 0; i < staticSize; i++) {
                Node temp = s.peek().getKey();
                int level = s.peek().getValue();
                s.pop();
                pick.add(temp.data);
                if (level%2 == 0) {
                    if (temp.left != null) q.add(new Pair<>(temp.left, level+1));
                    if (temp.right != null) q.add(new Pair<>(temp.right, level+1));
                }
                else if (level%2 != 0) {
                    if (temp.right != null) q.add(new Pair<>(temp.right, level+1));
                    if (temp.left != null) q.add(new Pair<>(temp.left, level+1));
                }
            }
            while (!q.isEmpty()) {
                s.push(q.remove());
            }
            ans.add(pick);
        }
        return ans;
    }

    ArrayList <Integer> boundary(Node node)
    {
        ArrayList<Integer> ans = new ArrayList<>();
        Node temp = node;
        Node prevNodeLeft = null;
        while (temp != null) {
            ans.add(temp.data);
            prevNodeLeft = temp;
            temp = temp.left;
        }
        temp = node;
        inOrderBoundary(temp, prevNodeLeft, ans);
        temp = node.right;
        Stack<Integer> s = new Stack<>();
        while (temp != null) {
            s.push(temp.data);
            temp = temp.right;
        }
        if (!s.empty()) s.pop();
        while (!s.empty()) {
            ans.add(s.pop());
        }
        return ans;
    }

    private void inOrderBoundary(Node temp, Node prevNodeLeft, ArrayList<Integer> ans) {
        if (temp == null) return;
        if (temp.left == null && temp.right == null && temp != prevNodeLeft) ans.add(temp.data);
        inOrderBoundary(temp.left, prevNodeLeft, ans);
        inOrderBoundary(temp.right, prevNodeLeft, ans);
    }

    public List<List<Integer>> verticalTraversal(Node root) {
        List<List<Integer>> ans = new ArrayList<>();
        TreeMap<Integer, List<Pair<Integer, Integer>>> m = new TreeMap<>();
        inOrderVT(root, m, 0, 0);
        Set<Integer> s = m.keySet();
        for (int i : s) {
            List<Pair<Integer, Integer>> pL = m.get(i);
            Collections.sort(pL, new Comparator<Pair<Integer, Integer>>() {
                @Override
                public int compare(Pair<Integer, Integer> o1, Pair<Integer, Integer> o2) {
                    if (o1.getKey() == o2.getKey()) {
                        return o1.getValue().compareTo(o2.getValue());
                    }
                    return o1.getKey().compareTo(o2.getKey());
                }
            });
            List<Integer> pick = new ArrayList<>();
            for (Pair<Integer, Integer> p : pL) {
                pick.add(p.getValue());
            }
            ans.add(pick);
        }
        return ans;
    }

    private void inOrderVT(Node root, TreeMap<Integer, List<Pair<Integer, Integer>>> m, int col, int row) {
        if (root == null) return;
        if (m.get(col) == null) {
            List<Pair<Integer, Integer>> l = new ArrayList<>();
            m.put(col, l);
        }
        m.get(col).add(new Pair<>(row, root.data));
        inOrderVT(root.left, m, col-1, row+1);
        inOrderVT(root.right, m, col+1, row+1);
    }

    static ArrayList<Integer> topView(Node root)
    {
        ArrayList<Integer> ans = new ArrayList<>();
        TreeMap<Integer, Pair<Integer, Integer>> m = new TreeMap<>();
        inOrderTV(root, m, 0, 0);
        for (Pair<Integer, Integer> pair : m.values()) {
            ans.add(pair.getValue());
        }
        return ans;
    }

    private static void inOrderTV(Node root, TreeMap<Integer, Pair<Integer, Integer>> m, int col, int row) {
        if (root == null) return;
        if (m.get(col) == null || m.get(col).getKey() < row) {
            m.put(col, new Pair<>(row, root.data));
        }
        inOrderTV(root.left, m, col-1, row+1);
        inOrderTV(root.right, m, col+1, row+1);
    }

    static ArrayList<Integer> bottomView(Node root)
    {
        ArrayList<Integer> ans = new ArrayList<>();
        TreeMap<Integer, Pair<Integer, Integer>> m = new TreeMap<>();
        inOrderBV(root, m, 0, 0);
        for (Pair<Integer, Integer> pair : m.values()) {
            ans.add(pair.getValue());
        }
        return ans;
    }

    private static void inOrderBV(Node root, TreeMap<Integer, Pair<Integer, Integer>> m, int col, int row) {
        if (root == null) return;
        if (m.get(col) == null || m.get(col).getKey() > row) {
            m.put(col, new Pair<>(row, root.data));
        }
        inOrderBV(root.left, m, col-1, row+1);
        inOrderBV(root.right, m, col+1, row+1);
    }

    public List<Integer> rightSideView(Node root) {
        return levelOrderRV(root);
    }

    public List<Integer> levelOrderRV(Node root) {
        List<Integer> list = new ArrayList<>();
        if (root == null) return list;
        Queue<Node> q = new LinkedList<>();
        q.add(root);
        while (!q.isEmpty()) {
            List<Integer> l = new ArrayList<>();
            int staticSize  = q.size();
            int x = 0;
            for (int i = 0; i < staticSize; i++) {
                Node node = q.remove();
                x = node.data;
                if (node.left != null) q.add(node.left);
                if (node.right != null) q.add(node.right);
            }
            list.add(x);
        }
        return list;
    }

    public static ArrayList<ArrayList<Integer>> Paths(Node root){
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        ArrayList<Integer> pick = new ArrayList<>();
        dfs(root, pick, ans);
        return ans;
    }

    private static void dfs(Node root, ArrayList<Integer> pick, ArrayList<ArrayList<Integer>> ans) {
        if (root == null) {
            ans.add(new ArrayList<>(pick));
            return;
        }
        pick.add(root.data);
        dfs(root.left, pick, ans);
        dfs(root.right, pick, ans);
        pick.remove(pick.size()-1);
    }

    public static Node lowestCommonAncestor(Node root, Node p, Node q) {
        Node ans = null;
        Node first = null;
        Node second = null;
        postOrderLCA(root, p, q, first, second, ans);
        return ans;
    }

    private static void postOrderLCA(Node root, Node p, Node q, Node first, Node second, Node ans) {
        if (root == null) return;
        postOrderLCA(root.left, p, q, first, second, ans);
        postOrderLCA(root.right, p, q, first, second, ans);
        if (root.data == p.data) {
            if (first == null) first = p;
            else second = p;
        }
        else if (root.data == q.data) {
            if (first == null) first = q;
            else second = q;
        }
        else if (first != null && second != null && ans == null) ans = root;
    }

    public int widthOfBinaryTree(Node root) {
        return levelOrderWidth(root);
    }

    public int levelOrderWidth(Node root) {
        if (root == null) return 0;
        Queue<Node> q = new LinkedList<>();
        q.add(root);
        int ans = 0;
        while (!q.isEmpty()) {
            int staticSize  = q.size();
            int firstNonNull = -1;
            int secondNonNull = 0;
            List<Node> list = new ArrayList<>();

            for (int i = 0; i < staticSize; i++) {
                Node node = q.remove();
                if (node == null && firstNonNull != -1) {
                    list.add(null);
                    list.add(null);
                }
                else {
                    q.addAll(list);
                    if (firstNonNull == -1) {
                        firstNonNull = i;
                    }
                    secondNonNull = i;
                    q.add(node.left);
                    q.add(node.right);
                }
            }
            if (firstNonNull != -1) {
                ans = Math.max(ans, secondNonNull-firstNonNull+1);
            }
        }
        return ans;
    }

    public List<Integer> distanceK(Node root, Node target, int k) {
       Map<Node, Node> cTop = new HashMap<>();
       cTop.put(root, null);
       inOrderChildParent(root, cTop);
       Set<Integer> visited = new HashSet();
       visited.add(target.data);
       List<Integer> ans = new ArrayList<>();
       inOrderDisK(target, k, 0, ans, visited, cTop);
       return ans;
    }

    private void inOrderChildParent(Node root, Map<Node, Node> cTop) {
        if (root == null) return;
        if (root.left != null) {
            cTop.put(root.left, root);
            inOrderChildParent(root.left, cTop);
        }
        if (root.right != null) {
            cTop.put(root.right, root);
            inOrderChildParent(root.right, cTop);
        }
    }

    private void inOrderDisK(Node root, int k, int i, List<Integer> ans, Set<Integer> visited, Map<Node, Node> cTop) {
        if (root == null) return;
        if (i == k) {
            ans.add(root.data);
            return;
        }
        if (root.left != null && !visited.contains(root.left.data)) {
            visited.add(root.left.data);
            inOrderDisK(root.left, k, i+1, ans, visited, cTop);
            visited.remove(root.left.data);
        }
        if (root.right != null && !visited.contains(root.right.data)) {
            visited.add(root.right.data);
            inOrderDisK(root.right, k, i+1, ans, visited, cTop);
            visited.remove(root.right.data);
        }
        if (cTop.get(root) != null && !visited.contains(cTop.get(root).data)) {
            visited.add(cTop.get(root).data);
            inOrderDisK(cTop.get(root), k, i+1, ans, visited, cTop);
            visited.remove(cTop.get(root).data);
        }
    }

    int ans = Integer.MIN_VALUE;
    int totalNodes = 0;
    public int minTime(Node root, int t)
    {
        Map<Node, Node> cTop = new HashMap<>();
        cTop.put(root, null);
        inOrderChildParent(root, cTop);
        Node target = levelOrderBurnTree(root, t);
        Set<Integer> visited = new HashSet();
        visited.add(t);
        inOrderDisBurnTree(target, 0, visited, cTop);
        return ans;
    }

    public Node levelOrderBurnTree(Node root, int target) {
        if (root == null) return null;
        Queue<Node> q = new LinkedList<>();
        q.add(root);
        Node ans = null;
        while (!q.isEmpty()) {
            List<Integer> l = new ArrayList<>();
            int staticSize  = q.size();
            totalNodes += staticSize;
            for (int i = 0; i < staticSize; i++) {
                Node node = q.remove();
                if (node.data == target) ans = node;
                if (node.left != null) q.add(node.left);
                if (node.right != null) q.add(node.right);
            }
        }
        return ans;
    }

    private void inOrderDisBurnTree(Node root, int i, Set<Integer> visited, Map<Node, Node> cTop) {
        if (root == null) return;
        if (root.left != null && !visited.contains(root.left.data)) {
            visited.add(root.left.data);
            inOrderDisBurnTree(root.left, i+1, visited, cTop);
            visited.remove(root.left.data);
        }
        if (root.right != null && !visited.contains(root.right.data)) {
            visited.add(root.right.data);
            inOrderDisBurnTree(root.right, i+1, visited, cTop);
            visited.remove(root.right.data);
        }
        if (cTop.get(root) != null && !visited.contains(cTop.get(root).data)) {
            visited.add(cTop.get(root).data);
            inOrderDisBurnTree(cTop.get(root), i+1, visited, cTop);
            visited.remove(cTop.get(root).data);
        }
        if (visited.size() == totalNodes) ans = Math.max(ans, i);
    }

    int nullNodes = 0;


    public int countNodes(Node root) {
        return levelOrderCompBinTree(root);
    }


    public int levelOrderCompBinTree(Node root) {
        if (root == null) return 0;
        Queue<Node> q = new LinkedList<>();
        q.add(root);
        int ans = 0;
        while (!q.isEmpty()) {
            List<Integer> l = new ArrayList<>();
            int staticSize  = q.size();
            ans += staticSize;
            int nullNodes = 0;
            for (int i = 0; i < staticSize; i++) {
                Node node = q.remove();
                l.add(node.data);
                if (node.left != null) q.add(node.left);
                else nullNodes++;
                if (node.right != null) q.add(node.right);
                else nullNodes++;
            }
            if (nullNodes != 0 && !q.isEmpty()) return ans+q.size();
        }
        return 0;
    }

    public static int cur = 0;

    public Node buildTree(int[] preorder, int[] inorder) {
        cur = 0;
        Map<Integer, Integer> m = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            m.put(inorder[i], i);
        }
        return buildTreeRec(preorder, inorder, m,0, inorder.length-1);
    }

    private Node buildTreeRec(int[] preorder, int[] inorder, Map<Integer, Integer> m, int start, int end) {
        if (cur >= end) return null;
        Node node = new Node(preorder[cur++]);
        int tI = m.get(node.data);
        if (tI - start > 0) {
            node.left = buildTreeRec(preorder, inorder, m, start, tI-1);
        }
        if (end - tI > 0) {
            node.left = buildTreeRec(preorder, inorder, m, tI+1, end);
        }
        return node;
    }

    int a = Integer.MAX_VALUE;
    int findCeil(Node root, int key) {
        if (root == null) return -1;
        if (root.data == key) return key;
        else if (root.data < key) {
            if (root.data < a) a = root.data;
            findCeil(root.right, key);
        }
        else if (root.data > key) {
            if (root.data < a) a = root.data;
            findCeil(root.left, key);
        }
        return a;
    }

    class Res {
        Node pre = null;
        Node succ = null;
    }

    public static void findPreSuc(Node root, Res p, Res s, int key)
    {
        if (root == null) {
            return;
        }
        else if (root.data == key) {
            if (root.left != null) {
                Node temp = root.left;
                while (temp != null) {
                    p.pre = temp;
                    temp = temp.right;
                }
            }
            if (root.right != null) {
                Node temp = root.right;
                while (temp != null) {
                    s.succ = temp;
                    temp = temp.left;
                }
            }
        }
        else {
            if (root.data < key) {
                s.succ = root;
                findPreSuc(root.left, p, s, key);
            }
            else {
                p.pre = root;
                findPreSuc(root.right, p, s, key);
            }
        }
    }


    Node prev = new Node(Integer.MIN_VALUE);
    Node first = null, second = null; // elements that needs to be swapped
    public void recoverTree(Node root) {
        if (root == null) return;
        inorderRecTree(root);
        int temp = first.data;
        first.data = second.data;
        second.data = temp;
    }

    private void inorderRecTree(Node root) {
        int[] harr = new int[5];
        harr = Arrays.copyOf(harr, harr.length+1);
        if (root == null) return;
        inorderRecTree(root.left);
        if (first == null && prev.data >= root.data) {
            first = root;
        }
        if (first != null && prev.data >= root.data) {
            second = root;
        }
        prev = root;
        inorderRecTree(root.right);
    }

    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>((a,b) -> b-a);
        for (int i = 0; i < nums.length; i++) {
            pq.add(nums[i]);
        }
        while (k > 0) {
            pq.remove();
        }
        return pq.peek();
    }

    public static ArrayList<Integer> mergeKArrays(int[][] arr,int K)
    {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int i = 0; i < K; i++) {
            for (int j = 0; j < K; j++) {
                pq.add(arr[i][j]);
            }
        }
        ArrayList<Integer> ans = new ArrayList<>();
        for (int i = 0; i < K*K; i++) {
            ans.add(pq.remove());
        }
        return ans;
    }

    public Node mergeKLists(Node[] lists) {
        // one way is to store all nodes in priority queue and then form new LL
        return recMerge(lists, 0, lists.length-1);
    }

    private Node recMerge(Node[] lists, int l, int h) {
        if (lists == null || lists.length == 0) return null;
        if (l == h) return lists[l];
        if (h-l == 1) {
            return merge(lists[l], lists[h]);
        }
        int m = (l+h)/2;
        Node left = recMerge(lists, l, m);
        Node right = recMerge(lists, m+1, h);
        return merge(left, right);
    }

    private Node merge(Node list, Node list1) {
        if (list == null) return list1;
        if (list1 == null) return list;
        Node temp = list;
        Node temp1 = list1;
        Node cur = new Node(0);
        while (temp != null && temp1 != null) {
            if (temp.data <= temp1.data) {
                cur.left = temp;
                temp = temp.left;
            }
            else {
                cur.left = temp1;
                temp1 = temp1.left;
            }
            cur = cur.left;
        }
        if (temp != null) cur.left = temp;
        if (temp1 != null) cur.left = temp1;
        return list.data <= list1.data ? list : list1;
    }

    public static boolean isNStraightHand(int[] hand, int groupSize) {
        TreeMap<Integer, Integer> m = new TreeMap<>();
        for (int i : hand) {
            m.put(i, m.getOrDefault(i, 0)+1);
        }
        for (int i = 0; i < groupSize; i++) {
            int x = groupSize;
            int p = -1;
            for (int j : m.keySet()) {
                if (m.get(j) == -1) continue;
                if (p != -1) {
                    if (j -p != 1) return false;
                }
                p = j;
                m.put(j, m.get(j)-1);
                if (m.get(j) == 0) m.put(j, -1);
                x--;
                if (x == 0)break;
            }
        }
        return true;
    }

    long minCost(long arr[], int n)
    {
        PriorityQueue<Long> pq = new PriorityQueue<>(new Comparator<Long>() {
            @Override
            public int compare(Long o1, Long o2) {
                return o1.compareTo(o2);
            }
        });
        for (long a : arr) {
            pq.add(a);
        }
        while (pq.size() != 1) {
            pq.add(pq.remove()+pq.remove());
        }
        return pq.peek();
    }

    static List<Integer> maxCombinations(int N, int K, int A[], int B[]) {
        PriorityQueue<Integer> pq1 = new PriorityQueue<>((a,b)->b-a);
        PriorityQueue<Integer> pq2 = new PriorityQueue<>((a,b)->b-a);
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            pq1.add(A[i]);
            pq2.add(B[i]);
        }
        int a = pq1.remove();
        int b = pq2.remove();
        ans.add(a+b);
        K--;
        while (K > 0) {
            if (pq1.size() != 0 && pq2.size() != 0 && pq1.peek()+b >= a+pq2.peek() && K > 0) {
                ans.add(pq1.remove()+b);
                K--;
            }
            if (pq1.size() != 0 && pq2.size() != 0 && pq1.peek()+b < a+pq2.peek() && K > 0) {
                ans.add(pq2.remove()+b);
                K--;
            }
        }
        return ans;
    }
}
