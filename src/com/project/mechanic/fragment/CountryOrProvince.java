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
import com.project.mechanic.adapter.ProvinceSelection;

public class CountryOrProvince extends Fragment {
	RelativeLayout countryItem, cityItem, allUser, allView, allLike;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View rootView = inflater.inflate(R.layout.fragment_country_or_province,
				null);

		countryItem = (RelativeLayout) rootView.findViewById(R.id.countryItem);
		cityItem = (RelativeLayout) rootView.findViewById(R.id.cityItem);
		allUser = (RelativeLayout) rootView.findViewById(R.id.allUser);
		allView = (RelativeLayout) rootView.findViewById(R.id.viewUsers);
		allLike = (RelativeLayout) rootView.findViewById(R.id.followUsers);

		countryItem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				cityItem.setVisibility(View.GONE);

				FragmentTransaction trans = ((MainActivity) getActivity())
						.getSupportFragmentManager().beginTransaction();

				trans.replace(R.id.content_frame, new SelectUserFragment());
				Bundle bundle = new Bundle();
				bundle.putString("CountryKey", "country");
				trans.addToBackStack(null);
				trans.commit();
			}
		});

		cityItem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				countryItem.setVisibility(View.GONE);
				cityItem.setVisibility(View.GONE);

				allUser.setVisibility(View.VISIBLE);

				allView.setVisibility(View.VISIBLE);
				allLike.setVisibility(View.VISIBLE);

				
				FragmentTransaction trans = ((MainActivity) getActivity())
						.getSupportFragmentManager().beginTransaction();

				trans.replace(R.id.content_frame, new ProvinceSelection());
				
				trans.addToBackStack(null);
				trans.commit();
			}
		});
		allLike.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				FragmentTransaction trans = ((MainActivity) getActivity())
						.getSupportFragmentManager().beginTransaction();

				trans.replace(R.id.content_frame, new SelectUserFragment());
				Bundle bundle = new Bundle();
				bundle.putString("CityKey", "city");
				trans.addToBackStack(null);
				trans.commit();
			}
		});

		return rootView;
	}

}
