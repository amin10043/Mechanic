package com.project.mechanic.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.entity.ListItem;
import com.project.mechanic.fragment.BerandFragment;
import com.project.mechanic.fragment.IntroductionFragment;
import com.project.mechanic.fragment.PublicationsFragment;
import com.project.mechanic.model.DataBaseAdapter;

public class BerandListAdapter extends ArrayAdapter<ListItem> {

	Context context;
	List<ListItem> list;
	ListItem tempItem;
	DataBaseAdapter adapter;
	int itemId;
	int lastPosition = 0;

	public BerandListAdapter(Context context, int resource,
			List<ListItem> objact, int id) {
		super(context, resource, objact);

		this.context = context;
		this.list = objact;
		adapter = new DataBaseAdapter(context);
		this.itemId = id;

	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater myInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		convertView = myInflater.inflate(R.layout.row_berand, parent, false);

		Animation animation = AnimationUtils.loadAnimation(getContext(),
				(position > lastPosition) ? R.anim.up_from_bottom
						: R.anim.down_from_top);
		convertView.startAnimation(animation);
		TextView txtName = (TextView) convertView
				.findViewById(R.id.row_berand_txt);

		// img.setBackgroundResource(R.drawable.google);

		tempItem = list.get(position);
		txtName.setText(tempItem.getName());

		Typeface typeFace = Typeface.createFromAsset(context.getAssets(),
				"fonts/BROYA.TTF");
		txtName.setTypeface(typeFace);

		String item = txtName.getText().toString();

		final ImageView img = (ImageView) convertView
				.findViewById(R.id.icon_item);
		final ImageView star1Img = (ImageView) convertView
				.findViewById(R.id.star1);
		final ImageView star2Img = (ImageView) convertView
				.findViewById(R.id.star2);
		final ImageView star3Img = (ImageView) convertView
				.findViewById(R.id.star3);
		final ImageView star4Img = (ImageView) convertView
				.findViewById(R.id.star4);
		final ImageView star5Img = (ImageView) convertView
				.findViewById(R.id.star5);

		int id = 0;
		for (ListItem listItem : list) {
			if (item.equals(listItem.getName())) {
				// check authentication and authorization
				id = listItem.getId();
			}
		}

		adapter.open();
		int res = adapter.getNumberOfListItemChilds(id);
		if (res > 0) {
			img.setVisibility(View.INVISIBLE);

			star1Img.setVisibility(View.INVISIBLE);
			star2Img.setVisibility(View.INVISIBLE);
			star3Img.setVisibility(View.INVISIBLE);
			star4Img.setVisibility(View.INVISIBLE);
			star5Img.setVisibility(View.INVISIBLE);

		} else

			img.setBackgroundResource(R.drawable.profile_account);
		star1Img.setBackgroundResource(R.drawable.ic_star_on);
		star2Img.setBackgroundResource(R.drawable.ic_star_on);
		star3Img.setBackgroundResource(R.drawable.ic_star_on);
		star4Img.setBackgroundResource(R.drawable.ic_star_on);
		star5Img.setBackgroundResource(R.drawable.ic_star_on);

		adapter.close();

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				RelativeLayout parentlayout = (RelativeLayout) v;
				TextView txtName = (TextView) parentlayout
						.findViewById(R.id.row_berand_txt);
				String item = txtName.getText().toString();

				int id = 0;
				for (ListItem listItem : list) {
					if (item.equals(listItem.getName())) {
						// check authentication and authorization
						id = listItem.getId();
					}
				}

				adapter.open();
				int res = adapter.getNumberOfListItemChilds(id);

				adapter.close();

				if (res > 0) {
					FragmentTransaction trans = ((MainActivity) context)
							.getSupportFragmentManager().beginTransaction();
					BerandFragment fragment = new BerandFragment();
					Bundle bundle = new Bundle();
					bundle.putString("Id", String.valueOf(id));
					fragment.setArguments(bundle);
					trans.replace(R.id.content_frame, fragment);
					trans.addToBackStack(null);
					trans.commit();

					Toast.makeText(context, "item id =" + id, 500).show();
				} else {
					FragmentTransaction trans = ((MainActivity) context)
							.getSupportFragmentManager().beginTransaction();
					IntroductionFragment fragment = new IntroductionFragment();
					Bundle bundle = new Bundle();
					bundle.putString("Id", String.valueOf(id));
					fragment.setArguments(bundle);
					trans.replace(R.id.content_frame, fragment);
					trans.addToBackStack(null);
					Toast.makeText(context, "item id =" + id, 500).show();

					trans.commit();
				}
			}
		});

		return convertView;
	}
		
		
		Public get
	}
}
