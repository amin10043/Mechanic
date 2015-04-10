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

public class DialogfroumTitle extends Dialog {

	private Button btntitle;
	private EditText titletxt;
	private EditText titleDestxt;
	OnMyDialogResult mDialogResult;
	private DataBaseAdapter dbadapter;
	int resourceId;
	Context context;
	Fragment fragment;

	public DialogfroumTitle(Context context, int resourceId, Fragment fragment) {
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
		btntitle = (Button) findViewById(R.id.Btntitle);
		titletxt = (EditText) findViewById(R.id.txtTitle);
		titleDestxt = (EditText) findViewById(R.id.txttitleDes);
		
		
		
		btntitle.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dbadapter = new DataBaseAdapter(context);
				dbadapter.open();
				dbadapter.insertFroumtitletoDb(titletxt.getText().toString(),
						titleDestxt.getText().toString(), 1);
				dbadapter.close();
				((FroumtitleFragment) fragment).updateView();
				DialogfroumTitle.this.dismiss();

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
