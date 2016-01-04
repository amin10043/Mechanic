package com.project.mechanic.fragment;

import java.util.LinkedHashMap;
import java.util.Map;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mechanic.R;
import com.project.mechanic.entity.Users;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.inter.SaveAsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.Saving;
import com.project.mechanic.service.SavingImage;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.utility.Utility;

public class DialogAnad extends Dialog implements AsyncInterface,
		SaveAsyncInterface {

	protected static final Context Contaxt = null;

	protected static final EditText number = null;

	private static int RESULT_LOAD_IMAGE = 1;

	private ImageView dialog_img1;
	private Button dialog_img2;
	private EditText dialog_anad_et1, dialog_anad_et2, UMobile, UPhonnumber,
			UFax, UEmail, Day;
	OnMyDialogResult mDialogResult;
	private DataBaseAdapter dbadapter;
	int resourceId;
	Context context;
	Fragment fragment;
	int ticketTypeID;
	int iduser;
	int emailCheck = 0;
	int nameCheck = 0;
	int faxCheck = 0;
	int phoneCheck = 0;
	int mobileCheck = 0;
	Utility util;
	Saving saving;
	Map<String, String> params;
	LinearLayout.LayoutParams headerEditParams;
	LinearLayout Lheader;
	String titel;
	String Bytimage;
	SavingImage savingImage;
	byte[] image;
	int gId = -1;

	TextView UName;
	int roz = 0;

	int ProvinceId;
	Users u;
	protected byte[] img;
	String TABLE_NAME = "Ticket";
	ServerDate serverDate;
	String gServerDate = "";
	ProgressDialog ringProgressDialog;

	public DialogAnad(Context context, int resourceId, Fragment fragment,
			int ticketTypeID, int ProvinceId) {
		super(context);
		this.resourceId = resourceId;
		this.context = context;
		this.fragment = fragment;
		this.ticketTypeID = ticketTypeID;
		this.ProvinceId = ProvinceId;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		setContentView(resourceId);
		dialog_img1 = (ImageView) findViewById(R.id.dialog_img1);
		dialog_img2 = (Button) findViewById(R.id.dialog_img2);
		dialog_anad_et1 = (EditText) findViewById(R.id.dialog_anad_et1);
		dialog_anad_et2 = (EditText) findViewById(R.id.dialog_anad_et2);
		Lheader = (LinearLayout) findViewById(R.id.linDialogImg);
		UName = (TextView) findViewById(R.id.name);
		UMobile = (EditText) findViewById(R.id.mobile);
		UEmail = (EditText) findViewById(R.id.email);
		UFax = (EditText) findViewById(R.id.fax);
		UPhonnumber = (EditText) findViewById(R.id.phone);
		Day = (EditText) findViewById(R.id.EditDay);

		util = new Utility(context);
		headerEditParams = new LinearLayout.LayoutParams(
				Lheader.getLayoutParams());
		headerEditParams.height = util.getScreenHeight() / 5;
		headerEditParams.width = util.getScreenHeight() / 5;
		dialog_img1.setLayoutParams(headerEditParams);
		dbadapter = new DataBaseAdapter(context);
		u = util.getCurrentUser();

		if (u != null) {

			UName.setText(u.getName());
			UMobile.setText(u.getMobailenumber());
			if (u.getEmail() != null) {
				UEmail.setText(u.getEmail());
			}
			if (u.getFaxnumber() != null) {
				UFax.setText(u.getFaxnumber());
			}
			if (u.getPhonenumber() != null) {
				UPhonnumber.setText(u.getPhonenumber());
			}
		}
		dialog_img2.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (!"".equals(Day.getText().toString())) {
					roz = Integer.parseInt(Day.getText().toString());
				}

				if ("".equals(dialog_anad_et1.getText().toString())
						|| "".equals(dialog_anad_et2.getText().toString())
						|| roz <= 0) {

					Toast.makeText(
							context,
							" عنوان آگهی .شرح آگهی.اعتبار آگهی نمی تواند خالی باشد",
							Toast.LENGTH_LONG).show();

				} else {

					serverDate = new ServerDate(context);
					serverDate.delegate = DialogAnad.this;
					serverDate.execute("");
					ringProgressDialog = ProgressDialog.show(context, null,
							"لطفا منتظر بمانید.");
				}
			}
		});
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

	public interface OnMyDialogResult {
		void finish(String result);
	}

	public void setDialogResult(OnMyDialogResult dialogResult) {
		mDialogResult = dialogResult;
	}

	public View getView() {
		return this.getLayoutInflater().inflate(resourceId, null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void processFinish(String output) {
		String title = dialog_anad_et1.getText().toString();
		String desc = dialog_anad_et2.getText().toString();
		// Integer.parseInt(Day.getText().toString());
		if (!"".equals(Day.getText().toString())) {
			roz = Integer.parseInt(Day.getText().toString());
		}

		if (ringProgressDialog != null) {
			ringProgressDialog.dismiss();
		}
		try {
			gId = Integer.valueOf(output);
			dbadapter.open();

			dbadapter.insertTickettoDbemptyImage(gId, dialog_anad_et1.getText()
					.toString(), dialog_anad_et2.getText().toString(), u
					.getId(), gServerDate, ticketTypeID, ProvinceId, UName
					.getText().toString(), UEmail.getText().toString(),
					UPhonnumber.getText().toString(),
					UFax.getText().toString(), null, UMobile.getText()
							.toString(), roz);
			dbadapter.close();

			if (dialog_img1.getDrawable() != null) {

				Bitmap bitmap = ((BitmapDrawable) dialog_img1.getDrawable())
						.getBitmap();

				Bitmap emptyBitmap = Bitmap.createBitmap(bitmap.getWidth(),
						bitmap.getHeight(), bitmap.getConfig());
				if (!bitmap.sameAs(emptyBitmap)) {
					image = Utility.CompressBitmap(bitmap);
					savingImage = new SavingImage(context);
					Map<String, Object> it = new LinkedHashMap<String, Object>();
					it.put("tableName", "Ticket");
					it.put("fieldName", "Image");
					it.put("id", gId);
					it.put("Image", image);

					savingImage.delegate = this;
					savingImage.execute(it);
					ringProgressDialog = ProgressDialog.show(context, null,
							"لطفا منتظر بمانید.");
				}
			} else {
				Toast.makeText(context, "آگهی شما با موفقیت ثبت شد",
						Toast.LENGTH_SHORT).show();
				((AnadFragment) fragment).updateView();
				this.dismiss();
			}

		} catch (NumberFormatException ex) {
			if (!output.contains("SoapFault") || !output.contains("java")
					|| !output.contains("Exception")) {
				if ("".equals(title) || "".equals(desc) || roz <= 0) {

					Toast.makeText(
							context,
							" عنوان آگهی .شرح آگهی.اعتبار آگهی نمی تواند خالی باشد",
							Toast.LENGTH_LONG).show();

				} else if ("".equals(UEmail.getText().toString())
						&& "".equals(UPhonnumber.getText().toString())
						&& "".equals(UFax.getText().toString())
						&& "".equals(UMobile.getText().toString())) {
					Toast.makeText(context,
							"یکی از فیلد های تطلاعات تماس اجباریست",
							Toast.LENGTH_LONG).show();
				} else {
					params = new LinkedHashMap<String, String>();
					saving = new Saving(context);
					saving.delegate = DialogAnad.this;

					params.put("TableName", "Ticket");
					params.put("Title", dialog_anad_et1.getText().toString());
					//params.put("Desc", dialog_anad_et2.getText().toString());
					params.put("UserId", String.valueOf(u.getId()));
					params.put("TypeId", String.valueOf(ticketTypeID));
					params.put("ProvinceId", String.valueOf(ProvinceId));
					params.put("Date", output);
					params.put("ModifyDate", output);

					params.put("UName", UName.getText().toString());
					params.put("UEmail", UEmail.getText().toString());
					params.put("UPhonnumber", UPhonnumber.getText().toString());
					params.put("UFax", UFax.getText().toString());
					params.put("UMobile", UMobile.getText().toString());
					params.put("IsUpdate", "0");
					params.put("Id", "0");
					params.put("Day", String.valueOf(roz));
					gServerDate = output;
					saving.execute(params);
					ringProgressDialog = ProgressDialog.show(context, "",
							"لطفا منتظر بمانید...", true);
				}
			} else {
				Toast.makeText(context, "خطا در ثبت", Toast.LENGTH_SHORT)
						.show();
			}
		} catch (Exception ex) {
			Toast.makeText(context, "خطا در ثبت", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void processFinishSaveImage(String output) {

		if (ringProgressDialog != null) {
			ringProgressDialog.dismiss();
		}
		try {
			Integer.valueOf(output);
			dbadapter.open();
			if (image != null)
				util.CreateFile(image, gId, "Mechanical", "Ticket", "ticket",
						"Ticket");
			dbadapter.UpdateImageServerDate(gId, "Ticket", gServerDate);
			// dbadapter.updateTicketImage(gId, image);
			dbadapter.close();

			Toast.makeText(context, "آگهی شما با موفقیت ثبت شد",
					Toast.LENGTH_SHORT).show();
			((AnadFragment) fragment).updateView();
			DialogAnad.this.dismiss();
			dbadapter.close();
		} catch (Exception ex) {
			Toast.makeText(context, "خطا در ثبت", Toast.LENGTH_SHORT).show();
		}

	}

}
