package com.project.mechanic.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;

public class DialogSettings extends Fragment {
	RelativeLayout Introduction, BestItems, NewsApp;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.dialog_settings, null);

		Introduction = (RelativeLayout) rootView
				.findViewById(R.id.introductionApp);
		BestItems = (RelativeLayout) rootView.findViewById(R.id.best);
		NewsApp = (RelativeLayout) rootView.findViewById(R.id.newsApp);

		Introduction.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Toast.makeText(getActivity(), "معرفی امکانات نرم افزار ", 0)
						.show();
			}
		});

		BestItems.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				FragmentTransaction trans = ((MainActivity) getActivity())
						.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new TypeBestItemsFragment());

				trans.addToBackStack(null);
				trans.commit();

			}
		});

		NewsApp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

			}
		});

		return rootView;
	}

}
