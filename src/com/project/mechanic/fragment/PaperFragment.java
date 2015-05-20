package com.project.mechanic.fragment;

import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mechanic.R;
import com.project.mechanic.adapter.PaperListAdapter;
import com.project.mechanic.entity.CommentInPaper;
import com.project.mechanic.entity.Paper;
import com.project.mechanic.entity.Users;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class PaperFragment extends Fragment {

	DataBaseAdapter adapter;
	int paperID;
	LinearLayout btnAddcmt;
	LinearLayout Like;
	TextView NumofLike, NumofComment, txttitle, txttitleDes, txtname, txtdate;
	DialogcmtInPaper dialog;

	ListView lst;
	ArrayList<CommentInPaper> mylist;
	PaperListAdapter PaperListadapter;
	int like = 0;
	Utility util;
	View header;
	Users CurrentUser;
	ImageView icon, sharebtn;
	String currentDate;
	PersianDate date;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_paper, null);
		header = getActivity().getLayoutInflater().inflate(
				R.layout.header_expandable, null);
		util = new Utility(getActivity());
		date = new PersianDate();
		currentDate = date.todayShamsi();

		CurrentUser = util.getCurrentUser();

		lst = (ListView) view.findViewById(R.id.listViewnewspaper);

		btnAddcmt = (LinearLayout) header.findViewById(R.id.addCommentToTopic);
		Like = (LinearLayout) header.findViewById(R.id.LikeTopicLinear);

		NumofComment = (TextView) header
				.findViewById(R.id.numberOfCommentTopic);
		NumofLike = (TextView) header.findViewById(R.id.txtNumofLike_CmtFroum);
		txttitle = (TextView) header.findViewById(R.id.title_topic);
		txttitleDes = (TextView) header.findViewById(R.id.description_topic);
		txtdate = (TextView) header.findViewById(R.id.date_cc);
		txtname = (TextView) header.findViewById(R.id.name_cc);

		icon = (ImageView) header.findViewById(R.id.iconfroumtitle);
		sharebtn = (ImageView) header.findViewById(R.id.sharefroumicon);

		adapter = new DataBaseAdapter(getActivity());
		// if (getArguments().getString("Id") != null) {
		{
			adapter.open();

			paperID = Integer.valueOf(getArguments().getString("Id"));

			if (CurrentUser == null
					|| !adapter.isUserLikedPaper(CurrentUser.getId(), paperID))
				Like.setBackgroundResource(R.drawable.like_froum_off);
			else

				Like.setBackgroundResource(R.drawable.like_froum);

			NumofComment.setText(adapter.CommentInPaper_count(paperID)
					.toString());

			NumofLike.setText(adapter.LikeInPaper_count(paperID).toString());

			// Bundle bundle = new Bundle();
			// bundle.getString("Id", String.valueOf(id));
			// id = Integer.valueOf(getArguments().getString("Id"));
			mylist = adapter.getCommentInPaperbyPaperid(paperID);
			final Paper p = adapter.getPaperItembyid(paperID);
			Users u = adapter.getUserbyid(p.getUserId());

			txtname.setText(u.getName());

			txttitle.setText(p.getTitle());
			txttitleDes.setText(p.getContext());
			txtdate.setText(p.getDate());

			if (u.getImage() == null) {
				icon.setImageResource(R.drawable.no_img_profile);
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
				icon.setImageBitmap(bmp);
				icon.setLayoutParams(lp);
			}
			adapter.close();
			if (lst != null) {
				lst.addHeaderView(header);
				PaperListadapter = new PaperListAdapter(getActivity(),
						R.layout.raw_papercmt, mylist, PaperFragment.this);

				lst.setAdapter(PaperListadapter);
			}
			sharebtn.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Intent sharingIntent = new Intent(
							android.content.Intent.ACTION_SEND);
					sharingIntent.setType("text/plain");
					// String shareBody = x.getDescription();
					sharingIntent.putExtra(
							android.content.Intent.EXTRA_SUBJECT, p.getTitle());
					sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
							p.getContext());
					startActivity(Intent.createChooser(sharingIntent,
							"اشتراک از طریق"));
				}
			});

			Like.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					if (CurrentUser == null) {
						Toast.makeText(getActivity(), "ابتدا باید وارد شوید",
								Toast.LENGTH_SHORT).show();
						return;

					} else {
						adapter.open();
						if (adapter.isUserLikedPaper(CurrentUser.getId(),
								paperID)) {
							Like.setBackgroundResource(R.drawable.like_froum_off);
							int c = adapter.LikeInPaper_count(paperID) - 1;
							NumofLike.setText(String.valueOf(c));
							adapter.deleteLikeFromPaper(CurrentUser.getId(),
									paperID);
						} else {
							adapter.open();
							Like.setBackgroundResource(R.drawable.like_froum);
							adapter.insertLikeInPaperToDb(CurrentUser.getId(),
									paperID, currentDate);
							NumofLike.setText(String.valueOf(adapter
									.LikeInPaper_count(paperID)));
							adapter.close();
						}

					}
					adapter.close();

				}
			});

			btnAddcmt.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					if (CurrentUser == null) {
						Toast.makeText(getActivity(), "ابتدا باید وارد شوید",
								Toast.LENGTH_SHORT).show();
						return;
					} else {
						dialog = new DialogcmtInPaper(PaperFragment.this,
								getActivity(), R.layout.dialog_addcomment,
								paperID);
						dialog.show();
					}

				}
			});
			return view;
		}

	}

	/*
	 * @Override public void onResume() { // TODO Auto-generated method stub
	 * super.onResume(); lst.deferNotifyDataSetChanged(); }
	 */

	public void updateView2() {
		adapter.open();
		mylist = adapter.getCommentInPaperbyPaperid(paperID);
		NumofComment.setText(adapter.CommentInPaper_count(paperID).toString());

		adapter.close();

		PaperListadapter = new PaperListAdapter(getActivity(),
				R.layout.raw_papercmt, mylist, PaperFragment.this);
		PaperListadapter.notifyDataSetChanged();
		lst.setAdapter(PaperListadapter);

	}

}
