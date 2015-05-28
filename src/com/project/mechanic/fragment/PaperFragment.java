package com.project.mechanic.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mechanic.R;
import com.project.mechanic.ListView.PullAndLoadListView;
import com.project.mechanic.ListView.PullAndLoadListView.OnLoadMoreListener;
import com.project.mechanic.ListView.PullToRefreshListView.OnRefreshListener;
import com.project.mechanic.adapter.PaperListAdapter;
import com.project.mechanic.entity.CommentInPaper;
import com.project.mechanic.entity.News;
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
	int i=0,j=9;
	List<CommentInPaper> subList;
	List<CommentInPaper> tempList;
	View view;
	PullAndLoadListView lstNews;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 view = inflater.inflate(R.layout.fragment_paper, null);
		header = getActivity().getLayoutInflater().inflate(
				R.layout.header_expandable, null);
		util = new Utility(getActivity());
		date = new PersianDate();
		currentDate = date.todayShamsi();

		CurrentUser = util.getCurrentUser();

	

		btnAddcmt = (LinearLayout) header.findViewById(R.id.addCommentToTopic);
		Like = (LinearLayout) header.findViewById(R.id.LikeTopicLinear);
         //   lst=(ListView) view.findViewById(R.id.listViewHeder);
		lstNews=(PullAndLoadListView) view.findViewById(R.id.listViewnewspaper);
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
		adapter.open();

		paperID = Integer.valueOf(getArguments().getString("Id"));

		if (CurrentUser == null
				|| !adapter.isUserLikedPaper(CurrentUser.getId(), paperID))
			Like.setBackgroundResource(R.drawable.like_froum_off);
		else

			Like.setBackgroundResource(R.drawable.like_froum);

		NumofComment.setText(adapter.CommentInPaper_count(paperID).toString());

		NumofLike.setText(adapter.LikeInPaper_count(paperID).toString());

		// Bundle bundle = new Bundle();
		// bundle.getString("Id", String.valueOf(id));
		// id = Integer.valueOf(getArguments().getString("Id"));
		mylist = adapter.getCommentInPaperbyPaperid(paperID);

		final Paper p = adapter.getPaperItembyid(paperID);
		Users u = adapter.getUserbyid(p.getUserId());
		//lstNews.addHeaderView(header);
	//lstNews.addHeaderView(header);
	

		lstNews.setAdapter(PaperListadapter);
	if(mylist!=null && !mylist.isEmpty()){
	
			if(mylist.size()<j){
				j=mylist.size();
			}
		List<CommentInPaper> tmpList = mylist.subList(i, j);
		subList = new ArrayList<CommentInPaper>();
		for(CommentInPaper T : tmpList){
			if(!subList.contains(T))
				subList.add(T);
		}
		}
	lstNews.addHeaderView(header);
	lstNews = (PullAndLoadListView) view.findViewById(R.id.listViewnewspaper);
	PaperListadapter = new PaperListAdapter(getActivity(),
			R.layout.raw_papercmt, subList, PaperFragment.this);

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
		
		//lst.addHeaderView(header);
		//paperListAdapterHeder = new PaperListAdapterHeder(getActivity(),
		//		R.layout.raw_papercmt, mylist2, PaperFragment.this);
		//lst.setAdapter(paperListAdapterHeder);
		
			//lst.addHeaderView(header);
			if( mylist!=null &&!mylist.isEmpty()){
		//	lstNews.addHeaderView(header);
			lstNews = (PullAndLoadListView) view.findViewById(R.id.listViewnewspaper);
			PaperListadapter = new PaperListAdapter(getActivity(),
					R.layout.raw_papercmt, subList, PaperFragment.this);

			lstNews.setAdapter(PaperListadapter);
			 ((PullAndLoadListView) lstNews)
				.setOnRefreshListener(new OnRefreshListener() {

					public void onRefresh() {
						// Do work to refresh the list here.

						new PullToRefreshDataTask().execute();
					}
				});
			    ((PullAndLoadListView) lstNews)
				.setOnLoadMoreListener(new OnLoadMoreListener() {

					public void onLoadMore() {
						// Do the work to load more items at the end of list
						// here
						if(mylist.size()<j){
							j=mylist.size();
						
						if(mylist.size()< j+1){
							i=j+1;
						}
						}
					if(mylist.size()< j+10){
						j = mylist.size()-1;
					}else{
						j+=10;
					}
					tempList = mylist.subList(i, j);
					for(CommentInPaper p : tempList){
						if(!subList.contains(p))
						subList.add(p);
					}
					//Toast.makeText(getActivity(), String.valueOf(i), Toast.LENGTH_SHORT).show();
					//ListAdapter.notifyDataSetChanged();
					new LoadMoreDataTask().execute();
					}
				});
				}
		
				
		
		
		sharebtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent sharingIntent = new Intent(
						android.content.Intent.ACTION_SEND);
				sharingIntent.setType("text/plain");
				// String shareBody = x.getDescription();
				sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
						p.getTitle());
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
					if (adapter.isUserLikedPaper(CurrentUser.getId(), paperID)) {
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
							getActivity(), R.layout.dialog_addcomment, paperID);
					dialog.show();
					
				}

			}
		});
		return view;

	}
private class LoadMoreDataTask extends AsyncTask<Void, Void, Void> {

	@Override
	protected Void doInBackground(Void... params) {

		if (isCancelled()) {
			return null;
		}

		// Simulates a background task
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
		}
		//
		// for (int i = 0; i < mNames.length; i++)
		// mListItems.add(mNames[i]);
		

		return null;
	}

	@Override
	protected void onPostExecute(Void result) {

		// We need notify the adapter that the data have been changed
		((BaseAdapter) PaperListadapter).notifyDataSetChanged();

		// Call onLoadMoreComplete when the LoadMore task, has finished
		((PullAndLoadListView) lstNews).onLoadMoreComplete();

		super.onPostExecute(result);
	}

	@Override
	protected void onCancelled() {
		// Notify the loading more operation has finished
		((PullAndLoadListView) lstNews).onLoadMoreComplete();
	}
}
private class PullToRefreshDataTask extends AsyncTask<Void, Void, Void> {

	@Override
	protected Void doInBackground(Void... params) {

		if (isCancelled()) {
			return null;
		}

		// Simulates a background task
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}

		// for (int i = 0; i < mAnimals.length; i++)
		// mListItems.addFirst(mAnimals[i]);

		return null;
	}

	@Override
	protected void onPostExecute(Void result) {

		// We need notify the adapter that the data have been changed
		((BaseAdapter) PaperListadapter).notifyDataSetChanged();

		// Call onLoadMoreComplete when the LoadMore task, has finished
		((PullAndLoadListView) lstNews).onRefreshComplete();

		super.onPostExecute(result);
	}

}
	public void updateView() {
		adapter.open();
		subList = adapter.getCommentInPaperbyPaperid(paperID);
		NumofComment.setText(adapter.CommentInPaper_count(paperID).toString());

	

		lstNews = (PullAndLoadListView) view.findViewById(R.id.listViewnewspaper);
		PaperListadapter = new PaperListAdapter(getActivity(),
				R.layout.raw_papercmt, subList, PaperFragment.this);

		lstNews.setAdapter(PaperListadapter);
		adapter.close();
	}

}
