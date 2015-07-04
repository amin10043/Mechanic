package com.project.mechanic.fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mechanic.R;
import com.project.mechanic.entity.Object;
import com.project.mechanic.entity.Users;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class DialogAdminsPage extends Dialog {
	Context context;
	int ObjectId;
	Utility util;
	DataBaseAdapter adapter;
	Object page;
	Users mainAdmin;
	TextView nameMainAdmin;
	ImageView picMainAdmin;
	RelativeLayout.LayoutParams pictureParams;
	ListView listSubAdmin;

	public DialogAdminsPage(Context context, int ObjectId) {
		super(context);
		this.context = context;
		this.ObjectId = ObjectId;
		util = new Utility(context);
		adapter = new DataBaseAdapter(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		setContentView(R.layout.dialog_admins_page);

		nameMainAdmin = (TextView) findViewById(R.id.nameMainAdmin);
		picMainAdmin = (ImageView) findViewById(R.id.mainAdminPagepic);
		listSubAdmin = (ListView) findViewById(R.id.listSubAdmin);

		adapter.open();

		page = adapter.getObjectbyid(ObjectId);
		mainAdmin = adapter.getUserbyid(page.getUserId());
		nameMainAdmin.setText(mainAdmin.getName());

		byte[] mainAdminArrayByte = mainAdmin.getImage();

		RelativeLayout LayoutMain = (RelativeLayout) findViewById(R.id.mainAdminLinear);

		pictureParams = new RelativeLayout.LayoutParams(
				LayoutMain.getLayoutParams());

		pictureParams.width = util.getScreenwidth() / 8;
		pictureParams.height = util.getScreenwidth() / 8;
		pictureParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		pictureParams.setMargins(5, 5, 5, 5);

		if (mainAdminArrayByte != null) {
			Bitmap bmp = BitmapFactory.decodeByteArray(mainAdminArrayByte, 0,
					mainAdminArrayByte.length);

			picMainAdmin.setImageBitmap(util.getRoundedCornerBitmap(bmp, 50));
			picMainAdmin.setLayoutParams(pictureParams);
		} else {
			picMainAdmin.setImageResource(R.drawable.no_img_profile);
			picMainAdmin.setLayoutParams(pictureParams);
		}
		adapter.close();

	}

}
