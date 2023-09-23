package Trie;

import java.util.*;

public class TrieXOR {
}

class Nodee {
    Nodee links[] = new Nodee[2];

    public Nodee() {
    }

    boolean containsKey(int bit) {
        return links[bit] != null;
    }

    Nodee get(int bit) {
        return links[bit];
    }

    void put(int bit, Nodee node) {
        links[bit] = node;
    }
}

class Triee {
    private static Nodee root;

    Triee() {
        root = new Nodee();
    }

    public static void insert(int num) {
        Nodee node = root;
        for (int i = 31; i >= 0; i--) {
            int bit = (num>>i)&1;
            if (!node.containsKey(bit)) {
                node.put(bit, new Nodee());
            }
            node = node.get(bit);
        }
    }

    public static int getMax(int num) {
        Nodee node = root;
        int maxNum = 0;
        for (int i = 31; i >= 0; i--) {
            int bit = (num>>i)&1;
            if (node.containsKey(1-bit)) {
                maxNum = maxNum | (1<<i);
                node = node.get(1-bit);
            }
            else {
                node = node.get(bit);
            }
        }
        return maxNum;
    }
}

class SolutionFindMaxXOR {
    public int findMaximumXOR(int[] nums) {
        Triee trie = new Triee();
        for (int i : nums) {
            trie.insert(i);
        }
        int maxAns = 0;
        for (int i : nums) {
            maxAns = Math.max(maxAns, trie.getMax(i));
        }
        return maxAns;
    }
}

class SolutionMaximizeXor {
    public int[] maximizeXor(int[] num, int[][] queries) {
        int n = num.length;
        List<Integer> nums = new ArrayList<>();
        for (int i : num) nums.add(i);
        Collections.sort(nums);
        List<List<Integer>> offlineQueries = new ArrayList<>();
        int m = queries.length;
        for (int i = 0; i < m; i++) {
            List<Integer> temp = new ArrayList<>();
            temp.add(queries[i][1]);
            temp.add(queries[i][0]);
            temp.add(i);
            offlineQueries.add(temp);
        }
        Collections.sort(offlineQueries, new Comparator<List<Integer>>() {
            @Override
            public int compare(List<Integer> o1, List<Integer> o2) {
                return o1.get(0) - o2.get(0);
            }
        });
        int ind = 0;
        int[] ans = new int[m];
        Triee trie = new Triee();
        for (int i = 0; i < m; i++) {
            while (ind < n && nums.get(ind) <= offlineQueries.get(i).get(0)) {
                trie.insert(nums.get(ind));
                ind++;
            }
            int queryInd = offlineQueries.get(i).get(2);
            if (ind != 0) ans[queryInd] = trie.getMax(offlineQueries.get(i).get(1));
            else ans[queryInd] = -1;
        }
        return ans;
    }
}
