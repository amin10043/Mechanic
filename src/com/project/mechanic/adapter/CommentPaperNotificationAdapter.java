package com.project.mechanic.adapter;

import java.util.List;


import com.project.mechanic.R;
import com.project.mechanic.entity.CommentInPaper;
import com.project.mechanic.entity.Users;
import com.project.mechanic.model.DataBaseAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CommentPaperNotificationAdapter extends ArrayAdapter<CommentInPaper>{
	Context context;
	List<CommentInPaper> mylist2;
	DataBaseAdapter dbadapter;


	public CommentPaperNotificationAdapter( Context context,int resource,List<CommentInPaper> list) {
			super(context,resource,list); {
		
		// TODO Auto-generated constructor stub
				this.context = context;
				this.mylist2  = list;
				dbadapter =new DataBaseAdapter(context);
				
			}
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
				CommentInPaper d = mylist2.get(position);
				String v = d.getDescription();
				txt.setText(v);
				TextView txt1 = (TextView) convertView
						.findViewById(R.id.datenotif);
				String v1 = d.getDatetime();
				txt1.setText(v1);
				
				
				
				
				TextView txt2 = (TextView) convertView
						.findViewById(R.id.namenotif);
				dbadapter.open();
				int v2=d.getUserid();
				//List<Users> mylist2=(List<Users>) dbadapter.getUserById(m2);
				

				Users u =dbadapter.getUserbyid(v2);

				txt2.setText(u.getName());

				dbadapter.close();

				

				

//				CommentInFroum comment = list.get(position);
//				Users x = adapter.getUserbyid(comment.getUserid());

				return convertView;
			}

	

	}
