package com.project.mechanic.chat;

import java.util.ArrayList;

import com.project.mechanic.R;
import com.project.mechanic.StaticValues;
import com.project.mechanic.chatAdapter.ChatItemAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

public class ContactChatFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View convertView = inflater.inflate(R.layout.fragment_contact_chat, null);
		EditText searchInput = (EditText) convertView.findViewById(R.id.searchInput);
		ListView itemList = (ListView) convertView.findViewById(R.id.lstComment);
		ImageView back = (ImageView) convertView.findViewById(R.id.back);

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

		final ChatItemAdapter ListAdapter = new ChatItemAdapter(getActivity(), R.layout.row_contact_chat, childFavorite,
				StaticValues.TypeContactChat);
		itemList.setAdapter(ListAdapter);

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				FragmentTransaction trans = ((TabHostChatType) getActivity()).getSupportFragmentManager()
						.beginTransaction();
				trans.addToBackStack(null);
				Fragment move = new ChatMenuFragment();
				trans.replace(R.id.content_frame, move);
				trans.commit();

			}
		});

		searchInput.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence text, int arg1, int arg2, int arg3) {
				ListAdapter.getFilter().filter(text);

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub

			}
		});

		return convertView;
	}

}
