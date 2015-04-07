package com.project.mechanic.fragment;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class LoginFragment extends Fragment {

	
	Dialogeml  dialog;
	
	
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		((MainActivity) getActivity()).setActivityTitle(R.string.Propaganda);
		View view = inflater.inflate(R.layout.fragment_login, null);
		

		
	
 
	Button btnlog	=(Button)view.findViewById(R.id.btnlogin);
	Button btncancle = (Button) view.findViewById(R.id.btncancle);
		
	Button btnreg= (Button) view.findViewById(R.id.btnreg1)	;
	Button btnforgot =(Button) view.findViewById(R.id.btnforgot);
	final EditText edituser  = (EditText)view.findViewById(R.id.editTextuser);
	final EditText editpass  = (EditText)view.findViewById(R.id.editTextpass);
 btnlog.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(View v) {
		
		
	}
});
	
		
 btnforgot.setOnClickListener(new View.OnClickListener() {
		
	
		public void onClick(View v) {
		
			
			
	
			
			
			  dialog = new Dialogeml(LoginFragment.this,getActivity(),R.layout.dialog_addemail);
			  dialog.show();
			
			
			
			
			
			
		}
	});
 btncancle.setOnClickListener(new View.OnClickListener() {
	
		public void onClick(View v) {
			
			
		}
	});
 btnreg.setOnClickListener(new View.OnClickListener() {
		
	
		public void onClick(View v) {
			
		
			
			FragmentTransaction trans = getActivity()
					.getSupportFragmentManager().beginTransaction();
			trans.replace(R.id.content_frame, new RegisterFragment());
			trans.commit();
			
			
		}
	});
 
 
 
 
 
		
	return view;
		
		
		
		
		
		
	}



		

}
