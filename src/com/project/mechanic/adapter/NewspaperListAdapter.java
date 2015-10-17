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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.entity.News;
import com.project.mechanic.fragment.NewsmoreFragment;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class NewspaperListAdapter extends ArrayAdapter<News> {

	Context context;
	List<News> list;
	DataBaseAdapter adapter;
	int lastPosition = 0;
	protected char[] id;
	Utility util;

	public NewspaperListAdapter(Context context, int resource, List<News> objact) {
		super(context, resource, objact);

		this.context = context;
		this.list = objact;
		adapter = new DataBaseAdapter(context);
		util = new Utility(context);
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		LayoutInflater myInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		convertView = myInflater
				.inflate(R.layout.main_item_list, parent, false);

		convertView = myInflater.inflate(R.layout.row_newspaper, parent, false);

		Animation animation = AnimationUtils.loadAnimation(getContext(),
				(position > lastPosition) ? R.anim.up_from_bottom
						: R.anim.down_from_top);
		convertView.startAnimation(animation);
		lastPosition = position;

		TextView tx1 = (TextView) convertView
				.findViewById(R.id.rownews_Titletxt);
		TextView tx2 = (TextView) convertView
				.findViewById(R.id.rownews_txtDescription);

		final News News = list.get(position);

		tx1.setText(News.getTitle());
		tx2.setText(News.getDescription());

		
		tx1.setTypeface(util.SetFontCasablanca());
		tx2.setTypeface(util.SetFontCasablanca());

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				LinearLayout parentlayout = (LinearLayout) v;
				TextView txtName = (TextView) parentlayout
						.findViewById(R.id.rownews_Titletxt);
				String item = txtName.getText().toString();

				int id = 0;
				for (News News : list) {
					if (item.equals(News.getTitle())) {
						// check authentication and authorization
						id = News.getId();
					}
				}

				adapter.open();
				int res = adapter.getNumberOfListItemChilds(id);
				adapter.close();

				FragmentTransaction trans = ((MainActivity) context)
						.getSupportFragmentManager().beginTransaction();
				NewsmoreFragment fragment = new NewsmoreFragment();
				Bundle bundle = new Bundle();
				bundle.putString("Id", String.valueOf(id));
				fragment.setArguments(bundle);
				trans.replace(R.id.content_frame, fragment);
				trans.addToBackStack(null);
				trans.commit();

				// Toast.makeText(context, "ok", Toast.LENGTH_SHORT).show();
				//
				// FragmentTransaction trans = ((MainActivity) context)
				// .getSupportFragmentManager().beginTransaction();
				// trans.replace(R.id.content_frame, new NewsmoreFragment());
				// trans.addToBackStack(null);
				// trans.commit();
			}
		});
		return convertView;
	}
}
