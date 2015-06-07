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
import com.project.mechanic.entity.Object;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class IntroductionEditFragment extends Fragment {
	private static int headerLoadCode = 1;
	private static int profileLoadCode = 2;
	private static int footerLoadcode = 3;

	DataBaseAdapter DBAdapter;
	Utility util;

	Object object;

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

	RelativeLayout editnetLink, editDNlink, namayendegi, khadamat,
			Linearprofile;

	LinearLayout Linearheader, Linearfooter;

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

		editnetLink = (RelativeLayout) view.findViewById(R.id.editpageNetwork);
		editDNlink = (RelativeLayout) view.findViewById(R.id.editpagedownload);
		namayendegi = (RelativeLayout) view.findViewById(R.id.Layoutlink1);
		khadamat = (RelativeLayout) view.findViewById(R.id.Layoutlink2);

		namayendegi.setVisibility(View.GONE);
		khadamat.setVisibility(View.GONE);

		Linearheader = (LinearLayout) view.findViewById(R.id.headerLinearPage);
		Linearprofile = (RelativeLayout) view
				.findViewById(R.id.linearEditProfil);
		Linearfooter = (LinearLayout) view.findViewById(R.id.linearfooteredit);

		headerEditParams = new LinearLayout.LayoutParams(
				Linearheader.getLayoutParams());
		headerEditParams.height = (int) (util.getScreenHeight() / 3);

		profileEditParams = new RelativeLayout.LayoutParams(
				Linearprofile.getLayoutParams());
		profileEditParams.width = util.getScreenwidth() / 8;
		profileEditParams.height = util.getScreenwidth() / 8;
		profileEditParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

		footerEditParams = new LinearLayout.LayoutParams(
				Linearfooter.getLayoutParams());
		footerEditParams.height = util.getScreenHeight() / 3;

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

		SharedPreferences sendDataID = getActivity().getSharedPreferences("Id",
				0);
		final int id = sendDataID.getInt("main_Id", -1);

		Toast.makeText(getActivity(), "introduction id =" + id,
				Toast.LENGTH_SHORT).show();

		// /////////display information///////////////////////

		// SharedPreferences sendDataID1 = getActivity().getSharedPreferences(
		// "Id", 0);
		// final int cid = sendDataID1.getInt("main_Id", -1);
		DBAdapter.open();
		object = DBAdapter.getObjectbyid(id);

		byte[] bitmapbyte = object.getImage2();
		if (bitmapbyte != null) {
			Bitmap bmp = BitmapFactory.decodeByteArray(bitmapbyte, 0,
					bitmapbyte.length);
			profileImageEdit.setImageBitmap(bmp);
		} else
			profileImageEdit.setImageResource(R.drawable.no_img_profile);

		byte[] bitmap = object.getImage1();
		if (bitmap != null) {
			Bitmap bmp = BitmapFactory
					.decodeByteArray(bitmap, 0, bitmap.length);
			headerImageEdit.setImageBitmap(bmp);
		} else
			headerImageEdit.setImageResource(R.drawable.no_image_header);

		byte[] bitm = object.getImage3();
		if (bitm != null) {
			Bitmap bmp = BitmapFactory.decodeByteArray(bitm, 0, bitm.length);
			footerImageEdit.setImageBitmap(bmp);
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

			@Override
			public void onClick(View arg0) {

				bmpHeader = ((BitmapDrawable) headerImageEdit.getDrawable())
						.getBitmap();
				bmpProfil = ((BitmapDrawable) profileImageEdit.getDrawable())
						.getBitmap();
				bmpFooter = ((BitmapDrawable) footerImageEdit.getDrawable())
						.getBitmap();

				final byte[] byteHeader = getBitmapAsByteArray(bmpHeader);
				final byte[] byteProfil = getBitmapAsByteArray(bmpProfil);
				final byte[] byteFooter = getBitmapAsByteArray(bmpFooter);

				if (headerImageEdit.getDrawable() == null
						&& profileImageEdit.getDrawable() == null
						&& footerImageEdit.getDrawable() == null) {

					Toast.makeText(getActivity(), "Empty ByteArray",
							Toast.LENGTH_SHORT).show();
				}

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
							byteProfil, byteFooter,

							Dcatalog, Dprice, Dpdf, Dvideo, addressValue,
							mobileValue, Dface, Dinstagram, Dlink, Dgoogle,
							Dweb, Dtwt);

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
		byte[] array = outputStream.toByteArray();
		BitmapFactory.decodeByteArray(array, 0, array.length);
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
			headerImageEdit.setImageBitmap(BitmapFactory
					.decodeFile(picturePath));
			headerImageEdit.setBackgroundColor(getResources().getColor(
					android.R.color.transparent));
			headerImageEdit.setLayoutParams(headerEditParams);

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
			profileImageEdit.setImageBitmap(BitmapFactory
					.decodeFile(picturePath));
			profileImageEdit.setBackgroundColor(getResources().getColor(
					android.R.color.transparent));
			profileImageEdit.setLayoutParams(profileEditParams);

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
			footerImageEdit.setImageBitmap(BitmapFactory
					.decodeFile(picturePath));
			footerImageEdit.setBackgroundColor(getResources().getColor(
					android.R.color.transparent));
			footerImageEdit.setLayoutParams(footerEditParams);

		}
	}

}
