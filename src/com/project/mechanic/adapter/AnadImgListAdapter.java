package com.project.mechanic.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.project.mechanic.R;
import com.project.mechanic.entity.Anad;
import com.project.mechanic.entity.Users;
import com.project.mechanic.fragment.DialogAnadimg;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class AnadImgListAdapter extends ArrayAdapter<Anad> {

	Context context;
	List<Anad> list;
	int[] imageId;
	private DialogAnadimg dialog1;
	Anad tempItem;
	DataBaseAdapter adapter;
	int ProvinceId;
	Users u;
	Utility util;

	public AnadImgListAdapter(Context context, int resource, List<Anad> objact,
			int ProvinceId) {
		super(context, resource, objact);

		this.context = context;
		this.list = objact;
		this.ProvinceId = ProvinceId;
		adapter = new DataBaseAdapter(context);

	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater myInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		convertView = myInflater.inflate(R.layout.row_anad_img, parent, false);

		ImageView img = (ImageView) convertView.findViewById(R.id.img_anad);
		adapter = new DataBaseAdapter(context);
		util = new Utility(context);
		u = util.getCurrentUser();
		tempItem = list.get(position);
		byte[] bitmapbyte = tempItem.getImage();
		if (bitmapbyte != null) {
			Bitmap bmp = BitmapFactory.decodeByteArray(bitmapbyte, 0,
					bitmapbyte.length);
			img.setImageBitmap(bmp);
		}

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (u == null) {
					Toast.makeText(context, " شما وارد نشده اید.",
							Toast.LENGTH_LONG).show();
					return;
				}

				RelativeLayout parentlayout = (RelativeLayout) v;

				dialog1 = new DialogAnadimg(context, R.layout.dialog_imganad,
						null, 0, 0);

				dialog1.setTitle(R.string.tabligh);
				dialog1.show();

			}
		});

		return convertView;

	}
}
