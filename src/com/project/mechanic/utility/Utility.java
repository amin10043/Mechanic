package com.project.mechanic.utility;

import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.project.mechanic.R;
import com.project.mechanic.SplashActivity;
import com.project.mechanic.entity.Users;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Roozh.SolarCalendar;

public class Utility {

	private Context context;
	private DataBaseAdapter adapter;

	public Utility(Context context) {
		this.context = context;
		adapter = new DataBaseAdapter(context);
	}

	public boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni == null) {
			// There are no active networks.
			return false;
		} else
			return true;
	}

	public void showYesNoDialog(Context context, String Title, String message) {
		new AlertDialog.Builder(context)
				.setTitle(Title)
				.setMessage(message)
				.setPositiveButton("بلی",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {

							}
						})
				.setNegativeButton("خیر",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {

							}
						}).setIcon(android.R.drawable.ic_dialog_alert).show();
	}

	public void showOkDialog(Context context, String Title, String message) {
		new AlertDialog.Builder(context)
				.setTitle(Title)
				.setMessage(message)
				.setPositiveButton("تایید",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						}).setIcon(android.R.drawable.ic_dialog_alert).show();
	}

	public Users getCurrentUser() {

		Users u = null;
		SharedPreferences settings = context.getSharedPreferences("user", 0);

		boolean isLogin = settings.getBoolean("isLogin", false);
		// if (isLogin) {

		adapter.open();
		u = adapter.getUserbyid(2); // FOR TESTING !!!!
		adapter.close();
		return u;
		// } else {
		// return null;
		// }

	}

	@SuppressLint("NewApi")
	public int getScreenHeight() {
		int columnWidth;
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();

		final Point point = new Point();
		try {
			display.getSize(point);
		} catch (java.lang.NoSuchMethodError ignore) { // Older device
			point.x = display.getWidth();
			point.y = display.getHeight();
		}
		columnWidth = point.y;
		return columnWidth;

	}

	@SuppressLint("NewApi")
	public int getScreenHeightWithPadding() {
		int columnWidth;
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();

		final Point point = new Point();
		try {
			display.getSize(point);
		} catch (java.lang.NoSuchMethodError ignore) { // Older device
			point.x = display.getWidth();
			point.y = display.getHeight();
		}

		DisplayMetrics outMetrics = new DisplayMetrics();
		display.getMetrics(outMetrics);

		float density = context.getResources().getDisplayMetrics().density;
		float dpHeight = outMetrics.heightPixels / density;
		float dpWidth = outMetrics.widthPixels / density;

		columnWidth = (int) dpHeight;

		int padding = (int) (70 * density);
		return point.y - padding;

	}

	public int iconPadding() {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		DisplayMetrics outMetrics = new DisplayMetrics();
		display.getMetrics(outMetrics);

		float density = context.getResources().getDisplayMetrics().density;
		int paddingIcon = (int) ((int) context.getResources().getDimension(
				R.dimen.iconPadding) * density);

		return paddingIcon;
	}

	public int getScreenwidth() {
		int columnWidth;
		WindowManager wm = (WindowManager) context
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

	public static String getCurrentShamsidate() {
		Locale loc = new Locale("en_US");
		Roozh util = new Roozh();
		SolarCalendar sc = util.new SolarCalendar();
		return String.valueOf(sc.year) + "/"
				+ String.format(loc, "%02d", sc.month) + "/"
				+ String.format(loc, "%02d", sc.date);
	}

	public void setFont() {
		Typeface typeFace = Typeface.createFromAsset(context.getAssets(),
				"fonts/BROYA.TTF");

	}

	public void showNotification() {
		Intent intent = new Intent(context, SplashActivity.class);
		PendingIntent pIntent = PendingIntent
				.getActivity(context, 0, intent, 0);

		// build notification
		// the addAction re-use the same intent to keep the example short
		Notification n = new Notification.Builder(context)
				.setContentTitle("New mail from " + "test@gmail.com")
				.setContentText("Subject").setSmallIcon(R.drawable.addimage)
				.setContentIntent(pIntent).setAutoCancel(true).build();
		// .addAction(R.drawable.addimage, "تست 1", pIntent)
		// .addAction(R.drawable.addimage, "تست 2", pIntent)
		// .addAction(R.drawable.addimage, "تست 3", pIntent)

		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);

		notificationManager.notify(0, n);
	}
}
