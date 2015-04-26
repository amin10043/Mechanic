package com.project.mechanic.fragment;

import com.project.mechanic.R;
import android.R.color;
import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
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

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_compeleteregister, null);

		
		
		
	EditText Addresstxt = (EditText) view.findViewById(R.id.Addresstxt);
	EditText Phonetxt = (EditText) view.findViewById(R.id.phonetxt);
	EditText Mobiletxt = (EditText) view.findViewById(R.id.mobiletxt);
	EditText Faxtxt = (EditText) view.findViewById(R.id.faxtxt);
	CheckBox Rulescheck = (CheckBox) view.findViewById(R.id.rulescheck);
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
		return view;
	}

	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
}
