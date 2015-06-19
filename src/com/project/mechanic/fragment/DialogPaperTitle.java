package com.project.mechanic.fragment;

import java.util.LinkedHashMap;
import java.util.Map;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.mechanic.R;
import com.project.mechanic.entity.Users;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.Saving;
import com.project.mechanic.utility.Utility;

public class DialogPaperTitle extends Dialog implements AsyncInterface {

	private Button btntitle;
	private EditText titletxt;
	private EditText titleDestxt;
	OnMyDialogResult mDialogResult;
	private DataBaseAdapter dbadapter;
	int resourceId;
	Context context;
	Fragment fragment;
	Users CurrentUser;
	Utility utility;
	PersianDate date;
	Saving saving;
	String currentDate;
	Map<String, String> params;
	ProgressDialog ringProgressDialog;

	public DialogPaperTitle(Context context, int resourceId, Fragment fragment) {
		super(context);
		// TODO Auto-generated constructor stub
		this.resourceId = resourceId;
		this.context = context;
		this.fragment = fragment;
		dbadapter = new DataBaseAdapter(context);
		utility = new Utility(context);
		dbadapter.open();
		CurrentUser = utility.getCurrentUser();
		dbadapter.close();
		date = new PersianDate();
		params = new LinkedHashMap<String, String>();

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (CurrentUser == null) {
			// karbar vared nashode ast !!!!!!!
			// nabayad inja bashaaad !!!!!!
			return;
		}
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		setContentView(resourceId);
		btntitle = (Button) findViewById(R.id.btnPdf1_Object);
		titletxt = (EditText) findViewById(R.id.txtTitleP);
		titleDestxt = (EditText) findViewById(R.id.txttitleDes);

		currentDate = date.todayShamsi();

		btntitle.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				saving = new Saving(context);
				saving.delegate = DialogPaperTitle.this;
				params.put("TableName", "Paper");
				params.put("Title", titletxt.getText().toString());
				params.put("Context", titleDestxt.getText().toString());
				params.put("UserId", String.valueOf(CurrentUser.getId()));
				params.put("Date", currentDate);
				params.put("IsUpdate", "0");
				params.put("Id", "0");

				saving.execute(params);

				ringProgressDialog = ProgressDialog.show(context, "",
						"لطفا منتظر بمانید...", true);

				ringProgressDialog.setCancelable(true);

				new Thread(new Runnable() {

					@Override
					public void run() {

						try {

							Thread.sleep(10000);

						} catch (Exception e) {

						}
					}
				}).start();

			}
		});

	}

	public interface OnMyDialogResult {
		void finish(String result);
	}

	public void setDialogResult(OnMyDialogResult dialogResult) {
		mDialogResult = dialogResult;
	}

	@Override
	public void processFinish(String output) {
		ringProgressDialog.dismiss();

		int id = -1;
		try {
			id = Integer.valueOf(output);
			dbadapter.open();
			dbadapter.insertPapertitletoDb(id, titletxt.getText().toString(),
					titleDestxt.getText().toString(), CurrentUser.getId(),
					currentDate);
			dbadapter.close();
			((TitlepaperFragment) fragment).updateView();
			DialogPaperTitle.this.dismiss();

		} catch (Exception ex) {
			Toast.makeText(context, "خطا در ثبت", Toast.LENGTH_SHORT).show();
		}

	}

}
