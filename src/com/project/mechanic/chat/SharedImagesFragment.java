package com.project.mechanic.chat;

import java.util.ArrayList;
import java.util.List;

import com.project.mechanic.R;
import com.project.mechanic.chatAdapter.SharedImagesAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class SharedImagesFragment extends Fragment {
	GridView imagesGrid;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View convertView = inflater.inflate(R.layout.fragment_shared_images, null);

		imagesGrid = (GridView) convertView.findViewById(R.id.shareImagesGrid);

		List<Integer> imageLsit = new ArrayList<Integer>();

		imageLsit.add(R.drawable.g1);
		imageLsit.add(R.drawable.g2);
		imageLsit.add(R.drawable.g3);
		imageLsit.add(R.drawable.up1);
		imageLsit.add(R.drawable.on1);
		imageLsit.add(R.drawable.or1);

		imageLsit.add(R.drawable.g1);
		imageLsit.add(R.drawable.g2);
		imageLsit.add(R.drawable.g3);
		imageLsit.add(R.drawable.up1);
		imageLsit.add(R.drawable.on1);
		imageLsit.add(R.drawable.or1);

		SharedImagesAdapter listAdapter = new SharedImagesAdapter(getActivity(), R.layout.row_image_shared, imageLsit);
		imagesGrid.setAdapter(listAdapter);

		return convertView;
	}

}
