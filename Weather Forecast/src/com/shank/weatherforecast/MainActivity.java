package com.shank.weatherforecast;

import android.os.Bundle;
import android.app.TabActivity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MainActivity extends TabActivity {
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		TabHost th=getTabHost();
		
		Intent autoIntent=new Intent(MainActivity.this,AutoMode.class);
		TabSpec tabspecAuto=th.newTabSpec("Auto");
		tabspecAuto.setIndicator("Auto");
		tabspecAuto.setContent(autoIntent);
		
		Intent manualIntent=new Intent(MainActivity.this,ManualSearch.class);
		TabSpec tabspecManual=th.newTabSpec("Manual");
		tabspecManual.setIndicator("Manual");
		tabspecManual.setContent(manualIntent);
		
		th.addTab(tabspecAuto);
		th.addTab(tabspecManual);
		th.setCurrentTab(0);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
