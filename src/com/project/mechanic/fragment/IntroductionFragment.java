package com.project.mechanic.fragment;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
	public int id = 1;

	public ImageButton like;
	public ImageButton Comment;

	ArrayList<CommentInObject> mylist;
	DataBaseAdapter adapter;
	ListView lst;

	TextView txtFax;
	TextView txtAddress;
	TextView txtPhone;
	TextView txtCellphone;
	TextView txtEmail;
	TextView txtDesc;
	TextView txtNumofLike;
	TextView txtNumofComment;
	ImageView advertise;
	ImageView advertise2;
	ImageButton Facebook;
	ImageButton Instagram;
	ImageButton LinkedIn;
	ImageButton Google;
	ImageButton Site;
	ImageButton Twitter;
	Button Pdf1;
	Button Pdf2;
	Button Pdf3;
	Button Pdf4;
	Object object;
	ImageButton phone;
	ImageButton cphone;
	ImageButton map;
	ImageButton email;
	ImageButton EditPage;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_introduction, null);

		((MainActivity) getActivity()).setActivityTitle(R.string.brand);
		adapter = new DataBaseAdapter(getActivity());
		peykan6 = (ImageView) view.findViewById(R.id.imageButton6);
		peykan5 = (ImageView) view.findViewById(R.id.imageButton7);
		advertise = (ImageView) view.findViewById(R.id.imgvadvertise_Object);
		advertise2 = (ImageView) view.findViewById(R.id.imgvadvertise2_Object);

		link1 = (RelativeLayout) view.findViewById(R.id.Layoutlink1);
		link2 = (RelativeLayout) view.findViewById(R.id.Layoutlink2);

		adapter = new DataBaseAdapter(getActivity());
		txtFax = (TextView) view.findViewById(R.id.txtFax_Object);
		txtAddress = (TextView) view.findViewById(R.id.txtAddress_Object);
		txtPhone = (TextView) view.findViewById(R.id.txtPhone_Object);
		txtCellphone = (TextView) view.findViewById(R.id.txtCellphone_Object);
		txtDesc = (TextView) view.findViewById(R.id.txtDesc_Object);
		txtEmail = (TextView) view.findViewById(R.id.txtEmail_Object);
		txtNumofLike = (TextView) view.findViewById(R.id.txtNumofLike_Object);
		txtNumofComment = (TextView) view
				.findViewById(R.id.txtNumofComment_Object);

		like = (ImageButton) view.findViewById(R.id.ImgbtnLike_Object);
		btnCmt = (ImageButton) view.findViewById(R.id.imgbtnCmt_introduction);
		Facebook = (ImageButton) view.findViewById(R.id.imgbtnLike_RawCmtFroum);
		Instagram = (ImageButton) view.findViewById(R.id.imgbtnInsta_Object);
		LinkedIn = (ImageButton) view.findViewById(R.id.imgbtnLinkedin_Object);
		Google = (ImageButton) view.findViewById(R.id.imgbtnGoogle_Object);
		Site = (ImageButton) view.findViewById(R.id.imgbtnSite_Object);
		Twitter = (ImageButton) view.findViewById(R.id.imgbtnTwitter_Object);

		phone = (ImageButton) view.findViewById(R.id.phonebtn);
		cphone = (ImageButton) view.findViewById(R.id.cphonebtn);
		map = (ImageButton) view.findViewById(R.id.mapbtn);
		email = (ImageButton) view.findViewById(R.id.emailbtn);

		Pdf1 = (Button) view.findViewById(R.id.btnPdf1_Object);
		Pdf2 = (Button) view.findViewById(R.id.btnPdf2_Object);
		Pdf3 = (Button) view.findViewById(R.id.btnPdf3_Object);
		Pdf4 = (Button) view.findViewById(R.id.btnPdf4_Object);

		EditPage = (ImageButton) view.findViewById(R.id.ImgbtnEdit);

		lst = (ListView) view.findViewById(R.id.listvCmt_Introduction);

		if (getArguments() != null && getArguments().getString("Id") != null) {
			id = Integer.valueOf(getArguments().getString("Id"));
		}

		SharedPreferences sendDataID = getActivity().getSharedPreferences("Id",
				0);
		final int cid = sendDataID.getInt("main_Id", -1);

		adapter.open();
		mylist = adapter.getAllCommentInObjectById(id);
		txtNumofComment.setText(adapter.CommentInObject_count().toString());
		txtNumofLike.setText(adapter.LikeInObject_count().toString());
		object = adapter.getAllObjectbyid(cid);
		if (object == null) {
			return view;
		}

		txtFax.setText(object.getFax());
		txtPhone.setText(object.getPhone());
		txtCellphone.setText(object.getCellphone());
		txtEmail.setText(object.getEmail());
		txtAddress.setText(object.getAddress());
		txtDesc.setText(object.getDescription());

		// advertise.setimage
		Facebook.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
						.parse(object.getFacebook()));
				startActivity(browserIntent);

			}
		});
		Instagram.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
						.parse(object.getInstagram()));
				startActivity(browserIntent);

			}
		});

		LinkedIn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
						.parse(object.getLinkedIn()));
				startActivity(browserIntent);

			}
		});

		Google.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
						.parse(object.getGoogle()));
				startActivity(browserIntent);

			}
		});

		Site.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
						.parse(object.getSite()));
				startActivity(browserIntent);

			}
		});

		Twitter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
						.parse(object.getTwitter()));
				startActivity(browserIntent);

			}
		});

		Pdf1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
						.parse(object.getPdf1()));
				startActivity(browserIntent);

			}
		});

		Pdf2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
						.parse(object.getPdf2()));
				startActivity(browserIntent);

			}
		});

		Pdf3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
						.parse(object.getPdf3()));
				startActivity(browserIntent);

			}
		});

		Pdf4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
						.parse(object.getPdf4()));
				startActivity(browserIntent);

			}
		});

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

		like.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				adapter.open();
				adapter.insertLikeInObjectToDb(1, 0, "", 1);
				txtNumofLike.setText(adapter.LikeInObject_count().toString());

			}
		});

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

		phone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// Toast.makeText(getActivity(), "ok",
				// Toast.LENGTH_SHORT).show();

				// startActivityForResult(new
				// Intent("android.intent.action.call",Uri.parse("tel:"+
				// txtCellphone.getText().toString())), 1);

				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
						+ txtCellphone.getText().toString()));
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);

			}
		});

		cphone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Toast.makeText(getActivity(), "ok", Toast.LENGTH_SHORT).show();
				// startActivityForResult(new
				// Intent("android.intent.action.call",Uri.parse("tel:"+
				// txtPhone.getText().toString())), 1);

				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
						+ txtPhone.getText().toString()));
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);

			}
		});

		email.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String b_email = txtEmail.getText().toString();
				Intent email = new Intent(Intent.ACTION_SEND);
				email.putExtra(Intent.EXTRA_EMAIL, new String[] { "b_email" });
				email.setType("message/rfc822");

				startActivity(Intent.createChooser(email,
						"Choose an Email client :"));

			}

		});

		map.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Toast.makeText(getActivity(), "ok", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(
						android.content.Intent.ACTION_VIEW,
						Uri.parse("https://www.google.com/maps/dir/36.2476613,59.4998502/Mashhad,+Khorasan+Razavi/Khorasan+Razavi,+Mashhad,+Kolahdooz+Blvd,+No.+47/@36.2934197,59.5606058,15z/data=!4m15!4m14!1m0!1m5!1m1!1s0x3f6c911abe4131d7:0xc9c57e3a9318753b!2m2!1d59.6167549!2d36.2604623!1m5!1m1!1s0x3f6c91798c9d172b:0xaf638c4e2e2ac720!2m2!1d59.5749626!2d36.2999667!3e2"));
				startActivity(intent);
			}
		});

		EditPage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				FragmentTransaction trans = getActivity()
						.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame,
						new IntroductionEditFragment());
				trans.commit();

			}
		});
		return view;

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

	public void updateView3() {
		adapter.open();
		mylist = adapter.getAllCommentInObjectById(id);
		txtNumofComment.setText(adapter.CommentInObject_count().toString());
		adapter.close();
		IntroductionListAdapter x = new IntroductionListAdapter(getActivity(),
				R.layout.raw_froumcmt, mylist);
		x.notifyDataSetChanged();
		lst.setAdapter(x);
	}

}
