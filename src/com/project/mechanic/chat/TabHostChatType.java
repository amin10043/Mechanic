package com.project.mechanic.chat;

import java.util.ArrayList;
import java.util.List;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.StaticValues;
import com.project.mechanic.adapter.SlideMenuAdapter;
import com.project.mechanic.chatAdapter.TabsPagerAdapter;
import com.project.mechanic.entity.Users;
import com.project.mechanic.fragment.DialogSettings;
import com.project.mechanic.fragment.Dialog_notification;
import com.project.mechanic.fragment.Dialog_notificationlike;
import com.project.mechanic.fragment.DisplayPersonalInformationFragment;
import com.project.mechanic.fragment.ExitDialog;
import com.project.mechanic.fragment.Favorite_Fragment;
import com.project.mechanic.fragment.FragmentAboutUs;
import com.project.mechanic.fragment.FragmentContactUs;
import com.project.mechanic.fragment.LoginFragment;
import com.project.mechanic.fragment.MainFragment;
import com.project.mechanic.fragment.PostTimelineFragment;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class TabHostChatType extends FragmentActivity implements ActionBar.TabListener {

	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;
	private ActionBar actionBar;
	View actionView;
	Utility util;
	DataBaseAdapter dbAdapter;
	int r1, r2, r3, r;
	int t1, t2, t3, t;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	SlideMenuAdapter slideadapter;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle arg0) {

		super.onCreate(arg0);
		setContentView(R.layout.tabhost_chat_type_fragment);
		util = new Utility(TabHostChatType.this);
		dbAdapter = new DataBaseAdapter(TabHostChatType.this);

		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

		viewPager.setAdapter(mAdapter);
		actionBar.setHomeButtonEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);

		////////////
		LayoutInflater mInflater = LayoutInflater.from(this);
		actionView = mInflater.inflate(R.layout.row_actionbar, null);
		actionBar.setCustomView(actionView);
		actionBar.setDisplayShowCustomEnabled(true);
		////////////

		// View tabView = mInflater.inflate(R.layout.row_tab, null);
		// TextView titleTab = (TextView) tabView.findViewById(R.id.tabtitle);
		// titleTab.setTypeface(util.SetFontCasablanca());

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Adding Tabs
		for (int i = 0; i < mAdapter.getCount(); i++) {

			// titleTab.setText(StaticValues.nameTabsChat[i]);
			actionBar.addTab(actionBar.newTab().setCustomView(titleText().get(i)).setTabListener(this));

		}

		actionBar.setDisplayShowTitleEnabled(false);

		actionBar.setIcon(R.color.orginal_hard_blue_color);
		actionBarItem();
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onPageSelected(int position) {
				// on changing the page
				// make respected tab selected
				actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});

		util.ShowFooterAgahi(this, false, 0);
	}

	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// on tab selected
		// show respected fragment view
		viewPager.setCurrentItem(tab.getPosition());
		// txt.setText(tab.getText());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}

	@SuppressLint("ResourceAsColor")
	private List<TextView> titleText() {

		List<TextView> titleList = new ArrayList<TextView>();

		// View tabView = getLayoutInflater().inflate(R.layout.row_tab, null);
		// TextView titleTab = (TextView) tabView.findViewById(R.id.tabtitle);

		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);

		lp.addRule(RelativeLayout.CENTER_IN_PARENT);

		for (int i = 0; i < StaticValues.nameTabsChat.length; i++) {

			TextView title = new TextView(TabHostChatType.this);

			title.setText(StaticValues.nameTabsChat[i]);
			title.setTypeface(util.SetFontCasablanca());
			title.setTextColor(R.color.white);
			title.setTextSize(18);
//			title.setLayoutParams(lp);

			titleList.add(title);

		}
		return titleList;

	}

	private void actionBarItem() {

		ImageView slideMenu = (ImageView) actionView.findViewById(R.id.iBtnMenu);
		ImageView chat = (ImageView) actionView.findViewById(R.id.private_chat);
		ImageView settings = (ImageView) actionView.findViewById(R.id.settings_icon);

		ImageButton message = (ImageButton) actionView.findViewById(R.id.iBtnmessage);
		ImageButton notification = (ImageButton) actionView.findViewById(R.id.iBtnNotification);
		ImageButton timeLine = (ImageButton) actionView.findViewById(R.id.timeline_btn);
		ImageButton iBtnMenu = (ImageButton) actionView.findViewById(R.id.iBtnMenu);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		slideadapter = new SlideMenuAdapter(this);

		settings.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				android.support.v4.app.FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new DialogSettings());

				trans.addToBackStack(null);
				trans.commit();
			}
		});

		chat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// FragmentTransaction trans =
				// getSupportFragmentManager().beginTransaction();
				// trans.replace(R.id.content_frame, new ExpandableListChat());
				//
				// trans.addToBackStack(null);
				// trans.commit();

				Intent firstpage = new Intent(TabHostChatType.this, TabHostChatType.class);
				startActivity(firstpage);
			}
		});

		timeLine.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				android.support.v4.app.FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new PostTimelineFragment());

				trans.addToBackStack(null);
				trans.commit();

			}
		});

		message.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Users user = util.getCurrentUser();
				if (user == null) {
					Toast.makeText(TabHostChatType.this, "ابتدا باید وارد شوید", Toast.LENGTH_SHORT).show();
					return;
				}

				dbAdapter.open();
				Dialog_notification dialog = new Dialog_notification(TabHostChatType.this, r, r1, r2);

				util.setSizeDialog(dialog);

				// int seen = 1;
				// adapter.updatecmseentodb(seen, user.getId());
				// adapter.updatecmobjectseentodb(seen, user.getId());
				// adapter.updatecmpaperseentodb(seen, user.getId());
				// txtcm1.setVisibility(View.GONE);
				// iBtnmessage.setEnabled(false);

				dbAdapter.close();

			}
		});

		notification.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Users user = util.getCurrentUser();
				if (user == null) {
					Toast.makeText(TabHostChatType.this, "ابتدا باید وارد شوید", Toast.LENGTH_SHORT).show();
					return;
				}
				dbAdapter.open();

				Dialog_notificationlike dialog1 = new Dialog_notificationlike(TabHostChatType.this, t, t1, t2);

				util.setSizeDialog(dialog1);
				// int seen = 1;
				// adapter.updatelikeseentodb(seen, user.getId());
				// adapter.updatelikefroumseentodb(seen, user.getId());
				// adapter.updatelikepaperseentodb(seen, user.getId());
				// txtlike.setVisibility(View.GONE);
				// iBtnNotification.setEnabled(false);

				dbAdapter.close();

			}

		});

		mDrawerList.setDivider(getResources().getDrawable(R.drawable.lili));
		mDrawerList.setAdapter(slideadapter);
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		iBtnMenu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
					mDrawerLayout.openDrawer(Gravity.RIGHT);
					// v.startAnimation(animation1);
					// animation1.start();

				}
			}
		});
	}

	private class DrawerItemClickListener implements ListView.OnItemClickListener {

		@SuppressWarnings("rawtypes")
		@Override
		public void onItemClick(AdapterView parent, View view, int position, long id) {
			selectItem(position);
		}
	}

	private void selectItem(int position) {

		Fragment fragment;
		FragmentManager fragmentManager;
		switch (position) {
		// case 0:
		//
		// break;

		case 0:
			// main page 0
			fragment = new MainFragment();
			fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

			break;

		case 1:

			// personal page 1
			if (util.getCurrentUser() != null) {

				fragment = new DisplayPersonalInformationFragment();
				fragmentManager = getSupportFragmentManager();
				// Bundle bundle = new Bundle();
				// bundle.putInt("0", 0);
				// fragment.setArguments(bundle);
				fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

				//
			} else {

				fragment = new LoginFragment();
				fragmentManager = getSupportFragmentManager();
				fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
			}
			break;

		case 2:
			// favorite 2

			if (util.getCurrentUser() != null) {
				fragment = new Favorite_Fragment();
				fragmentManager = getSupportFragmentManager();
				fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
			} else
				Toast.makeText(TabHostChatType.this, "ابتدا باید وارد شوید", 0).show();
			break;

		case 3:
			fragment = new FragmentContactUs();
			fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

			break;
		case 4:
			// contact us 4

			// about us 3
			fragment = new FragmentAboutUs();
			fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

			break;

		case 6:
			// contact us 5
			ConfirmAlert();
		}

		mDrawerList.setItemChecked(position, true);
		mDrawerLayout.closeDrawer(mDrawerList);

	}

	private void ConfirmAlert() {

		ExitDialog exDialog = new ExitDialog(TabHostChatType.this);

		util.setSizeDialog(exDialog);

		//

	}
}
