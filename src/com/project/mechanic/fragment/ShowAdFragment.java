package com.project.mechanic.fragment;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.entity.Ticket;
import com.project.mechanic.model.DataBaseAdapter;

public class ShowAdFragment extends Fragment {

	int id;
	DataBaseAdapter dbAdapter;
	TextView desc, name, email, phone, mobile, fax;
	ImageView img;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		((MainActivity) getActivity()).setActivityTitle(R.string.showad);
		id = Integer.valueOf(getArguments().getString("Id"));

		View view = inflater.inflate(R.layout.fragment_showad, null);

		img = (ImageView) view.findViewById(R.id.fragment_anad_imgadd);

		desc = (TextView) view.findViewById(R.id.fragment_showad_txt);
		name = (TextView) view.findViewById(R.id.fragment_showad_tx1);
		email = (TextView) view.findViewById(R.id.fragment_showad_tx2);
		phone = (TextView) view.findViewById(R.id.fragment_showad_tx3);
		mobile = (TextView) view.findViewById(R.id.fragment_showad_tx4);
		fax = (TextView) view.findViewById(R.id.fragment_showad_tx5);

		dbAdapter = new DataBaseAdapter(getActivity());

		dbAdapter.open();
		Ticket t = dbAdapter.getTicketById(id);

		byte[] bitmapbyte = t.getImage();
		if (bitmapbyte != null) {
			Bitmap bmp = BitmapFactory.decodeByteArray(bitmapbyte, 0,
					bitmapbyte.length);
			img.setImageBitmap(bmp);
		}

		desc.setText(t.getDesc());
		name.setText(t.getUName());
		email.setText(t.getUEmail());
		phone.setText(t.getUPhone());
		// mobile.setText(t.getu);
		fax.setText(t.getUFax());

		dbAdapter.close();

		return view;
	}
}
