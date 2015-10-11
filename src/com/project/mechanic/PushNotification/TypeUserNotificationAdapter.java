package com.project.mechanic.PushNotification;

import java.util.List;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;

public class TypeUserNotificationAdapter extends ArrayAdapter<String> {

	List<String> titleList;
	List<Integer> iconList, numberMember;
	Context context;
	int visibleItem;

	public TypeUserNotificationAdapter(Context context, int textViewResourceId,
			List<String> titleList, List<Integer> iconList,
			List<Integer> numberMember, int visibleItem) {

		super(context, textViewResourceId, titleList);
		this.titleList = titleList;
		this.context = context;
		this.iconList = iconList;
		this.numberMember = numberMember;
		this.visibleItem = visibleItem;

	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		if (convertView == null) {

			LayoutInflater myInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			convertView = myInflater.inflate(R.layout.row_type_user, parent,
					false);
		}
		TextView title = (TextView) convertView.findViewById(R.id.title);
		TextView number = (TextView) convertView
				.findViewById(R.id.numberMember);
		ImageView icon = (ImageView) convertView.findViewById(R.id.iconMember);

		title.setText(titleList.get(position));
		number.setText(numberMember.get(position) + "");
		icon.setBackgroundResource(iconList.get(position));

		if (visibleItem == 1 && position == 1) {
			convertView.setVisibility(View.GONE);

		}
		if (visibleItem == 1 && position == 2) {
			convertView.setVisibility(View.GONE);

		}
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (position == 2) {
					SelectUserFragment fr = new SelectUserFragment();

					FragmentTransaction trans = ((MainActivity) context)
							.getSupportFragmentManager().beginTransaction();

					trans.replace(R.id.content_frame, fr);

					trans.addToBackStack(null);
					trans.commit();
				}

			}
		});

		return convertView;
	}
}
