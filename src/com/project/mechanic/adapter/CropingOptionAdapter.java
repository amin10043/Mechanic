package com.project.mechanic.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mechanic.R;
import com.project.mechanic.entity.CropingOption;

public class CropingOptionAdapter extends ArrayAdapter {
	private ArrayList mOptions;
	private LayoutInflater mInflater;

	public CropingOptionAdapter(Context context, ArrayList options) {
		super(context, R.layout.croping_selector, options);

		mOptions = options;

		mInflater = LayoutInflater.from(context);
	}

	public View getView(int position, View convertView, ViewGroup group) {
		if (convertView == null)
			convertView = mInflater.inflate(R.layout.croping_selector, null);

		CropingOption item = (CropingOption) mOptions.get(position);

		if (item != null) {
			ImageView img_icon = ((ImageView) convertView
					.findViewById(R.id.img_icon));
			TextView txt_name = (TextView) convertView
					.findViewById(R.id.txt_name);
			img_icon.setImageDrawable(item.icon);
			txt_name.setText(item.title);
			return convertView;
		}

		return null;
	}
}
