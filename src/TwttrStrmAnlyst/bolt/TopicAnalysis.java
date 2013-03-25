package TwttrStrmAnlyst.bolt;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import twitter4j.HashtagEntity;

import TwttrStrmAnlyst.utility.GeneralClass;
import TwttrStrmAnlyst.utility.GeneralMethod;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;

public class TopicAnalysis implements IRichBolt{
	HashMap<Object, Integer> topicCount=new HashMap<Object, Integer>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		// TODO Auto-generated method stub
		
	}

	public void execute(Tuple input) {
		HashtagEntity[] topic = (HashtagEntity[]) input.getValueByField("tag"); //tags=input.getValueByField("tag").toString().trim();
		//String[] topic;
		
		if(topic.length<1 ){
			return;
		}else{
			//String[] tags=topic;

			for(int i=0;i<topic.length;i++){
				String tag=topic[i].getText().trim();
				if(GeneralClass.isExits(topicCount, topic[i].getText()))
				{
					Integer count=1;
					
					topicCount.put(tag, count);
				}
				else if( (!tag.equals(null)) && (!topicCount.equals(null)) ){
					Integer count=GeneralClass.getCountByObj(tag );				
					topicCount.put(tag, count++);
				}
			}
		}
		
				
		Date nowDate=new Date();
		int min=nowDate.getMinutes();
		int second=nowDate.getSeconds();
		
		if(min%2==0 && second==0){
			@SuppressWarnings({ "rawtypes", "unchecked" })
			Map<Object, Object> sortedtopicMap=GeneralMethod.sortMapByValue(topicCount,10);
			topicCount.clear();
			
			BufferedWriter br;
			try {
				String filename=GeneralMethod.getIntLocaltime(2, "mostHotTopic");
				if(filename.equals(null)){
					return;
				}else
					br = new BufferedWriter(new FileWriter(filename,true));
				
				int rank=0;
				for(Entry d:sortedtopicMap.entrySet()){
					rank=rank+1;
					String topic2= (String) d.getKey();			
					br.write("\n"+ rank +","+topic2+","+d.getValue()+","+topic2);
					//System.out.print("\n"+ rank +","+topic2.getScreenName()+","+d.getValue()+","+topic2.getMiniProfileImageURL());
					br.flush();
				}
				rank=0;	
			} catch (IOException e) {
				e.printStackTrace();
			}
			}		
		
	}

	public void cleanup() {
		// TODO Auto-generated method stub
		
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		
	}

	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

}
