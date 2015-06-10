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
import com.project.mechanic.row_items.CommentNotiItem;

public class CommentPaperNotificationAdapter extends
		ArrayAdapter<CommentNotiItem> {

	Context context;
	List<CommentNotiItem> mylist2;
	DataBaseAdapter dbadapter;

	public CommentPaperNotificationAdapter(Context context, int resource,
			List<CommentNotiItem> list) {
		super(context, resource, list);

		// public CommentPaperNotificationAdapter(Context context, int resource,
		// List<CommentInPaper> list) {
		// super(context, resource, list);
		{

			// TODO Auto-generated constructor stub
			this.context = context;
			this.mylist2 = list;
			dbadapter = new DataBaseAdapter(context);
			// util = new Utility(context);

		}

		// TODO Auto-generated constructor stub
		// this.context = context;
		// this.mylist2 = list;
		// dbadapter = new DataBaseAdapter(context);

	}

	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater myInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = myInflater.inflate(R.layout.row_notification_list, null);

		// listcmnotification= (ListView)
		// convertView.findViewById(R.id.listcmnotification);
		// ImageButton iBtnmessage=(ImageButton)
		// convertView.findViewById(R.id.iBtnmessage);
		// TextView txt = (TextView) convertView
		// .findViewById(R.id.main_text_notification);
		// Users u = util.getCurrentUser();
		// int id = u.getId();

		// CommentInPaper c = mylist2.get(position);
		// if (id == c.getId()) {

		// String v = c.getDescription();
		// txt.setText(v);
		// TextView txt1 = (TextView) convertView.findViewById(R.id.datenotif);
		// String v1 = c.getDatetime();
		// txt1.setText(v1);

		// TextView txt2 = (TextView) convertView.findViewById(R.id.namenotif);

		// int v2 = c.getUserid();
		// List<Users> mylist2=(List<Users>) dbadapter.getUserById(m2);

		// Users u1 = dbadapter.getUserbyid(v2);

		// txt2.setText(u1.getName());

		// CommentInFroum comment = list.get(position);
		// Users x = adapter.getUserbyid(comment.getUserid());
		dbadapter.open();
		TextView txt = (TextView) convertView
				.findViewById(R.id.main_text_notification);
		CommentNotiItem d = mylist2.get(position);
		txt.setText(d.getTitle());
		TextView txt1 = (TextView) convertView.findViewById(R.id.Desc);
		txt1.setText(d.getDesc());
		dbadapter.close();

		return convertView;
	}

}
