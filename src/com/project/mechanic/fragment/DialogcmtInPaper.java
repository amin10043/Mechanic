package com.project.mechanic.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.project.mechanic.R;
import com.project.mechanic.model.DataBaseAdapter;

public class DialogcmtInPaper extends Dialog {

	private Button btncmt;
	private EditText Cmttxt;
	OnMyDialogResult mDialogResult;
	private DataBaseAdapter dbadapter;
	Context context;
	Fragment f;
	int paperId;

	public DialogcmtInPaper(Fragment f, Context context, int paperId) {
		super(context);
		this.context = context;
		this.f = f;
		this.paperId = paperId;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_addcomment);
		btncmt = (Button) findViewById(R.id.btnComment);
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
