package com.project.mechanic.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mechanic.R;
import com.project.mechanic.entity.Ticket;
import com.project.mechanic.fragment.Favorite_Fragment;
import com.project.mechanic.fragment.PersianDate;
import com.project.mechanic.fragment.dialog_show_data;
import com.project.mechanic.model.DataBaseAdapter;

public class FavoriteListAdapter extends ArrayAdapter<Ticket> {

	Context context;
	List<Ticket> list;
	int[] imageId;
	Ticket tempItem;
	Fragment fragment;
	DataBaseAdapter adapter;
	int ProvinceId;
	private dialog_show_data dialog;
	int idticket;
	int proID;

	public FavoriteListAdapter(Context context, int resource,
			List<Ticket> objact, Fragment fragment) {
		super(context, resource, objact);

		this.context = context;
		this.list = objact;
		this.fragment = fragment;
		this.ProvinceId = ProvinceId;
		adapter = new DataBaseAdapter(context);

	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater myInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		convertView = myInflater.inflate(R.layout.row_favorite, parent, false);

		ImageView imgView = (ImageView) convertView
				.findViewById(R.id.row_favorite_img);
		TextView txtdesc = (TextView) convertView
				.findViewById(R.id.text_favorite_desc);
		TextView txtemail = (TextView) convertView
				.findViewById(R.id.dialog_show_email);
		TextView txtmobile = (TextView) convertView
				.findViewById(R.id.dialog_show_mobile);
		TextView txtphone = (TextView) convertView
				.findViewById(R.id.dialog_show_phone);
		TextView txtfax = (TextView) convertView
				.findViewById(R.id.dialog_show_fax);
		TextView txttitle = (TextView) convertView
				.findViewById(R.id.row_favorite_title);
		ImageButton imgtamas = (ImageButton) convertView
				.findViewById(R.id.img_tamas_favorite);
		ImageButton imgdelete = (ImageButton) convertView
				.findViewById(R.id.img_delete_favorite);
		ImageButton imgshare = (ImageButton) convertView
				.findViewById(R.id.img_share_favorite);

		PersianDate date = new PersianDate();
		tempItem = list.get(position);
		idticket = tempItem.getId();
		txttitle.setText(tempItem.getTitle());
		txtdesc.setText(tempItem.getDesc());
		byte[] bitmapbyte = tempItem.getImage();
		if (bitmapbyte != null) {
			Bitmap bmp = BitmapFactory.decodeByteArray(bitmapbyte, 0,
					bitmapbyte.length);
			imgView.setImageBitmap(bmp);
		}
		Typeface typeFace = Typeface.createFromAsset(context.getAssets(),
				"fonts/BROYA.TTF");
		txtdesc.setTypeface(typeFace);

		imgshare.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent sharingIntent = new Intent(
						android.content.Intent.ACTION_SEND);
				sharingIntent.setType("text/plain");
				String shareBody = "Here is the share content body";
				sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
						"Subject Here");
				sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
						shareBody);
				context.startActivity(Intent.createChooser(sharingIntent,
						"اشتراک از طریق"));
			}
		});
		imgtamas.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog = new dialog_show_data(context,
						R.layout.dialog_show_data, FavoriteListAdapter.this,
						idticket);
				dialog.setTitle(R.string.txtanadedite);

				dialog.show();

			}
		});
		imgdelete.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				adapter.open();
				adapter.deletebyIdTicket(idticket);
				adapter.close();
				((Favorite_Fragment) fragment).updateView();

			}
		});

		return convertView;

	}

}
