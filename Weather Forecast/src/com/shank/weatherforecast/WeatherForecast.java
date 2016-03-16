package com.shank.weatherforecast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class WeatherForecast extends Activity{
	

	TextView tv_cityName,tv_temperature,tv_skyDescription;
	static ListView listview;
	static String description[],dates[],iConList[],minMax[];
	static String cityName,temperature,skyDescription;
	//static List<HashMap<String,String>> aList;
	static int icons[];
	static int flag=-1;
	
	@Override
	protected void onPause() {
		flag=-1;
		super.onPause();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weather_forecast);
		
		List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();
		
		tv_cityName=(TextView) findViewById(R.id.tv_cityName);
		tv_temperature=(TextView) findViewById(R.id.tv_temp);
		tv_skyDescription=(TextView)findViewById(R.id.tv_skydesc);
		ImageView weatherConditionIcon=(ImageView)findViewById(R.id.iv_condIcon);
		
		listview=(ListView) findViewById(R.id.lv_weather_forecast);
		//df = DateFormat.getDateTimeInstance();
		
		for(int i=0;i<description.length;i++){
            HashMap<String, String> hm = new HashMap<String,String>();
            hm.put("date",dates[i]);
            hm.put("description", description[i]);
            hm.put("icons", Integer.toString(icons[i]));
            hm.put("minmax", minMax[i]);
            aList.add(hm);
        }
		//aList = new ArrayList<HashMap<String,String>>();
		String[] from = { "date","description","icons","minmax"};
		int[] to = { R.id.date,R.id.weather_condition,R.id.weather_icon,R.id.min_max_temp};
		SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.listview_layout, from, to);
		//Calling setData method to set the data
		//setData();
		tv_cityName.setText(cityName);
		tv_skyDescription.setText(skyDescription);
		tv_temperature.setText(temperature);
		
		weatherConditionIcon.setImageResource(icons[0]);
		try{
		listview.setAdapter(adapter);
		}catch(Exception e){
			e.printStackTrace();}
		
		 
		
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.main, menu);
	return true;
	}
	
		
	
	/*public void setData(JSONObject json, List<HashMap<String, String>> aListTemp, HashMap<String, String> hm2) {
		try {
			aList=aListTemp;
			cityName=json.getJSONObject("city").getString("name")+", "+json.getJSONObject("city").getString("country");
			temperature=Double.toString(json.getJSONArray("list").getJSONObject(0).getJSONObject("temp").getDouble("day"));
			skyDescription=json.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("description");
			listview.setAdapter(adapter);
			
			
			
		} catch (JSONException e) {
			
			e.printStackTrace();
		} 
		
	}*/

}

 