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

	
	
	private Button btntitle;
	private EditText titletxt;
	private EditText titleDestxt;
	OnMyDialogResult mDialogResult;
	private DataBaseAdapter dbadapter;
	int resourceId ; 
	public DialogfroumTitle(Context context,int resourceId) {
			super(context);
			// TODO Auto-generated constructor stub
			this.resourceId= resourceId; 
		}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 //setContentView(R.layout.dialog_addcomment);
		setContentView(resourceId);
		 btntitle= (Button) findViewById(R.id.Btntitle);
		 titletxt=(EditText)findViewById(R.id.txtTitle);
		 titleDestxt=(EditText)findViewById(R.id.txttitleDes);
		 btntitle.setOnClickListener(new android.view.View.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				if( mDialogResult != null ){
	                mDialogResult.finish(String.valueOf(titletxt.getText()));
	                dbadapter.insertFroumtitletoDb(titletxt.getText().toString(),titleDestxt.getText().toString(),1);
	                
	                
	            }
				 DialogfroumTitle.this.dismiss();
				 
			}
		 });
		 
		 
	}
	
	public interface OnMyDialogResult{
	       void finish(String result);
	    }

    public void setDialogResult(OnMyDialogResult dialogResult){
     mDialogResult = dialogResult;
 }

}
