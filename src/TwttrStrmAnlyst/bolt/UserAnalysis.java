package TwttrStrmAnlyst.bolt;

import java.util.Map;

import storm.realTraffic.gis.roadgridList;

import TwttrStrmAnlyst.utility.GeneralClassList;
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
	static GeneralClassList userMapList=null ;	

	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		// TODO Auto-generated method stub
		this.taskName = context.getThisComponentId();
		this.taskId = context.getThisTaskId();
		this._collector = collector;
		
		
	}

	public void execute(Tuple input) {
		

		
	}

	public void cleanup() {
		// TODO Auto-generated method stub
		
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		declarer.declare(new Fields("districts"));
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
