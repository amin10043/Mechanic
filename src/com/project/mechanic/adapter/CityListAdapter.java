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
import com.project.mechanic.entity.CountSubBrandInCity;
import com.project.mechanic.entity.CountSubBrandInProvince;
import com.project.mechanic.fragment.ObjectFragment;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class CityListAdapter extends ArrayAdapter<City> {

	Context context;
	List<City> list;
	int lastPosition = 0;
	DataBaseAdapter adapter;
	Utility util;

	int objectId = -1;
	int agencyService = -1;
	int mainObjectId;

	public CityListAdapter(Context context, int resource, List<City> objact, int objecId, int agencyService,
			int mainObjectId) {
		super(context, resource, objact);

		this.context = context;
		this.list = objact;
		adapter = new DataBaseAdapter(context);
		util = new Utility(context);
		this.objectId = objecId;
		this.agencyService = agencyService;
		this.mainObjectId = mainObjectId;
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater myInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		convertView = myInflater.inflate(R.layout.row_city, parent, false);

		// Animation animation = AnimationUtils.loadAnimation(getContext(),
		// (position > lastPosition) ? R.anim.up_from_bottom
		// : R.anim.down_from_top);
		// convertView.startAnimation(animation);
		lastPosition = position;

		TextView txt1 = (TextView) convertView.findViewById(R.id.RowCitytxt);
		TextView countCity = (TextView) convertView.findViewById(R.id.countCity);

		final City city = list.get(position);

		adapter.open();
		CountSubBrandInCity CountSubBrand = adapter.GetCountSubBrandCity(objectId, city.getId(), agencyService);
		adapter.close();

		if (CountSubBrand != null)
			countCity.setText(CountSubBrand.getCountInCity() + "");

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

				FragmentTransaction trans = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
				Fragment move = new ObjectFragment(mainObjectId, objectId, agencyService, id);
				// Bundle bundle = new Bundle();
				// bundle.putString("cityId", String.valueOf(city.getId()));
				// move.setArguments(bundle);
				trans.replace(R.id.content_frame, move);
				trans.commit();
			}
		});
		return convertView;
	}

	// public void onBackPressed() {
	//
	// FragmentTransaction trans = ((MainActivity) context)
	// .getSupportFragmentManager().beginTransaction();
	// trans.replace(R.id.content_frame, new ProvinceFragment());
	// trans.commit();
	//
	// }
}
