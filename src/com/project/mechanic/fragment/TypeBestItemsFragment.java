package com.project.mechanic.fragment;

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
import com.project.mechanic.PushNotification.MessageNotification;

public class TypeBestItemsFragment extends Fragment {

	RelativeLayout topPage, topPost, topPaper;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_type_best_items,
				null);

		topPage = (RelativeLayout) rootView.findViewById(R.id.topViewPage);
		topPost = (RelativeLayout) rootView.findViewById(R.id.topViewPost);
		topPaper = (RelativeLayout) rootView.findViewById(R.id.topViewPapar);

		topPage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				TopItems fr = new TopItems("page");

				FragmentTransaction trans = ((MainActivity) getActivity())
						.getSupportFragmentManager().beginTransaction();

				trans.replace(R.id.content_frame, fr);

				trans.addToBackStack(null);
				trans.commit();
			}
		});

		topPost.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				TopItems fr = new TopItems("post");

				FragmentTransaction trans = ((MainActivity) getActivity())
						.getSupportFragmentManager().beginTransaction();

				trans.replace(R.id.content_frame, fr);

				trans.addToBackStack(null);
				trans.commit();
			}
		});
		topPaper.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				TopItems fr = new TopItems("paper");

				FragmentTransaction trans = ((MainActivity) getActivity())
						.getSupportFragmentManager().beginTransaction();

				trans.replace(R.id.content_frame, fr);

				trans.addToBackStack(null);
				trans.commit();
			}
		});
		return rootView;
	}

}
