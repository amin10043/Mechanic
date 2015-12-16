package com.project.mechanic.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mechanic.R;
import com.project.mechanic.entity.LikeInPost;
import com.project.mechanic.entity.Users;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class PersonLikedPostAdapter extends ArrayAdapter<LikeInPost> {
	Context context;
	List<LikeInPost> myList;
	DataBaseAdapter adapter;
	Utility util;
	Users user;
	int userId;

	public PersonLikedPostAdapter(Context context, int resource,
			ArrayList<LikeInPost> objects) {
		super(context, resource, objects);
		this.context = context;
		myList = objects;
		adapter = new DataBaseAdapter(context);
		util = new Utility(context);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			LayoutInflater myInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			convertView = myInflater.inflate(R.layout.row_person_liked, parent,
					false);
		}
		ImageView peronImage = (ImageView) convertView
				.findViewById(R.id.imgvCmtuser_Froumfragment);

		TextView namePerson = (TextView) convertView
				.findViewById(R.id.rawUsernamecmttxt_cmt);

		TextView DateLiked = (TextView) convertView
				.findViewById(R.id.dateLiked);

		LikeInPost likes = myList.get(position);

		adapter.open();
		user = adapter.getUserById(likes.getId());
		adapter.close();
		namePerson.setText(user.getName());
		namePerson.setTypeface(util.SetFontCasablanca());
		DateLiked.setText(util.getPersianDate(likes.getDate()));

		RelativeLayout rl = (RelativeLayout) convertView
				.findViewById(R.id.rlfroumrl);

		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				rl.getLayoutParams());

		lp.width = (util.getScreenwidth() / 6);
		lp.height = (util.getScreenwidth() / 6);
		lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		lp.setMargins(5, 5, 5, 5);

		if (user.getImagePath() != null) {

			Bitmap bmp = BitmapFactory.decodeFile(user.getImagePath());

			peronImage.setImageBitmap(bmp);
			peronImage.setLayoutParams(lp);

		} else {
			peronImage.setImageResource(R.drawable.no_img_profile);
			peronImage.setLayoutParams(lp);

		}

		// convertView.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// // LikeInFroum likes = myList.get(position);
		// // adapter.open();
		// // user = adapter.getUserById(likes.getUserid());
		// // adapter.close();
		// // userId = user.getId();
		// // FragmentTransaction trans = ((MainActivity) context)
		// // .getSupportFragmentManager().beginTransaction();
		// // InformationUser fragment = new InformationUser();
		// // Bundle bundle = new Bundle();
		// // bundle.putInt("userId", userId);
		// // fragment.setArguments(bundle);
		// // trans.addToBackStack(null);
		// // trans.replace(R.id.content_frame, fragment);
		// // trans.commit();
		//
		// }
		// });

		return convertView;
	}

}
