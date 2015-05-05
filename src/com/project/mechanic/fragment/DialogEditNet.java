package com.project.mechanic.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.project.mechanic.R;

public class DialogEditNet extends Dialog {
	Context context;
	IntroductionEditFragment fragment;

	String l1, l2, l3, l4, l5, l6;
	EditText inFacebook, inLinkedin, inTwiiter, inWebsite, inGoogle,
			inInstagram;

	ImageButton saveBtn;

	public DialogEditNet(IntroductionEditFragment fragment, Context context,
			int xmlDesign) {
		super(context);
		this.context = context;
		this.fragment = fragment;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_edit_link_net);

		inFacebook = (EditText) findViewById(R.id.dialogm1);
		inLinkedin = (EditText) findViewById(R.id.dialogm2);
		inTwiiter = (EditText) findViewById(R.id.dialogm3);
		inWebsite = (EditText) findViewById(R.id.dialogm4);
		inGoogle = (EditText) findViewById(R.id.dialogm5);
		inInstagram = (EditText) findViewById(R.id.dialogm6);

		saveBtn = (ImageButton) findViewById(R.id.savenetlink);

		saveBtn.setOnClickListener(new View.OnClickListener() {

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

					fragment.Dface = l1;
					fragment.Dlink = l2;
					fragment.Dtwt = l3;
					fragment.Dweb = l4;
					fragment.Dgoogle = l5;
					fragment.Dinstagram = l6;

					Toast.makeText(context, "لینک ها با موفقیت اضافه شدند",
							Toast.LENGTH_SHORT).show();

					dismiss();

				}
			}
		});

	}

}
