package com.project.mechanic.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.project.mechanic.R;
import com.project.mechanic.row_items.RowMain;

public class ProvinceListAdapter extends ArrayAdapter<RowMain> {

	Context context;
	List<RowMain> list;
	
	public ProvinceListAdapter(Context context, int resource, List<RowMain> objact) {
		super(context, resource, objact);

		this.context = context;
		this.list = objact;

	}


	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater myInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		convertView = myInflater.inflate(R.layout.row_ostan, parent, false);

		TextView tx1 = (TextView) convertView.findViewById(R.id.RowOstantxt);
	
		RowMain person1 = list.get(position);
		
		tx1.setText(person1.getName());
	
		return convertView;
	}
	}