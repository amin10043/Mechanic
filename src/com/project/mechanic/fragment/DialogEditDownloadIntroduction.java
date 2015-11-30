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

public class DialogEditDownloadIntroduction extends Dialog {
	Context context;
	IntroductionEditFragment fragment;

	String l1, l2, l3, l4;
	EditText inCatalog, inPrice, inPDF, inVideo;
	Button saveBtn;
	
	Utility util;

	public DialogEditDownloadIntroduction(IntroductionEditFragment fragment,
			Context context, int xmllayout) {
		super(context);
		this.fragment = fragment;
		this.context = context;
		util = new Utility(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));

		setContentView(R.layout.dialog_edit_link_dn);

		saveBtn = (Button) findViewById(R.id.save);
		
		RelativeLayout rl = (RelativeLayout)findViewById(R.id.layout);
		
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(rl.getLayoutParams());
		lp.width = util.getScreenwidth()/4;
		lp.height  = util.getScreenwidth()/8;
		lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
		
		inCatalog = (EditText) findViewById(R.id.de1);
		inPrice = (EditText) findViewById(R.id.de2);
		inPDF = (EditText) findViewById(R.id.de3);
		inVideo = (EditText) findViewById(R.id.de4);
		
		saveBtn.setLayoutParams(lp);

		saveBtn.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				l1 = inCatalog.getText().toString();
				l2 = inPrice.getText().toString();
				l3 = inPDF.getText().toString();
				l4 = inVideo.getText().toString();

				if (l1.equals("") & l2.equals("") & l3.equals("")
						& l4.equals("")) {
					dismiss();

				} else {
					fragment.Dcatalog = l1;
					fragment.Dprice = l2;
					fragment.Dpdf = l3;
					fragment.Dvideo = l4;

					Toast.makeText(context, "لینک ها با موفقیت اضافه شدند",
							Toast.LENGTH_SHORT).show();

					dismiss();
				}
			}
		});

	}
}
