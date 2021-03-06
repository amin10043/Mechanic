package com.project.mechanic.fragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mechanic.R;
import com.project.mechanic.StaticValues;
import com.project.mechanic.crop.CropImage;
import com.project.mechanic.entity.City;
import com.project.mechanic.entity.Province;
import com.project.mechanic.entity.Users;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.inter.CommInterface;
import com.project.mechanic.inter.SaveAsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.Saving;
import com.project.mechanic.service.SavingImage;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.service.ServiceComm;
import com.project.mechanic.utility.Utility;

public class RegisterFragment extends Fragment implements AsyncInterface, SaveAsyncInterface,CommInterface {

	int resourceId;

	Fragment fragment;
	int ticketTypeID;
	int ProvinceId;
	ImageView btnaddpic1;
	List<Users> list;
	Utility utile;
	LinearLayout.LayoutParams lp;
//	ProgressDialog ringProgressDialog;
	String Name;
	String Mobile;
	String Pass;
	PersianDate date;
	String txtdate;
	TelephonyManager tm;
	String number;
	SharedPreferences server;
	int serverId = 0;
	protected static final int RESULT_LOAD_IMAGE = 2;
	DataBaseAdapter dbAdapter;
	TextView txtclickpic;
	private Toast toast;
	ViewGroup toastlayout;
	Users u;
	SavingImage savingImage;
	private boolean firstTime = true;
	private File mFileTemp;
	int MaxSizeImageSelected = 5;

	View view2, view;
	Uri selectedImage;
	private Uri picUri;
	ProgressDialog imageloadprogressdialog;
	private static final int PICK_FROM_CAMERA = 1;
	final int PIC_CROP = 3;
	int ostanId = 0, cityId = 0, dayId = 0, monthId = 0, yearId = 0;
	ArrayList<City> cityList;
	boolean flag;
	String birthday;
	byte[] Image;
	Button btncan, btnreg;
	EditText editname, editmobile, editpass;
	TextView comregtxt;

	String selectOstanLable = "انتخاب استان";
	String selectCityLable = "انتخاب شهر";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fragment_register, null);

		utile = new Utility(getActivity());
		dbAdapter = new DataBaseAdapter(getActivity());
		date = new PersianDate();
		tm = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
		btnaddpic1 = (ImageView) view.findViewById(R.id.btnaddpic);
		btncan = (Button) view.findViewById(R.id.btncancle2);
		btnreg = (Button) view.findViewById(R.id.btnreg2);
		comregtxt = (TextView) view.findViewById(R.id.compeletereg);
		editname = (EditText) view.findViewById(R.id.Textname);
		editmobile = (EditText) view.findViewById(R.id.mobiletxt);
		editpass = (EditText) view.findViewById(R.id.Textpass);
		// btnreg.setEnabled(false);

		final CheckBox Rulescheck = (CheckBox) view.findViewById(R.id.rulescheck);

		toastlayout = (ViewGroup) view.findViewById(R.id.toast_layout);
		TextView textrules = (TextView) view.findViewById(R.id.txtrulles);
		final LinearLayout lin1 = (LinearLayout) view.findViewById(R.id.lin1);

		LayoutInflater inflater1 = getLayoutInflater(getArguments());
		final View view2 = inflater1.inflate(R.layout.toast_define, toastlayout);
//		btnaddpic1.setBackgroundResource(R.drawable.i13);
		;

		lp = new LinearLayout.LayoutParams(lin1.getLayoutParams());
		lp.width = (int) (utile.getScreenwidth() / StaticValues.RateImageRegisterPage);
		lp.height = (int) (utile.getScreenwidth() / StaticValues.RateImageRegisterPage);
		btnaddpic1.setLayoutParams(lp);

		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			mFileTemp = new File(Environment.getExternalStorageDirectory(), AnadFragment.TEMP_PHOTO_FILE_NAME);
		} else {
			mFileTemp = new File(getActivity().getFilesDir(), AnadFragment.TEMP_PHOTO_FILE_NAME);
		}

		server = getActivity().getSharedPreferences("sId", 0);

		textrules.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new DisplayeRullseFragment());
				trans.commit();

			}
		});

		// Rulescheck.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // is chkIos checked?
		// if (((CheckBox) v).isChecked()) {
		// btnreg.setEnabled(true);
		// // btnreg.setBackgroundColor(R.drawable.buttonshape);
		// } else {
		//
		// btnreg.setEnabled(false);
		// // btnreg.setBackgroundColor(R.drawable.buttonshape2);
		// }
		// StringBuffer result = new StringBuffer();
		// result.append("Linux check : ").append(Rulescheck.isChecked());
		// }
		// });
		btnreg.setOnClickListener(new OnClickListener() {
			@SuppressWarnings("unchecked")
			public void onClick(View arg0) {

				Name = editname.getText().toString();
				Mobile = editmobile.getText().toString();
				Pass = editpass.getText().toString();
				txtdate = date.todayShamsi();
				number = tm.getLine1Number();

				if (Rulescheck.isChecked()) {

					if (ostanId != 0 && cityId != 0) {

						if (!utile.isNetworkConnected()) {
							utile.showOkDialog(getActivity(), "خطا در ارتباط", "شما به اینترنت متصل نیستید.");
						}

						else if (Name.equals("") || Pass.equals("") || Mobile.equals("")) {

							utile.showtoast(view2, R.drawable.errormassage, "لطفا فیلدهای اجباری را پر نمایید", "خطا");

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
//							ringProgressDialog = ProgressDialog.show(getActivity(), "", "لطفا منتظر بمانید...", true);
//
//							ringProgressDialog.setCancelable(true);
//							new Thread(new Runnable() {
//								@Override
//								public void run() {
//									try {
//										Thread.sleep(10000);
//									} catch (Exception e) {
//									}
//								}
//							}).start();

							comregtxt.setVisibility(View.VISIBLE);
							// btnreg.setEnabled(false);

							ServerDate date = new ServerDate(getActivity());
							date.delegate = RegisterFragment.this;
							date.execute("");
						}

					} else {
						Toast.makeText(getActivity(), "انتخاب استان و شهر اجباری است", 0).show();

					}

				} else {
					Toast.makeText(getActivity(), " باید قوانین را مطالعه و بپذیرید", 0).show();
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

				FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();
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

				FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new MainFragment());
				trans.commit();
			}
		});

		btnaddpic1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				final CharSequence[] items = { "گالری تصاویر", "دوربین" };
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setItems(items, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						if (item == 0) {
							do_gallery_work();
						} else {
							do_cam_work();
						}
					}
				});
				builder.show();

			}
		});
		final Spinner daySpinner = (Spinner) view.findViewById(R.id.daySpinner);
		final Spinner monthSpinner = (Spinner) view.findViewById(R.id.monthSpinner);
		final Spinner yearSpinner = (Spinner) view.findViewById(R.id.yearSpinner);

		final ArrayList<String> dayList = new ArrayList<String>();
		final ArrayList<String> monthList = new ArrayList<String>();
		final ArrayList<String> yearList = new ArrayList<String>();

		for (int i = 1; i <= 31; i++) {
			dayList.add(i + "");
		}
		for (int i = 1; i <= 12; i++) {
			monthList.add(i + "");
		}
		for (int i = 1300; i <= 1400; i++) {
			yearList.add(i + "");
		}

		ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,
				dayList);

		dayadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		daySpinner.setAdapter(dayadapter);

		ArrayAdapter<String> monthAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, monthList);

		monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		monthSpinner.setAdapter(monthAdapter);

		ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,
				yearList);

		yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		yearSpinner.setAdapter(yearAdapter);

		daySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {

				int day = (int) daySpinner.getSelectedItemId();

				dayId = Integer.valueOf(dayList.get(day));
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				//

			}
		});

		monthSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {

				int month = (int) monthSpinner.getSelectedItemId();

				monthId = Integer.valueOf(monthList.get(month));
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				//

			}
		});

		yearSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {

				int year = (int) yearSpinner.getSelectedItemId();

				yearId = Integer.valueOf(yearList.get(year));
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				//

			}
		});

		final Spinner ostanSpinner = (Spinner) view.findViewById(R.id.ostanSpinner);
		final Spinner citySpinner = (Spinner) view.findViewById(R.id.CitySpinner);

		dbAdapter.open();

		final ArrayList<Province> ostanList = dbAdapter.getAllProvinceNoSorting();

		dbAdapter.close();

		ArrayList<String> NameOstan = new ArrayList<String>();
		final ArrayList<String> NameCity = new ArrayList<String>();

		NameOstan.add(selectOstanLable);
		for (int i = 0; i < ostanList.size(); i++) {

			NameOstan.add(ostanList.get(i).getName());

		}

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,
				NameOstan);

		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		ostanSpinner.setAdapter(dataAdapter);
		citySpinner.setEnabled(false);

		ostanSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {

				int w = (int) ostanSpinner.getSelectedItemId() - 1;

				String item = (String) ostanSpinner.getSelectedItem();

				if (!item.equals(selectOstanLable)) {

					ostanId = ostanList.get(w).getId();

					dbAdapter.open();
					cityList = dbAdapter.getCitysByProvinceIdNoSort(ostanId);
					dbAdapter.close();

					citySpinner.setEnabled(true);

					NameCity.clear();
					NameCity.add(selectCityLable);

					for (int i = 0; i < cityList.size(); i++) {

						NameCity.add(cityList.get(i).getName());

					}
					ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
							android.R.layout.simple_spinner_item, NameCity);

					dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

					citySpinner.setAdapter(dataAdapter);
					Toast.makeText(getActivity(), ostanId + "", 0).show();
				} else {
					citySpinner.setEnabled(false);
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				//

			}
		});

		citySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {

				int m = (int) citySpinner.getSelectedItemId() - 1;
				String item = (String) citySpinner.getSelectedItem();

				if (!item.equals(selectCityLable)) {

					cityId = cityList.get(m).getId();
					Toast.makeText(getActivity(), cityId + "", 0).show();

				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				//

			}
		});
		daySpinner.setEnabled(false);
		monthSpinner.setEnabled(false);
		yearSpinner.setEnabled(false);
		final CheckBox isActiveBirthDay = (CheckBox) view.findViewById(R.id.checkBox1);
		isActiveBirthDay.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (isActiveBirthDay.isChecked()) {
					daySpinner.setEnabled(true);
					monthSpinner.setEnabled(true);
					yearSpinner.setEnabled(true);

					flag = true;
				} else {
					daySpinner.setEnabled(false);
					monthSpinner.setEnabled(false);
					yearSpinner.setEnabled(false);
					flag = false;
				}
			}
		});

		setFont();

		return view;
	}

	public void do_cam_work() {
		imageloadprogressdialog = ProgressDialog.show(getActivity(), "",
				"در حال باز کردن دوربین. لطفا منتظر بمانید...");
		try {
			Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(captureIntent, PICK_FROM_CAMERA);
		} catch (ActivityNotFoundException anfe) {
			Toast toast = Toast.makeText(getActivity(), "این دستگاه از برش تصویر پشتیبانی نمی کند.",
					Toast.LENGTH_SHORT);
			toast.show();
		}

	}

	public void do_gallery_work() {
		Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

		startActivityForResult(i, RESULT_LOAD_IMAGE);
	}

	@SuppressWarnings("deprecation")
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode != Activity.RESULT_OK) {
			return;
		}
		if (requestCode == PICK_FROM_CAMERA && data != null) {
			picUri = data.getData();
			try {
				Intent cropIntent = new Intent("com.android.camera.action.CROP");
				cropIntent.setDataAndType(picUri, "image/*");
				cropIntent.putExtra("scale", "true");
				cropIntent.putExtra("aspectX", 1);
				cropIntent.putExtra("aspectY", 1);
				cropIntent.putExtra("outputX", 256);
				cropIntent.putExtra("outputY", 256);
				cropIntent.putExtra("return-data", true);
				Bundle extras = data.getExtras();
				Bitmap thePic = extras.getParcelable("data");
				btnaddpic1.setImageBitmap(thePic);
				if (imageloadprogressdialog != null)
					imageloadprogressdialog.dismiss();
			} catch (ActivityNotFoundException anfe) {
				Toast toast = Toast.makeText(getActivity(), "این دستگاه از برش تصویر پشتیبانی نمی کند.",
						Toast.LENGTH_SHORT);
				toast.show();
			}

		}

		else if (requestCode == RESULT_LOAD_IMAGE) {
			try {
				long sizePicture = 0; // MB

				InputStream inputStream = getActivity().getContentResolver().openInputStream(data.getData());
				FileOutputStream fileOutputStream = new FileOutputStream(mFileTemp);
				Utility.copyStream(inputStream, fileOutputStream);
				fileOutputStream.close();
				inputStream.close();

				if (mFileTemp != null)
					sizePicture = mFileTemp.length() / 1024 / 1024;

				if (sizePicture > MaxSizeImageSelected)
					Toast.makeText(getActivity(), "حجم عکس انتخاب شده باید کمتر از " + MaxSizeImageSelected + "مگابایت باشد ",
							Toast.LENGTH_LONG).show();
				else
					startCropImage();				
				
				
				
				

			} catch (Exception e) {

				Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
			}

		} else if (requestCode == PIC_CROP) {
			String path = data.getStringExtra(CropImage.IMAGE_PATH);
			if (path == null) {
				return;
			}

			Bitmap bitmap = BitmapFactory.decodeFile(mFileTemp.getPath());
			btnaddpic1.setImageBitmap(bitmap);
			
			Image = Utility.compressImage(mFileTemp);

		} else {
			Toast.makeText(getActivity(), "Picture NOt taken", Toast.LENGTH_LONG).show();
		}
		btnaddpic1.setLayoutParams(lp);

		if (imageloadprogressdialog != null) {
			imageloadprogressdialog.dismiss();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void startCropImage() {

		Intent intent = new Intent(getActivity(), CropImage.class);
		intent.putExtra(CropImage.IMAGE_PATH, mFileTemp.getPath());
		intent.putExtra(CropImage.SCALE, true);

		intent.putExtra(CropImage.ASPECT_X, 3);
		intent.putExtra(CropImage.ASPECT_Y, 3);

		startActivityForResult(intent, PIC_CROP);
	}

	private boolean isValidName(String name) {
		String Name_PATTERN = "[a-zA-Z0-9ا-ی ]+";
		// String Name_PATTERN = "[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)" +
		// "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)" ;
		Pattern pattern = Pattern.compile(Name_PATTERN);
		Matcher matcher = pattern.matcher(name);
		return matcher.matches();
	}

	private boolean isValidPassword(String pass) {
		if (pass.length() > 5) {
			return true;
		}

		return false;
	}

	@Override
	public void processFinishSaveImage(String output) {
		int res = 0;
		try {
			res = Integer.valueOf(output);
			if (res > 0) {
				SharedPreferences settings = getActivity().getSharedPreferences("user", 0);
				SharedPreferences.Editor editor = settings.edit();
				editor.putBoolean("isLogin", true);
				editor.commit();
				FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new MainFragment());
				trans.commit();

				dbAdapter.open();

				dbAdapter.inserUserToDb(serverId, Name, null, Pass, null, Mobile, null, null, null, 0, txtdate,
						birthday, cityId);
				if (output != null) {

					utile.CreateFile(Image, serverId, "Mechanical", "Users", "user", "Users");
				}

				// dbAdapter.inserUserToDb(serverId, Name, null, Pass,
				// null, Mobile, null, null, Image, 0, txtdate);

				u = dbAdapter.getUserbymobailenumber(Mobile);
				if (u != null) {
					int id = u.getId();
					int admin = 1;
					dbAdapter.UpdateAdminUserToDb(id, admin);
				}
				dbAdapter.close();

				Toast.makeText(getActivity(), "شما وارد شده اید.", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getActivity(), "خطا در ثبت عکس", Toast.LENGTH_SHORT).show();
			}

		} catch (Exception ex) {
			Toast.makeText(getActivity(), "خطا در ثبت عکس", Toast.LENGTH_SHORT).show();
		}
//		if (ringProgressDialog != null) {
//			ringProgressDialog.dismiss();
//		}
	}

	@Override
	public void processFinish(String output) {
//		ringProgressDialog.dismiss();	
			try {
				serverId = Integer.valueOf(output);
				// saveImage
			} catch (Exception ex) {
		
				if (getActivity() != null) {
		
					if (flag == true) {	
						birthday = yearId + "/" + monthId + "/" + dayId;
					} else
						birthday = "";
					ServiceComm comm = new ServiceComm(getActivity());
					comm.delegate = this;
					Map<String,String> params = new LinkedHashMap<String,String>();
					params.put("tableName", "register");
					params.put("username", Name);
//					params.put("email", "");
					params.put("password", Pass);
//					params.put("phone", "");
					params.put("mobile", Mobile);
//					params.put("fax", "1");
//					params.put("address", "");
					params.put("date", output);
					params.put("modifyDate", output);
					params.put("BirthDay", birthday);
					params.put("cityId", String.valueOf(cityId));
//					ringProgressDialog = ProgressDialog.show(getActivity(), "", "لطفا منتظر بمانید...", true);
					comm.execute(params);			
				}
			}
	}
	private void setFont() {

		TextView txtHeader = (TextView) view.findViewById(R.id.txt_title_register);
		TextView lables1 = (TextView) view.findViewById(R.id.lables);
		TextView lables2 = (TextView) view.findViewById(R.id.cdas);
		TextView lables3 = (TextView) view.findViewById(R.id.lablesostan);
		TextView lables4 = (TextView) view.findViewById(R.id.lablescity);
		TextView lables5 = (TextView) view.findViewById(R.id.txtrulles);

		txtHeader.setTypeface(utile.SetFontCasablanca());
		lables1.setTypeface(utile.SetFontCasablanca());
		lables2.setTypeface(utile.SetFontCasablanca());
		lables3.setTypeface(utile.SetFontCasablanca());
		lables4.setTypeface(utile.SetFontCasablanca());
		lables5.setTypeface(utile.SetFontIranSans());

		btnreg.setTypeface(utile.SetFontCasablanca());
		btncan.setTypeface(utile.SetFontCasablanca());

	}

	@Override
	public void CommProcessFinish(String output) {
//		ringProgressDialog.dismiss();
		if("".equals(output) || "anyType{}".equals(output)){
			LayoutInflater inflater5 = getLayoutInflater(getArguments());
			View view5 = inflater5.inflate(R.layout.toast_define, toastlayout);
			utile.showtoast(view5, R.drawable.errormassage, "این شماره همراه قبلاً ثبت شده است.", "خطا");

			toast = new Toast(getActivity());
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.setDuration(Toast.LENGTH_LONG);
			toast.setView(view5);
			toast.show();
		}else{
			try{
				serverId = Integer.valueOf(output);
				server.edit().putInt("srv_id", serverId).commit();
				dbAdapter.open();
				if ((btnaddpic1.getDrawable() != null) && firstTime) {
					Bitmap bitmap = ((BitmapDrawable) btnaddpic1.getDrawable()).getBitmap();
					Bitmap emptyBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
					firstTime = false;
					if (!bitmap.sameAs(emptyBitmap)) {
						Map<String, Object> it = new LinkedHashMap<String, Object>();
	
//						Image = Utility.CompressBitmap(bitmap);
						if (getActivity() != null) {
							savingImage = new SavingImage(getActivity());
							it.put("tableName", "Users");
							it.put("fieldName", "Image");
							it.put("id", serverId);
							it.put("Image", Image);
						}
//						ringProgressDialog = ProgressDialog.show(getActivity(), "", "لطفا منتظر بمانید...", true);
						savingImage.delegate = this;
						savingImage.execute(it);
						// dbAdapter.inserUserToDb(serverId, Name, null, Pass,
						// null, Mobile, null, null, Image, 0, txtdate);
					}
				} else {
					dbAdapter.inserUsernonpicToDb(serverId, Name, null, Pass, null, Mobile, null, null, 0, txtdate,
							birthday, cityId);
					LayoutInflater inflater4 = getLayoutInflater(getArguments());
					View view4 = inflater4.inflate(R.layout.toast_define, toastlayout);
					utile.showtoast(view4, R.drawable.massage, "اطلاعات مورد نظر ثبت شد", "پیغام");
	
					toast = new Toast(getActivity());
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.setDuration(Toast.LENGTH_LONG);
					toast.setView(view4);
					toast.show();
	
					u = dbAdapter.getUserbymobailenumber(Mobile);
					if (u != null) {
						int id = u.getId();
						int admin = 1;
						dbAdapter.UpdateAdminUserToDb(id, admin);
					}
					SharedPreferences settings = getActivity().getSharedPreferences("user", 0);
					SharedPreferences.Editor editor = settings.edit();
					editor.putBoolean("isLogin", true);
					editor.commit();
	
					FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame, new MainFragment());
					trans.commit();
	
				}
				dbAdapter.close();
			} catch(Exception ex) {
				LayoutInflater inflater5 = getLayoutInflater(getArguments());
				View view5 = inflater5.inflate(R.layout.toast_define, toastlayout);
				utile.showtoast(view5, R.drawable.errormassage, "اطلاعات کاربر در سرور ثبت نشد.", "خطا");
	
				toast = new Toast(getActivity());
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.setDuration(Toast.LENGTH_LONG);
				toast.setView(view5);
				toast.show();
			}
		}
	}

}