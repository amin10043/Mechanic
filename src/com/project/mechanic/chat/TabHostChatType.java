package com.project.mechanic.chat;

import com.project.mechanic.R;
import com.project.mechanic.StaticValues;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class TabHostChatType extends FragmentActivity implements ActionBar.TabListener {

	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;
	private ActionBar actionBar;
	// private String[] tabs = { "علاقه مندی", "خانواده", "دوستان", "گروه",
	// "تالار", "درخواست دوستی", "پیشنهاد",
	// "مخاطبین" };

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle arg0) {

		super.onCreate(arg0);
		setContentView(R.layout.tabhost_chat_type_fragment);
		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

		viewPager.setAdapter(mAdapter);
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Adding Tabs
		for (int i = 0; i < mAdapter.getCount(); i++/* String tab_name : tabs */) {
			actionBar.addTab(actionBar.newTab().setText(StaticValues.nameTabsChat[i]).setTabListener(this));

		}

		actionBar.setDisplayUseLogoEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		// actionBar.setDisplayShowCustomEnabled(false);

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

}
