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
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.format.Time;
import android.view.View;
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

public class DialogPostFragment extends Fragment {

	Context mContext;

	ImageView btnPickFile;
	ImageView ShowImage;

	Button btnClearImage;
	Button btnSave;

	private DataBaseAdapter dbadapter;

	// private EditText PostTitle;
	private EditText PostDecription;

	Utility util;

	Users user;

	Users currentUser;

	View view;

	byte[] ImageConvertedToByte = null;

	String severDate;

	int ObjectId;

	@Override
	public View onCreateView(android.view.LayoutInflater inflater,
			android.view.ViewGroup container, Bundle savedInstanceStat) {
		ObjectId = Integer.valueOf(getArguments().getInt("Id"));
		view = inflater.inflate(R.layout.dialog_addtitlepostfragment, null);

		util = new Utility(this.getActivity());
		user = new Users();

		currentUser = util.getCurrentUser();

		ShowImage = (ImageView) view.findViewById(R.id.ShowImage);

		btnPickFile = (ImageView) view.findViewById(R.id.btnPickFile);
		btnPickFile.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				selectImageOption();
			}
		});

		btnClearImage = (Button) view.findViewById(R.id.btnClearImage);
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
		PostDecription = (EditText) view.findViewById(R.id.txttitleDes);
		btnSave = (Button) view.findViewById(R.id.btnPdf1_Object);
		btnSave.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				String ImageAddress = "";

				if (ImageConvertedToByte != null) {
					Time time = new Time();
					time.setToNow();

					ImageAddress = util.CreateFileString(
							ImageConvertedToByte,
							"_" + currentUser.getId() + "_"
									+ Long.toString(time.toMillis(false)),
							"Mechanical", "Post", "post");
				}
				if (!ImageAddress.isEmpty()
						|| !PostDecription.getText().toString().isEmpty()) {
					dbadapter.open();
//					dbadapter.insertPosttitletoDb(PostDecription.getText()
//							.toString(), currentUser.getId(), severDate,
//							ImageAddress);
					dbadapter.close();

					IntroductionFragment fragment = new IntroductionFragment(-1);

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

				} else
					Toast.makeText(getActivity(),
							"حداقل یه عکس یا یک متن وارد کنید",
							Toast.LENGTH_SHORT).show();
			}
		});

		return view;
	}

	LinearLayout.LayoutParams lp2;
	private Uri mImageCaptureUri;
	private File mFileTemp;
	public static final String TEMP_PHOTO_FILE_NAME = "temp_photo.jpg";
	private static final int CAMERA_CODE = 101, GALLERY_CODE = 201,
			CROPING_CODE = 301;
	final int PIC_CROP = 10;

	private void selectImageOption() {
		final CharSequence[] items = { "از دوربین", "از گالری تصاویر", "انصراف" };

		AlertDialog.Builder builder = new AlertDialog.Builder(
				this.getActivity());
		builder.setTitle("افزودن تصویر");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {

				if (items[item].equals("از دوربین")) {
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					File f = new File(android.os.Environment
							.getExternalStorageDirectory(), "temp1.jpg");
					mImageCaptureUri = Uri.fromFile(f);
					intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
					getActivity().startActivityForResult(intent, CAMERA_CODE);

				} else if (items[item].equals("از گالری تصاویر")) {

					Intent i = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

					getActivity().startActivityFromFragment(
							DialogPostFragment.this, i, GALLERY_CODE);

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