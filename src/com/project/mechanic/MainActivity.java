package com.project.mechanic;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.project.mechanic.R.color;
import com.project.mechanic.fragment.HomeFragment;
import com.project.mechanic.fragment.MenuFragment;

public class MainActivity extends FragmentActivity {

	private String[] mPlanetTitles;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence title;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		title = getActionBar().getTitle();
		mPlanetTitles = getResources().getStringArray(R.array.MenuItems);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		// Set the adapter for the list view
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_item, R.id.content, mPlanetTitles));
		// Set the list's click listener
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_launcher, /* nav drawer icon to replace 'Up' caret */
		R.string.hello_world, /* "open drawer" description */
		R.string.app_name /* "close drawer" description */) {

			/** Called when a drawer has settled in a completely closed state. */

			public void onDrawerClosed(View view) {
				getActionBar().setTitle(title);
			}

			/** Called when a drawer has settled in a completely open state. */

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(R.string.strMenu);
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

		// Set the drawer toggle as the DrawerListener
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setIcon(color.transparent);

		FragmentTransaction trans = getSupportFragmentManager()
				.beginTransaction();
		trans.replace(R.id.content_frame, new HomeFragment());
		trans.commit();

	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Pass the event to ActionBarDrawerToggle, if it returns
		// true, then it has handled the app icon touch event
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle your other action bar items...
		// switch (item.getItemId()) {
		// case R.id.action_settings:
		// Toast.makeText(this, "Settings selected", Toast.LENGTH_LONG).show();
		// break;
		//
		// default:
		// break;
		// }
		return super.onOptionsItemSelected(item);
	}

	/** Swaps fragments in the main content view */

	private void selectItem(int position) {
		// create a new fragment and specify the planet to show based on
		// position
		Fragment fragment = new MenuFragment();
		Bundle args = new Bundle();
		args.putInt(MenuFragment.ARG_OS, position);
		fragment.setArguments(args);

		// Insert the fragment by replacing any existing fragment
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.content_frame, fragment).commit();

		// Highlight the selected item, update the title, and close the drawer
		mDrawerList.setItemChecked(position, true);
		getActionBar().setTitle((mPlanetTitles[position]));
		mDrawerLayout.closeDrawer(mDrawerList);
	}

}
