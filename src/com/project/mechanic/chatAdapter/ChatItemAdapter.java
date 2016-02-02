package com.project.mechanic.chatAdapter;

import java.util.ArrayList;
import java.util.List;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.StaticValues;
import com.project.mechanic.chat.ChatFragment;
import com.project.mechanic.chat.TabHostChatType;
import com.project.mechanic.utility.Utility;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

public class ChatItemAdapter extends ArrayAdapter<String> {

	Context context;
	List<String> items;
	Utility util;
	List<String> menuItems;
	int type;

	static final String favoriteLable = "افزودن به علاقه مندی ";
	static final String notificationLable = "هشدار";
	static final String shareLable = "اشتراک گذاری";
	static final String clearHistory = "حذف محتویات";
	static final String blocklable = "مسدود کردن";
	static final String deleteLable = "حذف";
	static final String deleteAndExit = "حذف و ترک";
	static final String reportLable = "گزارش تخلف";

	static final String familyLable = "افزودن به خانواده";
	static final String friendLable = "افزودن به دوستان";
	static final String contactLable = "افزودن به مخاطبین";
	static final String chanalLable = "افزودن به تالار";

	public ChatItemAdapter(Context context, int resource, List<String> items, int type) {
		super(context, resource, items);
		this.context = context;
		this.items = items;
		util = new Utility(context);
		this.type = type;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		switch (type) {

		case StaticValues.TypeFavoriteChat: {

			convertView = typeConvertView(position);

			break;
		}
		case StaticValues.TypeFamilyChat: {

			convertView = typeConvertView(position);

			break;
		}
		case StaticValues.TypeFriendChat: {

			convertView = typeConvertView(position);

			break;
		}
		case StaticValues.TypeGroupChat: {

			convertView = typeConvertView(position);

			break;
		}

		case StaticValues.TypeChannalChat: {

			convertView = typeConvertView(position);

			break;
		}
		case StaticValues.TypeRequestionChat: {
			convertView = requestionView(position);
			break;
		}
		case StaticValues.TypeOffersChat: {

			convertView = offerView(position);
			break;
		}

		case StaticValues.TypeContactChat: {

			convertView = typeContactView(position);
			break;
		}

		default:
			convertView = typeConvertView(position);

			break;
		}

		return convertView;
	}

	private void SettingsItems(View v) {
		if (util.getCurrentUser() != null) {

			switch (type) {
			case StaticValues.TypeFavoriteChat: {

				break;
			}

			case StaticValues.TypeFamilyChat: {

				menuItems = new ArrayList<String>();
				menuItems.clear();

				menuItems.add(favoriteLable);
				menuItems.add(notificationLable);
				menuItems.add(shareLable);
				menuItems.add(clearHistory);
				menuItems.add(blocklable);
				menuItems.add(deleteLable);

				final PopupMenu popupMenu = util.ShowPopupMenu(menuItems, v);
				popupMenu.show();

				break;
			}
			case StaticValues.TypeFriendChat: {

				menuItems = new ArrayList<String>();
				menuItems.clear();

				menuItems.add(favoriteLable);
				menuItems.add(notificationLable);
				menuItems.add(shareLable);
				menuItems.add(clearHistory);
				menuItems.add(blocklable);
				menuItems.add(deleteLable);

				final PopupMenu popupMenu = util.ShowPopupMenu(menuItems, v);
				popupMenu.show();

				break;
			}

			case StaticValues.TypeGroupChat: {

				menuItems = new ArrayList<String>();
				menuItems.clear();

				menuItems.add(favoriteLable);
				menuItems.add(notificationLable);
				menuItems.add(shareLable);
				menuItems.add(clearHistory);
				menuItems.add(deleteAndExit);

				final PopupMenu popupMenu = util.ShowPopupMenu(menuItems, v);
				popupMenu.show();

				break;
			}

			case StaticValues.TypeChannalChat: {

				menuItems = new ArrayList<String>();
				menuItems.clear();

				menuItems.add(favoriteLable);
				menuItems.add(notificationLable);
				menuItems.add(shareLable);
				menuItems.add(clearHistory);
				menuItems.add(deleteAndExit);

				final PopupMenu popupMenu = util.ShowPopupMenu(menuItems, v);
				popupMenu.show();

				break;
			}

			case StaticValues.TypeRequestionChat: {

				menuItems = new ArrayList<String>();
				menuItems.clear();

				menuItems.add(blocklable);
				menuItems.add(reportLable);

				final PopupMenu popupMenu = util.ShowPopupMenu(menuItems, v);
				popupMenu.show();

				break;
			}

			case StaticValues.TypeOffersChat: {

				menuItems = new ArrayList<String>();
				menuItems.clear();

				menuItems.add(favoriteLable);
				menuItems.add(chanalLable);

				final PopupMenu popupMenu = util.ShowPopupMenu(menuItems, v);
				popupMenu.show();
				break;
			}
			case StaticValues.TypeContactChat: {

				menuItems = new ArrayList<String>();
				menuItems.clear();

				menuItems.add(favoriteLable);
				menuItems.add(notificationLable);
				menuItems.add(shareLable);
				menuItems.add(blocklable);

				final PopupMenu popupMenu = util.ShowPopupMenu(menuItems, v);
				popupMenu.show();

				break;
			}

			default:
				break;
			}

		}
	}

	private View typeConvertView(int position) {

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View convertView = inflater.inflate(R.layout.row_child_type_chat, null);

		TextView titleChild = (TextView) convertView.findViewById(R.id.Rowobjecttxt);
		TextView dateChat = (TextView) convertView.findViewById(R.id.datechat);
		ImageView imagChild = (ImageView) convertView.findViewById(R.id.row_favorite_img);

		LinearLayout settings = (LinearLayout) convertView.findViewById(R.id.settingsLayout);

		settings.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				SettingsItems(v);

			}
		});

		titleChild.setTypeface(util.SetFontCasablanca());

		titleChild.setText(items.get(position));

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (type == StaticValues.TypeGroupChat) {

					FragmentTransaction trans = ((TabHostChatType) context).getSupportFragmentManager()
							.beginTransaction();
					trans.addToBackStack(null);
					Fragment move = new ChatFragment((Activity) context, StaticValues.TypeInformationGroupAdminChat);
					trans.replace(R.id.content_frame, move);
					trans.commit();

				}
			}
		});

		return convertView;
	}

	private View typeContactView(int position) {

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View convertView = inflater.inflate(R.layout.row_contact_chat, null);

		TextView titleChild = (TextView) convertView.findViewById(R.id.Rowobjecttxt);
		TextView dateChat = (TextView) convertView.findViewById(R.id.datechat);
		ImageView imagChild = (ImageView) convertView.findViewById(R.id.row_favorite_img);

		LinearLayout settings = (LinearLayout) convertView.findViewById(R.id.settingsLayout);

		settings.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				SettingsItems(v);

			}
		});

		titleChild.setTypeface(util.SetFontCasablanca());

		titleChild.setText(items.get(position));

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				FragmentTransaction trans = ((TabHostChatType) context).getSupportFragmentManager().beginTransaction();
				trans.addToBackStack(null);
				Fragment move = new ChatFragment((Activity) context, 1);
				trans.replace(R.id.content_frame, move);
				trans.commit();

			}
		});

		return convertView;
	}

	private View requestionView(int position) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View convertView = inflater.inflate(R.layout.row_requestion_chat, null);

		TextView titleChild = (TextView) convertView.findViewById(R.id.Rowobjecttxt);
		TextView dateChat = (TextView) convertView.findViewById(R.id.datechat);
		ImageView imagChild = (ImageView) convertView.findViewById(R.id.row_favorite_img);
		ImageView accept = (ImageView) convertView.findViewById(R.id.acceptBtn);
		TextView source = (TextView) convertView.findViewById(R.id.row_source);

		LinearLayout settings = (LinearLayout) convertView.findViewById(R.id.settingsLayout);

		settings.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				SettingsItems(v);

			}
		});

		accept.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				menuItems = new ArrayList<String>();
				menuItems.clear();

				menuItems.add(favoriteLable);
				menuItems.add(familyLable);
				menuItems.add(friendLable);
				menuItems.add(contactLable);

				final PopupMenu popupMenu = util.ShowPopupMenu(menuItems, v);
				popupMenu.show();

			}
		});

		titleChild.setTypeface(util.SetFontCasablanca());

		titleChild.setText(items.get(position));
		source.setTypeface(util.SetFontCasablanca());

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				FragmentTransaction trans = ((TabHostChatType) context).getSupportFragmentManager().beginTransaction();
				trans.addToBackStack(null);
				Fragment move = new ChatFragment((Activity) context, 2);
				trans.replace(R.id.content_frame, move);
				trans.commit();

			}
		});

		return convertView;
	}

	private View offerView(int position) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View convertView = inflater.inflate(R.layout.row_offers_chat, null);

		TextView titleChild = (TextView) convertView.findViewById(R.id.Rowobjecttxt);
		ImageView imagChild = (ImageView) convertView.findViewById(R.id.row_favorite_img);
		ImageView like = (ImageView) convertView.findViewById(R.id.follow);

		like.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				SettingsItems(v);
			}
		});

		titleChild.setTypeface(util.SetFontCasablanca());

		titleChild.setText(items.get(position));

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				FragmentTransaction trans = ((TabHostChatType) context).getSupportFragmentManager().beginTransaction();
				trans.addToBackStack(null);
				Fragment move = new ChatFragment((Activity) context, 3);
				trans.replace(R.id.content_frame, move);
				trans.commit();

			}
		});

		return convertView;
	}

}
