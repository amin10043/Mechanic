package com.project.mechanic.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.project.mechanic.R;
import com.project.mechanic.entity.Froum;
import com.project.mechanic.entity.LikeInFroum;
import com.project.mechanic.entity.Users;
import com.project.mechanic.model.DataBaseAdapter;

public class LikeFroumNotificationAdapter extends ArrayAdapter<LikeInFroum> {
	Context context;
	List<LikeInFroum> mylist1;
	DataBaseAdapter dbadapter;

	public LikeFroumNotificationAdapter(Context context, int resource,
			List<LikeInFroum> list) {
		super(context, resource, list);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.mylist1 = list;
		dbadapter = new DataBaseAdapter(context);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater myInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = myInflater.inflate(R.layout.row_notification_list, null);

		TextView txt = (TextView) convertView.findViewById(R.id.namenotif);
		dbadapter.open();
		LikeInFroum c = mylist1.get(position);

		int m2 = c.getUserid();
		// List<Users> mylist2=(List<Users>) dbadapter.getUserById(m2);

		Users u = dbadapter.getUserbyid(m2);

		txt.setText(u.getName());

		TextView txt1 = (TextView) convertView
				.findViewById(R.id.main_text_notification);
		LikeInFroum c1 = mylist1.get(position);
		int m = c1.getFroumid();
		Froum f = dbadapter.getFroumItembyid(m);
		txt1.setText(f.getDescription());

		dbadapter.close();

		return convertView;
	}

}
