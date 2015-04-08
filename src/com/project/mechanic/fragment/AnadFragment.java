package com.project.mechanic.fragment;

import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.adapter.AnadListAdapter;
import com.project.mechanic.entity.ListItem;
import com.project.mechanic.model.DataBaseAdapter;

public class AnadFragment extends Fragment {

	DataBaseAdapter dbAdapter;
	private ImageButton addtitle;
	private DialogfroumTitle dialog;


	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_anad, null);

		((MainActivity) getActivity()).setActivityTitle(R.string.anad);
		
		addtitle = (ImageButton) view.findViewById(R.id.imgBtnAddcmt);
		

			dbAdapter = new DataBaseAdapter(getActivity());

			dbAdapter.open();
			List<ListItem> mylist = dbAdapter.getListItemsById(0);
			dbAdapter.close();

			ListView lstAnad = (ListView) view.findViewById(R.id.listVanad);
			AnadListAdapter ListAdapter = new AnadListAdapter(getActivity(),
					R.layout.row_anad, mylist);

			lstAnad.setAdapter(ListAdapter);
		
			addtitle.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {

					dialog = new DialogfroumTitle(getActivity(),R.layout.dialog_addtitle, AnadFragment.this);
					dialog.show();
				}
			});
		
		return view;

	}
}
