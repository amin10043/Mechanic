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
import com.project.mechanic.entity.LikeInObject;
import com.project.mechanic.entity.Users;
import com.project.mechanic.model.DataBaseAdapter;

public class LikeNotificationAdapter extends ArrayAdapter<LikeInObject> {
	Context context;
	List<LikeInObject> mylist;
	DataBaseAdapter dbadapter;

	public LikeNotificationAdapter(Context context, int resource,
			ArrayList<LikeInObject> mylist) {
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

		TextView txt = (TextView) convertView.findViewById(R.id.namenotif);
		LikeInObject c = mylist.get(position);
		dbadapter.open();
		int m = c.getUserId();

		Users u = dbadapter.getUserbyid(m);

		txt.setText(u.getName());

		dbadapter.close();

		return convertView;
	}

}
