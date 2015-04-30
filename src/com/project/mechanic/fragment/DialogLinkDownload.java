package com.project.mechanic.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.project.mechanic.R;

public class DialogLinkDownload extends Dialog {
	Context context;
	CreateIntroductionFragment fragment;

	String l1, l2, l3, l4, l5, l6;
	EditText inCatalog, inPrice, inPDF, inVideo;
	ImageButton saveBtn;

	public DialogLinkDownload(CreateIntroductionFragment fragment,
			Context context, int xmlDesign) {
		super(context);
		this.context = context;
		this.fragment = fragment;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.dialog_download_link);

		saveBtn = (ImageButton) findViewById(R.id.saveDownload);

		inCatalog = (EditText) findViewById(R.id.dialogCatalog);
		inPrice = (EditText) findViewById(R.id.dialogPrice);
		inPDF = (EditText) findViewById(R.id.dialogPdf);
		inVideo = (EditText) findViewById(R.id.dialogVideoe);

		l1 = inCatalog.getText().toString();
		l2 = inPrice.getText().toString();
		l3 = inPDF.getText().toString();
		l4 = inVideo.getText().toString();

		saveBtn.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				fragment.Lcatalog = l1;
				fragment.Lprice = l2;
				fragment.Lpdf = l3;
				fragment.Lvideo = l4;

				Toast.makeText(context, "لینک ها با موفقیت اضافه شدند",
						Toast.LENGTH_SHORT).show();

				dismiss();

			}
		});

	}
}
