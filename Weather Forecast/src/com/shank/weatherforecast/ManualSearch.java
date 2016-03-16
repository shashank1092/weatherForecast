package com.shank.weatherforecast;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ManualSearch extends Activity{
	ImageButton btnAdd,btnRemove;
	Button btnSubmit;
	EditText cityname1;
	int i=0;
	List<EditText> allEds = new ArrayList<EditText>();
	boolean status=true;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manual_search);
		btnAdd=(ImageButton) findViewById(R.id.ib_addcity);
		btnRemove=(ImageButton)findViewById(R.id.ib_removecity);
		btnSubmit=(Button)findViewById(R.id.btn_submit);
		final LinearLayout ll=(LinearLayout) findViewById(R.id.linearlayout2);
		cityname1=(EditText)findViewById(R.id.et_city1);
		
		
		
		//Adding Edit text Dynamically for new city
		btnAdd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EditText et=new EditText(ManualSearch.this);
				allEds.add(et);
				et.setId(i);
				et.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				et.setHint("City name "+Integer.toString(i+2));
				et.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
				ll.addView(et);
				i++;
				
				
			}
		});
		
		//Removing an edit text
		btnRemove.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(i>0){
					((ViewGroup) allEds.get(i-1).getParent()).removeView(allEds.get(i-1));
					allEds.remove(i-1);
					i--;
					
					}
				
			}
		});
		
		//When user clicks on submit button
		btnSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CityList obj=new CityList();
				//initializing the list of cites to citynames string of CityList class
				status=true;
				obj.cityNames=new String[allEds.size()+1];
				
				if(!TextUtils.isEmpty(cityname1.getText().toString())){
					
					obj.cityNames[0]=cityname1.getText().toString();
					
					
				for(int i=0;i<allEds.size();i++){
					if(!TextUtils.isEmpty(allEds.get(i).getText().toString()))
						obj.cityNames[i+1]=allEds.get(i).getText().toString();
					else
						{
						status=false;
						Toast.makeText(ManualSearch.this, "Please Enter city names to all the fields or remove the extra field", Toast.LENGTH_LONG).show();
						break;
						}
				}
				if (status==true) {
					try {
						Intent i = new Intent(ManualSearch.this, CityList.class);
						i.putExtra("citylist", obj.cityNames);
						startActivity(i);
					} catch (Exception E) {
						E.printStackTrace();
					}
				}
				}
				else
				Toast.makeText(ManualSearch.this, "Please Enter city name", Toast.LENGTH_LONG).show();
				
			}
		});
		
	}

}
