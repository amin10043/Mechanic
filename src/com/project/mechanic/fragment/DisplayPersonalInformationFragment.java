package com.project.mechanic.fragment;

import android.app.Service;
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
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mechanic.R;
import com.project.mechanic.entity.Ticket;
import com.project.mechanic.entity.Users;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.ServiceComm;
import com.project.mechanic.utility.Utility;


public class DisplayPersonalInformationFragment  extends Fragment {
	
	


	 DataBaseAdapter dbAdapter;
		ServiceComm service;
	Utility	utile1;
	
	Ticket tempItem;
	@Override

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_displaypersonalinformation, null);
utile1=new Utility(getActivity());
		service = new ServiceComm(getActivity());
		
	TextView	txtaddress	=(TextView) view.findViewById(R.id.address);
	TextView	txtcellphone=(TextView) view.findViewById(R.id.cellphone);
	TextView	txtphone=(TextView) view.findViewById(R.id.phone);
	TextView	txtemail=(TextView) view.findViewById(R.id.email);
	TextView	txtname=(TextView) view.findViewById(R.id.txtname);
	TextView	txtfax=(TextView) view.findViewById(R.id.fax);
	ImageView    img=(ImageView) view.findViewById(R.id.img1)	;
	ImageView logout =(ImageView) view.findViewById(R.id.imagelogout)	;
Button btnedit=(Button) view.findViewById(R.id.btnedit);

TextView txtdate=(TextView)view.findViewById(R.id.txtdate);
final LinearLayout lin2 = (LinearLayout) view.findViewById(R.id.lin2);

LayoutParams lp1 = new LinearLayout.LayoutParams(lin2.getLayoutParams());		
lp1.width=utile1.getScreenwidth()/4;
   lp1.height=utile1.getScreenwidth()/4;
   img.setLayoutParams(lp1);
	PersianDate date = new PersianDate();
	
	

	

	dbAdapter = new DataBaseAdapter(getActivity());
	dbAdapter.open();
	
	Users u  =utile1.getCurrentUser();
	int id = u.getId();
	byte[] bitmapbyte = u.getImage();
	if (bitmapbyte != null) {
		Bitmap bmp = BitmapFactory.decodeByteArray(bitmapbyte, 0,
				bitmapbyte.length);
		img.setImageBitmap(bmp);
	}
	String name=u.getName();
 	String email=u.getEmail();
 	String address=u.getAddress();
	String phone=u.getPhonenumber();
 	String cellphone=u.getMobailenumber();
 	String fax=u.getFaxnumber();	
////////////////////////////////////////////////	///////////////
//	int id =1;
//	Users x =dbAdapter.getUserById(id);	
//	byte[] bitmapbyte = x.getImage();
//	if (bitmapbyte != null) {
//		Bitmap bmp = BitmapFactory.decodeByteArray(bitmapbyte, 0,
//				bitmapbyte.length);
//		img.setImageBitmap(bmp);
//	}
//		
String d = u.getDate();
// 	int item = x.getId();
// 	String name=x.getName();
// 	String email=x.getEmail();
// 	String address=x.getAddress();
// 	String phone=x.getPhonenumber();
// 	String cellphone=x.getMobailenumber();
// 	String fax=x.getFaxnumber();
 	
 /////////////////////////	
		
	dbAdapter.close();
	txtdate.setText(d);
	txtname.setText(name);
	txtemail.setText(email);
	txtcellphone.setText(cellphone);
	txtphone.setText(phone);
	txtfax.setText(fax);
	txtaddress.setText(address);
//	picture.setImageURI(uri);

	
	
	logout.setOnClickListener(new OnClickListener() {
	
	@Override
		public void onClick(View arg0) {
		
		
		////////////////////////null get current user//////////////////
		
		SharedPreferences settings = getActivity().getSharedPreferences("User",
				0);
		SharedPreferences.Editor editor = settings.edit();
		

			editor.putBoolean("isLogin", false);
			
			editor.commit();
	//////////////////////////////////////////////////
	dbAdapter = new DataBaseAdapter(getActivity());
	dbAdapter.open();
	int ad=0;
	 dbAdapter.UpdateAdminAllUser(ad)	;
	dbAdapter.close();
	
	FragmentTransaction trans = getActivity()
			.getSupportFragmentManager().beginTransaction();
	trans.replace(R.id.content_frame, new LoginFragment());
	trans.commit();
			
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


	
	
	
	
	
	
}


