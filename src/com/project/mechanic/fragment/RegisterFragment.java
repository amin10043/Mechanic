package com.project.mechanic.fragment;


import com.project.mechanic.R;
import com.project.mechanic.model.DataBaseAdapter;

import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.EditText;


public class RegisterFragment extends Fragment {

	
	DataBaseAdapter dbAdapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 View view = inflater.inflate(R.layout.fragment_register, null);
		 
			dbAdapter = new DataBaseAdapter(getActivity());
			
			
	Button   btncan    = (Button) view.findViewById(R.id.btncancle2);
	Button  btnreg    =(Button) view.findViewById(R.id.btnreg2);
	EditText editname  = (EditText)view.findViewById(R.id.editTextname);		 
    EditText edituser  = (EditText)view.findViewById(R.id.editTextuser);		 
	EditText editpass  = (EditText)view.findViewById(R.id.editTextpass);
		 
	 final String Name = editname.getText().toString(); 
	 final String user = edituser.getText().toString();  
	 final String pass = editpass.getText().toString(); 
	 
	 
	btnreg.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			
			dbAdapter.open();
			
			dbAdapter.inserUserToDb( Name,user, pass);
			dbAdapter.close();
		}
	 });
		 
		 
		 
		 
		 
	 
	 btncan.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			
			 
		}
	 });
		 	 
		 
		 
		 
		 
		 
		 
		 
		 return view;
	}

	private EditText findViewById(int edittextuser) {
		// TODO Auto-generated method stub
		return null;
	}


}
