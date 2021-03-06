package com.project.mechanic.fragment;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mechanic.R;
import com.project.mechanic.StaticValues;
import com.project.mechanic.entity.Ticket;
import com.project.mechanic.entity.Users;
import com.project.mechanic.entity.Visit;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.inter.AsyncInterfaceVisit;
import com.project.mechanic.inter.VisitSaveInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.Saving;
import com.project.mechanic.service.SavingVisit;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.utility.Utility;
import com.project.mechanic.view.TextViewEx;

public class ShowAdFragment extends Fragment implements AsyncInterface, VisitSaveInterface {

	int id;
	int a;
	int F = 0;
	private static int RESULT_LOAD_IMAGE = 1;
	int favorite = 0;
	int userTicket;
	DataBaseAdapter dbAdapter;
	TextView name, email, phone, mobile, fax, day, date, countVisit;
	TextViewEx desc;
	ImageView img, showname, showfax, showemail, showphone, showmobile;
	Button btnreport, btnCancel;
	List mylist;
	ImageButton share, edite, like;
	Utility util;
	Users u;
	Ticket t;
	private Dialog_show_fragment dialog;
	private Dialog_report dialog_report;
	int proID = -1;
	int f;
	private boolean isFavorite = false;
	RelativeLayout headerRelative, iconRelative;
	RelativeLayout.LayoutParams headerParams;
	int idItem, typeId, sender, userId;
	Map<String, String> params;
	int counterVisit = 0;
	boolean isFinish = false, saveVisitFalg;
	String currentTime = "";
	List<Visit> visitList;
	View view;

	RadioButton r1, r2, r3, r4, r5;

	@SuppressWarnings("null")
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// ((MainActivity) getActivity()).setActivityTitle(R.string.showad);
		id = Integer.valueOf(getArguments().getString("Id"));
		util = new Utility(getActivity());
		view = inflater.inflate(R.layout.fragment_showad, null);

		img = (ImageView) view.findViewById(R.id.fragment_anad_imgadd);

		share = (ImageButton) view.findViewById(R.id.imgShare_showAd);
		like = (ImageButton) view.findViewById(R.id.imgLike_showAd);
		edite = (ImageButton) view.findViewById(R.id.imgedite_showAd);
		desc = (TextViewEx) view.findViewById(R.id.fragment_showad_txt);
		name = (TextView) view.findViewById(R.id.fragment_showad_tx1);
		email = (TextView) view.findViewById(R.id.fragment_showad_tx2);
		phone = (TextView) view.findViewById(R.id.fragment_showad_tx3);
		mobile = (TextView) view.findViewById(R.id.fragment_showad_tx4);
		fax = (TextView) view.findViewById(R.id.fragment_showad_tx5);
		showname = (ImageView) view.findViewById(R.id.lableName);
		showemail = (ImageView) view.findViewById(R.id.lableEmail);
		showphone = (ImageView) view.findViewById(R.id.lablephone);
		showmobile = (ImageView) view.findViewById(R.id.lableMobile);
		showfax = (ImageView) view.findViewById(R.id.lablefax);
		btnreport = (Button) view.findViewById(R.id.btn_report);
		btnCancel = (Button) view.findViewById(R.id.btn_cancel);
		day = (TextView) view.findViewById(R.id.textDay);
		date = (TextView) view.findViewById(R.id.textdate);
		countVisit = (TextView) view.findViewById(R.id.numberOfView);

		r1 = (RadioButton) view.findViewById(R.id.r1);
		r2 = (RadioButton) view.findViewById(R.id.r2);
		r3 = (RadioButton) view.findViewById(R.id.r3);
		r4 = (RadioButton) view.findViewById(R.id.r4);
		r5 = (RadioButton) view.findViewById(R.id.r5);

		RelativeLayout lin1 = (RelativeLayout) view.findViewById(R.id.a1);
		RelativeLayout lin2 = (RelativeLayout) view.findViewById(R.id.a2);
		RelativeLayout lin3 = (RelativeLayout) view.findViewById(R.id.a3);
		RelativeLayout lin4 = (RelativeLayout) view.findViewById(R.id.a4);

		ImageView line1 = (ImageView) view.findViewById(R.id.i1);
		ImageView line2 = (ImageView) view.findViewById(R.id.i2);
		ImageView line3 = (ImageView) view.findViewById(R.id.i3);
		ImageView line4 = (ImageView) view.findViewById(R.id.i4);

		RelativeLayout.LayoutParams p1 = new RelativeLayout.LayoutParams(lin1.getLayoutParams());
		RelativeLayout.LayoutParams p2 = new RelativeLayout.LayoutParams(lin2.getLayoutParams());
		RelativeLayout.LayoutParams p3 = new RelativeLayout.LayoutParams(lin3.getLayoutParams());
		RelativeLayout.LayoutParams p4 = new RelativeLayout.LayoutParams(lin4.getLayoutParams());

		p1.width = util.getScreenwidth() - 150;
		p1.addRule(RelativeLayout.CENTER_HORIZONTAL);
		p1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

		p2.width = util.getScreenwidth() - 150;
		p2.addRule(RelativeLayout.CENTER_HORIZONTAL);
		p2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

		p3.width = util.getScreenwidth() - 150;
		p3.addRule(RelativeLayout.CENTER_HORIZONTAL);
		p3.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

		p4.width = util.getScreenwidth() - 150;
		p4.addRule(RelativeLayout.CENTER_HORIZONTAL);
		p4.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

		line1.setLayoutParams(p1);
		line2.setLayoutParams(p2);
		line3.setLayoutParams(p3);
		line4.setLayoutParams(p4);

		dbAdapter = new DataBaseAdapter(getActivity());

		dbAdapter.open();

		t = dbAdapter.getTicketById(id);
		a = t.getId();
		userTicket = t.getUserId();
		final boolean check = dbAdapter.isUserFavorite(userTicket, a);
		if (check) {
			like.setBackgroundResource(R.drawable.ic_star_on);
		} else {
			like.setBackgroundResource(R.drawable.ic_star_off);
		}
		// this code invisible edit button
		edite.setVisibility(View.GONE);

		like.setSelected(check);
		String ImagePath = t.getImagePath();
		Bitmap imgBitmap;
		if (ImagePath == null) {
			img.setBackgroundResource(R.drawable.no_img_profile);

		} else {
			imgBitmap = BitmapFactory.decodeFile(t.getImagePath());
			img.setImageBitmap(imgBitmap);
		}

		dbAdapter.close();
		u = util.getCurrentUser();

		// if (u == null) {
		// like.setEnabled(false);
		// } else if (userTicket == u.getId()) {
		//
		// }

		headerRelative = (RelativeLayout) view.findViewById(R.id.headerAnad);
		headerParams = new RelativeLayout.LayoutParams(headerRelative.getLayoutParams());
		headerParams.width = util.getScreenwidth();
		headerParams.height = util.getScreenwidth();
		headerParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

		img.setLayoutParams(headerParams);

		final EditText DescriptionReport = (EditText) view.findViewById(R.id.descriptionEdit);
		DescriptionReport.setTypeface(util.SetFontIranSans());
		final RadioGroup rd = (RadioGroup) view.findViewById(R.id.rb1);

		rd.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int checkedId) {
				DescriptionReport.setVisibility(View.VISIBLE);
				btnreport.setVisibility(View.VISIBLE);
				btnCancel.setVisibility(View.VISIBLE);

			}
		});

		like.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (u == null)
					Toast.makeText(getActivity(), "ابتدا باید وارد شوید", 0).show();
				else {

					if (check == true) {
						dbAdapter.open();
						dbAdapter.deletebyIdTicket(a);
						dbAdapter.close();
						like.setBackgroundResource(R.drawable.ic_star_off);
						// like.setLayoutParams(likeParams);

					} else {
						dbAdapter.open();
						dbAdapter.insertFavoritetoDb(0, u.getId(), a, 3);
						dbAdapter.close();
						like.setBackgroundResource(R.drawable.ic_star_on);
						// like.setLayoutParams(likeParams);

					}
					isFavorite = !isFavorite;

				}
			}
		});
		edite.setOnClickListener(new View.OnClickListener() {

			// @Override
			public void onClick(View arg0) {
				dialog = new Dialog_show_fragment(getActivity(), R.layout.dialog_show, ShowAdFragment.this, a);
				dialog.setTitle(R.string.txtanadedite);

				dialog.show();

			}
		});
		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				rd.clearCheck();
				DescriptionReport.setVisibility(View.GONE);
				btnreport.setVisibility(View.GONE);
				btnCancel.setVisibility(View.GONE);

			}
		});
		btnreport.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (u == null) {
					Toast.makeText(getActivity(), " شما وارد نشده اید.", Toast.LENGTH_LONG).show();
					return;
				}
				Toast.makeText(getActivity(), "گزارش شما با موفقیت ارسال شد", Toast.LENGTH_SHORT).show();
				// dialog_report = new Dialog_report(getActivity(),
				// R.layout.dialog_report, ShowAdFragment.this, a);
				// dialog_report.setTitle("گزارش آگهی");
				//
				// dialog_report.show();

			}
		});

		dbAdapter.open();
		share.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
				sharingIntent.setType("text/plain");
				String shareBody = t.getDesc();
				sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
				sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
				startActivity(Intent.createChooser(sharingIntent, "اشتراک از طریق"));

			}
		});

		desc.setText(t.getDesc() , true);
		day.setText("" + t.getDay());
		countVisit.setText(t.getCountView() + "");
		date.setText(util.getPersianDate(t.getDate()));

		if ("".equals(t.getUName())) {
			showname.setVisibility(View.GONE);
			name.setVisibility(View.GONE);

		} else {
			showname.setVisibility(View.VISIBLE);
			name.setVisibility(View.VISIBLE);
			name.setText(t.getUName());
		}
		if ("".equals(t.getUEmail())) {
			showemail.setVisibility(View.GONE);
			email.setVisibility(View.GONE);

		} else {
			showemail.setVisibility(View.VISIBLE);
			email.setVisibility(View.VISIBLE);
			email.setText(t.getUEmail());
		}
		if ("".equals(t.getUPhone())) {
			showphone.setVisibility(View.GONE);
			phone.setVisibility(View.GONE);

		} else {
			showphone.setVisibility(View.VISIBLE);
			phone.setVisibility(View.VISIBLE);
			phone.setText(t.getUPhone());
		}
		if ("".equals(t.getUMobile())) {
			showmobile.setVisibility(View.GONE);
			mobile.setVisibility(View.GONE);

		} else {
			showmobile.setVisibility(View.VISIBLE);
			mobile.setVisibility(View.VISIBLE);
			mobile.setText(t.getUMobile());
		}
		if ("".equals(t.getUFax())) {
			showfax.setVisibility(View.GONE);
			fax.setVisibility(View.GONE);

		} else {
			showfax.setVisibility(View.VISIBLE);
			fax.setVisibility(View.VISIBLE);
			fax.setText(t.getUFax());
		}
		dbAdapter.close();

		checkInternet();
		setfont();
		return view;

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK && null != data) {
			Uri selectedImage = data.getData();

			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();

			ImageView imageView = (ImageView) dialog.findViewById(R.id.dialog_img11);
			imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

		}
	}

	private void setfont() {
		TextView lable1 = (TextView) view.findViewById(R.id.labelDayOfCommitAnad);
		TextView lable2 = (TextView) view.findViewById(R.id.labelAtebar);
		TextView lable3 = (TextView) view.findViewById(R.id.labelnumberofView);
		TextView lable4 = (TextView) view.findViewById(R.id.labelInformationAnad);
		TextView lable5 = (TextView) view.findViewById(R.id.fragment_showad_desc);
		TextView lable6 = (TextView) view.findViewById(R.id.fragment_showad_txt2);
		TextView lable7 = (TextView) view.findViewById(R.id.fragment_showad_txt4);
		TextView lable8 = (TextView) view.findViewById(R.id.lableReport);
		TextView lable9 = (TextView) view.findViewById(R.id.dangrousTxt);

		TextView lable10 = (TextView) view.findViewById(R.id.as);

		lable1.setTypeface(util.SetFontCasablanca());
		lable2.setTypeface(util.SetFontCasablanca());
		lable3.setTypeface(util.SetFontCasablanca());
		lable4.setTypeface(util.SetFontCasablanca());
		lable5.setTypeface(util.SetFontCasablanca());
		lable6.setTypeface(util.SetFontCasablanca());
		lable7.setTypeface(util.SetFontIranSans());
		lable8.setTypeface(util.SetFontCasablanca());
		lable9.setTypeface(util.SetFontCasablanca());
		lable10.setTypeface(util.SetFontCasablanca());

		btnreport.setTypeface(util.SetFontCasablanca());
		btnCancel.setTypeface(util.SetFontCasablanca());

		desc.setTypeface(util.SetFontCasablanca());

		r1.setTypeface(util.SetFontIranSans());
		r2.setTypeface(util.SetFontIranSans());
		r3.setTypeface(util.SetFontIranSans());
		r4.setTypeface(util.SetFontIranSans());
		r5.setTypeface(util.SetFontIranSans());
		name.setTypeface(util.SetFontCasablanca());
	}

	private ConnectivityManager getSystemService(String connectivityService) {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateView() {

		dbAdapter.open();

		Ticket t = dbAdapter.getTicketById(id);
		a = t.getId();
		userTicket = t.getUserId();

		byte[] bitmapbyte = t.getImage();
		if (bitmapbyte != null) {
			Bitmap bmp = BitmapFactory.decodeByteArray(bitmapbyte, 0, bitmapbyte.length);
			img.setImageBitmap(bmp);
		}
		dbAdapter.close();
		u = util.getCurrentUser();

		if (u == null) {
			like.setEnabled(false);
		} else if (userTicket == u.getId()) {

			edite.setVisibility(1);
		}

		dbAdapter.open();

		desc.setText(t.getDesc() , true);
		day.setText("" + t.getDay());
		date.setText(util.getPersianDate(t.getDate()));

		if ("".equals(t.getUName())) {
			showname.setVisibility(View.GONE);
			name.setVisibility(View.GONE);

		} else {
			showname.setVisibility(View.VISIBLE);
			name.setVisibility(View.VISIBLE);
			name.setText(t.getUName());
		}
		if ("".equals(t.getUEmail())) {
			showemail.setVisibility(View.GONE);
			email.setVisibility(View.GONE);

		} else {
			showemail.setVisibility(View.VISIBLE);
			email.setVisibility(View.VISIBLE);
			email.setText(t.getUEmail());
		}
		if ("".equals(t.getUPhone())) {
			showphone.setVisibility(View.GONE);
			phone.setVisibility(View.GONE);

		} else {
			showphone.setVisibility(View.VISIBLE);
			phone.setVisibility(View.VISIBLE);
			phone.setText(t.getUPhone());
		}
		if ("".equals(t.getUMobile())) {
			showmobile.setVisibility(View.GONE);
			mobile.setVisibility(View.GONE);

		} else {
			showmobile.setVisibility(View.VISIBLE);
			mobile.setVisibility(View.VISIBLE);
			mobile.setText(t.getUMobile());
		}
		if ("".equals(t.getUFax())) {
			showfax.setVisibility(View.GONE);
			fax.setVisibility(View.GONE);

		} else {
			showfax.setVisibility(View.VISIBLE);
			fax.setVisibility(View.VISIBLE);
			fax.setText(t.getUFax());
		}
		dbAdapter.close();

	}

	private void checkInternet() {

		if (util.getCurrentUser() != null) {

			if (util.isNetworkConnected()) {
				Toast.makeText(getActivity(), "Connected", 0).show();

				ServerDate date = new ServerDate(getActivity());
				date.delegate = ShowAdFragment.this;
				date.execute("");
				saveVisitFalg = true;

			} else {
				Toast.makeText(getActivity(), "Disconnected", 0).show();

				if (checkUsers() == true) {

					dbAdapter.open();
					dbAdapter.insertVisitToDb(util.getCurrentUser().getId(), StaticValues.TypeTicketVisit, t.getId());
					dbAdapter.close();
				}

			}

		}
	}

	private boolean checkUsers() {

		// if isSave = true allow to save visit on server
		// else don't allow to save
		boolean isSave = true;

		if (util.getCurrentUser().getId() == t.getUserId())
			isSave = false;

		return isSave;

	}

	private void sendVisit() {

		if (getActivity() != null) {

			dbAdapter.open();
			visitList = dbAdapter.getAllVisitItems();
			dbAdapter.close();

			Visit vis = null;

			if (visitList.size() != 0) {

				if (counterVisit < visitList.size()) {

					vis = visitList.get(counterVisit);

					sender = vis.getUserId();
					typeId = vis.getTypeId();
					idItem = vis.getObjectId();

					params = new LinkedHashMap<String, String>();
					SavingVisit saving = new SavingVisit(getActivity());
					saving.delegate = ShowAdFragment.this;

					params.put("TableName", "Visit");
					params.put("UserId", String.valueOf(sender));
					params.put("TypeId", String.valueOf(typeId));
					params.put("ObjectId", String.valueOf(idItem));
					params.put("ModifyDate", String.valueOf(currentTime));
					params.put("Date", String.valueOf(currentTime));

					params.put("IsUpdate", "0");
					params.put("Id", "0");

					saving.execute(params);

					counterVisit++;

					saveVisitFalg = true;
					// sendVisit();
				} else {

					params = new LinkedHashMap<String, String>();
					SavingVisit saving = new SavingVisit(getActivity());
					saving.delegate = ShowAdFragment.this;

					params.put("TableName", "Visit");
					params.put("UserId", String.valueOf(t.getUserId()));
					params.put("TypeId", String.valueOf(StaticValues.TypeTicketVisit));
					params.put("ObjectId", String.valueOf(t.getId()));
					params.put("ModifyDate", String.valueOf(currentTime));
					params.put("Date", String.valueOf(currentTime));

					params.put("IsUpdate", "0");
					params.put("Id", "0");

					saving.execute(params);

					dbAdapter.open();
					dbAdapter.deleteVisit();
					dbAdapter.close();

					saveVisitFalg = false;
					isFinish = true;

				}

			} else {

				if (checkUsers() == true) {
					userId = util.getCurrentUser().getId();
					typeId = StaticValues.TypeTicketVisit;
					// int idObj = object.getId();

					params = new LinkedHashMap<String, String>();
					SavingVisit saving = new SavingVisit(getActivity());
					saving.delegate = ShowAdFragment.this;

					params.put("TableName", "Visit");
					params.put("UserId", String.valueOf(userId));
					params.put("TypeId", String.valueOf(typeId));
					params.put("ObjectId", String.valueOf(t.getId()));
					params.put("ModifyDate", String.valueOf(currentTime));
					params.put("Date", String.valueOf(currentTime));

					params.put("IsUpdate", "0");
					params.put("Id", "0");

					saving.execute(params);
					saveVisitFalg = false;
					isFinish = true;

				}
			}

		}

	}

	@Override
	public void processFinish(String output) {

		Toast.makeText(getActivity(), output, 0).show();

		if (!output.contains("Exception")) {
			if (saveVisitFalg == true) {

				if (counterVisit == 0)
					currentTime = output;
				sendVisit();

			} else {
				if (isFinish == true) {

					return;
				}
			}

		}

	}

	@Override
	public void resultSaveVisit(String output) {

		if (!output.contains("Exception")) {

			if (isFinish == false) {
				Visit vis = null;

				if (visitList.size() != 0) {

					if (counterVisit < visitList.size()) {

						vis = visitList.get(counterVisit);

						sender = vis.getUserId();
						typeId = vis.getTypeId();
						idItem = vis.getObjectId();

						params = new LinkedHashMap<String, String>();
						SavingVisit saving = new SavingVisit(getActivity());
						saving.delegate = ShowAdFragment.this;

						params.put("TableName", "Visit");
						params.put("UserId", String.valueOf(sender));
						params.put("TypeId", String.valueOf(typeId));
						params.put("ObjectId", String.valueOf(idItem));
						params.put("ModifyDate", String.valueOf(currentTime));
						params.put("Date", String.valueOf(currentTime));

						params.put("IsUpdate", "0");
						params.put("Id", "0");

						saving.execute(params);

						counterVisit++;

						saveVisitFalg = true;
						// sendVisit();
					} else {

						params = new LinkedHashMap<String, String>();
						SavingVisit saving = new SavingVisit(getActivity());
						saving.delegate = ShowAdFragment.this;

						params.put("TableName", "Visit");
						params.put("UserId", String.valueOf(t.getUserId()));
						params.put("TypeId", String.valueOf(StaticValues.TypeTicketVisit));
						params.put("ObjectId", String.valueOf(t.getId()));
						params.put("ModifyDate", String.valueOf(currentTime));
						params.put("Date", String.valueOf(currentTime));

						params.put("IsUpdate", "0");
						params.put("Id", "0");

						saving.execute(params);

						dbAdapter.open();
						dbAdapter.deleteVisit();
						dbAdapter.close();

						saveVisitFalg = false;
						isFinish = true;

					}

				}
			}

		}

	}
}