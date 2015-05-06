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
import com.project.mechanic.entity.Executertype;
import com.project.mechanic.fragment.ObjectFragment;
import com.project.mechanic.model.DataBaseAdapter;

public class ExecutertypeListAdapter extends ArrayAdapter<Executertype> {

	Context context;
	List<Executertype> list;
	DataBaseAdapter adapter;
	int lastPosition = 0;

	public ExecutertypeListAdapter(Context context, int resource,
			List<Executertype> objact) {
		super(context, resource, objact);

		this.context = context;
		this.list = objact;
		adapter = new DataBaseAdapter(context);
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		LayoutInflater myInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		convertView = myInflater
				.inflate(R.layout.main_item_list, parent, false);

		convertView = myInflater.inflate(R.layout.row_executertype, parent,
				false);

		Animation animation = AnimationUtils.loadAnimation(getContext(),
				(position > lastPosition) ? R.anim.up_from_bottom
						: R.anim.down_from_top);
		convertView.startAnimation(animation);
		lastPosition = position;

		TextView tx1 = (TextView) convertView
				.findViewById(R.id.rowexecutertypetxt);

		Executertype Executertype = list.get(position);

		tx1.setText(Executertype.getName());
		Typeface typeFace = Typeface.createFromAsset(context.getAssets(),
				"fonts/BROYA.TTF");
		tx1.setTypeface(typeFace);

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Executertype Executertype = list.get(position);
				adapter.open();
				List<City> allItems = adapter.getCitysByProvinceId(Executertype
						.getId());
				adapter.close();

				FragmentTransaction trans = ((MainActivity) context)
						.getSupportFragmentManager().beginTransaction();
				trans.addToBackStack(null);
				Fragment move = new ObjectFragment();
				Bundle bundle = new Bundle();
				bundle.putString("cityId", String.valueOf(Executertype.getId()));
				move.setArguments(bundle);
				trans.replace(R.id.content_frame, move);
				trans.commit();
			}
		});
		return convertView;
	}

}
