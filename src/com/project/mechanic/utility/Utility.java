package com.project.mechanic.utility;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.support.v4.app.NotificationCompat;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.entity.Settings;
import com.project.mechanic.entity.Users;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.Updating;
import com.project.mechanic.utility.Roozh.SolarCalendar;

public class Utility implements AsyncInterface {

	private Context context;
	private DataBaseAdapter adapter;
	int notificationID;
	LayoutInflater inflater;
	ViewGroup toastlayout;

	private Updating serviceUpdate;
	Settings settings;

	public Utility(Context context) {
		this.context = context;
		adapter = new DataBaseAdapter(context);
	}

	public boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		return cm.getActiveNetworkInfo() != null
				&& cm.getActiveNetworkInfo().isConnectedOrConnecting();
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

	public void showtoast(View view, int picture, String massage, String Title) {

		TextView txtView_Title = (TextView) view.findViewById(R.id.txt_Title);
		TextView txtView_Context = (TextView) view
				.findViewById(R.id.txt_context);
		ImageView imageView = (ImageView) view.findViewById(R.id.image_toast);

		txtView_Title.setText(Title);

		txtView_Context.setText(massage);
		imageView.setImageResource(picture);

	}

	public Users getCurrentUser() {

		Users u = null;
		SharedPreferences settings = context.getSharedPreferences("user", 0);

		boolean isLogin = settings.getBoolean("isLogin", false);
		if (isLogin) {

			adapter.open();
			u = adapter.getCurrentUser();
			adapter.close();
			return u;
		} else {
			return null;
		}
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

		int padding = (int) (140 * density);
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

	public void Notification() {
		// Set Notification Title
		String strtitle = "t1";// getString(R.string.notificationtitle);
		// Set Notification Text
		String strtext = "test";// getString(R.string.notificationtext);

		// Open NotificationView Class on Notification Click
		Intent intent = new Intent(context, MainActivity.class);
		// Send data to NotificationView Class
		intent.putExtra("title", strtitle);
		intent.putExtra("text", strtext);
		// Open NotificationView.java Activity
		PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);

		// Create Notification using NotificationCompat.Builder
		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				context)
		// Set Icon
				.setSmallIcon(R.drawable.ic_notification)
				// Set Ticker Message
				.setTicker("arabian")
				// Set Title
				.setContentTitle("پیام جدید")
				// Set Text
				.setContentText("مکانیکال")
				// Add an Action Button below Notification
				.addAction(R.drawable.ic_launcher, "Action Button", pIntent)
				// Set PendingIntent into Notification
				.setContentIntent(pIntent)
				// Dismiss Notification
				.setAutoCancel(true);

		// Create Notification Manager
		NotificationManager notificationmanager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		// Build Notification with Notification Manager
		notificationmanager.notify(0, builder.build());

	}

	public void parseQuery(String query) {

		StringTokenizer tokens = new StringTokenizer(query, "***");
		if (!tokens.hasMoreTokens())
			return;
		String tableName = tokens.nextToken();
		if (!tokens.hasMoreTokens())
			return;
		String serverDate = tokens.nextToken();

		StringTokenizer inner = new StringTokenizer(tokens.nextToken(), ",");
		int i = 0;
		String[] col = new String[inner.countTokens()];
		while (inner.hasMoreTokens()) {
			col[i++] = inner.nextToken();
		}
		int j = 0;
		String[][] values = new String[tokens.countTokens()][];
		while (tokens.hasMoreTokens()) {
			values[j] = tokens.nextToken().split(",");
			j++;
		}

		adapter.open();
		if (values != null && values.length > 0)
			adapter.updateTables(tableName, col, values);

		adapter.setServerDate("ServerDate_" + tableName.trim(), serverDate);
		adapter.close();
	}

	public String getCuurentDateTime() {
		java.text.DateFormat dateFormat = new SimpleDateFormat(
				"yyyy/MM/dd HH:mm:ss");
		Date date = new Date();

		return dateFormat.format(date);

	}

	public static boolean isAppRunning(Context context) {
		// check with the first task(task in the foreground)
		// in the returned list of tasks
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> services = activityManager
				.getRunningTasks(Integer.MAX_VALUE);
		if (services.get(0).topActivity.getPackageName().toString()
				.equalsIgnoreCase(context.getPackageName().toString())) {
			// your application is running in the background
			return true;
		}
		return false;
	}

	public void setNoti(Activity a, int userId) {
		adapter.open();
		final int r = adapter.NumOfNewCmtInFroum(userId);
		int r1 = adapter.NumOfNewCmtInObject(userId);
		int r2 = adapter.NumOfNewCmtInPaper(userId);
		int r3 = r + r1 + r2;
		if (r3 == 0) {

			TextView txtcm = (TextView) a.findViewById(R.id.txtcm);
			txtcm.setVisibility(View.GONE);
		} else {
			TextView txtcm = (TextView) a.findViewById(R.id.txtcm);
			txtcm.setText(String.valueOf(r3));

		}

		int t = adapter.NumOfNewLikeInObject(userId);
		int t1 = adapter.NumOfNewLikeInFroum(userId);
		int t2 = adapter.NumOfNewLikeInPaper(userId);
		int t3 = t + t1 + t2;
		if (t3 == 0) {
			TextView txtlike = (TextView) a.findViewById(R.id.txtlike);
			txtlike.setVisibility(View.GONE);
		} else {
			TextView txtlike = (TextView) a.findViewById(R.id.txtlike);
			txtlike.setText(String.valueOf(t3));
		}

		adapter.close();
	}

	public static byte[] CompressBitmap(Bitmap bitmap) {

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 0, bos);
		return bos.toByteArray();

		// String env = Environment.getExternalStorageDirectory().getPath();
		// String path = env + "/test.png";
		// try {
		// File f = new File(path);
		//
		// FileOutputStream fileOut = new FileOutputStream(f);
		// OutputStream str = new
		// bitmap.compress(Bitmap.CompressFormat.PNG, 50, fileOut);
		// try {
		// fileOut.close();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		//
		// bitmap.recycle();
		//
		// } catch (FileNotFoundException e) {
		// e.printStackTrace();
		// }
	}

	public void Updating() {
		String tableUpdating = "User";
		serviceUpdate = new Updating(context);
		serviceUpdate.delegate = this;
		serviceUpdate.execute(tableUpdating,
				settings != null ? settings.getServerDate_Users() : "");

		tableUpdating = "Paper";
		serviceUpdate = new Updating(context);
		serviceUpdate.delegate = this;
		serviceUpdate.execute(tableUpdating,
				(settings != null ? settings.getServerDate_Paper() : ""));

		tableUpdating = "Froum";
		serviceUpdate = new Updating(context);
		serviceUpdate.delegate = this;
		serviceUpdate.execute(tableUpdating,
				(settings != null ? settings.getServerDate_Froum() : ""));
		// tableUpdating = "Object";
		// serviceUpdate = new Updating(this);
		// serviceUpdate.delegate = this;
		// serviceUpdate.execute(tableUpdating,
		// (settings != null ? settings.getServerDate() : ""));
		tableUpdating = "News";
		serviceUpdate = new Updating(context);
		serviceUpdate.delegate = this;
		serviceUpdate.execute(tableUpdating,
				(settings != null ? settings.getServerDate_News() : ""));
		tableUpdating = "Anad";
		serviceUpdate = new Updating(context);
		serviceUpdate.delegate = this;
		serviceUpdate.execute(tableUpdating,
				(settings != null ? settings.getServerDate_Anad() : ""));
		tableUpdating = "Ticket";
		serviceUpdate = new Updating(context);
		serviceUpdate.delegate = this;
		serviceUpdate.execute(tableUpdating,
				(settings != null ? settings.getServerDate_Ticket() : ""));
		tableUpdating = "LikeInPaper";
		serviceUpdate = new Updating(context);
		serviceUpdate.delegate = this;
		serviceUpdate.execute(tableUpdating,
				(settings != null ? settings.getServerDate_LikeInPaper() : ""));
		tableUpdating = "CmtInPaper";
		serviceUpdate = new Updating(context);
		serviceUpdate.delegate = this;
		serviceUpdate.execute(tableUpdating,
				(settings != null ? settings.getServerDate_CmtInPaper() : ""));

		tableUpdating = "LikeInFroum";
		serviceUpdate = new Updating(context);
		serviceUpdate.delegate = this;
		serviceUpdate.execute(tableUpdating,
				(settings != null ? settings.getServerDate_LikeInFroum() : ""));
		tableUpdating = "CommentInFroum";
		serviceUpdate = new Updating(context);
		serviceUpdate.delegate = this;
		serviceUpdate.execute(tableUpdating,
				(settings != null ? settings.getServerDate_CommentInFroum()
						: ""));

		tableUpdating = "LikeInObject";
		serviceUpdate = new Updating(context);
		serviceUpdate.delegate = this;
		serviceUpdate
				.execute(
						tableUpdating,
						(settings != null ? settings
								.getServerDate_LikeInObject() : ""));
		tableUpdating = "CommentInObject";
		serviceUpdate = new Updating(context);
		serviceUpdate.delegate = this;
		serviceUpdate.execute(tableUpdating,
				(settings != null ? settings.getServerDate_CommentInObject()
						: ""));
	}

	public Date readDateFromServer(String serverDate) {
		String pattern = "M/dd/yyyyh:mm aa";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date x = null;
		try {
			x = sdf.parse(serverDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			Toast.makeText(context, "خطا در خواندن تاریخ از سرور ",
					Toast.LENGTH_SHORT).show();
		}
		return x;
	}

	@Override
	public void processFinish(String output) {
		parseQuery(output);

	}
}
