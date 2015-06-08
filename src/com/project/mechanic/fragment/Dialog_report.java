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
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.project.mechanic.R;
import com.project.mechanic.entity.Ticket;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

@SuppressLint("ValidFragment")
public class Dialog_report extends Dialog {

	Fragment fragment;
	private DataBaseAdapter dbadapter;
	Context context;
	private static int RESULT_LOAD_IMAGE = 1;
	private static final int SELECT_PICTURE = 1;
	int ticketTypeID;
	int userID;
	Utility util;
	int resourceId;
	private EditText Editreport;
	LinearLayout Lheader;
	private Button btnreport;
	LinearLayout.LayoutParams headerEditParams;

	public Dialog_report(final Context context, int resourceId,
			Fragment fragment, int ticketTypeID) {
		super(context);

		this.resourceId = resourceId;
		this.context = context;
		this.fragment = fragment;
		this.ticketTypeID = ticketTypeID;
		// this.userID = userID;
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
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(resourceId);
		// id = Integer.valueOf(getArguments().getString("Id"));

		btnreport = (Button) findViewById(R.id.Button_report);
		Editreport = (EditText) findViewById(R.id.editText_report);

		dbadapter = new DataBaseAdapter(context);
		dbadapter.open();
		Ticket t = dbadapter.getTicketById(ticketTypeID);
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String matn = Editreport.getText().toString();

		// dialog_anad_2.setText(t.getDesc());
		// Name.setText(t.getUName());
		// Mobile.setText(t.getUMobile());
		// Email.setText(t.getUEmail());
		// Fax.setText(t.getUFax());
		// Phonnumber.setText(t.getUPhone());
		// dialog_btn.setOnClickListener(new android.view.View.OnClickListener()
		// {
		//
		// @Override
		// public void onClick(View arg0) {
		// String title = dialog_anad_1.getText().toString();
		// String desc = dialog_anad_2.getText().toString();

		// // if(!"".equals(dialog_anad_1.setText(text);))
		// if ("".equals(title) || "".equals(desc)) {
		// Toast.makeText(context,
		// " عنوان آگهی یا شرح آگهی نمی تواند خالی باشد",
		// Toast.LENGTH_LONG).show();
		// } else {
		// if ((dialog_img1.getDrawable() == null)) {
		//
		// dbadapter.UpdateTicketToDb(ticketTypeID, title, desc,
		// null, Name.getText().toString(), date, Email
		// .getText().toString(), Phonnumber
		// .getText().toString(), Fax.getText()
		// .toString(), Mobile.getText()
		// .toString());
		// } else {
		//
		// Bitmap bitmap = ((BitmapDrawable) dialog_img1
		// .getDrawable()).getBitmap();
		//
		// Bitmap emptyBitmap = Bitmap.createBitmap(
		// bitmap.getWidth(), bitmap.getHeight(),
		// bitmap.getConfig());
		// if (bitmap.sameAs(emptyBitmap)) {
		// dbadapter.UpdateTicketToDb(ticketTypeID, title,
		// desc, null, Name.getText().toString(),
		// date, Email.getText().toString(),
		// Phonnumber.getText().toString(), Fax
		// .getText().toString(), Mobile
		// .getText().toString());
		//
		// } else {
		// byte[] bytes = getBitmapAsByteArray(bitmap);
		// dbadapter.UpdateTicketToDb(ticketTypeID, title,
		// desc, bytes, Name.getText().toString(),
		// date, Email.getText().toString(),
		// Phonnumber.getText().toString(), Fax
		// .getText().toString(), Mobile
		// .getText().toString());
		//
		// }
		//
		// }
		// Toast.makeText(context, "آگهی شما با موفقیت ثبت شد",
		// Toast.LENGTH_SHORT).show();
		// dbadapter.close();
		// ((ShowAdFragment) fragment).updateView();
		// Dialog_report.this.dismiss();
		// }
		//
		// }
		// });

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
