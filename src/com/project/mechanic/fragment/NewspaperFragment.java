package com.project.mechanic.fragment;




import java.util.ArrayList;
import java.util.List;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.ListView.PullAndLoadListView;
import com.project.mechanic.ListView.PullAndLoadListView.OnLoadMoreListener;
import com.project.mechanic.ListView.PullToRefreshListView.OnRefreshListener;
import com.project.mechanic.adapter.NewsListAdapter;
import com.project.mechanic.adapter.NewspaperListAdapter;
import com.project.mechanic.adapter.ProvinceListAdapter;
import com.project.mechanic.entity.Froum;
import com.project.mechanic.entity.News;
import com.project.mechanic.entity.Province;
import com.project.mechanic.model.DataBaseAdapter;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class NewspaperFragment extends Fragment {

	ArrayList<News> mylist;
	DataBaseAdapter mdb;
	int i=0,j=9;
	List<News> subList;
	List<News> tempList;
	PullAndLoadListView lstNews;
	NewspaperListAdapter ListAdapter;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//((MainActivity) getActivity()).setActivityTitle(R.string.news);

		View view = inflater.inflate(R.layout.fragment_newspaper, null);

//	TextView textmore = (TextView) view.findViewById(R.id.newsmoretxt);
		mdb = new DataBaseAdapter(getActivity());
		mdb.open();
		mylist = mdb.getAllNews();
		mdb.close();
		
		if(mylist!=null && !mylist.isEmpty()){
			
			if(mylist.size()<j){
				j=mylist.size();
			}
		List<News> tmpList = mylist.subList(i, j);
		subList = new ArrayList<News>();
		for(News p : tmpList){
			if(!subList.contains(p))
				subList.add(p);
		}
		}
		if(mylist!=null && !mylist.isEmpty()){
		lstNews = (PullAndLoadListView) view.findViewById(R.id.listvnewspaper);
        ListAdapter = new NewspaperListAdapter(
				getActivity(), R.layout.row_newspaper, subList);

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
			for(News p : tempList){
				if(!subList.contains(p))
				subList.add(p);
			}
			//Toast.makeText(getActivity(), String.valueOf(i), Toast.LENGTH_SHORT).show();
			//ListAdapter.notifyDataSetChanged();
			new LoadMoreDataTask().execute();
			}
		});
		}
		
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

	
}

	
	

