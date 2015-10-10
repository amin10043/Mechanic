package com.project.mechanic.PushNotification;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;

@SuppressLint("ValidFragment")
public class TypeUserNotification extends Fragment {

	String typeUser;
	RelativeLayout allUser, allView, allLike;
	int PageId;

	@SuppressLint("ValidFragment")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(
				R.layout.fragment_type_user_notification, null);

		allUser = (RelativeLayout) rootView.findViewById(R.id.allUser);
		allView = (RelativeLayout) rootView.findViewById(R.id.viewUsers);
		allLike = (RelativeLayout) rootView.findViewById(R.id.followUsers);

		if (typeUser.equals("Froum") || typeUser.equals("Paper")
				|| typeUser.equals("Ticket")) {

			allUser.setVisibility(View.VISIBLE);
			allView.setVisibility(View.INVISIBLE);
			allLike.setVisibility(View.INVISIBLE);
		}
		if (typeUser.equals("Object") || typeUser.equals("BirthDay")) {

			allUser.setVisibility(View.VISIBLE);
			allView.setVisibility(View.VISIBLE);
			allLike.setVisibility(View.VISIBLE);
		}

		allLike.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				SelectUserFragment fr = new SelectUserFragment();

				FragmentTransaction trans = ((MainActivity) getActivity())
						.getSupportFragmentManager().beginTransaction();

				trans.replace(R.id.content_frame, fr);

				trans.addToBackStack(null);
				trans.commit();

			}
		});

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
