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

public class DialogAnad extends Dialog {

	private Button dialog_btn,dialog_btn1;
	private EditText dialog_anad_et1,dialog_anad_et5;
	OnMyDialogResult mDialogResult;
	private DataBaseAdapter dbadapter;
	int resourceId;
	Context context;
	Fragment fragment;

	public DialogAnad(Context context, int resourceId, Fragment fragment) {
		super(context);
		// TODO Auto-generated constructor stub
		this.resourceId = resourceId;
		this.context = context;
		this.fragment = fragment;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.dialog_addcomment);
		setContentView(resourceId);
		dialog_btn = (Button) findViewById(R.id.dialog_btn);
		dialog_btn1 = (Button) findViewById(R.id.dialog_btn1);
		dialog_anad_et1 = (EditText) findViewById(R.id.dialog_anad_et1);
		dialog_anad_et5 = (EditText) findViewById(R.id.dialog_anad_et5);
		
//		dialog_btn.setOnClickListener(new android.view.View.OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				dbadapter = new DataBaseAdapter(context);
//				dbadapter.open();
//				dbadapter.insertFroumtitletoDb(dialog_anad_et1.getText().toString(),dialog_anad_et2.getText().toString(),1);
//				dbadapter.close();
//				((FroumtitleFragment) fragment).updateView();
//				DialogAnad.this.dismiss();
//
//			}
//		});

	}

	public interface OnMyDialogResult {
		void finish(String result);
	}

	public void setDialogResult(OnMyDialogResult dialogResult) {
		mDialogResult = dialogResult;
	}

}
