package com.project.mechanic.fragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.StaticValues;
import com.project.mechanic.crop.CropImage;
import com.project.mechanic.entity.Users;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.inter.CommInterface;
import com.project.mechanic.inter.SaveAsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.Saving;
import com.project.mechanic.service.SavingImage;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.service.ServiceComm;
import com.project.mechanic.utility.Utility;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class NewTicketFragment extends Fragment implements AsyncInterface, SaveAsyncInterface, CommInterface {

	EditText titleTicketEditText, descriptionTicketEditText, emailEditText, phoneEditText, mobileEditText, faxEditText,
			dayTicketEditText;
	ImageView closePage, send, selectImage, deleteImage, imageShow;
	View convertView;
	TextView name, lableInsert, lableEtebar, lableDay;
	RelativeLayout imageLayout;
	Utility util;
	int ticketTypeId, provinceId;
	public static final int LoadImageGallery = 100;
	public static final int cropPicture = 200;
	private File mFileTemp;
	public static final String TEMP_PHOTO_FILE_NAME = "temp_photo.jpg";
	int MaxSizeImageSelected = 5;
	byte[] ImageConvertedToByte = null;
	RelativeLayout.LayoutParams paramsPic;
	Users currentUser;
	int countDay;
	DataBaseAdapter dbadapter;
	String title, description, email, phone, mobile, fax, dayTicket, serverDate;
	Map<String, String> params;
	int idTicket;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		convertView = inflater.inflate(R.layout.new_ticket_fragment, null);

		if (getArguments() != null)
			ticketTypeId = getArguments().getInt("TypeId");
		provinceId = getArguments().getInt("ProvinceId");

		findView();

		setLayouParams();

		setonClick();

		setValues();

		return convertView;
	}

	public void findView() {

		titleTicketEditText = (EditText) convertView.findViewById(R.id.titleTicket);
		descriptionTicketEditText = (EditText) convertView.findViewById(R.id.descriptionTicket);

		emailEditText = (EditText) convertView.findViewById(R.id.email);
		phoneEditText = (EditText) convertView.findViewById(R.id.phone);
		mobileEditText = (EditText) convertView.findViewById(R.id.mobile);
		faxEditText = (EditText) convertView.findViewById(R.id.fax);
		dayTicketEditText = (EditText) convertView.findViewById(R.id.dayTicket);

		closePage = (ImageView) convertView.findViewById(R.id.closePage);
		send = (ImageView) convertView.findViewById(R.id.sendTicket);
		imageShow = (ImageView) convertView.findViewById(R.id.imageShow);
		selectImage = (ImageView) convertView.findViewById(R.id.pickImage);
		deleteImage = (ImageView) convertView.findViewById(R.id.deleteImage);

		name = (TextView) convertView.findViewById(R.id.name);
		lableInsert = (TextView) convertView.findViewById(R.id.lableInsert);
		lableEtebar = (TextView) convertView.findViewById(R.id.lableEtebar);
		lableDay = (TextView) convertView.findViewById(R.id.lableDay);

		imageLayout = (RelativeLayout) convertView.findViewById(R.id.imageLayout);

		util = new Utility(getActivity());
		currentUser = util.getCurrentUser();
		dbadapter = new DataBaseAdapter(getActivity());

	}

	public void setonClick() {

		selectImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String state = Environment.getExternalStorageState();

				if (Environment.MEDIA_MOUNTED.equals(state)) {
					mFileTemp = new File(Environment.getExternalStorageDirectory(), TEMP_PHOTO_FILE_NAME);
				} else {
					mFileTemp = new File(getActivity().getFilesDir(), TEMP_PHOTO_FILE_NAME);
				}

				Intent i = new Intent(Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				getActivity().startActivityFromFragment(NewTicketFragment.this, i, LoadImageGallery);

			}
		});

		deleteImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				imageLayout.setVisibility(View.GONE);
				deleteImage.setVisibility(View.GONE);
				ImageConvertedToByte = null;

			}
		});

		closePage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				FragmentTransaction trans = ((MainActivity) getActivity()).getSupportFragmentManager()
						.beginTransaction();
				AnadFragment fragment = new AnadFragment();

				Bundle bundle = new Bundle();
				bundle.putString("Id", String.valueOf(ticketTypeId));
				bundle.putString("ProID", String.valueOf(provinceId));
				trans.setCustomAnimations(R.anim.enter_animation, R.anim.exit_animation);

				fragment.setArguments(bundle);
				trans.replace(R.id.content_frame, fragment);
				trans.commit();

			}
		});

		send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				title = titleTicketEditText.getText().toString();
				description = descriptionTicketEditText.getText().toString();

				email = emailEditText.getText().toString();
				phone = phoneEditText.getText().toString();
				mobile = mobileEditText.getText().toString();
				fax = faxEditText.getText().toString();

				if (!"".equals(dayTicketEditText.getText().toString())) {
					countDay = Integer.parseInt(dayTicketEditText.getText().toString());
				}

				if ("".equals(title) || "".equals(description) || countDay <= 0) {

					Toast.makeText(getActivity(), " عنوان آگهی .شرح آگهی.اعتبار آگهی نمی تواند خالی باشد",
							Toast.LENGTH_LONG).show();

				} else {

					ServerDate serverDate = new ServerDate(getActivity());
					serverDate.delegate = NewTicketFragment.this;
					serverDate.execute("");
				}
			}
		});
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == LoadImageGallery) {
			try {

				long sizePicture = 0; // MB

				InputStream inputStream = getActivity().getContentResolver().openInputStream(data.getData());
				FileOutputStream fileOutputStream = new FileOutputStream(mFileTemp);
				Utility.copyStream(inputStream, fileOutputStream);
				fileOutputStream.close();
				inputStream.close();

				if (mFileTemp != null)
					sizePicture = mFileTemp.length() / 1024 / 1024;

				if (sizePicture > MaxSizeImageSelected)
					Toast.makeText(getActivity(),
							"حجم عکس انتخاب شده باید کمتر از " + MaxSizeImageSelected + "مگابایت باشد ",
							Toast.LENGTH_LONG).show();
				else
					startCropImage();

			} catch (Exception e) {

				Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
			}

		}
		if (requestCode == cropPicture && data != null) {
			String path = data.getStringExtra(CropImage.IMAGE_PATH);
			if (path == null) {
				return;
			}
			Bitmap bitmap = null;
			if (mFileTemp.getPath() != null) {

				ImageConvertedToByte = Utility.compressImage(mFileTemp);
				bitmap = BitmapFactory.decodeFile(mFileTemp.getPath());

			}
			if (bitmap != null) {

				imageShow.setImageBitmap(Utility.getclip(bitmap));
				imageShow.setLayoutParams(paramsPic);

				deleteImage.setVisibility(View.VISIBLE);
				imageLayout.setVisibility(View.VISIBLE);
			}

		}

		super.onActivityResult(requestCode, resultCode, data);

	}

	private void startCropImage() {

		Intent intent = new Intent(getActivity(), CropImage.class);
		intent.putExtra(CropImage.IMAGE_PATH, mFileTemp.getPath());
		intent.putExtra(CropImage.SCALE, true);

		intent.putExtra(CropImage.ASPECT_X, 3);
		intent.putExtra(CropImage.ASPECT_Y, 3);

		startActivityForResult(intent, cropPicture);
	}

	public void setLayouParams() {

		paramsPic = new RelativeLayout.LayoutParams(selectImage.getLayoutParams());
		paramsPic.width = (int) (util.getScreenwidth() / StaticValues.RateImageTitlePaper);
		paramsPic.height = (int) (util.getScreenwidth() / StaticValues.RateImageTitlePaper);
		paramsPic.addRule(RelativeLayout.CENTER_IN_PARENT);

	}

	public void setValues() {

		if (currentUser != null) {

			name.setText(currentUser.getName());

			emailEditText.setText(currentUser.getEmail());
			phoneEditText.setText(currentUser.getPhonenumber());
			mobileEditText.setText(currentUser.getMobailenumber());
			faxEditText.setText(currentUser.getFaxnumber());
		}

	}

	@Override
	public void processFinish(String output) {

		if (util.checkError(output) == false) {

			if (!"".equals(dayTicketEditText.getText().toString())) {
				countDay = Integer.parseInt(dayTicketEditText.getText().toString());
			}
			int id = -1;
			try {
				idTicket = id = Integer.valueOf(output);
				dbadapter.open();

				dbadapter.insertTickettoDbemptyImage(idTicket, title, description, currentUser.getId(), serverDate,
						ticketTypeId, provinceId, currentUser.getName(), email, phone, fax, null, mobile, countDay);
				dbadapter.close();

				if (ImageConvertedToByte != null) {

					if (getActivity() != null) {

						SavingImage savingImage = new SavingImage(getActivity());
						Map<String, Object> it = new LinkedHashMap<String, Object>();
						it.put("tableName", "Ticket");
						it.put("fieldName", "Image");
						it.put("id", idTicket);
						it.put("Image", ImageConvertedToByte);

						savingImage.delegate = NewTicketFragment.this;
						savingImage.execute(it);
					}
				} else {

					FragmentTransaction trans = ((MainActivity) getActivity()).getSupportFragmentManager()
							.beginTransaction();
					AnadFragment fragment = new AnadFragment();

					Bundle bundle = new Bundle();
					bundle.putString("Id", String.valueOf(ticketTypeId));
					bundle.putString("ProID", String.valueOf(provinceId));
					trans.setCustomAnimations(R.anim.enter_animation, R.anim.exit_animation);

					fragment.setArguments(bundle);
					trans.replace(R.id.content_frame, fragment);
					trans.commit();

					Toast.makeText(getActivity(), "آگهی شما با موفقیت ثبت شد", Toast.LENGTH_SHORT).show();

				}

			} catch (NumberFormatException ex) {
				if (!output.contains("SoapFault") || !output.contains("java") || !output.contains("Exception")) {
					if ("".equals(title) || "".equals(description) || countDay <= 0) {

						Toast.makeText(getActivity(), " عنوان آگهی .شرح آگهی.اعتبار آگهی نمی تواند خالی باشد",
								Toast.LENGTH_LONG).show();

					} else if ("".equals(email) && "".equals(phone) && "".equals(fax) && "".equals(mobile)) {
						Toast.makeText(getActivity(), "یکی از فیلد های اطلاعات تماس اجباریست", Toast.LENGTH_LONG)
								.show();
					} else {

						serverDate = output;

						params = new LinkedHashMap<String, String>();
						Saving saving = new Saving(getActivity());
						saving.delegate = NewTicketFragment.this;

						params.put("TableName", "Ticket");
						params.put("Title", title);
						params.put("[Desc]", description);
						params.put("UserId", String.valueOf(currentUser.getId()));
						params.put("TypeId", String.valueOf(ticketTypeId));
						params.put("ProvinceId", String.valueOf(provinceId));
						params.put("Date", output);
						params.put("ModifyDate", output);

						params.put("UName", currentUser.getName());
						params.put("UEmail", email);
						params.put("UPhonnumber", phone);
						params.put("UFax", fax);
						params.put("UMobile", mobile);
						params.put("IsUpdate", "0");
						params.put("Id", "0");
						params.put("Day", String.valueOf(countDay));

						saving.execute(params);
					}
				} else {
					Toast.makeText(getActivity(), StaticValues.MessageError, Toast.LENGTH_SHORT).show();
				}
			} catch (Exception ex) {
				Toast.makeText(getActivity(), "خطا در ثبت", Toast.LENGTH_SHORT).show();
			}
		} else {

			Toast.makeText(getActivity(), StaticValues.MessageError, 0).show();
		}

	}

	@Override
	public void processFinishSaveImage(String output) {

		if (util.checkError(output) == false) {

			try {
				Integer.valueOf(output);

				if (ImageConvertedToByte != null) {
					util.CreateFile(ImageConvertedToByte, idTicket, "Mechanical", "Ticket", "ticket", "Ticket");

					if (getActivity() != null) {

						ServiceComm getDateService = new ServiceComm(getActivity());
						getDateService.delegate = NewTicketFragment.this;
						Map<String, String> items = new LinkedHashMap<String, String>();

						items.put("tableName", "getTicketImageDate");
						items.put("Id", String.valueOf(idTicket));

						getDateService.execute(items);

					}
				}

			} catch (Exception ex) {
				Toast.makeText(getActivity(), StaticValues.MessageError, Toast.LENGTH_SHORT).show();
			}

		} else {
			Toast.makeText(getActivity(), StaticValues.errorImageSaving, 0).show();
		}

	}

	@Override
	public void CommProcessFinish(String output) {

		if (util.checkError(output) == false) {

			dbadapter.open();
			dbadapter.UpdateImageServerDate(idTicket, "Ticket", output);
			dbadapter.close();

			FragmentTransaction trans = ((MainActivity) getActivity()).getSupportFragmentManager().beginTransaction();
			AnadFragment fragment = new AnadFragment();

			Bundle bundle = new Bundle();
			bundle.putString("Id", String.valueOf(ticketTypeId));
			bundle.putString("ProID", String.valueOf(provinceId));
			trans.setCustomAnimations(R.anim.enter_animation, R.anim.exit_animation);

			fragment.setArguments(bundle);
			trans.replace(R.id.content_frame, fragment);
			trans.commit();

			Toast.makeText(getActivity(), "آگهی شما با موفقیت ثبت شد", Toast.LENGTH_SHORT).show();

		}
	}
}
