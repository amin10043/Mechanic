package com.project.mechanic.fragment;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mechanic.R;
import com.project.mechanic.entity.Users;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.inter.GetAsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.SavingImage;
import com.project.mechanic.utility.ServiceComm;
import com.project.mechanic.utility.Utility;

public class RegisterFragment extends Fragment implements AsyncInterface,
		GetAsyncInterface {

	protected static final Context Contaxt = null;
	int resourceId;

	Fragment fragment;
	int ticketTypeID;
	int ProvinceId;
	ImageView btnaddpic1;
	List<Users> list;
	Utility utile;
	LinearLayout.LayoutParams lp;
	ServiceComm service;
	ProgressDialog ringProgressDialog;
	String Name;
	String Mobile;
	String Pass;
	PersianDate date;
	String txtdate;
	TelephonyManager tm;
	String number;
	SharedPreferences server;
	int serverId = 0;
	protected static final int RESULT_LOAD_IMAGE = 1;
	DataBaseAdapter dbAdapter;
	private Activity view;
	TextView txtclickpic;
	private Toast toast;
	ViewGroup toastlayout;

	SavingImage savingImage;
	private boolean firstTime = true;

	View view2;

	public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 0, outputStream);
		return outputStream.toByteArray();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_register, null);

		utile = new Utility(getActivity());
		service = new ServiceComm(getActivity());
		dbAdapter = new DataBaseAdapter(getActivity());
		date = new PersianDate();
		tm = (TelephonyManager) getActivity().getSystemService(
				Context.TELEPHONY_SERVICE);
		btnaddpic1 = (ImageView) view.findViewById(R.id.btnaddpic);
		Button btncan = (Button) view.findViewById(R.id.btncancle2);
		final Button btnreg = (Button) view.findViewById(R.id.btnreg2);
		final TextView comregtxt = (TextView) view
				.findViewById(R.id.compeletereg);
		final EditText editname = (EditText) view.findViewById(R.id.Textname);
		final EditText editmobile = (EditText) view
				.findViewById(R.id.mobiletxt);
		final EditText editpass = (EditText) view.findViewById(R.id.Textpass);
		btnreg.setEnabled(false);
		final CheckBox Rulescheck = (CheckBox) view
				.findViewById(R.id.rulescheck);

		toastlayout = (ViewGroup) view.findViewById(R.id.toast_layout);
		TextView textrules = (TextView) view.findViewById(R.id.txtrulles);
		ScrollView scroll_vertical_register1 = (ScrollView) view
				.findViewById(R.id.scroll_vertical_register);
		final LinearLayout lin1 = (LinearLayout) view.findViewById(R.id.lin1);

		LayoutInflater inflater1 = getLayoutInflater(getArguments());
		final View view2 = inflater1
				.inflate(R.layout.toast_define, toastlayout);
		btnaddpic1.setBackgroundResource(R.drawable.i13);
		lp = new LinearLayout.LayoutParams(lin1.getLayoutParams());
		lp.width = utile.getScreenwidth() / 4;
		lp.height = utile.getScreenwidth() / 4;
		btnaddpic1.setLayoutParams(lp);
		// ///////////////////////////////////////////////////

		server = getActivity().getSharedPreferences("sId", 0);

		textrules.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				FragmentTransaction trans = getActivity()
						.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new DisplayeRullseFragment());
				trans.commit();

			}
		});

		Rulescheck.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// is chkIos checked?
				if (((CheckBox) v).isChecked()) {
					btnreg.setEnabled(true);
				} else {

					btnreg.setEnabled(false);

				}
				StringBuffer result = new StringBuffer();
				result.append("Linux check : ").append(Rulescheck.isChecked());

				Context context;

			}
		});
		btnreg.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Name = editname.getText().toString();
				Mobile = editmobile.getText().toString();
				Pass = editpass.getText().toString();
				txtdate = date.todayShamsi();
				number = tm.getLine1Number();

				if (!utile.isNetworkConnected()) {
					utile.showOkDialog(getActivity(), "خطا در ارتباط",
							"شما به اینترنت متصل نیستید.");
				}

				else if (Name.equals("") || Pass.equals("")
						|| Mobile.equals("")) {

					utile.showtoast(view2, R.drawable.errormassage,
							"لطفا فیلدهای اجباری را پر نمایید", "خطا");

					toast = new Toast(getActivity());
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.setDuration(Toast.LENGTH_LONG);
					toast.setView(view2);
					toast.show();
				}

				else if (!isValidName(Name)) {
					editname.setError(" نام و نام خانوادگی شما نامعتبر است");
				}

				else if (!isValidPassword(Pass)) {
					editpass.setError("رمز عبور نا معتبر است");
				}

				else {
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

					comregtxt.setVisibility(View.VISIBLE);
					btnreg.setEnabled(false);
					// //////////////////////////////////////////////////////////////////

					// //////////////////////////
					Map<String, String> items = new HashMap<String, String>();
					items.put("register", "register");
					items.put("username", Name);
					items.put("email", "");
					items.put("password", Pass);
					items.put("phone", "");
					items.put("mobile", Mobile);
					items.put("fax", "0");
					items.put("address", "");
					items.put("date", txtdate);

					service.delegate = RegisterFragment.this;
					service.execute(items);

				}

			}
		});

		comregtxt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// final String Name = editname.getText().toString();

				dbAdapter = new DataBaseAdapter(getActivity());
				dbAdapter.open();

				int id = dbAdapter.getcount();
				dbAdapter.close();

				FragmentTransaction trans = getActivity()
						.getSupportFragmentManager().beginTransaction();
				CompeleteRegisterFragment fragment = new CompeleteRegisterFragment();

				Bundle bundle = new Bundle();

				bundle.putString("Id", String.valueOf(id));
				fragment.setArguments(bundle);
				trans.replace(R.id.content_frame, fragment);
				trans.commit();
			}
		});

		btncan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				FragmentTransaction trans = getActivity()
						.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new MainFragment());
				trans.commit();
			}
		});

		btnaddpic1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				getActivity().startActivityFromFragment(RegisterFragment.this,
						i, RESULT_LOAD_IMAGE);
			}
		});

		return view;
	}

	@Override
	public void processFinish(String output) {
		ringProgressDialog.dismiss();

		try {
			serverId = Integer.valueOf(output);

			// saveImage
			if (serverId > 0) {

				server.edit().putInt("srv_id", serverId).commit();

				dbAdapter.open();

				if ((btnaddpic1.getDrawable() != null) && firstTime) {

					Bitmap bitmap = ((BitmapDrawable) btnaddpic1.getDrawable())
							.getBitmap();

					Bitmap emptyBitmap = Bitmap.createBitmap(bitmap.getWidth(),
							bitmap.getHeight(), bitmap.getConfig());

					firstTime = false;
					if (!bitmap.sameAs(emptyBitmap)) {

						byte[] Image = getBitmapAsByteArray(bitmap);
						savingImage = new SavingImage(getActivity());
						Map<String, Object> it = new LinkedHashMap<String, Object>();
						it.put("tableName", "Users");
						it.put("fieldName", "Image");
						it.put("id", serverId);
						it.put("Image", Image);

						savingImage.delegate = this;
						savingImage.execute(it);
					}
				} else {
					dbAdapter.inserUsernonpicToDb(serverId, Name, null, Pass,
							null, Mobile, null, null, 0, txtdate);
				}

				dbAdapter.close();

				LayoutInflater inflater4 = getLayoutInflater(getArguments());
				View view4 = inflater4.inflate(R.layout.toast_define,
						toastlayout);
				utile.showtoast(view4, R.drawable.massage,
						"اطلاعات مورد نظر ثبت شد", "پیغام");

				toast = new Toast(getActivity());
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.setDuration(Toast.LENGTH_LONG);
				toast.setView(view4);
				toast.show();

				// if ("true".equals(output)) {
				// SharedPreferences settings = getActivity()
				// .getSharedPreferences("user", 0);
				// SharedPreferences.Editor editor = settings.edit();
				// editor.putBoolean("isLogin", true);
				//
				// // ثبت اطلاعات کاربر در دیتا بیس هم حتما انجام گیرد. فراموش
				// // نشود!!!!
				//
				// FragmentTransaction trans = getActivity()
				// .getSupportFragmentManager().beginTransaction();
				// trans.replace(R.id.content_frame, new MainFragment());
				// trans.commit();
				// dbAdapter.open();
				// u = dbAdapter.getUserbymobailenumber(mobileNumber);
				// if (u != null) {
				// int id = u.getId();
				// int admin = 1;
				// dbAdapter.UpdateAdminUserToDb(id, admin);
				// }
				// dbAdapter.close();
				// // } else {
				// Toast.makeText(
				// getActivity(),
				// "شما وارد شده اید اما شماره تلفن به درستی وارد نشده است.",
				// Toast.LENGTH_SHORT).show();
				// }
				// Toast.makeText(getActivity(), "شما وارد شده اید.",
				// Toast.LENGTH_SHORT).show();
				//
				// }

			}

			else {
				LayoutInflater inflater5 = getLayoutInflater(getArguments());
				View view5 = inflater5.inflate(R.layout.toast_define,
						toastlayout);
				utile.showtoast(view5, R.drawable.errormassage,
						"شما به سرویس متصل نشده اید", "خطا");

				toast = new Toast(getActivity());
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.setDuration(Toast.LENGTH_LONG);
				toast.setView(view5);
				toast.show();
			}
		} catch (Exception ex) {
			Toast.makeText(getActivity(), "khata", Toast.LENGTH_SHORT).show();
		}

	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RESULT_LOAD_IMAGE
				&& resultCode == Activity.RESULT_OK && null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getActivity().getContentResolver().query(
					selectedImage, filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();

			// ImageView btnaddpic1 = (ImageView) view
			// .findViewById(R.id.btnaddpic);
			btnaddpic1.setImageBitmap(BitmapFactory.decodeFile(picturePath));
			btnaddpic1.setBackgroundColor(getResources().getColor(
					android.R.color.transparent));

			btnaddpic1.setLayoutParams(lp);
			// txtclickpic.setVisibility(View.INVISIBLE);
		}

	}

	private boolean isValidName(String name) {
		String Name_PATTERN = "[a-zA-Z0-9-ا-ی ]+";
		// String Name_PATTERN = "[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)" +
		// "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)" ;
		Pattern pattern = Pattern.compile(Name_PATTERN);
		Matcher matcher = pattern.matcher(name);
		return matcher.matches();
	}

	// validating password with retype password
	private boolean isValidPassword(String pass) {
		if (pass.length() > 5) {
			return true;
		}

		return false;
	}

	private boolean isValidEmail(String email) {
		String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	@Override
	public void processFinish(byte[] output) {
		// TODO Auto-generated method stub

	}

}
