package com.project.mechanic.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.entity.Object;
import com.project.mechanic.fragment.IntroductionFragment;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class MainBrandListAdapter extends ArrayAdapter<Object> {

	Context context;
	List<Object> list;
	int lastPosition = 0;
	DataBaseAdapter adapter;
	RatingBar rating;
	Utility util;

	public MainBrandListAdapter(Context context, int resource,
			List<Object> objact) {
		super(context, resource, objact);

		this.context = context;
		this.list = objact;
		adapter = new DataBaseAdapter(context);
		util = new Utility(context);

	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		LayoutInflater myInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		convertView = myInflater.inflate(R.layout.row_object, parent, false);

		Animation animation = AnimationUtils.loadAnimation(getContext(),
				(position > lastPosition) ? R.anim.up_from_bottom
						: R.anim.down_from_top);
		convertView.startAnimation(animation);
		lastPosition = position;

		final TextView txt1 = (TextView) convertView
				.findViewById(R.id.Rowobjecttxt);

		Object person = list.get(position);

		txt1.setText(person.getName());
		Typeface typeFace = Typeface.createFromAsset(context.getAssets(),
				"fonts/BROYA.TTF");
		txt1.setTypeface(typeFace);

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				LinearLayout parentlayout = (LinearLayout) v;
				TextView txtName = (TextView) parentlayout
						.findViewById(R.id.txtName);
				String item = txtName.getText().toString();

				int id = 0;
				for (Object Object : list) {
					if (item.equals(Object.getName())) {
						// check authentication and authorization
						id = Object.getId();
						break;
					}
				}

				adapter.close();

				// SharedPreferences sendDataID = context.getSharedPreferences(
				// "Id", 0);

				FragmentTransaction trans = ((MainActivity) context)
						.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new IntroductionFragment());
				trans.addToBackStack(null);
				trans.commit();

				// String item = txt1.getText().toString();

				// int id = 0;
				// for (Object object : list) {
				// if (item.equals(object.getName())) {
				// // check authentication and authorization
				// id = object.getId();
				// sendDataID.edit().putInt("main_Id", id).commit();
				// Toast.makeText(context, id + "", Toast.LENGTH_SHORT)
				// .show();
				// }
				//
				// }

			}

		});
		return convertView;
	}

}
