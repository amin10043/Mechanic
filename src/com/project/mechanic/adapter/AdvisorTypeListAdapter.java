package com.project.mechanic.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.entity.AdvisorType;
import com.project.mechanic.entity.City;
import com.project.mechanic.entity.Province;
import com.project.mechanic.fragment.CityFragment;
import com.project.mechanic.fragment.ProvinceFragment;
import com.project.mechanic.model.DataBaseAdapter;

public class AdvisorTypeListAdapter extends ArrayAdapter<AdvisorType> {

	Context context;
	List<AdvisorType> list;
	DataBaseAdapter adapter;

	public AdvisorTypeListAdapter(Context context, int resource,
			List<AdvisorType> objact) {
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

		convertView = myInflater.inflate(R.layout.main_item_list, parent, false);

		convertView = myInflater.inflate(R.layout.row_advisortype, parent, false);

		TextView tx1 = (TextView) convertView.findViewById(R.id.RowAdvisortypetxt);

		AdvisorType  AdvisorType = list.get(position);

		tx1.setText(AdvisorType.getName());

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				AdvisorType AdvisorType = list.get(position);
				adapter.open();
				List<City> allItems = adapter.getCitysByProvinceId(AdvisorType.getId());
				adapter.close();

				FragmentTransaction trans = ((MainActivity) context)
						.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new ProvinceFragment());
				trans.addToBackStack(null);
				trans.commit();
			}
		});
		return convertView;
	}
}
