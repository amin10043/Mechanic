package com.project.mechanic.chatAdapter;

import java.util.List;

import com.project.mechanic.R;
import com.project.mechanic.utility.Utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SharedLinkAdapter extends ArrayAdapter<String> {

	Context context;
	List<String> descriptionList, linkList;;
	List<Integer> iconLink;
	Utility util;

	public SharedLinkAdapter(Context context, int resource, List<String> nameMusic, List<String> size,
			List<Integer> iconLink) {
		super(context, resource, nameMusic);

		this.context = context;
		this.descriptionList = nameMusic;
		this.linkList = size;
		util = new Utility(context);
		this.iconLink = iconLink;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (position == 3) {
			convertView = inflater.inflate(R.layout.row_date_divider, null);

		} else {
			convertView = inflater.inflate(R.layout.row_link_shared, null);

			TextView description = (TextView) convertView.findViewById(R.id.description);
			TextView linkAddress = (TextView) convertView.findViewById(R.id.link);
			ImageView image = (ImageView) convertView.findViewById(R.id.imageMusic);

			RelativeLayout layout = (RelativeLayout) convertView.findViewById(R.id.imageLayout);

			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(layout.getLayoutParams());

			lp.width = util.getScreenwidth() / 5;
			lp.height = util.getScreenwidth() / 5;
			lp.addRule(RelativeLayout.CENTER_VERTICAL);

			// TextView dateMusic = (TextView)
			// convertView.findViewById(R.id.datemusic);

			description.setText(descriptionList.get(position));
			linkAddress.setText(linkList.get(position));
			image.setImageResource(iconLink.get(position));
			image.setLayoutParams(lp);

			description.setTypeface(util.SetFontCasablanca());
		}
		return convertView;
	}

}
