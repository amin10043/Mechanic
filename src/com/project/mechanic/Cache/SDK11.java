package com.project.mechanic.Cache;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
class SDK11 {

	static void addInBitmapOption(BitmapFactory.Options opts, Bitmap inBitmap) {
		opts.inBitmap = inBitmap;
	}

}