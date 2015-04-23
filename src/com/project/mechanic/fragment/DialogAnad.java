package com.project.mechanic.fragment;

import java.io.ByteArrayOutputStream;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.project.mechanic.R;
import com.project.mechanic.model.DataBaseAdapter;

public class DialogAnad extends Dialog {

	protected static final Context Contaxt = null;

	protected static final EditText number = null;

	private static int RESULT_LOAD_IMAGE = 1;
	private static final int SELECT_PICTURE = 1;

	private ImageView dialog_img1, dialog_img2, dialog_img3;
	private CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5;
	private EditText dialog_anad_et1, dialog_anad_et2;
	OnMyDialogResult mDialogResult;
	private DataBaseAdapter dbadapter;
	int resourceId;
	Context context;
	Fragment fragment;
	int ticketTypeID;
	int emailCheck = 0;
	int nameCheck = 0;
	int faxCheck = 0;
	int phoneCheck = 0;
	int mobileCheck = 0;
	String titel;
	String Bytimage;
	int ProvinceId;
	protected byte[] img;
	String TABLE_NAME = "Ticket";

	public DialogAnad(Context context, int resourceId, Fragment fragment,
			int ticketTypeID, int ProvinceId) {
		super(context);
		// TODO Auto-generated constructor stub
		this.resourceId = resourceId;
		this.context = context;
		this.fragment = fragment;
		this.ticketTypeID = ticketTypeID;
		this.ProvinceId = ProvinceId;
	}

	public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 0, outputStream);
		return outputStream.toByteArray();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.dialog_addcomment);

		// id = Integer.valueOf(getArguments().getString("Id"));

		setContentView(resourceId);
		dialog_img1 = (ImageView) findViewById(R.id.dialog_img1);
		dialog_img2 = (ImageView) findViewById(R.id.dialog_img2);
		checkBox1 = (CheckBox) findViewById(R.id.checkBox1);
		checkBox2 = (CheckBox) findViewById(R.id.checkBox2);
		checkBox3 = (CheckBox) findViewById(R.id.checkBox3);
		checkBox4 = (CheckBox) findViewById(R.id.checkBox4);
		checkBox5 = (CheckBox) findViewById(R.id.checkBox5);
		dialog_anad_et1 = (EditText) findViewById(R.id.dialog_anad_et1);
		dialog_anad_et2 = (EditText) findViewById(R.id.dialog_anad_et2);

		dialog_img2.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dbadapter = new DataBaseAdapter(context);
				if (checkBox1.isChecked()) {
					emailCheck = 1;
				} else {
					emailCheck = 0;
				}
				if (checkBox2.isChecked()) {
					nameCheck = 1;
				} else {
					nameCheck = 0;
				}
				if (checkBox3.isChecked()) {
					faxCheck = 1;
				} else {
					faxCheck = 0;
				}
				if (checkBox4.isChecked()) {
					phoneCheck = 1;
				} else {
					phoneCheck = 0;
				}
				if (checkBox5.isChecked()) {
					mobileCheck = 1;
				} else {
					mobileCheck = 0;
				}

				dbadapter.open();

				if ((dialog_img1.getDrawable() == null)) {

					dbadapter.insertTickettoDbemptyImage(dialog_anad_et1
							.getText().toString(), dialog_anad_et2.getText()
							.toString(), 1, ticketTypeID, emailCheck,
							nameCheck, faxCheck, phoneCheck, mobileCheck,
							ProvinceId);

				} else {

					Bitmap bitmap = ((BitmapDrawable) dialog_img1.getDrawable())
							.getBitmap();

					Bitmap emptyBitmap = Bitmap.createBitmap(bitmap.getWidth(),
							bitmap.getHeight(), bitmap.getConfig());
					if (bitmap.sameAs(emptyBitmap)) {
						dbadapter.insertTickettoDbemptyImage(dialog_anad_et1
								.getText().toString(), dialog_anad_et2
								.getText().toString(), 1, ticketTypeID,
								emailCheck, nameCheck, faxCheck, phoneCheck,
								mobileCheck, ProvinceId);
					} else {
						byte[] bytes = getBitmapAsByteArray(bitmap);

						dbadapter.insertTickettoDb(dialog_anad_et1.getText()
								.toString(), dialog_anad_et2.getText()
								.toString(), 1, ticketTypeID, bytes,
								emailCheck, nameCheck, faxCheck, phoneCheck,
								mobileCheck, ProvinceId);
					}

				}
				dbadapter.close();
				// } catch (Exception e) {
				// e.printStackTrace();
				// // textView.append("Error Exception : " + e.getMessage());
				// }

				((AnadFragment) fragment).updateView();
				DialogAnad.this.dismiss();

			}
		});
		// checkBox1.setOnClickListener(new android.view.View.OnClickListener()
		// {
		//
		// @Override
		// public void onClick(View v) {
		// // is chkIos checked?
		// if (((CheckBox) v).isChecked()) {
		//
		// }

		// StringBuffer result = new StringBuffer();
		// result.append("checkBox1 : ").append(checkBox1.isChecked());
		// result.append("checkBox2 : ").append(checkBox2.isChecked());
		// result.append("checkBox3 :").append(checkBox3.isChecked());
		// result.append("checkBox4 :").append(checkBox4.isChecked());
		//
		// Toast.makeText(context, R.string.hello_world,
		// Toast.LENGTH_SHORT).show();
		// }
		// });

		dialog_img1.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				fragment.getActivity().startActivityFromFragment(fragment, i,
						RESULT_LOAD_IMAGE);

			}
		});

	}

	protected Resources getResources() {
		// TODO Auto-generated method stub
		return null;
	}

	protected Intent getIntent() {
		// TODO Auto-generated method stub
		return null;
	}

	public interface OnMyDialogResult {
		void finish(String result);
	}

	public void setDialogResult(OnMyDialogResult dialogResult) {
		mDialogResult = dialogResult;
	}

	public View getView() {
		return this.getLayoutInflater().inflate(resourceId, null);
	}

}
