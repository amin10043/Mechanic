package com.project.mechanic.adapter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.crop.Util;
import com.project.mechanic.entity.AdvisorType;
import com.project.mechanic.fragment.AdvisorTypeFragment;
import com.project.mechanic.fragment.ExecutertypeFragment;
import com.project.mechanic.fragment.ObjectFragment;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class AdvisorTypeListAdapter extends ArrayAdapter<AdvisorType> {

	Context context;
	List<AdvisorType> list;
	DataBaseAdapter adapter;
	protected Object item;
	int cityId;
	Utility util;

	public AdvisorTypeListAdapter(Context context, int resource,
			List<AdvisorType> objact, int CityId) {
		super(context, resource, objact);

		this.context = context;
		this.list = objact;
		this.cityId = CityId;
		adapter = new DataBaseAdapter(context);
		util = new Utility(context);
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		LayoutInflater myInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		convertView = myInflater
				.inflate(R.layout.main_item_list, parent, false);

		convertView = myInflater.inflate(R.layout.row_advisortype, parent,
				false);

		TextView tx1 = (TextView) convertView
				.findViewById(R.id.RowAdvisortypetxt);

		final AdvisorType AdvisorType = list.get(position);

		tx1.setText(AdvisorType.getName());

		
		tx1.setTypeface(util.SetFontCasablanca());

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				RelativeLayout parentlayout = (RelativeLayout) v;
				TextView txtName = (TextView) parentlayout
						.findViewById(R.id.RowAdvisortypetxt);
				String item = txtName.getText().toString();

				// Toast.makeText(context, item , Toast.LENGTH_LONG).show();

				adapter.open();
				ArrayList<AdvisorType> allAdvisorType = adapter
						.getAdvisorTypes();
				int id = 0;
				for (AdvisorType AdvisorType1 : allAdvisorType) {
					if (item.equals(AdvisorType1.getName())) {
						// check authentication and authorization
						id = AdvisorType1.getId();
					}
				}
				adapter.close();

				if (id == 1 || id == 2) {
					FragmentTransaction trans = ((MainActivity) context)
							.getSupportFragmentManager().beginTransaction();
					trans.addToBackStack(null);
					Fragment move = new ObjectFragment();
					Bundle bundle = new Bundle();
					bundle.putString("cityId", String.valueOf(cityId));
					bundle.putString("advisorId",
							String.valueOf(AdvisorType.getId()));
					move.setArguments(bundle);
					trans.replace(R.id.content_frame, move);
					trans.commit();

					Toast.makeText(getContext(),
							"advosiorType Id = " + AdvisorType.getId(), 0)
							.show();

				} else if (id == 3) {
					FragmentTransaction trans = ((MainActivity) context)
							.getSupportFragmentManager().beginTransaction();

					Fragment move = new ExecutertypeFragment();
					Bundle bundle = new Bundle();
					bundle.putString("cityId", String.valueOf(cityId));
					bundle.putString("advisorId",
							String.valueOf(AdvisorType.getId()));
					move.setArguments(bundle);
					trans.replace(R.id.content_frame, move);
					trans.addToBackStack(null);
					trans.commit();
					Toast.makeText(getContext(),
							"advosiorType Id = " + AdvisorType.getId(), 0)
							.show();

				}

			}
		});
		return convertView;
	}
}
