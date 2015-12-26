package com.project.mechanic.fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.utility.Utility;

public class EnterDialog extends Dialog {
	Context context;
	Button login, cancel;
	Utility util;

	public EnterDialog(Context context) {
		super(context);
		this.context = context;
		util = new Utility(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		setContentView(R.layout.enter_dialog);
		login = (Button) findViewById(R.id.yesExit);
		cancel = (Button) findViewById(R.id.noExit);

		login.setTypeface(util.SetFontCasablanca());
		cancel.setTypeface(util.SetFontCasablanca());
		TextView txtMessage = (TextView) findViewById(R.id.messageExit);
		txtMessage.setTypeface(util.SetFontIranSans());
		
		LinearLayout lin = (LinearLayout) findViewById(R.id.linear);

		LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(lin.getLayoutParams());
		llp.width = util.getScreenwidth() / 4;
		llp.height = util.getScreenwidth() / 8;
		llp.setMargins(10, 10, 10, 10);

		login.setLayoutParams(llp);
		cancel.setLayoutParams(llp);

		cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dismiss();

			}
		});

		login.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				FragmentTransaction trans = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new LoginFragment());
				trans.commit();
				dismiss();
			}
		});

	}

}
