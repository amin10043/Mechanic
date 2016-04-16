package com.project.mechanic.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.entity.ListItem;
import com.project.mechanic.fragment.BerandFragment;
import com.project.mechanic.fragment.MainBrandFragment;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class BerandListAdapter extends ArrayAdapter<ListItem> {

	Context context;
	List<ListItem> list;
	ListItem tempItem;
	DataBaseAdapter adapter;
	int lastPosition = 0;
	Utility util;

	public BerandListAdapter(Context context, int resource, List<ListItem> objact) {
		super(context, resource, objact);

		this.context = context;
		this.list = objact;
		adapter = new DataBaseAdapter(context);
		util = new Utility(context);

	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater myInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		convertView = myInflater.inflate(R.layout.row_berand, parent, false);

		// Animation animation = AnimationUtils.loadAnimation(getContext(),
		// (position > lastPosition) ? R.anim.up_from_bottom
		// : R.anim.down_from_top);
		// convertView.startAnimation(animation);
		TextView txtName = (TextView) convertView.findViewById(R.id.row_berand_txt);

		// img.setBackgroundResource(R.drawable.google);

		tempItem = list.get(position);
		txtName.setText(tempItem.getName());

		txtName.setTypeface(util.SetFontCasablanca());

		String item = txtName.getText().toString();

		final ImageView img = (ImageView) convertView.findViewById(R.id.icon_item);

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				RelativeLayout parentlayout = (RelativeLayout) v;
				TextView txtName = (TextView) parentlayout.findViewById(R.id.row_berand_txt);
				String item = txtName.getText().toString();

				int parentId = 0;
				for (ListItem listItem : list) {
					if (item.equals(listItem.getName())) {
						// check authentication and authorization
						parentId = listItem.getId();
					}
				}

				adapter.open();
				int res = adapter.getNumberOfListItemChilds(parentId);

				adapter.close();

				if (res > 0) {
					FragmentTransaction trans = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
					BerandFragment fragment = new BerandFragment(parentId);
//					Bundle bundle = new Bundle();
//					bundle.putString("Id", String.valueOf(parentId));
//					fragment.setArguments(bundle);
					trans.replace(R.id.content_frame, fragment);
					trans.addToBackStack(null);
					trans.commit();

				} else {
					FragmentTransaction trans = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
					MainBrandFragment fragment = new MainBrandFragment(parentId);
//					Bundle bundle = new Bundle();
//					bundle.putString("Id", String.valueOf(parentId));
//					fragment.setArguments(bundle);
					trans.replace(R.id.content_frame, fragment);
					trans.addToBackStack(null);

					trans.commit();
				}
			}
		});

		return convertView;

	}
}