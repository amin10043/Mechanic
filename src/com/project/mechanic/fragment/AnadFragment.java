package com.project.mechanic.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.Action.FloatingActionButton;
import com.project.mechanic.ListView.PullAndLoadListView;
import com.project.mechanic.ListView.PullAndLoadListView.OnLoadMoreListener;
import com.project.mechanic.ListView.PullToRefreshListView.OnRefreshListener;
import com.project.mechanic.adapter.AnadListAdapter;
import com.project.mechanic.entity.Anad;
import com.project.mechanic.entity.Ticket;
import com.project.mechanic.entity.Users;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

@SuppressLint("HandlerLeak")
public class AnadFragment extends Fragment {

	DataBaseAdapter dbAdapter;
	View view;
	List<Ticket> mylist;
	List<Anad> anadlist;
	private DialogAnad dialog;
	private DialogAnadimg dialog1;
	int ticketTypeid = 0;
	private static int RESULT_LOAD_IMAGE = 1;
	public static String picturePath;
	int proID = -1;
	int ticket;
	private LinearLayout verticalOuterLayout;
	List<Anad> list;
	List<Ticket> subList;
	List<Ticket> tempList;
	Anad tempItem;
	int position;
	int a;
	int I;
	int gridePadding = 1;
	private int verticalScrollMax;
	PullAndLoadListView lstTicket;
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
	int i = 0, j = 9;

	AnadListAdapter ListAdapter;
	private ScrollView verticalScrollview;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_anad, null);

		ticketTypeid = Integer.valueOf(getArguments().getString("Id"));
		FloatingActionButton createItem = (FloatingActionButton) view
				.findViewById(R.id.fabAnad);

		dbAdapter = new DataBaseAdapter(getActivity());
		util = new Utility(getActivity());
		u = util.getCurrentUser();

		if (getArguments().getString("ProID") != null) {
			proID = Integer.valueOf(getArguments().getString("ProID"));
		}

		dbAdapter.open();

		mylist = dbAdapter.getTicketByTypeIdProId(ticketTypeid, proID);
		anadlist = dbAdapter.getAnadtByTypeIdProId(proID);

		dbAdapter.close();

		if (mylist != null && !mylist.isEmpty()) {
			if (mylist.size() < j) {
				j = mylist.size();
			}
			List<Ticket> tmpList = mylist.subList(i, j);
			subList = new ArrayList<Ticket>();
			for (Ticket p : tmpList) {
				if (!subList.contains(p))
					subList.add(p);
			}
		}

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
				dialog.setTitle(R.string.txtanad);

				dialog.show();
			}
		});

		if (mylist != null && !mylist.isEmpty()) {
			lstTicket = (PullAndLoadListView) view.findViewById(R.id.listVanad);

			ListAdapter = new AnadListAdapter(getActivity(), R.layout.row_anad,
					subList, proID);

			lstTicket.setAdapter(ListAdapter);

			((PullAndLoadListView) lstTicket)
					.setOnRefreshListener(new OnRefreshListener() {

						public void onRefresh() {
							new PullToRefreshDataTask().execute();
						}
					});

			((PullAndLoadListView) lstTicket)
					.setOnLoadMoreListener(new OnLoadMoreListener() {

						public void onLoadMore() {
							if (mylist.size() < j + 1) {
								i = j + 1;
							}

							if (mylist.size() < j + 10) {
								j = mylist.size() - 1;
							} else {
								j += 10;
							}
							tempList = mylist.subList(i, j);
							for (Ticket p : tempList) {
								if (!subList.contains(p))
									subList.add(p);
							}
							new LoadMoreDataTask().execute();
						}
					});
		}

		verticalScrollview = (ScrollView) view
				.findViewById(R.id.vertical_scrollview_id);
		verticalOuterLayout = (LinearLayout) view
				.findViewById(R.id.vertical_outer_layout_id);
		addImagesToView(anadlist);

		ViewTreeObserver vto = verticalOuterLayout.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void onGlobalLayout() {
				verticalOuterLayout.getViewTreeObserver()
						.removeGlobalOnLayoutListener(this);
				getScrollMaxAmount();
				startAutoScrolling();
			}
		});
		return view;
	}

	public void addImagesToView(final List<Anad> lst) {

		for (I = 0; I < lst.size(); I++) {
			byte[] tmpImage = lst.get(I).getImage();

			final ImageButton imageButton = new ImageButton(getActivity());
			if (tmpImage == null) {
				Drawable image = this.getResources().getDrawable(
						R.drawable.propagand);

				imageButton.setImageDrawable(image);
				imageButton.setScaleType(ScaleType.FIT_XY);

			} else {
				imageButton.setImageBitmap(BitmapFactory.decodeByteArray(
						tmpImage, 0, tmpImage.length));

			}
			imageButton.setTag(I);

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
						Toast.makeText(getActivity(), position + "",
								Toast.LENGTH_LONG).show();

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
						dbAdapter.open();
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
						case -1:
							a = position + 1250 + 1;
							break;
						case 2:
							a = position + 1350 + 1;
							break;
						default:
						}

						Anad t = dbAdapter.getAnadByid(a);
						t.getObjectId();
						clickTimer.schedule(clickSchedule, 1500);
						if (u == null) {
							Toast.makeText(getActivity(),
									" شما وارد نشده اید.", Toast.LENGTH_LONG)
									.show();
							return;
						} else {
							if (t.getObjectId() == 0) {
								dialog1 = new DialogAnadimg(getActivity(),
										R.layout.dialog_imganad,
										AnadFragment.this, ticketTypeid, proID,
										a);
								dialog1.show();
								a = 0;
							} else {
								Toast.makeText(getActivity(),
										" عکس قبلا انتخاب شده",
										Toast.LENGTH_LONG).show();
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

			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					util.getScreenwidth() / 3, util.getScreenwidth() / 3);
			imageButton.setLayoutParams(params);
			imageButton.setScaleType(ScaleType.FIT_XY);
			verticalOuterLayout.addView(imageButton);
			dbAdapter.close();
		}
	}

	private class LoadMoreDataTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {

			if (isCancelled()) {
				return null;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			((BaseAdapter) ListAdapter).notifyDataSetChanged();
			((PullAndLoadListView) lstTicket).onLoadMoreComplete();
			super.onPostExecute(result);
		}

		@Override
		protected void onCancelled() {
			((PullAndLoadListView) lstTicket).onLoadMoreComplete();
		}
	}

	private class PullToRefreshDataTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {

			if (isCancelled()) {
				return null;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			((BaseAdapter) ListAdapter).notifyDataSetChanged();
			((PullAndLoadListView) lstTicket).onRefreshComplete();
			super.onPostExecute(result);
		}

		@Override
		protected void onCancelled() {
			((PullAndLoadListView) lstTicket).onLoadMoreComplete();
		}
	}

	public void updateView() {
		dbAdapter.open();
		mylist = dbAdapter.getTicketByTypeIdProId(ticketTypeid, proID);

		ListView lstAnad = (ListView) view.findViewById(R.id.listVanad);
		AnadListAdapter ListAdapter = new AnadListAdapter(getActivity(),
				R.layout.row_anad, mylist, proID);
		ListAdapter.notifyDataSetChanged();
		lstAnad.setAdapter(ListAdapter);

		dbAdapter.close();

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RESULT_LOAD_IMAGE
				&& resultCode == Activity.RESULT_OK && null != data) {
			Uri selectedImage = data.getData();

			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getActivity().getContentResolver().query(
					selectedImage, filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();

			ImageView imageView = (ImageView) dialog
					.findViewById(R.id.dialog_img1);
			imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

		}

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

}
