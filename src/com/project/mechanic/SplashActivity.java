package com.project.mechanic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;

public class SplashActivity extends Activity {

	int SPLASH_DISPLAY_TIME = 3000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_splash, menu);
		return true;
	}

}
