package com.shank.weatherforecast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class AutoMode extends Activity{
	TextView tv_currentLocation,latlong,tv1,tv2;
	Button btn_find,btn_submit;
	static String city=null;
	double latitude=0.0,longitude=0.0;
	Boolean GPSEnabled=false;
	ProgressBar pb;
	int count=0;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.automode);
		tv_currentLocation=(TextView) findViewById(R.id.viewCurrentLocation);
		latlong=(TextView) findViewById(R.id.latitude_longitude);
		tv1=(TextView) findViewById(R.id.tv_Sublocality);
		tv2=(TextView) findViewById(R.id.tv_country_name);
		btn_find=(Button)findViewById(R.id.btn_find_location);
		btn_submit=(Button)findViewById(R.id.btn_submit);
		pb=(ProgressBar)findViewById(R.id.progressBar1);
		pb.setVisibility(View.INVISIBLE);
		final LocationManager locmanager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
		
		
		
		//clicking on find button
		btn_find.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				checkGPSstatus(locmanager);
				if(GPSEnabled){
					
					pb.setVisibility(View.VISIBLE);
					
					MyLocationlistener locationListener=new MyLocationlistener();
					//LocationManager locmanager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
					locmanager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000,5,locationListener);
					
						
					}
					else
					Toast.makeText(AutoMode.this,"Enable GPS and try again", Toast.LENGTH_LONG).show();
			}
		});
		
		//Clicking on submit
		btn_submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FetchWeather obj=new FetchWeather();
				
				if (city!=null ||latitude!=0.0) {
					pb.setVisibility(ProgressBar.VISIBLE);
					obj.getWeather(city, latitude,longitude);
					final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
					//checks if weather information has been obtained
					Runnable helloRunnable = new Runnable() {
						public void run() {
							count++;
							if (WeatherForecast.flag == 1) {
								executor.shutdown();
								Intent i = new Intent(AutoMode.this,
										WeatherForecast.class);
								startActivity(i);

							} else if (WeatherForecast.flag == 0) {
								Toast.makeText(
										AutoMode.this,
										"Sorry, Could not find weather of your place",
										Toast.LENGTH_LONG).show();
								executor.shutdown();
							}

							if (count == 9)
								Toast.makeText(AutoMode.this,
										"Sorry, Could not find weather",
										Toast.LENGTH_LONG).show();

						}
					};
					executor.scheduleAtFixedRate(helloRunnable, 0, 7,
							TimeUnit.SECONDS);
				}
				else
				Toast.makeText(AutoMode.this,"Click on Find my location and then click submit", Toast.LENGTH_LONG).show();

				
				
					
							
				
				
				/*Handler handler = new Handler();
				handler.postDelayed(new Runnable() {
				    public void run() {
				    	try{
							Intent i=new Intent(AutoMode.this,WeatherForecast.class);
							pb.setVisibility(ProgressBar.INVISIBLE);
							startActivity(i);
							}catch(Exception e){
								e.printStackTrace();
								Toast.makeText(AutoMode.this,"Sorry, Could not find weather of your place", Toast.LENGTH_LONG).show();
								}
				    }
				}, 10000);*/
				
				
				
			}
		});
	}
	
	
@Override
	protected void onResume() {
		pb.setVisibility(ProgressBar.INVISIBLE);
		super.onResume();
	}

//checking GPS status
public void checkGPSstatus(LocationManager locmanager) {
		
		/*ContentResolver contentResolver=getBaseContext().getContentResolver();
	    GPSEnabled=android.provider.Settings.Secure.isLocationProviderEnabled(contentResolver, LocationManager.GPS_PROVIDER); */
		GPSEnabled=locmanager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		}

private class MyLocationlistener implements LocationListener{

	@Override
	public void onLocationChanged(Location location) {
		pb.setVisibility(View.INVISIBLE);
		latitude=location.getLatitude();
		longitude=location.getLongitude();
		latlong.setText("Lat :"+Double.toString(latitude)+" Long:"+Double.toString(longitude));
		Geocoder gcoder=new Geocoder(getBaseContext(), Locale.getDefault());
		
		List<Address>address;
		
		try{
			address=gcoder.getFromLocation(latitude, longitude, 1);
			
			if(address.size()>0){
				
				city=address.get(0).getLocality();
				tv_currentLocation.setText(city);
				tv2.setText(address.get(0).getCountryName());
				tv1.setText(address.get(0).getSubLocality()+",");
				
			}
			
		} catch (IOException e){
			e.printStackTrace();
			Toast.makeText(AutoMode.this,"Sorry could not find Address, Click OK to find by Coordinates", Toast.LENGTH_LONG).show();	
		}
	}

	@Override
	public void onProviderDisabled(String provider) {
		
		Toast.makeText(AutoMode.this,"Enable GPS and try again", Toast.LENGTH_LONG).show();
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		
		
	}
	

}



/*public void loadWeatherforecast() {
	
	if(flag==1)
	{
		try{
		Intent i=new Intent(this.getApplicationContext(),WeatherForecast.class);
		i.putExtra("cityentered", city);
		startActivity(i);
		}catch(Exception e){
		Log.e("loadweatherforecast", "***************Could not load Weather Forecast activity******************",e);
		}
	}
	else{
Toast.makeText(AutoMode.this,"Sorry, Could not find weather of your place", Toast.LENGTH_LONG).show();
pb.setVisibility(ProgressBar.INVISIBLE);
	}

}*/

	
	
}