package com.project.mechanic.fragment;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.adapter.ExpandableCommentFroum;
import com.project.mechanic.entity.CommentInFroum;
import com.project.mechanic.entity.Froum;
import com.project.mechanic.entity.Users;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class FroumFragment extends Fragment {

	// start defined by masoud

	DataBaseAdapter adapter;
	ExpandableCommentFroum exadapter;

	TextView titletxt, descriptiontxt, dateTopic, countComment, countLike,
			nametxt;
	LinearLayout addComment, likeTopic;
	ImageButton sharebtn;
	ImageView profileImg;
	int froumid;

	Froum topics;

	DialogcmtInfroum dialog;
	ArrayList<CommentInFroum> commentGroup, ReplyGroup;
	String currentDate;

	Map<CommentInFroum, List<CommentInFroum>> mapCollection;
	ExpandableListView exlistview;

	View header;
	Users CurrentUser;
	int IDcurrentUser;
	PersianDate date;
	Utility util;
	int id;
	Users user;

	// end defined by masoud

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceStdataate) {

		((MainActivity) getActivity()).setActivityTitle(R.string.Forums);
		View view = inflater.inflate(R.layout.fragment_froum, null);

		adapter = new DataBaseAdapter(getActivity());
		util = new Utility(getActivity());

		user = new Users();

		date = new PersianDate();
		currentDate = date.todayShamsi();

		header = getActivity().getLayoutInflater().inflate(
				R.layout.header_expandable, null);

		// start find view

		titletxt = (TextView) header.findViewById(R.id.title_topic);
		descriptiontxt = (TextView) header.findViewById(R.id.description_topic);
		dateTopic = (TextView) header.findViewById(R.id.date_cc);
		countComment = (TextView) header
				.findViewById(R.id.numberOfCommentTopic);
		countLike = (TextView) header.findViewById(R.id.txtNumofLike_CmtFroum);
		nametxt = (TextView) header.findViewById(R.id.name_cc);

		addComment = (LinearLayout) header.findViewById(R.id.addCommentToTopic);
		likeTopic = (LinearLayout) header.findViewById(R.id.LikeTopicLinear);

		sharebtn = (ImageButton) header.findViewById(R.id.sharefroumicon);
		profileImg = (ImageView) header.findViewById(R.id.iconfroumtitle);
		exlistview = (ExpandableListView) view.findViewById(R.id.commentlist);

		// end find view

		if (getArguments().getString("Id") != null)
			froumid = Integer.valueOf(getArguments().getString("Id"));

		adapter.open();
		CurrentUser = util.getCurrentUser();
		if (CurrentUser == null) {
			Toast.makeText(getActivity(), "ابتدا باید وارد شوید",
					Toast.LENGTH_SHORT).show();

		}

		else
			IDcurrentUser = CurrentUser.getId();

		topics = adapter.getFroumItembyid(froumid);
		Users u = adapter.getUserbyid(topics.getUserId());

		if (u != null) {

			nametxt.setText(u.getName());

			if (u.getImage() == null) {
				profileImg.setImageResource(R.drawable.no_img_profile);
			} else {
				byte[] bytepic = u.getImage();

				Bitmap bmp = BitmapFactory.decodeByteArray(bytepic, 0,
						bytepic.length);
				LinearLayout rl = (LinearLayout) header
						.findViewById(R.id.profileLinearcommenterinContinue);

				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
						rl.getLayoutParams());

				lp.width = util.getScreenwidth() / 7;
				lp.height = util.getScreenwidth() / 7;
				lp.setMargins(5, 5, 5, 5);
				profileImg.setImageBitmap(bmp);
				profileImg.setLayoutParams(lp);
			}
		}
		titletxt.setText(topics.getTitle());
		descriptiontxt.setText(topics.getDescription());
		countComment.setText(adapter.CommentInFroum_count(froumid).toString());
		countLike.setText(adapter.LikeInFroum_count(froumid).toString());
		dateTopic.setText(topics.getDate());

		adapter.close();

		addComment.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (CurrentUser == null) {
					Toast.makeText(getActivity(),
							"برای درج کامنت ابتدا باید وارد شوید",
							Toast.LENGTH_SHORT).show();

				} else {
					dialog = new DialogcmtInfroum(FroumFragment.this, 0,
							getActivity(), froumid, R.layout.dialog_addcomment);
					dialog.show();
					exadapter.notifyDataSetChanged();
				}
			}
		});
		adapter.open();

		commentGroup = adapter.getCommentInFroumbyPaperid(froumid, 0);

		mapCollection = new LinkedHashMap<CommentInFroum, List<CommentInFroum>>();

		List<CommentInFroum> reply = null;
		for (CommentInFroum comment : commentGroup) {
			reply = adapter
					.getReplyCommentbyCommentID(froumid, comment.getId());
			mapCollection.put(comment, reply);
		}
		adapter.close();

		exlistview.addHeaderView(header);
		exadapter = new ExpandableCommentFroum(getActivity(),
				(ArrayList<CommentInFroum>) commentGroup, mapCollection, this,
				froumid);

		exadapter.notifyDataSetChanged();
		exlistview.setAdapter(exadapter);
		adapter.open();

		if (CurrentUser == null
				|| !adapter.isUserLikedFroum(CurrentUser.getId(), froumid))
			likeTopic.setBackgroundResource(R.drawable.like_froum_off);
		else

			likeTopic.setBackgroundResource(R.drawable.like_froum);

		adapter.close();

		likeTopic.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				adapter.open();

				if (CurrentUser == null) {
					adapter.open();

					Toast.makeText(getActivity(),
							"برای درج لایک ابتدا باید وارد شوید",
							Toast.LENGTH_SHORT).show();
					adapter.close();
				} else {
					if (adapter.isUserLikedFroum(IDcurrentUser, froumid)) {
						adapter.open();
						likeTopic
								.setBackgroundResource(R.drawable.like_froum_off);
						int c = adapter.LikeInFroum_count(froumid) - 1;
						countLike.setText(String.valueOf(c));
						adapter.deleteLikeFromFroum(IDcurrentUser, froumid);
						adapter.close();
					} else {
						adapter.open();
						likeTopic.setBackgroundResource(R.drawable.like_froum);
						adapter.insertLikeInFroumToDb(IDcurrentUser, froumid,
								currentDate, 0);

						countLike.setText(adapter.LikeInFroum_count(froumid)
								.toString());

						adapter.close();

					}

				}
				adapter.close();

			}

		});

		sharebtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent sharingIntent = new Intent(
						android.content.Intent.ACTION_SEND);
				sharingIntent.setType("text/plain");
				// String shareBody = x.getDescription();
				sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
						topics.getTitle());
				sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
						topics.getDescription());
				startActivity(Intent.createChooser(sharingIntent,
						"اشتراک از طریق"));
			}
		});

		return view;
	}

	public int getFroumId() {
		return id;
	}

	private void setGroupIndicatorToRight() {
		/* Get the screen width */
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels;

		exlistview.setIndicatorBounds(width - getDipsFromPixel(35), width
				- getDipsFromPixel(5));
	}

	public int getDipsFromPixel(float pixels) {
		// Get the screen's density scale
		final float scale = getResources().getDisplayMetrics().density;
		// Convert the dps to pixels, based on density scale
		return (int) (pixels * scale + 0.5f);
	}

	public void updateList() {
		adapter.open();

		commentGroup = adapter.getCommentInFroumbyPaperid(froumid, 0);

		mapCollection = new LinkedHashMap<CommentInFroum, List<CommentInFroum>>();

		List<CommentInFroum> reply = null;
		for (CommentInFroum comment : commentGroup) {
			reply = adapter
					.getReplyCommentbyCommentID(froumid, comment.getId());
			mapCollection.put(comment, reply);
		}

		countComment.setText(adapter.CommentInFroum_count(froumid).toString());

		adapter.close();

		exadapter = new ExpandableCommentFroum(getActivity(),
				(ArrayList<CommentInFroum>) commentGroup, mapCollection, this,
				froumid);

		exadapter.notifyDataSetChanged();
		exlistview.setAdapter(exadapter);

	}

}
