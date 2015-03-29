package com.project.mechanic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class SplashActivity extends Activity {

	int SPLASH_DISPLAY_TIME = 3000;
	Handler handler;
	Runnable runnable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		ImageButton btnfb1 = (ImageButton) findViewById(R.id.btnfb);
		ImageButton btngp1 = (ImageButton) findViewById(R.id.btngplas);
		ImageButton btnins1 = (ImageButton) findViewById(R.id.btninstegram);
		ImageButton btnlink1 = (ImageButton) findViewById(R.id.btnlinking);
		ImageButton btnyout1 = (ImageButton) findViewById(R.id.btnytube);
		ImageButton btntw1 = (ImageButton) findViewById(R.id.btntwitter);

		btnfb1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				/*
				 * Intent i = new Intent(getBaseContext(), OtherActivity.class);
				 * startActivity(i);
				 */

			}
		});

		btngp1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				/*
				 * Intent i = new Intent(getBaseContext(), OtherActivity.class);
				 * startActivity(i);
				 */

			}
		});
		btnins1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				/*
				 * Intent i = new Intent(getBaseContext(), OtherActivity.class);
				 * startActivity(i);
				 */

			}
		});
		btnlink1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				/*
				 * Intent i = new Intent(getBaseContext(), OtherActivity.class);
				 * startActivity(i);
				 */

			}
		});
		btnyout1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				/*
				 * Intent i = new Intent(getBaseContext(), string[]);
				 * startActivity(i);
				 */

			}
		});
		btntw1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

		handler = new Handler();
		runnable = new Runnable() {
			public void run() {

				Intent intent = new Intent();
				intent.setClass(SplashActivity.this, MainActivity.class);

				startActivity(intent);
				finish();

				overridePendingTransition(R.layout.splash_out,
						R.layout.splash_in);

			}
		};
		handler.postDelayed(runnable, SPLASH_DISPLAY_TIME);

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			handler.removeCallbacks(runnable);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

}
