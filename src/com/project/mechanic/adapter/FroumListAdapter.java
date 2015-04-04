package com.project.mechanic.adapter;

import java.util.List;

import com.project.mechanic.R;
import com.project.mechanic.entity.Comment;
import com.project.mechanic.row_items.FroumItem;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;



public class FroumListAdapter  extends ArrayAdapter<Comment>{
	

	Context context;
	List<Comment> list;

	public FroumListAdapter(Context context, int resource,
			List<Comment> objects) {
		super(context, resource, objects);

		this.context = context;
		this.list = objects;
		
	}
	

	
	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater myInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		convertView = myInflater.inflate(R.layout.froumcmtitem, parent, false);

		TextView txt1 = (TextView) convertView.findViewById(R.id.rawCmttxt);
		TextView txt2 = (TextView) convertView.findViewById(R.id.rawUsernamecmttxt);
		
	
	    Comment person1 = list.get(position);
		
		txt1.setText(person1.getDescription());
		txt2.setText("zahra bidi");
		return convertView;
	}

}
