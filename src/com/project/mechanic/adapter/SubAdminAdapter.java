package com.project.mechanic.adapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mechanic.R;
import com.project.mechanic.StaticValues;
import com.project.mechanic.entity.SubAdmin;
import com.project.mechanic.entity.Users;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.Deleting;
import com.project.mechanic.utility.Utility;

public class SubAdminAdapter extends ArrayAdapter<SubAdmin> implements AsyncInterface {
	Context context;
	ArrayList<SubAdmin> myList;
	DataBaseAdapter adapter;
	Utility util;
	int ObjectId;
	String phoneInput;

	Deleting deleting;
	Map<String, String> params;
	ProgressDialog ringProgressDialog;
	int ItemId, b;

	public SubAdminAdapter(Context context, int resource, ArrayList<SubAdmin> list, int ObjectId) {
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
			LayoutInflater myInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			convertView = myInflater.inflate(R.layout.row_sub_admin, parent, false);
		}
		ImageView picture = (ImageView) convertView.findViewById(R.id.pictureSubAdmin1);
		TextView name = (TextView) convertView.findViewById(R.id.nameSubAdmin1);
		ImageView delete = (ImageView) convertView.findViewById(R.id.deletAdminPage);

		adapter.open();
		// Object o = adapter.getObjectbyid(ObjectId);
		// SubAdmin x = myList.get(o.getUserId());

		SubAdmin su = myList.get(position);
		Users u = adapter.getUserById(su.getUserId());

		name.setText(u.getName());
		name.setTypeface(util.SetFontIranSans());

		RelativeLayout rl = (RelativeLayout) convertView.findViewById(R.id.subAdmin1);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(rl.getLayoutParams());

		lp.width = (int) (util.getScreenwidth() / StaticValues.RateImagePostFragmentPage);
		lp.height = (int) (util.getScreenwidth() / StaticValues.RateImagePostFragmentPage);
		lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		lp.addRule(RelativeLayout.CENTER_VERTICAL);

		lp.setMargins(10, 10, 10, 10);

		String imagePath = u.getImagePath();
		if (imagePath != null) {
			Bitmap bmp = BitmapFactory.decodeFile(imagePath);
			if (bmp != null)
				picture.setImageBitmap(Utility.getclip(bmp));
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

				ItemId = 0;
				ListView listView = (ListView) t.getParent().getParent().getParent();
				b = listView.getPositionForView(t);
				SubAdmin f = getItem(b);
				if (f != null) {
					ItemId = f.getUserId();
				}

				adapter.close();

				params = new LinkedHashMap<String, String>();
				deleting = new Deleting(context);
				deleting.delegate = SubAdminAdapter.this;

				params.put("TableName", "SubAdmin");

				params.put("UserId", String.valueOf(ItemId));

				deleting.execute(params);

				ringProgressDialog = ProgressDialog.show(context, "", "لطفا منتظر بمانید...", true);

				ringProgressDialog.setCancelable(true);

				new Thread(new Runnable() {

					@Override
					public void run() {

						try {

							Thread.sleep(10000);

						} catch (Exception e) {

						}
					}
				}).start();

			}
		});

		notifyDataSetChanged();

		adapter.close();

		return convertView;
	}

	@Override
	public void processFinish(String output) {

		int id = -1;

		try {
			id = Integer.valueOf(output);
			adapter.open();

			Users h = adapter.getUserbyid(ItemId);
			adapter.deleteAdmin(h.getId());

			myList.remove(b);
			notifyDataSetChanged();
			adapter.close();

			if (ringProgressDialog != null) {
				ringProgressDialog.dismiss();

			}

		} catch (Exception e) {
			Toast.makeText(context, "خطا در ثبت", 0).show();
		}

	}
}
