package com.project.mechanic.adapter;

import java.util.List;

import com.project.mechanic.R;
import com.project.mechanic.entity.Comment;
import com.project.mechanic.entity.CommentInFroum;
import com.project.mechanic.entity.Users;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.row_items.FroumItem;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;



public class FroumListAdapter  extends ArrayAdapter<CommentInFroum>{
	

	Context context;
	List<CommentInFroum> list;
	DataBaseAdapter adapter;

	public FroumListAdapter(Context context, int resource,
			List<CommentInFroum> objects) {
		super(context, resource, objects);

		this.context = context;
		this.list = objects;
		adapter = new DataBaseAdapter(context);
		
	}
	

	
	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater myInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		convertView = myInflater.inflate(R.layout.raw_froumcmt, parent, false);
		adapter= new DataBaseAdapter(context);

		TextView txt1 = (TextView) convertView.findViewById(R.id.rawCmttxt);
		TextView txt2 = (TextView) convertView.findViewById(R.id.rawUsernamecmttxt_cmt);
		TextView txt3 = (TextView) convertView.findViewById(R.id.txtPhonenumber_CmtFroum);
		CommentInFroum comment = list.get(position);
		adapter.open();
		Users x = adapter.getUsernamebyid(comment.getUserid());
		adapter.close();
	    
	   
		
		txt1.setText(comment.getDescription());
		txt2.setText(x.getName());
		txt3.setText(x.getPhonenumber());
		return convertView;
	}

}
