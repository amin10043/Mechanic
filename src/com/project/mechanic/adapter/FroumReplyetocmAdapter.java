package com.project.mechanic.adapter;

import java.util.List;

import com.project.mechanic.entity.Comment;

import android.content.Context;
import android.widget.ArrayAdapter;



public class FroumReplyetocmAdapter extends ArrayAdapter<Comment> {

	public FroumReplyetocmAdapter(Context context, int textViewResourceId,
			List<Comment> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
	}

}
