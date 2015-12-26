package com.project.mechanic.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.mechanic.R;
import com.project.mechanic.utility.Utility;

public class ExitDialog extends Dialog {
	Context context;
	Button yes, no;
	Utility util;

	public ExitDialog(Context context) {
		super(context);
		this.context = context;
		util = new Utility(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		setContentView(R.layout.exit_dialog_layout);

		yes = (Button) findViewById(R.id.yesExit);
		no = (Button) findViewById(R.id.noExit);
		TextView txtMessage = (TextView) findViewById(R.id.messageExit);
		txtMessage.setTypeface(util.SetFontIranSans());

		yes.setTypeface(util.SetFontCasablanca());
		no.setTypeface(util.SetFontCasablanca());

		LinearLayout lin = (LinearLayout) findViewById(R.id.linear);

		LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(lin.getLayoutParams());
		llp.width = util.getScreenwidth() / 4;
		llp.height = util.getScreenwidth() / 8;
		llp.setMargins(10, 10, 10, 10);

		yes.setLayoutParams(llp);
		no.setLayoutParams(llp);

		no.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dismiss();

			}
		});

		yes.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent setIntent = new Intent(Intent.ACTION_MAIN);
				setIntent.addCategory(Intent.CATEGORY_HOME);
				setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				getContext().startActivity(setIntent);

				((Activity) context).finish();
			}
		});

	}

}
