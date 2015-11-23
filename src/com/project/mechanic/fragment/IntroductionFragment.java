package com.project.mechanic.fragment;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mechanic.R;
import com.project.mechanic.Action.FloatingActionButton;
import com.project.mechanic.adapter.ExpandIntroduction;
import com.project.mechanic.adapter.PosttitleListadapter;
import com.project.mechanic.entity.CommentInObject;
import com.project.mechanic.entity.LikeInObject;
import com.project.mechanic.entity.Object;
import com.project.mechanic.entity.Post;
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

	Context context;
	Utility ut;
	Users currentUser;
	View header, Posts;
	// view,
	ListView PostList;

	// ExpandableListView exListView;
	ExpandIntroduction exadapter;
	int ObjectID, gp;
	DialogPersonLikedObject ListLiked;
	UpdatingAllImage updating;
	Map<String, String> maps;
	ArrayList<CommentInObject> commentGroup, ReplyGroup;
	ArrayList<Post> ArrayPosts;
	Map<CommentInObject, List<CommentInObject>> mapCollection;
	public RelativeLayout agency, service, sendSMS, addressRelative,
			emailRelative, profileLinear, personPage, personPost, phone,
			cphone, email, map , shareBtn , followPage;
	public DialogcmtInobject dialog;
	public LinearLayout AddLike, AddComment, headImageLinear, footerLinear,
			likePost;
	public ImageButton Comment;
	LinearLayout.LayoutParams headerParams, footerParams;
	RelativeLayout.LayoutParams addressParams, emailParams, profileParams,
			followParams, shareParams, shareParams2;
	DataBaseAdapter adapter;
	TextView txtFax, txtAddress, txtPhone, txtCellphone, txtEmail, txtDesc,
			CountLikeIntroduction, CountCommentIntroduction, namePage,
			countLikePost;
	ImageView headerImage, footerImage, profileImage, reaport;
	ImageButton Facebook, Instagram, LinkedIn, Google, Site, Twitter, Pdf1,
			Pdf2, Pdf3, Pdf4;
	Object object;
	byte[] headerbyte, profilebyte, footerbyte;
	SharedPreferences sendDataID, pageId;
	Saving saving;
	Deleting deleting;
	Map<String, String> params;
	ProgressDialog ringProgressDialog;
	String serverDate = "";
	ServerDate date;
	Updating update;
	boolean flag, f1, f2, f3, LikeOrComment, commentClick = false;
	List<String> menuItems;

	ProgressBar loadingProgressHeader, loadingProgressProfile,
			loadingProgressFooter;
	Button  EditPage,  ShowPostBtn, btnShowPost;
	int userId, commentId = 0;

	FloatingActionButton action;

	DialogpostTitleFragment MyDialog;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// ((MainActivity) getActivity()).setActivityTitle(R.string.brand);
		adapter = new DataBaseAdapter(getActivity());
		ut = new Utility(getActivity());

		// define rootView and header Layout
		// view = inflater.inflate(R.layout.fragment_introduction, null);
		header = getActivity().getLayoutInflater().inflate(
				R.layout.header_introduction, null);
		Posts = inflater.inflate(R.layout.fragment_titlepost, null);
		PostList = (ListView) Posts.findViewById(R.id.lstComment);
		// PostList.addHeaderView(header);

		// define Views : find view by Id
		findView();

		pageId = getActivity().getSharedPreferences("Id", 0);

		if (getArguments().getString("Id") != null)
			ObjectID = Integer.valueOf(getArguments().getString("Id"));

		currentUser = ut.getCurrentUser();

		// set values for parameter and variable
		setValues();

		// fill expand ListView
		// fillExpandListViewCommnet();

		// fill Post List View
		PostList.addHeaderView(header);
		fillListView();

		// set layoutParams
		setLayoutParams();

		// setImage background headerImage , ProfileImage , FooterImage
		setImage();

		// set on or off for follow and like button
		setStateButtonFollowLike();

		// get image from server
		getImageFromServer();

		// set Image on or off for link network social and download link
		setStateLinkSocialAndDownload();

		// set visibility for Agency and Service Button
		setVisibilityItems();

		// on click action for items link Network and Link Download
		SetonItemsClickNetworkAndDownload();

		// on click action for items information contact
		setOnItemClickInformationContact();

		// on click image
		setonClickImage();

		// action for like ,follow m comment , edit , share
		LikeCommentAction();

		// action click for Agency , Service , settings , post button
		AgencyServiceSettingActionButton();

		// action click for poeple liked page btn
		showPeopleLikedBtn();

		// for manage footer slide image agahi
		// addComment();
		ut.ShowFooterAgahi(getActivity(), false, 6);

		if (currentUser != null)
			if (currentUser.getId() == object.getUserId())
				action.setVisibility(View.VISIBLE);
			else
				action.setVisibility(View.GONE);
		else
			action.setVisibility(View.GONE);

		// if (PostList.getCount() > 0)
		return Posts;
		// else
		// return header;

	}

	// public void updateList() {
	// sendDataID = getActivity().getSharedPreferences("Id", 0);
	// final int ObjectID = sendDataID.getInt("main_Id", -1);
	//
	// adapter.open();
	// commentGroup = adapter.getAllCommentInObjectById(ObjectID, 0);
	// mapCollection = new LinkedHashMap<CommentInObject,
	// List<CommentInObject>>();
	//
	// List<CommentInObject> reply = null;
	// for (CommentInObject comment : commentGroup) {
	// reply = adapter.getReplyCommentIntroduction(ObjectID,
	// comment.getId());
	// mapCollection.put(comment, reply);
	// }
	//
	// int countcmt = adapter.CommentInObject_count(ObjectID);
	// CountCommentIntroduction.setText(String.valueOf(countcmt));
	// exadapter = new ExpandIntroduction(getActivity(),
	// (ArrayList<CommentInObject>) commentGroup, mapCollection, this,
	// ObjectID);
	// adapter.close();
	// exadapter.notifyDataSetChanged();
	//
	// exListView.setAdapter(exadapter);
	//
	// }

	@Override
	public void processFinish(String output) {

		if (LikeOrComment == true) {
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
						// دنبال کردن
						comId = 0;
					else
						// لایک پست
						comId = 1;

					adapter.open();

					if (adapter.isUserLikeIntroductionPage(currentUser.getId(),
							ObjectID, comId)) {

						adapter.deleteLikeIntroduction(currentUser.getId(),
								ObjectID, comId);
						int countlike = adapter.LikeInObject_count(ObjectID,
								comId);

						if (flag) {
							followPage.setBackgroundColor(getResources()
									.getColor(R.color.gray));
							// personPage
							// .setBackgroundResource(R.drawable.count_like_off);
							CountLikeIntroduction.setText(String
									.valueOf(countlike));
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
						adapter.insertLikeInObjectToDb(id, currentUser.getId(),
								ObjectID, serverDate, comId);
						int countlike = adapter.LikeInObject_count(ObjectID,
								comId);

						if (flag) {
							followPage.setBackgroundColor(getResources()
									.getColor(R.color.green));
							// personPage.setBackgroundResource(R.drawable.count_like);
							CountLikeIntroduction.setText(String
									.valueOf(countlike));
							if (ringProgressDialog != null)
								ringProgressDialog.dismiss();
						} else {
							likePost.setBackgroundResource(R.drawable.like_on);
							personPost
									.setBackgroundResource(R.drawable.count_like);
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
						if (adapter.isUserLikeIntroductionPage(
								currentUser.getId(), ObjectID, comId)) {

							params = new LinkedHashMap<String, String>();
							if (getActivity() != null) {

								deleting = new Deleting(getActivity());
								deleting.delegate = IntroductionFragment.this;

								params.put("TableName", "LikeInObject");
								params.put("UserId",
										String.valueOf(currentUser.getId()));
								params.put("ObjectId", String.valueOf(ObjectID));
								params.put("CommentId", String.valueOf(comId));

								deleting.execute(params);
							}
							ringProgressDialog = ProgressDialog.show(
									getActivity(), "", "لطفا منتظر بمانید...",
									true);

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

							if (getActivity() != null) {
								saving = new Saving(getActivity());
								saving.delegate = IntroductionFragment.this;

								params.put("TableName", "LikeInObject");

								params.put("UserId",
										String.valueOf(currentUser.getId()));
								params.put("ObjectId", String.valueOf(ObjectID));
								params.put("Date", output);
								params.put("ModifyDate", output);

								params.put("CommentId", String.valueOf(comId));

								params.put("IsUpdate", "0");
								params.put("Id", "0");

								serverDate = output;

								saving.execute(params);
							}
							ringProgressDialog = ProgressDialog.show(
									getActivity(), "", "لطفا منتظر بمانید...",
									true);

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

					Toast.makeText(getActivity(), "خطا در ثبت",
							Toast.LENGTH_SHORT).show();
					loadingProgressHeader.setVisibility(View.GONE);
					loadingProgressProfile.setVisibility(View.GONE);
					loadingProgressFooter.setVisibility(View.GONE);
				}
			}
		} else {
			int id = -1;

			try {
				id = Integer.valueOf(output);

				adapter.open();

				adapter.insertCommentObjecttoDb(id,
						ut.inputComment(getActivity()), ObjectID,
						currentUser.getId(), serverDate, commentId);

				adapter.close();
				ut.ToEmptyComment(getActivity());

				if (ringProgressDialog != null)
					ringProgressDialog.dismiss();

				// fillExpandListViewCommnet();

			} catch (NumberFormatException e) {

				if (output != null
						&& !(output.contains("Exception") || output
								.contains("java"))) {

					adapter.open();
					params = new LinkedHashMap<String, String>();

					if (getActivity() != null)
						saving = new Saving(getActivity());
					saving.delegate = IntroductionFragment.this;

					params.put("TableName", "CommentInObject");

					params.put("Desk", ut.inputComment(getActivity()));
					params.put("ObjectId", String.valueOf(ObjectID));
					params.put("UserId", String.valueOf(currentUser.getId()));
					params.put("Date", output);
					params.put("ModifyDate", output);

					params.put("CommentId", String.valueOf(commentId));

					serverDate = output;

					params.put("IsUpdate", "0");
					params.put("Id", "0");

					saving.execute(params);

					ringProgressDialog = ProgressDialog.show(getActivity(), "",
							"لطفا منتظر بمانید...", true);

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
					Toast.makeText(getActivity(),
							"خطا در ثبت. پاسخ نا مشخص از سرور",
							Toast.LENGTH_SHORT).show();
				}
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
						profileImage.setImageBitmap(Utility
								.getRoundedCornerBitmap(b, 20));
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
						footerImage.setImageBitmap(b);
					else
						footerImage
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

	private void findView() {
		// exListView = (ExpandableListView) view
		// .findViewById(R.id.ExpandIntroduction);

		headerImage = (ImageView) header
				.findViewById(R.id.imgvadvertise_Object);
		footerImage = (ImageView) header
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
		 shareBtn = (RelativeLayout) header.findViewById(R.id.b);

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

		followPage = (RelativeLayout) header.findViewById(R.id.follow_follow);
		reaport = (ImageView) header.findViewById(R.id.reportImage);

		action = (FloatingActionButton) Posts.findViewById(R.id.fab);
	}

	// private void fillExpandListViewCommnet() {
	//
	// adapter.open();
	// commentGroup = adapter.getAllCommentInObjectById(ObjectID, 0);
	// mapCollection = new LinkedHashMap<CommentInObject,
	// List<CommentInObject>>();
	//
	// List<CommentInObject> reply = null;
	// for (CommentInObject comment : commentGroup) {
	// reply = adapter.getReplyCommentIntroduction(ObjectID,
	// comment.getId());
	// mapCollection.put(comment, reply);
	// }
	//
	// adapter.close();
	//
	// exadapter = new ExpandIntroduction(getActivity(),
	// (ArrayList<CommentInObject>) commentGroup, mapCollection, this,
	// ObjectID);
	// exListView.addHeaderView(header);
	//
	// exListView.setAdapter(exadapter);
	// if (commentClick == true)
	// exListView.setSelectedGroup(mapCollection.size() - 1);
	//
	// }

	private void fillListView() {
		adapter.open();
		ArrayPosts = adapter.getAllPost(object.getUserId());
		PosttitleListadapter ListAdapterPost = new PosttitleListadapter(
				getActivity(), R.layout.raw_posttitle, ArrayPosts,
				IntroductionFragment.this);
		PostList.setAdapter(ListAdapterPost);
	}

	private void setStateButtonFollowLike() {

		adapter.open();

		if (currentUser == null) {
		} else {
			if (adapter.isUserLikeIntroductionPage(currentUser.getId(),
					ObjectID, 0)) {
				followPage.setBackgroundColor(getResources().getColor(
						R.color.green));
				// personPage.setBackgroundResource(R.drawable.count_like);
			} else {
				followPage.setBackgroundColor(getResources().getColor(
						R.color.gray));
				// personPage.setBackgroundResource(R.drawable.count_like_off);

			}
			if (adapter.isUserLikeIntroductionPage(currentUser.getId(),
					ObjectID, 1)) {
				likePost.setBackgroundResource(R.drawable.like_on);
				personPost.setBackgroundResource(R.drawable.count_like);

			} else {
				likePost.setBackgroundResource(R.drawable.like_off);
				personPost.setBackgroundResource(R.drawable.count_like_off);

			}
		}
		adapter.close();

		if (currentUser == null || object.getUserId() != currentUser.getId()) {
			EditPage.setVisibility(View.INVISIBLE);
			Toast.makeText(getActivity(),
					"userid dar database sqlite be soorat dasti 0 save shode",
					Toast.LENGTH_LONG).show();
//			shareBtn.setLayoutParams(shareParams2);
		} else {
			EditPage.setVisibility(View.VISIBLE);
			EditPage.setLayoutParams(followParams);

		}

	}

	private void setLayoutParams() {

		// profileParams = new RelativeLayout.LayoutParams(
		// profileLinear.getLayoutParams());
		
		int marginTop = ut.getScreenwidth()-(ut.getScreenwidth() /8);

		FrameLayout profileFrame = (FrameLayout) header
				.findViewById(R.id.frameLayoutHeader);
		FrameLayout.LayoutParams profileParams = new FrameLayout.LayoutParams(
				profileFrame.getLayoutParams());

		profileParams.height = ut.getScreenwidth() / 4;
		profileParams.width = ut.getScreenwidth() / 4;
		profileParams.gravity = Gravity.CENTER_HORIZONTAL;
		
//		profileParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//		profileParams.addRule(RelativeLayout.BELOW, R.id.namePage);
		profileParams.setMargins(0, marginTop, 0, 0);

		profileImage.setLayoutParams(profileParams);

		FrameLayout.LayoutParams headerParams = new FrameLayout.LayoutParams(
				profileFrame.getLayoutParams());

		headerParams.height = ut.getScreenwidth();
		headerParams.width = ut.getScreenwidth();

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

//		shareBtn.setLayoutParams(shareParams);

		shareParams2 = new RelativeLayout.LayoutParams(
				profileLinear.getLayoutParams());

		shareParams2.height = ut.getScreenwidth() / 10;
		shareParams2.width = ut.getScreenwidth() / 4;
		shareParams2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		shareParams2.addRule(RelativeLayout.BELOW, R.id.followPage);
		shareParams2.setMargins(0, 10, 0, 0);

//		shareBtn.setLayoutParams(shareParams);
//		shareBtn.setTextSize(12);

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

		headerImage.setLayoutParams(headerParams);
		footerImage.setLayoutParams(footerParams);
	}

	private void setImage() {

		String PathHeader = object.getImagePath1();
		String PathProfile = object.getImagePath2();
		String PathFooter = object.getImagePath3();

		Bitmap bmpHeader = BitmapFactory.decodeFile(PathHeader);
		Bitmap bmpProfile = BitmapFactory.decodeFile(PathProfile);
		Bitmap bmpfooter = BitmapFactory.decodeFile(PathFooter);

		if (bmpHeader != null) {

			headerImage.setImageBitmap(bmpHeader);
		} else

			headerImage.setBackgroundResource(R.drawable.no_image_header);
		if (bmpProfile != null) {

			profileImage.setImageBitmap(Utility.getclip(bmpProfile));

		} else {
			profileImage.setBackgroundResource(R.drawable.circle_drawable);
			profileImage.setImageResource(R.drawable.no_img_profile);

		}
		if (bmpfooter != null) {
			footerImage.setImageBitmap(bmpfooter);

		} else
			footerImage.setBackgroundResource(R.drawable.no_image_header);
	}

	private void setStateLinkSocialAndDownload() {

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
	}

	private void setVisibilityItems() {

		// if ObjectBrandTypeId =1 >>>>>> namayandegi & khadamat : faal
		// if ObjectBrandTypeId =2 >>>>>> namayandegi & khadamat :gheyr faal
		// if ObjectBrandTypeId =3 >>>>>> namayandegi : faal
		// if ObjectBrandTypeId =4 >>>>>> khadamat : faal

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
		if (currentUser != null && object.getUserId() == currentUser.getId())
			followPage.setVisibility(View.GONE);

		if (object.getDescription() == null)
			txtDesc.setVisibility(View.GONE);

	}

	private void SetonItemsClickNetworkAndDownload() {
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

	}

	private void setOnItemClickInformationContact() {

		action.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				// if (Currentuser == null)
				// Toast.makeText(getActivity(), "ابتدا باید وارد شوید",
				// Toast.LENGTH_SHORT).show();
				// else {
				// DialogPostFragment fragment = new DialogPostFragment();
				//
				// FragmentTransaction trans = getActivity()
				// .getSupportFragmentManager().beginTransaction();
				// trans.setCustomAnimations(R.anim.pull_in_left,
				// R.anim.push_out_right);
				// trans.replace(R.id.content_frame, fragment);
				// trans.addToBackStack(null);
				// trans.commit();
				//
				// Bundle bundle = new Bundle();
				// bundle.putInt("Id", ObjectID);
				// fragment.setArguments(bundle);
				// }

				// MyDialog = new DialogpostTitle(getActivity(),
				// R.layout.dialog_addtitlepost, IntroductionFragment.this);
				// MyDialog.getWindow().setSoftInputMode(
				// WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
				FragmentManager fm = getActivity().getSupportFragmentManager();
				MyDialog = new DialogpostTitleFragment(ObjectID);
				// MyDialog = new DialogpostTitleFragment();
				MyDialog.show(fm, "My_Dialog_Dialog");

				// realize.edit().putInt("main_Id", 1).commit();

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

	}

	private void setonClickImage() {
		headerImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String pathImage = object.getImagePath1();
				String namePage = object.getName();

				DialogShowImage showImageDialog = new DialogShowImage(
						getActivity(), pathImage, namePage);
				showImageDialog.show();

			}
		});

		profileImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				String PathProfile = object.getImagePath2();
				String namePage = object.getName();
				DialogShowImage showImageDialog = new DialogShowImage(
						getActivity(), PathProfile, namePage);
				showImageDialog.show();

			}
		});

		footerImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String pathImage = object.getImagePath3();
				String namePage = object.getName();

				DialogShowImage showImageDialog = new DialogShowImage(
						getActivity(), pathImage, namePage);
				showImageDialog.show();
			}
		});
	}

	private void LikeCommentAction() {

		AddComment.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// if (currentUser == null) {
				// Toast.makeText(getActivity(), "ابتدا باید وارد شوید",
				// Toast.LENGTH_SHORT).show();
				// return;
				// } else {
				// dialog = new DialogcmtInobject(IntroductionFragment.this,
				// getActivity(), R.layout.dialog_addcomment,
				// ObjectID, 0);
				// dialog.show();
				// exadapter.notifyDataSetChanged();
				// }

			}
		});

		likePost.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (currentUser == null) {
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

				if (currentUser == null) {
					Toast.makeText(getActivity(),
							"برای درج لایک ابتدا باید وارد شوید",
							Toast.LENGTH_SHORT).show();
				} else {

					date = new ServerDate(getActivity());
					date.delegate = IntroductionFragment.this;
					date.execute("");

					flag = true;
					LikeOrComment = true;

				}
			}

		});

		EditPage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				FragmentTransaction trans = getActivity()
						.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame,
						new IntroductionEditFragment());
				trans.addToBackStack(null);
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

	}

	private void AgencyServiceSettingActionButton() {
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

		reaport.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (ut.getCurrentUser() != null) {
					if (ut.getCurrentUser().getId() == object.getUserId()) {

						menuItems = new ArrayList<String>();
						menuItems.clear();
						menuItems.add("ارسال پیام");
						menuItems.add("کپی");

					} else {
						menuItems = new ArrayList<String>();

						menuItems.clear();
						menuItems.add("ارسال پیام");
						menuItems.add("افزودن به علاقه مندی ها");
						menuItems.add("کپی");
						menuItems.add("گزارش تخلف");
					}
				} else {
					menuItems = new ArrayList<String>();

					menuItems.clear();
					menuItems.add("کپی");
				}

				final PopupMenu popupMenu = ut.ShowPopupMenu(menuItems, v);
				popupMenu.show();

				OnMenuItemClickListener menuitem = new OnMenuItemClickListener() {

					@Override
					public boolean onMenuItemClick(MenuItem item) {

						if (item.getTitle().equals("ارسال پیام")) {

							if (ut.getCurrentUser() != null)
								ut.sendMessage("Object");
							else
								Toast.makeText(context, "ابتدا باید وارد شوید",
										0).show();
						}

						if (item.getTitle().equals("افزودن به علاقه مندی ها")) {
							adapter.open();
							addToFavorite(ut.getCurrentUser().getId(), 4,
									object.getId());
							adapter.close();
						}
						if (item.getTitle().equals("کپی")) {

							ut.CopyToClipboard(object.getDescription());

						}
						if (item.getTitle().equals("گزارش تخلف")) {

							if (ut.getCurrentUser() != null)
								ut.reportAbuse(object.getId(), 4,
										object.getId(),
										object.getDescription(), 0);
							else
								Toast.makeText(context, "ابتدا باید وارد شوید",
										0).show();
						}

						return false;
					}
				};

				popupMenu.setOnMenuItemClickListener(menuitem);

				// DialogLongClick dia = new DialogLongClick(getActivity(), 4,
				// ObjectID, -1, IntroductionFragment.this, object
				// .getDescription());
				// Toast.makeText(getActivity(), ObjectID + "", 0).show();
				//
				// WindowManager.LayoutParams lp = new
				// WindowManager.LayoutParams();
				// lp.copyFrom(dia.getWindow().getAttributes());
				// lp.width = (int) (ut.getScreenwidth() / 1.5);
				// lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
				// ;
				// dia.show();
				//
				// dia.getWindow().setAttributes(lp);
				// dia.getWindow().setBackgroundDrawable(
				// new ColorDrawable(android.graphics.Color.TRANSPARENT));
			}
		});

	}

	private void setValues() {

		adapter.open();
		int countcmt = adapter.CommentInObject_count(ObjectID);
		CountCommentIntroduction.setText(String.valueOf(countcmt));

		int countlike = adapter.LikeInObject_count(ObjectID, 0);
		CountLikeIntroduction.setText(String.valueOf(countlike));

		int countlikePo = adapter.LikeInObject_count(ObjectID, 1);
		countLikePost.setText(String.valueOf(countlikePo));

		object = adapter.getObjectbyid(ObjectID);
		adapter.close();

		namePage.setText(object.getName());
		txtFax.setText(object.getFax());
		txtPhone.setText(object.getPhone());
		txtCellphone.setText(object.getCellphone());
		txtEmail.setText(object.getEmail());
		txtAddress.setText(object.getAddress());
		txtDesc.setText(object.getDescription());

	}

	private void showPeopleLikedBtn() {
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
	}

	private void getImageFromServer() {
		if (getActivity() != null) {

			update = new Updating(getActivity());
			update.delegate = IntroductionFragment.this;
			String[] para = new String[4];
			para[0] = "ImageServerDate";
			para[1] = String.valueOf(object.getId());
			para[2] = "masoud";
			para[3] = "0";
			update.execute(para);
			LikeOrComment = true;

		}

		loadingProgressHeader.setVisibility(View.VISIBLE);
		loadingProgressProfile.setVisibility(View.VISIBLE);
		loadingProgressFooter.setVisibility(View.VISIBLE);
	}

	private void countVisitFromPage() {

		if (currentUser != null) {
			if (currentUser.getId() != object.getId()) {
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

	}

	public void addToFavorite(int currentUserId, int source, int ItemId) {

		if (adapter.IsUserFavoriteItem(currentUserId, ItemId, source) == true) {
			Toast.makeText(context,
					" قبلا در لیست علاقه مندی ها ذخیره شده است ", 0).show();
		} else {
			adapter.insertFavoritetoDb(0, currentUserId, ItemId, source);
			Toast.makeText(context, "به لیست علاقه مندی ها اضافه شد ", 0)
					.show();
		}
	}

	// private void addComment() {
	// ImageView send = ut.ShowFooterAgahi(getActivity(), false, 6);

	//
	// send.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View arg0) {
	//
	// if ("".equals(ut.inputComment(getActivity()))) {
	// Toast.makeText(getActivity(), " نظر نمی تواند خالی باشد", 0)
	// .show();
	// } else {
	//
	// date = new ServerDate(getActivity());
	// date.delegate = IntroductionFragment.this;
	// date.execute("");
	// LikeOrComment = false;
	//
	// ut.ReplyLayout(getActivity(), "", false);
	// commentClick = true;
	//
	// }
	// }
	// });
	// ImageView delete = ut.deleteReply(getActivity());
	//
	// delete.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View arg0) {
	//
	// ut.ReplyLayout(getActivity(), "", false);
	//
	// }
	// });
	//
	// }

	public int groupPosition(int groupPosition) {
		gp = groupPosition;
		return gp;
	}

	public void CommentId(int Id) {
		commentId = Id;
	}
}
