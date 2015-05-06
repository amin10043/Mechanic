package com.project.mechanic.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.project.mechanic.R;
import com.project.mechanic.entity.Users;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class DialogcmtInPaper extends Dialog {

	private ImageButton btncmt;
	private EditText Cmttxt;
	OnMyDialogResult mDialogResult;
	private DataBaseAdapter dbadapter;
	Context context;
	Fragment f;
	int paperId;
	Utility util;

	public DialogcmtInPaper(Fragment f, Context context, int resourceId,
			int paperId) {
		super(context);
		this.context = context;
		this.f = f;
		this.paperId = paperId;
		util = new Utility(context);
		Users user = util.getCurrentUser();

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_addcomment);
		btncmt = (ImageButton) findViewById(R.id.btnComment);
		Cmttxt = (EditText) findViewById(R.id.txtCmt);
		btncmt.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dbadapter = new DataBaseAdapter(context);
				dbadapter.open();

				dbadapter.insertCommentInPapertoDb(Cmttxt.getText().toString(),
						paperId, 1, "1");
				dbadapter.close();
				((PaperFragment) f).updateView2();
				DialogcmtInPaper.this.dismiss();

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
