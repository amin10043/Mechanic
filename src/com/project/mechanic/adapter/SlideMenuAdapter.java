package com.project.mechanic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mechanic.R;

public class SlideMenuAdapter extends BaseAdapter {
	public SlideMenuAdapter(Context context) {
		super();
		this.context = context;
	}

	Context context;

	String[] slideitem = { "صفحه اصلی", "کاربری", "صفحه شخصی", "پسند شده ها",
			"درباره ما", "تماس با ما" };
	int[] icon = { R.drawable.ic_home, R.drawable.ic_pro,
			R.drawable.ic_personal, R.drawable.ic_bookmark,
			R.drawable.ic_about_us, R.drawable.phone };

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {

			LayoutInflater myInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			convertView = myInflater.inflate(R.layout.drawer_item, parent,
					false);
		}

		ImageView iconImg = (ImageView) convertView
				.findViewById(R.id.icon_slidemenu);
		TextView nametxt = (TextView) convertView.findViewById(R.id.namemenu);

		iconImg.setImageResource(icon[position]);
		nametxt.setText(slideitem[position]);

		return convertView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return slideitem.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return slideitem[position];
	}

	@Override
	public long getItemId(int id) {
		// TODO Auto-generated method stub
		return id;
	}

}
