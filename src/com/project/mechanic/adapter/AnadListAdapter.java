package com.project.mechanic.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.entity.ListItem;
import com.project.mechanic.fragment.AdvertisementFragment;
import com.project.mechanic.fragment.AdvisorTypeFragment;
import com.project.mechanic.fragment.BerandFragment;
import com.project.mechanic.fragment.ExecutertypeFragment;
import com.project.mechanic.fragment.FroumtitleFragment;
import com.project.mechanic.fragment.NewsFragment;
import com.project.mechanic.fragment.ProvinceFragment;
import com.project.mechanic.model.DataBaseAdapter;

public class AnadListAdapter extends ArrayAdapter<ListItem> {

	Context context;
	List<ListItem> list;
	int[] imageId;
	ListItem tempItem;
	DataBaseAdapter adapter;

	public AnadListAdapter(Context context, int resource, List<ListItem> objact) {
		super(context, resource, objact);

		this.context = context;
		this.list = objact;
		adapter = new DataBaseAdapter(context);

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater myInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		convertView = myInflater.inflate(R.layout.row_anad, parent, false);

		TextView txtName = (TextView) convertView.findViewById(R.id.row_anad_txt);

		ImageView img = (ImageView) convertView.findViewById(R.id.row_anad_img);

		tempItem = list.get(position);
		txtName.setText(tempItem.getName());
		

//		convertView.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//				LinearLayout parentlayout = (LinearLayout) v;
//				TextView txtName = (TextView) parentlayout
//						.findViewById(R.id.txtName);
//				String item = txtName.getText().toString();
//
//				adapter.open();
//				ArrayList<ListItem> allItems = adapter.getListItemsById(0);
//				int id = 0;
//				for (ListItem listItem : allItems) {
//					if (item.equals(listItem.getName())) {
//						// check authentication and authorization
//						id = listItem.getId();
//					}
//				}
//				adapter.close();
//
//				if (id == 1) {
//					FragmentTransaction trans = ((MainActivity) context)
//							.getSupportFragmentManager().beginTransaction();
//					BerandFragment fragment = new BerandFragment();
//					Bundle bundle = new Bundle();
//					bundle.putString("Id", String.valueOf(id));
//					fragment.setArguments(bundle);
//					trans.replace(R.id.content_frame, fragment);
//					trans.addToBackStack(null);
//					trans.commit();
//
//				} else if (id == 2) {
//					FragmentTransaction trans = ((MainActivity) context)
//							.getSupportFragmentManager().beginTransaction();
//					trans.replace(R.id.content_frame, new ProvinceFragment());
//					trans.addToBackStack(null);
//					trans.commit();
//
//				} else if (id == 3) {
//					FragmentTransaction trans = ((MainActivity) context)
//							.getSupportFragmentManager().beginTransaction();
//					trans.replace(R.id.content_frame, new AdvisorTypeFragment());
//					trans.addToBackStack(null);
//					trans.commit();
//
//				} else if (id == 4) {
//
//					FragmentTransaction trans = ((MainActivity) context)
//							.getSupportFragmentManager().beginTransaction();
//					trans.replace(R.id.content_frame,
//							new ExecutertypeFragment());
//					trans.addToBackStack(null);
//					trans.commit();
//
//				} else if (id == 5) {
//
//					FragmentTransaction trans = ((MainActivity) context)
//							.getSupportFragmentManager().beginTransaction();
//					NewsFragment fragment = new NewsFragment();
//					Bundle bundle = new Bundle();
//					bundle.putString("Id", String.valueOf(id));
//					fragment.setArguments(bundle);
//					trans.replace(R.id.content_frame, fragment);
//					trans.addToBackStack(null);
//					trans.commit();
//
//				} else if (id == 6) {
//
//					FragmentTransaction trans = ((MainActivity) context)
//							.getSupportFragmentManager().beginTransaction();
//					AdvertisementFragment fragment = new AdvertisementFragment();
//					Bundle bundle = new Bundle();
//					bundle.putString("Id", String.valueOf(id));
//					fragment.setArguments(bundle);
//					trans.replace(R.id.content_frame, fragment);
//					trans.addToBackStack(null);
//					trans.commit();
//					
//				} else if (id == 7) {
//					FragmentTransaction trans = ((MainActivity) context)
//							.getSupportFragmentManager().beginTransaction();
//					trans.replace(R.id.content_frame, new FroumtitleFragment());
//					trans.addToBackStack(null);
//					trans.commit();
//				}
//			}
//		});

		return convertView;

	}
}
