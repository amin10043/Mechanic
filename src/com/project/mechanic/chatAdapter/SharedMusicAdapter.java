package com.project.mechanic.chatAdapter;

import java.util.List;

import com.project.mechanic.R;
import com.project.mechanic.utility.Utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SharedMusicAdapter extends ArrayAdapter<String> {

	Context context;
	List<String> nameList, size;

	Utility util;

	public SharedMusicAdapter(Context context, int resource, List<String> nameMusic, List<String> size) {
		super(context, resource, nameMusic);

		this.context = context;
		this.nameList = nameMusic;
		this.size = size;
		util = new Utility(context);

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(R.layout.row_music_shared, null);

		TextView nameMusic = (TextView) convertView.findViewById(R.id.namemusic);
		TextView sizeMusic = (TextView) convertView.findViewById(R.id.sizeMsusic);
		// TextView dateMusic = (TextView)
		// convertView.findViewById(R.id.datemusic);

		nameMusic.setText(nameList.get(position));
		sizeMusic.setText(size.get(position) + " MB");

		nameMusic.setTypeface(util.SetFontCasablanca());

		return convertView;
	}

}
