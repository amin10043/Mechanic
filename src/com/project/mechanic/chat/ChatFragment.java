package com.project.mechanic.chat;

import java.util.ArrayList;
import java.util.List;

import com.project.mechanic.R;
import com.project.mechanic.StaticValues;
import com.project.mechanic.utility.Utility;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class ChatFragment extends Fragment {
	Utility util;
	Activity activity;
	int type;
	List<String> menuItems;

	static final String adminGroupLable = "مدیر گروه";
	static final String memberGroupLable = "عضو گروه";
	static final String adminChannelLable = "مدیر تالار";
	static final String memberChannelLable = "عضو تالار";

	public ChatFragment(Activity activity, int type) {

		this.activity = activity;
		this.type = type;
	}

	// public ChatFragment(int type) {
	//
	// this.type = type;
	// }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View convertView = inflater.inflate(R.layout.fragment_chat, null);
		util = new Utility(getActivity());

		RelativeLayout settings = (RelativeLayout) convertView.findViewById(R.id.settings);

		LinearLayout information = (LinearLayout) convertView.findViewById(R.id.infoItem);

		settings.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Toast.makeText(activity, "this is a test", 0).show();

			}
		});

		information.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				List<String> menuItems = new ArrayList<String>();
				menuItems.clear();

				menuItems.add(adminGroupLable);
				menuItems.add(memberGroupLable);
				menuItems.add(adminChannelLable);
				menuItems.add(memberChannelLable);

				final PopupMenu popupMenu = util.ShowPopupMenu(menuItems, v);
				popupMenu.show();

				popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {

					@Override
					public boolean onMenuItemClick(MenuItem menuItem) {

						String itemSelected = menuItem.getTitle() + "";

						if (itemSelected.equals(adminGroupLable)) {

							if (type == StaticValues.TypeInformationGroupAdminChat) {

								FragmentTransaction trans = ((TabHostChatType) getActivity())
										.getSupportFragmentManager().beginTransaction();
								trans.addToBackStack(null);
								Fragment move = new InformationGroupAdminFragment(
										StaticValues.TypeInformationGroupAdminChat);
								trans.replace(R.id.content_frame, move);
								trans.commit();
							}
						}

						if (itemSelected.equals(memberGroupLable)) {

							FragmentTransaction trans = ((TabHostChatType) getActivity()).getSupportFragmentManager()
									.beginTransaction();
							trans.addToBackStack(null);
							Fragment move = new InformationGroupMemberFragment();
							trans.replace(R.id.content_frame, move);
							trans.commit();
						}

						if (itemSelected.equals(adminChannelLable)) {

							FragmentTransaction trans = ((TabHostChatType) getActivity()).getSupportFragmentManager()
									.beginTransaction();
							trans.addToBackStack(null);
							Fragment move = new InformationChannelAdminFragment();
							trans.replace(R.id.content_frame, move);
							trans.commit();
						}

						if (itemSelected.equals(memberChannelLable)) {

							FragmentTransaction trans = ((TabHostChatType) getActivity()).getSupportFragmentManager()
									.beginTransaction();
							trans.addToBackStack(null);
							Fragment move = new InformationChannelMemberFragment();
							trans.replace(R.id.content_frame, move);
							trans.commit();
						}

						return false;
					}
				});

				//

			}
		});

		ImageView back = (ImageView) convertView.findViewById(R.id.back);

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

			}
		});
		
//		util.ShowFooterAgahi(getActivity(), true, 10);
//		//
//		 ImageView[] SendImages = util.inputCommentAndPickFile(getActivity());
		// ImageView send = SendImages[0];
		// ImageView pick = SendImages[1];
		//
		// if (getActivity() != null) {
		// send = util.ShowFooterAgahi(getActivity(), true, -1);
		//
		// send.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		//
		// }
		// });
		//
		// }
		//
		return convertView;
		// }
		//
		// private void InputComment() {
		//
	}

}
