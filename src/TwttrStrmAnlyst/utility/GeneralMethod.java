package TwttrStrmAnlyst.utility;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.omg.PortableServer.POAPackage.WrongAdapter;

import main.java.realODMatrix.bolt.CountBolt.District;

import TwttrStrmAnlyst.bolt.countBolt;


public class GeneralMethod {

	
	public static void writeToFile(String fileName, Object obj){
		try {
			//count=count+1;
			FileWriter fwriter;
			fwriter= new FileWriter(fileName,true);
		     BufferedWriter writer= new BufferedWriter(fwriter);
		      
		      	writer.write(obj.toString());

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
	
	    public static ArrayList getSortedHashtableByValue(Map h) {          
	        ArrayList<Map.Entry<Object,Integer>> l = new ArrayList<Map.Entry<Object,Integer>>(h.entrySet());    
	        Collections.sort(l, new Comparator<Map.Entry<Object,Integer>>() {      
	            public int compare(Map.Entry<Object,Integer> o1, Map.Entry<Object,Integer> o2) {      
	                return (o2.getValue() - o1.getValue());      
	            }      
	        });       
	        return l;    
	    } 
	    
	    public static Map.Entry[] getSortedMapByValue(Map h) {  
	    	Set set = h.entrySet();  
	    	Map.Entry[] entries = (Map.Entry[]) set.toArray(new Map.Entry[set.size()]);  
	    	Arrays.sort(entries, new Comparator() {  
	    		public int compare(Object arg0, Object arg1) {  
	    			Long key1 = Long.valueOf(((Map.Entry) arg0).getValue().toString());  
	    			Long key2 = Long.valueOf(((Map.Entry) arg1).getValue().toString());  
	    			return key1.compareTo(key2);  
	    		}  
	    	});  

	    	return entries;  
	    } 
	    
	    public static String getIntLocaltime(int interval,String folderName){
	    	if(interval<0 || interval>59){
	    		System.out.println("Error: you have passed in Invalid interval,valid interval should be 0<interval<59");
	    	return null;
	    	}
	    		
			Date nowDate=new Date();
			SimpleDateFormat sdf2= new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
			SimpleDateFormat sdf3= new SimpleDateFormat("yyyy-MM-dd");
			int min=nowDate.getMinutes();
			int second=nowDate.getSeconds();
			if( (min%interval) ==0 && (second==0) ){
				String nowTime=sdf2.format(nowDate);			 
				 String cur_dir=System.getProperty("user.dir");
				 cur_dir=cur_dir+"/"+folderName+sdf3.format(nowDate);
				 newFolder(cur_dir);
				 return cur_dir=cur_dir+"/"+nowTime;
			}else 
				return null;	
	    	
	    }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
