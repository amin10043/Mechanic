package com.project.mechanic.fragment;

import java.util.ArrayList;
import java.util.List;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.adapter.ExecutertypeListAdapter;
import com.project.mechanic.entity.City;
import com.project.mechanic.entity.Executertype;
import com.project.mechanic.entity.Province;
import com.project.mechanic.model.DataBaseAdapter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.ListView;

public class ExecutertypeFragment extends Fragment {

	DataBaseAdapter adapter;
	int cityId;
	int mainObjectId;
	int advisorId;

	public ExecutertypeFragment(int mainObjectId, int cityId, int advisorId) {

		this.mainObjectId = mainObjectId;
		this.cityId = cityId;
		this.advisorId = advisorId;

	}

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		((MainActivity) getActivity()).setActivityTitle(R.string.Executertyper);

		View view = inflater.inflate(R.layout.fragment_executertype, null);

		adapter = new DataBaseAdapter(getActivity());

		adapter.open();
		ArrayList<Executertype> mylist = adapter.getAllExecutertypeName();
		adapter.close();

		// cityId = Integer.valueOf(getArguments().getString("cityId"));

		ListView lstExecutertype = (ListView) view.findViewById(R.id.listVexecutertype);
		ExecutertypeListAdapter ListAdapter = new ExecutertypeListAdapter(getActivity(), R.layout.row_executertype,
				mylist, cityId, mainObjectId, advisorId);

		lstExecutertype.setAdapter(ListAdapter);

		return view;
	}

	@Override
	public void onResume() {
		getView().setFocusableInTouchMode(true);
		getView().requestFocus();

		getView().setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View arg0, int keyCode, KeyEvent event) {
				
				if (event.getAction() == KeyEvent.ACTION_DOWN)

				if (keyCode == KeyEvent.KEYCODE_BACK) {

					if (mainObjectId == 3) {

						FragmentTransaction trans = ((MainActivity) getActivity()).getSupportFragmentManager()
								.beginTransaction();
						AdvisorTypeFragment fragment = new AdvisorTypeFragment(mainObjectId, cityId);

						trans.replace(R.id.content_frame, fragment);

						trans.commit();

					}
					if (mainObjectId == 4) {

						adapter.open();
						City city = adapter.getCityById(cityId);
						Province p = adapter.getProvinceById(city.getProvinceId());
						List<City> allCity = adapter.getCitysByProvinceId(p.getId());
						adapter.close();

						FragmentTransaction trans = ((MainActivity) getActivity()).getSupportFragmentManager()
								.beginTransaction();
						trans.setCustomAnimations(R.anim.push_out_right, R.anim.pull_in_left);

						trans.replace(R.id.content_frame, new City3Fragment(allCity, mainObjectId));
						trans.commit();
					}

					return true;
				}

				return false;
			}
		});
		super.onResume();
	}
}
