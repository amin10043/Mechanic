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
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class SetAdminNoGroup extends Fragment {
	// ListView listMember;
	Utility util;
	View header;
	// Switch allowMember;
	TextView message, lable;
	RadioGroup radioGroup;

	static final String messageActiveLable = "همه اعضا میتوانند عضو جدیدی اضافه کنند و همچنین نام  و عکس گروه را تغییر دهند ";
	static final String messageDeactiveLable = "فقط مدیر میتواند عضوی را اضافه و حذف کند همچنین نام و عکس گروه را تغییر دهد";

	static final String creatorRadioBtn = "محمد ارزمان زاده";
	static final String allMemberRadioBtn = "همه اعضای گروه";
	static final String specialMember = "مدیران منتخب";

	RadioButton rb1, rb2, rb3;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		// convertView = inflater.inflate(R.layout.fragment_create_group_chat,
		// null);
		header = getActivity().getLayoutInflater().inflate(R.layout.header_admin, null);
		util = new Utility(getActivity());

		init();

		// fillListView();

		setFont();

		// checkIsShow();

		checkItems();

		radioCheck();

		return header;

	}

	// private void fillListView() {
	//
	// ArrayList<String> childFavorite = new ArrayList<String>();
	//
	// childFavorite.add("محمد ارزمان زاده");
	// childFavorite.add("مهندس حسینی");
	// childFavorite.add("مهندس هامونی");
	// childFavorite.add("مهدی ذبیحی");
	// childFavorite.add("مسعود محمودزاده");
	// childFavorite.add("محمد اسماعیلی");
	// childFavorite.add("اسماعیل شعبانی");
	// childFavorite.add("آیدین غیبی");
	// childFavorite.add("داوود امینی");
	//
	// SelectMemberAdminAdapter listAdapter = new
	// SelectMemberAdminAdapter(getActivity(),
	// R.layout.row_select_member_admin, childFavorite);
	// listMember.setAdapter(listAdapter);
	// }

	private void init() {

		// listMember = (ListView) header.findViewById(R.id.lstComment);
		// allowMember = (Switch) header.findViewById(R.id.switch1);
		message = (TextView) header.findViewById(R.id.message);

		radioGroup = (RadioGroup) header.findViewById(R.id.rbb);

		rb1 = (RadioButton) header.findViewById(R.id.r1);
		rb2 = (RadioButton) header.findViewById(R.id.r2);
		rb3 = (RadioButton) header.findViewById(R.id.r3);

		rb1.setText(creatorRadioBtn);
		rb2.setText(allMemberRadioBtn);
		rb3.setText(specialMember);
		lable = (TextView) header.findViewById(R.id.lablelavle);

		// listMember.addHeaderView(header);

		ImageView seprate = (ImageView) header.findViewById(R.id.seprate);

		seprate.setVisibility(View.GONE);

	}

	private void setFont() {

		message.setTypeface(util.SetFontIranSans());

		lable.setTypeface(util.SetFontCasablanca());
		rb1.setTypeface(util.SetFontCasablanca());
		rb2.setTypeface(util.SetFontCasablanca());
		rb3.setTypeface(util.SetFontCasablanca());

		// allowMember.setTypeface(util.SetFontIranSans());
	}

	// private void checkIsShow() {
	//
	//// boolean isActive = allowMember.isChecked();
	//
	// if (isActive == true) {
	// message.setText(messageActiveLable);
	//
	// } else {
	// message.setText(messageDeactiveLable);
	//
	// }
	// }

	private void checkItems() {

		// allowMember.setOnCheckedChangeListener(new OnCheckedChangeListener()
		// {
		//
		// @Override
		// public void onCheckedChanged(CompoundButton arg0, boolean isCheck) {
		//
		// if (isCheck == true)
		// message.setText(messageActiveLable);
		// else
		// message.setText(messageDeactiveLable);
		//
		// }
		// });
	}

	private void radioCheck() {

		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup rd, int checkedId) {

				RadioButton radio = (RadioButton) header.findViewById(checkedId);

				String title = radio.getText() + "";

				if (title.equals(specialMember)) {

					FragmentTransaction trans = ((TabHostChatType) getActivity()).getSupportFragmentManager()
							.beginTransaction();
					trans.addToBackStack(null);
					Fragment move = new SetAdminMember();
					trans.replace(R.id.content_frame, move);
					trans.commit();

				}

			}
		});

	}
}
