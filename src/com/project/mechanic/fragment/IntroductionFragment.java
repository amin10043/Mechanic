package com.project.mechanic.fragment;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.adapter.IntroductionListAdapter;
import com.project.mechanic.entity.CommentInObject;
import com.project.mechanic.entity.Object;
import com.project.mechanic.model.DataBaseAdapter;

public class IntroductionFragment extends Fragment {

	Context context;
	private ImageView peykan6, peykan5;
	public RelativeLayout link1, link2;
	public ImageButton btnCmt;

	public DialogcmtInobject dialog;
	DialogcmtInfroum dialog2;
	public int id = 0;

	public ImageButton like;

	ArrayList<CommentInObject> mylist;
	DataBaseAdapter adapter;
	ListView lst;

	TextView txtFax;
	TextView txtAddress;
	TextView txtPhone;
	TextView txtCellphone;
	TextView txtEmail;
	TextView txtDesc;
	ImageView advertise;
	ImageView advertise2;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_introduction, null);

		((MainActivity) getActivity()).setActivityTitle(R.string.brand);
		adapter = new DataBaseAdapter(getActivity());
		peykan6 = (ImageView) view.findViewById(R.id.imageButton6);
		peykan5 = (ImageView) view.findViewById(R.id.imageButton7);
		btnCmt = (ImageButton) view.findViewById(R.id.imgbtnCmt_introduction);
		link1 = (RelativeLayout) view.findViewById(R.id.Layoutlink1);
		link2 = (RelativeLayout) view.findViewById(R.id.Layoutlink2);

		adapter = new DataBaseAdapter(getActivity());
		txtFax = (TextView) view.findViewById(R.id.txtFax_Object);
		txtAddress = (TextView) view.findViewById(R.id.txtAddress_Object);
		txtPhone = (TextView) view.findViewById(R.id.txtPhone_Object);
		txtCellphone = (TextView) view.findViewById(R.id.txtCellphone_Object);
		txtDesc = (TextView) view.findViewById(R.id.txtDesc_Object);

		txtEmail = (TextView) view.findViewById(R.id.txtEmail_Object);
		peykan6 = (ImageView) view.findViewById(R.id.imageButton6);
		peykan5 = (ImageView) view.findViewById(R.id.imageButton7);
		advertise = (ImageView) view.findViewById(R.id.imgvadvertise_Object);
		advertise2 = (ImageView) view.findViewById(R.id.imgvadvertise2_Object);
		btnCmt = (ImageButton) view.findViewById(R.id.imgbtnCmt_introduction);
		like = (ImageButton) view.findViewById(R.id.ImgbtnLike_Object);
		link1 = (RelativeLayout) view.findViewById(R.id.Layoutlink1);

		link2 = (RelativeLayout) view.findViewById(R.id.Layoutlink2);

		lst = (ListView) view.findViewById(R.id.listvCmt_Introduction);
		// id = Integer.valueOf(getArguments().getString("Id"));
		adapter.open();
		mylist = adapter.getAllCommentInObjectById(id);
		Object object = adapter.getAllObjectbyid(id);
		// txtFax.setText(object.getFax());
		// txtPhone.setText(object.getPhone());
		// txtCellphone.setText(object.get);
		// txtEmail.setText(object.getEmail());
		// txtAddress.setText(object.get);
		// txtDesc.setText(object.getDescription());
		// advertise.setimage

		adapter.close();
		IntroductionListAdapter listAdapter = new IntroductionListAdapter(
				getActivity(), R.layout.raw_froumcmt, mylist);
		lst.setAdapter(listAdapter);

		resizeListView(lst);

		link2 = (RelativeLayout) view.findViewById(R.id.Layoutlink2);

		link1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				FragmentTransaction trans = getActivity()
						.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new ProvinceFragment());
				trans.commit();

			}
		});

		btnCmt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				dialog = new DialogcmtInobject(IntroductionFragment.this,
						getActivity(), R.layout.dialog_addcomment);
				dialog.show();
			}
		});

		/*
		 * like.setOnClickListener(new View.OnClickListener() {
		 * 
		 * @Override public void onClick(View arg0) { adapter.open();
		 * adapter.insertLikeInObjectToDb( 1, 0,"",1); ArrayList<LikeInObject> y
		 * = adapter.getAllLikeInObjectById(id);
		 * 
		 * int x= adapter.LikeInObject_count(y);
		 * 
		 * } });
		 */

		/*
		 * public void updateView3() { adapter.open(); mylist =
		 * adapter.getAllCommentInObjectById(id); adapter.close();
		 * 
		 * IntroductionListAdapter x= new IntroductionListAdapter(getActivity(),
		 * R.layout.raw_froumcmt, mylist); x.notifyDataSetChanged();
		 * lst.setAdapter(x);
		 * 
		 * }
		 */

		link2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				FragmentTransaction trans = getActivity()
						.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new ProvinceFragment());
				trans.commit();

			}
		});

		peykan6.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				FragmentTransaction trans = getActivity()
						.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new ProvinceFragment());
				trans.commit();

			}
		});

		peykan5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				FragmentTransaction trans = getActivity()
						.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new ProvinceFragment());
				trans.commit();

			}
		});

		return view;

	}

	private void updateView3() {
	}

	private void resizeListView(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = listView.getPaddingTop()
				+ listView.getPaddingBottom();
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);

			if (listItem instanceof ViewGroup) {
				listItem.setLayoutParams(new LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			}
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}
}
