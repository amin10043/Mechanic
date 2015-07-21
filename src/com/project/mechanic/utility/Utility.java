package com.project.mechanic.utility;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
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
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Build;
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
import com.project.mechanic.fragment.PersianDate;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.service.UpdatingAllDetail;
import com.project.mechanic.service.UpdatingAllMaster;
import com.project.mechanic.utility.Roozh.SolarCalendar;

public class Utility implements AsyncInterface {

	private Context context;
	private DataBaseAdapter adapter;
	int notificationID;
	LayoutInflater inflater;
	ViewGroup toastlayout;

	private UpdatingAllMaster serviceUpdate;
	private UpdatingAllDetail serviceUpdateD;
	Settings settings;
	PersianDate pDate;
	static boolean flag = false;
	static boolean flag2 = false;
	int state = 0;
	public static final int NUMBER_OF_RECORD_RECEIVED = 10;
	public static final int NUMBER_OF_RECORD_RECEIVED_D = 50;
	ServerDate date;

	public Utility(Context context) {
		this.context = context;
		adapter = new DataBaseAdapter(context);
		pDate = new PersianDate();
		adapter.open();
		settings = adapter.getSettings();
		adapter.close();
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

	public static void copyStream(InputStream input, OutputStream output)
			throws IOException {

		byte[] buffer = new byte[1024];
		int bytesRead;
		while ((bytesRead = input.read(buffer)) != -1) {
			output.write(buffer, 0, bytesRead);
		}
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

	@SuppressWarnings("deprecation")
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

	public void parseQuery(String q) {

		String[] allStr = q.split("&&&"); // each Table

		if (allStr == null || allStr.length < 1 || !q.contains("&&&")) {
			return;
		}

		for (int i = 0; i < allStr.length; ++i) {
			if (allStr[i] != null && !"".equals(allStr[i])) {
				String[] strs = allStr[i].split(Pattern.quote("***")); // each
																		// Record
				String tableName = strs[0];
				boolean flag = false;
				String[] cols = strs[1].split(Pattern.quote("^^^")); // column
				String ModifyDate = strs[2];
				int row = 0;
				String[][] values = new String[strs.length - 2][];
				for (int j = 3; j < strs.length; j++, row++) {
					values[row] = strs[j].split(Pattern.quote("^^^"));
					flag = true;
				}
				adapter.open();
				if (values != null && values.length > 0 && flag)
					adapter.updateTables(tableName.trim(), cols, values,
							ModifyDate);

				adapter.close();
			}
		}
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

		ByteArrayOutputStream str = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 50, str);
		try {
			str.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// bitmap.recycle();
		return str.toByteArray();
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void Updating() {

		date = new ServerDate(context);
		date.delegate = this;
		date.execute("");

	}

	public Date readDateFromServer(String serverDate) {

		Calendar c = null;
		try {
			c = Calendar.getInstance();
			c.setTimeInMillis(Long.valueOf(serverDate));
		} catch (Exception e) {
			Toast.makeText(context, "خطا در خواندن تاریخ از سرور ",
					Toast.LENGTH_SHORT).show();
		}
		return c.getTime();
	}

	@Override
	public void processFinish(String output) {

		if (output != null
				&& !(output.contains("Exception") || output.contains("java") || output
						.contains("SoapFault"))) {
			SharedPreferences pref = context.getSharedPreferences("update", 0);
			SharedPreferences.Editor editor = pref.edit();
			int from = pref.getInt("fromM", 0);
			int to = pref.getInt("toM", 0);

			switch (state) {

			case 0: // return date
				serviceUpdate = new UpdatingAllMaster(context);
				serviceUpdate.delegate = this;
				if (settings == null)
					settings = new Settings();
				String mDatesMaster ="";
//				String mDatesMaster = settings.getServerDate_Anad() + "-"
//						+ settings.getServerDate_Froum() + "-" + "-"
//						+ settings.getServerDate_News() + "-"
//						+ settings.getServerDate_Object() + "-"
//						+ settings.getServerDate_Paper() + "-"
//						+ settings.getServerDate_Ticket() + "-"
//						+ settings.getServerDate_Users() + "-";
				serviceUpdate.execute(mDatesMaster, String.valueOf(from),
						String.valueOf(to));
				flag = true;
				editor.putInt("fromM", from + NUMBER_OF_RECORD_RECEIVED);
				editor.putInt("toM", to + NUMBER_OF_RECORD_RECEIVED);

				adapter.open();
				adapter.setServerDate("ServerDate_Users", output);
				adapter.close();
				editor.commit();
				state = 1;
				break;
			case 1: // master
				parseQuery(output);
				from = pref.getInt("fromD", 0);
				to = pref.getInt("toD", 0);
				if (settings == null)
					settings = new Settings();
				String mDatesDetail ="";
//				String mDatesDetail = settings.getServerDate_CmtInPaper() + "-"
//						+ settings.getServerDate_CommentInFroum() + "-"
//						+ settings.getServerDate_CommentInObject() + "-"
//						+ settings.getServerDate_LikeInFroum() + "-"
//						+ settings.getServerDate_LikeInObject() + "-"
//						+ settings.getServerDate_LikeInPaper() + "-";
				serviceUpdateD = new UpdatingAllDetail(context);
				serviceUpdateD.delegate = this;
				serviceUpdateD.execute(mDatesDetail, String.valueOf(from),
						String.valueOf(to));

				editor.putInt("fromD", from + NUMBER_OF_RECORD_RECEIVED_D);
				editor.putInt("toD", to + NUMBER_OF_RECORD_RECEIVED_D);
				editor.apply();
				state = 2;
				break;
			case 2: // detail
				parseQuery(output);
				state = 0;
				break;
			}
		}
	}

	public Typeface setFont() {
		return Typeface.createFromAsset(context.getAssets(), "fonts/BROYA.TTF");
	}

	public String getPersianDate(String timeStamp) {
		String ret = "";
		String test = timeStamp;
		if (timeStamp != null && !"".equals(timeStamp)) {
			String y = test.substring(0, 4);
			String m = test.substring(4, 6);
			String d = test.substring(6, 8);
			String h = test.substring(8, 10);
			String M = test.substring(10, 12);
			String s = test.substring(12, 14);
			try {
				ret = pDate.Shamsi(Integer.valueOf(y), Integer.valueOf(m),
						Integer.valueOf(d)) + "  " + h + ":" + M + ":" + s;
			} catch (Exception ex) {
				ret = "";
			}
		}
		return ret;
	}

	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {

		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}
}
