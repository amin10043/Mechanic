package com.project.mechanic.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mechanic.R;
import com.project.mechanic.entity.CommentInFroum;
import com.project.mechanic.entity.Users;
import com.project.mechanic.fragment.DialogcmtInfroum;
import com.project.mechanic.fragment.FroumFragment;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class FroumListAdapter extends ArrayAdapter<CommentInFroum> {

	Context context;
	List<CommentInFroum> list;
	DataBaseAdapter adapter;
	private ImageButton CmtLike;
	private ImageButton CmtDisLike;
	private ImageButton Replytocm;
	private TextView NumofCmtLike;
	private TextView NumofCmtDisLike;
	private ImageView Userimage;
	FroumFragment froumfragment;
	Utility util;

	public FroumListAdapter(Context context, int resource,
			List<CommentInFroum> objects, FroumFragment f) {
		super(context, resource, objects);

		this.context = context;
		this.list = objects;
		adapter = new DataBaseAdapter(context);
		util = new Utility(context);
		this.froumfragment = f;

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
		Userimage = (ImageView) convertView
				.findViewById(R.id.imgvCmtuser_Froumfragment);
		CommentInFroum comment = list.get(position);
		adapter.open();
		Users x = adapter.getUserbyid(comment.getUserid());
		adapter.close();

		txt1.setText(comment.getDesk());
		txt2.setText(x.getName());
		txt3.setText(x.getPhonenumber());
		byte[] blob = x.getImage();
		Bitmap bmp = BitmapFactory.decodeByteArray(blob, 0, blob.length);

		RelativeLayout rl = (RelativeLayout) convertView
				.findViewById(R.id.rlfroumrl);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				rl.getLayoutParams());

		lp.width = util.getScreenwidth() / 7;
		lp.height = util.getScreenwidth() / 7;
		lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

		// Userimage.getLayoutParams().height = 100;
		// Userimage.getLayoutParams().width = 100;
		// Userimage.requestLayout();
		Userimage.setImageBitmap(bmp);
		Userimage.setLayoutParams(lp);
		CommentInFroum d = list.get(position);
		NumofCmtLike.setText(d.getNumOfLike());
		NumofCmtDisLike.setText(d.getNumOfDislike());
		ListView lstReply = (ListView) convertView
				.findViewById(R.id.lstReplytoCm);
		FroumReplyetocmAdapter ReplyAdapter = new FroumReplyetocmAdapter(
				context, R.layout.raw_froumcmt, list, null);
		lstReply.setAdapter(ReplyAdapter);
		resizeListView(lstReply);

		CmtLike.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				LinearLayout parentlayout = (LinearLayout) v.getParent()
						.getParent().getParent();
				View view = parentlayout.findViewById(R.id.rawCmttxt);
				View view2 = parentlayout
						.findViewById(R.id.txtNumofLike_RawCmtFroum);
				TextView txtlike = (TextView) view2;
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
				Utility utility = new Utility(context);
				Users user = new Users();
				user = utility.getCurrentUser();
				int userid = user.getId();

				adapter.insertCmtLikebyid(id, f, userid);
				adapter.insertLikeInCommentToDb(userid, 1, id);

				a = adapter.getCommentInFroumbyID(id);
				txtlike.setText(a.getNumOfLike());
				adapter.close();

			}
		});

		CmtDisLike.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				LinearLayout parentlayout = (LinearLayout) v.getParent()
						.getParent().getParent();
				View view = parentlayout.findViewById(R.id.rawCmttxt);
				View view2 = parentlayout
						.findViewById(R.id.txtNumofDislike_RawCmtFroum);
				TextView x = (TextView) view;
				TextView disliketxt = (TextView) view2;
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
				Utility utility = new Utility(context);
				Users user = new Users();
				user = utility.getCurrentUser();
				int userid = user.getId();

				adapter.insertCmtDisLikebyid(id, f, userid);
				adapter.insertLikeInCommentToDb(userid, 0, id);
				a = adapter.getCommentInFroumbyID(id);
				disliketxt.setText(a.getNumOfDislike());
				adapter.close();

			}
		});

		Replytocm = (ImageButton) convertView.findViewById(R.id.imgvReplytoCm);
		Replytocm.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				LinearLayout parentlayout = (LinearLayout) v.getParent()
						.getParent();
				View view = parentlayout.findViewById(R.id.rawCmttxt);
				TextView x = (TextView) view;
				String item = x.getText().toString();
				int id = 0;
				for (CommentInFroum listItem : list) {
					if (item.equals(listItem.getDesk())) {

						id = listItem.getId();
					}
				}

				DialogcmtInfroum dialog = new DialogcmtInfroum(froumfragment,
						id, context, -1, R.layout.dialog_addcomment);
				dialog.show();

			}

		});

		return convertView;
	}

	private void resizeListView(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = listView.getPaddingTop()
				+ listView.getPaddingBottom();
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);

			if (listItem instanceof ViewGroup) {
				listItem.setLayoutParams(new LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			}
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

}
