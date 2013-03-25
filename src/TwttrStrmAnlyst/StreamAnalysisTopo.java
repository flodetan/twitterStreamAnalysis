package TwttrStrmAnlyst;

import TwttrStrmAnlyst.bolt.LocationAnalysis;
import TwttrStrmAnlyst.bolt.TopicAnalysis;
import TwttrStrmAnlyst.bolt.UserAnalysis;
import TwttrStrmAnlyst.bolt.countBolt;
import TwttrStrmAnlyst.spout.TwitterSampleSpout;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;

public class StreamAnalysisTopo {

	/**
	 * @param args
	 */
	
	
	public static void main(String[] args) throws AlreadyAliveException, 
	InvalidTopologyException, 
	InterruptedException {
		// TODO Auto-generated method stub

		TwitterSampleSpout twitterSampleSpout = new TwitterSampleSpout("whughchen", "543634");

//		LocationAnalysis loctnBolt=new LocationAnalysis();
		UserAnalysis  userBolt= new UserAnalysis();
		TopicAnalysis topicBolt=new TopicAnalysis();	


		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("spout", twitterSampleSpout,1);
		//builder.setBolt("locationBolt", loctnBolt,1);		
		builder.setBolt("userBolt", userBolt,1).shuffleGrouping("spout");	
		builder.setBolt("topicBolt", topicBolt,1).shuffleGrouping("spout");
		
		Config conf = new Config();
		if(args!=null && args.length > 0) {
			conf.setNumWorkers(3);            

			LocalCluster  cluster= new LocalCluster();
			cluster.submitTopology(args[0], conf, builder.createTopology());
			//StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
		} 
		else {     

			conf.setDebug(true);
			conf.setMaxTaskParallelism(3);
			LocalCluster cluster = new LocalCluster();
			cluster.submitTopology(
					"Threshold_Test", conf, builder.createTopology());
			Thread.sleep(3000);
			cluster.shutdown(); 
		}

}


}
