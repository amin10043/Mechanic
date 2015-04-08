package com.project.mechanic.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.entity.ListItem;
import com.project.mechanic.fragment.CityFragment;
import com.project.mechanic.fragment.NewsFragment;
import com.project.mechanic.fragment.NewspaperFragment;
import com.project.mechanic.fragment.ShopFragment;
import com.project.mechanic.model.DataBaseAdapter;

public class NewsListAdapter extends ArrayAdapter<ListItem> {

	Context context;
	List<ListItem> list;
	ListItem tempItem;
	DataBaseAdapter adapter;
	int itemId;
	int lastPosition = 0;
	
	public NewsListAdapter(Context context, int resource,
			List<ListItem> objact, int id) {
		super(context, resource, objact);

		this.context = context;
		this.list = objact;
		adapter = new DataBaseAdapter(context);
		this.itemId = id;

	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater myInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		convertView = myInflater.inflate(R.layout.row_news, parent, false);

		Animation animation = AnimationUtils.loadAnimation(getContext(),
				(position > lastPosition) ? R.anim.up_from_bottom
						: R.anim.down_from_top);
		convertView.startAnimation(animation);
		TextView txtName = (TextView) convertView.findViewById(R.id.row_news_txt);

		tempItem = list.get(position);
		txtName.setText(tempItem.getName());

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
                
				LinearLayout parentlayout = (LinearLayout) v;
				TextView txtName = (TextView) parentlayout
						.findViewById(R.id.row_news_txt);
				String item = txtName.getText().toString();

				int id = 0;
				for (ListItem listItem : list) {
					if (item.equals(listItem.getName())) {
						// check authentication and authorization
						id = listItem.getId();
					}
				}

				adapter.open();
				int res = adapter.getNumberOfListItemChilds(id);
				adapter.close();

					if (res > 0) {
					FragmentTransaction trans = ((MainActivity) context)
							.getSupportFragmentManager().beginTransaction();
					NewsFragment fragment = new NewsFragment();
					Bundle bundle = new Bundle();
					bundle.putString("Id", String.valueOf(id));
					fragment.setArguments(bundle);
					trans.replace(R.id.content_frame, fragment);
					trans.addToBackStack(null);
					trans.commit();
					
					
				} else if (id==178) {
					FragmentTransaction trans = ((MainActivity) context)
							.getSupportFragmentManager().beginTransaction();
					
					
					CityFragment fragment = new CityFragment();
					Bundle bundle = new Bundle();
					bundle.putString("Id", String.valueOf(id));
					fragment.setArguments(bundle);
					trans.replace(R.id.content_frame, fragment);
					trans.addToBackStack(null);
					trans.commit();
					
				}
				else if(id==179){
					FragmentTransaction trans = ((MainActivity) context)
							.getSupportFragmentManager().beginTransaction();
					
					
					ShopFragment fragment = new ShopFragment();
					Bundle bundle = new Bundle();
					bundle.putString("Id", String.valueOf(id));
					fragment.setArguments(bundle);
					trans.replace(R.id.content_frame, fragment);
					trans.addToBackStack(null);
					trans.commit();
					
					
				}
			}
		});

		return convertView;

	}
}
