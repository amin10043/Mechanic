package com.project.mechanic.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;

import android.support.v4.app.Fragment;

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
import android.widget.Toast;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.entity.ListItem;
import com.project.mechanic.fragment.AdvertisementFragment;
import com.project.mechanic.fragment.AdvisorTypeFragment;
import com.project.mechanic.fragment.BerandFragment;
import com.project.mechanic.fragment.ExecutertypeFragment;
import com.project.mechanic.fragment.FroumtitleFragment;
import com.project.mechanic.fragment.NewsFragment;
import com.project.mechanic.fragment.ObjectFragment;
import com.project.mechanic.fragment.ProvinceFragment;
import com.project.mechanic.fragment.TitlepaperFragment;
import com.project.mechanic.model.DataBaseAdapter;

public class MainListAdapter extends ArrayAdapter<ListItem> {

	Context context;
	List<ListItem> list;
	int[] imageId;
	ListItem tempItem;
	DataBaseAdapter adapter;

	public MainListAdapter(Context context, int resource, List<ListItem> objact) {
		super(context, resource, objact);

		this.context = context;
		this.list = objact;
		adapter = new DataBaseAdapter(context);

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater myInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		convertView = myInflater
				.inflate(R.layout.main_item_list, parent, false);

		TextView txtName = (TextView) convertView.findViewById(R.id.txtName);
		TextView txtNoti = (TextView) convertView.findViewById(R.id.txtNoti);

		ImageView img = (ImageView) convertView.findViewById(R.id.imgItem);

		tempItem = list.get(position);
		txtName.setText(tempItem.getName());
		txtNoti.setText("1");

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				LinearLayout parentlayout = (LinearLayout) v;
				TextView txtName = (TextView) parentlayout
						.findViewById(R.id.txtName);
				String item = txtName.getText().toString();

				adapter.open();
				ArrayList<ListItem> allItems = adapter.getListItemsById(0);
				int id = 0;
				for (ListItem listItem : allItems) {
					if (item.equals(listItem.getName())) {
						// check authentication and authorization
						id = listItem.getId();
					}
				}
				adapter.close();

				
				SharedPreferences sendData = context.getSharedPreferences(
						"Id", 0);
				SharedPreferences.Editor editor = sendData.edit();
				editor.putInt("main_Id", id);
				editor.commit();
				
				if (id == 1) {
					FragmentTransaction trans = ((MainActivity) context)
							.getSupportFragmentManager().beginTransaction();

					

					BerandFragment fragment = new BerandFragment();
					Bundle bundle = new Bundle();
					bundle.putString("Id", String.valueOf(id));
					fragment.setArguments(bundle);

					trans.replace(R.id.content_frame, fragment);
					trans.addToBackStack(null);
					trans.commit();

				} else if (id == 2) {
					FragmentTransaction trans = ((MainActivity) context)
							.getSupportFragmentManager().beginTransaction();
					
					Fragment ostan = new ProvinceFragment();
					
					



//					
//					Bundle bundle = new Bundle();
//					bundle.putString("Id", String.valueOf(id));
//					ostan.setArguments(bundle);
					trans.addToBackStack(null);
					trans.replace(R.id.content_frame, ostan);

					trans.commit();

				} else if (id == 3) {
					FragmentTransaction trans = ((MainActivity) context)
							.getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame, new AdvisorTypeFragment());
					trans.addToBackStack(null);
					trans.commit();

				} else if (id == 4) {

					FragmentTransaction trans = ((MainActivity) context)
							.getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame,
							new ExecutertypeFragment());
					trans.addToBackStack(null);
					trans.commit();

				} else if (id == 5) {

					FragmentTransaction trans = ((MainActivity) context)
							.getSupportFragmentManager().beginTransaction();
					NewsFragment fragment = new NewsFragment();
					Bundle bundle = new Bundle();
					bundle.putString("Id", String.valueOf(id));
					fragment.setArguments(bundle);
					trans.replace(R.id.content_frame, fragment);
					trans.addToBackStack(null);
					trans.commit();

				} else if (id == 6) {

					FragmentTransaction trans = ((MainActivity) context)
							.getSupportFragmentManager().beginTransaction();
					AdvertisementFragment fragment = new AdvertisementFragment();
					Bundle bundle = new Bundle();
					bundle.putString("Id", String.valueOf(id));
					fragment.setArguments(bundle);
					trans.replace(R.id.content_frame, fragment);
					trans.addToBackStack(null);
					trans.commit();
					
				} else if (id == 7) {
					FragmentTransaction trans = ((MainActivity) context)
							.getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame, new FroumtitleFragment());
					trans.addToBackStack(null);
					trans.commit();
				
			} else if (id == 8) {
				FragmentTransaction trans = ((MainActivity) context)
						.getSupportFragmentManager().beginTransaction();
				//trans.replace(R.id.content_frame, new TitlepaperFragment());
				trans.addToBackStack(null);
				trans.commit();
			}
				}
			
			
		});

		return convertView;

	}
}
