package com.project.mechanic.fragment;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.project.mechanic.R;
import com.project.mechanic.adapter.PostTimelineListadapter;
import com.project.mechanic.entity.PostTimeline;
import com.project.mechanic.entity.Settings;
import com.project.mechanic.entity.Users;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.inter.CommInterface;
import com.project.mechanic.inter.GetAsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class PostTimelineFragment extends Fragment implements AsyncInterface,
		GetAsyncInterface, CommInterface {

	ArrayList<PostTimeline> TimeLinePost;
	ListView ListTimeLine;
	Users Currentuser;
	Utility utility;
	ArrayList<Integer> ids;
	ArrayList<Integer> missedIds;
	DataBaseAdapter adapter;
	Settings setting;

	@SuppressLint("InflateParams")
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceStdataate) {

		View view = inflater.inflate(R.layout.fragment_timelinepost, null);
		ListTimeLine = (ListView) view.findViewById(R.id.TimeLineList);

		adapter = new DataBaseAdapter(getActivity());
		utility = new Utility(getActivity());
		Currentuser = utility.getCurrentUser();

		// Fill List View of Timeline
		fillListView();

		return view;
	}

	private void fillListView() {
		adapter.open();
		TimeLinePost = adapter.getAllTimeline(Currentuser.getId());
		PostTimelineListadapter ListAdapterTimeLine = new PostTimelineListadapter(
				getActivity(), R.layout.raw_posttitle, TimeLinePost,
				PostTimelineFragment.this);
		ListTimeLine.setAdapter(ListAdapterTimeLine);
	}

	public void CommProcessFinish(String output) {
	}

	public void processFinish(byte[] output) {
	}

	public void processFinish(String output) {
	}

}
