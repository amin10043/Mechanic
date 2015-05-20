package com.project.mechanic.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.entity.Paper;
import com.project.mechanic.fragment.DialogcmtInPaper;
import com.project.mechanic.fragment.PaperFragment;
import com.project.mechanic.fragment.PersianDate;
import com.project.mechanic.model.DataBaseAdapter;

public class PapertitleListAdapter extends ArrayAdapter<Paper> {

	Context context;
	List<Paper> mylist;
	DataBaseAdapter adapter;
	private TextView NumofComment;
	private TextView NumofLike;
	private TextView DateView;
	int id;

	public PapertitleListAdapter(Context context, int resource,
			List<Paper> objects) {
		super(context, resource, objects);
		this.context = context;
		this.mylist = objects;
		adapter = new DataBaseAdapter(context);

	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		LayoutInflater myInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		adapter = new DataBaseAdapter(context);

		convertView = myInflater
				.inflate(R.layout.raw_papertitle, parent, false);

		final TextView txt1 = (TextView) convertView
				.findViewById(R.id.rowtitlepaper);
		TextView txt2 = (TextView) convertView
				.findViewById(R.id.rowdescriptionpaper);
		TextView txt3 = (TextView) convertView.findViewById(R.id.authorname);
		NumofComment = (TextView) convertView.findViewById(R.id.NumOfComment);
		NumofLike = (TextView) convertView.findViewById(R.id.NumOfLike);
		DateView = (TextView) convertView.findViewById(R.id.txtDate);
		PersianDate p = new PersianDate();
		DateView.setText(p.todayShamsi());
		Paper person1 = mylist.get(position);

		txt1.setText(person1.getTitle());
		txt2.setText(person1.getContext());
		txt3.setText("Maryam");
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
