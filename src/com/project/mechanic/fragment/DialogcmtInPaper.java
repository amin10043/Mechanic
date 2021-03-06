package com.project.mechanic.fragment;

import java.util.LinkedHashMap;
import java.util.Map;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.entity.Users;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.Saving;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.utility.Utility;

public class DialogcmtInPaper extends Dialog implements AsyncInterface {

	private ImageButton btncmt;
	private EditText Cmttxt;
	OnMyDialogResult mDialogResult;
	private DataBaseAdapter dbadapter;
	Context context;
	Fragment f;
	int paperId;
	Utility util;
	Users currentuser;
	// PersianDate p;

	Saving saving;
	Map<String, String> params;
	ProgressDialog ringProgressDialog;

	String serverDate = "";
	ServerDate date;

	// متغیر کد برای پیدا کردن مبدا ارسالی برای آپدیت کردن لیست می باشد
	int code;

	public DialogcmtInPaper(Fragment f, Context context, int resourceId, int paperId, int code) {
		super(context);
		this.context = context;
		this.f = f;
		this.paperId = paperId;
		util = new Utility(context);
		currentuser = util.getCurrentUser();
		this.code = code;
		// p = new PersianDate();
		dbadapter = new DataBaseAdapter(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

		setContentView(R.layout.dialog_addcomment);
		btncmt = (ImageButton) findViewById(R.id.btnComment);
		Cmttxt = (EditText) findViewById(R.id.txtCmt);

		btncmt.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if ("".equals(Cmttxt.getText().toString()))
					(Toast.makeText(context, "لطفا نظر خود را وارد نمایید", Toast.LENGTH_LONG)).show();
				else {

					if (currentuser == null) {
						(Toast.makeText(context, "برای ثبت نظر ابتدا باید وارد شوید.", Toast.LENGTH_LONG)).show();
					} else {

						date = new ServerDate(context);
						date.delegate = DialogcmtInPaper.this;
						date.execute("");

					}
					DialogcmtInPaper.this.dismiss();
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

		if (!"".equals(output) && output != null && !(output.contains("Exception") || output.contains("java"))) {
			int id = -1;

			try {
				id = Integer.valueOf(output);
				dbadapter.open();
				// dbadapter.insertCommentInPapertoDb(Cmttxt.getText().toString(),
				// paperId, currentuser.getId(), serverDate);
				dbadapter.close();

				final SharedPreferences realizeIdPaper = context.getSharedPreferences("Id", 0);

				if (code == 1) {
					// az PaperWithoutComment vared shod
					((PaperWithoutComment) f).setCountComment();

					realizeIdPaper.edit().putInt("main_Id", 1378).commit();

					FragmentTransaction trans = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
					PaperFragment fragment = new PaperFragment();
					trans.setCustomAnimations(R.anim.pull_in_left, R.anim.push_out_right);
					Bundle b = new Bundle();
					b.putString("Id", String.valueOf(paperId));
					fragment.setArguments(b);

					trans.replace(R.id.content_frame, fragment);
					trans.commit();
					if (ringProgressDialog != null) {
						ringProgressDialog.dismiss();
					}
				}
				if (code == 2) {
					// az paper fragment vard shode
					((PaperFragment) f).FillListView();
					if (ringProgressDialog != null) {
						ringProgressDialog.dismiss();
					}

				}

			} catch (NumberFormatException e) {

				if (!"".equals(output) && output != null
						&& !(output.contains("Exception") || output.contains("java"))) {

					params = new LinkedHashMap<String, String>();
					saving = new Saving(context);
					saving.delegate = DialogcmtInPaper.this;

					params.put("TableName", "CommentInPaper");

					params.put("Desk", Cmttxt.getText().toString());
					params.put("PaperId", String.valueOf(paperId));
					params.put("UserId", String.valueOf(currentuser.getId()));
					params.put("IsUpdate", "0");
					params.put("Date", output);
					params.put("ModifyDate", output);

					params.put("Id", "0");
					serverDate = output;

					saving.execute(params);

					ringProgressDialog = ProgressDialog.show(context, "", "لطفا منتظر بمانید...", true);

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

				} else
					Toast.makeText(context, "خطا در ثبت. پاسخ نا مشخص از سرور", Toast.LENGTH_SHORT).show();
			}

		} else
			Toast.makeText(context, "خطا در ثبت", 0).show();

	}

}
