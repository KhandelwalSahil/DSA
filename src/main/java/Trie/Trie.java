package Trie;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

class Node {
    Node links[] = new Node[26];
    boolean fl = false;

    public Node() {
    }

    boolean containsKey(char ch) {
        return links[ch-'a'] != null;
    }

    Node get(char ch) {
        return links[ch-'a'];
    }

    void put(char ch, Node node) {
        links[ch-'a'] = node;
    }

    void setEnd() {
        fl = true;
    }

    boolean isEnd() {
        return fl;
    }
}

public class Trie {

    private static Node root;

    Trie() {
        root = new Node();
    }

    public static void insert(String word) {
        Node node = root;
        for (int i = 0; i < word.length(); i++) {
            if (!node.containsKey(word.charAt(i))) {
                node.put(word.charAt(i), new Node());
            }
            node = node.get(word.charAt(i));
        }
        node.setEnd();
    }

    public static boolean search(String word) {
        Node node = root;
        for (int i = 0; i < word.length(); i++) {
            if (!node.containsKey(word.charAt(i))) {
                return false;
            }
            node = node.get(word.charAt(i));
        }
        return node.isEnd();
    }

    public static boolean startsWith(String word) {
        Node node = root;
        for (int i = 0; i < word.length(); i++) {
            if (!node.containsKey(word.charAt(i))) {
                return false;
            }
            node = node.get(word.charAt(i));
        }
        return true;
    }

    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.insert("apple");
        trie.insert("apps");
        trie.insert("bat");
        trie.insert("bac");
        System.out.println(trie.search("apps"));
        System.out.println(trie.search("appss"));
        System.out.println(trie.startsWith("ap"));
        System.out.println(trie.startsWith("apx"));
    }
}

class CountDistinctSubstring
{
    public static void main(String[] args) {
        minimumTimeSpent(Arrays.asList(1,2,3), Arrays.asList(1,1,1), Arrays.asList(1,2,3), Arrays.asList(10,5,1));
    }

    public static int countDistinctSubstring(String st)
    {
        int n = st.length();
        int cnt = 0;
        Node root = new Node();
        for (int i = 0; i < st.length(); i++) {
            Node node = root;
            for (int j = i; j < st.length(); j++) {
                if (!node.containsKey(st.charAt(j))) {
                    node.put(st.charAt(j), new Node());
                    cnt++;
                }
                node = node.get(st.charAt(j));
            }
        }
        return cnt+1;
    }

    public static int countNumWays(String s, int k) {
        // Write your code here
        int start = 0, end = k, ans = 0;
        int n = s.length();
        if (k > s.length()) return 0;

        String sub = s.substring(0, k);
        while (end <= n) {
            System.out.println(sub);
            if (isLexiSmaller(sub)) {
                ans++;
                System.out.println(sub + " " + ans);
            }
            start++;
            end++;
            if (end <= n) sub = s.substring(start, end);
        }
        return ans;
    }

    private static boolean isLexiSmaller(String sub) {
        String rev = "";
        for (int i = sub.length()-1; i >= 0; i--) {
            rev += sub.charAt(i);
        }
        return sub.compareTo(rev) > 1;
    }

    public static int minimumTimeSpent(List<Integer> comedyReleaseTime, List<Integer> comedyDuration, List<Integer> dramaReleaseTime, List<Integer> dramaDuration) {
        // Write your code here
        List<int[]> comedy = new ArrayList<>();
        List<int[]> drama = new ArrayList<>();

        for (int i = 0; i < comedyReleaseTime.size(); i++) {
            comedy.add(new int[]{comedyReleaseTime.get(i)+comedyDuration.get(i), i, comedyDuration.get(i)});
        }

        for (int i = 0; i < dramaReleaseTime.size(); i++) {
            drama.add(new int[]{dramaReleaseTime.get(i)+dramaDuration.get(i), i, dramaDuration.get(i)});
        }

        comedy.sort(new Comparator<int[]>() {
            @Override
            public int compare(int[] t1, int[] t2) {
                if (t1[0] == t2[0]) return t1[2]-t2[2];
                return t1[0]-t2[0];
            }
        });
        drama.sort(new Comparator<int[]>() {
            @Override
            public int compare(int[] t1, int[] t2) {
                if (t1[0] == t2[0]) return t1[2]-t2[2];
                return t1[0]-t2[0];
            }
        });

        int firstMovieIndex = comedy.get(0)[1];
        int firstMovieEnds = comedyReleaseTime.get(firstMovieIndex) + comedyDuration.get(firstMovieIndex);
        int secondMovieIndex = drama.get(0)[1];
        int secondMovieStarts = dramaReleaseTime.get(secondMovieIndex);
        int secondMovieDuration = dramaDuration.get(secondMovieIndex);
        int ans = 0;
        if (secondMovieStarts <= firstMovieEnds) {
            ans = firstMovieEnds+secondMovieDuration;
        }
        else {
            ans = secondMovieStarts + secondMovieDuration;
        }

        int firstMovieIndex1 = drama.get(0)[1];
        int firstMovieEnds1 = dramaReleaseTime.get(firstMovieIndex1) + dramaDuration.get(firstMovieIndex1);
        int secondMovieIndex1 = comedy.get(0)[1];
        int secondMovieStarts1 = comedyReleaseTime.get(secondMovieIndex1);
        int secondMovieDuration1 = comedyDuration.get(secondMovieIndex1);
        int ans1 = 0;
        if (secondMovieStarts1 <= firstMovieEnds1) {
            ans1 = firstMovieEnds1+secondMovieDuration1;
        }
        else {
            ans1 = secondMovieStarts1 + secondMovieDuration1;
        }


        return Math.min(ans, ans1);
    }
}
