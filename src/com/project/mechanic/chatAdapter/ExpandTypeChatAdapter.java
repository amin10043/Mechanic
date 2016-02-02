package com.project.mechanic.chatAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.chat.ChatFragment;
import com.project.mechanic.fragment.ExecutertypeFragment;
import com.project.mechanic.utility.Utility;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ExpandTypeChatAdapter extends BaseExpandableListAdapter {
	Context context;
	ArrayList<String> parentItems;
	HashMap<String, List<String>> collection;
	Utility util;

	public ExpandTypeChatAdapter(Context context, ArrayList<String> parentItems,
			HashMap<String, List<String>> childItems) {
		this.context = context;
		this.parentItems = parentItems;
		this.collection = childItems;
		util = new Utility(context);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return collection.get(parentItems.get(groupPosition)).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView,
			ViewGroup parent) {

		LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = infalInflater.inflate(R.layout.row_child_type_chat, null);

		TextView titleChild = (TextView) convertView.findViewById(R.id.Rowobjecttxt);
		TextView dateChat = (TextView) convertView.findViewById(R.id.datechat);
		ImageView imagChild = (ImageView) convertView.findViewById(R.id.row_favorite_img);
		
		titleChild.setTypeface(util.SetFontCasablanca());

		titleChild.setText(getChild(groupPosition, childPosition) + "");
		
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				FragmentTransaction trans = ((MainActivity) context)
						.getSupportFragmentManager().beginTransaction();
				trans.addToBackStack(null);
				Fragment move = new ChatFragment((Activity) context,1);
				trans.replace(R.id.content_frame, move);
				trans.commit();
				
			}
		});

		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return collection.get(parentItems.get(groupPosition)).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return parentItems.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return parentItems.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public View getGroupView(final int groupPosition, final boolean isExpanded, View convertView, ViewGroup parent) {

		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.row_group_type_chat, null);
		}
		TextView groupName = (TextView) convertView.findViewById(R.id.row_berand_txt);

		groupName.setText(parentItems.get(groupPosition));
		groupName.setTypeface(util.SetFontCasablanca());

		return convertView;
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
