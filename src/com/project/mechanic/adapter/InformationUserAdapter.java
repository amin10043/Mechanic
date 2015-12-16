package com.project.mechanic.adapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;
import android.widget.PopupMenu.OnMenuItemClickListener;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.entity.Froum;
import com.project.mechanic.entity.Paper;
import com.project.mechanic.entity.PersonalData;
import com.project.mechanic.entity.Users;
import com.project.mechanic.fragment.DisplayPersonalInformationFragment;
import com.project.mechanic.fragment.Favorite_Fragment;
import com.project.mechanic.fragment.FroumFragment;
import com.project.mechanic.fragment.IntroductionFragment;
import com.project.mechanic.fragment.PaperFragment;
import com.project.mechanic.fragment.ShowAdFragment;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class InformationUserAdapter extends BaseExpandableListAdapter {
	Context context;
	ArrayList<String> parentItems;
	HashMap<String, List<PersonalData>> listDataChild;
	Utility util;
	String todayDate;
	DataBaseAdapter adapter;
	Fragment fr;
	// int countPage, countTicket, countPaper, countFroum;
	List<Integer> sizeTypeItem;
	boolean isShowSettingBtn;
	String name;

	public InformationUserAdapter(Context context, ArrayList<String> parentItems,
			HashMap<String, List<PersonalData>> listDataChild, String todayDate, Fragment fr, List<Integer> sizeType,
			boolean isShowSettingBtn, String name) {

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

	@Override
	public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView,
			ViewGroup parent) {

		// if (convertView == null) {
		LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (("صفحات").equals(parentItems.get(groupPosition))) {
			
			if (sizeTypeItem.get(groupPosition) == 0) {

				convertView = infalInflater.inflate(R.layout.row_search, null);

				TextView txt = (TextView) convertView.findViewById(R.id.row_search_name);

				final PersonalData pd = (PersonalData) getChild(groupPosition, childPosition);

				// //////////

				txt.setText(pd.getDateTicket());

			}else{
			convertView = infalInflater.inflate(R.layout.row_personal_object, null);

			// update your views here

			TextView namePage = (TextView) convertView.findViewById(R.id.Rowobjecttxt);

			ImageView profileIco = (ImageView) convertView.findViewById(R.id.icon_object);

			ImageView report = (ImageView) convertView.findViewById(R.id.reportImage);

			report.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					List<String> items = new ArrayList<String>();

					items.clear();
					items.add("تمدید اعتبار");
					items.add("حذف");
					items.add("کپی");

					PopupMenu popupMenu = util.ShowPopupMenu(items, v);
					popupMenu.show();

				}
			});

			FrameLayout rl = (FrameLayout) convertView.findViewById(R.id.imageFrame);
			FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(rl.getLayoutParams());

			lp.width = (util.getScreenwidth() / 5);
			lp.height = (util.getScreenwidth() / 5);
			lp.setMargins(10, 10, 10, 10);

			profileIco.setScaleType(ScaleType.FIT_XY);

			profileIco.setLayoutParams(lp);

			if (isShowSettingBtn == true) {
				report.setVisibility(View.VISIBLE);
			} else
				report.setVisibility(View.GONE);

			final PersonalData pd = (PersonalData) getChild(groupPosition, childPosition);

			String ImagePath = pd.getImagePathObject();

			if (ImagePath != null) {
				Bitmap bitmap = BitmapFactory.decodeFile(ImagePath);
				profileIco.setImageBitmap(Utility.getclip(bitmap));
			}

			namePage.setText(pd.getNameObject());

			LinearLayout etebar = (LinearLayout) convertView.findViewById(R.id.et); // modate
																					// baghimande

			etebar.setVisibility(View.GONE);

			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {

					IntroductionFragment fragment = new IntroductionFragment();
					FragmentTransaction trans = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame, fragment);
					Bundle bundle = new Bundle();
					bundle.putString("Id", String.valueOf(pd.getObjectId()));
					fragment.setArguments(bundle);
					trans.addToBackStack(null);
					trans.commit();

				}
			});

		}} else if (("آگهی ها").equals(parentItems.get(groupPosition))) {
			convertView = infalInflater.inflate(R.layout.row_anad_personal, null);

			TextView txtdate = (TextView) convertView.findViewById(R.id.text_favorite_desc);
			TextView txtName = (TextView) convertView.findViewById(R.id.row_favorite_title);
			TextView txtDesc = (TextView) convertView.findViewById(R.id.row_anad_txt2);
			ImageView img2 = (ImageView) convertView.findViewById(R.id.row_favorite_img);

			ProgressBar LoadingProgress = (ProgressBar) convertView.findViewById(R.id.progressBar1);
			LoadingProgress.setVisibility(View.GONE);

			final PersonalData pd = (PersonalData) getChild(groupPosition, childPosition);

			txtdate.setText(util.getPersianDate(pd.getDateTicket()));
			txtName.setText(pd.getNameTicket());
			txtDesc.setText(pd.getDescriptonTicket());

			if (pd.getSeenBefore() > 0) {
				txtName.setTextColor(Color.GRAY);
				txtDesc.setTextColor(Color.GRAY);
				txtdate.setTextColor(Color.GRAY);

			}

			RelativeLayout llkj = (RelativeLayout) convertView.findViewById(R.id.layoutmnb);
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(llkj.getLayoutParams());
			params.width = util.getScreenwidth() / 5;
			params.height = util.getScreenwidth() / 5;
			params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			params.setMargins(10, 10, 10, 10);

			String pathProfile = pd.getImagePathTicket();
			Bitmap profileImage = BitmapFactory.decodeFile(pathProfile);

			if (profileImage != null) {

				img2.setImageBitmap(Utility.getclip(profileImage));
				img2.setLayoutParams(params);

			} else {
				img2.setImageResource(R.drawable.no_img_profile);
				img2.setLayoutParams(params);
			}
			// String commitDate = pd.getDateTicket();
			// int thisDay = 0;
			// int TicketDay = Integer.valueOf(commitDate.substring(0, 8));
			// if (todayDate != null && !todayDate.equals(""))
			// thisDay = Integer.valueOf(todayDate.substring(0, 8));
			// LinearLayout TicketBackground = (LinearLayout) convertView
			// .findViewById(R.id.backgroundTicket);

			// if (thisDay <= TicketDay + pd.getDayTicket()) {
			// TicketBackground.setBackgroundColor(Color.WHITE);
			if (pd.getSeenBefore() > 0) {
				txtName.setTextColor(Color.GRAY);
				txtDesc.setTextColor(Color.GRAY);
				txtdate.setTextColor(Color.GRAY);
			}
			//
			// } else {
			// TicketBackground.setBackgroundResource(R.color.lightred);
			//
			// if (pd.getSeenBefore() > 0) {
			// txtName.setTextColor(Color.WHITE);
			// txtDesc.setTextColor(Color.WHITE);
			// txtdate.setTextColor(Color.WHITE);
			//
			// }
			// }

			txtName.setTypeface(util.SetFontCasablanca());
			txtDesc.setTypeface(util.SetFontCasablanca());

			ImageView reaport = (ImageView) convertView.findViewById(R.id.reportImage);
			if (isShowSettingBtn == true) {
				reaport.setVisibility(View.VISIBLE);
			} else
				reaport.setVisibility(View.GONE);

			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					FragmentTransaction trans = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
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

					// String[] nameItems = { "تمدید اعتبار", "حذف", "کپی" };

					List<String> items = new ArrayList<String>();

					items.clear();
					items.add("تمدید اعتبار");
					items.add("حذف");
					items.add("کپی");

					util.ShowPopupMenu(items, v);

					// DialogLongClick dia = new DialogLongClick(context, 3, u,
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
		} else if (("مقالات").equals(parentItems.get(groupPosition))) {
			
			if (sizeTypeItem.get(groupPosition) == 0) {

				convertView = infalInflater.inflate(R.layout.row_search, null);

				TextView txt = (TextView) convertView.findViewById(R.id.row_search_name);

				final PersonalData pd = (PersonalData) getChild(groupPosition, childPosition);

				// //////////

				txt.setText(pd.getDateTicket());

			}else{
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

			report.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					List<String> items = new ArrayList<String>();

					items.clear();
					items.add("ارسال پیام");
					items.add("حذف");
					items.add("کپی");

					// String[] nameItems = { "ارسال پیام", "حذف", "کپی" };

					util.ShowPopupMenu(items, v);
				}
			});

			final PersonalData pd = (PersonalData) getChild(groupPosition, childPosition);

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

			lp.width = util.getScreenwidth() / 5;
			lp.height = util.getScreenwidth() / 5;
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

				iconProile.setLayoutParams(lp);
			}

			DateView.setText(util.getPersianDate(pd.getDatePaper()));

			txt1.setText(pd.getNamePaper());
			txt2.setText(pd.getDescriptonPaper());

			txt1.setTypeface(util.SetFontCasablanca());
			txt2.setTypeface(util.SetFontCasablanca());

			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {

					FragmentTransaction trans = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
					PaperFragment fragment = new PaperFragment();
					Bundle bundle = new Bundle();
					bundle.putString("Id", String.valueOf(pd.getPaperId()));
					fragment.setArguments(bundle);
					trans.replace(R.id.content_frame, fragment);
					trans.addToBackStack(null);
					trans.commit();
				}
			});

		}} else if (("تالار گفتگو").equals(parentItems.get(groupPosition))) {
			
			if (sizeTypeItem.get(groupPosition) == 0) {

				convertView = infalInflater.inflate(R.layout.row_search, null);

				TextView txt = (TextView) convertView.findViewById(R.id.row_search_name);

				final PersonalData pd = (PersonalData) getChild(groupPosition, childPosition);

				// //////////

				txt.setText(pd.getDateTicket());

			}else{
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

			report.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					List<String> items = new ArrayList<String>();

					items.clear();
					items.add("ارسال پیام");
					items.add("حذف");
					items.add("کپی");

					util.ShowPopupMenu(items, v);
				}
			});
			final PersonalData pd = (PersonalData) getChild(groupPosition, childPosition);

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

			lp.width = util.getScreenwidth() / 5;
			lp.height = util.getScreenwidth() / 5;
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
			}

			DateView.setText(util.getPersianDate(pd.getDateFroum()));

			txt1.setText(pd.getNameFroum());
			txt2.setText(pd.getDescriptionFroum());

			txt1.setTypeface(util.SetFontCasablanca());
			txt2.setTypeface(util.SetFontCasablanca());

			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {

					FragmentTransaction trans = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
					FroumFragment fragment = new FroumFragment();
					Bundle bundle = new Bundle();
					bundle.putString("Id", String.valueOf(pd.getFroumId()));
					fragment.setArguments(bundle);
					trans.replace(R.id.content_frame, fragment);
					trans.addToBackStack(null);
					trans.commit();

				}
			});

			}} else if (("مدیریت صفحات دنبال شده").equals(parentItems.get(groupPosition))) {
			convertView = infalInflater.inflate(R.layout.row_personal_object, null);

			// update your views here

			RelativeLayout followLayout = (RelativeLayout) convertView.findViewById(R.id.propertiesObject);
			// visitLayout = (RelativeLayout)
			// convertView.findViewById(R.id.relativeLayout2);

			RelativeLayout.LayoutParams paramsfollow = new RelativeLayout.LayoutParams(followLayout.getLayoutParams());
			RelativeLayout.LayoutParams paramsVisit = new RelativeLayout.LayoutParams(followLayout.getLayoutParams());

			paramsfollow.width = (util.getScreenwidth());
			paramsfollow.height = (util.getScreenwidth() / 4);
			paramsfollow.addRule(RelativeLayout.LEFT_OF, R.id.icon_object);

			paramsVisit.width = (util.getScreenwidth() / 16);
			paramsVisit.height = (util.getScreenwidth() / 16);
			paramsVisit.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

			ImageView followIcon = (ImageView) convertView.findViewById(R.id.iconNumberLike);
			ImageView visitIcon = (ImageView) convertView.findViewById(R.id.iconNumberVisit);

			followLayout.setLayoutParams(paramsfollow);

			followIcon.setLayoutParams(paramsVisit);
			visitIcon.setLayoutParams(paramsVisit);

			TextView namePage = (TextView) convertView.findViewById(R.id.Rowobjecttxt);

			ImageView profileIco = (ImageView) convertView.findViewById(R.id.icon_object);

			ImageView report = (ImageView) convertView.findViewById(R.id.reportImage);

			RelativeLayout rl = (RelativeLayout) convertView.findViewById(R.id.main_icon_reply);
			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(rl.getLayoutParams());

			lp.width = (util.getScreenwidth() / 5);
			lp.height = (util.getScreenwidth() / 5);
			lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			lp.setMargins(10, 10, 10, 10);
			profileIco.setLayoutParams(lp);

			final PersonalData pd = (PersonalData) getChild(groupPosition, childPosition);

			String ImagePath = pd.getImagePathObjectFollow();

			if (ImagePath != null) {
				Bitmap bitmap = BitmapFactory.decodeFile(ImagePath);
				profileIco.setImageBitmap(Utility.getclip(bitmap));
			}

			namePage.setText(pd.getNameFollowObject());

			TextView baghiMandeh = (TextView) convertView.findViewById(R.id.dayBaghiMandeh); // modate
																								// baghimande

			TextView lable1 = (TextView) convertView.findViewById(R.id.lable_etebar);

			TextView lable2 = (TextView) convertView.findViewById(R.id.lable2);

			baghiMandeh.setVisibility(View.GONE);
			lable1.setVisibility(View.GONE);
			lable2.setVisibility(View.GONE);

			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {

					IntroductionFragment fragment = new IntroductionFragment();
					FragmentTransaction trans = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
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
					items.add("حذف");

					PopupMenu popupMenu = util.ShowPopupMenu(items, v);

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
	public View getGroupView(final int groupPosition, final boolean isExpanded, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.row_group_test, null);

		}

		final ExpandableListView mExpandableListView = (ExpandableListView) parent;

		TextView titleGroup = (TextView) convertView.findViewById(R.id.row_berand_txt);
		// if (util.getCurrentUser() != null)
		titleGroup.setText(parentItems.get(groupPosition) + " - " + name);

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// if (sizeTypeItem.get(groupPosition) == 0) {
				// Toast.makeText(context, "عنوانی ثبت نشده است", 0).show();
				// } else {

				if (isExpanded) {
					mExpandableListView.collapseGroup(groupPosition);
					notifyDataSetChanged();

				} else
					mExpandableListView.expandGroup(groupPosition);

				notifyDataSetChanged();
				// }
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
