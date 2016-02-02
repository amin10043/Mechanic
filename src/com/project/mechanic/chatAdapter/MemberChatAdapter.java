package com.project.mechanic.chatAdapter;

import java.util.ArrayList;
import java.util.List;

import com.project.mechanic.R;
import com.project.mechanic.utility.Utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import android.widget.TextView;

public class MemberChatAdapter extends ArrayAdapter<String> {
	Context context;
	List<String> itemsList;
	List<String> menuItems;
	Utility util;

	static final String chatLable = "ê› êÊ";
	static final String removeLable = "Õ–› «“ ê—ÊÂ";

	public MemberChatAdapter(Context context, int resource, List<String> itemsList) {
		super(context, resource, itemsList);
		this.context = context;
		this.itemsList = itemsList;
		util = new Utility(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		convertView = inflater.inflate(R.layout.row_member, null);

		TextView nameMember = (TextView) convertView.findViewById(R.id.Rowobjecttxt);

		nameMember.setText(itemsList.get(position));

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				menuItems = new ArrayList<String>();
				menuItems.clear();

				menuItems.add(chatLable);
				menuItems.add(removeLable);

				final PopupMenu popupMenu = util.ShowPopupMenu(menuItems, v);
				popupMenu.show();
			}
		});

		return convertView;

	}

}
