package StackQueues;

import java.util.HashMap;
import java.util.Map;

class LRUCache {

    static class Node {
        Node prev,next;
        int key,value;

        Node(int k, int v) {
            key = k;
            value = v;
        }
    }

    Node head = new Node(0,0), tail = new Node(0, 0);
    int capacity;
    Map<Integer, Node> map = new HashMap();

    public LRUCache(int cap) {
        capacity = cap;
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            remove(node);
            insert(node);
            return node.value;
        }
        return -1;
    }

    public void put(int key, int value) {
        if (map.containsKey(key)) {
            remove(map.get(key));
        }
        if (map.size() == capacity) {
            remove(tail.prev);
        }
        insert(new Node(key, value));
    }

    public void remove (Node node) {
        map.remove(node.key);
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    public void insert (Node node) {
        node.next = head.next;
        head.next = node;
        node.next.prev = node;
        node.prev = head;
        map.put(node.key, node);
    }

    public static void main(String[] args) {
        LRUCache lRUCache = new LRUCache(2);
        lRUCache.put(1, 1); // cache is {1=1}
        lRUCache.put(2, 2); // cache is {1=1, 2=2}
        lRUCache.get(1);    // return 1
        lRUCache.put(3, 3); // LRU key was 2, evicts key 2, cache is {1=1, 3=3}
        lRUCache.get(2);    // returns -1 (not found)
        lRUCache.put(4, 4); // LRU key was 1, evicts key 1, cache is {4=4, 3=3}
        lRUCache.get(1);    // return -1 (not found)
        lRUCache.get(3);    // return 3
        lRUCache.get(4);    // return 4
    }
}

class Solution{
    static int pageFaults(int N, int C, int pages[]){
       LRUCache lruCache = new LRUCache(C);
       int pageFault = 0;
       for (int i = 0; i < N; i++) {
           if (lruCache.get(pages[i]) == -1) {
               pageFault++;
               lruCache.put(pages[i], 1001);
           }
       }
       return pageFault;
    }

    public static void main(String[] args) {
        pageFaults(9, 4, new int[]{5,0,1,3,2,4,1,0,5});
    }
}