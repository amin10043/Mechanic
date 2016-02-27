package com.project.mechanic.fragment;

import java.util.LinkedHashMap;
import java.util.Map;

import com.project.mechanic.R;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.inter.CommInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.Deleting;
import com.project.mechanic.service.ServiceComm;
import com.project.mechanic.utility.Utility;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class DialogAreYouSure extends Dialog implements AsyncInterface, CommInterface {
	Context context;
	Utility util;
	String source;
	int itemId;
	DataBaseAdapter dbAdapter;

	static String pageLable = "صفحه";
	static String PageFolloedLable = "دنبال شده ها";
	static String ticketlable = "آگهی";
	static String paperLable = "مقالات";
	static String FroumLable = "گفتگو";

	Deleting deleting;
	Map<String, String> params;
	ProgressDialog ringProgressDialog;
	Fragment fragment;
	ServiceComm service;

	public DialogAreYouSure(Context context, String source, int itemId, Fragment fragment) {
		super(context);
		this.context = context;
		this.source = source;
		util = new Utility(context);
		this.itemId = itemId;
		dbAdapter = new DataBaseAdapter(context);
		this.fragment = fragment;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_are_you_sure);

		Button yes = (Button) findViewById(R.id.yesBn);
		Button no = (Button) findViewById(R.id.noBtn);

		yes.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (source.equals(pageLable)) {

				}

				if (source.equals(PageFolloedLable)) {

				}
				if (source.equals(ticketlable)) {

					params = new LinkedHashMap<String, String>();

					deleting = new Deleting(context);
					deleting.delegate = DialogAreYouSure.this;

					params.put("TableName", "Ticket");
					params.put("Id", String.valueOf(itemId));

					deleting.execute(params);

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

				if (source.equals(paperLable)) {

					service = new ServiceComm(context);
					service.delegate = DialogAreYouSure.this;
					Map<String, String> items = new LinkedHashMap<String, String>();
					items.put("DeletingRecord", "DeletingRecord");

					items.put("tableName", "Paper");
					items.put("Id", String.valueOf(itemId));

					service.execute(items);

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

				if (source.equals(FroumLable)) {

					service = new ServiceComm(context);
					service.delegate = DialogAreYouSure.this;
					Map<String, String> items = new LinkedHashMap<String, String>();
					items.put("DeletingRecord", "DeletingRecord");

					items.put("tableName", "Froum");
					items.put("Id", String.valueOf(itemId));

					service.execute(items);

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
		});
		no.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				dismiss();

			}
		});
	}

	@Override
	public void processFinish(String output) {

		if (!output.contains("Exception")) {

			if (source.equals(ticketlable)) {

				dbAdapter.open();
				dbAdapter.deleteTicketItem(itemId);
				dbAdapter.close();

				if (ringProgressDialog != null) {
					ringProgressDialog.dismiss();
				}
				dismiss();

				((DisplayPersonalInformationFragment) fragment).FillExpandListView();

			}
		}
	}

	@Override
	public void CommProcessFinish(String output) {

		if (!output.contains("Exception")) {

			if (source.equals(paperLable)) {
				dbAdapter.open();

				dbAdapter.deletePaperTitle(itemId);
				dbAdapter.deleteCommentPaper(itemId);

				dbAdapter.close();
				dismiss();

				if (ringProgressDialog != null) {
					ringProgressDialog.dismiss();
				}
				((DisplayPersonalInformationFragment) fragment).FillExpandListView();

			}

			if (source.equals(FroumLable)) {

				dbAdapter.open();

				dbAdapter.deleteFroumTitle(itemId);
				dbAdapter.deleteCommentFroum(itemId);
				dbAdapter.deleteLikeFroum(itemId);

				dbAdapter.close();

				dismiss();

				if (ringProgressDialog != null) {
					ringProgressDialog.dismiss();
				}

				((DisplayPersonalInformationFragment) fragment).FillExpandListView();

			}

		}

	}
}
