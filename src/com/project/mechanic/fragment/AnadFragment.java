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
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.ListView.PullAndLoadListView;
import com.project.mechanic.ListView.PullAndLoadListView.OnLoadMoreListener;
import com.project.mechanic.ListView.PullToRefreshListView.OnRefreshListener;
import com.project.mechanic.adapter.AnadImgListAdapter;
import com.project.mechanic.adapter.AnadListAdapter;
import com.project.mechanic.entity.Anad;
import com.project.mechanic.entity.Province;
import com.project.mechanic.entity.Ticket;
import com.project.mechanic.entity.Users;

import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class AnadFragment extends Fragment {

	DataBaseAdapter dbAdapter;
	private ImageView imgadd, img;
	private TextView txt1;
	View view;
	List<Ticket> mylist;
	List<Anad> anadlist;
	private DialogAnad dialog;
	private DialogAnadimg dialog1;
	int ticketTypeid = 0;
	private static int RESULT_LOAD_IMAGE = 1;
	public static String picturePath;
	int proID = -1;
	private LinearLayout verticalOuterLayout;
	List<Anad> list;
	List<Ticket> subList;
	List<Ticket> tempList;
	Anad tempItem;
	int position;
	private Anad x;
	private ListView lstimg;

	private int column = 3;
	int gridePadding = 1;
	private int columnWidth;
	// private ListView verticalScrollview;
	private TextView verticalTextView;
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
	private Button clickedButton = null;
	Users u;
	Utility util;
	int i=0,j=6;
	AnadListAdapter ListAdapter;
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_anad, null);

		verticalOuterLayout = (LinearLayout) view
				.findViewById(R.id.vertical_outer_layout_id);

		//((MainActivity) getActivity()).setActivityTitle(R.string.anad);
		ticketTypeid = Integer.valueOf(getArguments().getString("Id"));

		imgadd = (ImageView) view.findViewById(R.id.fragment_anad_imgadd);
		txt1 = (TextView) view.findViewById(R.id.fragment_anad_txt1);
		img = (ImageView) view.findViewById(R.id.img_anad);

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
		if(!mylist.isEmpty()){
		List<Ticket> tmpList = mylist.subList(i, j);
		subList = new ArrayList<Ticket>();
		for(Ticket p : tmpList){
			if(!subList.contains(p))
				subList.add(p);
		}
		}
		// img.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// dialogimg = new DialogAnadimg(getActivity(),
		// R.layout.dialog_imganad, AnadFragment.this,
		// ticketTypeid, proID);
		// // dialog.setTitle(R.string.txtanad);
		//
		// dialogimg.show();
		//
		// }
		// });
		imgadd.setOnClickListener(new OnClickListener() {

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
		if(!mylist.isEmpty()){
		lstTicket = (PullAndLoadListView) view.findViewById(R.id.listVanad);
		
		 ListAdapter = new AnadListAdapter(getActivity(),
				R.layout.row_anad, subList, proID);

		 lstTicket.setAdapter(ListAdapter);
		 
		 ((PullAndLoadListView) lstTicket)
			.setOnRefreshListener(new OnRefreshListener() {

				public void onRefresh() {
					// Do work to refresh the list here.

					new PullToRefreshDataTask().execute();
				}
			});
		 
			((PullAndLoadListView) lstTicket)
			.setOnLoadMoreListener(new OnLoadMoreListener() {

				public void onLoadMore() {
					// Do the work to load more items at the end of list
					// here
					if(mylist.size()< j+1){
						i=j+1;
					}
				
				if(mylist.size()< j+10){
					j = mylist.size()-1;
				}else{
					j+=10;
				}
				tempList = mylist.subList(i, j);
				for(Ticket p : tempList){
					if(!subList.contains(p))
					subList.add(p);
				}
				//Toast.makeText(getActivity(), String.valueOf(i), Toast.LENGTH_SHORT).show();
				//ListAdapter.notifyDataSetChanged();
				new LoadMoreDataTask().execute();
				}
			});}

		lstimg = (ListView) view.findViewById(R.id.listVanad2);
		AnadImgListAdapter ListAdapter2 = new AnadImgListAdapter(getActivity(),
				R.layout.row_anad_img, anadlist, proID);

		lstimg.setAdapter(ListAdapter2);

		verticalOuterLayout = (LinearLayout) view
				.findViewById(R.id.vertical_outer_layout_id);
		ViewTreeObserver vto = verticalOuterLayout.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				verticalOuterLayout.getViewTreeObserver()
						.removeGlobalOnLayoutListener(this);
				getScrollMaxAmount();
				startAutoScrolling();
			}
		});

		txt1.setOnClickListener(new OnClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onClick(View arg0) {

				imgadd.callOnClick();
			}
		});

		return view;

	}
	private class LoadMoreDataTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {

			if (isCancelled()) {
				return null;
			}

			// Simulates a background task
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
			}
			//
			// for (int i = 0; i < mNames.length; i++)
			// mListItems.add(mNames[i]);
			

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

			// We need notify the adapter that the data have been changed
			((BaseAdapter) ListAdapter).notifyDataSetChanged();

			// Call onLoadMoreComplete when the LoadMore task, has finished
			((PullAndLoadListView) lstTicket).onLoadMoreComplete();

			super.onPostExecute(result);
		}

		@Override
		protected void onCancelled() {
			// Notify the loading more operation has finished
			((PullAndLoadListView) lstTicket).onLoadMoreComplete();
		}
	}
	private class PullToRefreshDataTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {

			if (isCancelled()) {
				return null;
			}

			// Simulates a background task
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}

			// for (int i = 0; i < mAnimals.length; i++)
			// mListItems.addFirst(mAnimals[i]);

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

			// We need notify the adapter that the data have been changed
			((BaseAdapter) ListAdapter).notifyDataSetChanged();

			// Call onLoadMoreComplete when the LoadMore task, has finished
			((PullAndLoadListView) lstTicket).onRefreshComplete();

			super.onPostExecute(result);
		}

		@Override
		protected void onCancelled() {
			// Notify the loading more operation has finished
			((PullAndLoadListView) lstTicket).onLoadMoreComplete();
		}
	}

	public void updateView() {
		dbAdapter.open();
		mylist = dbAdapter.getTicketByTypeIdProId(ticketTypeid, proID);
		dbAdapter.close();

		ListView lstAnad = (ListView) view.findViewById(R.id.listVanad);
		AnadListAdapter ListAdapter = new AnadListAdapter(getActivity(),
				R.layout.row_anad, mylist, proID);
		ListAdapter.notifyDataSetChanged();
		lstAnad.setAdapter(ListAdapter);

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
		// int actualWidth = (verticalOuterLayout.getMeasuredHeight() - (256 *
		// 3));
		// verticalScrollMax = actualWidth;

		// int actualWidth = (verticalOuterLayout.getMeasuredHeight() - (256 *
		// 3));
		// int actualWidth = (verticalOuterLayout.getMeasuredHeight() - (256 *
		// 3));
		// verticalScrollMax = actualWidth;
		// verticalScrollMax = verticalScrollview.getHeight();
		verticalScrollMax = new Utility(getActivity()).getScreenHeight();
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

			scrollTimer.schedule(scrollerSchedule, 30, 15);
		}
	}

	public void moveScrollView() {
		scrollPos = (int) (lstimg.getScrollY() + 1.0);
		if (scrollPos >= verticalScrollMax) {
			scrollPos = 0;
		}
		lstimg.scrollTo(0, scrollPos);
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
				// verticalTextView.setText(nameArray[(Integer) clickedButton
				// .getTag()]);
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
				verticalTextView.setText("");
				isFaceDown = true;
			}

			@Override
			public void onAnimationEnd(Animation arg0) {
				verticalTextView.setText("");
				isFaceDown = true;
			}
		};
		scaleFace.setAnimationListener(scaleFaceAnimationListener);
		return scaleFace;
	}

	// public void onBackPressed() {
	// // super.onBackPressed();
	// // finish();
	// }

	// public void onPause() {
	// super.onPause();
	// // finish();
	// }

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

// public void onBackPressed() {
//
// FragmentTransaction trans = getActivity().getSupportFragmentManager()
// .beginTransaction();
// trans.replace(R.id.content_frame, new BerandFragment());
// trans.addToBackStack(null);
// trans.commit();
// }
//
// }
