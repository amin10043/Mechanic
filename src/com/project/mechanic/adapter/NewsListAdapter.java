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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.entity.ListItem;
import com.project.mechanic.fragment.AnadFragment;
import com.project.mechanic.fragment.NewsBuildingFragment;
import com.project.mechanic.fragment.NewsFragment;
import com.project.mechanic.fragment.NewspaperFragment;
import com.project.mechanic.fragment.ShopFragment;
import com.project.mechanic.fragment.TitlepaperFragment;
import com.project.mechanic.fragment.UrlNewsPaperFragment;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class NewsListAdapter extends ArrayAdapter<ListItem> {

	Context context;
	List<ListItem> list;
	ListItem tempItem;
	DataBaseAdapter adapter;
	int itemId;
	int lastPosition = 0;
	Utility util;

	public NewsListAdapter(Context context, int resource, List<ListItem> objact, int id) {
		super(context, resource, objact);

		this.context = context;
		this.list = objact;
		adapter = new DataBaseAdapter(context);
		this.itemId = id;
		util = new Utility(context);

	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater myInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		convertView = myInflater.inflate(R.layout.row_shop, parent, false);

//		Animation animation = AnimationUtils.loadAnimation(getContext(),(position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
		// convertView.startAnimation(animation);
		TextView txtName = (TextView) convertView.findViewById(R.id.row_news_txt);

		tempItem = list.get(position);
		txtName.setText(tempItem.getName());

		txtName.setTypeface(util.SetFontCasablanca());

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				RelativeLayout parentlayout = (RelativeLayout) v;
				TextView txtName = (TextView) parentlayout.findViewById(R.id.row_news_txt);
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

				if (id == 166) {
					FragmentTransaction trans = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
					NewsFragment fragment = new NewsFragment();
					Bundle bundle = new Bundle();
					bundle.putString("Id", String.valueOf(id));
					fragment.setArguments(bundle);
					trans.replace(R.id.content_frame, fragment);
					trans.addToBackStack(null);
					trans.commit();

				} else if (id == 167) {
					FragmentTransaction trans = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
					TitlepaperFragment fragment = new TitlepaperFragment();
					Bundle bundle = new Bundle();
					bundle.putString("Id", String.valueOf(id));
					fragment.setArguments(bundle);
					trans.replace(R.id.content_frame, fragment);
					trans.addToBackStack(null);
					trans.commit();

				} else if (id == 168) {
					FragmentTransaction trans = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
					NewspaperFragment fragment = new NewspaperFragment();
					Bundle bundle = new Bundle();
					bundle.putString("Id", String.valueOf(id));
					fragment.setArguments(bundle);
					trans.replace(R.id.content_frame, fragment);
					trans.addToBackStack(null);
					trans.commit();

				} else if (id == 178) {
					FragmentTransaction trans = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
					AnadFragment fragment = new AnadFragment();
					Bundle bundle = new Bundle();
					bundle.putString("Id", String.valueOf(id));
					fragment.setArguments(bundle);
					trans.replace(R.id.content_frame, fragment);
					trans.addToBackStack(null);
					trans.commit();

				} else if (id == 179) {
					FragmentTransaction trans = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
					ShopFragment fragment = new ShopFragment();
					Bundle bundle = new Bundle();
					bundle.putString("Id", String.valueOf(id));
					fragment.setArguments(bundle);
					trans.replace(R.id.content_frame, fragment);
					trans.addToBackStack(null);
					trans.commit();
				}
				if (id == 169) {
					FragmentTransaction trans = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
					NewsBuildingFragment fragment = new NewsBuildingFragment();
					Bundle bundle = new Bundle();
					bundle.putString("Id", String.valueOf(id));
					fragment.setArguments(bundle);
					trans.replace(R.id.content_frame, fragment);
					trans.addToBackStack(null);
					trans.commit();

				}
				if (id == 290) {
					FragmentTransaction trans = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
					UrlNewsPaperFragment fragment = new UrlNewsPaperFragment();
					Bundle bundle = new Bundle();
					bundle.putString("Id", String.valueOf(id));
					fragment.setArguments(bundle);
					trans.replace(R.id.content_frame, fragment);
					trans.addToBackStack(null);
					trans.commit();

				}
				if (id == 170) {
					FragmentTransaction trans = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
					NewsBuildingFragment fragment = new NewsBuildingFragment();
					Bundle bundle = new Bundle();
					bundle.putString("Id", String.valueOf(id));
					fragment.setArguments(bundle);
					trans.replace(R.id.content_frame, fragment);
					trans.addToBackStack(null);
					trans.commit();

				}
				if (id == 171) {
					FragmentTransaction trans = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
					NewsBuildingFragment fragment = new NewsBuildingFragment();
					Bundle bundle = new Bundle();
					bundle.putString("Id", String.valueOf(id));
					fragment.setArguments(bundle);
					trans.replace(R.id.content_frame, fragment);
					trans.addToBackStack(null);
					trans.commit();

				}
				if (id == 291) {
					FragmentTransaction trans = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
					NewsBuildingFragment fragment = new NewsBuildingFragment();
					Bundle bundle = new Bundle();
					bundle.putString("Id", String.valueOf(id));
					fragment.setArguments(bundle);
					trans.replace(R.id.content_frame, fragment);
					trans.addToBackStack(null);
					trans.commit();

				}
				if (id == 292) {
					FragmentTransaction trans = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
					NewsBuildingFragment fragment = new NewsBuildingFragment();
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
