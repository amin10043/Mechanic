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

public class LikePaperNotificationAdapter extends ArrayAdapter<LikeNotiItem> {

	Context context;
	List<LikeNotiItem> mylist2;
	DataBaseAdapter dbadapter;

	public LikePaperNotificationAdapter(Context context, int resource,
			ArrayList<LikeNotiItem> mylist2) {
		super(context, resource);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.mylist2 = mylist2;
		dbadapter = new DataBaseAdapter(context);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater myInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = myInflater.inflate(R.layout.row_notification_list, null);

		TextView txt = (TextView) convertView
				.findViewById(R.id.main_text_notification);
		LikeNotiItem c = mylist2.get(position);
		txt.setText(c.getTitle());

		return convertView;
	}

}
