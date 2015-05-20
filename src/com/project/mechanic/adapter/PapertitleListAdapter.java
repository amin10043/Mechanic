package com.project.mechanic.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.entity.Paper;
import com.project.mechanic.entity.Users;
import com.project.mechanic.fragment.DialogcmtInPaper;
import com.project.mechanic.fragment.PaperFragment;
import com.project.mechanic.fragment.PersianDate;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class PapertitleListAdapter extends ArrayAdapter<Paper> {

	Context context;
	List<Paper> mylist;
	DataBaseAdapter adapter;
	private TextView NumofComment;
	private TextView NumofLike;
	private TextView DateView;
	int id;
	PersianDate p;
	Utility util;

	public PapertitleListAdapter(Context context, int resource,
			List<Paper> objects) {
		super(context, resource, objects);
		this.context = context;
		this.mylist = objects;
		adapter = new DataBaseAdapter(context);
		util = new Utility(context);
		p = new PersianDate();

	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		LayoutInflater myInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		convertView = myInflater
				.inflate(R.layout.raw_froumtitle, parent, false);

		// start find view

		final TextView txt1 = (TextView) convertView
				.findViewById(R.id.rowtitlepaper);
		TextView txt2 = (TextView) convertView
				.findViewById(R.id.rowdescriptionpaper);
		TextView txt3 = (TextView) convertView.findViewById(R.id.authorname);
		NumofComment = (TextView) convertView
				.findViewById(R.id.countCommentInEveryTopic);
		NumofLike = (TextView) convertView
				.findViewById(R.id.countLikeInFroumTitle);
		DateView = (TextView) convertView.findViewById(R.id.datetopicinFroum);
		ImageView iconProile = (ImageView) convertView
				.findViewById(R.id.iconfroumtitle);

		// end find view
		adapter.open();

		Paper person1 = mylist.get(position);

		Users x = adapter.getUserbyid(person1.getUserId());
		if (x.getImage() == null) {
			iconProile.setImageResource(R.drawable.no_img_profile);
		} else {

			byte[] byteImg = x.getImage();
			Bitmap bmp = BitmapFactory.decodeByteArray(byteImg, 0,
					byteImg.length);
			iconProile.setImageBitmap(bmp);

			RelativeLayout rl = (RelativeLayout) convertView
					.findViewById(R.id.topicTitleFroum);
			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
					rl.getLayoutParams());

			lp.width = util.getScreenwidth() / 7;
			lp.height = util.getScreenwidth() / 7;
			lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			lp.setMargins(5, 5, 5, 5);
			iconProile.setLayoutParams(lp);
			adapter.close();
		}
		adapter.close();

		DateView.setText(x.getDate());

		txt1.setText(person1.getTitle());
		txt2.setText(person1.getContext());
		txt3.setText(x.getName());
		adapter.open();

		NumofComment.setText(adapter.CommentInPaper_count(person1.getId())
				.toString());

		NumofLike
				.setText(adapter.LikeInPaper_count(person1.getId()).toString());
		adapter.close();

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				LinearLayout parentlayout = (LinearLayout) v;

				String item = ((TextView) parentlayout
						.findViewById(R.id.rowtitlepaper)).getText().toString();

				for (Paper listItem : mylist) {
					if (item.equals(listItem.getTitle())) {
						// check authentication and authorization
						id = listItem.getId();
					}
				}
				Toast.makeText(context, "send = " + id, Toast.LENGTH_SHORT)
						.show();

				FragmentTransaction trans = ((MainActivity) context)
						.getSupportFragmentManager().beginTransaction();
				PaperFragment fragment = new PaperFragment();
				Bundle bundle = new Bundle();
				bundle.putString("Id", String.valueOf(id));
				fragment.setArguments(bundle);

				DialogcmtInPaper dialog = new DialogcmtInPaper(null, context,
						R.layout.dialog_addcomment, id);
				Bundle bundle2 = new Bundle();
				bundle.putString("Id", String.valueOf(id));
				fragment.setArguments(bundle);

				trans.replace(R.id.content_frame, fragment);
				trans.commit();
			}

		});

		return convertView;
	}
}
