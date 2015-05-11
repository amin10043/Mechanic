package com.project.mechanic.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mechanic.R;
import com.project.mechanic.entity.CommentInFroum;
import com.project.mechanic.entity.Users;
import com.project.mechanic.fragment.DialogcmtInfroum;
import com.project.mechanic.fragment.FroumFragment;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class ExpandableCommentFroum extends BaseExpandableListAdapter {

	Context context;
	private Map<CommentInFroum, List<CommentInFroum>> mapCollection;
	private ArrayList<CommentInFroum> cmt;
	DataBaseAdapter adapter;
	Utility util;
	FroumFragment f;
	int froumID;

	public ExpandableCommentFroum(Context context,
			ArrayList<CommentInFroum> laptops,
			Map<CommentInFroum, List<CommentInFroum>> mapCollection,
			FroumFragment f, int froumID) {
		this.context = context;
		this.mapCollection = mapCollection;
		this.cmt = laptops;
		adapter = new DataBaseAdapter(context);
		util = new Utility(context);
		this.f = f;
		this.froumID = froumID;

	}

	public Object getChild(int groupPosition, int childPosition) {
		return mapCollection.get(cmt.get(groupPosition)).get(childPosition);
	}

	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	public View getChildView(final int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		CommentInFroum reply = (CommentInFroum) getChild(groupPosition,
				childPosition);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.row_child_item, null);
		}

		TextView mainReply = (TextView) convertView
				.findViewById(R.id.reply_txt_child);

		TextView dateReply = (TextView) convertView
				.findViewById(R.id.date_replyed);
		TextView nameReplyer = (TextView) convertView
				.findViewById(R.id.name_replyed);

		ImageButton ReplyerPic = (ImageButton) convertView
				.findViewById(R.id.icon_reply_comment);

		adapter.open();

		final CommentInFroum comment = cmt.get(groupPosition);
		Users y = adapter.getUserbyid(comment.getUserid());

		byte[] byteImageProfile = y.getImage();

		Bitmap bmp = BitmapFactory.decodeByteArray(byteImageProfile, 0,
				byteImageProfile.length);

		ReplyerPic.setImageBitmap(bmp);

		RelativeLayout rl = (RelativeLayout) convertView
				.findViewById(R.id.main_icon_reply);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				rl.getLayoutParams());

		lp.width = util.getScreenwidth() / 7;
		lp.height = util.getScreenwidth() / 7;
		lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		lp.setMargins(5, 5, 5, 5);
		ReplyerPic.setLayoutParams(lp);

		mainReply.setText(reply.getDesk());
		dateReply.setText(reply.getDatetime());
		nameReplyer.setText(y.getName());
		adapter.close();

		notifyDataSetChanged();
		return convertView;
	}

	public int getChildrenCount(int groupPosition) {
		if (mapCollection.get(cmt.get(groupPosition)) != null)
			return mapCollection.get(cmt.get(groupPosition)).size();
		return 0;
	}

	public Object getGroup(int groupPosition) {
		return cmt.get(groupPosition);
	}

	public int getGroupCount() {
		return cmt.size();
	}

	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	public View getGroupView(final int groupPosition, final boolean isExpanded,
			View convertView, ViewGroup parent) {

		// SharedPreferences sendIDtoExapandAdapter = context
		// .getSharedPreferences("Id", 0);
		// final int froumID = sendIDtoExapandAdapter.getInt("main_Id", -1);

		adapter.open();

		final CommentInFroum comment = cmt.get(groupPosition);
		Users x = adapter.getUserbyid(comment.getUserid());

		adapter.close();

		// String laptopName = (String) getGroup(groupPosition);
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.row_group_item, null);
		}

		// start find view
		final TextView mainComment = (TextView) convertView
				.findViewById(R.id.peygham);

		TextView nameCommenter = (TextView) convertView
				.findViewById(R.id.name_froum_profile);

		final TextView countLike = (TextView) convertView
				.findViewById(R.id.countCommentFroum);
		TextView countdisLike = (TextView) convertView
				.findViewById(R.id.countdislikecommentFroum);

		TextView dateCommenter = (TextView) convertView
				.findViewById(R.id.date_commented_in_froum);

		ImageButton addreply = (ImageButton) convertView
				.findViewById(R.id.replyToComment);

		ImageButton profileImage = (ImageButton) convertView
				.findViewById(R.id.icon_froum_profile);

		final ImageButton imglikeComment = (ImageButton) convertView
				.findViewById(R.id.positive_img);

		final ImageButton imgdislikeComment = (ImageButton) convertView
				.findViewById(R.id.negative_img);
		// end find view

		adapter.open();
		Users vv = util.getCurrentUser();
		if (adapter.isUserLikedComment(vv.getId(), comment.getId(), 1)) {
			imglikeComment.setImageResource((R.drawable.positive));
			// imgdislikeComment.setImageResource((R.drawable.negative));

		} else {
			imglikeComment.setImageResource((R.drawable.positive_off));
			// imgdislikeComment.setImageResource((R.drawable.negative_off));
		}

		if (adapter.isUserLikedComment(vv.getId(), comment.getId(), 0)) {
			imgdislikeComment.setImageResource((R.drawable.negative));
			// imgdislikeComment.setImageResource((R.drawable.negative));

		} else {
			imgdislikeComment.setImageResource((R.drawable.negative_off));
			// imgdislikeComment.setImageResource((R.drawable.negative_off));
		}

		adapter.close();

		// imglikeComment.setBackgroundResource(R.drawable.positive);

		// imgdislikeComment.setBackgroundResource(R.drawable.negative);

		final ExpandableListView mExpandableListView = (ExpandableListView) parent;

		// start initialize
		mainComment.setText(comment.getDesk());
		nameCommenter.setText(x.getName());
		dateCommenter.setText(comment.getDatetime());

		countLike.setText(comment.getNumOfLike());
		countdisLike.setText(comment.getNumOfDislike());

		byte[] byteImageProfile = x.getImage();

		Bitmap bmp = BitmapFactory.decodeByteArray(byteImageProfile, 0,
				byteImageProfile.length);

		profileImage.setImageBitmap(bmp);

		RelativeLayout rl = (RelativeLayout) convertView
				.findViewById(R.id.icon_header_comment_froum);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				rl.getLayoutParams());

		lp.width = util.getScreenwidth() / 7;
		lp.height = util.getScreenwidth() / 7;
		lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		lp.setMargins(5, 5, 5, 5);
		profileImage.setLayoutParams(lp);

		notifyDataSetChanged();

		imgdislikeComment.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View t) {

				adapter.open();
				int intCureentDisLike = Integer.valueOf(comment
						.getNumOfDislike());
				int newCountDisLike = intCureentDisLike + 1;
				String stringNewcountDisLike = String.valueOf(newCountDisLike);

				Users user = new Users();
				user = util.getCurrentUser();
				int userid = user.getId();
				//
				// // peyda kardan id comment sabt shode

				RelativeLayout parentlayout = (RelativeLayout) t.getParent()
						.getParent().getParent();
				View viewMaincmt = parentlayout.findViewById(R.id.peygham);
				TextView txtMaincmt = (TextView) viewMaincmt;

				View viewnumDislike = parentlayout
						.findViewById(R.id.countdislikecommentFroum);
				TextView txtdislike = (TextView) viewnumDislike;

				int id = 0;

				for (CommentInFroum listItem : cmt) {
					if (txtMaincmt.getText().toString()
							.equals(listItem.getDesk())) {

						id = listItem.getId();

					}
				}

				// send to database

				if (adapter.isUserLikedComment(userid, id, 0)) {
					// Toast.makeText(context, "dis",
					// Toast.LENGTH_SHORT).show();
					int b = intCureentDisLike - 1;
					String c = String.valueOf(b);
					adapter.deleteLikeFromCommentInFroum(id, userid, 0);

					adapter.insertCmtDisLikebyid(id, c, userid);
					f.updateList();

					txtdislike.setText(comment.getNumOfDislike());
					notifyDataSetChanged();
					imgdislikeComment.setImageResource((R.drawable.negative));

				} else {

					adapter.insertCmtDisLikebyid(id, stringNewcountDisLike,
							userid);
					adapter.insertLikeInCommentToDb(userid, 0, id);
					f.updateList();

					txtdislike.setText(comment.getNumOfDislike());
					imgdislikeComment
							.setImageResource((R.drawable.negative_off));
					notifyDataSetChanged();
				}
				adapter.close();

			}
		});

		imglikeComment.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// khandan meghdar ghabli tedad like ha
				// tabdil be int
				// ezafe kardan 1 vahed be meghdar ghabli
				// tabdil be String
				adapter.open();

				int intCureentLike = Integer.valueOf(comment.getNumOfLike());
				int newCountLike = intCureentLike + 1;
				String stringNewcountLike = String.valueOf(newCountLike);

				Users user = new Users();
				user = util.getCurrentUser();
				int userid = user.getId();
				//
				// // peyda kardan id comment sabt shode

				RelativeLayout parentlayout = (RelativeLayout) v.getParent()
						.getParent().getParent();
				View viewMaincmt = parentlayout.findViewById(R.id.peygham);
				TextView txtMaincmt = (TextView) viewMaincmt;

				View viewnumlike = parentlayout
						.findViewById(R.id.countCommentFroum);
				TextView txtlike = (TextView) viewnumlike;

				int id = 0;

				for (CommentInFroum listItem : cmt) {
					if (txtMaincmt.getText().toString()
							.equals(listItem.getDesk())) {

						id = listItem.getId();

					}
				}

				// send to database

				if (adapter.isUserLikedComment(userid, id, 1)) {

					adapter.deleteLikeFromCommentInFroum(id, userid, 1);

					int b = intCureentLike - 1;
					String c = String.valueOf(b);

					adapter.insertCmtLikebyid(id, c, userid);
					f.updateList();

					txtlike.setText(String.valueOf(adapter
							.getCountofCommentinFroumObject(froumID, id)));

					adapter.close();
					notifyDataSetChanged();

					imglikeComment
							.setBackgroundResource(R.drawable.positive_off);

				} else {

					adapter.insertCmtLikebyid(id, stringNewcountLike, userid);
					adapter.insertLikeInCommentToDb(userid, 1, id);

					f.updateList();

					txtlike.setText(String.valueOf(adapter
							.getCountofCommentinFroumObject(froumID, id)));

					adapter.close();
					notifyDataSetChanged();
					imglikeComment.setBackgroundResource(R.drawable.positive);

				}

			}
		});

		addreply.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View m) {
				RelativeLayout parentlayout = (RelativeLayout) m.getParent()
						.getParent();
				View view = parentlayout.findViewById(R.id.peygham);
				TextView x = (TextView) view;
				String item = x.getText().toString();
				int commentid = 0;
				for (CommentInFroum listItem : cmt) {
					if (item.equals(listItem.getDesk())) {

						commentid = listItem.getId();
					}
				}

				DialogcmtInfroum dialog = new DialogcmtInfroum(f, commentid,
						context, froumID, R.layout.dialog_addcomment);
				dialog.show();

				notifyDataSetChanged();

			}
		});

		convertView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (isExpanded) {
					mExpandableListView.collapseGroup(groupPosition);
					notifyDataSetChanged();

				} else
					mExpandableListView.expandGroup(groupPosition);
				notifyDataSetChanged();

			}
		});
		mainComment.setTypeface(null, Typeface.BOLD);

		// item.setText(laptopName);
		return convertView;
	}

	public boolean hasStableIds() {
		return true;
	}

	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
}