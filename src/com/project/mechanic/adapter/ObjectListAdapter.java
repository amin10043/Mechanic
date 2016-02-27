package com.project.mechanic.adapter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.StaticValues;
import com.project.mechanic.entity.Object;
import com.project.mechanic.entity.Users;
import com.project.mechanic.entity.Visit;
import com.project.mechanic.fragment.IntroductionFragment;
import com.project.mechanic.fragment.LoginFragment;
import com.project.mechanic.fragment.MainFragment;
import com.project.mechanic.fragment.UnavailableIntroduction;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.Deleting;
import com.project.mechanic.service.Saving;
import com.project.mechanic.service.ServerDate;
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
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ObjectListAdapter
		extends ArrayAdapter<Object> /* implements AsyncInterface */ {

	Context context;
	List<Object> list;
	int lastPosition = 0;
	DataBaseAdapter adapter;
	RatingBar rating;
	Utility util;
	Fragment fr;
	RelativeLayout followLayout;
	RelativeLayout.LayoutParams paramsfollow, paramsVisit;
	ProgressBar LoadingProgress;
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

	public ObjectListAdapter(Context context, int resource, List<Object> objact, Fragment fr, boolean IsShow,
			String DateTime, int Type) {
		super(context, resource, objact);
		this.context = context;
		this.list = objact;
		adapter = new DataBaseAdapter(context);
		util = new Utility(context);
		this.fr = fr;
		this.IsShow = IsShow;
		this.DateTime = DateTime;
		this.Type = Type;
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		LayoutInflater myInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		convertView = myInflater.inflate(R.layout.row_object, parent, false);

		lastPosition = position;

		final TextView txt1 = (TextView) convertView.findViewById(R.id.Rowobjecttxt);

		person = list.get(position);

		LoadingProgress = (ProgressBar) convertView.findViewById(R.id.progressBar1);

		txt1.setText(person.getName());

		txt1.setTypeface(util.SetFontCasablanca());
		rating = (RatingBar) convertView.findViewById(R.id.ratingBar1);

		int rate = person.getRate();

		if (rate > 0) {

			ImageView star1 = (ImageView) convertView.findViewById(R.id.star1);
			ImageView star2 = (ImageView) convertView.findViewById(R.id.star2);
			ImageView star3 = (ImageView) convertView.findViewById(R.id.star3);
			ImageView star4 = (ImageView) convertView.findViewById(R.id.star4);
			ImageView star5 = (ImageView) convertView.findViewById(R.id.star5);

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

		rating.setRating(person.getRate());

		rating.setEnabled(false);

		ImageView profileIco = (ImageView) convertView.findViewById(R.id.icon_object);

		RelativeLayout rl = (RelativeLayout) convertView.findViewById(R.id.propertiesObject);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(rl.getLayoutParams());

		lp.width = (int) (util.getScreenwidth() / StaticValues.RateImageObjectPage);
		lp.height = (int) (util.getScreenwidth() / StaticValues.RateImageObjectPage);
		lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		lp.addRule(RelativeLayout.CENTER_VERTICAL);
		lp.setMargins(10, 10, 10, 10);

		String pathProfile = person.getImagePath2();

		Bitmap profileImage = BitmapFactory.decodeFile(pathProfile);

		if (profileImage != null) {

			profileIco.setImageBitmap(Utility.getclip(profileImage));
			profileIco.setLayoutParams(lp);
			LoadingProgress.setVisibility(View.GONE);

		} else {
			profileIco.setImageResource(R.drawable.no_img_profile);
			profileIco.setLayoutParams(lp);
		}
		if (IsShow == false)
			LoadingProgress.setVisibility(View.GONE);

		String commitDate = person.getDate(); // tarikhe ijad safhe
		final SharedPreferences currentTime = context.getSharedPreferences("time", 0);

		String time = currentTime.getString("time", "-1");

		TextView countFollow = (TextView) convertView.findViewById(R.id.countlikepage);
		TextView countVisit = (TextView) convertView.findViewById(R.id.viewPageCount);

		countVisit.setText(person.getCountView() + "");
		adapter.open();
		int followersCount = adapter.LikeInObject_count(person.getId(), 0);
		adapter.close();
		countFollow.setText(followersCount + "");
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

		// else {
		// baghiMandeh.setText("نا معلوم");
		// }
		// convertView.setOnLongClickListener(new OnLongClickListener() {
		//
		// @Override
		// public boolean onLongClick(View v) {
		//
		// int i = 0;
		// int u = 0;
		// String t = "";
		// ListView listView = (ListView) v.getParent();
		// int position = listView.getPositionForView(v);
		// Object f = getItem(position);
		// if (f != null) {
		// u = f.getUserId();
		// i = f.getId();
		// t = f.getName();
		// }
		//
		// DialogLongClick dia = new DialogLongClick(context, 4, u, i, fr,
		// t);
		// Toast.makeText(context, "object id = " + i + " userId = " + u,
		// 0).show();
		// WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		// lp.copyFrom(dia.getWindow().getAttributes());
		// lp.width = (int) (util.getScreenwidth() / 1.5);
		// lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		// ;
		// dia.show();
		//
		// dia.getWindow().setAttributes(lp);
		// dia.getWindow().setBackgroundDrawable(
		// new ColorDrawable(android.graphics.Color.TRANSPARENT));
		// return true;
		// }
		//
		// });
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				SharedPreferences sendDataID = context.getSharedPreferences("Id", 0);

				if (person.getIsActive() == 0) {

					UnavailableIntroduction fragment = new UnavailableIntroduction();
					FragmentTransaction trans = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame, fragment);
					trans.addToBackStack("MainBrandFragment");

					String item = txt1.getText().toString();

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

					IntroductionFragment fragment = new IntroductionFragment();
					FragmentTransaction trans = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame, fragment);

					trans.addToBackStack("MainBrandFragment");

					String item = txt1.getText().toString();

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

	// @Override
	// public void processFinish(String output) {
	//
	// serverDate = output;
	//
	// if (flag == true) {
	//
	// sendVisit();
	//
	// } else {
	//
	// if (ringProgressDialog != null)
	// ringProgressDialog.dismiss();
	// }
	// }
}