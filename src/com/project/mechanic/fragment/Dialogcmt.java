package com.project.mechanic.fragment;

import com.project.mechanic.R;
import com.project.mechanic.model.DataBaseAdapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;



public class Dialogcmt extends Dialog {
	public Dialogcmt(Context context,int resourceId) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	private Button btncmt;
	private EditText Cmttxt;
	 OnMyDialogResult mDialogResult;
	 private DataBaseAdapter dbadapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.dialog_addcomment);
		 btncmt= (Button) findViewById(R.id.btnComment);
		 Cmttxt=(EditText)findViewById(R.id.txtCmt);
		 btncmt.setOnClickListener(new android.view.View.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				if( mDialogResult != null ){
	                mDialogResult.finish(String.valueOf(Cmttxt.getText()));
	                dbadapter.insertCommenttoDb(Cmttxt.getText().toString());
	                
	            }
				 Dialogcmt.this.dismiss();
				 
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
