package com.project.mechanic.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mechanic.R;
import com.project.mechanic.entity.Users;
import com.project.mechanic.utility.Utility;

public class SlideMenuAdapter extends BaseAdapter {
	Utility util;

	public SlideMenuAdapter(Context context) {
		super();
		this.context = context;
		util = new Utility(context);
	}

	Context context;

	String[] slideitem = { "صفحه اصلی", "صفحه شخصی", "پسند شده ها",
			"درباره ما", "تماس با ما", "خروج" };
	int[] icon = { R.drawable.ic_home, R.drawable.ic_personal,
			R.drawable.ic_bookmark, R.drawable.ic_about_us, R.drawable.phone,
			R.drawable.exit };

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

		Users u = util.getCurrentUser();
		if (position == 1 && u != null && u.getImage() != null) {
			iconImg.setImageBitmap(Utility.getRoundedCornerBitmap(BitmapFactory
					.decodeByteArray(u.getImage(), 0, u.getImage().length), 100));

		} else {
			iconImg.setImageResource(icon[position]);
		}

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
