package com.project.mechanic.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
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
			txtdate /*, namePage*/;

	ImageView profileImage;

	LinearLayout layoutImageProfile;

	int userIdPage;

	DataBaseAdapter dbadaAdapter;
	Utility util;
	Users userPage;

	ObjectListAdapter listAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

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

		allpageUser();

		profileImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				String PathProfile = userPage.getImagePath();
				String namePage = userPage.getName();
				DialogShowImage showImageDialog = new DialogShowImage(
						getActivity(), PathProfile, namePage);
				showImageDialog.show();

			}
		});
		
		util.ShowFooterAgahi(getActivity(), false, -1);

		return rootView;
	}

	private void findView() {

		Expandview = (ExpandableListView) rootView
				.findViewById(R.id.listPageUser);

		txtaddress = (TextView) header.findViewById(R.id.address);
		txtcellphone = (TextView) header.findViewById(R.id.cellphone);
		txtphone = (TextView) header.findViewById(R.id.phone);
		txtemail = (TextView) header.findViewById(R.id.email);
		txtname = (TextView) header.findViewById(R.id.displayname);
		txtfax = (TextView) header.findViewById(R.id.fax);
		txtdate = (TextView) header.findViewById(R.id.txtdate);
//		namePage = (TextView) header.findViewById(R.id.namepageList);

		profileImage = (ImageView) header.findViewById(R.id.img1);

		layoutImageProfile = (LinearLayout) header.findViewById(R.id.lin2);

	}

	private void informationUser() {

		/*
		 * start set image User
		 */

		LayoutParams lp1 = new LinearLayout.LayoutParams(
				layoutImageProfile.getLayoutParams());
		lp1.width = util.getScreenwidth() / 4;
		lp1.height = util.getScreenwidth() / 4;
		lp1.setMargins(5, 5, 5, 5);

		String ImagePath = userPage.getImagePath();

		if (ImagePath != null) {
			Bitmap bmp = BitmapFactory.decodeFile(ImagePath);
			profileImage.setImageBitmap(util.getRoundedCornerBitmap(bmp, 20));
			profileImage.setLayoutParams(lp1);
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
//		namePage.setText(userPage.getName());
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
					if (v.equals("0"))
						txtemail.setText("محدودیت در نمایش اطلاعات");
					// emailLayout.setVisibility(View.INVISIBLE);
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

		List<PersonalData> ObejctData = dbadaAdapter
				.CustomFieldObjectByUser(userPage.getId());
		List<PersonalData> FroumData = dbadaAdapter
				.CustomFieldFroumByUser(userPage.getId());
		List<PersonalData> PaperData = dbadaAdapter
				.CustomFieldPaperByUser(userPage.getId());
//		List<PersonalData> TicketData = dbadaAdapter
//				.CustomFieldTicketByUser(userPage.getId());

		dbadaAdapter.close();

		List<Integer> sizeTypeList = new ArrayList<Integer>();

		sizeTypeList.add(ObejctData.size());
	//	sizeTypeList.add(TicketData.size());
		sizeTypeList.add(PaperData.size());
		sizeTypeList.add(FroumData.size());

		// ExpandableListView Expandview = (ExpandableListView) rootView
		// .findViewById(R.id.items);

		HashMap<String, List<PersonalData>> listDataChild = new HashMap<String, List<PersonalData>>();

		ArrayList<String> parentItems = new ArrayList<String>();

		Expandview.setDividerHeight(5);
		Expandview.setGroupIndicator(null);
		Expandview.setClickable(true);

		parentItems.add("صفحات");
		//parentItems.add("آگهی ها");
		parentItems.add("مقالات");
		parentItems.add("تالار گفتگو");

		listDataChild.put(parentItems.get(0), ObejctData); // Header, Child data
		//listDataChild.put(parentItems.get(1), TicketData);
		listDataChild.put(parentItems.get(1), PaperData);
		listDataChild.put(parentItems.get(2), FroumData);

		final SharedPreferences currentTime = getActivity()
				.getSharedPreferences("time", 0);

		String time = currentTime.getString("time", "-1");

		final InformationUserAdapter listAdapter = new InformationUserAdapter(
				getActivity(), parentItems, listDataChild, time,
				InformationUser.this, sizeTypeList, false , userPage.getName());

		// setting list adapter
		Expandview.addHeaderView(header);

		Expandview.setAdapter(listAdapter);

	}
}
