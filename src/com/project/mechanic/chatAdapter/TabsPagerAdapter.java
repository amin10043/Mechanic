package com.project.mechanic.chatAdapter;

import com.project.mechanic.StaticValues;
import com.project.mechanic.chat.ChanalChatFragment;
import com.project.mechanic.chat.ChatMenuFragment;
import com.project.mechanic.chat.ContactChatFragment;
import com.project.mechanic.chat.FamilyChatFragment;
import com.project.mechanic.chat.FavoriteChatFragment;
import com.project.mechanic.chat.FriendChatFragment;
import com.project.mechanic.chat.GroupChatFragment;
import com.project.mechanic.chat.OffersChatFragment;
import com.project.mechanic.chat.RequestionChatFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {
	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		Fragment fr = null;

		if (index == 0) {
			fr = new ChatMenuFragment();

		}
		if (index == 1) {
			fr = new FavoriteChatFragment();

		}
		if (index == 2) {
			fr = new FamilyChatFragment();

		}
		if (index == 3) {
			fr = new FriendChatFragment();

		}
		if (index == 4) {
			fr = new GroupChatFragment();

		}
		if (index == 5) {
			fr = new ChanalChatFragment();

		}
		if (index == 6) {
			fr = new RequestionChatFragment();

		}
		if (index == 7) {
			fr = new OffersChatFragment();

		}
		if (index == 8) {
			fr = new ContactChatFragment();

		}
		return fr;

		// switch (index) {
		// case 0:
		// return new FavoriteChatFragment();
		// case 1:
		// return new FamilyChatFragment();
		// case 2:
		// return new FriendChatFragment();
		// case 3:
		// return new GroupChatFragment();
		// case 4:
		// return new ChanalChatFragment();
		// case 5:
		// return new RequestionChatFragment();
		// case 6:
		// return new OffersChatFragment();
		// case 7:
		// return new ContactChatFragment();
		//
		// }
		//
		// return new FavoriteChatFragment();
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return StaticValues.CountOfTabInChat;
	}

}
