package com.project.mechanic.utility;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {

	private String TAG = getClass().getSimpleName();

	private LruCache<String, Bitmap> mLruCache;

	private final WeakReference<ImageView> imageViewReference;
	private Context context;

	public BitmapWorkerTask(ImageView imageView, Context context) {

		// Use a WeakReference to ensure the ImageView can be garbage collected
		imageViewReference = new WeakReference<ImageView>(imageView);
		this.context = context;

		// Find out maximum memory available to application
		// 1024 is used because LruCache constructor takes int in kilobytes
		final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

		// Use 1/4th of the available memory for this memory cache.
		final int cacheSize = maxMemory / 4;
		Log.d(TAG, "max memory " + maxMemory + " cache size " + cacheSize);

		// LruCache takes key-value pair in constructor
		// key is the string to refer bitmap
		// value is the stored bitmap
		mLruCache = new LruCache<String, Bitmap>(cacheSize) {
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				// The cache size will be measured in kilobytes
				return bitmap.getByteCount() / 1024;
			}
		};
	}

	@Override
	protected Bitmap doInBackground(String... params) {
		final Bitmap bitmap = getScaledImage(params[0]);
		addBitmapToMemoryCache(String.valueOf(params[0]), bitmap);
		return bitmap;
	}

	public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
		if (getBitmapFromMemCache(key) == null) {
			mLruCache.put(key, bitmap);
		}
	}

	public Bitmap getBitmapFromMemCache(String key) {
		return mLruCache.get(key);
	}

	// onPostExecute() sets the bitmap fetched by doInBackground();
	@Override
	protected void onPostExecute(Bitmap bitmap) {
		if (imageViewReference != null && bitmap != null) {
			final ImageView imageView = (ImageView) imageViewReference.get();
			if (imageView != null) {
				imageView.setImageBitmap(bitmap);
			}
		}
	}

	private Bitmap getScaledImage(String imagePath) {
		Bitmap bitmap = null;
		Uri imageUri = Uri.parse(imagePath);
		try {
			BitmapFactory.Options options = new BitmapFactory.Options();

			/**
			 * inSampleSize flag if set to a value > 1, requests the decoder to
			 * sub-sample the original image, returning a smaller image to save
			 * memory. This is a much faster operation as decoder just reads
			 * every n-th pixel from given image, and thus providing a smaller
			 * scaled image. 'n' is the value set in inSampleSize which would be
			 * a power of 2 which is downside of this technique.
			 */
			options.inSampleSize = 4;

			options.inScaled = true;

			InputStream inputStream = context.getContentResolver().openInputStream(imageUri);

			bitmap = BitmapFactory.decodeStream(inputStream, null, options);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return bitmap;
	}

}