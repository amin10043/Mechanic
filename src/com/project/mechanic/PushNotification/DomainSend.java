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
import android.widget.TextView;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.utility.Utility;

@SuppressLint("ValidFragment")
public class DomainSend extends Fragment {

	String type;
	RelativeLayout countryItem, cityItem;
	Utility util;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_domain_send, null);
		
		util = new Utility(getActivity());
		
		TextView lableCountry = (TextView)rootView.findViewById(R.id.RowCitytxt);
		TextView lableCity = (TextView)rootView.findViewById(R.id.RowCitytxtcs);
		
		lableCountry.setTypeface(util.SetFontCasablanca());
		lableCity.setTypeface(util.SetFontCasablanca());


		countryItem = (RelativeLayout) rootView.findViewById(R.id.countryItem);
		cityItem = (RelativeLayout) rootView.findViewById(R.id.cityItem);

		final TextView headerMessage = (TextView) rootView
				.findViewById(R.id.lrt);

		if (type.equals("BirthDay"))
			headerMessage.setVisibility(View.VISIBLE);

		countryItem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (type.equals("BirthDay")) {

					MessageNotification fr = new MessageNotification();

					FragmentTransaction trans = ((MainActivity) getActivity())
							.getSupportFragmentManager().beginTransaction();

					trans.replace(R.id.content_frame, fr);

					trans.addToBackStack(null);
					trans.commit();
				} else {

					TypeUserNotification fr = new TypeUserNotification(type);

					FragmentTransaction trans = ((MainActivity) getActivity())
							.getSupportFragmentManager().beginTransaction();

					trans.replace(R.id.content_frame, fr);

					trans.addToBackStack(null);
					trans.commit();
				}
			}
		});

		cityItem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				FragmentTransaction trans = ((MainActivity) getActivity())
						.getSupportFragmentManager().beginTransaction();

				trans.replace(R.id.content_frame, new ProvinceSelection(type));

				trans.addToBackStack(null);
				trans.commit();
			}
		});

		return rootView;
	}

	public DomainSend(String type) {
		this.type = type;
	}

}
