package com.project.mechanic.fragment;

import java.io.ByteArrayOutputStream;

import android.app.Activity;
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
import com.project.mechanic.entity.Users;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class CreateIntroductionFragment extends Fragment {

	private static int RESULT_LOAD_IMAGE = 1;
	private static int HeaderCode = 2;
	private static int FooterCode = 3;
	int Object;
	int ObjectBrandTypeId;

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

	Bitmap bitmapHeader, bitmapProfil, bitmapFooter;

	public String Lfacebook, Llinkedin, Ltwitter, Lwebsite, Lgoogle,
			Linstagram;
	public String Lcatalog, Lprice, Lpdf, Lvideo;
	Users currentUser;

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
		final int MainObjectId = sendToCreate.getInt("MainObjectId", -1);
		final int CityId = sendToCreate.getInt("CityId", -1);
		final int objectId = sendToCreate.getInt("objectId", -1);

		/* ********** end: come from create of object fragment ********** */

		/* ********** start: come from create of main brand fragment ********** */
		SharedPreferences sendParentID = getActivity().getSharedPreferences(
				"Id", 0);
		final int parentId = sendParentID.getInt("ParentId", -1);
		final int mainItem = sendParentID.getInt("mainObject", -1);

		final int objectIdItem1 = sendParentID.getInt("objectId", -1);
		Toast.makeText(
				getActivity(),
				" parentId recieve = " + parentId + "\n ObjectBrandTypeId = "
						+ mainItem, Toast.LENGTH_SHORT).show();

		/* ********** end: come from create of main brand fragment ********** */

		/* ********** start: come from main fragment ********** */
		SharedPreferences sendData = getActivity()
				.getSharedPreferences("Id", 0);
		final int mainID = sendData.getInt("main_Id", -1);

		/* ********** end: come from main fragment ********** */

		linearCreateProfil = (RelativeLayout) view
				.findViewById(R.id.linearCreateProfil);

		headerLinear = (LinearLayout) view.findViewById(R.id.headerLinear);
		footerLinear = (LinearLayout) view.findViewById(R.id.footerlinears);

		profilParams = new RelativeLayout.LayoutParams(
				linearCreateProfil.getLayoutParams());
		profilParams.width = (util.getScreenwidth() / 8);
		profilParams.height = (util.getScreenwidth() / 8);
		profilParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

		headerParams = new LinearLayout.LayoutParams(
				headerLinear.getLayoutParams());
		headerParams.height = util.getScreenHeight() / 3;

		footerParams = new LinearLayout.LayoutParams(
				footerLinear.getLayoutParams());
		footerParams.height = util.getScreenHeight() / 3;

		nameParams = new RelativeLayout.LayoutParams(
				linearCreateProfil.getLayoutParams());
		nameParams.width = util.getScreenwidth() / 3;
		nameParams.height = util.getScreenwidth() / 10;

		RelativeLayout.LayoutParams edittextParams = new RelativeLayout.LayoutParams(
				(int) (util.getScreenwidth() / 1.8), LayoutParams.WRAP_CONTENT);
		edittextParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

		phoneEnter.setLayoutParams(edittextParams);
		faxEnter.setLayoutParams(edittextParams);
		mobileEnter.setLayoutParams(edittextParams);
		emailEnter.setLayoutParams(edittextParams);
		addressEnter.setLayoutParams(edittextParams);

		btnHeader.setLayoutParams(headerParams);
		btnProfile.setLayoutParams(profilParams);
		btnFooter.setLayoutParams(headerParams);
		// NameEnter.setLayoutParams(nameParams);

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

				bitmapHeader = ((BitmapDrawable) btnHeader.getDrawable())
						.getBitmap();
				bitmapProfil = ((BitmapDrawable) btnProfile.getDrawable())
						.getBitmap();
				bitmapFooter = ((BitmapDrawable) btnFooter.getDrawable())
						.getBitmap();

				if (bitmapHeader == null & bitmapProfil == null
						& bitmapFooter == null)

					Toast.makeText(getActivity(), "Empty ByteArray",
							Toast.LENGTH_SHORT).show();

				final byte[] byteHeader = getBitmapAsByteArray(bitmapHeader);
				final byte[] byteProfil = getBitmapAsByteArray(bitmapProfil);
				final byte[] byteFooter = getBitmapAsByteArray(bitmapFooter);

				nameValue = NameEnter.getText().toString();
				phoneValue = phoneEnter.getText().toString();
				faxValue = faxEnter.getText().toString();
				mobileValue = mobileEnter.getText().toString();
				emailValue = emailEnter.getText().toString();
				addressValue = addressEnter.getText().toString();
				descriptionValue = DescriptionEnter.getText().toString();

				if (nameValue.equals("") || mobileValue.equals("")) {
					Toast.makeText(getActivity(),
							"پر کردن فیلدهای نام وموبایل الزامی است",
							Toast.LENGTH_SHORT).show();
				}

				else {

					DBAdapter.open();
					if (mainID == 2 || mainID == 3 || mainID == 4) {
						int LastObjectId = DBAdapter.CreatePageInShopeObject(
								nameValue, phoneValue, emailValue, faxValue,
								descriptionValue, byteHeader, byteProfil,
								byteFooter, Lcatalog, Lprice, Lpdf, Lvideo,
								addressValue, mobileValue, Lfacebook,
								Linstagram, Llinkedin, Lgoogle, Lwebsite,
								Ltwitter, currentUser.getId(), mainID,
								ObjectBrandTypeId);

						DBAdapter.insertObjectInCity(LastObjectId, CityId);

					} else if (mainID == 1) {

						int LastObjectId = DBAdapter
								.InsertInformationNewObject(nameValue,
										phoneValue, emailValue, faxValue,
										descriptionValue, byteHeader,
										byteProfil, byteFooter, Lcatalog,
										Lprice, Lpdf, Lvideo, addressValue,
										mobileValue, Lfacebook, Linstagram,
										Llinkedin, Lgoogle, Lwebsite, Ltwitter,
										currentUser.getId(), parentId,
										mainItem, objectIdItem1,
										ObjectBrandTypeId);
						if (objectIdItem1 > 4)
							DBAdapter.insertObjectInCity(LastObjectId, CityId);

					} else {

						int LastObjectId = DBAdapter
								.InsertInformationNewObject(nameValue,
										phoneValue, emailValue, faxValue,
										descriptionValue, byteHeader,
										byteProfil, byteFooter, Lcatalog,
										Lprice, Lpdf, Lvideo, addressValue,
										mobileValue, Lfacebook, Linstagram,
										Llinkedin, Lgoogle, Lwebsite, Ltwitter,
										currentUser.getId(), 0, MainObjectId,
										objectId, ObjectBrandTypeId);
						if (objectIdItem1 > 4)
							DBAdapter.insertObjectInCity(LastObjectId, CityId);

					}

					DBAdapter.close();
					getActivity().getSupportFragmentManager().popBackStack();

				}

			}
		});

		return view;
	}

	public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 50, outputStream);
		return outputStream.toByteArray();
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
			btnProfile.setImageBitmap(BitmapFactory.decodeFile(picturePath));
			btnProfile.setBackgroundColor(getResources().getColor(
					android.R.color.transparent));
			btnProfile.setLayoutParams(profilParams);
			// NameEnter.setLayoutParams(nameParams);

		}
		if (requestCode == HeaderCode && resultCode == Activity.RESULT_OK
				&& null != data) {
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

			btnHeader.setImageBitmap(BitmapFactory.decodeFile(picturePath));
			btnHeader.setBackgroundColor(getResources().getColor(
					android.R.color.transparent));
			btnHeader.setLayoutParams(headerParams);
			// NameEnter.setLayoutParams(nameParams);

		}
		if (requestCode == FooterCode && resultCode == Activity.RESULT_OK
				&& null != data) {
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

			btnFooter.setImageBitmap(BitmapFactory.decodeFile(picturePath));
			btnFooter.setBackgroundColor(getResources().getColor(
					android.R.color.transparent));
			btnFooter.setLayoutParams(headerParams);
			// NameEnter.setLayoutParams(nameParams);

		}

	}

}
