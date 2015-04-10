package com.project.mechanic.fragment;

import com.project.mechanic.R;

import android.os.Bundle;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.Fragment;



public class Dialogeml extends Dialog {

	

	
	
	 OnMyDialogResult mDialogResult;
	 
	 Context context;
	 Fragment f;
	


	public Dialogeml(Fragment f, Context context,int resourceId) {
		super(context);
		this.context=context;
		this.f = f;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.dialog_addemail);
	}


	public interface OnMyDialogResult{
	       void finish(String result);
	    }

 public void setDialogResult(OnMyDialogResult dialogResult){
     mDialogResult = dialogResult;
 }

	

}
