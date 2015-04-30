package com.project.mechanic.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.project.mechanic.R;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class CreateIntroductionFragment extends Fragment {

	private static int RESULT_LOAD_IMAGE = 1;
	private static int HeaderCode = 2;
	private static int FooterCode = 3;

	DataBaseAdapter DBAdapter;
	ImageButton btnSave, btnProfile, btnHeader, btnFooter;
	String phoneValue, faxValue, mobileValue, emailValue, addressValue;
	EditText phoneEnter, faxEnter, mobileEnter, emailEnter, addressEnter;
	Fragment fragment;
	Utility util;
	LinearLayout linearCreateProfil, headerLinear, footerLinear;
	RelativeLayout NetworkSocial, DownloadLink;
	LinearLayout.LayoutParams profilParams, headerParams, footerParams;
	DialogNetworkSocial dialognetwork;
	DialogLinkDownload dialogDownload;

	public String Lfacebook, Llinkedin, Ltwitter, Lwebsite, Lgoogle,
			Linstagram;
	public String Lcatalog, Lprice, Lpdf, Lvideo;

	// EditText inFacebook, inLinkedin, inTwiiter, inWebsite, inGoogle,
	// inInstagram;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_create_introduction,
				null);

		DBAdapter = new DataBaseAdapter(getActivity());
		util = new Utility(getActivity());

		NetworkSocial = (RelativeLayout) view.findViewById(R.id.editNetwork);
		DownloadLink = (RelativeLayout) view.findViewById(R.id.editdownload);

		btnSave = (ImageButton) view.findViewById(R.id.btnsave);
		btnProfile = (ImageButton) view.findViewById(R.id.profile_img);
		btnHeader = (ImageButton) view.findViewById(R.id.imgvadvertise_Object);
		btnFooter = (ImageButton) view.findViewById(R.id.imgvadvertise2_Object);

		phoneEnter = (EditText) view.findViewById(R.id.editTextphone);
		faxEnter = (EditText) view.findViewById(R.id.editTextfax);
		mobileEnter = (EditText) view.findViewById(R.id.editTextmob);
		emailEnter = (EditText) view.findViewById(R.id.editTextemail);
		addressEnter = (EditText) view.findViewById(R.id.editTextaddres);

		SharedPreferences sendDataID = getActivity().getSharedPreferences("Id",
				0);
		final int id = sendDataID.getInt("main_Id", -1);

		Toast.makeText(getActivity(), id + "", Toast.LENGTH_SHORT).show();

		linearCreateProfil = (LinearLayout) view
				.findViewById(R.id.linearCreateProfil);

		headerLinear = (LinearLayout) view.findViewById(R.id.headerLinear);
		footerLinear = (LinearLayout) view.findViewById(R.id.footerlinears);

		profilParams = new LinearLayout.LayoutParams(
				linearCreateProfil.getLayoutParams());
		profilParams.width = util.getScreenwidth() / 5;
		profilParams.height = util.getScreenwidth() / 5;

		headerParams = new LinearLayout.LayoutParams(
				headerLinear.getLayoutParams());
		headerParams.height = util.getScreenHeight() / 3;

		footerParams = new LinearLayout.LayoutParams(
				footerLinear.getLayoutParams());
		footerParams.width = util.getScreenwidth();
		footerParams.height = util.getScreenHeight() / 3;

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
		// btnSave.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// phoneValue = phoneEnter.getText().toString();
		// faxValue = faxEnter.getText().toString();
		// mobileValue = mobileEnter.getText().toString();
		// emailValue = emailEnter.getText().toString();
		// addressValue = addressEnter.getText().toString();
		//
		// DBAdapter.open();
		// DBAdapter.UpdateObjectProperties(id, phoneValue, faxValue,
		// mobileValue, emailValue, addressValue);
		// DBAdapter.close();
		// Toast.makeText(getActivity(), "تغییرات با موفقیت ذخیره شد",
		// Toast.LENGTH_SHORT).show();
		// getActivity().getSupportFragmentManager().popBackStack();
		// }
		// });

		return view;
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
		}

	}

}
