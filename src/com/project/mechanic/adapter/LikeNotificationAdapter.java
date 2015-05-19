package com.project.mechanic.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import com.project.mechanic.R;
import com.project.mechanic.entity.Object;
import com.project.mechanic.entity.Users;
import com.project.mechanic.model.DataBaseAdapter;



public class LikeNotificationAdapter extends ArrayAdapter<Object> { 
	Context context;
	List<Object> mylist;
	
	
	
	
	
	public LikeNotificationAdapter(Context context, int resource,List<Object>list) {
		super(context,resource,list);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.mylist  = list;
	}


	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater myInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = myInflater.inflate(R.layout.row_notification_list, null);
		
		
		TextView txt = (TextView) convertView
				.findViewById(R.id.main_text_notification);
		Object c=mylist.get(position);
		String m = c.getName();
		txt.setText(m);
		
		
		
		
		
		return convertView;
	}

}


		
		
		
		
		
		
	
