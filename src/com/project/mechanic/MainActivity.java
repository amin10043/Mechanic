package com.project.mechanic;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
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
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.project.mechanic.fragment.MainFragment;
import com.project.mechanic.fragment.MenuFragment;
import com.project.mechanic.model.DataBaseAdapter;

public class MainActivity extends FragmentActivity {

	DataBaseAdapter adapter;
	private String[] mPlanetTitles;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	// private CharSequence title;
	private Fragment lastFragment;
	private boolean isFavorite = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		adapter = new DataBaseAdapter(this);

		mPlanetTitles = getResources().getStringArray(R.array.MenuItems);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_item, R.id.content, mPlanetTitles));

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
		ImageButton iBtnShare = (ImageButton) findViewById(R.id.iBtnShare);
		ImageButton iBtnBack = (ImageButton) findViewById(R.id.iBtnBack);
		final ImageButton iBtnFavorite = (ImageButton) findViewById(R.id.iBtnFavorite);

		iBtnMenu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (!mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
					mDrawerLayout.openDrawer(Gravity.RIGHT);
				}
			}
		});

		iBtnShare.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent sharingIntent = new Intent(
						android.content.Intent.ACTION_SEND);
				sharingIntent.setType("text/plain");
				String shareBody = "Here is the share content body";
				sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
						"Subject Here");
				sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
						shareBody);
				startActivity(Intent.createChooser(sharingIntent,
						"اشتراک از طریق"));

			}
		});

		iBtnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (lastFragment != null) {
					FragmentTransaction trans = getSupportFragmentManager()
							.beginTransaction();
					trans.replace(R.id.content_frame, lastFragment);
					trans.addToBackStack(null);
					trans.commit();
				} else {
					Intent intent = new Intent(MainActivity.this,
							SplashActivity.class);
					startActivity(intent);
				}

			}
		});

		iBtnFavorite.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (isFavorite) {
					iBtnFavorite
							.setImageResource(android.R.drawable.btn_star_big_off);
				} else {

					iBtnFavorite
							.setImageResource(android.R.drawable.btn_star_big_on);
				}
				isFavorite = !isFavorite;
			}
		});

		TextView txtTitle = (TextView) findViewById(R.id.txtTitle);
		txtTitle.setText(R.string.strMain);

		FragmentTransaction trans = getSupportFragmentManager()
				.beginTransaction();
		trans.replace(R.id.content_frame, new MainFragment());
		trans.addToBackStack(null);
		trans.commit();

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

		Fragment fragment = new MenuFragment();
		Bundle args = new Bundle();
		args.putInt(MenuFragment.ARG_OS, position);
		fragment.setArguments(args);

		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.content_frame, fragment).commit();

		mDrawerList.setItemChecked(position, true);
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	public void setLastFragment(Fragment fragment) {
		this.lastFragment = fragment;
	}
}
