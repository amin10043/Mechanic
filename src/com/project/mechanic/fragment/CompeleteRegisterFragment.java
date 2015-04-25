package com.project.mechanic.fragment;

import com.project.mechanic.R;
import android.R.color;
import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorJoiner.Result;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mechanic.R;
import com.project.mechanic.model.DataBaseAdapter;


public class CompeleteRegisterFragment extends Fragment {
	

	protected static final int RESULT_LOAD_IMAGE = 1;
	DataBaseAdapter dbAdapter;

	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_compeleteregister, null);

		dbAdapter = new DataBaseAdapter(getActivity());
		
		
		final	EditText Addresstxt = (EditText) view.findViewById(R.id.Addresstxt);
		final   EditText Phonetxt = (EditText) view.findViewById(R.id.phonetxt);
		final   EditText Mobiletxt = (EditText) view.findViewById(R.id.mobiletxt);
		final   EditText Faxtxt = (EditText) view.findViewById(R.id.faxtxt);
	final CheckBox Rulescheck = (CheckBox) view.findViewById(R.id.rulescheck);
	Button Compeletebtn = (Button) view.findViewById(R.id.compeleteregisterbtn);
	Button Backbtn = (Button) view.findViewById(R.id.backbtn);
		
	
		
	
	Backbtn.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			
			FragmentTransaction trans = getActivity()
					.getSupportFragmentManager().beginTransaction();
			trans.replace(R.id.content_frame, new RegisterFragment());
			trans.commit();
			
			
		}
	});
	
	Compeletebtn.setOnClickListener(new OnClickListener() {

		public void onClick(View arg0) {
			final String Address = Addresstxt.getText().toString();
			final String Phone = Phonetxt.getText().toString();
			final String Mobile =Mobiletxt.getText().toString();
			final String Fax = Faxtxt.getText().toString();
			StringBuffer result = new StringBuffer();
			result.append("checkbox click shod:").append(Rulescheck.isChecked());

			
			if (Address.equals("") && Phone.equals("") && Mobile.equals("")&& Fax.equals("")) {

				Toast.makeText(getActivity(),
						"·ÿ›« ›Ì·œÂ«Ì „Ê—œ ‰Ÿ— —« Å— ò‰Ìœ  ",
						Toast.LENGTH_SHORT).show();
			
			}
				else {
					
					
//					dbAdapter = new DataBaseAdapter(getActivity());
//					dbAdapter.open();
//					
//					dbAdapter.inserUserToDb(Name, Email, Pass, null, Image, 0);
//
//					dbAdapter.close();
//					Toast.makeText(getActivity(), "«ÿ·«⁄«  „Ê—œ ‰Ÿ— À»  ‘œ",
//							Toast.LENGTH_SHORT).show();
//					
//					Addresstxt.setText("");
//					Phonetxt.setText("");
//					Mobiletxt.setText("");
//					Faxtxt.setText("");
					
				}
		
	
	
		
	
		
		}

	});
			return view;
	
	
	
	
	
	
	
	
	
	
	
	}
}
	
	
	
