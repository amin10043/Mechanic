package com.project.mechanic.fragment;

import java.util.LinkedHashMap;
import java.util.Map;

import android.app.Dialog;
import android.content.Context;
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

public class DialogfroumTitle extends Dialog implements AsyncInterface {

	private Button btntitle;
	private EditText titletxt;
	private EditText titleDestxt;
	OnMyDialogResult mDialogResult;
	private DataBaseAdapter dbadapter;
	Utility utility;
	int resourceId;
	Context context;
	Fragment fragment;
	PersianDate date;
	Saving saving;
	Map<String, String> params;
	Users user;
	String currentDate;

	public DialogfroumTitle(Context context, int resourceId, Fragment fragment) {
		super(context);
		// TODO Auto-generated constructor stub
		this.resourceId = resourceId;
		this.context = context;
		this.fragment = fragment;
		dbadapter = new DataBaseAdapter(context);
		utility = new Utility(context);
		params = new LinkedHashMap<String, String>();
		saving = new Saving(context);
		saving.delegate = this;
		date = new PersianDate();
		user = utility.getCurrentUser();
		currentDate = date.todayShamsi();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		if (user == null) {
			// karbar vared nashode ast !!!!!!!
			// nabayad inja bashaaad !!!!!!
			return;
		}
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(resourceId);
		btntitle = (Button) findViewById(R.id.btnPdf1_Object);
		titletxt = (EditText) findViewById(R.id.txtTitleP);
		titleDestxt = (EditText) findViewById(R.id.txttitleDes);

		btntitle.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				params.put("TableName", "Froum");
				params.put("Title", titletxt.getText().toString());
				params.put("Description", titleDestxt.getText().toString());
				params.put("UserId", String.valueOf(user.getId()));
				params.put("Date", currentDate);

				saving.execute(params);
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
		int id = -1;
		try {
			id = Integer.valueOf(output);
			dbadapter.open();
			dbadapter
					.insertFroumtitletoDb(id, titletxt.getText().toString(),
							titleDestxt.getText().toString(), user.getId(),
							currentDate);
			dbadapter.close();
			((FroumtitleFragment) fragment).updateView();
			DialogfroumTitle.this.dismiss();

		} catch (Exception ex) {
			Toast.makeText(context, "خطا در ثبت", Toast.LENGTH_SHORT).show();
		}

	}

}
