package com.project.mechanic.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.mechanic.R;
import com.project.mechanic.entity.CommentInFroum;
import com.project.mechanic.entity.Users;
import com.project.mechanic.model.DataBaseAdapter;

public class FroumListAdapter extends ArrayAdapter<CommentInFroum> {

	Context context;
	List<CommentInFroum> list;
	DataBaseAdapter adapter;
	private ImageButton CmtLike;
	private ImageButton CmtDisLike;
	private TextView NumofCmtLike;
	private TextView NumofCmtDisLike;
	int id = 0;

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
		adapter = new DataBaseAdapter(context);

		final TextView txt1 = (TextView) convertView
				.findViewById(R.id.rawCmttxt);
		TextView txt2 = (TextView) convertView
				.findViewById(R.id.rawUsernamecmttxt_cmt);
		TextView txt3 = (TextView) convertView
				.findViewById(R.id.txtPhonenumber_CmtFroum);
		CmtLike = (ImageButton) convertView
				.findViewById(R.id.imgbtnLike_RawCmtFroum);
		CmtDisLike = (ImageButton) convertView
				.findViewById(R.id.imgbtnDisLike_RawCmtFroum);
		NumofCmtLike = (TextView) convertView
				.findViewById(R.id.txtNumofLike_RawCmtFroum);
		NumofCmtDisLike = (TextView) convertView
				.findViewById(R.id.txtNumofDislike_RawCmtFroum);
		CommentInFroum comment = list.get(position);
		adapter.open();
		Users x = adapter.getUsernamebyid(comment.getUserid());
		adapter.close();

		txt1.setText(comment.getDescription());
		txt2.setText(x.getName());
		txt3.setText(x.getPhonennumber());

		CmtLike.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				LinearLayout parentlayout = (LinearLayout) v.getParent()
						.getParent().getParent();
				View item = parentlayout.findViewById(R.id.rawCmttxt);
				for (CommentInFroum listItem : list) {
					if (item.equals(listItem.getDescription())) {

						id = listItem.getId();
					}
				}

				adapter.open();

				CommentInFroum a = null;
				String c = a.getNumofLike();
				String k = c + 1;
				adapter.insertCmtLikebyid(id, k);
				NumofCmtLike.setText(a.getNumofLike());
				adapter.close();

			}
		});

		CmtDisLike.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				LinearLayout parentlayout = (LinearLayout) v.getParent()
						.getParent().getParent();
				View item = parentlayout.findViewById(R.id.rawCmttxt);
				for (CommentInFroum listItem : list) {
					if (item.equals(listItem.getDescription())) {

						id = listItem.getId();
					}
				}
				adapter.open();
				CommentInFroum a = null;
				String c = a.getNumofDisLike();
				String k = c + 1;
				adapter.insertCmtDisLikebyid(id, k);
				NumofCmtDisLike.setText(a.getNumofDisLike());
				adapter.close();

			}
		});
		return convertView;
	}
}
