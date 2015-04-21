package com.project.mechanic.fragment;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.adapter.AnadListAdapter;
import com.project.mechanic.entity.Ticket;
import com.project.mechanic.model.DataBaseAdapter;

public class AnadFragment extends Fragment {

	DataBaseAdapter dbAdapter;
	private ImageView imgadd;
	private TextView txt1;
	View view;
	List<Ticket> mylist;
	private DialogAnad dialog;
	int ticketTypeid = 0;
	private static int RESULT_LOAD_IMAGE = 1;
	int proID = -1;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_anad, null);

		((MainActivity) getActivity()).setActivityTitle(R.string.anad);
		ticketTypeid = Integer.valueOf(getArguments().getString("Id"));

		imgadd = (ImageView) view.findViewById(R.id.fragment_anad_imgadd);
		txt1 = (TextView) view.findViewById(R.id.fragment_anad_txt1);

		dbAdapter = new DataBaseAdapter(getActivity());

		if (getArguments().getString("ProID") != null) {
			proID = Integer.valueOf(getArguments().getString("ProID"));
		}

		dbAdapter.open();

		mylist = dbAdapter.getTicketByTypeIdProId(ticketTypeid, proID);
		dbAdapter.close();

		imgadd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				dialog = new DialogAnad(getActivity(), R.layout.dialog_addanad,
						AnadFragment.this, ticketTypeid, proID);
				dialog.setTitle(R.string.txtanad);

				dialog.show();
			}
		});

		ListView lstAnad = (ListView) view.findViewById(R.id.listVanad);
		AnadListAdapter ListAdapter = new AnadListAdapter(getActivity(),
				R.layout.row_anad, mylist);

		lstAnad.setAdapter(ListAdapter);

		txt1.setOnClickListener(new OnClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onClick(View arg0) {

				imgadd.callOnClick();
			}
		});

		return view;

	}

	public void updateView() {
		dbAdapter.open();
		mylist = dbAdapter.getTicketByTypeIdProId(ticketTypeid, proID);
		dbAdapter.close();

		ListView lstAnad = (ListView) view.findViewById(R.id.listVanad);
		AnadListAdapter ListAdapter = new AnadListAdapter(getActivity(),
				R.layout.row_anad, mylist);
		ListAdapter.notifyDataSetChanged();
		lstAnad.setAdapter(ListAdapter);

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RESULT_LOAD_IMAGE
				&& resultCode == Activity.RESULT_OK && null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getActivity().getContentResolver().query(
					selectedImage, filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();

			ImageView imageView = (ImageView) dialog
					.findViewById(R.id.dialog_img1);
			imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
		}

	}

}
