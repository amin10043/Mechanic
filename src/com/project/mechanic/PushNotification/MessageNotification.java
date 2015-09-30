package com.project.mechanic.PushNotification;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.crop.CropImage;
import com.project.mechanic.entity.Users;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class MessageNotification extends Fragment {

	Button sendNotification;
	String message;
	EditText inputMessage;
	DataBaseAdapter dbAdapter;
	Utility util;
	ImageView selectImage;
	int IMAGE_CODE = 100;
	final int PIC_CROP = 10;
	private File mFileTemp;
	public static final String TEMP_PHOTO_FILE_NAME = "temp_photo.jpg";
	int ObjectId;
	LinearLayout.LayoutParams paramslayout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(
				R.layout.fragment_message_notification, null);

		dbAdapter = new DataBaseAdapter(getActivity());
		util = new Utility(getActivity());

		sendNotification = (Button) rootView
				.findViewById(R.id.sendNotification);
		inputMessage = (EditText) rootView.findViewById(R.id.getMessage);
		selectImage = (ImageView) rootView.findViewById(R.id.select_image);
		final Spinner pageSpinner = (Spinner) rootView
				.findViewById(R.id.pageSpinner);

		TextView lableNoImage = (TextView) rootView
				.findViewById(R.id.no_page_a);

		LinearLayout lay = (LinearLayout) rootView.findViewById(R.id.sbu);
		paramslayout = new LinearLayout.LayoutParams(lay.getLayoutParams());
		paramslayout.width = util.getScreenwidth() / 4;
		paramslayout.height = util.getScreenwidth() / 4;
		paramslayout.gravity = Gravity.CENTER;
		paramslayout.setMargins(5, 5, 5, 5);

		selectImage.setLayoutParams(paramslayout);

		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			mFileTemp = new File(Environment.getExternalStorageDirectory(),
					TEMP_PHOTO_FILE_NAME);
		} else {
			mFileTemp = new File(getActivity().getFilesDir(),
					TEMP_PHOTO_FILE_NAME);
		}

		dbAdapter.open();
		Users CurrentUser = util.getCurrentUser();

		ArrayList<String> namePage = new ArrayList<String>();

		com.project.mechanic.entity.Object page;
		final List<com.project.mechanic.entity.Object> objectPage = dbAdapter
				.getObjectByuserId(CurrentUser.getId());
		for (int i = 0; i < objectPage.size(); i++) {
			page = objectPage.get(i);
			namePage.add(page.getName());
		}
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_item, namePage);

		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		pageSpinner.setAdapter(dataAdapter);

		dbAdapter.close();

		if (objectPage.size() == 0) {
			lableNoImage.setVisibility(View.VISIBLE);
			pageSpinner.setEnabled(false);
			inputMessage.setEnabled(false);
			selectImage.setEnabled(false);
			sendNotification.setEnabled(false);
		}

		pageSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

				int itemId = (int) pageSpinner.getSelectedItemId();

				ObjectId = objectPage.get(itemId).getId();

				Toast.makeText(getActivity(), ObjectId + "", 0).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				//

			}
		});

		sendNotification.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				message = inputMessage.getText().toString();

				if (!message.equals("")) {

					TypeUserNotification fr = new TypeUserNotification(
							"BirthDay", 0);

					FragmentTransaction trans = ((MainActivity) getActivity())
							.getSupportFragmentManager().beginTransaction();

					trans.replace(R.id.content_frame, fr);

					trans.addToBackStack(null);
					trans.commit();

				} else {
					Toast.makeText(getActivity(),
							"وارد کردن متن پیغام اجباری است", 0).show();
				}
			}
		});

		selectImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				getActivity().startActivityFromFragment(
						MessageNotification.this, i, IMAGE_CODE);

			}
		});

		return rootView;
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == IMAGE_CODE) {
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
			selectImage.setLayoutParams(paramslayout);

		}
		if (requestCode == PIC_CROP && data != null) {
			String path = data.getStringExtra(CropImage.IMAGE_PATH);
			if (path == null) {
				return;
			}
			Bitmap bitmap = null;
			if (mFileTemp.getPath() != null)
				bitmap = BitmapFactory.decodeFile(mFileTemp.getPath());
			if (bitmap != null) {
				selectImage.setImageBitmap(bitmap);
				selectImage.setLayoutParams(paramslayout);

			}

		}

		super.onActivityResult(requestCode, resultCode, data);

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
