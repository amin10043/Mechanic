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

public class DialogcmtInfroum extends Dialog {

	private Button btncmt;
	private EditText Cmttxt;
	OnMyDialogResult mDialogResult;
	private DataBaseAdapter dbadapter;
	Context context;
	Fragment f;

	public DialogcmtInfroum(Fragment f, Context context, int resourceId) {
		super(context);
		this.context = context;
		this.f = f;
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
				int id = Integer.valueOf(f.getArguments().getString("Id"));
				dbadapter.insertCommentInFroumtoDb(Cmttxt.getText().toString(),
						id, 1, "1", 0, "0", "0");
				dbadapter.close();
				((FroumFragment) f).updateView2();
				DialogcmtInfroum.this.dismiss();

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
