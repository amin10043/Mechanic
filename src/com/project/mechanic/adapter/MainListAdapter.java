package com.project.mechanic.adapter;

import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.entity.ListItem;
import com.project.mechanic.fragment.BerandFragment;
import com.project.mechanic.fragment.CountryFragment;
import com.project.mechanic.fragment.FroumtitleFragment;
import com.project.mechanic.fragment.NewsFragment;
import com.project.mechanic.fragment.Province2Fragment;
import com.project.mechanic.fragment.Province3Fragment;
import com.project.mechanic.fragment.ProvinceFragment;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class MainListAdapter extends ArrayAdapter<ListItem> {

	Context context;
	List<ListItem> list;
	int[] imageId;
	ListItem tempItem;
	DataBaseAdapter adapter;
	Utility util;

	int[] icon = { R.drawable.ic_main_item1, R.drawable.ic_main_item2,
			R.drawable.ic_main_item3, R.drawable.ic_main_item4,
			R.drawable.ic_main_item5, R.drawable.ic_main_item6,
			R.drawable.ic_main_item7 };

	public MainListAdapter(Context context, int resource, List<ListItem> objact) {
		super(context, resource, objact);

		this.context = context;
		this.list = objact;
		adapter = new DataBaseAdapter(context);
		util = new Utility(context);

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {

			LayoutInflater myInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			convertView = myInflater.inflate(R.layout.main_item_list, parent,
					false);

			LinearLayout li = (LinearLayout) convertView;

			android.widget.AbsListView.LayoutParams lp = new AbsListView.LayoutParams(

			AbsListView.LayoutParams.MATCH_PARENT,
					AbsListView.LayoutParams.MATCH_PARENT);

			lp.height = (int) ((util.getScreenHeightWithPadding()) / 8);

			li.setLayoutParams(lp);

		}

		TextView txtName = (TextView) convertView.findViewById(R.id.txtName);
		Typeface typeFace = Typeface.createFromAsset(context.getAssets(),
				"fonts/BROYA.TTF");
		txtName.setTypeface(typeFace);

		final ImageView img = (ImageView) convertView
				.findViewById(R.id.imgItem);

		if (position < 7) {
			img.setBackgroundResource(icon[position]);
			img.getLayoutParams().width = ((View) img.getParent())
					.getLayoutParams().height - 20;
			img.getLayoutParams().height = ((View) img.getParent())
					.getLayoutParams().height - 20;
			img.requestLayout();
		}

		tempItem = list.get(position);
		txtName.setText(tempItem.getName());

		// PersianDate date = new PersianDate();
		// txtName.setText(date.todayShamsi());
		// Toast.makeText(context, "date " + date, 50).show();

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				LinearLayout parentlayout = (LinearLayout) v;
				TextView txtName = (TextView) parentlayout
						.findViewById(R.id.txtName);
				String item = txtName.getText().toString();

				int id = 0;
				for (ListItem listItem : list) {
					if (item.equals(listItem.getName())) {
						// check authentication and authorization
						id = listItem.getId();
						break;
					}
				}
				adapter.close();

				SharedPreferences sendData = context.getSharedPreferences("Id",
						0);
				sendData.edit().putInt("main_Id", id).commit();

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
					trans.addToBackStack(null);
					trans.replace(R.id.content_frame, ostan);

					trans.commit();

				} else if (id == 3) {
					FragmentTransaction trans = ((MainActivity) context)
							.getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame, new Province2Fragment());
					trans.addToBackStack(null);
					trans.commit();

				} else if (id == 4) {

					FragmentTransaction trans = ((MainActivity) context)
							.getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame, new Province3Fragment());
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

					// String ShamsiDate =
					// com.project.mechanic.utility.com.project.mechanic.adapter.ShamsiRoozh.getCurrentShamsidate();

				} else if (id == 6) {

					FragmentTransaction trans = ((MainActivity) context)
							.getSupportFragmentManager().beginTransaction();
					CountryFragment fragment = new CountryFragment();
					// Bundle bundle = new Bundle();
					// bundle.putString("Id", String.valueOf(id));
					// fragment.setArguments(bundle);
					trans.replace(R.id.content_frame, fragment);
					trans.addToBackStack(null);
					trans.commit();

				} else if (id == 7) {
					FragmentTransaction trans = ((MainActivity) context)
							.getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame, new FroumtitleFragment());
					trans.addToBackStack(null);
					trans.commit();

				}

			}

		});

		return convertView;

	}
}
