package com.project.mechanic.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mechanic.R;
import com.project.mechanic.adapter.SelectUsersAdapter;
import com.project.mechanic.entity.LikeInObject;
import com.project.mechanic.entity.Object;
import com.project.mechanic.entity.Users;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class SelectUserFragment extends Fragment {
	static final int PRICE = 50;
	GridView userGrid;
	DataBaseAdapter dbAdapter;
	Utility util;
	ArrayList<Users> user;
	boolean allChecked = false;
	int countSelected = 0;

	TextView txtCount, resultPrice;

	public int countItems;
	SelectUsersAdapter listAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_select_user, null);
		dbAdapter = new DataBaseAdapter(getActivity());
		util = new Utility(getActivity());
		user = new ArrayList<Users>();
		userGrid = (GridView) rootView.findViewById(R.id.usersList);

		txtCount = (TextView) rootView.findViewById(R.id.mumberUser);
		resultPrice = (TextView) rootView.findViewById(R.id.result);

		CheckBox selectAll = (CheckBox) rootView.findViewById(R.id.checkBox1);

		dbAdapter.open();
		Object obj = dbAdapter.getObjectByUserId(util.getCurrentUser().getId());
		if (obj != null) {
			List<LikeInObject> like = dbAdapter.getAllLikeFromObject(
					obj.getId(), 0);
			if (like.size() > 0)
				for (int i = 0; i < like.size(); i++) {
					int id = like.get(i).getUserId();
					Users u = dbAdapter.getUserById(id);
					user.add(u);

				}
		}
		dbAdapter.close();

		listAdapter = new SelectUsersAdapter(getActivity(),
				R.layout.row_select_users, user, SelectUserFragment.this,
				allChecked);

		userGrid.setAdapter(listAdapter);

		selectAll.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked == true) {
					allChecked = true;
					countSelected = user.size();
				} else {
					allChecked = false;
					countSelected = 0;

				}
				listAdapter = new SelectUsersAdapter(getActivity(),
						R.layout.row_select_users, user,
						SelectUserFragment.this, allChecked);

				userGrid.setAdapter(listAdapter);
				txtCount.setText(countSelected + "");
				FinalPrice(countSelected, PRICE);

			}
		});
		
		

		return rootView;
	}

	public int FinalPrice(int numberOfMessage, int price) {
		int result = numberOfMessage * price;
		resultPrice.setText(result + "");
		return result;
	}
}
