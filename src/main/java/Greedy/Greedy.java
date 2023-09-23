package Greedy;

import java.util.*;

public class Greedy {

    class SolutionCookieChildren {
        public int findContentChildren(int[] g, int[] s) {
            // at most one cookie
            Arrays.sort(g);
            Arrays.sort(s);
            int child = 0;
            for (int cookie = 0; cookie < s.length && child < g.length; cookie++) {
                if (s[cookie] >= g[child]) {
                    child++;
                }
            }
            return child;
        }
    }
}

    class Item {
        int value, weight;

        Item(int x, int y) {
            this.value = x;
            this.weight = y;
        }
    }

    class SolutionFractionalKnapsack {
        //Function to get the maximum total value in the knapsack.
        double fractionalKnapsack(int W, Item arr[], int n) {
            Arrays.sort(arr, new Comparator<Item>() {
                @Override
                public int compare(Item o1, Item o2) {
                    double r1 = (double) o1.value / (double) o1.weight;
                    double r2 = (double) o2.value / (double) o2.weight;
                    if (r1 < r2) return 1;
                    else if (r1 > r2) return -1;
                    return 0;
                }
            });
            for (int i = 0; i < n; i++) System.out.println(arr[i].value / arr[i].weight);
            int curWeight = 0;
            double value = 0;
            for (int i = 0; i < n; i++) {
                if (curWeight + arr[i].weight <= W) {
                    curWeight += arr[i].weight;
                    value += arr[i].value;
                } else {
                    int remWeight = W - curWeight;
                    curWeight = W;
                    value += (arr[i].value / (double) arr[i].weight) * (double) remWeight;
                }
            }
            return value;
        }
    }

    class SolutionCheckValidString {
        public boolean checkValidString(String s) {
            int cmax = 0;
            int cmin = 0;
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '(') {
                    cmax++;
                    cmin++;
                } else if (s.charAt(i) == ')') {
                    cmax--;
                    cmin--;
                } else {
                    cmax++;
                    cmin--;
                }
                if (cmax < 0) return false;
            }
            cmin = Math.max(cmin, 0);
            return cmin == 0;
        }
    }

    class SolutionMeetingSlots
    {
        //Function to find the maximum number of meetings that can
        //be performed in a meeting room.

        static class Meeting {
            int startT;
            int endT;

            public Meeting(int startT, int endT) {
                this.startT = startT;
                this.endT = endT;
            }
        }
        public static int maxMeetings(int start[], int end[], int n)
        {
            List<Meeting> meetings = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                meetings.add(new Meeting(start[i], end[i]));
            }
            Collections.sort(meetings, (a, b) -> a.endT-b.endT);
            int meetingCount = 1;
            int prevMeeting = meetings.get(0).endT;
            for (int i = 1; i < n; i++) {
                if (meetings.get(i).startT > prevMeeting) {
                    prevMeeting = meetings.get(i).endT;
                    meetingCount++;
                }
            }
            return meetingCount;
        }
    }

    class SolutionCanJump {
        public boolean canJump(int[] nums) {
            if (nums.length == 1) return true;
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] == 0) {
                    int j = i-1;
                    int jumpReq = 2;
                    boolean jumpFound = false;
                    while (j >= 0) {
                        if (nums[j] >= jumpReq) {
                            jumpFound = true;
                            break;
                        }
                        j--;
                        jumpReq++;
                    }
                    if (!jumpFound) return false;
                }
            }
            return true;
        }
    }

    class SolutionJump {
        public int jump(int[] nums) {
            int maxR = 0, lastJ = 0, i = 0, n = nums.length, jump = 0;
            while (i < n-1) {
                maxR = Math.max(maxR, i + nums[i]);
                if (i == lastJ) {
                    lastJ = maxR;
                    jump++;
                }
                i++;
            }
            return jump;
        }
    }

    class SolutionFindPlatform
    {
        static int findPlatform(int arr[], int dep[], int n)
        {
            Arrays.sort(arr);
            Arrays.sort(dep);
            int i = 1, j = 0, platform = 1, ans = 1;
            while (i < n && j < n) {
                if (arr[i] <= dep[j]) {
                    platform++;
                    i++;
                }
                else {
                    platform--;
                    j++;
                }
                if (platform > ans) {
                    ans = platform;
                }
            }
            return ans;
        }
    }


class SolutionJobScheduling
{
    class Job {
        int id, profit, deadline;
        Job(int x, int y, int z){
            this.id = x;
            this.deadline = y;
            this.profit = z;
        }
    }
    //Function to find the maximum profit and the number of jobs done.
    int[] JobScheduling(Job arr[], int n)
    {
        Arrays.sort(arr, (a, b) -> b.profit-a.profit);
        int maxDeadline = 0;
        for (int i = 0; i < n; i++) {
            maxDeadline = Math.max(maxDeadline, arr[i].deadline);
        }
        int[] days = new int[maxDeadline+1];
        for (int i = 0; i <= maxDeadline; i++) {
            days[i] = -1;
        }

        int countJobs = 0, profits = 0;
        for (int i = 0; i < n; i++) {
            for (int j = arr[i].deadline; j > 0; j--) {
                if (days[j] == -1) {
                    days[j] = i;
                    countJobs++;
                    profits += arr[i].profit;
                    break;
                }
            }
        }
        return new int[]{countJobs, profits};
    }
}
class SolutionCandy {

    class Candy {
        int rating;
        int index;

        public Candy(int rating, int index) {
            this.rating = rating;
            this.index = index;
        }
    }
    public int candy(int[] ratings) {
        int n = ratings.length;
        List<Candy> candies = new ArrayList<>();
        for (int i = 0; i < ratings.length; i++) {
            candies.add(new Candy(ratings[i], i));
        }
        Collections.sort(candies, (a,b) -> a.rating-b.rating);
        int[] candyCount = new int[n];
        int totalCandies = 0;
        for (Candy candy : candies) {
            int index = candy.index;
            int candyReq = 1;
            if (index > 0 && ratings[index-1] < ratings[index]) {
                candyReq = Math.max(candyReq, candyCount[index-1]+1);
            }
            if (index < n-1 && ratings[index+1] < ratings[index]) {
                candyReq = Math.max(candyReq, candyCount[index+1]+1);
            }
            candyCount[index] = candyReq;
            totalCandies += candyReq;
        }
        return totalCandies;
    }
}

class SolutionSolve {
    static int solve(int bt[]) {
        int n = bt.length;
        Arrays.sort(bt);
        int totalWeight = 0;
        for (int i = 1; i < n; i++) {
            totalWeight += bt[i-1];
        }
        return Math.floorDiv(totalWeight, n);
    }
}

class Solution {
    public int[][] insert(int[][] intervals, int[] newInterval) {
        int n = intervals.length;
        int l = 0;
        int r = n - 1;
        while (l < n && newInterval[0] > intervals[l][1]) {
            l++;
        }
        while (r >= 0 && newInterval[1] < intervals[r][0]) {
            r--;
        }
        int[][] res = new int[l + n - r][2];
        for (int i = 0; i < l; i++) {
            res[i] = Arrays.copyOf(intervals[i], intervals[i].length);
        }
        res[l][0] = Math.min(newInterval[0], l == n ? newInterval[0] : intervals[l][0]);
        res[l][1] = Math.max(newInterval[1], r == -1 ? newInterval[1] : intervals[r][1]);
        for (int i = l + 1, j = r + 1; j < n; i++, j++) {
            res[i] = intervals[j];
        }
        return res;
    }
}

