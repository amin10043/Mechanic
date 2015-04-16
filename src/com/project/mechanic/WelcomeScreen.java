package com.project.mechanic;


import com.project.mechanic.adapter.WelcomeScreenAdapter;

import android.os.Bundle;
import android.os.Handler;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.TypedValue;
import android.view.Display;
import android.view.Menu;
import android.view.WindowManager;
import android.widget.GridView;

public class WelcomeScreen extends Activity {
	int SPLASH_DISPLAY_TIME = 2000;
	Handler handler;
	Runnable runnable;

	private WelcomeScreenAdapter adapter;
	private GridView gridView;
	private int columnWidth;
	private int column = 3;
	int gridePadding = 8;

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

		gridView = (GridView) findViewById(R.id.grid_view);
		int[] image = { R.drawable.tayan, R.drawable.tayan,
				R.drawable.tayan, R.drawable.tayan,
				R.drawable.tayan, R.drawable.tayan,
				R.drawable.tayan, R.drawable.tayan,
				R.drawable.tayan, R.drawable.tayan,
				R.drawable.tayan, R.drawable.tayan,
				R.drawable.tayan, };
		Resources r = getResources();
		float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				gridePadding, r.getDisplayMetrics());

		columnWidth = (int) ((getScreenWidth() - ((column + 1) * padding)) / column);

		gridView.setNumColumns(column);
		gridView.setColumnWidth(columnWidth);
		gridView.setStretchMode(GridView.NO_STRETCH);
		gridView.setPadding((int) padding, (int) padding, (int) padding,
				(int) padding);
		gridView.setHorizontalSpacing((int) padding);
		gridView.setVerticalSpacing((int) padding);

		gridView.setAdapter(new WelcomeScreenAdapter(this, image, columnWidth));

	}

	@SuppressLint("NewApi")
	public int getScreenWidth() {
		int columnWidth;
		WindowManager wm = (WindowManager) this
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();

		final Point point = new Point();
		try {
			display.getSize(point);
		} catch (java.lang.NoSuchMethodError ignore) { // Older device
			point.x = display.getWidth();
			point.y = display.getHeight();
		}
		columnWidth = point.x;
		return columnWidth;

	}

	}


