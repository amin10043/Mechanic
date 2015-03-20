package com.project.mechanic.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.project.mechanic.R;

public class ProvinceListAdapter extends ArrayAdapter<String> {

	Context context;
	List<String> list;
	int[] imageId;

	public ProvinceListAdapter(Context context, int resource,
			int textViewResourceId, List<String> objects) {
		super(context, resource, textViewResourceId, objects);
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater myInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		convertView = myInflater.inflate(R.layout.fragment_ostan, parent, false);

		TextView tx1 = (TextView) convertView.findViewById(R.id.row_lstv_txt2);

		return convertView;

	}

}
