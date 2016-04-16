package com.project.mechanic.adapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.PopupMenu.OnMenuItemClickListener;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.StaticValues;
import com.project.mechanic.entity.CommentInPaper;
import com.project.mechanic.entity.Users;
import com.project.mechanic.fragment.InformationUser;
import com.project.mechanic.fragment.PaperFragment;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.Deleting;
import com.project.mechanic.utility.Utility;
import com.project.mechanic.view.TextViewEx;

public class PaperListAdapter extends ArrayAdapter<CommentInPaper> implements AsyncInterface {

	Context context;
	List<CommentInPaper> list;
	DataBaseAdapter adapter;
	// PaperFragment f;
	Utility util;
	private PaperFragment paperfragment;
	int userId;
	ImageView report;
	int itemId, userIdsender;
	ProgressDialog ringProgressDialog;
	String content;
	Map<String, String> params;
	Deleting deleting;

	public PaperListAdapter(Context context, int resource, List<CommentInPaper> objects, PaperFragment f) {
		super(context, resource, objects);

		this.context = context;
		this.list = objects;
		adapter = new DataBaseAdapter(context);
		util = new Utility(context);
		paperfragment= f;

	}

	List<String> menuItems;

	@SuppressLint("ViewHolder")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		LayoutInflater myInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		convertView = myInflater.inflate(R.layout.row_paper, parent, false);
		adapter = new DataBaseAdapter(context);

		TextViewEx txtcmt = (TextViewEx) convertView.findViewById(R.id.reply_txt_child);
		TextView txtuser = (TextView) convertView.findViewById(R.id.name_replyed);
		TextView txtdate = (TextView) convertView.findViewById(R.id.date_replyed);
		ImageButton profilepic = (ImageButton) convertView.findViewById(R.id.icon_reply_comment);

		CommentInPaper comment = list.get(position);

		report = (ImageView) convertView.findViewById(R.id.reportImagereply);

		adapter.open();
		RelativeLayout rl = (RelativeLayout) convertView.findViewById(R.id.main_icon_reply);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(rl.getLayoutParams());

		lp.width = (int) (util.getScreenwidth() / StaticValues.RateImageCommentAndReply);
		lp.height = (int) (util.getScreenwidth() / StaticValues.RateImageCommentAndReply);
		// lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		// lp.setMargins(5, 5, 5, 5);
		Users user = adapter.getUserbyid(comment.getUserid());
		// userId=user.getId();

		if (user.getImagePath() != null) {

			Bitmap bitmap = BitmapFactory.decodeFile(user.getImagePath());

			if (bitmap != null) {
				profilepic.setImageBitmap(Utility.getclip(bitmap));
				profilepic.setLayoutParams(lp);

			}
		}

		adapter.close();

		report.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				int d = (int) getItemId(position);
				final CommentInPaper cn = getItem(d);

				if (cn != null) {
					itemId = cn.getId();
					userIdsender = cn.getUserid();
					content = cn.getDescription();

				}

				if (util.getCurrentUser() != null) {
					if (util.getCurrentUser().getId() == userIdsender) {

						menuItems = new ArrayList<String>();
						menuItems.clear();
						menuItems.add("کپی");
						menuItems.add("حذف");

					} else {
						menuItems = new ArrayList<String>();

						menuItems.clear();
						menuItems.add("کپی");
						menuItems.add("گزارش تخلف");
					}
				} else {
					menuItems = new ArrayList<String>();

					menuItems.clear();
					menuItems.add("کپی");
				}

				final PopupMenu popupMenu = util.ShowPopupMenu(menuItems, v);
				popupMenu.show();
				OnMenuItemClickListener menuitem = new OnMenuItemClickListener() {

					@Override
					public boolean onMenuItemClick(MenuItem item) {

						if (item.getTitle().equals("کپی")) {

							util.CopyToClipboard(content);

						}
						if (item.getTitle().equals("گزارش تخلف")) {

							if (util.getCurrentUser() != null)

								util.reportAbuse(userIdsender, StaticValues.TypeReportCommentPaper, itemId, content,
										cn.getPaperid(), position , -1);
							else
								Toast.makeText(context, "ابتدا باید وارد شوید", 0).show();
						}
						if (item.getTitle().equals("حذف")) {
							if (util.getCurrentUser() != null && util.getCurrentUser().getId() == userIdsender)
								deleteItems(itemId);
							else {

								Toast.makeText(context, "", 0).show();
							}
						}

						return false;
					}
				};

				popupMenu.setOnMenuItemClickListener(menuitem);

				// int i = 0;
				// int us = 0;
				// String t = "";
				//
				// int d = (int) getItemId(position);
				// CommentInPaper w = getItem(d);
				// if (w != null) {
				// i = w.getId();
				// us = w.getUserid();
				// t = w.getDescription();
				// Toast.makeText(context, "id = " + i + " Userid = " + us, 0)
				// .show();
				// }
				//
				// DialogLongClick dia = new DialogLongClick(context, 6, us, i,
				// Paperfragment, t);
				// dia.show();
			}

		});

		profilepic.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				adapter.open();
				CommentInPaper comment = list.get(position);
				Users user = adapter.getUserbyid(comment.getUserid());
				userId = user.getId();
				FragmentTransaction trans = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
				InformationUser fragment = new InformationUser();
				Bundle bundle = new Bundle();
				bundle.putInt("userId", userId);
				fragment.setArguments(bundle);
				trans.replace(R.id.content_frame, fragment);
				trans.commit();

			}
		});
		txtcmt.setText(comment.getDescription(), true);
		txtcmt.setTypeface(util.SetFontIranSans());
		txtuser.setText(user.getName());
		txtuser.setTypeface(util.SetFontIranSans());
		txtdate.setText(util.getPersianDate(comment.getDatetime()));
		return convertView;
	}

	public void deleteItems(int itemId) {

		params = new LinkedHashMap<String, String>();

		deleting = new Deleting(context);
		deleting.delegate = PaperListAdapter.this;

		params.put("TableName", "CommentInPaper");
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

	@Override
	public void processFinish(String output) {

		adapter.open();
		adapter.deleteOnlyCommentPaper(itemId);
		adapter.close();

		if (ringProgressDialog != null) {
			ringProgressDialog.dismiss();
		}
		paperfragment.FillListView();

	}
}
