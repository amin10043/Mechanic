package com.project.mechanic.fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mechanic.R;
import com.project.mechanic.entity.Users;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.Saving;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.utility.Utility;

public class DialogpostTitle extends Dialog implements AsyncInterface {

	private Button btntitle;
	// private EditText titletxt;
	private EditText titleDestxt;
	OnMyDialogResult mDialogResult;
	private DataBaseAdapter dbadapter;
	Utility utility;
	int resourceId;
	Context context;
	Fragment fragment;
	PersianDate date;
	Saving saving;
	Map<String, String> params;
	Users currentUser;
	ProgressDialog ringProgressDialog;
	String currentDate;
	TextView titleHeader;
	ImageButton createImage;
	ServerDate sDate;
	String severDate;
	ImageView btnPickFile;

	ImageView ShowImage;
	byte[] ImageConvertedToByte = null;
	Button btnClearImage;

	public DialogpostTitle(Context context, int resourceId, Fragment fragment) {
		super(context);
		// TODO Auto-generated constructor stub
		this.resourceId = resourceId;
		this.context = context;
		this.fragment = fragment;
		dbadapter = new DataBaseAdapter(context);
		utility = new Utility(context);

		date = new PersianDate();
		currentUser = utility.getCurrentUser();
		currentDate = date.todayShamsi();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// if (currentUser == null) {
		// // karbar vared nashode ast !!!!!!!
		// // nabayad inja bashaaad !!!!!!
		// Toast.makeText(context, "ابدتدا باید وارد شوید", Toast.LENGTH_SHORT)
		// .show();
		// return;
		// }
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		setContentView(resourceId);
		btntitle = (Button) findViewById(R.id.btnPdf1_Object);
		// titletxt = (EditText) findViewById(R.id.txtTitleP);
		titleDestxt = (EditText) findViewById(R.id.txttitleDes);

		createImage = (ImageButton) findViewById(R.id.createicondialog);
		titleHeader = (TextView) findViewById(R.id.maintextcreate);

		// titletxt.setVisibility(View.GONE);
		// titleDestxt.setVisibility(View.GONE);
		// btntitle.setVisibility(View.GONE);

		btnPickFile = (ImageView) findViewById(R.id.btnPickFile);

		// createImage.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// if (currentUser != null) {
		// // titletxt.setVisibility(View.VISIBLE);
		// titleDestxt.setVisibility(View.VISIBLE);
		// btntitle.setVisibility(View.VISIBLE);
		//
		// createImage.setVisibility(View.GONE);
		// titleHeader.setVisibility(View.GONE);
		// }
		// }
		// });

		btntitle.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// sDate = new ServerDate(context);
				// sDate.delegate = DialogpostTitle.this;
				// sDate.execute("");
				dbadapter.open();
				dbadapter.insertPosttitletoDb(/* titletxt.getText().toString(), */
				titleDestxt.getText().toString(), currentUser.getId(),
						severDate, "ImageAddress");
				dbadapter.close();
				// ((PosttitleFragment) fragment).updateView();
				// dismiss();
			}
		});

		/****************************************************************************/
		/*
		 * LinearLayout lin3 = (LinearLayout)
		 * findViewById(R.id.RelativeLayout1); ut = new
		 * Utility(fragment.getActivity()); lp2 = new
		 * LinearLayout.LayoutParams(lin3.getLayoutParams()); lp2.height =
		 * ut.getScreenwidth() / 4; lp2.width = ut.getScreenwidth() / 4;
		 * lp2.setMargins(5, 5, 5, 5); ImageProfile.setLayoutParams(lp2);
		 */
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			mFileTemp = new File(Environment.getExternalStorageDirectory(),
					TEMP_PHOTO_FILE_NAME);
		} else {
			mFileTemp = new File(fragment.getActivity().getFilesDir(),
					TEMP_PHOTO_FILE_NAME);
		}
		btnPickFile.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				selectImageOption();
			}
		});

	}

	/********************************************************************/
	Utility ut;
	LinearLayout.LayoutParams lp2;
	private Uri mImageCaptureUri;
	private File mFileTemp;
	public static final String TEMP_PHOTO_FILE_NAME = "temp_photo.jpg";
	private static final int CAMERA_CODE = 101, GALLERY_CODE = 201,
			CROPING_CODE = 301;
	final int PIC_CROP = 10;
	ImageView ImageProfile, imagecamera;

	private void selectImageOption() {
		final CharSequence[] items = { "از دوربین", "از گالری تصاویر", "انصراف" };

		AlertDialog.Builder builder = new AlertDialog.Builder(
				fragment.getActivity());
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

					// fragment.getActivity().startActivityForResult(intent,
					// CAMERA_CODE);

				} else if (items[item].equals("از گالری تصاویر")) {

					Intent i = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

					fragment.getActivity().startActivityFromFragment(fragment,
							i, GALLERY_CODE);

					// Intent intent = new Intent();
					// intent.setType("image/*");
					// intent.setAction(Intent.ACTION_GET_CONTENT);
					// try {
					// intent.putExtra("return-data", true);
					// startActivityForResult(
					// Intent.createChooser(intent, "تکمیل کار با"),
					// GALLERY_CODE);
					// } catch (ActivityNotFoundException e) {
					// }

				} else if (items[item].equals("انصراف")) {
					dialog.dismiss();
				}
			}
		});

		builder.show();
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		fragment.onActivityResult(requestCode, resultCode, data);

		Toast.makeText(context, "Yes", Toast.LENGTH_SHORT).show();

		// switch (requestCode) {
		// case GALLERY_CODE:
		// if (resultCode == fragment.getActivity().RESULT_OK) {
		//
		// mImageCaptureUri = data.getData();
		// try {
		// Bitmap bitmapImage = decodeBitmap(mImageCaptureUri);
		// ShowImage.setImageBitmap(bitmapImage);
		// ImageConvertedToByte = Utility.CompressBitmap(bitmapImage);
		//
		// if (ImageConvertedToByte != null) {
		// ShowImage.setVisibility(View.VISIBLE);
		// btnClearImage.setVisibility(View.VISIBLE);
		// }
		//
		// } catch (FileNotFoundException e) {
		// e.printStackTrace();
		// }
		// }
		// }

	}

	public Bitmap decodeBitmap(Uri selectedImage) throws FileNotFoundException {
		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(fragment.getActivity().getContentResolver()
				.openInputStream(selectedImage), null, o);

		final int REQUIRED_SIZE = 100;

		int width_tmp = o.outWidth, height_tmp = o.outHeight;
		int scale = 1;
		while (true) {
			if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE) {
				break;
			}
			width_tmp /= 2;
			height_tmp /= 2;
			scale *= 2;
		}

		BitmapFactory.Options o2 = new BitmapFactory.Options();
		o2.inSampleSize = scale;
		return BitmapFactory.decodeStream(fragment.getActivity()
				.getContentResolver().openInputStream(selectedImage), null, o2);
	}

	/*****************************************************************************************/

	public interface OnMyDialogResult {
		void finish(String result);
	}

	public void setDialogResult(OnMyDialogResult dialogResult) {
		mDialogResult = dialogResult;
	}

	@Override
	public void processFinish(String output) {

		if (ringProgressDialog != null)
			ringProgressDialog.dismiss();
		int id = -1;
		try {
			id = Integer.valueOf(output);
			dbadapter.open();
			dbadapter.insertPosttitletoDb(/* id,titletxt.getText().toString(), */
			titleDestxt.getText().toString(), currentUser.getId(), severDate,
					"ImageAddress");
			dbadapter.close();
			((PosttitleFragment) fragment).updateView();
			this.dismiss();

		} catch (NumberFormatException ex) {
			if (output != null
					&& !(output.contains("Exception") || output
							.contains("java"))) {
				params = new LinkedHashMap<String, String>();
				saving = new Saving(context);
				saving.delegate = DialogpostTitle.this;

				params.put("TableName", "Post");
				// params.put("Title", titletxt.getText().toString());
				params.put("Description", titleDestxt.getText().toString());
				params.put("UserId", String.valueOf(currentUser.getId()));
				params.put("Date", output);
				params.put("ModifyDate", output);
				params.put("IsUpdate", "0");
				params.put("Id", "0");
				severDate = output;
				saving.execute(params);
				ringProgressDialog = ProgressDialog.show(context, "",
						"لطفا منتظر بمانید...", true);

				ringProgressDialog.setCancelable(true);
			} else {
				Toast.makeText(context, "خطا در ثبت. پاسخ نا مشخص از سرور",
						Toast.LENGTH_SHORT).show();
			}
		}

	}
}
