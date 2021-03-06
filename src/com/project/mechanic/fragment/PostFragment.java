package com.project.mechanic.fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.PopupMenu.OnMenuItemClickListener;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.StaticValues;
import com.project.mechanic.adapter.ExpandableCommentPost;
import com.project.mechanic.crop.CropImage;
import com.project.mechanic.entity.CommentInPost;
import com.project.mechanic.entity.LikeInPost;
import com.project.mechanic.entity.Object;
import com.project.mechanic.entity.Post;
import com.project.mechanic.entity.Settings;
import com.project.mechanic.entity.SubAdmin;
import com.project.mechanic.entity.Users;
import com.project.mechanic.entity.Visit;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.inter.CommInterface;
import com.project.mechanic.inter.GetAsyncInterface;
import com.project.mechanic.inter.VisitSaveInterface;
import com.project.mechanic.interfaceServer.GetAllCommentInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.server.GetAllCommentById;
import com.project.mechanic.service.Deleting;
import com.project.mechanic.service.Saving;
import com.project.mechanic.service.SavingVisit;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.service.ServiceComm;
import com.project.mechanic.service.UpdatingImage;
import com.project.mechanic.utility.Utility;
import com.project.mechanic.view.TextViewEx;

public class PostFragment extends Fragment
		implements AsyncInterface, GetAsyncInterface, CommInterface, VisitSaveInterface, GetAllCommentInterface {

	/**/
	Button dfragbutton;
	Button alertdfragbutton;
	// FloatingActionButton action;
	DialogPostFragment MyDialog;
	// DialogpostTitleFragment MyDialogShow;
	int /* postId , */ objectId;
	/**/
	int IDcurrentUser, itemId;
	List<Visit> visitList;
	private File mFileTemp;
	public static final String TEMP_PHOTO_FILE_NAME = "temp_photo.jpg";
	private static final int CAMERA_CODE = 101, GALLERY_CODE = 201, CROPING_CODE = 301;
	final int PIC_CROP = 10;
	DataBaseAdapter adapter;
	ExpandableCommentPost exadapter;
	private Uri mImageCaptureUri;

	TextView titletxt, dateTopic, countComment, countLike, nametxt;
	TextViewEx descriptiontxt;
	LinearLayout /* addComment, */ likeTopic;
	ImageButton sharebtn;
	ImageView profileImg, icLike;

	ImageView postImage;

	int postid;
	RelativeLayout count, commentcounter;

	Post topics;
	List<String> menuItems = new ArrayList<String>();

	DialogcmtInpost dialog;
	ArrayList<CommentInPost> commentGroup, ReplyGroup;
	// String currentDate;

	Map<CommentInPost, List<CommentInPost>> mapCollection;
	ExpandableListView exlistview;

	View header;
	Users CurrentUser, uu;
	// PersianDate date;
	Utility util;
	int id, gp;
	Users user;
	int userId, commentId = 0;

	Saving saving;
	Deleting deleting;
	Map<String, String> params;

	// ProgressDialog ringProgressDialog;

	String serverDate = "";
	ServerDate date;

	ArrayList<Integer> missedIds;
	boolean check = false;
	int controller = 0;
	int iid;

	ServiceComm service;
	UpdatingImage updating;
	Map<String, String> maps;
	boolean LikeOrComment; // like == true & comment == false
	boolean isFinish = false, saveVisitFalg;
	int counterVisit = 0;
	String currentTime = "";
	int ItemId, typeId;
	boolean IsPost = true;

	int positionPost = 0, positionBrand = 0;
	int cityId = -1;
	Settings settings;

	public PostFragment(int cityId) {
		this.cityId = cityId;
	}

	@SuppressLint("InflateParams")
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceStdataate) {

		((MainActivity) getActivity()).setActivityTitle(R.string.Forums);
		View view = inflater.inflate(R.layout.fragment_post, null);

		adapter = new DataBaseAdapter(getActivity());
		util = new Utility(getActivity());

		user = new Users();

		missedIds = new ArrayList<Integer>();

		header = getActivity().getLayoutInflater().inflate(R.layout.header_expandable, null);

		icLike = (ImageView) header.findViewById(R.id.likeIcon);

		/**/
		if (getArguments().getString("ObjectId") != null)
			objectId = Integer.valueOf(getArguments().getString("ObjectId"));

		if (getArguments() != null) {
			positionPost = getArguments().getInt("positionPost");
			positionBrand = getArguments().getInt("positionBrand");

		}
		// action = (FloatingActionButton) view.findViewById(R.id.fab);
		// action.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// DialogPostFragment fragment = new DialogPostFragment();
		//
		// FragmentTransaction trans = getActivity()
		// .getSupportFragmentManager().beginTransaction();
		// trans.replace(R.id.content_frame, fragment);
		// trans.addToBackStack(null);
		// trans.commit();
		//
		// Bundle bundle = new Bundle();
		// bundle.putInt("Id", ObjectID);
		// fragment.setArguments(bundle);
		//
		// // FragmentManager fm = getFragmentManager();
		// // MyDialogShow = new DialogpostTitleFragment();
		// // MyDialogShow.show(fm, "Dialog Fragment");
		//
		// }
		// });
		/**/

		// start find view

		postImage = (ImageView) header.findViewById(R.id.postImage);

		// titletxt = (TextView) header.findViewById(R.id.title_topic);
		descriptiontxt = (TextViewEx) header.findViewById(R.id.description_topic);
		dateTopic = (TextView) header.findViewById(R.id.date_cc);
		countComment = (TextView) header.findViewById(R.id.numberOfCommentTopic);
		countLike = (TextView) header.findViewById(R.id.txtNumofLike_CmtFroum);
		nametxt = (TextView) header.findViewById(R.id.name_cc);

		// addComment = (LinearLayout)
		// header.findViewById(R.id.addCommentToTopic);
		likeTopic = (LinearLayout) header.findViewById(R.id.LikeTopicLinear);

		sharebtn = (ImageButton) header.findViewById(R.id.sharefroumicon);
		profileImg = (ImageView) header.findViewById(R.id.iconfroumtitle);
		// exlistview = (ExpandableListView) view
		// .findViewById(R.id.listvCmt_Introduction_post);
		exlistview = (ExpandableListView) view.findViewById(R.id.listvCmt_Introduction_post);

		// count = (RelativeLayout) header.findViewById(R.id.countLike);
		// commentcounter = (RelativeLayout) header
		// .findViewById(R.id.countComment);

		// end find view
		if (getArguments().getString("Id") != null)
			postid = Integer.valueOf(getArguments().getString("Id"));

		adapter.open();
		CurrentUser = util.getCurrentUser();
		if (CurrentUser == null) {
		}

		else
			IDcurrentUser = CurrentUser.getId();

		topics = adapter.getPostItembyid(postid);
		Users u = adapter.getUserbyid(topics.getUserId());
		final Object obj = adapter.getObjectbyid(topics.getObjectId());

		if (u != null) {
			userId = u.getId();

			nametxt.setText(obj.getName());
			nametxt.setTypeface(util.SetFontIranSans());
			RelativeLayout rl = (RelativeLayout) header.findViewById(R.id.profileLinearcommenterinContinue);

			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(rl.getLayoutParams());

			lp.width = (int) (util.getScreenwidth() / StaticValues.RateImagePostFragmentPage);
			lp.height = (int) (util.getScreenwidth() / StaticValues.RateImagePostFragmentPage);
			lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			profileImg.setLayoutParams(lp);

			if (obj.getImagePath2() == null) {
				profileImg.setBackgroundResource(R.drawable.circle_drawable);

				profileImg.setImageResource(R.drawable.no_img_profile);
				profileImg.setLayoutParams(lp);

			} else {
				// byte[] bytepic = u.getImage();

				try {
					Bitmap bmp = BitmapFactory.decodeFile(obj.getImagePath2());
					if (bmp != null)
						profileImg.setImageBitmap(Utility.getclip(bmp));

					profileImg.setLayoutParams(lp);
				} catch (OutOfMemoryError e) {
					e.printStackTrace();
				}

			}
		}

		// if (!topics.getTitle().isEmpty()) {
		// titletxt.setText(topics.getTitle());
		// titletxt.setVisibility(View.VISIBLE);
		// }
		if (!topics.getDescription().isEmpty()) {
			descriptiontxt.setText(topics.getDescription(), true);
			descriptiontxt.setVisibility(View.VISIBLE);
		}

		RelativeLayout layoutImg = (RelativeLayout) header.findViewById(R.id.imageLayout);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(layoutImg.getLayoutParams());
		lp.width = util.getScreenwidth();
		lp.height = util.getScreenwidth();

		if (topics.getPhoto() != null) {
			File imgFile = new File(topics.getPhoto());

			if (imgFile.exists()) {
				try {
					Bitmap myBitmap = BitmapFactory.decodeFile(topics.getPhoto());
					postImage.setImageBitmap(myBitmap);
					postImage.setVisibility(View.VISIBLE);
					postImage.setLayoutParams(lp);
				} catch (OutOfMemoryError e) {
					e.printStackTrace();
				}

			}
		}
		countComment.setText(topics.getCountComment() + "");
		countLike.setText(topics.getCountLike() + "");
		dateTopic.setText(util.getPersianDate(topics.getDate()));

		// titletxt.setTypeface(util.SetFontCasablanca());
		descriptiontxt.setTypeface(util.SetFontIranSans());
		descriptiontxt.setPadding(5, 5, 5, 5);
		postImage.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				if (postImage.getDrawable() != null) {
					Bitmap bitmapHeader = ((BitmapDrawable) postImage.getDrawable()).getBitmap();
					byte[] ImageConvertedToByte = Utility.CompressBitmap(bitmapHeader);
					// Bitmap image=((BitmapDrawable)
					// postImage.getDrawable()).getBitmap();
					// Drawable myDrawable = postImage.getDrawable();
					DialogShowImage showImageDialog = new DialogShowImage(getActivity(), ImageConvertedToByte, "");
					showImageDialog.show();
				}
			}
		});

		profileImg.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				FragmentTransaction trans = ((MainActivity) getActivity()).getSupportFragmentManager()
						.beginTransaction();
				InformationUser fragment = new InformationUser();
				Bundle bundle = new Bundle();
				bundle.putInt("userId", userId);
				fragment.setArguments(bundle);
				trans.replace(R.id.content_frame, fragment);
				trans.commit();

			}
		});
		// addComment.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		//
		// if (CurrentUser == null) {
		// Toast.makeText(getActivity(),
		// "برای درج کامنت ابتدا باید وارد شوید",
		// Toast.LENGTH_SHORT).show();
		//
		// } else {
		//
		// dialog = new DialogcmtInpost(PostFragment.this, 0,
		// getActivity(), postid, R.layout.dialog_addcomment,
		// 2);
		// dialog.show();
		// exadapter.notifyDataSetChanged();
		// }
		// }
		// });

		commentGroup = adapter.getCommentInPostbyPostid(postid, 0);

		mapCollection = new LinkedHashMap<CommentInPost, List<CommentInPost>>();

		List<CommentInPost> reply = null;
		for (CommentInPost comment : commentGroup) {
			reply = adapter.getReplyCommentbyCommentIDPost(postid, comment.getId());
			mapCollection.put(comment, reply);
		}

		if (commentGroup != null) {
			Users uId;
			for (int i = 0; i < commentGroup.size(); i++) {

				CommentInPost cm = commentGroup.get(i);
				int uidd = cm.getUserId();
				uId = adapter.getUserById(uidd);

				if (uId == null) {
					missedIds.add(uidd);
				}

			}

		}

		if (missedIds.size() > 0) {
			if (getActivity() != null) {

				// ringProgressDialog = ProgressDialog.show(getActivity(), "",
				// "لطفا منتظر بمانید...", true);
				// ringProgressDialog.setCancelable(true);
				date = new ServerDate(getActivity());
				date.delegate = PostFragment.this;
				date.execute("");
				check = true;

			}
		}

		exlistview.addHeaderView(header);

		exadapter = new ExpandableCommentPost(getActivity(), (ArrayList<CommentInPost>) commentGroup, mapCollection,
				this, postid);

		exadapter.notifyDataSetChanged();

		exlistview.setAdapter(exadapter);

		if (CurrentUser == null) {
			icLike.setBackgroundResource(R.drawable.like_froum_off);
			// count.setBackgroundResource(R.drawable.count_like_off);
		} else {
			if (adapter.isUserLikedPost(CurrentUser.getId(), postid)) {
				icLike.setBackgroundResource(R.drawable.like);
				// count.setBackgroundResource(R.drawable.count_like);

			} else {

				icLike.setBackgroundResource(R.drawable.like_froum_off);
				// count.setBackgroundResource(R.drawable.count_like_off);

			}
		}
		adapter.close();
		// count.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// adapter.open();
		// ArrayList<LikeInPost> likedist = adapter
		// .getLikepostLikeInPostByPostId(postid);
		//
		// adapter.close();
		// if (likedist.size() == 0) {
		// Toast.makeText(getActivity(), "لایکی ثبت نشده است", 0)
		// .show();
		// } else {
		// DialogPersonLikedPost dia = new DialogPersonLikedPost(
		// getActivity(), postid, likedist);
		// dia.show();
		// }
		// }
		// });

		// این کد ها برای مشخص شدن مبدا ارسالی برای آپدیت کردن لیست می باشد
		// وقتی کاربر در صفحه فروم بدون کامنت ، نظری ثبت می کند به این صفحه
		// منتقل می شود و این کد ها برای مشخص کردن مبدا و انجام عمل آپدیت کردن
		// استفاده می شود
		SharedPreferences realizeIdComment = getActivity().getSharedPreferences("Id", 0);
		int destinyId = realizeIdComment.getInt("main_Id", -1);
		if (destinyId == 1371) {
			updateList();
		}

		likeTopic.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				adapter.open();

				if (CurrentUser == null) {
					Toast.makeText(getActivity(), "برای درج لایک ابتدا باید وارد شوید", Toast.LENGTH_SHORT).show();
				} else {

					// ringProgressDialog = ProgressDialog.show(getActivity(),
					// "", "لطفا منتظر بمانید...", true);
					// ringProgressDialog.setCancelable(true);
					date = new ServerDate(getActivity());
					date.delegate = PostFragment.this;
					date.execute("");
					LikeOrComment = true;
					IsPost = false;

					icLike.setBackgroundResource(R.drawable.like);
					int count = adapter.LikeInPost_count(postid);
					countLike.setText(String.valueOf(count + 1));

				}
				adapter.close();

			}

		});

		sharebtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String body = topics.getDescription() + "\n" + " مشاهده کامل گفتگو در: "
						+ "<a href=\"mechanical://SplashActivity\">اینجا</a> ";
				Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
				sharingIntent.setType("text/html");
				// sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
				// topics.getTitle());
				sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml(body));
				startActivity(Intent.createChooser(sharingIntent, "اشتراک از طریق"));
			}
		});

		ImageView report = (ImageView) view.findViewById(R.id.reportImage);
		report.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				final int userIdsender = topics.getUserId();
				final String t = topics.getDescription();
				itemId = topics.getId();

				if (util.getCurrentUser() != null) {

					if (CurrentUser.getId() == userIdsender) {

						menuItems.clear();
						menuItems.add("ارسال پیام");
						menuItems.add("کپی");
						menuItems.add("حذف");

					} else if (CurrentUser.getId() == obj.getUserId()) {

						menuItems.clear();
						menuItems.add("ارسال پیام");
						menuItems.add("کپی");
						menuItems.add("حذف");

					} else {

						// menuItems = new ArrayList<String>();

						menuItems.clear();
						menuItems.add("ارسال پیام");
						menuItems.add("افزودن به علاقه مندی ها");
						menuItems.add("کپی");
						menuItems.add("گزارش تخلف");
					}
				} else {
					// menuItems = new ArrayList<String>();

					menuItems.clear();
					menuItems.add("کپی");
				}

				final PopupMenu popupMenu = util.ShowPopupMenu(menuItems, v);
				popupMenu.show();

				OnMenuItemClickListener menuitem = new OnMenuItemClickListener() {

					@Override
					public boolean onMenuItemClick(MenuItem item) {

						if (item.getTitle().equals("ارسال پیام")) {

							util.sendMessage("Froum");

						}
						if (item.getTitle().equals("افزودن به علاقه مندی ها")) {
							// adapter.open();
							// addToFavorite(util.getCurrentUser().getId(), 1,
							// itemId);
							// adapter.close();
						}

						if (item.getTitle().equals("کپی")) {

							util.CopyToClipboard(t);

						}

						if (item.getTitle().equals("گزارش تخلف")) {

							util.reportAbuse(userIdsender, StaticValues.TypeReportPostFragment, itemId, t, objectId, 0,
									cityId);

						}
						if (item.getTitle().equals("حذف")) {
							if (util.getCurrentUser() != null && util.getCurrentUser().getId() == userIdsender) {
								// deleteItems(itemId);
							} else {

								Toast.makeText(getActivity(), "", 0).show();
							}
						}

						return false;
					}

				};
				popupMenu.setOnMenuItemClickListener(menuitem);

				// DialogLongClick dia = new DialogLongClick(getActivity(), 1,
				// topics.getUserId(), topics.getId(),
				// PostFragment.this, topics.getDescription());
				// WindowManager.LayoutParams lp = new
				// WindowManager.LayoutParams();
				// lp.copyFrom(dia.getWindow().getAttributes());
				// lp.width = (int) (util.getScreenwidth() / 1.5);
				// lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
				// ;
				// dia.show();
				//
				// dia.getWindow().setAttributes(lp);
				// dia.getWindow().setBackgroundDrawable(
				// new ColorDrawable(
				// android.graphics.Color.TRANSPARENT));
			}

		});

		// ImageView send = util.ShowFooterAgahi(getActivity(), true, 3);
		//
		// send.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		//
		// if ("".equals(util.inputComment(getActivity()))) {
		// Toast.makeText(getActivity(), " نظر نمی تواند خالی باشد", 0).show();
		// } else {
		//
		// date = new ServerDate(getActivity());
		// date.delegate = PostFragment.this;
		// date.execute("");
		// LikeOrComment = false;
		// // adapter.open();
		// // adapter.insertCommentInPosttoDb(id,
		// // util.inputComment(getActivity()), postid,
		// // CurrentUser.getId(),
		// // serverDate, commentId);
		// //
		// // adapter.close();
		// IsPost = false;
		// util.ToEmptyComment(getActivity());
		//
		// // util.ReplyLayout(getActivity(), "", false);
		//
		// }
		// }
		// });
		ImageView delete = util.deleteReply(getActivity());

		delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				util.ReplyLayout(getActivity(), "", false);

			}
		});

		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			mFileTemp = new File(Environment.getExternalStorageDirectory(), TEMP_PHOTO_FILE_NAME);
		} else {
			mFileTemp = new File(getActivity().getFilesDir(), TEMP_PHOTO_FILE_NAME);
		}

		ImageView[] TempImage = util.inputCommentAndPickFile(getActivity(), true);

		ImageView sendMessage = TempImage[0];
		ImageView getPicture = TempImage[1];
		getPicture.setVisibility(View.GONE);
		showPicture = TempImage[2];

		sendMessage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (CurrentUser == null) {
					Toast.makeText(getActivity(), "ابتدا باید وارد شوید", Toast.LENGTH_SHORT).show();
				} else {

					if ("".equals(util.inputComment(getActivity()))) {
						Toast.makeText(getActivity(), " نظر نمی تواند خالی باشد", 0).show();
					} else {

						// ringProgressDialog =
						// ProgressDialog.show(getActivity(), "", "لطفا منتظر
						// بمانید...", true);
						// ringProgressDialog.setCancelable(true);
						date = new ServerDate(getActivity());
						date.delegate = PostFragment.this;
						date.execute("");
						LikeOrComment = false;
						IsPost = false;

					}
				}

				// adapter.open();
				// adapter.insertCommentInPosttoDb(id,
				// util.inputComment(getActivity()), postid,
				// CurrentUser.getId(),
				// serverDate, commentId);
				//
				// adapter.close();

				// util.ToEmptyComment(getActivity());
				// util.ReplyLayout(getActivity(), "", false);
				// updateList();
			}
		});
		getPicture.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				selectImageOption();
			}
		});

		TextView countVisit = (TextView) header.findViewById(R.id.countVisit);
		checkInternet();
		adapter.open();
		Post pos = adapter.getPostItembyid(postid);

		countVisit.setText(pos.getCountView() + "");
		adapter.close();

		getAllComment();

		return view;
	}

	@Override
	public void onResume() {
		getView().setFocusableInTouchMode(true);
		getView().requestFocus();
		getView().setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {

				if (event.getAction() == KeyEvent.ACTION_DOWN)

					if (keyCode == KeyEvent.KEYCODE_BACK) {

						FragmentTransaction trans = ((MainActivity) getActivity()).getSupportFragmentManager()
								.beginTransaction();
						IntroductionFragment fragment = new IntroductionFragment(cityId);
						fragment.setPostionListPost(positionPost);

						Bundle bundle = new Bundle();
						bundle.putString("Id", String.valueOf(objectId));
						bundle.putInt("positionBrand", positionBrand);
						fragment.setArguments(bundle);
						// trans.addToBackStack("PostFramgnet");

						trans.replace(R.id.content_frame, fragment);
						// fragment.setPostionListPost(positionPost);

						trans.commit();

						return true;
					}
				return false;

			}
		});
		super.onResume();
	}

	public ImageView showPicture;
	String imgDecodableString;

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		try {
			if (requestCode == GALLERY_CODE && resultCode == Activity.RESULT_OK && null != data) {

				mImageCaptureUri = data.getData();
				// String img = data.getDataString();
				// String filename = (new File(filePath)).getName();

				Toast.makeText(getActivity(), mImageCaptureUri.getPath(), Toast.LENGTH_LONG).show();

				// Bitmap myBitmap = BitmapFactory.decodeFile(img);

				// showPicture.setImageBitmap(myBitmap);
				Bitmap bitmap = BitmapFactory.decodeFile(mImageCaptureUri.getPath());
				showPicture.setImageBitmap(bitmap);
				// showPicture.setImageURI(mImageCaptureUri);

				/*
				 * String[] filePathColumn = { MediaStore.Images.Media.DATA };
				 * 
				 * Cursor cursor = getActivity().getContentResolver().query(
				 * selectedImage, filePathColumn, null, null, null);
				 * cursor.moveToFirst();
				 * 
				 * int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				 * imgDecodableString = cursor.getString(columnIndex);
				 * cursor.close();
				 */

				/*
				 * showPicture.setImageBitmap(BitmapFactory
				 * .decodeFile(imgDecodableString));
				 */
				// showPicture.setImageURI(selectedImage);

			} else {
				Toast.makeText(getActivity(), "You haven't picked Image", Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
		}
	}

	private void selectImageOption() {
		final CharSequence[] items = { "از دوربین", "از گالری تصاویر", "انصراف" };

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("افزودن تصویر");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {

				if (items[item].equals("از دوربین")) {

					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp1.jpg");
					mImageCaptureUri = Uri.fromFile(f);
					intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
					startActivityForResult(intent, CAMERA_CODE);

					showPicture.setImageURI(mImageCaptureUri);

				} else if (items[item].equals("از گالری تصاویر")) {

					Intent galleryIntent = new Intent(Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

					startActivityForResult(galleryIntent, GALLERY_CODE);

				} else if (items[item].equals("انصراف")) {
					dialog.dismiss();
				}
			}
		});

		builder.show();
	}

	private void startCropImage() {

		Intent intent = new Intent(getActivity(), CropImage.class);
		intent.putExtra(CropImage.IMAGE_PATH, mFileTemp.getPath());
		intent.putExtra(CropImage.SCALE, true);

		intent.putExtra(CropImage.ASPECT_X, 3);
		intent.putExtra(CropImage.ASPECT_Y, 3);

		startActivityForResult(intent, PIC_CROP);
	}

	// public void onActivityResult(int requestCode, int resultCode, Intent
	// data) {
	//
	// if (requestCode == profileLoadCode) {
	// try {
	//
	// InputStream inputStream = getActivity().getContentResolver()
	// .openInputStream(data.getData());
	// FileOutputStream fileOutputStream = new FileOutputStream(
	// mFileTemp);
	// Utility.copyStream(inputStream, fileOutputStream);
	// fileOutputStream.close();
	// inputStream.close();
	//
	// startCropImage();
	//
	// } catch (Exception e) {
	//
	// Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG)
	// .show();
	// }
	// }
	// if (requestCode == PIC_CROP && data != null) {
	// String path = data.getStringExtra(CropImage.IMAGE_PATH);
	// if (path == null) {
	// return;
	// }
	// Bitmap bitmap = null;
	// if (mFileTemp.getPath() != null)
	// bitmap = BitmapFactory.decodeFile(mFileTemp.getPath());
	// if (bitmap != null) {
	// // profileImageEdit.setImageBitmap(bitmap);
	// }
	//
	// }
	//
	// super.onActivityResult(requestCode, resultCode, data);
	//
	// }

	public int getPostId() {
		return id;
	}

	public int groupPosition(int groupPosition) {
		gp = groupPosition;
		return gp;
	}

	public int getDipsFromPixel(float pixels) {
		// Get the screen's density scale
		final float scale = getResources().getDisplayMetrics().density;
		// Convert the dps to pixels, based on density scale
		return (int) (pixels * scale + 0.5f);
	}

	public void updateList() {
		adapter.open();

		commentGroup = adapter.getCommentInPostbyPostid(postid, 0);

		mapCollection = new LinkedHashMap<CommentInPost, List<CommentInPost>>();

		List<CommentInPost> reply = null;
		for (CommentInPost comment : commentGroup) {
			reply = adapter.getReplyCommentbyCommentIDPost(postid, comment.getId());
			mapCollection.put(comment, reply);
		}

		countComment.setText(adapter.CommentInPost_count(postid).toString());

		exadapter = new ExpandableCommentPost(getActivity(), (ArrayList<CommentInPost>) commentGroup, mapCollection,
				this, postid);

		exadapter.notifyDataSetChanged();

		exlistview.setAdapter(exadapter);
		exlistview.setSelectedGroup(mapCollection.size() - 1);

		adapter.close();

	}

	public void setcount() {
		countComment.setText(adapter.CommentInPost_count(postid).toString());

	}

	public void CommentId(int Id) {
		commentId = Id;
	}

	public void expanding(int groupPosition, int childPosition, boolean TypeAction) {
		adapter.open();

		commentGroup = adapter.getCommentInPostbyPostid(postid, 0);

		mapCollection = new LinkedHashMap<CommentInPost, List<CommentInPost>>();

		List<CommentInPost> reply = null;
		for (CommentInPost comment : commentGroup) {
			reply = adapter.getReplyCommentbyCommentIDPost(postid, comment.getId());
			mapCollection.put(comment, reply);
		}

		countComment.setText(adapter.CommentInPost_count(postid).toString());

		exadapter = new ExpandableCommentPost(getActivity(), (ArrayList<CommentInPost>) commentGroup, mapCollection,
				this, postid);
		exadapter.notifyDataSetChanged();
		exlistview.setAdapter(exadapter);
		if (groupPosition <= mapCollection.size()) {

			if (TypeAction) {

				exlistview.expandGroup(groupPosition);

				if (childPosition != -1 && childPosition > 0)
					exlistview.setSelectedChild(groupPosition, childPosition, true);
				else
					exlistview.setSelectedGroup(groupPosition);

			} else {

				if (childPosition != -1 && childPosition > 0) {

					exlistview.expandGroup(groupPosition);
					exlistview.setSelectedChild(groupPosition, childPosition - 1, true);

				} else {
					if (groupPosition > 0) {

						exlistview.setSelectedGroup(groupPosition - 1);
					}
				}

			}
		}
		adapter.close();

		// exlistview.setSelectedChild(groupPosition, 1, condition);

	}

	@Override
	public void processFinish(String output) {
		// Toast.makeText(getActivity(), output, 0).show();
		// if (ringProgressDialog != null)
		// ringProgressDialog.dismiss();

		if (!output.contains("Exception")) {
			if (IsPost == true) {
				//
				if (saveVisitFalg == true) {

					if (counterVisit == 0)
						currentTime = output;
					sendVisit();

				}
				/*
				 * else { // if (isFinish == true) { // isFinish = false; // }
				 * else // return; }
				 */

			} else {
				// if (ringProgressDialog != null)
				// ringProgressDialog.dismiss();

				adapter.open();
				int id = -1;
				try {
					id = Integer.valueOf(output);

					if (LikeOrComment == true) {
						if (adapter.isUserLikedPost(CurrentUser.getId(), postid)) {
							adapter.deleteLikeFromPost(CurrentUser.getId(), postid);
							icLike.setBackgroundResource(R.drawable.like_froum_off);
							// count.setBackgroundResource(R.drawable.count_like_off);

							countLike.setText(adapter.LikeInPost_count(postid).toString());
						} else {
							adapter.insertLikeInPostToDb(id, CurrentUser.getId(), postid, serverDate, 0);
							icLike.setBackgroundResource(R.drawable.like);
							// count.setBackgroundResource(R.drawable.count_like);

							countLike.setText(adapter.LikeInPost_count(postid).toString());
						}
					} else {
						adapter.open();

						adapter.insertCommentInPosttoDb(id, util.inputComment(getActivity()), postid,
								CurrentUser.getId(), serverDate, commentId);

						adapter.close();
						util.ToEmptyComment(getActivity());
						util.ReplyLayout(getActivity(), "", false);

						if (commentId == 0)
							expanding(exadapter.getGroupCount(), -1, true);
						else {
							;

							expanding(gp, exadapter.getChildrenCount(gp), true);

						}

					}
					// if (ringProgressDialog != null) {
					// ringProgressDialog.dismiss();
					// }
				} catch (NumberFormatException ex) {
					if (output != null && !(output.contains("Exception") || output.contains("java"))) {

						serverDate = output;

						if (LikeOrComment == false) {

							if (getActivity() != null) {
								params = new LinkedHashMap<String, String>();

								saving = new Saving(getActivity());
								saving.delegate = PostFragment.this;

								params.put("TableName", "CommentInPost");

								params.put("Description", util.inputComment(getActivity()));
								params.put("PostId", String.valueOf(postid));
								params.put("UserId", String.valueOf(CurrentUser.getId()));
								params.put("CommentId", String.valueOf(commentId));

								params.put("Date", output);
								params.put("ModifyDate", output);
								params.put("IsUpdate", "0");
								params.put("Id", "0");

								saving.execute(params);
								IsPost = false;
							}
							// ringProgressDialog =
							// ProgressDialog.show(getActivity(), "", "لطفا
							// منتظر بمانید...", true);
							//
							// ringProgressDialog.setCancelable(true);
							new Thread(new Runnable() {

								@Override
								public void run() {

									try {

										Thread.sleep(10000);

									} catch (Exception e) {

									}
								}
							}).start();

						} else {

							if (check == true) {
								getUserFromServer(missedIds, controller);
								return;
							}

							if (adapter.isUserLikedPost(CurrentUser.getId(), postid)) {

								params = new LinkedHashMap<String, String>();
								if (getActivity() != null) {

									deleting = new Deleting(getActivity());
									deleting.delegate = PostFragment.this;

									params.put("TableName", "LikeInPost");
									params.put("UserId", String.valueOf(CurrentUser.getId()));
									params.put("PostId", String.valueOf(postid));

									deleting.execute(params);
									IsPost = false;

								}
							} else {
								params = new LinkedHashMap<String, String>();
								if (getActivity() != null) {

									saving = new Saving(getActivity());
									saving.delegate = PostFragment.this;

									params.put("TableName", "LikeInPost");

									params.put("UserId", String.valueOf(CurrentUser.getId()));
									params.put("PostId", String.valueOf(postid));
									params.put("CommentId", "0");
									params.put("Date", output);
									params.put("ModifyDate", output);

									params.put("IsUpdate", "0");
									params.put("Id", "0");

									saving.execute(params);
									IsPost = false;

								}
							}
						}
					} else {
						Toast.makeText(getActivity(), "خطا در ثبت. پاسخ نا مشخص از سرور", Toast.LENGTH_SHORT).show();
						// if (ringProgressDialog != null) {
						// ringProgressDialog.dismiss();
						// }
					}
				}

				catch (Exception e) {

					Toast.makeText(getActivity(), "خطا در ثبت" + e.toString(), Toast.LENGTH_SHORT).show();
					adapter.close();
					// if (ringProgressDialog != null) {
					// ringProgressDialog.dismiss();
					// }
				}
				adapter.close();

			}
		}

	}

	private void getUserFromServer(ArrayList<Integer> missedIds, int controller) {

		if (controller < missedIds.size()) {

			iid = missedIds.get(controller);

			if (getActivity() != null) {

				service = new ServiceComm(getActivity());
				service.delegate = PostFragment.this;
				Map<String, String> items = new LinkedHashMap<String, String>();
				items.put("tableName", "getUserById");
				items.put("Id", String.valueOf(iid));
				service.execute(items);

			}
		}

	}

	@Override
	public void CommProcessFinish(String output) {

		if (!"".equals(output) && output != null
				&& !(output.contains("Exception") || output.contains("java") || output.contains("soap"))) {
			util.parseQuery(output);

			adapter.open();

			uu = adapter.getUserById(iid);

			adapter.close();
			if (getActivity() != null) {

				updating = new UpdatingImage(getActivity());
				updating.delegate = PostFragment.this;
				maps = new LinkedHashMap<String, String>();
				maps.put("tableName", "Users");
				maps.put("Id", String.valueOf(uu.getId()));
				maps.put("fromDate", uu.getImageServerDate());
				updating.execute(maps);
			}
		} else
			Toast.makeText(getActivity(), "خطا در دریافت کاربران", 0).show();
	}

	@Override
	public void processFinish(byte[] output) {

		util.CreateFile(output, iid, "Mechanical", "Users", "user", "Users");
		adapter.open();
		adapter.UpdateImageServerDate(iid, "Users", serverDate);
		adapter.close();

		updateList();
		// if (ringProgressDialog != null) {
		// ringProgressDialog.dismiss();
		// }
	}

	private void checkInternet() {

		if (CurrentUser != null) {
			if (checkUsers() == true) {

				if (util.isNetworkConnected()) {

					ServerDate date = new ServerDate(getActivity());
					date.delegate = PostFragment.this;
					date.execute("");
					saveVisitFalg = true;

				} else {

					adapter.open();
					adapter.insertVisitToDb(util.getCurrentUser().getId(), StaticValues.TypePostVisit, topics.getId());
					adapter.close();
				}

			}

		}
	}

	private boolean checkUsers() {

		// if isSave = true allow to save visit on server
		// else don't allow to save
		boolean isSave = true;

		adapter.open();
		List<SubAdmin> subAdminList = adapter.getAdmin(objectId);
		adapter.close();

		boolean fl1 = true;

		List<Integer> Ids = new ArrayList<Integer>();
		if (subAdminList.size() > 0)
			for (int i = 0; i < subAdminList.size(); i++) {
				Ids.add(subAdminList.get(i).getUserId());
			}

		for (int j = 0; j < Ids.size(); j++) {
			if (CurrentUser.getId() == Ids.get(j))
				fl1 = false;
		}
		if (fl1 == false || CurrentUser.getId() == topics.getUserId())
			isSave = false;

		return isSave;

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
					saving.delegate = PostFragment.this;

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

					saveVisitFalg = true;
					// sendVisit();
				} else {

					params = new LinkedHashMap<String, String>();
					SavingVisit saving = new SavingVisit(getActivity());
					saving.delegate = PostFragment.this;

					params.put("TableName", "Visit");
					params.put("UserId", String.valueOf(userId));
					params.put("TypeId", String.valueOf(typeId));
					params.put("ObjectId", String.valueOf(topics.getId()));
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
					userId = util.getCurrentUser().getId();
					typeId = StaticValues.TypePostVisit;
					// int idObj = object.getId();

					params = new LinkedHashMap<String, String>();
					SavingVisit saving = new SavingVisit(getActivity());
					saving.delegate = PostFragment.this;

					params.put("TableName", "Visit");
					params.put("UserId", String.valueOf(userId));
					params.put("TypeId", String.valueOf(typeId));
					params.put("ObjectId", String.valueOf(topics.getId()));
					params.put("ModifyDate", String.valueOf(currentTime));
					params.put("Date", String.valueOf(currentTime));

					params.put("IsUpdate", "0");
					params.put("Id", "0");

					saving.execute(params);
					saveVisitFalg = false;
					isFinish = true;

				}
			}

		}

	}

	@Override
	public void resultSaveVisit(String output) {

		if (!output.contains("Exception")) {
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
						saving.delegate = PostFragment.this;

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

						saveVisitFalg = true;
						// sendVisit();
					} else {

						params = new LinkedHashMap<String, String>();
						SavingVisit saving = new SavingVisit(getActivity());
						saving.delegate = PostFragment.this;

						params.put("TableName", "Visit");
						params.put("UserId", String.valueOf(userId));
						params.put("TypeId", String.valueOf(typeId));
						params.put("ObjectId", String.valueOf(topics.getId()));
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

				}

			}
		}

	}

	private void getAllComment() {

		adapter.open();
		settings = adapter.getSettings();
		adapter.close();

		final SharedPreferences currentTime = getActivity().getSharedPreferences("time", 0);

		String time = currentTime.getString("time", "-1");

		GetAllCommentById service = new GetAllCommentById(getActivity());
		service.delegate = PostFragment.this;
		Map<String, String> items = new LinkedHashMap<String, String>();
		items.put("tableName", "getAllCommentInPostById");
		items.put("id", String.valueOf(postid));
		items.put("fromDate", topics.getDate());
		items.put("endDate", time);
		items.put("isRefresh", "0");

		service.execute(items);

	}

	@Override
	public void ResultComment(String output) {

		if (util.checkError(output) == false) {

			util.parseQuery(output);
			exadapter.notifyDataSetChanged();
		}

	}
}
