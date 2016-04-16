package com.project.mechanic.fragment;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.project.mechanic.StaticValues;
import com.project.mechanic.adapter.SubAdminAdapter;
import com.project.mechanic.entity.Object;
import com.project.mechanic.entity.SubAdmin;
import com.project.mechanic.entity.Users;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.inter.CommInterface;
import com.project.mechanic.inter.GetAsyncInterface;
import com.project.mechanic.interfaceServer.DateFromServerForLike;
import com.project.mechanic.interfaceServer.GetImageInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.server.GetImageFromServer;
import com.project.mechanic.server.ServerDateForLike;
import com.project.mechanic.service.Saving;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.service.ServiceComm;
import com.project.mechanic.service.UpdatingImage;
import com.project.mechanic.utility.Utility;

public class DialogAdminsPage extends Dialog
		implements CommInterface, AsyncInterface, DateFromServerForLike, GetAsyncInterface, GetImageInterface {
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
	// int GlobalSubId;
	Saving saving;
	String serverDate = "";
	Users userSelected;
	String typeItem = "";
	int counterImage = 0;
	int idUser = 0;

	Map<String, String> params;
	ArrayList<SubAdmin> listAdmin = new ArrayList<SubAdmin>();
	SubAdminAdapter listadapter;
	ImageView divider;

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
		setContentView(R.layout.dialog_admins_page);

		findView();

		fillListView();

		setValue();

		getUserImageList();

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

					typeItem = "getUser";

					ringProgressDialog = ProgressDialog.show(context, "", "لطفا منتظر بمانید...", true);

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
					Toast.makeText(context, "وارد کردن شماره همراه اجباری است", 0).show();
				}
			}
		});

	}

	@Override
	public void CommProcessFinish(String output) {

		if (ringProgressDialog != null)
			ringProgressDialog.dismiss();

		if (typeItem.equals("getUser")) {

			if (output == null || "".equals(output) || "anyType{}".equals(output)) {
				Toast.makeText(context,
						"شماره مورد نظر در نرم افزار ثبت نام نکرده است و یا ممکن است شماره را اشتباه وارد کرده باشید ",
						Toast.LENGTH_LONG).show();
			} else {
				adapter.open();
				if (adapter.countSubAdminPage(ObjectId) < 4) {

					util.parseQuery(output);
					
					userSelected = adapter.getUserbymobailenumber(phoneInput);

					if (userSelected != null)
						if (adapter.IsUserAdmin(userSelected.getId(), (ObjectId))
								|| phoneInput.equals(mainAdmin.getMobailenumber())) {
							Toast.makeText(context, "این شماره قبلا استفاده شده است", 0).show();
						} else

						{

							if (context != null) {

								//////

								ServerDateForLike date = new ServerDateForLike(context);
								date.delegate = DialogAdminsPage.this;
								date.execute("");

								////////

								ringProgressDialog = ProgressDialog.show(context, "", "لطفا منتظر بمانید...", true);

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

						}
				} else
					Toast.makeText(context, "حداکثر تعداد مدیران یک صفحه 4 کاربر می باشد", 0).show();
				adapter.close();
			}
		}
		if (typeItem.equals("getDate")) {

			if (output.contains("Exception") || output.contains("anyType"))
				output = "";

			adapter.open();
			adapter.UpdateImageServerDate(userSelected.getId(), "Users", output);
			adapter.close();

		}
		if (typeItem.equals("getDateImages")) {

			if (output.contains("Exception") || output.contains("anyType"))
				output = "";

			adapter.open();
			adapter.UpdateImageServerDate(idUser, "Users", output);
			adapter.close();

			counterImage++;
			getDateImageList();

		}
	}

	@Override
	public void processFinish(String output) {
		if (ringProgressDialog != null)
			ringProgressDialog.dismiss();
		try {
			int id = Integer.valueOf(output);

			adapter.open();

			if (adapter.countSubAdminPage(ObjectId) < 4) {
				util.parseQuery(output);

				Users ux = adapter.getUserbymobailenumber(phoneInput);

				page = adapter.getObjectbyid(ObjectId);
				mainAdmin = adapter.getUserbyid(page.getUserId());

				if (adapter.IsUserAdmin(ux.getId(), (ObjectId)) || phoneInput.equals(mainAdmin.getMobailenumber())) {
					Toast.makeText(context, "این شماره قبلا استفاده شده است", 0).show();
				} else

				{
					adapter.insertSubAdminPage(id, ObjectId, ux.getId(), AdminId, serverDate);

					fillListView();
					getUserImage();
					in.setText("");
				}

			} else
				Toast.makeText(context, "حداکثر تعداد مدیران یک صفحه 4 کاربر می باشد", 0).show();
			adapter.close();

		} catch (NumberFormatException ex) {
			if (ringProgressDialog != null)
				ringProgressDialog.dismiss();
			Toast.makeText(context, "خطا در ثبت سرور", 0).show();
		}
	}

	@Override
	public void resultDateLike(String output) {
		if (ringProgressDialog != null)
			ringProgressDialog.dismiss();

		if (util.checkError(output) == false) {

			serverDate = output;

			params = new LinkedHashMap<String, String>();

			saving = new Saving(context);
			saving.delegate = DialogAdminsPage.this;

			params.put("TableName", "SubAdmin");

			params.put("ObjectId", String.valueOf(ObjectId));
			params.put("UserId", String.valueOf(userSelected.getId()));
			params.put("AdminId", String.valueOf(AdminId));
			params.put("Date", serverDate);
			params.put("ModifyDate", serverDate);

			params.put("IsUpdate", "0");
			params.put("Id", "0");

			saving.execute(params);

			ringProgressDialog = ProgressDialog.show(context, "", "لطفا منتظر بمانید...", true);

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
			if (ringProgressDialog != null)
				ringProgressDialog.dismiss();
		}

	}

	private void findView() {

		nameMainAdmin = (TextView) findViewById(R.id.nameMainAdmin);
		picMainAdmin = (ImageView) findViewById(R.id.mainAdminPagepic);
		listSubAdmin = (ListView) findViewById(R.id.listSubAdmin);
		addBtn = (ImageView) findViewById(R.id.addBtnPhoneNumber);
		in = (EditText) findViewById(R.id.inputPhoneSubAdmin);

		RelativeLayout LayoutMain = (RelativeLayout) findViewById(R.id.mainAdminLinear);

		pictureParams = new RelativeLayout.LayoutParams(LayoutMain.getLayoutParams());

		pictureParams.width = (int) (util.getScreenwidth() / StaticValues.RateImagePostFragmentPage);
		pictureParams.height = (int) (util.getScreenwidth() / StaticValues.RateImagePostFragmentPage);
		pictureParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		pictureParams.addRule(RelativeLayout.CENTER_VERTICAL);

		pictureParams.setMargins(10, 10, 10, 10);
		divider = (ImageView) findViewById(R.id.divider);

	}

	private void fillListView() {

		adapter.open();

		listAdmin.clear();
		listAdmin = adapter.getAdmin(ObjectId);
		listadapter = new SubAdminAdapter(context, R.layout.row_sub_admin, listAdmin, ObjectId);
		listSubAdmin.setAdapter(listadapter);

		adapter.close();

		if (listAdmin.size() > 0)
			divider.setVisibility(View.VISIBLE);
	}

	private void setValue() {

		adapter.open();

		page = adapter.getObjectbyid(ObjectId);
		mainAdmin = adapter.getUserbyid(page.getUserId());

		adapter.close();

		nameMainAdmin.setText(mainAdmin.getName());
		nameMainAdmin.setTypeface(util.SetFontIranSans());

		String imagePathAdmin = mainAdmin.getImagePath();

		if (imagePathAdmin != null) {
			Bitmap bmp = BitmapFactory.decodeFile(imagePathAdmin);
			if (bmp != null)
				picMainAdmin.setImageBitmap(Utility.getclip(bmp));
			picMainAdmin.setLayoutParams(pictureParams);
		} else {
			picMainAdmin.setImageResource(R.drawable.no_img_profile);
			picMainAdmin.setLayoutParams(pictureParams);
		}

	}

	private void getUserImage() {

		if (context != null) {

			String userImageServerDate = userSelected.getImageServerDate();
			if (userImageServerDate == null)
				userImageServerDate = "";

			UpdatingImage updating = new UpdatingImage(context);
			updating.delegate = DialogAdminsPage.this;
			HashMap<String, String> maps = new LinkedHashMap<String, String>();
			maps.put("tableName", "Users");
			maps.put("Id", String.valueOf(userSelected.getId()));
			maps.put("fromDate", userImageServerDate);
			updating.execute(maps);
		}
	}

	@Override
	public void processFinish(byte[] output) {

		if (output != null) {

			util.CreateFile(output, userSelected.getId(), "Mechanical", "Users", "user", "Users");

		}
		listadapter.notifyDataSetChanged();

		getUserImageDate();

	}

	private void getUserImageDate() {

		if (context != null) {

			ServiceComm getDateService = new ServiceComm(context);
			getDateService.delegate = DialogAdminsPage.this;
			Map<String, String> items = new LinkedHashMap<String, String>();

			items.put("tableName", "getUserImageDate");
			items.put("Id", String.valueOf(userSelected.getId()));

			getDateService.execute(items);
			typeItem = "getDate";

		}

	}

	private void getUserImageList() {

		if (context != null) {

			if (listAdmin.size() > 0) {

				if (counterImage < listAdmin.size()) {

					Users us = null;

					idUser = listAdmin.get(counterImage).getUserId();

					adapter.open();
					us = adapter.getUserbyid(idUser);
					adapter.close();

					String userImageServerDate = us.getImageServerDate();
					if (userImageServerDate == null)
						userImageServerDate = "";

					GetImageFromServer updating = new GetImageFromServer(context);
					updating.delegate = DialogAdminsPage.this;
					HashMap<String, String> maps = new LinkedHashMap<String, String>();
					maps.put("tableName", "Users");
					maps.put("Id", String.valueOf(idUser));
					maps.put("fromDate", userImageServerDate);
					updating.execute(maps);
				} else {

					counterImage = 0;
					getDateImageList();

				}
			}
		}

	}

	@Override
	public void ResultImage(byte[] output) {
		if (output != null) {

			util.CreateFile(output, idUser, "Mechanical", "Users", "user", "Users");

		}
		listadapter.notifyDataSetChanged();

		counterImage++;
		getUserImageList();
	}

	private void getDateImageList() {

		if (context != null) {

			if (counterImage < listAdmin.size()) {

				idUser = listAdmin.get(counterImage).getUserId();

				ServiceComm getDateService = new ServiceComm(context);
				getDateService.delegate = DialogAdminsPage.this;
				Map<String, String> items = new LinkedHashMap<String, String>();

				items.put("tableName", "getUserImageDate");
				items.put("Id", String.valueOf(idUser));

				getDateService.execute(items);
				typeItem = "getDateImages";

			}
		}

	}
}
