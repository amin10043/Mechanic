package com.project.mechanic.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.project.mechanic.R;
import com.project.mechanic.adapter.MainListAdapter;
import com.project.mechanic.entity.ListItem;

public class HomeFragment extends Fragment {
	private Button GotoIntroductionactivitybtn;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_main, null);

		GotoIntroductionactivitybtn = (Button) view.findViewById(R.id.btnTest);
		GotoIntroductionactivitybtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				FragmentTransaction trans = getActivity()
						.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new IntroductionFragment());
				trans.commit();
			}

		});

		List<ListItem> mylist = new ArrayList<ListItem>();

		ListItem p1 = new ListItem();
		ListItem p2 = new ListItem();
		ListItem p3 = new ListItem();
		ListItem p4 = new ListItem();
		ListItem p5 = new ListItem();
		ListItem p6 = new ListItem();
		ListItem p7 = new ListItem();
		ListItem p8 = new ListItem();

		p1.setName("����� ���ϐ�� �����-������� �������-�����ϐ� ��-����� ����");
		// p1.setNoti("��");
		p2.setName("���Ԑ���� ��ј� ��� ���� ����� �������");
		// p2.setNoti("��");
		p3.setName("�������-������ ��ј���� ���� ������� �����");
		// p3.setNoti("��");
		p4.setName("����� �����(����ѐ���)�� ����� �����");
		// p4.setNoti("��");
		p5.setName("������-������ ������ ������� �����");
		// p5.setNoti("��");
		p6.setName("�� ��������");
		// p6.setNoti("��");
		p7.setName("����� ��ʐ�");
		// p7.setNoti("��");
		p8.setName("������� ���ј");

		mylist.add(p1);
		mylist.add(p2);
		mylist.add(p3);
		mylist.add(p4);
		mylist.add(p5);
		mylist.add(p6);
		mylist.add(p7);
		mylist.add(p8);

		ListView lst = (ListView) view.findViewById(R.id.lstMain);
		MainListAdapter ListAdapter = new MainListAdapter(getActivity(),
				R.layout.row_lstv, mylist);

		lst.setAdapter(ListAdapter);

		lst.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				FragmentTransaction trans = getActivity()
						.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new ProvinceFragment());
				trans.commit();
			}
		});

		return view;
	}
}
