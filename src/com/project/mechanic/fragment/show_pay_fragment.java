package com.project.mechanic.fragment;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
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
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class show_pay_fragment extends Fragment {

	private static int RESULT_LOAD_IMAGE = 1;
	ImageView img_pay;
	Spinner sp_pay;
	DataBaseAdapter dbAdapter;
	int proID;
	int id;
	int i;
	int a=0;
	Utility util;
	LinearLayout.LayoutParams headerEditParams;
	LinearLayout Lheader;

	public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 0, outputStream);
		return outputStream.toByteArray();
	}

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		((MainActivity) getActivity()).setActivityTitle(R.string.showad);
		 i = Integer.valueOf(getArguments().getString("I"));
		// if (getArguments().getString("ProID") != null) {
		// proID = Integer.valueOf(getArguments().getString("ProID"));
		// }

		View view = inflater.inflate(R.layout.fragment_pay, null);
		Lheader = (LinearLayout) view.findViewById(R.id.linImg);
		img_pay = (ImageView) view.findViewById(R.id.img_pay);
		sp_pay = (Spinner) view.findViewById(R.id.sp_pay);
		Button btn_pay = (Button) view.findViewById(R.id.btn_pay);

		dbAdapter = new DataBaseAdapter(getActivity());
		util = new Utility(getActivity());
		headerEditParams = new LinearLayout.LayoutParams(
				Lheader.getLayoutParams());
		headerEditParams.height = util.getScreenHeight() / 3;
		headerEditParams.width = util.getScreenHeight() / 3;
		img_pay.setLayoutParams(headerEditParams);

		dbAdapter.open();
		List<String> mylist = dbAdapter.getAllObjectname();
		// List<String> myid = dbAdapter.getAllObjectid();

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_item, mylist);

		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		sp_pay.setAdapter(dataAdapter);

		dbAdapter.close();

		btn_pay.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String date = new SimpleDateFormat("yyyy-MM-dd")
						.format(new Date());
				SharedPreferences sendIdpro = getActivity()
						.getSharedPreferences("Id", 0);
				int id = sendIdpro.getInt("main_Id", -1);
				Toast.makeText(getActivity(), i + "do", Toast.LENGTH_SHORT)
						.show();
				Bitmap bitmap = ((BitmapDrawable) img_pay.getDrawable())
						.getBitmap();
				byte[] bytes = getBitmapAsByteArray(bitmap);
				String b = String.valueOf(sp_pay.getSelectedItem());

				// ************شرط پرداخت اینترنتی باید ذکر شود*************
				// ************شرط پرداخت اینترنتی باید ذکر شود*************
				// ************شرط پرداخت اینترنتی باید ذکر شود*************
				// ************شرط پرداخت اینترنتی باید ذکر شود*************
				// ************شرط پرداخت اینترنتی باید ذکر شود*************
				// ************شرط پرداخت اینترنتی باید ذکر شود*************
				// ************شرط پرداخت اینترنتی باید ذکر شود*************
				// ************شرط پرداخت اینترنتی باید ذکر شود*************
				dbAdapter.open();
				com.project.mechanic.entity.Object o = dbAdapter
						.getObjectByName(b);
				switch(id) {
				case 1:
					 a=i+1;
					dbAdapter.UpdateAnadToDb(a,bytes, o.getId(), date, 0, id);
					a=0;
				break;
				case 3:
					 a=i+50+1;
					dbAdapter.UpdateAnadToDb(a,bytes, o.getId(), date, 0, id);
					a=0;
				break;
				case 4:
					a=i+80+1;
					dbAdapter.UpdateAnadToDb(a,bytes, o.getId(), date, 0, id);
					a=0;
				break;
				case 5:
					a=i+150+1;
					dbAdapter.UpdateAnadToDb(a,bytes, o.getId(), date, 0, id);
					a=0;
				break;
				case 6:
					a=i+200+1;
					dbAdapter.UpdateAnadToDb(a,bytes, o.getId(), date, 0, id);
					a=0;
				break;
				case 7:
					a=i+230+1;
					dbAdapter.UpdateAnadToDb(a,bytes, o.getId(), date, 0, id);
					a=0;
				break;
				case 8:
					a=i+260+1;
					dbAdapter.UpdateAnadToDb(a,bytes, o.getId(), date, 0, id);
					a=0;
				break;
				case 9:
					a=i+360+1;
					dbAdapter.UpdateAnadToDb(a,bytes, o.getId(), date, 0, id);
					a=0;
				break;
				case 10:
					a=i+390+1;
					dbAdapter.UpdateAnadToDb(a,bytes, o.getId(), date, 0, id);
					a=0;
				break;
				case 11:
					a=i+420+1;
					dbAdapter.UpdateAnadToDb(a,bytes, o.getId(), date, 0, id);
					a=0;
				break;
				case 12:
					a=i+470+1;
					dbAdapter.UpdateAnadToDb(a,bytes, o.getId(), date, 0, id);
					a=0;
				break;
				case 13:
					a=i+500+1;
					dbAdapter.UpdateAnadToDb(a,bytes, o.getId(), date, 0, id);
					a=0;
				break;
				case 14:
					a=i+530+1;
					dbAdapter.UpdateAnadToDb(a,bytes, o.getId(), date, 0, id);
					a=0;
				break;
				case 15:
					a=i+560+1;
					dbAdapter.UpdateAnadToDb(a,bytes, o.getId(), date, 0, id);
					a=0;
				break;
				case 16:
					a=i+590+1;
					dbAdapter.UpdateAnadToDb(a,bytes, o.getId(), date, 0, id);
					a=0;
				break;
				case 17:
					a=i+620+1;
					dbAdapter.UpdateAnadToDb(a,bytes, o.getId(), date, 0, id);
					a=0;
				break;
				case 18:
					a=i+670+1;
					dbAdapter.UpdateAnadToDb(a,bytes, o.getId(), date, 0, id);
					a=0;
				break;
				case 19:
					a=i+700+1;
					dbAdapter.UpdateAnadToDb(a,bytes, o.getId(), date, 0, id);
					a=0;
				break;
				case 20:
					a=i+750+1;
					dbAdapter.UpdateAnadToDb(a,bytes, o.getId(), date, 0, id);
					a=0;
				break;
				case 21:
					a=i+780+1;
					dbAdapter.UpdateAnadToDb(a,bytes, o.getId(), date, 0, id);
					a=0;
				break;
				case 22:
					a=i+830+1;
					dbAdapter.UpdateAnadToDb(a,bytes, o.getId(), date, 0, id);
					a=0;
				break;
				case 23:
					a=i+860+1;
					dbAdapter.UpdateAnadToDb(a,bytes, o.getId(), date, 0, id);
					a=0;
				break;
				case 24:
					a=i+890+1;
					dbAdapter.UpdateAnadToDb(a,bytes, o.getId(), date, 0, id);
					a=0;
				break;
				case 25:
					a=i+920+1;
					dbAdapter.UpdateAnadToDb(a,bytes, o.getId(), date, 0, id);
					a=0;
				break;
				case 26:
					a=i+970+1;
					dbAdapter.UpdateAnadToDb(a,bytes, o.getId(), date, 0, id);
					a=0;
				break;
				case 27:
					a=i+1020+1;
					dbAdapter.UpdateAnadToDb(a,bytes, o.getId(), date, 0, id);
					a=0;
				break;
				case 28:
					a=i+1070+1;
					dbAdapter.UpdateAnadToDb(a,bytes, o.getId(), date, 0, id);
					a=0;
				break;
				case 29:
					a=i+1120+1;
					dbAdapter.UpdateAnadToDb(a,bytes, o.getId(), date, 0, id);
					a=0;
				break;
				case 30:
					a=i+1170+1;
					dbAdapter.UpdateAnadToDb(a,bytes, o.getId(), date, 0, id);
					a=0;
				break;
				case 31:
					a=i+1200+1;
					dbAdapter.UpdateAnadToDb(a,bytes, o.getId(), date, 0, id);
					a=0;
				break;
				case -1:
					a=i+1250+1;
					dbAdapter.UpdateAnadToDb(a,bytes, o.getId(), date, 0, id);
					a=0;
				break;
				case 2:
					a=i+1350+1;
					dbAdapter.UpdateAnadToDb(a,bytes, o.getId(), date, 0, id);
					a=0;
				break;
				default:
				}

				
				dbAdapter.close();
//				FragmentTransaction trans = ((MainActivity) getActivity())
//						.getSupportFragmentManager().beginTransaction();
//				AnadFragment fragment = new AnadFragment();
//				Bundle bundle = new Bundle();
//				// bundle.putString("Id", String.valueOf(id));
//				//if (id >= 0)
//				//	bundle.putString("ProID", String.valueOf(id));
//				fragment.setArguments(bundle);
//				trans.replace(R.id.content_frame, fragment);
//				trans.addToBackStack(null);
//				trans.commit();

			}
		});

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
