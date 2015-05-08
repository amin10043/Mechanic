package com.project.mechanic.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.entity.Ticket;
import com.project.mechanic.entity.Users;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class ShowAdFragment extends Fragment {

	int id;
	int a;
	int F = 0;
	int favorite = 0;
	int userTicket;
	DataBaseAdapter dbAdapter;
	TextView desc, name, email, phone, mobile, fax, showname, showfax,
			showemail, showphone, showmobile;
	ImageView img;
	ImageButton share, edite, like;
	Utility util;
	Users u;
	private DialogAnad dialog;
	int proID = -1;
	int f;
	private boolean isFavorite = false;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		((MainActivity) getActivity()).setActivityTitle(R.string.showad);
		id = Integer.valueOf(getArguments().getString("Id"));
		util = new Utility(getActivity());
		View view = inflater.inflate(R.layout.fragment_showad, null);

		img = (ImageView) view.findViewById(R.id.fragment_anad_imgadd);

		share = (ImageButton) view.findViewById(R.id.imgShare_showAd);
		like = (ImageButton) view.findViewById(R.id.imgLike_showAd);
		edite = (ImageButton) view.findViewById(R.id.imgedite_showAd);
		desc = (TextView) view.findViewById(R.id.fragment_showad_txt);
		name = (TextView) view.findViewById(R.id.fragment_showad_tx1);
		email = (TextView) view.findViewById(R.id.fragment_showad_tx2);
		phone = (TextView) view.findViewById(R.id.fragment_showad_tx3);
		mobile = (TextView) view.findViewById(R.id.fragment_showad_tx4);
		fax = (TextView) view.findViewById(R.id.fragment_showad_tx5);
		showname = (TextView) view.findViewById(R.id.fragment_showad_name);
		showemail = (TextView) view.findViewById(R.id.fragment_showad_email);
		showphone = (TextView) view.findViewById(R.id.fragment_showad_phone);
		showmobile = (TextView) view.findViewById(R.id.fragment_showad_mobile);
		showfax = (TextView) view.findViewById(R.id.fragment_showad_fax);

		dbAdapter = new DataBaseAdapter(getActivity());

		if (getArguments().getString("ProID") != null) {
			proID = Integer.valueOf(getArguments().getString("ProID"));
		}

		dbAdapter.open();
		Ticket t = dbAdapter.getTicketById(id);
		a = t.getId();
		userTicket = t.getUserId();

		byte[] bitmapbyte = t.getImage();
		if (bitmapbyte != null) {
			Bitmap bmp = BitmapFactory.decodeByteArray(bitmapbyte, 0,
					bitmapbyte.length);
			img.setImageBitmap(bmp);
		}
		dbAdapter.close();
		u = util.getCurrentUser();

		if (u == null) {
			like.setEnabled(false);
		} else if (userTicket == u.getId()) {

			edite.setVisibility(1);
		}

		like.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (u != null && isFavorite) {
					dbAdapter.open();
					dbAdapter.deletebyIdTicket(a);
					dbAdapter.close();
					like.setImageResource(R.drawable.ic_star_off);
				} else {
					dbAdapter.open();
					dbAdapter.insertFavoritetoDb(0, u.getId(), a);
					dbAdapter.close();
					like.setImageResource(R.drawable.ic_star_on);

				}
				isFavorite = !isFavorite;

			}
		});
		edite.setOnClickListener(new View.OnClickListener() {

			// @Override
			public void onClick(View arg0) {
				dialog = new DialogAnad(getActivity(), R.layout.dialog_addanad,
						ShowAdFragment.this, id, proID);
				dialog.setTitle(R.string.txtanadedite);

				dialog.show();

			}
		});

		// fragment = new DisplayPersonalInformationFragment();
		// fragmentManager = getSupportFragmentManager();
		// fragmentManager.beginTransaction()
		// .replace(R.id.content_frame, fragment).commit();

		dbAdapter.open();
		share.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent sharingIntent = new Intent(
						android.content.Intent.ACTION_SEND);
				sharingIntent.setType("text/plain");
				String shareBody = "Here is the share content body";
				sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
						"Subject Here");
				sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
						shareBody);
				startActivity(Intent.createChooser(sharingIntent,
						"اشتراک از طریق"));

			}
		});

		desc.setText(t.getDesc());

		if ("".equals(t.getUName())) {
			showname.setVisibility(View.GONE);
			name.setVisibility(View.GONE);

		} else {
			showname.setVisibility(View.VISIBLE);
			name.setVisibility(View.VISIBLE);
			name.setText(t.getUName());
		}
		if ("".equals(t.getUEmail())) {
			showemail.setVisibility(View.GONE);
			email.setVisibility(View.GONE);

		} else {
			showemail.setVisibility(View.VISIBLE);
			email.setVisibility(View.VISIBLE);
			email.setText(t.getUEmail());
		}
		if ("".equals(t.getUPhone())) {
			showphone.setVisibility(View.GONE);
			phone.setVisibility(View.GONE);

		} else {
			showphone.setVisibility(View.VISIBLE);
			phone.setVisibility(View.VISIBLE);
			phone.setText(t.getUPhone());
		}
		if ("".equals(t.getUMobile())) {
			showmobile.setVisibility(View.GONE);
			mobile.setVisibility(View.GONE);

		} else {
			showmobile.setVisibility(View.VISIBLE);
			mobile.setVisibility(View.VISIBLE);
			mobile.setText(t.getUMobile());
		}
		if ("".equals(t.getUFax())) {
			showfax.setVisibility(View.GONE);
			fax.setVisibility(View.GONE);

		} else {
			showfax.setVisibility(View.VISIBLE);
			fax.setVisibility(View.VISIBLE);
			fax.setText(t.getUFax());
		}
		dbAdapter.close();

		return view;
	}
}
