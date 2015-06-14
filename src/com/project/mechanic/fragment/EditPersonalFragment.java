package com.project.mechanic.fragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
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
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mechanic.R;
import com.project.mechanic.adapter.CropingOptionAdapter;
import com.project.mechanic.entity.CropingOption;
import com.project.mechanic.entity.Users;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.ServiceComm;
import com.project.mechanic.utility.Utility;

public class EditPersonalFragment extends Fragment {
	private static final int CAMERA_CODE = 101, GALLERY_CODE = 201,
			CROPING_CODE = 301;
	protected static final int RESULT_LOAD_IMAGE = 1;
	DataBaseAdapter dbAdapter;
	ServiceComm service;
	ImageView img2, imagecamera;
	LinearLayout.LayoutParams lp2;
	Utility ut;
	int id;
	private Uri mImageCaptureUri;
	private File outPutFile = null;

	public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 0, outputStream);
		return outputStream.toByteArray();
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View view = inflater
				.inflate(R.layout.fragment_editpersonal, null);
		outPutFile = new File(
				android.os.Environment.getExternalStorageDirectory(),
				"temp.jpg");
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
		img2 = (ImageView) view.findViewById(R.id.imgp);
		// imagecamera=(ImageView) view.findViewById(R.id.imagcamera);
		Button btnregedit = (Button) view.findViewById(R.id.btnregedit);
		Button btnback = (Button) view.findViewById(R.id.btnbackdisplay);

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

		btnback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

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
				// Intent i = new Intent(
				// Intent.ACTION_PICK,
				// android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				//
				// getActivity().startActivityFromFragment(
				// EditPersonalFragment.this, i, RESULT_LOAD_IMAGE);
				selectImageOption();
			}
		});

		return view;

	}

	// public void onActivityResult(int requestCode, int resultCode, Intent
	// data) {
	//
	// super.onActivityResult(requestCode, resultCode, data);
	//
	// if (requestCode == RESULT_LOAD_IMAGE
	// && resultCode == Activity.RESULT_OK && null != data) {
	// Uri selectedImage = data.getData();
	// String[] filePathColumn = { MediaStore.Images.Media.DATA };
	//
	// Cursor cursor = getActivity().getContentResolver().query(
	// selectedImage, filePathColumn, null, null, null);
	// cursor.moveToFirst();
	//
	// int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	// String picturePath = cursor.getString(columnIndex);
	// cursor.close();
	//
	// // ImageView btnaddpic1 = (ImageView) view
	// // .findViewById(R.id.btnaddpic);
	// img2.setImageBitmap(BitmapFactory.decodeFile(picturePath));
	// img2.setBackgroundColor(getResources().getColor(
	// android.R.color.transparent));
	// img2.setLayoutParams(lp2);
	// }
	//
	// }

	private void selectImageOption() {
		final CharSequence[] items = { "Capture Photo", "Choose from Gallery",
				"Cancel" };

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Add Photo!");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {

				if (items[item].equals("Capture Photo")) {

					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					File f = new File(android.os.Environment
							.getExternalStorageDirectory(), "temp1.jpg");
					mImageCaptureUri = Uri.fromFile(f);
					intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
					startActivityForResult(intent, CAMERA_CODE);

				} else if (items[item].equals("Choose from Gallery")) {

					// Intent i = new Intent(
					// Intent.ACTION_PICK,
					// android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

					Intent intent = new Intent();
					// call android default gallery
					intent.setType("image/*");
					intent.setAction(Intent.ACTION_GET_CONTENT);
					// ******** code for crop image
					// intent.putExtra("crop", "true");
					// intent.putExtra("aspectX", 1);
					// intent.putExtra("aspectY", 1);
					// intent.putExtra("outputX", 128);
					// intent.putExtra("outputY", 128);

					// getActivity().startActivityForResult(intent,
					// GALLERY_CODE);

					try {
						intent.putExtra("return-data", true);
						startActivityForResult(Intent.createChooser(intent,
								"Complete action using"), GALLERY_CODE);
					} catch (ActivityNotFoundException e) {
					}

				} else if (items[item].equals("Cancel")) {
					dialog.dismiss();
				}
			}
		});
		builder.show();
	}

	// ////////////////////////////////////////////////////////

	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == GALLERY_CODE && null != data) {

			mImageCaptureUri = data.getData();
			System.out.println("Gallery Image URI : " + mImageCaptureUri);
			CropingIMG();

		} else if (requestCode == CAMERA_CODE
				&& resultCode == Activity.RESULT_OK) {

			System.out.println("Camera Image URI : " + mImageCaptureUri);
			CropingIMG();
		} else if (requestCode == CROPING_CODE) {

			try {
				if (outPutFile.exists()) {
					Bitmap photo = decodeFile(outPutFile);
					img2.setImageBitmap(photo);
					img2.setLayoutParams(lp2);
					img2.setScaleType(ScaleType.FIT_XY);
				} else {
					Toast.makeText(getActivity().getApplicationContext(),
							"Error while save image", Toast.LENGTH_SHORT)
							.show();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void CropingIMG() {

		final ArrayList cropOptions = new ArrayList();

		final Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setType("image/*");

		ArrayList<ResolveInfo> list = (ArrayList<ResolveInfo>) getActivity()
				.getPackageManager().queryIntentActivities(intent, 0);
		int size = list.size();
		if (size == 0) {
			// Toast.makeText(this, "Cann't find image croping app",
			// Toast.LENGTH_SHORT).show();
			return;
		} else {
			intent.setData(mImageCaptureUri);
			intent.putExtra("outputX", 512);
			intent.putExtra("outputY", 512);
			intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1);
			intent.putExtra("scale", true);

			// TODO: don't use return-data tag because it's not return large
			// image data and crash not given any message
			// intent.putExtra("return-data", true);

			// Create output file here
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outPutFile));

			if (size == 1) {
				Intent i = new Intent(intent);
				ResolveInfo res = list.get(0);

				i.setComponent(new ComponentName(res.activityInfo.packageName,
						res.activityInfo.name));

				startActivityForResult(i, CROPING_CODE);
			} else {
				for (ResolveInfo res : list) {
					final CropingOption co = new CropingOption();
					PackageManager pm = getActivity().getPackageManager();
					co.title = pm
							.getApplicationLabel(res.activityInfo.applicationInfo);
					co.icon = pm
							.getApplicationIcon(res.activityInfo.applicationInfo);
					co.appIntent = new Intent(intent);
					co.appIntent
							.setComponent(new ComponentName(
									res.activityInfo.packageName,
									res.activityInfo.name));
					cropOptions.add(co);
				}

				CropingOptionAdapter adapter = new CropingOptionAdapter(
						getActivity().getApplicationContext(), cropOptions);

				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				builder.setTitle("Choose Croping App");
				builder.setCancelable(false);
				builder.setAdapter(adapter,
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int itemc) {

								final CropingOption co = new CropingOption();
								co.appIntent = new Intent(intent);
								startActivityForResult(co.appIntent,
										CROPING_CODE);
							}
						});

				builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {

						if (mImageCaptureUri != null) {
							getActivity().getContentResolver().delete(
									mImageCaptureUri, null, null);
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
			// decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);

			// Find the correct scale value. It should be the power of 2.
			final int REQUIRED_SIZE = 512;
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;
			while (true) {
				if (width_tmp / 2 < REQUIRED_SIZE
						|| height_tmp / 2 < REQUIRED_SIZE)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale *= 2;
			}

			// decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
		} catch (FileNotFoundException e) {
		}
		return null;
	}

}
