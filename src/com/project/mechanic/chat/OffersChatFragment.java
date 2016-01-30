package com.project.mechanic.chat;

import java.util.ArrayList;
import java.util.List;

import com.project.mechanic.R;
import com.project.mechanic.StaticValues;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class OffersChatFragment extends Fragment {
	Spinner subjectSpinner;
	ListView itemList;
	static final String selectSubject = "انتخاب موضوع";

	static final String siasiLable = "سیاسی";
	static final String ejtemaeiLable = "اجتماعی";
	static final String farhangiLable = "فرهنگی";
	static final String sportLable = "ورزشی";

	static final String artLable = "هنری";
	static final String businnesLable = "کسب و کار";
	static final String maroofLable = "چهره سرشناس";
	static final String mazhabiLable = "مذهبی";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View convertView = inflater.inflate(R.layout.fragment_offers_chat, null);

		subjectSpinner = (Spinner) convertView.findViewById(R.id.spinnerSubject);
		itemList = (ListView) convertView.findViewById(R.id.lstComment);

		fillSpinner();

		subjectSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {

				String item = (String) subjectSpinner.getSelectedItem();

				if (!item.equals(selectSubject)) {

					int id = -1;

					if (item.equals(siasiLable))
						id = 1;

					if (item.equals(ejtemaeiLable))
						id = 2;

					if (item.equals(farhangiLable))
						id = 3;

					if (item.equals(sportLable))
						id = 4;

					if (item.equals(artLable))
						id = 5;

					if (item.equals(businnesLable))
						id = 6;

					if (item.equals(maroofLable))
						id = 7;

					if (item.equals(mazhabiLable))
						id = 8;

					fillListView(id);

				} else {
					Toast.makeText(getActivity(), "انتخاب موضوع اجبازی است", 0).show();
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

		return convertView;
	}

	private void fillSpinner() {

		List<String> subjectName = new ArrayList<String>();

		subjectName.add(selectSubject);

		subjectName.add(siasiLable);
		subjectName.add(ejtemaeiLable);
		subjectName.add(farhangiLable);

		subjectName.add(sportLable);
		subjectName.add(artLable);
		subjectName.add(businnesLable);

		subjectName.add(maroofLable);
		subjectName.add(mazhabiLable);

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,
				subjectName);

		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		subjectSpinner.setAdapter(dataAdapter);
	}

	private void fillListView(int id) {

		ArrayList<String> childsItem = new ArrayList<String>();
		switch (id) {
		case 1: {
			childsItem = new ArrayList<String>();

			childsItem.add("اصول گرا");
			childsItem.add("اصلاح طلب");

			break;
		}
		case 2: {

			childsItem = new ArrayList<String>();

			childsItem.add("اجتماعی 1");
			childsItem.add("اجتماعی 2");
			childsItem.add("اجتماعی 3");

			break;
		}
		case 3: {

			childsItem = new ArrayList<String>();

			childsItem.add("فرهنگی 1");
			childsItem.add("فرهنگی 2");
			childsItem.add("فرهنگی 3");

			break;
		}
		case 4: {
			childsItem = new ArrayList<String>();

			childsItem.add("طرفداری");
			childsItem.add("ورزش 3");
			childsItem.add("نود");

			childsItem.add("کانون هواداران رئال مادرید");
			childsItem.add("کانون هواداران بارسلونا");
			childsItem.add("کانون هواداران بایرن مونیخ");

			childsItem.add("کمربند سیاه");

			break;
		}
		case 5: {

			childsItem = new ArrayList<String>();

			childsItem.add("خانه سینما");
			childsItem.add("موسیقی");
			childsItem.add("تئاتر");

			break;
		}
		case 6: {
			childsItem = new ArrayList<String>();

			childsItem.add("کسب و کار 1");
			childsItem.add("کسب و کار 2");
			childsItem.add("کسب و کار 3");

			break;
		}
		case 7: {
			childsItem = new ArrayList<String>();

			childsItem.add("پرویز پرستویی");
			childsItem.add("شهاب حسینی");
			childsItem.add("پارسا پیروزفر");
			break;
		}
		case 8: {
			childsItem = new ArrayList<String>();

			childsItem.add("مذهبی 1");
			childsItem.add("مذهبی 2");
			childsItem.add("مذهبی 3");
			break;
		}

		default:
			break;
		}

		ChatItemAdapter ListAdapter = new ChatItemAdapter(getActivity(), R.layout.row_offers_chat, childsItem,
				StaticValues.TypeOffersChat);
		itemList.setAdapter(ListAdapter);

	}
}
