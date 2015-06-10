package com.project.mechanic.fragment;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mechanic.R;
import com.project.mechanic.adapter.ExpandIntroduction;
import com.project.mechanic.entity.CommentInObject;
import com.project.mechanic.entity.Object;
import com.project.mechanic.entity.Users;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class IntroductionFragment extends Fragment {

	Utility ut;
	Users CurrentUser;
	PersianDate datePersian;
	String currentDate;
	View header;
	ExpandableListView exListView;
	ExpandIntroduction exadapter;
	int ObjectID;

	ArrayList<CommentInObject> commentGroup, ReplyGroup;
	Map<CommentInObject, List<CommentInObject>> mapCollection;

	private ImageView peykan6, peykan5;
	public RelativeLayout link1, link2, sendSMS, addressRelative,
			emailRelative, profileLinear;

	public DialogcmtInobject dialog;
	Fragment fragment;

	public LinearLayout AddLike, AddComment;
	public ImageButton Comment;
	byte[] bitHeader, bytepro, bytefoot;

	LinearLayout.LayoutParams headerParams, footerParams;
	RelativeLayout.LayoutParams addressParams, emailParams, profileParams;

	ArrayList<CommentInObject> mylist;
	DataBaseAdapter adapter;
	LinearLayout headImageLinear, footerLinear;

	TextView txtFax, txtAddress, txtPhone, txtCellphone, txtEmail, txtDesc,
			CountLikeIntroduction, CountCommentIntroduction, namePage;

	ImageView headerImage, advertise2, profileImage;
	ImageButton Facebook, Instagram, LinkedIn, Google, Site, Twitter, Pdf1,
			Pdf2, Pdf3, Pdf4, phone, cphone, map, email, EditPage, shareBtn;
	Object object;
	byte[] headerbyte, profilebyte, footerbyte;

	SharedPreferences sendDataID;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_introduction, null);
		// ((MainActivity) getActivity()).setActivityTitle(R.string.brand);

		adapter = new DataBaseAdapter(getActivity());
		ut = new Utility(getActivity());
		datePersian = new PersianDate();
		currentDate = datePersian.todayShamsi();
		header = getActivity().getLayoutInflater().inflate(
				R.layout.header_introduction, null);
		CurrentUser = ut.getCurrentUser();

		// start find view

		exListView = (ExpandableListView) view
				.findViewById(R.id.ExpandIntroduction);
		peykan6 = (ImageView) header.findViewById(R.id.imageButton6);
		peykan5 = (ImageView) header.findViewById(R.id.imageButton7);
		headerImage = (ImageView) header
				.findViewById(R.id.imgvadvertise_Object);
		advertise2 = (ImageView) header
				.findViewById(R.id.imgvadvertise2_Object);
		profileImage = (ImageView) header.findViewById(R.id.icon_pro);

		headImageLinear = (LinearLayout) header
				.findViewById(R.id.headerlinerpageintroduction);
		link1 = (RelativeLayout) header.findViewById(R.id.Layoutlink1);
		link2 = (RelativeLayout) header.findViewById(R.id.Layoutlink2);
		sendSMS = (RelativeLayout) header
				.findViewById(R.id.sendsmsIntroduction);

		txtFax = (TextView) header.findViewById(R.id.txtFax_Object);
		txtAddress = (TextView) header.findViewById(R.id.txtAddress_Object);
		txtPhone = (TextView) header.findViewById(R.id.txtPhone_Object);
		txtCellphone = (TextView) header.findViewById(R.id.txtCellphone_Object);
		txtDesc = (TextView) header.findViewById(R.id.txtDesc_Object);
		txtEmail = (TextView) header.findViewById(R.id.txtEmail_Object);
		namePage = (TextView) header.findViewById(R.id.namePage);
		CountLikeIntroduction = (TextView) header
				.findViewById(R.id.countLikeIntroduction);
		CountCommentIntroduction = (TextView) header
				.findViewById(R.id.CountCommentIntroduction);

		AddLike = (LinearLayout) header
				.findViewById(R.id.AddLikeIntroductionLinear);
		AddComment = (LinearLayout) header
				.findViewById(R.id.AddcommentIntroductionLinear);

		addressRelative = (RelativeLayout) header
				.findViewById(R.id.addressRelative);
		emailRelative = (RelativeLayout) header
				.findViewById(R.id.emailRelative);

		Facebook = (ImageButton) header.findViewById(R.id.nfacebook);
		Instagram = (ImageButton) header.findViewById(R.id.ninstagram);
		LinkedIn = (ImageButton) header.findViewById(R.id.nlinkedin);
		Google = (ImageButton) header.findViewById(R.id.ngoogle);
		Site = (ImageButton) header.findViewById(R.id.nsite);
		Twitter = (ImageButton) header.findViewById(R.id.ntwtert);

		phone = (ImageButton) header.findViewById(R.id.phonebtn);
		cphone = (ImageButton) header.findViewById(R.id.cphonebtn);
		map = (ImageButton) header.findViewById(R.id.mapbtn);
		email = (ImageButton) header.findViewById(R.id.emailbtn);
		shareBtn = (ImageButton) header.findViewById(R.id.shareIntroduction);

		Pdf1 = (ImageButton) header.findViewById(R.id.btnPdf1_Object);
		Pdf2 = (ImageButton) header.findViewById(R.id.btnPdf2_Object);
		Pdf3 = (ImageButton) header.findViewById(R.id.btnPdf3_Object);
		Pdf4 = (ImageButton) header.findViewById(R.id.btnPdf4_Object);
		profileLinear = (RelativeLayout) header
				.findViewById(R.id.linear_id_profile_introduction_page);
		footerLinear = (LinearLayout) header.findViewById(R.id.footerint);
		EditPage = (ImageButton) header.findViewById(R.id.ImgbtnEdit);

		sendDataID = getActivity().getSharedPreferences("Id", 0);
		final int ObjectID = sendDataID.getInt("main_Id", -1);

		adapter.open();
		commentGroup = adapter.getAllCommentInObjectById(ObjectID, 0);
		mapCollection = new LinkedHashMap<CommentInObject, List<CommentInObject>>();

		List<CommentInObject> reply = null;
		for (CommentInObject comment : commentGroup) {
			reply = adapter.getReplyCommentIntroduction(ObjectID,
					comment.getId());
			mapCollection.put(comment, reply);
		}

		adapter.close();

		exadapter = new ExpandIntroduction(getActivity(),
				(ArrayList<CommentInObject>) commentGroup, mapCollection, this,
				ObjectID);
		exListView.addHeaderView(header);

		exListView.setAdapter(exadapter);
		adapter.open();

		if (CurrentUser == null) {
		} else {
			if (adapter.isUserLikeIntroductionPage(CurrentUser.getId(),
					ObjectID))
				AddLike.setBackgroundResource(R.drawable.like_froum);
			else
				AddLike.setBackgroundResource(R.drawable.like_froum_off);
		}
		adapter.close();

		profileParams = new RelativeLayout.LayoutParams(
				profileLinear.getLayoutParams());

		profileParams.height = ut.getScreenwidth() / 8;
		profileParams.width = ut.getScreenwidth() / 8;
		profileParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

		profileImage.setLayoutParams(profileParams);

		headerParams = new LinearLayout.LayoutParams(
				headImageLinear.getLayoutParams());
		headerParams.height = ut.getScreenHeight() / 3;

		footerParams = new LinearLayout.LayoutParams(
				footerLinear.getLayoutParams());
		footerParams.height = ut.getScreenHeight() / 3;

		adapter.open();
		int countcmt = adapter.CommentInObject_count(ObjectID);
		CountCommentIntroduction.setText(String.valueOf(countcmt));

		int countlike = adapter.LikeInObject_count(ObjectID);
		CountLikeIntroduction.setText(String.valueOf(countlike));

		object = adapter.getObjectbyid(ObjectID);
		adapter.close();
		if (object == null) {
			return view;
		}
		namePage.setText(object.getName());

		headerImage.setLayoutParams(headerParams);
		advertise2.setLayoutParams(footerParams);

		// imagedisplay.setBackgroundResource(R.drawable.profile_account);
		adapter.open();
		if (object.getIsActive() == 0) {

			View t = inflater.inflate(R.layout.fragment_is_active_introduction,
					null);

			ImageButton EditActive = (ImageButton) t
					.findViewById(R.id.ImgbtnEdit);
			Users user = ut.getCurrentUser();
			if (user == null || object.getUserId() != user.getId()) {
				EditActive.setVisibility(View.INVISIBLE);
			} else {
				EditActive.setVisibility(View.VISIBLE);

			}

			bitHeader = object.getImage1();
			ImageView HeaderActive = (ImageView) t
					.findViewById(R.id.imgvadvertise_Object);

			if (bitHeader != null) {
				Bitmap bmp1 = BitmapFactory.decodeByteArray(bitHeader, 0,
						bitHeader.length);
				HeaderActive.setImageBitmap(bmp1);
			} else
				HeaderActive.setImageResource(R.drawable.no_image_header);
			RelativeLayout profileActiveRelative = (RelativeLayout) t
					.findViewById(R.id.linear_id_profile_introduction_page);

			RelativeLayout.LayoutParams ll = new RelativeLayout.LayoutParams(
					profileActiveRelative.getLayoutParams());
			ll.width = ut.getScreenwidth() / 8;
			ll.height = ut.getScreenwidth() / 8;
			ll.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

			ImageView profileActive = (ImageView) t.findViewById(R.id.icon_pro);
			bytepro = object.getImage2();
			if (bytepro != null) {
				Bitmap bmp2 = BitmapFactory.decodeByteArray(bytepro, 0,
						bytepro.length);
				profileActive.setImageBitmap(bmp2);
				profileActive.setLayoutParams(ll);

			} else {
				profileActive.setImageResource(R.drawable.no_img_profile);
				profileActive.setLayoutParams(ll);

			}

			RelativeLayout namayandegiActive = (RelativeLayout) t
					.findViewById(R.id.Layoutlink1);

			namayandegiActive.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {

					FragmentTransaction trans = getActivity()
							.getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame, new ProvinceFragment());
					trans.commit();
				}
			});

			RelativeLayout khadamatActive = (RelativeLayout) t
					.findViewById(R.id.Layoutlink2);
			khadamatActive.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					FragmentTransaction trans = getActivity()
							.getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame, new ProvinceFragment());
					trans.commit();
				}
			});

			EditActive.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {

					FragmentTransaction trans = getActivity()
							.getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame,
							new IntroductionEditFragment());
					trans.commit();
				}
			});

			TextView namePageDisActive = (TextView) t
					.findViewById(R.id.namePage);
			namePageDisActive.setText(object.getName());
			adapter.close();

			// this view is created for check active or inactive introduction
			// page

			return t;

		}

		adapter.close();

		Users user = ut.getCurrentUser();
		if (user == null || object.getUserId() != user.getId()) {
			EditPage.setVisibility(View.INVISIBLE);
			Toast.makeText(getActivity(),
					"userid dar database sqlite be soorat dasti 0 save shode",
					Toast.LENGTH_LONG).show();
		} else {
			EditPage.setVisibility(View.VISIBLE);

		}
		addressParams = new RelativeLayout.LayoutParams(
				addressRelative.getLayoutParams());

		addressParams.width = ut.getScreenwidth() / 2;
		addressParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		txtAddress.setLayoutParams(addressParams);

		emailParams = new RelativeLayout.LayoutParams(
				emailRelative.getLayoutParams());

		emailParams.width = (int) (ut.getScreenwidth() / 2.5);
		emailParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		txtEmail.setLayoutParams(emailParams);

		bitHeader = object.getImage1();

		if (bitHeader != null) {
			Bitmap bmp1 = BitmapFactory.decodeByteArray(bitHeader, 0,
					bitHeader.length);

			headerImage.setImageBitmap(bmp1);
		} else
			headerImage.setImageResource(R.drawable.no_image_header);
		// /////////////////////
		bytepro = object.getImage2();
		if (bytepro != null) {
			Bitmap bmp2 = BitmapFactory.decodeByteArray(bytepro, 0,
					bytepro.length);

			profileImage.setImageBitmap(bmp2);

		} else
			profileImage.setImageResource(R.drawable.no_img_profile);
		// ///////////////////////
		bytefoot = object.getImage3();
		if (bytefoot != null) {
			Bitmap bmp3 = BitmapFactory.decodeByteArray(bytefoot, 0,
					bytefoot.length);

			advertise2.setImageBitmap(bmp3);

		} else
			advertise2.setImageResource(R.drawable.no_image_header);

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
		//
		// headerbyte = object.getImage1();
		// profilebyte = object.getImage2();
		// footerbyte = object.getImage3();

		// if (footerbyte != null) {
		// Bitmap bmp = BitmapFactory.decodeByteArray(footerbyte, 0,
		// footerbyte.length);
		// OutputStream stream = null;
		// bmp.compress(CompressFormat.PNG, 80, stream);
		//
		// advertise2.setImageBitmap(bmp);
		// }

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

			}
		});

		sendSMS.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// add the phone number in the data
				Uri uri = Uri.parse("smsto:");

				Intent smsSIntent = new Intent(Intent.ACTION_SENDTO, uri);
				// add the message at the sms_body extra field
				smsSIntent.putExtra("sms_body", object.getName() + "\n"
						+ "همراه : " + "\n" + object.getCellphone() + "\n"
						+ "تلفن :" + "\n" + object.getPhone() + "\n" + "آدرس :"
						+ "\n" + object.getAddress());
				try {
					startActivity(smsSIntent);
				} catch (Exception ex) {
					Toast.makeText(getActivity(), "Your sms has failed...",
							Toast.LENGTH_LONG).show();
					ex.printStackTrace();
				}
			}
		});

		// IntroductionListAdapter listAdapter = new IntroductionListAdapter(
		// getActivity(), R.layout.raw_froumcmt, mylist);
		// lst.setAdapter(listAdapter);

		// resizeListView(lst);

		link2 = (RelativeLayout) header.findViewById(R.id.Layoutlink2);

		link1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				FragmentTransaction trans = getActivity()
						.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new ProvinceFragment());
				trans.commit();

			}
		});

		AddComment.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (CurrentUser == null) {
					Toast.makeText(getActivity(), "ابتدا باید وارد شوید",
							Toast.LENGTH_SHORT).show();
					return;
				} else {
					dialog = new DialogcmtInobject(IntroductionFragment.this,
							getActivity(), R.layout.dialog_addcomment,
							ObjectID, 0);
					dialog.show();
					exadapter.notifyDataSetChanged();
				}

			}
		});

		AddLike.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				adapter.open();

				if (CurrentUser == null) {
					Toast.makeText(getActivity(), "ابتدا باید وارد شوید",
							Toast.LENGTH_SHORT).show();
					return;

				} else {
					if (adapter.isUserLikeIntroductionPage(CurrentUser.getId(),
							ObjectID)) {
						AddLike.setBackgroundResource(R.drawable.like_froum_off);
						adapter.deleteLikeIntroduction(CurrentUser.getId(),
								ObjectID);
						int countlike = adapter.LikeInObject_count(ObjectID);
						CountLikeIntroduction.setText(String.valueOf(countlike));
					} else {
						adapter.insertLikeInObjectToDb(CurrentUser.getId(),
								ObjectID, currentDate, 0);
						AddLike.setBackgroundResource(R.drawable.like_froum);

						int countlike = adapter.LikeInObject_count(ObjectID);
						CountLikeIntroduction.setText(String.valueOf(countlike));
					}

				}
				adapter.close();
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

				// Toast.makeText(getActivity(), "ok",
				// Toast.LENGTH_SHORT).show();
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
		shareBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent sharingIntent = new Intent(
						android.content.Intent.ACTION_SEND);
				sharingIntent.setType("text/plain");
				// String shareBody = x.getDescription();
				// sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
				// object.getName());

				sharingIntent.putExtra(
						android.content.Intent.EXTRA_TEXT,
						object.getName() + "\n" + "تلفن :" + "\n"
								+ object.getPhone() + "\n" + "فکس :" + "\n"
								+ object.getFax() + "\n" + "موبایل :" + "\n"
								+ object.getCellphone() + "\n" + "ایمیل :"
								+ "\n" + object.getEmail() + "\n" + "آدرس : "
								+ "\n" + object.getAddress() + "\n"
								+ "فیس بوک : " + "\n" + object.getFacebook()
								+ "\n" + "لینکدین : " + "\n"
								+ object.getLinkedIn() + "\n" + "توئیتر :"
								+ "\n" + object.getTwitter() + "\n"
								+ " سایت : " + "\n" + object.getSite() + "\n"
								+ "  گوگل پلاس : " + "\n" + object.getGoogle()
								+ "\n" + "اینستاگرام : " + "\n"
								+ object.getInstagram() + "\n"
								+ "لینک کاتالوگ :" + "\n" + object.getPdf1()
								+ "\n" + "لیست قیمت" + "\n" + object.getPdf2()
								+ "\n" + "Pdf : " + "\n" + object.getPdf3()
								+ "\n" + "لینک آپارات" + "\n"
								+ object.getPdf4() + "\n" + "توضیحات :" + "\n"
								+ object.getDescription());
				startActivity(Intent.createChooser(sharingIntent,
						"اشتراک از طریق"));
			}
		});

		return view;

	}

	//

	public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 50, outputStream);

		return outputStream.toByteArray();

	}

	public void updateList() {
		sendDataID = getActivity().getSharedPreferences("Id", 0);
		final int ObjectID = sendDataID.getInt("main_Id", -1);

		adapter.open();
		commentGroup = adapter.getAllCommentInObjectById(ObjectID, 0);
		mapCollection = new LinkedHashMap<CommentInObject, List<CommentInObject>>();

		List<CommentInObject> reply = null;
		for (CommentInObject comment : commentGroup) {
			reply = adapter.getReplyCommentIntroduction(ObjectID,
					comment.getId());
			mapCollection.put(comment, reply);
		}

		int countcmt = adapter.CommentInObject_count(ObjectID);
		CountCommentIntroduction.setText(String.valueOf(countcmt));
		exadapter = new ExpandIntroduction(getActivity(),
				(ArrayList<CommentInObject>) commentGroup, mapCollection, this,
				ObjectID);
		adapter.close();
		exadapter.notifyDataSetChanged();

		exListView.setAdapter(exadapter);

	}

	// public void updateView3() {
	// adapter.open();
	// sendDataID = getActivity().getSharedPreferences("Id", 0);
	// final int cid = sendDataID.getInt("main_Id", -1);
	// mylist = adapter.getAllCommentInObjectById(cid);
	// CountCommentIntroduction.setText(adapter.CommentInObject_count()
	// .toString());
	// adapter.close();
	// IntroductionListAdapter x = new IntroductionListAdapter(getActivity(),
	// R.layout.raw_froumcmt, mylist);
	// x.notifyDataSetChanged();
	// // lst.setAdapter(x);
	// }

}
