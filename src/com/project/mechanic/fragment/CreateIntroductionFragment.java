package com.project.mechanic.fragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.project.mechanic.R;
import com.project.mechanic.crop.CropImage;
import com.project.mechanic.entity.Users;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.inter.SaveAsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.Deleting;
import com.project.mechanic.service.Saving;
import com.project.mechanic.service.SavingImage3Picture;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.utility.Utility;

public class CreateIntroductionFragment extends Fragment implements
		AsyncInterface, SaveAsyncInterface {

	private static int RESULT_LOAD_IMAGE = 1;
	private static int HeaderCode = 2;
	private static int FooterCode = 3;
	int Object;
	int ObjectBrandTypeId;
	int serverId = -1;
	public static final String TEMP_PHOTO_FILE_NAME = "temp_photo.jpg";
	final int PIC_CROP = 10;

	DataBaseAdapter DBAdapter;
	ImageButton btnSave;
	ImageView btnProfile, btnHeader, btnFooter;
	String nameValue, phoneValue, faxValue, mobileValue, emailValue,
			addressValue, descriptionValue;
	EditText NameEnter, phoneEnter, faxEnter, mobileEnter, emailEnter,
			addressEnter, DescriptionEnter;
	Fragment fragment;
	Utility util;
	LinearLayout headerLinear, footerLinear;
	RelativeLayout NetworkSocial, DownloadLink, nameEditRelative, namayendegi,
			khadamat, linearCreateProfil;
	LinearLayout.LayoutParams headerParams, footerParams;
	RelativeLayout.LayoutParams profilParams, nameParams;
	DialogNetworkSocial dialognetwork;
	DialogLinkDownload dialogDownload;
	CheckBox checkAgency, checkService;

	int mainID;

	Bitmap bitmapHeader, bitmapProfil, bitmapFooter, emptyHeader, emptyProfile,
			emptyFooter;

	public String Lfacebook, Llinkedin, Ltwitter, Lwebsite, Lgoogle,
			Linstagram;
	public String Lcatalog, Lprice, Lpdf, Lvideo;
	Users currentUser;

	String serverDate = "";
	ServerDate date;

	ProgressDialog ringProgressDialog;

	Saving saving;
	Deleting deleting;
	Map<String, String> params;
	int CityId;
	int mainItem;
	int objectIdItem1;
	int parentId;
	int MainObjectId;
	int objectId;
	SavingImage3Picture savingImage;
	int ObjectTypeId;

	byte[] byteHeader, byteProfil, byteFooter;
	boolean flag = true;
	int lastItem = 0;

	int AgencyService;

	String d = "";

	boolean f1, f2, f3;

	private File mFileTemp;

	boolean t1 = false;
	boolean t2 = false;
	boolean t3 = false;

	// EditText inFacebook, inLinkedin, inTwiiter, inWebsite, inGoogle,
	// inInstagram;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_create_introduction,
				null);
		// Object = Integer.valueOf(getArguments().getString("I"));
		DBAdapter = new DataBaseAdapter(getActivity());
		util = new Utility(getActivity());

		currentUser = util.getCurrentUser();

		NetworkSocial = (RelativeLayout) view.findViewById(R.id.editNetwork);
		DownloadLink = (RelativeLayout) view.findViewById(R.id.editdownload);

		btnSave = (ImageButton) view.findViewById(R.id.btnsave);
		btnProfile = (ImageView) view.findViewById(R.id.profile_img);
		btnHeader = (ImageView) view.findViewById(R.id.imgvadvertise_Object);
		btnFooter = (ImageView) view.findViewById(R.id.imgvadvertise2_Object);

		NameEnter = (EditText) view.findViewById(R.id.nameInput);
		phoneEnter = (EditText) view.findViewById(R.id.ephone);
		faxEnter = (EditText) view.findViewById(R.id.efax);
		mobileEnter = (EditText) view.findViewById(R.id.emobile);
		emailEnter = (EditText) view.findViewById(R.id.eemail);
		addressEnter = (EditText) view.findViewById(R.id.eaddress);
		DescriptionEnter = (EditText) view
				.findViewById(R.id.descriptionEdittext);

		namayendegi = (RelativeLayout) view.findViewById(R.id.Layoutlink1);
		khadamat = (RelativeLayout) view.findViewById(R.id.Layoutlink2);

		checkAgency = (CheckBox) view.findViewById(R.id.checkAgency);
		checkService = (CheckBox) view.findViewById(R.id.checkService);

		namayendegi.setVisibility(View.GONE);
		khadamat.setVisibility(View.GONE);

		/* ********** start: come from create object fragment ********** */

		SharedPreferences sendToCreate = getActivity().getSharedPreferences(
				"Id", 0);
		MainObjectId = sendToCreate.getInt("MainObjectId", -1);
		CityId = sendToCreate.getInt("CityId", -1);
		objectId = sendToCreate.getInt("objectId", -1);
		ObjectTypeId = sendToCreate.getInt("ObjectTypeId", -1);
		AgencyService = sendToCreate.getInt("IsAgency", -1);

		/* ********** end: come from create of object fragment ********** */

		/* ********** start: come from create of main brand fragment ********** */
		SharedPreferences sendParentID = getActivity().getSharedPreferences(
				"Id", 0);
		parentId = sendParentID.getInt("ParentId", -1);
		mainItem = sendParentID.getInt("mainObject", -1);

		objectIdItem1 = sendParentID.getInt("objectId", -1);
		Toast.makeText(
				getActivity(),
				" parentId recieve = " + parentId + "\n mainObjectId = "
						+ mainItem, Toast.LENGTH_SHORT).show();

		/* ********** end: come from create of main brand fragment ********** */

		/* ********** start: come from main fragment ********** */
		SharedPreferences sendData = getActivity()
				.getSharedPreferences("Id", 0);
		mainID = sendData.getInt("main_Id", -1);

		/* ********** end: come from main fragment ********** */

		linearCreateProfil = (RelativeLayout) view
				.findViewById(R.id.linearCreateProfil);

		headerLinear = (LinearLayout) view.findViewById(R.id.headerLinear);
		footerLinear = (LinearLayout) view.findViewById(R.id.footerlinears);

		profilParams = new RelativeLayout.LayoutParams(
				linearCreateProfil.getLayoutParams());
		profilParams.width = (util.getScreenwidth() / 5);
		profilParams.height = (util.getScreenwidth() / 5);
		profilParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

		headerParams = new LinearLayout.LayoutParams(
				headerLinear.getLayoutParams());
		headerParams.height = util.getScreenwidth();
		headerParams.width = util.getScreenwidth();

		footerParams = new LinearLayout.LayoutParams(
				footerLinear.getLayoutParams());
		footerParams.height = util.getScreenwidth();
		footerParams.width = util.getScreenwidth();

		nameParams = new RelativeLayout.LayoutParams(
				linearCreateProfil.getLayoutParams());
		nameParams.width = util.getScreenwidth() / 3;
		nameParams.height = util.getScreenwidth() / 10;

		RelativeLayout.LayoutParams edittextParams = new RelativeLayout.LayoutParams(
				(int) (util.getScreenwidth() / 1.8), LayoutParams.WRAP_CONTENT);
		edittextParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		edittextParams.addRule(RelativeLayout.CENTER_IN_PARENT);

		phoneEnter.setLayoutParams(edittextParams);
		faxEnter.setLayoutParams(edittextParams);
		mobileEnter.setLayoutParams(edittextParams);
		emailEnter.setLayoutParams(edittextParams);
		addressEnter.setLayoutParams(edittextParams);

		btnHeader.setLayoutParams(headerParams);
		btnProfile.setLayoutParams(profilParams);
		btnFooter.setLayoutParams(headerParams);
		// NameEnter.setLayoutParams(nameParams);

		if (mainID != 1) {
			checkAgency.setVisibility(View.GONE);
			checkService.setVisibility(View.GONE);
		}

		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			mFileTemp = new File(Environment.getExternalStorageDirectory(),
					TEMP_PHOTO_FILE_NAME);
		} else {
			mFileTemp = new File(getActivity().getFilesDir(),
					TEMP_PHOTO_FILE_NAME);
		}

		btnProfile.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				getActivity().startActivityFromFragment(
						CreateIntroductionFragment.this, i, RESULT_LOAD_IMAGE);
			}
		});

		NetworkSocial.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialognetwork = new DialogNetworkSocial(
						CreateIntroductionFragment.this, getActivity(),
						R.layout.dialog_network_social);
				dialognetwork.setTitle("ویرایش  لینک های شبکه های اجتماعی");
				dialognetwork.show();

			}
		});

		DownloadLink.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialogDownload = new DialogLinkDownload(
						CreateIntroductionFragment.this, getActivity(),
						R.layout.dialog_download_link);
				dialogDownload.setTitle("ویرایش لینک های دانلود");
				dialogDownload.show();
			}
		});

		btnHeader.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				getActivity().startActivityFromFragment(
						CreateIntroductionFragment.this, i, HeaderCode);
			}
		});

		btnFooter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				getActivity().startActivityFromFragment(
						CreateIntroductionFragment.this, i, FooterCode);
			}
		});

		if (btnHeader.getDrawable() == null & btnProfile.getDrawable() == null
				& btnFooter.getDrawable() == null)

			Toast.makeText(getActivity(), "Empty Bitmap", Toast.LENGTH_SHORT)
					.show();

		btnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// if (checkAgency.isChecked())
				// globalMainObjectId = 3;
				// if (checkService.isChecked())
				// globalMainObjectId = 4;

				if (checkAgency.isChecked() && checkService.isChecked())
					ObjectBrandTypeId = 1;
				else if (checkAgency.isChecked())
					ObjectBrandTypeId = 3;
				else if (checkService.isChecked())
					ObjectBrandTypeId = 4;
				else
					ObjectBrandTypeId = 2;

				if (btnHeader.getDrawable() != null) {
					bitmapHeader = ((BitmapDrawable) btnHeader.getDrawable())
							.getBitmap();

					emptyHeader = Bitmap.createBitmap(bitmapHeader.getWidth(),
							bitmapHeader.getHeight(), bitmapHeader.getConfig());

					byteHeader = Utility.CompressBitmap(bitmapHeader);

					f1 = true;

				}

				if (btnProfile.getDrawable() != null) {
					bitmapProfil = ((BitmapDrawable) btnProfile.getDrawable())
							.getBitmap();

					emptyProfile = Bitmap.createBitmap(bitmapProfil.getWidth(),
							bitmapProfil.getHeight(), bitmapProfil.getConfig());
					byteProfil = Utility.CompressBitmap(bitmapProfil);
					f2 = true;

				}

				if (btnFooter.getDrawable() != null) {
					bitmapFooter = ((BitmapDrawable) btnFooter.getDrawable())
							.getBitmap();

					emptyFooter = Bitmap.createBitmap(bitmapFooter.getWidth(),
							bitmapFooter.getHeight(), bitmapFooter.getConfig());

					byteFooter = Utility.CompressBitmap(bitmapFooter);

					f3 = true;

				}

				if (bitmapHeader == null & bitmapProfil == null
						& bitmapFooter == null)

					Toast.makeText(getActivity(), "Empty ByteArray",
							Toast.LENGTH_SHORT).show();

				// final byte[] byteHeader = getBitmapAsByteArray(bitmapHeader);
				// final byte[] byteProfil = getBitmapAsByteArray(bitmapProfil);
				// final byte[] byteFooter = getBitmapAsByteArray(bitmapFooter);

				nameValue = NameEnter.getText().toString();
				phoneValue = phoneEnter.getText().toString();
				faxValue = faxEnter.getText().toString();
				mobileValue = mobileEnter.getText().toString();
				emailValue = emailEnter.getText().toString();
				addressValue = addressEnter.getText().toString();
				descriptionValue = DescriptionEnter.getText().toString();

				if (nameValue.equals("")) {
					Toast.makeText(getActivity(),
							"پر کردن فیلد نام اجباری است", Toast.LENGTH_SHORT)
							.show();

				}

				else if (phoneValue.equals("") && mobileValue.equals("")
						& emailValue.equals("") && faxValue.equals("")) {

					Toast.makeText(getActivity(),
							"پر کردن حداقل یکی از فیلدهای تماس الزامی است",
							Toast.LENGTH_SHORT).show();
				} else {
					date = new ServerDate(getActivity());
					date.delegate = CreateIntroductionFragment.this;
					date.execute("");

					ringProgressDialog = ProgressDialog.show(getActivity(),
							null, "لطفا منتظر بمانید.");

				}

			}
		});
		
		util.ShowFooterAgahi(getActivity() , false);


		return view;
	}

	public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		// bitmap.compress(CompressFormat.PNG, 50, outputStream);

		return outputStream.toByteArray();
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {


		if (requestCode == RESULT_LOAD_IMAGE) {
			try {

				InputStream inputStream = getActivity().getContentResolver()
						.openInputStream(data.getData());
				FileOutputStream fileOutputStream = new FileOutputStream(
						mFileTemp);
				Utility.copyStream(inputStream, fileOutputStream);
				fileOutputStream.close();
				inputStream.close();
				t1 = true;

				startCropImage();

			} catch (Exception e) {

				Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG)
						.show();
			}
			btnProfile.setLayoutParams(profilParams);


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
				btnProfile.setImageBitmap(bitmap);
				btnProfile.setLayoutParams(profilParams);
				t1=false;

			}
			if (bitmap != null && t2) {
				btnHeader.setImageBitmap(bitmap);
				btnHeader.setLayoutParams(headerParams);
				t2 = false;

			}
			if (bitmap != null && t3) {
				btnFooter.setImageBitmap(bitmap);
				btnFooter.setLayoutParams(footerParams);
				t3 = false;

			}

		}

	
		if (requestCode == HeaderCode && resultCode == Activity.RESULT_OK
				&& null != data) {
			try {

				InputStream inputStream = getActivity().getContentResolver()
						.openInputStream(data.getData());
				FileOutputStream fileOutputStream = new FileOutputStream(
						mFileTemp);
				Utility.copyStream(inputStream, fileOutputStream);
				fileOutputStream.close();
				inputStream.close();
				t2 = true;

				startCropImage();

			} catch (Exception e) {

				Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG)
						.show();
			}
			 btnHeader.setLayoutParams(headerParams);

		}
		if (requestCode == FooterCode && resultCode == Activity.RESULT_OK
				&& null != data) {
			try {

				InputStream inputStream = getActivity().getContentResolver()
						.openInputStream(data.getData());
				FileOutputStream fileOutputStream = new FileOutputStream(
						mFileTemp);
				Utility.copyStream(inputStream, fileOutputStream);
				fileOutputStream.close();
				inputStream.close();
				t3 = true;

				startCropImage();

			} catch (Exception e) {

				Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG)
						.show();
			}
		
			 btnFooter.setLayoutParams(footerParams);

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

		try {

			if (ringProgressDialog != null) {
				ringProgressDialog.dismiss();
			}

			serverId = Integer.valueOf(output);
			DBAdapter.open();

			if (mainID == 2 || mainID == 3 || mainID == 4) {
				if (flag == true) {

					DBAdapter.CreatePageInShopeObject(serverId, nameValue,
							phoneValue, emailValue, faxValue, descriptionValue,
							Lcatalog, Lprice, Lpdf, Lvideo, addressValue,
							mobileValue, Lfacebook, Linstagram, Llinkedin,
							Lgoogle, Lwebsite, Ltwitter, currentUser.getId(),
							mainID, ObjectBrandTypeId, ObjectTypeId);

					flag = false;

					lastItem = serverId;

					params = new LinkedHashMap<String, String>();
					saving = new Saving(getActivity());
					saving.delegate = CreateIntroductionFragment.this;

					params.put("TableName", "ObjectInCity");

					params.put("ObjectId", String.valueOf(lastItem));
					params.put("CityId", String.valueOf(CityId));
					params.put("Date", serverDate);
					params.put("ModifyDate", serverDate);

					params.put("IsUpdate", "0");
					params.put("Id", "0");

					saving.execute(params);

					ringProgressDialog = ProgressDialog.show(getActivity(),
							null, "لطفا منتظر بمانید.");

				} else {

					if (ringProgressDialog != null) {
						ringProgressDialog.dismiss();
					}
					DBAdapter.insertObjectInCity(serverId, lastItem, CityId, d);

					if (ringProgressDialog != null) {
						ringProgressDialog.dismiss();
					}

					if (btnHeader.getDrawable() != null
							|| btnProfile.getDrawable() != null
							|| btnFooter.getDrawable() != null) {
						savingImage = new SavingImage3Picture(getActivity());
						savingImage.delegate = this;
						Map<String, Object> it = new LinkedHashMap<String, Object>();

						it.put("tableName", "Object");
						it.put("fieldName1", "Image1");
						it.put("fieldName2", "Image2");
						it.put("fieldName3", "Image3");

						it.put("id", lastItem);

						it.put("Image1", byteHeader);

						it.put("Image2", byteProfil);

						it.put("Image3", byteFooter);

						savingImage.execute(it);
						ringProgressDialog = ProgressDialog
								.show(getActivity(), null,
										"به منظور ذخیره سازی تصاویر لطفا چند لحظه منتظر بمانید.");

					}
				}

			}
			if (mainID == 1 && flag) {
				// int m = (Integer) null;

				DBAdapter.InsertInformationNewObject(serverId, nameValue,
						phoneValue, emailValue, faxValue, descriptionValue,
						Lcatalog, Lprice, Lpdf, Lvideo, addressValue,
						mobileValue, Lfacebook, Linstagram, Llinkedin, Lgoogle,
						Lwebsite, Ltwitter, currentUser.getId(), parentId, 1,
						objectIdItem1, ObjectBrandTypeId, 100, serverDate);

				lastItem = serverId;

				if (btnHeader.getDrawable() != null
						|| btnProfile.getDrawable() != null
						|| btnFooter.getDrawable() != null) {
					savingImage = new SavingImage3Picture(getActivity());
					savingImage.delegate = this;
					Map<String, Object> it = new LinkedHashMap<String, Object>();

					it.put("tableName", "Object");
					it.put("fieldName1", "Image1");
					it.put("fieldName2", "Image2");
					it.put("fieldName3", "Image3");

					it.put("id", serverId);

					it.put("Image1", byteHeader);

					it.put("Image2", byteProfil);

					it.put("Image3", byteFooter);

					savingImage.execute(it);
					ringProgressDialog = ProgressDialog
							.show(getActivity(), null,
									"به منظور ذخیره سازی تصاویر لطفا چند لحظه منتظر بمانید.");

				}
			}
			if (mainID > 4) {
				if (flag) {
					DBAdapter.InsertInformationNewObject(serverId, nameValue,
							phoneValue, emailValue, faxValue, descriptionValue,
							Lcatalog, Lprice, Lpdf, Lvideo, addressValue,
							mobileValue, Lfacebook, Linstagram, Llinkedin,
							Lgoogle, Lwebsite, Ltwitter, currentUser.getId(),
							0, MainObjectId, objectId, ObjectBrandTypeId,
							AgencyService, serverDate);

					flag = false;

					lastItem = serverId;

					params = new LinkedHashMap<String, String>();
					saving = new Saving(getActivity());
					saving.delegate = CreateIntroductionFragment.this;

					params.put("TableName", "ObjectInCity");

					params.put("ObjectId", String.valueOf(lastItem));
					params.put("CityId", String.valueOf(CityId));
					params.put("Date", serverDate);
					params.put("ModifyDate", serverDate);

					params.put("IsUpdate", "0");
					params.put("Id", "0");
					// serverDate = output;

					saving.execute(params);

					ringProgressDialog = ProgressDialog.show(getActivity(),
							null, "لطفا منتظر بمانید.");
				} else {
					DBAdapter.insertObjectInCity(serverId, lastItem, CityId,
							serverDate);

					if (btnHeader.getDrawable() != null
							|| btnProfile.getDrawable() != null
							|| btnFooter.getDrawable() != null) {
						savingImage = new SavingImage3Picture(getActivity());
						savingImage.delegate = this;
						Map<String, Object> it = new LinkedHashMap<String, Object>();

						it.put("tableName", "Object");
						it.put("fieldName1", "Image1");
						it.put("fieldName2", "Image2");
						it.put("fieldName3", "Image3");

						it.put("id", lastItem);

						it.put("Image1", byteHeader);

						it.put("Image2", byteProfil);

						it.put("Image3", byteFooter);

						savingImage.execute(it);
						ringProgressDialog = ProgressDialog
								.show(getActivity(), null,
										"به منظور ذخیره سازی تصاویر لطفا چند لحظه منتظر بمانید.");

					}

				}
			}
		

			DBAdapter.close();

		} catch (NumberFormatException e) {
			if (output != null
					&& !(output.contains("Exception") || output
							.contains("java"))) {

				if (mainID == 2 || mainID == 3 || mainID == 4) {

					params = new LinkedHashMap<String, String>();
					saving = new Saving(getActivity());
					saving.delegate = CreateIntroductionFragment.this;

					params.put("TableName", "Object");

					params.put("Name", nameValue);
					params.put("Phone", phoneValue);
					params.put("Email", emailValue);
					params.put("Fax", faxValue);
					params.put("Description", descriptionValue);
					params.put("Cellphone", mobileValue);
					params.put("Address", addressValue);
					params.put("Pdf1", Lcatalog);
					params.put("Pdf2", Lprice);
					params.put("Pdf3", Lpdf);
					params.put("Pdf4", Lvideo);
					params.put("ObjectBrandTypeId",
							String.valueOf(ObjectBrandTypeId));
					params.put("Facebook", Lfacebook);
					params.put("Instagram", Linstagram);
					params.put("LinkedIn", Llinkedin);
					params.put("Google", Lgoogle);
					params.put("Site", Lwebsite);
					params.put("Twitter", Ltwitter);
					params.put("UserId", String.valueOf(currentUser.getId()));
					params.put("Twitter", Ltwitter);
					params.put("MainObjectId", String.valueOf(mainID));
					params.put("ObjectTypeId", String.valueOf(ObjectTypeId));

					params.put("Date", output);
					params.put("ModifyDate", output);

					params.put("IsActive", "0");
					params.put("IsUpdate", "0");
					params.put("Id", "0");
					d = serverDate = output;

					saving.execute(params);

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

					getActivity().getSupportFragmentManager().popBackStack();

				} else if (mainID == 1) {

					params = new LinkedHashMap<String, String>();
					saving = new Saving(getActivity());
					saving.delegate = CreateIntroductionFragment.this;

					params.put("TableName", "Object");

					params.put("Name", nameValue);
					params.put("Phone", phoneValue);
					params.put("Email", emailValue);
					params.put("Fax", faxValue);
					params.put("Description", descriptionValue);
					params.put("Cellphone", mobileValue);
					params.put("Address", addressValue);
					params.put("Pdf1", Lcatalog);
					params.put("Pdf2", Lprice);
					params.put("Pdf3", Lpdf);
					params.put("Pdf4", Lvideo);
					params.put("Facebook", Lfacebook);
					params.put("Instagram", Linstagram);
					params.put("LinkedIn", Llinkedin);
					params.put("Google", Lgoogle);
					params.put("Site", Lwebsite);
					params.put("Twitter", Ltwitter);
					params.put("ObjectBrandTypeId",
							String.valueOf(ObjectBrandTypeId));
					params.put("UserId", String.valueOf(currentUser.getId()));
					params.put("ParentId", String.valueOf(parentId));
					params.put("MainObjectId", String.valueOf(1));
					params.put("ObjectId", String.valueOf(objectIdItem1));
					params.put("Date", output);
					params.put("ModifyDate", output);

					params.put("IsActive", "0");
					params.put("IsUpdate", "0");
					params.put("Id", "0");
					serverDate = output;

					saving.execute(params);

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

					getActivity().getSupportFragmentManager().popBackStack();

				} else {

					params = new LinkedHashMap<String, String>();
					saving = new Saving(getActivity());
					saving.delegate = CreateIntroductionFragment.this;

					params.put("TableName", "Object");

					params.put("Name", nameValue);
					params.put("Phone", phoneValue);
					params.put("Email", emailValue);
					params.put("Fax", faxValue);
					params.put("Description", descriptionValue);

					params.put("Pdf1", Lcatalog);
					params.put("Pdf2", Lprice);
					params.put("Pdf3", Lpdf);
					params.put("Pdf4", Lvideo);

					params.put("Cellphone", mobileValue);
					params.put("Address", addressValue);

					params.put("Facebook", Lfacebook);
					params.put("Instagram", Linstagram);
					params.put("LinkedIn", Llinkedin);
					params.put("Google", Lgoogle);
					params.put("Site", Lwebsite);
					params.put("Twitter", Ltwitter);

					params.put("UserId", String.valueOf(currentUser.getId()));
					params.put("ParentId", String.valueOf(0));

					params.put("MainObjectId", String.valueOf(MainObjectId));
					params.put("ObjectId", String.valueOf(objectId));
					params.put("ObjectBrandTypeId",
							String.valueOf(ObjectBrandTypeId));

					params.put("AgencyService", String.valueOf(AgencyService));

					serverDate = output;
					params.put("Date", output);
					params.put("ModifyDate", output);

					params.put("IsActive", "0");

					params.put("IsUpdate", "0");
					params.put("Id", "0");
					saving.execute(params);

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

					getActivity().getSupportFragmentManager().popBackStack();

				}

			} else {
				Toast.makeText(getActivity(),
						"خطا در ثبت. پاسخ نا مشخص از سرور", Toast.LENGTH_SHORT)
						.show();
			}

		} catch (Exception e) {

			Toast.makeText(getActivity(), "خطا در ثبت" + e, Toast.LENGTH_SHORT)
					.show();
		}
	}

	@Override
	public void processFinishSaveImage(String output) {
		if (ringProgressDialog != null) {
			ringProgressDialog.dismiss();
		}
		try {
			
			util.CreateFile(byteHeader, lastItem, "Mechanical", "Profile", "header", "Object");
			util.CreateFile(byteProfil, lastItem, "Mechanical", "Profile", "profile", "Object");
			util.CreateFile(byteFooter, lastItem, "Mechanical", "Profile", "footer", "Object");


//			DBAdapter.open();
//			DBAdapter.UpdateImageObjectToDatabase(lastItem, byteHeader,
//					byteProfil, byteFooter);

			if (f1)
				DBAdapter.updateObjectImage1ServerDate(lastItem, output);
			if (f2)
				DBAdapter.updateObjectImage2ServerDate(lastItem, output);
			if (f3)
				DBAdapter.updateObjectImage3ServerDate(lastItem, output);

			DBAdapter.close();

			Toast.makeText(getActivity(),
					"ذخیره سازی تصاویر با موفقیت انجا م شد", 0).show();

			if (ringProgressDialog != null) {
				ringProgressDialog.dismiss();
			}

		} catch (Exception e) {
			Toast.makeText(getActivity(), "خطا در ذخیره سازی تصاویر" + e,
					Toast.LENGTH_SHORT).show();
		}
	}
}
