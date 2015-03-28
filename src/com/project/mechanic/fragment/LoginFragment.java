package com.project.mechanic.fragment;

import com.project.mechanic.R;
import com.project.mechanic.R.layout;
import com.project.mechanic.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AutoCompleteTextView.Validator;
import android.widget.Button;

public class LoginFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_login, null);
		
		
		
	Button btnlog	=(Button)view.findViewById(R.id.btnlogin);
	Button btncancle = (Button) view.findViewById(R.id.btncancle);
		
	Button btnreg= (Button) view.findViewById(R.id.btnreg1)	;
	Button btnforgot =(Button) view.findViewById(R.id.btnforgot);
		
 btnlog.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
});
	
		
 btnforgot.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}
	});
 btncancle.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}
	});
 btnreg.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}
	});
		
	return view;
		
		
		
		
		
		
	}



		

}