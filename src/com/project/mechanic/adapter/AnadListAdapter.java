package com.project.mechanic.adapter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.entity.ListItem;
import com.project.mechanic.entity.Ticket;
import com.project.mechanic.fragment.BerandFragment;
import com.project.mechanic.fragment.ShowAdFragment;
import com.project.mechanic.model.DataBaseAdapter;

public class AnadListAdapter extends ArrayAdapter<Ticket> {

	Context context;
	List<Ticket> list;
	int[] imageId;
	Ticket tempItem;
	DataBaseAdapter adapter;

	public AnadListAdapter(Context context, int resource, List<Ticket> objact) {
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

		convertView = myInflater.inflate(R.layout.row_anad, parent, false);

		TextView txtName = (TextView) convertView.findViewById(R.id.row_anad_txt);

		ImageView img = (ImageView) convertView.findViewById(R.id.row_anad_img);

		tempItem = list.get(position);
		txtName.setText(tempItem.getTitle());
		

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				adapter.open();
				ArrayList<Ticket> allItems = adapter.getTicketById(0);
				int id = 0;
				for (Ticket Ticket : allItems) {
					if (tempItem.equals(Ticket.getTitle())) {
						// check authentication and authorization
						id = Ticket.getId();
					}
				}
				adapter.close();

				
//				SharedPreferences sendData = context.getSharedPreferences(
//						"Id", 0);
//				SharedPreferences.Editor editor = sendData.edit();
//				editor.putInt("main_Id", id);
//				editor.commit();
				
					FragmentTransaction trans = ((MainActivity) context)
					.getSupportFragmentManager().beginTransaction();
					ShowAdFragment fragment = new ShowAdFragment();
					Bundle bundle = new Bundle();
					bundle.putString("Id", String.valueOf(id));
					fragment.setArguments(bundle);
					trans.replace(R.id.content_frame, fragment);
					trans.addToBackStack(null);
					trans.commit();
				
//			
//				FragmentTransaction trans = ((MainActivity) context)
//						.getSupportFragmentManager().beginTransaction();
//				trans.replace(R.id.content_frame, new ShowAdFragment());
//				trans.addToBackStack(null);
//				trans.commit();
					}
		});

		return convertView;

	}
}
