package com.project.mechanic.fragment;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import com.project.mechanic.R;
import com.project.mechanic.StaticValues;
import com.project.mechanic.adapter.ObjectListAdapter;
import com.project.mechanic.adapter.PostTimelineListadapter;
import com.project.mechanic.entity.Post;
import com.project.mechanic.entity.PostTimeline;
import com.project.mechanic.entity.Settings;
import com.project.mechanic.entity.Users;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.inter.AsyncInterfaceVisit;
import com.project.mechanic.inter.CommInterface;
import com.project.mechanic.inter.GetAsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.UpdatingVisit;
import com.project.mechanic.utility.Utility;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class PostTimelineFragment extends Fragment implements AsyncInterface, GetAsyncInterface, CommInterface , AsyncInterfaceVisit {

	ArrayList<PostTimeline> TimeLinePost;
	ListView ListTimeLine;
	Users Currentuser;
	Utility utility;
	ArrayList<Integer> ids;
	ArrayList<Integer> missedIds;
	DataBaseAdapter adapter;
	Settings setting;
	int visitCounter = 0;
	Post post;

	@SuppressLint("InflateParams")
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceStdataate) {

		View view = inflater.inflate(R.layout.fragment_timelinepost, null);
		ListTimeLine = (ListView) view.findViewById(R.id.TimeLineList);

		adapter = new DataBaseAdapter(getActivity());
		utility = new Utility(getActivity());
		Currentuser = utility.getCurrentUser();

		// Fill List View of Timeline
		fillListView();
		getCountVisitFromServer();

		utility.ShowFooterAgahi(getActivity(), false, 0);
		return view;
	}

	private void fillListView() {

		adapter.open();
		TimeLinePost = adapter.getAllTimeline(Currentuser.getId());
		PostTimelineListadapter ListAdapterTimeLine = new PostTimelineListadapter(getActivity(), R.layout.raw_posttitle,
				TimeLinePost, PostTimelineFragment.this);
		ListTimeLine.setAdapter(ListAdapterTimeLine);
	}

	public void CommProcessFinish(String output) {
	}

	public void processFinish(byte[] output) {
	}

	public void processFinish(String output) {
	}

	private void getCountVisitFromServer() {

		if (visitCounter < TimeLinePost.size()) {

			adapter.open();
			post = adapter.getPostItembyid(TimeLinePost.get(visitCounter).getPostId());
			adapter.close();

			UpdatingVisit updateVisit = new UpdatingVisit(getActivity());
			updateVisit.delegate = PostTimelineFragment.this;
			Map<String, String> serv = new LinkedHashMap<String, String>();

			serv.put("tableName", "Visit");
			serv.put("objectId", String.valueOf(post.getId()));
			serv.put("typeId", StaticValues.TypePostVisit + "");
			updateVisit.execute(serv);

		} else {
			if (getActivity() != null) {

				fillListView();
			}
		}

	}

	@Override
	public void processFinishVisit(String output) {
		if (!output.contains("Exception")) {

			adapter.open();
			adapter.updateCountView("Post", post.getId(), Integer.valueOf(output));
			adapter.close();
		}
		visitCounter++;
		getCountVisitFromServer();
	}
}
