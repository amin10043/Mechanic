package com.project.mechanic.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.entity.Users;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.ServiceComm;
import com.project.mechanic.utility.Utility;

public class LoginFragment extends Fragment implements AsyncInterface {

	ServiceComm service;
	Utility util;
	Dialogeml dialog;
	DataBaseAdapter dbAdapter;
	Users u;
	String mobile;
	String pass;
	EditText editmobile;
	EditText editpass;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		service = new ServiceComm(getActivity());
		util = new Utility(getActivity());
		dbAdapter = new DataBaseAdapter(getActivity());
		((MainActivity) getActivity()).setActivityTitle(R.string.Propaganda);
		View view = inflater.inflate(R.layout.fragment_login, null);

		Button btnlog = (Button) view.findViewById(R.id.btnlogin);
		Button btncancle = (Button) view.findViewById(R.id.btncancle);

		Button btnreg = (Button) view.findViewById(R.id.btnreg1);
		Button btnforgot = (Button) view.findViewById(R.id.btnforgot);
		final EditText editmobile = (EditText) view
				.findViewById(R.id.editTextuser);
		final EditText editpass = (EditText) view
				.findViewById(R.id.editTextpass);
		// TextView test = (TextView) view.findViewById(R.id.texttest);
		btnlog.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				dbAdapter.open();

				mobile = editmobile.getText().toString();
				pass = editpass.getText().toString();

				dbAdapter.close();
				if (!util.isNetworkConnected()) {
					util.showOkDialog(getActivity(), "خطا در ارتباط",
							"شما به اینترنت متصل نیستید.");
				}

				else if ("".equals(mobile) || "".equals(pass)) {
					Toast.makeText(getActivity(),
							"نام کاربری و یا کلمه عبور نمی تواند خالی باشد.",
							Toast.LENGTH_SHORT).show();
				}

				else {

					String[] params = new String[] { "login", mobile, pass };
					service.delegate = LoginFragment.this;
					service.execute(params);
				}
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

		btncancle.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				FragmentTransaction trans = getActivity()
						.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new MainFragment());
				trans.commit();
			}
		});

		btnforgot.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				dialog = new Dialogeml(LoginFragment.this, getActivity(),
						R.layout.dialog_addemail);
				dialog.setTitle("پیام");
				dialog.show();
			}
		});

		// test.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// // TODO Auto-generated method stub
		// FragmentTransaction trans = getActivity()
		// .getSupportFragmentManager().beginTransaction();
		// trans.replace(R.id.content_frame,
		// new DisplayPersonalInformationFragment());
		// trans.commit();
		// }
		// });
		//
		return view;

	}

	@Override
	public void processFinish(String output) {

		SharedPreferences settings = getActivity().getSharedPreferences("User",
				0);
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
			mobile = editmobile.getText().toString();

			u = dbAdapter.getUserbymobailenumber(mobile);
			int id = u.getId();
			int admin = 1;
			dbAdapter.open();

			dbAdapter.UpdateAdminUserToDb(id, admin);
			dbAdapter.close();

		} else {
			Toast.makeText(getActivity(),
					"نام کاربری و یا کلمه عبور به درستی وارد نشده است.",
					Toast.LENGTH_SHORT).show();
			editor.putBoolean("isLogin", false);

		}

		editor.commit();

	}
}
