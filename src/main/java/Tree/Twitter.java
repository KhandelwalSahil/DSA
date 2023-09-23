package Tree;

import javafx.util.Pair;

import java.sql.Time;
import java.util.*;

public class Twitter {

    Map<Integer, List<Integer>> follow;
    Map<Integer, PriorityQueue<Pair<Long, Integer>>> tweets;
    List<Integer> users;
    public Twitter() {
        follow = new HashMap<>();
        tweets = new HashMap<>();
        users = new ArrayList<>();
    }

    public void postTweet(int userId, int tweetId) {
        if (!users.contains(userId)) users.add(userId);
        if (tweets.get(userId) == null) {
            tweets.put(userId, new PriorityQueue<>());
        }
        tweets.get(userId).add(new Pair<>(new Date().getTime(), tweetId));
    }

    public List<Integer> getNewsFeed(int userId) {
        List<Integer> tweet = new ArrayList<>();
        List<Pair<Long, Integer>> tweetPair = new ArrayList<>();
        PriorityQueue<Pair<Long, Integer>> tweetPairs = tweets.get(userId);
        for (int i = 0; i < Math.min(tweetPairs.size(),10); i++) {
            tweet.add(tweetPairs.peek().getValue());
            tweetPair.add(tweetPairs.peek());
            tweetPairs.remove();
        }
        tweets.get(userId).addAll(tweetPair);
        return tweet;
    }

    public void follow(int followerId, int followeeId) {
        if (follow.get(followerId) == null) follow.put(followerId, new ArrayList<>());
        follow.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if (follow.get(followerId) != null) {
            follow.get(followerId).remove(followeeId);
        }
    }
}
