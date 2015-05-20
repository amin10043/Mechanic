package com.project.mechanic.fragment;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.adapter.PapertitleListAdapter;
import com.project.mechanic.entity.Paper;
import com.project.mechanic.entity.Users;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class TitlepaperFragment extends Fragment {
	private ImageButton addtitle;
	private DialogPaperTitle dialog;
	DataBaseAdapter mdb;
	View view;
	ArrayList<Paper> mylist;
	ListView lst;
	PapertitleListAdapter ListAdapter;
	public static final int DIALOG_FRAGMENT = 1;
	Utility utility;
	Users CurrentUser;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(android.view.LayoutInflater inflater,
			android.view.ViewGroup container, Bundle savedInstanceState) {

		((MainActivity) getActivity()).setActivityTitle(R.string.News);
		view = inflater.inflate(R.layout.fragment_titlepaper, null);
		addtitle = (ImageButton) view.findViewById(R.id.imgBtnAddcmt_CmtFroum);

		mdb = new DataBaseAdapter(getActivity());
		utility = new Utility(getActivity());
		CurrentUser = utility.getCurrentUser();
		mdb.open();
		mylist = mdb.getAllPaper();
		mdb.close();

		addtitle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (CurrentUser == null) {
					Toast.makeText(getActivity(), "ابتدا باید وارد شوید",
							Toast.LENGTH_SHORT).show();
					return;
				} else {
					dialog = new DialogPaperTitle(getActivity(),
							R.layout.dialog_addtitle, TitlepaperFragment.this);
					dialog.show();
				}

			}
		});

		lst = (ListView) view.findViewById(R.id.lstComment);
		ListAdapter = new PapertitleListAdapter(getActivity(),
				R.layout.raw_froumtitle, mylist);
		lst.setAdapter(ListAdapter);

		/*
		 * lst.setOnItemClickListener(new OnItemClickListener() {
		 * 
		 * @Override public void onItemClick(AdapterView<?> arg0, View arg1, int
		 * arg2, long arg3) {
		 * 
		 * FragmentTransaction trans = getActivity()
		 * .getSupportFragmentManager().beginTransaction();
		 * trans.replace(R.id.content_frame, new PaperFragment());
		 * trans.commit(); } });
		 */
		return view;
	}

	public void updateView() {
		mdb.open();
		mylist = mdb.getAllPaper();
		mdb.close();

		ListAdapter = new PapertitleListAdapter(getActivity(),
				R.layout.raw_froumtitle, mylist);
		ListAdapter.notifyDataSetChanged();
		lst.setAdapter(ListAdapter);

	}

}