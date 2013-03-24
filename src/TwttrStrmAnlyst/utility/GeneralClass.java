package TwttrStrmAnlyst.utility;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

@SuppressWarnings("serial")
public class GeneralClass extends HashMap<Object, Integer>{

	
	//private Object obj;
	//private Integer count;
	static HashMap<Object, Integer> objHashMap;
	GeneralClass(){}
	//GeneralClass( Object obj, Integer count){
	//	this.obj= obj;
	//	this.count=count;
	//	}	


	public static  Integer getCountByObj(Object obj){
		for(Map.Entry<Object, Integer> g : objHashMap.entrySet()){
			if(g.getKey().equals(obj)){
				return g.getValue();
			}
		}		   
		return null;		   
	}

	public static  Boolean isExits(HashMap<Object, Integer > ObjHashMap,  Object obj){
		for(Entry<Object, Integer> g : ObjHashMap.entrySet()){
			if(g.getKey().equals(obj)){
				return true;
			}
		}
		return false;
	}

	
	
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
