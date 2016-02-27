package com.project.mechanic.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

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

	String[] slideitem = { "صفحه اصلی", "مدیریت شخصی", "علاقه مندی ها", "انجمن برنامه", "پشتیبانی", "قوانین",
			"درباره ما", "تنظیمات" };
	int[] icon = { R.drawable.ic_h, R.drawable.numvv, R.drawable.ic_bookmark, R.drawable.ic_bookmark,
			R.drawable.phone2, R.drawable.phone2, R.drawable.phone2, R.drawable.ic_about_us };

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {

			LayoutInflater myInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			convertView = myInflater.inflate(R.layout.drawer_item, parent, false);
		}

		ImageView iconImg = (ImageView) convertView.findViewById(R.id.icon_slidemenu);
		TextView nametxt = (TextView) convertView.findViewById(R.id.namemenu);

		Users u = util.getCurrentUser();
		nametxt.setTypeface(util.SetFontCasablanca());
		// if (/* position == 2 && */u != null && u.getImage() != null) {
		// iconImg.setImageBitmap(Utility.getRoundedCornerBitmap(BitmapFactory
		// .decodeByteArray(u.getImage(), 0, u.getImage().length), 100));
		//
		// } else {
		iconImg.setImageResource(icon[position]);
		//
		// }

		RelativeLayout layout = (RelativeLayout) convertView.findViewById(R.id.item_list_slide_menu);

		// if (position == 0) {
		//
		// RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
		// layout.getLayoutParams());
		// lp.width = 140;
		// lp.height = 140;
		// lp.addRule(RelativeLayout.CENTER_IN_PARENT);
		//
		// iconImg.setLayoutParams(lp);
		// Bitmap ttb;
		// if (u != null && u.getImage() != null) {
		//
		// ttb = BitmapFactory.decodeByteArray(u.getImage(), 0,
		// u.getImage().length);
		// iconImg.setImageBitmap(ttb);
		// }
		// // iconImg.setImageResource(R.drawable.mechanical_logo);
		//
		// nametxt.setVisibility(View.GONE);
		// }

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
