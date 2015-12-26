package com.project.mechanic.PushNotification;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;

@SuppressLint("ValidFragment")
public class TypeUserNotification extends Fragment {

	String typeUser;
	int PageId, visibleValue;

	@SuppressLint("ValidFragment")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(
				R.layout.fragment_type_user_notification, null);

		ListView typeItem = (ListView) rootView
				.findViewById(R.id.list_type_member);

		ArrayList<String> titleList = new ArrayList<String>();
		ArrayList<Integer> iconList = new ArrayList<Integer>();
		ArrayList<Integer> numberMember = new ArrayList<Integer>();

		titleList.add("کاربرانی که نرم افزار را نصب  دارند");
		titleList.add("کاربرانی که صفحه شما را بازدید کرده اند");
		titleList.add("کاربرانی که صفحه شما را دنبال می کنند");

		iconList.add(R.drawable.numvv);
		iconList.add(R.drawable.numvv);
		iconList.add(R.drawable.numll);

		numberMember.add(20);
		numberMember.add(34);
		numberMember.add(44);

		if (typeUser.equals("Froum") || typeUser.equals("Paper")
				|| typeUser.equals("Ticket")) {

			visibleValue = 1;

			
			titleList.clear();
			titleList.add("کاربرانی که نرم افزار را نصب  دارند");
			
			TypeUserNotificationAdapter listAdapter = new TypeUserNotificationAdapter(
					getActivity(), R.layout.row_type_user, titleList, iconList,
					numberMember, visibleValue);
			
		
			typeItem.setAdapter(listAdapter);

		}
		if (typeUser.equals("Object") || typeUser.equals("BirthDay")) {

			visibleValue = 2;
			TypeUserNotificationAdapter listAdapter = new TypeUserNotificationAdapter(
					getActivity(), R.layout.row_type_user, titleList, iconList,
					numberMember, visibleValue);

			typeItem.setAdapter(listAdapter);
		}

		return rootView;
	}

	public TypeUserNotification(String typeUser) {
		this.typeUser = typeUser;
	}

	public TypeUserNotification(String typeUser, int PageId) {
		this.typeUser = typeUser;
		this.PageId = PageId;
	}

}
