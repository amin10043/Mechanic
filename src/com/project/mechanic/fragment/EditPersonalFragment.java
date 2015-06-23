package com.project.mechanic.fragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
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
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.inter.SaveAsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.Saving;
import com.project.mechanic.service.SavingImage;
import com.project.mechanic.utility.ServiceComm;
import com.project.mechanic.utility.Utility;

public class EditPersonalFragment extends Fragment implements AsyncInterface,
		SaveAsyncInterface {
	private static final int CAMERA_CODE = 101, GALLERY_CODE = 201,
			CROPING_CODE = 301;
	protected static final int RESULT_LOAD_IMAGE = 1;
	DataBaseAdapter dbAdapter;
	ServiceComm service;
	ImageView img2, imagecamera;
	Utility util;
	LinearLayout.LayoutParams lp2;
	Utility ut;
	int id;
	Saving saving;
	private Uri mImageCaptureUri;
	private File outPutFile = null;
	Map<String, String> params;
	Map<String, Object> imageParams;
	ProgressDialog dialog;
	SavingImage saveImage;
	String Address;
	String Cellphone;
	String Phone;
	String Email;
	String Fax;
	Context context;
	int gId;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View view = inflater
				.inflate(R.layout.fragment_editpersonal, null);
		outPutFile = new File(
				android.os.Environment.getExternalStorageDirectory(),
				"temp.jpg");

		service = new ServiceComm(getActivity());
		context = getActivity();

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
		Button btnregedit = (Button) view.findViewById(R.id.btnregedit);
		Button btnback = (Button) view.findViewById(R.id.btnbackdisplay);
		LinearLayout lin3 = (LinearLayout) view.findViewById(R.id.lin5);
		lp2 = new LinearLayout.LayoutParams(lin3.getLayoutParams());
		lp2.height = ut.getScreenwidth() / 4;
		lp2.width = ut.getScreenwidth() / 4;
		img2.setLayoutParams(lp2);
		dbAdapter = new DataBaseAdapter(getActivity());

		Users u = ut.getCurrentUser();
		gId = u.getId();
		id = u.getId();
		byte[] bitmapbyte = u.getImage();
		if (bitmapbyte != null) {
			Bitmap bmp = BitmapFactory.decodeByteArray(bitmapbyte, 0,
					bitmapbyte.length);
			img2.setImageBitmap(bmp);
		}

		txtname.setText(u.getName());
		txtemail.setText(u.getEmail());
		txtcellphone.setText(u.getMobailenumber());
		txtphone.setText(u.getPhonenumber());
		txtfax.setText(u.getFaxnumber());
		txtaddress.setText(u.getAddress());
		btnregedit.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void onClick(View arg0) {

				dialog = ProgressDialog.show(getActivity(), "در حال بروزرسانی",
						"لطفا منتظر بمانید...");

				Address = txtaddress.getText().toString();
				Cellphone = txtcellphone.getText().toString();
				Phone = txtphone.getText().toString();
				Email = txtemail.getText().toString();
				Fax = txtfax.getText().toString();

				saving = new Saving(getActivity());
				saving.delegate = EditPersonalFragment.this;
				params = new LinkedHashMap<String, String>();
				params.put("tableName", "Users");
				params.put("Email", Email);
				params.put("Phonenumber", Cellphone);
				params.put("Faxnumber", Fax);
				params.put("Address", Address);
				params.put("IsUpdate", "1");
				params.put("Id", String.valueOf(id));
				saving.execute(params);

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
				selectImageOption();
			}
		});

		return view;

	}

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

					Intent intent = new Intent();
					intent.setType("image/*");
					intent.setAction(Intent.ACTION_GET_CONTENT);
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
					// img2.setLayoutParams(lp2);
					img2.setAdjustViewBounds(true);

					// img2.setMaxHeight(maxHeight);
					img2.setScaleType(ScaleType.CENTER_INSIDE);
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

		final ArrayList<CropingOption> cropOptions = new ArrayList<CropingOption>();

		final Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setType("image/*");

		ArrayList<ResolveInfo> list = (ArrayList<ResolveInfo>) getActivity()
				.getPackageManager().queryIntentActivities(intent, 0);
		int size = list.size();
		if (size == 0) {
			return;
		} else {
			intent.setData(mImageCaptureUri);
			intent.putExtra("outputX", 512);
			intent.putExtra("outputY", 512);
			intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1);
			intent.putExtra("scale", true);
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
								startActivityForResult(
										cropOptions.get(itemc).appIntent,
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
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);
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
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
		} catch (FileNotFoundException e) {
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void processFinish(String output) {
		int id = -1;
		try {
			id = Integer.valueOf(output);

			// id > 0 -> updating

			Bitmap bitmap = ((BitmapDrawable) img2.getDrawable()).getBitmap();

			Bitmap emptyBitmap = Bitmap.createBitmap(bitmap.getWidth(),
					bitmap.getHeight(), bitmap.getConfig());
			byte[] Image = null;

			if (!emptyBitmap.equals(bitmap)) {
				Image = Utility.CompressBitmap(bitmap);
			}

			if (context != null) {
				saveImage = new SavingImage(context);
				saveImage.delegate = this;
				imageParams = new LinkedHashMap<String, Object>();
				imageParams.put("tableName", "Users");
				imageParams.put("fieldName", "Image");
				imageParams.put("id", gId);
				imageParams.put("image", Image);

				saveImage.execute(imageParams);
			} else {
				dbAdapter.open();
				dbAdapter.UpdateAllUserToDbNoPic(id, Email, null, Phone,
						Cellphone, Fax, Address);

				dbAdapter.close();

			}

		} catch (NumberFormatException ex) {
			Toast.makeText(context, "خطا در بروز رسانی", Toast.LENGTH_SHORT)
					.show();
		}
	}

	@Override
	public void processFinishSaveImage(String output) {
		if (dialog != null) {
			dialog.dismiss();
		}
		if (output != null && "".equals(output)) {
			try {
				id = Integer.valueOf(output);

				Bitmap bitmap = ((BitmapDrawable) img2.getDrawable())
						.getBitmap();

				Bitmap emptyBitmap = Bitmap.createBitmap(bitmap.getWidth(),
						bitmap.getHeight(), bitmap.getConfig());
				byte[] Image = null;

				if (!emptyBitmap.equals(bitmap)) {
					Image = Utility.CompressBitmap(bitmap);
				}
				dbAdapter.open();
				dbAdapter.UpdateAllUserToDb(gId, Email, null, Phone, Cellphone,
						Fax, Address, Image);

				dbAdapter.close();

			} catch (NumberFormatException ex) {
				Toast.makeText(context, "  خطا در بروز رسانی تصویر",
						Toast.LENGTH_SHORT).show();
			}
		}
	}
}