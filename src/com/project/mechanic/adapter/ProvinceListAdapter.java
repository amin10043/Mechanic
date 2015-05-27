package com.project.mechanic.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
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
import com.project.mechanic.entity.Province;
import com.project.mechanic.fragment.CityFragment;
import com.project.mechanic.fragment.MainFragment;
import com.project.mechanic.model.DataBaseAdapter;

public class ProvinceListAdapter extends ArrayAdapter<Province> {

	Context context;
	List<Province> list;
	DataBaseAdapter adapter;
	int lastPosition = 0;
	int i = 10;

	public ProvinceListAdapter(Context context, int resource,
			List<Province> objact) {
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
		// search = (ImageView)findViewById(R.id.sedarch_v);
		convertView = myInflater.inflate(R.layout.row_ostan, parent, false);

		Animation animation = AnimationUtils.loadAnimation(getContext(),
				(position > lastPosition) ? R.anim.up_from_bottom
						: R.anim.down_from_top);
		convertView.startAnimation(animation);
		lastPosition = position;

		TextView tx1 = (TextView) convertView.findViewById(R.id.RowOstantxt);

		Province province = list.get(position);

		tx1.setText(province.getName());
		Typeface typeFace = Typeface.createFromAsset(context.getAssets(),
				"fonts/BROYA.TTF");
		tx1.setTypeface(typeFace);

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Province p = list.get(position);
				adapter.open();
				List<City> allItems = adapter.getCitysByProvinceId(p.getId());
				// Province P = adapter.getProvinceById(province.getId());

				int count = p.getCount();
				int id = p.getId();
				count = count + 1;
				adapter.UpdateProvinceToDb(id, count);
				adapter.close();

				FragmentTransaction trans = ((MainActivity) context)
						.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new CityFragment(allItems));
				trans.addToBackStack(null);
				trans.commit();
			}
		});
		return convertView;

	}

	public void onBackPressed() {

		FragmentTransaction trans = ((MainActivity) context)
				.getSupportFragmentManager().beginTransaction();
		trans.replace(R.id.content_frame, new MainFragment());
		trans.commit();

	}

}
