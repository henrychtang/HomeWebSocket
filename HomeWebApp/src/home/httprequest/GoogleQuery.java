package home.httprequest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GoogleQuery {
	private final String USER_AGENT = "Mozilla/5.0";
	 
	private void sendGet() throws Exception {
		 
		//String url = "http://www.google.com/finance/info?q=NSE:AIAENG,TSLA";
		String url = "http://www.google.com/finance/info?q=HKG:0001,0005,0013";
		 
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		// optional default is GET
		con.setRequestMethod("GET");
 
		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);
 
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
 
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
		//print result
		System.out.println(response.toString());
 
	}		
	public static void main(String[] args) throws Exception{
		
		GoogleQuery http = new GoogleQuery();
		 // TODO Auto-generated method stub
		System.out.println("Make a http query in google site");
		System.out.println("Testing 1 - Send Http GET request");
		http.sendGet();
		
	}

}
