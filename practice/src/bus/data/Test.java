package bus.data;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;

public class Test {
	
	private static final int REAL_TIME = 1;
	private static final int HOUR = 2;
	private static final int DAY = 3;
	private static final int GITA = 4;
	
	private static final String key1 = "RFjSKMmZNpiq1glk5UPEPjq5R3In5yPohr9FyopzLb6RVDxyTNqKHpgsx7wm%2F%2Bep76YaLNQnsJ7d2eRR9PPUKQ%3D%3D";	// realTiem
	private static final String key2 = "RFjSKMmZNpiq1glk5UPEPjq5R3In5yPohr9FyopzLb6RVDxyTNqKHpgsx7wm%2F%2Bep76YaLNQnsJ7d2eRR9PPUKQ%3D%3D";	// hourly
	private static final String key3 = "RFjSKMmZNpiq1glk5UPEPjq5R3In5yPohr9FyopzLb6RVDxyTNqKHpgsx7wm%2F%2Bep76YaLNQnsJ7d2eRR9PPUKQ%3D%3D";	// daily

	
    public static void main(String[] args) throws IOException {
        Test.getData(Test.REAL_TIME);
        
        //Test.getData(Test.REAL_TIME);
        //Test.getData(Test.DAY, "20190814150000");
        
       
    }
    public static void getData(int type) throws IOException {
    	getData(type, "");
    }
    
    public static void getData(int type, String date) throws IOException {
        
    	StringBuilder urlBuilder;
    	
        switch(type) {
        case Test.REAL_TIME:
        	urlBuilder = new StringBuilder("http://openapi.jejuits.go.kr/rfcapi/rest/jejuits/getTrafficInfo"); /*URL*/
        	urlBuilder.append("?" + URLEncoder.encode("authApiKey","UTF-8") + "="+key3); /*Service Key*/
        	break;
        case Test.HOUR:
        	urlBuilder = new StringBuilder("http://openapi.jejuits.go.kr/rfcapi/rest/jejuits/getTrafficInfoHourlyStat"); /*URL*/
        	urlBuilder.append("?" + URLEncoder.encode("authApiKey","UTF-8") + "="+key3); /*Service Key*/
        	urlBuilder.append("&" + URLEncoder.encode("statDt","UTF-8") + "=" + URLEncoder.encode(date, "UTF-8")); /*N/A*/
        	break;
        case Test.DAY:
        	urlBuilder = new StringBuilder("http://openapi.jejuits.go.kr/rfcapi/rest/jejuits/getTrafficInfoDailyStat"); /*URL*/
        	urlBuilder.append("?" + URLEncoder.encode("authApiKey","UTF-8") + "="+key3); /*Service Key*/
        	//urlBuilder.append("&" + URLEncoder.encode("statDt","UTF-8") + "=" + date); /*N/A*/
        	urlBuilder.append("&" + URLEncoder.encode("statDt","UTF-8") + "=" + URLEncoder.encode(date, "UTF-8")); /*N/A*/
        	break;
        case Test.GITA:
        	urlBuilder = new StringBuilder("http://busopen.jeju.go.kr/OpenAPI/service/bis/BusLocation"); /*URL*/
        	urlBuilder.append("?" + URLEncoder.encode("route","UTF-8") + "=406002021");
        	break;
        default:
        	urlBuilder = new StringBuilder("http://openapi.jejuits.go.kr/rfcapi/rest/jejuits/getTrafficInfoDailyStat"); /*URL*/
        };
       
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
        	sb.append(line);
        }
        rd.close();
        conn.disconnect();
        String[] list = sb.toString().split("<list>");
        
        for(String s : list) {
        	System.out.println(s);
        }
        //System.out.println(sb.toString());
    }
}
