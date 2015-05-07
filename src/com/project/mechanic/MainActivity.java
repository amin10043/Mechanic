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
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnCloseListener;
import android.widget.TextView;

import com.project.mechanic.adapter.SlideMenuAdapter;
import com.project.mechanic.fragment.Favorite_Fragment;
import com.project.mechanic.fragment.LoginFragment;
import com.project.mechanic.fragment.MainFragment;
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
	private int mInterval = 5000; // 5 seconds by default, can be changed later

	private Handler mHandler;
	SlideMenuAdapter slideadapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		adapter = new DataBaseAdapter(this);
		slideadapter = new SlideMenuAdapter(this);

		util = new Utility(MainActivity.this);
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
		SearchView searchV = (SearchView) findViewById(R.id.searchV);

		searchV.setOnSearchClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				txtTitle.setVisibility(View.GONE);

			}
		});

		searchV.setOnCloseListener(new OnCloseListener() {

			@Override
			public boolean onClose() {
				txtTitle.setVisibility(View.VISIBLE);
				return false;
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
			util.showNotification();
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

			// if ( util.getCurrentUser()!=null ) {
			//
			// // SharedPreferences sendData = this.getSharedPreferences("Id",
			// // 0);
			// //sendData.edit().putInt("main_Id", Service).commit();
			// fragment = new DisplayPersonalInformationFragment();
			// fragmentManager = getSupportFragmentManager();
			// fragmentManager.beginTransaction()
			// .replace(R.id.content_frame, fragment).commit();
			//
			//
			// }
			// else
			// {

			fragment = new LoginFragment();
			fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment).commit();
			// }
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
