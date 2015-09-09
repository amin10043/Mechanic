package com.project.mechanic.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.adapter.MainListAdapter;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class MainFragment extends Fragment {

	DataBaseAdapter dbAdapter;
	MainListAdapter ListAdapter;
	LinearLayout footer_layLayout;
	Utility util;
	RelativeLayout mainRow1, mainRow2, mainRow3, mainRow4, mainRow5, mainRow6,
			mainRow7, mainRow8;
	RelativeLayout.LayoutParams r1, r2, r3, r4, r5, r6, r7, r8;
	ImageView img1, img2, img3, img4, img5, img6, img7;

	TextView tite1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		util = new Utility(getActivity());
		View view = inflater.inflate(R.layout.fragment_main_page, null);

		mainRow1 = (RelativeLayout) view.findViewById(R.id.relative_item1);
		mainRow2 = (RelativeLayout) view.findViewById(R.id.relative_item2);
		mainRow3 = (RelativeLayout) view.findViewById(R.id.relative_item3);
		mainRow4 = (RelativeLayout) view.findViewById(R.id.relative_item4);
		mainRow5 = (RelativeLayout) view.findViewById(R.id.relative_item5);
		mainRow6 = (RelativeLayout) view.findViewById(R.id.relative_item6);
		mainRow7 = (RelativeLayout) view.findViewById(R.id.relative_item7);
		mainRow8 = (RelativeLayout) view.findViewById(R.id.relative_item8);

		r1 = new RelativeLayout.LayoutParams(mainRow1.getLayoutParams());
		r2 = new RelativeLayout.LayoutParams(mainRow2.getLayoutParams());
		r3 = new RelativeLayout.LayoutParams(mainRow3.getLayoutParams());
		r4 = new RelativeLayout.LayoutParams(mainRow4.getLayoutParams());
		r5 = new RelativeLayout.LayoutParams(mainRow5.getLayoutParams());
		r6 = new RelativeLayout.LayoutParams(mainRow6.getLayoutParams());
		r7 = new RelativeLayout.LayoutParams(mainRow7.getLayoutParams());
		r8 = new RelativeLayout.LayoutParams(mainRow8.getLayoutParams());

		img1 = (ImageView) view.findViewById(R.id.icon_item1);
		img2 = (ImageView) view.findViewById(R.id.icon_item2);
		img3 = (ImageView) view.findViewById(R.id.icon_item3);
		img4 = (ImageView) view.findViewById(R.id.icon_item4);
		img5 = (ImageView) view.findViewById(R.id.icon_item5);
		img6 = (ImageView) view.findViewById(R.id.icon_item6);
		img7 = (ImageView) view.findViewById(R.id.icon_item7);

		r1.width = util.getScreenwidth() / 10;
		r1.height = util.getScreenwidth() / 10;
		r1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		r1.setMargins(5, 0, 1, 0);
		r1.addRule(RelativeLayout.CENTER_VERTICAL);
		img1.setLayoutParams(r1);

		r2.width = util.getScreenwidth() / 10;
		r2.height = util.getScreenwidth() / 10;
		r2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		r2.setMargins(5, 0, 1, 0);
		r2.addRule(RelativeLayout.CENTER_VERTICAL);
		img2.setLayoutParams(r2);

		r3.width = util.getScreenwidth() / 10;
		r3.height = util.getScreenwidth() / 10;
		r3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		r3.setMargins(5, 0, 1, 0);
		r3.addRule(RelativeLayout.CENTER_VERTICAL);
		img3.setLayoutParams(r3);

		r4.width = util.getScreenwidth() / 10;
		r4.height = util.getScreenwidth() / 10;
		r4.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		r4.setMargins(5, 0, 1, 0);
		r4.addRule(RelativeLayout.CENTER_VERTICAL);
		img4.setLayoutParams(r4);

		r5.width = util.getScreenwidth() / 10;
		r5.height = util.getScreenwidth() / 10;
		r5.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		r5.setMargins(5, 0, 1, 0);
		r5.addRule(RelativeLayout.CENTER_VERTICAL);
		img5.setLayoutParams(r5);

		r6.width = util.getScreenwidth() / 10;
		r6.height = util.getScreenwidth() / 10;
		r6.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		r6.setMargins(5, 0, 1, 0);
		r6.addRule(RelativeLayout.CENTER_VERTICAL);
		img6.setLayoutParams(r6);

		r7.width = util.getScreenwidth() / 10;
		r7.height = util.getScreenwidth() / 10;
		r7.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		r7.setMargins(5, 0, 1, 0);
		r7.addRule(RelativeLayout.CENTER_VERTICAL);
		img7.setLayoutParams(r7);

		final SharedPreferences sendData = getActivity().getSharedPreferences(
				"Id", 0);

		mainRow1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				int id = 1;
				FragmentTransaction trans = ((MainActivity) getActivity())
						.getSupportFragmentManager().beginTransaction();
				BerandFragment fragment = new BerandFragment();
				Bundle bundle = new Bundle();
				bundle.putString("Id", String.valueOf(id));
				fragment.setArguments(bundle);

				sendData.edit().putInt("main_Id", id).commit();

				trans.replace(R.id.content_frame, fragment);
				trans.addToBackStack(null);
				trans.commit();
			}
		});

		mainRow2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				int id = 2;

				FragmentTransaction trans = ((MainActivity) getActivity())
						.getSupportFragmentManager().beginTransaction();
				Fragment ostan = new ProvinceFragment();

				sendData.edit().putInt("main_Id", id).commit();

				trans.addToBackStack(null);
				trans.replace(R.id.content_frame, ostan);

				trans.commit();
			}
		});

		mainRow3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				int id = 3;

				FragmentTransaction trans = ((MainActivity) getActivity())
						.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new Province2Fragment());

				sendData.edit().putInt("main_Id", id).commit();

				trans.addToBackStack(null);
				trans.commit();

			}
		});

		mainRow4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				int id = 4;

				FragmentTransaction trans = ((MainActivity) getActivity())
						.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new Province3Fragment());

				sendData.edit().putInt("main_Id", id).commit();

				trans.addToBackStack(null);
				trans.commit();

			}
		});
		mainRow5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				int id = 5;
				FragmentTransaction trans = ((MainActivity) getActivity())
						.getSupportFragmentManager().beginTransaction();
				NewsFragment fragment = new NewsFragment();
				Bundle bundle = new Bundle();
				bundle.putString("Id", String.valueOf(id));
				fragment.setArguments(bundle);

				sendData.edit().putInt("main_Id", id).commit();

				trans.replace(R.id.content_frame, fragment);
				trans.addToBackStack(null);
				trans.commit();

			}
		});

		mainRow6.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				int id = 6;

				FragmentTransaction trans = ((MainActivity) getActivity())
						.getSupportFragmentManager().beginTransaction();
				CountryFragment fragment = new CountryFragment();
				// Bundle bundle = new Bundle();
				// bundle.putString("Id", String.valueOf(id));
				// fragment.setArguments(bundle);

				sendData.edit().putInt("main_Id", id).commit();

				trans.replace(R.id.content_frame, fragment);
				trans.addToBackStack(null);
				trans.commit();
			}
		});

		mainRow7.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				int id = 7;
				FragmentTransaction trans = ((MainActivity) getActivity())
						.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new TypeFroum());

				sendData.edit().putInt("main_Id", id).commit();

				trans.addToBackStack(null);
				trans.commit();
			}
		});

		// dbAdapter = new DataBaseAdapter(getActivity());
		//
		// dbAdapter.open();
		// List<ListItem> mylist = dbAdapter.getListItemsById(0);
		// dbAdapter.close();
		//
		// ListView lstMain = (ListView) view.findViewById(R.id.lstMain);
		// ListAdapter = new MainListAdapter(getActivity(),
		// R.layout.main_item_list, mylist);
		//
		// lstMain.setAdapter(ListAdapter);
		// ImageView v = (ImageView) view.findViewById(R.id.imgAdverst);
		// v.getLayoutParams().height = util.getScreenHeightWithPadding() / 9;
		// v.requestLayout();

		return view;
	}

}