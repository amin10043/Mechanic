package com.project.mechanic.fragment;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.adapter.PersonLikedAdapter;
import com.project.mechanic.entity.LikeInFroum;
import com.project.mechanic.entity.Users;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.inter.CommInterface;
import com.project.mechanic.inter.GetAsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.service.UpdatingImage;
import com.project.mechanic.utility.ServiceComm;
import com.project.mechanic.utility.Utility;

public class DialogPersonLikedFroum extends Dialog implements CommInterface,
		GetAsyncInterface, AsyncInterface {

	Context context;
	DataBaseAdapter adapter;
	int FroumId;
	ListView lv;
	DialogPersonLikedFroum dia;
	ArrayList<LikeInFroum> likedist;

	ArrayList<Integer> ids;
	ArrayList<Integer> missedIds;

	ServiceComm service;
	Utility util;

	ProgressBar progress;

	// String strIdes = "";

	UpdatingImage updating;
	Map<String, String> maps;

	String serverDate = "";
	ServerDate date;

	Users uu;

	int iid;

	int controller = 0;

	public DialogPersonLikedFroum(Context context, int FroumId,
			ArrayList<LikeInFroum> list) {
		super(context);

		this.context = context;
		this.FroumId = FroumId;
		this.likedist = list;
		adapter = new DataBaseAdapter(context);
		util = new Utility(context);
	}

	public DialogPersonLikedFroum(Context context) {
		super(context);

		adapter = new DataBaseAdapter(context);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));

		lv = (ListView) findViewById(R.id.listPeronLiked);
		progress = (ProgressBar) findViewById(R.id.progressBar1);
		missedIds = new ArrayList<Integer>();

		adapter.open();

		setContentView(R.layout.dialog_person_liked);

		if (likedist != null) {
			Users uId;
			for (int i = 0; i < likedist.size(); ++i) {
				int uidd = likedist.get(i).getUserid();
				uId = adapter.getUserById(uidd);
				if (uId == null) {
					missedIds.add(uidd);
				}
			}
		}

		adapter.close();

		if (missedIds.size() == 0) {

			progress = (ProgressBar) findViewById(R.id.progressBar1);
			lv = (ListView) findViewById(R.id.listPeronLiked);

			PersonLikedAdapter listadapter = new PersonLikedAdapter(context,
					R.layout.row_person_liked, likedist);
			lv.setAdapter(listadapter);
			progress.setVisibility(View.GONE);
			lv.setVisibility(View.VISIBLE);
			
			lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					
					LikeInFroum likes = likedist.get(position);
					adapter.open();
					Users user = adapter.getUserById(likes.getUserid());
					adapter.close();
					int userId = user.getId();
					FragmentTransaction trans = ((MainActivity) context)
							.getSupportFragmentManager().beginTransaction();
					InformationUser fragment = new InformationUser();
					Bundle bundle = new Bundle();
					bundle.putInt("userId", userId);
					fragment.setArguments(bundle);
					trans.addToBackStack(null);
					trans.replace(R.id.content_frame, fragment);
					trans.commit();
					dismiss();
					
				}
			});
		} else {
			if (context != null) {
				date = new ServerDate(context);
				date.delegate = DialogPersonLikedFroum.this;
				date.execute("");

			}
		}

	}

	@Override
	public void CommProcessFinish(String output) {

		if (!"".equals(output)
				&& output != null
				&& !(output.contains("Exception") || output.contains("java") || output
						.contains("soap"))) {
			util.parseQuery(output);

			adapter.open();

			uu = adapter.getUserById(iid);

			adapter.close();
			if (context != null) {
				updating = new UpdatingImage(context);
				updating.delegate = DialogPersonLikedFroum.this;
				maps = new LinkedHashMap<String, String>();
				maps.put("tableName", "Users");
				maps.put("Id", String.valueOf(uu.getId()));
				maps.put("fromDate", uu.getImageServerDate());
				updating.execute(maps);

			}
		} else
			Toast.makeText(context, "خطا در دریافت کاربران", 0).show();
	}

	@Override
	public void processFinish(byte[] output) {

		lv = (ListView) findViewById(R.id.listPeronLiked);
		progress = (ProgressBar) findViewById(R.id.progressBar1);

		util.CreateFile(output, iid, "Mechanical", "Users", "user", "Users" );
		adapter.open();
		adapter.UpdateImageServerDate(iid, "Users", serverDate);
		adapter.close();

		PersonLikedAdapter listadapter = new PersonLikedAdapter(context,
				R.layout.row_person_liked, likedist);
		lv.setAdapter(listadapter);

		listadapter.notifyDataSetChanged();

		progress.setVisibility(View.GONE);
		lv.setVisibility(View.VISIBLE);

	}

	@Override
	public void processFinish(String output) {

		serverDate = output;
		getUserFromServer(missedIds, controller);

		adapter.open();

	}

	private void getUserFromServer(ArrayList<Integer> missedIds, int controller) {

		if (controller < missedIds.size()) {

			iid = missedIds.get(controller);
			if (context != null) {
				service = new ServiceComm(context);
				service.delegate = DialogPersonLikedFroum.this;
				Map<String, String> items = new LinkedHashMap<String, String>();
				items.put("tableName", "getUserById");
				items.put("Id", String.valueOf(iid));
				service.execute(items);

			}
		}

	}
}
