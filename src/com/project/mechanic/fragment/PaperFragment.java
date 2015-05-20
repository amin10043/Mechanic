package com.project.mechanic.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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
	int id;
	private LinearLayout btnAddcmt;
	private LinearLayout Like;
	private TextView NumofLike;
	private TextView NumofComment;
	private TextView txttitle;
	private TextView txttitleDes;
	DialogcmtInPaper dialog;
	private TextView txtdate;

	ListView lst;
	ArrayList<CommentInPaper> mylist;
	PaperListAdapter PaperListadapter;
	int like = 0;
	Utility util;
	View header;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_paper, null);
		header = getActivity().getLayoutInflater().inflate(
				R.layout.header_expandable, null);
		util = new Utility(getActivity());

		final Users user = util.getCurrentUser();

		lst = (ListView) view.findViewById(R.id.listViewnewspaper);

		btnAddcmt = (LinearLayout) header.findViewById(R.id.addCommentToTopic);
		Like = (LinearLayout) header.findViewById(R.id.LikeTopicLinear);

		NumofComment = (TextView) header
				.findViewById(R.id.numberOfCommentTopic);
		NumofLike = (TextView) header.findViewById(R.id.txtNumofLike_CmtFroum);
		txttitle = (TextView) header.findViewById(R.id.title_topic);
		txttitleDes = (TextView) header.findViewById(R.id.description_topic);
		txtdate = (TextView) header.findViewById(R.id.date_cc);

		adapter = new DataBaseAdapter(getActivity());
		// if (getArguments().getString("Id") != null) {
		{
			adapter.open();

			id = Integer.valueOf(getArguments().getString("Id"));
			// Toast.makeText(getActivity(), paperId + "", Toast.LENGTH_SHORT)
			// .show();
			NumofComment.setText(adapter.CommentInPaper_count(id).toString());

			NumofLike.setText(adapter.LikeInPaper_count(id).toString());

			Toast.makeText(getActivity(), "recieve = " + id, Toast.LENGTH_SHORT)
					.show();

			// Bundle bundle = new Bundle();
			// bundle.getString("Id", String.valueOf(id));
			// id = Integer.valueOf(getArguments().getString("Id"));
			mylist = adapter.getCommentInPaperbyPaperid(id);
			Paper p = adapter.getPaperItembyid(id);

			txttitle.setText(p.getTitle());
			txttitleDes.setText(p.getContext());
			adapter.close();
			if (lst != null) {
				lst.addHeaderView(header);
				PaperListadapter = new PaperListAdapter(getActivity(),
						R.layout.raw_papercmt, mylist, PaperFragment.this);

				lst.setAdapter(PaperListadapter);
			}

			Like.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Utility utility = new Utility(getActivity());
					Users user = new Users();
					user = utility.getCurrentUser();
					int userid = user.getId();
					adapter.open();
					adapter.insertLikeInPaperToDb(user.getId(), id, "");
					NumofLike.setText(adapter.LikeInPaper_count(id).toString());
					adapter.close();
				}
			});

			btnAddcmt.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {

					// TODO Auto-generated method stub
					dialog = new DialogcmtInPaper(PaperFragment.this,
							getActivity(), R.layout.dialog_addcomment, id);
					dialog.show();

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
		mylist = adapter.getCommentInPaperbyPaperid(id);
		NumofComment.setText(adapter.CommentInPaper_count(id).toString());

		adapter.close();

		PaperListadapter = new PaperListAdapter(getActivity(),
				R.layout.raw_papercmt, mylist, PaperFragment.this);
		PaperListadapter.notifyDataSetChanged();
		lst.setAdapter(PaperListadapter);

	}

}
