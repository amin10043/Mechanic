package com.project.mechanic.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mechanic.R;
import com.project.mechanic.adapter.DataPersonalExpandAdapter;
import com.project.mechanic.adapter.InformationUserAdapter;
import com.project.mechanic.adapter.ObjectListAdapter;
import com.project.mechanic.entity.Object;
import com.project.mechanic.entity.PersonalData;
import com.project.mechanic.entity.Users;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class InformationUser extends Fragment {

	ExpandableListView Expandview;

	View rootView, header;

	TextView txtaddress, txtcellphone, txtphone, txtemail, txtname, txtfax,
			txtdate /* , namePage */;

	ImageView profileImage;

	LinearLayout layoutImageProfile;

	int userIdPage;

	DataBaseAdapter dbadaAdapter;
	Utility util;
	Users userPage;

	ObjectListAdapter listAdapter;

	RelativeLayout phoneLayout, emailLayout, faxLayout, mobileLayout, AddressLayout, btnedit, birthDayUsers;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_information_user, null);
		header = inflater.inflate(R.layout.header_information, null);
		dbadaAdapter = new DataBaseAdapter(getActivity());
		util = new Utility(getActivity());

		if (getArguments().getInt("userId") != 0) {
			Bundle bundle = this.getArguments();
			userIdPage = bundle.getInt("userId", 0);
		}

		dbadaAdapter.open();
		userPage = dbadaAdapter.getUserbyid(userIdPage);
		dbadaAdapter.close();

		findView();
		informationUser();
		// Expandview.addHeaderView(header);
		setLayoutParams();
		allpageUser();

		profileImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				String PathProfile = userPage.getImagePath();
				String namePage = userPage.getName();
				DialogShowImage showImageDialog = new DialogShowImage(getActivity(), PathProfile, namePage);
				showImageDialog.show();

			}
		});

		util.ShowFooterAgahi(getActivity(), false, -1);

		return rootView;
	}

	private void setLayoutParams() {

		RelativeLayout.LayoutParams f1 = new RelativeLayout.LayoutParams(phoneLayout.getLayoutParams());
		RelativeLayout.LayoutParams f2 = new RelativeLayout.LayoutParams(mobileLayout.getLayoutParams());
		RelativeLayout.LayoutParams f3 = new RelativeLayout.LayoutParams(emailLayout.getLayoutParams());
		RelativeLayout.LayoutParams f4 = new RelativeLayout.LayoutParams(faxLayout.getLayoutParams());
		RelativeLayout.LayoutParams f5 = new RelativeLayout.LayoutParams(AddressLayout.getLayoutParams());

		f1.width = util.getScreenwidth();
		f1.height = LayoutParams.WRAP_CONTENT;
		f1.setMargins(50, 0, 50, 0);
		f1.addRule(RelativeLayout.CENTER_HORIZONTAL);
		f1.addRule(RelativeLayout.BELOW, R.id.textView6);

		f2.width = util.getScreenwidth();
		f2.height = LayoutParams.WRAP_CONTENT;
		f2.setMargins(50, 0, 50, 0);
		f2.addRule(RelativeLayout.CENTER_HORIZONTAL);
		f2.addRule(RelativeLayout.BELOW, R.id.textView8);

		f3.width = util.getScreenwidth();
		f3.height = LayoutParams.WRAP_CONTENT;
		f3.setMargins(50, 0, 50, 0);
		f3.addRule(RelativeLayout.CENTER_HORIZONTAL);
		f3.addRule(RelativeLayout.BELOW, R.id.textView4);

		f4.width = util.getScreenwidth();
		f4.height = LayoutParams.WRAP_CONTENT;
		f4.setMargins(50, 0, 50, 0);
		f4.addRule(RelativeLayout.CENTER_HORIZONTAL);
		f4.addRule(RelativeLayout.BELOW, R.id.textView10);

		f5.width = util.getScreenwidth();
		f5.height = LayoutParams.WRAP_CONTENT;
		f5.setMargins(50, 0, 50, 0);
		f5.addRule(RelativeLayout.CENTER_HORIZONTAL);
		f5.addRule(RelativeLayout.BELOW, R.id.textView12);

		ImageView l11 = (ImageView) header.findViewById(R.id.i1);
		ImageView l22 = (ImageView) header.findViewById(R.id.i2);
		ImageView l33 = (ImageView) header.findViewById(R.id.i3);
		ImageView l44 = (ImageView) header.findViewById(R.id.i4);
		ImageView l55 = (ImageView) header.findViewById(R.id.i5);

		l11.setLayoutParams(f1);
		l22.setLayoutParams(f2);
		l33.setLayoutParams(f3);
		l44.setLayoutParams(f4);
		l55.setLayoutParams(f5);

		// LinearLayout imageLinear = (LinearLayout) header
		// .findViewById(R.id.imageLinear);
		//
		// LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
		// imageLinear.getLayoutParams());
		// llp.width = (int) (util.getScreenwidth() / 4.5);
		// llp.height = (int) (util.getScreenwidth() / 4.5);
		//
		// profileImage.setLayoutParams(llp);

		int marginTop = (util.getScreenHeight() / 3) - (util.getScreenwidth() / 8);

		FrameLayout profileFrame = (FrameLayout) header.findViewById(R.id.frameLayoutHeader);
		FrameLayout.LayoutParams profileParams = new FrameLayout.LayoutParams(profileFrame.getLayoutParams());

		profileParams.height = util.getScreenwidth() / 4;
		profileParams.width = util.getScreenwidth() / 4;
		profileParams.gravity = Gravity.CENTER_HORIZONTAL;

		// profileParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		// profileParams.addRule(RelativeLayout.BELOW, R.id.namePage);
		profileParams.setMargins(0, marginTop, 0, 0);

		// LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
		// imageLinear.getLayoutParams());
		// llp.width = (int) (util.getScreenwidth() / 4.5);
		// llp.height = (int) (util.getScreenwidth() / 4.5);

		profileImage.setLayoutParams(profileParams);

		ImageView headerImageView = (ImageView) header.findViewById(R.id.imgvadvertise_Object);

		FrameLayout.LayoutParams headerparams = new FrameLayout.LayoutParams(profileFrame.getLayoutParams());

		headerparams.height = util.getScreenHeight() / 3;
		headerparams.width = util.getScreenwidth();
		headerparams.gravity = Gravity.CENTER_HORIZONTAL;

		headerImageView.setLayoutParams(headerparams);

	}

	private void findView() {

		phoneLayout = (RelativeLayout) header.findViewById(R.id.laySabet);
		mobileLayout = (RelativeLayout) header.findViewById(R.id.layHamrah);
		AddressLayout = (RelativeLayout) header.findViewById(R.id.layaddress);
		faxLayout = (RelativeLayout) header.findViewById(R.id.layfax);
		emailLayout = (RelativeLayout) header.findViewById(R.id.layEmail);

		Expandview = (ExpandableListView) rootView.findViewById(R.id.items);

		txtaddress = (TextView) header.findViewById(R.id.address);
		txtcellphone = (TextView) header.findViewById(R.id.cellphone);
		txtphone = (TextView) header.findViewById(R.id.phone);
		txtemail = (TextView) header.findViewById(R.id.email);
		txtname = (TextView) header.findViewById(R.id.displayname);
		txtfax = (TextView) header.findViewById(R.id.fax);
		txtdate = (TextView) header.findViewById(R.id.txtdate);
		// namePage = (TextView) header.findViewById(R.id.namepageList);

		profileImage = (ImageView) header.findViewById(R.id.img1);

		layoutImageProfile = (LinearLayout) header.findViewById(R.id.imageLinear);

	}

	private void informationUser() {

		/*
		 * start set image User
		 */

		LayoutParams lp1 = new LinearLayout.LayoutParams(layoutImageProfile.getLayoutParams());
		lp1.width = util.getScreenwidth() / 4;
		lp1.height = util.getScreenwidth() / 4;
		lp1.setMargins(5, 5, 5, 5);

		String ImagePath = userPage.getImagePath();

		if (ImagePath != null) {
			Bitmap bmp = BitmapFactory.decodeFile(ImagePath);
			profileImage.setImageBitmap(Utility.getclip(bmp));
			profileImage.setLayoutParams(lp1);
			profileImage.setScaleType(ScaleType.FIT_XY);
		} else {
			profileImage.setImageResource(R.drawable.no_img_profile);
			profileImage.setLayoutParams(lp1);
			profileImage.setScaleType(ScaleType.FIT_XY);

		}

		/*
		 * end set image user
		 */

		/*
		 * start set information text
		 */

		txtname.setText(userPage.getName());
		txtdate.setText(util.getPersianDate(userPage.getDate()));
		txtemail.setText(userPage.getEmail());
		txtcellphone.setText(userPage.getMobailenumber());
		txtphone.setText(userPage.getPhonenumber());
		txtfax.setText(userPage.getFaxnumber());
		txtaddress.setText(userPage.getAddress());
		// namePage.setText(userPage.getName());
		/*
		 * end et information text
		 */

		/*
		 * start check view information
		 */

		String v = "";
		String information = userPage.getShowInfoItem();
		if (information != null) {

			for (int i = 0; i < information.length(); i++) {
				v = (String) information.subSequence(i, i + 1);
				if (i == 0) {
					if (v.equals("0"))
						txtphone.setText("محدودیت در نمایش اطلاعات");
					// phoneLayout.setVisibility(View.INVISIBLE);
				}

				if (i == 1) {
					if (v.equals("0"))
						txtcellphone.setText("محدودیت در نمایش اطلاعات");
					// mobileLayout.setVisibility(View.INVISIBLE);
				}

				if (i == 2) {
					if (v.equals("0")) {
						txtemail.setText("محدودیت در نمایش اطلاعات");
						txtemail.setGravity(Gravity.RIGHT);

					}
				}

				if (i == 3) {
					if (v.equals("0"))
						txtfax.setText("محدودیت در نمایش اطلاعات");

					// faxLayout.setVisibility(View.INVISIBLE);
				}

				if (i == 4) {
					if (v.equals("0"))
						txtaddress.setText("محدودیت در نمایش اطلاعات");
					// AddressLayout.setVisibility(View.INVISIBLE);
				}

			}

		}

		/*
		 * end check view information
		 */
	}

	private void allpageUser() {

		dbadaAdapter.open();

		List<PersonalData> ObejctData = dbadaAdapter.CustomFieldObjectByUser(userPage.getId());
		List<PersonalData> FroumData = dbadaAdapter.CustomFieldFroumByUser(userPage.getId());
		List<PersonalData> PaperData = dbadaAdapter.CustomFieldPaperByUser(userPage.getId());
		// List<PersonalData> TicketData = dbadaAdapter
		// .CustomFieldTicketByUser(userPage.getId());
		List<PersonalData> AnadData = dbadaAdapter.CustomFieldAnadByUser(userPage.getId());

		dbadaAdapter.close();

		List<Integer> sizeTypeList = new ArrayList<Integer>();

		sizeTypeList.add(ObejctData.size());
		// sizeTypeList.add(TicketData.size());
		sizeTypeList.add(PaperData.size());
		sizeTypeList.add(FroumData.size());
		sizeTypeList.add(AnadData.size());

		// ExpandableListView Expandview = (ExpandableListView) rootView
		// .findViewById(R.id.items);

		HashMap<String, List<PersonalData>> listDataChild = new HashMap<String, List<PersonalData>>();

		ArrayList<String> parentItems = new ArrayList<String>();

		Expandview.setDividerHeight(5);
		Expandview.setGroupIndicator(null);
		Expandview.setClickable(true);

		parentItems.add("صفحات");
		// parentItems.add("آگهی ها");
		parentItems.add("مقالات");
		parentItems.add("تالار گفتگو");
		parentItems.add("مدیریت تبلیغات");

		List<PersonalData> emptyItem = new ArrayList<PersonalData>();

		PersonalData prd = new PersonalData();

		if (ObejctData.size() == 0) {
			prd.setDateTicket("آیتمی اضافه نشده است");
			emptyItem.clear();
			emptyItem.add(prd);

			listDataChild.put(parentItems.get(0), emptyItem);
		} else {
			listDataChild.put(parentItems.get(0), ObejctData);

		}

		if (PaperData.size() == 0) {
			prd.setDateTicket("آیتمی اضافه نشده است");
			emptyItem.clear();
			emptyItem.add(prd);

			listDataChild.put(parentItems.get(1), emptyItem);
		} else {
			listDataChild.put(parentItems.get(1), PaperData);
		}
		if (FroumData.size() == 0) {
			prd.setDateTicket("آیتمی اضافه نشده است");
			emptyItem.clear();
			emptyItem.add(prd);

			listDataChild.put(parentItems.get(2), emptyItem);

		} else {

			listDataChild.put(parentItems.get(2), FroumData);
		}
		
		if (AnadData.size() == 0) {
			prd.setDateTicket("آیتمی اضافه نشده است");
			emptyItem.clear();
			emptyItem.add(prd);

			listDataChild.put(parentItems.get(3), emptyItem);

		} else {

			listDataChild.put(parentItems.get(3), AnadData);
		}

		final SharedPreferences currentTime = getActivity().getSharedPreferences("time", 0);

		String time = currentTime.getString("time", "-1");

		final InformationUserAdapter listAdapter = new InformationUserAdapter(getActivity(), parentItems, listDataChild,
				time, InformationUser.this, sizeTypeList, false, userPage.getName());

		// setting list adapter
		Expandview.addHeaderView(header);

		Expandview.setAdapter(listAdapter);

	}
}
