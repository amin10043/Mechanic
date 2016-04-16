package com.project.mechanic.adapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import com.project.mechanic.R;
import com.project.mechanic.StaticValues;
import com.project.mechanic.crop.CropImage;
import com.project.mechanic.entity.Users;
import com.project.mechanic.fragment.CreateIntroductionFragment;
import com.project.mechanic.fragment.DialogLinkDownload;
import com.project.mechanic.fragment.DialogNetworkSocial;
import com.project.mechanic.fragment.IntroductionFragment;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.inter.SaveAsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.Saving;
import com.project.mechanic.service.SavingImage3Picture;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.utility.Utility;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CreateMainBrand extends Fragment implements AsyncInterface, SaveAsyncInterface {

	View rootView;

	DataBaseAdapter DBAdapter;

	Utility util;

	Users currentUser;

	LinearLayout NetworkSocial, DownloadLink, headerLinear, footerLinear;

	Button btnSave;

	ImageView btnHeader, btnProfile, btnFooter;

	EditText NameEnter, phoneEnter, faxEnter, mobileEnter, emailEnter, addressEnter, DescriptionEnter, websiteEnter;

	CheckBox checkAgency, checkService;

	TextView lableAgency, lableService, lableEditNetwork, lableEditDownloadLink;

	RelativeLayout linearCreateProfil, nameLayout;

	LinearLayout.LayoutParams headerParams;

	RelativeLayout.LayoutParams profilParams, nameParams;

	static final int RESULT_LOAD_IMAGE = 1;
	static final int headerGallery = 2;
	private static int footerGallery = 3;
	static final int PIC_CROP = 10;

	private File mFileTemp;

	int MaxSizeImageSelected = 5;

	boolean t1 = false;
	boolean t2 = false;
	boolean t3 = false;

	byte[] byteHeader = null, byteProfil = null, byteFooter = null;

	DialogNetworkSocial dialognetwork;
	DialogLinkDownload dialogDownload;

	int ObjectBrandTypeId = -1;

	String nameValue, phoneValue, faxValue, mobileValue, emailValue, addressValue, descriptionValue, websiteValue;

	ProgressDialog ringProgressDialog;

	Map<String, String> params;

	String serverDate = "";

	String faceBook, linkedIn, twiiter, google, instagram;

	String catalog, price, pdf;

	int parentId;

	int brandId = -1;

	boolean f1, f2, f3;

	public CreateMainBrand(int parentId) {
		this.parentId = parentId;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_create_introduction, null);

		findView();

		createFileImage();

		setLayoutParams();

		setFont();

		imagesClick();

		onClick();

		return rootView;
	}

	private void findView() {

		DBAdapter = new DataBaseAdapter(getActivity());
		util = new Utility(getActivity());

		currentUser = util.getCurrentUser();

		NetworkSocial = (LinearLayout) rootView.findViewById(R.id.editNetwork);
		DownloadLink = (LinearLayout) rootView.findViewById(R.id.editdownload);
		headerLinear = (LinearLayout) rootView.findViewById(R.id.headerLinear);
		footerLinear = (LinearLayout) rootView.findViewById(R.id.footerlinears);

		lableAgency = (TextView) rootView.findViewById(R.id.lableagency);
		lableService = (TextView) rootView.findViewById(R.id.lableservice);
		lableEditNetwork = (TextView) rootView.findViewById(R.id.lableeditnetwork);
		lableEditDownloadLink = (TextView) rootView.findViewById(R.id.labledownload);

		btnSave = (Button) rootView.findViewById(R.id.btnsave);
		btnHeader = (ImageView) rootView.findViewById(R.id.imgvadvertise_Object);
		btnProfile = (ImageView) rootView.findViewById(R.id.profile_img);
		btnFooter = (ImageView) rootView.findViewById(R.id.imgvadvertise2_Object);

		NameEnter = (EditText) rootView.findViewById(R.id.nameInput);
		phoneEnter = (EditText) rootView.findViewById(R.id.editTextphone);
		faxEnter = (EditText) rootView.findViewById(R.id.editTextfax);
		mobileEnter = (EditText) rootView.findViewById(R.id.editTextmob);
		emailEnter = (EditText) rootView.findViewById(R.id.editTextemail);
		addressEnter = (EditText) rootView.findViewById(R.id.editTextaddres);
		DescriptionEnter = (EditText) rootView.findViewById(R.id.descriptionEdittext);
		websiteEnter = (EditText) rootView.findViewById(R.id.editwebsite);

		checkAgency = (CheckBox) rootView.findViewById(R.id.checkAgency);
		checkService = (CheckBox) rootView.findViewById(R.id.checkService);

		linearCreateProfil = (RelativeLayout) rootView.findViewById(R.id.linearCreateProfil);
		nameLayout = (RelativeLayout) rootView.findViewById(R.id.namelayout);

	}

	private void setLayoutParams() {

		headerParams = new LinearLayout.LayoutParams(headerLinear.getLayoutParams());
		headerParams.height = util.getScreenwidth();
		headerParams.width = util.getScreenwidth();

		btnHeader.setLayoutParams(headerParams);
		btnFooter.setLayoutParams(headerParams);

		// *********** ////////////////////

		profilParams = new RelativeLayout.LayoutParams(linearCreateProfil.getLayoutParams());
		profilParams.width = (util.getScreenwidth() / 5);
		profilParams.height = (util.getScreenwidth() / 5);
		profilParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

		btnProfile.setLayoutParams(profilParams);

		// *********** ////////////////////

		nameParams = new RelativeLayout.LayoutParams(nameLayout.getLayoutParams());
		nameParams.width = util.getScreenwidth() / 2;
		nameParams.height = util.getScreenwidth() / 10;
		nameParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		nameParams.addRule(RelativeLayout.BELOW, R.id.profile_img);

		NameEnter.setLayoutParams(nameParams);

		// *********** ////////////////////

	}

	private void setFont() {

		lableAgency.setTypeface(util.SetFontCasablanca());
		lableService.setTypeface(util.SetFontCasablanca());

		phoneEnter.setTypeface(util.SetFontIranSans());
		faxEnter.setTypeface(util.SetFontIranSans());
		mobileEnter.setTypeface(util.SetFontIranSans());
		websiteEnter.setTypeface(util.SetFontIranSans());
		emailEnter.setTypeface(util.SetFontIranSans());
		addressEnter.setTypeface(util.SetFontIranSans());
		DescriptionEnter.setTypeface(util.SetFontIranSans());

		checkAgency.setTypeface(util.SetFontCasablanca());
		checkService.setTypeface(util.SetFontCasablanca());

		lableEditNetwork.setTypeface(util.SetFontCasablanca());
		lableEditDownloadLink.setTypeface(util.SetFontCasablanca());
		btnSave.setTypeface(util.SetFontIranSans());

	}

	private void imagesClick() {

		btnHeader.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent i = new Intent(Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				getActivity().startActivityFromFragment(CreateMainBrand.this, i, headerGallery);

			}
		});

		btnProfile.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				getActivity().startActivityFromFragment(CreateMainBrand.this, i, RESULT_LOAD_IMAGE);
			}
		});

		btnFooter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				getActivity().startActivityFromFragment(CreateMainBrand.this, i, footerGallery);
			}
		});
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == RESULT_LOAD_IMAGE) {
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
			btnProfile.setLayoutParams(profilParams);

		}
		if (requestCode == PIC_CROP && data != null) {
			String path = data.getStringExtra(CropImage.IMAGE_PATH);
			if (path == null) {
				return;
			}
			Bitmap bitmap = null;
			if (mFileTemp.getPath() != null) {
				bitmap = BitmapFactory.decodeFile(mFileTemp.getPath());

			}
			if (bitmap != null && t1) {
				btnProfile.setImageBitmap(bitmap);
				btnProfile.setLayoutParams(profilParams);
				t1 = false;

				byteProfil = Utility.compressImage(mFileTemp);

			}
			if (bitmap != null && t2) {
				btnHeader.setImageBitmap(bitmap);
				btnHeader.setLayoutParams(headerParams);
				t2 = false;

				byteHeader = Utility.compressImage(mFileTemp);

			}
			if (bitmap != null && t3) {
				btnFooter.setImageBitmap(bitmap);
				btnFooter.setLayoutParams(headerParams);
				t3 = false;

				byteFooter = Utility.compressImage(mFileTemp);

			}

		}

		if (requestCode == headerGallery && resultCode == Activity.RESULT_OK && null != data) {
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
			btnHeader.setLayoutParams(headerParams);

		}
		if (requestCode == footerGallery && resultCode == Activity.RESULT_OK && null != data) {
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

			btnFooter.setLayoutParams(headerParams);

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

	private void onClick() {

		NetworkSocial.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialognetwork = new DialogNetworkSocial(
						/* CreateMainBrand.this, */ getActivity(), R.layout.dialog_network_social);
				// dialognetwork.setTitle("ویرایش لینک های شبکه های اجتماعی");
				util.setSizeDialog(dialognetwork);

			}
		});

		DownloadLink.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialogDownload = new DialogLinkDownload(
						/* CreateIntroductionFragment.this, */getActivity(), R.layout.dialog_download_link);
				// dialogDownload.setTitle("ویرایش لینک های دانلود");
				util.setSizeDialog(dialogDownload);
			}
		});

		btnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (checkAgency.isChecked() && checkService.isChecked())
					ObjectBrandTypeId = StaticValues.HaveAgencyHaveService;
				else if (checkAgency.isChecked())
					ObjectBrandTypeId = StaticValues.OnlyAgency;
				else if (checkService.isChecked())
					ObjectBrandTypeId = StaticValues.OnlyService;
				else
					ObjectBrandTypeId = StaticValues.NoAgencyNoService;

				nameValue = NameEnter.getText().toString();
				phoneValue = phoneEnter.getText().toString();
				faxValue = faxEnter.getText().toString();
				mobileValue = mobileEnter.getText().toString();
				emailValue = emailEnter.getText().toString();
				addressValue = addressEnter.getText().toString();
				descriptionValue = DescriptionEnter.getText().toString();
				websiteValue = websiteEnter.getText().toString();

				faceBook = DialogNetworkSocial.facebook;
				linkedIn = DialogNetworkSocial.linkedin;
				twiiter = DialogNetworkSocial.twiiter;
				google = DialogNetworkSocial.google;
				instagram = DialogNetworkSocial.instagram;

				catalog = DialogLinkDownload.catalog;
				price = DialogLinkDownload.price;
				pdf = DialogLinkDownload.pdf;

				if (nameValue.equals("")) {
					Toast.makeText(getActivity(), "پر کردن فیلد نام اجباری است", Toast.LENGTH_SHORT).show();

				} else if (phoneValue.equals("") && mobileValue.equals("") && emailValue.equals("")
						&& faxValue.equals("")) {

					Toast.makeText(getActivity(), "پر کردن حداقل یکی از فیلدهای تماس الزامی است", Toast.LENGTH_SHORT)
							.show();
				} else if (byteHeader == null && byteProfil == null && byteFooter == null) {
					Toast.makeText(getActivity(), StaticValues.selectImagesBrandMessage, 0).show();

				} else {
					ServerDate date = new ServerDate(getActivity());
					date.delegate = CreateMainBrand.this;
					date.execute("");

				}

			}
		});

	}

	@Override
	public void processFinish(String output) {

		if (util.checkError(output) == false) {

			try {
				brandId = Integer.valueOf(output);

				DBAdapter.open();

				DBAdapter.CreateNewBrand(brandId, nameValue, phoneValue, emailValue, faxValue, descriptionValue,
						catalog, price, pdf, addressValue, mobileValue, faceBook, instagram, linkedIn, google,
						websiteValue, twiiter, currentUser.getId(), parentId, ObjectBrandTypeId, serverDate);

				DBAdapter.close();

				SavingImage3Picture savingImage = new SavingImage3Picture(getActivity());
				savingImage.delegate = CreateMainBrand.this;
				Map<String, Object> it = new LinkedHashMap<String, Object>();

				it.put("tableName", "Object");
				it.put("fieldName1", "Image1");
				it.put("fieldName2", "Image2");
				it.put("fieldName3", "Image3");

				it.put("id", brandId);

				it.put("Image1", byteHeader);
				it.put("Image2", byteProfil);
				it.put("Image3", byteFooter);

				savingImage.execute(it);

				ringProgressDialog = ProgressDialog.show(getActivity(), null, " لطفا چند لحظه منتظر بمانید.");

			} catch (NumberFormatException e) {

				params = new LinkedHashMap<String, String>();
				Saving saving = new Saving(getActivity());
				saving.delegate = CreateMainBrand.this;

				params.put("TableName", "Object");

				params.put("Name", nameValue);
				params.put("Phone", phoneValue);
				params.put("Email", emailValue);
				params.put("Fax", faxValue);
				params.put("Description", descriptionValue);
				params.put("Cellphone", mobileValue);
				params.put("Address", addressValue);

				params.put("Pdf1", catalog);
				params.put("Pdf2", price);
				params.put("Pdf3", pdf);

				params.put("Facebook", faceBook);
				params.put("Instagram", instagram);
				params.put("LinkedIn", linkedIn);
				params.put("Google", google);
				params.put("Site", websiteValue);
				params.put("Twitter", twiiter);

				params.put("ObjectBrandTypeId", String.valueOf(ObjectBrandTypeId));
				params.put("MainObjectId", String.valueOf(StaticValues.MainItem1));
				params.put("ParentId", String.valueOf(parentId));
				params.put("UserId", String.valueOf(currentUser.getId()));

				params.put("Date", output);
				params.put("ModifyDate", output);

				params.put("IsActive", String.valueOf(StaticValues.actived));

				params.put("IsUpdate", "0");
				params.put("Id", "0");

				serverDate = output;

				saving.execute(params);

			}
		} // end check error

	}// end process

	private void createFileImage() {

		String TEMP_PHOTO_FILE_NAME = "temp_photo.jpg";

		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			mFileTemp = new File(Environment.getExternalStorageDirectory(), TEMP_PHOTO_FILE_NAME);
		} else {
			mFileTemp = new File(getActivity().getFilesDir(), TEMP_PHOTO_FILE_NAME);
		}

	}

	@Override
	public void processFinishSaveImage(String output) {

		if (ringProgressDialog != null) {
			ringProgressDialog.dismiss();
		}

		if (util.checkError(output) == false) {

			try {

				DBAdapter.open();

				if (byteHeader != null) {
					util.CreateFile(byteHeader, brandId, "Mechanical", "Profile", "header", "Object");
					DBAdapter.updateObjectImage1ServerDate(brandId, output);

				}
				if (byteProfil != null) {
					util.CreateFile(byteProfil, brandId, "Mechanical", "Profile", "profile", "Object");
					DBAdapter.updateObjectImage2ServerDate(brandId, output);

				}
				if (byteFooter != null) {
					util.CreateFile(byteFooter, brandId, "Mechanical", "Profile", "footer", "Object");
					DBAdapter.updateObjectImage3ServerDate(brandId, output);

				}

				DBAdapter.close();

				Toast.makeText(getActivity(), "ذخیره سازی تصاویر با موفقیت انجا م شد", 0).show();

				IntroductionFragment fragment = new IntroductionFragment(-1);
				FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, fragment);

				Bundle bundle = new Bundle();
				bundle.putString("Id", String.valueOf(brandId));
				bundle.putInt("positionBrand", 0);

				fragment.setArguments(bundle);
				trans.commit();

			} catch (Exception e) {

				if (ringProgressDialog != null) {
					ringProgressDialog.dismiss();
				}
				Toast.makeText(getActivity(), "خطا در ذخیره سازی تصاویر" + e, Toast.LENGTH_SHORT).show();
			}

		}

	}
}