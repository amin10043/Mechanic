package com.project.mechanic.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

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
	Users user;
	PersianDate p;

	public DialogcmtInPaper(Fragment f, Context context, int resourceId,
			int paperId) {
		super(context);
		this.context = context;
		this.f = f;
		this.paperId = paperId;
		util = new Utility(context);
		user = util.getCurrentUser();
		p = new PersianDate();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_addcomment);
		btncmt = (ImageButton) findViewById(R.id.btnComment);
		Cmttxt = (EditText) findViewById(R.id.txtCmt);
		dbadapter = new DataBaseAdapter(context);

		btncmt.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if ("".equals(Cmttxt.getText().toString()))
					(Toast.makeText(context, "لطفا نظر خود را وارد نمایید",
							Toast.LENGTH_LONG)).show();
				else {

					dbadapter.open();

					dbadapter.insertCommentInPapertoDb(Cmttxt.getText()
							.toString(), paperId, user.getId(), p.todayShamsi());
					dbadapter.close();
					((PaperFragment) f).updateView2();
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

}
