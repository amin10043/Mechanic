package com.project.mechanic;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mechanic.entity.Settings;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class WelcomeScreen extends Activity {

	private LinearLayout verticalOuterLayout;
	// private ImageButton btnNext, btnExit, btnins1, btnint1, btngp1, btnfb1,
	// btntw1, btnlink1;

	LinearLayout row1, row2, row3, row4, row5, row6, row7, row8, row9, row10,
			row_Displacement, row_network;
	ImageButton img1, img2, img3, img4, img5, img6, img7, img8, img9, img10,
			img11, img12, img13, img14, img15, img16, img17, img18, img19,
			img20, img21, img22, img23, img24, img25, img26, img27, img28,
			img29, img30, next_btn, pre_btn, i1, i2, i3, i4, i5, i6;
	private int column = 3;
	int gridePadding = 1;
	private int columnWidth;
	private ScrollView verticalScrollview;
	private TextView verticalTextView;
	private int verticalScrollMax;
	private Timer scrollTimer = null;
	private TimerTask clickSchedule;
	private TimerTask scrollerSchedule;
	private TimerTask faceAnimationSchedule;
	private int scrollPos = 0;
	private Timer clickTimer = null;
	private Timer faceTimer = null;
	private Button clickedButton = null;

	private Utility util;
	private DataBaseAdapter adapter;
	Settings settings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_welcome_screen);

		SharedPreferences pref = getSharedPreferences("update", 0);
		SharedPreferences.Editor editor = pref.edit();
		editor.putInt("fromM", 0);
		editor.putInt("toM", 4);
		editor.putInt("fromD", 0);
		editor.putInt("toD", 4);
		editor.commit();

		adapter = new DataBaseAdapter(this);
		util = new Utility(this);

		adapter.open();
		settings = adapter.getSettings();
		adapter.close();

		// util.Updating();

		initialize();
		clickItem();

		columnWidth = (int) (getScreenWidth() / column);

		initialize();
		setBackground();
		setMinMax();
		Adding();
		setParams();
		clickItem();

		verticalOuterLayout = (LinearLayout) findViewById(R.id.vertical_outer_layout_id);
		verticalScrollview = (ScrollView) findViewById(R.id.vertical_scrollview_id);
		ViewTreeObserver vto = verticalOuterLayout.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void onGlobalLayout() {
				verticalOuterLayout.getViewTreeObserver()
						.removeGlobalOnLayoutListener(this);
				getScrollMaxAmount();
				startAutoScrolling();
			}
		});

	}

	@SuppressWarnings("deprecation")
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
		row1 = (LinearLayout) findViewById(R.id.row1_linear);
		row2 = (LinearLayout) findViewById(R.id.row2_linear);
		row3 = (LinearLayout) findViewById(R.id.row3_linear);
		row4 = (LinearLayout) findViewById(R.id.row4_linear);
		row5 = (LinearLayout) findViewById(R.id.row5_linear);
		row6 = (LinearLayout) findViewById(R.id.row6_linear);
		row7 = (LinearLayout) findViewById(R.id.row7_linear);
		row8 = (LinearLayout) findViewById(R.id.row8_linear);
		row9 = (LinearLayout) findViewById(R.id.row9_linear);
		row10 = (LinearLayout) findViewById(R.id.row10_linear);

		row_Displacement = (LinearLayout) findViewById(R.id.row_Displacement);
		row_network = (LinearLayout) findViewById(R.id.row_network);

		img1 = new ImageButton(WelcomeScreen.this);
		img2 = new ImageButton(WelcomeScreen.this);
		img3 = new ImageButton(WelcomeScreen.this);

		img4 = new ImageButton(WelcomeScreen.this);
		img5 = new ImageButton(WelcomeScreen.this);
		img6 = new ImageButton(WelcomeScreen.this);

		img7 = new ImageButton(WelcomeScreen.this);
		img8 = new ImageButton(WelcomeScreen.this);
		img9 = new ImageButton(WelcomeScreen.this);

		img10 = new ImageButton(WelcomeScreen.this);
		img11 = new ImageButton(WelcomeScreen.this);
		img12 = new ImageButton(WelcomeScreen.this);

		img13 = new ImageButton(WelcomeScreen.this);
		img14 = new ImageButton(WelcomeScreen.this);
		img15 = new ImageButton(WelcomeScreen.this);

		img16 = new ImageButton(WelcomeScreen.this);
		img17 = new ImageButton(WelcomeScreen.this);
		img18 = new ImageButton(WelcomeScreen.this);

		img19 = new ImageButton(WelcomeScreen.this);
		img20 = new ImageButton(WelcomeScreen.this);
		img21 = new ImageButton(WelcomeScreen.this);

		img22 = new ImageButton(WelcomeScreen.this);
		img23 = new ImageButton(WelcomeScreen.this);
		img24 = new ImageButton(WelcomeScreen.this);

		img25 = new ImageButton(WelcomeScreen.this);
		img26 = new ImageButton(WelcomeScreen.this);
		img27 = new ImageButton(WelcomeScreen.this);

		img28 = new ImageButton(WelcomeScreen.this);
		img29 = new ImageButton(WelcomeScreen.this);
		img30 = new ImageButton(WelcomeScreen.this);

		next_btn = new ImageButton(WelcomeScreen.this);
		pre_btn = new ImageButton(WelcomeScreen.this);

		i1 = new ImageButton(WelcomeScreen.this);
		i2 = new ImageButton(WelcomeScreen.this);
		i3 = new ImageButton(WelcomeScreen.this);
		i4 = new ImageButton(WelcomeScreen.this);
		i5 = new ImageButton(WelcomeScreen.this);
		i6 = new ImageButton(WelcomeScreen.this);

	}

	private void setBackground() {
		img1.setBackgroundResource(R.drawable.up2);
		img2.setBackgroundResource(R.drawable.on2);
		img3.setBackgroundResource(R.drawable.or2);

		img4.setBackgroundResource(R.drawable.up1);
		img5.setBackgroundResource(R.drawable.on1);
		img6.setBackgroundResource(R.drawable.or1);

		img7.setBackgroundResource(R.drawable.up1);
		img8.setBackgroundResource(R.drawable.on1);
		img9.setBackgroundResource(R.drawable.or1);

		img10.setBackgroundResource(R.drawable.up2);
		img11.setBackgroundResource(R.drawable.on2);
		img12.setBackgroundResource(R.drawable.or2);

		img13.setBackgroundResource(R.drawable.up1);
		img14.setBackgroundResource(R.drawable.on1);
		img15.setBackgroundResource(R.drawable.or1);

		img16.setBackgroundResource(R.drawable.up2);
		img17.setBackgroundResource(R.drawable.on2);
		img18.setBackgroundResource(R.drawable.or2);

		img19.setBackgroundResource(R.drawable.up1);
		img20.setBackgroundResource(R.drawable.on1);
		img21.setBackgroundResource(R.drawable.or1);

		img22.setBackgroundResource(R.drawable.up2);
		img23.setBackgroundResource(R.drawable.on2);
		img24.setBackgroundResource(R.drawable.or2);

		img25.setBackgroundResource(R.drawable.up2);
		img26.setBackgroundResource(R.drawable.on2);
		img27.setBackgroundResource(R.drawable.or2);

		img28.setBackgroundResource(R.drawable.up2);
		img29.setBackgroundResource(R.drawable.on2);
		img30.setBackgroundResource(R.drawable.or2);

		next_btn.setBackgroundResource(R.drawable.next);
		pre_btn.setBackgroundResource(R.drawable.prev);

		i1.setBackgroundResource(R.drawable.i4);
		i2.setBackgroundResource(R.drawable.i6);
		i3.setBackgroundResource(R.drawable.i5);
		i4.setBackgroundResource(R.drawable.i2);
		i5.setBackgroundResource(R.drawable.i3);
		i6.setBackgroundResource(R.drawable.i1);

	}

	private void setMinMax() {
		img1.setMinimumHeight(columnWidth);
		img1.setMinimumWidth(columnWidth);

		img2.setMinimumHeight(columnWidth);
		img2.setMinimumWidth(columnWidth);

		img3.setMinimumHeight(columnWidth);
		img3.setMinimumWidth(columnWidth);

		img4.setMinimumHeight(columnWidth);
		img4.setMinimumWidth(columnWidth);

		img5.setMinimumHeight(columnWidth);
		img5.setMinimumWidth(columnWidth);

		img6.setMinimumHeight(columnWidth);
		img6.setMinimumWidth(columnWidth);

		img7.setMinimumHeight(columnWidth);
		img7.setMinimumWidth(columnWidth);

		img8.setMinimumHeight(columnWidth);
		img8.setMinimumWidth(columnWidth);

		img9.setMinimumHeight(columnWidth);
		img9.setMinimumWidth(columnWidth);

		img10.setMinimumHeight(columnWidth);
		img10.setMinimumWidth(columnWidth);

		img11.setMinimumHeight(columnWidth);
		img11.setMinimumWidth(columnWidth);

		img12.setMinimumHeight(columnWidth);
		img12.setMinimumWidth(columnWidth);

		img13.setMinimumHeight(columnWidth);
		img13.setMinimumWidth(columnWidth);

		img14.setMinimumHeight(columnWidth);
		img14.setMinimumWidth(columnWidth);

		img15.setMinimumHeight(columnWidth);
		img15.setMinimumWidth(columnWidth);

		img16.setMinimumHeight(columnWidth);
		img16.setMinimumWidth(columnWidth);

		img17.setMinimumHeight(columnWidth);
		img17.setMinimumWidth(columnWidth);

		img18.setMinimumHeight(columnWidth);
		img18.setMinimumWidth(columnWidth);

		img19.setMinimumHeight(columnWidth);
		img19.setMinimumWidth(columnWidth);

		img20.setMinimumHeight(columnWidth);
		img20.setMinimumWidth(columnWidth);

		img21.setMinimumHeight(columnWidth);
		img21.setMinimumWidth(columnWidth);

		img22.setMinimumHeight(columnWidth);
		img22.setMinimumWidth(columnWidth);

		img23.setMinimumHeight(columnWidth);
		img23.setMinimumWidth(columnWidth);

		img24.setMinimumHeight(columnWidth);
		img24.setMinimumWidth(columnWidth);

		img25.setMinimumHeight(columnWidth);
		img25.setMinimumWidth(columnWidth);

		img26.setMinimumHeight(columnWidth);
		img26.setMinimumWidth(columnWidth);

		img27.setMinimumHeight(columnWidth);
		img27.setMinimumWidth(columnWidth);

		img28.setMinimumHeight(columnWidth);
		img28.setMinimumWidth(columnWidth);

		img29.setMinimumHeight(columnWidth);
		img29.setMinimumWidth(columnWidth);

		img30.setMinimumHeight(columnWidth);
		img30.setMinimumWidth(columnWidth);

	}

	private void Adding() {

		row1.addView(img1);
		row1.addView(img2);
		row1.addView(img3);

		row2.addView(img4);
		row2.addView(img5);
		row2.addView(img6);

		row3.addView(img7);
		row3.addView(img8);
		row3.addView(img9);

		row4.addView(img10);
		row4.addView(img11);
		row4.addView(img12);

		row5.addView(img13);
		row5.addView(img14);
		row5.addView(img15);

		row6.addView(img16);
		row6.addView(img17);
		row6.addView(img18);

		row7.addView(img19);
		row7.addView(img20);
		row7.addView(img21);

		row8.addView(img22);
		row8.addView(img23);
		row8.addView(img24);

		row9.addView(img25);
		row9.addView(img26);
		row9.addView(img27);

		row10.addView(img28);
		row10.addView(img29);
		row10.addView(img30);

		row_Displacement.addView(pre_btn);
		row_Displacement.addView(next_btn);

		row_network.addView(i1);
		row_network.addView(i2);
		row_network.addView(i3);
		row_network.addView(i4);
		row_network.addView(i5);
		row_network.addView(i6);

	}

	private void setParams() {

		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				row1.getLayoutParams());
		lp.width = columnWidth;
		lp.height = columnWidth;
		lp.setMargins(1, 1, 1, 1);

		img1.setLayoutParams(lp);
		img2.setLayoutParams(lp);
		img3.setLayoutParams(lp);

		img4.setLayoutParams(lp);
		img5.setLayoutParams(lp);
		img6.setLayoutParams(lp);

		img7.setLayoutParams(lp);
		img8.setLayoutParams(lp);
		img9.setLayoutParams(lp);

		img10.setLayoutParams(lp);
		img11.setLayoutParams(lp);
		img12.setLayoutParams(lp);

		img13.setLayoutParams(lp);
		img14.setLayoutParams(lp);
		img15.setLayoutParams(lp);

		img16.setLayoutParams(lp);
		img17.setLayoutParams(lp);
		img18.setLayoutParams(lp);

		img19.setLayoutParams(lp);
		img20.setLayoutParams(lp);
		img21.setLayoutParams(lp);

		img22.setLayoutParams(lp);
		img23.setLayoutParams(lp);
		img24.setLayoutParams(lp);

		img25.setLayoutParams(lp);
		img26.setLayoutParams(lp);
		img27.setLayoutParams(lp);

		img28.setLayoutParams(lp);
		img29.setLayoutParams(lp);
		img30.setLayoutParams(lp);

		LinearLayout.LayoutParams ip = new LinearLayout.LayoutParams(
				row_network.getLayoutParams());

		ip.height = columnWidth / 2;
		ip.setMargins(3, 0, 1, 0);

		row_Displacement.setLayoutParams(ip);
		row_network.setLayoutParams(ip);

		LinearLayout.LayoutParams dd = new LinearLayout.LayoutParams(
				row1.getLayoutParams());

		dd.width = 3 * columnWidth / 2;
		dd.height = columnWidth / 2;
		next_btn.setLayoutParams(dd);
		pre_btn.setLayoutParams(dd);

		LinearLayout.LayoutParams np = new LinearLayout.LayoutParams(
				row1.getLayoutParams());
		np.height = columnWidth / 2;
		np.width = 3 * (columnWidth - 2) / 6;

		i1.setLayoutParams(np);
		i2.setLayoutParams(np);
		i3.setLayoutParams(np);
		i4.setLayoutParams(np);
		i5.setLayoutParams(np);
		i6.setLayoutParams(np);

	}

	private void clickItem() {

		next_btn.setOnClickListener(new OnClickListener() {

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

		pre_btn.setOnClickListener(new OnClickListener() {

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

	}

	// ////////////////////////////////////////////////////////////////////////////////////
	public void getScrollMaxAmount() {
		// int actualWidth = (verticalOuterLayout.getMeasuredHeight() - (256 *
		// 3));
		int actualWidth = (verticalOuterLayout.getMeasuredHeight());
		verticalScrollMax = actualWidth;
		// verticalScrollMax = verticalScrollview.getHeight();
		// verticalScrollMax = new Utility(this).getScreenHeight();
	}

	public void startAutoScrolling() {
		if (scrollTimer == null) {
			scrollTimer = new Timer();
			final Runnable Timer_Tick = new Runnable() {
				public void run() {
					moveScrollView();
				}
			};

			if (scrollerSchedule != null) {
				scrollerSchedule.cancel();
				scrollerSchedule = null;
			}
			scrollerSchedule = new TimerTask() {
				@Override
				public void run() {
					runOnUiThread(Timer_Tick);
				}
			};

			scrollTimer.schedule(scrollerSchedule, 30, 15);
		}
	}

	public void moveScrollView() {
		scrollPos = (int) (verticalScrollview.getScrollY() + 1.0);
		if (scrollPos >= verticalScrollMax) {
			scrollPos = 0;
		} else {
			verticalScrollview.scrollTo(0, scrollPos);
			// Log.e("moveScrollView", "moveScrollView");
		}
	}

	public void stopAutoScrolling() {
		if (scrollTimer != null) {
			scrollTimer.cancel();
			scrollTimer = null;
		}
	}

	public Animation scaleFaceUpAnimation() {
		Animation scaleFace = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		scaleFace.setDuration(500);
		scaleFace.setFillAfter(true);
		scaleFace.setInterpolator(new AccelerateInterpolator());
		Animation.AnimationListener scaleFaceAnimationListener = new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation arg0) {
			}

			@Override
			public void onAnimationRepeat(Animation arg0) {
				if (faceTimer != null) {
					faceTimer.cancel();
					faceTimer = null;
				}
			}

			@Override
			public void onAnimationEnd(Animation arg0) {
				if (faceTimer != null) {
					faceTimer.cancel();
					faceTimer = null;
				}

				faceTimer = new Timer();
				if (faceAnimationSchedule != null) {
					faceAnimationSchedule.cancel();
					faceAnimationSchedule = null;
				}
				faceAnimationSchedule = new TimerTask() {
					@Override
					public void run() {
						faceScaleHandler.sendEmptyMessage(0);
					}
				};

				faceTimer.schedule(faceAnimationSchedule, 750);
			}
		};
		scaleFace.setAnimationListener(scaleFaceAnimationListener);
		return scaleFace;
	}

	@SuppressLint("HandlerLeak")
	private Handler faceScaleHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (clickedButton.isSelected() == true)
				clickedButton.startAnimation(scaleFaceDownAnimation(500));
		}
	};

	public Animation scaleFaceDownAnimation(int duration) {
		Animation scaleFace = new ScaleAnimation(1.2f, 1.0f, 1.2f, 1.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		scaleFace.setDuration(duration);
		scaleFace.setFillAfter(true);
		scaleFace.setInterpolator(new AccelerateInterpolator());
		Animation.AnimationListener scaleFaceAnimationListener = new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation arg0) {
			}

			@Override
			public void onAnimationRepeat(Animation arg0) {
				verticalTextView.setText("");
			}

			@Override
			public void onAnimationEnd(Animation arg0) {
				verticalTextView.setText("");
			}
		};
		scaleFace.setAnimationListener(scaleFaceAnimationListener);
		return scaleFace;
	}

	public void onDestroy() {
		clearTimerTaks(clickSchedule);
		clearTimerTaks(scrollerSchedule);
		clearTimerTaks(faceAnimationSchedule);
		clearTimers(scrollTimer);
		clearTimers(clickTimer);
		clearTimers(faceTimer);

		clickSchedule = null;
		scrollerSchedule = null;
		faceAnimationSchedule = null;
		scrollTimer = null;
		clickTimer = null;
		faceTimer = null;

		super.onDestroy();
	}

	private void clearTimers(Timer timer) {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	private void clearTimerTaks(TimerTask timerTask) {
		if (timerTask != null) {
			timerTask.cancel();
			timerTask = null;
		}
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();

		Toast.makeText(this, "LOW MEMORY1", Toast.LENGTH_SHORT).show();
	}
}
