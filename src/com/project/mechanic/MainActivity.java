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
import android.widget.Toast;

import com.project.mechanic.adapter.SlideMenuAdapter;
import com.project.mechanic.entity.Users;
import com.project.mechanic.fragment.CityFragment;
import com.project.mechanic.fragment.Dialog_notification;
import com.project.mechanic.fragment.Dialog_notificationlike;
import com.project.mechanic.fragment.DisplayPersonalInformationFragment;
import com.project.mechanic.fragment.Favorite_Fragment;
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
	Users user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		adapter = new DataBaseAdapter(this);
		slideadapter = new SlideMenuAdapter(this);

		adapter = new DataBaseAdapter(this);
		slideadapter = new SlideMenuAdapter(this);

		util = new Utility(MainActivity.this);
		user = util.getCurrentUser();
		if (user != null) {
			util.setNoti(this, user.getId());
		}

		ImageButton iBtnmessage = (ImageButton) findViewById(R.id.iBtnmessage);
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

				dialog = new Dialog_notification(MainActivity.this);

				dialog.show();
				int seen = 1;
				adapter.updatecmseentodb(seen, user.getId());
				adapter.updatecmobjectseentodb(seen, user.getId());
				adapter.updatecmpaperseentodb(seen, user.getId());
				int r = adapter.NumOfNewCmtInFroum(user.getId());
				int r1 = adapter.NumOfNewCmtInObject(user.getId());
				int r2 = adapter.NumOfNewCmtInPaper(user.getId());
				int r3 = r + r1 + r2;
				TextView txtcm = (TextView) findViewById(R.id.txtcm);
				txtcm.setText("" + r3);

				adapter.close();

			}
		});
		ImageButton iBtnNotification = (ImageButton) findViewById(R.id.iBtnNotification);
		iBtnNotification.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (util.getCurrentUser() == null) {
					Toast.makeText(MainActivity.this,
							"شما هنوز وارد نشده اید.", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				adapter.open();
				dialog1 = new Dialog_notificationlike(MainActivity.this);

				dialog1.show();
				int seen = 1;
				adapter.updatelikeseentodb(seen, user.getId());
				adapter.updatelikefroumseentodb(seen, user.getId());
				adapter.updatelikepaperseentodb(seen, user.getId());

				int t = adapter.NumOfNewLikeInObject(user.getId());

				int t1 = adapter.NumOfNewLikeInFroum(user.getId());
				int t2 = adapter.NumOfNewLikeInPaper(user.getId());
				int t3 = t + t1 + t2;
				TextView txtlike = (TextView) findViewById(R.id.txtlike);
				txtlike.setText("" + t3);
				adapter.close();

			}

		});

		// mPlanetTitles = getResources().getStringArray(R.array.MenuItems);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		// mDrawerList.setAdapter(new ArrayAdapter<String>(this,
		// R.layout.drawer_item, R.id.content, mPlanetTitles));

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
		// ImageButton iBtnShare = (ImageButton) findViewById(R.id.iBtnShare);
		// ImageButton iBtnBack = (ImageButton) findViewById(R.id.iBtnBack);
		// final ImageButton iBtnFavorite = (ImageButton)
		// findViewById(R.id.iBtnFavorite);
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
				}
				Fragment fragment;
				FragmentManager fragmentManager;
				fragment = new SearchFragment();
				fragmentManager = getSupportFragmentManager();
				fragmentManager.beginTransaction()
						.replace(R.id.content_frame, fragment).commit();

				// Intent i = new Intent(MainActivity.this, Search.class);
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

		// iBtnShare.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// Intent sharingIntent = new Intent(
		// android.content.Intent.ACTION_SEND);
		// sharingIntent.setType("text/plain");
		// String shareBody = "Here is the share content body";
		// sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
		// "Subject Here");
		// sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
		// shareBody);
		// startActivity(Intent.createChooser(sharingIntent,
		// "اشتراک از طریق"));
		//
		// }
		// });
		//
		// iBtnBack.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// if (lastFragment != null) {
		// FragmentTransaction trans = getSupportFragmentManager()
		// .beginTransaction();
		// trans.replace(R.id.content_frame, lastFragment);
		// trans.addToBackStack(null);
		// trans.commit();
		// } else {
		// Intent intent = new Intent(MainActivity.this,
		// SplashActivity.class);
		// startActivity(intent);
		// }
		//
		// }
		// });
		//
		// iBtnFavorite.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// if (isFavorite) {
		// iBtnFavorite
		// .setImageResource(android.R.drawable.btn_star_big_off);
		// } else {
		//
		// iBtnFavorite
		// .setImageResource(android.R.drawable.btn_star_big_on);
		// }
		// isFavorite = !isFavorite;
		// }
		// });
		//
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
				// //sendData.edit().putInt("main_Id", Service).commit();
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
		case 5:
			fragment = new FragmentContactUs();
			fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment).commit();
		}

		mDrawerList.setItemChecked(position, true);
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	// public boolean onKeyDown(int keyCode, KeyEvent event) {
	// if (keyCode == KeyEvent.KEYCODE_BACK) {
	// exitByBackKey();
	//
	// // moveTaskToBack(false);
	//
	// return true;
	// }
	// return super.onKeyDown(keyCode, event);
	// }
	//
	// protected void exitByBackKey() {
	//
	// new AlertDialog.Builder(MainActivity.this)
	// .setTitle("خروج از برنامه")
	// .setMessage("آیا از خروج اطمینان دارید؟")
	// .setNegativeButton("خیر", null)
	// .setPositiveButton("بله",
	// new DialogInterface.OnClickListener() {
	//
	// public void onClick(DialogInterface arg0, int arg1) {
	// finish();
	// System.exit(0);
	// }
	// }).create().show();
	//
	// }

}
