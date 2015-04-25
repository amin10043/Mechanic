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

	public FroumListAdapter(Context context, int resource,
			List<CommentInFroum> objects) {
		super(context, resource, objects);

		this.context = context;
		this.list = objects;
		adapter = new DataBaseAdapter(context);

	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

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
		CmtDisLike = (ImageButton) convertView
				.findViewById(R.id.imgbtnLike_RawCmtFroum);
		CmtLike = (ImageButton) convertView
				.findViewById(R.id.imgbtnDisLike_RawCmtFroum);
		NumofCmtLike = (TextView) convertView
				.findViewById(R.id.txtNumofLike_RawCmtFroum);
		NumofCmtDisLike = (TextView) convertView
				.findViewById(R.id.txtNumofDislike_RawCmtFroum);
		CommentInFroum comment = list.get(position);
		adapter.open();
		Users x = adapter.getUsernamebyid(comment.getUserid());
		adapter.close();

		txt1.setText(comment.getDesk());
		txt2.setText(x.getName());
		txt3.setText(x.getPhonennumber());
		CommentInFroum d = list.get(position);
		NumofCmtLike.setText(d.getNumOfLike());
		NumofCmtDisLike.setText(d.getNumOfDislike());

		CmtLike.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				LinearLayout parentlayout = (LinearLayout) v.getParent()
						.getParent().getParent();
				View view = parentlayout.findViewById(R.id.rawCmttxt);
				TextView x = (TextView) view;
				String item = x.getText().toString();
				int id = 0;
				for (CommentInFroum listItem : list) {
					if (item.equals(listItem.getDesk())) {

						id = listItem.getId();
					}
				}

				adapter.open();
				CommentInFroum a = list.get(position);
				String s = a.getNumOfLike();
				int c = Integer.valueOf(a.getNumOfLike());
				int k = c + 1;
				String f = String.valueOf(k);
				adapter.insertCmtLikebyid(id, f);
				NumofCmtLike.setText(a.getNumOfLike());
				adapter.close();

			}
		});

		CmtDisLike.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				LinearLayout parentlayout = (LinearLayout) v.getParent()
						.getParent().getParent();
				View view = parentlayout.findViewById(R.id.rawCmttxt);
				TextView x = (TextView) view;
				String item = x.getText().toString();
				int id = 0;
				for (CommentInFroum listItem : list) {
					if (item.equals(listItem.getDesk())) {

						id = listItem.getId();
					}
				}
				adapter.open();
				CommentInFroum a = list.get(position);
				String c = a.getNumOfDislike();
				int h = Integer.valueOf(a.getNumOfDislike());
				int k = h + 1;
				String f = String.valueOf(k);
				adapter.insertCmtDisLikebyid(id, f);
				NumofCmtDisLike.setText(a.getNumOfDislike());
				adapter.close();

			}
		});
		return convertView;
	}
}
