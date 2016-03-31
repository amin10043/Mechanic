package com.project.mechanic.fragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.StaticValues;
import com.project.mechanic.Action.FloatingActionButton;
import com.project.mechanic.adapter.AnadListAdapter;
import com.project.mechanic.adapter.ObjectListAdapter;
import com.project.mechanic.crop.CropImage;
import com.project.mechanic.entity.Anad;
import com.project.mechanic.entity.ExtraSettings;
import com.project.mechanic.entity.Settings;
import com.project.mechanic.entity.Ticket;
import com.project.mechanic.entity.Users;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.inter.AsyncInterfaceVisit;
import com.project.mechanic.inter.CommInterface;
import com.project.mechanic.inter.GetAsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.service.ServiceComm;
import com.project.mechanic.service.Updating;
import com.project.mechanic.service.UpdatingImage;
import com.project.mechanic.service.UpdatingVisit;
import com.project.mechanic.utility.Utility;

@SuppressLint({ "HandlerLeak", "NewApi" })
public class AnadFragment extends Fragment
		implements AsyncInterface, GetAsyncInterface, CommInterface, AsyncInterfaceVisit {

	DataBaseAdapter dbAdapter;
	View rootView/* , LoadMoreFooter */;
	List<Ticket> ticketList;
	List<Anad> anadlist;
	private DialogAnad dialog;
	private DialogAnadimg dialog1;
	int ticketTypeId = 0;
	private static int RESULT_LOAD_IMAGE = 1;
	public static String picturePath;
	int provinceId = 0;
	// int ticket;
	private LinearLayout verticalOuterLayout;
	// List<Anad> list;
	// List<Ticket> subList;
	// List<Ticket> tempList;
	int position;
	// int a;
	// int I;
	int gridePadding = 1;
	private int verticalScrollMax;
	// PullAndLoadListView lstTicket;
	private Timer scrollTimer = null;
	private TimerTask clickSchedule;
	private TimerTask scrollerSchedule;
	private TimerTask faceAnimationSchedule;
	public int scrollPos = 0;
	public int scrollViewHeight = 0;

	private Boolean isFaceDown = true;
	private Timer clickTimer = null;
	private Timer faceTimer = null;
	private ImageView clickedButton = null;
	Users currentUser;
	Utility util;
	// int i = 0, j = 9;
	final int PIC_CROP = 2;

	ImageView imageView;
	AnadListAdapter ListAdapter;
	private ScrollView verticalScrollview;
	int count;

	public static final String TEMP_PHOTO_FILE_NAME = "temp_photo.jpg";
	private File mFileTemp;
	ListView listviewanad;;
	SwipeRefreshLayout swipeLayout;
	Updating updating;
	Settings setting;

	boolean FindPosition;
	int beforePosition, code = 100, /* icode = 0 ImageCode, */counterAnad = 0;
	UpdatingImage update;
	Map<String, String> maps;

	String serverDate;
	ImageView imageButton;
	LinearLayout.LayoutParams layoutParams;
	ServerDate date;

	String typeItem, time;
	int TicketId;
	int counterTicketList = 0;
	FloatingActionButton createItem;
	Anad anadItem;
	// boolean startShowImageAfterDownload = false;
	List<ImageView> imageList = new ArrayList<ImageView>();
	int visitCounter = 0, anadId;
	Ticket ticketItem;

	List<Ticket> correctTicket = new ArrayList<Ticket>();
	TextView countAnad;

	int numberImageShow;

	RelativeLayout bottomSheet;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_anad, null);
		countAnad = (TextView) rootView.findViewById(R.id.countA);
		dbAdapter = new DataBaseAdapter(getActivity());
		util = new Utility(getActivity());
		currentUser = util.getCurrentUser();
		layoutParams = new LinearLayout.LayoutParams(util.getScreenwidth() / 3, util.getScreenwidth() / 3);

		findView();

		getSettings();

		currentTime();

		getTicketTypeIdAndProvinceId();

		// get date from server
		RunServiceDate();

		fillAnadListTicketList();

		getCountVisitFromServer();

		createTicket();

		createFile();

		swiperLayoutRefresh();

		addImagesToView();

		totalMethodeScroll();

		saveVisitAnad();
		
		bottomSheetView();

		return rootView;
	}

	private void bottomSheetView() {

		TextView txt1 = (TextView) rootView.findViewById(R.id.txt1);
		TextView txt2 = (TextView) rootView.findViewById(R.id.txt2);
		TextView txt3 = (TextView) rootView.findViewById(R.id.txt3);
		TextView txt4 = (TextView) rootView.findViewById(R.id.txt4);

		TextView lable = (TextView) rootView.findViewById(R.id.better);

		ImageView accept = (ImageView) rootView.findViewById(R.id.createDialogPage);
		ImageView close = (ImageView) rootView.findViewById(R.id.delete);

		txt1.setTypeface(util.SetFontIranSans());
		txt2.setTypeface(util.SetFontIranSans());
		txt3.setTypeface(util.SetFontIranSans());
		txt4.setTypeface(util.SetFontIranSans());
		lable.setTypeface(util.SetFontIranSans());

		txt1.setText("1-انتخاب عکس جهت آگهی باید مناسب و هماهنگ با فعالیت شما باشد");
		txt2.setText("2-سایر کاربران با لمس کردن آگهی مربوط به شما مستقیما به صفحه شما در این نرم افزار هدایت می شوند");

		if (provinceId > 0) {
			txt3.setText("3-این آگهی در 6 تالار مربوط به  این استان همزمان اجرا می شود");
			txt4.setText("4-هزینه این آگهی به مبلغ 5.000.000 ریال و از تاریخ ثبت به مدت  یک سال می باشد");

		} else {
			txt3.setText("3-این آگهی در 6 تالار کشوری همزمان اجرا می شود");
			txt4.setText("4-هزینه این آگهی به مبلغ 20.000.000 ریال و از تاریخ ثبت به مدت  یک سال می باشد");

		}

		accept.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();
				show_pay_fragment fragment = new show_pay_fragment();

				Bundle bundle = new Bundle();
				bundle.putString("AnadId", String.valueOf(anadId));
				bundle.putString("TypeId", String.valueOf(ticketTypeId));

				fragment.setArguments(bundle);

				trans.replace(R.id.content_frame, fragment);
				trans.addToBackStack(null);
				trans.commit();
				
				bottomSheet.setVisibility(View.GONE);


			}
		});

		close.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.up_from_bottom);
				bottomSheet.startAnimation(anim);

				bottomSheet.setVisibility(View.GONE);

			}
		});

	}

	public List<ImageView> addImagesToView() {

		dbAdapter.open();
		anadlist = dbAdapter.getAnadtByTypeIdProId(provinceId);
		dbAdapter.close();

		imageList.clear();
		verticalOuterLayout.removeAllViewsInLayout();
		;

		for (int t = 0; t < anadlist.size(); t++) {
			// byte[] tmpImage = lst.get(t).getImage();
			String imagePath = anadlist.get(t).getImagePath();

			// final ImageView
			imageButton = new ImageView(getActivity());
			if (imagePath == null) {
				Drawable image = this.getResources().getDrawable(R.drawable.propagand);

				imageButton.setImageDrawable(image);
				imageButton.setScaleType(ScaleType.FIT_XY);

			} else {
				imageButton.setImageBitmap(BitmapFactory.decodeFile(imagePath));

			}
			imageButton.setTag(t);

			imageButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {

					int position = (Integer) arg0.getTag();
					if (isFaceDown) {
						if (clickTimer != null) {
							clickTimer.cancel();
							clickTimer = null;
						}
						clickedButton = (ImageView) arg0;

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
						Anad t = anadlist.get(position);

						int objectId = t.getObjectId();
						clickTimer.schedule(clickSchedule, 1500);

						if (objectId == 0) {
							if (currentUser == null) {
								Toast.makeText(getActivity(), " برای ثبت تبلیغات وارد شوید", Toast.LENGTH_LONG).show();
								return;
							} else {

								// dialog1 = new DialogAnadimg(getActivity(),
								// R.layout.dialog_imganad, AnadFragment.this,
								// ticketTypeId, provinceId, t.getId());
								// util.setSizeDialog(dialog1);

								bottomSheet.setVisibility(View.VISIBLE);
//								createItem.setVisibility(View.INVISIBLE);

								Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.down_from_top);
								bottomSheet.startAnimation(anim);

								savePosition(provinceId, false);

							}
						} else {
							Toast.makeText(getActivity(), " عکس قبلا انتخاب شده", Toast.LENGTH_LONG).show();
							FragmentTransaction trans = ((MainActivity) getActivity()).getSupportFragmentManager()
									.beginTransaction();
							IntroductionFragment fragment = new IntroductionFragment();
							Bundle bundle = new Bundle();
							bundle.putString("Id", String.valueOf(t.getObjectId()));
							// bundlei.putString("I",
							// String.valueOf(t.getObjectId()));
							fragment.setArguments(bundle);
							trans.replace(R.id.content_frame, fragment);
							trans.addToBackStack(null);
							trans.commit();

							savePosition(provinceId, false);

						}

					}
				}

			});

			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(util.getScreenwidth() / 3,
					util.getScreenwidth() / 3);
			imageButton.setLayoutParams(params);
			imageButton.setScaleType(ScaleType.FIT_XY);
			verticalOuterLayout.addView(imageButton);
			imageList.add(imageButton);
		}
		// startShowImageAfterDownload = true;
		return imageList;

	}

	public void updateView() {
		dbAdapter.open();
		ticketList = dbAdapter.getTicketByTypeIdProId(ticketTypeId, provinceId);

		// ListView lstAnad = (ListView) view.findViewById(R.id.listVanad);
		AnadListAdapter ListAdapter = new AnadListAdapter(getActivity(), R.layout.row_anad, correctTicket, provinceId,
				AnadFragment.this, false, time, 1, ticketTypeId);
		ListAdapter.notifyDataSetChanged();
		listviewanad.setAdapter(ListAdapter);

		dbAdapter.close();

	}

	private void getTicketImageFromServer(List<Ticket> ticketList) {

		Ticket ticketItem;

		if (counterTicketList < ticketList.size()) {

			ticketItem = ticketList.get(counterTicketList);
			TicketId = ticketItem.getId();

			String imageTicketDate = ticketItem.getImageServerDate();
			if (imageTicketDate == null)
				imageTicketDate = "";

			if (getActivity() != null) {

				update = new UpdatingImage(getActivity());
				update.delegate = AnadFragment.this;
				maps = new LinkedHashMap<String, String>();
				maps.put("tableName", "Ticket");
				maps.put("Id", String.valueOf(TicketId));
				maps.put("fromDate", imageTicketDate);
				update.execute(maps);
				typeItem = "Ticket";

				// counterTicketList++;
				// getTicketImageFromServer(ValidTicket(ticketList),
				// counterTicketList);

			}
		} else {

			ticketList.clear();
			dbAdapter.open();
			ticketList = dbAdapter.getTicketByTypeIdProId(ticketTypeId, provinceId);
			dbAdapter.close();
			ListAdapter = new AnadListAdapter(getActivity(), R.layout.row_anad, ValidTicket(ticketList), provinceId,
					AnadFragment.this, false, time, 1, ticketTypeId);
			// if (mylist != null && !mylist.isEmpty())
			listviewanad.setAdapter(ListAdapter);
			getAnadImageFromServer(counterAnad);
			// LoadMoreFooter.setVisibility(View.INVISIBLE);

		}
	}

	private void getAnadImageFromServer(int counter) {

		dbAdapter.open();
		anadlist = dbAdapter.getAnadtByTypeIdProId(provinceId);
		dbAdapter.close();

		if (counter < anadlist.size()) {

			anadItem = anadlist.get(counter);
			String anadImageDate = anadItem.getImageServerDate();

			if (anadImageDate == null)
				anadImageDate = "";

			if (getActivity() != null) {

				update = new UpdatingImage(getActivity());
				update.delegate = AnadFragment.this;
				maps = new LinkedHashMap<String, String>();
				maps.put("tableName", "Anad");
				maps.put("Id", String.valueOf(anadItem.getId()));
				maps.put("fromDate", anadImageDate);
				update.execute(maps);
				typeItem = "Anad";
				// LoadMoreFooter.setVisibility(View.INVISIBLE);
			}

		} else {
			addImagesToView();

			correctTicket = ValidTicket(ticketList);

			counterTicketList = 0;
			getTicketDate();

		}

	}

	private void findView() {

		// find view by Id
		createItem = (FloatingActionButton) rootView.findViewById(R.id.fab);

		listviewanad = (ListView) rootView.findViewById(R.id.listVanad);

		swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);

		verticalScrollview = (ScrollView) rootView.findViewById(R.id.vertical_scrollview_id);

		// verticalScrollview.setVerticalScrollbarPosition(500);

		verticalOuterLayout = (LinearLayout) rootView.findViewById(R.id.vertical_outer_layout_id);

		bottomSheet = (RelativeLayout)rootView.findViewById(R.id.bottmSheet);
	}

	private void currentTime() {

		final SharedPreferences currentTime = getActivity().getSharedPreferences("time", 0);

		time = currentTime.getString("time", "-1");
	}

	private void getTicketTypeIdAndProvinceId() {

		ticketTypeId = Integer.valueOf(getArguments().getString("Id"));
		if (getArguments().getString("ProID") != null) {
			provinceId = Integer.valueOf(getArguments().getString("ProID"));
		}

	}

	private void RunServiceDate() {

		if (getActivity() != null) {
			date = new ServerDate(getActivity());
			date.delegate = AnadFragment.this;
			date.execute("");
		}
	}

	private void fillAnadListTicketList() {

		dbAdapter.open();

		ticketList = dbAdapter.getTicketByTypeIdProId(ticketTypeId, provinceId);
		anadlist = dbAdapter.getAnadtByTypeIdProId(provinceId);

		correctTicket = ValidTicket(ticketList);

		dbAdapter.close();

		ListAdapter = new AnadListAdapter(getActivity(), R.layout.row_anad, correctTicket, provinceId,
				AnadFragment.this, true, time, 1, ticketTypeId);
		listviewanad.setAdapter(ListAdapter);

		// LoadMoreFooter =
		// getActivity().getLayoutInflater().inflate(R.layout.load_more_footer,
		// null);
		// listviewanad.addFooterView(LoadMoreFooter);
		// LoadMoreFooter.setVisibility(View.INVISIBLE);
	}

	private void createTicket() {

		createItem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (currentUser == null) {
					Toast.makeText(getActivity(), " شما وارد نشده اید.", Toast.LENGTH_LONG).show();
					return;
				}

				FragmentTransaction trans = ((MainActivity) getActivity()).getSupportFragmentManager()
						.beginTransaction();
				NewTicketFragment fragment = new NewTicketFragment();
				Bundle bundle = new Bundle();

				bundle.putInt("TypeId", ticketTypeId);
				bundle.putInt("ProvinceId", provinceId);
				trans.setCustomAnimations(R.anim.enter_animation, R.anim.exit_animation);

				fragment.setArguments(bundle);
				trans.replace(R.id.content_frame, fragment);
				trans.addToBackStack(null);

				trans.commit();

				// dialog = new DialogAnad(getActivity(),
				// R.layout.dialog_addanad, AnadFragment.this, ticketTypeId,
				// provinceId);

				savePosition(provinceId, false);

			}
		});

	}

	private void createFile() {

		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			mFileTemp = new File(Environment.getExternalStorageDirectory(), TEMP_PHOTO_FILE_NAME);
		} else {
			mFileTemp = new File(getActivity().getFilesDir(), TEMP_PHOTO_FILE_NAME);
		}
	}

	private void swiperLayoutRefresh() {

		swipeLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {

				if (getActivity() != null) {

					updating = new Updating(getActivity());

					updating.delegate = AnadFragment.this;
					String[] params = new String[4];
					params[0] = "Ticket";
					params[1] = setting.getServerDate_Start_Ticket() != null ? setting.getServerDate_Start_Ticket()
							: "";
					params[2] = setting.getServerDate_End_Ticket() != null ? setting.getServerDate_End_Ticket() : "";

					params[3] = "1";
					updating.execute(params);
				}
			}
		});

		swipeLayout.setColorScheme(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
				android.R.color.holo_orange_light, android.R.color.holo_red_light);

	}

	private void totalMethodeScroll() {

		if (listviewanad != null) {

			listviewanad.setOnScrollListener(new OnScrollListener() {

				@Override
				public void onScrollStateChanged(AbsListView arg0, int arg1) {

					switch (arg1) {
					case SCROLL_STATE_FLING:
						createItem.hide(true);
						break;
					case SCROLL_STATE_TOUCH_SCROLL:
						createItem.show(true);
						break;
					}

				}

				@Override
				public void onScroll(AbsListView arg0, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

					int lastInScreen = firstVisibleItem + visibleItemCount;

					if (lastInScreen == totalItemCount) {

						// LoadMoreFooter.setVisibility(View.VISIBLE);

						if (getActivity() != null) {

							updating = new Updating(getActivity());

							updating.delegate = AnadFragment.this;
							String[] params = new String[4];
							params[0] = "Ticket";
							params[1] = setting.getServerDate_Start_Ticket() != null
									? setting.getServerDate_Start_Paper() : "";
							params[2] = setting.getServerDate_End_Ticket() != null ? setting.getServerDate_End_Ticket()
									: "";

							params[3] = "0";
							updating.execute(params);
						}

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
				verticalOuterLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				getScrollMaxAmount();
				startAutoScrolling();
			}

		});
	}

	private void getSettings() {
		dbAdapter.open();
		setting = dbAdapter.getSettings();
		dbAdapter.close();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode != Activity.RESULT_OK) {
			return;
		}
		if (requestCode == RESULT_LOAD_IMAGE && null != data) {
			try {

				InputStream inputStream = getActivity().getContentResolver().openInputStream(data.getData());
				FileOutputStream fileOutputStream = new FileOutputStream(mFileTemp);
				Utility.copyStream(inputStream, fileOutputStream);
				fileOutputStream.close();
				inputStream.close();

				startCropImage();

			} catch (Exception e) {

				Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
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
		int actualWidth = (verticalOuterLayout.getMeasuredHeight() /*- (256 * 3)*/);

		verticalScrollMax = actualWidth;

		scrollPos = returnSavedPosition();

		verticalScrollview.scrollTo(0, scrollPos);

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

			scrollTimer.schedule(scrollerSchedule, 1000, 20);
		}
	}

	public void moveScrollView() {

		scrollPos = (int) (verticalScrollview.getScrollY() + 1.0);
		scrollViewHeight = verticalScrollview.getHeight();

		if (imageButton.getHeight() > 0) {

			int space = scrollViewHeight % imageButton.getHeight();

			if (space > 0)
				count = ((scrollPos + scrollViewHeight - space) / imageButton.getHeight()) + 1;
			else
				count = ((scrollPos + scrollViewHeight) / imageButton.getHeight());

			if ((scrollPos + scrollViewHeight) == verticalScrollMax) {

				scrollPos = 0;
				savePosition(provinceId, true);
				;
			}

		}

		if (anadlist.size() > 0)
			countAnad.setText(count + " / " + anadlist.size());

		verticalScrollview.scrollTo(0, scrollPos);

	}

	public void stopAutoScrolling() {
		if (scrollTimer != null) {
			scrollTimer.cancel();
			scrollTimer = null;
		}
	}

	public Animation scaleFaceUpAnimation() {
		Animation scaleFace = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
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
		Animation scaleFace = new ScaleAnimation(1.2f, 1.0f, 1.2f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
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

	@Override
	public void processFinish(String output) {
		// LoadMoreFooter.setVisibility(View.INVISIBLE);
		if (output.contains("anyType")) {
			// LoadMoreFooter.setVisibility(View.INVISIBLE);
		}
		if (swipeLayout != null) {
			swipeLayout.setRefreshing(false);
			// LoadMoreFooter.setVisibility(View.INVISIBLE);

		}

		if (output.length() == 18 && code == 100) {
			serverDate = output;
			// LoadMoreFooter.setVisibility(View.INVISIBLE);

			getTicketImageFromServer(ticketList);
		}

		if (output != null && !(output.contains("Exception") || output.contains("java") || output.contains("SoapFault")
				|| output.contains("anyType")) && code == -1) {
			// LoadMoreFooter.setVisibility(View.INVISIBLE);

			util.parseQuery(output);
			ticketList.clear();

			dbAdapter.open();
			ticketList.addAll(dbAdapter.getAllTicket());
			dbAdapter.close();

			ListAdapter = new AnadListAdapter(getActivity(), R.layout.row_anad, ticketList, provinceId,
					AnadFragment.this, false, time, 1, ticketTypeId);

			listviewanad.setAdapter(ListAdapter);
			// LoadMoreFooter.setVisibility(View.INVISIBLE);

			if (FindPosition == false) {
				listviewanad.setSelection(beforePosition);
				// LoadMoreFooter.setVisibility(View.INVISIBLE);

			}
			// LoadMoreFooter.setVisibility(View.INVISIBLE);

			ListAdapter.notifyDataSetChanged();
			// LoadMoreFooter.setVisibility(View.INVISIBLE);

		}
	}

	private void saveVisitAnad() {

		String savedValue = "";

		if (currentUser != null) {

			savedValue = currentUser.getId() + "*";

			if (anadlist.size() > 0) {

				for (int z = 0; z < anadlist.size(); z++) {

					Anad a = anadlist.get(z);

					if (a != null) {

						int id = a.getObjectId();

						if (id != 0)
							savedValue += id + "-";

					}

				}

			}
		}

	}

	@Override
	public void onStop() {

		savePosition(provinceId, false);

		super.onStop();
	}

	@Override
	public void onResume() {

		getView().setFocusableInTouchMode(true);
		getView().requestFocus();
		getView().setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {

				if (event.getAction() == KeyEvent.ACTION_DOWN) {

					if (keyCode == KeyEvent.KEYCODE_BACK) {

						savePosition(provinceId, false);

						FragmentTransaction trans = ((MainActivity) getActivity()).getSupportFragmentManager()
								.beginTransaction();

						if (provinceId == 0) {

							AdvertisementFragment fragment = new AdvertisementFragment();
							// Bundle bundle = new Bundle();
							// bundle.putString("Id",
							// String.valueOf(object.getParentId()));
							// bundle.putInt("position", positionBrand);
							// fragment.setArguments(bundle);
							trans.replace(R.id.content_frame, fragment);

							trans.commit();

						} else {

							AdvertisementFragment fragment = new AdvertisementFragment();
							Bundle bundle = new Bundle();
							bundle.putString("provinceId", String.valueOf(provinceId));
							// bundle.putInt("position", positionBrand);
							// fragment.setArguments(bundle);
							trans.replace(R.id.content_frame, fragment);

							trans.commit();

						}

						return true;
					}

				}

				return false;
			}
		});
		super.onResume();
	}

	@Override
	public void processFinish(byte[] output) {

		if (typeItem.equals("Anad")) {

			if (output != null) {
				util.CreateFile(output, anadItem.getId(), "Mechanical", "Anad", "anad", "Anad");
			}
			counterAnad++;
			getAnadImageFromServer(counterAnad);

		}

		if (typeItem.equals("Ticket")) {
			if (output != null) {

				boolean IsEmptyByte = util.IsEmptyByteArrayImage(output);
				if (IsEmptyByte == false) {
					util.CreateFile(output, TicketId, "Mechanical", "Ticket", "ticket", "Ticket");

				}
			}
			counterTicketList++;
			getTicketImageFromServer(correctTicket);
		}

	}

	private void getTicketDate() {

		if (counterTicketList < correctTicket.size()) {

			ticketItem = correctTicket.get(counterTicketList);
			TicketId = ticketItem.getId();

			ServiceComm getDateService = new ServiceComm(getActivity());

			getDateService.delegate = AnadFragment.this;
			Map<String, String> items = new LinkedHashMap<String, String>();
			items.put("tableName", "getTicketImageDate");
			items.put("Id", String.valueOf(TicketId));
			getDateService.execute(items);

			typeItem = "Ticket";
		} else {

			counterAnad = 0;

			getAnadDate();
		}
	}

	private void getAnadDate() {

		dbAdapter.open();
		anadlist = dbAdapter.getAnadtByTypeIdProId(provinceId);
		dbAdapter.close();

		if (counterAnad < anadlist.size()) {

			anadItem = anadlist.get(counterAnad);
			anadId = anadItem.getId();

			ServiceComm getDateService = new ServiceComm(getActivity());

			getDateService.delegate = AnadFragment.this;
			Map<String, String> items = new LinkedHashMap<String, String>();
			items.put("tableName", "getAnadImageDate");
			items.put("Id", String.valueOf(anadId));
			getDateService.execute(items);
			typeItem = "Anad";

		}
	}

	@Override
	public void CommProcessFinish(String output) {

		if (output.contains("exception") || output.contains("anyType"))
			output = "";

		if (typeItem.equals("Ticket")) {

			dbAdapter.open();
			dbAdapter.UpdateImageServerDate(TicketId, "Ticket", output);
			dbAdapter.close();

			counterTicketList++;
			getTicketDate();
		}

		if (typeItem.equals("Anad")) {

			dbAdapter.open();
			dbAdapter.UpdateImageServerDate(anadId, "Anad", output);
			dbAdapter.close();

			counterAnad++;
			getAnadDate();

		}

	}

	private List<Ticket> ValidTicket(List<Ticket> list) {
		boolean isShow = false;
		Ticket ticket = null;
		ArrayList<Ticket> result = new ArrayList<Ticket>();

		for (int i = 0; i < list.size(); i++) {
			ticket = list.get(i);

			int thisDay = 0;
			int TicketDay = Integer.valueOf(ticket.getDate().substring(0, 8));
			if (time != null && !time.equals(""))
				thisDay = Integer.valueOf(time.substring(0, 8));

			if (thisDay <= TicketDay + ticket.getDay())
				isShow = true;
			else
				isShow = false;

			if (isShow == true) {
				result.add(ticket);
			}
		}
		return result;

	}

	private void getCountVisitFromServer() {

		List<Ticket> items = correctTicket;

		if (visitCounter < items.size()) {

			dbAdapter.open();
			ticketItem = dbAdapter.getTicketById(ticketList.get(visitCounter).getId());
			dbAdapter.close();

			UpdatingVisit updateVisit = new UpdatingVisit(getActivity());
			updateVisit.delegate = AnadFragment.this;
			Map<String, String> serv = new LinkedHashMap<String, String>();

			serv.put("tableName", "Visit");
			serv.put("objectId", String.valueOf(ticketItem.getId()));
			serv.put("typeId", StaticValues.TypeTicketVisit + "");
			updateVisit.execute(serv);

		} else {
			if (getActivity() != null) {

				fillAnadListTicketList();
			}
		}

	}

	@Override
	public void resultCountView(String output) {

		if (!output.contains("Exception")) {

			dbAdapter.open();
			dbAdapter.updateCountView("Ticket", ticketItem.getId(), Integer.valueOf(output));
			dbAdapter.close();
		}
		visitCounter++;
		getCountVisitFromServer();

	}

	public void savePosition(int provinceId, boolean flag) {

		String[] positionArray = ArrayPosition();

		String p = "";

		if (flag == false) {

			for (int x = 0; x < positionArray.length; x++)
				if (x == provinceId) {
					positionArray[x] = String.valueOf(scrollPos);
					break;
				}

		} else {
			for (int x = 0; x < positionArray.length; x++)
				if (x == provinceId) {
					positionArray[x] = String.valueOf(0);
					break;
				}
		}

		for (int a = 0; a < positionArray.length; a++)
			p += positionArray[a] + "-";

		dbAdapter.open();
		dbAdapter.updatePositionScrollAnad(p);
		dbAdapter.close();

	}

	public int returnSavedPosition() {

		ExtraSettings extraSettings = null;
		String[] positionArray = new String[32];

		dbAdapter.open();
		extraSettings = dbAdapter.getPositionScroll();
		dbAdapter.close();

		String tempPosition = extraSettings.getValue();

		positionArray = tempPosition.split("-");

		if (positionArray[provinceId] != null && !positionArray[provinceId].equals("-")) {

			scrollPos = Integer.valueOf(positionArray[provinceId]);
		} else
			scrollPos = 0;

		return scrollPos;

	}

	public String[] ArrayPosition() {

		ExtraSettings extraSettings = null;
		String[] positionArray = new String[32];

		dbAdapter.open();
		extraSettings = dbAdapter.getPositionScroll();
		dbAdapter.close();

		String tempPosition = extraSettings.getValue();

		positionArray = tempPosition.split("-");

		return positionArray;

	}
}
