package com.project.mechanic;

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

import com.project.mechanic.adapter.SlideMenuAdapter;
import com.project.mechanic.fragment.CityFragment;
import com.project.mechanic.fragment.Dialog_notification;
import com.project.mechanic.fragment.Dialog_notificationlike;
import com.project.mechanic.fragment.DisplayPersonalInformationFragment;
import com.project.mechanic.fragment.Favorite_Fragment;
import com.project.mechanic.fragment.FragmentAboutUs;
import com.project.mechanic.fragment.FragmentContactUs;
import com.project.mechanic.fragment.LoginFragment;
import com.project.mechanic.fragment.MainFragment;
import com.project.mechanic.fragment.ProvinceFragment;
import com.project.mechanic.fragment.SearchFragment;
import com.project.mechanic.model.DataBaseAdapter;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// final String title = "comment in froum";

		adapter = new DataBaseAdapter(this);
		slideadapter = new SlideMenuAdapter(this);

		adapter.open();

		int r = adapter.NumOfNewCmtInFroum();
		TextView txtcm = (TextView) findViewById(R.id.txtcm);
		txtcm.setText("" + r);

		int t = adapter.NumOfNewLikeInObject();
		TextView txtlike = (TextView) findViewById(R.id.txtlike);
		txtlike.setText("" + t);
		adapter.close();

		ImageButton iBtnmessage = (ImageButton) findViewById(R.id.iBtnmessage);
		iBtnmessage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// adapter.open();
				//
				// int r = adapter.NumOfNewCmtInFroum();
				// TextView txtcm = (TextView) findViewById(R.id.txtcm);
				// txtcm.setText("" + r);
				//
				// int t = adapter.NumOfNewLikeInObject1();
				// TextView txtlike = (TextView) findViewById(R.id.txtlike);
				// txtlike.setText("" + t);
				adapter.open();
				dialog = new Dialog_notification(MainActivity.this);

				// dialog.setTitle(title);

				dialog.show();
				int seen = 1;
				adapter.updatecmseentodb(seen);
				adapter.updatecmobjectseentodb(seen);
				adapter.updatecmpaperseentodb(seen);

				int r = adapter.NumOfNewCmtInFroum();
				TextView txtcm = (TextView) findViewById(R.id.txtcm);
				txtcm.setText("" + r);
				adapter.close();

			}
		});
		ImageButton iBtnNotification = (ImageButton) findViewById(R.id.iBtnNotification);
		iBtnNotification.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				adapter.open();
				dialog1 = new Dialog_notificationlike(MainActivity.this);

				dialog1.show();
				int seen = 1;
				adapter.updatelikeseentodb(seen);
				adapter.updatelikefroumseentodb(seen);
				adapter.updatelikepaperseentodb(seen);

				int t = adapter.NumOfNewLikeInObject();
				TextView txtlike = (TextView) findViewById(R.id.txtlike);
				txtlike.setText("" + t);
				adapter.close();

			}
		});

		util = new Utility(MainActivity.this);
		// mPlanetTitles =
		// getResources().getStringArray(R.array.MenuItems);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		// mDrawerList.setAdapter(new ArrayAdapter<String>(this,
		// R.layout.drawer_item, R.id.content, mPlanetTitles));

		mDrawerList.setAdapter(slideadapter);

		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		ImageButton iBtnMenu = (ImageButton) findViewById(R.id.iBtnMenu);
		// ImageButton iBtnShare = (ImageButton)
		// findViewById(R.id.iBtnShare);
		// ImageButton iBtnBack = (ImageButton)
		// findViewById(R.id.iBtnBack);
		// final ImageButton iBtnFavorite = (ImageButton)
		// findViewById(R.id.iBtnFavorite);
		final TextView txtTitle = (TextView) findViewById(R.id.txtTitleP);
		ImageView search = (ImageView) findViewById(R.id.sedarch_v);

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
				}
				Fragment fragment;
				FragmentManager fragmentManager;
				fragment = new SearchFragment();
				fragmentManager = getSupportFragmentManager();
				fragmentManager.beginTransaction()
						.replace(R.id.content_frame, fragment).commit();

				// Intent i = new Intent(MainActivity.this,
				// Search.class);
				//
				// i.putExtra("table", tableName);
				// startActivity(i);

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

		mHandler = new Handler();
		mHandler.postDelayed(mStatusChecker, mInterval);
	}

	Runnable mStatusChecker = new Runnable() {

		@Override
		public void run() {
			util.Notification();
			mHandler.postDelayed(mStatusChecker, mInterval);
		}
	};

	void startRepeatingTask() {
		mStatusChecker.run();
	}

	void stopRepeatingTask() {
		mHandler.removeCallbacks(mStatusChecker);
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

				// // SharedPreferences sendData =
				// this.getSharedPreferences("Id",
				// // 0);
				// //sendData.edit().putInt("main_Id",
				// Service).commit();
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

		case 3:

			fragment = new Favorite_Fragment();
			fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment).commit();

			break;

		case 4:
			fragment = new FragmentAboutUs();
			fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment).commit();
			break;

		case 5:
			fragment = new FragmentContactUs();
			fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment).commit();
			break;

		}

		mDrawerList.setItemChecked(position, true);
		mDrawerLayout.closeDrawer(mDrawerList);
	}

}
