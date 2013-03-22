package twitterSteamSpout;

/**
 * twiiterStramAnalysis twitterSteamSpout twitterRec.java
 *
 * Copyright 2013 Xdata@SIAT
 * Created:2013-3-22 下午8:08:38
 * email: gh.chen@siat.ac.cn
 *
 */

import twitter4j.Status;
import twitter4j.StatusAdapter;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.FilterQuery;

public class twitterRec {

		// TODO Auto-generated method stub
	public static void main(String[] args) {
		 TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
         StatusListener listener = new StatusListener() {
             public void onStatus(Status status) {
                 System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
             }

             public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
                 System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
             }

             public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
                 System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
             }

             public void onScrubGeo(long userId, long upToStatusId) {
                 System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
             }

             public void onException(Exception ex) {
                 ex.printStackTrace();
             }
         };
         //twitterStream.addListener(listener);
 		
         FilterQuery filterQuery = new FilterQuery();
         String[] keyWord = {"free app"};
         filterQuery.track(keyWord);
         //twitterStream.filter(filterQuery);

	}

}
