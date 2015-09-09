package com.project.mechanic.fragment;

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mechanic.R;
import com.project.mechanic.adapter.ObjectListAdapter;
import com.project.mechanic.entity.Object;
import com.project.mechanic.entity.Users;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class InformationUser extends Fragment {

	ListView listViewPages;

	View rootView, header;

	TextView txtaddress, txtcellphone, txtphone, txtemail, txtname, txtfax,
			txtdate, namePage;

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
		listViewPages.addHeaderView(header);

		allpageUser();
		
		profileImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				String PathProfile = userPage.getImagePath();
				String namePage = userPage.getName();
				DialogShowImage showImageDialog = new DialogShowImage(
						getActivity(), PathProfile , namePage);
				showImageDialog.show();
				
								
			}
		});

		return rootView;
	}

	private void findView() {

		listViewPages = (ListView) rootView.findViewById(R.id.listPageUser);

		txtaddress = (TextView) header.findViewById(R.id.address);
		txtcellphone = (TextView) header.findViewById(R.id.cellphone);
		txtphone = (TextView) header.findViewById(R.id.phone);
		txtemail = (TextView) header.findViewById(R.id.email);
		txtname = (TextView) header.findViewById(R.id.displayname);
		txtfax = (TextView) header.findViewById(R.id.fax);
		txtdate = (TextView) header.findViewById(R.id.txtdate);
		namePage = (TextView) header.findViewById(R.id.namepageList);

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
			profileImage.setImageBitmap(bmp);
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
		namePage.setText(userPage.getName());
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
		List<Object> listPage = dbadaAdapter.getAllObjectByUserId(userIdPage);
		dbadaAdapter.close();

		listAdapter = new ObjectListAdapter(getActivity(), R.layout.row_object,
				listPage, InformationUser.this, false);

		listViewPages.setAdapter(listAdapter);

		if (listPage.size() == 0) {

			RelativeLayout rela = (RelativeLayout) header
					.findViewById(R.id.noPage);

			rela.setVisibility(View.VISIBLE);
		}

	}
}
