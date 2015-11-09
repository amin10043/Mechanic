package com.project.mechanic.fragment;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.adapter.PersonLikedAdapter;
import com.project.mechanic.adapter.PersonLikedPaperAdapter;
import com.project.mechanic.entity.LikeInFroum;
import com.project.mechanic.entity.LikeInPaper;
import com.project.mechanic.entity.Users;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.inter.CommInterface;
import com.project.mechanic.inter.GetAsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.service.UpdatingImage;
import com.project.mechanic.utility.ServiceComm;
import com.project.mechanic.utility.Utility;

public class DialogPersonLikedPaper extends Dialog implements AsyncInterface,
		CommInterface, GetAsyncInterface {
	int PaperId;
	ArrayList<LikeInPaper> listLike;
	DataBaseAdapter adapter;
	ListView lv;
	Context context;
	ProgressBar progress;
	ArrayList<Integer> ids;
	ArrayList<Integer> missedIds;
	ServerDate date;
	int controller = 0;
	String serverDate = "";
	int iid;
	ServiceComm service;
	Utility util;
	Users uu;
	UpdatingImage updating;
	Map<String, String> maps;

	public DialogPersonLikedPaper(Context context, int PaperId,
			ArrayList<LikeInPaper> likelist) {
		super(context);
		this.context = context;
		this.PaperId = PaperId;
		this.listLike = likelist;
		adapter = new DataBaseAdapter(context);
		util = new Utility(context);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));

		lv = (ListView) findViewById(R.id.listPeronLiked);
		progress = (ProgressBar) findViewById(R.id.progressBar1);
		missedIds = new ArrayList<Integer>();

		setContentView(R.layout.dialog_person_liked);

		adapter.open();

		if (listLike != null) {
			Users uId;
			for (int i = 0; i < listLike.size(); ++i) {
				int uidd = listLike.get(i).getUserid();
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

			progress.setVisibility(View.GONE);
			lv.setVisibility(View.VISIBLE);

			PersonLikedPaperAdapter listadapter = new PersonLikedPaperAdapter(
					context, R.layout.row_person_liked, listLike);
			lv.setAdapter(listadapter);

			lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long arg3) {

					LikeInPaper likes = listLike.get(position);
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
					trans.replace(R.id.content_frame, fragment);
					trans.commit();
					dismiss();
				}
			});

		} else {
			if (context != null) {
				date = new ServerDate(context);
				date.delegate = DialogPersonLikedPaper.this;
				date.execute("");

			}
		}
	}

	@Override
	public void processFinish(String output) {

		serverDate = output;
		getUserFromServer(missedIds, controller);
	}

	private void getUserFromServer(ArrayList<Integer> missedIds, int controller) {

		if (controller < missedIds.size()) {

			iid = missedIds.get(controller);
			if (context != null) {
				service = new ServiceComm(context);
				service.delegate = DialogPersonLikedPaper.this;
				Map<String, String> items = new LinkedHashMap<String, String>();
				items.put("tableName", "getUserById");
				items.put("Id", String.valueOf(iid));
				service.execute(items);

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
				updating.delegate = DialogPersonLikedPaper.this;
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
		
		if (output != null) {

			

			util.CreateFile(output, iid, "Mechanical", "Users", "user", "Users");
			adapter.open();
			adapter.UpdateImageServerDate(iid, "Users", serverDate);
			adapter.close();
		} else {

			PersonLikedPaperAdapter listadapter = new PersonLikedPaperAdapter(
					context, R.layout.row_person_liked, listLike);
			lv.setAdapter(listadapter);

			listadapter.notifyDataSetChanged();

			progress.setVisibility(View.GONE);
			lv.setVisibility(View.VISIBLE);
		}

	}

}
