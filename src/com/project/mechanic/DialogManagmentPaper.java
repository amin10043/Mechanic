package com.project.mechanic;

import java.util.List;

import com.project.mechanic.entity.Paper;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Window;

public class DialogManagmentPaper extends Dialog {
	Context context;
	List<Paper> paperList;
	Fragment fragment;

	public DialogManagmentPaper(Context context, List<Paper> paperList,
			Fragment fragment) {

		super(context);
		this.context = context;
		this.paperList = paperList;
		this.fragment = fragment;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));

		setContentView(R.layout.dialog_managment_paper);
		
		
	}

}
