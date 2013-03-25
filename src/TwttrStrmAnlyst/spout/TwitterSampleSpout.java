/**
 * twiiterStramAnalysis twitterSteamSpout test.java
 *
 * Copyright 2013 Xdata@SIAT
 * Created:2013-3-22 下午6:59:52
 * email: gh.chen@siat.ac.cn
 */
package TwttrStrmAnlyst.spout;


import TwttrStrmAnlyst.utility.*;
import backtype.storm.Config;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import twitter4j.FilterQuery;
import twitter4j.GeoLocation;
import twitter4j.HashtagEntity;
import twitter4j.Location;
import twitter4j.Place;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.StreamController.User;
import twitter4j.UserMentionEntity;

public class TwitterSampleSpout extends BaseRichSpout {
    private static final long serialVersionUID = 1L;
	SpoutOutputCollector _collector;
    LinkedBlockingQueue<Status> queue = null;
    TwitterStream _twitterStream;
    String _username;
    String _pwd;
    
    
    public TwitterSampleSpout(String username, String pwd) {
        _username = username;
        _pwd = pwd;
    }
    
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        queue = new LinkedBlockingQueue<Status>(1000);
        _collector = collector;
        StatusListener listener = new StatusListener() {

            public void onStatus(Status status) {
                queue.offer(status);
            }

            public void onDeletionNotice(StatusDeletionNotice sdn) {
            }

            public void onTrackLimitationNotice(int i) {
            }

            public void onScrubGeo(long l, long l1) {
            }

            public void onException(Exception e) {
            }

			public void onStallWarning(StallWarning arg0) {
				// TODO Auto-generated method stub
				
			}
            
        };
        TwitterStreamFactory fact = new TwitterStreamFactory(new ConfigurationBuilder().setUser(_username).setPassword(_pwd).build());
        _twitterStream = fact.getInstance();
        _twitterStream.addListener(listener);
        _twitterStream.sample();
       
    }

    public void nextTuple() {
        Status ret = queue.poll();
        if(ret==null) {
            Utils.sleep(50);
        } else {
  
        	Values tupleValues= new Values();
        	tupleValues=this.intepretMsg(ret);
        	_collector.emit(tupleValues);

        	      	
            

        }
    }

    @Override
    public void close() {
        //_twitterStream.shutdown();
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        Config ret = new Config();
        ret.setMaxTaskParallelism(1);
        return ret;
    }    

    @Override
    public void ack(Object id) {
    }

    @Override
    public void fail(Object id) {
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("timeStamp",
        		"twitterID",
                "place",
                "user",
                "reTwCnt",
                "source",
                "location",
                "tag",
                "userMentioned",
                "text"   			
        		));
    }
    
    public Values intepretMsg(Status ret){ 
    	DecimalFormat df2=(DecimalFormat) DecimalFormat.getInstance(); 
    	df2.applyPattern("0.000000");
    	SimpleDateFormat sdf2= new SimpleDateFormat("yyyy-MM-dd-HH");
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String date=sdf2.format(new Date());
    	String cur_dir=System.getProperty("user.dir")+"/"+date;	
        
    	
    	Date timeStamp=ret.getCreatedAt();
    	long twitterID=ret.getId();
    	Place place=ret.getPlace(); 
    	long reTwCnt=ret.getRetweetCount();
    	twitter4j.User user=ret.getUser();    	

    	String source=ret.getSource();
    	GeoLocation location=ret.getGeoLocation(); 
    	UserMentionEntity[] userMentioned = ret.getUserMentionEntities();
    	HashtagEntity[] tag = ret.getHashtagEntities();
    	String text=ret.getText();
    	
    	/*System.out.print(sdf.format(timeStamp)+","+
    	place+","+
    	user+","+
    	reTwCnt+","+	
    	source+","+
    	location+","+
    	tag+","+
    	userMentioned+","+
    	"{"+text+"}"+"\r\n");*/


    	/*String twitt=sdf2.format(timeStamp)+","+
    	    	place+","+
    	    	user.getScreenName().toString()+","+
    	    	reTwCnt+","+	
    	    	source+","+
    	    	"["+df2.format(location.getLongitude())+","+df2.format(location.getLatitude())+"]"+","+
    	    	tag.toString()+","+
    	    	userMentioned.toString()+","+
    	    	"{"+text+"}"+"\r\n";
    	GeneralMethod.writeToFile(cur_dir, twitt);
    	System.out.print(twitt);*/
    	
    	
    	GeneralMethod.writeToFile(cur_dir, sdf.format(timeStamp)+";");
    	GeneralMethod.writeToFile(cur_dir, twitterID+";");
    	if(place!=null){
    		GeneralMethod.writeToFile(cur_dir, place.getFullName().toString()+";");
    	}else{
    		GeneralMethod.writeToFile(cur_dir,"N_P"+";");
    	}
    	GeneralMethod.writeToFile(cur_dir, reTwCnt+";");
    	GeneralMethod.writeToFile(cur_dir, "["+user.getId()+","+user.getScreenName()+","+user.getName()+","+user.getMiniProfileImageURL()+","+user.getStatusesCount()+","+user.getFollowersCount()+","+user.getFollowersCount()+","+user.getLocation()+"];");	
	
    	GeneralMethod.writeToFile(cur_dir, source+";");	
    	if(location!=null){
    		GeneralMethod.writeToFile(cur_dir, "["+df2.format(location.getLongitude())+","+df2.format(location.getLatitude())+"]"+";");
    	}else{
    		GeneralMethod.writeToFile(cur_dir,"N_L"+";");
    	}
    	if(tag.length>=1){
    		GeneralMethod.writeToFile(cur_dir, "[");
    		for(int i=0;i<tag.length;i++){
    		GeneralMethod.writeToFile(cur_dir, tag[i].toString()+",");
    		}
    		GeneralMethod.writeToFile(cur_dir, "];");
    	}else{
    		GeneralMethod.writeToFile(cur_dir,"N_T"+";");
    	}
    	if(userMentioned.length>=1){
    		//GeneralMethod.writeToFile(cur_dir, "[");
    		for(int i=0;i<userMentioned.length;i++){
    	     GeneralMethod.writeToFile(cur_dir, "["+userMentioned[i].getId()+","+userMentioned[i].getScreenName()+","+userMentioned[i].getName()+"]");
    		}
    		//GeneralMethod.writeToFile(cur_dir, "];");
    		GeneralMethod.writeToFile(cur_dir, ";");
    	}else{
    		GeneralMethod.writeToFile(cur_dir,"N_U"+";");
    	}
    	GeneralMethod.writeToFile(cur_dir, "{"+text+"}"+"\r\n");
    
    	Values tupleValues=new Values(timeStamp,
    									twitterID,
    			                        place,
    			                        user,
    			                        reTwCnt,
    			                        source,
    			                        location,
    			                        tag,
    			                        userMentioned,
    			                        text   			
    			);    	

        
    	return tupleValues; 
    	
    }
    
}
