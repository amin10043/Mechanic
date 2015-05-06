package com.project.mechanic.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mechanic.R;
import com.project.mechanic.entity.Ticket;
import com.project.mechanic.fragment.PersianDate;
import com.project.mechanic.model.DataBaseAdapter;

public class FavoriteListAdapter extends ArrayAdapter<Ticket> {

	Context context;
	List<Ticket> list;
	int[] imageId;
	Ticket tempItem;
	DataBaseAdapter adapter;
	int ProvinceId;

	public FavoriteListAdapter(Context context, int resource,
			List<Ticket> objact, int ProvinceId) {
		super(context, resource, objact);

		this.context = context;
		this.list = objact;
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
		TextView txttitle = (TextView) convertView
				.findViewById(R.id.row_favorite_title);
		// ImageButton imgtamas = (ImageButton) convertView
		// .findViewById(R.id.img_tamas_favorite);
		// ImageButton imgdelete = (ImageButton) convertView
		// .findViewById(R.id.img_delete_favorite);
		// ImageButton imgshare = (ImageButton) convertView
		// .findViewById(R.id.img_share_favorite);

		PersianDate date = new PersianDate();
		tempItem = list.get(position);

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
		// convertView.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		//
		// RelativeLayout parentlayout = (RelativeLayout) v;
		// TextView txtName = (TextView) parentlayout
		// .findViewById(R.id.row_favorite_title);
		// String item = txtName.getText().toString();
		// int id = 0;
		// for (Ticket Ticket : list) {
		//
		// if (item.equals(Ticket.getTitle())) {
		// // check authentication and authorization
		// id = Ticket.getId();
		// }
		// }

		// FragmentTransaction trans = ((MainActivity) context)
		// .getSupportFragmentManager().beginTransaction();
		// ShowAdFragment fragment = new ShowAdFragment();
		// Bundle bundle = new Bundle();
		// bundle.putString("Id", String.valueOf(id));
		// if (ProvinceId >= 0)
		// bundle.putString("ProID", String.valueOf(ProvinceId));
		// fragment.setArguments(bundle);
		// trans.replace(R.id.content_frame, fragment);
		// trans.addToBackStack(null);
		// trans.commit();
		//
		// }
		// });

		return convertView;

	}
}
