package com.project.mechanic.chat;

import java.util.ArrayList;
import java.util.List;

import com.project.mechanic.R;
import com.project.mechanic.chatAdapter.MemberChatAdapter;
import com.project.mechanic.utility.Utility;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.PopupMenu.OnMenuItemClickListener;

public class InformationGroupMemberFragment extends Fragment {

	View convertView, header;
	ListView memberGroup;
	Utility util;

	static final String imageLable = "تصاویر";
	static final String videoLable = "فیلم";
	static final String musicLable = "موسیقی";
	static final String fileLable = "فایل";
	static final String linkaLble = "لینک";

	List<String> menuItems;

	RelativeLayout addMember, shareItems;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		convertView = inflater.inflate(R.layout.fragment_information_group_member, null);
		header = getActivity().getLayoutInflater().inflate(R.layout.header_information_group_member, null);

		init();
		memberGroup.addHeaderView(header);

		fillListView();
		setOnClick();
		return convertView;
	}

	private void init() {

		util = new Utility(getActivity());
		memberGroup = (ListView) convertView.findViewById(R.id.lstComment);

		addMember = (RelativeLayout) header.findViewById(R.id.addmember);
		shareItems = (RelativeLayout) header.findViewById(R.id.shareItems);

	}

	private void fillListView() {

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
		memberGroup.setAdapter(listAdapter);

	}

	private void setOnClick() {
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

		addMember.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
//				Toast.makeText(getActivity(), "باید ادمین اجازه دهد", 0).show();
			}
		});
	}
}
