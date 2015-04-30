package com.project.mechanic.fragment;

import android.app.Service;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mechanic.R;
import com.project.mechanic.entity.Users;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.ServiceComm;


public class DisplayPersonalInformationFragment  extends Fragment {
	
	


	 DataBaseAdapter dbAdapter;
		ServiceComm service;

	@Override

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_displaypersonalinformation, null);

		service = new ServiceComm(getActivity());
		
	TextView	txtaddress	=(TextView) view.findViewById(R.id.txtaddress);
	TextView	txtcellphone=(TextView) view.findViewById(R.id.txtcellphone);
	TextView	txtphone=(TextView) view.findViewById(R.id.txtphone);
	TextView	txtemail=(TextView) view.findViewById(R.id.txtemail);
	TextView	txtname=(TextView) view.findViewById(R.id.txtname);
	TextView	txtfax=(TextView) view.findViewById(R.id.txtfax);
	ImageView   img=(ImageView) view.findViewById(R.id.imgDialoganad)	;
		
//	ParseUser currentUser = ParseUser.getCurrentUser();
//	if (currentUser != null) {
//	  // do stuff with the user
//	} else {
//	  // show the signup or login screen
//	}
//	
	img.getLayoutParams().height = 120;
	img.getLayoutParams().width = 120;
	img.requestLayout();
	dbAdapter = new DataBaseAdapter(getActivity());
	dbAdapter.open();
	
//	
	

	
//	ServiceComm currentUser = ServiceComm.getCurrentUser();
//	if (currentUser != null) {
//	  // do stuff with the user
//	} else {
//	  // show the signup or login screen
//	}
	
	
	
	
	
	
//	Users currentUser = Users.getCurrentUser();
	
	
	
	
	
	int id = 9;
	Users x =dbAdapter.getUserById(id);	
	byte[] bitmapbyte = x.getImage();
	if (bitmapbyte != null) {
		Bitmap bmp = BitmapFactory.decodeByteArray(bitmapbyte, 0,
				bitmapbyte.length);
		img.setImageBitmap(bmp);
	}
					
 	int item = x.getId();
 	String name=x.getName();
 	String email=x.getEmail();
 	String address=x.getAddress();
 	String phone=x.getPhonenumber();
 	String cellphone=x.getMobailenumber();
 	String fax=x.getFaxnumber();
 	
 	
		
	dbAdapter.close();
	
	txtname.setText(name);
	txtemail.setText(email);
	txtcellphone.setText(cellphone);
	txtphone.setText(phone);
	txtfax.setText(fax);
	txtaddress.setText(address);
//	picture.setImageURI(uri);

		return view;
	}


	
	
	
	
	
	
}


