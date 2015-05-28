package com.project.mechanic.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.ListView.PullAndLoadListView;
import com.project.mechanic.ListView.PullAndLoadListView.OnLoadMoreListener;
import com.project.mechanic.ListView.PullToRefreshListView.OnRefreshListener;
import com.project.mechanic.adapter.PapertitleListAdapter;
import com.project.mechanic.entity.CommentInPaper;
import com.project.mechanic.entity.News;
import com.project.mechanic.entity.Paper;
import com.project.mechanic.entity.Users;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class TitlepaperFragment extends Fragment {
	private ImageButton addtitle;
	private DialogPaperTitle dialog;
	DataBaseAdapter mdb;
	View view;
	ArrayList<Paper> mylist;
	List<Paper> subList;
	List<Paper> tempList;
	PullAndLoadListView lstNews;
	int i=0,j=9;
	ListView lst;
	PapertitleListAdapter ListAdapter;
	public static final int DIALOG_FRAGMENT = 1;
	Utility utility;
	Users CurrentUser;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(android.view.LayoutInflater inflater,
			android.view.ViewGroup container, Bundle savedInstanceState) {

		//((MainActivity) getActivity()).setActivityTitle(R.string.News);
		view = inflater.inflate(R.layout.fragment_titlepaper, null);
		addtitle = (ImageButton) view.findViewById(R.id.imgBtnAddcmt_CmtFroum);

		mdb = new DataBaseAdapter(getActivity());
		utility = new Utility(getActivity());
		CurrentUser = utility.getCurrentUser();
		mdb.open();
		mylist = mdb.getAllPaper();
		mdb.close();
		if(mylist!=null && !mylist.isEmpty()){
			
			if(mylist.size()<j){
				j=mylist.size();
			}
		List<Paper> tmpList = mylist.subList(i, j);
		subList = new ArrayList<Paper>();
		for(Paper p : tmpList){
			if(!subList.contains(p))
				subList.add(p);
		}
		}

		addtitle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (CurrentUser == null) {
					Toast.makeText(getActivity(), "ابتدا باید وارد شوید",
							Toast.LENGTH_SHORT).show();
					return;
				} else {
					dialog = new DialogPaperTitle(getActivity(),
							R.layout.dialog_addtitle, TitlepaperFragment.this);
					dialog.show();
				}

			}
		});
		
		
		if(mylist!=null && !mylist.isEmpty()){

		lstNews = (PullAndLoadListView) view.findViewById(R.id.lstComment);
		ListAdapter = new PapertitleListAdapter(getActivity(),
				R.layout.raw_froumtitle, subList);
		lstNews.setAdapter(ListAdapter);
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
				if(mylist.size()< j+1){
					i=j+1;
				}
			
			if(mylist.size()< j+10){
				j = mylist.size()-1;
			}else{
				j+=10;
			}
			tempList = mylist.subList(i, j);
			for(Paper p : tempList){
				if(!subList.contains(p))
				subList.add(p);
			}
			//Toast.makeText(getActivity(), String.valueOf(i), Toast.LENGTH_SHORT).show();
			//ListAdapter.notifyDataSetChanged();
			new LoadMoreDataTask().execute();
			}
		});
		}

		/*
		 * lst.setOnItemClickListener(new OnItemClickListener() {
		 * 
		 * @Override public void onItemClick(AdapterView<?> arg0, View arg1, int
		 * arg2, long arg3) {
		 * 
		 * FragmentTransaction trans = getActivity()
		 * .getSupportFragmentManager().beginTransaction();
		 * trans.replace(R.id.content_frame, new PaperFragment());
		 * trans.commit(); } });
		 */
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
			((BaseAdapter) ListAdapter).notifyDataSetChanged();

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
			((BaseAdapter) ListAdapter).notifyDataSetChanged();

			// Call onLoadMoreComplete when the LoadMore task, has finished
			((PullAndLoadListView) lstNews).onRefreshComplete();

			super.onPostExecute(result);
		}

}
	public void updateView() {
		mdb.open();
		mylist = mdb.getAllPaper();
		mdb.close();

		ListAdapter = new PapertitleListAdapter(getActivity(),
				R.layout.raw_froumtitle, mylist);
		ListAdapter.notifyDataSetChanged();
		lst.setAdapter(ListAdapter);

	}

}