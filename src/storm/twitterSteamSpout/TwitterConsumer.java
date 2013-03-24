/**
 * twiiterStramAnalysis twitterSteamSpout TwitterConsumer.java
 *
 * Copyright 2013 Xdata@SIAT
 * Created:2013-3-22 下午6:44:30
 * email: gh.chen@siat.ac.cn
 */
package storm.twitterSteamSpout;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;

/**
 * A hacky little class illustrating how to receive and store Twitter streams
 * for later analysis, requires Apache Commons HTTP Client 4+. Stores the data
 * in 64MB long JSON files.
 * 
 * Usage:
 * 
 * TwitterConsumer t = new TwitterConsumer("username", "password",
 *      "http://stream.twitter.com/1/statuses/sample.json", "sample");
 * t.start();
 */
public class TwitterConsumer extends Thread {
    //
    static String STORAGE_DIR = "d:\\";
    static long BYTES_PER_FILE = 64 * 1024 * 1024;
    //
    public long Messages = 0;
    public long Bytes = 0;
    public long Timestamp = 0;
//  AccessToken token= new AccessToken("390019331-rO8Bqd0TC29IJCkuy4UUhBNH1Lh0EJeo7oor6qIH", "B3T2AxUfVSjMvtkfKo2xsnbBnKM9tIA9dBdMscs6GoI");
//  twitterStream.setOAuthAccessToken(token);
//  twitterStream.setOAuthConsumer("8r88QTXXn32F6ApT6y3yw", "9W92beI4AJu53T1ZTso8V3CHXk6NjqYNz0JbuwgZpzw");
//
    private String accessToken = "390019331-rO8Bqd0TC29IJCkuy4UUhBNH1Lh0EJeo7oor6qIH";
    private String accessSecret = "B3T2AxUfVSjMvtkfKo2xsnbBnKM9tIA9dBdMscs6GoI";
    private String consumerKey = "8r88QTXXn32F6ApT6y3yw";
    private String consumerSecret = "9W92beI4AJu53T1ZTso8V3CHXk6NjqYNz0JbuwgZpzw"; 

    private String feedUrl;
    private String filePrefix;
    boolean isRunning = true;
    File file = null;
    FileWriter fw = null;
    long bytesWritten = 0;

    public static void main(String[] args) {
        TwitterConsumer t = new TwitterConsumer(
           /* "XXX", 
            "XXX",
            "XXX",
            "XXX",*/
        		"390019331-rO8Bqd0TC29IJCkuy4UUhBNH1Lh0EJeo7oor6qIH",
        		"B3T2AxUfVSjMvtkfKo2xsnbBnKM9tIA9dBdMscs6GoI",
        		"8r88QTXXn32F6ApT6y3yw",
        		"9W92beI4AJu53T1ZTso8V3CHXk6NjqYNz0JbuwgZpzw",
            "http://stream.twitter.com/1/statuses/sample.json", "sample");
        t.start();
    }

    public TwitterConsumer(String accessToken, String accessSecret, String consumerKey, String consumerSecret, String url, String prefix) {
        this.accessToken = accessToken;
        this.accessSecret = accessSecret;
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
        feedUrl = url;
        filePrefix = prefix;
        Timestamp = System.currentTimeMillis();
    }

    /**
     * @throws IOException
     */
    private void rotateFile() throws IOException {
        // Handle the existing file
        if (fw != null)
            fw.close();
        // Create the next file
        file = new File(STORAGE_DIR, filePrefix + "-"
                + System.currentTimeMillis() + ".json");
        bytesWritten = 0;
        fw = new FileWriter(file);
        System.out.println("Writing to " + file.getAbsolutePath());
    }

    /**
     * @see java.lang.Thread#run()
     */
    public void run() {
        // Open the initial file
        try { rotateFile(); } catch (IOException e) { e.printStackTrace(); return; }
        // Run loop
        while (isRunning) {
            try {

                OAuthConsumer consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
                consumer.setTokenWithSecret(accessToken, accessSecret);
                HttpGet request = new HttpGet(feedUrl);
                consumer.sign(request);

                DefaultHttpClient client = new DefaultHttpClient();
                HttpResponse response = client.execute(request);
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent()));
                while (true) {
                    String line = reader.readLine();
                    if (line == null)
                        break;
                    if (line.length() > 0) {
                        if (bytesWritten + line.length() + 1 > BYTES_PER_FILE)
                            rotateFile();
                        fw.write(line + "\n");
                        bytesWritten += line.length() + 1;
                        Messages++;
                        Bytes += line.length() + 1;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Sleeping before reconnect...");
            try { Thread.sleep(1000); } catch (Exception e) { }
        }
    }
}
