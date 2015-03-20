package com.project.mechanic.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mechanic.R;
import com.project.mechanic.entity.ListItem;

public class MainListAdapter extends ArrayAdapter<ListItem> {

	Context context;
	List<ListItem> list;
	int[] imageId;
	ListItem tempItem;

	public MainListAdapter(Context context, int resource, List<ListItem> objact) {
		super(context, resource, objact);

		this.context = context;
		this.list = objact;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater myInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		convertView = myInflater
				.inflate(R.layout.main_item_list, parent, false);

		TextView txtName = (TextView) convertView.findViewById(R.id.txtName);
		TextView txtNoti = (TextView) convertView.findViewById(R.id.txtNoti);
		@SuppressWarnings("unused")
		ImageView img = (ImageView) convertView.findViewById(R.id.imgItem);

		tempItem = list.get(position);
		txtName.setText(tempItem.getName());
		txtNoti.setText("1");

		return convertView;

	}
}
