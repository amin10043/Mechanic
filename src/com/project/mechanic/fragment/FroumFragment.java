package com.project.mechanic.fragment;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.adapter.FroumListAdapter;
import com.project.mechanic.entity.CommentInFroum;
import com.project.mechanic.entity.Froum;
import com.project.mechanic.entity.Users;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class FroumFragment extends Fragment {

	DataBaseAdapter adapter;
	private int frmid;
	private ImageButton btnAddcmt;
	private ImageButton Like;
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
	DialogcmtInfroum dialog;
	ImageButton Replytocm;
	FroumListAdapter froumListadapter;
	int id;
	int id2;

	ListView lst;
	ListView lstReply;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceStdataate) {

		((MainActivity) getActivity()).setActivityTitle(R.string.Forums);
		View view = inflater.inflate(R.layout.fragment_froum, null);

		btnAddcmt = (ImageButton) view.findViewById(R.id.imgBtnAddcmt_CmtFroum);
		Like = (ImageButton) view.findViewById(R.id.imgbtnLike_Cmt_Froum);
		CmtLike = (ImageButton) view.findViewById(R.id.imgbtnLike_RawCmtFroum);
		CmtDisLike = (ImageButton) view
				.findViewById(R.id.imgbtnDisLike_RawCmtFroum);

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

		NumofComment.setText(adapter.CommentInFroum_count().toString());

		NumofLike.setText(adapter.LikeInFroum_count().toString());
		mylist = adapter.getCommentInFroumbyPaperid(id);

		/*
		 * CommentInFroum d = null; NumofCmtLike.setText(d.getNumofLike());
		 * NumofCmtDisLike.setText(d.getNumofDisLike());
		 */

		mylist = adapter.getCommentInFroumbyPaperid(id);
		Froum x = adapter.getFroumItembyid(id);
		txttitle.setText(x.getTitle());
		txttitleDes.setText(x.getDescription());
		adapter.close();

		lst = (ListView) view.findViewById(R.id.lstComment);
		lstReply = (ListView) view.findViewById(R.id.lstReplytoCm);
		ListAdapter = new FroumListAdapter(getActivity(),
				R.layout.raw_froumcmt, mylist, FroumFragment.this);

		lst.setAdapter(ListAdapter);

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

		btnAddcmt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				dialog = new DialogcmtInfroum(FroumFragment.this, 1,
						getActivity(), R.layout.dialog_addcomment);
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
