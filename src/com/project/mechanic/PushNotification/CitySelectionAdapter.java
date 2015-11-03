package com.project.mechanic.PushNotification;

import java.util.ArrayList;
import java.util.List;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.entity.City;
import com.project.mechanic.fragment.ExecutertypeFragment;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.row_items.RowMain;
import com.project.mechanic.utility.Utility;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CitySelectionAdapter extends ArrayAdapter<City> {
	Context context;
	List<City> list;
	int lastPosition = 0;
	DataBaseAdapter adapter;
	String type;
	Utility util;

	public CitySelectionAdapter(Context context, int resource,
			List<City> objact, String type) {
		super(context, resource, objact);

		this.context = context;
		this.list = objact;
		adapter = new DataBaseAdapter(context);
		this.type = type;
		util = new Utility(context);
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater myInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		convertView = myInflater.inflate(R.layout.row_city, parent, false);

		Animation animation = AnimationUtils.loadAnimation(getContext(),
				(position > lastPosition) ? R.anim.up_from_bottom
						: R.anim.down_from_top);
		convertView.startAnimation(animation);
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

				if (type.equals("BirthDay")) {

					FragmentTransaction trans = ((MainActivity) context)
							.getSupportFragmentManager().beginTransaction();
					trans.addToBackStack(null);

					MessageNotification move = new MessageNotification();

					trans.replace(R.id.content_frame, move);

					trans.commit();
				} else {

					FragmentTransaction trans = ((MainActivity) context)
							.getSupportFragmentManager().beginTransaction();
					trans.addToBackStack(null);

					Fragment move = new TypeUserNotification(type);

					trans.replace(R.id.content_frame, move);

					trans.commit();
					Toast.makeText(context, "city Id = " + city.getId(), 0)
							.show();
				}
			}
		});
		return convertView;
	}
}
