package com.project.mechanic.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
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
import com.project.mechanic.entity.Province;
import com.project.mechanic.entity.Ticket;
import com.project.mechanic.fragment.AdvertisementFragment;
import com.project.mechanic.model.DataBaseAdapter;

public class ShopListAdapter extends ArrayAdapter<Province> {

	Context context;
	List<Province> list;
	DataBaseAdapter adapter;
	int lastPosition = 0;

	public ShopListAdapter(Context context, int resource, List<Province> objact) {
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

		convertView = myInflater.inflate(R.layout.row_ostan, parent, false);

		Animation animation = AnimationUtils.loadAnimation(getContext(),
				(position > lastPosition) ? R.anim.up_from_bottom
						: R.anim.down_from_top);
		convertView.startAnimation(animation);
		lastPosition = position;

		TextView tx1 = (TextView) convertView.findViewById(R.id.RowOstantxt);

		Province province = list.get(position);

		tx1.setText(province.getName());

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Province province = list.get(position);
				adapter.open();
				List<Ticket> allItems = adapter.getTicketByProvinceId(province
						.getId());

				adapter.close();

				Bundle bundle = new Bundle();
				bundle.putString("provinceId", String.valueOf(province.getId()));
				AdvertisementFragment f = new AdvertisementFragment();
				FragmentTransaction trans = ((MainActivity) context)
						.getSupportFragmentManager().beginTransaction();
				f.setArguments(bundle);
				trans.replace(R.id.content_frame, f);
				trans.addToBackStack(null);
				trans.commit();
			}
		});
		return convertView;
	}
}
