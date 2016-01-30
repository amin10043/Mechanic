package com.project.mechanic.adapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.PopupMenu.OnMenuItemClickListener;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.StaticValues;
import com.project.mechanic.entity.Anad;
import com.project.mechanic.entity.Froum;
import com.project.mechanic.entity.Ticket;
import com.project.mechanic.fragment.AnadFragment;
import com.project.mechanic.fragment.DialogLongClick;
import com.project.mechanic.fragment.ShowAdFragment;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.Deleting;
import com.project.mechanic.service.Saving;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.utility.ServiceComm;
import com.project.mechanic.utility.Utility;

@SuppressLint("ResourceAsColor")
public class AnadListAdapter extends ArrayAdapter<Ticket> implements AsyncInterface {

	Context context;
	List<Ticket> list;
	int[] imageId;
	Ticket tempItem;
	DataBaseAdapter adapter;
	int ProvinceId;
	Utility util;
	Fragment fragment;
	RelativeLayout.LayoutParams layoutParams;
	boolean IsShow;
	ProgressBar LoadingProgress;
	String DateTime;
	int Type;
	// DialogManagementTicket dia;
	int itemId, userIdsender;
	List<String> menuItems;
	String content;
	Map<String, String> params;
	Deleting deleting;
	ProgressDialog ringProgressDialog;
	int ticketTypeId;

	boolean flag;
	int idTicket;
	String serverDate = "";

	public AnadListAdapter(Context context, int resource, List<Ticket> objact, int ProvinceId, Fragment fragment,
			boolean IsShow, String DateTime, int Type, int ticketTypeId) {
		super(context, resource, objact);

		this.context = context;
		this.list = objact;
		this.ProvinceId = ProvinceId;
		adapter = new DataBaseAdapter(context);
		util = new Utility(context);
		this.fragment = fragment;
		this.IsShow = IsShow;
		this.DateTime = DateTime;
		this.Type = Type;
		this.ticketTypeId = ticketTypeId;

		// if type==1 >>>> anadFragment

	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater myInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		convertView = myInflater.inflate(R.layout.row_anad, parent, false);

		TextView txtdate = (TextView) convertView.findViewById(R.id.text_favorite_desc);
		final TextView txtName = (TextView) convertView.findViewById(R.id.row_favorite_title);
		TextView txtDesc = (TextView) convertView.findViewById(R.id.row_anad_txt2);
		ImageView img2 = (ImageView) convertView.findViewById(R.id.row_favorite_img);

		LoadingProgress = (ProgressBar) convertView.findViewById(R.id.progressBar1);
		if (!list.isEmpty())
			tempItem = list.get(position);
		txtdate.setText(util.getPersianDate(tempItem.getDate()));
		txtName.setText(tempItem.getTitle());
		if (tempItem.getDesc() != null && !tempItem.getDesc().equals("null")) {
			if (tempItem.getDesc().length() > 25)
				txtDesc.setText(tempItem.getDesc().substring(0, 25) + " ...");
			else
				txtDesc.setText(tempItem.getDesc());

		} else {
			txtDesc.setVisibility(View.GONE);
			;

		}
		if (tempItem.getSeenBefore() > 0) {
			txtName.setTextColor(Color.GRAY);
			txtDesc.setTextColor(Color.GRAY);
			txtdate.setTextColor(Color.GRAY);

		}

		FrameLayout llkj = (FrameLayout) convertView.findViewById(R.id.imageFrame);

		FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(llkj.getLayoutParams());

		layoutParams.width = util.getScreenwidth() / 5;
		layoutParams.height = util.getScreenwidth() / 5;
		// layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		// layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		layoutParams.setMargins(10, 10, 10, 10);

		String pathProfile = tempItem.getImagePath();
		Bitmap profileImage = BitmapFactory.decodeFile(pathProfile);

		if (profileImage != null) {

			img2.setImageBitmap(Utility.getclip(profileImage));
			img2.setLayoutParams(layoutParams);
			LoadingProgress.setVisibility(View.GONE);

		} else {
			img2.setImageResource(R.drawable.no_img_profile);
			img2.setLayoutParams(layoutParams);
		}
		if (IsShow == false)
			LoadingProgress.setVisibility(View.GONE);

		String commitDate = tempItem.getDate();
		int thisDay = 0;
		int TicketDay = Integer.valueOf(commitDate.substring(0, 8));
		if (DateTime != null && !DateTime.equals(""))
			thisDay = Integer.valueOf(DateTime.substring(0, 8));
		RelativeLayout TicketBackground = (RelativeLayout) convertView.findViewById(R.id.backgroundTicket);

		if (thisDay <= TicketDay + tempItem.getDay()) {
			TicketBackground.setBackgroundColor(Color.WHITE);
			if (tempItem.getSeenBefore() > 0) {
				txtName.setTextColor(Color.GRAY);
				txtDesc.setTextColor(Color.GRAY);
				txtdate.setTextColor(Color.GRAY);

			}

		} else {
			if (Type == 1)
				TicketBackground.setBackgroundColor(Color.WHITE);
			else
				TicketBackground.setBackgroundResource(R.color.lightred);

			if (tempItem.getSeenBefore() > 0) {
				txtName.setTextColor(Color.WHITE);
				txtDesc.setTextColor(Color.WHITE);
				txtdate.setTextColor(Color.WHITE);

			}
		}

		txtName.setTypeface(util.SetFontCasablanca());
		txtDesc.setTypeface(util.SetFontCasablanca());

		ImageView reaport = (ImageView) convertView.findViewById(R.id.reportImage);

		reaport.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				RelativeLayout parentlayout = (RelativeLayout) v.getParent().getParent();
				// TextView txtName = (TextView) parentlayout
				// .findViewById(R.id.row_favorite_title);
				String item = txtName.getText().toString();
				for (Ticket Ticket : list) {

					if (item.equals(Ticket.getTitle())) {
						// check authentication and authorization
						itemId = Ticket.getId();
						userIdsender = Ticket.getUserId();
						content = Ticket.getDesc();

					}
				}

				if (util.getCurrentUser() != null) {
					if (util.getCurrentUser().getId() == userIdsender) {

						menuItems = new ArrayList<String>();
						menuItems.clear();
						menuItems.add("ارسال پیام");
						menuItems.add("کپی");
						menuItems.add("حذف");

					} else {
						menuItems = new ArrayList<String>();

						menuItems.clear();
						menuItems.add("ارسال پیام");
						menuItems.add("افزودن به علاقه مندی ها");
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

						if (item.getTitle().equals("ارسال پیام")) {

							if (util.getCurrentUser() != null)
								util.sendMessage("Ticket");
							else
								Toast.makeText(context, "ابتدا باید وارد شوید", 0).show();
						}

						if (item.getTitle().equals("افزودن به علاقه مندی ها")) {
							adapter.open();
							addToFavorite(util.getCurrentUser().getId(), 3, itemId);
							adapter.close();
						}
						if (item.getTitle().equals("کپی")) {

							util.CopyToClipboard(content);

						}
						if (item.getTitle().equals("گزارش تخلف")) {

							if (util.getCurrentUser() != null)
								util.reportAbuseTicket(userIdsender, itemId, content, ticketTypeId, ProvinceId);
							// util.reportAbuse(userIdsender, 3, itemId,
							// content, 0);
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

			}

		});

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				RelativeLayout parentlayout = (RelativeLayout) v;
				TextView txtName = (TextView) parentlayout.findViewById(R.id.row_favorite_title);
				String item = txtName.getText().toString();
				int id = 0;
				for (Ticket Ticket : list) {

					if (item.equals(Ticket.getTitle())) {
						// check authentication and authorization
						id = Ticket.getId();
					}
				}

				FragmentTransaction trans = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
				ShowAdFragment fragment = new ShowAdFragment();
				Bundle bundle = new Bundle();
				bundle.putString("Id", String.valueOf(id));
				if (ProvinceId >= 0)
					bundle.putString("ProID", String.valueOf(ProvinceId));
				fragment.setArguments(bundle);
				trans.replace(R.id.content_frame, fragment);
				trans.addToBackStack(null);
				trans.commit();

				adapter.open();
				adapter.SetSeen("Ticket", id, "1");
				adapter.close();

							}
		});

		// reaport.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		//
		// // RelativeLayout parentlayout = (RelativeLayout) v.getParent();
		// // TextView txtName = (TextView) parentlayout
		// // .findViewById(R.id.row_favorite_title);
		// // String item = txtName.getText().toString();
		// // int id = 0;
		// // int u = 0;
		// // String t = "";
		// // for (Ticket Ticket : list) {
		// //
		// // if (item.equals(Ticket.getTitle())) {
		// // // check authentication and authorization
		// // id = Ticket.getId();
		// // u = Ticket.getUserId();
		// // t = Ticket.getDesc();
		// //
		// // }
		// // }
		// //
		// // DialogLongClick dia = new DialogLongClick(context, 3, u, id,
		// // fragment, t);
		// // Toast.makeText(context, id + "", 0).show();
		// //
		// // WindowManager.LayoutParams lp = new
		// // WindowManager.LayoutParams();
		// // lp.copyFrom(dia.getWindow().getAttributes());
		// // lp.width = (int) (util.getScreenwidth() / 1.5);
		// // lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		// // ;
		// // dia.show();
		// //
		// // dia.getWindow().setAttributes(lp);
		// // dia.getWindow().setBackgroundDrawable(
		// // new ColorDrawable(android.graphics.Color.TRANSPARENT));
		// }
		// });

		// dia.dismiss();
		
		TextView countVisit = (TextView)convertView.findViewById(R.id.getTicketCountVisit);
		
		countVisit.setText(tempItem.getCountView()+"");

		return convertView;

	}

	public void deleteItems(int itemId) {

		params = new LinkedHashMap<String, String>();

		deleting = new Deleting(context);
		deleting.delegate = AnadListAdapter.this;

		params.put("TableName", "Ticket");
		params.put("Id", String.valueOf(itemId));

		deleting.execute(params);
		flag = false;

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

	public void addToFavorite(int currentUserId, int source, int ItemId) {

		if (adapter.IsUserFavoriteItem(currentUserId, ItemId, source) == true) {
			Toast.makeText(context, " قبلا در لیست علاقه مندی ها ذخیره شده است ", 0).show();
		} else {
			adapter.insertFavoritetoDb(0, currentUserId, ItemId, source);
			Toast.makeText(context, "به لیست علاقه مندی ها اضافه شد ", 0).show();
		}
	}

	@Override
	public void processFinish(String output) {
		serverDate = output;
	

			if (ringProgressDialog != null) {
				ringProgressDialog.dismiss();
			}

			adapter.open();
			adapter.deleteTicketItem(itemId);
			adapter.close();

			((AnadFragment) fragment).updateView();
		}
	
}
