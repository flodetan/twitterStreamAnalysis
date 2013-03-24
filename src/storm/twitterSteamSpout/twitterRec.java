package storm.twitterSteamSpout;

/**
 * twiiterStramAnalysis twitterSteamSpout twitterRec.java
 *
 * Copyright 2013 Xdata@SIAT
 * Created:2013-3-22 下午8:08:38
 * email: gh.chen@siat.ac.cn
 *
 */

import twitter4j.AccountSettings;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusAdapter;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.FilterQuery;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class twitterRec {

		// TODO Auto-generated method stub
	public static void main(String[] args) throws TwitterException {
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

				public void onStallWarning(StallWarning warning) {
					// TODO Auto-generated method stub
					
				}
		    };
//		     .setOAuthConsumerKey("8r88QTXXn32F6ApT6y3yw")  
//	          .setOAuthConsumerSecret("9W92beI4AJu53T1ZTso8V3CHXk6NjqYNz0JbuwgZpzw")  
//	          .setOAuthAccessToken("390019331-rO8Bqd0TC29IJCkuy4UUhBNH1Lh0EJeo7oor6qIH")  
//	          .setOAuthAccessTokenSecret("B3T2AxUfVSjMvtkfKo2xsnbBnKM9tIA9dBdMscs6GoI"); 

         twitterStream.addListener(listener);
 		
         FilterQuery filterQuery = new FilterQuery();
         String[] keyWord = {"free app"};
         filterQuery.track(keyWord);
//         AccessToken token= new AccessToken("390019331-rO8Bqd0TC29IJCkuy4UUhBNH1Lh0EJeo7oor6qIH", "B3T2AxUfVSjMvtkfKo2xsnbBnKM9tIA9dBdMscs6GoI");
//         twitterStream.setOAuthAccessToken(token);
//         twitterStream.setOAuthConsumer("8r88QTXXn32F6ApT6y3yw", "9W92beI4AJu53T1ZTso8V3CHXk6NjqYNz0JbuwgZpzw");
//         twitterStream.filter(filterQuery);
         String CONSUMER_KEY="8r88QTXXn32F6ApT6y3yw";
         String CONSUMER_SECRET="9W92beI4AJu53T1ZTso8V3CHXk6NjqYNz0JbuwgZpzw";
         ConfigurationBuilder builder = new ConfigurationBuilder();
         builder.setOAuthConsumerKey(CONSUMER_KEY);
         builder.setOAuthConsumerSecret(CONSUMER_SECRET);
         builder.setOAuthAccessTokenSecret("B3T2AxUfVSjMvtkfKo2xsnbBnKM9tIA9dBdMscs6GoI");
         builder.setOAuthAccessToken("390019331-rO8Bqd0TC29IJCkuy4UUhBNH1Lh0EJeo7oor6qIH");
         Configuration configuration = builder.build();
         TwitterFactory factory = new TwitterFactory(configuration);
         Twitter twitter = factory.getInstance();
         String s=twitter.getScreenName();
        // AccountSettings s=twitter.getAccountSettings();
         System.out.print(s);
	}
	

}
