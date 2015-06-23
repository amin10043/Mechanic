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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mechanic.R;
import com.project.mechanic.entity.CommentInObject;
import com.project.mechanic.entity.Users;
import com.project.mechanic.fragment.DialogcmtInobject;
import com.project.mechanic.fragment.IntroductionFragment;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class ExpandIntroduction extends BaseExpandableListAdapter {

	Context context;
	private Map<CommentInObject, List<CommentInObject>> mapCollection;
	private ArrayList<CommentInObject> CommentList;
	DataBaseAdapter adapter;
	Utility util;
	IntroductionFragment f;
	int ObjectID, userid;
	Users Currentuser;

	public ExpandIntroduction(Context context,
			ArrayList<CommentInObject> CommentList,
			Map<CommentInObject, List<CommentInObject>> mapCollection,
			IntroductionFragment introductionFragment, int ObjectId) {

		this.context = context;
		this.mapCollection = mapCollection;
		this.CommentList = CommentList;
		adapter = new DataBaseAdapter(context);
		util = new Utility(context);
		this.f = introductionFragment;
		this.ObjectID = ObjectId;
		adapter.open();
		Currentuser = util.getCurrentUser();
		adapter.close();
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return mapCollection.get(CommentList.get(groupPosition)).get(
				childPosition);

	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		CommentInObject reply = (CommentInObject) getChild(groupPosition,
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

		final CommentInObject comment = CommentList.get(groupPosition);
		Users y = adapter.getUserbyid(comment.getUserid());

		if (y.getImage() == null) {
			ReplyerPic.setImageResource(R.drawable.no_img_profile);
		} else {

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
		}

		mainReply.setText(reply.getDescription());
		dateReply.setText(reply.getDatetime());
		nameReplyer.setText(y.getName());
		adapter.close();

		notifyDataSetChanged();
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		if (mapCollection.get(CommentList.get(groupPosition)) != null)
			return mapCollection.get(CommentList.get(groupPosition)).size();
		return 0;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return CommentList.get(groupPosition);

	}

	@Override
	public int getGroupCount() {
		return CommentList.size();

	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;

	}

	@Override
	public View getGroupView(final int groupPosition, final boolean isExpanded,
			View convertView, ViewGroup parent) {
		adapter.open();

		final CommentInObject comment = CommentList.get(groupPosition);
		Users x = adapter.getUserbyid(comment.getUserid());

		adapter.close();

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

		TextView countOfReply = (TextView) convertView
				.findViewById(R.id.numberOfCommentTopic);

		LinearLayout addreply = (LinearLayout) convertView
				.findViewById(R.id.addCommentToTopic);

		ImageButton profileImage = (ImageButton) convertView
				.findViewById(R.id.icon_froum_profile);

		final ImageButton imglikeComment = (ImageButton) convertView
				.findViewById(R.id.positive_img);

		final ImageButton imgdislikeComment = (ImageButton) convertView
				.findViewById(R.id.negative_img);

		final ExpandableListView mExpandableListView = (ExpandableListView) parent;

		// end find view

		// start set variable
		adapter.open();
		Currentuser = util.getCurrentUser();

		if (Currentuser == null) {
			imglikeComment.setImageResource((R.drawable.positive_off));
			imgdislikeComment.setImageResource((R.drawable.negative_off));
		} else {
			if (adapter.isUserLikedComment(Currentuser.getId(),
					comment.getId(), 1)) {
				imglikeComment.setImageResource((R.drawable.positive));
			} else {
				imglikeComment.setImageResource((R.drawable.positive_off));

			}
			if (adapter.isUserLikedComment(Currentuser.getId(),
					comment.getId(), 0)) {
				imgdislikeComment.setImageResource((R.drawable.negative));
			} else {
				imgdislikeComment.setImageResource((R.drawable.negative_off));

			}

		}

		mainComment.setText(comment.getDescription());
		nameCommenter.setText(x.getName());
		dateCommenter.setText(comment.getDatetime());
		// if (adapter.getCountOfReplyInObject(ObjectID, comment.getId()) == 0)
		// {
		// LinearLayout lrr = (LinearLayout) convertView
		// .findViewById(R.id.linearShowcountofRepply);
		// lrr.setVisibility(View.GONE);
		//
		// } else
		// countOfReply.setText(adapter.getCountOfReplyInObject(ObjectID,
		// comment.getId()).toString());

		// countLike.setText(comment.getId());
		// countdisLike.setText(comment.getFroumid());

		// start... this code for set image of profile
		adapter.open();
		if (x.getImage() == null) {
			profileImage.setImageResource(R.drawable.no_img_profile);
		} else {

			byte[] byteImageProfile = x.getImage();

			Bitmap bmp = BitmapFactory.decodeByteArray(byteImageProfile, 0,
					byteImageProfile.length);

			profileImage.setImageBitmap(bmp);
		}
		adapter.close();
		RelativeLayout rl = (RelativeLayout) convertView
				.findViewById(R.id.icon_header_comment_froum);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				rl.getLayoutParams());

		lp.width = util.getScreenwidth() / 7;
		lp.height = util.getScreenwidth() / 7;
		lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		lp.setMargins(5, 5, 5, 5);
		profileImage.setLayoutParams(lp);

		// end... this code for set image of profile

		adapter.close();

		// end set variable

		notifyDataSetChanged();

		// imgdislikeComment.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View t) {
		//
		// adapter.open();
		// if (Currentuser == null) {
		// Toast.makeText(context, "ابتدا باید وارد شوید",
		// Toast.LENGTH_SHORT).show();
		// return;
		// } else {
		// int intCureentDisLike = Integer.valueOf(comment.getId());
		// int newCountDisLike = intCureentDisLike + 1;
		// String stringNewcountDisLike = String
		// .valueOf(newCountDisLike);
		//
		// //
		// // // peyda kardan id comment sabt shode
		//
		// RelativeLayout parentlayout = (RelativeLayout) t
		// .getParent().getParent().getParent();
		// View viewMaincmt = parentlayout.findViewById(R.id.peygham);
		// TextView txtMaincmt = (TextView) viewMaincmt;
		//
		// View viewnumDislike = parentlayout
		// .findViewById(R.id.countdislikecommentFroum);
		// TextView txtdislike = (TextView) viewnumDislike;
		//
		// int id = 0;
		//
		// for (CommentInObject listItem : CommentList) {
		// if (txtMaincmt.getText().toString().equals(listItem)) {
		//
		// id = listItem.getId();
		//
		// }
		// }
		//
		// // send to database
		//
		// if (adapter.isUserLikedComment(Currentuser.getId(), id, 0)) {
		//
		// int b = intCureentDisLike - 1;
		// String c = String.valueOf(b);
		// adapter.deleteLikeFromCommentInFroum(id,
		// Currentuser.getId(), 0);
		//
		// adapter.insertCmtDisLikebyid(id, c, Currentuser.getId());
		// f.updateList();
		//
		// txtdislike.setText(comment.getId());
		// notifyDataSetChanged();
		// imgdislikeComment
		// .setImageResource((R.drawable.negative));
		//
		// } else {
		// adapter.insertCmtDisLikebyid(id, stringNewcountDisLike,
		// Currentuser.getId());
		// adapter.insertLikeInCommentToDb(Currentuser.getId(), 0,
		// id);
		// f.updateList();
		//
		// txtdislike.setText(comment.getId());
		// imgdislikeComment
		// .setImageResource((R.drawable.negative_off));
		// notifyDataSetChanged();
		//
		// }
		// adapter.close();
		// }
		// }
		// });
		//
		imglikeComment.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// khandan meghdar ghabli tedad like ha
				// tabdil be int
				// ezafe kardan 1 vahed be meghdar ghabli
				// tabdil be String
				adapter.open();

				if (Currentuser == null) {
					Toast.makeText(context, "ابتدا باید وارد شوید",
							Toast.LENGTH_SHORT).show();
					return;
				} else {

					int intCureentLike = Integer.valueOf(comment.getId());
					int newCountLike = intCureentLike + 1;
					String stringNewcountLike = String.valueOf(newCountLike);

					// // peyda kardan id comment sabt shode

					RelativeLayout parentlayout = (RelativeLayout) v
							.getParent().getParent().getParent();
					View viewMaincmt = parentlayout.findViewById(R.id.peygham);
					TextView txtMaincmt = (TextView) viewMaincmt;

					View viewnumlike = parentlayout
							.findViewById(R.id.countCommentFroum);
					TextView txtlike = (TextView) viewnumlike;

					int id = 0;

					for (CommentInObject listItem : CommentList) {
						if (txtMaincmt.getText().toString().equals(listItem)) {

							id = listItem.getId();

						}
					}

					// send to database

					if (adapter.isUserLikedComment(Currentuser.getId(), id, 1)) {

						adapter.deleteLikeFromCommentInFroum(id,
								Currentuser.getId(), 1);

						int b = intCureentLike - 1;
						String c = String.valueOf(b);

						adapter.insertCmtLikebyid(id, c, Currentuser.getId());
						f.updateList();

						txtlike.setText(String.valueOf(adapter
								.getCountofCommentinFroumObject(ObjectID, id)));

						adapter.close();
						notifyDataSetChanged();

						imglikeComment
								.setBackgroundResource(R.drawable.positive_off);

					} else {

						adapter.insertCmtLikebyid(id, stringNewcountLike,
								Currentuser.getId());
						// adapter.insertLikeInCommentToDb(Currentuser.getId(),
						// 1,
						// id);

						f.updateList();

						txtlike.setText(String.valueOf(adapter
								.getCountofCommentinFroumObject(ObjectID, id)));

						adapter.close();
						notifyDataSetChanged();
						imglikeComment
								.setBackgroundResource(R.drawable.positive);

					}

				}
			}
		});

		addreply.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View m) {

				adapter.open();

				if (Currentuser == null) {
					Toast.makeText(context, "ابتدا باید وارد شوید",
							Toast.LENGTH_SHORT).show();
					return;
				} else {

					RelativeLayout parentlayout = (RelativeLayout) m
							.getParent().getParent().getParent();
					View view = parentlayout.findViewById(R.id.peygham);
					TextView x = (TextView) view;
					String item = x.getText().toString();
					int commentid = 0;
					// String dd = comment.getDescription();
					for (CommentInObject test : CommentList) {
						if (item.equals(test.getDescription())) {
							commentid = test.getId();
							break;
						}
					}

					DialogcmtInobject dialog = new DialogcmtInobject(f,
							context, R.layout.dialog_addcomment, ObjectID,
							commentid);
					dialog.show();

					notifyDataSetChanged();
					adapter.close();
				}
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

		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
