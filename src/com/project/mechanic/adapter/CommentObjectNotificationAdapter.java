package com.project.mechanic.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mechanic.R;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.row_items.CommentNotiItem;
import com.project.mechanic.utility.Utility;

public class CommentObjectNotificationAdapter extends
		ArrayAdapter<CommentNotiItem> {

	Context context;
	List<CommentNotiItem> notificationList;
	DataBaseAdapter dbadapter;
	Utility	util;

	public CommentObjectNotificationAdapter(Context context, int resource,
			List<CommentNotiItem> list) {
		super(context, resource, list);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.notificationList = list;
		dbadapter = new DataBaseAdapter(context);
		util = new Utility(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater myInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = myInflater.inflate(R.layout.row_notification_list, null);

		TextView name = (TextView) convertView.findViewById(R.id.name);
		ImageView img = (ImageView) convertView
				.findViewById(R.id.imagePersonNotificated);
		RelativeLayout layout = (RelativeLayout) convertView
				.findViewById(R.id.hhg);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				layout.getLayoutParams());

		CommentNotiItem c1 = notificationList.get(position);
		name.setText(c1.getName() + "\n" + " برای صفحه " + c1.getTitle()
				+ " نظر گذاشت " + "\n"
				+ util.YearMonthDay(c1.getDate()));

		lp.width = util.getScreenwidth() / 5;
		lp.height = util.getScreenwidth() / 5;
		lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		lp.setMargins(2, 2, 2, 2);

		img.setLayoutParams(lp);

		return convertView;

	}

}
