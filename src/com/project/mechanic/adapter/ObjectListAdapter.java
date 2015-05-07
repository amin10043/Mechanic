package com.project.mechanic.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.entity.Object;
import com.project.mechanic.fragment.IntroductionFragment;
import com.project.mechanic.fragment.MainFragment;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class ObjectListAdapter extends ArrayAdapter<Object> {

	Context context;
	List<Object> list;
	int lastPosition = 0;
	DataBaseAdapter adapter;
	RatingBar rating;
	Utility util;

	public ObjectListAdapter(Context context, int resource, List<Object> objact) {
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
		rating = (RatingBar) convertView.findViewById(R.id.ratingBar1);

		rating.setRating(person.getRate());
		rating.setEnabled(false);

		// String item = txt1.getText().toString();

		// final ImageView img = (ImageView) convertView
		// .findViewById(R.id.icon_item);
		// final ImageView star1Img = (ImageView) convertView
		// .findViewById(R.id.star1);
		// final ImageView star2Img = (ImageView) convertView
		// .findViewById(R.id.star2);
		// final ImageView star3Img = (ImageView) convertView
		// .findViewById(R.id.star3);
		// final ImageView star4Img = (ImageView) convertView
		// .findViewById(R.id.star4);
		// final ImageView star5Img = (ImageView) convertView
		// .findViewById(R.id.star5);

		// int id = 0;
		// for (Object listItem : list) {
		// if (item.equals(listItem.getName())) {
		// // check authentication and authorization
		// id = listItem.getId();
		// }
		// }
		//
		// adapter.open();
		// int res = adapter.getNumberOfListItemChilds(id);
		// if (res > 0) {
		// img.setBackgroundResource(R.drawable.profile_account);
		// star1Img.setBackgroundResource(R.drawable.ic_star_on);
		// star2Img.setBackgroundResource(R.drawable.ic_star_on);
		// star3Img.setBackgroundResource(R.drawable.ic_star_on);
		// star4Img.setBackgroundResource(R.drawable.ic_star_on);
		// star5Img.setBackgroundResource(R.drawable.ic_star_on);
		//
		// } else
		//
		// Toast.makeText(context, "object list adapter", Toast.LENGTH_SHORT)
		// .show();

		// adapter.close();

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				SharedPreferences sendDataID = context.getSharedPreferences(
						"Id", 0);

				FragmentTransaction trans = ((MainActivity) context)
						.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new IntroductionFragment());
				// trans.addToBackStack(null);
				trans.commit();

				String item = txt1.getText().toString();

				int id = 0;
				for (Object object : list) {
					if (item.equals(object.getName())) {
						// check authentication and authorization
						id = object.getId();
						sendDataID.edit().putInt("main_Id", id).commit();
						Toast.makeText(context, "object list adapter  = " + id,
								Toast.LENGTH_SHORT).show();

					}

				}

			}

		});
		return convertView;
	}

	public void onBackPressed() {

		FragmentTransaction trans = ((MainActivity) context)
				.getSupportFragmentManager().beginTransaction();
		trans.replace(R.id.content_frame, new MainFragment());
		trans.commit();

	}
}
