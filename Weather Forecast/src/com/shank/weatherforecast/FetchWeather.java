package com.shank.weatherforecast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.media.audiofx.Equalizer;
import android.os.AsyncTask;
import android.util.Log;
import android.webkit.DateSorter;

public class FetchWeather {
	WeatherForecast obj=new WeatherForecast();
	static int cod;
	static JSONObject data;
	
	public static JSONObject getJSON(String[] params){
		String openweathermap_url;
        try {
        if(params[0]!=null){
        	String city=params[0].replace(" ", "%20");
        openweathermap_url=new StringBuilder("http://api.openweathermap.org/data/2.5/forecast/daily?q=").append(city).append("&mode=json&units=metric&cnt=14&APPID=982ab0d5554322cf10fe45444379fd99").toString();
        }
        else{
        	String lat=params[1];
        	String lon=params[2];
        	openweathermap_url=new StringBuilder("http://api.openweathermap.org/data/2.5/forecast/daily?lat=").append(lat+"&lon=").append(lon).append("&mode=json&units=metric&cnt=14&APPID=982ab0d5554322cf10fe45444379fd99").toString();
        }
        URL url=new URL(openweathermap_url);
        HttpURLConnection connection=(HttpURLConnection) url.openConnection();
        
        BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
        
        StringBuffer json=new StringBuffer(1024);
        String tmp=" ";
        while((tmp=reader.readLine())!=null)
            json.append(tmp).append("\n");
        
        reader.close();
        data=new JSONObject(json.toString());
        cod=data.getInt("cod");
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        	Log.e("getJSON","***************Could not get JSON Object***********",e);
        }
        
        if(cod!=200)
        {
        	
        	return null;
        }
        else{
        System.out.println("found Data");
        return data;
        }
     }
	
	
	//calls the getJSON method of FetchWeather class to get the response from server
		public void getWeather(String city, double latitude, double longitude){
			
			String params[]={city,Double.toString(latitude),Double.toString(longitude)};
			new WeatherAsynctask().execute(params);
			
			}
	
		//Getting weather informations from the json object
		 void fetchWeatherInformations(JSONObject json) {
			try {
				
		JSONArray jArrList=json.getJSONArray("list");
		obj.dates=new String[jArrList.length()];
		obj.description=new String[jArrList.length()];
		obj.iConList=new String[jArrList.length()];
		obj.minMax=new String[jArrList.length()];
		for(int i=0;i<jArrList.length();i++){
			
			
			JSONObject jsonObject=jArrList.getJSONObject(i);
			//System.out.println(jsonObject.getLong("dt"));
			
			JSONArray jArrWeather=jsonObject.getJSONArray("weather");
			JSONObject jsonWeatherObject=jArrWeather.getJSONObject(0);
			//JSONObject jsonTempObject=jsonObject.getJSONObject("temp");
			
			
			obj.dates[i]=new SimpleDateFormat("EEE-dd-MMM-yyyy").format(new Date(jsonObject.getLong("dt")*1000));
			//System.out.println(obj.dates[i]);
			obj.description[i]=jsonWeatherObject.getString("description");
			//System.out.println(obj.description[i]);
			obj.iConList[i]=jsonWeatherObject.getString("icon");
			//System.out.println(obj.iConList[i]);
			obj.minMax[i]="Min: "+Double.toString(jsonObject.getJSONObject("temp").getDouble("min"))+" ℃     Max: "+Double.toString(jsonObject.getJSONObject("temp").getDouble("max"))+" ℃";
		}
		
		getiConList(obj);
		
		obj.cityName=json.getJSONObject("city").getString("name")+", "+json.getJSONObject("city").getString("country");
		obj.temperature=Double.toString(json.getJSONArray("list").getJSONObject(0).getJSONObject("temp").getDouble("day"));
		obj.skyDescription=json.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("description");
		WeatherForecast.flag=1;
		 
		 
			}
			catch(Exception e){
		        Log.e("fetchWeatherInformations", "************One or more fields not found in the JSON data*************",e);
		        WeatherForecast.flag=0;
		    }
			
			
		}
		 
		 public static void getiConList(WeatherForecast obj)
			{
				obj.icons=new int[obj.iConList.length];
				String s []=new String[]{"01d","01n","02d","02n","03d","03n","04d","04n","09d","09n","10d","10n","11d","11n","13d","13n","50d","50n"}; 
				
			 for(int i=0;i<obj.iConList.length;i++)
				{
					for(int j=0;j<18;j++)
					{
					if(obj.iConList[i].equalsIgnoreCase(s[j])){
						
						switch (j) {
						case 0:obj.icons[i]=R.drawable.image_01d;
							break;
						case 1:obj.icons[i]=R.drawable.image_01n;
							break;
						case 2:obj.icons[i]=R.drawable.image_02d;
							break;
						case 3:obj.icons[i]=R.drawable.image_02n;
							break;
						case 4:obj.icons[i]=R.drawable.image_03d;
							break;
						case 5:obj.icons[i]=R.drawable.image_03n;
							break;
						case 6:obj.icons[i]=R.drawable.image_04d;
							break;
						case 7:obj.icons[i]=R.drawable.image_04n;
							break;
						case 8:obj.icons[i]=R.drawable.image_09d;
							break;
						case 9:obj.icons[i]=R.drawable.image_09n;
							break;
						case 10:obj.icons[i]=R.drawable.image_10d;
							break;
						case 11:obj.icons[i]=R.drawable.image_10n;
							break;
						case 12:obj.icons[i]=R.drawable.image_11d;
							break;
						case 13:obj.icons[i]=R.drawable.image_11n;
							break;
						case 14:obj.icons[i]=R.drawable.image_13d;
							break;
						case 15:obj.icons[i]=R.drawable.image_13n;
							break;
						case 16:obj.icons[i]=R.drawable.image_50d;
							break;
						case 17:obj.icons[i]=R.drawable.image_50n;
							break;
						}
						break;
					}
					}
				}
			}
		 class WeatherAsynctask extends AsyncTask<String, Void, String>{

				@Override
				protected void onPostExecute(String result) {
					try {
					
						fetchWeatherInformations(new JSONObject(result));
						
						
					} catch (Exception e) {
						
						e.printStackTrace();
						
						Log.e("fetchWeatherInformations(jsonobject)", "************could not fetch weather information from the Json Object",e);
					}
					super.onPostExecute(result);
				}

				@Override
				protected String doInBackground(String... params) {
					
					JSONObject jsonobject=getJSON(params);
					if(jsonobject==null){
					return null;
					}
					else
					return jsonobject.toString();
					
				}
				
				
				
				
			}
        
	}



      
