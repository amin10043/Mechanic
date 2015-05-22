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
import android.widget.Toast;

import com.project.mechanic.R;
import com.project.mechanic.entity.CommentInFroum;
import com.project.mechanic.entity.Users;
import com.project.mechanic.fragment.DialogcmtInfroum;
import com.project.mechanic.fragment.FroumFragment;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class FroumListAdapter extends ArrayAdapter<CommentInFroum> {

	Context context;
	List<CommentInFroum> list, replyList;
	DataBaseAdapter adapter;
	private ImageButton CmtLike, CmtDisLike, Replytocm;
	private ImageView Userimage;

	private TextView NumofCmtLike, NumofCmtDisLike, txt1, txt2, txt3;
	FroumFragment froumfragment;
	Utility util;
	RelativeLayout rl;

	int id = 0;

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

		// //////// start find view

		txt1 = (TextView) convertView.findViewById(R.id.rawCmttxt);
		txt2 = (TextView) convertView.findViewById(R.id.rawUsernamecmttxt_cmt);
		txt3 = (TextView) convertView
				.findViewById(R.id.txtPhonenumber_CmtFroum);

		CmtLike = (ImageButton) convertView
				.findViewById(R.id.imgbtnDisLike_RawCmtFroum);
		CmtDisLike = (ImageButton) convertView
				.findViewById(R.id.imgbtnLike_RawCmtFroum);

		Userimage = (ImageView) convertView
				.findViewById(R.id.imgvCmtuser_Froumfragment);

		Replytocm = (ImageButton) convertView.findViewById(R.id.imgvReplytoCm);

		NumofCmtLike = (TextView) convertView
				.findViewById(R.id.txtNumofLike_RawCmtFroum);
		NumofCmtDisLike = (TextView) convertView
				.findViewById(R.id.txtNumofDislike_RawCmtFroum);

		// //////// end find view

		CommentInFroum comment = list.get(position);
		adapter.open();
		Users x = adapter.getUserbyid(comment.getUserid());
		adapter.close();

		txt1.setText(comment.getDesk());
		txt2.setText(x.getName());
		txt3.setText(x.getPhonenumber());
		byte[] blob = x.getImage();
		Bitmap bmp = BitmapFactory.decodeByteArray(blob, 0, blob.length);

		rl = (RelativeLayout) convertView.findViewById(R.id.rlfroumrl);
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
		notifyDataSetChanged();

		ListView lstReply = (ListView) convertView
				.findViewById(R.id.lstReplytoCm);
		adapter.open();
		replyList = adapter.getCommentInFroumbyPaperid(d.getFroumid(),
				d.getId());
		adapter.close();
		FroumReplyetocmAdapter ReplyAdapter = new FroumReplyetocmAdapter(
				context, R.layout.raw_froumcmt, replyList, null);
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
				int s = a.getNumOfLike();
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

				for (CommentInFroum listItem : list) {
					if (item.equals(listItem.getDesk())) {

						id = listItem.getId();
					}
				}
				adapter.open();
				CommentInFroum a = list.get(position);
				int c = a.getNumOfDislike();
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

		Replytocm.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				LinearLayout parentlayout = (LinearLayout) v.getParent()
						.getParent();
				View view = parentlayout.findViewById(R.id.rawCmttxt);
				TextView x = (TextView) view;
				String item = x.getText().toString();
				int commentid = 0;
				for (CommentInFroum listItem : list) {
					if (item.equals(listItem.getDesk())) {

						commentid = listItem.getId();
					}
				}
				Toast.makeText(context, "f l adapter", Toast.LENGTH_SHORT)
						.show();
				DialogcmtInfroum dialog = new DialogcmtInfroum(froumfragment,
						commentid, context, froumfragment.getFroumId(),
						R.layout.dialog_addcomment);
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
		// params.height =
		// android.widget.LinearLayout.LayoutParams.WRAP_CONTENT;
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

}
