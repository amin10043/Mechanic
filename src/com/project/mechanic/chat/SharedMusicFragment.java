package com.project.mechanic.chat;

import java.util.ArrayList;
import java.util.List;

import com.project.mechanic.R;
import com.project.mechanic.chatAdapter.SharedMusicAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class SharedMusicFragment extends Fragment {

	ListView musicList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View convertView = inflater.inflate(R.layout.fragment_shared_music, null);

		musicList = (ListView) convertView.findViewById(R.id.lstComment);

		List<String> nameMusic = new ArrayList<String>();
		List<String> sizeMusic = new ArrayList<String>();

		nameMusic.add("جاده");
		nameMusic.add("دنبالش میرم");
		nameMusic.add("سکوت");
		nameMusic.add("قاصدک");
		nameMusic.add("دروغه");

		sizeMusic.add("5");
		sizeMusic.add("4");
		sizeMusic.add("5");
		sizeMusic.add("4");
		sizeMusic.add("3");

		SharedMusicAdapter listAdapter = new SharedMusicAdapter(getActivity(), R.layout.row_music_shared, nameMusic,
				sizeMusic);

		musicList.setAdapter(listAdapter);

		return convertView;
	}

}
