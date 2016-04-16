package com.project.mechanic.adapter;

import java.util.List;
import java.util.Map;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.StaticValues;
import com.project.mechanic.entity.Object;
import com.project.mechanic.entity.Users;
import com.project.mechanic.fragment.IntroductionFragment;
import com.project.mechanic.fragment.UnavailableIntroduction;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.Deleting;
import com.project.mechanic.service.Saving;
import com.project.mechanic.utility.Utility;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ObjectListAdapter
		extends ArrayAdapter<Object> /* implements AsyncInterface */ {

	Context context;
	List<Object> list;
	// int lastPosition = 0;
	DataBaseAdapter adapter;
	// RatingBar rating;
	Utility util;
	Fragment fr;
	RelativeLayout followLayout;
	RelativeLayout.LayoutParams paramsfollow, paramsVisit;
	// ProgressBar LoadingProgress;
	boolean IsShow;
	String DateTime;
	int Type;
	int etebarDay = 365; // constant for agahi
	int diff;

	Saving saving;
	Deleting deleting;
	Map<String, String> params;

	ProgressDialog ringProgressDialog;
	Users currentUser;
	Object person;
	boolean flag;
	int ItemId, userId, typeId;
	int counterVisit = 0;
	String serverDate = "";
	Bitmap imageBitmap;
	int cityId;
	// TextView txt1, countFollow, countVisit;
	//
	// ImageView star1, star2, star3, star4, star5, profileIco;
	//
	// RelativeLayout rl;
	LayoutInflater myInflater;

	public ObjectListAdapter(Context context, int resource, List<Object> objact, Fragment fr, boolean IsShow,
			String DateTime, int Type, int cityId) {
		super(context, resource, objact);
		this.context = context;
		this.list = objact;
		adapter = new DataBaseAdapter(context);
		util = new Utility(context);
		this.fr = fr;
		this.IsShow = IsShow;
		this.DateTime = DateTime;
		this.Type = Type;
		this.cityId = cityId;
		myInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	@SuppressLint("holder")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		convertView = myInflater.inflate(R.layout.row_object, null);

		final TextView titlePage = (TextView) convertView.findViewById(R.id.Rowobjecttxt);
		TextView countFollow = (TextView) convertView.findViewById(R.id.countlikepage);
		TextView countVisit = (TextView) convertView.findViewById(R.id.viewPageCount);

		// holder.ratingBar = (RatingBar)
		// convertView.findViewById(R.id.ratingBar1);

		ImageView star1 = (ImageView) convertView.findViewById(R.id.star1);
		ImageView star2 = (ImageView) convertView.findViewById(R.id.star2);
		ImageView star3 = (ImageView) convertView.findViewById(R.id.star3);
		ImageView star4 = (ImageView) convertView.findViewById(R.id.star4);
		ImageView star5 = (ImageView) convertView.findViewById(R.id.star5);

		ImageView profileImage = (ImageView) convertView.findViewById(R.id.icon_object);

		RelativeLayout layout = (RelativeLayout) convertView.findViewById(R.id.propertiesObject);

		person = list.get(position);

		titlePage.setText(person.getName());

		titlePage.setTypeface(util.SetFontCasablanca());

		int rate = person.getRate();

		if (rate > 0) {

			switch (rate) {
			case 1:
				star1.setBackgroundResource(R.drawable.ic_star_on);

				break;
			case 2:
				star1.setBackgroundResource(R.drawable.ic_star_on);
				star2.setBackgroundResource(R.drawable.ic_star_on);

				break;

			case 3:
				star1.setBackgroundResource(R.drawable.ic_star_on);
				star2.setBackgroundResource(R.drawable.ic_star_on);
				star3.setBackgroundResource(R.drawable.ic_star_on);

				break;

			case 4:
				star1.setBackgroundResource(R.drawable.ic_star_on);
				star2.setBackgroundResource(R.drawable.ic_star_on);
				star3.setBackgroundResource(R.drawable.ic_star_on);
				star4.setBackgroundResource(R.drawable.ic_star_on);

				break;

			case 5:
				star1.setBackgroundResource(R.drawable.ic_star_on);
				star2.setBackgroundResource(R.drawable.ic_star_on);
				star3.setBackgroundResource(R.drawable.ic_star_on);
				star4.setBackgroundResource(R.drawable.ic_star_on);
				star5.setBackgroundResource(R.drawable.ic_star_on);

				break;

			default:
				break;
			}

		}

		// holder.ratingBar.setRating(person.getRate());

		// holder.ratingBar.setEnabled(false);

		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(layout.getLayoutParams());

		lp.width = (int) (util.getScreenwidth() / StaticValues.RateImageObjectPage);
		lp.height = (int) (util.getScreenwidth() / StaticValues.RateImageObjectPage);
		lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		lp.addRule(RelativeLayout.CENTER_VERTICAL);
		lp.setMargins(10, 10, 10, 10);

		String pathProfile = person.getImagePath2();

		if (!"".equals(pathProfile))

			try {
				imageBitmap = BitmapFactory.decodeFile(pathProfile);

			} catch (OutOfMemoryError exception) {
				exception.printStackTrace();
			}

		if (imageBitmap != null) {

			profileImage.setImageBitmap(Utility.getclip(imageBitmap));
			profileImage.setLayoutParams(lp);

		} else {
			profileImage.setImageResource(R.drawable.no_img_profile);
			profileImage.setLayoutParams(lp);
		}

		String commitDate = person.getDate(); // tarikhe ijad safhe
		final SharedPreferences currentTime = context.getSharedPreferences("time", 0);

		String time = currentTime.getString("time", "-1");

		countVisit.setText(person.getCountView() + "");
		// adapter.open();
		// int followersCount = adapter.LikeInObject_count(person.getId(),
		// 0);
		// adapter.close();
		countFollow.setText(person.getCountFollower() + "");
		if (person.getActiveDate() == null) {

			if (commitDate != null && !"".equals(commitDate)) {

				diff = util.differentTwoDate(commitDate, time);

				if (diff == 30) {

					adapter.open();
					adapter.SetIsActive(person.getId(), 0);
					adapter.close();

				}

				// baghiMandeh.setText(String.valueOf(diff));
			}
		} else {

			diff = util.differentTwoDate(person.getActiveDate(), time);

			if (diff <= 0) {

				adapter.open();
				adapter.SetIsActive(person.getId(), 0);
				adapter.close();

			}

		}

		currentUser = util.getCurrentUser();

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				SharedPreferences sendDataID = context.getSharedPreferences("Id", 0);

				if (person.getIsActive() == 0) {

					UnavailableIntroduction fragment = new UnavailableIntroduction();
					FragmentTransaction trans = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame, fragment);
					trans.addToBackStack("MainBrandFragment");

					String item = titlePage.getText().toString();

					int id = 0;
					for (Object object : list) {
						if (item.equals(object.getName())) {
							// check authentication and authorization
							id = object.getId();
							sendDataID.edit().putInt("main_Id", id).commit();

						}

					}

					Bundle bundle = new Bundle();
					bundle.putString("Id", String.valueOf(id));
					fragment.setArguments(bundle);
					trans.commit();

				} else {

					IntroductionFragment fragment = new IntroductionFragment(cityId);
					FragmentTransaction trans = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame, fragment);

					trans.addToBackStack("MainBrandFragment");

					String item = titlePage.getText().toString();

					int id = 0;
					for (Object object : list) {
						if (item.equals(object.getName())) {
							// check authentication and authorization
							id = object.getId();
							sendDataID.edit().putInt("main_Id", id).commit();

						}

					}

					Bundle bundle = new Bundle();
					bundle.putString("Id", String.valueOf(id));
					bundle.putInt("positionBrand", position);

					fragment.setArguments(bundle);
					trans.commit();
				}
				ItemId = person.getId();

			}
		});
		// final Animation animSideDown = AnimationUtils.loadAnimation(context,
		// R.anim.slide_down);
		//
		// convertView.setAnimation(animSideDown);

		return convertView;
	}

}

// class ViewHolder {
//
// public TextView titlePage;
// public TextView countVisit;
// public TextView countFollow;
//
//// public RatingBar ratingBar;
//
// public ImageView star1;
// public ImageView star2;
// public ImageView star3;
// public ImageView star4;
// public ImageView star5;
// public ImageView profileImage;
//
// public RelativeLayout layout;
//
// public int position;
// }