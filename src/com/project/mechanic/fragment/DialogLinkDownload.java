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
import android.widget.TextView;
import android.widget.Toast;

public class DialogLinkDownload extends Dialog {
	Context context;
	CreateIntroductionFragment fragment;

	String l1, l2, l3, l4, l5, l6;
	EditText inCatalog, inPrice, inPDF, inVideo;
	ImageView saveBtn, closeBtn;
	Utility util;

	public DialogLinkDownload(CreateIntroductionFragment fragment, Context context, int xmlDesign) {
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
		setContentView(R.layout.dialog_download_link);
		getWindow().setLayout(util.getScreenwidth() - 20, WindowManager.LayoutParams.WRAP_CONTENT);

		saveBtn = (ImageView) findViewById(R.id.saveDownload);
		closeBtn = (ImageView) findViewById(R.id.closeDialog);

		inCatalog = (EditText) findViewById(R.id.d1);
		inPrice = (EditText) findViewById(R.id.d2);
		inPDF = (EditText) findViewById(R.id.d3);
		inVideo = (EditText) findViewById(R.id.d4);

		TextView title = (TextView) findViewById(R.id.title);

		title.setTypeface(util.SetFontIranSans());

		closeBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dismiss();
			}
		});
		saveBtn.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				l1 = inCatalog.getText().toString();
				l2 = inPrice.getText().toString();
				l3 = inPDF.getText().toString();
				l4 = inVideo.getText().toString();

				if (!l1.equals("") || !l2.equals("") || !l3.equals("") || !l4.equals("")) {

					fragment.Lcatalog = l1;
					fragment.Lprice = l2;
					fragment.Lpdf = l3;
					fragment.Lvideo = l4;
					dismiss();

				} else {
					Toast.makeText(context, "پر کردن یک مقدار اجباری است", 0).show();
				}

				// Toast.makeText(context, "لینک ها با موفقیت اضافه شدند",
				// Toast.LENGTH_SHORT).show();
				// Toast.makeText(context, fragment.Lcatalog,
				// Toast.LENGTH_SHORT).show();

			}
		});

	}
}
