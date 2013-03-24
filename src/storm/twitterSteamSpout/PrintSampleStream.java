package storm.twitterSteamSpout;  
//import org.json.JSONObject;  
//import org.json.JSONObject;  
//import com.mongodb.BasicDBObject;  
//import com.mongodb.util.JSON  
import twitter4j.*;  
import twitter4j.conf.ConfigurationBuilder;  
//import twitter4j.internal.org.json.JSONObject;  
//import java.io.*;    
import twitter4j.json.DataObjectFactory;  
//import com.mongodb.BasicDBObject;  
import com.mongodb.DB;  
import com.mongodb.DBCollection;  
//import com.mongodb.DBCursor;  
import com.mongodb.DBObject;  
import com.mongodb.Mongo;  
//import com.mongodb.MongoException;  
import com.mongodb.util.JSON;  
//import twitter4j.internal.org.json.JSONException;  
//import org.json.*;  
/**  
 * <p>This is a code example of Twitter4J Streaming API - sample method support.<br>  
 * Usage: java twitter4j.examples.PrintSampleStream<br>  
 * </p>  
 *  
 * 
 */  
public final class PrintSampleStream {  
    /**  
     * Main entry of this application.  
     *  
     * @param args  
     */  
    public DBCollection collection;  
    public Mongo mongo;  
    public int count = 1;  
      
    public void LinkMongodb() throws Exception {  
          
        /*  
         * Link Mongodb   
         * build a data named FourS2  
         * build a collection named Foursquare  
         *    
         */  
        mongo = new Mongo("localhost", 27017);  
        DB db = mongo.getDB("TwitterMovie3");  
        collection = db.getCollection("Movie");  
        System.out.println("Link Mongodb!");  
    }  
      
  
    public static void main(String[] args) throws TwitterException {  
          
        final PrintSampleStream pr = new PrintSampleStream();  
          
        try {  
            pr.LinkMongodb();  
        }  catch (Exception e) {  
            e.printStackTrace();  
        }    
          
        ConfigurationBuilder cb = new ConfigurationBuilder();  
        cb.setDebugEnabled(true)  
          /*.setOAuthConsumerKey("7ZVgfKiOvBDcDFpytRWSA")  
          .setOAuthConsumerSecret("JmeJVeym78arzmGthrDUshQyhkq6nWA9tWLUKxc")  
          .setOAuthAccessToken("321341780-Zy7LptVYBZBVvAeQ5GFJ4aKFw8sdqhWBnvA3pDuO")  
          .setOAuthAccessTokenSecret("foi8FnQCeN0J5cdwad05Q6d7dbytFayQn1ZOvmhF6Qc"); */
          .setOAuthConsumerKey("8r88QTXXn32F6ApT6y3yw")  
          .setOAuthConsumerSecret("9W92beI4AJu53T1ZTso8V3CHXk6NjqYNz0JbuwgZpzw")  
          .setOAuthAccessToken("390019331-rO8Bqd0TC29IJCkuy4UUhBNH1Lh0EJeo7oor6qIH")  
          .setOAuthAccessTokenSecret("B3T2AxUfVSjMvtkfKo2xsnbBnKM9tIA9dBdMscs6GoI"); 
        cb.setJSONStoreEnabled(true);  
          
        TwitterStreamFactory tf = new TwitterStreamFactory(cb.build());  
        TwitterStream twitterStream = tf.getInstance();  
        StatusListener listener = new StatusListener() {  
            public void onStatus(Status status) {  
                System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());  
                System.out.println(status);  
                String str = DataObjectFactory.getRawJSON(status);              
                try {  
                    //JSONObject nnstr = new JSONObject(newstr);  
                    DBObject dbObject =(DBObject)JSON.parse(str);  
                    pr.collection.insert(dbObject);  
                    System.out.println(dbObject);  
                    pr.count++;  
                    if(pr.count>300000) {  
                        pr.mongo.close();  
                        System.exit(0);  
                    }  
                }  catch (Exception e) {  
                    e.printStackTrace();  
                }   
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
  
            public void onStallWarning(StallWarning warning) {  
                System.out.println("Got stall warning:" + warning);  
            }  
  
            public void onException(Exception ex) {  
                ex.printStackTrace();  
            }  
        };  
        twitterStream.addListener(listener);  
  
        String[] trackArray;  
        String[] Track = {"IMDB", "movie","film","cinema", };  
        //trackArray[0] = "Obama";  
        //trackArray[1] = "Romney";  
          
        FilterQuery filter = new FilterQuery();  
        filter.track(Track);  
        twitterStream.filter(filter);  
        //pr.mongo.close();  
        //filter.locations(locations);
        
    }  
         
} 