package com.project.mechanic.fragment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.project.mechanic.R;
import com.project.mechanic.StaticValues;
import com.project.mechanic.adapter.PostTimelineListadapter;
import com.project.mechanic.entity.LikeInObject;
import com.project.mechanic.entity.Object;
import com.project.mechanic.entity.Post;
import com.project.mechanic.entity.PostTimeline;
import com.project.mechanic.entity.Settings;
import com.project.mechanic.entity.Users;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.inter.AsyncInterfaceVisit;
import com.project.mechanic.inter.CommInterface;
import com.project.mechanic.inter.DataPersonalInterface;
import com.project.mechanic.inter.GetAsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.GetPostByObjectId;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.service.ServiceComm;
import com.project.mechanic.service.UpdatingImage;
import com.project.mechanic.service.UpdatingPersonalPage;
import com.project.mechanic.service.UpdatingVisit;
import com.project.mechanic.utility.Utility;

import android.annotation.SuppressLint;
import android.os.BaseBundle;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class PostTimelineFragment extends Fragment
		implements AsyncInterface, GetAsyncInterface, CommInterface, AsyncInterfaceVisit, DataPersonalInterface {

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
	boolean isFirstRun, booleanPost = true;;
	String serverDate = "";
	int counterFollowObject = 0;
	// List<Object> myFollowingPages = new ArrayList<Object>();
	Map<String, String> maps;
	// Object objectItem;
	int postCounter = 0;
	Integer idItem = 0;
	List<Integer> IdArrayObject = new ArrayList<Integer>();
	// Settings settings;

	@SuppressLint("InflateParams")
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceStdataate) {

		View view = inflater.inflate(R.layout.fragment_timelinepost, null);
		ListTimeLine = (ListView) view.findViewById(R.id.TimeLineList);

		adapter = new DataBaseAdapter(getActivity());
		utility = new Utility(getActivity());

		adapter.open();
		setting = adapter.getSettings();
		adapter.close();

		Currentuser = utility.getCurrentUser();

		// Fill List View of Timeline
		fillListView();

		// run server date
		RunServiceDate();

		// getCountVisitFromServer();

		return view;
	}

	private void getPostFromServer() {

		// Object obj = null;

		if (postCounter < IdArrayObject.size()) {
			idItem = IdArrayObject.get(postCounter);

			GetPostByObjectId getPost = new GetPostByObjectId(getActivity());
			getPost.delegate = PostTimelineFragment.this;
			String[] params = new String[5];
			params[0] = "Post";
			params[1] = setting.getServerDate_Start_Post();
			params[2] = setting.getServerDate_End_Post();
			params[3] = "0";
			params[4] = String.valueOf(idItem);

			getPost.execute(params);
			booleanPost = true;
			postCounter++;
		} else
			fillListView();

	}

	private void RunServiceDate() {

		if (getActivity() != null) {

			ServerDate date = new ServerDate(getActivity());
			date.delegate = PostTimelineFragment.this;
			date.execute("");

			// isFirstRun = true;

		}
	}

	private void getFollowedPages() {

		UpdatingPersonalPage updating = new UpdatingPersonalPage(getActivity());
		updating.delegate = PostTimelineFragment.this;
		String[] params = new String[5];
		params[0] = "LikeInObject";
		params[1] = setting.getServerDate_Start_LikeInObject();
		params[2] = setting.getServerDate_End_LikeInObject();

		params[3] = "0";
		params[4] = String.valueOf(utility.getCurrentUser().getId());

		updating.execute(params);
		booleanPost = false;

	}

	private void getImageFollowed() {

		if (counterFollowObject < IdArrayObject.size()) {

			idItem = IdArrayObject.get(counterFollowObject);

			adapter.open();
			Object objectItem = adapter.getObjectbyid(idItem);
			adapter.close();

			String objectImageDate = "";
			if (objectItem != null)
				objectImageDate = objectItem.getImage2ServerDate();

			if (objectImageDate == null)
				objectImageDate = "";

			if (getActivity() != null) {

				UpdatingImage ImageUpdating = new UpdatingImage(getActivity());
				ImageUpdating.delegate = PostTimelineFragment.this;
				maps = new LinkedHashMap<String, String>();
				maps.put("tableName", "Object2");
				maps.put("Id", String.valueOf(idItem));
				maps.put("fromDate", objectImageDate);
				ImageUpdating.execute(maps);
			}

		} else {
			counterFollowObject = 0;
			getDateFollow();

		}

	}

	private void getDateFollow() {

		if (counterFollowObject < IdArrayObject.size()) {

			idItem = IdArrayObject.get(counterFollowObject);

			if (getActivity() != null) {
				ServiceComm getDateService = new ServiceComm(getActivity());

				getDateService.delegate = PostTimelineFragment.this;
				Map<String, String> items = new LinkedHashMap<String, String>();
				items.put("tableName", "getObject2ImageDate");
				items.put("Id", String.valueOf(idItem));
				getDateService.execute(items);
			}
		} else {

			getPostFromServer();

		}
	}

	private void getAllPages() {

		Set<Integer> Ids = new HashSet<Integer>();

		adapter.open();
		List<LikeInObject> likePages = adapter.getAllPageFollowingMe(utility.getCurrentUser().getId(), 0);

		if (likePages.size() > 0)
			for (int i = 0; i < likePages.size(); i++) {
				IdArrayObject.add(likePages.get(i).getPaperId());
			}
		adapter.close();

		int end = IdArrayObject.size();
		// IdArrayObject.clear();

		for (int i = 0; i < end; i++) {
			Ids.add(IdArrayObject.get(i));
		}
		IdArrayObject.clear();
		IdArrayObject = toInt(Ids);
	}

	public List<Integer> toInt(Set<Integer> set) {

		List<Integer> list = new ArrayList<Integer>();
		// int i = 0;
		for (Integer val : set)
			list.add(val);
		return list;
	}

	private void fillListView() {

		adapter.open();
		TimeLinePost = adapter.getAllTimeline(Currentuser.getId());
		PostTimelineListadapter ListAdapterTimeLine = new PostTimelineListadapter(getActivity(), R.layout.raw_posttitle,
				TimeLinePost, PostTimelineFragment.this);
		ListTimeLine.setAdapter(ListAdapterTimeLine);
		adapter.close();
	}

	public void CommProcessFinish(String output) {

		if (output.contains("Exception") || output.contains("anyType"))
			output = "";

		adapter.open();
		adapter.updateObjectImage2ServerDate(idItem, output);
		adapter.close();

		counterFollowObject++;
		getDateFollow();
	}

	public void processFinish(byte[] output) {

		if (output != null) {

			utility.CreateFile(output, idItem, "Mechanical", "Profile", "profile", "Object");

		}
		counterFollowObject++;
		getImageFollowed();
	}

	public void processFinish(String output) {

		if (!output.contains("Exception")) {

			serverDate = output;
			getFollowedPages();

		}
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
	public void resultCountView(String output) {
		if (!output.contains("Exception")) {

			adapter.open();
			adapter.updateCountView("Post", post.getId(), Integer.valueOf(output));
			adapter.close();
		}
		visitCounter++;
		getCountVisitFromServer();
	}

	@Override
	public void ResultServer(String output) {

		if (booleanPost == true) {
			if (!output.contains("Exception") && !output.contains("anyType")) {

				utility.parseQuery(output);
				getPostFromServer();
			} else
				getPostFromServer();

		} else {
			if (!output.contains("Exception") && !output.contains("anyType")) {
				utility.parseQuery(output);
				getAllPages();
				getImageFollowed();
			}

		}
	}

}
