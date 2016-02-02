package com.project.mechanic.chatAdapter;

import java.util.List;

import com.project.mechanic.R;
import com.project.mechanic.StaticValues;
import com.project.mechanic.utility.Utility;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SelectContactAdapter extends ArrayAdapter<String> {
	Context context;
	List<String> items;
	Utility util;

	public SelectContactAdapter(Context context, int resource, List<String> items) {
		super(context, resource, items);
		this.context = context;
		this.items = items;
		util = new Utility(context);

	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(R.layout.row_select_member_admin, null);

		TextView nameItem = (TextView) convertView.findViewById(R.id.Rowobjecttxt);

		nameItem.setText(items.get(position));
		nameItem.setTypeface(util.SetFontCasablanca());

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

			}
		});
		return convertView;

	}

}
