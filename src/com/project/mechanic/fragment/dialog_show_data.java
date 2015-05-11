package com.project.mechanic.fragment;

import java.io.ByteArrayOutputStream;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.mechanic.R;
import com.project.mechanic.adapter.FavoriteListAdapter;
import com.project.mechanic.entity.Ticket;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

@SuppressLint("ValidFragment")
public class dialog_show_data extends Dialog {

	FavoriteListAdapter fragment;
	private DataBaseAdapter dbadapter;
	Context context;
	int ticketTypeID;
	Utility util;
	Ticket tempItem;
	int resourceId;
	private EditText dialog_anad_1, dialog_anad_2, Name, Mobile, Phonnumber,
			Fax, Email;
	LinearLayout Lheader;
	private ImageView dialog_img1;
	private Button dialog_btn;
	LinearLayout.LayoutParams headerEditParams;

	public dialog_show_data(Context context, int resourceId,
			FavoriteListAdapter favoriteListAdapter, int ticketTypeID) {
		super(context);

		this.resourceId = resourceId;
		this.context = context;
		this.fragment = favoriteListAdapter;
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
		TextView txtemail = (TextView) findViewById(R.id.dialog_show_email);
		TextView txtphone = (TextView) findViewById(R.id.dialog_show_phone);
		TextView txtmobile = (TextView) findViewById(R.id.dialog_show_mobile);
		TextView txtfax = (TextView) findViewById(R.id.dialog_show_fax);
		ImageButton imgbtnclose = (ImageButton) findViewById(R.id.dialog_btn_close);

		dbadapter = new DataBaseAdapter(context);
		dbadapter.open();
		Ticket t = dbadapter.getTicketById(ticketTypeID);
		txtemail.setText(t.getUEmail());
		txtphone.setText(t.getUPhone());
		txtmobile.setText(t.getUMobile());
		txtfax.setText(t.getUFax());
		dbadapter.close();
		imgbtnclose.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog_show_data.this.dismiss();

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
