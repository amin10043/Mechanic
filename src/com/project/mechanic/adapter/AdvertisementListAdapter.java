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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.entity.TicketType;
import com.project.mechanic.fragment.AnadFragment;
import com.project.mechanic.model.DataBaseAdapter;

public class AdvertisementListAdapter extends ArrayAdapter<TicketType> {

	Context context;
	List<TicketType> list;
	TicketType tempItem;
	DataBaseAdapter adapter;
	int itemId;
	int lastPosition = 0;
	int typeID = 0;
	int proId;

	public AdvertisementListAdapter(Context context, int resource,
			List<TicketType> objact, int proID) {

		super(context, resource, objact);

		this.context = context;
		this.list = objact;
		adapter = new DataBaseAdapter(context);
		this.proId = proID;
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater myInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		convertView = myInflater.inflate(R.layout.row_news, parent, false);

		Animation animation = AnimationUtils.loadAnimation(getContext(),
				(position > lastPosition) ? R.anim.up_from_bottom
						: R.anim.down_from_top);
		convertView.startAnimation(animation);
		TextView txtName = (TextView) convertView
				.findViewById(R.id.row_news_txt);

		tempItem = list.get(position);
		txtName.setText(tempItem.getDesc());

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				LinearLayout parentlayout = (LinearLayout) v;
				TextView txtName = (TextView) parentlayout
						.findViewById(R.id.row_news_txt);
				String item = txtName.getText().toString();

				int id = 0;
				for (TicketType listItem : list) {
					if (item.equals(listItem.getDesc())) {
						// check authentication and authorization
						id = listItem.getId();
					}
				}

				FragmentTransaction trans = ((MainActivity) context)
						.getSupportFragmentManager().beginTransaction();
				AnadFragment fragment = new AnadFragment();
				Bundle bundle = new Bundle();
				bundle.putString("Id", String.valueOf(id));
				bundle.putString("ProID", String.valueOf(proId));
				fragment.setArguments(bundle);
				trans.replace(R.id.content_frame, fragment);
				trans.addToBackStack(null);
				trans.commit();

			}
		});

		return convertView;

	}
}
