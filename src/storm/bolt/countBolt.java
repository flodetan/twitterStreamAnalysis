package storm.bolt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;

public class countBolt implements IRichBolt {
	private OutputCollector _collector;	
	Integer taskId;
	String taskName;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		// TODO Auto-generated method stub
		this.taskName = context.getThisComponentId();
		this.taskId = context.getThisTaskId();
		this._collector = collector;
		
	}

	public void execute(Tuple input) {
		// TODO Auto-generated method stub
		System.out.print(input.size());
		System.out.print(input.getValues().toString());
			
		
		Fields fields=input.getFields();
		int size=fields.size();
		String cur_dir=System.getProperty("user.dir");		
		cur_dir=cur_dir+"/twitteStruct.xml";
		File file=new File(cur_dir);
		if (!file.exists()) { 
			countBolt.writeToFile(cur_dir," <FIELDLIST>\n");
			for(int i=0;i<size;i++){
			countBolt.writeToFile(cur_dir,"\t<FIELD>");
			countBolt.writeToFile(cur_dir, fields.get(i));
			countBolt.writeToFile(cur_dir,"</FIELD>\n");
			}	
			countBolt.writeToFile(cur_dir," </FIELDLIST>\n");
		}
		
		String path=countBolt.getPath();
		List<Object> twValues=input.getValues();
		for(int i=0;i<size;i++){
			if(twValues.get(i).getClass().equals(java.util.Date.class) ){
				SimpleDateFormat sdf2= new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
				String timeStamp=sdf2.format(twValues.get(i));
				countBolt.writeToFile(path,timeStamp);
				countBolt.writeToFile(path,"|,");
			}
			else {
				countBolt.writeToFile(path,twValues.get(i).toString());
				countBolt.writeToFile(path,"|,");
			}
				
		}		
		
		
		System.out.print(twValues.toString());
		
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

	public static void writeToFile(String fileName, Object obj){
		try {
			//count=count+1;
			FileWriter fwriter;
			fwriter= new FileWriter(fileName,true);
		     BufferedWriter writer= new BufferedWriter(fwriter);
		      
		      	writer.write(obj.toString());

		      //writer.write("\n\n");
		      writer.close(); 
				
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
	}
	
	 public static void newFolder(String folderPath) { 
		    try { 
		      String filePath = folderPath.toString(); 
		      //filePath = filePath.toString(); 
		      java.io.File myFilePath = new java.io.File(filePath); 
		      if (!myFilePath.exists()) { 
		        myFilePath.mkdir(); 
		      } 
		    } 
		    catch (Exception e) { 
		      System.out.println("Eorror: Can't create new folder!"); 
		      e.printStackTrace(); 
		    } 
		  }
	 
	 public static String getPath(){
		 DecimalFormat df2=(DecimalFormat) DecimalFormat.getInstance(); 	
			SimpleDateFormat sdf2= new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
			SimpleDateFormat sdf3= new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf4= new SimpleDateFormat("yyyy-MM-dd-HH-");
			df2.applyPattern("00"); 
			
			
			Date nowDate=new Date();
			String nowTime=sdf2.format(nowDate);
			 String cur_dir=System.getProperty("user.dir");				 
			 String path=cur_dir+"/rawGPSData/"+sdf3.format(nowDate);
			 countBolt.newFolder(path);
			
			int min=nowDate.getMinutes();
			int second=nowDate.getSeconds();
			if(min<30 ){min=00;second=00;	}
			else if(min>=30){min=30;second=00;}
			path=cur_dir+"/rawTwitterData/"+sdf3.format(nowDate)+"/"+sdf4.format(nowDate)+df2.format(min)+"-"+df2.format(second);
			
			return path;
	 }
	 
}
