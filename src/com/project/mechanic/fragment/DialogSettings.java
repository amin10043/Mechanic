package com.project.mechanic.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.utility.Utility;

public class DialogSettings extends Fragment {
	RelativeLayout Introduction, BestItems, NewsApp;
	Utility util;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.dialog_settings, null);
		util = new Utility(getActivity());

		TextView introductiontxt = (TextView) rootView.findViewById(R.id.tre);
		TextView besttxt = (TextView) rootView.findViewById(R.id.userviewtxt);
		TextView information = (TextView) rootView.findViewById(R.id.alert);

		Introduction = (RelativeLayout) rootView
				.findViewById(R.id.introductionApp);
		BestItems = (RelativeLayout) rootView.findViewById(R.id.best);
		NewsApp = (RelativeLayout) rootView.findViewById(R.id.newsApp);

		introductiontxt.setTypeface(util.SetFontCasablanca());
		besttxt.setTypeface(util.SetFontCasablanca());
		information.setTypeface(util.SetFontCasablanca());

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
		util.ShowFooterAgahi(getActivity(), false, 0);
		return rootView;
	}

}
