package com.project.mechanic.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.entity.Ticket;
import com.project.mechanic.fragment.DialogLongClick;
import com.project.mechanic.fragment.ShowAdFragment;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class AnadListAdapter extends ArrayAdapter<Ticket> {

	Context context;
	List<Ticket> list;
	int[] imageId;
	Ticket tempItem;
	DataBaseAdapter adapter;
	int ProvinceId;
	Utility util;
	Fragment fragment;
	RelativeLayout.LayoutParams params;
	boolean IsShow;
	ProgressBar LoadingProgress;


	public AnadListAdapter(Context context, int resource, List<Ticket> objact,
			int ProvinceId, Fragment fragment , boolean IsShow) {
		super(context, resource, objact);

		this.context = context;
		this.list = objact;
		this.ProvinceId = ProvinceId;
		adapter = new DataBaseAdapter(context);
		util = new Utility(context);
		this.fragment = fragment;
		this.IsShow = IsShow;


	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater myInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		convertView = myInflater.inflate(R.layout.row_anad, parent, false);

		TextView txtdate = (TextView) convertView
				.findViewById(R.id.text_favorite_desc);
		TextView txtName = (TextView) convertView
				.findViewById(R.id.row_favorite_title);
		TextView txtDesc = (TextView) convertView
				.findViewById(R.id.row_anad_txt2);
		ImageView img2 = (ImageView) convertView
				.findViewById(R.id.row_favorite_img);
		
		LoadingProgress = (ProgressBar)convertView.findViewById(R.id.progressBar1);

		tempItem = list.get(position);
		txtdate.setText(util.getPersianDate(tempItem.getDate()));
		txtName.setText(tempItem.getTitle());
		if (tempItem.getDesc() !=null && !tempItem.getDesc().equals("null"))
			txtDesc.setText(tempItem.getDesc() + " ... ");
		byte[] bitmapbyte = tempItem.getImage();
		if (tempItem.getSeenBefore() > 0) {
			txtName.setTextColor(Color.GRAY);
			txtDesc.setTextColor(Color.GRAY);
			txtdate.setTextColor(Color.GRAY);

		}

		RelativeLayout llkj = (RelativeLayout) convertView
				.findViewById(R.id.layoutmnb);
		params = new RelativeLayout.LayoutParams(llkj.getLayoutParams());
		params.width = util.getScreenwidth() / 5;
		params.height = util.getScreenwidth() / 5;
		params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		params.setMargins(1, 1, 1, 1);
		
		
		String pathProfile = tempItem.getImagePath();
		Bitmap profileImage = BitmapFactory.decodeFile(pathProfile);

		
		if (profileImage != null) {

			img2.setImageBitmap(profileImage);
			img2.setLayoutParams(params);
			LoadingProgress.setVisibility(View.GONE);


		} else {
			img2.setImageResource(R.drawable.no_img_profile);
			img2.setLayoutParams(params);
		}
		if (IsShow==false)
			LoadingProgress.setVisibility(View.GONE);

//		if (bitmapbyte != null) {
//			Bitmap bmp = BitmapFactory.decodeByteArray(bitmapbyte, 0,
//					bitmapbyte.length);
//			img2.setImageBitmap(util.getRoundedCornerBitmap(bmp, 50));
//			img2.setLayoutParams(params);
//		} else {
//			img2.setBackgroundResource(R.drawable.no_img_profile);
//			img2.setLayoutParams(params);
//
//		}

		Typeface typeFace = Typeface.createFromAsset(context.getAssets(),
				"fonts/BROYA.TTF");
		txtName.setTypeface(typeFace);

		ImageView reaport = (ImageView) convertView
				.findViewById(R.id.reportImage);

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				RelativeLayout parentlayout = (RelativeLayout) v;
				TextView txtName = (TextView) parentlayout
						.findViewById(R.id.row_favorite_title);
				String item = txtName.getText().toString();
				int id = 0;
				for (Ticket Ticket : list) {

					if (item.equals(Ticket.getTitle())) {
						// check authentication and authorization
						id = Ticket.getId();
					}
				}

				FragmentTransaction trans = ((MainActivity) context)
						.getSupportFragmentManager().beginTransaction();
				ShowAdFragment fragment = new ShowAdFragment();
				Bundle bundle = new Bundle();
				bundle.putString("Id", String.valueOf(id));
				if (ProvinceId >= 0)
					bundle.putString("ProID", String.valueOf(ProvinceId));
				fragment.setArguments(bundle);
				trans.replace(R.id.content_frame, fragment);
				trans.addToBackStack(null);
				trans.commit();

				adapter.open();
				adapter.SetSeen("Ticket", id, "1");
				adapter.close();

			}
		});

		reaport.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				RelativeLayout parentlayout = (RelativeLayout) v.getParent();
				TextView txtName = (TextView) parentlayout
						.findViewById(R.id.row_favorite_title);
				String item = txtName.getText().toString();
				int id = 0;
				int u = 0;
				String t = "";
				for (Ticket Ticket : list) {

					if (item.equals(Ticket.getTitle())) {
						// check authentication and authorization
						id = Ticket.getId();
						u = Ticket.getUserId();
						t = Ticket.getDesc();

					}
				}

				DialogLongClick dia = new DialogLongClick(context, 3, u, id,
						fragment, t);
				Toast.makeText(context, id + "", 0).show();

				WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
				lp.copyFrom(dia.getWindow().getAttributes());
				lp.width = (int) (util.getScreenwidth() / 1.5);
				lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
				;
				dia.show();

				dia.getWindow().setAttributes(lp);
				dia.getWindow().setBackgroundDrawable(
						new ColorDrawable(android.graphics.Color.TRANSPARENT));
			}
		});

		return convertView;

	}
}
