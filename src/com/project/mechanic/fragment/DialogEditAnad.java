package com.project.mechanic.fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.project.mechanic.R;
import com.project.mechanic.StaticValues;
import com.project.mechanic.crop.CropImage;
import com.project.mechanic.entity.Users;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.inter.SaveAsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.Saving;
import com.project.mechanic.service.SavingImage;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.utility.Utility;

import android.R.id;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class DialogEditAnad extends DialogFragment implements AsyncInterface, SaveAsyncInterface {
	ImageView imageAnad;
	Context context;
	Spinner pageSpinner;
	Utility util;
	Fragment fragment;
	Uri mImageCaptureUri;
	private static final int GALLERY_CODE = 201;
	final int PIC_CROP = 10;
	private File mFileTemp;
	public static final String TEMP_PHOTO_FILE_NAME = "temp_photo.jpg";
	byte[] ImageConvertedToByte = null;
	RelativeLayout.LayoutParams lp;
	DataBaseAdapter dbAdapter;
	List<Integer> ListId;
	static final String selectPageLable = "انتخاب صفحه";
	String itemSelected;
	int IdPage, anadId;
	String serverDate = "";
	ServerDate server;
	Map<String, String> params;
	boolean isFirst, booleanImage = false;
	Map<String, Object> imageParams;
	ProgressDialog ringProgressDialog;

	public DialogEditAnad(Context context, Fragment fr, int anadId) {
		this.context = context;
		util = new Utility(context);
		this.fragment = fr;
		dbAdapter = new DataBaseAdapter(context);
		this.anadId = anadId;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

		View rootView = inflater.inflate(R.layout.dialog_edit_anad, container, false);
		TextView lable1 = (TextView) rootView.findViewById(R.id.lableText);
		TextView lable2 = (TextView) rootView.findViewById(R.id.lableText2);

		imageAnad = (ImageView) rootView.findViewById(R.id.imageAnad);
		pageSpinner = (Spinner) rootView.findViewById(R.id.selectPage);

		Button saveBtn = (Button) rootView.findViewById(R.id.saveBtn);

		lable1.setTypeface(util.SetFontCasablanca());
		lable2.setTypeface(util.SetFontCasablanca());

		RelativeLayout layoutImage = (RelativeLayout) rootView.findViewById(R.id.imageLayout);
		lp = new RelativeLayout.LayoutParams(layoutImage.getLayoutParams());
		lp.width = (int) (util.getScreenwidth() / StaticValues.RateImageEditAnad);
		lp.height = (int) (util.getScreenwidth() / StaticValues.RateImageEditAnad);

		lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		lp.addRule(RelativeLayout.CENTER_VERTICAL);
		lp.setMargins(5, 5, 5, 5);
		imageAnad.setLayoutParams(lp);

		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			mFileTemp = new File(Environment.getExternalStorageDirectory(), TEMP_PHOTO_FILE_NAME);
		} else {
			mFileTemp = new File(getActivity().getFilesDir(), TEMP_PHOTO_FILE_NAME);
		}

		Users CurrentUser = util.getCurrentUser();
		ArrayList<String> namePage = new ArrayList<String>();
		ListId = new ArrayList<Integer>();
		dbAdapter.open();

		com.project.mechanic.entity.Object page;
		List<com.project.mechanic.entity.Object> objectPage = dbAdapter.getObjectByuserId(CurrentUser.getId());
		dbAdapter.close();

		if (objectPage.size() == 0) {
			pageSpinner.setVisibility(View.GONE);
			TextView txr = (TextView) rootView.findViewById(R.id.lableNOPage);
			txr.setVisibility(View.VISIBLE);

		} else {
			namePage.add(selectPageLable);

			for (int i = 0; i < objectPage.size(); i++) {
				page = objectPage.get(i);
				namePage.add(page.getName());
				ListId.add(page.getId());
			}
			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_spinner_item, namePage);

			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

			pageSpinner.setAdapter(dataAdapter);
		}

		pageSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

				int w = (int) pageSpinner.getSelectedItemId() - 1;

				itemSelected = (String) pageSpinner.getSelectedItem();

				if (!itemSelected.contains(selectPageLable)) {

					IdPage = ListId.get(w);
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		imageAnad.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent i = new Intent(Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				startActivityForResult(i, GALLERY_CODE);
			}
		});

		saveBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (!itemSelected.equals(selectPageLable) || ImageConvertedToByte == null) {

					server = new ServerDate(getActivity());
					server.delegate = DialogEditAnad.this;
					server.execute("");
					isFirst = true;
					Toast.makeText(context, anadId + "", 0).show();
					ringProgressDialog = ProgressDialog.show(getActivity(), "", "لطفا منتظر بمانید...", true);

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

				} else
					Toast.makeText(context, "انتخاب صفحه اجباری است", 0).show();

			}
		});

		return rootView;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// if (data != null) {
		switch (requestCode) {
		case GALLERY_CODE:
			if (resultCode == this.getActivity().RESULT_OK) {

				mImageCaptureUri = data.getData();
				try {
					// Bitmap bitmapImage = decodeBitmap(mImageCaptureUri);
					// ShowImage.setImageBitmap(bitmapImage);
					// ImageConvertedToByte =
					// Utility.CompressBitmap(bitmapImage);

					InputStream inputStream = getActivity().getContentResolver().openInputStream(data.getData());
					FileOutputStream fileOutputStream = new FileOutputStream(mFileTemp);
					try {
						Utility.copyStream(inputStream, fileOutputStream);
					} catch (IOException e) {
						e.printStackTrace();
					}
					try {
						fileOutputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					try {
						inputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}

					startCropImage();

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}

		case PIC_CROP:
			if (data != null) {
				String path = data.getStringExtra(CropImage.IMAGE_PATH);
				if (path == null) {
					return;
				}
				Bitmap bitmap = null;
				if (mFileTemp.getPath() != null)
					bitmap = BitmapFactory.decodeFile(mFileTemp.getPath());
				if (bitmap != null) {
					ImageConvertedToByte = Utility.CompressBitmap(bitmap);
					imageAnad.setImageBitmap(Utility.getclip(bitmap));
					imageAnad.setLayoutParams(lp);
				}
			}
		}

	}

	private void startCropImage() {

		Intent intent = new Intent(getActivity(), CropImage.class);
		intent.putExtra(CropImage.IMAGE_PATH, mFileTemp.getPath());
		intent.putExtra(CropImage.SCALE, true);

		intent.putExtra(CropImage.ASPECT_X, 3);
		intent.putExtra(CropImage.ASPECT_Y, 3);

		startActivityForResult(intent, PIC_CROP);
	}

	@Override
	public void processFinish(String output) {
		if (ringProgressDialog != null)
			ringProgressDialog.dismiss();

		if (!output.contains("Exception")) {
			if (isFirst == true) {
				serverDate = output;

				if (context != null) {

						Saving saving = new Saving(getActivity());
						saving.delegate = DialogEditAnad.this;
						params = new LinkedHashMap<String, String>();
						params.put("tableName", "Anad");

						params.put("Id", String.valueOf(anadId));
						params.put("ObjectId", String.valueOf(IdPage));
						params.put("ModifyDate", serverDate);
						params.put("IsUpdate", "1");

						saving.execute(params);
						isFirst = false;
						booleanImage = true;

						ringProgressDialog = ProgressDialog.show(getActivity(), "", "لطفا منتظر بمانید...", true);

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

					

				}
			} else {
					
						dbAdapter.open();
						dbAdapter.updateAnadItem(anadId, IdPage);
						dbAdapter.close();
						
						if (getActivity() != null) {

						SavingImage saveImage = new SavingImage(context);
						saveImage.delegate = DialogEditAnad.this;
						imageParams = new LinkedHashMap<String, Object>();
						imageParams.put("tableName", "Anad");
						imageParams.put("fieldName", "Image");
						imageParams.put("id", anadId);
						imageParams.put("image", ImageConvertedToByte);

						saveImage.execute(imageParams);

						ringProgressDialog = ProgressDialog.show(getActivity(), "", "لطفا منتظر بمانید...", true);

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

					
				}
			}
		}

	}

	@Override
	public void processFinishSaveImage(String output) {

		if (ringProgressDialog != null)
			ringProgressDialog.dismiss();

		if (!output.contains("Exception")) {

			util.CreateFile(ImageConvertedToByte, anadId, "Mechanical", "Anad", "anad", "Anad");

			((DisplayPersonalInformationFragment) fragment).FillExpandListView();

		}

		dismiss();

	}

}
