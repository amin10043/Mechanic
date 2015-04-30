package com.project.mechanic.fragment;

import java.io.ByteArrayOutputStream;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import com.project.mechanic.R;
import com.project.mechanic.model.DataBaseAdapter;

public class DialogAnadimg extends Dialog {

	protected static final Context Contaxt = null;

	protected static final EditText number = null;

	private static int RESULT_LOAD_IMAGE = 1;
	private static final int SELECT_PICTURE = 1;

	private ImageView imgdialoganad;
	private ImageButton sabt, enseraf;
	private Spinner sp;
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

	public DialogAnadimg(Context context, int resourceId, Fragment fragment,
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
		imgdialoganad = (ImageView) findViewById(R.id.imgDialoganad);
		sabt = (ImageButton) findViewById(R.id.imgsabt);
		enseraf = (ImageButton) findViewById(R.id.imghazf);
		sp = (Spinner) findViewById(R.id.spinner1);

		// dialog_img2.setOnClickListener(new
		// android.view.View.OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// dbadapter = new DataBaseAdapter(context);
		//
		// dbadapter.open();
		//
		// if ((dialog_img1.getDrawable() == null)) {
		//
		// dbadapter.insertTickettoDbemptyImage(dialog_anad_et1
		// .getText().toString(), dialog_anad_et2.getText()
		// .toString(), 1, null, ticketTypeID, 0, 0, 0, 0, 0,
		// ProvinceId, UName.getText().toString(), UEmail
		// .getText().toString(), UPhonnumber
		// .getText().toString(), UFax.getText()
		// .toString(), null, UMobile.getText()
		// .toString());
		//
		// } else {
		//
		// Bitmap bitmap = ((BitmapDrawable) dialog_img1.getDrawable())
		// .getBitmap();
		//
		// Bitmap emptyBitmap = Bitmap.createBitmap(bitmap.getWidth(),
		// bitmap.getHeight(), bitmap.getConfig());
		// if (bitmap.sameAs(emptyBitmap)) {
		// dbadapter.insertTickettoDbemptyImage(dialog_anad_et1
		// .getText().toString(), dialog_anad_et2
		// .getText().toString(), 1, null, ticketTypeID,
		// 0, 0, 0, 0, 0, ProvinceId, UName.getText()
		// .toString(), UEmail.getText()
		// .toString(), UPhonnumber.getText()
		// .toString(), UFax.getText().toString(),
		// null, UMobile.getText().toString());
		// } else {
		// byte[] bytes = getBitmapAsByteArray(bitmap);
		//
		// dbadapter.insertTickettoDb(dialog_anad_et1.getText()
		// .toString(), dialog_anad_et2.getText()
		// .toString(), 1, bytes, null, ticketTypeID, 0,
		// 0, 0, 0, 0, ProvinceId, UName.getText()
		// .toString(), UEmail.getText()
		// .toString(), UPhonnumber.getText()
		// .toString(), UFax.getText().toString(),
		// null, null, UMobile.getText().toString());
		//
		// }
		//
		// }
		// Toast.makeText(context, "آگهی شما با موفقیت ثبت شد",
		// Toast.LENGTH_SHORT).show();
		// dbadapter.close();
		// ((AnadFragment) fragment).updateView();
		// DialogAnadimg.this.dismiss();
		//
		// }
		// });

		// dialog_img1.setOnClickListener(new
		// android.view.View.OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// Intent i = new Intent(
		// Intent.ACTION_PICK,
		// android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		//
		// fragment.getActivity().startActivityFromFragment(fragment, i,
		// RESULT_LOAD_IMAGE);
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

	public void setDialogResult(OnMyDialogResult dialogResult) {
		mDialogResult = dialogResult;
	}

	public View getView() {
		return this.getLayoutInflater().inflate(resourceId, null);
	}

}
