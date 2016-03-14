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
import com.project.mechanic.adapter.ExpandIntroduction;
import com.project.mechanic.adapter.ExpandableCommentPost;
import com.project.mechanic.crop.CropImage;
import com.project.mechanic.entity.CommentInObject;
import com.project.mechanic.entity.CommentInPost;
import com.project.mechanic.entity.LikeInPost;
import com.project.mechanic.entity.Object;
import com.project.mechanic.entity.Post;
import com.project.mechanic.entity.SubAdmin;
import com.project.mechanic.entity.Users;
import com.project.mechanic.entity.Visit;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.inter.CommInterface;
import com.project.mechanic.inter.GetAsyncInterface;
import com.project.mechanic.inter.VisitSaveInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.Deleting;
import com.project.mechanic.service.Saving;
import com.project.mechanic.service.SavingVisit;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.service.ServiceComm;
import com.project.mechanic.service.UpdatingImage;
import com.project.mechanic.utility.Utility;

public class FixedPostFragment extends Fragment implements AsyncInterface {

	/**/
	// FloatingActionButton action;
	DialogPostFragment MyDialog;
	// DialogpostTitleFragment MyDialogShow;
	int /* postId , */ objectId;
	/**/
	int IDcurrentUser, itemId;
	final int PIC_CROP = 10;
	DataBaseAdapter adapter;
	ExpandIntroduction exadapter;

	TextView titletxt, descriptiontxt, date, countComment, countLike, nametxt, countVisit;
	LinearLayout /* addComment, */ likeTopic;
	ImageButton sharebtn;

	RelativeLayout count, commentcounter, layoutImg;

	// Post topics;
	List<String> menuItems;

	DialogcmtInpost dialog;
	ArrayList<CommentInObject> commentGroup, ReplyGroup;
	// String currentDate;

	Map<CommentInObject, List<CommentInObject>> mapCollection;
	ExpandableListView exlistview;

	// PersianDate date;
	Utility util;
	int id, gp, cp;
	int userId, commentId = 0;

	Saving saving;
	Deleting deleting;
	Map<String, String> params;

	// ProgressDialog ringProgressDialog;

	String serverDate = "";
	// ServerDate date;

	ServiceComm service;
	UpdatingImage updating;
	Map<String, String> maps;
	boolean LikeOrComment; // like == true & comment == false
	// boolean isFinish = false, saveVisitFalg;
	String currentTime = "";
	int ItemId, typeId;
	// boolean IsPost = true;

	int positionPost = 0, positionBrand = 0;
	RelativeLayout rl;

	////////////////////////////
	View view, header;
	Object obj;
	ImageView profileImg, icLike, report, postImage;
	Users currentUser;
	LinearLayout layoutView;

	@SuppressLint("InflateParams")
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceStdataate) {

		view = inflater.inflate(R.layout.fragment_post, null);
		if (getArguments() != null)
			objectId = Integer.valueOf(getArguments().getString("Id"));

		findView();

		setValue();

		setLayoutParams();

		showItems();

		onClick();

		setFont();

		fillComment();

		util.ShowFooterAgahi(getActivity(), false, 10);

		ImageView[] TempImage = util.inputCommentAndPickFile(getActivity());

		ImageView sendMessage = TempImage[0];
		ImageView getPicture = TempImage[1];
		getPicture.setVisibility(View.GONE);

		sendMessage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (currentUser == null) {
					Toast.makeText(getActivity(), "ابتدا باید وارد شوید", Toast.LENGTH_SHORT).show();
				} else {

					if ("".equals(util.inputComment(getActivity()))) {
						Toast.makeText(getActivity(), " نظر نمی تواند خالی باشد", 0).show();
					} else {

						ServerDate date = new ServerDate(getActivity());
						date.delegate = FixedPostFragment.this;
						date.execute("");
						LikeOrComment = false;

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
						IntroductionFragment fragment = new IntroductionFragment();
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

	public int getPostId() {
		return id;
	}

	public int groupPosition(int groupPosition) {
		gp = groupPosition;
		return gp;
	}

	public int childPosition(int childPosition) {
		cp = childPosition;
		return cp;
	}

	public void setcount() {

		adapter.open();
		countComment.setText(adapter.CommentInObject_count(objectId).toString());
		adapter.close();

	}

	public void CommentId(int Id) {
		commentId = Id;
	}

	@Override
	public void processFinish(String output) {

		if (!output.contains("Exception")) {

			adapter.open();
			int resultId = -1;
			try {
				resultId = Integer.valueOf(output);

				if (LikeOrComment == true) {
					if (adapter.isUserLikeIntroductionPage(currentUser.getId(), objectId,
							StaticValues.TypeLikeFixedPost)) {

						adapter.open();

						adapter.deleteLikeIntroduction(currentUser.getId(), objectId, StaticValues.TypeLikeFixedPost);

						adapter.close();

						icLike.setBackgroundResource(R.drawable.like_froum_off);

					} else {

						adapter.open();

						adapter.insertLikeInObjectToDb(resultId, currentUser.getId(), objectId, serverDate,
								StaticValues.TypeLikeFixedPost);

						adapter.close();

						icLike.setBackgroundResource(R.drawable.like);

					}
					adapter.open();
					int likeCountNumber = adapter.LikeInObject_count(objectId, StaticValues.TypeLikeFixedPost);
					adapter.close();

					countLike.setText(likeCountNumber + "");

				} else {
					adapter.open();

					adapter.insertCommentObjecttoDb(resultId, util.inputComment(getActivity()), objectId,
							currentUser.getId(), serverDate, commentId);

					int commentCountNumber = adapter.getCountofLikeCommentIntroduction(objectId,
							StaticValues.TypeLikeFixedPost);

					adapter.close();
					util.ToEmptyComment(getActivity());
					util.ReplyLayout(getActivity(), "", false);

					countComment.setText(commentCountNumber + "");

					if (commentId == 0)
						expanding(exadapter.getGroupCount(), 0, false);
					else {
						expanding(gp, cp, true);

					}

				}

			} catch (NumberFormatException ex) {
				if (output != null && !(output.contains("Exception") || output.contains("java"))) {

					serverDate = output;

					if (LikeOrComment == false) {

						if (getActivity() != null) {
							params = new LinkedHashMap<String, String>();

							saving = new Saving(getActivity());
							saving.delegate = FixedPostFragment.this;

							params.put("TableName", "CommentInObject");

							params.put("Desk", util.inputComment(getActivity()));
							params.put("ObjectId", String.valueOf(objectId));
							params.put("UserId", String.valueOf(currentUser.getId()));
							params.put("CommentId", String.valueOf(commentId));

							params.put("Date", output);
							params.put("ModifyDate", output);
							params.put("IsUpdate", "0");
							params.put("Id", "0");

							saving.execute(params);
						}

					} else {

						if (adapter.isUserLikeIntroductionPage(currentUser.getId(), objectId,
								StaticValues.TypeLikeFixedPost)) {

							params = new LinkedHashMap<String, String>();
							if (getActivity() != null) {

								deleting = new Deleting(getActivity());
								deleting.delegate = FixedPostFragment.this;

								params.put("TableName", "LikeInObject");
								params.put("UserId", String.valueOf(currentUser.getId()));
								params.put("ObjectId", String.valueOf(objectId));

								deleting.execute(params);

							}
						} else {
							params = new LinkedHashMap<String, String>();
							if (getActivity() != null) {

								saving = new Saving(getActivity());
								saving.delegate = FixedPostFragment.this;

								params.put("TableName", "LikeInObject");

								params.put("UserId", String.valueOf(currentUser.getId()));
								params.put("ObjectId", String.valueOf(objectId));
								params.put("CommentId", String.valueOf(StaticValues.TypeLikeFixedPost));
								params.put("Date", output);
								params.put("ModifyDate", output);

								params.put("IsUpdate", "0");
								params.put("Id", "0");

								saving.execute(params);

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

	public void fillComment() {

		adapter.open();
		commentGroup = adapter.getAllCommentInObjectById(objectId, 0);
		mapCollection = new LinkedHashMap<CommentInObject, List<CommentInObject>>();

		List<CommentInObject> reply = null;
		for (CommentInObject comment : commentGroup) {
			reply = adapter.getReplyCommentIntroduction(objectId, comment.getId());
			mapCollection.put(comment, reply);
		}

		exadapter = new ExpandIntroduction(getActivity(), (ArrayList<CommentInObject>) commentGroup, mapCollection,
				this, objectId);
		adapter.close();
		exadapter.notifyDataSetChanged();

		exlistview.setAdapter(exadapter);

	}

	public void findView() {

		adapter = new DataBaseAdapter(getActivity());
		util = new Utility(getActivity());

		currentUser = util.getCurrentUser();

		header = getActivity().getLayoutInflater().inflate(R.layout.header_expandable, null);

		profileImg = (ImageView) header.findViewById(R.id.iconfroumtitle);
		icLike = (ImageView) header.findViewById(R.id.likeIcon);
		postImage = (ImageView) header.findViewById(R.id.postImage);
		report = (ImageView) header.findViewById(R.id.reportImage);

		descriptiontxt = (TextView) header.findViewById(R.id.description_topic);
		date = (TextView) header.findViewById(R.id.date_cc);
		countComment = (TextView) header.findViewById(R.id.numberOfCommentTopic);
		countLike = (TextView) header.findViewById(R.id.txtNumofLike_CmtFroum);
		nametxt = (TextView) header.findViewById(R.id.name_cc);

		likeTopic = (LinearLayout) header.findViewById(R.id.LikeTopicLinear);
		sharebtn = (ImageButton) header.findViewById(R.id.sharefroumicon);

		exlistview = (ExpandableListView) view.findViewById(R.id.listvCmt_Introduction_post);
		exlistview.addHeaderView(header);

		rl = (RelativeLayout) header.findViewById(R.id.profileLinearcommenterinContinue);
		layoutImg = (RelativeLayout) header.findViewById(R.id.imageLayout);

		countVisit = (TextView) header.findViewById(R.id.countVisit);
		layoutView = (LinearLayout) header.findViewById(R.id.bvl);

	}

	public void setValue() {

		adapter.open();

		obj = adapter.getObjectbyid(objectId);
		int likeCountNumber = adapter.LikeInObject_count(objectId, StaticValues.TypeLikeFixedPost);
		adapter.close();

		setcount();

		nametxt.setText(obj.getName());
		descriptiontxt.setText(obj.getDescription());
		countLike.setText(likeCountNumber + "");

	}

	public void showItems() {

		date.setVisibility(View.GONE);
		layoutView.setVisibility(View.GONE);

		if (currentUser == null) {
			icLike.setBackgroundResource(R.drawable.like_froum_off);
		} else {
			adapter.open();
			if (adapter.isUserLikeIntroductionPage(currentUser.getId(), objectId, StaticValues.TypeLikeFixedPost)) {
				icLike.setBackgroundResource(R.drawable.like);

			} else {

				icLike.setBackgroundResource(R.drawable.like_froum_off);

			}
			adapter.close();
		}

	}

	public void setLayoutParams() {

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
			try {
				Bitmap bmp = BitmapFactory.decodeFile(obj.getImagePath2());
				profileImg.setImageBitmap(Utility.getclip(bmp));

				profileImg.setLayoutParams(lp);
			} catch (OutOfMemoryError e) {
				e.printStackTrace();
			}

		}

		///////////////////////////////////////

		RelativeLayout.LayoutParams ll = new RelativeLayout.LayoutParams(layoutImg.getLayoutParams());
		ll.width = util.getScreenwidth();
		ll.height = util.getScreenwidth();

		if (obj.getImagePath3() != null) {

			try {
				Bitmap myBitmap = BitmapFactory.decodeFile(obj.getImagePath3());
				postImage.setImageBitmap(myBitmap);
				postImage.setVisibility(View.VISIBLE);
				postImage.setLayoutParams(ll);
			} catch (OutOfMemoryError e) {
				e.printStackTrace();
			}

		} else
			postImage.setVisibility(View.GONE);
	}

	public void onClick() {

		profileImg.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				FragmentTransaction trans = ((MainActivity) getActivity()).getSupportFragmentManager()
						.beginTransaction();
				InformationUser fragment = new InformationUser();
				Bundle bundle = new Bundle();
				bundle.putInt("userId", obj.getUserId());
				fragment.setArguments(bundle);
				trans.replace(R.id.content_frame, fragment);
				trans.commit();

			}
		});

		likeTopic.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				adapter.open();

				if (currentUser == null) {
					Toast.makeText(getActivity(), "برای درج لایک ابتدا باید وارد شوید", Toast.LENGTH_SHORT).show();
				} else {

					ServerDate date = new ServerDate(getActivity());
					date.delegate = FixedPostFragment.this;
					date.execute("");

					LikeOrComment = true;

					// icLike.setBackgroundResource(R.drawable.like);
					// int count = adapter.LikeInPost_count(postid);
					// countLike.setText(String.valueOf(count + 1));

				}
				adapter.close();

			}

		});

		sharebtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String body = obj.getDescription() + "\n" + " مشاهده کامل گفتگو در: "
						+ "<a href=\"mechanical://SplashActivity\">اینجا</a> ";
				Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
				sharingIntent.setType("text/html");
				// sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
				// topics.getTitle());
				sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml(body));
				startActivity(Intent.createChooser(sharingIntent, "اشتراک از طریق"));
			}
		});

		report.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				final int userIdsender = obj.getUserId();
				final String t = obj.getDescription();
				itemId = obj.getId();

				if (util.getCurrentUser() != null) {
					if (util.getCurrentUser().getId() == userIdsender) {

						menuItems = new ArrayList<String>();
						menuItems.clear();
						menuItems.add("ارسال پیام");
						menuItems.add("کپی");

					} else {
						menuItems = new ArrayList<String>();

						menuItems.clear();
						menuItems.add("ارسال پیام");
						menuItems.add("کپی");
						menuItems.add("گزارش تخلف");
					}
				} else {
					menuItems = new ArrayList<String>();

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

						if (item.getTitle().equals("کپی")) {

							util.CopyToClipboard(t);

						}

						if (item.getTitle().equals("گزارش تخلف")) {

							util.reportAbuse(userIdsender, StaticValues.TypeReportPage, itemId, t, objectId, 0);

						}

						return false;
					}

				};
				popupMenu.setOnMenuItemClickListener(menuitem);

			}

		});

		ImageView[] TempImage = util.inputCommentAndPickFile(getActivity());

		ImageView sendMessage = TempImage[0];
		ImageView getPicture = TempImage[1];
		getPicture.setVisibility(View.GONE);
		ImageView showPicture = TempImage[2];

		sendMessage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (currentUser == null) {
					Toast.makeText(getActivity(), "ابتدا باید وارد شوید", Toast.LENGTH_SHORT).show();
				} else {

					if ("".equals(util.inputComment(getActivity()))) {
						Toast.makeText(getActivity(), " نظر نمی تواند خالی باشد", 0).show();
					} else {

						ServerDate date = new ServerDate(getActivity());
						date.delegate = FixedPostFragment.this;
						date.execute("");

					}
				}

			}
		});

		ImageView delete = util.deleteReply(getActivity());

		delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				util.ReplyLayout(getActivity(), "", false);

			}
		});

	}

	public void setFont() {
		descriptiontxt.setTypeface(util.SetFontIranSans());

	}

	public void expanding(int groupPosition, int childPosition, boolean isChild) {

		fillComment();

		if (isChild == false)
			exlistview.setSelectedGroup(groupPosition);
		else {
			exlistview.expandGroup(groupPosition);
			exlistview.setSelectedGroup(groupPosition);
			exlistview.setSelectedChild(groupPosition, childPosition, true);

		}

	}

}
