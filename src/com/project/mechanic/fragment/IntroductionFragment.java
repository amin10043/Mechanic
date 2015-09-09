package com.project.mechanic.fragment;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mechanic.R;
import com.project.mechanic.adapter.ExpandIntroduction;
import com.project.mechanic.entity.CommentInObject;
import com.project.mechanic.entity.LikeInObject;
import com.project.mechanic.entity.Object;
import com.project.mechanic.entity.Users;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.inter.GetAllAsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.Deleting;
import com.project.mechanic.service.Saving;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.service.Updating;
import com.project.mechanic.service.UpdatingAllImage;
import com.project.mechanic.utility.Utility;

public class IntroductionFragment extends Fragment implements AsyncInterface,
		GetAllAsyncInterface {

	Utility ut;
	Users CurrentUser;
	PersianDate datePersian;
	View header;
	ExpandableListView exListView;
	ExpandIntroduction exadapter;
	int ObjectID;
	DialogPersonLikedObject ListLiked;
	UpdatingAllImage updating;
	Map<String, String> maps;

	ArrayList<CommentInObject> commentGroup, ReplyGroup;
	Map<CommentInObject, List<CommentInObject>> mapCollection;

	private ImageView peykan6, peykan5;
	public RelativeLayout agency, service, sendSMS, addressRelative,
			emailRelative, profileLinear, personPage, personPost;

	public DialogcmtInobject dialog;
	Fragment fragment;

	public LinearLayout AddLike, AddComment;
	public ImageButton Comment;
	byte[] bitHeader, bytepro, bytefoot;

	LinearLayout.LayoutParams headerParams, footerParams;
	RelativeLayout.LayoutParams addressParams, emailParams, profileParams,
			followParams, shareParams;

	ArrayList<CommentInObject> mylist;
	DataBaseAdapter adapter;
	LinearLayout headImageLinear, footerLinear, likePost;

	TextView txtFax, txtAddress, txtPhone, txtCellphone, txtEmail, txtDesc,
			CountLikeIntroduction, CountCommentIntroduction, namePage,
			countLikePost;

	ImageView headerImage, advertise2, profileImage;
	ImageButton Facebook, Instagram, LinkedIn, Google, Site, Twitter, Pdf1,
			Pdf2, Pdf3, Pdf4;
	Object object;
	byte[] headerbyte, profilebyte, footerbyte;

	SharedPreferences sendDataID;

	Saving saving;
	Deleting deleting;
	Map<String, String> params;

	ProgressDialog ringProgressDialog;

	String serverDate = "";
	ServerDate date;
	boolean flag;

	RelativeLayout phone, cphone, email, map;

	Updating update;

	boolean f1;
	boolean f2;
	boolean f3;
	// RelativeLayout timeLayoutTimeLine;

	ProgressBar loadingProgressHeader, loadingProgressProfile,
			loadingProgressFooter;
	Button followPage, EditPage, shareBtn;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_introduction, null);
		// ((MainActivity) getActivity()).setActivityTitle(R.string.brand);

		adapter = new DataBaseAdapter(getActivity());
		ut = new Utility(getActivity());

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
		agency = (RelativeLayout) header.findViewById(R.id.Layoutlink1);
		service = (RelativeLayout) header.findViewById(R.id.Layoutlink2);
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
				.findViewById(R.id.emailsRelative);

		Facebook = (ImageButton) header.findViewById(R.id.nfacebook);
		Instagram = (ImageButton) header.findViewById(R.id.ninstagram);
		LinkedIn = (ImageButton) header.findViewById(R.id.nlinkedin);
		Google = (ImageButton) header.findViewById(R.id.ngoogle);
		Site = (ImageButton) header.findViewById(R.id.nsite);
		Twitter = (ImageButton) header.findViewById(R.id.ntwtert);

		phone = (RelativeLayout) header.findViewById(R.id.fixedPhone);
		cphone = (RelativeLayout) header.findViewById(R.id.personalMobile);
		map = (RelativeLayout) header.findViewById(R.id.addressRelative);
		email = (RelativeLayout) header.findViewById(R.id.emailsRelative);
		shareBtn = (Button) header.findViewById(R.id.shareIntroduction);

		Pdf1 = (ImageButton) header.findViewById(R.id.btnPdf1_Object);
		Pdf2 = (ImageButton) header.findViewById(R.id.btnPdf2_Object);
		Pdf3 = (ImageButton) header.findViewById(R.id.btnPdf3_Object);
		Pdf4 = (ImageButton) header.findViewById(R.id.btnPdf4_Object);
		profileLinear = (RelativeLayout) header
				.findViewById(R.id.linear_id_profile_introduction_page);
		footerLinear = (LinearLayout) header.findViewById(R.id.footerint);
		EditPage = (Button) header.findViewById(R.id.ImgbtnEdit);
		likePost = (LinearLayout) header
				.findViewById(R.id.likePostIntroduction);

		countLikePost = (TextView) header.findViewById(R.id.countlllllll);
		personPage = (RelativeLayout) header.findViewById(R.id.countLiketext);
		personPost = (RelativeLayout) header.findViewById(R.id.countLikeqqz);

		loadingProgressHeader = (ProgressBar) header
				.findViewById(R.id.header_progress_header);

		loadingProgressProfile = (ProgressBar) header
				.findViewById(R.id.profile_progress_profile);
		loadingProgressFooter = (ProgressBar) header
				.findViewById(R.id.footer_progress_footer);

		followPage = (Button) header.findViewById(R.id.followPage);

		sendDataID = getActivity().getSharedPreferences("Id", 0);
		ObjectID = sendDataID.getInt("main_Id", -1);

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

		exListView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView arg0, int arg1) {
				switch (arg1) {
				case SCROLL_STATE_IDLE:

					break;

				case SCROLL_STATE_TOUCH_SCROLL:

				default:
					break;
				}
			}

			@Override
			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub

			}
		});
		adapter.open();

		if (CurrentUser == null) {
		} else {
			if (adapter.isUserLikeIntroductionPage(CurrentUser.getId(),
					ObjectID, 0)) {
				followPage.setBackgroundColor(getResources().getColor(
						R.color.green));
				// personPage.setBackgroundResource(R.drawable.count_like);
			} else {
				followPage.setBackgroundColor(getResources().getColor(
						R.color.gray));
				// personPage.setBackgroundResource(R.drawable.count_like_off);

			}
			if (adapter.isUserLikeIntroductionPage(CurrentUser.getId(),
					ObjectID, 1)) {
				likePost.setBackgroundResource(R.drawable.like_on);
				personPost.setBackgroundResource(R.drawable.count_like);

			} else {
				likePost.setBackgroundResource(R.drawable.like_off);
				personPost.setBackgroundResource(R.drawable.count_like_off);

			}
		}
		adapter.close();

		profileParams = new RelativeLayout.LayoutParams(
				profileLinear.getLayoutParams());

		profileParams.height = ut.getScreenwidth() / 4;
		profileParams.width = ut.getScreenwidth() / 4;
		profileParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		profileParams.addRule(RelativeLayout.BELOW, R.id.namePage);
		profileParams.setMargins(0, 10, 0, 0);

		profileImage.setLayoutParams(profileParams);

		headerParams = new LinearLayout.LayoutParams(
				headImageLinear.getLayoutParams());
		headerParams.height = ut.getScreenwidth();

		footerParams = new LinearLayout.LayoutParams(
				footerLinear.getLayoutParams());
		footerParams.height = ut.getScreenwidth();
		footerParams.width = ut.getScreenwidth();

		followParams = new RelativeLayout.LayoutParams(
				profileLinear.getLayoutParams());

		followParams.width = ut.getScreenwidth() / 4;
		followParams.height = ut.getScreenwidth() / 10;
		followParams.setMargins(0, 10, 0, 0);
		followParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		followParams.addRule(RelativeLayout.BELOW, R.id.icon_pro);

		followPage.setLayoutParams(followParams);

		shareParams = new RelativeLayout.LayoutParams(
				profileLinear.getLayoutParams());

		shareParams.height = ut.getScreenwidth() / 10;
		shareParams.width = ut.getScreenwidth() / 4;
		shareParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		shareParams.addRule(RelativeLayout.BELOW, R.id.ImgbtnEdit);
		shareParams.setMargins(0, 10, 0, 0);

		shareBtn.setLayoutParams(shareParams);

		RelativeLayout.LayoutParams shareParams2 = new RelativeLayout.LayoutParams(
				profileLinear.getLayoutParams());

		shareParams2.height = ut.getScreenwidth() / 10;
		shareParams2.width = ut.getScreenwidth() / 4;
		shareParams2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		shareParams2.addRule(RelativeLayout.BELOW, R.id.followPage);
		shareParams2.setMargins(0, 10, 0, 0);

		shareBtn.setLayoutParams(shareParams);
		shareBtn.setTextSize(12);

		adapter.open();
		int countcmt = adapter.CommentInObject_count(ObjectID);
		CountCommentIntroduction.setText(String.valueOf(countcmt));

		int countlike = adapter.LikeInObject_count(ObjectID, 0);
		CountLikeIntroduction.setText(String.valueOf(countlike));

		int countlikePo = adapter.LikeInObject_count(ObjectID, 1);
		countLikePost.setText(String.valueOf(countlikePo));

		object = adapter.getObjectbyid(ObjectID);
		adapter.close();

		update = new Updating(getActivity());
		update.delegate = IntroductionFragment.this;
		String[] para = new String[4];
		para[0] = "ImageServerDate";
		para[1] = String.valueOf(object.getId());
		para[2] = "masoud";
		para[3] = "0";
		update.execute(para);

		// updating = new UpdatingAllImage(getActivity());
		// updating.delegate = this;
		// maps = new LinkedHashMap<String, String>();
		// // maps.put("tableName", "Object1");
		// maps.put("tableName", "All");
		// maps.put("Id", String.valueOf(ObjectID));
		// maps.put("fromDate1", object.getImage1ServerDate());
		// maps.put("fromDate2", object.getImage2ServerDate());
		// maps.put("fromDate3", object.getImage3ServerDate());
		// updating.execute(maps);

		// ringProgressDialog = ProgressDialog.show(getActivity(), "",
		// "به روز رسانی تصاویر...", true);

		loadingProgressHeader.setVisibility(View.VISIBLE);
		loadingProgressProfile.setVisibility(View.VISIBLE);
		loadingProgressFooter.setVisibility(View.VISIBLE);

		if (ut.getCurrentUser() != null) {
			if (ut.getCurrentUser().getId() != object.getId()) {
				if (!ut.isNetworkConnected()) {
					adapter.open();
					adapter.insertVisitToDb(ut.getCurrentUser().getId(), 2,
							object.getId());
					adapter.close();
				} else if ((ut.isNetworkConnected())) {
					adapter.open();
					// ارسال اطلاعات به جدول ویزیت سرور
					// ارسال اطلاعات از جدول ویزیت گوشی به جدول ویزیت سرور
					adapter.deleteVisit();
					adapter.close();
				}
			}
		}

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
				HeaderActive.setBackgroundResource(R.drawable.no_image_header);
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
				profileActive.setBackgroundResource(R.drawable.no_img_profile);
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

			// return t;

		}

		adapter.close();

		Users user = ut.getCurrentUser();
		if (user == null || object.getUserId() != user.getId()) {
			EditPage.setVisibility(View.INVISIBLE);
			Toast.makeText(getActivity(),
					"userid dar database sqlite be soorat dasti 0 save shode",
					Toast.LENGTH_LONG).show();
			shareBtn.setLayoutParams(shareParams2);
		} else {
			EditPage.setVisibility(View.VISIBLE);
			EditPage.setLayoutParams(followParams);

		}

		if (user != null && object.getUserId() == user.getId())
			followPage.setVisibility(View.GONE);
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

		String PathHeader = object.getImagePath1();
		String PathProfile = object.getImagePath2();
		String PathFooter = object.getImagePath3();

		Bitmap bmpHeader = BitmapFactory.decodeFile(PathHeader);
		Bitmap bmpProfile = BitmapFactory.decodeFile(PathProfile);
		Bitmap bmpfooter = BitmapFactory.decodeFile(PathFooter);

		// bitHeader = object.getImage1();

		if (bmpHeader != null) {
			// Bitmap bmp1 = BitmapFactory.decodeByteArray(bitHeader, 0,
			// bitHeader.length);

			headerImage.setImageBitmap(bmpHeader);
		} else

			headerImage.setBackgroundResource(R.drawable.no_image_header);
		// /////////////////////
		// bytepro = object.getImage2();
		if (bmpProfile != null) {
			// Bitmap bmp2 = BitmapFactory.decodeByteArray(bytepro, 0,
			// bytepro.length);

			profileImage.setImageBitmap(bmpProfile);

		} else {
			profileImage.setBackgroundResource(R.drawable.no_img_profile);

		}
		// ///////////////////////
		// bytefoot = object.getImage3();
		if (bmpfooter != null) {
			// Bitmap bmp3 = BitmapFactory.decodeByteArray(bytefoot, 0,
			// bytefoot.length);

			advertise2.setImageBitmap(bmpfooter);

		} else
			advertise2.setBackgroundResource(R.drawable.no_image_header);

		txtFax.setText(object.getFax());
		txtPhone.setText(object.getPhone());
		txtCellphone.setText(object.getCellphone());
		txtEmail.setText(object.getEmail());
		txtAddress.setText(object.getAddress());
		txtDesc.setText(object.getDescription());

		if (object.getDescription() == null)
			txtDesc.setVisibility(View.GONE);

		// if mainObjectId =1 >>>>>> namayandegi va khadamat faal hast
		// if mainObjectId =2 >>>>>> namayandegi va khadamat gheyr faal hast
		// if MainObjectId =3 >>>>>> namayandegi faal hast
		// if MainObjectId =4 >>>>>> khadamat faal hast

		if (object.getObjectBrandTypeId() == 1) {
			agency.setVisibility(View.VISIBLE);
			service.setVisibility(View.VISIBLE);

		}
		if (object.getObjectBrandTypeId() == 2) {
			agency.setVisibility(View.GONE);
			service.setVisibility(View.GONE);
		}
		if (object.getObjectBrandTypeId() == 3) {
			agency.setVisibility(View.VISIBLE);
			service.setVisibility(View.GONE);
		}
		if (object.getObjectBrandTypeId() == 4) {
			agency.setVisibility(View.GONE);
			service.setVisibility(View.VISIBLE);
		}

		if (object.getFacebook() != null
				&& !object.getFacebook().equals("null"))
			Facebook.setImageResource(R.drawable.facebook);
		else
			Facebook.setImageResource(R.drawable.facebook_off);

		if (object.getInstagram() != null
				&& !object.getInstagram().equals("null"))
			Instagram.setImageResource(R.drawable.insta);
		else
			Instagram.setImageResource(R.drawable.insta_off);

		if (object.getLinkedIn() != null
				&& !object.getLinkedIn().equals("null"))
			LinkedIn.setImageResource(R.drawable.lnkin);
		else
			LinkedIn.setImageResource(R.drawable.lnkin_off);

		if (object.getGoogle() != null && !object.getGoogle().equals("null"))
			Google.setImageResource(R.drawable.google);
		else
			Google.setImageResource(R.drawable.google_off);

		if (object.getSite() != null && !object.getSite().equals("null"))
			Site.setImageResource(R.drawable.internet);
		else
			Site.setImageResource(R.drawable.internet_off);

		if (object.getTwitter() != null && !object.getTwitter().equals("null"))
			Twitter.setImageResource(R.drawable.twtr);
		else
			Twitter.setImageResource(R.drawable.twtr_off);

		if (object.getPdf1() != null && !object.getPdf1().equals("null"))
			Pdf1.setImageResource(R.drawable.ic_catalog);
		else
			Pdf1.setImageResource(R.drawable.ic_catalog_off);

		if (object.getPdf2() != null && !object.getPdf2().equals("null"))
			Pdf2.setImageResource(R.drawable.ic_price);

		else
			Pdf2.setImageResource(R.drawable.ic_price_off);

		if (object.getPdf3() != null && !object.getPdf3().equals("null"))
			Pdf3.setImageResource(R.drawable.ic_pdf);
		else
			Pdf3.setImageResource(R.drawable.ic_pdf_off);

		if (object.getPdf4() != null && !object.getPdf4().equals("null"))
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
		headerImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String pathImage = object.getImagePath1();
				String namePage = object.getName();
				
				DialogShowImage showImageDialog = new DialogShowImage(
						getActivity(), pathImage , namePage);
				showImageDialog.show();
				
			}
		});

		profileImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				String PathProfile = object.getImagePath2();
				String namePage = object.getName();
				DialogShowImage showImageDialog = new DialogShowImage(
						getActivity(), PathProfile , namePage);
				showImageDialog.show();
				
				

			}
		});
		
		advertise2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String pathImage = object.getImagePath3();
				String namePage = object.getName();
				
				DialogShowImage showImageDialog = new DialogShowImage(
						getActivity(), pathImage , namePage);
				showImageDialog.show();				
			}
		});

		personPage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				adapter.open();

				ArrayList<LikeInObject> likedist = adapter
						.getAllLikeFromObject(ObjectID, 0);

				adapter.close();
				if (likedist.size() == 0) {
					Toast.makeText(getActivity(), "لایکی ثبت نشده است", 0)
							.show();
				} else {
					DialogPersonLikedObject dia = new DialogPersonLikedObject(
							getActivity(), ObjectID, likedist);
					dia.show();

				}
			}
		});

		personPost.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				adapter.open();
				ArrayList<LikeInObject> likedist = adapter
						.getAllLikeFromObject(ObjectID, 1);

				adapter.close();
				if (likedist.size() == 0) {
					Toast.makeText(getActivity(), "لایکی ثبت نشده است", 0)
							.show();
				} else {
					DialogPersonLikedObject dia = new DialogPersonLikedObject(
							getActivity(), ObjectID, likedist);
					dia.show();
				}
			}
		});

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

		final SharedPreferences pageId = getActivity().getSharedPreferences(
				"Id", 0);
		agency.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				FragmentTransaction trans = getActivity()
						.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new ProvinceFragment());
				trans.commit();
				pageId.edit().putInt("brandID", object.getId()).commit();
				pageId.edit().putInt("IsAgency", 1).commit();
				pageId.edit()
						.putInt("main object id", object.getMainObjectId())
						.commit();
				Toast.makeText(
						getActivity(),
						"mainObjectId send brand = " + object.getMainObjectId()
								+ " agency ", 0).show();
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

		likePost.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (CurrentUser == null) {
					Toast.makeText(getActivity(),
							"برای درج لایک ابتدا باید وارد شوید",
							Toast.LENGTH_SHORT).show();
				} else {

					date = new ServerDate(getActivity());
					date.delegate = IntroductionFragment.this;
					date.execute("");

					flag = false;

				}

			}

		});

		followPage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (CurrentUser == null) {
					Toast.makeText(getActivity(),
							"برای درج لایک ابتدا باید وارد شوید",
							Toast.LENGTH_SHORT).show();
				} else {

					date = new ServerDate(getActivity());
					date.delegate = IntroductionFragment.this;
					date.execute("");

					flag = true;

				}
			}

		});

		service.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				FragmentTransaction trans = getActivity()
						.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new ProvinceFragment());
				trans.commit();

				pageId.edit().putInt("brandID", object.getId()).commit();
				pageId.edit().putInt("IsAgency", 0).commit();
				pageId.edit()
						.putInt("main object id", object.getMainObjectId())
						.commit();
				Toast.makeText(
						getActivity(),
						"MainObjectId = " + object.getMainObjectId()
								+ "service", 0).show();

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

	@Override
	public void processFinish(String output) {

		if (output.contains("---")) {
			if (ringProgressDialog != null)
				ringProgressDialog.dismiss();

			loadingProgressHeader.setVisibility(View.GONE);
			loadingProgressProfile.setVisibility(View.GONE);
			loadingProgressFooter.setVisibility(View.GONE);
			return;
		} else if (output.contains("-")) {
			String[] strs = output.split(Pattern.quote("-")); // each

			String s1, s2, s3;

			serverDate = s1 = strs[0];
			s2 = strs[1];
			s3 = strs[2];

			if (object.getImage1ServerDate() == null
					|| !object.getImage1ServerDate().equals(s1)) {
				object.setImage1ServerDate(s1);
				f1 = true;
			}
			if (object.getImage2ServerDate() == null
					|| !object.getImage2ServerDate().equals(s2)) {
				object.setImage2ServerDate(s2);
				f2 = true;
			}

			if (object.getImage3ServerDate() == null
					|| !object.getImage3ServerDate().equals(s3)) {
				object.setImage3ServerDate(s3);
				f3 = true;
			}

			if (f1 || f2 || f3) {
				updating = new UpdatingAllImage(getActivity());
				updating.delegate = this;
				maps = new LinkedHashMap<String, String>();
				// maps.put("tableName", "Object1");
				maps.put("tableName", "All");
				maps.put("Id", String.valueOf(ObjectID));
				maps.put("fromDate1", object.getImage1ServerDate());
				maps.put("fromDate2", object.getImage2ServerDate());
				maps.put("fromDate3", object.getImage3ServerDate());
				updating.execute(maps);

			} else {
				loadingProgressHeader.setVisibility(View.GONE);
				loadingProgressProfile.setVisibility(View.GONE);
				loadingProgressFooter.setVisibility(View.GONE);
				Toast.makeText(getActivity(),
						"به روز رسانی تصاویر با موفقیت انجام شد",
						Toast.LENGTH_SHORT).show();
			}

		} else {
			int id = -1;
			try {
				id = Integer.valueOf(output);
				// این متغیر مشخص کنندهلایک صفحه و لایک پست می بایشد
				int comId;
				if (flag)
					// لایک صفحه
					comId = 0;
				else
					// لایک پست
					comId = 1;

				adapter.open();

				if (adapter.isUserLikeIntroductionPage(CurrentUser.getId(),
						ObjectID, comId)) {

					adapter.deleteLikeIntroduction(CurrentUser.getId(),
							ObjectID, comId);
					int countlike = adapter.LikeInObject_count(ObjectID, comId);

					if (flag) {
						followPage.setBackgroundColor(getResources().getColor(
								R.color.gray));
						// personPage
						// .setBackgroundResource(R.drawable.count_like_off);
						CountLikeIntroduction
								.setText(String.valueOf(countlike));
						if (ringProgressDialog != null)
							ringProgressDialog.dismiss();

					} else {
						likePost.setBackgroundResource(R.drawable.like_off);
						personPost
								.setBackgroundResource(R.drawable.count_like_off);
						countLikePost.setText(String.valueOf(countlike));
						if (ringProgressDialog != null)
							ringProgressDialog.dismiss();
					}

				} else {
					adapter.insertLikeInObjectToDb(id, CurrentUser.getId(),
							ObjectID, serverDate, comId);
					int countlike = adapter.LikeInObject_count(ObjectID, comId);

					if (flag) {
						followPage.setBackgroundColor(getResources().getColor(
								R.color.green));
						// personPage.setBackgroundResource(R.drawable.count_like);
						CountLikeIntroduction
								.setText(String.valueOf(countlike));
						if (ringProgressDialog != null)
							ringProgressDialog.dismiss();
					} else {
						likePost.setBackgroundResource(R.drawable.like_on);
						personPost.setBackgroundResource(R.drawable.count_like);
						countLikePost.setText(String.valueOf(countlike));
						if (ringProgressDialog != null)
							ringProgressDialog.dismiss();
					}

				}
				adapter.close();

			} catch (NumberFormatException e) {
				if (output != null
						&& !(output.contains("Exception") || output
								.contains("java"))) {
					// این متغیر مشخص کنندهلایک صفحه و لایک پست می بایشد
					int comId;
					if (flag)
						// لایک صفحه
						comId = 0;
					else
						// لایک پست
						comId = 1;
					adapter.open();
					if (adapter.isUserLikeIntroductionPage(CurrentUser.getId(),
							ObjectID, comId)) {

						params = new LinkedHashMap<String, String>();
						deleting = new Deleting(getActivity());
						deleting.delegate = IntroductionFragment.this;

						params.put("TableName", "LikeInObject");
						params.put("UserId",
								String.valueOf(CurrentUser.getId()));
						params.put("ObjectId", String.valueOf(ObjectID));
						params.put("CommentId", String.valueOf(comId));

						deleting.execute(params);

						ringProgressDialog = ProgressDialog.show(getActivity(),
								"", "لطفا منتظر بمانید...", true);

						ringProgressDialog.setCancelable(true);
						new Thread(new Runnable() {

							@Override
							public void run() {

								try {

									Thread.sleep(10000);

								} catch (Exception e) {

								}
							}
						}).start();
						adapter.close();

					} else {
						adapter.open();
						params = new LinkedHashMap<String, String>();
						saving = new Saving(getActivity());
						saving.delegate = IntroductionFragment.this;

						params.put("TableName", "LikeInObject");

						params.put("UserId",
								String.valueOf(CurrentUser.getId()));
						params.put("ObjectId", String.valueOf(ObjectID));
						params.put("Date", output);
						params.put("CommentId", String.valueOf(comId));

						params.put("IsUpdate", "0");
						params.put("Id", "0");

						serverDate = output;

						saving.execute(params);

						ringProgressDialog = ProgressDialog.show(getActivity(),
								"", "لطفا منتظر بمانید...", true);

						ringProgressDialog.setCancelable(true);
						new Thread(new Runnable() {

							@Override
							public void run() {

								try {

									Thread.sleep(10000);

								} catch (Exception e) {

								}
							}
						}).start();
						adapter.close();

					}
					adapter.close();

				} else {
					Toast.makeText(getActivity(),
							"خطا در ثبت. پاسخ نا مشخص از سرور",
							Toast.LENGTH_SHORT).show();
					loadingProgressHeader.setVisibility(View.GONE);
					loadingProgressProfile.setVisibility(View.GONE);
					loadingProgressFooter.setVisibility(View.GONE);
				}
			}

			catch (Exception e) {

				Toast.makeText(getActivity(), "خطا در ثبت", Toast.LENGTH_SHORT)
						.show();
				loadingProgressHeader.setVisibility(View.GONE);
				loadingProgressProfile.setVisibility(View.GONE);
				loadingProgressFooter.setVisibility(View.GONE);
			}
		}
	}

	@Override
	public void processFinish(List<byte[]> output) {

		if (output != null && output.size() > 0) {

			adapter.open();

			if (f1)
				if (output.get(0) != null) {
					ut.CreateFile(output.get(0), object.getId(), "Mechanical",
							"Profile", "header", "Object");

					// adapter.UpdateHeaderImageObject(ObjectID, output.get(0));
					object = adapter.getObjectbyid(ObjectID);

					String PathImageHeader = "";
					PathImageHeader = object.getImagePath1();
					Bitmap b = BitmapFactory.decodeFile(PathImageHeader);

					if (b != null)
						headerImage.setImageBitmap(b);
					else
						headerImage
								.setBackgroundResource(R.drawable.no_image_header);
					adapter.updateObjectImage1ServerDate(ObjectID, serverDate);
				}
			if (f2)
				if (output.get(1) != null) {
					ut.CreateFile(output.get(1), object.getId(), "Mechanical",
							"Profile", "profile", "Object");
					object = adapter.getObjectbyid(ObjectID);

					String PathImageProfile = "";
					PathImageProfile = object.getImagePath2();
					// adapter.UpdateProfileImageObject(ObjectID,
					// output.get(1));
					Bitmap b = BitmapFactory.decodeFile(PathImageProfile);

					if (b != null)
						profileImage.setImageBitmap(b);
					else
						profileImage
								.setBackgroundResource(R.drawable.no_img_profile);
					adapter.updateObjectImage2ServerDate(ObjectID, serverDate);
				}

			if (f3)
				if (output.get(2) != null) {
					ut.CreateFile(output.get(2), object.getId(), "Mechanical",
							"Profile", "footer", "Object");
					object = adapter.getObjectbyid(ObjectID);

					String PathImageFooter = "";
					PathImageFooter = object.getImagePath3();

					// adapter.UpdateFooterImageObject(ObjectID, output.get(2));
					Bitmap b = BitmapFactory.decodeFile(PathImageFooter);

					if (b != null)
						advertise2.setImageBitmap(b);
					else
						advertise2
								.setBackgroundResource(R.drawable.no_image_header);

					adapter.updateObjectImage3ServerDate(ObjectID, serverDate);

				}
			Toast.makeText(getActivity(),
					"به روز رسانی تصاویر با موفقیت انجام شد",
					Toast.LENGTH_SHORT).show();

			loadingProgressHeader.setVisibility(View.GONE);
			loadingProgressProfile.setVisibility(View.GONE);
			loadingProgressFooter.setVisibility(View.GONE);

			adapter.close();

		}

		if (ringProgressDialog != null)
			ringProgressDialog.dismiss();

	}

	public int getObjectId() {
		return ObjectID;
	}
}
