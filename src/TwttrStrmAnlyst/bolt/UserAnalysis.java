package TwttrStrmAnlyst.bolt;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import twitter4j.User;

import TwttrStrmAnlyst.utility.GeneralClass;
import TwttrStrmAnlyst.utility.GeneralMethod;
import backtype.storm.hooks.info.EmitInfo;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class UserAnalysis implements IRichBolt {


	private static final long serialVersionUID = 1L;
	private String taskName;
	private int taskId;
	private OutputCollector _collector;
	HashMap<Object, Integer> userFollowerCount=new HashMap<Object, Integer>(); // <userID, count>
	//HashMap<Object, Integer> userStatusCount=new HashMap<Object, Integer>();

	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		// TODO Auto-generated method stub
		this.taskName = context.getThisComponentId();
		this.taskId = context.getThisTaskId();
		this._collector = collector;
		
		
	}

	/**
	 *
	 */
	public void execute(Tuple input) {
		User user=(User) input.getValueByField("user");
		//userFollowerCount=new HashMap<Object, Integer>();
		//userStatusCount=new  HashMap<Object, Integer>();
		userFollowerCount.put(user, user.getFollowersCount());
		//userFollowerCount.put(user, user.getStatusesCount());
		
		Date nowDate=new Date();
		int min=nowDate.getMinutes();
		int second=nowDate.getSeconds();
		
		if(min%2==0 && second==0){
			
			@SuppressWarnings({ "rawtypes", "unchecked" })
			Map<Object, Object> sortedUserMap=GeneralMethod.sortMapByValue(userFollowerCount,10);
			//ArrayList<Map.Entry<Object,Integer>> sortedUserMap=GeneralMethod.getSortedHashtableByValue(userFollowerCount);
			String filename=GeneralMethod.getIntLocaltime(2, "mostPopularUser");	

			BufferedWriter br;
			try {
				br = new BufferedWriter(new FileWriter(filename,true));
				int rank=0;
				for(Entry d:sortedUserMap.entrySet()){
					rank=rank+1;
					User user2=(User) d.getKey();			
					br.write("\n"+ rank +","+user2.getScreenName()+","+d.getValue()+","+user2.getProfileImageURL());
					System.out.print("\n"+ rank +","+user2.getScreenName()+","+d.getValue()+","+user2.getMiniProfileImageURL());
					br.flush();
				}
				rank=0;	
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			try {
				userFollowerCount.clear();
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}	

	}
		

		
		
		

		
	public void cleanup() {
		// TODO Auto-generated method stub
		
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		declarer.declare(new Fields("user","count"));
	}

	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}


}
