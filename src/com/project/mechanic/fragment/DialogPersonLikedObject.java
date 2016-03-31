package com.project.mechanic.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.StaticValues;
import com.project.mechanic.adapter.PersonLikedObjectAdapter;
import com.project.mechanic.entity.Users;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.inter.CommInterface;
import com.project.mechanic.inter.GetAsyncInterface;
import com.project.mechanic.interfaceServer.UsersFollowersInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.server.UsersFollowersServer;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.service.ServiceComm;
import com.project.mechanic.service.UpdatingImage;
import com.project.mechanic.utility.Utility;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class DialogPersonLikedObject extends Dialog
		implements AsyncInterface, CommInterface, GetAsyncInterface, UsersFollowersInterface {

	Context context;
	DataBaseAdapter dbAdapter;
	int ObjectId;
	ListView lv;
	List<Integer> idsUsers;
	ProgressBar progress;
	ArrayList<Integer> ids;
	ArrayList<Integer> missedIds = new ArrayList<Integer>();;
	ServerDate date;
	int controller = 0;
	String serverDate = "";
	int userId;
	ServiceComm service;
	Utility util;
	// Users user;
	UpdatingImage updating;
	Map<String, String> maps;
	ArrayList<Users> userList = new ArrayList<Users>();
	List<String> dateLike = new ArrayList<String>();
	PersonLikedObjectAdapter listadapter;

	// boolean isMissedUser = false, getUser = true;

	public DialogPersonLikedObject(Context context, int ObjectId, List<Integer> idsUsers, List<String> dateLike) {
		super(context);

		this.context = context;
		this.ObjectId = ObjectId;
		this.idsUsers = idsUsers;
		this.dateLike = dateLike;
		dbAdapter = new DataBaseAdapter(context);
		util = new Utility(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.dialog_person_liked);

		lv = (ListView) findViewById(R.id.listPeronLiked);
		progress = (ProgressBar) findViewById(R.id.progressBar1);

		if (util.isNetworkConnected()) {

			getUsers();

		} else {
			fillListViewFollowed();
		}

		// if (likedist != null) {
		// Users userItem;
		// dbAdapter.open();
		// for (int i = 0; i < likedist.size(); ++i) {
		//
		// int userId = likedist.get(i).getUserId();
		// dateLike.add(likedist.get(i).getDatetime());
		// userItem = dbAdapter.getUserById(userId);
		//
		// if (userItem == null) {
		// missedIds.add(userId);
		// } else {
		// userList.add(userItem);
		// }
		// }
		// dbAdapter.close();
		// }
		// if (missedIds.size() == 0) {
		//
		// fillListView();
		//
		// controller = 0;
		// getImage();
		//
		// } else {
		// if (context != null) {
		// date = new ServerDate(context);
		// date.delegate = DialogPersonLikedObject.this;
		// date.execute("");
		// isMissedUser = true;
		//
		// }
		// }

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

				// LikeInObject likes = likedist.get(position);
				// dbAdapter.open();
				// Users user = dbAdapter.getUserById(likes.getUserId());
				// dbAdapter.close();
				// int userId = user.getId();

				FragmentTransaction trans = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
				InformationUser fragment = new InformationUser();
				Bundle bundle = new Bundle();
				bundle.putInt("userId", idsUsers.get(position));
				fragment.setArguments(bundle);
				trans.replace(R.id.content_frame, fragment);
				trans.commit();
				dismiss();
			}
		});

	}

	@Override
	public void processFinish(String output) {

		// if (util.checkError(output) == false) {
		//
		// serverDate = output;
		// getUserFromServer();
		// }
	}

	// private void getUserFromServer() {
	//
	// if (controller < missedIds.size()) {
	//
	// userIdMissed = missedIds.get(controller);
	// if (context != null) {
	// service = new ServiceComm(context);
	// service.delegate = DialogPersonLikedObject.this;
	// Map<String, String> items = new LinkedHashMap<String, String>();
	// items.put("tableName", "getUserById");
	// items.put("Id", String.valueOf(userIdMissed));
	// service.execute(items);
	//
	// }
	// } else {
	//
	// fillListView();
	//
	// controller = 0;
	// // getImage();
	//
	// }
	//
	// }

	@Override
	public void CommProcessFinish(String output) {

		if (output.contains("Exception") || output.contains("anyType"))
			output = "";

		dbAdapter.open();
		dbAdapter.UpdateImageServerDate(userId, "Users", output);
		dbAdapter.close();

		controller++;
		getDateImages();

	}

	@Override
	public void processFinish(byte[] output) {

		String imagePath = "";

		if (output != null) {

			imagePath = util.CreateFile(output, userId, "Mechanical", "Users", "user", "Users");

			if (!imagePath.equals(""))
				userList.get(controller).setImagePath(imagePath);

			listadapter.notifyDataSetChanged();

		}
		controller++;

		getImage();

	}

	private void getImage() {

		if (controller < idsUsers.size()) {

			userId = idsUsers.get(controller);

			dbAdapter.open();
			Users u = dbAdapter.getUserById(userId);
			dbAdapter.close();

			String ImageDate = "";

			if (u != null)
				if (u.getImageServerDate() != null)
					ImageDate = u.getImageServerDate();

			if (context != null) {
				updating = new UpdatingImage(context);
				updating.delegate = DialogPersonLikedObject.this;
				maps = new LinkedHashMap<String, String>();
				maps.put("tableName", "Users");
				maps.put("Id", String.valueOf(userId));
				maps.put("fromDate", ImageDate);
				updating.execute(maps);

			}

		} else {
			controller = 0;
			getDateImages();
		}

	}

	// } else {
	// updateView();
	//
	// controller = 0;
	// getDateImages();
	// }
	// } else {
	// if (controller < userList.size()) {
	//
	// userIdMissed = userList.get(controller).getId();
	//
	// dbAdapter.open();
	// user = dbAdapter.getUserById(userIdMissed);
	// dbAdapter.close();
	//
	// String imageDateUser = user.getImageServerDate();
	//
	// if (imageDateUser == null)
	// imageDateUser = "";
	//
	// if (context != null) {
	// updating = new UpdatingImage(context);
	// updating.delegate = DialogPersonLikedObject.this;
	// maps = new LinkedHashMap<String, String>();
	// maps.put("tableName", "Users");
	// maps.put("Id", String.valueOf(userIdMissed));
	// maps.put("fromDate", imageDateUser);
	// updating.execute(maps);
	//
	// }
	// } else {
	// controller = 0;
	// getDateImages();
	// }
	// }
	// }
	//
	// private void fillListView() {
	//
	// progress.setVisibility(View.GONE);
	// lv.setVisibility(View.VISIBLE);
	//
	// listadapter = new PersonLikedObjectAdapter(context,
	// R.layout.row_person_liked, userList, dateLike);
	// lv.setAdapter(listadapter);
	//
	// }

	// private void updateView() {
	//
	// userList.clear();
	// if (likedist != null) {
	// Users userItem;
	// dbAdapter.open();
	// for (int i = 0; i < likedist.size(); ++i) {
	//
	// int userId = likedist.get(i).getUserId();
	// dateLike.add(likedist.get(i).getDatetime());
	// userItem = dbAdapter.getUserById(userId);
	//
	// if (userItem != null)
	// userList.add(userItem);
	// }
	// }
	// dbAdapter.close();
	//
	// fillListView();
	//
	// }

	private void getDateImages() {

		if (controller < userList.size()) {

			userId = userList.get(controller).getId();

			if (context != null) {

				ServiceComm getDateService = new ServiceComm(context);

				getDateService.delegate = DialogPersonLikedObject.this;
				Map<String, String> items = new LinkedHashMap<String, String>();
				items.put("tableName", "getUserImageDate");
				items.put("Id", String.valueOf(userId));
				getDateService.execute(items);

			}
		}
	}

	private void getUsers() {

		if (context != null) {

			UsersFollowersServer getDateService = new UsersFollowersServer(context);
			getDateService.delegate = DialogPersonLikedObject.this;
			Map<String, String> items = new LinkedHashMap<String, String>();

			items.put("tableName", "getUserByObjectId");
			items.put("objectId", String.valueOf(ObjectId));
			items.put("type", String.valueOf(StaticValues.TypeLikePage));

			getDateService.execute(items);

		}

	}

	@Override
	public void resultUserFollowers(String output) {

		if (!output.contains("Exception")) {
			util.parseQuery(output);

			fillListViewFollowed();

		}

	}

	private void fillListViewFollowed() {

		userList.clear();
		dbAdapter.open();

		for (int i = 0; i < idsUsers.size(); i++) {

			Users u = dbAdapter.getUserById(idsUsers.get(i));

			if (u != null)
				userList.add(u);

		}

		dbAdapter.close();

		listadapter = new PersonLikedObjectAdapter(context, R.layout.row_person_liked, userList, dateLike);
		lv.setAdapter(listadapter);

		if (userList.size() > 0) {

			progress.setVisibility(View.GONE);
			lv.setVisibility(View.VISIBLE);
		}

		if (util.isNetworkConnected())

			getImage();

	}

	// private void getImage(){
	//
	// UpdatingImage updating = new UpdatingImage(context);
	// updating.delegate = this;
	// HashMap<String, String> maps = new LinkedHashMap<String, String>();
	// maps.put("tableName", "Users");
	// maps.put("Id", String.valueOf(idsUsers.getId()));
	// maps.put("fromDate", userImageServerDate);
	// updating.execute(maps);
	//
	// }

}
