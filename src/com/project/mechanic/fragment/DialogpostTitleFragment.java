package com.project.mechanic.fragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import com.project.mechanic.R;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.inter.SaveAsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class DialogpostTitleFragment extends DialogFragment {
	
	Context mContext;
	ImageView btnPickFile;
	ImageView SelectPick;
	
	public DialogpostTitleFragment() {
        mContext = getActivity();
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.dialog_addtitlepostfragment, container,
				false);
		getDialog().setTitle("ایجاد پست جدید");
		
		SelectPick = (ImageView) rootView.findViewById(R.id.btnPickFile);
		
		btnPickFile = (ImageView) rootView.findViewById(R.id.btnPickFile);
		btnPickFile.setOnClickListener(new View.OnClickListener() {
			   public void onClick(View v) {
				   selectImageOption();
			   }        
			});
		
		return rootView;
	}
	
	public static DialogpostTitleFragment newInstance() {
		DialogpostTitleFragment f = new DialogpostTitleFragment();
        return f;
    }
	
	Utility ut;
	LinearLayout.LayoutParams lp2;
	private Uri mImageCaptureUri;
	private File mFileTemp;
	public static final String TEMP_PHOTO_FILE_NAME = "temp_photo.jpg";
	private static final int CAMERA_CODE = 101, GALLERY_CODE = 201,
			CROPING_CODE = 301;
	final int PIC_CROP = 10;
	//ImageView ImageProfile, imagecamera;
	
	private void selectImageOption() {
		final CharSequence[] items = { "از دوربین", "از گالری تصاویر", "انصراف" };

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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

					//fragment.getActivity().startActivityForResult(intent, CAMERA_CODE);

				} else if (items[item].equals("از گالری تصاویر")) {

					Intent i = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

					getActivity().startActivityForResult(
							i, GALLERY_CODE);

					Toast.makeText(getActivity(), "Text1", Toast.LENGTH_LONG).show();
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
		
		
		Toast.makeText(getActivity(), "YEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEES", Toast.LENGTH_LONG).show();
		/*if (requestCode == GALLERY_CODE) {
			try {

				InputStream inputStream = getActivity().getContentResolver()
						.openInputStream(data.getData());
				FileOutputStream fileOutputStream = new FileOutputStream(
						mFileTemp);
				Utility.copyStream(inputStream, fileOutputStream);
				fileOutputStream.close();
				inputStream.close();
				
				Bitmap bitmap = null;
				if (mFileTemp.getPath() != null)
					bitmap = BitmapFactory.decodeFile(mFileTemp.getPath());
				if (bitmap != null) {
					SelectPick.setImageBitmap(bitmap);
				}
				
				//startCropImage();

			} catch (Exception e) {

				Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG)
						.show();
			}
			SelectPick.setLayoutParams(lp2);
			

		}*/
		/*if (requestCode == PIC_CROP && data != null) {
			String path = data.getStringExtra(CropImage.IMAGE_PATH);
			if (path == null) {
				return;
			}
			Bitmap bitmap = null;
			if (mFileTemp.getPath() != null)
				bitmap = BitmapFactory.decodeFile(mFileTemp.getPath());
			if (bitmap != null) {
				ImageProfile.setImageBitmap(bitmap);
				ImageProfile.setLayoutParams(lp2);

			}

		}*/
		
		super.onActivityResult(requestCode, resultCode, data);
	}
	
}