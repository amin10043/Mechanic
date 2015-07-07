package com.project.mechanic.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mechanic.R;
import com.project.mechanic.entity.SubAdmin;
import com.project.mechanic.entity.Users;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class SubAdminAdapter extends ArrayAdapter<SubAdmin> {
	Context context;
	ArrayList<SubAdmin> myList;
	DataBaseAdapter adapter;
	Utility util;
	int ObjectId;
	String phoneInput;

	public SubAdminAdapter(Context context, int resource,
			ArrayList<SubAdmin> list, int ObjectId) {
		super(context, resource, list);
		this.context = context;
		myList = list;
		adapter = new DataBaseAdapter(context);
		util = new Utility(context);
		this.ObjectId = ObjectId;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater myInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			convertView = myInflater.inflate(R.layout.row_sub_admin, parent,
					false);
		}
		ImageView picture = (ImageView) convertView
				.findViewById(R.id.pictureSubAdmin1);
		TextView name = (TextView) convertView.findViewById(R.id.nameSubAdmin1);
		ImageView delete = (ImageView) convertView
				.findViewById(R.id.deletAdminPage);

		adapter.open();
		// Object o = adapter.getObjectbyid(ObjectId);
		// SubAdmin x = myList.get(o.getUserId());

		SubAdmin su = myList.get(position);
		Users u = adapter.getUserById(su.getUserId());

		name.setText(u.getName());

		RelativeLayout rl = (RelativeLayout) convertView
				.findViewById(R.id.subAdmin1);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				rl.getLayoutParams());

		lp.width = util.getScreenwidth() / 7;
		lp.height = util.getScreenwidth() / 7;
		lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		lp.setMargins(5, 5, 5, 5);

		byte[] bitmapbyte = u.getImage();
		if (bitmapbyte != null) {
			Bitmap bmp = BitmapFactory.decodeByteArray(bitmapbyte, 0,
					bitmapbyte.length);
			picture.setImageBitmap(util.getRoundedCornerBitmap(bmp, 50));
			picture.setLayoutParams(lp);
		} else {
			picture.setImageResource(R.drawable.no_img_profile);
			picture.setLayoutParams(lp);

		}
		notifyDataSetChanged();

		delete.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View t) {

				adapter.open();
				RelativeLayout parentlayout = (RelativeLayout) t.getParent();
				View name = parentlayout.findViewById(R.id.nameSubAdmin1);
				TextView x = (TextView) name;

				SubAdmin cc = myList.get(position);
				int i = cc.getUserId();
				Users h = adapter.getUserbyid(i);
				int id = 0;
				for (SubAdmin r : myList) {
					if (x.getText().toString().equals(h.getName())) {

						id = cc.getId();

					}
				}

				adapter.deleteAdmin(id);
				notifyDataSetChanged();
				adapter.close();
			}
		});

		adapter.close();

		return convertView;
	}
}
