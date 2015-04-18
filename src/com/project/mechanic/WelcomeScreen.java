package com.project.mechanic;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListAdapter;

import com.project.mechanic.adapter.WelcomeScreenAdapter;

public class WelcomeScreen extends Activity {
	int SPLASH_DISPLAY_TIME = 1000;
	Handler handler;
	Runnable runnable;

	private WelcomeScreenAdapter adapter;
	private GridView gridView;
	private int columnWidth;
	private int column = 3;
	int gridePadding = 0;
	private LinearLayout ln_row1, ln_row2;
	private ImageButton img1, img2;

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
		int[] image = { R.drawable.tayan, R.drawable.tayan, R.drawable.tayan,
				R.drawable.tayan, R.drawable.tayan, R.drawable.tayan,
				R.drawable.tayan, R.drawable.tayan, R.drawable.tayan,
				R.drawable.tayan, R.drawable.tayan, R.drawable.tayan,
				R.drawable.tayan, R.drawable.tayan, R.drawable.tayan,
				R.drawable.tayan, R.drawable.tayan, R.drawable.tayan, };

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

		// ln_row1 = new LinearLayout(this);
		// ln_row1.setOrientation(LinearLayout.HORIZONTAL);
		// // LayoutParams linLayoutParam = new
		// LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		//
		// img1 = new ImageButton(this);
		// img2 = new ImageButton(this);
		//
		// img1.setBackgroundResource(R.drawable.like);
		// img2.setBackgroundResource(R.drawable.save);
		//
		// ln_row1.addView(img1);
		// ln_row1.addView(img2);
		//
		// gridView.addView(ln_row1);

		gridView.setAdapter(new WelcomeScreenAdapter(this, image, columnWidth));
		resizeGridView(gridView);

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

	private void resizeGridView(final GridView gridview) {
		ListAdapter listAdapter = gridview.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = gridview.getPaddingTop()
				+ gridview.getPaddingBottom();
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, gridview);

			if (listItem instanceof ViewGroup) {
				listItem.setLayoutParams(new LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			}
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = gridview.getLayoutParams();
		params.height = totalHeight
				+ (gridview.getHeight() * (listAdapter.getCount() - 1));
		gridview.setLayoutParams(params);
	}

}
