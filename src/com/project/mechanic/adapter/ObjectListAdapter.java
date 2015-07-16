package com.project.mechanic.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.entity.Froum;
import com.project.mechanic.entity.Object;
import com.project.mechanic.fragment.DialogLongClick;
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
	Fragment fr;

	public ObjectListAdapter(Context context, int resource,
			List<Object> objact, Fragment fr) {
		super(context, resource, objact);

		this.context = context;
		this.list = objact;
		adapter = new DataBaseAdapter(context);
		util = new Utility(context);
		this.fr = fr;

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

		final Object person = list.get(position);

		txt1.setText(person.getName());
		Typeface typeFace = Typeface.createFromAsset(context.getAssets(),
				"fonts/BROYA.TTF");
		txt1.setTypeface(typeFace);
		rating = (RatingBar) convertView.findViewById(R.id.ratingBar1);

		rating.setRating(person.getRate());

		rating.setEnabled(false);

		ImageView profileIco = (ImageView) convertView
				.findViewById(R.id.icon_object);

		RelativeLayout rl = (RelativeLayout) convertView
				.findViewById(R.id.main_icon_reply);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				rl.getLayoutParams());

		lp.width = (util.getScreenwidth() / 8);
		lp.height = (util.getScreenwidth() / 8);
		lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		lp.setMargins(5, 5, 5, 5);

		if (person.getImage2() == null) {
			profileIco.setImageResource(R.drawable.no_img_profile);
			profileIco.setLayoutParams(lp);

		} else {
			byte[] byteImageProfile = person.getImage2();

			Bitmap bmp = BitmapFactory.decodeByteArray(byteImageProfile, 0,
					byteImageProfile.length);

			profileIco.setImageBitmap(bmp);

			profileIco.setLayoutParams(lp);
		}
		convertView.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {

				int i = 0;
				int u = 0;
				String t = "";
				ListView listView = (ListView) v.getParent();
				int position = listView.getPositionForView(v);
				Object f = getItem(position);
				if (f != null) {
					u = f.getUserId();
					i = f.getId();
					t = f.getDescription();
				}
				adapter.open();
				int sumAgency = adapter.countSubAgencyBrand(f.getId());
				Toast.makeText(
						context,
						"sum agency = "
								+ adapter.countSubAgencyBrand(f.getId())
								+ "object id = " + i , 0).show();
				if (sumAgency > 0) {
					DialogLongClick dia = new DialogLongClick(context, 4, u, -1,
							fr , t);
					dia.show();

				} else {

					adapter.close();
					DialogLongClick dia = new DialogLongClick(context, 4, u, i,
							fr , t);
					Toast.makeText(context,
							"object id = " + i + " userId = " + u, 0).show();
					dia.show();
				}
				return true;
			}

		});
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

						//
						// Toast.makeText(context,
						// "parentId = " + object.getParentId(),
						// Toast.LENGTH_SHORT).show();

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
