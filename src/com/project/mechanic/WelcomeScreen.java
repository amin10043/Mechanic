package com.project.mechanic;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class WelcomeScreen extends Activity {
	int SPLASH_DISPLAY_TIME = 1000;
	Handler handler;
	Runnable runnable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome_screen);

		handler = new Handler();
		runnable = new Runnable() {
			public void run() {

				Intent intent = new Intent();
				intent.setClass(WelcomeScreen.this, MainActivity.class);

				startActivity(intent);
				finish();

				overridePendingTransition(R.layout.splash_out,
						R.layout.splash_in);

			}
		};
		handler.postDelayed(runnable, SPLASH_DISPLAY_TIME);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_welcome_screen, menu);
		return true;
	}

}
