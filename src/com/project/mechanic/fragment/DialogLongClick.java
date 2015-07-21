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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.project.mechanic.R;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.inter.CommInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.Deleting;
import com.project.mechanic.service.Saving;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.utility.ServiceComm;
import com.project.mechanic.utility.Utility;

public class DialogLongClick extends Dialog implements AsyncInterface,
		CommInterface {
	Context context;
	int source;
	int UserIdObject;
	Utility util;
	RelativeLayout delete;
	ImageView report;
	DataBaseAdapter adapter;
	int Item;
	ServiceComm service;
	Deleting deleting;
	Map<String, String> params;
	ProgressDialog ringProgressDialog;

	Fragment fragment;
	Saving saving;
	String desc;
	ServerDate date;

	public DialogLongClick(Context context, int source, int UserIdObject,
			int item, Fragment fragment, String desc) {
		super(context);
		this.context = context;
		this.source = source;
		this.UserIdObject = UserIdObject;
		util = new Utility(context);
		adapter = new DataBaseAdapter(context);
		this.Item = item;
		this.fragment = fragment;
		this.desc = desc;

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));

		setContentView(R.layout.dialog_long_click);
		delete = (RelativeLayout) findViewById(R.id.delete_item);
		report = (ImageView) findViewById(R.id.report_item);

		if (util.getCurrentUser() == null) {
			dismiss();
		} else if (util.getCurrentUser().getId() != UserIdObject)
			delete.setVisibility(View.GONE);

		// source == 1 >>>> froum
		// source == 2 >>>> paper
		// source == 3 >>>> ticket
		// source == 4 >>>> Object
		// source == 5 >>>> Comment froum
		// source == 6 >>>> Comment paper
		// source == 7 >>>> Comment object

		delete.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (util.getCurrentUser() != null) {

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

					if (source == 2) {

						service = new ServiceComm(context);
						service.delegate = DialogLongClick.this;
						Map<String, String> items = new LinkedHashMap<String, String>();
						items.put("DeletingRecord", "DeletingRecord");

						items.put("tableName", "Paper");
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

					if (source == 4) {
						adapter.open();
						int sumAgency = adapter.countSubAgencyBrand(Item);
						adapter.close();

						if (sumAgency > 0) {
							Toast.makeText(
									context,
									"این صفحه دارای زیر مجموعه ای از نماینگی ها می باشد لذا امکان حذف میسر نمی باشد",
									0).show();
						}

						

						 else {

							service = new ServiceComm(context);
							service.delegate = DialogLongClick.this;
							Map<String, String> items = new LinkedHashMap<String, String>();
							items.put("DeletingRecord", "DeletingRecord");

							items.put("tableName", "Object");
							items.put("Id", String.valueOf(Item));

							service.execute(items);

							ringProgressDialog = ProgressDialog.show(context,
									"", "لطفا منتظر بمانید...", true);

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

					if (source == 5) {
						if (Item == -1) {
							Toast.makeText(
									context,
									"نظر ثبت شده دارای پاسخ می باشد لذا امکان حذف میسر نمی باشد",
									0).show();

						} else {
							params = new LinkedHashMap<String, String>();

							deleting = new Deleting(context);
							deleting.delegate = DialogLongClick.this;

							params.put("TableName", "CommentInFroum");
							params.put("ID", String.valueOf(Item));

							deleting.execute(params);

							ringProgressDialog = ProgressDialog.show(context,
									"", "لطفا منتظر بمانید...", true);

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

					if (source == 6) {
						params = new LinkedHashMap<String, String>();

						deleting = new Deleting(context);
						deleting.delegate = DialogLongClick.this;

						params.put("TableName", "CommentInPaper");
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

					if (source == 7) {
						if (Item == -1) {
							Toast.makeText(
									context,
									"نظر ثبت شده دارای پاسخ می باشد لذا امکان حذف میسر نمی باشد",
									0).show();

						} else {
							params = new LinkedHashMap<String, String>();

							deleting = new Deleting(context);
							deleting.delegate = DialogLongClick.this;

							params.put("TableName", "CommentInObject");
							params.put("Id", String.valueOf(Item));

							deleting.execute(params);

							ringProgressDialog = ProgressDialog.show(context,
									"", "لطفا منتظر بمانید...", true);

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
					Toast.makeText(context, "ابتدا باید وارد شوید", 0).show();
			}
		});

		report.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				date = new ServerDate(context);
				date.delegate = DialogLongClick.this;
				date.execute("");

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
		});

	}

	@Override
	public void processFinish(String output) {
		if (ringProgressDialog != null) {
			ringProgressDialog.dismiss();
		}
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

				((FroumtitleFragment) fragment).updateView();

			}

			if (source == 2) {
				adapter.open();

				adapter.deletePaperTitle(Item);
				adapter.deleteCommentPaper(Item);

				adapter.close();
				dismiss();

				if (ringProgressDialog != null) {
					ringProgressDialog.dismiss();
				}
				((PaperFragment) fragment).updateView();

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
			if (source == 4) {

				adapter.open();

				adapter.deleteObjectTitle(Item);
				adapter.deleteCommentObject(Item);
				adapter.deleteLikeObject(Item);

				adapter.close();

				dismiss();

				if (ringProgressDialog != null) {
					ringProgressDialog.dismiss();
				}

				((MainBrandFragment) fragment).UpdateList();
				((ObjectFragment) fragment).UpdateList();

			}

			if (source == 5) {
				adapter.open();
				adapter.deleteOnlyCommentFroum(Item);
				adapter.close();

				if (ringProgressDialog != null) {
					ringProgressDialog.dismiss();
				}
				dismiss();
				((FroumFragment) fragment).updateList();

			}
			if (source == 6) {
				adapter.open();
				adapter.deleteOnlyCommentPaper(Item);
				adapter.close();

				if (ringProgressDialog != null) {
					ringProgressDialog.dismiss();
				}
				dismiss();
				((PaperFragment) fragment).updateView();

			}
			if (source == 7) {
				adapter.open();
				adapter.deleteOnlyCommentObject(Item);
				adapter.close();

				if (ringProgressDialog != null) {
					ringProgressDialog.dismiss();
				}
				dismiss();
				((IntroductionFragment) fragment).updateList();

			}

		} catch (NumberFormatException e) {
			if (output != null
					&& !(output.contains("Exception") || output
							.contains("java"))) {

				params = new LinkedHashMap<String, String>();
				saving = new Saving(context);
				saving.delegate = DialogLongClick.this;

				params.put("TableName", "Report");

				params.put("[Desc]", desc);
				params.put("UserIdSender", String.valueOf(UserIdObject));
				params.put("UserIdReporter",
						String.valueOf(util.getCurrentUser().getId()));
				params.put("SourceId", String.valueOf(Item));
				params.put("TypeId", String.valueOf(source));
				params.put("Date", output);

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

			} else {
				Toast.makeText(context,
						"خطا در ثبت. پاسخ نا مشخص از سرور" + e + " ",
						Toast.LENGTH_SHORT).show();
			}
		} catch (Exception ex) {

			Toast.makeText(context, "خطا در ثبت", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void CommProcessFinish(String output) {
		processFinish(output);
	}

}
