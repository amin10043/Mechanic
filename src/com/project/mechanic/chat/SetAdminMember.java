package com.project.mechanic.chat;

import java.util.ArrayList;

import com.project.mechanic.R;
import com.project.mechanic.chatAdapter.SelectMemberAdminAdapter;
import com.project.mechanic.utility.Utility;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;

public class SetAdminMember extends Fragment {

	static final String creatorRadioBtn = "محمد ارزمان زاده";
	static final String allMemberRadioBtn = "همه اعضای گروه";
	static final String specialMember = "مدیران منتخب";

	ListView listMember;
	Utility util;
	View convertView, header;
	Switch allowMember;
	TextView message, lable;
	RadioGroup radioGroup;

	RadioButton rb1, rb2, rb3;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		convertView = inflater.inflate(R.layout.fragment_create_group_chat, null);
		header = getActivity().getLayoutInflater().inflate(R.layout.header_admin, null);
		util = new Utility(getActivity());

		init();

		fillListView();

		setFont();

		radioCheck();

		return convertView;
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

		SelectMemberAdminAdapter listAdapter = new SelectMemberAdminAdapter(getActivity(),
				R.layout.row_select_member_admin, childFavorite);
		listMember.setAdapter(listAdapter);
	}

	private void init() {

		listMember = (ListView) convertView.findViewById(R.id.lstComment);
		allowMember = (Switch) header.findViewById(R.id.switch1);
		message = (TextView) header.findViewById(R.id.message);

		radioGroup = (RadioGroup) header.findViewById(R.id.rbb);

		rb1 = (RadioButton) header.findViewById(R.id.r1);
		rb2 = (RadioButton) header.findViewById(R.id.r2);
		rb3 = (RadioButton) header.findViewById(R.id.r3);

		rb1.setText(creatorRadioBtn);
		rb2.setText(allMemberRadioBtn);
		rb3.setText(specialMember);
		lable = (TextView) header.findViewById(R.id.lablelavle);

		listMember.addHeaderView(header);

		RelativeLayout rl = (RelativeLayout) convertView.findViewById(R.id.hh);
		rl.setVisibility(View.GONE);
		rb3.setChecked(true);
	}

	private void setFont() {

		message.setTypeface(util.SetFontIranSans());

		lable.setTypeface(util.SetFontCasablanca());
		rb1.setTypeface(util.SetFontCasablanca());
		rb2.setTypeface(util.SetFontCasablanca());
		rb3.setTypeface(util.SetFontCasablanca());

		// allowMember.setTypeface(util.SetFontIranSans());
	}

	private void radioCheck() {

		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup rd, int checkedId) {

				RadioButton radio = (RadioButton) header.findViewById(checkedId);

				String title = radio.getText() + "";

				if (title.equals(creatorRadioBtn) || title.equals(allMemberRadioBtn)) {

					FragmentTransaction trans = ((TabHostChatType) getActivity()).getSupportFragmentManager()
							.beginTransaction();
					trans.addToBackStack(null);
					Fragment move = new SetAdminNoGroup();
					trans.replace(R.id.content_frame, move);
					trans.commit();

				}

			}
		});

	}
}
