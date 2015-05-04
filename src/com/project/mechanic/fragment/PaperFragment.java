package com.project.mechanic.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.project.mechanic.R;
import com.project.mechanic.adapter.PaperListAdapter;
import com.project.mechanic.entity.CommentInPaper;
import com.project.mechanic.entity.Paper;
import com.project.mechanic.entity.Users;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class PaperFragment extends Fragment {

	DataBaseAdapter adapter;
	int id;
	private ImageButton btnAddcmt;
	private ImageButton Like;
	private TextView NumofLike;
	private TextView NumofComment;
	private TextView txttitle;
	private TextView txttitleDes;
	DialogcmtInPaper dialog;
	ListView lst;
	ArrayList<CommentInPaper> mylist;
	PaperListAdapter PaperListadapter;
	int like = 0;
	Utility util;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_paper, null);
		util = new Utility(getActivity());
		final Users user = util.getCurrentUser();
		btnAddcmt = (ImageButton) view.findViewById(R.id.imgBtnAddcmt_CmtFroum);
		Like = (ImageButton) view.findViewById(R.id.imgbtnLike_CmtFroum);
		lst = (ListView) view.findViewById(R.id.lstComment);
		NumofComment = (TextView) view.findViewById(R.id.txtNumofCmt_CmtFroum);
		NumofLike = (TextView) view.findViewById(R.id.txtNumofLike_CmtFroum);
		txttitle = (TextView) view.findViewById(R.id.rawTitletxt);
		txttitleDes = (TextView) view.findViewById(R.id.rawtxtDescription);

		adapter = new DataBaseAdapter(getActivity());
		adapter.open();

		// Bundle bundle = new Bundle();
		// bundle.getString("Id", String.valueOf(id));
		id = Integer.valueOf(getArguments().getString("Id"));
		mylist = adapter.getCommentInPaperbyPaperid(id);
		Paper p = adapter.getPaperItembyid(id);

		txttitle.setText(p.getTitle());
		txttitleDes.setText(p.getContext());
		adapter.close();
		PaperListAdapter ListAdapter = new PaperListAdapter(getActivity(),
				R.layout.raw_papercmt, mylist);

		lst.setAdapter(ListAdapter);

		Like.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				adapter.open();

				adapter.insertLikeInPaperToDb(user.getId(), id, "");
				NumofLike.setText(adapter.LikeInPaper_count().toString());

			}
		});

		btnAddcmt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				dialog = new DialogcmtInPaper(PaperFragment.this,
						getActivity(), R.layout.dialog_addcomment,id);
				dialog.show();

			}
		});
		return view;

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		lst.deferNotifyDataSetChanged();
	}

	public void updateView2() {
		adapter.open();
		mylist = adapter.getCommentInPaperbyPaperid(id);
		NumofComment.setText(adapter.CommentInPaper_count().toString());

		adapter.close();

		PaperListadapter = new PaperListAdapter(getActivity(),
				R.layout.raw_papercmt, mylist);
		PaperListadapter.notifyDataSetChanged();
		lst.setAdapter(PaperListadapter);

	}

}