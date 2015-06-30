package mytest;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpURLConnectionExample {
	
	private final String USER_AGENT = "Mozilla/5.0";

	public static void main(String[] args) throws Exception {
		HttpURLConnectionExample http = new HttpURLConnectionExample();
		System.out.println("\nTesting 2 - Send Http POST request");
		http.sendPost();
	}

	private void sendPost() throws Exception {
		String url = "https://selfsolve.apple.com/wcResults.do";
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
 
		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
 
		String urlParameters = "{ \"apiKey\": \"ayyyyyy\",\n"
								+ "\"metrics\": [\n"
								+ "{\n \"applicationName\": \"tano\",\n"
								+ "\"operation\": \"alls\",\n" 
								+ "\"marketplace\": \"\",\n" 
								+ "\"hostName\": \"local\",\n" 
								+ "\"startTime\": 1231231,\n" 
								+ "\"endTime\": 222211,\n" 
								+ "\"metricName\": \"ayyy\",\n" 
								+ "\"value\": 2321.222 \n} \n] \n}";
 
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
 
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
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
		System.out.println("Response to string is :");
		System.out.println(response.toString());
		
	}

}
