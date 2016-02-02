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
import android.widget.TextView;

public class SharedVideoAdapter extends ArrayAdapter<String> {
	List<String> duration;
	List<Integer> imageList;

	Context context;
	Utility util;

	public SharedVideoAdapter(Context context, int resource, List<String> duration, List<Integer> imageLsit) {
		super(context, resource, duration);
		this.context = context;
		util = new Utility(context);
		this.duration = duration;
		this.imageList = imageLsit;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(R.layout.row_video_shared, null);

		ImageView picture = (ImageView) convertView.findViewById(R.id.imageV);
		TextView txtTime = (TextView) convertView.findViewById(R.id.duration);

		RelativeLayout imageLayout = (RelativeLayout) convertView.findViewById(R.id.imageFrame);

		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(imageLayout.getLayoutParams());

		lp.width = util.getScreenwidth() / 4;
		lp.height = util.getScreenwidth() / 4;
		lp.addRule(RelativeLayout.CENTER_HORIZONTAL);

		picture.setImageResource(imageList.get(position));

		picture.setScaleType(ScaleType.FIT_XY);
		picture.setLayoutParams(lp);

		txtTime.setText(duration.get(position));

		return convertView;
	}

}
