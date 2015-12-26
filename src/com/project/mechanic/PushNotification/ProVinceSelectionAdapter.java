package com.project.mechanic.PushNotification;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.entity.City;
import com.project.mechanic.entity.Province;
import com.project.mechanic.fragment.City3Fragment;
import com.project.mechanic.fragment.NewsFragment;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class ProVinceSelectionAdapter extends ArrayAdapter<Province> {

	Context context;
	List<Province> list;
	DataBaseAdapter adapter;
	int lastPosition = 0;
	String type;
	Utility util;

	public ProVinceSelectionAdapter(Context context, int resource,
			List<Province> objact, String type) {
		super(context, resource, objact);

		this.context = context;
		this.list = objact;
		this.type = type;
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

		convertView = myInflater.inflate(R.layout.row_ostan, parent, false);

//		Animation animation = AnimationUtils.loadAnimation(getContext(),
//				(position > lastPosition) ? R.anim.up_from_bottom
//						: R.anim.down_from_top);
//		convertView.startAnimation(animation);
		lastPosition = position;

		TextView tx1 = (TextView) convertView.findViewById(R.id.RowOstantxt);

		Province province = list.get(position);

		tx1.setText(province.getName());
		
		tx1.setTypeface(util.SetFontCasablanca());

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Province province = list.get(position);
				adapter.open();

				int count = province.getCount();
				int id = province.getId();
				count = count + 1;
				adapter.UpdateProvinceToDb(id, count);

				adapter.close();

				FragmentTransaction trans = ((MainActivity) context)
						.getSupportFragmentManager().beginTransaction();

				trans.replace(R.id.content_frame, new CitySelection(type, id));

				trans.addToBackStack(null);
				trans.commit();
			}
		});
		return convertView;
	}
}
