package com.project.mechanic.chatAdapter;

import java.util.List;

import com.project.mechanic.R;
import com.project.mechanic.StaticValues;
import com.project.mechanic.chat.NewChannelFragment;
import com.project.mechanic.chat.NewGroupFragment;
import com.project.mechanic.chat.ContactChatFragment;
import com.project.mechanic.chat.TabHostChatType;
import com.project.mechanic.utility.Utility;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ChatMenuAdapter extends ArrayAdapter<String> {
	Context context;
	List<String> items;
	Utility util;

	public ChatMenuAdapter(Context context, int resource, List<String> items) {
		super(context, resource, items);
		this.context = context;
		this.items = items;
		util = new Utility(context);

	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(R.layout.row_menu_chat, null);

		TextView nameItem = (TextView) convertView.findViewById(R.id.RowCitytxt);

		nameItem.setText(items.get(position));
		nameItem.setTypeface(util.SetFontCasablanca());

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (position == 0) {
					Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
					sharingIntent.setType("text/plain");
					sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, StaticValues.LinkSoftwateChat);
					context.startActivity(Intent.createChooser(sharingIntent, "اشتراک  نرم افزار از طریق"));
				}
				if (position == 1) {

					FragmentTransaction trans = ((TabHostChatType) context).getSupportFragmentManager()
							.beginTransaction();
					trans.addToBackStack(null);
					Fragment move = new NewGroupFragment();
					trans.replace(R.id.content_frame, move);
					trans.commit();

				}
				if (position == 2) {

					FragmentTransaction trans = ((TabHostChatType) context).getSupportFragmentManager()
							.beginTransaction();
					trans.addToBackStack(null);
					Fragment move = new NewChannelFragment();
					trans.replace(R.id.content_frame, move);
					trans.commit();

				}

				if (position == 4) {

					FragmentTransaction trans = ((TabHostChatType) context).getSupportFragmentManager()
							.beginTransaction();
					trans.addToBackStack(null);
					Fragment move = new ContactChatFragment();
					trans.replace(R.id.content_frame, move);
					trans.commit();

				}

			}
		});
		return convertView;

	}

}
