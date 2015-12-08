package com.project.mechanic.fragment;

import com.project.mechanic.R;
import com.project.mechanic.utility.Utility;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class DialogEditNet extends Dialog {
	Context context;
	IntroductionEditFragment fragment;

	String l1, l2, l3, l4, l5, l6;
	EditText inFacebook, inLinkedin, inTwiiter, inGoogle, inInstagram;

	Button saveBtn;
	Utility util;

	public DialogEditNet(IntroductionEditFragment fragment, Context context, int xmlDesign) {
		super(context);
		this.context = context;
		this.fragment = fragment;
		util = new Utility(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		setContentView(R.layout.dialog_edit_link_net);

		inFacebook = (EditText) findViewById(R.id.dialogm1);
		inLinkedin = (EditText) findViewById(R.id.dialogm2);
		inTwiiter = (EditText) findViewById(R.id.dialogm3);
		inGoogle = (EditText) findViewById(R.id.dialogm5);
		inInstagram = (EditText) findViewById(R.id.dialogm6);

		RelativeLayout rl = (RelativeLayout) findViewById(R.id.layout);

		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(rl.getLayoutParams());
		lp.width = util.getScreenwidth() / 4;
		lp.height = util.getScreenwidth() / 8;
		lp.addRule(RelativeLayout.CENTER_HORIZONTAL);

		saveBtn = (Button) findViewById(R.id.savenetlink);
		saveBtn.setLayoutParams(lp);
		saveBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				l1 = inFacebook.getText().toString();
				l2 = inLinkedin.getText().toString();
				l3 = inTwiiter.getText().toString();
				l5 = inGoogle.getText().toString();
				l6 = inInstagram.getText().toString();

				if (l1.equals("") & l2.equals("") & l3.equals("") & l4.equals("") & l5.equals("") & l6.equals("")) {
					dismiss();
				} else {

					fragment.Dface = l1;
					fragment.Dlink = l2;
					fragment.Dtwt = l3;
					fragment.Dgoogle = l5;
					fragment.Dinstagram = l6;

					Toast.makeText(context, "لینک ها با موفقیت اضافه شدند", Toast.LENGTH_SHORT).show();

					dismiss();

				}
			}
		});

	}

}
