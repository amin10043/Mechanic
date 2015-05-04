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

public class IntroductionListAdapter extends ArrayAdapter<CommentInObject> {

	Context context;
	List<CommentInObject> list;
	DataBaseAdapter adapter;

	public IntroductionListAdapter(Context context, int resource,
			List<CommentInObject> objects) {
		super(context, resource, objects);

		this.context = context;
		this.list = objects;
		adapter = new DataBaseAdapter(context);

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			LayoutInflater myInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			convertView = myInflater.inflate(R.layout.raw_froumcmt, parent,
					false);
		}

		adapter = new DataBaseAdapter(context);

		TextView txt1 = (TextView) convertView.findViewById(R.id.rawCmttxt);
		TextView txt2 = (TextView) convertView.findViewById(R.id.rawUsernamecmttxt_cmt);
		TextView txt3 = (TextView) convertView.findViewById(R.id.txtPhonenumber_CmtFroum);
		
		CommentInObject Comment = list.get(position);
		adapter.open();
		Users x = adapter.getUserbyid(Comment.getUserid());
		adapter.close();

		

		txt1.setText(Comment.getDescription());
		txt2.setText(x.getName());
		txt3.setText(x.getPhonenumber());

		return convertView;

	}

	@Override
	public int getCount() {

		return list.size();
	}

	@Override
	public CommentInObject getItem(int position) {

		return list.get(position);
	}

}
