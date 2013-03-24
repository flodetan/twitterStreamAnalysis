/**
 * twiiterStramAnalysis GeneralClass.java
 *
 * Copyright 2013 Xdata@SIAT
 * Created:2013-3-25 1:59:52
 * email: gh.chen@siat.ac.cn
 */
package TwttrStrmAnlyst.utility;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


/**
 * @author Chen Guang hua
 *
 */
public class GeneralClassList {
	public String objID;	
	HashMap<String,Object> objMap =new HashMap<String,Object>();  // ObjID , Object
	HashMap<String,Integer> countMap =new HashMap<String,Integer>(); //ObjID , count
	
	GeneralClassList(){}
	GeneralClassList(String objID,HashMap<String,Integer> countMap,HashMap<String,Object> objMap ){
		this.objID=objID;
		this.countMap=countMap;
		this.objMap=objMap;		
	}


	
	/*public class AnyObject extends ArrayList<Object>{		
		private static final long serialVersionUID = 1L;
		//int count;
		Object obj;
		//public String objID;
		public AnyObject(){}
		AnyObject(Object obj){
			this.obj=obj;
		}
	}*/
	


	public  Object getObjByID(String objID){
		for(Map.Entry<String, Object> g : objMap.entrySet()){
			if(g.getKey().equals(objID)){
				return g.getValue();
			}
		}		   
		return null;		   
	}

	public  Integer getCountByID(String objID){
		for(Map.Entry<String, Integer> g : countMap.entrySet()){
			if(g.getKey().equals(objID)){
				return g.getValue();
			}
		}		   
		return null;		   
	}

	/*public  Boolean isExits(HashMap<String,Object> ObjMap,  String objID){
		for(Map.Entry<String,Object> g : ObjMap.entrySet()){
			if(g.getKey().equals(objID)){
				return true;
			}
		}
		return false;
	}
	
	public  Boolean isExits(HashMap<String,Integer> ObjHMap,  String objID){
		for(Entry<String, Integer> g : ObjHMap.entrySet()){
			if(g.getKey().equals(objID)){
				return true;
			}
		}
		return false;
	}*/
	public static  Boolean isExits(HashMap<String,Object> ObjHMap,  String objID){
		for(Entry<String, Object> g : ObjHMap.entrySet()){
			if(g.getKey().equals(objID)){
				return true;
			}
		}
		return false;
	}



//	public objObjMap(String path) throws SQLException, IOException{
//		this.ObjMap = this.read(path);
//		
//	}
		
	/*private HashMap<String, Object> read(String path) throws  IOException{
		File file = new File(path);
		//FileDataStoreFactorySpi factory = FileDataStoreFinder.getDataStoreFactory("shp");
		//Map params = Collections.singletonMap( "url", file.toURL() );
		ShapefileDataStore shpDataStore = null;
		shpDataStore = new ShapefileDataStore(file.toURL());
		shpDataStore.setStringCharset(Charset.forName("GBK")); 

		//Feature Access
		String typeName = shpDataStore.getTypeNames()[0];  
		FeatureSource<ObjectType, Object> featureSource = null;  
		featureSource = (FeatureSource<ObjectType, Object>)shpDataStore.getFeatureSource(typeName);  
		FeatureCollection<ObjectType, Object> result = featureSource.getFeatures();  
		FeatureIterator<Object> itertor = result.features();  
		while(itertor.hasNext()){  
			//Data Reader
			Object feature = itertor.next();  


			List<Object> fields = feature.getAttributes();


			String objID=feature.getAttribute("objID").toString();

			if (!isExits(ObjMap, objID)) {

				Object Object=new Object();
				Object.add(feature);






				ObjMap.put(objID,Object);  //添加网格

			}
			else{

				Object Object=getObjByID(objID);
				Object.add(feature); 		


			}  
			itertor.close(); 




			return ObjMap;
		}
	}*/

	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
	}
	

}
