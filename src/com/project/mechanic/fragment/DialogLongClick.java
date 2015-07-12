package com.project.mechanic.fragment;

import java.util.LinkedHashMap;
import java.util.Map;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.project.mechanic.R;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.Deleting;
import com.project.mechanic.utility.ServiceComm;
import com.project.mechanic.utility.Utility;

public class DialogLongClick extends Dialog implements AsyncInterface {
	Context context;
	int source;
	int UserIdObject;
	Utility util;
	RelativeLayout delete, report;
	DataBaseAdapter adapter;
	int Item;
	ServiceComm service;
	Deleting deleting;
	Map<String, String> params;
	ProgressDialog ringProgressDialog;

	Fragment fragment;

	public DialogLongClick(Context context, int source, int UserIdObject,
			int item, Fragment fragment) {
		super(context);
		this.context = context;
		this.source = source;
		this.UserIdObject = UserIdObject;
		util = new Utility(context);
		adapter = new DataBaseAdapter(context);
		this.Item = item;
		this.fragment = fragment;

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));

		setContentView(R.layout.dialog_long_click);
		delete = (RelativeLayout) findViewById(R.id.delete_item);
		report = (RelativeLayout) findViewById(R.id.report_item);

		if (util.getCurrentUser().getId() != UserIdObject)
			delete.setVisibility(View.GONE);
		// source == 1 >>>> froum
		// source == 2 >>>> paper
		// source == 3 >>>> ticket
		// source == 4 >>>> Object

		delete.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (source == 1) {

					if (source == 1) {

						service = new ServiceComm(context);
						service.delegate = DialogLongClick.this;
						Map<String, String> items = new LinkedHashMap<String, String>();
						items.put("DeletingRecord", "DeletingRecord");

						items.put("tableName", "Froum");
						items.put("Id", String.valueOf(Item));

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
					}

				}

				if (source == 3) {
					params = new LinkedHashMap<String, String>();

					deleting = new Deleting(context);
					deleting.delegate = DialogLongClick.this;

					params.put("TableName", "Ticket");
					params.put("Id", String.valueOf(Item));

					deleting.execute(params);

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
			}
		});

	}

	@Override
	public void processFinish(String output) {
		int id = -1;
		try {

			id = Integer.valueOf(output);
			if (source == 1) {

				adapter.open();

				adapter.deleteFroumTitle(Item);
				adapter.deleteCommentFroum(Item);
				adapter.deleteLikeFroum(Item);

				adapter.close();

				dismiss();

				if (ringProgressDialog != null) {
					ringProgressDialog.dismiss();
				}

			}

			if (source == 3) {
				adapter.open();
				adapter.deleteTicketItem(Item);
				adapter.close();

				if (ringProgressDialog != null) {
					ringProgressDialog.dismiss();
				}
				dismiss();

				((AnadFragment) fragment).updateView();

			}

		} catch (NumberFormatException e) {
			if (output != null
					&& !(output.contains("Exception") || output
							.contains("java"))) {

			} else {
				Toast.makeText(context, "خطا در ثبت. پاسخ نا مشخص از سرور",
						Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {

			Toast.makeText(context, "خطا در ثبت", Toast.LENGTH_SHORT).show();
		}
	}

}
