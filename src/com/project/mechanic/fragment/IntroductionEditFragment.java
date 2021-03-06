package com.project.mechanic.fragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import com.project.mechanic.R;
import com.project.mechanic.StaticValues;
import com.project.mechanic.crop.CropImage;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.inter.SaveAsyncInterface;
import com.project.mechanic.interfaceServer.CountAgencySerViceInterface;
import com.project.mechanic.interfaceServer.DateFromServerForLike;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.server.CountAgencyServiceServer;
import com.project.mechanic.server.ServerDateForLike;
import com.project.mechanic.service.Saving;
import com.project.mechanic.service.SavingImage3Picture;
import com.project.mechanic.utility.Utility;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class IntroductionEditFragment extends Fragment
		implements DateFromServerForLike, AsyncInterface, SaveAsyncInterface, CountAgencySerViceInterface {
	private static int headerLoadCode = 1;
	private static int profileLoadCode = 2;
	private static int footerLoadcode = 3;
	int pageId;
	String serverDate = "";
	int MaxSizeImageSelected = 5;

	public IntroductionEditFragment(int pageId) {
		this.pageId = pageId;
	}

	DataBaseAdapter DBAdapter;
	Utility util;

	com.project.mechanic.entity.Object object;

	DialogEditDownloadIntroduction diadown;

	DialogEditNet dianet;

	// Bitmap bmpHeader, bmpPrifile, bmpfooter;

	Button btnSave;
	TextView lableStar, lableNetwork, lableAdmins, lableDownload, lablecopy;

	String nameValue, phoneValue, faxValue, mobileValue, emailValue, addressValue, descriptionValue, websiteValue;

	EditText nameEnter, phoneEnter, faxEnter, mobileEnter, emailEnter, addressEnter, descriptionEnter, websiteEnter;

	ImageView headerImageEdit, profileImageEdit, footerImageEdit;

	LinearLayout.LayoutParams headerEditParams, footerEditParams, editnameParams;

	RelativeLayout.LayoutParams profileEditParams;

	RelativeLayout namayendegi, khadamat, Linearprofile;

	LinearLayout editnetLink, Linearheader, Linearfooter, editDNlink, AdminsPage;

	public String Dcatalog, Dprice, Dpdf, Dvideo;
	public String Dface, Dlink, Dtwt, Dgoogle, Dinstagram;
	RatingBar rating;
	ImageView payBtn, copyBtn;

	Bitmap bmpHeader, bmpProfil, bmpFooter;
	Saving saving;
	Map<String, String> params;
	// int PageId;
	ProgressDialog ringProgressDialog;
	SavingImage3Picture savingImage;

	byte[] byteHeader = null, byteProfil = null, byteFooter = null;
	private File mFileTemp;

	public static final String TEMP_PHOTO_FILE_NAME = "temp_photo.jpg";
	final int PIC_CROP = 10;

	boolean t1 = false;
	boolean t2 = false;
	boolean t3 = false;

	TextView linkPage;
	int upgradRatingCount;
	CheckBox checkAgency, checkService;

	TextView lableAgency, lableService;
	int ObjectBrandTypeId;
	String message = "";
	int scrollY = 0;
	LinearLayout li;

	boolean typeAgency = true;

	int countAgency = 0;
	int countService = 0;

	TextView haveAgency, haveService;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_introduction_edit, null);

		DBAdapter = new DataBaseAdapter(getActivity());
		util = new Utility(getActivity());

		btnSave = (Button) view.findViewById(R.id.btnsave);
		headerImageEdit = (ImageView) view.findViewById(R.id.hh1);
		profileImageEdit = (ImageView) view.findViewById(R.id.pp1);
		footerImageEdit = (ImageView) view.findViewById(R.id.ft1);

		nameEnter = (EditText) view.findViewById(R.id.nameEdit);
		phoneEnter = (EditText) view.findViewById(R.id.editTextphone);
		faxEnter = (EditText) view.findViewById(R.id.editTextfax);
		mobileEnter = (EditText) view.findViewById(R.id.editTextmob);
		emailEnter = (EditText) view.findViewById(R.id.editTextemail);
		addressEnter = (EditText) view.findViewById(R.id.editTextaddres);
		descriptionEnter = (EditText) view.findViewById(R.id.descriptionpageedit);
		websiteEnter = (EditText) view.findViewById(R.id.editwebsite);

		editnetLink = (LinearLayout) view.findViewById(R.id.editpageNetwork);
		editDNlink = (LinearLayout) view.findViewById(R.id.editpagedownload);
		// namayendegi = (RelativeLayout) view.findViewById(R.id.Layoutlink1);
		// khadamat = (RelativeLayout) view.findViewById(R.id.Layoutlink2);
		AdminsPage = (LinearLayout) view.findViewById(R.id.listAdmin);
		rating = (RatingBar) view.findViewById(R.id.ratingBar1);
		payBtn = (ImageView) view.findViewById(R.id.btnPay);

		copyBtn = (ImageView) view.findViewById(R.id.copyToClipboard);
		linkPage = (TextView) view.findViewById(R.id.linkPage);

		checkAgency = (CheckBox) view.findViewById(R.id.checkAgency);
		checkService = (CheckBox) view.findViewById(R.id.checkService);

		// namayendegi.setVisibility(View.GONE);
		// khadamat.setVisibility(View.GONE);

		Linearheader = (LinearLayout) view.findViewById(R.id.headerLinearPage);
		Linearprofile = (RelativeLayout) view.findViewById(R.id.linearEditProfil);
		Linearfooter = (LinearLayout) view.findViewById(R.id.linearfooteredit);

		headerEditParams = new LinearLayout.LayoutParams(Linearheader.getLayoutParams());
		headerEditParams.height = (int) (util.getScreenwidth());

		profileEditParams = new RelativeLayout.LayoutParams(Linearprofile.getLayoutParams());
		profileEditParams.width = util.getScreenwidth() / 4;
		profileEditParams.height = util.getScreenwidth() / 4;
		profileEditParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		profileEditParams.addRule(RelativeLayout.BELOW, R.id.btnsave);
		profileEditParams.setMargins(5, 5, 5, 5);

		footerEditParams = new LinearLayout.LayoutParams(Linearfooter.getLayoutParams());
		footerEditParams.height = util.getScreenwidth();

		editnameParams = new LinearLayout.LayoutParams(Linearprofile.getLayoutParams());
		editnameParams.width = util.getScreenwidth() / 3;
		editnameParams.height = util.getScreenwidth() / 10;

		// RelativeLayout.LayoutParams edittextParams = new
		// RelativeLayout.LayoutParams(
		// (int) (util.getScreenwidth() / 1.8), LayoutParams.WRAP_CONTENT);
		// edittextParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		//
		// phoneEnter.setLayoutParams(edittextParams);
		// faxEnter.setLayoutParams(edittextParams);
		// mobileEnter.setLayoutParams(edittextParams);
		// emailEnter.setLayoutParams(edittextParams);
		// addressEnter.setLayoutParams(edittextParams);

		lableStar = (TextView) view.findViewById(R.id.lableSt);
		lableNetwork = (TextView) view.findViewById(R.id.lableeditnetwork);
		lableDownload = (TextView) view.findViewById(R.id.labledownload);
		lablecopy = (TextView) view.findViewById(R.id.copylable);
		lableAdmins = (TextView) view.findViewById(R.id.lableEditadmin);

		lableStar.setTypeface(util.SetFontIranSans());
		lableNetwork.setTypeface(util.SetFontCasablanca());
		lableDownload.setTypeface(util.SetFontCasablanca());
		lablecopy.setTypeface(util.SetFontCasablanca());
		lableAdmins.setTypeface(util.SetFontCasablanca());

		headerImageEdit.setLayoutParams(headerEditParams);
		profileImageEdit.setLayoutParams(profileEditParams);
		footerImageEdit.setLayoutParams(footerEditParams);

		lableAgency = (TextView) view.findViewById(R.id.lableagency);
		lableService = (TextView) view.findViewById(R.id.lableservice);

		lableAgency.setTypeface(util.SetFontCasablanca());
		lableService.setTypeface(util.SetFontCasablanca());

		addressEnter.setTypeface(util.SetFontIranSans());
		descriptionEnter.setTypeface(util.SetFontIranSans());

		btnSave.setTypeface(util.SetFontIranSans());

		haveAgency = (TextView) view.findViewById(R.id.haveAgency);
		haveService = (TextView) view.findViewById(R.id.haveService);

		haveAgency.setTypeface(util.SetFontIranSans());
		haveService.setTypeface(util.SetFontIranSans());

		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			mFileTemp = new File(Environment.getExternalStorageDirectory(), TEMP_PHOTO_FILE_NAME);
		} else {
			mFileTemp = new File(getActivity().getFilesDir(), TEMP_PHOTO_FILE_NAME);
		}

		// SharedPreferences sendDataID =
		// getActivity().getSharedPreferences("Id", 0);
		// PageId = sendDataID.getInt("main_Id", -1);

		// Toast.makeText(getActivity(), "introduction id =" + PageId,
		// Toast.LENGTH_SHORT).show();

		// /////////display information///////////////////////

		// SharedPreferences sendDataID1 = getActivity().getSharedPreferences(
		// "Id", 0);
		// final int cid = sendDataID1.getInt("main_Id", -1);
		
		
		
		
		DBAdapter.open();
		object = DBAdapter.getObjectbyid(pageId);
		descriptionEnter.setText(object.getDescription());

		String pathHeader = object.getImagePath1();
		String pathProfile = object.getImagePath2();
		String pathFooter = object.getImagePath3();

		if (!"".equals(pathHeader)) {

			try {
				bmpHeader = BitmapFactory.decodeFile(pathHeader);
				if (bmpHeader != null)
					byteHeader = getBitmapAsByteArray(bmpHeader);

			} catch (OutOfMemoryError e) {
				e.printStackTrace();
			}

		}

		if (!"".equals(pathProfile)) {
			try {
				bmpProfil = BitmapFactory.decodeFile(pathProfile);
				if (bmpProfil != null)
					byteProfil = getBitmapAsByteArray(bmpProfil);
			} catch (OutOfMemoryError e) {
				e.printStackTrace();
			}
		}

		if (!"".equals(pathFooter)) {

			try {
				bmpFooter = BitmapFactory.decodeFile(pathFooter);
				if (bmpFooter != null)
					byteFooter = getBitmapAsByteArray(bmpFooter);

			} catch (OutOfMemoryError e) {
				e.printStackTrace();
			}
		}

		// byte[] bitmapbyte = OBJECT.getImage2();

		if (bmpHeader != null) {
			// Bitmap bmp = BitmapFactory.decodeByteArray(bitmapbyte, 0,
			// bitmapbyte.length);
			headerImageEdit.setImageBitmap(bmpHeader);
		} else
			headerImageEdit.setImageResource(R.drawable.no_image_header);

		// byte[] bitmap = object.getImage1();
		if (bmpProfil != null) {
			// Bitmap bmp = BitmapFactory
			// .decodeByteArray(bitmap, 0, bitmap.length);
			profileImageEdit.setImageBitmap(bmpProfil);
		} else
			profileImageEdit.setImageResource(R.drawable.no_img_profile);

		// byte[] bitm = object.getImage3();
		if (bmpFooter != null) {
			// Bitmap bmp = BitmapFactory.decodeByteArray(bitm, 0, bitm.length);
			footerImageEdit.setImageBitmap(bmpFooter);
		} else
			footerImageEdit.setImageResource(R.drawable.no_image_header);

		nameEnter.setText(object.getName());
		phoneEnter.setText(object.getPhone());
		faxEnter.setText(object.getFax());
		mobileEnter.setText(object.getCellphone());
		emailEnter.setText(object.getEmail());
		addressEnter.setText(object.getAddress());

		DBAdapter.close();

		nameEnter.setTypeface(util.SetFontIranSans());
		// //////////////////////////////////////////////////

		int mainObjectId = object.getMainObjectId();
		ObjectBrandTypeId = object.getObjectBrandTypeId();
		if (mainObjectId != 1) {
			checkAgency.setVisibility(View.GONE);
			lableAgency.setVisibility(View.GONE);
			lableService.setVisibility(View.GONE);
			checkService.setVisibility(View.GONE);
		}

		switch (object.getObjectBrandTypeId()) {
		case 1:
			checkAgency.setChecked(true);
			checkService.setChecked(true);

			break;
		case 2:

			checkAgency.setChecked(false);
			checkService.setChecked(false);

			break;
		case 3:
			checkAgency.setChecked(true);

			break;
		case 4:
			checkService.setChecked(false);

			break;

		default:
			break;
		}

		if (object.getCountAgency() > 0) {
			checkAgency.setEnabled(false);
			haveAgency.setVisibility(View.VISIBLE);

		}

		if (object.getCountService() > 0) {
			checkService.setEnabled(false);
			haveService.setVisibility(View.VISIBLE);
		}

		copyBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ClipboardManager clipMan = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
				ClipData cData = ClipData.newPlainText("text", linkPage.getText().toString());
				clipMan.setPrimaryClip(cData);
				Toast.makeText(getActivity(), "آدرس صفحه کپی شد", 0).show();

			}
		});

		headerImageEdit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				getActivity().startActivityFromFragment(IntroductionEditFragment.this, i, headerLoadCode);

			}
		});

		upgradRatingCount = (int) object.getRate();
		rating.setProgress(upgradRatingCount);

		payBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				upgradRatingCount = (int) rating.getRating();

				if (upgradRatingCount >= 0) {
					DBAdapter.open();

					DBAdapter.updateRatingObject(upgradRatingCount, pageId);

					Toast.makeText(getActivity(), "ثبت درگاه انجام شود ", 0).show();

					Toast.makeText(getActivity(), upgradRatingCount + "", 0).show();

					DBAdapter.close();

				} else {
					Toast.makeText(getActivity(), "انتخاب ستاره ها الزامی است", 0).show();
				}
			}
		});

		AdminsPage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				int AdminId = object.getUserId();
				DialogAdminsPage adminDialog = new DialogAdminsPage(getActivity(), pageId, AdminId);
				util.setSizeDialog(adminDialog);
			}
		});

		profileImageEdit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				getActivity().startActivityFromFragment(IntroductionEditFragment.this, i, profileLoadCode);

			}
		});
		footerImageEdit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				getActivity().startActivityFromFragment(IntroductionEditFragment.this, i, footerLoadcode);

			}
		});

		editDNlink.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				diadown = new DialogEditDownloadIntroduction(IntroductionEditFragment.this, getActivity(),
						R.layout.dialog_edit_link_dn);
				diadown.setTitle("ویرایش لینک های دانلود");

				util.setSizeDialog(diadown);

			}
		});

		editnetLink.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dianet = new DialogEditNet(IntroductionEditFragment.this, getActivity(), R.layout.dialog_edit_link_dn);
				dianet.setTitle("ویرایش لینک شبکه های اجتماعی");
				util.setSizeDialog(dianet);
			}
		});

		btnSave.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void onClick(View arg0) {

				nameValue = nameEnter.getText().toString();
				phoneValue = phoneEnter.getText().toString();
				faxValue = faxEnter.getText().toString();
				mobileValue = mobileEnter.getText().toString();
				emailValue = emailEnter.getText().toString();
				addressValue = addressEnter.getText().toString();
				descriptionValue = descriptionEnter.getText().toString();
				websiteValue = websiteEnter.getText().toString();
				
				upgradRatingCount = (int) rating.getRating();


				if (nameValue.equals("")) {
					Toast.makeText(getActivity(), "پر کردن فیلد نام الزامی است", Toast.LENGTH_SHORT).show();
				} else {

					li = (LinearLayout) checkAgency.getParent().getParent().getParent().getParent().getParent();

					View v = (View) checkAgency.getParent();

					scrollY = v.getTop();

					getCountAgencyService();


				}
			}
		});

		return view;
	}

	public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.JPEG, 80, outputStream);
		byte[] array = outputStream.toByteArray();
		BitmapFactory.decodeByteArray(array, 0, array.length);
		return outputStream.toByteArray();

	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == profileLoadCode) {
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
					Toast.makeText(getActivity(),
							"حجم عکس انتخاب شده باید کمتر از " + MaxSizeImageSelected + "مگابایت باشد ",
							Toast.LENGTH_LONG).show();
				else {
					t1 = true;
					startCropImage();
				}

			} catch (Exception e) {

				Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
			}
			profileImageEdit.setLayoutParams(profileEditParams);

		}
		if (requestCode == PIC_CROP && data != null) {
			String path = data.getStringExtra(CropImage.IMAGE_PATH);
			if (path == null) {
				return;
			}
			Bitmap bitmap = null;
			if (mFileTemp.getPath() != null)
				bitmap = BitmapFactory.decodeFile(mFileTemp.getPath());
			if (bitmap != null && t1) {
				profileImageEdit.setImageBitmap(bitmap);
				profileImageEdit.setLayoutParams(profileEditParams);
				t1 = false;

				byteProfil = Utility.compressImage(mFileTemp);

			}
			if (bitmap != null && t2) {
				headerImageEdit.setImageBitmap(bitmap);
				headerImageEdit.setLayoutParams(headerEditParams);
				t2 = false;

				byteHeader = Utility.compressImage(mFileTemp);

			}
			if (bitmap != null && t3) {
				footerImageEdit.setImageBitmap(bitmap);
				footerImageEdit.setLayoutParams(footerEditParams);
				t3 = false;

				byteFooter = Utility.compressImage(mFileTemp);

			}

		}

		if (requestCode == headerLoadCode && resultCode == Activity.RESULT_OK && null != data) {
			try {

				InputStream inputStream = getActivity().getContentResolver().openInputStream(data.getData());
				FileOutputStream fileOutputStream = new FileOutputStream(mFileTemp);
				Utility.copyStream(inputStream, fileOutputStream);
				fileOutputStream.close();
				inputStream.close();

				long sizePicture = 0; // MB

				if (mFileTemp != null)
					sizePicture = mFileTemp.length() / 1024 / 1024;

				if (sizePicture > MaxSizeImageSelected)
					Toast.makeText(getActivity(),
							"حجم عکس انتخاب شده باید کمتر از " + MaxSizeImageSelected + "مگابایت باشد ",
							Toast.LENGTH_LONG).show();
				else {
					t2 = true;
					startCropImage();
				}

			} catch (Exception e) {

				Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
			}
			headerImageEdit.setLayoutParams(headerEditParams);

		}
		if (requestCode == footerLoadcode && resultCode == Activity.RESULT_OK && null != data) {
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
					Toast.makeText(getActivity(),
							"حجم عکس انتخاب شده باید کمتر از " + MaxSizeImageSelected + "مگابایت باشد ",
							Toast.LENGTH_LONG).show();
				else {
					t3 = true;
					startCropImage();
				}

			} catch (Exception e) {

				Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
			}

			footerImageEdit.setLayoutParams(footerEditParams);

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

	@Override
	public void processFinish(String output) {

		int id = -1;
		try {
			if (ringProgressDialog != null) {
				ringProgressDialog.dismiss();
			}
		

			if (getActivity() != null) {
				savingImage = new SavingImage3Picture(getActivity());
				savingImage.delegate = IntroductionEditFragment.this;
				Map<String, Object> it = new LinkedHashMap<String, Object>();

				it.put("tableName", "Object");
				it.put("fieldName1", "Image1");
				it.put("fieldName2", "Image2");
				it.put("fieldName3", "Image3");

				it.put("id", String.valueOf(pageId));

				it.put("Image1", byteHeader);
				it.put("Image2", byteProfil);
				it.put("Image3", byteFooter);

				savingImage.execute(it);
				
				ringProgressDialog = ProgressDialog.show(getActivity(), null, StaticValues.MessagePleaseWait);


			}
			DBAdapter.open();
			DBAdapter.UpdateObjectProperties(pageId, nameValue, phoneValue, emailValue, faxValue, descriptionValue,
					Dcatalog, Dprice, Dpdf, Dvideo, addressValue, mobileValue, Dface, Dinstagram, Dlink, Dgoogle,
					websiteValue, Dtwt, ObjectBrandTypeId);

			DBAdapter.close();
		

		} catch (NumberFormatException ex) {
			Toast.makeText(getActivity(), "خطا در بروز رسانی", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void processFinishSaveImage(String output) {
		
		if (ringProgressDialog != null) {
			ringProgressDialog.dismiss();
		}

		if (output != null) {
		

			if (headerImageEdit.getDrawable() == null && profileImageEdit.getDrawable() == null
					&& footerImageEdit.getDrawable() == null) {

				Toast.makeText(getActivity(), "Empty ByteArray", Toast.LENGTH_SHORT).show();
			}

		

			if (byteHeader != null)
				util.CreateFile(byteHeader, pageId, "Mechanical", "Profile", "header", "Object");

			if (byteProfil != null)
				util.CreateFile(byteProfil, pageId, "Mechanical", "Profile", "profile", "Object");

			if (byteFooter != null)
				util.CreateFile(byteFooter, pageId, "Mechanical", "Profile", "footer", "Object");

		
			
			getActivity().getSupportFragmentManager().popBackStack();

		
		}
		
	}

	@Override
	public void resultDateLike(String output) {
		
		if (ringProgressDialog != null) {
			ringProgressDialog.dismiss();
		}

		if (util.checkError(output) == false) {

			serverDate = output;

			saving = new Saving(getActivity());
			saving.delegate = IntroductionEditFragment.this;
			params = new LinkedHashMap<String, String>();

			params.put("tableName", "Object");

			params.put("Name", nameValue);
			params.put("Phone", phoneValue);
			params.put("Email", emailValue);
			params.put("Fax", faxValue);
			params.put("Description", descriptionValue);
			params.put("Pdf1", Dcatalog);
			params.put("Pdf2", Dprice);
			params.put("Pdf3", Dpdf);
			params.put("Pdf4", Dvideo);
			params.put("Address", addressValue);
			params.put("Cellphone", mobileValue);
			params.put("Facebook", Dface);
			params.put("Instagram", Dinstagram);
			params.put("LinkedIn", Dlink);
			params.put("Google", Dgoogle);
			params.put("Site", websiteValue);
			params.put("Twitter", Dtwt);
			params.put("rate", String.valueOf(upgradRatingCount));
			params.put("ObjectBrandTypeId", String.valueOf(ObjectBrandTypeId));

			params.put("ModifyDate", serverDate);

			params.put("IsUpdate", "1");
			params.put("Id", String.valueOf(pageId));
			saving.execute(params);

			ringProgressDialog = ProgressDialog.show(getActivity(), null, StaticValues.MessagePleaseWait);

		}
	}

	private void getCountAgencyService() {

		CountAgencyServiceServer getCount = new CountAgencyServiceServer(getActivity());
		getCount.delegate = IntroductionEditFragment.this;
		Map<String, String> items = new LinkedHashMap<String, String>();

		items.put("tableName", "isObjectHasSubObject");
		items.put("objectId", String.valueOf(object.getId()));
		if (typeAgency == true)
			items.put("agencyService", String.valueOf(StaticValues.TypeObjectIsAgency));
		else
			items.put("agencyService", String.valueOf(StaticValues.TypeObjectIsService));

		getCount.execute(items);
		
		ringProgressDialog = ProgressDialog.show(getActivity(), null, StaticValues.MessagePleaseWait);


	}

	@Override
	public void ResultCountAgency(String output) {
		
		if (ringProgressDialog != null) {
			ringProgressDialog.dismiss();
		}

		if (util.checkError(output) == false) {
			int value = Integer.valueOf(output);

			if (typeAgency) {

				DBAdapter.open();
				DBAdapter.updateCountAgencyService(object.getId(), value, StaticValues.TypeObjectIsAgency);
				typeAgency = false;
				DBAdapter.close();

				countAgency = value;

				getCountAgencyService();

			} else {
				DBAdapter.open();
				DBAdapter.updateCountAgencyService(object.getId(), value, StaticValues.TypeObjectIsService);

				DBAdapter.close();

				countService = value;

				if (countAgency > 0) {
					checkAgency.setChecked(true);
					checkAgency.setEnabled(false);
				}

				if (countService > 0) {
					checkService.setChecked(true);
					checkService.setEnabled(false);
				}

				if (checkAgency.isChecked() && checkService.isChecked())
					ObjectBrandTypeId = StaticValues.HaveAgencyHaveService;
				else if (checkAgency.isChecked())
					ObjectBrandTypeId = StaticValues.OnlyAgency;
				else if (checkService.isChecked())
					ObjectBrandTypeId = StaticValues.OnlyService;
				else
					ObjectBrandTypeId = StaticValues.NoAgencyNoService;

				ServerDateForLike dates = new ServerDateForLike(getActivity());
				dates.delegate = IntroductionEditFragment.this;
				dates.execute("");
				
				ringProgressDialog = ProgressDialog.show(getActivity(), null, StaticValues.MessagePleaseWait);


			}

			;
		}

	}

}
