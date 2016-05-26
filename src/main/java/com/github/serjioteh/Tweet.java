package com.github.serjioteh;

import twitter4j.Status;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

public class Tweet implements Comparable<Tweet>, Serializable {
    public Tweet(Status status) {
        id            = status.getId();
        userID        = status.getUser().getId();
        text          = status.getText();
        timestamp     = status.getCreatedAt();
        favoriteCount = status.getFavoriteCount();
        retweetCount  = status.getRetweetCount();
        lang          = status.getLang();
    }

    public Tweet(long id, long userID, String text, Date timestamp, int favoriteCount, int retweetCount, String lang) {
        this.id = id;
        this.userID = id;
        this.text = text;
        this.timestamp = timestamp;
        this.favoriteCount = favoriteCount;
        this.retweetCount = retweetCount;
        this.lang = lang;
    }

    @Override
    public boolean equals(Object tweet) {
        if (!(tweet instanceof Tweet))
            return false;
        if (tweet == this)
            return true;
        Tweet t2 = (Tweet) tweet;
        return tweet instanceof Tweet && ((Tweet) tweet).getId() == this.id;
    }

    @Override
    public int hashCode() {
        return (int) this.id;
    }

    @Override
    public String toString() {
        return  "ID=" + this.id +
                ", CreatedAt=" + this.timestamp +
                ", lang='" + this.lang + '\'' +
                ", favoriteCount=" + this.favoriteCount +
                ", retweetCount=" + this.retweetCount +
                ", text='" + this.text + '\'';
    }

    private final long id;
    private final long userID;
    private final String text;
    private final Date timestamp;
    private final int favoriteCount;
    private final int retweetCount;
    private final String lang;

    public Date getTimestamp() {
        return this.timestamp;
    }

    public int getRetweetCount() {
        return this.retweetCount;
    }

    public int getFavoriteCount() {
        return this.favoriteCount;
    }

    public long getId() {
        return this.id;
    }

    public String getLang() { return this.lang; }

    @Override
    public int compareTo(Tweet tweet) throws ClassCastException{
        if (!(tweet instanceof Tweet))
            throw new ClassCastException();
        if (id == tweet.id)
            return 0;
        if (id < tweet.id)
            return -1;
        return 1;
    }

    public String getText() { return this.text; }

    static class TimedStampComparator implements Comparator<Tweet> {
        public int compare(Tweet t1, Tweet t2) {
            return t1.getTimestamp().compareTo(t2.getTimestamp());
        }
    }

    static class FavCountComparator implements Comparator<Tweet> {
        public int compare(Tweet t1, Tweet t2) {
            return t1.getFavoriteCount() - t2.getFavoriteCount();
        }
    }

    static class RetweetCountComparator implements Comparator<Tweet> {
        public int compare(Tweet t1, Tweet t2) {
            return t1.getRetweetCount() - t2.getRetweetCount();
        }
    }
}
