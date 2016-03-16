package com.shank.weatherforecast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class CityList extends Activity{
	

	String [] cityNames;
	ListView lv_cityList;
	int []image;
	ProgressBar pb;
	int count=0;
	
	
	@Override
	protected void onResume() {
		pb.setVisibility(ProgressBar.INVISIBLE);
		super.onResume();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.city_list);
		
		lv_cityList=(ListView) findViewById(R.id.lv_citylist);
		pb=(ProgressBar)findViewById(R.id.progressBar1);
		pb.setVisibility(ProgressBar.INVISIBLE);
		List<HashMap<String,String>> cityList = new ArrayList<HashMap<String,String>>();
		Intent intent=getIntent();
		cityNames=intent.getStringArrayExtra("citylist");
		image=new int[cityNames.length];
		
		for(int i=0;i<cityNames.length;i++){
			image[i]=R.drawable.arrowfinal2;
			HashMap<String, String> hm = new HashMap<String,String>();
			 hm.put("cityname",cityNames[i]);
	         hm.put("image", Integer.toString(image[i]) );
	         cityList.add(hm);
			
		}
		
		// Keys used in Hashmap
        String[] from = { "image","cityname"};
 
        // Ids of views in listview_layout
        int[] to = { R.id.image,R.id.txt1};
		
		SimpleAdapter adapter=new SimpleAdapter(getBaseContext(), cityList, R.layout.city_listview_layout, from, to);
		lv_cityList.setAdapter(adapter);
		
		lv_cityList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int pos,long id) {
				
				pb.setVisibility(ProgressBar.VISIBLE);
				HashMap<String,String> map =(HashMap<String,String>)lv_cityList.getItemAtPosition(pos);
				String city=map.get("cityname");
				FetchWeather obj=new FetchWeather();
				obj.getWeather(city, 0, 0);
				final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
				
				
				//checks if weather information has been obtained
				Runnable helloRunnable = new Runnable() {
				    public void run() {
				    	count++;
				    	if(WeatherForecast.flag==1){
				    		executor.shutdown();
				    		//pb.setVisibility(ProgressBar.INVISIBLE);
							Intent i=new Intent(CityList.this,WeatherForecast.class);
							startActivity(i);
							
						}
				    	else if(WeatherForecast.flag==0){
				    		Toast.makeText(CityList.this,"Sorry, Could not find weather of your place", Toast.LENGTH_LONG).show();
				    		executor.shutdown();
				    	}
				    	if(count==9)
				    		Toast.makeText(CityList.this,"Sorry, Could not find weather", Toast.LENGTH_LONG).show();
				        
				    }
				};
				
				executor.scheduleAtFixedRate(helloRunnable, 0, 7, TimeUnit.SECONDS);
				
			}
		});
		
		
	}

}
