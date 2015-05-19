package com.project.mechanic.fragment;

import java.io.ByteArrayOutputStream;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mechanic.R;
import com.project.mechanic.entity.Users;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.ServiceComm;
import com.project.mechanic.utility.Utility;

public class EditPersonalFragment extends Fragment {

	protected static final int RESULT_LOAD_IMAGE = 1;
	DataBaseAdapter dbAdapter;
	ServiceComm service;
	ImageView img2, imagecamera;
	LinearLayout.LayoutParams lp2;
	Utility ut;
	int id;

	public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 0, outputStream);
		return outputStream.toByteArray();
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View view = inflater
				.inflate(R.layout.fragment_editpersonal, null);

		service = new ServiceComm(getActivity());
		ut = new Utility(getActivity());
		final EditText txtaddress = (EditText) view
				.findViewById(R.id.etxtaddress);
		final EditText txtcellphone = (EditText) view
				.findViewById(R.id.etxtcellphone);
		final EditText txtphone = (EditText) view.findViewById(R.id.etxtphone);
		final EditText txtemail = (EditText) view.findViewById(R.id.etxtemail);
		final TextView txtname = (TextView) view.findViewById(R.id.etxtname);
		final EditText txtfax = (EditText) view.findViewById(R.id.etxtfax);
		// img2=(ImageView) view.findViewById(R.id.imgp);
		// imagecamera=(ImageView) view.findViewById(R.id.imagcamera);
		Button btnregedit = (Button) view.findViewById(R.id.btnregedit);

		LinearLayout lin3 = (LinearLayout) view.findViewById(R.id.lin5);

		lp2 = new LinearLayout.LayoutParams(lin3.getLayoutParams());

		lp2.height = ut.getScreenwidth() / 4;
		lp2.width = ut.getScreenwidth() / 4;

		img2.setLayoutParams(lp2);
		dbAdapter = new DataBaseAdapter(getActivity());
		dbAdapter.open();
		// /

		Users u = ut.getCurrentUser();
		id = u.getId();
		byte[] bitmapbyte = u.getImage();
		if (bitmapbyte != null) {
			Bitmap bmp = BitmapFactory.decodeByteArray(bitmapbyte, 0,
					bitmapbyte.length);
			img2.setImageBitmap(bmp);
		}
		String name = u.getName();
		String email = u.getEmail();
		String address = u.getAddress();
		String phone = u.getPhonenumber();
		String cellphone = u.getMobailenumber();
		String fax = u.getFaxnumber();

		// //////////

		// final int id =1;
		// Users x =dbAdapter.getUserById(id);
		// byte[] bitmapbyte = x.getImage();
		// if (bitmapbyte != null) {
		// Bitmap bmp = BitmapFactory.decodeByteArray(bitmapbyte, 0,
		// bitmapbyte.length);
		// img2.setImageBitmap(bmp);
		// }

		int item = u.getId();
		// String name=x.getName();
		// String email=x.getEmail();
		// String address=x.getAddress();
		// String phone=x.getPhonenumber();
		// String cellphone=x.getMobailenumber();
		// String fax=x.getFaxnumber();
		//

		dbAdapter.close();

		txtname.setText(name);
		txtemail.setText(email);
		txtcellphone.setText(cellphone);
		txtphone.setText(phone);
		txtfax.setText(fax);
		txtaddress.setText(address);
		// picture.setImageURI(uri);

		btnregedit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				String Name = txtname.getText().toString();
				String Address = txtaddress.getText().toString();
				String Cellphone = txtcellphone.getText().toString();
				String Phone = txtphone.getText().toString();
				String Email = txtemail.getText().toString();
				String Fax = txtfax.getText().toString();

				//
				Bitmap bitmap = ((BitmapDrawable) img2.getDrawable())
						.getBitmap();

				// Bitmap emptyBitmap = Bitmap.createBitmap(bitmap.getWidth(),
				// bitmap.getHeight(), bitmap.getConfig());

				dbAdapter = new DataBaseAdapter(getActivity());
				dbAdapter.open();
				byte[] Image = getBitmapAsByteArray(bitmap);

				if (img2.getDrawable() == null) {
					Toast.makeText(getActivity(), "null", Toast.LENGTH_SHORT);
				} else {

					Toast.makeText(getActivity(), "notnull", Toast.LENGTH_SHORT);

				}

				dbAdapter.UpdateAllUserToDb(id, Email, null, Phone, Cellphone,
						Fax, Address, Image);

				dbAdapter.close();
				FragmentTransaction trans = getActivity()
						.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame,
						new DisplayPersonalInformationFragment());
				trans.commit();

			}
		});

		img2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				getActivity().startActivityFromFragment(
						EditPersonalFragment.this, i, RESULT_LOAD_IMAGE);
			}
		});

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
			img2.setImageBitmap(BitmapFactory.decodeFile(picturePath));
			img2.setBackgroundColor(getResources().getColor(
					android.R.color.transparent));
			img2.setLayoutParams(lp2);
		}

	}

}
