package com.github.serjioteh;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;

class LinkedListTweetsContainer<T extends Tweet> implements TweetsContainer {

    private final List<Tweet> tweets;

    public LinkedListTweetsContainer() { tweets = new LinkedList<>();    }

    @Override
    public boolean add(Tweet tweet) {
        return tweets.add(tweet);
    }

    @Override
    public boolean addAll(Collection collection) {
        return tweets.addAll(collection);
    }

    @Override
    public boolean remove(Tweet tweet) {
        return tweets.remove(tweet);
    }

    @Override
    public void clear() {
        tweets.clear();
    }

    @Override
    public Tweet getTopRated() {
        return Collections.max(tweets, new Tweet.FavCountComparator());
    }

    @Override
    public Tweet getOldest() {
        return Collections.max(tweets, new Tweet.TimedStampComparator());
    }

    @Override
    public void sort(Comparator comparator) {
        Collections.sort(tweets, comparator);
    }

    @Override
    public Map<String, List<Tweet>> groupByLang() {
        return tweets.stream().collect(Collectors.groupingBy(Tweet::getLang));
    }

    @Override
    public Map<String, Long> getTagCloud(String lang) {
        Map<String, Long> TagCloud = tweets.stream()
                .filter(t -> t.getLang().equals(lang))
                .map(t -> t.getText())
                .map(String::toLowerCase)
//                .map(s -> s.split("\\s+|,\\s*|\\.\\s*")).flatMap(Arrays::stream)
                .flatMap(Pattern.compile("\\s+|,\\s*|\\.\\s*|:\\s*|\\?\\s*|!\\s*")::splitAsStream)
                .filter(s -> s.length() > 3)
                .filter(s -> !s.contains("http"))
                .collect(groupingBy(Function.identity(), counting()));
        return TagCloud;
    }

    @Override
    public Iterator iterator() {
        return tweets.iterator();
    }

    @Override
    public void forEach(Consumer action) {
        tweets.forEach(tweet -> action.accept(tweet));
    }

    @Override
    public boolean retainAll(Collection c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection c) {
        return false;
    }

    @Override
    public int size() {
        return tweets.size();
    }

    @Override
    public boolean isEmpty() {
        return tweets.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return tweets.contains(o);
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public boolean add(Object o) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public Object[] toArray(Object[] a) {
        return new Object[0];
    }

    @Override
    public boolean containsAll(Collection c) {
        return tweets.containsAll(c);
    }

    @Override
    public Tweet getFirst() {
        return tweets.get(0);
    }

}
