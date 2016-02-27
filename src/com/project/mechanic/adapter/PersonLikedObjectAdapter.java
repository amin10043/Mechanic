package com.project.mechanic.adapter;

import java.util.ArrayList;
import java.util.List;

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

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.StaticValues;
import com.project.mechanic.entity.LikeInObject;
import com.project.mechanic.entity.Users;
import com.project.mechanic.fragment.DisplayPersonalInformationFragment;
import com.project.mechanic.fragment.InformationUser;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class PersonLikedObjectAdapter extends ArrayAdapter<Users> {
	Context context;
	// List<LikeInObject> myList;
	DataBaseAdapter adapter;
	Utility util;
	int userId;
	List<String> addressImage;
	List<Users> users;
	List<String> dateLikeList;

	public PersonLikedObjectAdapter(Context context, int resource, List<Users> users, List<String> dateLikeList) {
		super(context, resource, users);
		this.context = context;
		adapter = new DataBaseAdapter(context);
		util = new Utility(context);
		this.users = users;
		this.dateLikeList = dateLikeList;

	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			LayoutInflater myInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			convertView = myInflater.inflate(R.layout.row_person_liked, parent, false);
		}
		ImageView peronImage = (ImageView) convertView.findViewById(R.id.imgvCmtuser_Froumfragment);

		TextView namePerson = (TextView) convertView.findViewById(R.id.rawUsernamecmttxt_cmt);

		TextView DateLiked = (TextView) convertView.findViewById(R.id.dateLiked);

		Users user = users.get(position);

		namePerson.setText(user.getName());
		namePerson.setTypeface(util.SetFontCasablanca());

		DateLiked.setText(util.getPersianDate(dateLikeList.get(position)));

		RelativeLayout rl = (RelativeLayout) convertView.findViewById(R.id.rlfroumrl);

		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(rl.getLayoutParams());

		lp.width = (int) (util.getScreenwidth() / StaticValues.RateImageUserLikedObject);
		lp.height = (int) (util.getScreenwidth() / StaticValues.RateImageUserLikedObject);
		lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		lp.setMargins(5, 5, 5, 5);

		if (user.getImagePath() == null) {
			peronImage.setImageResource(R.drawable.no_img_profile);
			peronImage.setLayoutParams(lp);

		} else {

			Bitmap bmp = BitmapFactory.decodeFile(user.getImagePath());

			if (bmp != null)
				peronImage.setImageBitmap(Utility.getclip(bmp));
			peronImage.setLayoutParams(lp);
		}

		return convertView;
	}
}
