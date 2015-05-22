package com.project.mechanic.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.project.mechanic.R;
import com.project.mechanic.entity.CommentInObject;
import com.project.mechanic.entity.Users;
import com.project.mechanic.model.DataBaseAdapter;

public class CommentObjectNotificationAdapter extends ArrayAdapter<CommentInObject>{
	
		Context context;
		List<CommentInObject> mylist1;
		DataBaseAdapter dbadapter;
		public CommentObjectNotificationAdapter(Context context,int resource,List<CommentInObject> list) {
			super(context,resource,list);
			// TODO Auto-generated constructor stub
			this.context = context;
			this.mylist1  = list;
			dbadapter =new DataBaseAdapter(context);
			
			
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater myInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = myInflater.inflate(R.layout.row_notification_list, null);

			// listcmnotification= (ListView)
			// convertView.findViewById(R.id.listcmnotification);
			// ImageButton iBtnmessage=(ImageButton)
			// convertView.findViewById(R.id.iBtnmessage);
			TextView txt = (TextView) convertView
					.findViewById(R.id.main_text_notification);
			CommentInObject c = mylist1.get(position);
			String n = c.getDescription();
			txt.setText(n);
			TextView txt1 = (TextView) convertView
					.findViewById(R.id.datenotif);
			String n1 = c.getDatetime();
			txt1.setText(n1);
			
			
			
			
			TextView txt2 = (TextView) convertView
					.findViewById(R.id.namenotif);
			dbadapter.open();
			int n2=c.getUserid();
			//List<Users> mylist2=(List<Users>) dbadapter.getUserById(m2);
			

			Users u =dbadapter.getUserbyid(n2);

			txt2.setText(u.getName());

			dbadapter.close();

			

			

//			CommentInFroum comment = list.get(position);
//			Users x = adapter.getUserbyid(comment.getUserid());

			return convertView;
		}

	}




