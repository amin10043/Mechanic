package com.project.mechanic.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class WelcomeScreenAdapter extends BaseAdapter {

	private Context context;
	private int[] image;
	int imageWidth;


	public WelcomeScreenAdapter(Context context, int[] img, int columnWidth) {
		this.context = context;
		image = img;
		imageWidth = columnWidth;

	}

	@Override
	public int getCount() {
		return image.length;
	}

	@Override
	public Object getItem(int position) {
		return image[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView = new ImageView(context);
		imageView.setImageResource(image[position]);


		imageView.setLayoutParams(new GridView.LayoutParams(imageWidth,
				imageWidth));
		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		return imageView;
	}
	

}
