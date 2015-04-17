package com.project.mechanic.fragment;

import com.project.mechanic.R;
import com.project.mechanic.fragment.DialogcmtInfroum.OnMyDialogResult;
import com.project.mechanic.model.DataBaseAdapter;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
public class DialogcmtInobject extends Dialog {
	
	 Context context;
	 Fragment f;
	 private Button btncmt;
	 private EditText Cmttxt;
	 OnMyDialogResult mDialogResult;
	 private DataBaseAdapter dbadapter;

	public DialogcmtInobject(Fragment f, Context context,int resourceId) {
		super(context);
		this.context=context;
		this.f = f;
		
	}
	
	 
	 
	 
	 
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
					    dbadapter = new DataBaseAdapter(context);
					    dbadapter.open();
					    int	id = Integer.valueOf(f. getArguments().getString("Id"));
		               dbadapter.insertCommentObjecttoDb(Cmttxt.getText().toString(),1, 1,
		            			"",  1);
		                dbadapter.close();
						((IntroductionFragment) f).updateView3();
						DialogcmtInobject.this.dismiss();
					 
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
