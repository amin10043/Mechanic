package com.project.mechanic;

import java.util.List;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.StrictMode;
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
import android.widget.TextView;
import android.widget.Toast;

import com.project.mechanic.adapter.SlideMenuAdapter;
import com.project.mechanic.entity.CommentInFroum;
import com.project.mechanic.entity.Users;
import com.project.mechanic.fragment.AdvisorTypeFragment;
import com.project.mechanic.fragment.BerandFragment;
import com.project.mechanic.fragment.City2Fragment;
import com.project.mechanic.fragment.City3Fragment;
import com.project.mechanic.fragment.CityFragment;
import com.project.mechanic.fragment.CountryFragment;
import com.project.mechanic.fragment.Dialog_notification;
import com.project.mechanic.fragment.Dialog_notificationlike;
import com.project.mechanic.fragment.DisplayPersonalInformationFragment;
import com.project.mechanic.fragment.EnterDialog;
import com.project.mechanic.fragment.ExecutertypeFragment;
import com.project.mechanic.fragment.ExitDialog;
import com.project.mechanic.fragment.Favorite_Fragment;
import com.project.mechanic.fragment.FragmentAboutUs;
import com.project.mechanic.fragment.FragmentContactUs;
import com.project.mechanic.fragment.FroumFragment;
import com.project.mechanic.fragment.FroumWithoutComment;
import com.project.mechanic.fragment.FroumtitleFragment;
import com.project.mechanic.fragment.IntroductionFragment;
import com.project.mechanic.fragment.LoginFragment;
import com.project.mechanic.fragment.MainBrandFragment;
import com.project.mechanic.fragment.MainFragment;
import com.project.mechanic.fragment.NewsFragment;
import com.project.mechanic.fragment.ObjectFragment;
import com.project.mechanic.fragment.PaperFragment;
import com.project.mechanic.fragment.PaperWithoutComment;
import com.project.mechanic.fragment.Province2Fragment;
import com.project.mechanic.fragment.Province3Fragment;
import com.project.mechanic.fragment.ProvinceFragment;
import com.project.mechanic.fragment.TitlepaperFragment;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.PeriodicTask;
import com.project.mechanic.utility.Utility;

public class MainActivity extends FragmentActivity {

	DataBaseAdapter adapter;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	Utility util;
	SlideMenuAdapter slideadapter;
	Dialog_notification dialog;
	Dialog_notificationlike dialog1;
	Users user;
	List<CommentInFroum> mylist;
	int t1, t2, t3, t;
	int r1, r2, r3, r;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
		adapter = new DataBaseAdapter(this);
		slideadapter = new SlideMenuAdapter(this);
		final ImageButton iBtnmessage = (ImageButton) findViewById(R.id.iBtnmessage);
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
				user = util.getCurrentUser();
				if (user == null) {
					Toast.makeText(MainActivity.this,
							"شما هنوز وارد نشده اید.", Toast.LENGTH_SHORT)
							.show();
					return;
				}

				adapter.open();
				dialog = new Dialog_notification(MainActivity.this, r, r1, r2);

				dialog.show();

				int seen = 1;
				adapter.updatecmseentodb(seen, user.getId());
				adapter.updatecmobjectseentodb(seen, user.getId());
				adapter.updatecmpaperseentodb(seen, user.getId());
				txtcm1.setVisibility(View.GONE);
				iBtnmessage.setEnabled(false);

				adapter.close();

			}
		});

		final ImageButton iBtnNotification = (ImageButton) findViewById(R.id.iBtnNotification);
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
				adapter.open();

				dialog1 = new Dialog_notificationlike(MainActivity.this, t, t1,
						t2);

				dialog1.show();
				int seen = 1;
				adapter.updatelikeseentodb(seen, user.getId());
				adapter.updatelikefroumseentodb(seen, user.getId());
				adapter.updatelikepaperseentodb(seen, user.getId());
				txtlike.setVisibility(View.GONE);
				iBtnNotification.setEnabled(false);

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
		// ImageView search = (ImageView) findViewById(R.id.sedarch_v);

		// search.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		//
		// Fragment f = getSupportFragmentManager().findFragmentById(
		// R.id.content_frame);
		//
		// String tableName = "";
		// if (f instanceof ProvinceFragment) {
		// tableName = "Province";
		// } else if (f instanceof CityFragment) {
		// tableName = "City";
		// } else if (f instanceof ObjectFragment) {
		// tableName = "Object";
		// }
		//
		// Fragment fragment;
		// FragmentManager fragmentManager;
		// fragment = new SearchFragment();
		// fragmentManager = getSupportFragmentManager();
		// fragmentManager.beginTransaction()
		// .replace(R.id.content_frame, fragment).commit();
		//
		// }
		// });

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

		// Service for period task EveryTime
		// Intent intent = new Intent(MainActivity.this, HelloService.class);
		// startService(intent);
		// Service for period task EveryTime

		// Second Service
		// Calendar calendar = Calendar.getInstance();
		//
		// calendar.set(Calendar.HOUR_OF_DAY, 10);
		// calendar.set(Calendar.MINUTE, 43);
		// calendar.set(Calendar.SECOND, 00);
		// calendar.set(Calendar.AM_PM, Calendar.AM);
		//
		// Intent myIntent = new Intent(MainActivity.this, MyReceiver.class);
		// pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0,
		// myIntent, 0);
		// AlarmManager alarmManager = (AlarmManager)
		// getSystemService(ALARM_SERVICE);
		// alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(),
		// pendingIntent);
		// Second Service

		new PeriodicTask(this);
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
				Bundle bundle = new Bundle();
				bundle.putInt("0", 0);
				fragment.setArguments(bundle);
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
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction trans = fm.beginTransaction();
		Fragment f = fm.findFragmentById(R.id.content_frame);

		// String page = "";
		if (f instanceof MainFragment) {
			// page = "FragmentOne";
			ConfirmAlert();
		}

		else {
			if (f instanceof FroumFragment) {
				trans.setCustomAnimations(R.anim.push_out_right,
						R.anim.pull_in_left);
				SharedPreferences shared = getSharedPreferences("Id", 0);
				FroumtitleFragment fragment = new FroumtitleFragment();
				int ListId = shared.getInt("Froum_List_Id", 0);
				Bundle b = new Bundle();
				b.putInt("Froum_List_Id", ListId);
				fragment.setArguments(b);
				trans.replace(R.id.content_frame, fragment);
				trans.commit();
			} else if (f instanceof FroumWithoutComment) {
				trans.setCustomAnimations(R.anim.push_out_right,
						R.anim.pull_in_left);
				SharedPreferences shared = getSharedPreferences("Id", 0);
				FroumtitleFragment fragment = new FroumtitleFragment();
				int ListId = shared.getInt("Froum_List_Id", 0);
				Bundle b = new Bundle();
				b.putInt("Froum_List_Id", ListId);
				fragment.setArguments(b);
				trans.replace(R.id.content_frame, fragment);
				trans.commit();

			} else if (f instanceof FroumtitleFragment) {
				trans.setCustomAnimations(R.anim.push_out_right,
						R.anim.pull_in_left);
				trans.replace(R.id.content_frame, new MainFragment());
				trans.commit();

			} else if (f instanceof BerandFragment) {
				trans.setCustomAnimations(R.anim.push_out_right,
						R.anim.pull_in_left);
				trans.replace(R.id.content_frame, new MainFragment());
				trans.commit();
			} else if (f instanceof MainBrandFragment) {
				trans.setCustomAnimations(R.anim.push_out_right,
						R.anim.pull_in_left);
				BerandFragment fragment = new BerandFragment();
				// Bundle b = new Bundle();
				// b.putString("Id", "1");
				// fragment.setArguments(b);
				trans.replace(R.id.content_frame, fragment);
				trans.commit();
			} else if (f instanceof IntroductionFragment) {
				trans.setCustomAnimations(R.anim.push_out_right,
						R.anim.pull_in_left);
				trans.replace(R.id.content_frame, new MainBrandFragment());
				trans.commit();
			} else if (f instanceof ProvinceFragment) {
				trans.setCustomAnimations(R.anim.push_out_right,
						R.anim.pull_in_left);
				trans.replace(R.id.content_frame, new MainFragment());
				trans.commit();
			} else if (f instanceof Province2Fragment) {
				trans.setCustomAnimations(R.anim.push_out_right,
						R.anim.pull_in_left);
				trans.replace(R.id.content_frame, new MainFragment());
				trans.commit();
			} else if (f instanceof Province3Fragment) {
				trans.setCustomAnimations(R.anim.push_out_right,
						R.anim.pull_in_left);
				trans.replace(R.id.content_frame, new MainFragment());
				trans.commit();
			} else if (f instanceof City3Fragment) {
				trans.setCustomAnimations(R.anim.push_out_right,
						R.anim.pull_in_left);
				trans.replace(R.id.content_frame, new MainFragment());
				trans.commit();
			} else if (f instanceof City2Fragment) {
				trans.setCustomAnimations(R.anim.push_out_right,
						R.anim.pull_in_left);
				trans.replace(R.id.content_frame, new Province2Fragment());
				trans.commit();
			} else if (f instanceof CityFragment) {
				trans.setCustomAnimations(R.anim.push_out_right,
						R.anim.pull_in_left);
				trans.replace(R.id.content_frame, new ProvinceFragment());
				trans.commit();
			} else if (f instanceof ObjectFragment) {
				trans.setCustomAnimations(R.anim.push_out_right,
						R.anim.pull_in_left);
				trans.replace(R.id.content_frame, new CityFragment());
				trans.commit();
			} else if (f instanceof AdvisorTypeFragment) {
				trans.setCustomAnimations(R.anim.push_out_right,
						R.anim.pull_in_left);
				trans.replace(R.id.content_frame, new City2Fragment());
				trans.commit();
			} else if (f instanceof ExecutertypeFragment) {
				trans.setCustomAnimations(R.anim.push_out_right,
						R.anim.pull_in_left);
				trans.replace(R.id.content_frame, new City3Fragment());
				trans.commit();
			} else if (f instanceof NewsFragment) {
				trans.setCustomAnimations(R.anim.push_out_right,
						R.anim.pull_in_left);
				trans.replace(R.id.content_frame, new MainFragment());
				trans.commit();
			} else if (f instanceof CountryFragment) {
				trans.setCustomAnimations(R.anim.push_out_right,
						R.anim.pull_in_left);
				trans.replace(R.id.content_frame, new MainFragment());
				trans.commit();
			} else if (f instanceof NewsFragment) {
				trans.setCustomAnimations(R.anim.push_out_right,
						R.anim.pull_in_left);
				trans.replace(R.id.content_frame, new MainFragment());
				trans.commit();
			} else if (f instanceof TitlepaperFragment) {
				trans.setCustomAnimations(R.anim.push_out_right,
						R.anim.pull_in_left);
				trans.replace(R.id.content_frame, new NewsFragment());
				trans.commit();
			} else if (f instanceof PaperWithoutComment) {
				trans.setCustomAnimations(R.anim.push_out_right,
						R.anim.pull_in_left);
				trans.replace(R.id.content_frame, new TitlepaperFragment());
				trans.commit();
			} else if (f instanceof PaperFragment) {
				trans.setCustomAnimations(R.anim.push_out_right,
						R.anim.pull_in_left);
				trans.replace(R.id.content_frame, new TitlepaperFragment());
				trans.commit();
			} else {
				super.onBackPressed();
			}
		}
	}

	private void ConfirmAlert() {

		ExitDialog exDialog = new ExitDialog(MainActivity.this);

		exDialog.show();
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();

		Toast.makeText(this, "LOW MEMORY2", Toast.LENGTH_SHORT).show();
	}
}
