package com.project.mechanic.fragment;

import com.project.mechanic.R;
import com.project.mechanic.entity.Object;
import com.project.mechanic.entity.Users;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class UnavailableIntroduction extends Fragment {
	DataBaseAdapter dbAdapter;
	int ObjectId;
	Utility util;

	View rootView;
	Button editBtn;
	Object object;
	Users currentUser;
	RelativeLayout profileRelative;
	ImageView profileImg;
	RelativeLayout.LayoutParams profileParams, followParams, shareParams;
	RelativeLayout khadamatBtn;
	RelativeLayout namayandegiBtn;
	TextView namePage;
	Button followPage, shareBtn;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_is_active_introduction,
				null);

		if (getArguments().getString("Id") != null)
			ObjectId = Integer.valueOf(getArguments().getString("Id"));

		initValues();
		layoutParams();

		dbAdapter.open();
		Object object = dbAdapter.getObjectbyid(ObjectId);
		dbAdapter.close();
		click();

		if (currentUser == null || object.getUserId() != currentUser.getId()) {
			editBtn.setVisibility(View.GONE);
		} else {
			editBtn.setVisibility(View.VISIBLE);

		}

		if (object.getImagePath2() != null) {
			Bitmap bitmap = BitmapFactory.decodeFile(object.getImagePath2());

			profileImg.setImageBitmap(bitmap);
			profileImg.setLayoutParams(profileParams);

		} else {
			profileImg.setBackgroundResource(R.drawable.no_img_profile);
			profileImg.setLayoutParams(profileParams);

		}
		
		if (object.getObjectBrandTypeId() == 1) {
			namayandegiBtn.setVisibility(View.VISIBLE);
			khadamatBtn.setVisibility(View.VISIBLE);

		}
		if (object.getObjectBrandTypeId() == 2) {
			namayandegiBtn.setVisibility(View.GONE);
			khadamatBtn.setVisibility(View.GONE);
		}
		if (object.getObjectBrandTypeId() == 3) {
			namayandegiBtn.setVisibility(View.VISIBLE);
			khadamatBtn.setVisibility(View.GONE);
		}
		if (object.getObjectBrandTypeId() == 4) {
			namayandegiBtn.setVisibility(View.GONE);
			khadamatBtn.setVisibility(View.VISIBLE);
		}

		namePage.setText(object.getName());

		return rootView;
	}

	private void initValues() {

		dbAdapter = new DataBaseAdapter(getActivity());
		util = new Utility(getActivity());

		editBtn = (Button) rootView.findViewById(R.id.ImgbtnEdit);

		currentUser = util.getCurrentUser();

		profileImg = (ImageView) rootView.findViewById(R.id.icon_pro);

		khadamatBtn = (RelativeLayout) rootView.findViewById(R.id.Layoutlink2);
		namayandegiBtn = (RelativeLayout) rootView
				.findViewById(R.id.Layoutlink1);

		namePage = (TextView) rootView.findViewById(R.id.namePage);

		followPage = (Button) rootView.findViewById(R.id.followPage);

		shareBtn = (Button) rootView.findViewById(R.id.shareIntroduction);

	}

	private void layoutParams() {

		profileRelative = (RelativeLayout) rootView
				.findViewById(R.id.linear_id_profile_introduction_page);

		profileParams = new RelativeLayout.LayoutParams(
				profileRelative.getLayoutParams());

		profileParams.height = util.getScreenwidth() / 4;
		profileParams.width = util.getScreenwidth() / 4;
		profileParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		profileParams.addRule(RelativeLayout.BELOW, R.id.namePage);
		profileParams.setMargins(0, 10, 0, 0);


		followParams = new RelativeLayout.LayoutParams(
				profileRelative.getLayoutParams());
		
		followParams.width = util.getScreenwidth() / 4;

		followParams.height = util.getScreenwidth() / 10;
		followParams.setMargins(0, 10, 0, 0);
		followParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		followParams.addRule(RelativeLayout.BELOW, R.id.icon_pro);

		shareParams = new RelativeLayout.LayoutParams(
				profileRelative.getLayoutParams());
		shareParams.height = util.getScreenwidth() / 10;
		shareParams.width = util.getScreenwidth() / 4;
		shareParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		shareParams.addRule(RelativeLayout.BELOW, R.id.ImgbtnEdit);
		shareParams.setMargins(0, 10, 0, 0);

		profileImg.setLayoutParams(profileParams);
		followPage.setLayoutParams(followParams);
		shareBtn.setLayoutParams(shareParams);
		editBtn.setLayoutParams(followParams);

	}

	private void click() {

		namayandegiBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				FragmentTransaction trans = getActivity()
						.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new ProvinceFragment());
				trans.addToBackStack(null);

				trans.commit();
			}
		});

		khadamatBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				FragmentTransaction trans = getActivity()
						.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new ProvinceFragment());
				trans.addToBackStack(null);

				trans.commit();
			}
		});

		editBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				FragmentTransaction trans = getActivity()
						.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame,
						new IntroductionEditFragment(ObjectId));
				trans.addToBackStack(null);
				trans.commit();
			}
		});

	}
}
