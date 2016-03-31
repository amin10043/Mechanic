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
import android.widget.TextView;
import android.widget.Toast;

public class DialogEditDownloadIntroduction extends Dialog {
	Context context;
	IntroductionEditFragment fragment;

	String l1, l2, l3, l4;
	EditText inCatalog, inPrice, inPDF, inVideo;
	ImageView saveBtn, closeBtn;

	Utility util;

	public DialogEditDownloadIntroduction(IntroductionEditFragment fragment, Context context, int xmllayout) {
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
		setContentView(R.layout.dialog_download_link);
		getWindow().setLayout(util.getScreenwidth() - 20, WindowManager.LayoutParams.WRAP_CONTENT);

		saveBtn = (ImageView) findViewById(R.id.saveDownload);
		closeBtn = (ImageView) findViewById(R.id.closeDialog);

		closeBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dismiss();
			}
		});

		inCatalog = (EditText) findViewById(R.id.d1);
		inPrice = (EditText) findViewById(R.id.d2);
		inPDF = (EditText) findViewById(R.id.d3);
		inVideo = (EditText) findViewById(R.id.d4);

		TextView title = (TextView) findViewById(R.id.title);
		title.setTypeface(util.SetFontIranSans());
		
		inCatalog.setTypeface(util.SetFontIranSans());
		inPrice.setTypeface(util.SetFontIranSans());
		inPDF.setTypeface(util.SetFontIranSans());
		inVideo.setTypeface(util.SetFontIranSans());


		saveBtn.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				l1 = inCatalog.getText().toString();
				l2 = inPrice.getText().toString();
				l3 = inPDF.getText().toString();
				l4 = inVideo.getText().toString();

				if (!l1.equals("") || !l2.equals("") || !l3.equals("") || !l4.equals("")) {

					fragment.Dcatalog = l1;
					fragment.Dprice = l2;
					fragment.Dpdf = l3;
					fragment.Dvideo = l4;
					dismiss();

				} else {

					Toast.makeText(context, "پر کردن یک مقدار اجباری است", 0).show();

				}
			}
		});

	}
}
