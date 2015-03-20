package com.project.mechanic.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.project.mechanic.R;
import com.project.mechanic.adapter.MainListAdapter;
import com.project.mechanic.row_items.RowMain;

public class HomeFragment extends Fragment {
	private Button GotoIntroductionactivitybtn;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_main, null);
		
		 GotoIntroductionactivitybtn = (Button)view.findViewById(R.id.btnTest);
		 GotoIntroductionactivitybtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				

					FragmentTransaction trans = getActivity()
							.getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame, new IntroductionFragment());
					trans.commit();
				}
				
		 });
		 

		List<RowMain> mylist = new ArrayList<RowMain>();

		RowMain p1 = new RowMain();
		RowMain p2 = new RowMain();
		RowMain p3 = new RowMain();
		RowMain p4 = new RowMain();
		RowMain p5 = new RowMain();
		RowMain p6 = new RowMain();
		RowMain p7 = new RowMain();
		RowMain p8 = new RowMain();

		p1.setName(" Ê·Ìœ ò‰‰œê«‰ œ«Œ·Ì-»—‰œÂ«Ì Ê«—œ« Ì-‰„«Ì‰œêÌ Â«-œ›« — ›—Ê‘");
		p1.setNoti("«”");
		p2.setName("›—Ê‘ê«ÂÂ« Ê‘—ò  Â«Ì ⁄—÷Â ò‰‰œÂ „Õ’Ê·« ");
		p2.setNoti("«”");
		p3.setName("„‘«Ê—«‰-‰«Ÿ—«‰ Ê‘—ò Â«Ì „Ã—Ì  «”Ì”«  „ò«‰ÌòÌ");
		p3.setNoti("«”");
		p4.setName("«‘Œ«’ ÕﬁÌﬁÌ(«ÌÀ«—ê—«‰) « ”Ì”«  „ò«‰ÌòÌ");
		p4.setNoti("«”");
		p5.setName("‰‘—Ì« -„ﬁ«·«  Ê«Œ»«—  «”Ì”«  „ò«‰ÌòÌ");
		p5.setNoti("«”");
		p6.setName("¬êÂÌ Ê »·Ì€« ");
		p6.setNoti("«”");
		p7.setName(" «·«— ê› êÊ");
		p7.setNoti("«”");
		p8.setName("»Ì·»Ê—œ „ Õ—ò");

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
