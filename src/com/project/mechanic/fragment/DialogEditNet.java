package com.project.mechanic.fragment;

import com.project.mechanic.R;
import com.project.mechanic.utility.Utility;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class DialogEditNet extends Dialog {
	Context context;
	IntroductionEditFragment fragment;

	String l1, l2, l3, l4, l5, l6;
	EditText inFacebook, inLinkedin, inTwiiter, inGoogle, inInstagram;

	ImageView saveBtn, closeBtn;
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
		setContentView(R.layout.dialog_network_social);
		getWindow().setLayout(util.getScreenwidth() - 20, WindowManager.LayoutParams.WRAP_CONTENT);

		inFacebook = (EditText) findViewById(R.id.dialogm1);
		inLinkedin = (EditText) findViewById(R.id.dialogm2);
		inTwiiter = (EditText) findViewById(R.id.dialogm3);
		inGoogle = (EditText) findViewById(R.id.dialogm5);
		inInstagram = (EditText) findViewById(R.id.dialogm6);

		saveBtn = (ImageView) findViewById(R.id.savenetlink);

		closeBtn = (ImageView) findViewById(R.id.closeDialog);

		closeBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dismiss();
			}
		});

		saveBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				l1 = inFacebook.getText().toString();
				l2 = inLinkedin.getText().toString();
				l3 = inTwiiter.getText().toString();
				l5 = inGoogle.getText().toString();
				l6 = inInstagram.getText().toString();

				if (!l1.equals("") || !l2.equals("") || !l3.equals("") || !l5.equals("") || !l6.equals("")) {

					fragment.Dface = l1;
					fragment.Dlink = l2;
					fragment.Dtwt = l3;
					fragment.Dgoogle = l5;
					fragment.Dinstagram = l6;
					dismiss();

				} else {
					Toast.makeText(context, "پر کردن یک مقدار اجباری است", 0).show();

				}
			}
		});

	}

}
