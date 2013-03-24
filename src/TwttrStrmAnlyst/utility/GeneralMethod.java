package TwttrStrmAnlyst.utility;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import TwttrStrmAnlyst.bolt.countBolt;


public class GeneralMethod {

	
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
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
