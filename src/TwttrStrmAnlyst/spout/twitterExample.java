package TwttrStrmAnlyst.spout;

/**
 * twiiterStramAnalysis twitterSteamSpout twitterExample.java
 *
 * Copyright 2013 Xdata@SIAT
 * Created:2013-3-22 下午7:18:55
 * email: gh.chen@siat.ac.cn
 *
 */

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
 
/**
 * A simple example that uses HttpClient to execute an HTTP request against
 * a target site that requires user authentication.
 */
public class twitterExample {
 
    public static void main(String[] args) throws Exception {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {
 
 
            httpclient.getCredentialsProvider().setCredentials(
                    new AuthScope("https://stream.twitter.com", 443),
                    new UsernamePasswordCredentials("whughchen@gmail.com", "543634"));
                    //new UsernamePasswordCredentials("XXXX@gmail.com", "zzzz"));
 
            HttpGet httpget = new HttpGet("https://stream.twitter.com/1/statuses/sample.json");
 
            System.out.println("executing request" + httpget.getRequestLine());
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
 
            System.out.println("----------------------------------------");
            System.out.println(response.getStatusLine());
            if (entity != null) {
                System.out.println("Response content length: " + entity.getContentLength());
            }
            EntityUtils.consume(entity);
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
    }
}