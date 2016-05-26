package com.github.serjioteh;

import twitter4j.*;
import twitter4j.api.HelpResources;

import java.util.*;
import java.text.SimpleDateFormat;


public class Accessor {

    private static final Twitter twitter = new TwitterFactory().getInstance();
    private static final int MAX_TWEETS_PER_PAGE = 100;

    /**
     * @param query - строка поиска, передаваемая в Twitter4j
     * @param since - дата, с которой начинается поиск (достаточно указать день, месяц и год)
     * @param querySize - количество твитов в результирующей выборке
     */
    public static TweetsContainer<Tweet> search(String query, Date since, int querySize) throws
            InterruptedException, TwitterException {
        // tweets left to retrieve
        int tweets_cnt = querySize;
        try {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            String sinceString = dateFormatter.format(since);
            Query twitter4jQuery = new Query(query);
            twitter4jQuery.setSince(sinceString);
            twitter4jQuery.setCount(MAX_TWEETS_PER_PAGE);
            QueryResult result;
            TweetsContainer<Tweet> container = new LinkedListTweetsContainer<Tweet>();
            System.out.println("Query:" + twitter4jQuery);
            do {
                System.out.println("Tweeters left to proceed: " + tweets_cnt);
                checkTwitterLimits();
                result = twitter.search(twitter4jQuery);
                List<Status> tweets = result.getTweets();
                System.out.println("query_result_size:" + tweets.size());
                for (Status tweet : tweets) {
//                    System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getLang());
                    container.add(new Tweet(tweet));
                    tweets_cnt -= 1;
                    if ( tweets_cnt == 0 )
                        break;
                }
                twitter4jQuery = result.nextQuery();
            } while (result.hasNext() && tweets_cnt > 0);
            return container;
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
            return null;
        }
    }

    private static void checkTwitterLimits() throws TwitterException, InterruptedException {
        RateLimitStatus searchTweetsRateLimit = twitter.getRateLimitStatus("search").get("/search/tweets");
        int callsLeft = searchTweetsRateLimit.getRemaining();
        int secondsToSleep = searchTweetsRateLimit.getSecondsUntilReset();
        System.out.format("You have %d calls remaining out of %d, Limit resets in %d seconds\n",
                callsLeft,
                searchTweetsRateLimit.getLimit(),
                secondsToSleep);
        if (callsLeft == 0) {
            System.out.format("Sleeping %d seconds due to rate limis\n" , secondsToSleep);
            Thread.sleep((secondsToSleep + 5) * 1000);
        }
    }

    public static Map<String, String> getSupportedLanguages() throws TwitterException, InterruptedException {
        checkTwitterLimits();
        Map<String, String> mapLangs = new HashMap<>();
        ResponseList<HelpResources.Language> langs = twitter.getLanguages();
        for (HelpResources.Language lang : langs) {
            mapLangs.put(lang.getCode(), lang.getName());
        }
        return mapLangs;
    }

}
