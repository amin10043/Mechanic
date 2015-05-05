package com.project.mechanic.fragment;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.adapter.FroumListAdapter;
import com.project.mechanic.adapter.FroumReplyetocmAdapter;
import com.project.mechanic.entity.CommentInFroum;
import com.project.mechanic.entity.Froum;
import com.project.mechanic.entity.Users;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class FroumFragment extends Fragment {

	DataBaseAdapter adapter;
	private int frmid;
	private LinearLayout btnAddcmt;
	private LinearLayout Like;
	private ImageButton CmtLike;
	private ImageButton CmtDisLike;
	private Button btncmt;
	private TextView txttitle;
	private TextView txttitleDes;
	private TextView NumofLike;
	private TextView NumofComment;
	private TextView NumofCmtLike;
	private TextView NumofCmtDisLike;
	FroumListAdapter ListAdapter;

	ArrayList<CommentInFroum> mylist;
	ArrayList<CommentInFroum> ReplyeList;
	DialogcmtInfroum dialog;
	ImageButton Replytocm;
	FroumListAdapter froumListadapter;
	FroumReplyetocmAdapter ReplyAdapter;
	int id;
	int id2;
	int Commentid;

	ListView lst;
	ListView lstReply;

	private ImageButton sharebtn;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceStdataate) {

		((MainActivity) getActivity()).setActivityTitle(R.string.Forums);
		View view = inflater.inflate(R.layout.fragment_froum, null);

		btnAddcmt = (LinearLayout) view
				.findViewById(R.id.imgBtnAddcmt_CmtFroum);
		Like = (LinearLayout) view.findViewById(R.id.imgbtnLike_Cmt_Froum);
		CmtLike = (ImageButton) view.findViewById(R.id.imgbtnLike_RawCmtFroum);
		CmtDisLike = (ImageButton) view
				.findViewById(R.id.imgbtnDisLike_RawCmtFroum);
		sharebtn = (ImageButton) view.findViewById(R.id.sharefroumicon);

		btncmt = (Button) view.findViewById(R.id.btnComment);
		txttitle = (TextView) view.findViewById(R.id.rawTitletxt);
		txttitleDes = (TextView) view.findViewById(R.id.rawtxtDescription);
		NumofComment = (TextView) view.findViewById(R.id.txtNumofCmt_CmtFroum);
		NumofLike = (TextView) view.findViewById(R.id.txtNumofLike_CmtFroum);
		NumofCmtLike = (TextView) view
				.findViewById(R.id.txtNumofLike_RawCmtFroum);
		NumofCmtDisLike = (TextView) view
				.findViewById(R.id.txtNumofDislike_RawCmtFroum);

		adapter = new DataBaseAdapter(getActivity());
		adapter.open();
		id = Integer.valueOf(getArguments().getString("Id"));
		// Commentid = Integer.valueOf(getArguments().getString("CommentID"));

		NumofComment.setText(adapter.CommentInFroum_count().toString());

		NumofLike.setText(adapter.LikeInFroum_count().toString());
		mylist = adapter.getCommentInFroumbyPaperid(id);

		/*
		 * CommentInFroum d = null; NumofCmtLike.setText(d.getNumofLike());
		 * NumofCmtDisLike.setText(d.getNumofDisLike());
		 */

		mylist = adapter.getCommentInFroumbyPaperid(id);
		final Froum x = adapter.getFroumItembyid(id);
		ReplyeList = adapter.getReplyCommentbyCommentID(id, 1);
		// Froum x = adapter.getFroumItembyid(id);
		txttitle.setText(x.getTitle());
		txttitleDes.setText(x.getDescription());
		adapter.close();

		lst = (ListView) view.findViewById(R.id.lstComment);
		ListAdapter = new FroumListAdapter(getActivity(),
				R.layout.raw_froumcmt, mylist, FroumFragment.this);

		lst.setAdapter(ListAdapter);
		resizeListView(lst);

		Like.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Utility utility = new Utility(getActivity());
				Users user = new Users();
				user = utility.getCurrentUser();
				int userid = user.getId();
				adapter.open();
				adapter.insertLikeInFroumToDb(userid, 0, "", 1);
				NumofLike.setText(adapter.LikeInFroum_count().toString());
				adapter.close();

			}
		});
		sharebtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent sharingIntent = new Intent(
						android.content.Intent.ACTION_SEND);
				sharingIntent.setType("text/plain");
				String shareBody = x.getDescription();
				sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
						x.getTitle());
				sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
						shareBody);
				startActivity(Intent.createChooser(sharingIntent,
						"اشتراک از طریق"));
			}
		});

		btnAddcmt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				dialog = new DialogcmtInfroum(FroumFragment.this, 0,
						getActivity(), id, R.layout.dialog_addcomment);
				dialog.show();

			}
		});
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

	public void updateView2() {
		adapter.open();
		mylist = adapter.getCommentInFroumbyPaperid(id);
		NumofComment.setText(adapter.CommentInFroum_count().toString());
		adapter.close();
		froumListadapter = new FroumListAdapter(getActivity(),
				R.layout.raw_froumcmt, mylist, FroumFragment.this);
		froumListadapter.notifyDataSetChanged();
		lst.setAdapter(froumListadapter);

	}

	public void updateView3() {

		froumListadapter = new FroumListAdapter(getActivity(),
				R.layout.raw_froumcmt, mylist, FroumFragment.this);
		froumListadapter.notifyDataSetChanged();
		lst.setAdapter(froumListadapter);

	}

	private void resizeListView(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = listView.getPaddingTop()
				+ listView.getPaddingBottom();
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);

			if (listItem instanceof ViewGroup) {
				listItem.setLayoutParams(new LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			}
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}
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
