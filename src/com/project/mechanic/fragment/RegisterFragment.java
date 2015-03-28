package com.project.mechanic.fragment;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class RegisterFragment extends Fragment {


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_login, null);
		
		
		Button btnreg2= (Button) view.findViewById(R.id.btnreg2);
		Button btncancle2= (Button) view.findViewById(R.id.btncancle2);
		
		
		btnreg2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		
		
		
		
btncancle2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});	
		
		
		
		
		
		
		
		
		
		return view;
		
	}	

}
