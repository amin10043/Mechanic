package com.project.mechanic.fragment;

import java.util.LinkedHashMap;
import java.util.Map;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.project.mechanic.R;
import com.project.mechanic.entity.Users;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.Deleting;
import com.project.mechanic.service.Saving;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.utility.Utility;

public class DialogcmtInobject extends Dialog implements AsyncInterface {

	Context context;
	IntroductionFragment f;
	private ImageButton btncmt;
	private EditText Cmttxt;
	OnMyDialogResult mDialogResult;
	private DataBaseAdapter dbadapter;
	Utility util;
	Users CurrentUser;
	// PersianDate persiandate;
	// String currentDate;
	int objectID, commentId;

	Saving saving;
	Deleting deleting;
	Map<String, String> params;

	ProgressDialog ringProgressDialog;

	String serverDate = "";
	ServerDate date;

	public DialogcmtInobject(IntroductionFragment f, Context context,
			int resourceId, int objectID, int commentId) {
		super(context);
		this.context = context;
		this.f = f;
		dbadapter = new DataBaseAdapter(context);
		util = new Utility(context);
		// persiandate = new PersianDate();
		this.objectID = objectID;
		this.commentId = commentId;
		CurrentUser = util.getCurrentUser();

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		setContentView(R.layout.dialog_addcomment);
		btncmt = (ImageButton) findViewById(R.id.btnComment);
		Cmttxt = (EditText) findViewById(R.id.txtCmt);
		// currentDate = persiandate.todayShamsi();
		btncmt.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				date = new ServerDate(context);
				date.delegate = DialogcmtInobject.this;
				date.execute("");

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
			dbadapter.insertCommentObjecttoDb(id, Cmttxt.getText().toString(),
					objectID, CurrentUser.getId(), serverDate, commentId);

			dbadapter.close();
			f.updateList();
			if (ringProgressDialog != null)
				ringProgressDialog.dismiss();
			DialogcmtInobject.this.dismiss();

		} catch (NumberFormatException e) {

			if (output != null
					&& !(output.contains("Exception") || output
							.contains("java"))) {

				dbadapter.open();
				params = new LinkedHashMap<String, String>();
				saving = new Saving(context);
				saving.delegate = DialogcmtInobject.this;

				params.put("TableName", "CommentInObject");

				params.put("Desk", Cmttxt.getText().toString());
				params.put("ObjectId", String.valueOf(objectID));
				params.put("UserId", String.valueOf(CurrentUser.getId()));
				params.put("Date", output);
				params.put("ModifyDate", output);

				params.put("CommentId", String.valueOf(commentId));

				serverDate = output;

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
				dbadapter.close();

			} else {
				Toast.makeText(context, "خطا در ثبت. پاسخ نا مشخص از سرور",
						Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			Toast.makeText(context, "خطا در ثبت", Toast.LENGTH_SHORT).show();
		}
	}

}
