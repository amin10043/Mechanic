package com.project.mechanic.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.project.mechanic.R;
import com.project.mechanic.entity.CommentInFroum;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.row_items.CommentNotiItem;

public class commentnotificationAdapter extends ArrayAdapter<CommentNotiItem> {
	Context context;

	List<CommentInFroum> mylist1;
	// List<Froum> mylist1;

	List<CommentNotiItem> mylist;

	DataBaseAdapter dbadapter;

	// Utility util;

	public commentnotificationAdapter(Context context, int resource,
			List<CommentNotiItem> list) {
		super(context, resource, list);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.mylist = list;
		dbadapter = new DataBaseAdapter(context);
		// util = new Utility(context);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater myInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = myInflater.inflate(R.layout.row_notification_list, null);

		TextView txt = (TextView) convertView
				.findViewById(R.id.main_text_notification);

		// Users u = util.getCurrentUser();
		// int id = u.getId();
		// Froum d = mylist1.get(position);
		// CommentInFroum c = mylist.get(position);
		// if (id == d.getUserId()) {
		// CommentInFroum c1 = mylist1.get(position);
		// String m = c1.getDesk();
		// txt.setText(m);
		// TextView txt1 = (TextView) convertView.findViewById(R.id.datenotif);
		// String m1 = c1.getDatetime();
		// txt1.setText(m1);

		// TextView txt2 = (TextView) convertView.findViewById(R.id.namenotif);

		// //int m2 = c1.getUserid();
		// List<Users> mylist2=(List<Users>) dbadapter.getUserById(m2);

		// Users u1 = dbadapter.getUserbyid(m2);

		// txt2.setText(u1.getName());

		// CommentInFroum comment = list.get(position);
		// Users x = adapter.getUserbyid(comment.getUserid());
		dbadapter.open();
		CommentNotiItem c = mylist.get(position);

		txt.setText(c.getTitle());
		TextView txt1 = (TextView) convertView.findViewById(R.id.Desc);
		txt1.setText(c.getDesk());
		dbadapter.close();

		return convertView;

	}

}
