package com.project.mechanic.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.project.mechanic.R;
import com.project.mechanic.entity.Anad;
import com.project.mechanic.model.DataBaseAdapter;

public class AnadImgListAdapter extends ArrayAdapter<Anad> {

	Context context;
	List<Anad> list;
	Anad tempItem;
	DataBaseAdapter adapter;

	public AnadImgListAdapter(Context context, int resource, List<Anad> objact) {
		super(context, resource, objact);

		this.context = context;
		this.list = objact;
		adapter = new DataBaseAdapter(context);

	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater myInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		convertView = myInflater.inflate(R.layout.row_anad_img, parent, false);

		ImageView img = (ImageView) convertView.findViewById(R.id.img_anad);

		tempItem = list.get(position);
		byte[] bitmapbyte = tempItem.getImage();
		if (bitmapbyte != null) {
			Bitmap bmp = BitmapFactory.decodeByteArray(bitmapbyte, 0,
					bitmapbyte.length);
			img.setImageBitmap(bmp);
		}
		// byte[] blob = ((Anad) anadlist).getImage();
		// Bitmap bmp = BitmapFactory.decodeByteArray(blob, 0, blob.length);
		// img.setImageBitmap(bmp);

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// RelativeLayout parentlayout = (RelativeLayout) v;
				// TextView txtName = (TextView) parentlayout
				// .findViewById(R.id.row_anad_txt);
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
				// fragment.setArguments(bundle);
				// trans.replace(R.id.content_frame, fragment);
				// trans.addToBackStack(null);
				// trans.commit();

			}
		});

		return convertView;

	}
}
