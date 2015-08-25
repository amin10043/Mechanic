package com.project.mechanic.fragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.Action.FloatingActionButton;
import com.project.mechanic.adapter.AnadListAdapter;
import com.project.mechanic.crop.CropImage;
import com.project.mechanic.entity.Anad;
import com.project.mechanic.entity.Settings;
import com.project.mechanic.entity.Ticket;
import com.project.mechanic.entity.Users;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.inter.GetAsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.service.Updating;
import com.project.mechanic.service.UpdatingImage;
import com.project.mechanic.utility.Utility;

@SuppressLint("HandlerLeak")
public class AnadFragment extends Fragment implements AsyncInterface,
		GetAsyncInterface {

	DataBaseAdapter dbAdapter;
	View view;
	List<Ticket> mylist;
	List<Anad> anadlist;
	private DialogAnad dialog;
	private DialogAnadimg dialog1;
	int ticketTypeid = 0;
	private static int RESULT_LOAD_IMAGE = 1;
	public static String picturePath;
	int proID = 0;
	int ticket;
	private LinearLayout verticalOuterLayout;
	List<Anad> list;
	// List<Ticket> subList;
	List<Ticket> tempList;
	Anad tempItem;
	int position;
	int a;
	int I;
	int gridePadding = 1;
	private int verticalScrollMax;
	// PullAndLoadListView lstTicket;
	private Timer scrollTimer = null;
	private TimerTask clickSchedule;
	private TimerTask scrollerSchedule;
	private TimerTask faceAnimationSchedule;
	private int scrollPos = 0;
	private Boolean isFaceDown = true;
	private Timer clickTimer = null;
	private Timer faceTimer = null;
	private ImageButton clickedButton = null;
	Users u;
	Utility util;
	// int i = 0, j = 9;
	final int PIC_CROP = 2;

	ImageView imageView;
	AnadListAdapter ListAdapter;
	private ScrollView verticalScrollview;

	public static final String TEMP_PHOTO_FILE_NAME = "temp_photo.jpg";
	private File mFileTemp;
	ListView listviewanad;;
	SwipeRefreshLayout swipeLayout;
	Updating updating;
	UpdatingImage update;
	Settings setting;
	View LoadMoreFooter;

	boolean FindPosition;
	int beforePosition;
	int ImageCode;
	ProgressDialog ringProgressDialog;
	Map<String, String> maps;

	List<Integer> listId;

	ImageView imageButton;
	LinearLayout.LayoutParams params;

	String serverDate = "";
	ServerDate date;
	int DateCurrentDay;
	int code = 100;
	int icode = 0;

	boolean flag, IsFinish = false;

	RelativeLayout timeLayoutTimeLine;

	// int ic =1 ;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_anad, null);

		ticketTypeid = Integer.valueOf(getArguments().getString("Id"));
		final FloatingActionButton createItem = (FloatingActionButton) view
				.findViewById(R.id.fabAnad);

		dbAdapter = new DataBaseAdapter(getActivity());
		util = new Utility(getActivity());
		u = util.getCurrentUser();

		if (getArguments().getString("ProID") != null) {
			proID = Integer.valueOf(getArguments().getString("ProID"));
			// Toast.makeText(getActivity(), "pro Id = " + proID, 0).show();
		}

		verticalScrollview = (ScrollView) view
				.findViewById(R.id.vertical_scrollview_id);
		verticalOuterLayout = (LinearLayout) view
				.findViewById(R.id.vertical_outer_layout_id);
		dbAdapter.open();

		mylist = dbAdapter.getTicketByTypeIdProId(ticketTypeid, proID);
		anadlist = dbAdapter.getAnadtByTypeIdProId(proID);
		setting = dbAdapter.getSettings();

		dbAdapter.close();
		params = new LinearLayout.LayoutParams(util.getScreenwidth() / 3,
				util.getScreenwidth() / 3);

		// start get inad image from server

		date = new ServerDate(getActivity());
		date.delegate = AnadFragment.this;
		date.execute("");

		timeLayoutTimeLine = (RelativeLayout) view.findViewById(R.id.searchV);

		// end get inad image from server

		// if (mylist != null && !mylist.isEmpty()) {
		// if (mylist.size() < j) {
		// j = mylist.size();
		// }
		// if (i <= j) {
		// List<Ticket> tmpList = mylist.subList(i, j);
		// subList = new ArrayList<Ticket>();
		// for (Ticket p : tmpList) {
		// if (!subList.contains(p))
		// subList.add(p);
		// }
		// }
		// }
		listviewanad = (ListView) view.findViewById(R.id.listVanad);

		createItem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (u == null) {
					Toast.makeText(getActivity(), " شما وارد نشده اید.",
							Toast.LENGTH_LONG).show();
					return;
				}
				dialog = new DialogAnad(getActivity(), R.layout.dialog_addanad,
						AnadFragment.this, ticketTypeid, proID);
				// dialog.setTitle(R.string.txtanad);
				dialog.getWindow().setSoftInputMode(
						WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

				dialog.show();
				imageView = (ImageView) dialog.findViewById(R.id.dialog_img1);
			}
		});

		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			mFileTemp = new File(Environment.getExternalStorageDirectory(),
					TEMP_PHOTO_FILE_NAME);
		} else {
			mFileTemp = new File(getActivity().getFilesDir(),
					TEMP_PHOTO_FILE_NAME);
		}

		// if (mylist != null && !mylist.isEmpty()) {
		// lstTicket = (PullAndLoadListView) view.findViewById(R.id.listVanad);

		ListAdapter = new AnadListAdapter(getActivity(), R.layout.row_anad,
				mylist, proID, AnadFragment.this);
		// if (mylist != null && !mylist.isEmpty())
		listviewanad.setAdapter(ListAdapter);

		LoadMoreFooter = getActivity().getLayoutInflater().inflate(
				R.layout.load_more_footer, null);
		listviewanad.addFooterView(LoadMoreFooter);
		LoadMoreFooter.setVisibility(View.INVISIBLE);

		swipeLayout = (SwipeRefreshLayout) view
				.findViewById(R.id.swipe_container);

		swipeLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				updating = new Updating(getActivity());

				updating.delegate = AnadFragment.this;
				String[] params = new String[4];
				params[0] = "Ticket";
				params[1] = setting.getServerDate_Start_Ticket() != null ? setting
						.getServerDate_Start_Ticket() : "";
				params[2] = setting.getServerDate_End_Ticket() != null ? setting
						.getServerDate_End_Ticket() : "";

				params[3] = "1";
				updating.execute(params);

			}
		});

		swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);

		if (listviewanad != null) {

			listviewanad.setOnScrollListener(new OnScrollListener() {

				@Override
				public void onScrollStateChanged(AbsListView arg0, int arg1) {

					switch (arg1) {
					case SCROLL_STATE_FLING:
						createItem.hide(true);
						break;
					case SCROLL_STATE_TOUCH_SCROLL: {
						createItem.show(true);
						timeLayoutTimeLine.setVisibility(View.GONE);
						break;
					}
					}
				}

				@Override
				public void onScroll(AbsListView arg0, int firstVisibleItem,
						int visibleItemCount, int totalItemCount) {

					int lastInScreen = firstVisibleItem + visibleItemCount;

					if (lastInScreen == totalItemCount) {
						// lst.addFooterView(LoadMoreFooter);

						LoadMoreFooter.setVisibility(View.VISIBLE);

						updating = new Updating(getActivity());

						updating.delegate = AnadFragment.this;
						String[] params = new String[4];
						params[0] = "Ticket";
						params[1] = setting.getServerDate_Start_Ticket() != null ? setting
								.getServerDate_Start_Paper() : "";
						params[2] = setting.getServerDate_End_Ticket() != null ? setting
								.getServerDate_End_Ticket() : "";

						params[3] = "0";
						updating.execute(params);

						int countList = ListAdapter.getCount();
						beforePosition = countList;

						FindPosition = false;
					}
				}
			});
		}
		ViewTreeObserver vto = verticalOuterLayout.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void onGlobalLayout() {
				verticalOuterLayout.getViewTreeObserver()
						.removeGlobalOnLayoutListener(this);
				// getScrollMaxAmount();
				// startAutoScrolling();
			}
		});
		return view;
	}

	@SuppressLint("ResourceAsColor")
	private void displayImageAnad(List<Anad> anadlist, int index) {
		Anad anadItem;
		String path = "";
		Bitmap bmp;
		int objId, countEmptyPath = 0;
		while (index < anadlist.size()) {
			imageButton = new ImageView(getActivity());

			anadItem = anadlist.get(index);
			path = anadItem.getImagePath();
			objId = anadItem.getObjectId();

			if (path != null) {
				bmp = BitmapFactory.decodeFile(path);
				imageButton.setImageBitmap(bmp);
				imageButton.setLayoutParams(params);
				imageButton.setScaleType(ScaleType.FIT_XY);

			} else {
				imageButton.setBackgroundResource(R.drawable.propagand);
				imageButton.setLayoutParams(params);
				imageButton.setScaleType(ScaleType.FIT_XY);
				if (String.valueOf(objId) == null)
					countEmptyPath++;

			}
			verticalOuterLayout.addView(imageButton);
			index++;

		}
		if (countEmptyPath != 0) {

		}

	}

	private void click() {

		imageButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				int position = (Integer) arg0.getTag();
				if (isFaceDown) {
					if (clickTimer != null) {
						clickTimer.cancel();
						clickTimer = null;
					}
					clickedButton = (ImageButton) arg0;

					stopAutoScrolling();
					clickedButton.startAnimation(scaleFaceUpAnimation());
					clickedButton.setSelected(true);
					clickTimer = new Timer();

					if (clickSchedule != null) {
						clickSchedule.cancel();
						clickSchedule = null;
					}

					clickSchedule = new TimerTask() {
						public void run() {
							startAutoScrolling();
						}
					};
					switch (proID) {
					case 1:
						a = position + 1;
						break;
					case 3:
						a = position + 50 + 1;
						break;
					case 4:
						a = position + 80 + 1;
						break;
					case 5:
						a = position + 150 + 1;
						break;
					case 6:
						a = position + 200 + 1;
						break;
					case 7:
						a = position + 230 + 1;
						break;
					case 8:
						a = position + 260 + 1;
						break;
					case 9:
						a = position + 360 + 1;
						break;
					case 10:
						a = position + 390 + 1;
						break;
					case 11:
						a = position + 420 + 1;
						break;
					case 12:
						a = position + 470 + 1;
						break;
					case 13:
						a = position + 500 + 1;
						break;
					case 14:
						a = position + 530 + 1;
						break;
					case 15:
						a = position + 560 + 1;
						break;
					case 16:
						a = position + 590 + 1;
						break;
					case 17:
						a = position + 620 + 1;
						break;
					case 18:
						a = position + 670 + 1;
						break;
					case 19:
						a = position + 700 + 1;
						break;
					case 20:
						a = position + 750 + 1;
						break;
					case 21:
						a = position + 780 + 1;
						break;
					case 22:
						a = position + 830 + 1;
						break;
					case 23:
						a = position + 860 + 1;
						break;
					case 24:
						a = position + 890 + 1;
						break;
					case 25:
						a = position + 920 + 1;
						break;
					case 26:
						a = position + 970 + 1;
						break;
					case 27:
						a = position + 1020 + 1;
						break;
					case 28:
						a = position + 1070 + 1;
						break;
					case 29:
						a = position + 1120 + 1;
						break;
					case 30:
						a = position + 1170 + 1;
						break;
					case 31:
						a = position + 1200 + 1;
						break;
					case 0:
						a = position + 1250 + 1;
						break;
					case 2:
						a = position + 1350 + 1;
						break;
					default:
					}

					Toast.makeText(getActivity(), "position = " + position, 0)
							.show();
					dbAdapter.open();
					Anad t = dbAdapter.getAnadByid(a);
					dbAdapter.close();

					int objectId = t.getObjectId();
					clickTimer.schedule(clickSchedule, 1500);
					if (u == null) {
						Toast.makeText(getActivity(), " شما وارد نشده اید.",
								Toast.LENGTH_LONG).show();
						return;
					} else {
						if (objectId == 0) {
							dialog1 = new DialogAnadimg(getActivity(),
									R.layout.dialog_imganad, AnadFragment.this,
									ticketTypeid, proID, a);
							dialog1.show();
							a = 0;
						} else {
							Toast.makeText(getActivity(),
									" عکس قبلا انتخاب شده", Toast.LENGTH_LONG)
									.show();
							FragmentTransaction trans = ((MainActivity) getActivity())
									.getSupportFragmentManager()
									.beginTransaction();
							IntroductionFragment fragment = new IntroductionFragment();
							Bundle bundlei = new Bundle();
							// bundle.putString("Id", String.valueOf(id));
							bundlei.putString("I",
									String.valueOf(t.getObjectId()));
							fragment.setArguments(bundlei);
							trans.replace(R.id.content_frame, fragment);
							trans.addToBackStack(null);
							trans.commit();
						}

					}
				}
			}

		});

	}

	public void addImagesToView(final List<Anad> lst, int ImageId, int I) {

		if (getActivity() == null)
			return;
		imageButton = new ImageView(getActivity());
		imageButton.setBackgroundColor(getResources().getColor(
				R.color.transparent));
		Anad anadItem = lst.get(I);
		String path = "";
		if (anadItem.getId() == ImageId) {

			path = anadItem.getImagePath();
			Bitmap bmp = BitmapFactory.decodeFile(path);
			imageButton.setImageBitmap(bmp);

		} else
			imageButton.setBackgroundResource(R.drawable.propagand);

		imageButton.setLayoutParams(params);
		imageButton.setScaleType(ScaleType.CENTER_CROP);
		verticalOuterLayout.addView(imageButton);

		// imageButton.setTag(I);
		ImageCode++;
		icode++;
		getAnadImageFromServer(lst, icode, false);

		if (icode == lst.size())
			IsFinish = true;

	}

	public void updateView() {
		dbAdapter.open();
		mylist = dbAdapter.getTicketByTypeIdProId(ticketTypeid, proID);

		// ListView lstAnad = (ListView) view.findViewById(R.id.listVanad);
		AnadListAdapter ListAdapter = new AnadListAdapter(getActivity(),
				R.layout.row_anad, mylist, proID, AnadFragment.this);
		ListAdapter.notifyDataSetChanged();
		listviewanad.setAdapter(ListAdapter);

		dbAdapter.close();

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode != Activity.RESULT_OK) {
			return;
		}
		if (requestCode == RESULT_LOAD_IMAGE && null != data) {
			try {

				InputStream inputStream = getActivity().getContentResolver()
						.openInputStream(data.getData());
				FileOutputStream fileOutputStream = new FileOutputStream(
						mFileTemp);
				Utility.copyStream(inputStream, fileOutputStream);
				fileOutputStream.close();
				inputStream.close();

				startCropImage();

			} catch (Exception e) {

				Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG)
						.show();
			}

		}
		if (requestCode == PIC_CROP) {
			String path = data.getStringExtra(CropImage.IMAGE_PATH);
			if (path == null) {

				return;
			}

			Bitmap bitmap = BitmapFactory.decodeFile(mFileTemp.getPath());
			imageView.setImageBitmap(bitmap);
		}
		super.onActivityResult(requestCode, resultCode, data);

	}

	private void startCropImage() {

		Intent intent = new Intent(getActivity(), CropImage.class);
		intent.putExtra(CropImage.IMAGE_PATH, mFileTemp.getPath());
		intent.putExtra(CropImage.SCALE, true);

		intent.putExtra(CropImage.ASPECT_X, 3);
		intent.putExtra(CropImage.ASPECT_Y, 3);

		startActivityForResult(intent, PIC_CROP);
	}

	public void getScrollMaxAmount() {
		int actualWidth = (verticalOuterLayout.getMeasuredHeight() - (256 * 3));
		verticalScrollMax = actualWidth;
	}

	public void startAutoScrolling() {
		if (scrollTimer == null) {
			scrollTimer = new Timer();
			final Runnable Timer_Tick = new Runnable() {
				public void run() {
					moveScrollView();
				}
			};

			if (scrollerSchedule != null) {
				scrollerSchedule.cancel();
				scrollerSchedule = null;
			}
			scrollerSchedule = new TimerTask() {
				@Override
				public void run() {
					getActivity().runOnUiThread(Timer_Tick);
				}
			};

			scrollTimer.schedule(scrollerSchedule, 30, 20);
		}
	}

	public void moveScrollView() {

		scrollPos = (int) (verticalScrollview.getScrollY() + 1.0);
		if (scrollPos >= verticalScrollMax) {
			scrollPos = 0;
		}
		verticalScrollview.scrollTo(0, scrollPos);
	}

	public void stopAutoScrolling() {
		if (scrollTimer != null) {
			scrollTimer.cancel();
			scrollTimer = null;
		}
	}

	public Animation scaleFaceUpAnimation() {
		Animation scaleFace = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		scaleFace.setDuration(500);
		scaleFace.setFillAfter(true);
		scaleFace.setInterpolator(new AccelerateInterpolator());
		Animation.AnimationListener scaleFaceAnimationListener = new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation arg0) {
				isFaceDown = false;
			}

			@Override
			public void onAnimationRepeat(Animation arg0) {
				if (faceTimer != null) {
					faceTimer.cancel();
					faceTimer = null;
				}
			}

			@Override
			public void onAnimationEnd(Animation arg0) {
				if (faceTimer != null) {
					faceTimer.cancel();
					faceTimer = null;
				}

				faceTimer = new Timer();
				if (faceAnimationSchedule != null) {
					faceAnimationSchedule.cancel();
					faceAnimationSchedule = null;
				}
				faceAnimationSchedule = new TimerTask() {
					@Override
					public void run() {
						faceScaleHandler.sendEmptyMessage(0);
					}
				};

				faceTimer.schedule(faceAnimationSchedule, 750);
			}
		};
		scaleFace.setAnimationListener(scaleFaceAnimationListener);
		return scaleFace;
	}

	private Handler faceScaleHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (clickedButton.isSelected() == true)
				clickedButton.startAnimation(scaleFaceDownAnimation(500));
		}
	};

	public Animation scaleFaceDownAnimation(int duration) {
		Animation scaleFace = new ScaleAnimation(1.2f, 1.0f, 1.2f, 1.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		scaleFace.setDuration(duration);
		scaleFace.setFillAfter(true);
		scaleFace.setInterpolator(new AccelerateInterpolator());
		Animation.AnimationListener scaleFaceAnimationListener = new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation arg0) {
			}

			@Override
			public void onAnimationRepeat(Animation arg0) {
				// verticalTextView.setText("");
				isFaceDown = true;
			}

			@Override
			public void onAnimationEnd(Animation arg0) {
				// verticalTextView.setText("");
				isFaceDown = true;
			}
		};
		scaleFace.setAnimationListener(scaleFaceAnimationListener);
		return scaleFace;
	}

	public void onDestroy() {
		clearTimerTaks(clickSchedule);
		clearTimerTaks(scrollerSchedule);
		clearTimerTaks(faceAnimationSchedule);
		clearTimers(scrollTimer);
		clearTimers(clickTimer);
		clearTimers(faceTimer);

		clickSchedule = null;
		scrollerSchedule = null;
		faceAnimationSchedule = null;
		scrollTimer = null;
		clickTimer = null;
		faceTimer = null;

		super.onDestroy();
	}

	private void clearTimers(Timer timer) {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	private void clearTimerTaks(TimerTask timerTask) {
		if (timerTask != null) {
			timerTask.cancel();
			timerTask = null;
		}
	}

	private void getAnadImageFromServer(List<Anad> anadlist, int icode,
			boolean IsFirst) {

		if (IsFirst == true) {

			switch (proID) {

			case 1:
				ImageCode = 1;
				break;
			case 3:
				ImageCode = 51;
				break;
			case 4:
				ImageCode = 81;
				break;
			case 5:
				ImageCode = 151;
				break;
			case 6:
				ImageCode = 201;
				break;
			case 7:
				ImageCode = 231;
				break;
			case 8:
				ImageCode = 261;
				break;
			case 9:
				ImageCode = 361;
				break;
			case 10:
				ImageCode = 391;
				break;
			case 11:
				ImageCode = 421;
				break;
			case 12:
				ImageCode = 471;
				break;
			case 13:
				ImageCode = 501;
				break;
			case 14:
				ImageCode = 531;
				break;
			case 15:
				ImageCode = 561;
				break;
			case 16:
				ImageCode = 591;
				break;
			case 17:
				ImageCode = 621;
				break;
			case 18:
				ImageCode = 671;
				break;
			case 19:
				ImageCode = 701;
				break;
			case 20:
				ImageCode = 751;
				break;
			case 21:
				ImageCode = 781;
				break;
			case 22:
				ImageCode = 831;
				break;
			case 23:
				ImageCode = 861;
				break;
			case 24:
				ImageCode = 891;
				break;
			case 25:
				ImageCode = 921;
				break;
			case 26:
				ImageCode = 971;
				break;
			case 27:
				ImageCode = 1021;
				break;
			case 28:
				ImageCode = 1071;
				break;
			case 29:
				ImageCode = 1121;
				break;
			case 30:
				ImageCode = 1171;
				break;
			case 31:
				ImageCode = 1201;
				break;
			case 0:
				ImageCode = 1251;
				break;
			case 2:
				ImageCode = 1351;
				break;
			default:
			}
		}
		Anad anadItem;
		String commiteDate, pathImage;
		boolean allowShow = true;
		int objId;

		if (icode == anadlist.size())
			IsFinish = true;

		if (icode < anadlist.size()) {
			anadItem = anadlist.get(icode);

			commiteDate = anadItem.getDate();
			pathImage = anadItem.getImagePath();
			objId = anadItem.getObjectId();

			Bitmap bmp;

			if (commiteDate == null)
				commiteDate = "20150000000000";

			int AnadYear = Integer.valueOf(commiteDate.substring(0, 4));
			int thisYear = Integer.valueOf(serverDate.substring(0, 4));

			if (thisYear < AnadYear + 1)
				allowShow = true;
			else
				allowShow = false;

			if (allowShow == false) {
				Toast.makeText(getActivity(),
						" آگهی شماره " + ImageCode + "منقضی شده است", 0).show();
				dbAdapter.open();
				dbAdapter.UpdateAnadToDb(ImageCode, -1, "", -1, proID);
				dbAdapter.close();

				update = new UpdatingImage(getActivity());
				update.delegate = AnadFragment.this;
				maps = new LinkedHashMap<String, String>();
				maps.put("tableName", "Anad");
				maps.put("Id", String.valueOf(ImageCode));
				maps.put("fromDate", "a");
				update.execute(maps);
			} else {
				if (pathImage != null) {

					if (getActivity() == null)
						return;
					imageButton = new ImageView(getActivity());

					bmp = BitmapFactory.decodeFile(pathImage);
					imageButton.setImageBitmap(bmp);
					imageButton.setLayoutParams(params);
					imageButton.setScaleType(ScaleType.FIT_XY);

					verticalOuterLayout.addView(imageButton);

					icode++;
					getAnadImageFromServer(anadlist, icode, false);

				}
				// if (objId == 0 && pathImage == null ) {

				//
				// addImagesToView(anadlist, ImageCode, icode);
				// }

				if (String.valueOf(objId) != null && pathImage == null) {

					update = new UpdatingImage(getActivity());
					update.delegate = AnadFragment.this;
					maps = new LinkedHashMap<String, String>();
					maps.put("tableName", "Anad");
					maps.put("Id", String.valueOf(ImageCode));
					maps.put("fromDate", "a");
					update.execute(maps);
				}

			}
		}
	}

	@Override
	public void processFinish(String output) {

		if (swipeLayout != null) {

			swipeLayout.setRefreshing(false);
		}

		if (output.length() == 18 && code == 100) {
			serverDate = output;
			getAnadImageFromServer(anadlist, icode, true);
		}

		else if (output.contains("anyType")) {
			LoadMoreFooter.setVisibility(View.INVISIBLE);
			// lst.removeFooterView(LoadMoreFooter);
		}

		else if (output != null
				&& !(output.contains("Exception") || output.contains("java")
						|| output.contains("SoapFault") || output
							.contains("anyType")) && code == -1) {
			util.parseQuery(output);
			mylist.clear();

			dbAdapter.open();
			mylist.addAll(dbAdapter.getAllTicket());
			dbAdapter.close();

			ListAdapter = new AnadListAdapter(getActivity(), R.layout.row_anad,
					mylist, proID, AnadFragment.this);

			// if (mylist != null && !mylist.isEmpty())
			listviewanad.setAdapter(ListAdapter);

			if (FindPosition == false) {
				listviewanad.setSelection(beforePosition);

			}
			LoadMoreFooter.setVisibility(View.INVISIBLE);

			ListAdapter.notifyDataSetChanged();

		}
	}

	@Override
	public void processFinish(byte[] output) {

		if (output != null) {

			int iz = util.CreateFile(output, ImageCode, "Mechanical", "Agahi",
					"imgAgahi", "Anad");

			dbAdapter.open();

			anadlist = dbAdapter.getAnadtByTypeIdProId(proID);

			dbAdapter.close();

			addImagesToView(anadlist, iz, icode);

			I++;
		}
	}
}
