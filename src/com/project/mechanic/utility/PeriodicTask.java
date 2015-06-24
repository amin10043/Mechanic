package com.project.mechanic.utility;

import android.content.Context;
import android.os.Handler;

public class PeriodicTask {

	private int mInterval = 60000;
	private Handler mHandler;
	Context context;
	Utility util;

	public PeriodicTask(Context context) {
		this.context = context;
		util = new Utility(context);
		mHandler = new Handler();
		mHandler.postDelayed(mStatusChecker, mInterval);

	}

	public void startAlert() {
		if (Utility.isAppRunning(context)) {
			mInterval = 10000; // 1 minute
			util.Updating();
		} else {
			mInterval = 7200000; // 2 hours
			util.Updating();
		}
	}

	Runnable mStatusChecker = new Runnable() {

		@Override
		public void run() {
			startAlert();
			mHandler.postDelayed(mStatusChecker, mInterval);
		}
	};

	void startRepeatingTask() {
		mStatusChecker.run();
	}

	void stopRepeatingTask() {
		mHandler.removeCallbacks(mStatusChecker);
	}

}
