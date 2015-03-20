package com.project.mechanic.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mechanic.R;
import com.project.mechanic.row_items.RowMain;

public class MainListAdapter extends ArrayAdapter<RowMain> {

	Context context;
	List<RowMain> list;
	int[] imageId;

	public MainListAdapter(Context context, int resource, List<RowMain> objact) {
		super(context, resource, objact);

		this.context = context;
		this.list = objact;

	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater myInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		convertView = myInflater.inflate(R.layout.row_lstv, parent, false);

		TextView tx1 = (TextView) convertView.findViewById(R.id.row_lstv_txt2);
		TextView tx2 = (TextView) convertView.findViewById(R.id.row_lstv_txt1);
		@SuppressWarnings("unused")
		ImageView img = (ImageView) convertView.findViewById(R.id.row_lstv_imgv1);

		RowMain person1 = list.get(position);
		RowMain person2 = list.get(position);
		RowMain person3 = list.get(position);

		tx1.setText(person1.getName());
		tx2.setText(person2.getNoti());

		return convertView;

	}
}
