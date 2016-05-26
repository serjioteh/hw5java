package com.github.serjioteh;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RefineryUtilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    public static void main(String[] args) throws ParseException {
//        Calendar calendar = Calendar.getInstance();
//        calendar.clear();
//        calendar.set(2016, Calendar.MAY, 1, 0, 0);
//        Date date = calendar.getTime();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
            String dateInString = "01-05-2016 00:00:00";
            Date date = sdf.parse(dateInString);

            TweetsContainer<Tweet> tweetsRealMadrid = Accessor.search("Real Madrid", date, 5000);
            TweetsContainer<Tweet> tweetsAtleticoMadrid = Accessor.search("Atletico Madrid", date, 5000);
//            TweetsContainer<Tweet> tweetsAtleticoMadrid = Accessor.search("Atlético de Madrid", date, 5000);

            HashSet<Tweet> tweets_set_RealMadrid = new HashSet<Tweet>(tweetsRealMadrid);
            HashSet<Tweet> tweets_set_AtleticoMadrid = new HashSet<Tweet>(tweetsAtleticoMadrid);
            Map<String, String> languages = Accessor.getSupportedLanguages();
            Map<String, Collection<Tweet>> groupedRealMadrid = tweetsRealMadrid.groupByLang();
            Map<String, Collection<Tweet>> groupedAtleticoMadrid = tweetsAtleticoMadrid.groupByLang();
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

            System.out.println("-------------------------------RealMadrid------------------------------------");
            for ( Map.Entry<String, Collection<Tweet>> entry : groupedRealMadrid.entrySet()) {
                System.out.println(entry.getKey() + ":" + entry.getValue().size());
                dataset.addValue(entry.getValue().size(), "RealMadrid", entry.getKey());
            }
            System.out.println("-----------------------------AtleticoMadrid----------------------------------");
            for ( Map.Entry<String, Collection<Tweet>> entry : groupedAtleticoMadrid.entrySet()) {
                System.out.println(entry.getKey() + ":" + entry.getValue().size());
                dataset.addValue(entry.getValue().size(), "AtleticoMadrid", entry.getKey());
            }

            System.out.println("------------------------[Tag cloud for RealMadrid:en]------------------------");
            Map<String, Long> tagCloud = tweetsRealMadrid.getTagCloud("en");
            for ( Map.Entry<String, Long> entry : tagCloud.entrySet() ) {
                System.out.println(entry.getKey() + ":" + entry.getValue());
            }

            System.out.println("TweetsContainer<Tweet> tweetsRealMadrid.size    : " + tweetsRealMadrid.size());
            System.out.println("Set<Tweet> tweets_set_RealMadrid.size           : " + tweetsRealMadrid.size());
            System.out.println("TweetsContainer<Tweet> tweetsAtleticoMadrid.size: " + tweetsAtleticoMadrid.size());
            System.out.println("Set<Tweet> tweets_set_tweetsAtleticoMadrid.size : " + tweetsAtleticoMadrid.size());

            BarChart_AWT chart = new BarChart_AWT("Java hw5 result chart", "RealMadrid & AtleticoMadrid tweets barchart by languages", dataset);
            chart.pack( );
            RefineryUtilities.centerFrameOnScreen( chart );
            chart.setVisible( true );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
