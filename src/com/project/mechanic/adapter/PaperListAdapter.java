package com.project.mechanic.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.project.mechanic.R;
import com.project.mechanic.entity.CommentInPaper;
import com.project.mechanic.model.DataBaseAdapter;

public class PaperListAdapter extends ArrayAdapter<CommentInPaper> {

	Context context;
	List<CommentInPaper> list;
	DataBaseAdapter adapter;
	Fragment f;

	public PaperListAdapter(Context context, int resource,
			List<CommentInPaper> objects) {
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

		convertView = myInflater.inflate(R.layout.raw_papercmt, parent, false);
		adapter = new DataBaseAdapter(context);

		TextView txtcmt = (TextView) convertView.findViewById(R.id.rawCmttxt);
		TextView txtuser = (TextView) convertView.findViewById(R.id.txtUser);
		TextView txtdate = (TextView) convertView.findViewById(R.id.txtDate);
		CommentInPaper comment = list.get(position);

		txtcmt.setText(comment.getCommentid());
		txtuser.setText(comment.getUserid());
		txtdate.setText(comment.getDatetime());
		return convertView;
	}
}
