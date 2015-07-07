package com.project.mechanic.fragment;

import java.util.ArrayList;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mechanic.R;
import com.project.mechanic.adapter.SubAdminAdapter;
import com.project.mechanic.entity.Object;
import com.project.mechanic.entity.SubAdmin;
import com.project.mechanic.entity.Users;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.ServiceComm;
import com.project.mechanic.utility.Utility;

public class DialogAdminsPage extends Dialog implements AsyncInterface {
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
	ImageView addBtn;
	String phoneInput;
	EditText in;
	ServiceComm service;
	ProgressDialog ringProgressDialog;
	ArrayList<Users> listu;
	int AdminId;

	public DialogAdminsPage(Context context, int ObjectId, int AdminId) {
		super(context);
		this.context = context;
		this.ObjectId = ObjectId;
		util = new Utility(context);
		this.AdminId = AdminId;
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
		addBtn = (ImageView) findViewById(R.id.addBtnPhoneNumber);
		in = (EditText) findViewById(R.id.inputPhoneSubAdmin);
		adapter.open();

		ArrayList<SubAdmin> listAdmin = adapter.getAdmin(ObjectId);
		SubAdminAdapter listadapter = new SubAdminAdapter(context,
				R.layout.row_sub_admin, listAdmin, ObjectId);
		listSubAdmin.setAdapter(listadapter);

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

		addBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				phoneInput = in.getText().toString();

				if (!"".equals(phoneInput)) {

					adapter.open();
					Users u = adapter.getUserbymobailenumber(phoneInput);

					adapter.insertSubAdminPage(ObjectId, u.getId(), AdminId);

					ArrayList<SubAdmin> listAdmin = adapter.getAdmin(ObjectId);

					SubAdminAdapter listadapter = new SubAdminAdapter(context,
							R.layout.row_sub_admin, listAdmin, ObjectId);
					listSubAdmin.setAdapter(listadapter);
					listadapter.notifyDataSetChanged();
					adapter.close();
					in.setText("");
					// service = new ServiceComm(context);
					// service.delegate = DialogAdminsPage.this;
					// Map<String, String> items = new LinkedHashMap<String,
					// String>();
					// items.put("login", "login");
					// items.put("phone", phoneInput);
					//
					// service.execute(items);
					//
					// ringProgressDialog = ProgressDialog.show(context, "",
					// "لطفا منتظر بمانید...", true);
					//
					// ringProgressDialog.setCancelable(true);
					//
					// new Thread(new Runnable() {
					//
					// @Override
					// public void run() {
					//
					// try {
					//
					// Thread.sleep(10000);
					//
					// } catch (Exception e) {
					//
					// }
					// }
					// }).start();

				}
			}
		});
		adapter.close();

	}

	@Override
	public void processFinish(String output) {
		ringProgressDialog.dismiss();
		if (output == null || "".equals(output)) {
			Toast.makeText(
					context,
					"شماره مورد نظر شما یافت نشد و یا ممکن است شماره را اشتباه وارد کرده باشید",
					Toast.LENGTH_SHORT).show();
		} else {
			adapter.open();
			Users u = adapter.getUserbymobailenumber(phoneInput);

			adapter.insertSubAdminPage(ObjectId, u.getId(), AdminId);

			ArrayList<SubAdmin> listAdmin = adapter.getAllAdmin(ObjectId);

			SubAdminAdapter listadapter = new SubAdminAdapter(context,
					R.layout.row_sub_admin, listAdmin, ObjectId);
			adapter.close();

		}

	}
}
