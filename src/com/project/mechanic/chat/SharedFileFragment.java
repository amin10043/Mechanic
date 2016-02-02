package com.project.mechanic.chat;

import java.util.ArrayList;
import java.util.List;

import com.project.mechanic.R;
import com.project.mechanic.chatAdapter.SharedFileAdapter;
import com.project.mechanic.chatAdapter.SharedMusicAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class SharedFileFragment extends Fragment {

	ListView fileList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View convertView = inflater.inflate(R.layout.fragment_shared_music, null);

		fileList = (ListView) convertView.findViewById(R.id.lstComment);

		List<String> nameFile = new ArrayList<String>();
		List<String> sizeFile = new ArrayList<String>();

		nameFile.add("نرم  افزار مکانیکال");
		nameFile.add("فایل 1");
		nameFile.add("فایل 2");
		nameFile.add("فایل 3");

		sizeFile.add("4.5");
		sizeFile.add("2.7");
		sizeFile.add("3.1");
		sizeFile.add("4.4");

		SharedFileAdapter listAdapter = new SharedFileAdapter(getActivity(), R.layout.row_music_shared, nameFile,
				sizeFile);

		fileList.setAdapter(listAdapter);

		return convertView;
	}

}
