package com.project.mechanic.chat;

import java.util.ArrayList;
import java.util.List;

import com.project.mechanic.R;
import com.project.mechanic.StaticValues;
import com.project.mechanic.utility.Utility;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.PopupMenu.OnMenuItemClickListener;

public class InformationChannelMemberFragment extends Fragment {
	ListView memberList;
	View convertView;
	List<String> menuItems;
	Utility util;

	static final String copyLable = "کپی ";
	static final String shareLable = "اشتراک گذاری";

	TextView descriptionChannel, linkChannel;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		convertView = inflater.inflate(R.layout.fragment_member_channel, null);
		util = new Utility(getActivity());

		descriptionChannel = (TextView) convertView.findViewById(R.id.descriptionChannel);
		linkChannel = (TextView) convertView.findViewById(R.id.linkChannel);

		LinearLayout descriptionLink = (LinearLayout) convertView.findViewById(R.id.descriptionLink);

		RelativeLayout link = (RelativeLayout) convertView.findViewById(R.id.link);

		descriptionLink.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				menuItems = new ArrayList<String>();
				menuItems.clear();

				menuItems.add(copyLable);
				menuItems.add(shareLable);

				final PopupMenu popupMenu = util.ShowPopupMenu(menuItems, v);
				popupMenu.show();

				popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {

					@Override
					public boolean onMenuItemClick(MenuItem menuItem) {

						String itemSelected = menuItem.getTitle() + "";

						if (itemSelected.equals(copyLable)) {

							util.CopyToClipboard(descriptionChannel.getText().toString());

						}

						if (itemSelected.equals(shareLable)) {
							Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
							sharingIntent.setType("text/plain");
							sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, descriptionChannel.getText().toString());
							getActivity().startActivity(Intent.createChooser(sharingIntent, "اشتراک  از طریق"));
						}

						return false;
					}
				});

			}
		});

		link.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				menuItems = new ArrayList<String>();
				menuItems.clear();

				menuItems.add(copyLable);
				menuItems.add(shareLable);

				final PopupMenu popupMenu = util.ShowPopupMenu(menuItems, v);
				popupMenu.show();
				
				
				popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {

					@Override
					public boolean onMenuItemClick(MenuItem menuItem) {

						String itemSelected = menuItem.getTitle() + "";

						if (itemSelected.equals(copyLable)) {

							util.CopyToClipboard(linkChannel.getText().toString());

						}

						if (itemSelected.equals(shareLable)) {
							Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
							sharingIntent.setType("text/plain");
							sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, linkChannel.getText().toString());
							getActivity().startActivity(Intent.createChooser(sharingIntent, "اشتراک  از طریق"));
						}

						return false;
					}
				});

				
				
			}
		});

		return convertView;
	}

}
