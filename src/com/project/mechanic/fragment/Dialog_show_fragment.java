package com.project.mechanic.fragment;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.project.mechanic.R;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

@SuppressLint("ValidFragment")
public class Dialog_show_fragment extends Dialog {

	Fragment fragment;
	private DataBaseAdapter dbadapter;
	Context context;
	int ticketTypeID;
	Utility util;
	int resourceId;
	private EditText dialog_anad_1, dialog_anad_2, Name, Mobile, Phonnumber,
			Fax, Email;
	LinearLayout Lheader;
	private ImageView dialog_img1;
	private Button dialog_btn;
	LinearLayout.LayoutParams headerEditParams;

	public Dialog_show_fragment(final Context context, int resourceId,
			Fragment fragment, final int ticketTypeID) {
		super(context);

		this.resourceId = resourceId;
		this.context = context;
		this.fragment = fragment;
		this.ticketTypeID = ticketTypeID;
	}

	public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 0, outputStream);
		return outputStream.toByteArray();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.dialog_addcomment);
		setContentView(resourceId);
		// id = Integer.valueOf(getArguments().getString("Id"));

		dialog_img1 = (ImageView) findViewById(R.id.dialog_img11);
		dialog_btn = (Button) findViewById(R.id.dialog_img12);
		dialog_anad_1 = (EditText) findViewById(R.id.dialog_anad_t1);
		dialog_anad_2 = (EditText) findViewById(R.id.dialog_anad_t2);
		// Lheader = (LinearLayout) findViewById(R.id.linDialogImg);
		Name = (EditText) findViewById(R.id.Name);
		Mobile = (EditText) findViewById(R.id.Mobile);
		Email = (EditText) findViewById(R.id.Email);
		Fax = (EditText) findViewById(R.id.Fax);
		Phonnumber = (EditText) findViewById(R.id.Phone);

		// util = new Utility(context);
		// headerEditParams = new LinearLayout.LayoutParams(
		// Lheader.getLayoutParams());
		// headerEditParams.height = util.getScreenHeight() / 5;
		// headerEditParams.width = util.getScreenHeight() / 5;
		// dialog_img1.setLayoutParams(headerEditParams);

		dbadapter = new DataBaseAdapter(context);
		dbadapter.open();
		dialog_btn.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String title = dialog_anad_1.getText().toString();
				String desc = dialog_anad_2.getText().toString();
				String date = new SimpleDateFormat("yyyy-MM-dd")
						.format(new Date());
				if ("".equals(title) || "".equals(desc)) {
					Toast.makeText(context,
							" عنوان آگهی یا شرح آگهی نمی تواند خالی باشد",
							Toast.LENGTH_LONG).show();
				} else {
					if ((dialog_img1.getDrawable() == null)) {

						dbadapter.UpdateTicketToDb(ticketTypeID, title, desc,
								null, Name.getText().toString(), date, Email
										.getText().toString(), Phonnumber
										.getText().toString(), Fax.getText()
										.toString(), Mobile.getText()
										.toString());
					} else {

						Bitmap bitmap = ((BitmapDrawable) dialog_img1
								.getDrawable()).getBitmap();

						Bitmap emptyBitmap = Bitmap.createBitmap(
								bitmap.getWidth(), bitmap.getHeight(),
								bitmap.getConfig());
						if (bitmap.sameAs(emptyBitmap)) {
							dbadapter.UpdateTicketToDb(ticketTypeID, title,
									desc, null, Name.getText().toString(),
									date, Email.getText().toString(),
									Phonnumber.getText().toString(), Fax
											.getText().toString(), Mobile
											.getText().toString());

						} else {
							byte[] bytes = getBitmapAsByteArray(bitmap);
							dbadapter.UpdateTicketToDb(ticketTypeID, title,
									desc, bytes, Name.getText().toString(),
									date, Email.getText().toString(),
									Phonnumber.getText().toString(), Fax
											.getText().toString(), Mobile
											.getText().toString());

						}

					}
					Toast.makeText(context, "آگهی شما با موفقیت ثبت شد",
							Toast.LENGTH_SHORT).show();
					dbadapter.close();

					Dialog_show_fragment.this.dismiss();
				}

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

	public View getView() {
		return this.getLayoutInflater().inflate(resourceId, null);
	}

}
