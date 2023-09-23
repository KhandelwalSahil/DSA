package StackQueues;

import java.util.*;

public class StackQueue {

    static class Stack {
        private int[] s;
        private int top;

        public Stack(int n) {
            this.s = new int[n];
            this.top = -1;
        }
    }

    static class Queue {
        private int[] q;
        private int front;
        private int rear;

        public Queue(int n) {
            this.q = new int[n];
            this.front = -1;
            this.rear = -1;
        }
    }

    public static void main(String[] args) {
        doStacking();
        doQueueing();
        int[] nums = {1,2,1};
        nextGreaterElements(nums);
    }

    private static void doQueueing() {
        Queue queue = new Queue(10);
        deQueue(queue);
        enQueue(queue, 1, 10);
        enQueue(queue, 2, 10);
        deQueue(queue);
        deQueue(queue);
        deQueue(queue);
        enQueue(queue, 1, 10);
        enQueue(queue, 2, 10);
        deQueue(queue);
        deQueue(queue);
        deQueue(queue);
    }

    public static void doStacking() {
        Stack stack = new Stack(10);
        pop(stack);
        push(stack, 1, 10);
        push(stack, 2, 10);
        pop(stack);
        pop(stack);
        pop(stack);
    }

    public static void push(Stack stack, int x, int n) {
        if (stack.top == n-1) {
            System.out.println("Stack is full");
            return;
        }
        stack.top = stack.top + 1;
        stack.s[stack.top] = x;
        System.out.println("Pushed " + x);
    }

    public static int pop(Stack stack) {
        if (stack.top == -1) {
            System.out.println("Stack is empty");
            return -1;
        }
        int x = stack.s[stack.top];
        stack.top = stack.top - 1;
        System.out.println("Popped " + x);
        return x;
    }

    public static void enQueue(Queue queue, int x, int n) {
        if (queue.rear == n-1) {
            System.out.println("Queue is full");
            return;
        }
        else if (queue.front == -1 && queue.rear == -1) {
            queue.front = 0;
        }
        queue.rear = queue.rear + 1;
        queue.q[queue.rear] = x;
        System.out.println("Queued " + x);
    }

    public static int deQueue(Queue queue) {
        if (queue.front == -1 && queue.rear == -1) {
            System.out.println("Queue is empty");
            return -1;
        }
        else if (queue.front == queue.rear) {
            int p = queue.q[queue.front];
            System.out.println("Dequeued " + p);
            queue.rear = -1;
            queue.front = -1;
            return p;
        }
        int x = queue.q[queue.front];
        queue.front = queue.front + 1;
        System.out.println("Dequeued " + x);
        return x;
    }

    public static boolean isValid(String s) {
        java.util.Stack<Character> stack = new java.util.Stack<>();
        for (char c : s.toCharArray()) {
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            }
            else {
                if (stack.empty()) {
                    return false;
                }
                char chr = stack.pop();
                if ((c == '(' && chr == ')') || (c == '[' && chr == ']') || (c == '{' && chr == '}')) {
                    continue;
                }
            }
        }
        return s.isEmpty();
    }
    public static String infixToPostfix(String exp) {
        Map<Character, Integer> prec = new HashMap();
        prec.put('+',1);
        prec.put('-',1);
        prec.put('/',2);
        prec.put('*',2);
        prec.put('^',3);
        List<Character> list = new ArrayList();
        list.addAll(Arrays.asList('+', '-', '/', '*', '^'));
        java.util.Stack<Character> stack = new java.util.Stack<>();
        String ans = "";
        for (char c : exp.toCharArray()) {
            if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    ans += stack.pop();
                }
                stack.pop();
            }
            else if (c == '(') {
                stack.push(c);
            }
            else if (list.contains(c)) {
                while (!stack.isEmpty() && stack.peek() != '(' && prec.get(stack.peek()) > prec.get(c)) {
                    ans += stack.pop();
                }
                stack.push(c);
            }
            else {
                ans += c;
            }
        }
        return ans;
    }

    public static int[] nextGreaterElements(int[] nums) {
        java.util.Stack<Integer> s = new java.util.Stack<>();
        int n = nums.length;
        int[] ans = new int[n];
        for (int i = 2*n-1; i >= 0; i--) {
            while (!s.empty() && nums[i%n] > s.peek()) {
                s.pop();
            }
            ans[i%n] = s.empty() ? -1 : s.peek();
            s.push(nums[i%n]);
        }
        return ans;
    }

    public long subArrayRanges(int[] nums) {
        java.util.Stack<Integer> s = new java.util.Stack<>();
        int n = nums.length;
        long answer = 0;
        for (int i = 0; i <= n; i++) {
            while (!s.empty() && ((i == n) && nums[s.peek()] >= nums[i])) {
                int mid = s.pop();
                int left = s.empty() ? -1 : s.peek();
                answer -= (i-mid)*(mid-left)*nums[i];
            }
            s.push(i);
        }
        s.clear();
        for (int i = 0; i <= n; i++) {
            while (!s.empty() && ((i == n) && nums[s.peek()] <= nums[i])) {
                int mid = s.pop();
                int left = s.empty() ? -1 : s.peek();
                answer += (i-mid)*(mid-left)*nums[i];
            }
            s.push(i);
        }
        return answer;
    }

    public String removeKdigits(String num, int k) {
        java.util.Stack<Character> stack = new java.util.Stack<>();
        int n = num.length();
        if(k == 0) {
            return num;
        }
        for (int i = 0; i < n; i++) {
            while (k > 0 && !stack.empty() && num.charAt(i) < stack.peek()) {
                k--;
                stack.pop();
            }
            if (stack.size() == 1 && stack.peek() == '0') {
                stack.pop();
            }
        }
        while (k > 0 && !stack.empty()) {
            k--;
            stack.pop();
        }
        String ans = "";
        while (!stack.empty()) {
            ans = stack.pop() + ans;
        }
        return ans;
    }

    public int largestRectangleArea(int[] heights) {
        java.util.Stack<Integer> stack = new java.util.Stack();
        int n = heights.length;
        int maxA = 0;
        for (int i = 0; i <= n; i++) {
            while (!stack.empty() && ((i == n) || heights[stack.peek()] > heights[i])) {
                int height = heights[stack.pop()];
                int width = stack.empty() ? i : (i-stack.peek()-1);
                maxA = Math.max(maxA, height*width);
            }
            stack.push(i);
        }
        return maxA;
    }

    public int maximalRectangle(char[][] matrix) {
        int r = matrix.length;
        int c = matrix[0].length;
        int[] ans = new int[c];
        for (int i = 0; i < c; i++) {
            ans[i] = 0;
        }
        int ansMax = 0;
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (matrix[i][j] == 0) {
                    ans[j] = 0;
                }
                else {
                    ans[j] = ans[j]++;
                }
            }
            ansMax = Math.max(ansMax, largestRectangleArea(ans));
        }
        return ansMax;
    }

    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        int[] r = new int[n-k+1];
        int ri = 0;
        Deque<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            if (!q.isEmpty() && q.peek() == i-k) {
                q.poll();
            }
            while (!q.isEmpty() && nums[q.peekLast()] < nums[i]) {
                q.pollLast();
            }
            q.offer(i);
            if (i >= k-1) {
                r[ri++] = nums[q.peek()];
            }
        }
        return r;
    }

    public int orangesRotting(int[][] grid) {
        // BFS as we want to move ahead in all rotten oranges's neighbour directions in one go
        int row = grid.length;
        int col = grid[0].length;
        int countTotal = 0;
        java.util.Queue<int[]> queue = new LinkedList<>();
        for (int i  = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (grid[i][j] == 2) {
                    queue.offer(new int[]{i,j});
                }
                if (grid[i][j] != 0) {
                    countTotal++;
                }
            }
        }
        int count = 0, time = 0;
        int[] dx = {-1,0,+1,0};
        int[] dy = {0,1,0,-1};
        while (!queue.isEmpty()) {
            int size = queue.size();
            count += size;
            int[] point = queue.poll();
            for (int j = 0; j < size; j++) {
                int x = point[0] + dx[j];
                int y = point[1] + dy[j];
                if (x < 0 || y < 0 || x >= row || y >= col || grid[x][y] == 0 || grid[x][y] == 2) {
                    continue;
                }
                grid[x][y] = 2;
                queue.offer(new int[]{x,y});
            }
            if (!queue.isEmpty()) {
                time++;
            }
        }
        return countTotal == count ? time : -1;
    }
}
