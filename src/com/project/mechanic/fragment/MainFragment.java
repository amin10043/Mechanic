package com.project.mechanic.fragment;

import android.content.SharedPreferences;
import android.graphics.Typeface;
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
			mainRow7;
	RelativeLayout.LayoutParams r1, r2, r3, r4, r5, r6, r7, r8;
	ImageView img1, img2, img3, img4, img5, img6, img7;

	TextView tite1, lable1, lable2, lable3, lable4, lable5, lable6, lable7;
	View view;
	SharedPreferences sendData;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		util = new Utility(getActivity());

		sendData = getActivity().getSharedPreferences("Id", 0);
		view = inflater.inflate(R.layout.fragment_main_page, null);

		// define Views : find view by Id Items
		findView();

		// define Layout Params
		DefineLayoutParams();

		// setLayout Params
		setLayoutParams();

		// set Font title
		setFont();

		// on click
		onClick();

		util.ShowFooterAgahi(getActivity(), true, 1);

		return view;
	}

	private void findView() {

		mainRow1 = (RelativeLayout) view.findViewById(R.id.relative_item1);
		mainRow2 = (RelativeLayout) view.findViewById(R.id.relative_item2);
		mainRow3 = (RelativeLayout) view.findViewById(R.id.relative_item3);
		mainRow4 = (RelativeLayout) view.findViewById(R.id.relative_item4);
		mainRow5 = (RelativeLayout) view.findViewById(R.id.relative_item5);
		mainRow6 = (RelativeLayout) view.findViewById(R.id.relative_item6);
		mainRow7 = (RelativeLayout) view.findViewById(R.id.relative_item7);

		img1 = (ImageView) view.findViewById(R.id.icon_item1);
		img2 = (ImageView) view.findViewById(R.id.icon_item2);
		img3 = (ImageView) view.findViewById(R.id.icon_item3);
		img4 = (ImageView) view.findViewById(R.id.icon_item4);
		img5 = (ImageView) view.findViewById(R.id.icon_item5);
		img6 = (ImageView) view.findViewById(R.id.icon_item6);
		img7 = (ImageView) view.findViewById(R.id.icon_item7);

		lable1 = (TextView) view.findViewById(R.id.lable1);
		lable2 = (TextView) view.findViewById(R.id.lable2);
		lable3 = (TextView) view.findViewById(R.id.lable3);
		lable4 = (TextView) view.findViewById(R.id.lable4);
		lable5 = (TextView) view.findViewById(R.id.lable5);
		lable6 = (TextView) view.findViewById(R.id.lable6);
		lable7 = (TextView) view.findViewById(R.id.lable7);
	}

	private void DefineLayoutParams() {

		r1 = new RelativeLayout.LayoutParams(mainRow1.getLayoutParams());
		r2 = new RelativeLayout.LayoutParams(mainRow2.getLayoutParams());
		r3 = new RelativeLayout.LayoutParams(mainRow3.getLayoutParams());
		r4 = new RelativeLayout.LayoutParams(mainRow4.getLayoutParams());
		r5 = new RelativeLayout.LayoutParams(mainRow5.getLayoutParams());
		r6 = new RelativeLayout.LayoutParams(mainRow6.getLayoutParams());
		r7 = new RelativeLayout.LayoutParams(mainRow7.getLayoutParams());

	}

	private void setLayoutParams() {

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
	}

	private void setFont() {

		lable1.setTypeface(util.SetFontCasablanca());
		lable2.setTypeface(util.SetFontCasablanca());
		lable3.setTypeface(util.SetFontCasablanca());
		lable4.setTypeface(util.SetFontCasablanca());
		lable5.setTypeface(util.SetFontCasablanca());
		lable6.setTypeface(util.SetFontCasablanca());
		lable7.setTypeface(util.SetFontCasablanca());
	}

	private void onClick() {

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

	}

}