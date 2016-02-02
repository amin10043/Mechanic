package com.project.mechanic.chat;

import java.util.ArrayList;
import java.util.List;

import com.project.mechanic.R;
import com.project.mechanic.chatAdapter.SelectContactAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class NewChannelFragment extends Fragment {

	ListView memberList;
	Spinner numberMember;

	static final String selectNumberLable = "تعداد";
	static final int Area10K = 10000;
	static final int Area100K = 100000;
	static final int Area200K = 200000;
	static final int Area1M = 1000000;

	static final String num1 = "500";

	RadioButton freeChannel, expireChannel;
	View convertView, header;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		convertView = inflater.inflate(R.layout.fragment_add_channel, null);
		header = getActivity().getLayoutInflater().inflate(R.layout.header_new_channel, null);

		init();

		memberList.addHeaderView(header);

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

		SelectContactAdapter ListAdapter = new SelectContactAdapter(getActivity(), R.layout.row_select_member_admin,
				childFavorite);
		memberList.setAdapter(ListAdapter);

		fillSpinner();
		selectItem();
		return convertView;
	}

	private void fillSpinner() {

//		numberMember.setEnabled(false);

		List<String> nums = new ArrayList<String>();

		nums.add(selectNumberLable);
		nums.add(num1);

		int K1count = 1000;

		while (K1count < Area10K) {

			nums.add(String.valueOf(K1count));
			K1count += 1000;

		}
		int k10count = 10000;
		while (k10count < Area100K) {
			nums.add(String.valueOf(k10count));
			k10count += 5000;
		}
		int k100count = 100000;
		while (k100count < Area200K) {
			nums.add(String.valueOf(k100count));
			k100count += 20000;
		}

		int k200count = 200000;
		while (k200count < Area1M) {
			nums.add(String.valueOf(k200count));
			k200count += 50000;
		}
		nums.add(String.valueOf(Area1M));

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,
				nums);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		numberMember.setAdapter(adapter);
	}

	private void selectItem() {

		numberMember.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {

				String itemSelected = (String) numberMember.getSelectedItem();

				if (!itemSelected.equals(selectNumberLable)) {

					expireChannel.setText(itemSelected);
					expireChannel.setChecked(true);

					Toast.makeText(getActivity(), itemSelected, 0).show();

				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				//

			}
		});
	}

	private void init() {

		numberMember = (Spinner) header.findViewById(R.id.countMember);
		memberList = (ListView) convertView.findViewById(R.id.lstComment);

		freeChannel = (RadioButton) header.findViewById(R.id.r1);
		expireChannel = (RadioButton) header.findViewById(R.id.r2);

	}

}
