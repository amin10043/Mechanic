package com.project.mechanic.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.entity.Users;
import com.project.mechanic.utility.Utility;

public class DialogCreatePage extends Dialog {
	Context context;
	Utility util;
	ImageButton create;
	Users Currentser;

	public DialogCreatePage(Context context) {
		super(context);
		this.context = context;
		util = new Utility(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.dialog_create);
		create = (ImageButton) findViewById(R.id.createDialogPage);
		Currentser = util.getCurrentUser();

		if (Currentser == null) {
			create.setBackgroundResource(R.drawable.ic_create_off);
		} else {
			create.setBackgroundResource(R.drawable.ic_create);

		}

		create.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (Currentser == null) {
					Toast.makeText(context, "ابتدا باید وارد شوید",
							Toast.LENGTH_SHORT).show();
				} else {
					FragmentTransaction trans = ((MainActivity) context)
							.getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame,
							new CreateIntroductionFragment());
					trans.commit();
					dismiss();
				}
			}

		});

	}
}
