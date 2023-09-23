package String;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class SolutionMinAddToMakeValid {
    public int minAddToMakeValid(String s) {
        int open = 0;
        int close = 0;
        int ans = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') open++;
            if (s.charAt(i) == ')') close++;
            if (close > open) {
                ans += close-open;
                open = close;
            }
        }
        ans += Math.abs(open-close);
        return ans;
    }
}

class Solution {
    public String countAndSay(int n) {
        if (n == 1) return "1";
        List<Integer> list = new ArrayList<>();
        list.add(1);
        for (int i = 2; i <= n; i++) {
            List<Integer> temp = new ArrayList<>();
            for (int j = 0; j < list.size(); j=j) {
                int num = list.get(j);
                int count = 0;
                while (j < list.size() && list.get(j) == num) {
                    count++;
                    j++;
                }
                temp.add(count);
                temp.add(num);
            }
            list = temp;
        }
        String str = "";
        for (int i : list) {
            str += String.valueOf(i);
        }
        return str;
    }
}

class SolutionRabinKarp
{

    static int power(int x, int y, int p)
    {
        int res = 1; // Initialize result

        x = x % p; // Update x if it is more than or
        // equal to p

        if (x == 0)
            return 0; // In case x is divisible by p;

        while (y > 0)
        {

            // If y is odd, multiply x with result
            if ((y & 1) != 0)
                res = (res * x) % p;

            // y must be even now
            y = y >> 1; // y = y/2
            x = (x * x) % p;
        }
        return res;
    }

    static int hashString(String str) {
        int mod = (int)1e9 + 7;
        int n = str.length();
        int hash = 0;
        for (int i = 0; i < n; i++) {
            hash = (hash+(str.charAt(i)-'a'+1)*(power(26, n-i-1, mod)))%mod;
        }
        return hash;
    }

    static int hashStringShiftWindow(int curHash, char prevChar, char nextChar, int window) {
        int mod = (int)1e9 + 7;
        int hash = curHash-((prevChar-'a'+1)*power(26, window-1, mod));
        long hashLong = hash*26;
        hashLong = hashLong%1000000007;
        if (curHash == 200794872) System.out.println(hashLong);
        hash = (hash*26)%mod;
        hash = (hash+(nextChar-'a'+1))%mod;
        return hash;
    }

    static ArrayList<Integer> search(String pat, String S)
    {
        int patHash = hashString(pat);
        int windowHash = hashString(S.substring(0, pat.length()));
        int n = S.length();
        int m = pat.length();
        ArrayList<Integer> ans = new ArrayList<>();
        for (int i = m-1; i <= n-m; i++) {
            if (patHash == windowHash) {
                ans.add(i-m+1);
            }
            if (i == n-m) continue;
            windowHash = hashStringShiftWindow(windowHash, S.charAt(i-m+1), S.charAt(i+1), m);
        }
        if (ans.size() == 0) ans.add(-1);
        return ans;
    }
}

class SolutionZAlgorithm
{

    ArrayList<Integer> search(String pat, String S)
    {
        char[] conStr = new char[pat.length()+1+S.length()];
        int index = 0;
        for (int i = 0; i < pat.length(); i++) {
            conStr[index] = pat.charAt(i);
            index++;
        }
        conStr[index] = '$';
        index++;
        for (int i = 0; i < S.length(); i++) {
            conStr[index] = S.charAt(i);
            index++;
        }
        int[] z = new int[conStr.length];
        z = zFunc(conStr);
        ArrayList<Integer> ans = new ArrayList<>();
        for (int i = 0; i < z.length; i++) {
            if (z[i] == pat.length()) {
                ans.add(i-pat.length()-1);
            }
        }
        return ans;
    }

    private int[] zFunc(char[] conStr) {
        int n = conStr.length;
        int[] z = new int[n];
        int left = 0, right = 0;
        for (int k = 1; k < n; k++) {
            if (k > right) {
                left = k;
                right = k;
                while (right < n && conStr[right] == conStr[right-left]) {
                    right++;
                }
                z[k] = right-left;
                right--;
            }
            else {
                int k1 = k-left;
                if (z[k1] + k <= right) {
                    z[k] = z[k1];
                }
                else {
                    left = k;
                    right = k;
                    while (right < n && conStr[right] == conStr[right-left]) {
                        right++;
                    }
                    z[k] = right-left;
                    right--;
                }
            }
        }
        return z;
    }
}

class SolutionKMP
{
    private int[] computeTemporaryArray(char pattern[]){
        int [] lps = new int[pattern.length];
        int index =0;
        for(int i=1; i < pattern.length;){
            if(pattern[i] == pattern[index]){
                lps[i] = index + 1;
                index++;
                i++;
            }else{
                if(index != 0){
                    index = lps[index-1];
                }else{
                    lps[i] =0;
                    i++;
                }
            }
        }
        return lps;
    }

    ArrayList<Integer> search(String pattern, String text)
    {
        int lps[] = computeTemporaryArray(pattern.toCharArray());
        ArrayList<Integer> ans = new ArrayList<>();
        int i=0;
        int j=0;
        while(i < text.length() && j < pattern.length()){
            if(text.charAt(i) == pattern.charAt(j)){
                i++;
                j++;
                if(j == pattern.length()){
                    ans.add(i-j+1);
                    j = 0;
                    i = i-pattern.length()+1;
                }
            }else{
                if(j!=0){
                    j = lps[j-1];
                }else{
                    i++;
                }
            }
        }
        if(ans.size() == 0){
            ans.add(-1);
        }
        return ans;
    }
}

class SolutionShortestPalindrome {

    private int[] computeTemporaryArray(char pattern[]){
        int [] lps = new int[pattern.length];
        int index =0;
        for(int i=1; i < pattern.length;){
            if(pattern[i] == pattern[index]){
                lps[i] = index + 1;
                index++;
                i++;
            }else{
                if(index != 0){
                    index = lps[index-1];
                }else{
                    lps[i] =0;
                    i++;
                }
            }
        }
        return lps;
    }

    public String shortestPalindrome(String s) {
        int n = s.length();
        int[] lps = new int[2*n+1];
        char[] str = new char[2*n+1];
        str[n] = '#';
        for (int i = 0; i < n; i++) {
            str[i] = s.charAt(i);
            str[n+i+1] = s.charAt(n-i-1);
        }
        lps = computeTemporaryArray(str);
        int x = lps[2*n];
        String remStr = "";
        for (int i = n-1; i >= x; i--) {
            remStr += s.charAt(i);
        }
        return remStr+s;
    }
}

class SolutionLongestHappyPrefix {

    private int[] zFunc(char[] conStr) {
        int n = conStr.length;
        int[] z = new int[n];
        int left = 0, right = 0;
        for (int k = 1; k < n; k++) {
            if (k > right) {
                left = k;
                right = k;
                while (right < n && conStr[right] == conStr[right-left]) {
                    right++;
                }
                z[k] = right-left;
                right--;
            }
            else {
                int k1 = k-left;
                if (z[k1] + k <= right) {
                    z[k] = z[k1];
                }
                else {
                    left = k;
                    right = k;
                    while (right < n && conStr[right] == conStr[right-left]) {
                        right++;
                    }
                    z[k] = right-left;
                    right--;
                }
            }
        }
        return z;
    }

    public String longestPrefix(String s) {
        int[] noOfChar = zFunc(s.toCharArray());

        int ans = 0;
        for (int i = 0; i < s.length(); i++) {
            if (i+noOfChar[i] == s.length() && ans <= noOfChar[i]) {
                ans = noOfChar[i];
            }
        }
        return s.substring(0, ans);
    }
}