package TwttrStrmAnlyst.bolt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import twitter4j.User;

import TwttrStrmAnlyst.utility.GeneralClass;
import TwttrStrmAnlyst.utility.GeneralMethod;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;

public class UserAnalysis implements IRichBolt {


	private static final long serialVersionUID = 1L;
	private String taskName;
	private int taskId;
	private OutputCollector _collector;
	//static GeneralClass userMap=null ;	
	HashMap<Object, Integer> userCount; // <userID, count>

	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		// TODO Auto-generated method stub
		this.taskName = context.getThisComponentId();
		this.taskId = context.getThisTaskId();
		this._collector = collector;
		
		
	}

	public void execute(Tuple input) {
		User user=(User) input.getValueByField("user");
		String userID=user.getName();
		
		
		if (!GeneralClass.isExits(userCount, user)) {
			 //没有此用户，则新建一个用户，并存起来				
			Integer count=1;
			userCount.put(user, count);

			return ;
			  
		}else{   //如果已经有该用户
			Integer count=GeneralClass.getCountByObj(user);
			
			count++;
	
			}
		Map.Entry[] sortedUserMap=GeneralMethod.getSortedMapByValue(userCount);
				
		String filename=GeneralMethod.getIntLocaltime(60, "userCount");
		//String userProperty=u
		GeneralMethod.writeToFile(filename, sortedUserMap);	
		
		}
		
		
		

		
	public void cleanup() {
		// TODO Auto-generated method stub
		
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		declarer.declare(new Fields("userCount"));
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
