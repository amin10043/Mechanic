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

	public LikeFroumNotificationAdapter(Context context, int resource,
			List<LikeNotiItem> list) {
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

		TextView txt1 = (TextView) convertView
				.findViewById(R.id.main_text_notification);
		LikeNotiItem c1 = mylist1.get(position);
		txt1.setText(c1.getTitle());

		return convertView;
	}

}