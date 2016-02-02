package com.project.mechanic.chat;

import java.util.ArrayList;
import java.util.List;

import com.project.mechanic.R;
import com.project.mechanic.chatAdapter.SharedImagesAdapter;
import com.project.mechanic.chatAdapter.SharedVideoAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class SharedVideoFragment extends Fragment {

	GridView movieGrid;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View convertView = inflater.inflate(R.layout.fragment_shared_images, null);
		movieGrid = (GridView) convertView.findViewById(R.id.shareImagesGrid);

		List<Integer> imageLsit = new ArrayList<Integer>();
		List<String> duration = new ArrayList<String>();

		imageLsit.add(R.drawable.i1);
		imageLsit.add(R.drawable.i2);
		imageLsit.add(R.drawable.i3);
		imageLsit.add(R.drawable.i4);
		imageLsit.add(R.drawable.i5);
		imageLsit.add(R.drawable.i6);

		imageLsit.add(R.drawable.i1);
		imageLsit.add(R.drawable.i2);
		imageLsit.add(R.drawable.i3);
		imageLsit.add(R.drawable.i4);
		imageLsit.add(R.drawable.i5);
		imageLsit.add(R.drawable.i6);

		duration.add("15:10");
		duration.add("17:40");
		duration.add("11:12");
		duration.add("11:11");
		duration.add("15:12");
		duration.add("20:10");

		duration.add("19:15");
		duration.add("17:17");
		duration.add("14:15");
		duration.add("13:12");
		duration.add("19:11");
		duration.add("12:15");

		SharedVideoAdapter listAdapter = new SharedVideoAdapter(getActivity(), R.layout.row_video_shared, duration,
				imageLsit);

		movieGrid.setAdapter(listAdapter);

		return convertView;
	}
}
