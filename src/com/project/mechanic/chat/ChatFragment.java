package com.project.mechanic.chat;

import com.project.mechanic.R;
import com.project.mechanic.crop.Util;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.utility.Utility;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ChatFragment extends Fragment {
	Utility util;
	Activity activity;
	int type;

	public ChatFragment(Activity activity, int type) {

		this.activity = activity;
		this.type = type;
	}

	// public ChatFragment(int type) {
	//
	// this.type = type;
	// }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View convertView = inflater.inflate(R.layout.fragment_chat, null);
		util = new Utility(getActivity());

		ImageView settings = (ImageView) convertView.findViewById(R.id.settings);

		LinearLayout information = (LinearLayout) convertView.findViewById(R.id.infoItem);

		settings.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

			}
		});

		information.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				FragmentTransaction trans = ((TabHostChatType) getActivity()).getSupportFragmentManager()
						.beginTransaction();
				trans.addToBackStack(null);
				Fragment move = new InformationChatFragment();
				trans.replace(R.id.content_frame, move);
				trans.commit();

			}
		});

//		util.ShowFooterAgahi(activity, true, 10);
//
//		ImageView[] SendImages = util.inputCommentAndPickFile(getActivity());
//		ImageView send = SendImages[0];
//		ImageView pick = SendImages[1];
//
//		if (getActivity() != null) {
//			send = util.ShowFooterAgahi(getActivity(), true, -1);
//
//			send.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View arg0) {
//
//				}
//			});
//
//		}
//
		return convertView;
//	}
//
//	private void InputComment() {
//
	}

}
