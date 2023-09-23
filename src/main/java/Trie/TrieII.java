package Trie;


import java.util.ArrayList;
import java.util.List;

class NodE {
    NodE links[] = new NodE[26];
    int cntEndWith = 0;
    int cntPrefix = 0;

    public NodE() {
    }

    boolean containsKey(char ch) {
        return links[ch-'a'] != null;
    }

    NodE get(char ch) {
        return links[ch-'a'];
    }

    void put(char ch, NodE node) {
        links[ch-'a'] = node;
    }

    void increaseEnd() {
        cntEndWith++;
    }

    void deleteEnd() {
        cntEndWith--;
    }

    void increasePrefix() {
        cntPrefix++;
    }

    void reducePrefix() {
        cntPrefix--;
    }
}

public class TrieII {

    private static NodE root;

    TrieII() {
        root = new NodE();
    }

    public NodE getRoot() {
        return root;
    }

    public static void insert(String word) {
        NodE node = root;
        for (int i = 0; i < word.length(); i++) {
            if (!node.containsKey(word.charAt(i))) {
                node.put(word.charAt(i), new NodE());
            }
            node = node.get(word.charAt(i));
            node.increasePrefix();
        }
        node.increaseEnd();
    }

    public static int countWordsEqualTo(String word) {
        NodE node = root;
        for (int i = 0; i < word.length(); i++) {
            if (node.containsKey(word.charAt(i))) {
                node = node.get(word.charAt(i));
            }
            else {
                return 0;
            }
        }
        return node.cntEndWith;
    }

    public static int countWordsStartsWith(String word) {
        NodE node = root;
        for (int i = 0; i < word.length(); i++) {
            if (node.containsKey(word.charAt(i))) {
                node = node.get(word.charAt(i));
            }
            else {
                return 0;
            }
        }
        return node.cntPrefix;
    }

    public static void erase(String word) {
        NodE node = root;
        for (int i = 0; i < word.length(); i++) {
            if (node.containsKey(word.charAt(i))) {
                node = node.get(word.charAt(i));
                node.reducePrefix();
            }
            else {
                return;
            }
        }
        node.deleteEnd();
    }

    public static void main(String[] args) {
        TrieII trie = new TrieII();
        trie.insert("apple");
        trie.insert("apple");
        trie.insert("apps");
        trie.insert("apps");

        System.out.println(trie.countWordsEqualTo("apple"));

        trie.erase("apple");

        System.out.println(trie.countWordsEqualTo("apple"));
    }
}

class Solution{

    // ??
    String longestCommonPrefix(String arr[], int n){
        TrieII trie = new TrieII();
        for (int i = 0; i < arr.length-1; i++) {
            trie.insert(arr[i]);
        }
        NodE node = trie.getRoot();
        String word = arr[n-1];
        List<Character> ans = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            if (!node.containsKey(word.charAt(i))) {
                node.put(word.charAt(i), new NodE());
            } else {
                ans.add(word.charAt(i));
            }
            node = node.get(word.charAt(i));
            node.increasePrefix();
        }
        node.increaseEnd();
        String s = "";
        for (char c: ans) {
            s += c;
        }
        return s;
    }
}
