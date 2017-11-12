package istic.weekend.web.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RestConsumer {
		
		
		public static JSONArray restWeather (String city_p) {
			
			JSONArray objList_l = null;
			
		  try {
			  // http://api.openweathermap.org/data/2.5/forecast?q=RENNES&units=metric&appid=0b1132d225995e5fa07975897eeea98a
			ResourceBundle bundle = ResourceBundle.getBundle("istic.weekend.web.rest.properties.ressource");
			String apiUrl_l= bundle.getString("rest.url");
			String apiKey_l= bundle.getString("rest.api.password");
			String urlFull_l = "http://"+apiUrl_l+city_p+"&units=metric&appid="+apiKey_l;
			URL url = new URL(urlFull_l);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/xml");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

			String jsonStr_l="";
			String output;
			//System.out.println("Output from Server .... \n"); //FIXME Utiliser log4j
			while ((output = br.readLine()) != null) {
				jsonStr_l+=output;
			}
			//System.out.println(jsonStr_l);
			conn.disconnect();
			JSONObject objFull_l;
			try {
				objFull_l = new JSONObject(jsonStr_l);
			objList_l = objFull_l.getJSONArray("list");
			
			//System.out.println("moyenTemp= "+getMoyenTemp(objList_l));
			
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		  } catch (MalformedURLException e) {
			  

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();

		  }
		  return objList_l;
		}
		
		public static Double getMoyenTemp(JSONArray array) {
			Double Temp =0.0;
			try {
				for (int i = 0; i < array.length(); i++) {
				Long currentTime = array.getJSONObject(i).getLong("dt");
				if(isSaturdayFever(currentTime)) {
					Temp+=array.getJSONObject(i).getJSONObject("main").getDouble("temp");
				}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Double moyen=0.0;
			if(Temp!=0.0) {
			 moyen= Temp / 4;
			}
			return moyen;
		}
		
		public static Double getMinTemp(JSONArray array) {
			Double Temp =0.0;
			Double Temptemp = 0.0;
			boolean begin = true;
			try {
				for (int i = 0; i < array.length(); i++) {
				Long currentTime = array.getJSONObject(i).getLong("dt");
				if(isSaturdayFever(currentTime)) {
					if(begin) {
						begin = false;
						Temp=array.getJSONObject(i).getJSONObject("main").getDouble("temp");
					}
					else {
						Temptemp=array.getJSONObject(i).getJSONObject("main").getDouble("temp");
						if(Temptemp<=Temp) {
							Temp = Temptemp;
						}
					}
				}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return Temp;
		}
		
		public static Double getMaxTemp(JSONArray array) {
			Double Temp =0.0;
			Double Temptemp = 0.0;
			boolean begin = true;
			try {
				for (int i = 0; i < array.length(); i++) {
				Long currentTime = array.getJSONObject(i).getLong("dt");
				if(isSaturdayFever(currentTime)) {
					if(begin) {
						begin = false;
						Temp=array.getJSONObject(i).getJSONObject("main").getDouble("temp");
					}
					else {
						Temptemp=array.getJSONObject(i).getJSONObject("main").getDouble("temp");
						if(Temptemp>=Temp) {
							Temp = Temptemp;
						}
					}
				}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return Temp;
		}
		
		public static String getMeteo(JSONArray array) {
			String result = "";
			try {
				for (int i = 0; i < array.length(); i++) {
				Long currentTime = array.getJSONObject(i).getLong("dt");
				if(isSaturdayFeverMiddle(currentTime)) {
					result=array.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main");
					
				}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}
		
		
		public static boolean isSaturdayFever(long unix) {
		    Calendar calendar = Calendar.getInstance();
		    calendar.setTimeInMillis(unix*1000);
		    //SSystem.out.println(calendar.getTime().toString());
		    if((calendar.get(Calendar.DAY_OF_WEEK)==Calendar.FRIDAY)
		    		&&((calendar.get(Calendar.HOUR_OF_DAY)>=9)
		    				&&(calendar.get(Calendar.HOUR_OF_DAY)<=21))) {
		    	return true;
		    }
		    else return false;
		}
		
		public static boolean isSaturdayFeverMiddle(long unix) {
		    Calendar calendar = Calendar.getInstance();
		    calendar.setTimeInMillis(unix*1000);
		    //SSystem.out.println(calendar.getTime().toString());
		    if((calendar.get(Calendar.DAY_OF_WEEK)==Calendar.FRIDAY)
		    		&&(calendar.get(Calendar.HOUR_OF_DAY)<=12)) {
		    	return true;
		    }
		    else return false;
		}
		

	}