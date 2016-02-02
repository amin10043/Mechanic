package com.project.mechanic.chatAdapter;

import java.util.List;

import com.project.mechanic.R;
import com.project.mechanic.utility.Utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

public class SharedImagesAdapter extends ArrayAdapter<Integer> {
	List<Integer> imageList;
	Context context;
	Utility util;

	public SharedImagesAdapter(Context context, int resource, List<Integer> imageList) {
		super(context, resource, imageList);
		this.context = context;
		util = new Utility(context);
		this.imageList = imageList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(R.layout.row_image_shared, null);

		ImageView picture = (ImageView) convertView.findViewById(R.id.imageV);
		RelativeLayout imageLayout = (RelativeLayout) convertView.findViewById(R.id.imageFrame);

		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(imageLayout.getLayoutParams());

		lp.width = util.getScreenwidth() / 4;
		lp.height = util.getScreenwidth() / 4;
		lp.addRule(RelativeLayout.CENTER_VERTICAL);

		picture.setImageResource(imageList.get(position));

		picture.setScaleType(ScaleType.FIT_XY);
		picture.setLayoutParams(lp);

		return convertView;
	}

}
