package com.project.mechanic.fragment;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.adapter.ObjectListAdapter;
import com.project.mechanic.entity.Object;
import com.project.mechanic.entity.Users;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class MainBrandFragment extends Fragment {
	DataBaseAdapter adapter;
	int id;
	Users CurrentUser;
	Utility util;

	// List<Object> objectList = null;

	// public MainBrandFragment(List<Object> allob) {
	// super();
	// objectList = allob;
	// }

	public MainBrandFragment() {
		super();
	}

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		id = Integer.valueOf(getArguments().getString("Id"));

		((MainActivity) getActivity()).setTitle(R.string.object);

		View view = inflater.inflate(R.layout.fragment_object, null);
		ImageButton createPage = (ImageButton) view
				.findViewById(R.id.imgBtnAddcmt_CmtFroum);

		adapter = new DataBaseAdapter(getActivity());
		util = new Utility(getActivity());
		CurrentUser = util.getCurrentUser();

		adapter.open();
		// objectList = adapter.getObjectbyParentId(id);
		ArrayList<Object> mylist = adapter.getObjectbyParentId(id);

		adapter.close();

		// RelativeLayout CreatePage = (RelativeLayout) view
		// .findViewById(R.id.relative);
		if (CurrentUser == null)
			createPage.setImageResource(R.drawable.ic_create_off);

		createPage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (CurrentUser == null) {
					Toast.makeText(getActivity(), "ابتدا باید وارد شوید",
							Toast.LENGTH_SHORT).show();
				} else {

					SharedPreferences sendParentID = getActivity()
							.getSharedPreferences("Id", 0);

					FragmentTransaction trans = getActivity()
							.getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame,
							new CreateIntroductionFragment());
					sendParentID.edit().putInt("ParentId", id).commit();
					trans.commit();
				}
			}
		});

		ListView lstObject = (ListView) view
				.findViewById(R.id.listvCmt_Introduction);
		ObjectListAdapter ListAdapter = new ObjectListAdapter(getActivity(),
				R.layout.row_object, mylist);

		lstObject.setAdapter(ListAdapter);

		return view;
	}
}
