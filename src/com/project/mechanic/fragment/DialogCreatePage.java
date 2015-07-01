package com.project.mechanic.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
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
	String message;
	private DialogfroumTitle dialog;

	public DialogCreatePage(Context context, String message) {
		super(context);
		this.context = context;
		util = new Utility(context);
		this.message = message;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));

		setContentView(R.layout.dialog_create);
		create = (ImageButton) findViewById(R.id.createDialogPage);
		TextView main = (TextView) findViewById(R.id.textDay);
		Currentser = util.getCurrentUser();

		Typeface typeFace = Typeface.createFromAsset(context.getAssets(),
				"fonts/BoldRoya.TTF");
		main.setText(message);
		main.setTypeface(typeFace);

		SharedPreferences realize = context.getSharedPreferences("Id", 0);
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
