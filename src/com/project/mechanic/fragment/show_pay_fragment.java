package com.project.mechanic.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;

public class show_pay_fragment extends Fragment {

	private static int RESULT_LOAD_IMAGE = 1;
	ImageView img_pay;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		((MainActivity) getActivity()).setActivityTitle(R.string.showad);
		// id = Integer.valueOf(getArguments().getString("Id"));

		View view = inflater.inflate(R.layout.fragment_pay, null);

		img_pay = (ImageView) view.findViewById(R.id.img_pay);
		Spinner sp_pay = (Spinner) view.findViewById(R.id.sp_pay);
		Button btn_pay = (Button) view.findViewById(R.id.btn_pay);

		// dbAdapter = new DataBaseAdapter(getActivity());

		img_pay.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				getActivity().startActivityFromFragment(show_pay_fragment.this,
						i, RESULT_LOAD_IMAGE);

			}
		});

		return view;
	}

	@Override
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

			// ImageView imageView = (ImageView) dialog
			// .findViewById(R.id.dialog_img1);
			img_pay.setImageBitmap(BitmapFactory.decodeFile(picturePath));

		}
	}
}
