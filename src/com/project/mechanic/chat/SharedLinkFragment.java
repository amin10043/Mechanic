package com.project.mechanic.chat;

import java.util.ArrayList;
import java.util.List;

import com.project.mechanic.R;
import com.project.mechanic.chatAdapter.SharedLinkAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class SharedLinkFragment extends Fragment {

	ListView linkList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View convertView = inflater.inflate(R.layout.fragment_shared_link, null);

		linkList = (ListView) convertView.findViewById(R.id.lstComment);

		List<String> description = new ArrayList<String>();
		List<String> linkAddress = new ArrayList<String>();
		List<Integer> imageLink = new ArrayList<Integer>();

		description.add("لینک1");
		description.add("لینک2");
		description.add("لینک3");
		description.add("لینک4");
		description.add("لینک5");
		description.add("لینک6");

		linkAddress.add("www.mechanical.ir/link1");
		linkAddress.add("www.mechanical.ir/link2");
		linkAddress.add("www.mechanical.ir/link3");
		linkAddress.add("www.mechanical.ir/link4");
		linkAddress.add("www.mechanical.ir/link5");
		linkAddress.add("www.mechanical.ir/link6");

		imageLink.add(R.drawable.i1);
		imageLink.add(R.drawable.i2);
		imageLink.add(R.drawable.i3);
		imageLink.add(R.drawable.i4);
		imageLink.add(R.drawable.i5);
		imageLink.add(R.drawable.i6);

		SharedLinkAdapter listAdapter = new SharedLinkAdapter(getActivity(), R.layout.row_link_shared, description,
				linkAddress, imageLink);

		linkList.setAdapter(listAdapter);

		return convertView;
	}

}
