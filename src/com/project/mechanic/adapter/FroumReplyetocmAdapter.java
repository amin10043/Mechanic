package com.project.mechanic.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mechanic.R;
import com.project.mechanic.entity.CommentInFroum;
import com.project.mechanic.entity.Users;
import com.project.mechanic.fragment.FroumFragment;
import com.project.mechanic.model.DataBaseAdapter;

public class FroumReplyetocmAdapter extends ArrayAdapter<CommentInFroum> {

	Context context;
	List<CommentInFroum> list;
	DataBaseAdapter adapter;
	private ImageButton CmtLike;
	private ImageButton CmtDisLike;
	private ImageButton Replytocm;
	private ImageView Userimage;
	FroumFragment froumfragment;

	public FroumReplyetocmAdapter(Context context, int resource,
			List<CommentInFroum> objects, FroumFragment f) {
		super(context, resource, objects);
		this.context = context;
		this.list = objects;
		adapter = new DataBaseAdapter(context);
		this.froumfragment = f;

	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		LayoutInflater myInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		convertView = myInflater.inflate(R.layout.row_froum_reply_cmt, parent,
				false);
		adapter = new DataBaseAdapter(context);

		final TextView txt1 = (TextView) convertView
				.findViewById(R.id.rawCmttxt);
		TextView txt2 = (TextView) convertView
				.findViewById(R.id.rawUsernamecmttxt_cmt);

		Userimage = (ImageView) convertView
				.findViewById(R.id.imgvCmtuser_Froumfragment);
		CommentInFroum comment = list.get(position);
		adapter.open();
		Users x = adapter.getUserbyid(comment.getUserid());
		adapter.close();

		txt1.setText(comment.getDesk());
		txt2.setText(x.getName());
		byte[] blob = x.getImage();
		Bitmap bmp = BitmapFactory.decodeByteArray(blob, 0, blob.length);
		Userimage.getLayoutParams().height = 50;
		Userimage.getLayoutParams().width = 50;
		Userimage.requestLayout();
		Userimage.setImageBitmap(bmp);
		CommentInFroum d = list.get(position);
		// convertView.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		//
		// LinearLayout parentlayout = (LinearLayout) v;
		//
		// String item = txt1.getText().toString();
		// int id = 0;
		// for (CommentInFroum listItem : list) {
		// if (item.equals(listItem.getDesk())) {
		// // check authentication and authorization
		// id = listItem.getId();
		//
		// }
		// }
		//
		// FragmentTransaction trans = ((MainActivity) context)
		// .getSupportFragmentManager().beginTransaction();
		// FroumFragment fragment = new FroumFragment();
		// Bundle bundle = new Bundle();
		// bundle.putString("CommentID", String.valueOf(id));
		//
		// fragment.setArguments(bundle);
		// trans.replace(R.id.content_frame, fragment);
		// trans.commit();
		//
		// }
		//
		// });

		return convertView;
	}

}
