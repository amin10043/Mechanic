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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.project.mechanic.R;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class IntroductionEditFragment extends Fragment {
	private static int headerLoadCode = 1;
	private static int profileLoadCode = 2;
	private static int footerLoadcode = 3;

	DataBaseAdapter DBAdapter;
	Utility util;

	DialogEditDownloadIntroduction diadown;
	DialogEditNet dianet;
	ImageButton btnSave;
	String nameValue, phoneValue, faxValue, mobileValue, emailValue,
			addressValue, descriptionValue;
	EditText nameEnter, phoneEnter, faxEnter, mobileEnter, emailEnter,
			addressEnter, descriptionEnter;
	ImageView headerEdit, profileEdit, footerEdit;
	LinearLayout.LayoutParams headerEditParams, profileEditParams,
			footerEditParams, editnameParams;

	RelativeLayout editnetLink, editDNlink, namayendegi, khadamat;

	LinearLayout Lheader, Lpro, Lfooter;

	public String Dcatalog, Dprice, Dpdf, Dvideo;
	public String Dface, Dlink, Dtwt, Dweb, Dgoogle, Dinstagram;

	Bitmap bmpHeader, bmpProfil, bmpFooter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_introduction_edit, null);

		DBAdapter = new DataBaseAdapter(getActivity());
		util = new Utility(getActivity());

		btnSave = (ImageButton) view.findViewById(R.id.btnsave);
		headerEdit = (ImageView) view.findViewById(R.id.hh1);
		profileEdit = (ImageView) view.findViewById(R.id.pp1);
		footerEdit = (ImageView) view.findViewById(R.id.ft1);

		nameEnter = (EditText) view.findViewById(R.id.nameEdit);
		phoneEnter = (EditText) view.findViewById(R.id.editTextphone);
		faxEnter = (EditText) view.findViewById(R.id.editTextfax);
		mobileEnter = (EditText) view.findViewById(R.id.editTextmob);
		emailEnter = (EditText) view.findViewById(R.id.editTextemail);
		addressEnter = (EditText) view.findViewById(R.id.editTextaddres);
		descriptionEnter = (EditText) view
				.findViewById(R.id.descriptionpageedit);

		editnetLink = (RelativeLayout) view.findViewById(R.id.editpageNetwork);
		editDNlink = (RelativeLayout) view.findViewById(R.id.editpagedownload);

		namayendegi = (RelativeLayout) view.findViewById(R.id.Layoutlink1);
		khadamat = (RelativeLayout) view.findViewById(R.id.Layoutlink2);

		namayendegi.setVisibility(View.GONE);
		khadamat.setVisibility(View.GONE);

		Lheader = (LinearLayout) view.findViewById(R.id.headerLinearPage);
		Lpro = (LinearLayout) view.findViewById(R.id.linearEditProfil);
		Lfooter = (LinearLayout) view.findViewById(R.id.linearfooteredit);

		headerEditParams = new LinearLayout.LayoutParams(
				Lheader.getLayoutParams());
		headerEditParams.height = util.getScreenHeight() / 3;
		Lheader.setPadding(0, 0, 0, 20);

		profileEditParams = new LinearLayout.LayoutParams(
				Lpro.getLayoutParams());
		profileEditParams.width = util.getScreenwidth() / 5;
		profileEditParams.height = util.getScreenwidth() / 5;

		footerEditParams = new LinearLayout.LayoutParams(
				Lfooter.getLayoutParams());
		footerEditParams.height = util.getScreenHeight() / 3;

		editnameParams = new LinearLayout.LayoutParams(Lpro.getLayoutParams());
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

		SharedPreferences sendDataID = getActivity().getSharedPreferences("Id",
				0);
		final int id = sendDataID.getInt("main_Id", -1);

		Toast.makeText(getActivity(), id + "", Toast.LENGTH_SHORT).show();

		headerEdit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				getActivity().startActivityFromFragment(
						IntroductionEditFragment.this, i, headerLoadCode);

			}
		});

		profileEdit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				getActivity().startActivityFromFragment(
						IntroductionEditFragment.this, i, profileLoadCode);

			}
		});
		footerEdit.setOnClickListener(new OnClickListener() {

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

		bmpHeader = ((BitmapDrawable) headerEdit.getDrawable()).getBitmap();
		bmpProfil = ((BitmapDrawable) profileEdit.getDrawable()).getBitmap();
		bmpFooter = ((BitmapDrawable) footerEdit.getDrawable()).getBitmap();

		if (bmpHeader == null & bmpProfil == null & bmpFooter == null)

			Toast.makeText(getActivity(), "Empty ByteArray", Toast.LENGTH_SHORT)
					.show();

		final byte[] byteHeader = getBitmapAsByteArray(bmpHeader);
		final byte[] byteProfil = getBitmapAsByteArray(bmpProfil);
		final byte[] byteFooter = getBitmapAsByteArray(bmpFooter);

		btnSave.setOnClickListener(new OnClickListener() {

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

					DBAdapter.open();
					DBAdapter.UpdateObjectProperties(id, nameValue, phoneValue,
							emailValue, faxValue, descriptionValue, byteHeader,
							byteProfil, byteFooter, Dcatalog, Dprice, Dpdf,
							Dvideo, addressValue, mobileValue, Dface,
							Dinstagram, Dlink, Dgoogle, Dweb, Dtwt);
					DBAdapter.close();

					Toast.makeText(getActivity(), "تغییرات با موفقیت ذخیره شد",
							Toast.LENGTH_SHORT).show();

					getActivity().getSupportFragmentManager().popBackStack();
				}
			}
		});

		return view;
	}

	public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 0, outputStream);
		return outputStream.toByteArray();
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == headerLoadCode && resultCode == Activity.RESULT_OK
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
			headerEdit.setImageBitmap(BitmapFactory.decodeFile(picturePath));
			headerEdit.setBackgroundColor(getResources().getColor(
					android.R.color.transparent));
			headerEdit.setLayoutParams(headerEditParams);

		}
		if (requestCode == profileLoadCode && resultCode == Activity.RESULT_OK
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
			profileEdit.setImageBitmap(BitmapFactory.decodeFile(picturePath));
			profileEdit.setBackgroundColor(getResources().getColor(
					android.R.color.transparent));
			profileEdit.setLayoutParams(profileEditParams);

		}
		if (requestCode == footerLoadcode && resultCode == Activity.RESULT_OK
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
			footerEdit.setImageBitmap(BitmapFactory.decodeFile(picturePath));
			footerEdit.setBackgroundColor(getResources().getColor(
					android.R.color.transparent));
			footerEdit.setLayoutParams(footerEditParams);

		}
	}
}
