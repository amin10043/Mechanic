package com.project.mechanic.fragment;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.project.mechanic.R;
import com.project.mechanic.adapter.MainListAdapter;
import com.project.mechanic.entity.ListItem;
import com.project.mechanic.model.DataBaseAdapter;

public class MainFragment extends Fragment {

	DataBaseAdapter dbAdapter;
	MainListAdapter ListAdapter;
	LinearLayout footer_layLayout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// int [] icon = {R.drawable.ic_main_item1 , R.drawable.add ,
		// R.drawable.like , R.drawable.news3 , R.drawable.login_icon ,
		// R.drawable.cancle2 , R.drawable.google};

		View view = inflater.inflate(R.layout.fragment_main, null);
		dbAdapter = new DataBaseAdapter(getActivity());

		dbAdapter.open();
		List<ListItem> mylist = dbAdapter.getListItemsById(0);
		dbAdapter.close();

		ListView lstMain = (ListView) view.findViewById(R.id.lstMain);
		ListAdapter = new MainListAdapter(getActivity(),
				R.layout.main_item_list, mylist);

		Toast.makeText(getActivity(), String.valueOf(getScreenWidth()),
				Toast.LENGTH_SHORT).show();
		lstMain.setAdapter(ListAdapter);

		// footer_layLayout = (LinearLayout) getView()
		// .findViewById(R.id.tablighat);
		//
		// footer_layLayout.setBackgroundResource(R.drawable.propaganda);

		// lstMain.setOnTouchListener(new OnTouchListener() {
		//
		// @Override
		// public boolean onTouch(View arg0, MotionEvent ev) {
		//
		// return(ev.getAction()== MotionEvent.ACTION_MOVE);
		//
		//
		// }
		// });

		// lstMain.setla

		return view;
	}

	@SuppressLint("NewApi")
	public int getScreenWidth() {
		int columnWidth;
		WindowManager wm = (WindowManager) getActivity().getSystemService(
				Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();

		final Point point = new Point();
		try {
			display.getSize(point);
		} catch (java.lang.NoSuchMethodError ignore) { // Older device
			point.x = display.getWidth();
			point.y = display.getHeight();
		}
		columnWidth = point.y;
		return columnWidth;

	}

}
