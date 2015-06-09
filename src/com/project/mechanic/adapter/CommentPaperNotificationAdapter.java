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

		// TODO Auto-generated constructor stub
		this.context = context;
		this.mylist2 = list;
		dbadapter = new DataBaseAdapter(context);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater myInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = myInflater.inflate(R.layout.row_notification_list, null);

		TextView txt = (TextView) convertView
				.findViewById(R.id.main_text_notification);
		CommentNotiItem d = mylist2.get(position);
		txt.setText(d.getTitle());
		TextView txt1 = (TextView) convertView.findViewById(R.id.Desc);
		txt1.setText(d.getDesc());

		return convertView;
	}

}
