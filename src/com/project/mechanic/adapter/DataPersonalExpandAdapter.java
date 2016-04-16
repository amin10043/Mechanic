package com.project.mechanic.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.StaticValues;
import com.project.mechanic.entity.Anad;
import com.project.mechanic.entity.Froum;
import com.project.mechanic.entity.Paper;
import com.project.mechanic.entity.PersonalData;
import com.project.mechanic.entity.Province;
import com.project.mechanic.entity.Ticket;
import com.project.mechanic.entity.TicketType;
import com.project.mechanic.entity.Users;
import com.project.mechanic.fragment.DialogAreYouSure;
import com.project.mechanic.fragment.DialogEditAnad;
import com.project.mechanic.fragment.DisplayPersonalInformationFragment;
import com.project.mechanic.fragment.FroumFragment;
import com.project.mechanic.fragment.IntroductionEditFragment;
import com.project.mechanic.fragment.IntroductionFragment;
import com.project.mechanic.fragment.PaperFragment;
import com.project.mechanic.fragment.ShowAdFragment;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DataPersonalExpandAdapter extends BaseExpandableListAdapter {
	Context context;
	ArrayList<String> parentItems;
	HashMap<String, List<PersonalData>> listDataChild;
	Utility util;
	String todayDate;
	DataBaseAdapter adapter;
	DisplayPersonalInformationFragment fr;
	// int countPage, countTicket, countPaper, countFroum;
	List<Integer> sizeTypeItem;
	boolean isShowSettingBtn;
	String name;

	static String sendLable = "ارسال پیام";
	static String editLable = "ویرایش";
	static String deleteLable = "حذف";
	static String copyLikkLable = "کپی لینک";
	static String tamdidLAble = "تمدید اعتبار";
	static String copyLable = "کپی";

	static String pageLable = "صفحه";
	static String PageFolloedLable = "دنبال شده ها";
	static String ticketlable = "آگهی";
	static String paperLable = "مقالات";
	static String FroumLable = "گفتگو";

	private int lastExpandedPosition = -1;

	public DataPersonalExpandAdapter(Context context, ArrayList<String> parentItems,
			HashMap<String, List<PersonalData>> listDataChild, String todayDate, DisplayPersonalInformationFragment fr,
			List<Integer> sizeType, boolean isShowSettingBtn, String name) {

		this.context = context;
		this.parentItems = parentItems;
		this.listDataChild = listDataChild;
		util = new Utility(context);
		this.todayDate = todayDate;
		adapter = new DataBaseAdapter(context);
		this.fr = fr;
		// this.countPage = countPage;
		// this.countTicket = countTicket;
		// this.countPaper = countPaper;
		// this.countFroum = countFroum;
		this.sizeTypeItem = sizeType;
		this.isShowSettingBtn = isShowSettingBtn;
		this.name = name;

	}

	@Override
	public int getChildTypeCount() {
		return 2;// super.getChildTypeCount();
	}

	@Override
	public int getGroupType(int groupPosition) {
		// TODO Auto-generated method stub
		return 1;// super.getGroupType(groupPosition);
	}

	@Override
	public int getGroupTypeCount() {

		return 1;// super.getGroupTypeCount();
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return listDataChild.get(parentItems.get(groupPosition)).get(childPosition);
	}

	@Override
	public int getChildType(int groupPosition, int childPosition) {

		return 1;// type;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@SuppressWarnings("deprecation")
	@Override
	public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView,
			ViewGroup parent) {

		// if (convertView == null) {
		LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (("مدیریت صفحات").equals(parentItems.get(groupPosition))) {

			if (sizeTypeItem.get(groupPosition) == 0) {

				convertView = infalInflater.inflate(R.layout.row_search, null);

				TextView txt = (TextView) convertView.findViewById(R.id.row_search_name);

				final PersonalData pd = (PersonalData) getChild(groupPosition, childPosition);

				// //////////

				txt.setText(pd.getDateTicket());
				txt.setTypeface(util.SetFontCasablanca());

			} else {

				convertView = infalInflater.inflate(R.layout.row_personal_object, null);

				// update your views here

				TextView namePage = (TextView) convertView.findViewById(R.id.Rowobjecttxt);

				ImageView profileIco = (ImageView) convertView.findViewById(R.id.icon_object);

				ImageView report = (ImageView) convertView.findViewById(R.id.reportImage);
				final PersonalData pd = (PersonalData) getChild(groupPosition, childPosition);

				report.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						List<String> items = new ArrayList<String>();

						items.clear();
						items.add(editLable);
						items.add(tamdidLAble);
						items.add(copyLikkLable);
						items.add(deleteLable);

						PopupMenu popupMenu = util.ShowPopupMenu(items, v);
						popupMenu.show();

						popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {

							@Override
							public boolean onMenuItemClick(MenuItem menuItem) {

								String nameItem = (String) menuItem.getTitle();

								if (nameItem.equals(editLable)) {

									SharedPreferences sendDataID = context.getSharedPreferences("Id", 0);
									sendDataID.edit().putInt("main_Id", pd.getObjectId()).commit();

									IntroductionEditFragment fragment = new IntroductionEditFragment(pd.getObjectId());
									FragmentTransaction trans = ((MainActivity) context).getSupportFragmentManager()
											.beginTransaction();
									trans.replace(R.id.content_frame, fragment);

									trans.addToBackStack(null);
									trans.commit();

								}
								if (nameItem.equals(tamdidLAble)) {

									Toast.makeText(context, tamdidLAble, 0).show();
								}
								if (nameItem.equals(deleteLable)) {
									Toast.makeText(context, deleteLable, 0).show();
								}
								if (nameItem.equals(copyLikkLable)) {
									Toast.makeText(context, copyLikkLable, 0).show();
								}

								return false;
							}
						});

					}
				});

				FrameLayout rl = (FrameLayout) convertView.findViewById(R.id.imageFrame);
				FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(rl.getLayoutParams());

				lp.width = (int) (util.getScreenwidth() / StaticValues.RateImageDisplayPersonalAdapter);
				lp.height = (int) (util.getScreenwidth() / StaticValues.RateImageDisplayPersonalAdapter);
				lp.setMargins(5, 10, 10, 10);

				profileIco.setScaleType(ScaleType.FIT_XY);

				profileIco.setLayoutParams(lp);

				String ImagePath = pd.getImagePathObject();

				if (ImagePath != null) {
					Bitmap bitmap = BitmapFactory.decodeFile(ImagePath);

					profileIco.setImageBitmap(Utility.getclip(bitmap));
				}

				namePage.setText(pd.getNameObject());
				namePage.setTypeface(util.SetFontIranSans());

				TextView lable1 = (TextView) convertView.findViewById(R.id.lable_etebar);
				TextView lable2 = (TextView) convertView.findViewById(R.id.lable2);

				TextView baghiMandeh = (TextView) convertView.findViewById(R.id.dayBaghiMandeh); // modate

				lable1.setTypeface(util.SetFontCasablanca());
				lable2.setTypeface(util.SetFontCasablanca());
				baghiMandeh.setTypeface(util.SetFontCasablanca());

				String commitDate = pd.getDateObject(); // tarikhe ijad safhe

				if (commitDate != null && !"".equals(commitDate)) {
					final SharedPreferences currentTime = context.getSharedPreferences("time", 0);

					String time = currentTime.getString("time", "111111111111111111");

					int diff = util.differentTwoDate(commitDate, time);

					baghiMandeh.setText(diff + "");

					ImageView imgBi = (ImageView) convertView.findViewById(R.id.aks_bi_etebar);

					if (diff <= 0) {
						imgBi.setVisibility(View.VISIBLE);
						imgBi.setLayoutParams(lp);
					}

				} else {
					baghiMandeh.setText("نا معلوم");
				}
				TextView follwers = (TextView) convertView.findViewById(R.id.followers);

				TextView views = (TextView) convertView.findViewById(R.id.views);
				adapter.open();

				com.project.mechanic.entity.Object obj = adapter.getObjectbyid(pd.getObjectId());

				views.setText(obj.getCountView() + "");

				int followersCount = adapter.LikeInObject_count(pd.getObjectId(), 0);

				follwers.setText(followersCount + "");
				adapter.close();

				int rate = obj.getRate();

				if (rate > 0) {

					ImageView star1 = (ImageView) convertView.findViewById(R.id.star1);
					ImageView star2 = (ImageView) convertView.findViewById(R.id.star2);
					ImageView star3 = (ImageView) convertView.findViewById(R.id.star3);
					ImageView star4 = (ImageView) convertView.findViewById(R.id.star4);
					ImageView star5 = (ImageView) convertView.findViewById(R.id.star5);

					switch (rate) {
					case 1:
						star1.setBackgroundResource(R.drawable.ic_star_on);

						break;
					case 2:
						star1.setBackgroundResource(R.drawable.ic_star_on);
						star2.setBackgroundResource(R.drawable.ic_star_on);

						break;

					case 3:
						star1.setBackgroundResource(R.drawable.ic_star_on);
						star2.setBackgroundResource(R.drawable.ic_star_on);
						star3.setBackgroundResource(R.drawable.ic_star_on);

						break;

					case 4:
						star1.setBackgroundResource(R.drawable.ic_star_on);
						star2.setBackgroundResource(R.drawable.ic_star_on);
						star3.setBackgroundResource(R.drawable.ic_star_on);
						star4.setBackgroundResource(R.drawable.ic_star_on);

						break;

					case 5:
						star1.setBackgroundResource(R.drawable.ic_star_on);
						star2.setBackgroundResource(R.drawable.ic_star_on);
						star3.setBackgroundResource(R.drawable.ic_star_on);
						star4.setBackgroundResource(R.drawable.ic_star_on);
						star5.setBackgroundResource(R.drawable.ic_star_on);

						break;

					default:
						break;
					}

				}

				convertView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {

						IntroductionFragment fragment = new IntroductionFragment(-1);
						FragmentTransaction trans = ((MainActivity) context).getSupportFragmentManager()
								.beginTransaction();
						trans.replace(R.id.content_frame, fragment);
						Bundle bundle = new Bundle();
						bundle.putString("Id", String.valueOf(pd.getObjectId()));
						fragment.setArguments(bundle);
						trans.addToBackStack(null);
						trans.commit();

					}
				});

			}
		} else if (("مدیریت آگهی ها").equals(parentItems.get(groupPosition))) {

			if (sizeTypeItem.get(groupPosition) == 0) {

				convertView = infalInflater.inflate(R.layout.row_search, null);

				TextView txt = (TextView) convertView.findViewById(R.id.row_search_name);

				final PersonalData pd = (PersonalData) getChild(groupPosition, childPosition);

				// //////////

				txt.setText(pd.getDateTicket());
				txt.setTypeface(util.SetFontCasablanca());

			} else {
				convertView = infalInflater.inflate(R.layout.row_anad_personal, null);

				TextView txtdate = (TextView) convertView.findViewById(R.id.text_favorite_desc);
				TextView txtName = (TextView) convertView.findViewById(R.id.row_favorite_title);
				// TextView txtDesc = (TextView)
				// convertView.findViewById(R.id.row_anad_txt2);
				ImageView img2 = (ImageView) convertView.findViewById(R.id.row_favorite_img);
				TextView address = (TextView) convertView.findViewById(R.id.address);

				// ProgressBar LoadingProgress = (ProgressBar)
				// convertView.findViewById(R.id.progressBar1);
				// LoadingProgress.setVisibility(View.GONE);

				final PersonalData pd = (PersonalData) getChild(groupPosition, childPosition);

				txtdate.setText(util.getPersianDate(pd.getDateTicket()));
				txtName.setText(pd.getNameTicket());
				// txtDesc.setText(pd.getDescriptonTicket());

				if (pd.getSeenBefore() > 0) {
					txtName.setTextColor(Color.GRAY);
					// txtDesc.setTextColor(Color.GRAY);
					txtdate.setTextColor(Color.GRAY);

				}
				if ("".equals(pd.getNameTicket()) || pd.getNameTicket() == null) {
					txtName.setVisibility(View.GONE);

				}
				if ("".equals(pd.getDescriptonTicket()) || pd.getDescriptonTicket() == null) {
					// txtDesc.setVisibility(View.GONE);
				}
				ImageView imgBi = (ImageView) convertView.findViewById(R.id.aks_bi_etebar);

				FrameLayout llkj = (FrameLayout) convertView.findViewById(R.id.imageFrame);

				FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(llkj.getLayoutParams());

				params.width = (int) (util.getScreenwidth() / StaticValues.RateImageDisplayPersonalAdapter);
				params.height = (int) (util.getScreenwidth() / StaticValues.RateImageDisplayPersonalAdapter);
				// params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
				// params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
				// params.addRule(RelativeLayout.CENTER_VERTICAL);
				params.setMargins(5, 10, 10, 10);

				adapter.open();
				Ticket t = adapter.getTicketById(pd.getTicketId());
				Province p = adapter.getProvinceById(t.getProvinceId());
				String provinceName = p.getName();

				List<TicketType> tt = adapter.getAllTicketType();

				TicketType titype = tt.get(t.getTypeId() - 1);
				String typeNameTicket = titype.getDesc();

				address.setText(typeNameTicket + " : " + provinceName);

				address.setTypeface(util.SetFontCasablanca());
				adapter.close();

				String pathProfile = pd.getImagePathTicket();
				Bitmap profileImage = BitmapFactory.decodeFile(pathProfile);

				if (profileImage != null) {

					img2.setImageBitmap(Utility.getclip(profileImage));
					img2.setLayoutParams(params);

				} else {
					// img2.setImageResource(R.drawable.no_img_profile);
					img2.setLayoutParams(params);
				}

				String commitDate = pd.getDateTicket();
				int thisDay = 0;
				int TicketDay = Integer.valueOf(commitDate.substring(0, 8));
				if (todayDate != null && !todayDate.equals(""))
					thisDay = Integer.valueOf(todayDate.substring(0, 8));
				RelativeLayout TicketBackground = (RelativeLayout) convertView.findViewById(R.id.backgroundTicket);

				if (thisDay <= TicketDay + pd.getDayTicket()) {
					TicketBackground.setBackgroundColor(Color.WHITE);
					if (pd.getSeenBefore() > 0) {
						txtName.setTextColor(Color.GRAY);
						// txtDesc.setTextColor(Color.GRAY);
						txtdate.setTextColor(Color.GRAY);
					}

				} else {
					imgBi.setVisibility(View.VISIBLE);
					imgBi.setLayoutParams(params);

					// img2.setImageResource(R.drawable.bi_etebar);
					// TicketBackground.setBackgroundResource(R.color.lightred);

					if (pd.getSeenBefore() > 0) {
						txtName.setTextColor(Color.WHITE);
						// txtDesc.setTextColor(Color.WHITE);
						txtdate.setTextColor(Color.WHITE);

					}
				}

				txtName.setTypeface(util.SetFontCasablanca());
				// txtDesc.setTypeface(util.SetFontCasablanca());

				ImageView reaport = (ImageView) convertView.findViewById(R.id.reportImage);
				if (isShowSettingBtn == true) {
					reaport.setVisibility(View.VISIBLE);
				} else
					reaport.setVisibility(View.GONE);

				TextView views = (TextView) convertView.findViewById(R.id.views);
				adapter.open();
				Ticket tic = adapter.getTicketById(pd.getTicketId());

				views.setText(tic.getCountView() + "");
				adapter.close();

				TextView lable1 = (TextView) convertView.findViewById(R.id.address);

				lable1.setTypeface(util.SetFontCasablanca());

				convertView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						FragmentTransaction trans = ((MainActivity) context).getSupportFragmentManager()
								.beginTransaction();
						ShowAdFragment fragment = new ShowAdFragment();
						Bundle bundle = new Bundle();
						bundle.putString("Id", String.valueOf(pd.getTicketId()));
						fragment.setArguments(bundle);
						trans.replace(R.id.content_frame, fragment);
						trans.addToBackStack(null);
						trans.commit();

						adapter.open();
						adapter.SetSeen("Ticket", pd.getTicketId(), "1");
						adapter.close();
						// }
					}
				});

				reaport.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						List<String> items = new ArrayList<String>();

						items.clear();
						items.add(tamdidLAble);
						items.add(copyLikkLable);
						items.add(deleteLable);

						PopupMenu popmenu = util.ShowPopupMenu(items, v);
						popmenu.show();

						popmenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {

							@Override
							public boolean onMenuItemClick(MenuItem menuItem) {

								String nameItem = (String) menuItem.getTitle();

								if (nameItem.equals(tamdidLAble)) {

									Toast.makeText(context, tamdidLAble, 0).show();
								}
								if (nameItem.equals(deleteLable)) {

									DialogAreYouSure dia = new DialogAreYouSure(context, ticketlable, pd.getTicketId(),
											fr);

									util.setSizeDialog(dia);

									Toast.makeText(context, deleteLable, 0).show();
								}
								if (nameItem.equals(copyLikkLable)) {
									Toast.makeText(context, copyLikkLable, 0).show();
								}

								return false;
							}
						});

						// DialogLongClick dia = new DialogLongClick(context, 3,
						// u,
						// id, fr, t);
						// Toast.makeText(context, id + "", 0).show();
						//
						// WindowManager.LayoutParams lp = new
						// WindowManager.LayoutParams();
						// lp.copyFrom(dia.getWindow().getAttributes());
						// lp.width = (int) (util.getScreenwidth() / 1.5);
						// lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
						// ;
						// dia.show();
						//
						// dia.getWindow().setAttributes(lp);
						// dia.getWindow().setBackgroundDrawable(
						// new ColorDrawable(
						// android.graphics.Color.TRANSPARENT));
					}
				});

				// update your views here
			}
		} else if (("مدیریت مقالات").equals(parentItems.get(groupPosition))) {

			if (sizeTypeItem.get(groupPosition) == 0) {

				convertView = infalInflater.inflate(R.layout.row_search, null);

				TextView txt = (TextView) convertView.findViewById(R.id.row_search_name);

				final PersonalData pd = (PersonalData) getChild(groupPosition, childPosition);

				// //////////

				txt.setText(pd.getDateTicket());
				txt.setTypeface(util.SetFontCasablanca());

			} else {
				convertView = infalInflater.inflate(R.layout.row_personal_froum, null);

				// update your views here

				final TextView txt1 = (TextView) convertView.findViewById(R.id.rowtitlepaper);
				TextView txt2 = (TextView) convertView.findViewById(R.id.rowdescriptionpaper);
				TextView txt3 = (TextView) convertView.findViewById(R.id.authorname);

				TextView DateView = (TextView) convertView.findViewById(R.id.datetopicinFroum);
				ImageView iconProile = (ImageView) convertView.findViewById(R.id.iconfroumtitle);

				ImageView report = (ImageView) convertView.findViewById(R.id.reportImage);

				if (isShowSettingBtn == true) {
					report.setVisibility(View.VISIBLE);
				} else
					report.setVisibility(View.GONE);

				// end find view
				final PersonalData pd = (PersonalData) getChild(groupPosition, childPosition);

				report.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						List<String> items = new ArrayList<String>();

						items.clear();
						items.add(sendLable);
						items.add(copyLikkLable);
						items.add(deleteLable);

						PopupMenu popupmenu = util.ShowPopupMenu(items, v);
						popupmenu.show();

						popupmenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {

							@Override
							public boolean onMenuItemClick(MenuItem menuItem) {

								String nameItem = (String) menuItem.getTitle();

								if (nameItem.equals(sendLable)) {

									util.sendMessage("Paper");
								}
								if (nameItem.equals(deleteLable)) {

									DialogAreYouSure dia = new DialogAreYouSure(context, paperLable, pd.getPaperId(),
											fr);

									util.setSizeDialog(dia);

								}
								if (nameItem.equals(copyLikkLable)) {
									Toast.makeText(context, copyLikkLable, 0).show();
								}

								return false;
							}
						});
					}
				});

				if (pd.getSeenBeforePaper() > 0) {
					txt1.setTextColor(Color.GRAY);
					txt2.setTextColor(Color.GRAY);
					txt3.setTextColor(Color.GRAY);
					DateView.setTextColor(Color.GRAY);

				}

				// Users x = adapter.getUserbyid(person1.getUserId());
				// userId=x.getId();
				LinearLayout rl = (LinearLayout) convertView.findViewById(R.id.topicTitleFroum);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(rl.getLayoutParams());

				lp.width = (int) (util.getScreenwidth() / StaticValues.RateImageDisplayPersonalAdapter);
				lp.height = (int) (util.getScreenwidth() / StaticValues.RateImageDisplayPersonalAdapter);
				// lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
				lp.setMargins(10, 10, 10, 10);
				iconProile.setLayoutParams(lp);

				Users u = null;
				String ImagePath = "";
				if (isShowSettingBtn == false) {
					adapter.open();
					u = adapter.getUserbyid(pd.getUserIdPaper());
					adapter.close();
					ImagePath = u.getImagePath();

					txt3.setText(u.getName());

				} else {
					ImagePath = util.getCurrentUser().getImagePath();
					txt3.setText(util.getCurrentUser().getName());

				}
				if (ImagePath != null) {
					Bitmap bmp = BitmapFactory.decodeFile(ImagePath);
					iconProile.setImageBitmap(Utility.getclip(bmp));

					// iconProile.setLayoutParams(lp);
				} else {
					iconProile.setImageResource(R.drawable.no_img_profile);
					// iconProile.setLayoutParams(lp);

				}

				DateView.setText(util.getPersianDate(pd.getDatePaper()));

				txt1.setText(pd.getNamePaper());
				txt2.setText(pd.getDescriptonPaper());

				txt1.setTypeface(util.SetFontCasablanca());
				txt2.setTypeface(util.SetFontIranSans());

				TextView views = (TextView) convertView.findViewById(R.id.views);
				adapter.open();

				Paper pa = adapter.getPaperItembyid(pd.getPaperId());

				views.setText(pa.getCountView() + "");
				adapter.close();
				convertView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {

						FragmentTransaction trans = ((MainActivity) context).getSupportFragmentManager()
								.beginTransaction();
						PaperFragment fragment = new PaperFragment();
						Bundle bundle = new Bundle();
						bundle.putString("Id", String.valueOf(pd.getPaperId()));
						fragment.setArguments(bundle);
						trans.replace(R.id.content_frame, fragment);
						trans.addToBackStack(null);
						trans.commit();
					}
				});

			}
		} else if (("مدیریت تالار گفتگو").equals(parentItems.get(groupPosition))) {

			if (sizeTypeItem.get(groupPosition) == 0) {

				convertView = infalInflater.inflate(R.layout.row_search, null);

				TextView txt = (TextView) convertView.findViewById(R.id.row_search_name);

				final PersonalData pd = (PersonalData) getChild(groupPosition, childPosition);

				// //////////

				txt.setText(pd.getDateTicket());
				txt.setTypeface(util.SetFontCasablanca());

			} else {
				convertView = infalInflater.inflate(R.layout.row_personal_froum, null);

				// update your views here

				final TextView txt1 = (TextView) convertView.findViewById(R.id.rowtitlepaper);
				TextView txt2 = (TextView) convertView.findViewById(R.id.rowdescriptionpaper);
				TextView txt3 = (TextView) convertView.findViewById(R.id.authorname);

				TextView DateView = (TextView) convertView.findViewById(R.id.datetopicinFroum);
				ImageView iconProile = (ImageView) convertView.findViewById(R.id.iconfroumtitle);

				ImageView report = (ImageView) convertView.findViewById(R.id.reportImage);
				if (isShowSettingBtn == true) {
					report.setVisibility(View.VISIBLE);
				} else
					report.setVisibility(View.GONE);
				// end find view

				final PersonalData pd = (PersonalData) getChild(groupPosition, childPosition);

				report.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						List<String> items = new ArrayList<String>();

						items.clear();
						items.add(sendLable);
						items.add(copyLikkLable);
						items.add(deleteLable);

						PopupMenu popupMenu = util.ShowPopupMenu(items, v);
						popupMenu.show();

						popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {

							@Override
							public boolean onMenuItemClick(MenuItem menuItem) {

								String nameItem = (String) menuItem.getTitle();

								if (nameItem.equals(sendLable)) {

									util.sendMessage("Froum");
								}
								if (nameItem.equals(deleteLable)) {

									DialogAreYouSure dia = new DialogAreYouSure(context, FroumLable, pd.getFroumId(),
											fr);

									util.setSizeDialog(dia);

								}
								if (nameItem.equals(copyLikkLable)) {
									Toast.makeText(context, copyLikkLable, 0).show();
								}

								return false;
							}
						});

					}
				});

				if (pd.getSeenBeforeFroum() > 0) {
					txt1.setTextColor(Color.GRAY);
					txt2.setTextColor(Color.GRAY);
					txt3.setTextColor(Color.GRAY);
					DateView.setTextColor(Color.GRAY);

				}

				// Users x = adapter.getUserbyid(person1.getUserId());
				// userId=x.getId();
				LinearLayout rl = (LinearLayout) convertView.findViewById(R.id.topicTitleFroum);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(rl.getLayoutParams());

				lp.width = (int) (util.getScreenwidth() / StaticValues.RateImageDisplayPersonalAdapter);
				lp.height = (int) (util.getScreenwidth() / StaticValues.RateImageDisplayPersonalAdapter);
				// lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
				lp.setMargins(10, 10, 10, 10);
				iconProile.setLayoutParams(lp);

				Users u = null;
				String ImagePath = "";
				if (isShowSettingBtn == false) {
					adapter.open();
					u = adapter.getUserbyid(pd.getUserIdFroum());
					adapter.close();
					ImagePath = u.getImagePath();
					txt3.setText(u.getName());

				} else {
					ImagePath = util.getCurrentUser().getImagePath();
					txt3.setText(util.getCurrentUser().getName());

				}

				if (ImagePath != null) {
					Bitmap bmp = BitmapFactory.decodeFile(ImagePath);
					iconProile.setImageBitmap(Utility.getclip(bmp));

					iconProile.setLayoutParams(lp);
				} else {
					iconProile.setImageResource(R.drawable.no_img_profile);
					iconProile.setLayoutParams(lp);

				}
				DateView.setText(util.getPersianDate(pd.getDateFroum()));

				txt1.setText(pd.getNameFroum());
				txt2.setText(pd.getDescriptionFroum());

				txt1.setTypeface(util.SetFontCasablanca());
				txt2.setTypeface(util.SetFontIranSans());

				TextView views = (TextView) convertView.findViewById(R.id.views);
				adapter.open();

				Froum fr = adapter.getFroumItembyid(pd.getFroumId());

				views.setText(fr.getCountView() + "");
				adapter.close();
				convertView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {

						FragmentTransaction trans = ((MainActivity) context).getSupportFragmentManager()
								.beginTransaction();
						FroumFragment fragment = new FroumFragment();
						Bundle bundle = new Bundle();
						bundle.putString("Id", String.valueOf(pd.getFroumId()));
						fragment.setArguments(bundle);
						trans.replace(R.id.content_frame, fragment);
						trans.addToBackStack(null);
						trans.commit();

					}
				});

			}
		} else if (("مدیریت صفحات دنبال شده").equals(parentItems.get(groupPosition))) {

			if (sizeTypeItem.get(groupPosition) == 0) {

				convertView = infalInflater.inflate(R.layout.row_search, null);

				TextView txt = (TextView) convertView.findViewById(R.id.row_search_name);

				final PersonalData pd = (PersonalData) getChild(groupPosition, childPosition);

				// //////////

				txt.setText(pd.getDateTicket());
				txt.setTypeface(util.SetFontCasablanca());

			} else {
				convertView = infalInflater.inflate(R.layout.row_personal_object, null);

				// update your views here

				// //////////////////////

				TextView namePage = (TextView) convertView.findViewById(R.id.Rowobjecttxt);

				ImageView profileIco = (ImageView) convertView.findViewById(R.id.icon_object);

				ImageView report = (ImageView) convertView.findViewById(R.id.reportImage);

				FrameLayout rl = (FrameLayout) convertView.findViewById(R.id.imageFrame);
				FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(rl.getLayoutParams());

				lp.width = (int) (util.getScreenwidth() / StaticValues.RateImageDisplayPersonalAdapter);
				lp.height = (int) (util.getScreenwidth() / StaticValues.RateImageDisplayPersonalAdapter);
				lp.setMargins(10, 10, 10, 10);

				profileIco.setScaleType(ScaleType.FIT_XY);

				profileIco.setLayoutParams(lp);

				FrameLayout ad = (FrameLayout) convertView.findViewById(R.id.imageFrame);
				FrameLayout.LayoutParams ss = new FrameLayout.LayoutParams(ad.getLayoutParams());

				ss.width = (int) (util.getScreenwidth() / StaticValues.RateImageDisplayPersonalAdapter);
				ss.height = (int) (util.getScreenwidth() / StaticValues.RateImageDisplayPersonalAdapter);
				ss.setMargins(10, 10, 10, 10);

				profileIco.setScaleType(ScaleType.FIT_XY);

				profileIco.setLayoutParams(ss);

				final PersonalData pd = (PersonalData) getChild(groupPosition, childPosition);

				String ImagePath = pd.getImagePathObjectFollow();

				if (ImagePath != null) {
					Bitmap bitmap = BitmapFactory.decodeFile(ImagePath);
					profileIco.setImageBitmap(Utility.getclip(bitmap));
				}

				namePage.setText(pd.getNameFollowObject());
				namePage.setTypeface(util.SetFontIranSans());

				TextView baghiMandeh = (TextView) convertView.findViewById(R.id.dayBaghiMandeh); // modate
																									// baghimande

				TextView lable1 = (TextView) convertView.findViewById(R.id.lable_etebar);

				TextView lable2 = (TextView) convertView.findViewById(R.id.lable2);

				baghiMandeh.setVisibility(View.GONE);
				lable1.setVisibility(View.GONE);
				lable2.setVisibility(View.GONE);

				TextView follwers = (TextView) convertView.findViewById(R.id.followers);

				TextView views = (TextView) convertView.findViewById(R.id.views);
				adapter.open();
				com.project.mechanic.entity.Object obj = adapter.getObjectbyid(pd.getObjectFollowId());
				views.setText(obj.getCountView() + "");

				int followersCount = adapter.LikeInObject_count(pd.getObjectFollowId(), 0);

				follwers.setText(followersCount + "");
				adapter.close();

				convertView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {

						IntroductionFragment fragment = new IntroductionFragment(-1);
						FragmentTransaction trans = ((MainActivity) context).getSupportFragmentManager()
								.beginTransaction();
						trans.replace(R.id.content_frame, fragment);
						Bundle bundle = new Bundle();
						bundle.putString("Id", String.valueOf(pd.getObjectFollowId()));
						fragment.setArguments(bundle);
						trans.addToBackStack(null);
						trans.commit();

					}
				});

				report.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						List<String> items = new ArrayList<String>();
						items.clear();
						items.add(deleteLable);

						PopupMenu popupMenu = util.ShowPopupMenu(items, v);
						popupMenu.show();
						OnMenuItemClickListener menuitem = new OnMenuItemClickListener() {

							@Override
							public boolean onMenuItemClick(MenuItem item) {

								if (item.getItemId() == 0) {

									adapter.open();
									if (adapter.isUserLikeIntroductionPage(util.getCurrentUser().getId(),
											pd.getObjectFollowId(), 0)) {

										adapter.deleteLikeIntroduction(util.getCurrentUser().getId(),
												pd.getObjectFollowId(), 0);
										notifyDataSetChanged();

										Toast.makeText(context, " از لیست دنبال شوندگان حذف شد ", 0).show();

										((DisplayPersonalInformationFragment) fr).FillExpandListView();

									}
								}

								return false;
							}
						};

						popupMenu.setOnMenuItemClickListener(menuitem);

						adapter.close();

					}
				});

				// /////////////////////////////////

				int rate = obj.getRate();

				if (rate > 0) {

					ImageView star1 = (ImageView) convertView.findViewById(R.id.star1);
					ImageView star2 = (ImageView) convertView.findViewById(R.id.star2);
					ImageView star3 = (ImageView) convertView.findViewById(R.id.star3);
					ImageView star4 = (ImageView) convertView.findViewById(R.id.star4);
					ImageView star5 = (ImageView) convertView.findViewById(R.id.star5);

					switch (rate) {
					case 1:
						star1.setBackgroundResource(R.drawable.ic_star_on);

						break;
					case 2:
						star1.setBackgroundResource(R.drawable.ic_star_on);
						star2.setBackgroundResource(R.drawable.ic_star_on);

						break;

					case 3:
						star1.setBackgroundResource(R.drawable.ic_star_on);
						star2.setBackgroundResource(R.drawable.ic_star_on);
						star3.setBackgroundResource(R.drawable.ic_star_on);

						break;

					case 4:
						star1.setBackgroundResource(R.drawable.ic_star_on);
						star2.setBackgroundResource(R.drawable.ic_star_on);
						star3.setBackgroundResource(R.drawable.ic_star_on);
						star4.setBackgroundResource(R.drawable.ic_star_on);

						break;

					case 5:
						star1.setBackgroundResource(R.drawable.ic_star_on);
						star2.setBackgroundResource(R.drawable.ic_star_on);
						star3.setBackgroundResource(R.drawable.ic_star_on);
						star4.setBackgroundResource(R.drawable.ic_star_on);
						star5.setBackgroundResource(R.drawable.ic_star_on);

						break;

					default:
						break;
					}

				}

			}
		} else if (("مدیریت تبلیغات").equals(parentItems.get(groupPosition))) {

			if (sizeTypeItem.get(groupPosition) == 0) {

				convertView = infalInflater.inflate(R.layout.row_search, null);

				TextView txt = (TextView) convertView.findViewById(R.id.row_search_name);

				final PersonalData pd = (PersonalData) getChild(groupPosition, childPosition);

				// //////////

				txt.setText(pd.getDateTicket());
				txt.setTypeface(util.SetFontCasablanca());

			} else {
				convertView = infalInflater.inflate(R.layout.row_anad_personal_tabligh, null);

				// update your views here

				// //////////////////////

				TextView namePage = (TextView) convertView.findViewById(R.id.Rowobjecttxt);
				TextView ProvinceName = (TextView) convertView.findViewById(R.id.row_anad_txt2);

				ImageView profileIco = (ImageView) convertView.findViewById(R.id.row_favorite_img);

				ImageView report = (ImageView) convertView.findViewById(R.id.reportImage);

				FrameLayout rl = (FrameLayout) convertView.findViewById(R.id.imageFrame);
				FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(rl.getLayoutParams());

				lp.width = (int) (util.getScreenwidth() / StaticValues.RateImageDisplayPersonalAdapter);
				lp.height = (int) (util.getScreenwidth() / StaticValues.RateImageDisplayPersonalAdapter);
				lp.setMargins(10, 10, 10, 10);

				profileIco.setScaleType(ScaleType.FIT_XY);

				profileIco.setLayoutParams(lp);

				FrameLayout ad = (FrameLayout) convertView.findViewById(R.id.imageFrame);
				FrameLayout.LayoutParams ss = new FrameLayout.LayoutParams(ad.getLayoutParams());

				ss.width = (int) (util.getScreenwidth() / StaticValues.RateImageDisplayPersonalAdapter);
				ss.height = (int) (util.getScreenwidth() / StaticValues.RateImageDisplayPersonalAdapter);
				ss.setMargins(10, 10, 10, 10);

				profileIco.setScaleType(ScaleType.FIT_XY);

				profileIco.setLayoutParams(ss);

				final PersonalData pd = (PersonalData) getChild(groupPosition, childPosition);

				int anadId = pd.getAnadId();
				adapter.open();
				Anad a = adapter.getAnadByid(anadId);
				com.project.mechanic.entity.Object obj = adapter.getObjectbyid(a.getObjectId());
				Province province = adapter.getProvinceById(a.getProvinceId());
				adapter.close();

				String ImagePath = a.getImagePath();

				if (ImagePath != null) {
					Bitmap bitmap = BitmapFactory.decodeFile(ImagePath);
					profileIco.setImageBitmap(Utility.getclip(bitmap));
				}

				namePage.setText(obj.getName());
				ProvinceName.setText(province.getName());
				ProvinceName.setTypeface(util.SetFontCasablanca());

				TextView baghiMandeh = (TextView) convertView.findViewById(R.id.day); // modate
																						// baghimande

				TextView lable1 = (TextView) convertView.findViewById(R.id.lable_etebar);
				TextView lable2 = (TextView) convertView.findViewById(R.id.lable2);

				lable1.setTypeface(util.SetFontCasablanca());
				lable2.setTypeface(util.SetFontCasablanca());
				baghiMandeh.setTypeface(util.SetFontCasablanca());

				String commitDate = a.getDate(); // tarikhe ijad safhe

				if (commitDate != null && !"".equals(commitDate)) {
					final SharedPreferences currentTime = context.getSharedPreferences("time", 0);

					String time = currentTime.getString("time", "-1");

					int diff = util.differentTwoDate(commitDate, time);

					baghiMandeh.setText(diff + "");

					ImageView imgBi = (ImageView) convertView.findViewById(R.id.aks_bi_etebar);

					if (diff <= 0) {
						imgBi.setVisibility(View.VISIBLE);
						imgBi.setLayoutParams(lp);
					}

				} else {
					baghiMandeh.setText("نا معلوم");
				}

				// baghiMandeh.setVisibility(View.GONE);
				// lable1.setVisibility(View.GONE);
				// lable2.setVisibility(View.GONE);

				report.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						List<String> items = new ArrayList<String>();
						items.clear();
						items.add(editLable);
						items.add(tamdidLAble);

						PopupMenu popupMenu = util.ShowPopupMenu(items, v);
						popupMenu.show();
						OnMenuItemClickListener menuitem = new OnMenuItemClickListener() {

							@Override
							public boolean onMenuItemClick(MenuItem menuItem) {

								String nameItem = (String) menuItem.getTitle();

								if (nameItem.equals(editLable)) {

									FragmentManager fm = ((FragmentActivity) context).getSupportFragmentManager();

									DialogEditAnad dialog = new DialogEditAnad(context, fr, pd.getAnadId());
									dialog.show(fm, nameItem);
								}
								if (nameItem.equals(tamdidLAble)) {
									Toast.makeText(context, copyLikkLable, 0).show();
								}

								return false;
							}
						};

						popupMenu.setOnMenuItemClickListener(menuitem);

						adapter.close();

					}
				});

				// /////////////////////////////////

			}
		}

		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return listDataChild.get(parentItems.get(groupPosition)).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return parentItems.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return parentItems.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(final int groupPosition, final boolean isExpanded, View convertView,
			final ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.row_group_test, null);

		}

		final ExpandableListView mExpandableListView = (ExpandableListView) parent;

		TextView titleGroup = (TextView) convertView.findViewById(R.id.row_berand_txt);
		if (util.getCurrentUser() != null)
			titleGroup.setText(parentItems.get(groupPosition));
		titleGroup.setTypeface(util.SetFontCasablanca());

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {


				RelativeLayout layout = (RelativeLayout) v;
				ImageView indicator = (ImageView) layout.getChildAt(0);

				if (isExpanded) {
					mExpandableListView.collapseGroup(groupPosition);
					indicator.setBackgroundResource(R.drawable.dow);

				} else {

					indicator.setBackgroundResource(R.drawable.dow_s);
					mExpandableListView.expandGroup(groupPosition);
					mExpandableListView.setSelectedChild(groupPosition, 0, true);

					if (lastExpandedPosition != -1 && groupPosition != lastExpandedPosition) {
						mExpandableListView.collapseGroup(lastExpandedPosition);
					}
					lastExpandedPosition = groupPosition;

				}

			}
		});

		return convertView;
	}

	public void onBackPressed(ExpandableListView ex, int position, boolean isExpand) {

		if (ex.getVisibility() == View.VISIBLE) {
			ex.setVisibility(View.GONE);
			Toast.makeText(context, "open", 0).show();

		} else {
			Toast.makeText(context, "close", 0).show();
		}

	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
