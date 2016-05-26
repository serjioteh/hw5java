package com.github.serjioteh;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by Админ on 26.05.2016.
 */
public class LinkedListTweetsContainerTest extends LinkedListTweetsContainer {

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
        tweetsContainer.clear();
    }

    @Test
    public void testAdd() throws Exception {
        Tweet t1 = new Tweet(1, 2, "some test from tweet!", new Date(), 1, 1, "en");
        Tweet t2 = new Tweet(2, 2, "some test from tweet!", new Date(), 1, 1, "en");
        tweetsContainer.add(t1);
        tweetsContainer.add(t2);
        assertTrue(tweetsContainer.contains(t1));
        assertTrue(tweetsContainer.contains(t2));
    }

    @Test
    public void testAddAll() throws Exception {
        Tweet t1 = new Tweet(1, 2, "some test from tweet!", new Date(), 1, 1, "en");
        Tweet t2 = new Tweet(2, 2, "some test from tweet!", new Date(), 1, 1, "en");
        tweetsContainer.add(t1);
        tweetsContainer.add(t2);
        Tweet t3 = new Tweet(3, 2, "some test from tweet!", new Date(), 1, 1, "en");
        Tweet t4 = new Tweet(4, 2, "some test from tweet!", new Date(), 1, 1, "en");
        List<Tweet> tweets = new ArrayList<>();
        tweets.add(t3);
        tweets.add(t4);
        tweetsContainer.addAll(tweets);
        assertTrue(tweetsContainer.contains(t1));
        assertTrue(tweetsContainer.contains(t2));
        assertTrue(tweetsContainer.contains(t3));
        assertTrue(tweetsContainer.contains(t4));
    }

    @Test
    public void testSize() throws Exception {
        Tweet t1 = new Tweet(1, 2, "some test from tweet!", new Date(), 1, 1, "en");
        Tweet t2 = new Tweet(2, 2, "some test from tweet!", new Date(), 1, 1, "en");
        Tweet t3 = new Tweet(3, 2, "some test from tweet!", new Date(), 1, 1, "en");
        Tweet t4 = new Tweet(4, 2, "some test from tweet!", new Date(), 1, 1, "en");
        tweetsContainer.add(t1);
        tweetsContainer.add(t2);
        tweetsContainer.add(t3);
        tweetsContainer.add(t4);
        assertEquals(tweetsContainer.size(), 4);
    }

    @Test
    public void testRemove() throws Exception {
        Tweet t1 = new Tweet(1, 2, "some test from tweet!", new Date(), 1, 1, "en");
        Tweet t2 = new Tweet(2, 2, "some test from tweet!", new Date(), 1, 1, "en");
        tweetsContainer.add(t1);
        tweetsContainer.add(t2);
        tweetsContainer.remove(t1);
        assertFalse(tweetsContainer.contains(t1));
    }

    @Test
    public void testClear() throws Exception {
        Tweet t1 = new Tweet(1, 2, "some test from tweet!", new Date(), 1, 1, "en");
        Tweet t2 = new Tweet(2, 2, "some test from tweet!", new Date(), 1, 1, "en");
        tweetsContainer.add(t1);
        tweetsContainer.add(t2);
        tweetsContainer.clear();
        assertEquals(tweetsContainer.size(), 0);
    }

    @Test
    public void testGetOldest() throws ParseException {
        Tweet t1 = new Tweet(1, 2, "some test from tweet!", new SimpleDateFormat("dd/MM/yyyy").parse("21/12/2015"), 1, 1, "en");
        Tweet t2 = new Tweet(2, 2, "some test from tweet!", new SimpleDateFormat("dd/MM/yyyy").parse("21/12/2012"), 1, 1, "en");
        Tweet t3 = new Tweet(3, 2, "some test from tweet!", new SimpleDateFormat("dd/MM/yyyy").parse("21/05/2012"), 1, 1, "en");
        tweetsContainer.add(t1);
        tweetsContainer.add(t2);
        tweetsContainer.add(t3);
        assertEquals(tweetsContainer.getOldest().getId(), 1);
    }

    @Test
    public void testTopRated() throws Exception {
        Tweet t1 = new Tweet(1, 2, "some test from tweet!", new Date(), 1, 1, "en");
        Tweet t2 = new Tweet(2, 2, "some test from tweet!", new Date(), 10, 1, "en");
        Tweet t3 = new Tweet(3, 2, "some test from tweet!", new Date(), 100, 1, "en");
        tweetsContainer.add(t1);
        tweetsContainer.add(t2);
        tweetsContainer.add(t3);
        assertEquals(tweetsContainer.getTopRated().getId(), 3);
    }

    @Test
    public void testSort() throws Exception {
        Tweet t1 = new Tweet(1, 2, "some test from tweet!", new Date(), 10, 1, "en");
        Tweet t2 = new Tweet(2, 2, "some test from tweet!", new Date(), 1, 1, "en");
        Tweet t3 = new Tweet(3, 2, "some test from tweet!", new Date(), 100, 1, "en");
        tweetsContainer.add(t1);
        tweetsContainer.add(t2);
        tweetsContainer.add(t3);
        Comparator<Tweet> comparator = new Tweet.FavCountComparator();
        tweetsContainer.sort(comparator);
        Tweet previousTweet = tweetsContainer.getFirst();
        for (Tweet tweet: tweetsContainer) {
            assertTrue(comparator.compare(previousTweet, tweet) <= 0);
        }
    }

    @Test
    public void testGroupByLang() throws Exception {
        Tweet t1 = new Tweet(1, 2, "some test from tweet!", new Date(), 1, 1, "fr");
        Tweet t2 = new Tweet(2, 2, "some test from tweet!", new Date(), 1, 1, "fr");
        Tweet t3 = new Tweet(3, 2, "some test from tweet!", new Date(), 1, 1, "en");
        tweetsContainer.add(t1);
        tweetsContainer.add(t2);
        tweetsContainer.add(t3);
        Map<String, Collection<Tweet>> grouped = tweetsContainer.groupByLang();
        assertEquals(grouped.get("fr").size(), 2);
        assertEquals(grouped.get("en").size(), 1);
    }

    @Test
    public void testGetFirst() throws Exception {
        Tweet t1 = new Tweet(1, 2, "some test from tweet!", new Date(), 1, 1, "fr");
        Tweet t2 = new Tweet(2, 2, "some test from tweet!", new Date(), 1, 1, "fr");
        Tweet t3 = new Tweet(3, 2, "some test from tweet!", new Date(), 1, 1, "en");
        tweetsContainer.add(t1);
        tweetsContainer.add(t2);
        tweetsContainer.add(t3);
        assertEquals(tweetsContainer.getFirst(), t1);
    }

    @Test
    public void getTagCloud() throws Exception {
        Tweet t1 = new Tweet(1, 2, "this is word", new Date(), 1, 1, "en");
        Tweet t2 = new Tweet(2, 2, "a little test, this", new Date(), 1, 1, "en");
        Tweet t3 = new Tweet(3, 2, "for", new Date(), 1, 1, "en");
        Tweet t4 = new Tweet(3, 2, "my program testing as test.test!", new Date(), 1, 1, "en");
        Tweet t5 = new Tweet(3, 2, "espanoil text to skip", new Date(), 1, 1, "es");
        Tweet t6 = new Tweet(3, 2, "french text to skip", new Date(), 1, 1, "fr");
        tweetsContainer.add(t1);
        tweetsContainer.add(t2);
        tweetsContainer.add(t3);
        tweetsContainer.add(t4);
        tweetsContainer.add(t5);
        tweetsContainer.add(t6);
        Map<String, Long>  grouped = tweetsContainer.getTagCloud("en");
        assertEquals(grouped.get("test").longValue(), 3);
        assertEquals(grouped.get("this").longValue(), 2);
        assertEquals(grouped.get("word").longValue(), 1);
        assertEquals(grouped.get("little").longValue(), 1);
        assertEquals(grouped.get("program").longValue(), 1);
    }


    private TweetsContainer<Tweet> tweetsContainer = new LinkedListTweetsContainer<>();
}