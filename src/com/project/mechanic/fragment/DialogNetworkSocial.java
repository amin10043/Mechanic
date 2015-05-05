package com.project.mechanic.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.project.mechanic.R;

public class DialogNetworkSocial extends Dialog {
	Context context;
	CreateIntroductionFragment fragment;

	String l1, l2, l3, l4, l5, l6;
	EditText inFacebook, inLinkedin, inTwiiter, inWebsite, inGoogle,
			inInstagram;

	ImageButton saveBtn;

	public DialogNetworkSocial(CreateIntroductionFragment fragment,
			Context context, int xmlDesign) {
		super(context);
		this.context = context;
		this.fragment = fragment;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.dialog_network_social);

		saveBtn = (ImageButton) findViewById(R.id.savelink);

		inFacebook = (EditText) findViewById(R.id.dialogFacebook);
		inLinkedin = (EditText) findViewById(R.id.dialogLinkedIn);
		inTwiiter = (EditText) findViewById(R.id.dialogTwitter);
		inWebsite = (EditText) findViewById(R.id.dialogWebsite);
		inGoogle = (EditText) findViewById(R.id.dialogGoogle);
		inInstagram = (EditText) findViewById(R.id.dialogInstagram);

		saveBtn.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				l1 = inFacebook.getText().toString();
				l2 = inLinkedin.getText().toString();
				l3 = inTwiiter.getText().toString();
				l4 = inWebsite.getText().toString();
				l5 = inGoogle.getText().toString();
				l6 = inInstagram.getText().toString();

				if (l1.equals("") & l2.equals("") & l3.equals("")
						& l4.equals("") & l5.equals("") & l6.equals("")) {
					dismiss();
				} else {

					fragment.Lfacebook = l1;
					fragment.Llinkedin = l2;
					fragment.Ltwitter = l3;
					fragment.Lwebsite = l4;
					fragment.Lgoogle = l5;
					fragment.Linstagram = l6;

					Toast.makeText(context, "لینک ها با موفقیت اضافه شدند",
							Toast.LENGTH_SHORT).show();

					dismiss();
				}
			}
		});

	}
}
