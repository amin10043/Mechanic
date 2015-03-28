package com.project.mechanic.fragment;

import com.project.mechanic.R;
import com.project.mechanic.fragment.Dialogcmt.OnMyDialogResult;
import com.project.mechanic.model.DataBaseAdapter;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



public class DialogfroumTitle extends Dialog {

	public DialogfroumTitle(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	private Button btntitle;
	private EditText titletxt;
	private EditText titleDestxt;
	 OnMyDialogResult mDialogResult;
	 private DataBaseAdapter dbadapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.dialog_addcomment);
		 btntitle= (Button) findViewById(R.id.btnTitle);
		 titletxt=(EditText)findViewById(R.id.txtTitle);
		 titleDestxt=(EditText)findViewById(R.id.txttitleDes);
		 btntitle.setOnClickListener(new android.view.View.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				if( mDialogResult != null ){
	                mDialogResult.finish(String.valueOf(titletxt.getText()));
	                dbadapter.insertFroumtitletoDb(titletxt.getText().toString());
	                dbadapter.insertFroumdescriptiontoDb(titleDestxt.getText().toString());
	                
	            }
				 DialogfroumTitle.this.dismiss();
				 
			}
		 });
		 
		 
	}

}
