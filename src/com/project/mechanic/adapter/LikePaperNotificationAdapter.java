package com.project.mechanic.adapter;

import java.util.ArrayList;
import java.util.List;

import com.project.mechanic.R;
import com.project.mechanic.entity.LikeInPaper;
import com.project.mechanic.entity.Users;
import com.project.mechanic.model.DataBaseAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class LikePaperNotificationAdapter extends ArrayAdapter<LikeInPaper> {
	
	Context context;
	List<LikeInPaper> mylist2;
	DataBaseAdapter dbadapter;

	public LikePaperNotificationAdapter(Context context, int resource,ArrayList<LikeInPaper>mylist2) {
		super(context, resource);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.mylist2  = mylist2;
		dbadapter =new DataBaseAdapter(context);
	}


	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater myInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = myInflater.inflate(R.layout.row_notification_list, null);
		
		
		TextView txt = (TextView) convertView
				.findViewById(R.id.main_text_notification);
		LikeInPaper c=mylist2.get(position);
		dbadapter.open();
		int m=c.getUserid();
		
		

		Users u =dbadapter.getUserbyid(m);

		txt.setText(u.getName());

		dbadapter.close();
		
		
		
		
		
		return convertView;
	}

}
	


