package com.project.mechanic.fragment;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.adapter.ExpandableCommentFroum;
import com.project.mechanic.adapter.FroumListAdapter;
import com.project.mechanic.adapter.FroumReplyetocmAdapter;
import com.project.mechanic.entity.CommentInFroum;
import com.project.mechanic.entity.Froum;
import com.project.mechanic.entity.Users;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class FroumFragment extends Fragment {

	// start defined by masoud

	DataBaseAdapter adapter;
	ExpandableCommentFroum exadapter;

	ExpandableListView commentListview;
	TextView titletxt, descriptiontxt, dateTopic, countComment, countLike;
	LinearLayout addComment, likeTopic;
	ImageButton sharebtn;
	int froumid;

	Froum topics;

	DialogcmtInfroum dialog;
	ArrayList<CommentInFroum> commentGroup, ReplyGroup;

	List<String> groupList;
	List<String> childList;
	Map<CommentInFroum, List<CommentInFroum>> mapCollection;
	ExpandableListView exlistview;

	View header;

	// end defined by masoud

	private ImageButton CmtLike, CmtDisLike;
	private Button btncmt;

	//
	TextView txttitleDes, NumofLike, NumofComment, NumofCmtLike,
			NumofCmtDisLike, datetxt;

	private int frmid;
	private LinearLayout btnAddcmt, Like;

	FroumListAdapter ListAdapter;

	ArrayList<CommentInFroum> mylist;
	// ArrayList<CommentInFroum> ReplyeList;
	ImageButton Replytocm;
	FroumListAdapter froumListadapter;
	FroumReplyetocmAdapter ReplyAdapter;
	int id;
	int id2;
	int Commentid;

	ListView lstReply;
	Froum x;
	String dateString;
	Users user;
	Utility util;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceStdataate) {

		((MainActivity) getActivity()).setActivityTitle(R.string.Forums);
		View view = inflater.inflate(R.layout.fragment_froum, null);

		adapter = new DataBaseAdapter(getActivity());
		util = new Utility(getActivity());

		user = new Users();

		header = getActivity().getLayoutInflater().inflate(
				R.layout.header_expandable, null);

		// start find view

		titletxt = (TextView) header.findViewById(R.id.title_topic);
		descriptiontxt = (TextView) header.findViewById(R.id.description_topic);
		dateTopic = (TextView) header.findViewById(R.id.date_topic);
		countComment = (TextView) header
				.findViewById(R.id.numberOfCommentTopic);
		countLike = (TextView) header.findViewById(R.id.txtNumofLike_CmtFroum);

		addComment = (LinearLayout) header.findViewById(R.id.addCommentToTopic);
		likeTopic = (LinearLayout) view.findViewById(R.id.LikeTopicLinear);

		sharebtn = (ImageButton) header.findViewById(R.id.sharefroumicon);
		exlistview = (ExpandableListView) view.findViewById(R.id.commentlist);

		// end find view

		if (getArguments().getString("Id") != null)
			froumid = Integer.valueOf(getArguments().getString("Id"));

		adapter.open();

		topics = adapter.getFroumItembyid(froumid);
		titletxt.setText(topics.getTitle());
		descriptiontxt.setText(topics.getDescription());
		countComment.setText(adapter.CommentInFroum_count(froumid).toString());
		countLike.setText(adapter.LikeInFroum_count(id).toString());

		adapter.close();

		addComment.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog = new DialogcmtInfroum(FroumFragment.this, 0,
						getActivity(), froumid, R.layout.dialog_addcomment);
				dialog.show();
				exadapter.notifyDataSetChanged();

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

		// nnnnnn

		// datetxt = (TextView) view.findViewById(R.id.datefroum);

		// btnAddcmt = (LinearLayout) view
		// .findViewById(R.id.imgBtnAddcmt_CmtFroum);
		// // Like = (LinearLayout)
		// view.findViewById(R.id.imgbtnLike_Cmt_Froum);
		// CmtLike = (ImageButton)
		// view.findViewById(R.id.imgbtnLike_RawCmtFroum);
		// CmtDisLike = (ImageButton) view
		// .findViewById(R.id.imgbtnDisLike_RawCmtFroum);
		//
		// btncmt = (Button) view.findViewById(R.id.btnComment);
		// titletxt = (TextView) view.findViewById(R.id.rawTitletxt);
		// txttitleDes = (TextView) view.findViewById(R.id.rawtxtDescription);
		// NumofComment = (TextView)
		// view.findViewById(R.id.txtNumofCmt_CmtFroum);
		// NumofLike = (TextView) view.findViewById(R.id.txtNumofLike_CmtFroum);
		// NumofCmtLike = (TextView) view
		// .findViewById(R.id.txtNumofLike_RawCmtFroum);
		// NumofCmtDisLike = (TextView) view
		// .findViewById(R.id.txtNumofDislike_RawCmtFroum);
		//

		// NumofComment.setText(adapter.CommentInFroum_count(id).toString());
		//
		// NumofLike.setText(adapter.LikeInFroum_count(id).toString());
		// mylist = adapter.getCommentInFroumbyPaperid(id, 0);
		// // ReplyeList = adapter.getReplyCommentbyCommentID(id, );
		// Froum x = adapter.getFroumItembyid(id);
		// titletxt.setText(x.getTitle());
		// txttitleDes.setText(x.getDescription());

		// Commentid =
		// Integer.valueOf(getArguments().getString("CommentID"));

		// NumofComment.setText(adapter.CommentInFroum_count(id).toString());
		//
		// NumofLike.setText(adapter.LikeInFroum_count(id).toString());
		// mylist = adapter.getCommentInFroumbyPaperid(id, 0);

		/*
		 * CommentInFroum d = null; NumofCmtLike.setText(d.getNumofLike());
		 * NumofCmtDisLike.setText(d.getNumofDisLike());
		 */

		// x = adapter.getFroumItembyid(id);
		// titletxt.setText(x.getTitle());
		// txttitleDes.setText(x.getDescription());
		// NumofComment.setText(adapter.CommentInFroum_count(id).toString());
		//
		// NumofLike.setText(adapter.LikeInFroum_count(id).toString());

		/*
		 * CommentInFroum d = null; NumofCmtLike.setText(d.getNumofLike());
		 * NumofCmtDisLike.setText(d.getNumofDisLike());
		 */

		// final Froum x = adapter.getFroumItembyid(id);
		// ReplyeList = adapter.getReplyCommentbyCommentID(id, 1);
		// // Froum x = adapter.getFroumItembyid(id);
		// titletxt.setText(x.getTitle());
		// txttitleDes.setText(x.getDescription());
		// adapter.close();
		// }

		// Commentid = Integer.valueOf(getArguments().getString("CommentID"));

		// commentListview = (ExpandableListView) view
		// .findViewById(R.id.commentlist);
		//
		// if (commentListview != null) {
		// ListAdapter = new FroumListAdapter(getActivity(),
		// R.layout.raw_froumcmt, mylist, FroumFragment.this);
		// commentListview.setAdapter(ListAdapter);
		// // resizeListView(commentListview);
		// }

		// Like.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// user = util.getCurrentUser();
		// int userid = user.getId();
		// adapter.open();
		// adapter.insertLikeInFroumToDb(userid, 0, "", 1);
		// NumofLike.setText(adapter.LikeInFroum_count(id).toString());
		// adapter.close();
		//
		// }
		// });
		// sharebtn.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// Intent sharingIntent = new Intent(
		// android.content.Intent.ACTION_SEND);
		// sharingIntent.setType("text/plain");
		// // String shareBody = x.getDescription();
		// sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
		// x.getTitle());
		// sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
		// "froumfragment");
		// startActivity(Intent.createChooser(sharingIntent,
		// "اشتراک از طریق"));
		// }
		// });

		// btnAddcmt.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// // TODO Auto-generated method stub
		//
		// dialog = new DialogcmtInfroum(FroumFragment.this, 0,
		// getActivity(), id, R.layout.dialog_addcomment);
		// dialog.show();
		//
		// }
		// });
		return view;
	}

	/*
	 * @Override public void onResume() { // TODO Auto-generated method stub
	 * super.onResume(); froumListadapter = new FroumListAdapter(getActivity(),
	 * R.layout.raw_froumcmt, mylist, FroumFragment.this);
	 * froumListadapter.notifyDataSetChanged();
	 * lst.setAdapter(froumListadapter);
	 * 
	 * }
	 */

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

	// public void updateView2() {
	// adapter.open();
	// mylist = adapter.getCommentInFroumbyPaperid(id, 0);
	// NumofComment.setText(adapter.CommentInFroum_count(id).toString());
	// adapter.close();
	// froumListadapter = new FroumListAdapter(getActivity(),
	// R.layout.raw_froumcmt, mylist, FroumFragment.this);
	// commentListview.setAdapter(froumListadapter);
	// froumListadapter.notifyDataSetChanged();
	//
	// }

	//
	// public void updateView3() {
	//
	// froumListadapter = new FroumListAdapter(getActivity(),
	// R.layout.raw_froumcmt, mylist, FroumFragment.this);
	// froumListadapter.notifyDataSetChanged();
	// lst.setAdapter(froumListadapter);
	//
	// }

	// private void resizeListView(ListView listView) {
	// ListAdapter listAdapter = listView.getAdapter();
	// if (listAdapter == null) {
	// // pre-condition
	// return;
	// }
	//
	// int totalHeight = listView.getPaddingTop()
	// + listView.getPaddingBottom();
	// for (int i = 0; i < listAdapter.getCount(); i++) {
	// View listItem = listAdapter.getView(i, null, listView);
	//
	// if (listItem instanceof ViewGroup) {
	// listItem.setLayoutParams(new LayoutParams(
	// LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	// }
	// listItem.measure(0, 0);
	// totalHeight += listItem.getMeasuredHeight();
	// }
	//
	// ViewGroup.LayoutParams params = listView.getLayoutParams();
	// params.height = totalHeight
	// + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
	// listView.setLayoutParams(params);
	// }
	/*
	 * public void refresh(){ adapter.open(); int
	 * count=adapter.Tablecommentcount(); String[] cmt=new String[count];
	 * for(int i=0;i<count;i++){ cmt[i]=adapter.DisplayComment(i,3); }
	 * adapter.close(); // list1.setAdapter(new
	 * ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,cmt));
	 * 
	 * }
	 */

}
