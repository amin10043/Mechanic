package com.project.mechanic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SplashActivity extends Activity {

	int SPLASH_DISPLAY_TIME = 3000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		
		
		Button btnfb1=(Button) findViewById(R.id.btnfb);
		Button btngp2=(Button) findViewById(R.id.btngplas);
		Button btnlink3=(Button) findViewById(R.id.btnlinking);
		Button btnin4=(Button) findViewById(R.id.btninstegram);
		Button btnyt5=(Button) findViewById(R.id.btnytube);
		Button btntw6=(Button) findViewById(R.id.btntwitter);
		Button btnnext7=(Button) findViewById(R.id.btnnext);
		Button btnprev8=(Button) findViewById(R.id.btnprev);
		btnfb1.setOnClickListener(new OnClickListener() {
			
		
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		btngp2.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		btnlink3.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		btnin4.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		btnyt5.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		btntw6.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		btnnext7.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		btnprev8.setOnClickListener(new OnClickListener() {
	
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
});

		new Handler().postDelayed(new Runnable() {
			public void run() {

				Intent intent = new Intent();
				intent.setClass(SplashActivity.this, MainActivity.class);

				startActivity(intent);
				finish();

				overridePendingTransition(R.layout.splash_out,
						R.layout.splash_in);

			}
		}, SPLASH_DISPLAY_TIME);

	}

	
	

}
