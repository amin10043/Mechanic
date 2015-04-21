package com.project.mechanic;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageButton;

import com.project.mechanic.adapter.WelcomeScreenAdapter;

public class WelcomeScreen extends Activity {
	// int SPLASH_DISPLAY_TIME = 30000;
	// Handler handler;
	// Runnable runnable;

	private WelcomeScreenAdapter adapter;
	private GridView gridView;
	private int columnWidth;
	private int column = 3;
	private int gridePadding = 0;
	private ImageButton btnNext, btnExit, btnins1, btnint1, btngp1, btnfb1,
			btntw1, btnlink1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome_screen);

		initialize();
		cliclItem();
		//
		// handler = new Handler();
		// runnable = new Runnable() {
		// public void run() {
		//
		// Intent intent = new Intent();
		// intent.setClass(WelcomeScreen.this, MainActivity.class);
		//
		// startActivity(intent);
		// finish();
		//
		// overridePendingTransition(R.layout.splash_out,
		// R.layout.splash_in);
		//
		// }
		// };
		// handler.postDelayed(runnable, SPLASH_DISPLAY_TIME);

		gridView = (GridView) findViewById(R.id.grid_view);
		int[] image = { R.drawable.up2, R.drawable.on2, R.drawable.or2,
				R.drawable.g1, R.drawable.g2, R.drawable.g3, R.drawable.tayan,
				R.drawable.tayan, R.drawable.tayan, R.drawable.tayan,
				R.drawable.tayan, R.drawable.tayan, R.drawable.tayan,
				R.drawable.tayan, R.drawable.tayan, R.drawable.tayan,
				R.drawable.tayan, R.drawable.tayan, R.drawable.tayan,
				R.drawable.tayan, R.drawable.tayan, R.drawable.tayan,
				R.drawable.tayan, R.drawable.tayan, };

		Resources r = getResources();
		float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				gridePadding, r.getDisplayMetrics());

		columnWidth = (int) ((getScreenWidth() - ((column + 1) * padding)) / column);

		gridView.setNumColumns(column);
		gridView.setColumnWidth(columnWidth);
		gridView.setStretchMode(GridView.NO_STRETCH);
		gridView.setPadding((int) padding, (int) padding, (int) padding,
				(int) padding);
		gridView.setHorizontalSpacing((int) 0);
		gridView.setVerticalSpacing((int) 0);

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

	private void initialize() {

		btnNext = (ImageButton) findViewById(R.id.btnnext);
		btnExit = (ImageButton) findViewById(R.id.btnprev);

		btnins1 = (ImageButton) findViewById(R.id.btninstegram);
		btnint1 = (ImageButton) findViewById(R.id.btn_internet);
		btngp1 = (ImageButton) findViewById(R.id.btngplas);
		btnfb1 = (ImageButton) findViewById(R.id.btnfb);
		btntw1 = (ImageButton) findViewById(R.id.btntwitter);
		btnlink1 = (ImageButton) findViewById(R.id.btnlinking);

	} // end initialize

	private void cliclItem() {

		btnNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(WelcomeScreen.this, MainActivity.class);

				startActivity(intent);
				finish();

				overridePendingTransition(R.layout.splash_out,
						R.layout.splash_in);

			}
		});

		btnExit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				new AlertDialog.Builder(WelcomeScreen.this)
						.setTitle("خروج از برنامه")
						.setMessage("آیا از خروج اطمینان دارید؟")
						.setNegativeButton("خیر", null)
						.setPositiveButton("بله",
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface arg0,
											int arg1) {
										finish();
										System.exit(0);
									}
								}).create().show();

			}
		});

	} // end cliclItem

}
