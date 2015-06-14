package com.project.mechanic.fragment;

import java.util.LinkedHashMap;
import java.util.Map;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
	String mobileNumber = "";
	ProgressDialog pDialog;
	ProgressDialog ringProgressDialog;
	Handler updateBarHandler;
	final int progress_bar_type = 0;
	ViewGroup toastlayout;
	private Toast toast;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		util = new Utility(getActivity());
		dbAdapter = new DataBaseAdapter(getActivity());
		updateBarHandler = new Handler();
		// ((MainActivity) getActivity()).setActivityTitle(R.string.Propaganda);
		View view = inflater.inflate(R.layout.fragment_login, null);

		Button btnlog = (Button) view.findViewById(R.id.btnlogin);
		Button btncancle = (Button) view.findViewById(R.id.btncancle);
		// Button launchRingDialog= (Button) view.findViewById(R.id.btnring);
		TextView btnreg = (TextView) view.findViewById(R.id.btnreg1);
		TextView btnforgot = (TextView) view.findViewById(R.id.btnforgot);
		editmobile = (EditText) view.findViewById(R.id.editTextmobile);
		final EditText editpass = (EditText) view
				.findViewById(R.id.editTextpass);
		// TextView test = (TextView) view.findViewById(R.id.texttest);
		btnlog.setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void onClick(View v) {

				dbAdapter.open();

				mobile = editmobile.getText().toString();
				pass = editpass.getText().toString();
				// Toast.makeText(getActivity(), mobile, Toast.LENGTH_SHORT)
				// .show();
				dbAdapter.close();
				if (!util.isNetworkConnected()) {
					util.showOkDialog(getActivity(), "خطا در ارتباط",
							"شما به اینترنت متصل نیستید.");
				}

				else if ("".equals(mobile) || "".equals(pass)) {
					// Toast.makeText(getActivity(),
					// "تلفن همراه و کلمه عبور نمی تواند خالی باشد.",
					// Toast.LENGTH_SHORT).show();

					LayoutInflater inflater4 = getLayoutInflater(getArguments());
					View view4 = inflater4.inflate(R.layout.toast_define,
							toastlayout);
					util.showtoast(view4, R.drawable.massage,
							"تلفن همراه و کلمه عبور نمی تواند خالی باشد",
							"اخطار");

					toast = new Toast(getActivity());
					toast.setGravity(Gravity.CENTER, 0, 0);

					toast.setDuration(Toast.LENGTH_SHORT);
					toast.setView(view4);
					toast.show();

				}

				else {

					// String[] params = new String[] { "login", mobile, pass };
					service = new ServiceComm(getActivity());
					service.delegate = LoginFragment.this;
					Map<String, String> items = new LinkedHashMap<String, String>();
					items.put("login", "login");
					items.put("phone", mobile);
					items.put("password", pass);

					service.execute(items);
					mobileNumber = mobile;

					ringProgressDialog = ProgressDialog.show(getActivity(), "",
							"لطفا منتظر بمانید...", true);

					ringProgressDialog.setCancelable(true);

					new Thread(new Runnable() {

						@Override
						public void run() {

							try {

								Thread.sleep(10000);

							} catch (Exception e) {

							}
						}
					}).start();

				}
			}
		});

		btnreg.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

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

		ringProgressDialog.dismiss();

		SharedPreferences settings = getActivity().getSharedPreferences("user",
				0);
		SharedPreferences.Editor editor = settings.edit();
		if ("true".equals(output)) {

			editor.putBoolean("isLogin", true);

			// ثبت اطلاعات کاربر در دیتا بیس هم حتما انجام گیرد. فراموش نشود!!!!

			FragmentTransaction trans = getActivity()
					.getSupportFragmentManager().beginTransaction();
			trans.replace(R.id.content_frame, new MainFragment());
			trans.commit();
			// String mobile2 = editmobile.getText().toString();

			// if (mobile != null) {
			//
			//

			dbAdapter.open();
			u = dbAdapter.getUserbymobailenumber(mobileNumber);
			if (u != null) {
				int id = u.getId();
				int admin = 1;
				dbAdapter.UpdateAdminUserToDb(id, admin);
			}
			dbAdapter.close();
			// } else {
			// Toast.makeText(
			// getActivity(),
			// "شما وارد شده اید اما شماره تلفن به درستی وارد نشده است.",
			// Toast.LENGTH_SHORT).show();
			// }
			Toast.makeText(getActivity(), "شما وارد شده اید.",
					Toast.LENGTH_SHORT).show();
			TextView txtlike = (TextView) (getActivity())
					.findViewById(R.id.txtlike);
			txtlike.setVisibility(View.VISIBLE);
			TextView txtcm1 = (TextView) (getActivity())
					.findViewById(R.id.txtcm);
			txtcm1.setVisibility(View.VISIBLE);

			util.setNoti(getActivity(), u.getId());

		} else {

			Toast.makeText(getActivity(),
					"نام کاربری و یا کلمه عبور به درستی وارد نشده است.",
					Toast.LENGTH_SHORT).show();
			editor.putBoolean("isLogin", false);

		}

		editor.commit();

	}
}
