//////////////////
package com.project.mechanic.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.StaticValues;
import com.project.mechanic.PushNotification.DomainSend;
import com.project.mechanic.adapter.AnadListAdapter;
import com.project.mechanic.adapter.DataPersonalExpandAdapter;
import com.project.mechanic.entity.LikeInObject;
import com.project.mechanic.entity.Object;
import com.project.mechanic.entity.PersonalData;
import com.project.mechanic.entity.Settings;
import com.project.mechanic.entity.Ticket;
import com.project.mechanic.entity.Users;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.inter.CommInterface;
import com.project.mechanic.inter.GetAsyncInterface;
import com.project.mechanic.interfaceServer.AllObjectLiked;
import com.project.mechanic.inter.DataPersonalInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.server.GetAllObjectLiked;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.service.ServiceComm;
import com.project.mechanic.service.UpdatingImage;
import com.project.mechanic.service.UpdatingPersonalPage;
import com.project.mechanic.utility.Utility;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayPersonalInformationFragment extends Fragment
		implements AsyncInterface, GetAsyncInterface, CommInterface, DataPersonalInterface, AllObjectLiked {

	DataBaseAdapter dbAdapter;
	Utility util;
	UpdatingImage serviceImage;
	DialogPersonLikedFroum dia;
	ImageView img, logout;
	Ticket tempItem;
	Users currentUser;
	Settings setting;
	String serverDate;
	ServerDate date;
	// int userId;
	RelativeLayout phoneLayout, emailLayout, faxLayout, mobileLayout, AddressLayout, btnedit, birthDayUsers;

	AnadListAdapter anadGridAdapter;
	View rootView, header;
	ExpandableListView Expandview;

	TextView txtaddress, txtcellphone, txtphone, txtemail, txtname, txtfax, txtdate;
	TextView txtEdit, txtBirthday;
	// LinearLayout.LayoutParams lp1;
	RelativeLayout.LayoutParams editBtnParams, paramsLayout;

	UpdatingPersonalPage updating;
	// ProgressDialog ringProgressDialog;

	boolean isFirstRun;
	String tableName;
	int selectTableId = 1;
	List<PersonalData> ObejctData = new ArrayList<PersonalData>();
	List<PersonalData> FroumData = new ArrayList<PersonalData>();
	List<PersonalData> PaperData = new ArrayList<PersonalData>();
	List<PersonalData> TicketData = new ArrayList<PersonalData>();
	List<PersonalData> AnadData = new ArrayList<PersonalData>();
	List<PersonalData> FollowedPageLsit = new ArrayList<PersonalData>();

	Map<String, String> maps;

	int objectIdPage, objectIdFollowed, ticketIdData, anadIdData;
	int counterMyObject = 0, counterFollowObject = 0, counterTicket = 0, counterAnad = 0;
	boolean f1 = false;
	boolean f2 = false;
	boolean f3 = false;
	boolean f4 = false;
	DataPersonalExpandAdapter listAdapter;

	PersonalData pdObject, pdObjectFollowed, pdTicket, pdAnad;
	ArrayList<String> parentItems = new ArrayList<String>();
	HashMap<String, List<PersonalData>> listDataChild = new HashMap<String, List<PersonalData>>();
	List<Integer> sizeTypeList = new ArrayList<Integer>();
	List<Object> myFollowingPages = new ArrayList<Object>();

	@SuppressLint("NewApi")
	@Override
	public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		util = new Utility(getActivity());
		dbAdapter = new DataBaseAdapter(getActivity());

		// define rootView and header Layout
		rootView = inflater.inflate(R.layout.test_expandable, null);
		header = inflater.inflate(R.layout.fragment_test_display_personal, null);
		util.inputCommentAndPickFile(getActivity(), false);

		// define Views : find View By Id
		findView();

		// get User
		CurrentUser();

		setFont();

		RunServiceDate();

		// set data for expandListView
		FillExpandListView();

		// setValue for Parameter and variable
		setValue();

		// set LauoutParams
		setLayoutParams();

		// on click action
		onClick();

		return rootView;
	}

	public void FillExpandListView() {

		if (util.getCurrentUser() != null) {

			fillListDataChild();

			final SharedPreferences currentTime = getActivity().getSharedPreferences("time", 0);

			String time = currentTime.getString("time", "-1");

			listAdapter = new DataPersonalExpandAdapter(getActivity(), parentItems, listDataChild, time,
					DisplayPersonalInformationFragment.this, sizeTypeList, true, util.getCurrentUser().getName());

			// setting list adapter

			Expandview.setAdapter(listAdapter);
		}
	}

	private void findView() {

		phoneLayout = (RelativeLayout) header.findViewById(R.id.laySabet);
		mobileLayout = (RelativeLayout) header.findViewById(R.id.layHamrah);
		AddressLayout = (RelativeLayout) header.findViewById(R.id.layaddress);
		faxLayout = (RelativeLayout) header.findViewById(R.id.layfax);
		emailLayout = (RelativeLayout) header.findViewById(R.id.layEmail);

		Expandview = (ExpandableListView) rootView.findViewById(R.id.items);

		txtaddress = (TextView) header.findViewById(R.id.address);
		txtcellphone = (TextView) header.findViewById(R.id.cellphone);
		txtphone = (TextView) header.findViewById(R.id.phone);
		txtemail = (TextView) header.findViewById(R.id.email);
		txtname = (TextView) header.findViewById(R.id.displayname);
		txtfax = (TextView) header.findViewById(R.id.fax);

		img = (ImageView) header.findViewById(R.id.img1);
		logout = (ImageView) header.findViewById(R.id.logout);

		btnedit = (RelativeLayout) header.findViewById(R.id.btnedit);

		birthDayUsers = (RelativeLayout) header.findViewById(R.id.birth);

		txtdate = (TextView) header.findViewById(R.id.txtdate);
		txtdate.setVisibility(View.GONE);

		Expandview.addHeaderView(header);

		txtEdit = (TextView) header.findViewById(R.id.labb1);
		txtBirthday = (TextView) header.findViewById(R.id.tst);

	}

	private Users CurrentUser() {

		dbAdapter.open();
		currentUser = dbAdapter.getUserById(util.getCurrentUser().getId());
		dbAdapter.close();

		return currentUser;

	}

	private void setLayoutParams() {

		RelativeLayout.LayoutParams f1 = new RelativeLayout.LayoutParams(phoneLayout.getLayoutParams());
		RelativeLayout.LayoutParams f2 = new RelativeLayout.LayoutParams(mobileLayout.getLayoutParams());
		RelativeLayout.LayoutParams f3 = new RelativeLayout.LayoutParams(emailLayout.getLayoutParams());
		RelativeLayout.LayoutParams f4 = new RelativeLayout.LayoutParams(faxLayout.getLayoutParams());
		RelativeLayout.LayoutParams f5 = new RelativeLayout.LayoutParams(AddressLayout.getLayoutParams());

		f1.width = util.getScreenwidth();
		f1.height = 2;
		f1.setMargins(50, 0, 50, 0);
		f1.addRule(RelativeLayout.CENTER_HORIZONTAL);
		f1.addRule(RelativeLayout.BELOW, R.id.textView6);

		f2.width = util.getScreenwidth();
		f2.height = 2;
		f2.setMargins(50, 0, 50, 0);
		f2.addRule(RelativeLayout.CENTER_HORIZONTAL);
		f2.addRule(RelativeLayout.BELOW, R.id.textView8);

		f3.width = util.getScreenwidth();
		f3.height = 2;
		f3.setMargins(50, 0, 50, 0);
		f3.addRule(RelativeLayout.CENTER_HORIZONTAL);
		f3.addRule(RelativeLayout.BELOW, R.id.textView4);

		f4.width = util.getScreenwidth();
		f4.height = 2;
		f4.setMargins(50, 0, 50, 0);
		f4.addRule(RelativeLayout.CENTER_HORIZONTAL);
		f4.addRule(RelativeLayout.BELOW, R.id.textView10);

		f5.width = util.getScreenwidth();
		f5.height = 2;
		f5.setMargins(50, 0, 50, 0);
		f5.addRule(RelativeLayout.CENTER_HORIZONTAL);
		f5.addRule(RelativeLayout.BELOW, R.id.textView12);

		ImageView l11 = (ImageView) header.findViewById(R.id.i1);
		ImageView l22 = (ImageView) header.findViewById(R.id.i2);
		ImageView l33 = (ImageView) header.findViewById(R.id.i3);
		ImageView l44 = (ImageView) header.findViewById(R.id.i4);
		ImageView l55 = (ImageView) header.findViewById(R.id.i5);

		l11.setLayoutParams(f1);
		l22.setLayoutParams(f2);
		l33.setLayoutParams(f3);
		l44.setLayoutParams(f4);
		l55.setLayoutParams(f5);

		int marginTop = (util.getScreenHeight() / 3) - (util.getScreenwidth() / 8);

		FrameLayout profileFrame = (FrameLayout) header.findViewById(R.id.frameLayoutHeader);
		FrameLayout.LayoutParams profileParams = new FrameLayout.LayoutParams(profileFrame.getLayoutParams());

		profileParams.height = util.getScreenwidth() / 4;
		profileParams.width = util.getScreenwidth() / 4;
		profileParams.gravity = Gravity.CENTER_HORIZONTAL;

		// profileParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		// profileParams.addRule(RelativeLayout.BELOW, R.id.namePage);
		profileParams.setMargins(0, marginTop, 0, 0);

		// LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
		// imageLinear.getLayoutParams());
		// llp.width = (int) (util.getScreenwidth() / 4.5);
		// llp.height = (int) (util.getScreenwidth() / 4.5);

		img.setLayoutParams(profileParams);

		ImageView headerImageView = (ImageView) header.findViewById(R.id.imgvadvertise_Object);

		FrameLayout.LayoutParams headerparams = new FrameLayout.LayoutParams(profileFrame.getLayoutParams());

		headerparams.height = util.getScreenHeight() / 3;
		headerparams.width = util.getScreenwidth();
		headerparams.gravity = Gravity.CENTER_HORIZONTAL;

		headerImageView.setLayoutParams(headerparams);

		// RelativeLayout imageLinear = (RelativeLayout)
		// header.findViewById(R.id.sd);
		RelativeLayout l = (RelativeLayout) header.findViewById(R.id.reee);

		RelativeLayout.LayoutParams lkj = new RelativeLayout.LayoutParams(l.getLayoutParams());
		lkj.width = android.widget.LinearLayout.LayoutParams.WRAP_CONTENT;
		lkj.height = android.widget.LinearLayout.LayoutParams.WRAP_CONTENT;

		lkj.setMargins(0, 0, 50, 0);

		RelativeLayout.LayoutParams cd = new RelativeLayout.LayoutParams(l.getLayoutParams());
		cd.width = android.widget.LinearLayout.LayoutParams.WRAP_CONTENT;
		cd.height = android.widget.LinearLayout.LayoutParams.WRAP_CONTENT;

		cd.setMargins(50, 0, 0, 0);

		birthDayUsers.setLayoutParams(cd);
		btnedit.setLayoutParams(lkj);

	}

	private void setValue() {

		String ImagePath = currentUser.getImagePath();
		if (ImagePath != null) {
			Bitmap bmp = BitmapFactory.decodeFile(ImagePath);
			img.setBackgroundResource(R.drawable.circle_drawable);

			if (bmp != null)

				img.setImageBitmap(Utility.getclip(bmp));
		} else {
			img.setBackgroundResource(R.drawable.circle_drawable);
			img.setImageResource(R.drawable.no_img_profile);

		}

		String name = currentUser.getName();
		String email = currentUser.getEmail();
		String address = currentUser.getAddress();
		String phone = currentUser.getPhonenumber();
		String cellphone = currentUser.getMobailenumber();
		String fax = currentUser.getFaxnumber();
		String date = currentUser.getDate();

		txtname.setText(name);
		txtemail.setText(email);
		txtaddress.setText(address);
		txtphone.setText(phone);
		txtcellphone.setText(cellphone);
		txtfax.setText(fax);
		// txtdate.setText(util.getPersianDate(date));

	}

	private void onClick() {
		img.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String ImagePath = currentUser.getImagePath();
				String name = currentUser.getName();

				DialogShowImage showImage = new DialogShowImage(getActivity(), ImagePath, name);
				showImage.show();
			}
		});

		logout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// //////////////////////null get current user//////////////////

				SharedPreferences settings = getActivity().getSharedPreferences("User", 0);
				SharedPreferences.Editor editor = settings.edit();

				editor.putBoolean("isLogin", false);

				editor.commit();
				// ////////////////////////////////////////////////
				dbAdapter.open();
				int ad = 0;
				dbAdapter.UpdateAdminAllUser(ad);
				dbAdapter.close();

				FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new LoginFragment());
				trans.commit();
				TextView txtlike = (TextView) (getActivity()).findViewById(R.id.txtlike);
				txtlike.setVisibility(View.GONE);
				TextView txtcm1 = (TextView) (getActivity()).findViewById(R.id.txtcm);
				txtcm1.setVisibility(View.GONE);

			}

		});

		btnedit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new EditPersonalFragment());
				trans.addToBackStack(null);

				trans.commit();

			}
		});

		final SharedPreferences tashkhis = getActivity().getSharedPreferences("Id", 0);

		birthDayUsers.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				DomainSend fragment = new DomainSend("BirthDay");

				FragmentTransaction trans = ((MainActivity) getActivity()).getSupportFragmentManager()
						.beginTransaction();

				trans.replace(R.id.content_frame, fragment);
				trans.addToBackStack(null);
				trans.commit();

				tashkhis.edit().putString("enter", "DisplayPersonal").commit();
				tashkhis.edit().putString("FromTableName", "BirthDay").commit();

			}
		});

	}

	private void setFont() {

		txtEdit.setTypeface(util.SetFontCasablanca());
		txtBirthday.setTypeface(util.SetFontCasablanca());

		txtaddress.setTypeface(util.SetFontIranSans());
		// txtcellphone.setTypeface(util.SetFontCasablanca());
		// txtphone.setTypeface(util.SetFontCasablanca());
		// txtemail.setTypeface(util.SetFontCasablanca());
		txtname.setTypeface(util.SetFontCasablanca());
		// txtfax.setTypeface(util.SetFontCasablanca());

	}

	private void getAllDataFromServer() {

		if (getActivity() != null) {

			switch (selectTableId) {

			case 1: {
				tableName = "Object";

				updating = new UpdatingPersonalPage(getActivity());
				updating.delegate = DisplayPersonalInformationFragment.this;
				String[] params = new String[5];
				params[0] = tableName;
				params[1] = currentUser.getDate();
				params[2] = serverDate;

				params[3] = "0";
				params[4] = String.valueOf(currentUser.getId());

				updating.execute(params);

				break;
			}
			case 2: {
				tableName = "Anad";

				updating = new UpdatingPersonalPage(getActivity());
				updating.delegate = DisplayPersonalInformationFragment.this;
				String[] params = new String[5];
				params[0] = tableName;
				params[1] = currentUser.getDate();
				params[2] = serverDate;

				params[3] = "0";
				params[4] = String.valueOf(currentUser.getId());

				updating.execute(params);
				break;
			}
			case 3: {
				tableName = "Paper";

				updating = new UpdatingPersonalPage(getActivity());
				updating.delegate = DisplayPersonalInformationFragment.this;
				String[] params = new String[5];
				params[0] = tableName;
				params[1] = currentUser.getDate();
				params[2] = serverDate;

				params[3] = "0";
				params[4] = String.valueOf(currentUser.getId());

				updating.execute(params);
				break;
			}
			case 4: {
				tableName = "Froum";

				updating = new UpdatingPersonalPage(getActivity());
				updating.delegate = DisplayPersonalInformationFragment.this;
				String[] params = new String[5];
				params[0] = tableName;
				params[1] = currentUser.getDate();
				params[2] = serverDate;

				params[3] = "0";
				params[4] = String.valueOf(currentUser.getId());

				updating.execute(params);
				break;
			}
			case 5: {
				tableName = "Ticket";

				updating = new UpdatingPersonalPage(getActivity());
				updating.delegate = DisplayPersonalInformationFragment.this;
				String[] params = new String[5];
				params[0] = tableName;
				params[1] = currentUser.getDate();
				params[2] = serverDate;

				params[3] = "0";
				params[4] = String.valueOf(currentUser.getId());

				updating.execute(params);
				break;
			}
			case 6: {
				tableName = "LikeInObject";

				updating = new UpdatingPersonalPage(getActivity());
				updating.delegate = DisplayPersonalInformationFragment.this;
				String[] params = new String[5];
				params[0] = tableName;
				params[1] = currentUser.getDate();
				params[2] = serverDate;

				params[3] = "0";
				params[4] = String.valueOf(currentUser.getId());

				updating.execute(params);
				break;
			}

			case 7: {
				getObjectFollow();
				break;
			}

			}

			isFirstRun = false;
		}

	}

	@Override
	public void processFinish(String output) {
		// if (selectTableId < 8) {

		// if (ringProgressDialog != null)
		// ringProgressDialog.dismiss();

		if (util.checkError(output) == false) {

			// if (ringProgressDialog != null)
			// ringProgressDialog.dismiss();

			if (isFirstRun == true) {

				serverDate = output;

				getAllDataFromServer();

			}
		}

		// }

	}

	private void RunServiceDate() {

		if (getActivity() != null) {

			// ringProgressDialog = ProgressDialog.show(getActivity(), "", "لطفا
			// منتظر بمانید...", true);
			// ringProgressDialog.setCancelable(true);

			date = new ServerDate(getActivity());
			date.delegate = DisplayPersonalInformationFragment.this;
			date.execute("");

			isFirstRun = true;

		}
	}

	private void getImageObject() {

		if (counterMyObject < ObejctData.size()) {

			pdObject = ObejctData.get(counterMyObject);
			objectIdPage = pdObject.getObjectId();

			String objectImageDate = pdObject.getImage2ServerDateObject();

			if (objectImageDate == null)
				objectImageDate = "";

			if (getActivity() != null) {

				UpdatingImage ImageUpdating = new UpdatingImage(getActivity());
				ImageUpdating.delegate = DisplayPersonalInformationFragment.this;
				maps = new LinkedHashMap<String, String>();
				maps.put("tableName", "Object2");
				maps.put("Id", String.valueOf(objectIdPage));
				maps.put("fromDate", objectImageDate);
				ImageUpdating.execute(maps);
			}

		} else {

			f1 = false;
			f2 = true;
			getImageFollowed();

		}
	}

	private void getImageFollowed() {

		if (counterFollowObject < FollowedPageLsit.size()) {

			pdObjectFollowed = FollowedPageLsit.get(counterFollowObject);
			objectIdFollowed = pdObjectFollowed.getObjectFollowId();

			String objectImageDate = pdObjectFollowed.getImage2ServerDateObject();

			if (objectImageDate == null)
				objectImageDate = "";

			if (getActivity() != null) {

				UpdatingImage ImageUpdating = new UpdatingImage(getActivity());
				ImageUpdating.delegate = DisplayPersonalInformationFragment.this;
				maps = new LinkedHashMap<String, String>();
				maps.put("tableName", "Object2");
				maps.put("Id", String.valueOf(objectIdFollowed));
				maps.put("fromDate", objectImageDate);
				ImageUpdating.execute(maps);
			}

		} else {
			f2 = false;
			f3 = true;
			getTicketImage();

		}

	}

	private void getTicketImage() {

		if (getActivity() != null) {

			if (counterTicket < TicketData.size()) {

				pdTicket = TicketData.get(counterTicket);
				ticketIdData = pdTicket.getTicketId();
				String objectImageDate = pdTicket.getImageServerDateTicket();

				if (objectImageDate == null)
					objectImageDate = "";

				UpdatingImage update = new UpdatingImage(getActivity());
				update.delegate = DisplayPersonalInformationFragment.this;
				maps = new LinkedHashMap<String, String>();
				maps.put("tableName", "Ticket");
				maps.put("Id", String.valueOf(ticketIdData));
				maps.put("fromDate", objectImageDate);
				update.execute(maps);

			} else {
				f3 = false;
				f4 = true;
				getAnadImage();

			}
		}

	}

	private void getAnadImage() {

		if (getActivity() != null) {
			if (counterAnad < AnadData.size()) {

				pdAnad = AnadData.get(counterAnad);
				anadIdData = pdAnad.getAnadId();

				String objectImageDate = pdAnad.getImageServerDateAnad();

				if (objectImageDate == null)
					objectImageDate = "";

				UpdatingImage update = new UpdatingImage(getActivity());
				update.delegate = DisplayPersonalInformationFragment.this;
				maps = new LinkedHashMap<String, String>();
				maps.put("tableName", "Anad");
				maps.put("Id", String.valueOf(anadIdData));
				maps.put("fromDate", objectImageDate);
				update.execute(maps);
			} else {

				// if (ringProgressDialog != null)
				// ringProgressDialog.dismiss();

				Toast.makeText(getActivity(), " به روز رسانی اطلاعات با موفقیت انجام شد", 0).show();

				f1 = false;
				f2 = false;
				f3 = false;
				f4 = false;

				counterMyObject = 0;
				counterFollowObject = 0;
				counterTicket = 0;
				counterAnad = 0;
				f1 = true;
				getDateObject();

			}
		}

	}

	private void getDateObject() {

		if (counterMyObject < ObejctData.size()) {

			PersonalData pd = ObejctData.get(counterMyObject);
			objectIdPage = pd.getObjectId();

			if (getActivity() != null) {

				ServiceComm getDateService = new ServiceComm(getActivity());
				getDateService.delegate = DisplayPersonalInformationFragment.this;
				Map<String, String> items = new LinkedHashMap<String, String>();
				items.put("tableName", "getObject2ImageDate");
				items.put("Id", String.valueOf(objectIdPage));
				getDateService.execute(items);
			}
		} else {
			f1 = false;
			f2 = true;
			getDateFollow();

		}
	}

	private void getDateFollow() {

		if (counterFollowObject < FollowedPageLsit.size()) {

			PersonalData pd = FollowedPageLsit.get(counterFollowObject);
			objectIdFollowed = pd.getObjectFollowId();

			if (getActivity() != null) {
				ServiceComm getDateService = new ServiceComm(getActivity());

				getDateService.delegate = DisplayPersonalInformationFragment.this;
				Map<String, String> items = new LinkedHashMap<String, String>();
				items.put("tableName", "getObject2ImageDate");
				items.put("Id", String.valueOf(objectIdFollowed));
				getDateService.execute(items);
			}
		} else {
			f2 = false;
			f3 = true;
			getDateTicket();

		}
	}

	private void getDateTicket() {
		if (counterTicket < TicketData.size()) {

			PersonalData pd = TicketData.get(counterTicket);
			ticketIdData = pd.getTicketId();

			if (getActivity() != null) {

				ServiceComm getDateService = new ServiceComm(getActivity());

				getDateService.delegate = DisplayPersonalInformationFragment.this;
				Map<String, String> items = new LinkedHashMap<String, String>();
				items.put("tableName", "getTicketImageDate");
				items.put("Id", String.valueOf(ticketIdData));
				getDateService.execute(items);
			}
		} else {
			f3 = false;
			f4 = true;
			getDateAnad();
		}

	}

	private void getDateAnad() {

		if (counterAnad < AnadData.size()) {

			PersonalData pd = AnadData.get(counterAnad);
			anadIdData = pd.getAnadId();

			if (getActivity() != null) {

				ServiceComm getDateService = new ServiceComm(getActivity());

				getDateService.delegate = DisplayPersonalInformationFragment.this;
				Map<String, String> items = new LinkedHashMap<String, String>();
				items.put("tableName", "getAnadImageDate");
				items.put("Id", String.valueOf(anadIdData));
				getDateService.execute(items);

			}
		} else {

		}

	}

	@Override
	public void processFinish(byte[] output) {
		if (f1 == true) {
			String ImagePathObject = "";

			if (output != null) {

				ImagePathObject = util.CreateFile(output, objectIdPage, "Mechanical", "Profile", "profile", "Object");
				if (!ImagePathObject.equals(""))
					pdObject.setImagePathObject(ImagePathObject);
				listAdapter.notifyDataSetChanged();

			}
			counterMyObject++;
			getImageObject();

		} // end f1
		else if (f2 == true) {
			String ImagePathObjectFollowed = "";

			if (output != null) {

				ImagePathObjectFollowed = util.CreateFile(output, objectIdFollowed, "Mechanical", "Profile", "profile",
						"Object");

				if (!ImagePathObjectFollowed.equals(""))
					pdObjectFollowed.setImagePathObjectFollow(ImagePathObjectFollowed);
				listAdapter.notifyDataSetChanged();
			}
			counterFollowObject++;
			getImageFollowed();
		} // end f2
		else {
			if (f3 == true) {
				String ImagePathTicket = "";

				if (output != null) {

					boolean IsEmptyByte = util.IsEmptyByteArrayImage(output);
					if (IsEmptyByte == false) {
						ImagePathTicket = util.CreateFile(output, ticketIdData, "Mechanical", "Ticket", "ticket",
								"Ticket");

						if (!ImagePathTicket.equals(""))
							pdTicket.setImagePathTicket(ImagePathTicket);
						listAdapter.notifyDataSetChanged();

					}

				}
				counterTicket++;
				getTicketImage();
			} // end f3
			else if (f4 == true) {

				String imagePathAnad = "";

				if (output != null) {

					imagePathAnad = util.CreateFile(output, anadIdData, "Mechanical", "Anad", "anad", "Anad");

					if (!imagePathAnad.equals(""))
						pdAnad.setImagePathAnad(imagePathAnad);
					listAdapter.notifyDataSetChanged();

				}
				counterAnad++;
				getAnadImage();

			}

		}

	}

	@Override
	public void CommProcessFinish(String output) {
		if (f1 == true) {

			if (output.contains("Exception") || output.contains("anyType"))
				output = "";

			dbAdapter.open();
			dbAdapter.updateObjectImage2ServerDate(objectIdPage, output);
			dbAdapter.close();

			counterMyObject++;
			getDateObject();
		} // end f1
		else {

			if (f2 == true) {

				if (output.contains("Exception") || output.contains("anyType"))
					output = "";

				dbAdapter.open();
				dbAdapter.updateObjectImage2ServerDate(objectIdFollowed, output);
				dbAdapter.close();

				counterFollowObject++;
				getDateFollow();
				;

			} // end f2
			else {
				if (f3 == true) {

					if (output.contains("Exception") || output.contains("anyType"))
						output = "";

					dbAdapter.open();
					dbAdapter.UpdateImageServerDate(ticketIdData, "Ticket", output);
					dbAdapter.close();

					counterTicket++;
					getDateTicket();
				} else {
					if (f4 == true) {

						if (output.contains("Exception") || output.contains("anyType"))
							output = "";

						dbAdapter.open();
						dbAdapter.UpdateImageServerDate(anadIdData, "Anad", output);
						dbAdapter.close();

						counterTicket++;
						getDateTicket();
					}
				}

			}
		}
		listAdapter.notifyDataSetChanged();

	}

	@Override
	public void ResultServer(String output) {

		if (util.checkError(output) == false) {

			util.parseQuery(output);

			// ringProgressDialog = ProgressDialog.show(getActivity(), "", "
			// به روز رسانی تصاویر ...", true);
			// ringProgressDialog.setCancelable(true);
			// Toast.makeText(getActivity(), "start", 0).show();

			listAdapter.notifyDataSetChanged();
			// FillExpandListView();

			selectTableId++;
			getAllDataFromServer();

		}

	}

	private void getObjectFollow() {

		if (getActivity() != null) {

			GetAllObjectLiked getDateService = new GetAllObjectLiked(getActivity());
			getDateService.delegate = DisplayPersonalInformationFragment.this;
			Map<String, String> items = new LinkedHashMap<String, String>();

			items.put("tableName", "getAllObjectForUserId");
			items.put("userId", String.valueOf(currentUser.getId()));
			items.put("fromDate", currentUser.getDate());
			items.put("endDate", serverDate);
			items.put("isRefresh", "0");

			getDateService.execute(items);

		}

	}

	public void fillListDataChild() {

		dbAdapter.open();

		ObejctData.clear();
		FroumData.clear();
		PaperData.clear();
		TicketData.clear();
		AnadData.clear();
		FollowedPageLsit.clear();

		ObejctData = dbAdapter.CustomFieldObjectByUser(currentUser.getId());
		FroumData = dbAdapter.CustomFieldFroumByUser(currentUser.getId());
		PaperData = dbAdapter.CustomFieldPaperByUser(currentUser.getId());
		TicketData = dbAdapter.CustomFieldTicketByUser(currentUser.getId());
		AnadData = dbAdapter.CustomFieldAnadByUser(currentUser.getId());

		List<LikeInObject> likePages = dbAdapter.getAllPageFollowingMe(util.getCurrentUser().getId(),
				StaticValues.TypeLikePage);

		myFollowingPages.clear();

		if (likePages.size() > 0)
			for (int i = 0; i < likePages.size(); i++) {

				Object o = dbAdapter.getObjectbyid(likePages.get(i).getPaperId());
				if (o != null)
					myFollowingPages.add(o);
			}
		FollowedPageLsit = dbAdapter.CustomFieldObjectFollowByUser(myFollowingPages);

		dbAdapter.close();

		sizeTypeList.add(ObejctData.size());
		sizeTypeList.add(FollowedPageLsit.size());
		sizeTypeList.add(TicketData.size());
		sizeTypeList.add(PaperData.size());
		sizeTypeList.add(FroumData.size());
		sizeTypeList.add(AnadData.size());

		// Expandview = (ExpandableListView)
		// rootView.findViewById(R.id.items);

		// Expandview.setDividerHeight(20);

		// Drawable d =
		// getResources().getDrawable(R.drawable.indicator_expandable);
		// Expandview.setIndicatorBounds(345,375);

		// Expandview.setGroupIndicator(d);

		Expandview.setGroupIndicator(null);
		Expandview.setClickable(true);

		parentItems.clear();

		parentItems.add("مدیریت صفحات");
		parentItems.add("مدیریت صفحات دنبال شده");
		parentItems.add("مدیریت آگهی ها");
		parentItems.add("مدیریت مقالات");
		parentItems.add("مدیریت تالار گفتگو");
		parentItems.add("مدیریت تبلیغات");

		List<PersonalData> emptyItem = new ArrayList<PersonalData>();

		PersonalData prd = new PersonalData();
		listDataChild.clear();

		if (ObejctData.size() == 0) {

			prd.setDateTicket("آیتمی اضافه نشده است");
			emptyItem.clear();
			emptyItem.add(prd);

			listDataChild.put(parentItems.get(0), emptyItem);

		} else {
			listDataChild.put(parentItems.get(0), ObejctData);

		}

		if (FollowedPageLsit.size() == 0) {

			prd.setDateTicket("آیتمی اضافه نشده است");
			emptyItem.clear();
			emptyItem.add(prd);

			listDataChild.put(parentItems.get(1), emptyItem);
		} else {

			listDataChild.put(parentItems.get(1), FollowedPageLsit);

		}

		if (TicketData.size() == 0) {
			prd.setDateTicket("آیتمی اضافه نشده است");
			emptyItem.clear();
			emptyItem.add(prd);

			listDataChild.put(parentItems.get(2), emptyItem);
		} else {
			listDataChild.put(parentItems.get(2), TicketData);

		}

		if (PaperData.size() == 0) {
			prd.setDateTicket("آیتمی اضافه نشده است");
			emptyItem.clear();
			emptyItem.add(prd);

			listDataChild.put(parentItems.get(3), emptyItem);
		} else {
			listDataChild.put(parentItems.get(3), PaperData);
		}
		if (FroumData.size() == 0) {
			prd.setDateTicket("آیتمی اضافه نشده است");
			emptyItem.clear();
			emptyItem.add(prd);

			listDataChild.put(parentItems.get(4), emptyItem);

		} else {

			listDataChild.put(parentItems.get(4), FroumData);
		}
		if (AnadData.size() == 0) {
			prd.setDateTicket("آیتمی اضافه نشده است");
			emptyItem.clear();
			emptyItem.add(prd);

			listDataChild.put(parentItems.get(5), emptyItem);

		} else {

			listDataChild.put(parentItems.get(5), AnadData);
		}

	}

	@Override
	public void ResultObjectLiked(String output) {

		if (util.checkError(output) == false) {

			util.parseQuery(output);

			listAdapter.notifyDataSetChanged();

			f1 = true;
			getImageObject();
		}

	}
}
