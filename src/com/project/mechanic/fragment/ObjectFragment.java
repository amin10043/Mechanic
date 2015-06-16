package com.project.mechanic.fragment;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.Action.FloatingActionButton;
import com.project.mechanic.adapter.ObjectListAdapter;
import com.project.mechanic.entity.Object;
import com.project.mechanic.entity.Users;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class ObjectFragment extends Fragment {

	DataBaseAdapter adapter;
	private Intent intent;
	Users currentUser;
	Utility util;
	DialogCreatePage dialog;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		((MainActivity) getActivity()).setTitle(R.string.object);

		View view = inflater.inflate(R.layout.fragment_object, null);

		SharedPreferences sendData = getActivity()
				.getSharedPreferences("Id", 0);
		final int id = sendData.getInt("main_Id", -1);
		Toast.makeText(getActivity(), "id = " + id, Toast.LENGTH_SHORT).show();
		final int city_id = Integer.valueOf(getArguments().getString("cityId"));

		adapter = new DataBaseAdapter(getActivity());
		util = new Utility(getActivity());
		currentUser = util.getCurrentUser();
		// RelativeLayout createPage = (RelativeLayout) view
		// .findViewById(R.id.relative);
		adapter.open();
		ArrayList<Object> mylist = adapter.getObjectBy_BTId_CityId(id, city_id);
		adapter.close();

		ListView lstObject = (ListView) view
				.findViewById(R.id.listvCmt_Introduction);
		ObjectListAdapter ListAdapter = new ObjectListAdapter(getActivity(),
				R.layout.row_object, mylist);

		lstObject.setAdapter(ListAdapter);

		FloatingActionButton createItem = (FloatingActionButton) view
				.findViewById(R.id.fab);
		final String message = "کاربر گرامی اگر برند یا مجموعه تولیدی را در اختیار دارید یا در بازار ایران نسبت به آن شناخت دارید که در این نرم افزار قید نشده لطفا مشخصات مورد نظر را برای ما ارسال نمایید   با تشکر";

		createItem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog = new DialogCreatePage(getActivity(), message);
				dialog.show();

				SharedPreferences sendMainObjectId = getActivity()
						.getSharedPreferences("Id", 0);
				sendMainObjectId.edit().putInt("MainObjectId", id).commit();
				sendMainObjectId.edit().putInt("CityId", city_id).commit();
				Toast.makeText(getActivity(), "MainObjectId = " + id,
						Toast.LENGTH_SHORT).show();
			}
		});

		// createPage.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		//
		// dialog = new DialogCreatePage(getActivity());
		// dialog.show();
		//
		// }
		// });

		return view;
	}

}
