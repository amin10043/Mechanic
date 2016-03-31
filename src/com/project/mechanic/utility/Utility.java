package com.project.mechanic.utility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.PushNotification.DomainSend;
import com.project.mechanic.entity.Settings;
import com.project.mechanic.entity.Users;
import com.project.mechanic.fragment.PersianDate;
import com.project.mechanic.fragment.ReportAbuseFragment;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.service.UpdatingAllDetail;
import com.project.mechanic.service.UpdatingAllMaster;
import com.project.mechanic.utility.Roozh.SolarCalendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class Utility implements AsyncInterface {

	private static Context context;
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
	ServerDate date;
	public String todayDate;
	int currentimageindex = 0;
	Handler mHandler1, mHandler2;

	Runnable run1;
	int flagSlide;
	int counterVisit;
	int ItemId, userId, typeId;
	String currentTime = "";

	static Bitmap scaledBitmap;

	public Utility(Context context) {
		this.context = context;
		adapter = new DataBaseAdapter(context);
		pDate = new PersianDate();
		adapter.open();
		settings = adapter.getSettings();
		adapter.close();
	}

	public boolean first_time_check() {
		SharedPreferences uPreferences = context.getSharedPreferences("firstTime", 0);
		String first = uPreferences.getString("first", null);
		if ((first == null)) {
			return false;
		} else
			return true;
	}

	public boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
	}

	public void showYesNoDialog(Context context, String Title, String message) {
		new AlertDialog.Builder(context).setTitle(Title).setMessage(message)
				.setPositiveButton("بلی", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

					}
				}).setNegativeButton("خیر", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

					}
				}).setIcon(android.R.drawable.ic_dialog_alert).show();
	}

	public void showOkDialog(Context context, String Title, String message) {
		new AlertDialog.Builder(context).setTitle(Title).setMessage(message)
				.setPositiveButton("تایید", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).setIcon(android.R.drawable.ic_dialog_alert).show();
	}

	public static void copyStream(InputStream input, OutputStream output) throws IOException {

		byte[] buffer = new byte[1024];
		int bytesRead;
		while ((bytesRead = input.read(buffer)) != -1) {
			output.write(buffer, 0, bytesRead);
		}
	}

	public void showtoast(View view, int picture, String massage, String Title) {

		TextView txtView_Title = (TextView) view.findViewById(R.id.txt_Title);
		TextView txtView_Context = (TextView) view.findViewById(R.id.txt_context);
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
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
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
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
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
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		DisplayMetrics outMetrics = new DisplayMetrics();
		display.getMetrics(outMetrics);

		float density = context.getResources().getDisplayMetrics().density;
		int paddingIcon = (int) ((int) context.getResources().getDimension(R.dimen.iconPadding) * density);

		return paddingIcon;
	}

	@SuppressWarnings("deprecation")
	public int getScreenwidth() {
		int columnWidth;
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
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
		return String.valueOf(sc.year) + "/" + String.format(loc, "%02d", sc.month) + "/"
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
		PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		// Create Notification using NotificationCompat.Builder
		NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
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

		if (allStr == null || allStr.length < 1) {
			return;
		}

		for (int i = 0; i < allStr.length; ++i) {
			if (allStr[i] != null && !"".equals(allStr[i])) {
				String[] strs = allStr[i].split(Pattern.quote("***")); // each
																		// Record
				if (strs != null && strs.length > 3) {
					String tableName = strs[0];
					boolean flag = false;
					String[] cols = strs[1].split(Pattern.quote("^^^")); // column
					String startModifyDate = strs[2];
					String endModifyDate = strs[3];
					int row = 0;
					String[][] values = new String[strs.length - 2][];
					for (int j = 4; j < strs.length; j++, row++) {
						values[row] = strs[j].split(Pattern.quote("^^^"));
						flag = true;
					}
					adapter.open();
					if (values != null && values.length > 0 && flag)
						adapter.updateTables(tableName.trim(), cols, values, startModifyDate, endModifyDate);

					adapter.close();
				}
			}
		}
	}

	public static boolean isAppRunning(Context context) {
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> services = activityManager.getRunningTasks(Integer.MAX_VALUE);
		if (services.get(0).topActivity.getPackageName().toString()
				.equalsIgnoreCase(context.getPackageName().toString())) {
			return true;
		}
		return false;
	}

	public void setNoti(Activity a, int userId) {

		TextView txtcm = (TextView) a.findViewById(R.id.txtcm);

		adapter.open();
		final int r = adapter.NumOfNewCmtInFroum(userId);
		int r1 = adapter.NumOfNewCmtInObject(userId);
		int r2 = adapter.NumOfNewCmtInPaper(userId);
		int r3 = r + r1 + r2;

		int t = adapter.NumOfNewLikeInObject(userId);
		int t1 = adapter.NumOfNewLikeInFroum(userId);
		int t2 = adapter.NumOfNewLikeInPaper(userId);
		int t3 = t + t1 + t2;

		if (r3 + t3 == 0) {
			txtcm.setVisibility(View.GONE);
		} else {
			txtcm.setText(r3 + t3 + "");
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

	public void Updating(ServerDate task) {

		// date = new ServerDate(context);
		task.delegate = this;
		task.execute("");

	}

	public Date readDateFromServer(String serverDate) {

		Calendar c = null;
		try {
			c = Calendar.getInstance();
			c.setTimeInMillis(Long.valueOf(serverDate));
		} catch (Exception e) {
			Toast.makeText(context, "خطا در خواندن تاریخ از سرور ", Toast.LENGTH_SHORT).show();
		}
		return c.getTime();
	}

	@Override
	public void processFinish(String output) {

		if (output != null && !(output.contains("Exception") || output.contains("anyType") || output.contains("java")
				|| output.contains("SoapFault"))) {
			if (output.length() == 18) {
				todayDate = output;

				final SharedPreferences currentTime = context.getSharedPreferences("time", 0);
				currentTime.edit().putString("time", todayDate).commit();

			}
			switch (state) {

			case 0: // return date
				serviceUpdate = new UpdatingAllMaster(context);
				serviceUpdate.delegate = this;
				if (settings == null)
					settings = new Settings();
				String mStartDatesMaster = "";
				String mEndDatesMaster = "";
				mStartDatesMaster = (settings.getServerDate_Start_Anad() != null ? settings.getServerDate_Start_Anad()
						: "") + "-"
						+ (settings.getServerDate_Start_Froum() != null ? settings.getServerDate_Start_Froum() : "")
						+ "-" + (settings.getServerDate_Start_News() != null ? settings.getServerDate_Start_News() : "")
						+ "-"
						+ (settings.getServerDate_Start_Object() != null ? settings.getServerDate_Start_Object() : "")
						+ "-"
						+ (settings.getServerDate_Start_Paper() != null ? settings.getServerDate_Start_Paper() : "")
						+ "-"
						+ (settings.getServerDate_Start_Ticket() != null ? settings.getServerDate_Start_Ticket() : "")
						+ "-"
						+ (settings.getServerDate_Start_Users() != null ? settings.getServerDate_Start_Users() : "")
						+ "-" + (settings.getServerDate_Start_Post() != null ? settings.getServerDate_Start_Post() : "")
						+ "-";

				mEndDatesMaster = (settings.getServerDate_End_Anad() != null ? settings.getServerDate_End_Anad() : "")
						+ "-" + (settings.getServerDate_End_Froum() != null ? settings.getServerDate_End_Froum() : "")
						+ "-" + (settings.getServerDate_End_News() != null ? settings.getServerDate_End_News() : "")
						+ "-" + (settings.getServerDate_End_Object() != null ? settings.getServerDate_End_Object() : "")
						+ "-" + (settings.getServerDate_End_Paper() != null ? settings.getServerDate_End_Paper() : "")
						+ "-" + (settings.getServerDate_End_Ticket() != null ? settings.getServerDate_End_Ticket() : "")
						+ "-" + (settings.getServerDate_End_Users() != null ? settings.getServerDate_End_Users() : "")
						+ "-" + (settings.getServerDate_End_Post() != null ? settings.getServerDate_End_Post() : "")
						+ "-";

				serviceUpdate.execute(mStartDatesMaster, mEndDatesMaster, "0");
				flag = true;
				// adapter.open();
				// adapter.setServerDateMaster(mStartDatesMaster,
				// mEndDatesMaster);
				// adapter.close();
				state = 1;
				break;
			case 1: // master
				parseQuery(output);
				if (settings == null)
					settings = new Settings();

				String mStartDatesDetail = "";
				String mEndDatesDetail = "";
				mStartDatesDetail = (settings.getServerDate_Start_CmtInPaper() != null
						? settings.getServerDate_Start_CmtInPaper() : "")
						+ "-"
						+ (settings.getServerDate_Start_CommentInFroum() != null
								? settings.getServerDate_Start_CommentInFroum() : "")
						+ "-"
						+ (settings.getServerDate_Start_CommentInObject() != null
								? settings.getServerDate_Start_CommentInObject() : "")
						+ "-"
						+ (settings.getServerDate_Start_LikeInFroum() != null
								? settings.getServerDate_Start_LikeInFroum() : "")
						+ "-"
						+ (settings.getServerDate_Start_LikeInObject() != null
								? settings.getServerDate_Start_LikeInObject() : "")
						+ "-"
						+ (settings.getServerDate_Start_LikeInPaper() != null
								? settings.getServerDate_Start_LikeInPaper() : "")
						+ "-"
						+ (settings.getServerDate_Start_LikeInComment() != null
								? settings.getServerDate_Start_LikeInComment() : "")
						+ "-"
						+ (settings.getServerDate_Start_LikeInCommentObject() != null
								? settings.getServerDate_Start_LikeInCommentObject() : "")
						+ "-" + (settings.getServerDate_Start_ObjectInCity() != null
								? settings.getServerDate_Start_ObjectInCity() : "")

						+ "-" + (settings.getServerDate_Start_LikeInPost() != null
								? settings.getServerDate_Start_LikeInPost() : "")

						+ "-" + (settings.getServerDate_Start_LikeInCommentPost() != null
								? settings.getServerDate_Start_LikeInCommentPost() : "")

						+ "-" + (settings.getServerDate_Start_CommentInPost() != null
								? settings.getServerDate_Start_CommentInPost() : "");

				mEndDatesDetail = settings.getServerDate_End_CmtInPaper() != null
						? settings.getServerDate_End_CmtInPaper()
						: "" + "-"
								+ (settings.getServerDate_End_CommentInFroum() != null
										? settings.getServerDate_End_CommentInFroum() : "")
								+ "-"
								+ (settings.getServerDate_End_CommentInObject() != null
										? settings.getServerDate_End_CommentInObject() : "")
								+ "-"
								+ (settings.getServerDate_End_LikeInFroum() != null
										? settings.getServerDate_End_LikeInFroum() : "")
								+ "-"
								+ (settings.getServerDate_End_LikeInObject() != null
										? settings.getServerDate_End_LikeInObject() : "")
								+ "-"
								+ (settings.getServerDate_End_LikeInPaper() != null
										? settings.getServerDate_End_LikeInPaper() : "")
								+ "-"
								+ (settings.getServerDate_End_LikeInComment() != null
										? settings.getServerDate_End_LikeInComment() : "")
								+ "-"
								+ (settings.getServerDate_End_LikeInCommentObject() != null
										? settings.getServerDate_End_LikeInCommentObject() : "")
								+ "-" + (settings.getServerDate_End_ObjectInCity() != null
										? settings.getServerDate_End_ObjectInCity() : "")

								+ "-"
								+ (settings.getServerDate_End_LikeInPost() != null
										? settings.getServerDate_End_LikeInPost() : "")
								+ "-"
								+ (settings.getServerDate_End_LikeInCommentPost() != null
										? settings.getServerDate_End_LikeInCommentPost() : "")
								+ "-" + (settings.getServerDate_End_CommentInPost() != null
										? settings.getServerDate_End_CommentInPost() : "");

				serviceUpdateD = new UpdatingAllDetail(context);
				serviceUpdateD.delegate = this;
				serviceUpdateD.execute(mStartDatesDetail, mEndDatesDetail, "0");
				// adapter.open();
				// adapter.setServerDateDetail(mStartDatesDetail,
				// mEndDatesDetail);
				// adapter.close();

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
				ret = pDate.Shamsi(Integer.valueOf(y), Integer.valueOf(m), Integer.valueOf(d)) + "  " + h + ":" + M
						+ ":" + s;
			} catch (Exception ex) {
				ret = "";
			}
		}
		return ret;
	}

	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {

		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
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

	@SuppressWarnings("null")
	public String CreateFile(byte[] byteImage, int ImageId, String nameMainDirectory, String SubFolder, String nameFile,
			String TableName) {

		// تایپ برای مشخص کردن جدول ذخیره سازی آدرس تصاویر می باشد

		// پیدا کردن نام پکیج پروژه
		String namePackage = context.getApplicationContext().getPackageName();

		// پیدا کردن آدرس SD
		File root = android.os.Environment.getExternalStorageDirectory();

		// پیدا کردن و ایجاد آدرس پوشه پکیج
		File FolderPackage = new File(root.getPath() + "/Android/data/" + namePackage + "/");

		if (!FolderPackage.exists()) {
			FolderPackage.mkdirs(); // build directory
		}

		// پیدا کردن و ایجادآدرس پوشه اصلی
		File mainDirectory = new File(FolderPackage.getPath() + "/" + nameMainDirectory + "/");
		if (!mainDirectory.exists()) {
			mainDirectory.mkdirs(); // build directory
		}

		// پیدا کردن و ایجاد آدرس پوشه فرعی
		File agahiDirectory = new File(mainDirectory.getPath() + "/" + SubFolder + "/");

		if (!agahiDirectory.exists()) {
			agahiDirectory.mkdirs(); // build directory
		}

		byte[] zeroByte = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

		boolean retval = Arrays.equals(byteImage, zeroByte);

		if (retval == false) {

			File f = new File(agahiDirectory + File.separator + nameFile + String.valueOf(ImageId) + ".jpg");
			// itemID.add(ImageId);
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// write the bytes in file
			FileOutputStream fo = null;
			try {
				fo = new FileOutputStream(f);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			try {
				fo.write(byteImage);
			} catch (IOException e) {
				e.printStackTrace();
			}

			// remember close de FileOutput
			try {
				fo.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (TableName.equals("Anad")) {

				adapter.open();
				adapter.UpdateImagePathToDb(ImageId, f.getPath(), "Anad");
				adapter.close();
			}
			if (TableName.equals("Ticket")) {
				adapter.open();
				adapter.UpdateImagePathToDb(ImageId, f.getPath(), "Ticket");
				adapter.close();

			}
			if (TableName.equals("Object")) {
				int PathId = 0;

				if (nameFile.contains("header"))
					PathId = 1;
				if (nameFile.contains("profile"))
					PathId = 2;
				if (nameFile.contains("footer"))
					PathId = 3;

				adapter.open();
				adapter.UpdateImagePathObject(ImageId, f.getPath(), PathId);
				adapter.close();
			}

			if (TableName.equals("Users")) {
				adapter.open();
				adapter.UpdateImagePathToDb(ImageId, f.getPath(), "Users");
				adapter.close();

			}
			Toast.makeText(context, nameFile + " " + ImageId + "ذخیره شد", 0).show();
			return f.getPath();

		} else
			return "";

	}

	public String CreateFileString(byte[] byteImage, String ImageId, String nameMainDirectory, String SubFolder,
			String nameFile) {

		// تایپ برای مشخص کردن جدول ذخیره سازی آدرس تصاویر می باشد

		// پیدا کردن نام پکیج پروژه
		String namePackage = context.getApplicationContext().getPackageName();

		// پیدا کردن آدرس SD
		File root = android.os.Environment.getExternalStorageDirectory();

		// پیدا کردن و ایجاد آدرس پوشه پکیج
		File FolderPackage = new File(root.getPath() + "/Android/data/" + namePackage + "/");

		if (!FolderPackage.exists()) {
			FolderPackage.mkdirs(); // build directory
		}

		// پیدا کردن و ایجادآدرس پوشه اصلی
		File mainDirectory = new File(FolderPackage.getPath() + "/" + nameMainDirectory + "/");
		if (!mainDirectory.exists()) {
			mainDirectory.mkdirs(); // build directory
		}

		// پیدا کردن و ایجاد آدرس پوشه فرعی
		File agahiDirectory = new File(mainDirectory.getPath() + "/" + SubFolder + "/");

		if (!agahiDirectory.exists()) {
			agahiDirectory.mkdirs(); // build directory
		}

		byte[] zeroByte = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

		boolean retval = Arrays.equals(byteImage, zeroByte);

		if (retval == false) {

			File f = new File(agahiDirectory + File.separator + nameFile + String.valueOf(ImageId) + ".jpg");
			// itemID.add(ImageId);
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// write the bytes in file
			FileOutputStream fo = null;
			try {
				fo = new FileOutputStream(f);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			try {
				fo.write(byteImage);
			} catch (IOException e) {
				e.printStackTrace();
			}

			// remember close de FileOutput
			try {
				fo.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			Toast.makeText(context, nameFile + " " + ImageId + "ذخیره شد", 0).show();
			return f.getPath();

		} else
			return "";

	}

	public boolean IsEmptyByteArrayImage(byte[] ImageByte) {

		byte[] zeroByte = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

		boolean retval = Arrays.equals(ImageByte, zeroByte);
		return retval;

	}

	public String YearMonthDay(String timeStamp) {
		String ret = "";
		String test = timeStamp;
		if (timeStamp != null && !"".equals(timeStamp)) {
			String y = test.substring(0, 4);
			String m = test.substring(4, 6);
			String d = test.substring(6, 8);

			try {
				ret = pDate.Shamsi(Integer.valueOf(y), Integer.valueOf(m), Integer.valueOf(d));
			} catch (Exception ex) {
				ret = "";
			}
		}
		return ret;
	}

	public ImageView ShowFooterAgahi(Activity activity, boolean IsShow, final int Type) {

		// type = 0 >>>>> mainFragment
		// type = 1 >>>>> main brand fragment
		// type = 2 >>>>> object fragment >> foroshgah
		// type = 3 >>>>> object fragment >> moshaveran
		// type = 4 >>>>> object fragment >> ashkhas haghighi
		// type = 5 >>>>> UrlNewsPaperFragment
		// type = 6 >>>>> introduction fragment
		// type = 7 >>>>> froumtitle && titlePaper fragment
		// type = 8 >>>>> paper and Froum fragment
		// type = 9 >>>>> top items
		// type = 10 >>>>> chat fragment

		ViewFlipper vf = (ViewFlipper) activity.findViewById(R.id.footerAgahi);

		RelativeLayout la = (RelativeLayout) activity.findViewById(R.id.getCommentLayout);

		ImageView send = (ImageView) activity.findViewById(R.id.sendComment);
		// EditText comment = (EditText)
		// activity.findViewById(R.id.inputComment);

		ImageView img1 = (ImageView) activity.findViewById(R.id.img1);
		ImageView img2 = (ImageView) activity.findViewById(R.id.img2);

		ImageView img3 = (ImageView) activity.findViewById(R.id.img3);

		if (IsShow == true) {
			vf.setVisibility(View.VISIBLE);

			switch (Type) {

			case 0: {
				la.setVisibility(View.GONE);

				img1.setBackgroundResource(R.drawable.slide1);

				img2.setBackgroundResource(R.drawable.slide2);

				img3.setBackgroundResource(R.drawable.slide3);
				break;
			}
			case 1: {
				la.setVisibility(View.GONE);

				img1.setBackgroundResource(R.drawable.slide1);

				img2.setBackgroundResource(R.drawable.slide2);

				img3.setBackgroundResource(R.drawable.slide3);
				break;
			}
			case 2: {
				la.setVisibility(View.GONE);

				img1.setBackgroundResource(R.drawable.slide4);

				img2.setBackgroundResource(R.drawable.slide5);

				img3.setBackgroundResource(R.drawable.slide6);
				break;

			}
			case 3: {
				la.setVisibility(View.GONE);

				img1.setBackgroundResource(R.drawable.i1);

				img2.setBackgroundResource(R.drawable.i2);

				img3.setBackgroundResource(R.drawable.i3);
				break;
			}
			case 4: {
				la.setVisibility(View.GONE);

				img1.setBackgroundResource(R.drawable.i4);

				img2.setBackgroundResource(R.drawable.i5);

				img3.setBackgroundResource(R.drawable.i6);
				break;

			}

			case 5: {
				la.setVisibility(View.GONE);

				img1.setBackgroundResource(R.drawable.i4);

				img2.setBackgroundResource(R.drawable.i5);

				img3.setBackgroundResource(R.drawable.i6);
				break;

			}

			case 6: {
				if (getCurrentUser() != null) {
					la.setVisibility(View.VISIBLE);

					vf.setVisibility(View.GONE);
				} else
					vf.setVisibility(View.GONE);
				break;

			}
			case 7: {
				la.setVisibility(View.GONE);

				img1.setBackgroundResource(R.drawable.birthday_icon);

				img2.setBackgroundResource(R.drawable.admin_icon);

				img3.setBackgroundResource(R.drawable.ic_create_post);
				break;

			}
			case 8: {
				if (getCurrentUser() != null) {
					la.setVisibility(View.VISIBLE);

					vf.setVisibility(View.GONE);
				} else
					vf.setVisibility(View.GONE);

				break;

			}
			case 9: {
				la.setVisibility(View.GONE);

				img1.setBackgroundResource(R.drawable.birthday_icon);

				img2.setBackgroundResource(R.drawable.admin_icon);

				img3.setBackgroundResource(R.drawable.ic_create_post);
				break;

			}
			case 10: {
				inputCommentAndPickFile(activity);
				break;

			}

			default:
				break;
			}

			vf.setAutoStart(true);
			vf.setFlipInterval(4000);
			vf.startFlipping();
		} else {
			vf.setVisibility(View.GONE);
			la.setVisibility(View.GONE);

		}
		return send;
	}

	public Typeface SetFontCasablanca() {

		Typeface typeFace = Typeface.createFromAsset(context.getAssets(), "fonts/Casablanca.TTF");

		return typeFace;

	}

	public Typeface SetFontIranSans() {

		Typeface typeFace = Typeface.createFromAsset(context.getAssets(), "fonts/IranSans.TTF");

		return typeFace;

	}

	public String inputComment(Activity activity) {

		EditText comment = (EditText) activity.findViewById(R.id.inputComment);
		String content = comment.getText().toString();

		return content;
	}

	public void ToEmptyComment(Activity activity) {

		EditText comment = (EditText) activity.findViewById(R.id.inputComment);
		comment.setText("");
	}

	public void ReplyLayout(Activity activity, String text, boolean isShow) {

		if (activity != null) {

			TextView reply = (TextView) activity.findViewById(R.id.reply);
			RelativeLayout layout = (RelativeLayout) activity.findViewById(R.id.replyLayout);

			if (isShow == true) {
				layout.setVisibility(View.VISIBLE);
				reply.setText(text);
			} else {
				reply.setText("");
				layout.setVisibility(View.GONE);

			}
		}
	}

	public ImageView deleteReply(Activity activity) {

		ImageView delete = (ImageView) activity.findViewById(R.id.deleteReply);

		return delete;
	}

	public ImageView[] inputCommentAndPickFile(Activity activity) {

		ImageView[] arrayImage = new ImageView[3];

		ViewFlipper vf = (ViewFlipper) activity.findViewById(R.id.footerAgahi);
		RelativeLayout la = (RelativeLayout) activity.findViewById(R.id.getCommentLayout);

		la.setVisibility(View.VISIBLE);
		vf.setVisibility(View.GONE);

		arrayImage[0] = (ImageView) activity.findViewById(R.id.sendComment);
		arrayImage[1] = (ImageView) activity.findViewById(R.id.pickPicture);
		// arrayImage[2] = (ImageView) activity.findViewById(R.id.showPicture);

		arrayImage[1].setVisibility(View.VISIBLE);
		// arrayImage[2].setVisibility(View.VISIBLE);

		return arrayImage;

	}

	public PopupMenu ShowPopupMenu(List<String> nameItems, View v) {
		int index = 0;

		final PopupMenu popupMenu = new PopupMenu(context, v);

		for (int i = 0; i < nameItems.size(); i++) {
			String item = nameItems.get(i);
			popupMenu.getMenu().add(Menu.NONE, i, Menu.NONE, item);
			index = i;

		}

		v.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				popupMenu.show();
			}
		});

		// popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
		//
		// @Override
		// public boolean onMenuItemClick(MenuItem item) {
		//
		// // if (item.getItemId() == 0) {
		//
		// Toast.makeText(context, "t", 0).show();
		// // }
		//
		// return false;
		// }
		// });

		return popupMenu;

	}

	public void sendMessage(String source) {

		DomainSend fr = new DomainSend(source);

		FragmentTransaction trans = ((MainActivity) context).getSupportFragmentManager().beginTransaction();

		trans.replace(R.id.content_frame, fr);
		trans.addToBackStack(null);
		trans.commit();

	}

	public void CopyToClipboard(String value) {

		ClipboardManager clipMan = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
		// value = "http://www.google.com\n" + value;

		Toast.makeText(context, value, Toast.LENGTH_SHORT).show();

		ClipData cData = ClipData.newPlainText("text", value);
		clipMan.setPrimaryClip(cData);
		Toast.makeText(context, "کپی شد", 0).show();

	}

	public void reportAbuse(int userIdSender, int source, int itemId, String content, int backId, int position) {

		// if (source > 4) {
		// FragmentTransaction trans = ((MainActivity)
		// context).getSupportFragmentManager().beginTransaction();
		// ReportAbuseFragment fragment = new ReportAbuseFragment(backId);
		// trans.setCustomAnimations(R.anim.pull_in_left,
		// R.anim.push_out_right);
		//
		// Bundle bundle = new Bundle();
		//
		// bundle.putInt("userIdSender", userIdSender);
		// bundle.putInt("source", source);
		// bundle.putInt("itemId", itemId);
		// bundle.putString("content", content);
		// fragment.setArguments(bundle);
		//
		// trans.replace(R.id.content_frame, fragment);
		// trans.commit();
		// } else {
		FragmentTransaction trans = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
		ReportAbuseFragment fragment = new ReportAbuseFragment(userIdSender, source, itemId, content, backId, position);
		trans.setCustomAnimations(R.anim.pull_in_left, R.anim.push_out_right);

		Bundle bundle = new Bundle();
		//
		// bundle.putInt("userIdSender", userIdSender);
		// bundle.putInt("source", source);
		// bundle.putInt("itemId", itemId);
		// bundle.putString("content", content);
		// bundle.putInt("position", position);
		// fragment.setArguments(bundle);

		trans.replace(R.id.content_frame, fragment);
		trans.commit();
		// }

	}

	public void reportAbuseTicket(int userIdSender, int itemId, String content, int ticketTypeId, int provinceId) {

		FragmentTransaction trans = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
		ReportAbuseFragment fragment = new ReportAbuseFragment();
		trans.setCustomAnimations(R.anim.pull_in_left, R.anim.push_out_right);

		Bundle bundle = new Bundle();

		bundle.putInt("userIdSender", userIdSender);
		bundle.putInt("itemId", itemId);
		bundle.putInt("source", 3);
		bundle.putString("content", content);
		bundle.putInt("ticketTypeId", ticketTypeId);
		bundle.putInt("provinceId", provinceId);

		fragment.setArguments(bundle);

		trans.replace(R.id.content_frame, fragment);
		trans.commit();

	}

	public static Bitmap getclip(Bitmap bitmap) {

		Bitmap output = null;
		Canvas canvas = null;
		try {
			output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);

		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		}
		if (output != null)
			canvas = new Canvas(output);

		if (canvas != null) {

			final Paint paint = new Paint();
			final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getWidth() / 2, paint);
			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			canvas.drawBitmap(bitmap, rect, rect, paint);
		}

		return output;
	}

	public static List<String> convertYear(String timeStamp) {

		List<String> dates = new ArrayList<String>();

		if (timeStamp != null && !"".equals(timeStamp)) {
			dates.add(timeStamp.substring(0, 4));
			dates.add(timeStamp.substring(4, 6));
			dates.add(timeStamp.substring(6, 8));

		}
		return dates;

	}

	public int differentTwoDate(String date1, String date2) {

		List<String> pastDate = convertYear(date1);
		List<String> CurrentDate = convertYear(date2);

		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();

		calendar1.set(Integer.valueOf(pastDate.get(0)) + 1, Integer.valueOf(pastDate.get(1)),
				Integer.valueOf(pastDate.get(2)));

		calendar2.set(Integer.valueOf(CurrentDate.get(0)), Integer.valueOf(CurrentDate.get(1)),
				Integer.valueOf(CurrentDate.get(2)));

		long milsecs1 = calendar1.getTimeInMillis();
		long milsecs2 = calendar2.getTimeInMillis();
		long diff = Math.abs(milsecs2 - milsecs1);
		// long dsecs = diff / 1000;
		// long dminutes = diff / (60 * 1000);
		// long dhours = diff / (60 * 60 * 1000);

		long ddays = diff / (24 * 60 * 60 * 1000);
		int dif = (int) ddays;

		return dif;

	}

	public void setSizeDialog(Dialog dialog) {

		// WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		// lp.copyFrom(dialog.getWindow().getAttributes());
		// lp.width = (int) 1000;
		// lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.BLACK));

		dialog.getWindow().getAttributes().windowAnimations = R.style.animationDialog;
		dialog.show();

	}
	
	public void notificationDialogAnimation(Dialog dialog , int margingTop) {

		// WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		// lp.copyFrom(dialog.getWindow().getAttributes());
		// lp.width = (int) 1000;
		// lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		
		Window window = dialog.getWindow();
		WindowManager.LayoutParams wlp = window.getAttributes();

		wlp.gravity = Gravity.TOP;
		wlp.y=margingTop;
		wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
		window.setAttributes(wlp);

		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.BLACK));

		dialog.getWindow().getAttributes().windowAnimations = R.style.notificationAnimation;

		dialog.show();

	}

	public List<String> spilitDateTime(String date) {

		List<String> list = new ArrayList<String>();

		if (date != null && !"".equals(date)) {

			String da = date.substring(0, 10);
			list.add(da);

			String time = date.substring(11, 17);
			list.add(time);

		}

		return list;

	}

	public List<Integer> createUnicId(List<Integer> ids) {

		Set<Integer> different = new HashSet<Integer>();
		List<Integer> unicIds = new ArrayList<Integer>();

		for (int i = 0; i < ids.size(); i++)
			different.add(ids.get(i));

		for (Integer val : different)
			unicIds.add(val);

		return unicIds;

	}

	public boolean checkError(String output) {

		boolean isError = true;

		if (!"".equals(output) && output != null
				&& !(output.contains("Exception") || output.contains("java") || output.contains("soap")))
			isError = false;

		return isError;

	}

	public void showErrorToast() {

		String message = " با عرض پوزش ، خطایی رخ داد";
		Toast.makeText(context, message, 0).show();
	}

	public void showRingProgressDialog(ProgressDialog ringProgressDialog, boolean isShow) {

		if (isShow == true) {
			ringProgressDialog.setCancelable(true);
			new Thread(new Runnable() {

				@Override
				public void run() {

					try {

						Thread.sleep(10000);

					} catch (Exception e) {

					}
				}
			}).start();

		} else {
			ringProgressDialog.dismiss();
		}
	}

	public static byte[] compressImage(File file) {

		String filePath = file.getPath();

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

		int actualHeight = options.outHeight;
		int actualWidth = options.outWidth;

		float maxHeight = 800.0f;
		float maxWidth = 612.0f;
		float imgRatio = actualWidth / actualHeight;
		float maxRatio = maxWidth / maxHeight;

		if (actualHeight > maxHeight || actualWidth > maxWidth) {
			if (imgRatio < maxRatio) {
				imgRatio = maxHeight / actualHeight;
				actualWidth = (int) (imgRatio * actualWidth);
				actualHeight = (int) maxHeight;
			} else if (imgRatio > maxRatio) {
				imgRatio = maxWidth / actualWidth;
				actualHeight = (int) (imgRatio * actualHeight);
				actualWidth = (int) maxWidth;
			} else {
				actualHeight = (int) maxHeight;
				actualWidth = (int) maxWidth;

			}
		}

		options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);
		options.inJustDecodeBounds = false;
		options.inDither = false;
		options.inPurgeable = true;
		options.inInputShareable = true;
		options.inTempStorage = new byte[16 * 1024];

		try {
			bmp = BitmapFactory.decodeFile(filePath, options);
		} catch (OutOfMemoryError exception) {
			exception.printStackTrace();

		}
		try {
			scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
		} catch (OutOfMemoryError exception) {
			exception.printStackTrace();
		}

		float ratioX = actualWidth / (float) options.outWidth;
		float ratioY = actualHeight / (float) options.outHeight;
		float middleX = actualWidth / 2.0f;
		float middleY = actualHeight / 2.0f;

		Matrix scaleMatrix = new Matrix();
		scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

		Canvas canvas = new Canvas(scaledBitmap);
		canvas.setMatrix(scaleMatrix);
		canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2,
				new Paint(Paint.FILTER_BITMAP_FLAG));

		ExifInterface exif;
		try {
			exif = new ExifInterface(filePath);

			int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
			Log.d("EXIF", "Exif: " + orientation);
			Matrix matrix = new Matrix();
			if (orientation == 6) {
				matrix.postRotate(90);
				Log.d("EXIF", "Exif: " + orientation);
			} else if (orientation == 3) {
				matrix.postRotate(180);
				Log.d("EXIF", "Exif: " + orientation);
			} else if (orientation == 8) {
				matrix.postRotate(270);
				Log.d("EXIF", "Exif: " + orientation);
			}
			scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(),
					matrix, true);

		} catch (IOException e) {
			e.printStackTrace();
		}
		FileOutputStream out = null;
		ByteArrayOutputStream stream = new ByteArrayOutputStream();

		String filename = getFilename();
		try {
			out = new FileOutputStream(filename);
			scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
			scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		byte[] byteArray = stream.toByteArray();

		return byteArray;

	}

	public static String getFilename() {

		// پیدا کردن نام پکیج پروژه
		String namePackage = context.getApplicationContext().getPackageName();

		// پیدا کردن آدرس SD
		File root = android.os.Environment.getExternalStorageDirectory();

		// پیدا کردن و ایجاد آدرس پوشه پکیج
		File FolderPackage = new File(root.getPath() + "/Android/data/" + namePackage + "/");

		if (!FolderPackage.exists()) {
			FolderPackage.mkdirs(); // build directory
		}

		// پیدا کردن و ایجادآدرس پوشه اصلی
		File mainDirectory = new File(FolderPackage.getPath() + "/" + "Mechanical" + "/");
		if (!mainDirectory.exists()) {
			mainDirectory.mkdirs(); // build directory
		}

		// پیدا کردن و ایجاد آدرس پوشه فرعی
		File subfolder = new File(mainDirectory.getPath() + "/" + "Temp" + "/");

		if (!subfolder.exists()) {
			subfolder.mkdirs(); // build directory
		}

		String uriSting = (subfolder.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
		return uriSting;

	}

	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		final float totalPixels = width * height;
		final float totalReqPixelsCap = reqWidth * reqHeight * 2;

		while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
			inSampleSize++;
		}

		return inSampleSize;
	}

	public static Bitmap decodeFile(String path) {
		try {
			// Decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(path, o);

			// The new size we want to scale to
			// long maxMemory = Runtime.getRuntime().maxMemory()/1024;
			// int maxMemoryForImage = (int) (maxMemory / 8);

			final int REQUIRED_SIZE = 100;

			// Find the correct scale value. It should be the power of 2.
			int scale = 1;
			if (o.outHeight > REQUIRED_SIZE || o.outWidth > REQUIRED_SIZE) {
				scale = (int) Math.pow(2, (int) Math
						.ceil(Math.log(REQUIRED_SIZE / (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
			}

			// Decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			return BitmapFactory.decodeFile(path, o2);

		} catch (Exception e) {
		}
		return null;

	}
}
