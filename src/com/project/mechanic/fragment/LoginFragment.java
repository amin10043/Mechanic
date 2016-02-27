
package com.project.mechanic.fragment;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.project.mechanic.R;
import com.project.mechanic.entity.Users;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.inter.CommInterface;
import com.project.mechanic.inter.GetAsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.service.ServiceComm;
import com.project.mechanic.service.UpdatingImage;
import com.project.mechanic.utility.Utility;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LoginFragment extends Fragment implements CommInterface, AsyncInterface, GetAsyncInterface {

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
	String serverDate = "";
	boolean dateFlag = false;
	View view;
	String typeItem;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		util = new Utility(getActivity());
		dbAdapter = new DataBaseAdapter(getActivity());
		updateBarHandler = new Handler();
		// ((MainActivity) getActivity()).setActivityTitle(R.string.Propaganda);
		view = inflater.inflate(R.layout.fragment_login, null);

		RelativeLayout btnlog = (RelativeLayout) view.findViewById(R.id.btnlogin);
		// Button btncancle = (Button) view.findViewById(R.id.btncancle);
		// Button launchRingDialog= (Button) view.findViewById(R.id.btnring);
		RelativeLayout btnreg = (RelativeLayout) view.findViewById(R.id.btnreg1);
		TextView btnforgot = (TextView) view.findViewById(R.id.btnforgot);
		editmobile = (EditText) view.findViewById(R.id.editTextmobile);
		final EditText editpass = (EditText) view.findViewById(R.id.editTextpass);
		// TextView test = (TextView) view.findViewById(R.id.texttest);
		btnlog.setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void onClick(View v) {

				dbAdapter.open();

				mobile = editmobile.getText().toString();
				pass = editpass.getText().toString();
				dbAdapter.close();
				if (!util.isNetworkConnected()) {
					util.showOkDialog(getActivity(), "خطا در ارتباط", "شما به اینترنت متصل نیستید.");
				}

				else if ("".equals(mobile) || "".equals(pass)) {
					LayoutInflater inflater4 = getLayoutInflater(getArguments());
					View view4 = inflater4.inflate(R.layout.toast_define, toastlayout);
					util.showtoast(view4, R.drawable.massage, "تلفن همراه و کلمه عبور نمی تواند خالی باشد", "اخطار");

					toast = new Toast(getActivity());
					toast.setGravity(Gravity.CENTER, 0, 0);

					toast.setDuration(Toast.LENGTH_SHORT);
					toast.setView(view4);
					toast.show();

				}

				else {

					ServerDate date = new ServerDate(getActivity());
					date.delegate = LoginFragment.this;
					date.execute("");

					mobileNumber = mobile;

					ringProgressDialog = ProgressDialog.show(getActivity(), "", "لطفا منتظر بمانید...", true);

					ringProgressDialog.setCancelable(true);
				}

				InputMethodManager imm = (InputMethodManager) getActivity()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
			}

		});

		btnreg.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new RegisterFragment());
				trans.commit();

			}
		});

		btnforgot.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				dialog = new Dialogeml(LoginFragment.this, getActivity(), R.layout.dialog_addemail);
				dialog.setTitle("پیام");
				dialog.show();
			}
		});

		util.ShowFooterAgahi(getActivity(), false, 1);
		layoutParams();

		TextView lableEnter = (TextView) view.findViewById(R.id.lableEnter);
		TextView labletxt = (TextView) view.findViewById(R.id.lableee);
		TextView txtRegister = (TextView) view.findViewById(R.id.txt_title_register);
		TextView txttr = (TextView) view.findViewById(R.id.labler);

		lableEnter.setTypeface(util.SetFontCasablanca());
		labletxt.setTypeface(util.SetFontCasablanca());
		txttr.setTypeface(util.SetFontCasablanca());
		txtRegister.setTypeface(util.SetFontIranSans());

		return view;

	}

	@Override
	public void processFinish(String output) {
		if (ringProgressDialog != null)
			ringProgressDialog.dismiss();
		SharedPreferences settings = getActivity().getSharedPreferences("user", 0);
		SharedPreferences.Editor editor = settings.edit();

		if (output == null || "anyType{}".equals(output) || output.contains("Exception") || output.contains("java")) {
			Toast.makeText(getActivity(), "خطا در ارتباط با سرور.", Toast.LENGTH_SHORT).show();
			editor.putBoolean("isLogin", false);

		} else if (output.contains("SocketTimeoutException")) {
			Toast.makeText(getActivity(), "خطا در ارتباط با سرور. مهات زمانی تمام شده است", Toast.LENGTH_SHORT).show();

		} else if (output.contains("Exception") || output.contains("java")) {
			Toast.makeText(getActivity(), "خطایی در دریافت اطلاعات از سرور رخ داده است.", Toast.LENGTH_SHORT).show();

		} else {

			if (!dateFlag) {
				serverDate = output;
				service = new ServiceComm(getActivity());
				service.delegate = LoginFragment.this;
				Map<String, String> items = new LinkedHashMap<String, String>();
				items.put("login", "login");
				items.put("phone", mobile);
				items.put("password", pass);

				service.execute(items);

				typeItem = "login";
				ringProgressDialog = ProgressDialog.show(getActivity(), "", "لطفا منتظر بمانید...", true);
				dateFlag = true;
			} else {

				dbAdapter.open();
				u = dbAdapter.getUserbymobailenumber(mobileNumber);
				if (u == null) {
					util.parseQuery(output);
				}
				u = dbAdapter.getUserbymobailenumber(mobileNumber);
				dbAdapter.close();

				if (u == null) {
					Toast.makeText(getActivity(), "خطایی در دریافت اطلاعات از سرور رخ داده است.", Toast.LENGTH_SHORT)
							.show();
				} else {
					int id = u.getId();
					int admin = 1;
					String userImageServerDate = u.getImageServerDate();

					if (userImageServerDate == null)
						userImageServerDate = "";

					dbAdapter.open();
					dbAdapter.UpdateAdminUserToDb(id, admin);
					dbAdapter.close();
					Toast.makeText(getActivity(), "شما وارد شده اید.", Toast.LENGTH_SHORT).show();
					TextView txtlike = (TextView) (getActivity()).findViewById(R.id.txtlike);
					txtlike.setVisibility(View.VISIBLE);
					TextView txtcm1 = (TextView) (getActivity()).findViewById(R.id.txtcm);
					txtcm1.setVisibility(View.VISIBLE);
					editor.putBoolean("isLogin", true);

					UpdatingImage updating = new UpdatingImage(getActivity());
					updating.delegate = this;
					HashMap<String, String> maps = new LinkedHashMap<String, String>();
					maps.put("tableName", "Users");
					maps.put("Id", String.valueOf(u.getId()));
					maps.put("fromDate", userImageServerDate);
					updating.execute(maps);

					ringProgressDialog = ProgressDialog.show(getActivity(), "", "لطفا منتظر بمانید...", true);
				}
			}
		}

		editor.commit();
	}

	@Override
	public void processFinish(byte[] output) {
		if (ringProgressDialog != null) {
			ringProgressDialog.dismiss();
		}

		if (output != null) {

			// dbAdapter.open();

			util.CreateFile(output, u.getId(), "Mechanical", "Users", "user", "Users");

			// dbAdapter.UpdateUserImage(u.getId(), output, serverDate);
			// dbAdapter.close();
		}

		FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();
		trans.replace(R.id.content_frame, new MainFragment());
		trans.commit();

		util.setNoti(getActivity(), util.getCurrentUser().getId());

		if (getActivity() != null) {

			ServiceComm getDateService = new ServiceComm(getActivity());
			getDateService.delegate = LoginFragment.this;
			Map<String, String> items = new LinkedHashMap<String, String>();

			items.put("tableName", "getUserImageDate");
			items.put("Id", String.valueOf(u.getId()));

			getDateService.execute(items);
			typeItem = "getDate";

		}

	}

	@Override
	public void CommProcessFinish(String output) {

		if (!output.contains("Exception")) {

			if (typeItem.equals("login"))
				processFinish(output);

			if (typeItem.equals("getDate")) {
				if (output.contains("Exception") || output.contains("anyType"))
					output = "";

				dbAdapter.open();
				dbAdapter.UpdateImageServerDate(u.getId(), "Users", output);
				dbAdapter.close();

			}

		}
	}

	public void layoutParams() {

		RelativeLayout lii = (RelativeLayout) view.findViewById(R.id.linearlogin);
		FrameLayout fr = (FrameLayout) view.findViewById(R.id.framelogin);

		RelativeLayout.LayoutParams llp = new RelativeLayout.LayoutParams(lii.getLayoutParams());
		llp.width = LayoutParams.MATCH_PARENT;
		llp.height = LayoutParams.MATCH_PARENT;
		llp.setMargins(10, (util.getScreenHeight() / 10), 10, (util.getScreenHeight() / 10));

		fr.setLayoutParams(llp);

		RelativeLayout rl = (RelativeLayout) view.findViewById(R.id.lin7_register);

		RelativeLayout.LayoutParams rrr = new RelativeLayout.LayoutParams(rl.getLayoutParams());
		rrr.width = util.getScreenHeight() / 10;
		rrr.height = util.getScreenHeight() / 10;
		rrr.addRule(RelativeLayout.CENTER_HORIZONTAL);

		ImageView blankImage2 = (ImageView) view.findViewById(R.id.blankImage2);
		blankImage2.setLayoutParams(rrr);

		RelativeLayout ccc = (RelativeLayout) view.findViewById(R.id.layoutRelativelogin);

		ccc.setPadding(0, (util.getScreenHeight() / 10) - (util.getScreenHeight() / 20), 0,
				(util.getScreenHeight() / 10) - (util.getScreenHeight() / 20));

		//////////////

		RelativeLayout x = (RelativeLayout) view.findViewById(R.id.labelEnter);

		RelativeLayout.LayoutParams aaa = new RelativeLayout.LayoutParams(x.getLayoutParams());
		aaa.width = util.getScreenHeight() / 10;
		aaa.height = util.getScreenHeight() / 10;
		aaa.addRule(RelativeLayout.CENTER_HORIZONTAL);

		ImageView blankImage1 = (ImageView) view.findViewById(R.id.blankImage1);
		blankImage1.setLayoutParams(aaa);

	}

}
