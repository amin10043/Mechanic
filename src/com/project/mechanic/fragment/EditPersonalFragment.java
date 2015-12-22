package com.project.mechanic.fragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mechanic.R;
import com.project.mechanic.adapter.CropingOptionAdapter;
import com.project.mechanic.crop.CropImage;
import com.project.mechanic.entity.City;
import com.project.mechanic.entity.CropingOption;
import com.project.mechanic.entity.Province;
import com.project.mechanic.entity.Users;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.inter.SaveAsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.Saving;
import com.project.mechanic.service.SavingImage;
import com.project.mechanic.utility.Utility;

public class EditPersonalFragment extends Fragment implements AsyncInterface, SaveAsyncInterface {
	private static final int CAMERA_CODE = 101, GALLERY_CODE = 201, CROPING_CODE = 301;
	protected static final int RESULT_LOAD_IMAGE = 1;
	DataBaseAdapter dbAdapter;
	ImageView ImageProfile, imagecamera;
	RelativeLayout.LayoutParams lp2;
	Utility ut;
	int id;
	Saving saving;
	private Uri mImageCaptureUri;
	private File outPutFile = null;
	Map<String, String> params;
	Map<String, Object> imageParams;
	ProgressDialog dialog;
	SavingImage saveImage;
	String Address;
	String Cellphone;
	String Phone;
	String Email;
	String Fax, birthday = "", ProvinceCity;
	Context context;
	int gId;
	String[] viewItemArray = new String[5];
	String infoItem = "";

	int ostanId, cityId, dayId, monthId, yearId;
	ArrayList<City> cityList;
	boolean flag;
	private File mFileTemp;
	public static final String TEMP_PHOTO_FILE_NAME = "temp_photo.jpg";
	final int PIC_CROP = 10;

	EditText txtname;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.fragment_editpersonal, null);
		outPutFile = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");

		context = getActivity();
		dbAdapter = new DataBaseAdapter(getActivity());
		ut = new Utility(getActivity());
		final EditText txtaddress = (EditText) view.findViewById(R.id.etxtaddress);
		final EditText txtcellphone = (EditText) view.findViewById(R.id.etxtcellphone);
		final EditText txtphone = (EditText) view.findViewById(R.id.etxtphone);
		final EditText txtemail = (EditText) view.findViewById(R.id.etxtemail);
		txtname = (EditText) view.findViewById(R.id.etxtname);
		final EditText txtfax = (EditText) view.findViewById(R.id.etxtfax);
		ImageProfile = (ImageView) view.findViewById(R.id.imgp);
		Button btnregedit = (Button) view.findViewById(R.id.btnregedit);
		Button btnback = (Button) view.findViewById(R.id.btnbackdisplay);
		RelativeLayout lin3 = (RelativeLayout) view.findViewById(R.id.lin5);

		final CheckBox checkPhone = (CheckBox) view.findViewById(R.id.showPhoneValue);
		final CheckBox checkMobile = (CheckBox) view.findViewById(R.id.showmobileValue);
		final CheckBox checkEmail = (CheckBox) view.findViewById(R.id.showEmailValue);
		final CheckBox checkFax = (CheckBox) view.findViewById(R.id.showFaxValue);
		final CheckBox checkAddress = (CheckBox) view.findViewById(R.id.showAddressValue);
		Users u = ut.getCurrentUser();
		gId = u.getId();
		id = u.getId();
		final int cityIduser = u.getCityId();

		// byte[] bitmapbyte = u.getImage();

		String ImagePath = u.getImagePath();

		String v = "";
		String information = u.getShowInfoItem();
		if (information != null) {

			for (int i = 0; i < information.length(); i++) {
				v = (String) information.subSequence(i, i + 1);
				if (i == 0) {
					if (v.equals("1"))
						checkPhone.setChecked(true);
					else
						checkPhone.setChecked(false);

				}

				if (i == 1) {
					if (v.equals("1"))
						checkMobile.setChecked(true);
					else
						checkMobile.setChecked(false);

				}

				if (i == 2) {
					if (v.equals("1"))
						checkEmail.setChecked(true);
					else
						checkEmail.setChecked(false);

				}

				if (i == 3) {
					if (v.equals("1"))
						checkFax.setChecked(true);
					else
						checkFax.setChecked(false);

				}

				if (i == 4) {
					if (v.equals("1"))
						checkAddress.setChecked(true);
					else
						checkAddress.setChecked(false);

				}

			}
		}

		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			mFileTemp = new File(Environment.getExternalStorageDirectory(), TEMP_PHOTO_FILE_NAME);
		} else {
			mFileTemp = new File(getActivity().getFilesDir(), TEMP_PHOTO_FILE_NAME);
		}

		final Spinner daySpinner = (Spinner) view.findViewById(R.id.daySpinner);
		final Spinner monthSpinner = (Spinner) view.findViewById(R.id.monthSpinner);
		final Spinner yearSpinner = (Spinner) view.findViewById(R.id.yearSpinner);

		final Spinner ostanSpinner = (Spinner) view.findViewById(R.id.ostanSpinner);
		final Spinner citySpinner = (Spinner) view.findViewById(R.id.CitySpinner);

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

		dbAdapter.open();

		final ArrayList<Province> ostanList = dbAdapter.getAllProvinceNoSorting();

		dbAdapter.close();

		ArrayList<String> NameOstan = new ArrayList<String>();
		final ArrayList<String> NameCity = new ArrayList<String>();

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

				int w = (int) ostanSpinner.getSelectedItemId();

				ostanId = ostanList.get(w).getId();

				dbAdapter.open();
				cityList = dbAdapter.getCitysByProvinceIdNoSort(ostanId);
				dbAdapter.close();

				citySpinner.setEnabled(true);

				NameCity.clear();
				for (int i = 0; i < cityList.size(); i++) {

					NameCity.add(cityList.get(i).getName());

				}
				ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
						android.R.layout.simple_spinner_item, NameCity);

				dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

				citySpinner.setAdapter(dataAdapter);

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				//

			}
		});

		citySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {

				int m = (int) citySpinner.getSelectedItemId();

				cityId = cityList.get(m).getId();

				if (cityIduser != 0) {
					for (int i = 0; i < cityList.size(); i++) {

						City c = cityList.get(i);

						if (c.getId() == cityIduser) {
							citySpinner.setSelection(i);
							break;

						}
					}

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

		if (cityIduser != 0) {
			dbAdapter.open();

			City cityUser = dbAdapter.getCityById(cityIduser);
			Province provinceUser = dbAdapter.getProvinceById(cityUser.getProvinceId());
			ostanSpinner.setSelection(provinceUser.getId() - 1);
			cityList = dbAdapter.getCitysByProvinceIdNoSort(provinceUser.getId());
			for (int i = 0; i < cityList.size(); i++) {

				City c = cityList.get(i);

				if (c.getId() == cityUser.getId()) {
					citySpinner.setSelection(i);
					break;

				}
			}

			dbAdapter.close();
		}

		lp2 = new RelativeLayout.LayoutParams(lin3.getLayoutParams());
		lp2.height = ut.getScreenwidth() / 4;
		lp2.width = ut.getScreenwidth() / 4;
		lp2.addRule(RelativeLayout.CENTER_HORIZONTAL);
		lp2.setMargins(5, 5, 5, 5);
		ImageProfile.setLayoutParams(lp2);
		dbAdapter = new DataBaseAdapter(getActivity());

		if (ImagePath != null) {
			Bitmap bmp = BitmapFactory.decodeFile(ImagePath);
			if (bmp != null)
				ImageProfile.setImageBitmap(Utility.getclip(bmp));
		} else {
			ImageProfile.setImageResource(R.drawable.no_img_profile);
			ImageProfile.setBackgroundResource(R.drawable.circle_drawable);

		}
		ImageProfile.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				selectImageOption();
			}
		});
		txtname.setText(u.getName());
		txtemail.setText(u.getEmail());
		txtcellphone.setText(u.getMobailenumber());
		txtphone.setText(u.getPhonenumber());
		txtfax.setText(u.getFaxnumber());
		txtaddress.setText(u.getAddress());

		String DateBirthDay = u.getBirthDay();
		if (DateBirthDay != null) {
			if (!DateBirthDay.equals("")) {
				isActiveBirthDay.setChecked(true);
				String[] m = DateBirthDay.split("/");

				daySpinner.setSelection(Integer.valueOf(m[2]) - 1);
				monthSpinner.setSelection(Integer.valueOf(m[1]) - 1);

				int year = Integer.valueOf(m[0]) - 1300;
				yearSpinner.setSelection(year);

			}
		}

		btnregedit.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void onClick(View arg0) {

				dialog = ProgressDialog.show(getActivity(), "در حال بروزرسانی", "لطفا منتظر بمانید...");

				Address = txtaddress.getText().toString();
				Cellphone = txtcellphone.getText().toString();
				Phone = txtphone.getText().toString();
				Email = txtemail.getText().toString();
				Fax = txtfax.getText().toString();

				if (checkPhone.isChecked())
					viewItemArray[0] = "1";
				else
					viewItemArray[0] = "0";

				if (checkMobile.isChecked())
					viewItemArray[1] = "1";
				else
					viewItemArray[1] = "0";

				if (checkEmail.isChecked())
					viewItemArray[2] = "1";
				else
					viewItemArray[2] = "0";

				if (checkFax.isChecked())
					viewItemArray[3] = "1";
				else
					viewItemArray[3] = "0";

				if (checkAddress.isChecked())
					viewItemArray[4] = "1";
				else
					viewItemArray[4] = "0";

				for (int i = 0; i < viewItemArray.length; i++) {
					infoItem = infoItem + viewItemArray[i];
				}

				if (checkPhone.isChecked() || checkMobile.isChecked() || checkEmail.isChecked() || checkFax.isChecked()
						|| checkAddress.isChecked())

				{
					if (getActivity() != null) {
						saving = new Saving(getActivity());
						saving.delegate = EditPersonalFragment.this;
						params = new LinkedHashMap<String, String>();
						params.put("tableName", "Users");
						params.put("Name", txtname.getText().toString());

						params.put("Email", Email);
						params.put("Phonenumber", Cellphone);
						params.put("Faxnumber", Fax);
						params.put("Address", Address);
						params.put("IsUpdate", "1");
						params.put("Id", String.valueOf(id));
						params.put("ShowInfoItem", infoItem);
						if (flag == true) {
							birthday = yearId + "/" + monthId + "/" + dayId;
							params.put("BirthDay", birthday);
						}

						params.put("CityId", String.valueOf(cityId));

						saving.execute(params);
					}
				} else
					Toast.makeText(getActivity(), "حداقل یکی از موارد تماس باید انتخاب شده باشد", 0).show();
			}

		});

		btnback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new DisplayPersonalInformationFragment());
				trans.commit();

			}
		});
		RelativeLayout btnedit = (RelativeLayout) view.findViewById(R.id.btnedit);
		RelativeLayout.LayoutParams lp3 = new RelativeLayout.LayoutParams(lin3.getLayoutParams());
		lp3.width = ut.getScreenwidth() / 4;
		lp3.setMargins(5, 5, 5, 5);

		btnedit.setLayoutParams(lp3);
		btnedit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				selectImageOption();
			}
		});

		TextView lable1 = (TextView) view.findViewById(R.id.tt1t);
		TextView lable2 = (TextView) view.findViewById(R.id.tt2t);
		TextView lable3 = (TextView) view.findViewById(R.id.lables);
		TextView lable4 = (TextView) view.findViewById(R.id.cdas);
		TextView lable5 = (TextView) view.findViewById(R.id.lablesostan);
		TextView lable6 = (TextView) view.findViewById(R.id.lablescity);

		lable1.setTypeface(ut.SetFontIranSans());
		lable2.setTypeface(ut.SetFontIranSans());

		lable3.setTypeface(ut.SetFontCasablanca());
		lable4.setTypeface(ut.SetFontCasablanca());
		lable5.setTypeface(ut.SetFontCasablanca());
		lable6.setTypeface(ut.SetFontCasablanca());

		btnback.setTypeface(ut.SetFontCasablanca());
		btnregedit.setTypeface(ut.SetFontCasablanca());

		return view;

	}

	private void selectImageOption() {
		final CharSequence[] items = { "از دوربین", "از گالری تصاویر", "انصراف" };

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("افزودن تصویر");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {

				if (items[item].equals("از دوربین")) {

					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

					File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp1.jpg");

					mImageCaptureUri = Uri.fromFile(f);
					intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);

					startActivityForResult(intent, CAMERA_CODE);

				} else if (items[item].equals("از گالری تصاویر")) {

					Intent i = new Intent(Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

					getActivity().startActivityFromFragment(EditPersonalFragment.this, i, GALLERY_CODE);

					// Intent intent = new Intent();
					// intent.setType("image/*");
					// intent.setAction(Intent.ACTION_GET_CONTENT);
					// try {
					// intent.putExtra("return-data", true);
					// startActivityForResult(
					// Intent.createChooser(intent, "تکمیل کار با"),
					// GALLERY_CODE);
					// } catch (ActivityNotFoundException e) {
					// }

				} else if (items[item].equals("انصراف")) {
					dialog.dismiss();
				}
			}
		});

		builder.show();
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == GALLERY_CODE) {
			try {

				InputStream inputStream = getActivity().getContentResolver().openInputStream(data.getData());
				FileOutputStream fileOutputStream = new FileOutputStream(mFileTemp);
				Utility.copyStream(inputStream, fileOutputStream);
				fileOutputStream.close();
				inputStream.close();

				startCropImage();

			} catch (Exception e) {

				Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
			}
			ImageProfile.setLayoutParams(lp2);

		}
		if (requestCode == PIC_CROP && data != null) {
			String path = data.getStringExtra(CropImage.IMAGE_PATH);
			if (path == null) {
				return;
			}
			Bitmap bitmap = null;
			if (mFileTemp.getPath() != null)
				bitmap = BitmapFactory.decodeFile(mFileTemp.getPath());
			if (bitmap != null) {
				ImageProfile.setImageBitmap(bitmap);
				ImageProfile.setLayoutParams(lp2);

			}

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

	// ////////////////////////////////////////////////////////

	// public void onActivityResult(int requestCode, int resultCode, Intent
	// data) {
	//
	// super.onActivityResult(requestCode, resultCode, data);
	//
	// if (requestCode == GALLERY_CODE && null != data) {
	//
	// mImageCaptureUri = data.getData();
	// CropingIMG();
	//
	// } else if (requestCode == CAMERA_CODE
	// && resultCode == Activity.RESULT_OK) {
	//
	// System.out.println("Camera Image URI : " + mImageCaptureUri);
	// CropingIMG();
	//
	// } else if (requestCode == CROPING_CODE) {
	//
	// try {
	// if (outPutFile.exists()) {
	// Bitmap photo = decodeFile(outPutFile);
	// ImageProfile.setImageBitmap(photo);
	// // img2.setLayoutParams(lp2);
	// ImageProfile.setAdjustViewBounds(true);
	//
	// // img2.setMaxHeight(maxHeight);
	// ImageProfile.setScaleType(ScaleType.CENTER_INSIDE);
	// } else {
	// Toast.makeText(getActivity().getApplicationContext(),
	// "Error while save image", Toast.LENGTH_SHORT)
	// .show();
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// }

	private void CropingIMG() {

		final ArrayList<CropingOption> cropOptions = new ArrayList<CropingOption>();

		final Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setType("image/*");

		ArrayList<ResolveInfo> list = (ArrayList<ResolveInfo>) getActivity().getPackageManager()
				.queryIntentActivities(intent, 0);
		int size = list.size();
		if (size == 0) {
			return;
		} else {
			intent.setData(mImageCaptureUri);
			// intent.putExtra("outputX", 512);
			// intent.putExtra("outputY", 512);
			// intent.putExtra("aspectX", 1);
			// intent.putExtra("aspectY", 1);
			intent.putExtra("scale", true);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outPutFile));
			if (size == 1) {
				Intent i = new Intent(intent);
				ResolveInfo res = list.get(0);

				i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));

				startActivityForResult(i, CROPING_CODE);
			} else {
				for (ResolveInfo res : list) {
					final CropingOption co = new CropingOption();
					PackageManager pm = getActivity().getPackageManager();
					co.title = pm.getApplicationLabel(res.activityInfo.applicationInfo);
					co.icon = pm.getApplicationIcon(res.activityInfo.applicationInfo);
					co.appIntent = new Intent(intent);
					co.appIntent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
					cropOptions.add(co);
				}

				CropingOptionAdapter adapter = new CropingOptionAdapter(getActivity().getApplicationContext(),
						cropOptions);

				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setTitle("Choose Croping App");
				builder.setCancelable(false);
				builder.setAdapter(adapter, new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int itemc) {

						final CropingOption co = new CropingOption();
						co.appIntent = new Intent(intent);
						startActivityForResult(cropOptions.get(itemc).appIntent, CROPING_CODE);
					}
				});

				builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {

						if (mImageCaptureUri != null) {
							getActivity().getContentResolver().delete(mImageCaptureUri, null, null);
							mImageCaptureUri = null;
						}
					}
				});

				AlertDialog alert = builder.create();
				alert.show();
			}
		}
	}

	private Bitmap decodeFile(File f) {
		try {
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);
			final int REQUIRED_SIZE = 512;
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;
			while (true) {
				if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale *= 2;
			}
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
		} catch (FileNotFoundException e) {
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void processFinish(String output) {
		int id = -1;
		try {
			id = Integer.valueOf(output);

			// id > 0 -> updating

			Bitmap bitmap = ((BitmapDrawable) ImageProfile.getDrawable()).getBitmap();

			Bitmap emptyBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
			byte[] Image = null;

			if (!emptyBitmap.equals(bitmap)) {
				Image = Utility.CompressBitmap(bitmap);

				dbAdapter.open();
				dbAdapter.UpdateAllUserToDbNoPic(txtname.getText().toString(), ut.getCurrentUser().getId(), Email, null,
						Phone, Cellphone, Fax, Address, infoItem, birthday, cityId);

				dbAdapter.close();

				FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new DisplayPersonalInformationFragment());
				trans.commit();
			}

			if (context != null) {
				saveImage = new SavingImage(context);
				saveImage.delegate = this;
				imageParams = new LinkedHashMap<String, Object>();
				imageParams.put("tableName", "Users");
				imageParams.put("fieldName", "Image");
				imageParams.put("id", gId);
				imageParams.put("image", Image);

				saveImage.execute(imageParams);
			}

			// FragmentTransaction trans = getActivity()
			// .getSupportFragmentManager().beginTransaction();
			// trans.replace(R.id.content_frame,
			// new DisplayPersonalInformationFragment());
			// trans.commit();

		} catch (NumberFormatException ex) {
			Toast.makeText(context, "خطا در بروز رسانی", Toast.LENGTH_SHORT).show();
			if (dialog != null)
				dialog.dismiss();
		}
	}

	@Override
	public void processFinishSaveImage(String output) {
		if (dialog != null) {
			dialog.dismiss();
		}
		if (output != null && !"".equals(output)) {
			try {
				id = Integer.valueOf(output);

				Bitmap bitmap = ((BitmapDrawable) ImageProfile.getDrawable()).getBitmap();

				Bitmap emptyBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
				byte[] Image = null;

				if (!emptyBitmap.equals(bitmap)) {
					Image = Utility.CompressBitmap(bitmap);
				}

				ut.CreateFile(Image, gId, "Mechanical", "Users", "user", "Users");

				// dbAdapter.open();
				// dbAdapter.UpdateAllUserToDb(gId, Email, null, Phone,
				// Cellphone,
				// Fax, Address, Image);
				//
				// dbAdapter.close();

				FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new DisplayPersonalInformationFragment());
				trans.commit();

			} catch (NumberFormatException ex) {
				Toast.makeText(context, "  خطا در بروز رسانی تصویر", Toast.LENGTH_SHORT).show();

				if (dialog != null)
					dialog.dismiss();
			}
		}
	}
}
