package com.project.mechanic.fragment;


import com.project.mechanic.MainActivity;
import com.project.mechanic.R;

import android.app.Dialog;

import android.content.SharedPreferences;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.utility.ServiceComm;
import com.project.mechanic.utility.Utility;

public class LoginFragment extends Fragment implements AsyncInterface {

	ServiceComm service;
	Utility util;

	
	Dialogeml  dialog;
	
	
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		service = new ServiceComm(getActivity());
		util = new Utility(getActivity());

		((MainActivity) getActivity()).setActivityTitle(R.string.Propaganda);
		View view = inflater.inflate(R.layout.fragment_login, null);

		Button btnlog = (Button) view.findViewById(R.id.btnlogin);
		Button btncancle = (Button) view.findViewById(R.id.btncancle);

		Button btnreg = (Button) view.findViewById(R.id.btnreg1);
		Button btnforgot = (Button) view.findViewById(R.id.btnforgot);
		final EditText edituser = (EditText) view
				.findViewById(R.id.editTextuser);
		final EditText editpass = (EditText) view
				.findViewById(R.id.editTextpass);

		btnlog.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if (!util.isNetworkConnected()) {
					util.showOkDialog(getActivity(), "خطا در ارتباط",
							"شما به اینترنت متصل نیستید.");
				}

				String user = edituser.getText().toString();
				String pass = editpass.getText().toString();
				if ("".equals(user) || "".equals(pass)) {
					Toast.makeText(getActivity(),
							"نام کاربری و یا کلمه عبور نمی تواند خالی باشد.",
							Toast.LENGTH_SHORT).show();
					return;
				}
				String[] params = new String[] { "login", user, pass };
				service.delegate = LoginFragment.this;
				service.execute(params);

			}
		});

		
		
		btnreg.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				FragmentTransaction trans = getActivity()
						.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new RegisterFragment());
				trans.commit();

			}
		});
		
		 btnforgot.setOnClickListener(new View.OnClickListener() {
				
				
				public void onClick(View v) {
				
					
					
			
					
					
					  dialog = new Dialogeml(LoginFragment.this,getActivity(),R.layout.dialog_addemail);
					  dialog.show();
					
					
					
					
					
					
				}
			});

		return view;

	}



	@Override
	public void processFinish(String output) {

		SharedPreferences settings = getActivity().getSharedPreferences(
				"UserLogin", 0);
		SharedPreferences.Editor editor = settings.edit();
		if ("true".equals(output)) {
			Toast.makeText(getActivity(), "شما وارد شده اید.",
					Toast.LENGTH_SHORT).show();

			editor.putBoolean("isLogin", true);

			// ثبت اطلاعات کاربر در دیتا بیس هم حتما انجام گیرد. فراموش نشود!!!!


			FragmentTransaction trans = getActivity()
					.getSupportFragmentManager().beginTransaction();
			trans.replace(R.id.content_frame, new MainFragment());
			trans.commit();

		} else {
			Toast.makeText(getActivity(),
					"نام کاربری و یا کلمه عبور به درستی وارد نشده است.",
					Toast.LENGTH_SHORT).show();
			editor.putBoolean("isLogin", false);

		}

		editor.commit();

	}
}
