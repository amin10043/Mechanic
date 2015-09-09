package com.project.mechanic.fragment;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.project.mechanic.R;
import com.project.mechanic.adapter.ObjectListAdapter;
import com.project.mechanic.entity.Object;
import com.project.mechanic.entity.Settings;
import com.project.mechanic.entity.Ticket;
import com.project.mechanic.entity.Users;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.inter.GetAsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.service.UpdatingImage;
import com.project.mechanic.utility.Utility;

public class DisplayPersonalInformationFragment extends Fragment implements
		GetAsyncInterface, AsyncInterface {

	DataBaseAdapter dbAdapter;
	Utility utile1;
	UpdatingImage serviceImage;
	DialogPersonLikedFroum dia;
	ImageView img;
	Ticket tempItem;
	Users u;
	Settings setting;
	String serverDate;
	ServerDate date;
	int userId;
	RelativeLayout phoneLayout, emailLayout, faxLayout, mobileLayout,
			AddressLayout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		utile1 = new Utility(getActivity());
		dbAdapter = new DataBaseAdapter(getActivity());

		View rootView = inflater.inflate(R.layout.fragment_information_user,
				null);

		View header = inflater.inflate(
				R.layout.fragment_displaypersonalinformation, null);

		phoneLayout = (RelativeLayout) header.findViewById(R.id.laySabet);
		mobileLayout = (RelativeLayout) header.findViewById(R.id.layHamrah);
		AddressLayout = (RelativeLayout) header.findViewById(R.id.layaddress);
		faxLayout = (RelativeLayout) header.findViewById(R.id.layfax);
		emailLayout = (RelativeLayout) header.findViewById(R.id.layEmail);

		if (getArguments() != null) {
			if (getArguments().getInt("0") == 0) {
				Bundle bundle = this.getArguments();
				userId = bundle.getInt("0", 0);
			}

			if (getArguments().getInt("userId") != 0) {
				Bundle bundle = this.getArguments();
				userId = bundle.getInt("userId", 0);
			}
		}
		if (userId != 0) {

			dbAdapter.open();
			u = dbAdapter.getUserById(userId);
			dbAdapter.close();

		} else {
			u = utile1.getCurrentUser();
		}
		if (u == null) {
			// خطا . نباید این اتفاق بیفتد!
			return rootView;
		}

		date = new ServerDate(getActivity());
		date.delegate = this;
		date.execute("");

		TextView txtaddress = (TextView) header.findViewById(R.id.address);
		TextView txtcellphone = (TextView) header.findViewById(R.id.cellphone);
		TextView txtphone = (TextView) header.findViewById(R.id.phone);
		TextView txtemail = (TextView) header.findViewById(R.id.email);
		TextView txtname = (TextView) header.findViewById(R.id.displayname);
		TextView txtfax = (TextView) header.findViewById(R.id.fax);
		img = (ImageView) header.findViewById(R.id.img1);
		ImageView logout = (ImageView) header.findViewById(R.id.logout);
		Button btnedit = (Button) header.findViewById(R.id.btnedit);

		TextView txtdate = (TextView) header.findViewById(R.id.txtdate);

		final LinearLayout lin2 = (LinearLayout) header.findViewById(R.id.lin2);

		LayoutParams lp1 = new LinearLayout.LayoutParams(lin2.getLayoutParams());
		lp1.width = utile1.getScreenwidth() / 4;
		lp1.height = utile1.getScreenwidth() / 4;
		lp1.setMargins(5, 5, 5, 5);
		img.setLayoutParams(lp1);

		// byte[] bitmapbyte = u.getImage();
		String ImagePath = u.getImagePath();
		if (ImagePath != null) {
			Bitmap bmp = BitmapFactory.decodeFile(ImagePath);
			img.setImageBitmap(bmp);
		}

		ListView lv = (ListView) rootView.findViewById(R.id.listPageUser);
		lv.addHeaderView(header);

		dbAdapter.open();
		List<Object> listPage = dbAdapter.getAllObjectByUserId(u.getId());
		dbAdapter.close();

		ObjectListAdapter listAdapter = new ObjectListAdapter(getActivity(),
				R.layout.row_object, listPage,
				DisplayPersonalInformationFragment.this, false);

		lv.setAdapter(listAdapter);

		if (listPage.size() == 0) {

			RelativeLayout rela = (RelativeLayout) header
					.findViewById(R.id.noPage);

			rela.setVisibility(View.VISIBLE);
		}
		
		TextView namett = (TextView)header.findViewById(R.id.namepageList);
		
		namett.setText(u.getName());

		img.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String ImagePath = u.getImagePath();
				String name = u.getName();

				DialogShowImage showImage = new DialogShowImage(getActivity(),
						ImagePath, name);
				showImage.show();
			}
		});
		String name = u.getName();
		String email = u.getEmail();
		String address = u.getAddress();
		String phone = u.getPhonenumber();
		String cellphone = u.getMobailenumber();
		String fax = u.getFaxnumber();
		String d = u.getDate();

		txtdate.setText(utile1.getPersianDate(d));
		txtname.setText(name);
		txtemail.setText(email);
		txtcellphone.setText(cellphone);
		txtphone.setText(phone);
		txtfax.setText(fax);
		txtaddress.setText(address);

		logout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// //////////////////////null get current user//////////////////

				SharedPreferences settings = getActivity()
						.getSharedPreferences("User", 0);
				SharedPreferences.Editor editor = settings.edit();

				editor.putBoolean("isLogin", false);

				editor.commit();
				// ////////////////////////////////////////////////
				dbAdapter.open();
				int ad = 0;
				dbAdapter.UpdateAdminAllUser(ad);
				dbAdapter.close();

				FragmentTransaction trans = getActivity()
						.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new LoginFragment());
				trans.commit();
				TextView txtlike = (TextView) (getActivity())
						.findViewById(R.id.txtlike);
				txtlike.setVisibility(View.GONE);
				TextView txtcm1 = (TextView) (getActivity())
						.findViewById(R.id.txtcm);
				txtcm1.setVisibility(View.GONE);

			}

		});

		btnedit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				FragmentTransaction trans = getActivity()
						.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new EditPersonalFragment());
				trans.commit();
			}
		});

		return rootView;
	}

	@Override
	public void processFinish(byte[] output) {
		if (output != null) {
			Bitmap bmp = BitmapFactory
					.decodeByteArray(output, 0, output.length);
			img.setImageBitmap(Utility.getRoundedCornerBitmap(bmp, 50));
			dbAdapter.open();
			dbAdapter.UpdateUserImage(u.getId(), output, serverDate);
			dbAdapter.close();
		} else {
			if (u != null && u.getImage() != null) {
				Bitmap bmp = BitmapFactory.decodeByteArray(u.getImage(), 0,
						u.getImage().length);
				img.setImageBitmap(Utility.getRoundedCornerBitmap(bmp, 50));
			}
		}
	}

	@Override
	public void processFinish(String output) {
		if (!"".equals(output) && output != null) {
			serverDate = output;
			HashMap<String, String> params = new LinkedHashMap<String, String>();
			params.put("tableName", "Users");
			params.put("Id", String.valueOf(u.getId()));
			params.put("fromDate", u.getImageServerDate());
			Context context = getActivity();
			if (context != null) {

				serviceImage = new UpdatingImage(context);
				serviceImage.delegate = this;
				serviceImage.execute(params);

			}
		}

	}
}
