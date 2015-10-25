package com.project.mechanic.adapter;

import java.util.Calendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
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
	RelativeLayout followLayout;
	RelativeLayout.LayoutParams paramsfollow, paramsVisit;
	ProgressBar LoadingProgress;
	boolean IsShow;
	String DateTime;
	int Type;
	int etebarDay = 365; // constant for agahi

	public ObjectListAdapter(Context context, int resource,
			List<Object> objact, Fragment fr, boolean IsShow, String DateTime,
			int Type) {
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

		LayoutInflater myInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		convertView = myInflater.inflate(R.layout.row_object, parent, false);

		// Animation animation = AnimationUtils.loadAnimation(getContext(),
		// (position > lastPosition) ? R.anim.up_from_bottom
		// : R.anim.down_from_top);
		// convertView.startAnimation(animation);
		lastPosition = position;

		final TextView txt1 = (TextView) convertView
				.findViewById(R.id.Rowobjecttxt);

		final Object person = list.get(position);

		LoadingProgress = (ProgressBar) convertView
				.findViewById(R.id.progressBar1);

		txt1.setText(person.getName());

		txt1.setTypeface(util.SetFontCasablanca());
		rating = (RatingBar) convertView.findViewById(R.id.ratingBar1);

		rating.setRating(person.getRate());

		rating.setEnabled(false);

		ImageView profileIco = (ImageView) convertView
				.findViewById(R.id.icon_object);

		RelativeLayout rl = (RelativeLayout) convertView
				.findViewById(R.id.main_icon_reply);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				rl.getLayoutParams());

		lp.width = (util.getScreenwidth() / 4);
		lp.height = (util.getScreenwidth() / 4);
		lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		lp.setMargins(5, 0, 0, 0);

		String pathProfile = person.getImagePath2();

		Bitmap profileImage = BitmapFactory.decodeFile(pathProfile);

		if (profileImage != null) {

			profileIco.setImageBitmap(profileImage);
			profileIco.setLayoutParams(lp);
			LoadingProgress.setVisibility(View.GONE);

		} else {
			profileIco.setImageResource(R.drawable.no_img_profile);
			profileIco.setLayoutParams(lp);
		}
		if (IsShow == false)
			LoadingProgress.setVisibility(View.GONE);

		followLayout = (RelativeLayout) convertView
				.findViewById(R.id.propertiesObject);
		// visitLayout = (RelativeLayout)
		// convertView.findViewById(R.id.relativeLayout2);

		paramsfollow = new RelativeLayout.LayoutParams(
				followLayout.getLayoutParams());
		paramsVisit = new RelativeLayout.LayoutParams(
				followLayout.getLayoutParams());

		paramsfollow.width = (util.getScreenwidth());
		paramsfollow.height = (util.getScreenwidth() / 4);
		paramsfollow.addRule(RelativeLayout.LEFT_OF, R.id.icon_object);

		paramsVisit.width = (util.getScreenwidth() / 16);
		paramsVisit.height = (util.getScreenwidth() / 16);
		paramsVisit.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

		ImageView followIcon = (ImageView) convertView
				.findViewById(R.id.iconNumberLike);
		ImageView visitIcon = (ImageView) convertView
				.findViewById(R.id.iconNumberVisit);

		followLayout.setLayoutParams(paramsfollow);

		followIcon.setLayoutParams(paramsVisit);
		visitIcon.setLayoutParams(paramsVisit);

		TextView baghiMandeh = (TextView) convertView
				.findViewById(R.id.dayBaghiMandeh); // modate baghimande

		String commitDate = person.getDate(); // tarikhe ijad safhe

		if (commitDate != null && !"".equals(commitDate)) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(Long.valueOf(commitDate));
			calendar.add(Calendar.YEAR, 1);

			final SharedPreferences currentTime = context.getSharedPreferences(
					"time", 0);

			String time = currentTime.getString("time", "-1");

			Calendar calendarNow = Calendar.getInstance();
			calendarNow.setTimeInMillis(Long.valueOf(time));
			int diff = calendarNow.compareTo(calendar);

			baghiMandeh.setText(String.valueOf(diff));
		} else {
			baghiMandeh.setText("نا معلوم");
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
					t = f.getName();
				}

				DialogLongClick dia = new DialogLongClick(context, 4, u, i, fr,
						t);
				Toast.makeText(context, "object id = " + i + " userId = " + u,
						0).show();
				WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
				lp.copyFrom(dia.getWindow().getAttributes());
				lp.width = (int) (util.getScreenwidth() / 1.5);
				lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
				;
				dia.show();

				dia.getWindow().setAttributes(lp);
				dia.getWindow().setBackgroundDrawable(
						new ColorDrawable(android.graphics.Color.TRANSPARENT));
				return true;
			}

		});
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				SharedPreferences sendDataID = context.getSharedPreferences(
						"Id", 0);
				IntroductionFragment fragment = new IntroductionFragment();
				FragmentTransaction trans = ((MainActivity) context)
						.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, fragment);

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
				trans.addToBackStack(null);
				trans.commit();
			}

		});
		// final Animation animSideDown = AnimationUtils.loadAnimation(context,
		// R.anim.slide_down);
		//
		// convertView.setAnimation(animSideDown);

		return convertView;
	}

	public void onBackPressed() {

		FragmentTransaction trans = ((MainActivity) context)
				.getSupportFragmentManager().beginTransaction();
		trans.replace(R.id.content_frame, new MainFragment());
		trans.commit();

	}
}
