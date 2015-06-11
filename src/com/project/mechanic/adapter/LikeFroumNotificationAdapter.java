package com.project.mechanic.adapter;

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

public class LikeFroumNotificationAdapter extends ArrayAdapter<LikeNotiItem> {
	Context context;

	List<LikeNotiItem> mylist1;

	DataBaseAdapter dbadapter;

	// Utility util;

	public LikeFroumNotificationAdapter(Context context, int resource,
			List<LikeNotiItem> list) {
		super(context, resource, list);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.mylist1 = list;
		dbadapter = new DataBaseAdapter(context);
		// util = new Utility(context);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater myInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = myInflater.inflate(R.layout.row_notification_list, null);

		// TextView txt = (TextView) convertView.findViewById(R.id.namenotif);

		// Users u = util.getCurrentUser();
		// int id = u.getId();
		// Froum d = mylist1.get(position);
		//
		// if (id == d.getUserId()) {
		// LikeInFroum c = mylist.get(position);

		// int m2 = c.getUserid();
		// List<Users> mylist2=(List<Users>) dbadapter.getUserById(m2);

		// Users u1 = dbadapter.getUserbyid(m2);

		// txt.setText(u1.getName());

		dbadapter.open();
		TextView txt1 = (TextView) convertView
				.findViewById(R.id.main_text_notification);

		// LikeInFroum c1 = mylist.get(position);
		// int m = c1.getFroumid();
		// Froum f = dbadapter.getFroumItembyid(m);
		// txt1.setText(f.getDescription());

		LikeNotiItem c1 = mylist1.get(position);
		txt1.setText(c1.getTitle());
		dbadapter.close();

		return convertView;
	}

}
