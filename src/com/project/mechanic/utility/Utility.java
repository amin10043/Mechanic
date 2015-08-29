package com.project.mechanic.utility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

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
import android.support.v4.app.NotificationCompat;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
	ServerDate date;

	public Utility(Context context) {
		this.context = context;
		adapter = new DataBaseAdapter(context);
		pDate = new PersianDate();
		adapter.open();
		settings = adapter.getSettings();
		adapter.close();
	}

	public boolean first_time_check() {
		SharedPreferences uPreferences = context.getSharedPreferences(
				"firstTime", 0);
		String first = uPreferences.getString("first", null);
		if ((first == null)) {
			return false;
		} else
			return true;
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
						adapter.updateTables(tableName.trim(), cols, values,
								startModifyDate, endModifyDate);

					adapter.close();
				}
			}
		}
	}

	public static boolean isAppRunning(Context context) {
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> services = activityManager
				.getRunningTasks(Integer.MAX_VALUE);
		if (services.get(0).topActivity.getPackageName().toString()
				.equalsIgnoreCase(context.getPackageName().toString())) {
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
				&& !(output.contains("Exception") || output.contains("anyType")
						|| output.contains("java") || output
							.contains("SoapFault"))) {
			switch (state) {

			case 0: // return date
				serviceUpdate = new UpdatingAllMaster(context);
				serviceUpdate.delegate = this;
				if (settings == null)
					settings = new Settings();
				String mStartDatesMaster = "";
				String mEndDatesMaster = "";
				mStartDatesMaster = (settings.getServerDate_Start_Anad() != null ? settings
						.getServerDate_Start_Anad() : "")
						+ "-"
						+ (settings.getServerDate_Start_Froum() != null ? settings
								.getServerDate_Start_Froum() : "")
						+ "-"
						+ (settings.getServerDate_Start_News() != null ? settings
								.getServerDate_Start_News() : "")
						+ "-"
						+ (settings.getServerDate_Start_Object() != null ? settings
								.getServerDate_Start_Object() : "")
						+ "-"
						+ (settings.getServerDate_Start_Paper() != null ? settings
								.getServerDate_Start_Paper() : "")
						+ "-"
						+ (settings.getServerDate_Start_Ticket() != null ? settings
								.getServerDate_Start_Ticket() : "")
						+ "-"
						+ (settings.getServerDate_Start_Users() != null ? settings
								.getServerDate_Start_Users() : "") + "-";

				mEndDatesMaster = (settings.getServerDate_End_Anad() != null ? settings
						.getServerDate_End_Anad() : "")
						+ "-"
						+ (settings.getServerDate_End_Froum() != null ? settings
								.getServerDate_End_Froum() : "")
						+ "-"
						+ (settings.getServerDate_End_News() != null ? settings
								.getServerDate_End_News() : "")
						+ "-"
						+ (settings.getServerDate_End_Object() != null ? settings
								.getServerDate_End_Object() : "")
						+ "-"
						+ (settings.getServerDate_End_Paper() != null ? settings
								.getServerDate_End_Paper() : "")
						+ "-"
						+ (settings.getServerDate_End_Ticket() != null ? settings
								.getServerDate_End_Ticket() : "")
						+ "-"
						+ (settings.getServerDate_End_Users() != null ? settings
								.getServerDate_End_Users() : "") + "-";

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
				mStartDatesDetail = (settings.getServerDate_Start_CmtInPaper() != null ? settings
						.getServerDate_Start_CmtInPaper() : "")
						+ "-"
						+ (settings.getServerDate_Start_CommentInFroum() != null ? settings
								.getServerDate_Start_CommentInFroum() : "")
						+ "-"
						+ (settings.getServerDate_Start_CommentInObject() != null ? settings
								.getServerDate_Start_CommentInObject() : "")
						+ "-"
						+ (settings.getServerDate_Start_LikeInFroum() != null ? settings
								.getServerDate_Start_LikeInFroum() : "")
						+ "-"
						+ (settings.getServerDate_Start_LikeInObject() != null ? settings
								.getServerDate_Start_LikeInObject() : "")
						+ "-"
						+ (settings.getServerDate_Start_LikeInPaper() != null ? settings
								.getServerDate_Start_LikeInPaper() : "")
						+ "-"
						+ (settings.getServerDate_Start_LikeInComment() != null ? settings
								.getServerDate_Start_LikeInComment() : "")
						+ "-"
						+ (settings.getServerDate_Start_LikeInCommentObject() != null ? settings
								.getServerDate_Start_LikeInCommentObject() : "")
						+ "-"
						+ (settings.getServerDate_Start_ObjectInCity() != null ? settings
								.getServerDate_Start_ObjectInCity() : "");

				mEndDatesDetail = settings.getServerDate_End_CmtInPaper() != null ? settings
						.getServerDate_End_CmtInPaper()
						: ""
								+ "-"
								+ (settings.getServerDate_End_CommentInFroum() != null ? settings
										.getServerDate_End_CommentInFroum()
										: "")
								+ "-"
								+ (settings.getServerDate_End_CommentInObject() != null ? settings
										.getServerDate_End_CommentInObject()
										: "")
								+ "-"
								+ (settings.getServerDate_End_LikeInFroum() != null ? settings
										.getServerDate_End_LikeInFroum() : "")
								+ "-"
								+ (settings.getServerDate_End_LikeInObject() != null ? settings
										.getServerDate_End_LikeInObject() : "")
								+ "-"
								+ (settings.getServerDate_End_LikeInPaper() != null ? settings
										.getServerDate_End_LikeInPaper() : "")
								+ "-"
								+ (settings.getServerDate_End_LikeInComment() != null ? settings
										.getServerDate_End_LikeInComment() : "")
								+ "-"
								+ (settings
										.getServerDate_End_LikeInCommentObject() != null ? settings
										.getServerDate_End_LikeInCommentObject()
										: "")
								+ "-"
								+ (settings.getServerDate_End_ObjectInCity() != null ? settings
										.getServerDate_End_ObjectInCity() : "");

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

	@SuppressWarnings("null")
	public int CreateFile(byte[] byteImage, int ImageId,
			String nameMainDirectory, String SubFolder, String nameFile,
			String TableName) {

		// تایپ برای مشخص کردن جدول ذخیره سازی آدرس تصاویر می باشد
		// List<Integer> itemID = new ArrayList<Integer>();

		// پیدا کردن نام پکیج پروژه
		String namePackage = context.getApplicationContext().getPackageName();

		// پیدا کردن آدرس SD
		File root = android.os.Environment.getExternalStorageDirectory();

		// پیدا کردن و ایجاد آدرس پوشه پکیج
		File FolderPackage = new File(root.getPath() + "/Android/data/"
				+ namePackage + "/");

		if (!FolderPackage.exists()) {
			FolderPackage.mkdirs(); // build directory
		}

		// پیدا کردن و ایجادآدرس پوشه اصلی
		File mainDirectory = new File(FolderPackage.getPath() + "/"
				+ nameMainDirectory + "/");
		if (!mainDirectory.exists()) {
			mainDirectory.mkdirs(); // build directory
		}

		// پیدا کردن و ایجاد آدرس پوشه فرعی
		File agahiDirectory = new File(mainDirectory.getPath() + "/"
				+ SubFolder + "/");

		if (!agahiDirectory.exists()) {
			agahiDirectory.mkdirs(); // build directory
		}

		byte[] zeroByte = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

		boolean retval = Arrays.equals(byteImage, zeroByte);

		if (retval == false) {

			// byte[] array = byteImage;

			// BitmapFactory.decodeByteArray(array, 0, array.length);

			// you can create a new file name "test.jpg" in sdcard folder.
			File f = new File(agahiDirectory + File.separator + nameFile
					+ String.valueOf(ImageId) + ".jpg");
			// itemID.add(ImageId);
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// write the bytes in file
			FileOutputStream fo = null;
			try {
				fo = new FileOutputStream(f);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				fo.write(byteImage);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// remember close de FileOutput
			try {
				fo.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Toast.makeText(
					context,
					"عکس شماره " + String.valueOf(ImageId)
							+ "با موفقیت ثبت شد  ", 0).show();

			if (TableName.equals("Anad")) {

				adapter.open();
				adapter.UpdateImagePathToDb(ImageId, f.getPath());
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
			return ImageId;

		} else
			return 0;

	}

	public RelativeLayout timeLineDrawing(Activity activity) {

		final RelativeLayout timeLine = (RelativeLayout) activity
				.findViewById(R.id.timeline);
		timeLine.setVisibility(View.VISIBLE);

		return timeLine;

	}
}
