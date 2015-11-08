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
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.Action.FloatingActionButton;
import com.project.mechanic.adapter.ExpandableCommentPost;
import com.project.mechanic.crop.CropImage;
import com.project.mechanic.entity.CommentInPost;
import com.project.mechanic.entity.LikeInPost;
import com.project.mechanic.entity.Post;
import com.project.mechanic.entity.Users;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.inter.CommInterface;
import com.project.mechanic.inter.GetAsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.Deleting;
import com.project.mechanic.service.Saving;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.service.UpdatingImage;
import com.project.mechanic.utility.ServiceComm;
import com.project.mechanic.utility.Utility;

public class PostFragment extends Fragment implements AsyncInterface,
		GetAsyncInterface, CommInterface {

	/**/
	Button dfragbutton;
	Button alertdfragbutton;
	FloatingActionButton action;
	DialogPostFragment MyDialog;
	DialogpostTitleFragment MyDialogShow;
	int ObjectID;
	/**/

	private File mFileTemp;
	public static final String TEMP_PHOTO_FILE_NAME = "temp_photo.jpg";
	private static final int CAMERA_CODE = 101, GALLERY_CODE = 201,
			CROPING_CODE = 301;
	final int PIC_CROP = 10;
	DataBaseAdapter adapter;
	ExpandableCommentPost exadapter;
	private Uri mImageCaptureUri;

	TextView titletxt, descriptiontxt, dateTopic, countComment, countLike,
			nametxt;
	LinearLayout addComment, likeTopic;
	ImageButton sharebtn;
	ImageView profileImg;

	ImageView postImage;

	int postid;
	RelativeLayout count, commentcounter;

	Post topics;

	DialogcmtInpost dialog;
	ArrayList<CommentInPost> commentGroup, ReplyGroup;
	// String currentDate;

	Map<CommentInPost, List<CommentInPost>> mapCollection;
	ExpandableListView exlistview;

	View header;
	Users CurrentUser, uu;
	int IDcurrentUser;
	// PersianDate date;
	Utility util;
	int id, gp;
	Users user;
	int userId, commentId = 0;

	Saving saving;
	Deleting deleting;
	Map<String, String> params;

	ProgressDialog ringProgressDialog;

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

	@SuppressLint("InflateParams")
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceStdataate) {

		((MainActivity) getActivity()).setActivityTitle(R.string.Forums);
		View view = inflater.inflate(R.layout.fragment_post, null);

		adapter = new DataBaseAdapter(getActivity());
		util = new Utility(getActivity());

		user = new Users();

		missedIds = new ArrayList<Integer>();

		header = getActivity().getLayoutInflater().inflate(
				R.layout.header_expandable, null);

		/**/
		if (getArguments().getString("Id") != null)
			ObjectID = Integer.valueOf(getArguments().getString("Id"));
		action = (FloatingActionButton) view.findViewById(R.id.fab);
		action.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				DialogPostFragment fragment = new DialogPostFragment();

				FragmentTransaction trans = getActivity()
						.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, fragment);
				trans.addToBackStack(null);
				trans.commit();

				Bundle bundle = new Bundle();
				bundle.putInt("Id", ObjectID);
				fragment.setArguments(bundle);

				// FragmentManager fm = getFragmentManager();
				// MyDialogShow = new DialogpostTitleFragment();
				// MyDialogShow.show(fm, "Dialog Fragment");

			}
		});
		/**/

		// start find view

		postImage = (ImageView) header.findViewById(R.id.postImage);

		titletxt = (TextView) header.findViewById(R.id.title_topic);
		descriptiontxt = (TextView) header.findViewById(R.id.description_topic);
		dateTopic = (TextView) header.findViewById(R.id.date_cc);
		countComment = (TextView) header
				.findViewById(R.id.numberOfCommentTopic);
		countLike = (TextView) header.findViewById(R.id.txtNumofLike_CmtFroum);
		nametxt = (TextView) header.findViewById(R.id.name_cc);

		addComment = (LinearLayout) header.findViewById(R.id.addCommentToTopic);
		likeTopic = (LinearLayout) header.findViewById(R.id.LikeTopicLinear);

		sharebtn = (ImageButton) header.findViewById(R.id.sharefroumicon);
		profileImg = (ImageView) header.findViewById(R.id.iconfroumtitle);
		// exlistview = (ExpandableListView) view
		// .findViewById(R.id.listvCmt_Introduction_post);
		exlistview = (ExpandableListView) view
				.findViewById(R.id.listvCmt_Introduction_post);

		count = (RelativeLayout) header.findViewById(R.id.countLike);
		commentcounter = (RelativeLayout) header
				.findViewById(R.id.countComment);

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
		if (u != null) {
			userId = u.getId();

			nametxt.setText(u.getName());
			LinearLayout rl = (LinearLayout) header
					.findViewById(R.id.profileLinearcommenterinContinue);

			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					rl.getLayoutParams());

			lp.width = util.getScreenwidth() / 7;
			lp.height = util.getScreenwidth() / 7;
			profileImg.setLayoutParams(lp);

			if (u.getImagePath() == null) {
				profileImg.setImageResource(R.drawable.no_img_profile);
				profileImg.setLayoutParams(lp);

			} else {
				// byte[] bytepic = u.getImage();

				Bitmap bmp = BitmapFactory.decodeFile(u.getImagePath());
				profileImg.setImageBitmap(Utility.getRoundedCornerBitmap(bmp,
						50));

				profileImg.setLayoutParams(lp);
			}
		}

		if (!topics.getTitle().isEmpty()) {
			titletxt.setText(topics.getTitle());
			titletxt.setVisibility(View.VISIBLE);
		}
		if (!topics.getTitle().isEmpty()) {
			descriptiontxt.setText(topics.getDescription());
			descriptiontxt.setVisibility(View.VISIBLE);
		}
		if (!topics.getPhoto().isEmpty()) {
			File imgFile = new File(topics.getPhoto());

			if (imgFile.exists()) {
				Bitmap myBitmap = BitmapFactory.decodeFile(topics.getPhoto());
				postImage.setImageBitmap(myBitmap);
				postImage.setVisibility(View.VISIBLE);
			}
		}
		countComment.setText(adapter.CommentInPost_count(postid).toString());
		countLike.setText(adapter.LikeInPost_count(postid).toString());
		dateTopic.setText(util.getPersianDate(topics.getDate()));

		// titletxt.setTypeface(util.SetFontCasablanca());
		// descriptiontxt.setTypeface(util.SetFontCasablanca());

		profileImg.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				FragmentTransaction trans = ((MainActivity) getActivity())
						.getSupportFragmentManager().beginTransaction();
				InformationUser fragment = new InformationUser();
				Bundle bundle = new Bundle();
				bundle.putInt("userId", userId);
				fragment.setArguments(bundle);
				trans.replace(R.id.content_frame, fragment);
				trans.commit();

			}
		});
		addComment.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (CurrentUser == null) {
					Toast.makeText(getActivity(),
							"برای درج کامنت ابتدا باید وارد شوید",
							Toast.LENGTH_SHORT).show();

				} else {

					dialog = new DialogcmtInpost(PostFragment.this, 0,
							getActivity(), postid, R.layout.dialog_addcomment,
							2);
					dialog.show();
					exadapter.notifyDataSetChanged();
				}
			}
		});

		commentGroup = adapter.getCommentInPostbyPaperid(postid, 0);

		mapCollection = new LinkedHashMap<CommentInPost, List<CommentInPost>>();

		List<CommentInPost> reply = null;
		for (CommentInPost comment : commentGroup) {
			reply = adapter.getReplyCommentbyCommentIDPost(postid,
					comment.getId());
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

				ringProgressDialog = ProgressDialog.show(getActivity(), "",
						"لطفا منتظر بمانید...", true);
				ringProgressDialog.setCancelable(true);
				date = new ServerDate(getActivity());
				date.delegate = PostFragment.this;
				date.execute("");
				check = true;

			}
		}

		exlistview.addHeaderView(header);

		exadapter = new ExpandableCommentPost(getActivity(),
				(ArrayList<CommentInPost>) commentGroup, mapCollection, this,
				postid);

		exadapter.notifyDataSetChanged();

		exlistview.setAdapter(exadapter);

		if (CurrentUser == null) {
			likeTopic.setBackgroundResource(R.drawable.like_off);
			count.setBackgroundResource(R.drawable.count_like_off);
		} else {
			if (adapter.isUserLikedPost(CurrentUser.getId(), postid)) {
				likeTopic.setBackgroundResource(R.drawable.like_on);
				count.setBackgroundResource(R.drawable.count_like);

			} else {

				likeTopic.setBackgroundResource(R.drawable.like_off);
				count.setBackgroundResource(R.drawable.count_like_off);

			}
		}
		adapter.close();
		count.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				adapter.open();
				ArrayList<LikeInPost> likedist = adapter
						.getLikepostLikeInPostByPostId(postid);

				adapter.close();
				if (likedist.size() == 0) {
					Toast.makeText(getActivity(), "لایکی ثبت نشده است", 0)
							.show();
				} else {
					DialogPersonLikedPost dia = new DialogPersonLikedPost(
							getActivity(), postid, likedist);
					dia.show();
				}
			}
		});

		// این کد ها برای مشخص شدن مبدا ارسالی برای آپدیت کردن لیست می باشد
		// وقتی کاربر در صفحه فروم بدون کامنت ، نظری ثبت می کند به این صفحه
		// منتقل می شود و این کد ها برای مشخص کردن مبدا و انجام عمل آپدیت کردن
		// استفاده می شود
		SharedPreferences realizeIdComment = getActivity()
				.getSharedPreferences("Id", 0);
		int destinyId = realizeIdComment.getInt("main_Id", -1);
		if (destinyId == 1371) {
			updateList();
		}

		likeTopic.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				adapter.open();

				if (CurrentUser == null) {
					Toast.makeText(getActivity(),
							"برای درج لایک ابتدا باید وارد شوید",
							Toast.LENGTH_SHORT).show();
				} else {

					ringProgressDialog = ProgressDialog.show(getActivity(), "",
							"لطفا منتظر بمانید...", true);
					ringProgressDialog.setCancelable(true);
					date = new ServerDate(getActivity());
					date.delegate = PostFragment.this;
					date.execute("");
					LikeOrComment = true;

				}
				adapter.close();

			}

		});

		sharebtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String body = topics.getDescription() + "\n"
						+ " مشاهده کامل گفتگو در: "
						+ "<a href=\"mechanical://SplashActivity\">اینجا</a> ";
				Intent sharingIntent = new Intent(
						android.content.Intent.ACTION_SEND);
				sharingIntent.setType("text/html");
				sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
						topics.getTitle());
				sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
						Html.fromHtml(body));
				startActivity(Intent.createChooser(sharingIntent,
						"اشتراک از طریق"));
			}
		});

		ImageView report = (ImageView) view.findViewById(R.id.reportImage);
		report.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (CurrentUser == null) {
					Toast.makeText(getActivity(), "ابتدا باید وارد شوید", 0)
							.show();
				} else {

					DialogLongClick dia = new DialogLongClick(getActivity(), 1,
							topics.getUserId(), topics.getId(),
							PostFragment.this, topics.getDescription());
					WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
					lp.copyFrom(dia.getWindow().getAttributes());
					lp.width = (int) (util.getScreenwidth() / 1.5);
					lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
					;
					dia.show();

					dia.getWindow().setAttributes(lp);
					dia.getWindow().setBackgroundDrawable(
							new ColorDrawable(
									android.graphics.Color.TRANSPARENT));
				}
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
		// Toast.makeText(getActivity(), " نظر نمی تواند خالی باشد", 0)
		// .show();
		// } else {
		//
		// // date = new ServerDate(getActivity());
		// // date.delegate = PostFragment.this;
		// // date.execute("");
		// // LikeOrComment = false;
		// adapter.open();
		// adapter.insertCommentInPosttoDb(id,
		// util.inputComment(getActivity()), postid,
		// CurrentUser.getId(), serverDate, commentId);
		//
		// adapter.close();
		//
		// util.ToEmptyComment(getActivity());
		//
		// util.ReplyLayout(getActivity(), "", false);
		//
		// }
		// }
		// });
		// ImageView delete = util.deleteReply(getActivity());
		//
		// delete.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		//
		// util.ReplyLayout(getActivity(), "", false);
		//
		// }
		// });

		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			mFileTemp = new File(Environment.getExternalStorageDirectory(),
					TEMP_PHOTO_FILE_NAME);
		} else {
			mFileTemp = new File(getActivity().getFilesDir(),
					TEMP_PHOTO_FILE_NAME);
		}

		util.ShowFooterAgahi(getActivity(), false, 10);

		ImageView[] TempImage = util.inputCommentAndPickFile(getActivity());

		ImageView sendMessage = TempImage[0];
		ImageView getPicture = TempImage[1];
		showPicture = TempImage[2];

		sendMessage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				adapter.open();
				adapter.insertCommentInPosttoDb(id,
						util.inputComment(getActivity()), postid,
						CurrentUser.getId(), serverDate, commentId);

				adapter.close();

				util.ToEmptyComment(getActivity());
				util.ReplyLayout(getActivity(), "", false);
			}
		});
		getPicture.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				selectImageOption();
			}
		});

		return view;
	}

	public ImageView showPicture;
	String imgDecodableString;

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		try {
			if (requestCode == GALLERY_CODE && resultCode == Activity.RESULT_OK
					&& null != data) {

				mImageCaptureUri = data.getData();
				// String img = data.getDataString();
				// String filename = (new File(filePath)).getName();

				Toast.makeText(getActivity(), mImageCaptureUri.getPath(),
						Toast.LENGTH_LONG).show();

				// Bitmap myBitmap = BitmapFactory.decodeFile(img);

				// showPicture.setImageBitmap(myBitmap);
				Bitmap bitmap = BitmapFactory.decodeFile(mImageCaptureUri
						.getPath());
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
				Toast.makeText(getActivity(), "You haven't picked Image",
						Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			Toast.makeText(getActivity(), "Something went wrong",
					Toast.LENGTH_LONG).show();
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
					File f = new File(android.os.Environment
							.getExternalStorageDirectory(), "temp1.jpg");
					mImageCaptureUri = Uri.fromFile(f);
					intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
					startActivityForResult(intent, CAMERA_CODE);

					showPicture.setImageURI(mImageCaptureUri);

				} else if (items[item].equals("از گالری تصاویر")) {

					Intent galleryIntent = new Intent(
							Intent.ACTION_PICK,
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

		commentGroup = adapter.getCommentInPostbyPaperid(postid, 0);

		mapCollection = new LinkedHashMap<CommentInPost, List<CommentInPost>>();

		List<CommentInPost> reply = null;
		for (CommentInPost comment : commentGroup) {
			reply = adapter.getReplyCommentbyCommentIDPost(postid,
					comment.getId());
			mapCollection.put(comment, reply);
		}

		countComment.setText(adapter.CommentInPost_count(postid).toString());

		exadapter = new ExpandableCommentPost(getActivity(),
				(ArrayList<CommentInPost>) commentGroup, mapCollection, this,
				postid);

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

	public void expanding(int groupPosition) {
		adapter.open();

		commentGroup = adapter.getCommentInPostbyPaperid(postid, 0);

		mapCollection = new LinkedHashMap<CommentInPost, List<CommentInPost>>();

		List<CommentInPost> reply = null;
		for (CommentInPost comment : commentGroup) {
			reply = adapter.getReplyCommentbyCommentIDPost(postid,
					comment.getId());
			mapCollection.put(comment, reply);
		}

		countComment.setText(adapter.CommentInPost_count(postid).toString());

		exadapter = new ExpandableCommentPost(getActivity(),
				(ArrayList<CommentInPost>) commentGroup, mapCollection, this,
				postid);
		exadapter.notifyDataSetChanged();
		exlistview.setAdapter(exadapter);
		if (groupPosition <= mapCollection.size()) {
			exlistview.expandGroup(groupPosition);
			exlistview.setSelectedGroup(groupPosition);

		}
		adapter.close();

		// exlistview.setSelectedChild(groupPosition, 1, condition);

	}

	@Override
	public void processFinish(String output) {

		adapter.open();
		int id = -1;
		try {
			id = Integer.valueOf(output);

			if (LikeOrComment == true) {
				if (adapter.isUserLikedPost(CurrentUser.getId(), postid)) {
					adapter.deleteLikeFromPost(CurrentUser.getId(), postid);
					likeTopic.setBackgroundResource(R.drawable.like_off);
					count.setBackgroundResource(R.drawable.count_like_off);

					countLike.setText(adapter.LikeInPost_count(postid)
							.toString());
				} else {
					adapter.insertLikeInPostToDb(id, CurrentUser.getId(),
							postid, serverDate, 0);
					likeTopic.setBackgroundResource(R.drawable.like_on);
					count.setBackgroundResource(R.drawable.count_like);

					countLike.setText(adapter.LikeInPost_count(postid)
							.toString());
				}
			} else {
				adapter.open();

				// adapter.insertCommentInPosttoDb(id,
				// util.inputComment(getActivity()), postid,
				// CurrentUser.getId(), serverDate, commentId);
				//
				// adapter.close();
				// util.ToEmptyComment(getActivity());
				if (commentId == 0)
					expanding(exadapter.getGroupCount());
				else {
					expanding(gp);

				}

			}
			if (ringProgressDialog != null) {
				ringProgressDialog.dismiss();
			}
		} catch (NumberFormatException ex) {
			if (output != null
					&& !(output.contains("Exception") || output
							.contains("java"))) {

				serverDate = output;

				if (LikeOrComment == false) {

					if (getActivity() != null) {
						params = new LinkedHashMap<String, String>();

						saving = new Saving(getActivity());
						saving.delegate = PostFragment.this;

						params.put("TableName", "CommentInPost");

						params.put("Desk", util.inputComment(getActivity()));
						params.put("PostId", String.valueOf(postid));
						params.put("UserId",
								String.valueOf(CurrentUser.getId()));
						params.put("CommentId", String.valueOf(commentId));

						params.put("Date", output);
						params.put("ModifyDate", output);
						params.put("IsUpdate", "0");
						params.put("Id", "0");

						saving.execute(params);
					}
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
							params.put("UserId",
									String.valueOf(CurrentUser.getId()));
							params.put("PostId", String.valueOf(postid));

							deleting.execute(params);
						}
					} else {
						params = new LinkedHashMap<String, String>();
						if (getActivity() != null) {

							saving = new Saving(getActivity());
							saving.delegate = PostFragment.this;

							params.put("TableName", "LikeInPost");

							params.put("UserId",
									String.valueOf(CurrentUser.getId()));
							params.put("PostId", String.valueOf(postid));
							params.put("CommentId", "0");
							params.put("Date", output);
							params.put("IsUpdate", "0");
							params.put("Id", "0");

							saving.execute(params);
						}
					}
				}
			} else {
				Toast.makeText(getActivity(),
						"خطا در ثبت. پاسخ نا مشخص از سرور", Toast.LENGTH_SHORT)
						.show();
				if (ringProgressDialog != null) {
					ringProgressDialog.dismiss();
				}
			}
		}

		catch (Exception e) {

			Toast.makeText(getActivity(), "خطا در ثبت", Toast.LENGTH_SHORT)
					.show();
			adapter.close();
			if (ringProgressDialog != null) {
				ringProgressDialog.dismiss();
			}
		}
		adapter.close();
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

		if (!"".equals(output)
				&& output != null
				&& !(output.contains("Exception") || output.contains("java") || output
						.contains("soap"))) {
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
		if (ringProgressDialog != null) {
			ringProgressDialog.dismiss();
		}
	}

}
