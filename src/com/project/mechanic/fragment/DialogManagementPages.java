package com.project.mechanic.fragment;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mechanic.R;
import com.project.mechanic.adapter.ObjectListAdapter;
import com.project.mechanic.entity.Object;
import com.project.mechanic.entity.Users;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.inter.GetAsyncInterface;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.service.UpdatingImage;
import com.project.mechanic.utility.Utility;

public class DialogManagementPages extends Dialog implements GetAsyncInterface {

	Context context;
	List<Object> listPage;
	ListView lv;
	TextView namePage;
	Fragment fragment;

	ServerDate date;

	String time;
	int controller = 0;

	UpdatingImage ImageUpdating;
	Map<String, String> maps;
	ObjectListAdapter listAdapter;
	Utility util;
	int mainId;

	public DialogManagementPages(Context context, List<Object> listPage,
			Fragment fragment) {
		super(context);
		this.context = context;
		this.listPage = listPage;
		this.fragment = fragment;
		util = new Utility(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// getWindow().setBackgroundDrawable(
		// new ColorDrawable(android.graphics.Color.TRANSPARENT));

		setContentView(R.layout.dialog_managment_pages);

		final SharedPreferences currentTime = context.getSharedPreferences(
				"time", 0);

		time = currentTime.getString("time", "-1");
		lv = (ListView) findViewById(R.id.listPageUser);
		namePage = (TextView) findViewById(R.id.namepageList);
		RelativeLayout noItem = (RelativeLayout) findViewById(R.id.noPage);

		if (listPage.size() == 0) {
			noItem.setVisibility(View.VISIBLE);
		} else {

			listAdapter = new ObjectListAdapter(context, R.layout.row_object,
					listPage, fragment, false, time, 0);
			lv.setAdapter(listAdapter);
		}

		Utility util = new Utility(context);
		Users currentUser = util.getCurrentUser();
		namePage.setText(currentUser.getName());
		getImageProfile(listPage, controller);

	}

	@Override
	public void processFinish(byte[] output) {

		if (output != null)
			util.CreateFile(output, mainId, "Mechanical", "Profile", "profile",
					"Object");

		controller++;
		getImageProfile(listPage, controller);

	}

	public void getImageProfile(List<Object> listPage, int controller) {

		if (controller < listPage.size()) {

			Object obj = listPage.get(controller);
			mainId = obj.getId();

			if (context != null) {
				ImageUpdating = new UpdatingImage(context);
				ImageUpdating.delegate = DialogManagementPages.this;
				maps = new LinkedHashMap<String, String>();
				maps.put("tableName", "Object2");
				maps.put("Id", String.valueOf(mainId));
				maps.put("fromDate", obj.getImage2ServerDate());
				ImageUpdating.execute(maps);
			}
		} else {

			listAdapter = new ObjectListAdapter(context, R.layout.row_object,
					listPage, fragment, false, time, 0);
			lv.setAdapter(listAdapter);

			listAdapter.notifyDataSetChanged();

		}
	}
}
