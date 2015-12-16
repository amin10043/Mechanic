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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mechanic.R;
import com.project.mechanic.entity.Users;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.Saving;
import com.project.mechanic.service.ServerDate;
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
	Users currentUser;
	ProgressDialog ringProgressDialog;
	String currentDate;
	TextView titleHeader;
	Button createImage;
	ServerDate sDate;
	String severDate;

	public DialogfroumTitle(Context context, int resourceId, Fragment fragment) {
		super(context);
		// TODO Auto-generated constructor stub
		this.resourceId = resourceId;
		this.context = context;
		this.fragment = fragment;
		dbadapter = new DataBaseAdapter(context);
		utility = new Utility(context);

		date = new PersianDate();
		currentUser = utility.getCurrentUser();
		currentDate = date.todayShamsi();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		if (currentUser == null) {
			// karbar vared nashode ast !!!!!!!
			// nabayad inja bashaaad !!!!!!
			Toast.makeText(context, "ابدتدا باید وارد شوید", Toast.LENGTH_SHORT).show();
			return;
		}
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		setContentView(resourceId);
		btntitle = (Button) findViewById(R.id.btnPdf1_Object);
		titletxt = (EditText) findViewById(R.id.txtTitleP);
		titleDestxt = (EditText) findViewById(R.id.txttitleDes);

		createImage = (Button) findViewById(R.id.createicondialog);
		titleHeader = (TextView) findViewById(R.id.maintextcreate);

		titletxt.setVisibility(View.GONE);
		titleDestxt.setVisibility(View.GONE);
		btntitle.setVisibility(View.GONE);

		createImage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (currentUser != null) {
					titletxt.setVisibility(View.VISIBLE);
					titleDestxt.setVisibility(View.VISIBLE);
					btntitle.setVisibility(View.VISIBLE);

					createImage.setVisibility(View.GONE);
					titleHeader.setVisibility(View.GONE);
				}
			}
		});
		titleHeader.setTypeface(utility.SetFontIranSans());
		btntitle.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				String title = titletxt.getText().toString();
				String description = titleDestxt.getText().toString();

				if (!"".equals(title) && !"".equals(description)) {
					
					sDate = new ServerDate(context);
					sDate.delegate = DialogfroumTitle.this;
					sDate.execute("");
				}else
				{
					Toast.makeText(context, "پر کردن عنوان و توضیحات الزامی است", 0).show();
				}

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

		if (ringProgressDialog != null)
			ringProgressDialog.dismiss();
		int id = -1;
		try {
			id = Integer.valueOf(output);
			dbadapter.open();
			dbadapter.insertFroumtitletoDb(id, titletxt.getText().toString(), titleDestxt.getText().toString(),
					currentUser.getId(), severDate);
			dbadapter.close();
			((FroumtitleFragment) fragment).updateView();
			this.dismiss();

		} catch (NumberFormatException ex) {
			if (output != null && !(output.contains("Exception") || output.contains("java"))) {
				params = new LinkedHashMap<String, String>();
				saving = new Saving(context);
				saving.delegate = DialogfroumTitle.this;

				params.put("TableName", "Froum");
				params.put("Title", titletxt.getText().toString());
				params.put("Description", titleDestxt.getText().toString());
				params.put("UserId", String.valueOf(currentUser.getId()));
				params.put("Date", output);
				params.put("ModifyDate", output);
				params.put("IsUpdate", "0");
				params.put("Id", "0");
				severDate = output;
				saving.execute(params);
				ringProgressDialog = ProgressDialog.show(context, "", "لطفا منتظر بمانید...", true);

				ringProgressDialog.setCancelable(true);
			} else {
				Toast.makeText(context, "خطا در ثبت. پاسخ نا مشخص از سرور", Toast.LENGTH_SHORT).show();
			}
		}

	}
}
