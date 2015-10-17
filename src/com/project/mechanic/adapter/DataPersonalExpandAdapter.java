package com.project.mechanic.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.project.mechanic.R;
import com.project.mechanic.entity.PersonalData;

public class DataPersonalExpandAdapter extends BaseExpandableListAdapter {
	Context context;
	ArrayList<String> parentItems;
	HashMap<String, List<PersonalData>> listDataChild;

	public DataPersonalExpandAdapter(Context context,
			ArrayList<String> parentItems,
			HashMap<String, List<PersonalData>> listDataChild) {

		this.context = context;
		this.parentItems = parentItems;
		this.listDataChild = listDataChild;

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
		return listDataChild.get(parentItems.get(groupPosition)).get(
				childPosition);
	}

	@Override
	public int getChildType(int groupPosition, int childPosition) {

		int type = groupPosition;

		// switch (type) {
		// case 0:
		// type = 0;
		// break;
		// case 1:
		// type = 1;
		// break;
		// case 2:
		// type = 2;
		// break;
		// case 3:
		// type = 3;
		// break;
		//
		// default:
		// break;
		// }

		return 1;// type;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		// if (convertView == null) {
		LayoutInflater infalInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		// convertView = infalInflater.inflate(R.layout.row_personal_data,
		// null);
		//
		// RelativeLayout objectLayout = (RelativeLayout) convertView
		// .findViewById(R.id.brandLayout);
		// RelativeLayout ticketLayout = (RelativeLayout) convertView
		// .findViewById(R.id.ticketLayout);
		// LinearLayout froumPaperTitle = (LinearLayout) convertView
		// .findViewById(R.id.froumPaperTitle);
		//
		// if (groupPosition==0){
		// ticketLayout.setVisibility(View.GONE);
		// froumPaperTitle.setVisibility(View.GONE);
		//
		// }
		//
		// if (groupPosition==1){
		// objectLayout.setVisibility(View.GONE);
		// froumPaperTitle.setVisibility(View.GONE);
		//
		// }
		// if (groupPosition==2 || groupPosition==3){
		// ticketLayout.setVisibility(View.GONE);
		// objectLayout.setVisibility(View.GONE);
		//
		// }

		// ///////////////////////////////////
		if (("صفحات").equals(parentItems.get(groupPosition))) {
			convertView = infalInflater.inflate(R.layout.row_object, null);
			// update your views here
		} else if (("آگهی ها").equals(parentItems.get(groupPosition))) {
			convertView = infalInflater.inflate(R.layout.row_anad, null);
			// update your views here
		} else if (("مقالات").equals(parentItems.get(groupPosition))) {
			convertView = infalInflater.inflate(R.layout.raw_froumtitle, null);
			// update your views here
		} else if (("تالار گفتگو").equals(parentItems.get(groupPosition))) {
			convertView = infalInflater.inflate(R.layout.raw_froumtitle, null);
			// update your views here
		}

		// //////////////////////////////////////////

		// int itemType = getChildType(groupPosition, childPosition);
		//
		//
		// switch (itemType) {
		// case 0:
		// convertView = infalInflater.inflate(R.layout.row_object, null);
		//
		// break;
		//
		// case 1:
		// convertView = infalInflater.inflate(R.layout.row_anad, null);
		//
		// break;
		//
		// case 2:
		// convertView = infalInflater.inflate(R.layout.raw_froumtitle,
		// null);
		// break;
		//
		// case 3:
		// convertView = infalInflater.inflate(R.layout.raw_froumtitle,
		// null);
		// break;
		//
		// default:
		// break;
		// }

		// }
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
	public View getGroupView(final int groupPosition, final boolean isExpanded,
			View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.row_berand, null);
		}
		TextView titleGroup = (TextView) convertView
				.findViewById(R.id.row_berand_txt);
		titleGroup.setText(parentItems.get(groupPosition));

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
