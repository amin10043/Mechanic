package com.project.mechanic.fragment;

import java.util.HashMap;
import java.util.LinkedHashMap;

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
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.project.mechanic.R;
import com.project.mechanic.entity.Settings;
import com.project.mechanic.entity.Ticket;
import com.project.mechanic.entity.Users;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.inter.GetAsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.service.UpdatingImage;
import com.project.mechanic.utility.ServiceComm;
import com.project.mechanic.utility.Utility;

public class DisplayPersonalInformationFragment extends Fragment implements
		GetAsyncInterface, AsyncInterface {

	DataBaseAdapter dbAdapter;
	ServiceComm service;
	Utility utile1;
	UpdatingImage serviceImage;

	ImageView img;
	Ticket tempItem;
	Users u;
	Settings setting;
	String serverDate;
	ServerDate date;
	int userId;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(
				R.layout.fragment_displaypersonalinformation, null);
		utile1 = new Utility(getActivity());
		service = new ServiceComm(getActivity());
		dbAdapter = new DataBaseAdapter(getActivity());
		
		if (getArguments().getInt("0") == 0){
		Bundle bundle = this.getArguments();
		userId = bundle.getInt("0", 0);
		}
		
		 if (getArguments().getInt("userId") != 0){
			Bundle bundle = this.getArguments();
		userId = bundle.getInt("userId", 0);}
	    if(userId!=0)	{
		
		  Toast.makeText(getActivity(),""+userId,Toast.LENGTH_SHORT).show();
		   dbAdapter.open();
		   u= dbAdapter.getUserById(userId);
		   dbAdapter.close();
		
		
    	 }else{
		u = utile1.getCurrentUser();
    	 }
		if (u == null) {
			// خطا . نباید این اتفاق بیفتد!
			return view;
		}
    	
		date = new ServerDate(getActivity());
		date.delegate = this;
		date.execute("");

		TextView txtaddress = (TextView) view.findViewById(R.id.address);
		TextView txtcellphone = (TextView) view.findViewById(R.id.cellphone);
		TextView txtphone = (TextView) view.findViewById(R.id.phone);
		TextView txtemail = (TextView) view.findViewById(R.id.email);
		TextView txtname = (TextView) view.findViewById(R.id.displayname);
		TextView txtfax = (TextView) view.findViewById(R.id.fax);
		img = (ImageView) view.findViewById(R.id.img1);
		ImageView logout = (ImageView) view.findViewById(R.id.logout);
		Button btnedit = (Button) view.findViewById(R.id.btnedit);

		TextView txtdate = (TextView) view.findViewById(R.id.txtdate);

		final LinearLayout lin4 = (LinearLayout) view.findViewById(R.id.lin2);

		LayoutParams lp1 = new LinearLayout.LayoutParams(lin4.getLayoutParams());
		lp1.width = utile1.getScreenwidth() / 4;
		lp1.height = utile1.getScreenwidth() / 4;
		img.setLayoutParams(lp1);

		byte[] bitmapbyte = u.getImage();
		if (bitmapbyte != null) {
			Bitmap bmp = BitmapFactory.decodeByteArray(bitmapbyte, 0,
					bitmapbyte.length);
			img.setImageBitmap(Utility.getRoundedCornerBitmap(bmp, 50));
		}
		String name = u.getName();
		String email = u.getEmail();
		String address = u.getAddress();
		String phone = u.getPhonenumber();
		String cellphone = u.getMobailenumber();
		String fax = u.getFaxnumber();
		String d = u.getDate();

		txtdate.setText(d);
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

		return view;
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
