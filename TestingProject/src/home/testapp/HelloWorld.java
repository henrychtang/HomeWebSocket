package home.testapp;

import java.util.HashMap;

public class HelloWorld {
	
	HashMap <String,String> map=new HashMap<String,String>();
	
	HelloWorld(){
		map.put("Monday","Plan A");
		map.put("Tuesday","Plan B");
		map.put("Wednesday","Plan C");
		map.put("Wednesday","Plan D");
		map.put("Monday","Plan E");
		map.put("Tuesday","Plan F");
			}
	

	public static void main(String[] args) throws Exception{
		
	        HelloWorld hw=new HelloWorld();
	        System.out.println(hw.map);
	}

}
