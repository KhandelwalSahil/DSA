package LL;

import java.util.Arrays;
import java.util.Stack;

public class SingleLL {

    Node head;

    static class Node {
        private int data;
        private Node next;

        public Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    public static void main(String[] args) {
        SingleLL singleLL = new SingleLL();


        insert(singleLL, 4);
        insert(singleLL, 2);
        insert(singleLL, 6);
        insert(singleLL, 1);
        insert(singleLL, 3);
        insert(singleLL, 5);

        printLL(singleLL);

        // delete at nth position, 1-based
//        for (int i = 0; i < 5; i++) {
//            deleteNode(singleLL, 5-i);
//        }

        reverseLL(singleLL);
        printLL(singleLL);

        singleLL.head = reverseLLRec(singleLL.head);
        printLL(singleLL);

        System.out.println("Middle: " + findMiddlePointerLL(singleLL).data);
        System.out.println("isCyclePresent: " + isCyclicLL(singleLL));
//        insertCycle(singleLL, 3);
        System.out.println("isCyclePresent: " + isCyclicLL(singleLL));
        System.out.println("Length of loop: " + loopLength(singleLL));
        System.out.println("Sorting LL...");
        singleLL.head = sortLL(singleLL.head);
        printLL(singleLL);
    }

    private static int loopLength(SingleLL singleLL) {
        Node slow = singleLL.head, fast = singleLL.head;
        Node loopStart = singleLL.head;
        int loopLength = 0;

        while(fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                while (loopStart != slow) {
                    loopStart = loopStart.next;
                    slow = slow.next;
                }
                System.out.println("LoopStart: " + loopStart.data);
                slow = slow.next;
                loopLength = 1;
                while (slow != loopStart) {
                    slow = slow.next;
                    loopLength++;
                }
                return loopLength;
            }
        }
        return loopLength;
    }

    private static void insertCycle(SingleLL singleLL, int i) {
        Node curPoint = singleLL.head;
        Node cycleStart = null;
        while (curPoint.next != null) {
            if (curPoint.data == i) {
                cycleStart = curPoint;
            }
            curPoint = curPoint.next;
        }
        curPoint.next = cycleStart;
    }

    private static Node findMiddlePointerLL(SingleLL singleLL) {
        Node slow = singleLL.head, fast = singleLL.head;
        while(fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    private static boolean isCyclicLL(SingleLL singleLL) {
        Node slow = singleLL.head, fast = singleLL.head;
        while(fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return true;
            }
        }
        return false;
    }

    private static void deleteNode(SingleLL singleLL, int i) {
        if (i == 1) {
            singleLL.head = singleLL.head.next;
            return;
        }
        int itr = 1;
        Node curPointer = singleLL.head;
        Node prev = null;
        while(itr < i) {
            prev = curPointer;
            curPointer = curPointer.next;
            itr++;
            if (curPointer == null) {
                System.out.println("Not found");
                return;
            }
        }
        prev.next = curPointer.next;
    }

    private static void printLL(SingleLL singleLL) {
        System.out.println("Printing LL");
        Node curPointer = singleLL.head;
        while (curPointer != null) {
            System.out.println(curPointer.data);
            curPointer = curPointer.next;
        }
    }

    private static void insert(SingleLL singleLL, int i) {
        Node node = new Node(i);

        if (singleLL.head == null) {
            singleLL.head = node;
        }
        else {
            Node curPointer = singleLL.head;
            while(curPointer.next != null) {
                curPointer = curPointer.next;
            }
            curPointer.next = node;
        }
    }

    private static void reverseLL (SingleLL singleLL) {
        Node prev = null;
        Node curPointer = singleLL.head;
        Node next = null;

        while (curPointer != null) {
            next = curPointer.next;
            curPointer.next = prev;
            prev = curPointer;
            curPointer = next;
        }
        singleLL.head = prev;
    }

    private static Node reverseLLRec (Node head) {
        if (head == null || head.next == null) {
            return head;
        }
        Node reverseHead = reverseLLRec(head.next);
        head.next.next = head;
        head.next = null;
        return reverseHead;
    }

    private static Node sortLL(Node head) {
        // find mid pointer, divide and conquer, then do merging
        // why this works, because while merging whenever we compare two subsets, each subset individually is sorted

        if (head == null || head.next == null) {
            return head;
        }

        Node slow = head, fast = head, prev = head;

        while (fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        prev.next = null;
        Node l1 = sortLL(head);
        Node l2 = sortLL(slow);
        return mergeLL(l1, l2);
    }

    private static Node mergeLL(Node l1, Node l2) {
        Node cur = new Node(-1);
        Node head = cur;

        while (l1 != null && l2 != null) {
            if (l1.data <= l2.data) {
                cur.next = l1;
                l1 = l1.next;
            } else {
                cur.next = l2;
                l2 = l2.next;
            }
            cur = cur.next;
        }

        if (l1 != null) {
            cur.next = l1;
        }

        if (l2 != null) {
            cur.next = l2;
        }
        return head.next;
    }

    public Node addTwoNumbers(Node node1, Node node2) {
        Stack<Integer> s1 = new Stack<>();
        Stack<Integer> s2 = new Stack<>();
        Node l1 = node1;
        Node l2 = node2;
        while (l1 != null) {
            s1.push(l1.data);
            l1 = l1.next;
        }
        while (l2 != null) {
            s2.push(l2.data);
            l2 = l2.next;
        }
        Node prev = null;
        int cFwd = 0;
        Node node = null;
        while (!s1.empty() || !s2.empty()) {
            int x = s1.size() != 0 ? s1.pop() : 0;
            int y = s2.size() != 0 ? s2.pop() : 0;
            int val = x+y;
            if (val >= 10) {
                cFwd = val % 10;
                val -= cFwd;
            }
            node = new Node(val);
            node.next = prev;
            prev = node;
        }
        return node;
    }
}
