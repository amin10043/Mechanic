package com.project.mechanic.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.project.mechanic.R;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.row_items.LikeNotiItem;

public class LikeNotificationAdapter extends ArrayAdapter<LikeNotiItem> {
	Context context;
	List<LikeNotiItem> mylist;
	DataBaseAdapter dbadapter;

	public LikeNotificationAdapter(Context context, int resource,
			ArrayList<LikeNotiItem> mylist) {
		super(context, resource, mylist);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.mylist = mylist;
		dbadapter = new DataBaseAdapter(context);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater myInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = myInflater.inflate(R.layout.row_notification_list, null);

		TextView txtTitle = (TextView) convertView
				.findViewById(R.id.main_text_notification);
		LikeNotiItem likeNoti = mylist.get(position);
		// dbadapter.open();
		// LikeInFroum lk = dbadapter.getLikeInFroumById(c.getId());
		// dbadapter.close();

		txtTitle.setText(likeNoti.getTitle());

		return convertView;
	}

}
