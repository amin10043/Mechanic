package com.project.mechanic.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.fragment.CityFragment;
import com.project.mechanic.fragment.ProvinceFragment;
import com.project.mechanic.row_items.RowMain;

public class ProvinceListAdapter extends ArrayAdapter<RowMain> {

	Context context;
	List<RowMain> list;

	public ProvinceListAdapter(Context context, int resource,
			List<RowMain> objact) {
		super(context, resource, objact);

		this.context = context;
		this.list = objact;

	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater myInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		convertView = myInflater
				.inflate(R.layout.main_item_list, parent, false);

		convertView = myInflater.inflate(R.layout.row_ostan, parent, false);

		TextView tx1 = (TextView) convertView.findViewById(R.id.RowOstantxt);

		RowMain person1 = list.get(position);

		tx1.setText(person1.getName());

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				FragmentTransaction trans = ((MainActivity) context)
						.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new CityFragment());
				trans.addToBackStack(null);
				trans.commit();
			}
		});
		return convertView;
	}
}
