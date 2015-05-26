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
import com.project.mechanic.entity.Users;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class commentnotificationAdapter extends ArrayAdapter<CommentInFroum> {
	Context context;
	List<CommentInFroum> mylist;
	DataBaseAdapter dbadapter;
	Utility util;

	public commentnotificationAdapter(Context context, int resource,
			List<CommentInFroum> list) {
		super(context, resource, list);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.mylist = list;
		dbadapter = new DataBaseAdapter(context);
		util = new Utility(context);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater myInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = myInflater.inflate(R.layout.row_notification_list, null);

		// listcmnotification= (ListView)
		// convertView.findViewById(R.id.listcmnotification);
		// ImageButton iBtnmessage=(ImageButton)
		// convertView.findViewById(R.id.iBtnmessage);
		TextView txt = (TextView) convertView
				.findViewById(R.id.main_text_notification);

		Users u = util.getCurrentUser();
		int id = u.getId();

		CommentInFroum c = mylist.get(position);
		if (id == c.getId()) {

			String m = c.getDesk();
			txt.setText(m);
			TextView txt1 = (TextView) convertView.findViewById(R.id.datenotif);
			String m1 = c.getDatetime();
			txt1.setText(m1);

			TextView txt2 = (TextView) convertView.findViewById(R.id.namenotif);
			dbadapter.open();

			int m2 = c.getUserid();
			// List<Users> mylist2=(List<Users>) dbadapter.getUserById(m2);

			Users u1 = dbadapter.getUserbyid(m2);

			txt2.setText(u1.getName());

			dbadapter.close();
		}

		// CommentInFroum comment = list.get(position);
		// Users x = adapter.getUserbyid(comment.getUserid());

		return convertView;

	}

}
