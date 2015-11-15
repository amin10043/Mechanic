package com.project.mechanic.fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.project.mechanic.R;
import com.project.mechanic.crop.CropImage;
import com.project.mechanic.entity.Users;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class DialogpostTitleFragment extends DialogFragment {

	Context mContext;
	ImageView btnPickFile;
	ImageView ShowImage;
	Button btnClearImage;
	Utility util;
	Users user;
	View view;
	byte[] ImageConvertedToByte = null;

	LinearLayout.LayoutParams lp2;
	private Uri mImageCaptureUri;
	private File mFileTemp;
	public static final String TEMP_PHOTO_FILE_NAME = "temp_photo.jpg";
	private static final int CAMERA_CODE = 101, GALLERY_CODE = 201,
			CROPING_CODE = 301;
	final int PIC_CROP = 10;

	/**/
	private DataBaseAdapter dbadapter;
	private EditText PostDecription;
	Button btnSave;
	Users currentUser;
	int ObjectId;
	String severDate;

	/**/

	public DialogpostTitleFragment(int object_id) {
		mContext = getActivity();
		ObjectId = object_id;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.dialog_addtitlepostfragment,
				container, false);
		getDialog().setTitle("ایجاد پست جدید");

		// ObjectId = Integer.valueOf(getArguments().getInt("Id"));
		util = new Utility(this.getActivity());
		user = new Users();
		currentUser = util.getCurrentUser();

		btnPickFile = (ImageView) rootView.findViewById(R.id.btnPickFile);
		ShowImage = (ImageView) rootView.findViewById(R.id.ShowImage);

		btnPickFile = (ImageView) rootView.findViewById(R.id.btnPickFile);
		btnPickFile.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				selectImageOption();
			}
		});

		btnClearImage = (Button) rootView.findViewById(R.id.btnClearImage);
		btnClearImage.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				ImageConvertedToByte = null;
				ShowImage.setVisibility(View.GONE);
				btnClearImage.setVisibility(View.INVISIBLE);
			}
		});

		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			mFileTemp = new File(Environment.getExternalStorageDirectory(),
					TEMP_PHOTO_FILE_NAME);
		} else {
			mFileTemp = new File(getActivity().getFilesDir(),
					TEMP_PHOTO_FILE_NAME);
		}

		dbadapter = new DataBaseAdapter(getActivity());

		// PostTitle = (EditText) view.findViewById(R.id.txtTitleP);
		PostDecription = (EditText) rootView.findViewById(R.id.txttitleDes);
		btnSave = (Button) rootView.findViewById(R.id.btnPdf1_Object);
		btnSave.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				Time time = new Time();
				time.setToNow();
				String Date = Long.toString(time.toMillis(false));

				String ImageAddress = "";

				if (ImageConvertedToByte != null) {

					ImageAddress = util.CreateFileString(ImageConvertedToByte,
							"_" + currentUser.getId() + "_" + Date,
							"Mechanical", "Post", "post");
				}
				if (!ImageAddress.isEmpty()
						|| !PostDecription.getText().toString().isEmpty()) {

					dbadapter.open();
					dbadapter.insertPosttitletoDb(PostDecription.getText()
							.toString(), currentUser.getId(), "", ImageAddress);
					dbadapter.close();

					IntroductionFragment fragment = new IntroductionFragment();

					FragmentTransaction trans = getActivity()
							.getSupportFragmentManager().beginTransaction();
					trans.setCustomAnimations(R.anim.pull_in_left,
							R.anim.push_out_right);
					trans.replace(R.id.content_frame, fragment);
					trans.addToBackStack(null);
					trans.commit();

					Bundle bundle = new Bundle();
					bundle.putString("Id", ObjectId + "");
					fragment.setArguments(bundle);

					Fragment prev = getActivity().getSupportFragmentManager()
							.findFragmentByTag("My_Dialog_Dialog");
					if (prev != null) {
						DialogpostTitleFragment df = (DialogpostTitleFragment) prev;
						df.dismiss();
					}

				} else
					Toast.makeText(getActivity(),
							"حداقل یه عکس یا یک متن وارد کنید",
							Toast.LENGTH_SHORT).show();
			}
		});

		return rootView;
	}

	// public static DialogpostTitleFragment newInstance() {
	// DialogpostTitleFragment f = new DialogpostTitleFragment();
	// return f;
	// }
	private Uri imageToUploadUri;

	private void selectImageOption() {
		final CharSequence[] items = { "از دوربین", "از گالری تصاویر", "انصراف" };

		AlertDialog.Builder builder = new AlertDialog.Builder(
				this.getActivity());
		builder.setTitle("افزودن تصویر");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {

				if (items[item].equals("از دوربین")) {
					// Intent intent = new
					// Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					// File f = new File(android.os.Environment
					// .getExternalStorageDirectory(), "temp1.jpg");
					// mImageCaptureUri = Uri.fromFile(f);
					// intent.putExtra(MediaStore.EXTRA_OUTPUT,
					// mImageCaptureUri);
					// getActivity().startActivityForResult(intent,
					// CAMERA_CODE);
					// Intent cameraIntent = new Intent(
					// android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
					// startActivityForResult(cameraIntent, CAMERA_CODE);
					// getActivity().startActivityFromFragment(
					// DialogpostTitleFragment.this, cameraIntent,
					// CAMERA_CODE);
					// Intent intent = new
					// Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					// File f = new File(android.os.Environment
					// .getExternalStorageDirectory(), "temp.jpg");
					// intent.putExtra(MediaStore.EXTRA_OUTPUT,
					// Uri.fromFile(f));
					// // pic = f;
					//
					// startActivityForResult(intent, CAMERA_CODE);
					// Intent chooserIntent = new Intent(
					// MediaStore.ACTION_IMAGE_CAPTURE);
					// File f = new File(
					// Environment.getExternalStorageDirectory(),
					// "POST_IMAGE.jpg");
					// chooserIntent.putExtra(MediaStore.EXTRA_OUTPUT,
					// Uri.fromFile(f));
					// imageToUploadUri = Uri.fromFile(f);
					// startActivityForResult(chooserIntent, CAMERA_CODE);
					// Intent intent = new
					// Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					// startActivityForResult(intent, CAMERA_CODE);

				} else if (items[item].equals("از گالری تصاویر")) {

					Intent i = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

					getActivity().startActivityFromFragment(
							DialogpostTitleFragment.this, i, GALLERY_CODE);

					// Intent intent = new Intent();
					// intent.setType("image/*");
					// intent.setAction(Intent.ACTION_GET_CONTENT);
					// try {
					// intent.putExtra("return-data", true);
					// startActivityForResult(
					// Intent.createChooser(intent, "تکمیل کار با"),
					// GALLERY_CODE);
					// } catch (ActivityNotFoundException e) {
					// }

				} else if (items[item].equals("انصراف")) {
					dialog.dismiss();
				}
			}
		});

		builder.show();
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// if (data != null) {
		switch (requestCode) {
		case GALLERY_CODE:
			if (resultCode == this.getActivity().RESULT_OK) {

				mImageCaptureUri = data.getData();
				try {
					// Bitmap bitmapImage = decodeBitmap(mImageCaptureUri);
					// ShowImage.setImageBitmap(bitmapImage);
					// ImageConvertedToByte =
					// Utility.CompressBitmap(bitmapImage);

					InputStream inputStream = getActivity()
							.getContentResolver().openInputStream(
									data.getData());
					FileOutputStream fileOutputStream = new FileOutputStream(
							mFileTemp);
					try {
						Utility.copyStream(inputStream, fileOutputStream);
					} catch (IOException e) {
						e.printStackTrace();
					}
					try {
						fileOutputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					try {
						inputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}

					startCropImage();

					// if (ImageConvertedToByte != null) {
					// ShowImage.setVisibility(View.VISIBLE);
					// btnClearImage.setVisibility(View.VISIBLE);
					// }

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
//		case CAMERA_CODE:
//			if (resultCode == this.getActivity().RESULT_OK) {
//				Toast.makeText(mContext, "Camera", Toast.LENGTH_SHORT).show();
//			}
			// Bitmap photo = (Bitmap) data.getExtras().get("data");
			// ShowImage.setImageBitmap(photo);
			// ShowImage.setVisibility(View.VISIBLE);
			// btnClearImage.setVisibility(View.VISIBLE);
		case PIC_CROP:
			if (data != null) {
				String path = data.getStringExtra(CropImage.IMAGE_PATH);
				if (path == null) {
					return;
				}
				Bitmap bitmap = null;
				if (mFileTemp.getPath() != null)
					bitmap = BitmapFactory.decodeFile(mFileTemp.getPath());
				if (bitmap != null) {
					ImageConvertedToByte = Utility.CompressBitmap(bitmap);
					ShowImage.setImageBitmap(bitmap);
					ShowImage.setVisibility(View.VISIBLE);
					btnClearImage.setVisibility(View.VISIBLE);
					// ImageProfile.setLayoutParams(lp2);
				}
			}
		}
		// } else
		// Toast.makeText(mContext, "Camera", Toast.LENGTH_SHORT).show();

	}

	public Bitmap decodeBitmap(Uri selectedImage) throws FileNotFoundException {
		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(this.getActivity().getContentResolver()
				.openInputStream(selectedImage), null, o);

		final int REQUIRED_SIZE = 100;

		int width_tmp = o.outWidth, height_tmp = o.outHeight;
		int scale = 1;
		while (true) {
			if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE) {
				break;
			}
			width_tmp /= 2;
			height_tmp /= 2;
			scale *= 2;
		}

		BitmapFactory.Options o2 = new BitmapFactory.Options();
		o2.inSampleSize = scale;
		return BitmapFactory.decodeStream(this.getActivity()
				.getContentResolver().openInputStream(selectedImage), null, o2);
	}

	private void startCropImage() {

		Intent intent = new Intent(getActivity(), CropImage.class);
		intent.putExtra(CropImage.IMAGE_PATH, mFileTemp.getPath());
		intent.putExtra(CropImage.SCALE, true);

		intent.putExtra(CropImage.ASPECT_X, 3);
		intent.putExtra(CropImage.ASPECT_Y, 3);

		startActivityForResult(intent, PIC_CROP);
	}

}