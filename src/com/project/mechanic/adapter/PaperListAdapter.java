package com.project.mechanic.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.entity.CommentInPaper;
import com.project.mechanic.entity.Users;
import com.project.mechanic.fragment.DisplayPersonalInformationFragment;
import com.project.mechanic.fragment.PaperFragment;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class PaperListAdapter extends ArrayAdapter<CommentInPaper> {

	Context context;
	List<CommentInPaper> list;
	DataBaseAdapter adapter;
	Fragment f;
	Utility util;
	private PaperFragment Paperfragment;
	int userId;

	public PaperListAdapter(Context context, int resource,
			List<CommentInPaper> objects, PaperFragment f) {
		super(context, resource, objects);

		this.context = context;
		this.list = objects;
		adapter = new DataBaseAdapter(context);
		util = new Utility(context);
		this.Paperfragment = f;

	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		LayoutInflater myInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		convertView = myInflater
				.inflate(R.layout.row_child_item, parent, false);
		adapter = new DataBaseAdapter(context);

		TextView txtcmt = (TextView) convertView
				.findViewById(R.id.reply_txt_child);
		TextView txtuser = (TextView) convertView
				.findViewById(R.id.name_replyed);
		TextView txtdate = (TextView) convertView
				.findViewById(R.id.date_replyed);
		ImageButton profilepic = (ImageButton) convertView
				.findViewById(R.id.icon_reply_comment);

		CommentInPaper comment = list.get(position);

		adapter.open();
		RelativeLayout rl = (RelativeLayout) convertView
				.findViewById(R.id.main_icon_reply);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				rl.getLayoutParams());

		lp.width = util.getScreenwidth() / 7;
		lp.height = util.getScreenwidth() / 7;
		lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		lp.setMargins(5, 5, 5, 5);
		Users user = adapter.getUserbyid(comment.getUserid());
		//userId=user.getId();
		if (user.getImage() == null) {
			profilepic.setImageResource(R.drawable.no_img_profile);
			profilepic.setLayoutParams(lp);

		} else {

			byte[] byteImageProfile = user.getImage();

			Bitmap bmp = BitmapFactory.decodeByteArray(byteImageProfile, 0,
					byteImageProfile.length);

			profilepic.setImageBitmap(util.getRoundedCornerBitmap(bmp, 50));

			profilepic.setLayoutParams(lp);
		}
		adapter.close();
		profilepic.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				adapter.open();
				CommentInPaper comment = list.get(position);
				Users user = adapter.getUserbyid(comment.getUserid());
				userId=user.getId();
				FragmentTransaction trans = ((MainActivity) context)
						.getSupportFragmentManager().beginTransaction();
				DisplayPersonalInformationFragment fragment = new DisplayPersonalInformationFragment();
				Bundle bundle = new Bundle();
				bundle.putInt("userId", userId);
				fragment.setArguments(bundle);
				trans.replace(R.id.content_frame, fragment);
				trans.commit();
				Toast.makeText(context, "PaperListAdapter",
						Toast.LENGTH_SHORT).show();
				
			}
		});
		txtcmt.setText(comment.getDescription());
		txtuser.setText(user.getName());
		txtdate.setText(util.getPersianDate(comment.getDatetime()));
		return convertView;
	}
}
