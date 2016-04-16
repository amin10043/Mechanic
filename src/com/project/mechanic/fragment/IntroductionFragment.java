package com.project.mechanic.fragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.StaticValues;
import com.project.mechanic.Action.FloatingActionButton;
import com.project.mechanic.adapter.ExpandIntroduction;
import com.project.mechanic.adapter.PosttitleListadapter;
import com.project.mechanic.crop.CropImage;
import com.project.mechanic.entity.CommentInObject;
import com.project.mechanic.entity.LikeInObject;
import com.project.mechanic.entity.Object;
import com.project.mechanic.entity.Post;
import com.project.mechanic.entity.Settings;
import com.project.mechanic.entity.SubAdmin;
import com.project.mechanic.entity.Users;
import com.project.mechanic.entity.Visit;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.inter.AsyncInterfaceVisit;
import com.project.mechanic.inter.CommInterface;
import com.project.mechanic.inter.DataPersonalInterface;
import com.project.mechanic.inter.GetAllAsyncInterface;
import com.project.mechanic.inter.GetAsyncInterface;
import com.project.mechanic.inter.SaveAsyncInterface;
import com.project.mechanic.inter.VisitSaveInterface;
import com.project.mechanic.interfaceServer.AllFollowersInterface;
import com.project.mechanic.interfaceServer.CountAgencySerViceInterface;
import com.project.mechanic.interfaceServer.CountCommentInterface;
import com.project.mechanic.interfaceServer.CountLikeInterface;
import com.project.mechanic.interfaceServer.DateFromServerForLike;
import com.project.mechanic.interfaceServer.DateFromServerForVisit;
import com.project.mechanic.interfaceServer.DateImagePostInterface;
import com.project.mechanic.interfaceServer.DateImagesInformationObject;
import com.project.mechanic.interfaceServer.DeleteLikeFromServer;
import com.project.mechanic.interfaceServer.GetUserInteface;
import com.project.mechanic.interfaceServer.HappySadInterface;
import com.project.mechanic.interfaceServer.HeaderProfileFooterImagesObject;
import com.project.mechanic.interfaceServer.ImagePostInterface;
import com.project.mechanic.interfaceServer.LikeFromServer;
import com.project.mechanic.interfaceServer.SubAdminInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.server.AllFollowerObjectServer;
import com.project.mechanic.server.CountAgencyServiceServer;
import com.project.mechanic.server.DeletingLike;
import com.project.mechanic.server.GetCountComment;
import com.project.mechanic.server.GetCountLike;
import com.project.mechanic.server.GetDateImagePost;
import com.project.mechanic.server.GetDatesImagesObject;
import com.project.mechanic.server.GetHappySadServer;
import com.project.mechanic.server.GetImagePost;
import com.project.mechanic.server.GetImagesHeaderProfileFooter;
import com.project.mechanic.server.GetSubAdminServer;
import com.project.mechanic.server.GetUserServer;
import com.project.mechanic.server.SavingLike;
import com.project.mechanic.server.ServerDateForLike;
import com.project.mechanic.server.ServerDateForVisit;
import com.project.mechanic.service.Deleting;
import com.project.mechanic.service.GetPostByObjectId;
import com.project.mechanic.service.Saving;
import com.project.mechanic.service.SavingImage;
import com.project.mechanic.service.SavingVisit;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.service.ServiceComm;
import com.project.mechanic.service.Updating;
import com.project.mechanic.service.UpdatingAllImage;
import com.project.mechanic.service.UpdatingVisit;
import com.project.mechanic.utility.Cache;
import com.project.mechanic.utility.Utility;
import com.project.mechanic.view.TextViewEx;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.format.Time;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class IntroductionFragment extends Fragment
		implements LikeFromServer, DateFromServerForLike, DeleteLikeFromServer, DateImagesInformationObject,
		HeaderProfileFooterImagesObject, DateFromServerForVisit, ImagePostInterface, DateImagePostInterface,
		HappySadInterface, AsyncInterface, GetAllAsyncInterface, DataPersonalInterface, AllFollowersInterface,
		VisitSaveInterface, AsyncInterfaceVisit, GetAsyncInterface, CommInterface, SaveAsyncInterface,
		SubAdminInterface, GetUserInteface, CountAgencySerViceInterface, CountLikeInterface, CountCommentInterface {

	// Context context;
	Utility ut;
	Users currentUser;
	View header, Posts;
	// view,
	ListView PostList;
	List<Visit> visitList;
	// ExpandableListView exListView;
	ExpandIntroduction exadapter;
	int ObjectID, gp;
	DialogPersonLikedObject ListLiked;
	UpdatingAllImage updating;
	Map<String, String> maps;
	ArrayList<CommentInObject> commentGroup, ReplyGroup;
	ArrayList<Post> ArrayPosts = new ArrayList<Post>();
	Map<CommentInObject, List<CommentInObject>> mapCollection;
	public RelativeLayout agency, service, sendSMS, addressRelative, profileLinear, /* personPost, */ phone, cphone,
			email, shareBtn, followPage, EditPage, WebsiteRelative;
	public DialogcmtInobject dialog;
	public LinearLayout /* AddLike, AddComment, */ headImageLinear, footerLinear, likePost, personPage;
	public ImageButton Comment;
	LinearLayout.LayoutParams headerParams, footerParams;
	RelativeLayout.LayoutParams addressParams, emailParams, profileParams, followParams, shareParams, shareParams2,
			websiteParams;
	DataBaseAdapter adapter;
	TextView txtFax, txtAddress, txtPhone, txtCellphone, txtEmail, CountLikeIntroduction, CountCommentIntroduction,
			namePage, countLikePost, txtWebsite, countPost;
	TextViewEx txtDesc;
	ImageView headerImage, footerImage, profileImage, reaport;
	ImageButton Facebook, Instagram, LinkedIn, Google, Site, Twitter, Pdf1, Pdf2, Pdf3, Pdf4;
	Object object;
	byte[] headerbyte, profilebyte, footerbyte;
	// SharedPreferences sendDataID, pageId;
	Saving saving;
	Deleting deleting;
	Map<String, String> params;
	ProgressDialog ringProgressDialog;
	String serverDate = "";
	ServerDate date;
	Updating update;
	boolean flag, f1, f2, f3, LikeOrComment, commentClick = false;
	List<String> menuItems;
	int postItemId = 0;
	// ProgressBar loadingProgressHeader/* , loadingProgressProfile */,
	// loadingProgressFooter;
	Button ShowPostBtn, btnShowPost;
	int userId, commentId = 0;
	Post postItem;
	FloatingActionButton action;

	// DialogpostTitleFragment MyDialog;
	ImageView iconFollow, iconVeryOk, iconOk, iconVerysad, iconsad;

	Settings setting;

	TextView points, countRazi, countShaki, countVeryRazi, countVeryShaki, CountAgencyTxt, CountServiceTxt;
	TextView lableShare, lableEdit, lableFollow, lableNumFollow, lableNumView, lableNumPost, lableAgency, lableservice,
			lableSendBusinessCard;
	int counter;
	int ItemId, typeId;
	int counterVisit = 0;
	String currentTime = "";
	private GifAnimationDrawable little, big;
	boolean isFinish = false, saveVisitFalg, booleanPost = true;
	int visitCounter = 0;
	Post post;
	int positionListPost = 0;
	PosttitleListadapter ListAdapterPost;

	int typeLike;
	int positionBrand = 0, positionScroll = 0;
	// int positionPost = 0;
	RelativeLayout bottomSheet, mainSheet;
	EditText descriptionPost;
	boolean isPostCount = true;
	Animation enterFromDown, exitToDown;
	ImageView send, close;
	String descriptionPostTxt = "";
	byte[] ImageConvertedToByte = null;
	int PostId;
	Map<String, java.lang.Object> imageParams;
	ImageView selectImage, deleteImage;
	static final int GalleryCode = 100;
	private File mFileTemp;
	public static final String TEMP_PHOTO_FILE_NAME = "temp_photo.jpg";
	final int PIC_CROP = 10;
	RelativeLayout.LayoutParams paramsPic;

	int MaxSizeImageSelected = 5;
	Bitmap bmpHeader, bmpProfile, bmpfooter;
	// LinearLayout footerLayout;
	int typeHappySad = StaticValues.TypeVeryHappyFromPage;
	int whatTypeLikePage = StaticValues.TypeLikePage; // 0 >> follower --- 2 >>
														// happy --- 3 >> sad
	boolean typeAgency = true;
	int adminController = 0;
	int counterLike = 0;
	int counterComment = 0;

	int pId = 0;
	int cityId = -1;

	LinearLayout IdeaLayout;

	public IntroductionFragment(int cityId) {
		this.cityId = cityId;
	}

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		// ((MainActivity) getActivity()).setActivityTitle(R.string.brand);
		adapter = new DataBaseAdapter(getActivity());
		ut = new Utility(getActivity());
		// ListAdapterPost = new PosttitleListadapter(getActivity(),
		// R.layout.raw_posttitle, ArrayPosts,
		// IntroductionFragment.this , positionBrand);

		// define rootView and header Layout
		// view = inflater.inflate(R.layout.fragment_introduction, null);
		header = getActivity().getLayoutInflater().inflate(R.layout.header_introduction, null);
		Posts = inflater.inflate(R.layout.fragment_titlepost, null);
		PostList = (ListView) Posts.findViewById(R.id.lstComment);
		// PostList.addHeaderView(header);

		// define Views : find view by Id
		findView();

		// pageId = getActivity().getSharedPreferences("Id", 0);

		if (getArguments().getString("Id") != null)
			ObjectID = Integer.valueOf(getArguments().getString("Id"));

		if (getArguments() != null)
			positionBrand = Integer.valueOf(getArguments().getInt("positionBrand"));

		currentUser = ut.getCurrentUser();

		adapter.open();
		object = adapter.getObjectbyid(ObjectID);
		adapter.close();

		// set values for parameter and variable
		setValues();

		// fill expand ListView
		// fillExpandListViewCommnet();

		// fill Post List View
		PostList.addHeaderView(header);
		fillListView();

		if (getArguments() != null) {
			positionScroll = Integer.valueOf(getArguments().getInt("positionScroll"));
			// setPositionPage();
		}

		// if (getArguments() != null) {
		//
		// positionPost = getArguments().getInt("positionPost");
		// PostList.setSelection(positionPost);
		//
		// }

		// set layoutParams
		setLayoutParams();

		// setImage background headerImage , ProfileImage , FooterImage
		setImage();

		// connect to server for save visit
		checkInternet();

		// set on or off for follow and like button
		setStateButtonFollowLike();

		// set Image on or off for link network social and download link
		// setStateLinkSocialAndDownload();

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

		setFont();

		stateHappyOrSad();

		getCountInformation();

		getPost();

		getImageFromServer();

		GetHappySad();

		getLikeInObjectPage();
		// for manage footer slide image agahi
		// addComment();

		getSubAdmin();

		getCountAgencyService();

		getLikeNumberOfPost();

		if (currentUser != null)
			if (currentUser.getId() == object.getUserId() || isAdmin(currentUser.getId()))
				action.setVisibility(View.VISIBLE);
			else
				action.setVisibility(View.GONE);
		else
			action.setVisibility(View.GONE);

		// if (PostList.getCount() > 0)
		
		
		
		ut.inputCommentAndPickFile(getActivity(), false);

		return Posts;
		// else
		// return header;

	}

	@Override
	public void onResume() {

		getView().setFocusableInTouchMode(true);
		getView().requestFocus();
		setPositionPage();
		getView().setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {

				if (event.getAction() == KeyEvent.ACTION_DOWN)

					if (keyCode == KeyEvent.KEYCODE_BACK) {

						if (object.getMainObjectId() == 1) {

							FragmentTransaction trans = ((MainActivity) getActivity()).getSupportFragmentManager()
									.beginTransaction();
							MainBrandFragment fragment = new MainBrandFragment(object.getParentId());
							// Bundle bundle = new Bundle();
							// bundle.putString("Id",
							// String.valueOf(object.getParentId()));
							// bundle.putInt("position", positionBrand);
							// fragment.setArguments(bundle);
							trans.replace(R.id.content_frame, fragment);

							trans.commit();
						} else {
							FragmentTransaction trans = ((MainActivity) getActivity()).getSupportFragmentManager()
									.beginTransaction();
							ObjectFragment fragment = new ObjectFragment(object.getMainObjectId(), -1, -1, cityId);

							trans.replace(R.id.content_frame, fragment);

							trans.commit();
						}

						return true;
					}

				return false;
			}
		});
		super.onResume();
	}



	@Override
	public void processFinish(String output) {

		Toast.makeText(getActivity(), output, 0).show();
		if (ut.checkError(output) == false) {

			int id = -1;
			try {

				PostId = id = Integer.valueOf(output);

				adapter.open();
				adapter.insertPosttitletoDb(PostId, descriptionPostTxt, currentUser.getId(), serverDate, "", ObjectID);
				adapter.close();

				if (ImageConvertedToByte != null) {

					if (getActivity() != null) {
						SavingImage saveImage = new SavingImage((getActivity()));
						saveImage.delegate = IntroductionFragment.this;
						imageParams = new LinkedHashMap<String, java.lang.Object>();
						imageParams.put("tableName", "Post");
						imageParams.put("fieldName", "Photo");
						imageParams.put("id", PostId);
						imageParams.put("image", ImageConvertedToByte);

						saveImage.execute(imageParams);
						// ringProgressDialog =
						// ProgressDialog.show(getActivity(), "", "لطفا منتظر
						// بمانید...", true);
						//
						// ringProgressDialog.setCancelable(true);
					}

				} else {
					setPostionListPost(1);
					fillListView();

				}

			} catch (NumberFormatException ex) {

				if (output != null && !(output.contains("Exception") || output.contains("java"))) {

					if (getActivity() != null) {

						serverDate = output;

						saving = new Saving(getActivity());
						saving.delegate = IntroductionFragment.this;
						params = new LinkedHashMap<String, String>();

						params.put("TableName", "Post");

						params.put("UserId", String.valueOf(currentUser.getId()));
						params.put("Description", descriptionPostTxt);
						params.put("ObjectId", ObjectID + "");

						params.put("Date", serverDate);
						params.put("ModifyDate", serverDate);

						params.put("IsUpdate", "0");
						params.put("Id", "0");

						saving.execute(params);

		

					}
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
					ut.CreateFile(output.get(0), object.getId(), "Mechanical", "Profile", "header", "Object");

					// adapter.UpdateHeaderImageObject(ObjectID, output.get(0));
					object = adapter.getObjectbyid(ObjectID);

					String PathImageHeader = "";
					PathImageHeader = object.getImagePath1();
					Bitmap b = BitmapFactory.decodeFile(PathImageHeader);

					if (b != null)
						headerImage.setImageBitmap(b);
					else
						headerImage.setBackgroundResource(R.drawable.no_image_header);
					adapter.updateObjectImage1ServerDate(ObjectID, serverDate);
				}
			if (f2)
				if (output.get(1) != null) {
					ut.CreateFile(output.get(1), object.getId(), "Mechanical", "Profile", "profile", "Object");
					object = adapter.getObjectbyid(ObjectID);

					String PathImageProfile = "";
					PathImageProfile = object.getImagePath2();
					// adapter.UpdateProfileImageObject(ObjectID,
					// output.get(1));
					Bitmap b = BitmapFactory.decodeFile(PathImageProfile);

					if (b != null)
						profileImage.setImageBitmap(Utility.getclip(b));
					else
						profileImage.setBackgroundResource(R.drawable.no_img_profile);
					adapter.updateObjectImage2ServerDate(ObjectID, serverDate);
				}

			if (f3)
				if (output.get(2) != null) {
					ut.CreateFile(output.get(2), object.getId(), "Mechanical", "Profile", "footer", "Object");
					object = adapter.getObjectbyid(ObjectID);

					String PathImageFooter = "";
					PathImageFooter = object.getImagePath3();

					// adapter.UpdateFooterImageObject(ObjectID, output.get(2));
					Bitmap b = BitmapFactory.decodeFile(PathImageFooter);

					if (b != null)
						footerImage.setImageBitmap(b);
					else
						footerImage.setBackgroundResource(R.drawable.no_image_header);

					adapter.updateObjectImage3ServerDate(ObjectID, serverDate);

				}
			Toast.makeText(getActivity(), "به روز رسانی تصاویر با موفقیت انجام شد", Toast.LENGTH_SHORT).show();

			// loadingProgressHeader.setVisibility(View.GONE);
			// // loadingProgressProfile.setVisibility(View.GONE);
			// loadingProgressFooter.setVisibility(View.GONE);

			adapter.close();

		}

		// if (ringProgressDialog != null)
		// ringProgressDialog.dismiss();

	}

	public int getObjectId() {
		return ObjectID;
	}

	private void findView() {
		// exListView = (ExpandableListView) view
		// .findViewById(R.id.ExpandIntroduction);

		headerImage = (ImageView) header.findViewById(R.id.imgvadvertise_Object);
		footerImage = (ImageView) header.findViewById(R.id.imgvadvertise2_Object);
		profileImage = (ImageView) header.findViewById(R.id.icon_pro);

		// headImageLinear = (LinearLayout) header
		// .findViewById(R.id.headerlinerpageintroduction);
		agency = (RelativeLayout) header.findViewById(R.id.Layoutlink1);
		service = (RelativeLayout) header.findViewById(R.id.Layoutlink2);
		sendSMS = (RelativeLayout) header.findViewById(R.id.sendsmsIntroduction);

		txtFax = (TextView) header.findViewById(R.id.txtFax_Object);
		txtAddress = (TextView) header.findViewById(R.id.txtAddress_Object);
		txtPhone = (TextView) header.findViewById(R.id.txtPhone_Object);
		txtCellphone = (TextView) header.findViewById(R.id.txtCellphone_Object);
		txtDesc = (TextViewEx) header.findViewById(R.id.txtDesc_Object);
		txtEmail = (TextView) header.findViewById(R.id.txtEmail_Object);
		namePage = (TextView) header.findViewById(R.id.namePage);
		CountLikeIntroduction = (TextView) header.findViewById(R.id.countLikeIntroduction);
		CountCommentIntroduction = (TextView) header.findViewById(R.id.numberOfCommentTopic);

		txtWebsite = (TextView) header.findViewById(R.id.txtwebsite);
		countPost = (TextView) header.findViewById(R.id.countPost);
		// AddLike = (LinearLayout) header
		// .findViewById(R.id.AddLikeIntroductionLinear);
		// AddComment = (LinearLayout) header
		// .findViewById(R.id.AddcommentIntroductionLinear);

		addressRelative = (RelativeLayout) header.findViewById(R.id.addressRelative);
		WebsiteRelative = (RelativeLayout) header.findViewById(R.id.personweb);

		Facebook = (ImageButton) header.findViewById(R.id.nfacebook);
		Instagram = (ImageButton) header.findViewById(R.id.ninstagram);
		LinkedIn = (ImageButton) header.findViewById(R.id.nlinkedin);
		Google = (ImageButton) header.findViewById(R.id.ngoogle);
		Site = (ImageButton) header.findViewById(R.id.nsite);
		Twitter = (ImageButton) header.findViewById(R.id.ntwtert);

		phone = (RelativeLayout) header.findViewById(R.id.fixedPhone);
		cphone = (RelativeLayout) header.findViewById(R.id.personalMobile);
		email = (RelativeLayout) header.findViewById(R.id.emailsRelative);
		shareBtn = (RelativeLayout) header.findViewById(R.id.b);

		Pdf1 = (ImageButton) header.findViewById(R.id.btnPdf1_Object);
		Pdf2 = (ImageButton) header.findViewById(R.id.btnPdf2_Object);
		Pdf3 = (ImageButton) header.findViewById(R.id.btnPdf3_Object);
		Pdf4 = (ImageButton) header.findViewById(R.id.btnPdf4_Object);
		profileLinear = (RelativeLayout) header.findViewById(R.id.linear_id_profile_introduction_page);
		footerLinear = (LinearLayout) header.findViewById(R.id.footerint);
		EditPage = (RelativeLayout) header.findViewById(R.id.ImgbtnEdit);
		likePost = (LinearLayout) header.findViewById(R.id.LikeTopicLinear);

		points = (TextView) header.findViewById(R.id.points);

		countLikePost = (TextView) header.findViewById(R.id.txtNumofLike_CmtFroum);
		personPage = (LinearLayout) header.findViewById(R.id.countLiketext);
		// personPost = (RelativeLayout) header.findViewById(R.id.countLikeqqz);

		// loadingProgressHeader = (ProgressBar)
		// header.findViewById(R.id.header_progress_header);

		// loadingProgressProfile = (ProgressBar)
		// header.findViewById(R.id.profile_progress_profile);
		// loadingProgressFooter = (ProgressBar)
		// header.findViewById(R.id.footer_progress_footer);

		followPage = (RelativeLayout) header.findViewById(R.id.follow_follow);
		reaport = (ImageView) header.findViewById(R.id.reportImage);

		action = (FloatingActionButton) Posts.findViewById(R.id.fab);

		iconFollow = (ImageView) header.findViewById(R.id.imageiconfollow);

		lableShare = (TextView) header.findViewById(R.id.tst);
		lableEdit = (TextView) header.findViewById(R.id.labb1);
		lableFollow = (TextView) header.findViewById(R.id.labbbb1);

		lableNumFollow = (TextView) header.findViewById(R.id.lablelablefollow);
		lableNumView = (TextView) header.findViewById(R.id.lableviewlable);
		lableNumPost = (TextView) header.findViewById(R.id.lablematlab);

		lableAgency = (TextView) header.findViewById(R.id.textView12);
		lableservice = (TextView) header.findViewById(R.id.textView13);
		lableSendBusinessCard = (TextView) header.findViewById(R.id.dialog_phone);

		bottomSheet = (RelativeLayout) Posts.findViewById(R.id.bottmSheet);
		mainSheet = (RelativeLayout) Posts.findViewById(R.id.mainSheet);

		descriptionPost = (EditText) Posts.findViewById(R.id.txtTitleP);

		enterFromDown = AnimationUtils.loadAnimation(getActivity(), R.anim.down_from_top);
		exitToDown = AnimationUtils.loadAnimation(getActivity(), R.anim.up_from_bottom);

		send = (ImageView) Posts.findViewById(R.id.createDialogPage);
		close = (ImageView) Posts.findViewById(R.id.delete);
		selectImage = (ImageView) Posts.findViewById(R.id.selectImage);
		deleteImage = (ImageView) Posts.findViewById(R.id.deleteImage);

		CountAgencyTxt = (TextView) header.findViewById(R.id.countAgency);
		CountServiceTxt = (TextView) header.findViewById(R.id.countService);

		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			mFileTemp = new File(Environment.getExternalStorageDirectory(), TEMP_PHOTO_FILE_NAME);
		} else {
			mFileTemp = new File(getActivity().getFilesDir(), TEMP_PHOTO_FILE_NAME);
		}

		TextView lableNazar = (TextView) header.findViewById(R.id.lablesqw);

		lableNazar.setTypeface(ut.SetFontIranSans());

		IdeaLayout = (LinearLayout) header.findViewById(R.id.settingsLayout);
	}

	public void setPositionPage() {
		// fillListView();
		// PostList.setScrollY(positionScroll);

	}

	@Override
	public void onStart() {

		// setPositionPage();
		super.onStart();
	}

	public void fillListView() {
		adapter.open();
		ArrayPosts.clear();
		ArrayPosts = adapter.getAllPost(object.getId());
		adapter.close();
		ListAdapterPost = new PosttitleListadapter(getActivity(), R.layout.raw_posttitle, ArrayPosts,
				IntroductionFragment.this, positionBrand);

		PostList.setAdapter(ListAdapterPost);
		PostList.setSelection(positionListPost);
	}

	public void setPostionListPost(int pos) {
		positionListPost = pos;

	}

	private void setStateButtonFollowLike() {

		adapter.open();

		if (currentUser == null) {
		} else {
			if (adapter.isUserLikeIntroductionPage(currentUser.getId(), ObjectID, StaticValues.TypeLikePage)) {
				iconFollow.setBackgroundResource(R.drawable.ic_followed);
				// personPage.setBackgroundResource(R.drawable.count_like);
			} else {
				iconFollow.setBackgroundResource(R.drawable.ic_following);

				// personPage.setBackgroundResource(R.drawable.count_like_off);

			}
			if (adapter.isUserLikeIntroductionPage(currentUser.getId(), ObjectID, StaticValues.TypeLikeFixedPost)) {
				likePost.setBackgroundResource(R.drawable.like_froum_on);
				// personPost.setBackgroundResource(R.drawable.count_like);

			} else {
				likePost.setBackgroundResource(R.drawable.like_froum_off);
				// personPost.setBackgroundResource(R.drawable.count_like_off);

			}
		}
		adapter.close();

		if (currentUser == null || object.getUserId() != currentUser.getId()) {
			EditPage.setVisibility(View.GONE);

			// shareBtn.setLayoutParams(shareParams2);
		} else {
			EditPage.setVisibility(View.VISIBLE);
			// EditPage.setLayoutParams(followParams);

		}

	}

	private void setLayoutParams() {

		// profileParams = new RelativeLayout.LayoutParams(
		// profileLinear.getLayoutParams());

		int marginTop = ut.getScreenwidth() - (ut.getScreenwidth() / 8);

		FrameLayout profileFrame = (FrameLayout) header.findViewById(R.id.frameLayoutHeader);
		FrameLayout.LayoutParams profileParams = new FrameLayout.LayoutParams(profileFrame.getLayoutParams());

		profileParams.height = ut.getScreenwidth() / 4;
		profileParams.width = ut.getScreenwidth() / 4;
		profileParams.gravity = Gravity.CENTER_HORIZONTAL;

		// profileParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		// profileParams.addRule(RelativeLayout.BELOW, R.id.namePage);
		profileParams.setMargins(0, marginTop, 0, 0);

		profileImage.setLayoutParams(profileParams);

		FrameLayout.LayoutParams headerParams = new FrameLayout.LayoutParams(profileFrame.getLayoutParams());

		headerParams.height = ut.getScreenwidth();
		headerParams.width = ut.getScreenwidth();

		footerParams = new LinearLayout.LayoutParams(footerLinear.getLayoutParams());
		footerParams.height = ut.getScreenwidth();
		footerParams.width = ut.getScreenwidth();

		// followParams = new RelativeLayout.LayoutParams(
		// profileLinear.getLayoutParams());
		//
		// followParams.width = ut.getScreenwidth() / 4;
		// followParams.height = ut.getScreenwidth() / 10;
		// followParams.setMargins(0, 10, 0, 0);
		// followParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		// followParams.addRule(RelativeLayout.BELOW, R.id.icon_pro);

		// followPage.setLayoutParams(followParams);

		// shareParams = new RelativeLayout.LayoutParams(
		// profileLinear.getLayoutParams());
		//
		// shareParams.height = ut.getScreenwidth() / 10;
		// shareParams.width = ut.getScreenwidth() / 4;
		// shareParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		// shareParams.addRule(RelativeLayout.BELOW, R.id.ImgbtnEdit);
		// shareParams.setMargins(0, 10, 0, 0);

		// shareBtn.setLayoutParams(shareParams);

		// shareParams2 = new RelativeLayout.LayoutParams(
		// profileLinear.getLayoutParams());
		//
		// shareParams2.height = ut.getScreenwidth() / 10;
		// shareParams2.width = ut.getScreenwidth() / 4;
		// shareParams2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		// shareParams2.addRule(RelativeLayout.BELOW, R.id.followPage);
		// shareParams2.setMargins(0, 10, 0, 0);

		// shareBtn.setLayoutParams(shareParams);
		// shareBtn.setTextSize(12);

		addressParams = new RelativeLayout.LayoutParams(addressRelative.getLayoutParams());

		addressParams.width = LayoutParams.WRAP_CONTENT;
		// addressParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		addressParams.addRule(RelativeLayout.CENTER_VERTICAL);
		addressParams.addRule(RelativeLayout.ALIGN_LEFT, R.id.i5);
		addressParams.addRule(RelativeLayout.LEFT_OF, R.id.ImageView01);

		addressParams.setMargins(0, 0, 0, 0);
		txtAddress.setGravity(Gravity.LEFT);
		txtAddress.setLayoutParams(addressParams);

		emailParams = new RelativeLayout.LayoutParams(email.getLayoutParams());

		emailParams.width = LayoutParams.WRAP_CONTENT;
		// emailParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		emailParams.addRule(RelativeLayout.ALIGN_LEFT, R.id.i3);
		emailParams.addRule(RelativeLayout.LEFT_OF, R.id.textView4);

		emailParams.addRule(RelativeLayout.CENTER_VERTICAL);
		emailParams.setMargins(0, 0, 0, 0);

		txtEmail.setLayoutParams(emailParams);

		headerImage.setLayoutParams(headerParams);
		footerImage.setLayoutParams(footerParams);

		RelativeLayout.LayoutParams llp = new RelativeLayout.LayoutParams(mainSheet.getLayoutParams());
		llp.width = ut.getScreenwidth();
		llp.height = ut.getScreenwidth() / 2;
		llp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

		RelativeLayout.LayoutParams ep = new RelativeLayout.LayoutParams(bottomSheet.getLayoutParams());
		ep.width = LayoutParams.MATCH_PARENT;
		ep.height = LayoutParams.MATCH_PARENT;
		ep.addRule(RelativeLayout.RIGHT_OF, R.id.delete);
		ep.addRule(RelativeLayout.LEFT_OF, R.id.createDialogPage);

		ep.setMargins(10, 10, 10, 10);

		descriptionPost.setLayoutParams(ep);
		bottomSheet.setLayoutParams(llp);

		RelativeLayout in = (RelativeLayout) Posts.findViewById(R.id.input);
		paramsPic = new RelativeLayout.LayoutParams(in.getLayoutParams());
		paramsPic.width = (int) (ut.getScreenwidth() / StaticValues.RateImagePostFragmentPage);
		paramsPic.height = (int) (ut.getScreenwidth() / StaticValues.RateImagePostFragmentPage);
		paramsPic.addRule(RelativeLayout.CENTER_IN_PARENT);

		websiteParams = new RelativeLayout.LayoutParams(WebsiteRelative.getLayoutParams());

		websiteParams.width = LayoutParams.WRAP_CONTENT;
		// emailParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		websiteParams.addRule(RelativeLayout.ALIGN_LEFT, R.id.i2d);
		websiteParams.addRule(RelativeLayout.LEFT_OF, R.id.in);

		websiteParams.addRule(RelativeLayout.CENTER_VERTICAL);
		websiteParams.setMargins(0, 0, 0, 0);

		txtWebsite.setLayoutParams(websiteParams);

	}

	private void setImage() {

		String PathHeader = object.getImagePath1();
		String PathProfile = object.getImagePath2();
		String PathFooter = object.getImagePath3();

		if (!"".equals(PathHeader)) {
			try {
				bmpHeader = BitmapFactory.decodeFile(PathHeader);
			} catch (OutOfMemoryError e) {
				e.printStackTrace();
			}
		}

		if (!"".equals(PathProfile)) {
			try {
				bmpProfile = BitmapFactory.decodeFile(PathProfile);
			} catch (OutOfMemoryError e) {
				e.printStackTrace();
			}

		}

		if (!"".equals(PathFooter)) {
			try {
				bmpfooter = BitmapFactory.decodeFile(PathFooter);

			} catch (OutOfMemoryError e) {
				e.printStackTrace();
			}
		}

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

		if (object.getFacebook() != null && !object.getFacebook().equals("null"))
			Facebook.setImageResource(R.drawable.facebook);
		else
			Facebook.setImageResource(R.drawable.facebook_off);

		if (object.getInstagram() != null && !object.getInstagram().equals("null"))
			Instagram.setImageResource(R.drawable.insta);
		else
			Instagram.setImageResource(R.drawable.insta_off);

		if (object.getLinkedIn() != null && !object.getLinkedIn().equals("null"))
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
			Site.setImageResource(R.drawable.internet);

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

		ImageView divder = (ImageView) header.findViewById(R.id.i231);
		LinearLayout agencyServ = (LinearLayout) header.findViewById(R.id.agencyServ);

		if (object.getObjectBrandTypeId() == StaticValues.HaveAgencyHaveService) {
			agency.setVisibility(View.VISIBLE);
			service.setVisibility(View.VISIBLE);

			service.setBackgroundResource(R.drawable.shadow_layer);

		}
		if (object.getObjectBrandTypeId() == StaticValues.NoAgencyNoService) {
			agency.setVisibility(View.GONE);
			service.setVisibility(View.GONE);
			agencyServ.setVisibility(View.GONE);
			divder.setVisibility(View.GONE);
		}
		if (object.getObjectBrandTypeId() == StaticValues.OnlyAgency) {
			agency.setVisibility(View.VISIBLE);
			agency.setBackgroundResource(R.drawable.shadow_layer);
			service.setVisibility(View.GONE);
			divder.setVisibility(View.GONE);
		}
		if (object.getObjectBrandTypeId() == StaticValues.OnlyService) {
			agency.setVisibility(View.GONE);
			service.setVisibility(View.VISIBLE);
			service.setBackgroundResource(R.drawable.shadow_layer);

			divder.setVisibility(View.GONE);
		}
		if (currentUser != null)
			if (object.getUserId() == currentUser.getId() || isAdmin(currentUser.getId()))
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
					FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame, new FragmentShowSite(url));
					trans.addToBackStack(null);
					trans.commit();
				}
			}
		});
		Instagram.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (object.getInstagram() != null) {

					String url = "http://" + object.getInstagram();

					FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame, new FragmentShowSite(url));
					trans.addToBackStack(null);

					trans.commit();
				}

			}
		});

		LinkedIn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (object.getLinkedIn() != null) {

					String url = "http://" + object.getLinkedIn();

					FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame, new FragmentShowSite(url));
					trans.addToBackStack(null);

					trans.commit();
				}

			}
		});

		Google.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (object.getGoogle() != null) {

					String url = "http://" + object.getGoogle();

					FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame, new FragmentShowSite(url));
					trans.addToBackStack(null);

					trans.commit();
				}

			}
		});

		Site.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (object.getSite() != null) {

					String url = "http://" + object.getSite();

					FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame, new FragmentShowSite(url));
					trans.addToBackStack(null);

					trans.commit();
				}

			}
		});

		Twitter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (object.getTwitter() != null) {

					String url = "http://" + object.getTwitter();

					FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame, new FragmentShowSite(url));
					trans.addToBackStack(null);

					trans.commit();
				}

			}
		});

		Pdf1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (object.getPdf1() != null) {
					String url = "http://" + object.getPdf1();

					FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame, new FragmentShowSite(url));
					trans.addToBackStack(null);

					trans.commit();
				}

			}
		});

		Pdf2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (object.getPdf2() != null) {

					String url = "http://" + object.getPdf2();

					FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame, new FragmentShowSite(url));
					trans.addToBackStack(null);

					trans.commit();
				}

			}
		});

		Pdf3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (object.getPdf3() != null) {

					String url = "http://" + object.getPdf3();

					FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame, new FragmentShowSite(url));
					trans.addToBackStack(null);

					trans.commit();
				}

			}
		});

		Pdf4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (object.getPdf4() != null) {

					String url = "http://" + object.getPdf4();

					FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame, new FragmentShowSite(url));
					trans.addToBackStack(null);

					trans.commit();
				}

			}
		});

	}

	private void setOnItemClickInformationContact() {

		final LinearLayout okPage = (LinearLayout) Posts.findViewById(R.id.Ok);
		final LinearLayout hatePage = (LinearLayout) Posts.findViewById(R.id.no);

		final LinearLayout VeryokPage = (LinearLayout) Posts.findViewById(R.id.very_Ok);
		final LinearLayout VeryhatePage = (LinearLayout) Posts.findViewById(R.id.very_no);

		countRazi = (TextView) Posts.findViewById(R.id.countRezayat);
		countShaki = (TextView) Posts.findViewById(R.id.countShaki);

		countVeryRazi = (TextView) Posts.findViewById(R.id.countveryRezayat);
		countVeryShaki = (TextView) Posts.findViewById(R.id.countveryShaki);

		iconVeryOk = (ImageView) Posts.findViewById(R.id.iconveryrazi);
		iconOk = (ImageView) Posts.findViewById(R.id.iconrazi);
		iconsad = (ImageView) Posts.findViewById(R.id.iconnarazi);
		iconVerysad = (ImageView) Posts.findViewById(R.id.iconvery_narazi);

		VeryokPage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (currentUser == null) {
					Toast.makeText(getActivity(), "ابتدا باید وارد شوید", Toast.LENGTH_SHORT).show();
				} else {

					adapter.open();

					if (adapter.isUserHappyFromPage(currentUser.getId(), ObjectID, StaticValues.TypeHappyFromPage)
							|| adapter.isUserHappyFromPage(currentUser.getId(), ObjectID, StaticValues.TypeSadFromPage)
							|| adapter.isUserHappyFromPage(currentUser.getId(), ObjectID,
									StaticValues.TypeVerySadFromPage)) {
						Toast.makeText(getActivity(), StaticValues.IsRepeatLikeMessage, 0).show();
					} else {

						if (VeryokPage.isEnabled()) {

							enableIdeaButton(false);

							// int countVeryHappy =
							// adapter.getCountHappyOrSadFromPage(ObjectID,
							// StaticValues.TypeVeryHappyFromPage);

							int c = Integer.valueOf(countVeryRazi.getText().toString());

							if (adapter.isUserHappyFromPage(currentUser.getId(), ObjectID,
									StaticValues.TypeVeryHappyFromPage)) {

								iconVeryOk.setBackgroundResource(R.drawable.very_razi);

								if (c > 0)
									countVeryRazi.setText(c - 1 + "");
							} else {

								iconVeryOk.setBackgroundResource(R.drawable.very_ok);
								countVeryRazi.setText(c + 1 + "");
							}

							ServerDateForLike date = new ServerDateForLike(getActivity());
							date.delegate = IntroductionFragment.this;
							date.execute("");

							typeLike = StaticValues.TypeVeryHappyFromPage;

							adapter.close();
						}
					}
				}

			}
		});

		okPage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (currentUser == null) {
					Toast.makeText(getActivity(), "ابتدا باید وارد شوید", Toast.LENGTH_SHORT).show();
				} else {

					adapter.open();
					if (adapter.isUserHappyFromPage(currentUser.getId(), ObjectID, StaticValues.TypeSadFromPage)
							|| adapter.isUserHappyFromPage(currentUser.getId(), ObjectID,
									StaticValues.TypeVerySadFromPage)
							|| adapter.isUserHappyFromPage(currentUser.getId(), ObjectID,
									StaticValues.TypeVeryHappyFromPage)) {
						Toast.makeText(getActivity(), StaticValues.IsRepeatLikeMessage, 0).show();
					} else {

						if (okPage.isEnabled()) {

							enableIdeaButton(false);

							// int countHappy =
							// adapter.getCountHappyOrSadFromPage(ObjectID,
							// StaticValues.TypeHappyFromPage);

							int c = Integer.valueOf(countRazi.getText().toString());

							if (adapter.isUserHappyFromPage(currentUser.getId(), ObjectID,
									StaticValues.TypeHappyFromPage)) {

								iconOk.setBackgroundResource(R.drawable.razi);
								if (c > 0)
									countRazi.setText(c - 1 + "");
							} else {

								iconOk.setBackgroundResource(R.drawable.ok);
								countRazi.setText(c + 1 + "");
							}

							ServerDateForLike date = new ServerDateForLike(getActivity());
							date.delegate = IntroductionFragment.this;
							date.execute("");

							typeLike = StaticValues.TypeHappyFromPage;

							adapter.close();
						}

					}
				}

			}
		});

		hatePage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (currentUser == null) {
					Toast.makeText(getActivity(), "ابتدا باید وارد شوید", Toast.LENGTH_SHORT).show();
				} else {
					adapter.open();

					if (adapter.isUserHappyFromPage(currentUser.getId(), ObjectID, StaticValues.TypeHappyFromPage)
							|| adapter.isUserHappyFromPage(currentUser.getId(), ObjectID,
									StaticValues.TypeVeryHappyFromPage)
							|| adapter.isUserHappyFromPage(currentUser.getId(), ObjectID,
									StaticValues.TypeVerySadFromPage)) {
						Toast.makeText(getActivity(), StaticValues.IsRepeatLikeMessage, 0).show();
					} else {

						if (hatePage.isEnabled()) {

							enableIdeaButton(false);

							// int countSad =
							// adapter.getCountHappyOrSadFromPage(ObjectID,
							// StaticValues.TypeSadFromPage);

							int c = Integer.valueOf(countShaki.getText().toString());

							if (adapter.isUserHappyFromPage(currentUser.getId(), ObjectID,
									StaticValues.TypeSadFromPage)) {

								iconsad.setBackgroundResource(R.drawable.narazi);
								if (c > 0)
									countShaki.setText(c - 1 + "");
							} else {

								iconsad.setBackgroundResource(R.drawable.hate);
								countShaki.setText(c + 1 + "");
							}

							ServerDateForLike date = new ServerDateForLike(getActivity());
							date.delegate = IntroductionFragment.this;
							date.execute("");

							typeLike = StaticValues.TypeSadFromPage;

							adapter.close();

						}
					}
				}

			}
		});

		VeryhatePage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (currentUser == null) {
					Toast.makeText(getActivity(), "ابتدا باید وارد شوید", Toast.LENGTH_SHORT).show();
				} else {
					adapter.open();

					if (adapter.isUserHappyFromPage(currentUser.getId(), ObjectID, StaticValues.TypeHappyFromPage)
							|| adapter.isUserHappyFromPage(currentUser.getId(), ObjectID,
									StaticValues.TypeVeryHappyFromPage)
							|| adapter.isUserHappyFromPage(currentUser.getId(), ObjectID,
									StaticValues.TypeSadFromPage)) {
						Toast.makeText(getActivity(), StaticValues.IsRepeatLikeMessage, 0).show();
					} else {

						if (VeryhatePage.isEnabled()) {

							enableIdeaButton(false);

							int c = Integer.valueOf(countVeryShaki.getText().toString());

							// int countVerySad =
							// adapter.getCountHappyOrSadFromPage(ObjectID,
							// StaticValues.TypeVerySadFromPage);

							if (adapter.isUserHappyFromPage(currentUser.getId(), ObjectID,
									StaticValues.TypeVerySadFromPage)) {

								iconVerysad.setBackgroundResource(R.drawable.very_narazi);
								if (c > 0)
									countVeryShaki.setText(c - 1 + "");
							} else {

								iconVerysad.setBackgroundResource(R.drawable.very_hate);
								countVeryShaki.setText(c + 1 + "");
							}

							ServerDateForLike date = new ServerDateForLike(getActivity());
							date.delegate = IntroductionFragment.this;
							date.execute("");

							typeLike = StaticValues.TypeVerySadFromPage;

							adapter.close();

						}
					}
				}

			}
		});

		close.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				bottomSheet.startAnimation(exitToDown);

				bottomSheet.setVisibility(View.GONE);
				action.setVisibility(View.VISIBLE);

				descriptionPost.setText("");
			}
		});
		send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (ut.getCurrentUser() == null) {

					Toast.makeText(getActivity(), "ابتدا باید وارد شوید", 0).show();

					bottomSheet.startAnimation(enterFromDown);
					bottomSheet.setVisibility(View.GONE);
					action.setVisibility(View.VISIBLE);

				} else {

					descriptionPostTxt = descriptionPost.getText().toString();

					if (!"".equals(descriptionPostTxt) || ImageConvertedToByte != null) {

						ServerDate sDate = new ServerDate(getActivity());
						sDate.delegate = IntroductionFragment.this;
						sDate.execute("");

						descriptionPost.setText("");

						bottomSheet.startAnimation(exitToDown);

						bottomSheet.setVisibility(View.GONE);
						action.setVisibility(View.VISIBLE);

					} else {
						Toast.makeText(getActivity(), "وارد کردن متن یا عکس الزامی است", 0).show();

					}

				}

			}
		});

		selectImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent i = new Intent(Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				getActivity().startActivityFromFragment(IntroductionFragment.this, i, GalleryCode);
			}
		});
		deleteImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				ImageConvertedToByte = null;
				selectImage.setBackgroundResource(R.drawable.circle_drawable);
				selectImage.setLayoutParams(paramsPic);
				selectImage.setImageResource(R.drawable.no_img_profile);

			}
		});

		action.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {

				// FragmentManager fm =
				// getActivity().getSupportFragmentManager();
				// MyDialog = new DialogpostTitleFragment(ObjectID,
				// positionBrand);
				// MyDialog.show(fm, "My_Dialog_Dialog");

				bottomSheet.startAnimation(enterFromDown);
				bottomSheet.setVisibility(View.VISIBLE);
				action.setVisibility(View.INVISIBLE);

				ImageConvertedToByte = null;
				descriptionPost.setText("");

				// createNewPost();

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

				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + txtPhone.getText().toString()));
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

				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + txtCellphone.getText().toString()));
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);

			}
		});

		email.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String b_email = txtEmail.getText().toString();
				Intent email = new Intent(Intent.ACTION_SEND);
				email.putExtra(Intent.EXTRA_EMAIL, new String[] { b_email });
				email.setType("message/rfc822");

				startActivity(Intent.createChooser(email, "Choose an Email client :"));

			}

		});

		addressRelative.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// Toast.makeText(getActivity(), "ok",
				// Toast.LENGTH_SHORT).show();
				// Intent intent = new
				// Intent(android.content.Intent.ACTION_VIEW, Uri.parse(
				// "https://www.google.com/maps/dir/36.2476613,59.4998502/Mashhad,+Khorasan+Razavi/Khorasan+Razavi,+Mashhad,+Kolahdooz+Blvd,+No.+47/@36.2934197,59.5606058,15z/data=!4m15!4m14!1m0!1m5!1m1!1s0x3f6c911abe4131d7:0xc9c57e3a9318753b!2m2!1d59.6167549!2d36.2604623!1m5!1m1!1s0x3f6c91798c9d172b:0xaf638c4e2e2ac720!2m2!1d59.5749626!2d36.2999667!3e2"));
				// startActivity(intent);
			}
		});
		sendSMS.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// add the phone number in the data
				Uri uri = Uri.parse("smsto:");

				Intent smsSIntent = new Intent(Intent.ACTION_SENDTO, uri);
				// add the message at the sms_body extra field
				smsSIntent.putExtra("sms_body", object.getName() + "\n" + "همراه : " + "\n" + object.getCellphone()
						+ "\n" + "تلفن :" + "\n" + object.getPhone() + "\n" + "آدرس :" + "\n" + object.getAddress());
				try {
					startActivity(smsSIntent);
				} catch (Exception ex) {
					Toast.makeText(getActivity(), "Your sms has failed...", Toast.LENGTH_LONG).show();
					ex.printStackTrace();
				}
			}
		});

		WebsiteRelative.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (object.getSite() != null) {
					String url = "http://" + object.getSite();
					FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame, new FragmentShowSite(url));
					trans.addToBackStack(null);
					trans.commit();
				}

			}
		});
	}

	private void startCropImage() {

		Intent intent = new Intent(getActivity(), CropImage.class);
		intent.putExtra(CropImage.IMAGE_PATH, mFileTemp.getPath());
		intent.putExtra(CropImage.SCALE, true);

		intent.putExtra(CropImage.ASPECT_X, 3);
		intent.putExtra(CropImage.ASPECT_Y, 3);

		startActivityForResult(intent, PIC_CROP);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == GalleryCode) {
			try {

				long sizePicture = 0; // MB

				InputStream inputStream = getActivity().getContentResolver().openInputStream(data.getData());
				FileOutputStream fileOutputStream = new FileOutputStream(mFileTemp);
				Utility.copyStream(inputStream, fileOutputStream);
				fileOutputStream.close();
				inputStream.close();

				if (mFileTemp != null)
					sizePicture = mFileTemp.length() / 1024 / 1024;

				if (sizePicture > MaxSizeImageSelected)
					Toast.makeText(getActivity(),
							"حجم عکس انتخاب شده باید کمتر از " + MaxSizeImageSelected + "مگابایت باشد ",
							Toast.LENGTH_LONG).show();
				else
					startCropImage();

			} catch (Exception e) {

				Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
			}

		}
		if (requestCode == PIC_CROP && data != null) {
			String path = data.getStringExtra(CropImage.IMAGE_PATH);
			if (path == null) {
				return;
			}
			Bitmap bitmap = null;
			if (mFileTemp.getPath() != null) {

				ImageConvertedToByte = Utility.compressImage(mFileTemp);
				bitmap = BitmapFactory.decodeFile(mFileTemp.getPath());

			}
			if (bitmap != null) {

				selectImage.setImageBitmap(Utility.getclip(bitmap));
				selectImage.setLayoutParams(paramsPic);
			}

		}

		super.onActivityResult(requestCode, resultCode, data);

	}

	private void setonClickImage() {
		headerImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String pathImage = object.getImagePath1();
				String namePage = object.getName();

				DialogShowImage showImageDialog = new DialogShowImage(getActivity(), pathImage, namePage);
				showImageDialog.show();

			}
		});

		profileImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				String PathProfile = object.getImagePath2();
				String namePage = object.getName();
				DialogShowImage showImageDialog = new DialogShowImage(getActivity(), PathProfile, namePage);
				showImageDialog.show();

			}
		});

		footerImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String pathImage = object.getImagePath3();
				String namePage = object.getName();

				DialogShowImage showImageDialog = new DialogShowImage(getActivity(), pathImage, namePage);
				showImageDialog.show();
			}
		});

		footerLinear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				ListView lv = (ListView) v.getParent().getParent().getParent();

				View c = lv.getChildAt(0);
				int scrollY = -c.getTop() + lv.getFirstVisiblePosition() * c.getHeight();

				FragmentTransaction trans = ((MainActivity) getActivity()).getSupportFragmentManager()
						.beginTransaction();
				FixedPostFragment fragment = new FixedPostFragment(cityId);

				Bundle bundle = new Bundle();
				bundle.putString("Id", String.valueOf(ObjectID));
				bundle.putInt("positionScroll", scrollY);

				fragment.setArguments(bundle);
				// trans.addToBackStack("PostFramgnet");
				trans.replace(R.id.content_frame, fragment);
				trans.commit();

			}
		});

	}

	private void LikeCommentAction() {

		likePost.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (currentUser == null) {
					Toast.makeText(getActivity(), "برای درج لایک ابتدا باید وارد شوید", Toast.LENGTH_SHORT).show();
				} else {

					ServerDateForLike date = new ServerDateForLike(getActivity());
					date.delegate = IntroductionFragment.this;
					date.execute("");

					typeLike = StaticValues.TypeLikeFixedPost;

					adapter.open();
					int countlike = adapter.LikeInObject_count(ObjectID, typeLike);
					adapter.close();

					likePost.setBackgroundResource(R.drawable.like_froum_on);
					countLikePost.setText(String.valueOf(countlike + 1));

					enableButton(false);
					// ringProgressDialog = ProgressDialog.show(getActivity(),
					// "", StaticValues.MessagePleaseWait, true);

				}

			}

		});

		followPage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (currentUser == null) {
					Toast.makeText(getActivity(), "برای دنبال کردن باید وارد شوید", Toast.LENGTH_SHORT).show();
				} else {

					ServerDateForLike date = new ServerDateForLike(getActivity());
					date.delegate = IntroductionFragment.this;
					date.execute("");

					typeLike = StaticValues.TypeLikePage;

					adapter.open();
					int countlike = adapter.LikeInObject_count(ObjectID, typeLike);
					adapter.close();

					iconFollow.setBackgroundResource(R.drawable.ic_followed);
					// CountLikeIntroduction.setText(String.valueOf(countlike +
					// 1));

					enableButton(false);

					// ringProgressDialog = ProgressDialog.show(getActivity(),
					// "", StaticValues.MessagePleaseWait, true);

					// flag = true;
					// LikeOrComment = true;

				}
			}

		});

		EditPage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new IntroductionEditFragment(ObjectID));
				trans.addToBackStack(null);
				trans.commit();

			}
		});
		shareBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
				sharingIntent.setType("text/plain");
				// String shareBody = x.getDescription();
				// sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
				// object.getName());

				sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
						object.getName() + "\n" + "تلفن :" + "\n" + object.getPhone() + "\n" + "فکس :" + "\n"
								+ object.getFax() + "\n" + "موبایل :" + "\n" + object.getCellphone() + "\n" + "ایمیل :"
								+ "\n" + object.getEmail() + "\n" + "آدرس : " + "\n" + object.getAddress() + "\n"
								+ "فیس بوک : " + "\n" + object.getFacebook() + "\n" + "لینکدین : " + "\n"
								+ object.getLinkedIn() + "\n" + "توئیتر :" + "\n" + object.getTwitter() + "\n"
								+ " سایت : " + "\n" + object.getSite() + "\n" + "  گوگل پلاس : " + "\n"
								+ object.getGoogle() + "\n" + "اینستاگرام : " + "\n" + object.getInstagram() + "\n"
								+ "لینک کاتالوگ :" + "\n" + object.getPdf1() + "\n" + "لیست قیمت" + "\n"
								+ object.getPdf2() + "\n" + "Pdf : " + "\n" + object.getPdf3() + "\n" + "لینک آپارات"
								+ "\n" + object.getPdf4() + "\n" + "توضیحات :" + "\n" + object.getDescription());
				startActivity(Intent.createChooser(sharingIntent, "اشتراک از طریق"));
			}
		});

	}

	private void AgencyServiceSettingActionButton() {
		agency.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame,
						new ProvinceFragment(StaticValues.MainItem1, ObjectID, StaticValues.TypeObjectIsAgency));
				trans.commit();
				// pageId.edit().putInt("brandID", object.getId()).commit();
				// pageId.edit().putInt("IsAgency",
				// StaticValues.TypeObjectIsAgency).commit();
				// pageId.edit().putInt("main object id",
				// object.getMainObjectId()).commit();
				Toast.makeText(getActivity(), "mainObjectId send brand = " + object.getMainObjectId() + " agency ", 0)
						.show();
			}
		});

		service.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame,
						new ProvinceFragment(StaticValues.MainItem1, ObjectID, StaticValues.TypeObjectIsService));
				trans.commit();

				// pageId.edit().putInt("brandID", object.getId()).commit();
				// pageId.edit().putInt("IsAgency",
				// StaticValues.TypeObjectIsService).commit();
				// pageId.edit().putInt("main object id",
				// object.getMainObjectId()).commit();
				Toast.makeText(getActivity(), "MainObjectId = " + object.getMainObjectId() + "service", 0).show();

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
								Toast.makeText(getActivity(), "ابتدا باید وارد شوید", 0).show();
						}

						if (item.getTitle().equals("افزودن به علاقه مندی ها")) {
							adapter.open();
							addToFavorite(ut.getCurrentUser().getId(), 4, object.getId());
							adapter.close();
						}
						if (item.getTitle().equals("کپی")) {

							ut.CopyToClipboard(object.getDescription());

						}
						if (item.getTitle().equals("گزارش تخلف")) {

							if (ut.getCurrentUser() != null)
								ut.reportAbuse(object.getUserId(), StaticValues.TypeReportFixedPostIntroduction,
										object.getId(), object.getDescription(), ObjectID, 0, cityId);
							else
								Toast.makeText(getActivity(), "ابتدا باید وارد شوید", 0).show();
						}

						return false;
					}
				};

				popupMenu.setOnMenuItemClickListener(menuitem);

			}
		});

	}

	private void setValues() {

		adapter.open();
		int countcmt = adapter.CommentInObject_count(ObjectID);
		CountCommentIntroduction.setText(String.valueOf(countcmt));

		int countlike = adapter.LikeInObject_count(ObjectID, 0);
		// CountLikeIntroduction.setText(String.valueOf(countlike));

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
		txtDesc.setText(object.getDescription(), true);
		if (object.getSite() != null && !object.getSite().equals("null"))
			txtWebsite.setText(object.getSite());

		else
			txtWebsite.setText("");

		// setCountPost();

		TextView numVisit = (TextView) header.findViewById(R.id.numVisitPage);

		numVisit.setText(object.getCountView() + "");
		CountLikeIntroduction.setText(object.getCountFollower() + "");
		countPost.setText(object.getCountPost() + "");

		CountAgencyTxt.setText(object.getCountAgency() + "");
		CountServiceTxt.setText(object.getCountService() + "");
	}

	public void setCountPost() {

		// adapter.open();
		// countPost.setText(adapter.CountPostUser(object.getId()) + "");
		// adapter.close();
	}

	private void showPeopleLikedBtn() {
		personPage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				List<Integer> idsUsers = new ArrayList<Integer>();
				List<String> dateLike = new ArrayList<String>();

				adapter.open();
				ArrayList<LikeInObject> likedist = adapter.getAllLikeFromObject(ObjectID, 0);
				adapter.close();

				for (int i = 0; i < likedist.size(); i++) {
					idsUsers.add(likedist.get(i).getUserId());
					dateLike.add(likedist.get(i).getDatetime());
				}

				if (likedist.size() == 0) {
					Toast.makeText(getActivity(), "دنبال کننده ای ثبت نشده است", 0).show();
				} else {
					DialogPersonLikedObject dia = new DialogPersonLikedObject(getActivity(), ObjectID, idsUsers,
							dateLike);
					ut.setSizeDialog(dia);

				}
			}
		});

		// personPost.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		//
		// adapter.open();
		// ArrayList<LikeInObject> likedist = adapter
		// .getAllLikeFromObject(ObjectID, 1);
		//
		// adapter.close();
		// if (likedist.size() == 0) {
		// Toast.makeText(getActivity(), "لایکی ثبت نشده است", 0)
		// .show();
		// } else {
		// DialogPersonLikedObject dia = new DialogPersonLikedObject(
		// getActivity(), ObjectID, likedist);
		// dia.show();
		// }
		// }
		// });
	}

	private void getImageFromServer() {
		if (getActivity() != null) {

			GetDatesImagesObject update = new GetDatesImagesObject(getActivity());
			update.delegate = IntroductionFragment.this;
			String[] para = new String[4];
			para[0] = "ImageServerDate";
			para[1] = String.valueOf(object.getId());
			para[2] = "masoud";
			para[3] = "0";
			update.execute(para);
			// LikeOrComment = true;
			// saveVisitFalg = false;
			// isFinish = false;

		}

		// loadingProgressHeader.setVisibility(View.VISIBLE);
		//
		// new Thread(new Runnable() {
		// public void run() {
		// while (f1 == false) {
		//
		// try {
		// Thread.sleep(1000);
		// loadingProgressHeader.post(new Runnable() {
		// public void run() {
		// counter = counter + 2;
		// loadingProgressHeader.setProgress(counter);
		// }
		// });
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		//
		// // Update the progress bar
		//
		// }
		//
		// }
		// }).start();
		//
		// // loadingProgressProfile.setVisibility(View.VISIBLE);
		// loadingProgressFooter.setVisibility(View.VISIBLE);
		// new Thread(new Runnable() {
		// public void run() {
		// while (f1 == false) {
		//
		// try {
		// Thread.sleep(1000);
		// loadingProgressFooter.post(new Runnable() {
		// public void run() {
		// counter = counter + 2;
		// loadingProgressFooter.setProgress(counter);
		// }
		// });
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		//
		// // Update the progress bar
		//
		// }
		//
		// }
		// }).start();
	}

	private void checkInternet() {

		if (currentUser != null) {
			if (checkUsers() == true) {

				if (ut.isNetworkConnected()) {
					// Toast.makeText(getActivity(), "Connected", 0).show();

					ServerDateForVisit date = new ServerDateForVisit(getActivity());
					date.delegate = IntroductionFragment.this;
					date.execute("");

					// saveVisitFalg = true;

				} else {
					// Toast.makeText(getActivity(), "Disconnected", 0).show();

					adapter.open();
					adapter.insertVisitToDb(ut.getCurrentUser().getId(), StaticValues.TypeObjectVisit, ObjectID);
					adapter.close();
				}

			} else {
				// getImageFromServer();

				// ServerDate date = new ServerDate(getActivity());
				// date.delegate = IntroductionFragment.this;
				// date.execute("");
				//
				// saveVisitFalg = false;
				// isFinish = true;
			}

		} else {

			// getImageFromServer();
		}
	}

	private void sendVisit() {

		if (getActivity() != null) {

			adapter.open();
			visitList = adapter.getAllVisitItems();
			adapter.close();

			Visit vis = null;

			if (visitList.size() != 0) {

				if (counterVisit < visitList.size()) {

					vis = visitList.get(counterVisit);

					userId = vis.getUserId();
					typeId = vis.getTypeId();
					ItemId = vis.getObjectId();

					params = new LinkedHashMap<String, String>();
					SavingVisit saving = new SavingVisit(getActivity());
					saving.delegate = IntroductionFragment.this;

					params.put("TableName", "Visit");
					params.put("UserId", String.valueOf(userId));
					params.put("TypeId", String.valueOf(typeId));
					params.put("ObjectId", String.valueOf(ItemId));
					params.put("ModifyDate", String.valueOf(currentTime));
					params.put("Date", String.valueOf(currentTime));

					params.put("IsUpdate", "0");
					params.put("Id", "0");

					saving.execute(params);

					counterVisit++;

					isFinish = false;
					// saveVisitFalg = true;
					// sendVisit();
				} else {

					params = new LinkedHashMap<String, String>();
					SavingVisit saving = new SavingVisit(getActivity());
					saving.delegate = IntroductionFragment.this;

					params.put("TableName", "Visit");
					params.put("UserId", String.valueOf(userId));
					params.put("TypeId", String.valueOf(typeId));
					params.put("ObjectId", String.valueOf(ObjectID));
					params.put("ModifyDate", String.valueOf(currentTime));
					params.put("Date", String.valueOf(currentTime));

					params.put("IsUpdate", "0");
					params.put("Id", "0");

					saving.execute(params);

					adapter.open();
					adapter.deleteVisit();
					adapter.close();

					saveVisitFalg = false;
					isFinish = true;

				}

			} else {

				if (checkUsers() == true) {
					userId = ut.getCurrentUser().getId();
					typeId = StaticValues.TypeObjectVisit;
					// int idObj = object.getId();

					params = new LinkedHashMap<String, String>();
					SavingVisit saving = new SavingVisit(getActivity());
					saving.delegate = IntroductionFragment.this;

					params.put("TableName", "Visit");
					params.put("UserId", String.valueOf(userId));
					params.put("TypeId", String.valueOf(typeId));
					params.put("ObjectId", String.valueOf(ObjectID));
					params.put("ModifyDate", String.valueOf(currentTime));
					params.put("Date", String.valueOf(currentTime));

					params.put("IsUpdate", "0");
					params.put("Id", "0");

					saving.execute(params);
					// saveVisitFalg = false;
					isFinish = true;

				}
				// getImageFromServer();

			}

			// getCountVisitFromServer();

		}
	}

	public void addToFavorite(int currentUserId, int source, int ItemId) {

		if (adapter.IsUserFavoriteItem(currentUserId, ItemId, source) == true) {
			Toast.makeText(getActivity(), " قبلا در لیست علاقه مندی ها ذخیره شده است ", 0).show();
		} else {
			adapter.insertFavoritetoDb(0, currentUserId, ItemId, source);
			Toast.makeText(getActivity(), "به لیست علاقه مندی ها اضافه شد ", 0).show();
		}
	}

	private void setFont() {

		lableShare.setTypeface(ut.SetFontCasablanca());
		lableEdit.setTypeface(ut.SetFontCasablanca());

		namePage.setTypeface(ut.SetFontCasablanca());
		lableFollow.setTypeface(ut.SetFontCasablanca());

		lableNumFollow.setTypeface(ut.SetFontCasablanca());

		lableNumView.setTypeface(ut.SetFontCasablanca());
		lableNumPost.setTypeface(ut.SetFontCasablanca());

		lableAgency.setTypeface(ut.SetFontCasablanca());
		lableservice.setTypeface(ut.SetFontCasablanca());

		lableSendBusinessCard.setTypeface(ut.SetFontIranSans());

		txtDesc.setTypeface(ut.SetFontIranSans());
		txtAddress.setTypeface(ut.SetFontIranSans());

		descriptionPost.setTypeface(ut.SetFontIranSans());

	}

	public int groupPosition(int groupPosition) {
		gp = groupPosition;
		return gp;
	}

	private boolean checkUsers() {

		// if isSave = true allow to save visit on server
		// else don't allow to save
		boolean isSave = true;

		adapter.open();
		List<SubAdmin> subAdminList = adapter.getAdmin(object.getId());
		adapter.close();

		boolean fl1 = true;

		List<Integer> Ids = new ArrayList<Integer>();
		if (subAdminList.size() > 0)
			for (int i = 0; i < subAdminList.size(); i++) {
				Ids.add(subAdminList.get(i).getUserId());
			}

		for (int j = 0; j < Ids.size(); j++) {
			if (currentUser.getId() == Ids.get(j))
				fl1 = false;
		}
		if (currentUser == null)
			isSave = true;
		else

		if (fl1 == false || currentUser.getId() == object.getUserId())
			isSave = false;

		return isSave;

	}

	public void CommentId(int Id) {
		commentId = Id;
	}

	public void getPost() {

		adapter.open();
		setting = adapter.getSettings();
		adapter.close();

		GetPostByObjectId getVisit = new GetPostByObjectId(getActivity());
		getVisit.delegate = IntroductionFragment.this;
		String[] params = new String[5];
		params[0] = "Post";
		params[1] = setting.getServerDate_Start_Post();
		params[2] = setting.getServerDate_End_Post();
		params[3] = "0";
		params[4] = String.valueOf(ObjectID);

		getVisit.execute(params);

	}

	@Override
	public void ResultServer(String output) {

		if (ut.checkError(output) == false) {

			if (!output.contains("anyType")) {
				ut.parseQuery(output);
				fillListView();

			}

			setCountPost();

			getImagePost();

		}
	}

	@Override
	public void resultSaveVisit(String output) {

		if (ut.checkError(output) == false) {

			if (isFinish == false) {

				Visit vis = null;

				if (visitList.size() != 0) {

					if (counterVisit < visitList.size()) {

						vis = visitList.get(counterVisit);

						userId = vis.getUserId();
						typeId = vis.getTypeId();
						ItemId = vis.getObjectId();

						params = new LinkedHashMap<String, String>();
						SavingVisit saving = new SavingVisit(getActivity());
						saving.delegate = IntroductionFragment.this;

						params.put("TableName", "Visit");
						params.put("UserId", String.valueOf(userId));
						params.put("TypeId", String.valueOf(typeId));
						params.put("ObjectId", String.valueOf(ItemId));
						params.put("ModifyDate", String.valueOf(currentTime));
						params.put("Date", String.valueOf(currentTime));

						params.put("IsUpdate", "0");
						params.put("Id", "0");

						saving.execute(params);

						counterVisit++;
						isFinish = false;
						// saveVisitFalg = true;
						// sendVisit();
					} else {

						params = new LinkedHashMap<String, String>();
						SavingVisit saving = new SavingVisit(getActivity());
						saving.delegate = IntroductionFragment.this;

						params.put("TableName", "Visit");
						params.put("UserId", String.valueOf(userId));
						params.put("TypeId", String.valueOf(typeId));
						params.put("ObjectId", String.valueOf(ObjectID));
						params.put("ModifyDate", String.valueOf(currentTime));
						params.put("Date", String.valueOf(currentTime));

						params.put("IsUpdate", "0");
						params.put("Id", "0");

						saving.execute(params);

						adapter.open();
						adapter.deleteVisit();
						adapter.close();

						// saveVisitFalg = false;
						isFinish = true;

					}

				}
			}

		}

	}

	private void getCountVisitFromServer() {

		if (visitCounter < ArrayPosts.size()) {

			adapter.open();
			post = adapter.getPostItembyid(ArrayPosts.get(visitCounter).getId());
			adapter.close();

			UpdatingVisit updateVisit = new UpdatingVisit(getActivity());
			updateVisit.delegate = IntroductionFragment.this;
			Map<String, String> serv = new LinkedHashMap<String, String>();

			serv.put("tableName", "Visit");
			serv.put("objectId", String.valueOf(post.getId()));
			serv.put("typeId", StaticValues.TypePostVisit + "");
			updateVisit.execute(serv);

		}

	}

	@Override
	public void resultCountView(String output) {

		if (!output.contains("Exception")) {

			adapter.open();
			adapter.updateCountView("Post", post.getId(), Integer.valueOf(output));
			adapter.close();

			ArrayPosts.get(visitCounter).setCountView(Integer.valueOf(output));
			ListAdapterPost.notifyDataSetChanged();

		}
		visitCounter++;
		getCountVisitFromServer();

	}

	private void getImagePost() {

		if (ArrayPosts.size() > 0) {

			if (postItemId < ArrayPosts.size()) {

				postItem = ArrayPosts.get(postItemId);
				String imagePostDate = postItem.getServerDate();

				if (imagePostDate == null)
					imagePostDate = "";

				if (getActivity() != null) {

					GetImagePost ImageUpdating = new GetImagePost(getActivity());
					ImageUpdating.delegate = IntroductionFragment.this;
					maps = new LinkedHashMap<String, String>();

					maps.put("tableName", "Post");
					maps.put("Id", String.valueOf(postItem.getId()));
					maps.put("fromDate", imagePostDate);

					ImageUpdating.execute(maps);
				}

			} else {
				postItemId = 0;
				getImageDate();
			}
		}
	}

	@Override
	public void processFinish(byte[] output) {

		if (!output.equals("Exception")) {

			String ImageAddress = "";

			Time time = new Time();
			time.setToNow();
			String Date = Long.toString(time.toMillis(false));

			if (output != null) {

				ImageAddress = ut.CreateFileString(output, "_" + ObjectID + "_" + Date + postItem.getId(), "Mechanical",
						"Post", "post");
			}
			if (!ImageAddress.equals("")) {

				adapter.open();
				adapter.insertImageAddressToDb("Post", postItem.getId(), ImageAddress, serverDate);
				adapter.close();
			}

			postItemId++;
			getImagePost();

		}

	}

	private void getImageDate() {

		if (postItemId < ArrayPosts.size()) {

			postItem = ArrayPosts.get(postItemId);

			if (getActivity() != null) {

				GetDateImagePost getDateService = new GetDateImagePost(getActivity());
				getDateService.delegate = IntroductionFragment.this;
				Map<String, String> items = new LinkedHashMap<String, String>();

				items.put("tableName", "getPostImageDate");
				items.put("Id", String.valueOf(postItem.getId()));

				getDateService.execute(items);

			}
		} else {

			getCountVisitFromServer();
		}

	}

	@Override
	public void CommProcessFinish(String output) {

		if (ut.checkError(output) == false) {

			if (isPostCount == true) {

				adapter.open();
				adapter.updateCountPost(ObjectID, Integer.valueOf(output));
				adapter.close();

				countPost.setText(output);

				isPostCount = false;
				getCountInformation();

			} else {
				CountLikeIntroduction.setText(output);
				adapter.open();
				adapter.updateCountFollower(ObjectID, Integer.valueOf(output));
				adapter.close();
			}

		}

		// if (output.contains("Exception") || output.contains(("anyType")))
		// output = "";
		//
		// adapter.open();
		// adapter.UpdateImageServerDatePost(postItem.getId(), output);
		// adapter.close();
		//
		// postItemId++;
		//
		// getImageDate();

	}

	@Override
	public void resultLike(String output) {

		if (ut.checkError(output) == false) {

			int likeId = -1;

			try {

				likeId = Integer.valueOf(output);

				if (typeLike == StaticValues.TypeVeryHappyFromPage) {

					adapter.open();
					adapter.insertHappyOrSadFromPageToDb(likeId, currentUser.getId(), ObjectID, serverDate,
							StaticValues.TypeVeryHappyFromPage);

					int countVeryHappy = adapter.getCountHappyOrSadFromPage(ObjectID,
							StaticValues.TypeVeryHappyFromPage);
					adapter.close();

					iconVeryOk.setBackgroundResource(R.drawable.very_ok);

					countVeryRazi.setText(countVeryHappy + "");

				} //

				if (typeLike == StaticValues.TypeHappyFromPage) {

					adapter.open();
					adapter.insertHappyOrSadFromPageToDb(likeId, currentUser.getId(), ObjectID, serverDate,
							StaticValues.TypeHappyFromPage);

					int countHappy = adapter.getCountHappyOrSadFromPage(ObjectID, StaticValues.TypeHappyFromPage);
					adapter.close();

					iconOk.setBackgroundResource(R.drawable.ok);

					countRazi.setText(countHappy + "");

				} //
				if (typeLike == StaticValues.TypeSadFromPage) {

					adapter.open();
					adapter.insertHappyOrSadFromPageToDb(likeId, currentUser.getId(), ObjectID, serverDate,
							StaticValues.TypeSadFromPage);

					int countSad = adapter.getCountHappyOrSadFromPage(ObjectID, StaticValues.TypeSadFromPage);
					adapter.close();

					iconsad.setBackgroundResource(R.drawable.hate);

					countShaki.setText(countSad + "");

				}

				if (typeLike == StaticValues.TypeVerySadFromPage) {

					adapter.open();
					adapter.insertHappyOrSadFromPageToDb(likeId, currentUser.getId(), ObjectID, serverDate,
							StaticValues.TypeVerySadFromPage);

					int countSad = adapter.getCountHappyOrSadFromPage(ObjectID, StaticValues.TypeVerySadFromPage);
					adapter.close();

					iconVerysad.setBackgroundResource(R.drawable.very_hate);

					countVeryShaki.setText(countSad + "");

				}

				if (typeLike == StaticValues.TypeLikePage || typeLike == StaticValues.TypeLikeFixedPost) {

					adapter.open();
					adapter.insertLikeInObjectToDb(likeId, currentUser.getId(), ObjectID, serverDate, typeLike);
					int countlike = adapter.LikeInObject_count(ObjectID, typeLike);
					adapter.close();

					if (typeLike == StaticValues.TypeLikePage) {

						iconFollow.setBackgroundResource(R.drawable.ic_followed);
						CountLikeIntroduction.setText(countlike + "");

					} else {
						likePost.setBackgroundResource(R.drawable.like_froum_on);
						countLikePost.setText(countlike + "");

					}
				}

				enableButton(true);
				enableIdeaButton(true);
				// ut.showRingProgressDialog(ringProgressDialog, false);

			} catch (Exception e) {

				ut.showErrorToast();
				enableButton(true);
				enableIdeaButton(true); // ut.showRingProgressDialog(ringProgressDialog,
										// false);

			}
		} else {
			ut.showErrorToast();
			enableButton(true);

			// ut.showRingProgressDialog(ringProgressDialog, false);

		}

	}

	@Override
	public void resultDateLike(String output) {

		if (ut.checkError(output) == false) {

			serverDate = output;
			if (typeLike == StaticValues.TypeVeryHappyFromPage) {
				adapter.open();
				if (adapter.isUserHappyFromPage(currentUser.getId(), ObjectID, StaticValues.TypeVeryHappyFromPage)) {

					params = new LinkedHashMap<String, String>();
					if (getActivity() != null) {

						DeletingLike deleting = new DeletingLike(getActivity());
						deleting.delegate = IntroductionFragment.this;

						params.put("TableName", "LikeInObject");
						params.put("UserId", String.valueOf(currentUser.getId()));
						params.put("ObjectId", String.valueOf(ObjectID));
						params.put("LikeType", String.valueOf(typeLike));

						deleting.execute(params);
					}
				} else {

					params = new LinkedHashMap<String, String>();

					if (getActivity() != null) {
						SavingLike saving = new SavingLike(getActivity());
						saving.delegate = IntroductionFragment.this;

						params.put("TableName", "LikeInObject");

						params.put("UserId", String.valueOf(currentUser.getId()));
						params.put("ObjectId", String.valueOf(ObjectID));
						params.put("Date", output);
						params.put("ModifyDate", output);

						params.put("LikeType", String.valueOf(typeLike));

						params.put("IsUpdate", "0");
						params.put("Id", "0");

						serverDate = output;

						saving.execute(params);

					}
				}

				adapter.close();

			} // end type very happy
			if (typeLike == StaticValues.TypeHappyFromPage) {
				adapter.open();
				if (adapter.isUserHappyFromPage(currentUser.getId(), ObjectID, StaticValues.TypeHappyFromPage)) {

					params = new LinkedHashMap<String, String>();
					if (getActivity() != null) {

						DeletingLike deleting = new DeletingLike(getActivity());
						deleting.delegate = IntroductionFragment.this;

						params.put("TableName", "LikeInObject");
						params.put("UserId", String.valueOf(currentUser.getId()));
						params.put("ObjectId", String.valueOf(ObjectID));
						params.put("LikeType", String.valueOf(typeLike));

						deleting.execute(params);
					}
				} else {

					params = new LinkedHashMap<String, String>();

					if (getActivity() != null) {
						SavingLike saving = new SavingLike(getActivity());
						saving.delegate = IntroductionFragment.this;

						params.put("TableName", "LikeInObject");

						params.put("UserId", String.valueOf(currentUser.getId()));
						params.put("ObjectId", String.valueOf(ObjectID));
						params.put("Date", output);
						params.put("ModifyDate", output);

						params.put("LikeType", String.valueOf(typeLike));

						params.put("IsUpdate", "0");
						params.put("Id", "0");

						serverDate = output;

						saving.execute(params);

					}
				}

				adapter.close();

			} // end type happy

			if (typeLike == StaticValues.TypeSadFromPage) {

				adapter.open();
				if (adapter.isUserHappyFromPage(currentUser.getId(), ObjectID, StaticValues.TypeSadFromPage)) {

					params = new LinkedHashMap<String, String>();
					if (getActivity() != null) {

						DeletingLike deleting = new DeletingLike(getActivity());
						deleting.delegate = IntroductionFragment.this;

						params.put("TableName", "LikeInObject");
						params.put("UserId", String.valueOf(currentUser.getId()));
						params.put("ObjectId", String.valueOf(ObjectID));
						params.put("LikeType", String.valueOf(typeLike));

						deleting.execute(params);
					}
				} else {

					params = new LinkedHashMap<String, String>();

					if (getActivity() != null) {
						SavingLike saving = new SavingLike(getActivity());
						saving.delegate = IntroductionFragment.this;

						params.put("TableName", "LikeInObject");

						params.put("UserId", String.valueOf(currentUser.getId()));
						params.put("ObjectId", String.valueOf(ObjectID));
						params.put("Date", output);
						params.put("ModifyDate", output);

						params.put("LikeType", String.valueOf(typeLike));

						params.put("IsUpdate", "0");
						params.put("Id", "0");

						serverDate = output;

						saving.execute(params);

					}
				}

				adapter.close();

			}

			if (typeLike == StaticValues.TypeVerySadFromPage) {

				adapter.open();
				if (adapter.isUserHappyFromPage(currentUser.getId(), ObjectID, StaticValues.TypeVerySadFromPage)) {

					params = new LinkedHashMap<String, String>();
					if (getActivity() != null) {

						DeletingLike deleting = new DeletingLike(getActivity());
						deleting.delegate = IntroductionFragment.this;

						params.put("TableName", "LikeInObject");
						params.put("UserId", String.valueOf(currentUser.getId()));
						params.put("ObjectId", String.valueOf(ObjectID));
						params.put("LikeType", String.valueOf(typeLike));

						deleting.execute(params);
					}
				} else {

					params = new LinkedHashMap<String, String>();

					if (getActivity() != null) {
						SavingLike saving = new SavingLike(getActivity());
						saving.delegate = IntroductionFragment.this;

						params.put("TableName", "LikeInObject");

						params.put("UserId", String.valueOf(currentUser.getId()));
						params.put("ObjectId", String.valueOf(ObjectID));
						params.put("Date", output);
						params.put("ModifyDate", output);

						params.put("LikeType", String.valueOf(typeLike));

						params.put("IsUpdate", "0");
						params.put("Id", "0");

						serverDate = output;

						saving.execute(params);

					}
				}

				adapter.close();

			}

			else {

				if (typeLike == StaticValues.TypeLikePage || typeLike == StaticValues.TypeLikeFixedPost) {
					adapter.open();
					if (adapter.isUserLikeIntroductionPage(currentUser.getId(), ObjectID, typeLike)) {

						params = new LinkedHashMap<String, String>();
						if (getActivity() != null) {

							DeletingLike deleting = new DeletingLike(getActivity());
							deleting.delegate = IntroductionFragment.this;

							params.put("TableName", "LikeInObject");
							params.put("UserId", String.valueOf(currentUser.getId()));
							params.put("ObjectId", String.valueOf(ObjectID));
							params.put("LikeType", String.valueOf(typeLike));

							deleting.execute(params);

							// ut.showRingProgressDialog(ringProgressDialog,
							// true);
						}

					} else {

						params = new LinkedHashMap<String, String>();

						if (getActivity() != null) {
							SavingLike saving = new SavingLike(getActivity());
							saving.delegate = IntroductionFragment.this;

							params.put("TableName", "LikeInObject");

							params.put("UserId", String.valueOf(currentUser.getId()));
							params.put("ObjectId", String.valueOf(ObjectID));
							params.put("Date", output);
							params.put("ModifyDate", output);

							params.put("LikeType", String.valueOf(typeLike));

							params.put("IsUpdate", "0");
							params.put("Id", "0");

							serverDate = output;

							saving.execute(params);

							// ut.showRingProgressDialog(ringProgressDialog,
							// true);
						}

					}
				}

			}
			adapter.close();

		} else {
			ut.showErrorToast();
			// ut.showRingProgressDialog(ringProgressDialog, false);
		}
	}

	@Override
	public void resultDeleteLike(String output) {

		if (ut.checkError(output) == false) {

			int likeId = -1;

			try {
				likeId = Integer.valueOf(output);

				if (typeLike == StaticValues.TypeVeryHappyFromPage) {

					adapter.open();

					adapter.deleteHappyOrSadFromPage(currentUser.getId(), ObjectID, StaticValues.TypeVeryHappyFromPage);
					int countHappy = adapter.getCountHappyOrSadFromPage(ObjectID, StaticValues.TypeVeryHappyFromPage);

					adapter.close();

					iconVeryOk.setBackgroundResource(R.drawable.very_razi);
					countVeryRazi.setText(countHappy + "");

				}

				if (typeLike == StaticValues.TypeHappyFromPage) {

					adapter.open();

					adapter.deleteHappyOrSadFromPage(currentUser.getId(), ObjectID, StaticValues.TypeHappyFromPage);
					int countHappy = adapter.getCountHappyOrSadFromPage(ObjectID, StaticValues.TypeHappyFromPage);

					adapter.close();

					iconOk.setBackgroundResource(R.drawable.razi);
					countRazi.setText(countHappy + "");

				}

				if (typeLike == StaticValues.TypeSadFromPage) {

					adapter.open();

					adapter.deleteHappyOrSadFromPage(currentUser.getId(), ObjectID, StaticValues.TypeSadFromPage);
					int countSad = adapter.getCountHappyOrSadFromPage(ObjectID, StaticValues.TypeSadFromPage);

					adapter.close();

					iconsad.setBackgroundResource(R.drawable.narazi);
					countShaki.setText(countSad + "");

				}

				if (typeLike == StaticValues.TypeVerySadFromPage) {

					adapter.open();

					adapter.deleteHappyOrSadFromPage(currentUser.getId(), ObjectID, StaticValues.TypeVerySadFromPage);
					int countSad = adapter.getCountHappyOrSadFromPage(ObjectID, StaticValues.TypeVerySadFromPage);

					adapter.close();

					iconVerysad.setBackgroundResource(R.drawable.very_narazi);
					countVeryShaki.setText(countSad + "");

				}

				if (typeLike == StaticValues.TypeLikePage || typeLike == StaticValues.TypeLikeFixedPost) {

					adapter.open();
					adapter.deleteLikeIntroduction(currentUser.getId(), ObjectID, typeLike);
					int countlike = adapter.LikeInObject_count(ObjectID, typeLike);
					adapter.close();

					if (typeLike == StaticValues.TypeLikePage) {

						iconFollow.setBackgroundResource(R.drawable.ic_following);
						// CountLikeIntroduction.setText(String.valueOf(countlike));

					} else {

						likePost.setBackgroundResource(R.drawable.like_froum_off);
						countLikePost.setText(String.valueOf(countlike));

					}
				}

				enableButton(true);

				// ut.showRingProgressDialog(ringProgressDialog, false);

			} catch (Exception e) {
				ut.showErrorToast();
				enableButton(true);

				// ut.showRingProgressDialog(ringProgressDialog, false);
			}

		} else

		{
			ut.showErrorToast();
			enableButton(true);

			// ut.showRingProgressDialog(ringProgressDialog, false);
		}

	}

	@Override
	public void resultDateImagesObject(String output) {

		if (ut.checkError(output) == false) {

			if (output.contains("-")) {

				String[] strs = new String[3];

				strs[0] = "";
				strs[1] = "";
				strs[2] = "";

				strs = output.split(Pattern.quote("-"), 5);

				String s1 = "";
				String s2 = "";
				String s3 = "";

				if (!"".equals(strs[0]) && s1 != null)
					serverDate = s1 = strs[0];

				if (!"".equals(strs[1]) && s2 != null)
					s2 = strs[1];

				if (!"".equals(strs[2]) && s3 != null)
					s3 = strs[2];

				if (object.getImage1ServerDate() == null || !object.getImage1ServerDate().equals(s1)) {
					object.setImage1ServerDate(s1);
					f1 = true;
				}
				if (object.getImage2ServerDate() == null || !object.getImage2ServerDate().equals(s2)) {
					object.setImage2ServerDate(s2);
					f2 = true;
				}

				if (object.getImage3ServerDate() == null || !object.getImage3ServerDate().equals(s3)) {
					object.setImage3ServerDate(s3);
					f3 = true;
				}

				if (f1 || f2 || f3) {
					GetImagesHeaderProfileFooter updating = new GetImagesHeaderProfileFooter(getActivity());
					updating.delegate = IntroductionFragment.this;
					maps = new LinkedHashMap<String, String>();
					// maps.put("tableName", "Object1");
					maps.put("tableName", "All");
					maps.put("Id", String.valueOf(ObjectID));
					maps.put("fromDate1", object.getImage1ServerDate());
					maps.put("fromDate2", object.getImage2ServerDate());
					maps.put("fromDate3", object.getImage3ServerDate());
					updating.execute(maps);

				}
			}

		} else {
			ut.showErrorToast();
		}

	}

	@Override
	public void resultHeaderProfileFooterImages(List<byte[]> output) {

		if (output != null && output.size() > 0) {

			adapter.open();

			if (f1)
				if (output.get(0) != null) {
					ut.CreateFile(output.get(0), object.getId(), "Mechanical", "Profile", "header", "Object");

					object = adapter.getObjectbyid(ObjectID);

					String PathImageHeader = "";
					PathImageHeader = object.getImagePath1();
					Bitmap b = BitmapFactory.decodeFile(PathImageHeader);

					if (b != null)
						headerImage.setImageBitmap(b);
					else
						headerImage.setBackgroundResource(R.drawable.no_image_header);
					adapter.updateObjectImage1ServerDate(ObjectID, serverDate);
				}
			if (f2)
				if (output.get(1) != null) {
					ut.CreateFile(output.get(1), object.getId(), "Mechanical", "Profile", "profile", "Object");
					object = adapter.getObjectbyid(ObjectID);

					String PathImageProfile = "";
					PathImageProfile = object.getImagePath2();

					Bitmap b = BitmapFactory.decodeFile(PathImageProfile);

					if (b != null)
						profileImage.setImageBitmap(Utility.getclip(b));
					else
						profileImage.setBackgroundResource(R.drawable.no_img_profile);
					adapter.updateObjectImage2ServerDate(ObjectID, serverDate);
				}

			if (f3)
				if (output.get(2) != null) {
					ut.CreateFile(output.get(2), object.getId(), "Mechanical", "Profile", "footer", "Object");
					object = adapter.getObjectbyid(ObjectID);

					String PathImageFooter = "";
					PathImageFooter = object.getImagePath3();

					Bitmap b = BitmapFactory.decodeFile(PathImageFooter);

					if (b != null)
						footerImage.setImageBitmap(b);
					else
						footerImage.setBackgroundResource(R.drawable.no_image_header);

					adapter.updateObjectImage3ServerDate(ObjectID, serverDate);

				}

		} else {
			ut.showErrorToast();
		}
	}

	@Override
	public void resultDateVisit(String output) {

		if (ut.checkError(output) == false) {

			currentTime = output;
			sendVisit();

		}
	}

	@Override
	public void resultImagePost(byte[] output) {

		if (output != null) {

			String ImageAddress = "";

			Time time = new Time();
			time.setToNow();
			String Date = Long.toString(time.toMillis(false));

			if (output != null) {

				ImageAddress = ut.CreateFileString(output, "_" + ObjectID + "_" + Date + postItem.getId(), "Mechanical",
						"Post", "post");
			}
			if (!ImageAddress.equals("")) {

				adapter.open();
				adapter.insertImageAddressToDb("Post", postItem.getId(), ImageAddress, "");
				adapter.close();

				ArrayPosts.get(postItemId).setPhoto(ImageAddress);
				ListAdapterPost.notifyDataSetChanged();
			}

			postItemId++;
			getImagePost();

		} else {
			postItemId++;
			getImagePost();
		}

	}

	@Override
	public void resultDateImagePost(String output) {

		if (ut.checkError(output) == false) {

			if (output.contains("Exception") || output.contains(("anyType")))
				output = "";

			adapter.open();
			adapter.UpdateImageServerDatePost(postItem.getId(), output);
			adapter.close();

			postItemId++;

			getImageDate();

		}

	}

	public void enableButton(boolean isEnable) {

		followPage.setEnabled(isEnable);
		likePost.setEnabled(isEnable);

	}

	public void enableIdeaButton(boolean isEnable) {

		iconVeryOk.setEnabled(isEnable);
		iconOk.setEnabled(isEnable);

		iconsad.setEnabled(isEnable);
		iconVerysad.setEnabled(isEnable);

	}

	public void getCountInformation() {

		if (isPostCount == true) {

			ServiceComm comm = new ServiceComm(getActivity());
			comm.delegate = this;
			Map<String, String> params = new LinkedHashMap<String, String>();
			params.put("tableName", "getPostCountByObjectId");

			params.put("objectId", String.valueOf(ObjectID));
			comm.execute(params);

		} else {
			ServiceComm comm = new ServiceComm(getActivity());
			comm.delegate = this;
			Map<String, String> params = new LinkedHashMap<String, String>();
			params.put("tableName", "getFollowerCountByObjectId");

			params.put("objectId", String.valueOf(ObjectID));
			comm.execute(params);

			isPostCount = false;
		}

	}

	private boolean isAdmin(int userId) {

		// if isSave = true allow to save visit on server
		// else don't allow to save
		boolean isSave = false;

		adapter.open();
		List<SubAdmin> subAdminList = adapter.getAdmin(ObjectID);
		adapter.close();

		boolean fl1 = false;

		List<Integer> Ids = new ArrayList<Integer>();
		if (subAdminList.size() > 0)
			for (int i = 0; i < subAdminList.size(); i++) {
				Ids.add(subAdminList.get(i).getUserId());
			}

		for (int j = 0; j < Ids.size(); j++) {
			if (userId == Ids.get(j))
				fl1 = true;
		}
		// if (Currentuser == null)
		// isSave = false;
		// else

		if (fl1 == true || userId == object.getUserId())
			isSave = true;

		return isSave;

	}

	@Override
	public void processFinishSaveImage(String output) {

		if (ut.checkError(output) == false) {

			String ImageAddress = "";

			if (ImageConvertedToByte != null) {

				ImageAddress = ut.CreateFileString(ImageConvertedToByte, "_" + currentUser.getId() + "_" + serverDate,
						"Mechanical", "Post", "post");
			}
			if (!ImageAddress.equals("")) {

				adapter.open();
				adapter.insertImageAddressToDb("Post", PostId, ImageAddress, serverDate);
				adapter.close();
				setPostionListPost(1);
				fillListView();

			}

		}
	}

	private void GetHappySad() {

		int type = -1;

		if (typeHappySad == StaticValues.TypeVeryHappyFromPage)
			type = StaticValues.TypeVeryHappyFromPage;

		if (typeHappySad == StaticValues.TypeHappyFromPage)
			type = StaticValues.TypeHappyFromPage;

		if (typeHappySad == StaticValues.TypeSadFromPage)
			type = StaticValues.TypeSadFromPage;

		if (typeHappySad == StaticValues.TypeVerySadFromPage)
			type = StaticValues.TypeVerySadFromPage;

		GetHappySadServer getCount = new GetHappySadServer(getActivity());
		getCount.delegate = IntroductionFragment.this;
		Map<String, String> items = new LinkedHashMap<String, String>();

		items.put("tableName", "getHappySadByObjectId");
		items.put("objectId", String.valueOf(ObjectID));
		items.put("type", String.valueOf(type));

		getCount.execute(items);

	}

	@Override
	public void ResultHappySad(String output) {

		if (ut.checkError(output) == false) {
			adapter.open();

			if (typeHappySad == StaticValues.TypeVeryHappyFromPage) {

				adapter.updateCountHappyOrSad(ObjectID, Integer.valueOf(output), typeHappySad);
				countVeryRazi.setText(output);
				object.setCountVeryHappy(Integer.valueOf(output));

				typeHappySad = StaticValues.TypeHappyFromPage;
				GetHappySad();

			} else if (typeHappySad == StaticValues.TypeHappyFromPage) {

				adapter.updateCountHappyOrSad(ObjectID, Integer.valueOf(output), typeHappySad);
				countRazi.setText(output);
				object.setCountHappy(Integer.valueOf(output));

				typeHappySad = StaticValues.TypeSadFromPage;
				GetHappySad();

			} else if (typeHappySad == StaticValues.TypeSadFromPage) {

				adapter.updateCountHappyOrSad(ObjectID, Integer.valueOf(output), typeHappySad);
				countShaki.setText(output);
				object.setCountSad(Integer.valueOf(output));

				typeHappySad = StaticValues.TypeVerySadFromPage;
				GetHappySad();

			} else {

				adapter.updateCountHappyOrSad(ObjectID, Integer.valueOf(output), typeHappySad);
				countVeryShaki.setText(output);
				object.setCountVerySad(Integer.valueOf(output));

				typeHappySad = -1;

			}

			adapter.close();

			stateHappyOrSad();

		}

	}

	private void stateHappyOrSad() {

		countRazi.setText(object.getCountHappy() + "");
		countShaki.setText(object.getCountSad() + "");

		countVeryRazi.setText(object.getCountVeryHappy() + "");
		countVeryShaki.setText(object.getCountVerySad() + "");

		adapter.open();

		if (currentUser != null) {

			if (adapter.isUserHappyFromPage(currentUser.getId(), ObjectID, StaticValues.TypeVeryHappyFromPage)) {
				iconVeryOk.setBackgroundResource(R.drawable.very_ok);
			} else
				iconVeryOk.setBackgroundResource(R.drawable.very_razi);

			if (adapter.isUserHappyFromPage(currentUser.getId(), ObjectID, StaticValues.TypeHappyFromPage)) {
				iconOk.setBackgroundResource(R.drawable.ok);
			} else
				iconOk.setBackgroundResource(R.drawable.razi);

			if (adapter.isUserHappyFromPage(currentUser.getId(), ObjectID, StaticValues.TypeSadFromPage)) {
				iconsad.setBackgroundResource(R.drawable.hate);
			} else
				iconsad.setBackgroundResource(R.drawable.narazi);

			if (adapter.isUserHappyFromPage(currentUser.getId(), ObjectID, StaticValues.TypeVerySadFromPage)) {
				iconVerysad.setBackgroundResource(R.drawable.very_hate);
			} else
				iconVerysad.setBackgroundResource(R.drawable.very_narazi);

		}

		adapter.close();

	}

	private void getLikeInObjectPage() {

		if (getActivity() != null) {

			AllFollowerObjectServer getDateService = new AllFollowerObjectServer(getActivity());
			getDateService.delegate = IntroductionFragment.this;
			Map<String, String> items = new LinkedHashMap<String, String>();

			items.put("tableName", "getLikeInObjectByObjectId");
			items.put("objectId", String.valueOf(ObjectID));
			items.put("type", String.valueOf(whatTypeLikePage));

			getDateService.execute(items);

		}

	}

	@Override
	public void ResultFollowers(String output) {

		switch (whatTypeLikePage) {
		case StaticValues.TypeLikePage: // followers

			if (ut.checkError(output) == false && !output.contains("anyType")) {
				ut.parseQuery(output);
			}

			whatTypeLikePage = StaticValues.TypeVeryHappyFromPage;

			getLikeInObjectPage();

			break;

		case StaticValues.TypeVeryHappyFromPage: // very happy

			if (ut.checkError(output) == false && !output.contains("anyType")) {
				ut.parseQuery(output);
			}

			whatTypeLikePage = StaticValues.TypeHappyFromPage;

			getLikeInObjectPage();

			break;

		case StaticValues.TypeHappyFromPage: // happy

			if (ut.checkError(output) == false && !output.contains("anyType")) {
				ut.parseQuery(output);
			}
			whatTypeLikePage = StaticValues.TypeSadFromPage;

			getLikeInObjectPage();
			break;

		case StaticValues.TypeSadFromPage: // Sad

			if (ut.checkError(output) == false && !output.contains("anyType")) {
				ut.parseQuery(output);
			}
			whatTypeLikePage = StaticValues.TypeVerySadFromPage;

			getLikeInObjectPage();
			break;

		case StaticValues.TypeVerySadFromPage: // very Sad

			if (ut.checkError(output) == false && !output.contains("anyType")) {
				ut.parseQuery(output);
			}

			break;

		default:
			break;
		}

	}

	private void getSubAdmin() {

		GetSubAdminServer getAdmin = new GetSubAdminServer(getActivity());
		getAdmin.delegate = IntroductionFragment.this;
		Map<String, String> items = new LinkedHashMap<String, String>();

		items.put("tableName", "getAllSubAdminByObjectId");
		items.put("objectId", String.valueOf(ObjectID));

		getAdmin.execute(items);

	}

	@Override
	public void resultSubAdmin(String output) {

		if (ut.checkError(output) == false) {

			ut.parseQuery(output);
			getUserAdmin();

		}

	}

	public void getUserAdmin() {

		adapter.open();
		List<SubAdmin> subAdminList = adapter.getAdmin(object.getId());

		if (adminController < subAdminList.size()) {

			int userIdAdmin = subAdminList.get(adminController).getUserId();
			Users admin = adapter.getUserById(userIdAdmin);

			if (admin == null) {

				if (getActivity() != null) {

					GetUserServer get = new GetUserServer(getActivity());
					get.delegate = IntroductionFragment.this;

					Map<String, String> items = new LinkedHashMap<String, String>();

					items.put("tableName", "getUserById");
					items.put("Id", String.valueOf(userIdAdmin));

					get.execute(items);

				}

			} else {

				adminController++;
				getUserAdmin();
			}

		}

		adapter.close();

	}

	@Override
	public void resultGetUser(String output) {

		if (ut.checkError(output) == false) {

			ut.parseQuery(output);

			adminController++;
			getUserAdmin();

		}

	}

	private void getCountAgencyService() {

		CountAgencyServiceServer getCount = new CountAgencyServiceServer(getActivity());
		getCount.delegate = IntroductionFragment.this;
		Map<String, String> items = new LinkedHashMap<String, String>();

		items.put("tableName", "isObjectHasSubObject");
		items.put("objectId", String.valueOf(ObjectID));
		if (typeAgency == true)
			items.put("agencyService", String.valueOf(StaticValues.TypeObjectIsAgency));
		else
			items.put("agencyService", String.valueOf(StaticValues.TypeObjectIsService));

		getCount.execute(items);

	}

	@Override
	public void ResultCountAgency(String output) {

		if (ut.checkError(output) == false) {
			int value = Integer.valueOf(output);

			if (typeAgency) {

				adapter.open();

				adapter.updateCountAgencyService(ObjectID, value, StaticValues.TypeObjectIsAgency);
				CountAgencyTxt.setText(output);

				typeAgency = false;
				getCountAgencyService();

				adapter.close();

			} else {
				adapter.open();
				adapter.updateCountAgencyService(ObjectID, value, StaticValues.TypeObjectIsService);
				CountServiceTxt.setText(output);

				adapter.close();

			}

			;
		}

	}

	private void getLikeNumberOfPost() {

		if (counterLike < ArrayPosts.size()) {

			Post p = ArrayPosts.get(counterLike);

			pId = p.getId();

			GetCountLike getCountLike = new GetCountLike(getActivity());
			getCountLike.delegate = IntroductionFragment.this;
			Map<String, String> items = new LinkedHashMap<String, String>();

			items.put("tableName", "getLikeInPostCount");
			items.put("id", String.valueOf(pId));

			getCountLike.execute(items);

		} else {
			getCountComment();
		}

	}

	@Override
	public void ResultCountLike(String output) {

		if (ut.checkError(output) == false) {

			adapter.open();

			adapter.updateCountLike("Post", pId, Integer.valueOf(output));

			adapter.close();

			ArrayPosts.get(counterLike).setCountLike(Integer.valueOf(output));
			ListAdapterPost.notifyDataSetChanged();

			counterLike++;
			getLikeNumberOfPost();

		}

	}

	private void getCountComment() {

		if (counterComment < ArrayPosts.size()) {

			Post p = ArrayPosts.get(counterComment);

			pId = p.getId();

			GetCountComment get = new GetCountComment(getActivity());
			get.delegate = IntroductionFragment.this;
			Map<String, String> items = new LinkedHashMap<String, String>();

			items.put("tableName", "getCommentInPostCount");
			items.put("id", String.valueOf(pId));

			get.execute(items);

		}
	}

	@Override
	public void ReultCountComment(String output) {

		if (ut.checkError(output) == false) {

			adapter.open();

			adapter.updateCountComment("Post", pId, Integer.valueOf(output));

			adapter.close();

			ArrayPosts.get(counterComment).setCountComment(Integer.valueOf(output));
			ListAdapterPost.notifyDataSetChanged();

			counterComment++;
			getCountComment();

		}

	}

}