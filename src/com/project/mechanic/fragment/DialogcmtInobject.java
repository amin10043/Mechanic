package com.project.mechanic.fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;

import com.project.mechanic.R;
import com.project.mechanic.entity.Users;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class DialogcmtInobject extends Dialog {

	Context context;
	IntroductionFragment f;
	private ImageButton btncmt;
	private EditText Cmttxt;
	OnMyDialogResult mDialogResult;
	private DataBaseAdapter dbadapter;
	Utility util;
	Users CurrentUser;
	PersianDate persiandate;
	String currentDate;
	int objectID, commentId;

	public DialogcmtInobject(IntroductionFragment f, Context context,
			int resourceId, int objectID, int commentId) {
		super(context);
		this.context = context;
		this.f = f;
		dbadapter = new DataBaseAdapter(context);
		util = new Utility(context);
		persiandate = new PersianDate();
		this.objectID = objectID;
		this.commentId = commentId;

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
		currentDate = persiandate.todayShamsi();
		btncmt.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dbadapter.open();
				CurrentUser = util.getCurrentUser();
				dbadapter.insertCommentObjecttoDb(Cmttxt.getText().toString(),
						objectID, CurrentUser.getId(), currentDate, commentId);

				dbadapter.close();
				f.updateList();
				DialogcmtInobject.this.dismiss();

			}
		});

	}

	public interface OnMyDialogResult {
		void finish(String result);
	}

	public void setDialogResult(OnMyDialogResult dialogResult) {
		mDialogResult = dialogResult;
	}

}
