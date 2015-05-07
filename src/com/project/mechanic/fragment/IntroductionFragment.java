package com.project.mechanic.fragment;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import android.widget.LinearLayout;
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
import com.project.mechanic.utility.Utility;

public class IntroductionFragment extends Fragment {

	Context context;
	private ImageView peykan6, peykan5;
	public RelativeLayout link1, link2;
	public ImageButton btnCmt;

	public DialogcmtInobject dialog;
	DialogcmtInfroum dialog2;
	public int id = 1;
	Fragment fragment;
	public ImageButton like;
	public ImageButton Comment;
	ImageView imagedisplay;
	LinearLayout.LayoutParams lp2;
	ArrayList<CommentInObject> mylist;
	DataBaseAdapter adapter;
	ListView lst;
	LinearLayout linpic;
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
	ImageButton Pdf1;
	ImageButton Pdf2;
	ImageButton Pdf3;
	ImageButton Pdf4;
	Object object;
	ImageButton phone;
	ImageButton cphone;
	ImageButton map;
	ImageButton email;
	ImageButton EditPage;
	ImageButton CreatePage;
	byte[] headerbyte, profilebyte, footerbyte;
	ImageView profileimage;

	Utility ut;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_introduction, null);
		ut = new Utility(getActivity());
		((MainActivity) getActivity()).setActivityTitle(R.string.brand);
		adapter = new DataBaseAdapter(getActivity());
		peykan6 = (ImageView) view.findViewById(R.id.imageButton6);
		peykan5 = (ImageView) view.findViewById(R.id.imageButton7);
		advertise = (ImageView) view.findViewById(R.id.imgvadvertise_Object);
		advertise2 = (ImageView) view.findViewById(R.id.imgvadvertise2_Object);
		profileimage = (ImageView) view.findViewById(R.id.icon_pro);

		imagedisplay = (ImageView) view.findViewById(R.id.imagedisplay);
		link1 = (RelativeLayout) view.findViewById(R.id.Layoutlink1);
		link2 = (RelativeLayout) view.findViewById(R.id.Layoutlink2);

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
		Facebook = (ImageButton) view.findViewById(R.id.nfacebook);
		Instagram = (ImageButton) view.findViewById(R.id.ninstagram);
		LinkedIn = (ImageButton) view.findViewById(R.id.nlinkedin);
		Google = (ImageButton) view.findViewById(R.id.ngoogle);
		Site = (ImageButton) view.findViewById(R.id.nsite);
		Twitter = (ImageButton) view.findViewById(R.id.ntwtert);

		phone = (ImageButton) view.findViewById(R.id.phonebtn);
		cphone = (ImageButton) view.findViewById(R.id.cphonebtn);
		map = (ImageButton) view.findViewById(R.id.mapbtn);
		email = (ImageButton) view.findViewById(R.id.emailbtn);

		Pdf1 = (ImageButton) view.findViewById(R.id.btnPdf1_Object);
		Pdf2 = (ImageButton) view.findViewById(R.id.btnPdf2_Object);
		Pdf3 = (ImageButton) view.findViewById(R.id.btnPdf3_Object);
		Pdf4 = (ImageButton) view.findViewById(R.id.btnPdf4_Object);
		linpic = (LinearLayout) view.findViewById(R.id.linearpicture);
		EditPage = (ImageButton) view.findViewById(R.id.ImgbtnEdit);
		CreatePage = (ImageButton) view.findViewById(R.id.ImgbtnCreate);

		lst = (ListView) view.findViewById(R.id.listvCmt_Introduction);

		if (getArguments() != null && getArguments().getString("Id") != null) {
			id = Integer.valueOf(getArguments().getString("Id"));
		}

		SharedPreferences sendDataID = getActivity().getSharedPreferences("Id",
				0);
		final int cid = sendDataID.getInt("main_Id", -1);

		Toast.makeText(getActivity(), "" + cid, Toast.LENGTH_LONG).show();

		Toast.makeText(getActivity(), "" + id, Toast.LENGTH_LONG).show();

		lp2 = new LinearLayout.LayoutParams(linpic.getLayoutParams());

		lp2.height = ut.getScreenwidth() / 4;
		lp2.width = ut.getScreenwidth() / 4;

		imagedisplay.setLayoutParams(lp2);

		adapter.open();
		mylist = adapter.getAllCommentInObjectById(id);
		txtNumofComment.setText(adapter.CommentInObject_count().toString());
		txtNumofLike.setText(adapter.LikeInObject_count().toString());
		object = adapter.getObjectbyid(cid);
		if (object == null) {
			return view;
		}

		// imagedisplay.setBackgroundResource(R.drawable.profile_account);

		byte[] bitmapbyte = object.getImage1();
		if (bitmapbyte != null) {
			Bitmap bmp1 = BitmapFactory.decodeByteArray(bitmapbyte, 0,
					bitmapbyte.length);
			advertise.setImageBitmap(bmp1);

		}

		byte[] bitmap = object.getImage2();
		if (bitmap != null) {
			Bitmap bmp2 = BitmapFactory.decodeByteArray(bitmap, 0,
					bitmap.length);
			imagedisplay.setImageBitmap(bmp2);

		}
		byte[] bitm = object.getImage3();
		if (bitm != null) {
			Bitmap bmp3 = BitmapFactory.decodeByteArray(bitm, 0, bitm.length);
			advertise2.setImageBitmap(bmp3);

		}

		txtFax.setText(object.getFax());
		txtPhone.setText(object.getPhone());
		txtCellphone.setText(object.getCellphone());
		txtEmail.setText(object.getEmail());
		txtAddress.setText(object.getAddress());
		txtDesc.setText(object.getDescription());

		if (object.getObjectBrandTypeId() == 2)
			link2.setVisibility(View.GONE);
		else if (object.getObjectBrandTypeId() == 3)
			link1.setVisibility(View.GONE);
		else if (object.getObjectBrandTypeId() == 1) {
			link1.setVisibility(View.GONE);
			link2.setVisibility(View.GONE);
		}

		if (object.getFacebook() != null)
			Facebook.setImageResource(R.drawable.facebook);
		else
			Facebook.setImageResource(R.drawable.facebook_off);

		if (object.getInstagram() != null)
			Instagram.setImageResource(R.drawable.insta);
		else
			Instagram.setImageResource(R.drawable.insta_off);

		if (object.getLinkedIn() != null)
			LinkedIn.setImageResource(R.drawable.lnkin);
		else
			LinkedIn.setImageResource(R.drawable.lnkin_off);

		if (object.getGoogle() != null)
			Google.setImageResource(R.drawable.google);
		else
			Google.setImageResource(R.drawable.google_off);

		if (object.getSite() != null)
			Site.setImageResource(R.drawable.internet);
		else
			Site.setImageResource(R.drawable.internet_off);

		if (object.getTwitter() != null)
			Twitter.setImageResource(R.drawable.twtr);
		else
			Twitter.setImageResource(R.drawable.twtr_off);

		if (object.getPdf1() != null)
			Pdf1.setImageResource(R.drawable.ic_catalog);
		else
			Pdf1.setImageResource(R.drawable.ic_catalog_off);

		if (object.getPdf2() != null)
			Pdf2.setImageResource(R.drawable.ic_price);

		else
			Pdf2.setImageResource(R.drawable.ic_price_off);

		if (object.getPdf3() != null)
			Pdf3.setImageResource(R.drawable.ic_pdf);
		else
			Pdf3.setImageResource(R.drawable.ic_pdf_off);

		if (object.getPdf4() != null)
			Pdf4.setImageResource(R.drawable.ic_video);
		else
			Pdf4.setImageResource(R.drawable.ic_video_off);

		headerbyte = object.getImage1();
		profilebyte = object.getImage2();
		footerbyte = object.getImage3();

		if (profilebyte != null) {
			Bitmap bmp = BitmapFactory.decodeByteArray(profilebyte, 0,
					profilebyte.length);
			profileimage.setImageBitmap(bmp);
		}
		// advertise.setimage
		Facebook.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (object.getFacebook() != null) {
					String url = "http://" + object.getFacebook();
					FragmentTransaction trans = getActivity()
							.getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame, new FragmentShowSite(url));
					trans.commit();
				}
			}
		});
		Instagram.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (object.getInstagram() != null) {

					String url = "http://" + object.getInstagram();

					FragmentTransaction trans = getActivity()
							.getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame, new FragmentShowSite(url));
					trans.commit();
				}

				// Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
				// .parse(object.getInstagram()));
				// startActivity(browserIntent);

			}
		});

		LinkedIn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (object.getLinkedIn() != null) {

					String url = "http://" + object.getLinkedIn();

					FragmentTransaction trans = getActivity()
							.getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame, new FragmentShowSite(url));
					trans.commit();
				}
				//
				// Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
				// .parse(object.getLinkedIn()));
				// startActivity(browserIntent);

			}
		});

		Google.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (object.getGoogle() != null) {

					String url = "http://" + object.getGoogle();

					FragmentTransaction trans = getActivity()
							.getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame, new FragmentShowSite(url));
					trans.commit();
				}

				// Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
				// .parse(object.getGoogle()));
				// startActivity(browserIntent);

			}
		});

		Site.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (object.getSite() != null) {

					String url = "http://" + object.getSite();

					FragmentTransaction trans = getActivity()
							.getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame, new FragmentShowSite(url));
					trans.commit();
				}

				// Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
				// .parse(object.getSite()));
				// startActivity(browserIntent);

			}
		});

		Twitter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (object.getTwitter() != null) {

					String url = "http://" + object.getTwitter();

					FragmentTransaction trans = getActivity()
							.getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame, new FragmentShowSite(url));
					trans.commit();
				}

				// Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
				// .parse(object.getTwitter()));
				// startActivity(browserIntent);

			}
		});

		Pdf1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (object.getPdf1() != null) {
					String url = "http://" + object.getPdf1();

					FragmentTransaction trans = getActivity()
							.getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame, new FragmentShowSite(url));
					trans.commit();
				}

				// Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
				// .parse(object.getPdf1()));
				// startActivity(browserIntent);

			}
		});

		Pdf2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (object.getPdf2() != null) {

					String url = "http://" + object.getPdf2();

					FragmentTransaction trans = getActivity()
							.getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame, new FragmentShowSite(url));
					trans.commit();
				}

				// Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
				// .parse(object.getPdf2()));
				// startActivity(browserIntent);

			}
		});

		Pdf3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (object.getPdf3() != null) {

					String url = "http://" + object.getPdf3();

					FragmentTransaction trans = getActivity()
							.getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame, new FragmentShowSite(url));
					trans.commit();
				}

				// Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
				// .parse(object.getPdf3()));
				// startActivity(browserIntent);

			}
		});

		Pdf4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (object.getPdf4() != null) {

					String url = "http://" + object.getPdf4();

					FragmentTransaction trans = getActivity()
							.getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame, new FragmentShowSite(url));
					trans.commit();
				}

				// Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
				// .parse(object.getPdf4()));
				// startActivity(browserIntent);

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

		CreatePage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				FragmentTransaction trans = getActivity()
						.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame,
						new CreateIntroductionFragment());
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

	// public void onBackPressed() {
	// Fragment fragment = getFragmentManager().findFragmentByTag(
	// "MainFragment");
	// if (fragment != null && fragment instanceof BackPressedListener) {
	// ((BackPressedListener) fragment).onBackPressed();
	// } else {
	// // super.onBackPressed();
	// }
}
