package com.project.mechanic.fragment;


import java.util.jar.Attributes.Name;

import com.project.mechanic.R;
import com.project.mechanic.model.DataBaseAdapter;

import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class RegisterFragment extends Fragment {

	
	DataBaseAdapter dbAdapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 View view = inflater.inflate(R.layout.fragment_register, null);
		 
			dbAdapter = new DataBaseAdapter(getActivity());
			
			
	Button   btncan    = (Button) view.findViewById(R.id.btncancle2);
	Button   btnreg    =(Button) view.findViewById(R.id.btnreg2);
	final EditText editname  = (EditText)view.findViewById(R.id.editTextname);		 
    final EditText edituser  = (EditText)view.findViewById(R.id.editTextuser);		 
	final EditText editpass  = (EditText)view.findViewById(R.id.editTextpass);
		 
	
	 
	btnreg.setOnClickListener(new OnClickListener(){

	
		public void onClick(View arg0) {
			final String Name = editname.getText().toString(); 
			 final String user = edituser.getText().toString();  
			 final String pass = editpass.getText().toString(); 
			if (Name.equals("")&& user.equals("")&& pass.equals("")) {
				
				
				Toast.makeText(getActivity(), "«ÿ·«⁄«  ò«„· ‰‘œ  ", Toast.LENGTH_SHORT).show();
				
				
			} 
			
			else {

			}
			 
			dbAdapter.open();
			dbAdapter.inserUserToDb( Name,user, pass);
			dbAdapter.close();
			
Toast.makeText(getActivity(), "«ÿ·«⁄«  „Ê—œ ‰Ÿ— À»  ‘œ", Toast.LENGTH_SHORT).show();
			
			
			
			
			
		}
	 });
		 
		 
		 
		 
		 
	 
	 btncan.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			
			
			FragmentTransaction trans = getActivity()
					.getSupportFragmentManager().beginTransaction();
			trans.replace(R.id.content_frame, new LoginFragment());
			trans.commit();
		}
	 });
		 	 
		 
		 
		 
		 
		 
		 
		 
		 return view;
	}

	private EditText findViewById(int edittextuser) {
		// TODO Auto-generated method stub
		return null;
	}


}
