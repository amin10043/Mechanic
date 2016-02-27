package com.project.mechanic.fragment;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

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
import com.project.mechanic.inter.CommInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.Saving;
import com.project.mechanic.service.ServiceComm;
import com.project.mechanic.utility.Utility;

public class DialogAdminsPage extends Dialog implements CommInterface,
		AsyncInterface {
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
	int GlobalSubId;
	Saving saving;

	Map<String, String> params;

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
//		getWindow().setBackgroundDrawable(
//				new ColorDrawable(android.graphics.Color.TRANSPARENT));
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

		pictureParams.width = util.getScreenwidth() / 4;
		pictureParams.height = util.getScreenwidth() / 4;
		pictureParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		pictureParams.setMargins(5, 5, 5, 5);

		if (mainAdminArrayByte != null) {
			Bitmap bmp = BitmapFactory.decodeByteArray(mainAdminArrayByte, 0,
					mainAdminArrayByte.length);

			picMainAdmin.setImageBitmap(Utility.getclip(bmp));
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
					// /// save date

					service = new ServiceComm(context);
					service.delegate = DialogAdminsPage.this;
					Map<String, String> items = new LinkedHashMap<String, String>();
					items.put("login", "regPhone");
					items.put("phone", phoneInput);

					service.execute(items);

					ringProgressDialog = ProgressDialog.show(context, "",
							"لطفا منتظر بمانید...", true);

					ringProgressDialog.setCancelable(true);

					new Thread(new Runnable() {

						@Override
						public void run() {

							try {

								Thread.sleep(10000);

							} catch (Exception e) {

							}
						}
					}).start();

				} else {
					Toast.makeText(context, "وارد کردن شماره همراه اجباری است",
							0).show();
				}
			}
		});
		adapter.close();

	}

	public void updatingList() {
		ArrayList<SubAdmin> listAdmin = adapter.getAdmin(ObjectId);

		SubAdminAdapter listadapter = new SubAdminAdapter(context,
				R.layout.row_sub_admin, listAdmin, ObjectId);
		listSubAdmin.setAdapter(listadapter);
		adapter.close();
	}

	@Override
	public void CommProcessFinish(String output) {
		ringProgressDialog.dismiss();

		if (output == null || "".equals(output) || "anyType{}".equals(output)) {
			Toast.makeText(
					context,
					"شماره مورد نظر در نرم افزار ثبت نام نکرده است و یا ممکن است شماره را اشتباه وارد کرده باشید ",
					Toast.LENGTH_LONG).show();
		} else {
			adapter.open();
			if (adapter.countSubAdminPage(ObjectId) < 4) {

				util.parseQuery(output);
				Users u = adapter.getUserbymobailenumber(phoneInput);

				if (adapter.IsUserAdmin(u.getId(), (ObjectId))
						|| phoneInput.equals(mainAdmin.getMobailenumber())) {
					Toast.makeText(context, "این شماره قبلا استفاده شده است", 0)
							.show();
				} else

				{

					params = new LinkedHashMap<String, String>();
					saving = new Saving(context);
					saving.delegate = DialogAdminsPage.this;

					params.put("TableName", "SubAdmin");

					params.put("ObjectId", String.valueOf(ObjectId));

					params.put("UserId", String.valueOf(u.getId()));
					GlobalSubId = u.getId();
					params.put("AdminId", String.valueOf(AdminId));

					params.put("IsUpdate", "0");
					params.put("Id", "0");
					saving.execute(params);
					ringProgressDialog = ProgressDialog.show(context, "",
							"لطفا منتظر بمانید...", true);

					ringProgressDialog.setCancelable(true);
					new Thread(new Runnable() {

						@Override
						public void run() {

							try {

								Thread.sleep(10000);

							} catch (Exception e) {

							}
						}
					}).start();
				}
			} else
				Toast.makeText(context,
						"حداکثر تعداد مدیران یک صفحه 4 کاربر می باشد", 0)
						.show();
			adapter.close();
		}
	}

	@Override
	public void processFinish(String output) {
		ringProgressDialog.dismiss();
		try {
			int id = Integer.valueOf(output);

			adapter.open();

			if (adapter.countSubAdminPage(ObjectId) < 4) {
				util.parseQuery(output);

				Users ux = adapter.getUserbymobailenumber(phoneInput);

				page = adapter.getObjectbyid(ObjectId);
				mainAdmin = adapter.getUserbyid(page.getUserId());

				if (adapter.IsUserAdmin(ux.getId(), (ObjectId))
						|| phoneInput.equals(mainAdmin.getMobailenumber())) {
					Toast.makeText(context, "این شماره قبلا استفاده شده است", 0)
							.show();
				} else

				{
					adapter.insertSubAdminPage(id, ObjectId, ux.getId(),
							AdminId);
					ArrayList<SubAdmin> listAdmin = adapter.getAdmin(ObjectId);

					SubAdminAdapter listadapter = new SubAdminAdapter(context,
							R.layout.row_sub_admin, listAdmin, ObjectId);
					listSubAdmin.setAdapter(listadapter);
					listadapter.notifyDataSetChanged();
					in.setText("");
				}

			} else
				Toast.makeText(context,
						"حداکثر تعداد مدیران یک صفحه 4 کاربر می باشد", 0)
						.show();
			adapter.close();

		} catch (NumberFormatException ex) {
			Toast.makeText(context, "خطا در ثبت سرور", 0).show();
		}
	}
}
