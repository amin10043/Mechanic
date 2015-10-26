package com.project.mechanic.fragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
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
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mechanic.R;
import com.project.mechanic.crop.CropImage;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.inter.SaveAsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.Saving;
import com.project.mechanic.service.SavingImage3Picture;
import com.project.mechanic.utility.Utility;

public class IntroductionEditFragment extends Fragment implements
		AsyncInterface, SaveAsyncInterface {
	private static int headerLoadCode = 1;
	private static int profileLoadCode = 2;
	private static int footerLoadcode = 3;

	DataBaseAdapter DBAdapter;
	Utility util;

	com.project.mechanic.entity.Object object;

	DialogEditDownloadIntroduction diadown;

	DialogEditNet dianet;

	ImageButton btnSave;

	String nameValue, phoneValue, faxValue, mobileValue, emailValue,
			addressValue, descriptionValue;

	EditText nameEnter, phoneEnter, faxEnter, mobileEnter, emailEnter,
			addressEnter, descriptionEnter;

	ImageView headerImageEdit, profileImageEdit, footerImageEdit;

	LinearLayout.LayoutParams headerEditParams, footerEditParams,
			editnameParams;

	RelativeLayout.LayoutParams profileEditParams;

	RelativeLayout namayendegi, khadamat, Linearprofile;

	LinearLayout editnetLink, Linearheader, Linearfooter, editDNlink,
			AdminsPage;

	public String Dcatalog, Dprice, Dpdf, Dvideo;
	public String Dface, Dlink, Dtwt, Dweb, Dgoogle, Dinstagram;
	RatingBar rating;
	ImageView payBtn, copyBtn;

	Bitmap bmpHeader, bmpProfil, bmpFooter;
	Saving saving;
	Map<String, String> params;
	int PageId;
	ProgressDialog ringProgressDialog;
	SavingImage3Picture savingImage;

	byte[] byteHeader, byteProfil, byteFooter;
	private File mFileTemp;

	public static final String TEMP_PHOTO_FILE_NAME = "temp_photo.jpg";
	final int PIC_CROP = 10;

	boolean t1 = false;
	boolean t2 = false;
	boolean t3 = false;

	TextView linkPage;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_introduction_edit, null);

		DBAdapter = new DataBaseAdapter(getActivity());
		util = new Utility(getActivity());

		btnSave = (ImageButton) view.findViewById(R.id.btnsave);
		headerImageEdit = (ImageView) view.findViewById(R.id.hh1);
		profileImageEdit = (ImageView) view.findViewById(R.id.pp1);
		footerImageEdit = (ImageView) view.findViewById(R.id.ft1);

		nameEnter = (EditText) view.findViewById(R.id.nameEdit);
		phoneEnter = (EditText) view.findViewById(R.id.editTextphone);
		faxEnter = (EditText) view.findViewById(R.id.editTextfax);
		mobileEnter = (EditText) view.findViewById(R.id.editTextmob);
		emailEnter = (EditText) view.findViewById(R.id.editTextemail);
		addressEnter = (EditText) view.findViewById(R.id.editTextaddres);
		descriptionEnter = (EditText) view
				.findViewById(R.id.descriptionpageedit);

		editnetLink = (LinearLayout) view.findViewById(R.id.editpageNetwork);
		editDNlink = (LinearLayout) view.findViewById(R.id.editpagedownload);
		namayendegi = (RelativeLayout) view.findViewById(R.id.Layoutlink1);
		khadamat = (RelativeLayout) view.findViewById(R.id.Layoutlink2);
		AdminsPage = (LinearLayout) view.findViewById(R.id.listAdmin);
		rating = (RatingBar) view.findViewById(R.id.ratingBar1);
		payBtn = (ImageView) view.findViewById(R.id.btnPay);

		copyBtn = (ImageView) view.findViewById(R.id.copyToClipboard);
		linkPage = (TextView) view.findViewById(R.id.linkPage);

		namayendegi.setVisibility(View.GONE);
		khadamat.setVisibility(View.GONE);

		Linearheader = (LinearLayout) view.findViewById(R.id.headerLinearPage);
		Linearprofile = (RelativeLayout) view
				.findViewById(R.id.linearEditProfil);
		Linearfooter = (LinearLayout) view.findViewById(R.id.linearfooteredit);

		headerEditParams = new LinearLayout.LayoutParams(
				Linearheader.getLayoutParams());
		headerEditParams.height = (int) (util.getScreenwidth());

		profileEditParams = new RelativeLayout.LayoutParams(
				Linearprofile.getLayoutParams());
		profileEditParams.width = util.getScreenwidth() / 4;
		profileEditParams.height = util.getScreenwidth() / 4;
		profileEditParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		profileEditParams.addRule(RelativeLayout.BELOW, R.id.btnsave);
		profileEditParams.setMargins(5, 5, 5, 5);

		footerEditParams = new LinearLayout.LayoutParams(
				Linearfooter.getLayoutParams());
		footerEditParams.height = util.getScreenwidth();

		editnameParams = new LinearLayout.LayoutParams(
				Linearprofile.getLayoutParams());
		editnameParams.width = util.getScreenwidth() / 3;
		editnameParams.height = util.getScreenwidth() / 10;

		RelativeLayout.LayoutParams edittextParams = new RelativeLayout.LayoutParams(
				(int) (util.getScreenwidth() / 1.8), LayoutParams.WRAP_CONTENT);
		edittextParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

		phoneEnter.setLayoutParams(edittextParams);
		faxEnter.setLayoutParams(edittextParams);
		mobileEnter.setLayoutParams(edittextParams);
		emailEnter.setLayoutParams(edittextParams);
		addressEnter.setLayoutParams(edittextParams);

		headerImageEdit.setLayoutParams(headerEditParams);
		profileImageEdit.setLayoutParams(profileEditParams);
		footerImageEdit.setLayoutParams(footerEditParams);

		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			mFileTemp = new File(Environment.getExternalStorageDirectory(),
					TEMP_PHOTO_FILE_NAME);
		} else {
			mFileTemp = new File(getActivity().getFilesDir(),
					TEMP_PHOTO_FILE_NAME);
		}

		SharedPreferences sendDataID = getActivity().getSharedPreferences("Id",
				0);
		PageId = sendDataID.getInt("main_Id", -1);

		Toast.makeText(getActivity(), "introduction id =" + PageId,
				Toast.LENGTH_SHORT).show();

		// /////////display information///////////////////////

		// SharedPreferences sendDataID1 = getActivity().getSharedPreferences(
		// "Id", 0);
		// final int cid = sendDataID1.getInt("main_Id", -1);
		DBAdapter.open();
		object = DBAdapter.getObjectbyid(PageId);

		String pathHeader = object.getImagePath1();
		String pathProfile = object.getImagePath2();
		String pathFooter = object.getImagePath3();

		Bitmap bmpHeader = BitmapFactory.decodeFile(pathHeader);
		Bitmap bmpPrifile = BitmapFactory.decodeFile(pathProfile);
		Bitmap bmpfooter = BitmapFactory.decodeFile(pathFooter);

		// byte[] bitmapbyte = OBJECT.getImage2();

		if (bmpHeader != null) {
			// Bitmap bmp = BitmapFactory.decodeByteArray(bitmapbyte, 0,
			// bitmapbyte.length);
			headerImageEdit.setImageBitmap(bmpHeader);
		} else
			headerImageEdit.setImageResource(R.drawable.no_image_header);

		// byte[] bitmap = object.getImage1();
		if (bmpPrifile != null) {
			// Bitmap bmp = BitmapFactory
			// .decodeByteArray(bitmap, 0, bitmap.length);
			profileImageEdit.setImageBitmap(bmpPrifile);
		} else
			profileImageEdit.setImageResource(R.drawable.no_img_profile);

		// byte[] bitm = object.getImage3();
		if (bmpfooter != null) {
			// Bitmap bmp = BitmapFactory.decodeByteArray(bitm, 0, bitm.length);
			footerImageEdit.setImageBitmap(bmpfooter);
		} else
			footerImageEdit.setImageResource(R.drawable.no_image_header);

		nameEnter.setText(object.getName());
		phoneEnter.setText(object.getPhone());
		faxEnter.setText(object.getFax());
		mobileEnter.setText(object.getCellphone());
		emailEnter.setText(object.getEmail());
		addressEnter.setText(object.getAddress());

		DBAdapter.close();

		// //////////////////////////////////////////////////

		copyBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ClipboardManager clipMan = (ClipboardManager) getActivity()
						.getSystemService(Context.CLIPBOARD_SERVICE);
				ClipData cData = ClipData.newPlainText("text", linkPage
						.getText().toString());
				clipMan.setPrimaryClip(cData);
				Toast.makeText(getActivity(), "آدرس صفحه کپی شد", 0).show();

			}
		});

		headerImageEdit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				getActivity().startActivityFromFragment(
						IntroductionEditFragment.this, i, headerLoadCode);

			}
		});

		payBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				int rat = (int) rating.getRating();
				if (rat >= 0) {
					DBAdapter.open();

					DBAdapter.updateRatingObject(rat, PageId);

					Toast.makeText(getActivity(), "ثبت درگاه انجام شود ", 0)
							.show();

					Toast.makeText(getActivity(), rat + "", 0).show();

					DBAdapter.close();

				} else {
					Toast.makeText(getActivity(), "انتخاب ستاره ها الزامی است",
							0).show();
				}
			}
		});

		AdminsPage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				int AdminId = object.getUserId();
				DialogAdminsPage adminDialog = new DialogAdminsPage(
						getActivity(), PageId, AdminId);
				adminDialog.show();
			}
		});

		profileImageEdit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				getActivity().startActivityFromFragment(
						IntroductionEditFragment.this, i, profileLoadCode);

			}
		});
		footerImageEdit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				getActivity().startActivityFromFragment(
						IntroductionEditFragment.this, i, footerLoadcode);

			}
		});

		editDNlink.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				diadown = new DialogEditDownloadIntroduction(
						IntroductionEditFragment.this, getActivity(),
						R.layout.dialog_edit_link_dn);
				diadown.setTitle("ویرایش لینک های دانلود");
				diadown.show();
			}
		});

		editnetLink.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dianet = new DialogEditNet(IntroductionEditFragment.this,
						getActivity(), R.layout.dialog_edit_link_dn);
				dianet.setTitle("ویرایش لینک شبکه های اجتماعی");
				dianet.show();
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

				if (nameValue.equals("")) {
					Toast.makeText(getActivity(),
							"پر کردن فیلد نام الزامی است", Toast.LENGTH_SHORT)
							.show();
				} else {

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
					params.put("Site", Dweb);
					params.put("Twitter", Dtwt);

					params.put("IsUpdate", "1");
					params.put("Id", String.valueOf(PageId));
					saving.execute(params);

					ringProgressDialog = ProgressDialog.show(getActivity(),
							"در حال بروزرسانی", "لطفا منتظر بمانید...");

					getActivity().getSupportFragmentManager().popBackStack();
				}
			}
		});

		return view;
	}

	public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 50, outputStream);
		byte[] array = outputStream.toByteArray();
		BitmapFactory.decodeByteArray(array, 0, array.length);
		return outputStream.toByteArray();

	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == profileLoadCode) {
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

			}
			if (bitmap != null && t2) {
				headerImageEdit.setImageBitmap(bitmap);
				headerImageEdit.setLayoutParams(headerEditParams);
				t2 = false;

			}
			if (bitmap != null && t3) {
				footerImageEdit.setImageBitmap(bitmap);
				footerImageEdit.setLayoutParams(footerEditParams);
				t3 = false;

			}

		}

		if (requestCode == headerLoadCode && resultCode == Activity.RESULT_OK
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
			headerImageEdit.setLayoutParams(headerEditParams);

		}
		if (requestCode == footerLoadcode && resultCode == Activity.RESULT_OK
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
			// if (getActivity() != null) {

			//
			// byte[] byteHeader = util.CompressBitmap(bmpHeader);
			// byte[] byteProfil = util.CompressBitmap(bmpProfil);
			// byte[] byteFooter = util.CompressBitmap(bmpFooter);

			if (headerImageEdit.getDrawable() == null
					&& profileImageEdit.getDrawable() == null
					&& footerImageEdit.getDrawable() == null) {

				Toast.makeText(getActivity(), "Empty ByteArray",
						Toast.LENGTH_SHORT).show();
			}

			if (headerImageEdit.getDrawable() != null) {
				bmpHeader = ((BitmapDrawable) headerImageEdit.getDrawable())
						.getBitmap();
				if (bmpHeader != null)
					byteHeader = Utility.CompressBitmap(bmpHeader);

			}
			if (profileImageEdit.getDrawable() != null) {
				bmpProfil = ((BitmapDrawable) profileImageEdit.getDrawable())
						.getBitmap();
				if (bmpProfil != null)
					byteProfil = Utility.CompressBitmap(bmpProfil);
			}
			if (footerImageEdit.getDrawable() != null) {
				bmpFooter = ((BitmapDrawable) footerImageEdit.getDrawable())
						.getBitmap();
				if (bmpFooter != null)
					byteFooter = Utility.CompressBitmap(bmpFooter);
			}

			if (getActivity() != null) {
				savingImage = new SavingImage3Picture(getActivity());
				savingImage.delegate = IntroductionEditFragment.this;
				Map<String, Object> it = new LinkedHashMap<String, Object>();

				it.put("tableName", "Object");
				it.put("fieldName1", "Image1");
				it.put("fieldName2", "Image2");
				it.put("fieldName3", "Image3");

				it.put("id", String.valueOf(PageId));

				it.put("Image1", byteHeader);
				it.put("Image2", byteProfil);
				it.put("Image3", byteFooter);

				savingImage.execute(it);
				ringProgressDialog = ProgressDialog
						.show(getActivity(), null,
								"به منظور به روز رسانی تصاویر لطفا چند لحظه منتظر بمانید.");
			}
			// } else {
			DBAdapter.open();
			DBAdapter.UpdateObjectProperties(PageId, nameValue, phoneValue,
					emailValue, faxValue, descriptionValue, Dcatalog, Dprice,
					Dpdf, Dvideo, addressValue, mobileValue, Dface, Dinstagram,
					Dlink, Dgoogle, Dweb, Dtwt);

			DBAdapter.close();
			// if (ringProgressDialog != null) {
			// ringProgressDialog.dismiss();
			// }

			// }

		} catch (NumberFormatException ex) {
			Toast.makeText(getActivity(), "خطا در بروز رسانی",
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void processFinishSaveImage(String output) {
		Toast.makeText(getActivity(), "output = " + output, 0).show();

		if (output != null) {
			// try {
			// bmpHeader = ((BitmapDrawable) headerImageEdit.getDrawable())
			// .getBitmap();
			// bmpProfil = ((BitmapDrawable) profileImageEdit.getDrawable())
			// .getBitmap();
			// bmpFooter = ((BitmapDrawable) footerImageEdit.getDrawable())
			// .getBitmap();

			// byte[] byteHeader = getBitmapAsByteArray(bmpHeader);
			// byte[] byteProfil = getBitmapAsByteArray(bmpProfil);
			// byte[] byteFooter = getBitmapAsByteArray(bmpFooter);

			if (headerImageEdit.getDrawable() == null
					&& profileImageEdit.getDrawable() == null
					&& footerImageEdit.getDrawable() == null) {

				Toast.makeText(getActivity(), "Empty ByteArray",
						Toast.LENGTH_SHORT).show();
			}

			// byteHeader = Utility.CompressBitmap(bmpHeader);
			// byteProfil = Utility.CompressBitmap(bmpProfil);
			// byteFooter = Utility.CompressBitmap(bmpFooter);

			util.CreateFile(byteHeader, PageId, "Mechanical", "Profile",
					"header", "Object");

			util.CreateFile(byteProfil, PageId, "Mechanical", "Profile",
					"profile", "Object");

			util.CreateFile(byteFooter, PageId, "Mechanical", "Profile",
					"footer", "Object");

			// DBAdapter.open();
			// DBAdapter.updateAllImageIntroductionPage(PageId, byteHeader,
			// byteProfil, byteFooter);
			// DBAdapter.close();
			if (ringProgressDialog != null) {
				ringProgressDialog.dismiss();
			}

			// } catch (NumberFormatException e) {
			// Toast.makeText(getActivity(), "  خطا در بروز رسانی تصویر",
			// Toast.LENGTH_SHORT).show();
			// }
		}
		if (ringProgressDialog != null) {
			ringProgressDialog.dismiss();
		}
	}

}
