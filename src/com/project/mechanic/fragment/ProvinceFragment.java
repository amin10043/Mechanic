package com.project.mechanic.fragment;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.ListView.PullAndLoadListView;
import com.project.mechanic.ListView.PullAndLoadListView.OnLoadMoreListener;
import com.project.mechanic.ListView.PullToRefreshListView.OnRefreshListener;
import com.project.mechanic.adapter.ProvinceListAdapter;
import com.project.mechanic.entity.Province;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class ProvinceFragment extends Fragment {

	DataBaseAdapter adapter;
	Utility util;
	private ImageView search;
	PullAndLoadListView lstProvince;
	ProvinceListAdapter ListAdapter;
	ArrayList<Province> mylist;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//((MainActivity) getActivity()).setActivityTitle(R.string.ostan);
		util = new Utility(getActivity());

		View view = inflater.inflate(R.layout.fragment_ostan, null);
		search = (ImageView) view.findViewById(R.id.sedarch_v);
		adapter = new DataBaseAdapter(getActivity());

		adapter.open();

		mylist = adapter.getAllProvinceName();
		adapter.close();

		lstProvince = (PullAndLoadListView) view.findViewById(R.id.listvOstan);
		ListAdapter = new ProvinceListAdapter(getActivity(),
				R.layout.row_ostan, mylist);

		lstProvince.setAdapter(ListAdapter);

		((PullAndLoadListView) lstProvince)
				.setOnRefreshListener(new OnRefreshListener() {

					public void onRefresh() {
						// Do work to refresh the list here.

						new PullToRefreshDataTask().execute();
					}
				});

		// set a listener to be invoked when the list reaches the end
		((PullAndLoadListView) lstProvince)
				.setOnLoadMoreListener(new OnLoadMoreListener() {

					public void onLoadMore() {
						// Do the work to load more items at the end of list
						// here
						new LoadMoreDataTask().execute();
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
				Thread.sleep(1000);
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
			((PullAndLoadListView) lstProvince).onLoadMoreComplete();

			super.onPostExecute(result);
		}

		@Override
		protected void onCancelled() {
			// Notify the loading more operation has finished
			((PullAndLoadListView) lstProvince).onLoadMoreComplete();
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
			((PullAndLoadListView) lstProvince).onRefreshComplete();

			super.onPostExecute(result);
		}

		@Override
		protected void onCancelled() {
			// Notify the loading more operation has finished
			((PullAndLoadListView) lstProvince).onLoadMoreComplete();
		}
	}

}
