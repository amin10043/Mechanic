package com.project.mechanic.chat;

import java.util.ArrayList;
import java.util.List;

import com.project.mechanic.R;
import com.project.mechanic.StaticValues;
import com.project.mechanic.chatAdapter.MemberChatAdapter;
import com.project.mechanic.utility.Utility;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.RelativeLayout;

public class InformationGroupAdminFragment extends Fragment {

	ListView memberListView;
	View convertView, header;
	List<String> menuItems;
	Utility util;
	int type;
	RelativeLayout listAdmin, addMember, shareItems;

	public InformationGroupAdminFragment(int type) {

		this.type = type;
	}

	static final String copyLable = "کپی لینک";
	static final String EditLable = "تغیر لینک";
	static final String shareLable = "اشتراک گذاری لینک توسط کاربران";

	static final String imageLable = "تصاویر";
	static final String videoLable = "فیلم";
	static final String musicLable = "موسیقی";
	static final String fileLable = "فایل";
	static final String linkaLble = "لینک";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		util = new Utility(getActivity());
		convertView = inflater.inflate(R.layout.fragment_information_chat, null);
		header = getActivity().getLayoutInflater().inflate(R.layout.header_information_chat, null);
		listAdmin = (RelativeLayout) header.findViewById(R.id.listAdmin);
		addMember = (RelativeLayout) header.findViewById(R.id.addmember);
		shareItems = (RelativeLayout) header.findViewById(R.id.shareItems);

		switch (type) {
		case StaticValues.TypeInformationGroupAdminChat: {
			viewOfAdminSettings();
			break;

		}

		default:
			break;
		}
		return convertView;
	}

	private void setonCliclItems() {

		RelativeLayout linkGroup = (RelativeLayout) header.findViewById(R.id.linkGroup);
		linkGroup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				menuItems = new ArrayList<String>();
				menuItems.clear();

				menuItems.add(copyLable);
				menuItems.add(EditLable);
				menuItems.add(shareLable);

				final PopupMenu popupMenu = util.ShowPopupMenu(menuItems, v);
				popupMenu.show();
			}
		});

		listAdmin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				FragmentTransaction trans = ((TabHostChatType) getActivity()).getSupportFragmentManager()
						.beginTransaction();
				trans.addToBackStack(null);
				Fragment move = new SetAdminNoGroup();
				trans.replace(R.id.content_frame, move);
				trans.commit();

			}
		});

		addMember.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				FragmentTransaction trans = ((TabHostChatType) getActivity()).getSupportFragmentManager()
						.beginTransaction();
				trans.addToBackStack(null);
				Fragment move = new AddMemberFragment();
				trans.replace(R.id.content_frame, move);
				trans.commit();
			}
		});

		shareItems.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				menuItems = new ArrayList<String>();
				menuItems.clear();

				menuItems.add(imageLable);
				menuItems.add(videoLable);
				menuItems.add(musicLable);
				menuItems.add(fileLable);
				menuItems.add(linkaLble);

				final PopupMenu popupMenu = util.ShowPopupMenu(menuItems, v);
				popupMenu.show();

				popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {

					@Override
					public boolean onMenuItemClick(MenuItem menuItem) {

						String itemSelected = menuItem.getTitle() + "";

						if (itemSelected.equals(imageLable)) {

							FragmentTransaction trans = ((TabHostChatType) getActivity()).getSupportFragmentManager()
									.beginTransaction();
							trans.addToBackStack(null);
							Fragment move = new SharedImagesFragment();
							trans.replace(R.id.content_frame, move);
							trans.commit();
						}

						if (itemSelected.equals(videoLable)) {

							FragmentTransaction trans = ((TabHostChatType) getActivity()).getSupportFragmentManager()
									.beginTransaction();
							trans.addToBackStack(null);
							Fragment move = new SharedVideoFragment();
							trans.replace(R.id.content_frame, move);
							trans.commit();
						}

						if (itemSelected.equals(musicLable)) {

							FragmentTransaction trans = ((TabHostChatType) getActivity()).getSupportFragmentManager()
									.beginTransaction();
							trans.addToBackStack(null);
							Fragment move = new SharedMusicFragment();
							trans.replace(R.id.content_frame, move);
							trans.commit();
						}

						if (itemSelected.equals(fileLable)) {

							FragmentTransaction trans = ((TabHostChatType) getActivity()).getSupportFragmentManager()
									.beginTransaction();
							trans.addToBackStack(null);
							Fragment move = new SharedFileFragment();
							trans.replace(R.id.content_frame, move);
							trans.commit();
						}
						
						if (itemSelected.equals(linkaLble)) {

							FragmentTransaction trans = ((TabHostChatType) getActivity()).getSupportFragmentManager()
									.beginTransaction();
							trans.addToBackStack(null);
							Fragment move = new SharedLinkFragment();
							trans.replace(R.id.content_frame, move);
							trans.commit();
						}

						return false;
					}
				});
			}
		});

	}

	public void viewOfAdminSettings() {
		memberListView = (ListView) convertView.findViewById(R.id.lstComment);
		memberListView.addHeaderView(header);
		ArrayList<String> childFavorite = new ArrayList<String>();

		childFavorite.add("محمد ارزمان زاده");
		childFavorite.add("مهندس حسینی");
		childFavorite.add("مهندس هامونی");
		childFavorite.add("مهدی ذبیحی");
		childFavorite.add("مسعود محمودزاده");
		childFavorite.add("محمد اسماعیلی");
		childFavorite.add("اسماعیل شعبانی");
		childFavorite.add("آیدین غیبی");
		childFavorite.add("داوود امینی");

		MemberChatAdapter listAdapter = new MemberChatAdapter(getActivity(), R.layout.row_member, childFavorite);
		memberListView.setAdapter(listAdapter);

		setonCliclItems();
	}
}
