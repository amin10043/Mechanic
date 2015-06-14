package com.project.mechanic;

import java.util.Calendar;
import java.util.List;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mechanic.adapter.SlideMenuAdapter;
import com.project.mechanic.entity.CommentInFroum;
import com.project.mechanic.entity.Users;
import com.project.mechanic.fragment.CityFragment;
import com.project.mechanic.fragment.Dialog_notification;
import com.project.mechanic.fragment.Dialog_notificationlike;
import com.project.mechanic.fragment.DisplayPersonalInformationFragment;
import com.project.mechanic.fragment.EnterDialog;
import com.project.mechanic.fragment.ExitDialog;
import com.project.mechanic.fragment.Favorite_Fragment;
import com.project.mechanic.fragment.FragmentAboutUs;
import com.project.mechanic.fragment.FragmentContactUs;
import com.project.mechanic.fragment.LoginFragment;
import com.project.mechanic.fragment.MainFragment;
import com.project.mechanic.fragment.ObjectFragment;
import com.project.mechanic.fragment.ProvinceFragment;
import com.project.mechanic.fragment.SearchFragment;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.HelloService;
import com.project.mechanic.service.MyReceiver;
import com.project.mechanic.utility.Utility;

public class MainActivity extends FragmentActivity {

	DataBaseAdapter adapter;
	private String[] mPlanetTitles;
	private DrawerLayout mDrawerLayout;

	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	// private CharSequence title;
	private Fragment lastFragment;
	private boolean isFavorite = false;
	Utility util;
	private int mInterval = 40000; // 5 seconds by default, can be changed later
	private Handler mHandler;
	SlideMenuAdapter slideadapter;
	Dialog_notification dialog;
	Dialog_notificationlike dialog1;
	Users user;
	private PendingIntent pendingIntent;
	List<CommentInFroum> mylist;
	int t1, t2, t3, t;
	int r1, r2, r3, r;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// this code is for lock rotate screen
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);

		adapter = new DataBaseAdapter(this);
		slideadapter = new SlideMenuAdapter(this);

		adapter = new DataBaseAdapter(this);
		slideadapter = new SlideMenuAdapter(this);
		ImageButton iBtnmessage = (ImageButton) findViewById(R.id.iBtnmessage);
		final TextView txtcm1 = (TextView) findViewById(R.id.txtcm);
		final TextView txtlike = (TextView) findViewById(R.id.txtlike);

		util = new Utility(MainActivity.this);
		user = util.getCurrentUser();
		if (user != null) {
			txtcm1.setVisibility(View.VISIBLE);
			txtlike.setVisibility(View.VISIBLE);
			util.setNoti(this, user.getId());

		} else {

			EnterDialog dialogEnter = new EnterDialog(MainActivity.this);
			dialogEnter.show();

		}

		iBtnmessage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				user = util.getCurrentUser();
				if (user == null) {
					Toast.makeText(MainActivity.this,
							"شما هنوز وارد نشده اید.", Toast.LENGTH_SHORT)
							.show();

					return;
				}

				adapter.open();
				// int r = adapter.NumOfNewCmtInFroum(user.getId());
				// int r1 = adapter.NumOfNewCmtInObject(user.getId());
				// int r2 = adapter.NumOfNewCmtInPaper(user.getId());
				// int r3 = r + r1 + r2;
				// TextView txtcm = (TextView) findViewById(R.id.txtcm);
				// txtcm.setText("" + r3);

				dialog = new Dialog_notification(MainActivity.this, r, r1, r2);

				dialog.show();

				// CommentInFroum a = (CommentInFroum) mylist;

				// int b = a.getFroumid();
				// Froum d = adapter.getFroumItembyid(b);
				// int e = d.getUserId();

				int seen = 1;
				adapter.updatecmseentodb(seen, user.getId());
				adapter.updatecmobjectseentodb(seen, user.getId());
				adapter.updatecmpaperseentodb(seen, user.getId());
				txtcm1.setVisibility(View.GONE);

				// int r3 = r + r1 + r2;
				// txtcm1.setText("" + r3);

				adapter.close();

			}
		});

		ImageButton iBtnNotification = (ImageButton) findViewById(R.id.iBtnNotification);
		iBtnNotification.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				user = util.getCurrentUser();
				if (user == null) {
					Toast.makeText(MainActivity.this,
							"شما هنوز وارد نشده اید.", Toast.LENGTH_SHORT)
							.show();
					return;
				}

				// TextView numlikef = (TextView) findViewById(R.id.numlikef);
				// TextView numlikeo = (TextView) findViewById(R.id.numlikeo);
				// TextView numlikep = (TextView) findViewById(R.id.numlikep);
				adapter.open();

				dialog1 = new Dialog_notificationlike(MainActivity.this, t, t1,
						t2);

				// int t3 = t + t1 + t2;
				// txtlike.setText("" + t3);

				dialog1.show();
				int seen = 1;
				adapter.updatelikeseentodb(seen, user.getId());
				adapter.updatelikefroumseentodb(seen, user.getId());
				adapter.updatelikepaperseentodb(seen, user.getId());
				txtlike.setVisibility(View.GONE);

				// int t = adapter.NumOfNewLikeInObject(user.getId());
				// int t = 4;
				// // String tt = String.valueOf(t);
				// int t1 = adapter.NumOfNewLikeInFroum(user.getId());
				// int t2 = adapter.NumOfNewLikeInPaper(user.getId());
				// int t3 = t + t1 + t2;
				//
				// TextView txtlike = (TextView) findViewById(R.id.txtlike);
				// txtlike.setText(String.valueOf(t3));
				// numlikef.setText(String.valueOf(t));
				// // numlikeo.setText("" + t1);
				// // numlikep.setText("" + t2);
				adapter.close();

			}

		});

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		mDrawerList.setAdapter(slideadapter);

		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_launcher, /* nav drawer icon to replace 'Up' caret */
		R.string.hello_world, /* "open drawer" description */
		R.string.app_name /* "close drawer" description */) {

			public void onDrawerClosed(View view) {
			}

			public void onDrawerOpened(View drawerView) {

			}

			@Override
			public boolean onOptionsItemSelected(MenuItem item) {

				if (item != null && item.getItemId() == android.R.id.home) {
					if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
						mDrawerLayout.closeDrawer(Gravity.RIGHT);
					} else {
						mDrawerLayout.openDrawer(Gravity.RIGHT);
					}
				}
				return false;
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		ImageButton iBtnMenu = (ImageButton) findViewById(R.id.iBtnMenu);
		final TextView txtTitle = (TextView) findViewById(R.id.txtTitleP);
		ImageView search = (ImageView) findViewById(R.id.sedarch_v);

		search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Fragment f = getSupportFragmentManager().findFragmentById(
						R.id.content_frame);

				String tableName = "";
				if (f instanceof ProvinceFragment) {
					tableName = "Province";
				} else if (f instanceof CityFragment) {
					tableName = "City";
				} else if (f instanceof ObjectFragment) {
					tableName = "Object";
				}

				Fragment fragment;
				FragmentManager fragmentManager;
				fragment = new SearchFragment();
				fragmentManager = getSupportFragmentManager();
				fragmentManager.beginTransaction()
						.replace(R.id.content_frame, fragment).commit();

			}
		});

		iBtnMenu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (!mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
					mDrawerLayout.openDrawer(Gravity.RIGHT);
				}
			}
		});

		txtTitle.setText(R.string.strMain);

		FragmentTransaction trans = getSupportFragmentManager()
				.beginTransaction();
		trans.replace(R.id.content_frame, new MainFragment());
		trans.addToBackStack(null);
		trans.commit();

		setActivityTitle(R.string.strMain);

		Intent intent = new Intent(MainActivity.this, HelloService.class);
		startService(intent);

		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.HOUR_OF_DAY, 3);
		calendar.set(Calendar.MINUTE, 26);
		calendar.set(Calendar.SECOND, 00);
		calendar.set(Calendar.AM_PM, Calendar.PM);

		Intent myIntent = new Intent(MainActivity.this, MyReceiver.class);
		pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0,
				myIntent, 0);

		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(),
				pendingIntent);
	}

	public void setActivityTitle(int title) {
		TextView txtTitle = (TextView) findViewById(R.id.txtTitleP);
		txtTitle.setText(title);

	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {

		@SuppressWarnings("rawtypes")
		@Override
		public void onItemClick(AdapterView parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	private void selectItem(int position) {

		Fragment fragment;
		FragmentManager fragmentManager;
		switch (position) {
		case 0:

			fragment = new MainFragment();
			fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment).commit();
			break;

		case 1:

			// ////////////////////////////////////////////////

			if (util.getCurrentUser() != null) {
				fragment = new DisplayPersonalInformationFragment();
				fragmentManager = getSupportFragmentManager();
				fragmentManager.beginTransaction()
						.replace(R.id.content_frame, fragment).commit();

				//
			} else {

				fragment = new LoginFragment();
				fragmentManager = getSupportFragmentManager();
				fragmentManager.beginTransaction()
						.replace(R.id.content_frame, fragment).commit();
			}
			break;

		case 2:

			fragment = new Favorite_Fragment();
			fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment).commit();

			break;

		case 3:
			fragment = new FragmentAboutUs();
			fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment).commit();
			break;

		case 4:
			fragment = new FragmentContactUs();
			fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment).commit();
			break;
		case 5:
			ConfirmAlert();
			break;
		}

		mDrawerList.setItemChecked(position, true);
		mDrawerLayout.closeDrawer(mDrawerList);

	}

	@Override
	public void onBackPressed() {
		Fragment f = getSupportFragmentManager().findFragmentById(
				R.id.content_frame);

		String page = "";
		if (f instanceof MainFragment) {
			page = "FragmentOne";
			ConfirmAlert();
		}

		else {
			super.onBackPressed();
		}
	}

	private void ConfirmAlert() {

		ExitDialog exDialog = new ExitDialog(MainActivity.this);

		exDialog.show();
		// AlertDialog.Builder builder = new AlertDialog.Builder(this);
		// builder.setMessage("آیا از خروج اطمینان دارید؟")
		// .setCancelable(false)
		// .setPositiveButton("بــــله",
		// new DialogInterface.OnClickListener() {
		//
		// public void onClick(DialogInterface dialog, int id) {
		// dialog.dismiss();
		// onYesClick();
		//
		// }
		//
		// })
		// .setNegativeButton("خــیر",
		// new DialogInterface.OnClickListener() {
		//
		// public void onClick(DialogInterface dialog, int id) {
		// dialog.cancel();
		// onNoClick();
		// }
		// });
		// AlertDialog alert = builder.create();
		// alert.show();
	}

	private void onYesClick() {
		Intent setIntent = new Intent(Intent.ACTION_MAIN);
		setIntent.addCategory(Intent.CATEGORY_HOME);
		setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(setIntent);

		MainActivity.this.finish();

	}

	private void onNoClick() {

	}
}
