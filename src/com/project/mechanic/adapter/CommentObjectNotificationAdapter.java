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

public class CommentObjectNotificationAdapter extends
		ArrayAdapter<CommentNotiItem> {

	Context context;
	List<CommentNotiItem> mylist1;
	DataBaseAdapter dbadapter;

	public CommentObjectNotificationAdapter(Context context, int resource,
			List<CommentNotiItem> list) {
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
		TextView txt = (TextView) convertView
				.findViewById(R.id.main_text_notification);
		CommentNotiItem c = mylist1.get(position);
		txt.setText(c.getTitle());
		TextView txt1 = (TextView) convertView.findViewById(R.id.Desc);
		txt1.setText(c.getDesc());
		return convertView;
	}

}
