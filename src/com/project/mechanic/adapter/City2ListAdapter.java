package com.project.mechanic.adapter;

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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.entity.City;
import com.project.mechanic.fragment.AdvisorTypeFragment;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class City2ListAdapter extends ArrayAdapter<City> {

	Context context;
	List<City> list;
	int lastPosition = 0;
	DataBaseAdapter adapter;
	Utility util;

	public City2ListAdapter(Context context, int resource, List<City> objact) {
		super(context, resource, objact);

		this.context = context;
		this.list = objact;
		adapter = new DataBaseAdapter(context);

	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater myInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		convertView = myInflater.inflate(R.layout.row_city, parent, false);
		util = new Utility(context);

//		Animation animation = AnimationUtils.loadAnimation(getContext(),
//				(position > lastPosition) ? R.anim.up_from_bottom
//						: R.anim.down_from_top);
//		convertView.startAnimation(animation);
		lastPosition = position;

		TextView txt1 = (TextView) convertView.findViewById(R.id.RowCitytxt);

		final City city = list.get(position);

		txt1.setText(city.getName());
	
		txt1.setTypeface(util.SetFontCasablanca());

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				int count = city.getCount();
				int id = city.getId();
				count = count + 1;
				adapter.open();
				adapter.UpdateCityToDb(id, count);
				adapter.close();

				FragmentTransaction trans = ((MainActivity) context)
						.getSupportFragmentManager().beginTransaction();
				trans.addToBackStack(null);
				Fragment move = new AdvisorTypeFragment();
				Bundle bundle = new Bundle();
				bundle.putString("cityId", String.valueOf(city.getId()));
				move.setArguments(bundle);
				trans.replace(R.id.content_frame, move);
				trans.commit();
			}
		});
		return convertView;
	}
}
