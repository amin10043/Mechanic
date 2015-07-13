package com.project.mechanic.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.entity.LikeInFroum;
import com.project.mechanic.entity.Users;
import com.project.mechanic.fragment.DialogAnad;
import com.project.mechanic.fragment.DialogPersonLikedFroum;
import com.project.mechanic.fragment.DialogcmtInfroum;
import com.project.mechanic.fragment.DisplayPersonalInformationFragment;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class PersonLikedAdapter extends ArrayAdapter<LikeInFroum> {
	Context context;
	List<LikeInFroum> myList;
	DataBaseAdapter adapter;
	Utility util;
	Users user;
	int userId;
	DialogPersonLikedFroum dia;

	public PersonLikedAdapter(Context context, int resource,
			ArrayList<LikeInFroum> objects) {
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

		LikeInFroum likes = myList.get(position);

		adapter.open();
		 user = adapter.getUserById(likes.getUserid());
		adapter.close();
				namePerson.setText(user.getName());
		DateLiked.setText(util.getPersianDate(likes.getDatetime()));

		RelativeLayout rl = (RelativeLayout) convertView
				.findViewById(R.id.rlfroumrl);

		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				rl.getLayoutParams());

		lp.width = (util.getScreenwidth() / 8);
		lp.height = (util.getScreenwidth() / 8);
		lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		lp.setMargins(5, 5, 5, 5);

		if (user.getImage() == null) {
			peronImage.setImageResource(R.drawable.no_img_profile);
			peronImage.setLayoutParams(lp);

		} else {
			byte[] byteImageProfile = user.getImage();

			Bitmap bmp = BitmapFactory.decodeByteArray(byteImageProfile, 0,
					byteImageProfile.length);

			peronImage.setImageBitmap(Utility.getRoundedCornerBitmap(bmp, 50));
			peronImage.setLayoutParams(lp);
		}
	
		peronImage.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				LikeInFroum likes = myList.get(position);
				adapter.open();
				 user = adapter.getUserById(likes.getUserid());
				adapter.close();
				userId= user.getId();
				FragmentTransaction trans = ((MainActivity) context)
						.getSupportFragmentManager().beginTransaction();
				DisplayPersonalInformationFragment fragment = new DisplayPersonalInformationFragment();
				Bundle bundle = new Bundle();
				bundle.putInt("userId", userId);
				fragment.setArguments(bundle);
				trans.replace(R.id.content_frame, fragment);
				trans.commit();
			}
		});
		

		return convertView;
	}

}
