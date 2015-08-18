package com.project.mechanic.fragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.crop.CropImage;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.inter.SaveAsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.Saving;
import com.project.mechanic.service.SavingImage;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.utility.Utility;

public class show_pay_fragment extends Fragment implements AsyncInterface,
		SaveAsyncInterface {

	private static int RESULT_LOAD_IMAGE = 1;
	ImageView img_pay;
	Spinner sp_pay;
	DataBaseAdapter dbAdapter;
	int proID;
	int id;
	int i;
	int T;
	int a = 0;
	Utility util;
	LinearLayout.LayoutParams headerEditParams;
	LinearLayout Lheader;
	private File mFileTemp;
	private Uri picUri;
	ProgressDialog imageloadprogressdialog;
	private static final int PICK_FROM_CAMERA = 2;
	final int PIC_CROP = 3;

	String serverDate = "";
	ServerDate server;

	SavingImage saveImage;
	Map<String, Object> imageParams;
	ProgressDialog ringProgressDialog;
	Saving saving;
	Map<String, String> params;

	// public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
	// ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	// bitmap.compress(CompressFormat.PNG, 0, outputStream);
	// return outputStream.toByteArray();
	// }

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		((MainActivity) getActivity()).setActivityTitle(R.string.showad);
		i = Integer.valueOf(getArguments().getString("I"));

		View view = inflater.inflate(R.layout.fragment_pay, null);
		Lheader = (LinearLayout) view.findViewById(R.id.linImg);
		img_pay = (ImageView) view.findViewById(R.id.img_pay);
		sp_pay = (Spinner) view.findViewById(R.id.sp_pay);
		LinearLayout btn_pay = (LinearLayout) view.findViewById(R.id.btn_pay);

		dbAdapter = new DataBaseAdapter(getActivity());
		util = new Utility(getActivity());
		headerEditParams = new LinearLayout.LayoutParams(
				Lheader.getLayoutParams());
		headerEditParams.height = util.getScreenHeight() / 3;
		headerEditParams.width = util.getScreenHeight() / 3;
		img_pay.setLayoutParams(headerEditParams);

		dbAdapter.open();
		List<String> mylist = dbAdapter.getAllObjectname();

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_item, mylist);

		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		sp_pay.setAdapter(dataAdapter);

		dbAdapter.close();

		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			mFileTemp = new File(Environment.getExternalStorageDirectory(),
					AnadFragment.TEMP_PHOTO_FILE_NAME);
		} else {
			mFileTemp = new File(getActivity().getFilesDir(),
					AnadFragment.TEMP_PHOTO_FILE_NAME);
		}

		btn_pay.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// String date = new SimpleDateFormat("yyyy-MM-dd")
				// .format(new Date());
				SharedPreferences sendIdpro = getActivity()
						.getSharedPreferences("Id", 0);
				id = sendIdpro.getInt("main_Id", 0);
				Toast.makeText(getActivity(), i + "do", Toast.LENGTH_SHORT)
						.show();

				// ************شرط پرداخت اینترنتی باید ذکر شود*************
				// ************شرط پرداخت اینترنتی باید ذکر شود*************
				// ************شرط پرداخت اینترنتی باید ذکر شود*************
				// ************شرط پرداخت اینترنتی باید ذکر شود*************
				// ************شرط پرداخت اینترنتی باید ذکر شود*************
				// ************شرط پرداخت اینترنتی باید ذکر شود*************
				// ************شرط پرداخت اینترنتی باید ذکر شود*************
				// ************شرط پرداخت اینترنتی باید ذکر شود*************

				// start syncing

				server = new ServerDate(getActivity());
				server.delegate = show_pay_fragment.this;
				server.execute("");

			}
		});

		img_pay.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				final CharSequence[] items = { "گالری تصاویر", "دوربین" };
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				builder.setItems(items, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						if (item == 0) {
							do_gallery_work();
						} else {
							do_cam_work();
						}
					}
				});
				builder.show();

			}
		});

		return view;
	}

	public void do_cam_work() {
		imageloadprogressdialog = ProgressDialog.show(getActivity(), "",
				"در حال باز کردن دوربین. لطفا منتظر بمانید...");
		try {
			Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(captureIntent, PICK_FROM_CAMERA);
		} catch (ActivityNotFoundException anfe) {
			Toast toast = Toast.makeText(getActivity(),
					"این دستگاه از برش تصویر پشتیبانی نمی کند.",
					Toast.LENGTH_SHORT);
			toast.show();
		}

	}

	public void do_gallery_work() {
		Intent i = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

		startActivityForResult(i, RESULT_LOAD_IMAGE);
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
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode != Activity.RESULT_OK) {
			return;
		}
		if (requestCode == PICK_FROM_CAMERA) {
			picUri = data.getData();
			try {
				Intent cropIntent = new Intent("com.android.camera.action.CROP");
				cropIntent.setDataAndType(picUri, "image/*");
				cropIntent.putExtra("scale", "true");
				cropIntent.putExtra("aspectX", 1);
				cropIntent.putExtra("aspectY", 1);
				cropIntent.putExtra("outputX", 256);
				cropIntent.putExtra("outputY", 256);
				cropIntent.putExtra("return-data", true);
				Bundle extras = data.getExtras();
				Bitmap thePic = extras.getParcelable("data");
				img_pay.setImageBitmap(thePic);
				if (imageloadprogressdialog != null)
					imageloadprogressdialog.dismiss();
			} catch (ActivityNotFoundException anfe) {
				Toast toast = Toast.makeText(getActivity(),
						"این دستگاه از برش تصویر پشتیبانی نمی کند.",
						Toast.LENGTH_SHORT);
				toast.show();
			}

		}

		else if (requestCode == RESULT_LOAD_IMAGE) {
			try {

				InputStream inputStream = getActivity().getContentResolver()
						.openInputStream(data.getData());
				FileOutputStream fileOutputStream = new FileOutputStream(
						mFileTemp);
				Utility.copyStream(inputStream, fileOutputStream);
				fileOutputStream.close();
				inputStream.close();

				startCropImage();

			} catch (Exception e) {

				Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG)
						.show();
			}

		} else if (requestCode == PIC_CROP) {
			String path = data.getStringExtra(CropImage.IMAGE_PATH);
			if (path == null) {
				return;
			}

			Bitmap bitmap = BitmapFactory.decodeFile(mFileTemp.getPath());
			img_pay.setImageBitmap(bitmap);
		} else {
			Toast.makeText(getActivity(), "Picture NOt taken",
					Toast.LENGTH_LONG).show();
		}
		img_pay.setLayoutParams(headerEditParams);

		if (imageloadprogressdialog != null) {
			imageloadprogressdialog.dismiss();
		}
		super.onActivityResult(requestCode, resultCode, data);

	}

	@Override
	public void processFinishSaveImage(String output) {

		if (output != null && !"".equals(output)) {
			try {
				Bitmap bitmap = ((BitmapDrawable) img_pay.getDrawable())
						.getBitmap();
				byte[] bytes = Utility.CompressBitmap(bitmap);

				dbAdapter.open();
				dbAdapter.updateImageAnad(i, bytes);

				dbAdapter.close();

				if (ringProgressDialog != null)
					ringProgressDialog.dismiss();

			} catch (NumberFormatException e) {
				Toast.makeText(getActivity(), "  خطا در بروز رسانی تصویر",
						Toast.LENGTH_SHORT).show();
			}
		}

	}

	@Override
	public void processFinish(String output) {
		int serverId = -1;
		if (ringProgressDialog != null)
			ringProgressDialog.dismiss();
		try {
			serverId = Integer.valueOf(output);

			dbAdapter.open();

			String b = String.valueOf(sp_pay.getSelectedItem());

			com.project.mechanic.entity.Object o = dbAdapter.getObjectByName(b);

			dbAdapter.UpdateAnadToDb(i, o.getId(), serverDate, 0, id);
			dbAdapter.close();

			FragmentTransaction trans = ((MainActivity) getActivity())
					.getSupportFragmentManager().beginTransaction();
			AnadFragment fragment = new AnadFragment();
			Bundle bundle = new Bundle();
			bundle.putString("Id", String.valueOf(id));
			if (id >= 0)
				bundle.putString("ProID", String.valueOf(id));
			fragment.setArguments(bundle);
			trans.replace(R.id.content_frame, fragment);
			trans.addToBackStack(null);
			trans.commit();

			if (ringProgressDialog != null)
				ringProgressDialog.dismiss();

			Bitmap bitmap = ((BitmapDrawable) img_pay.getDrawable())
					.getBitmap();
			byte[] bytes = Utility.CompressBitmap(bitmap);

			saveImage = new SavingImage(getActivity());
			saveImage.delegate = this;
			imageParams = new LinkedHashMap<String, Object>();
			imageParams.put("tableName", "Anad");
			imageParams.put("fieldName", "Image");
			imageParams.put("Id", i);
			imageParams.put("Image", bytes);

			saveImage.execute(imageParams);

			ringProgressDialog = ProgressDialog.show(getActivity(), null,
					"لطفا منتظر بمانید.");

			Toast.makeText(getActivity(), "i = " + i + "\n ostan = " + id, 0)
					.show();
		} catch (NumberFormatException e) {

			if (!"".equals(output)
					&& output != null
					&& !(output.contains("Exception") || output
							.contains("java"))) {

				String b = String.valueOf(sp_pay.getSelectedItem());
				dbAdapter.open();
				com.project.mechanic.entity.Object o = dbAdapter
						.getObjectByName(b);

				dbAdapter.close();
				saving = new Saving(getActivity());
				saving.delegate = show_pay_fragment.this;
				params = new LinkedHashMap<String, String>();

				params.put("tableName", "Anad");

				params.put("ObjectId", String.valueOf(o.getId()));
				 params.put("Date", output);
				params.put("ModifyDate", output);

				params.put("TypeId", "0");

				params.put("IsUpdate", "1");
				params.put("Id", String.valueOf(i));
				saving.execute(params);

				serverDate = output;
				ringProgressDialog = ProgressDialog.show(getActivity(), null,
						"لطفا منتظر بمانید.");

			} else
				Toast.makeText(getActivity(),
						"خطا در ثبت. پاسخ نا مشخص از سرور", Toast.LENGTH_SHORT)
						.show();
		}

	}
}