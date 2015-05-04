package com.project.mechanic.fragment;

import java.io.ByteArrayOutputStream;

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
import android.text.Html;
import android.text.method.LinkMovementMethod;
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
import com.project.mechanic.entity.Users;
import com.project.mechanic.model.DataBaseAdapter;


public class CompeleteRegisterFragment extends Fragment {
	

	protected static final int RESULT_LOAD_IMAGE = 1;
	DataBaseAdapter dbAdapter;
	
	
	
	public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 0, outputStream);
		return outputStream.toByteArray();
	}
	
	
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.fragment_compeleteregister, null);

		dbAdapter = new DataBaseAdapter(getActivity());
		
		
		final	EditText Addresstxt = (EditText) view.findViewById(R.id.Addresstxt);
		final   EditText Phonetxt = (EditText) view.findViewById(R.id.phonetxt);
		final   EditText Mobiletxt = (EditText) view.findViewById(R.id.mobiletxt);
		final   EditText Faxtxt = (EditText) view.findViewById(R.id.faxtxt);
	final CheckBox Rulescheck = (CheckBox) view.findViewById(R.id.rulescheck);
	final Button Compeletebtn = (Button) view.findViewById(R.id.compeleteregisterbtn);
	Button Backbtn = (Button) view.findViewById(R.id.backbtn);
		
	final EditText editname = (EditText) view
			.findViewById(R.id.editTextname);
	final EditText edituser = (EditText) view
			.findViewById(R.id.editTextuser);
	final EditText editpass = (EditText) view
			.findViewById(R.id.editTextpass);
	
	TextView textrules=(TextView) view.findViewById(R.id.textrules);
	Compeletebtn.setEnabled(false);
	
	 
	textrules.setText(Html.fromHtml("<html><a href=\"http://example.com/\">قوانین</a></html>"));
	textrules.setMovementMethod(LinkMovementMethod.getInstance());
	
	
	
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
	
	
	
	Rulescheck.setOnClickListener(new OnClickListener()
	 {
	
	 @Override
	 public void onClick(View v) {
	// is chkIos checked?
	 if (((CheckBox) v).isChecked()) {
		 Compeletebtn.setEnabled(true);
	 }
	 else {
		 
		 Compeletebtn.setEnabled(false);
		 
		 
	 }
	 StringBuffer result = new StringBuffer();
	 result.append("Linux check : ").append(Rulescheck.isChecked());
	
	
	 Context context;
	
	 }
	 });
	
	
	Compeletebtn.setOnClickListener(new OnClickListener() {

		public void onClick(View arg0) {
			
				
				dbAdapter = new DataBaseAdapter(getActivity());
				dbAdapter.open();

				int id = Integer.valueOf(getArguments().getString("Id"));
				
					Users x =dbAdapter.getUserById(id);					
		     	int item = x.getId();
//				Toast.makeText(getActivity(),
//						item+"",
//				Toast.LENGTH_SHORT).show();
			        dbAdapter.UpdateUserToDb(item, Phonetxt.getText().toString(), 
			        		Mobiletxt.getText().toString(),Faxtxt.getText().toString(),
			        		Addresstxt.getText().toString());
			        dbAdapter.close();			
			        Toast.makeText(getActivity(),
					"ثبت انجام شد  ",
					Toast.LENGTH_SHORT).show();
			       
			        Addresstxt.setText("");
			        Phonetxt.setText("");
             		Mobiletxt.setText("");
             		Faxtxt.setText("");
			}
	
		
		

	});
			return view;
	
	
	
	
	
	
	
	
	
	
	
	}
}
	
	
	
