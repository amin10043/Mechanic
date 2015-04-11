package com.project.mechanic.adapter;

import java.util.ArrayList;
import java.util.List;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.entity.City;
import com.project.mechanic.entity.Froum;
import com.project.mechanic.entity.Paper;
import com.project.mechanic.fragment.FroumFragment;
import com.project.mechanic.fragment.PaperFragment;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.row_items.FroumtitleItem;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PapertitleListAdapter  extends ArrayAdapter<Paper>{

	Context context;
	List<Paper>  mylist;
	DataBaseAdapter adapter;
	public PapertitleListAdapter(Context context, int resource,
			List<Paper> objects) {
		super(context, resource, objects);
		this.context= context;
		this.mylist= objects;
		adapter = new DataBaseAdapter(context);
		
	}
	
	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater myInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		adapter= new DataBaseAdapter(context);
		

		convertView = myInflater.inflate(R.layout.raw_froumtitle, parent, false);
		

		final TextView txt1 = (TextView) convertView.findViewById(R.id.rawTitletxt);
		TextView txt2 = (TextView) convertView.findViewById(R.id.rawtxtDescription);
		TextView txt3 = (TextView) convertView.findViewById(R.id.rawtxtUsername);
		
	
	    Paper person1 = mylist.get(position);
		
		txt1.setText(person1.getTitle());
		txt2.setText(person1.getContext());
		txt3.setText("Maryam");
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				LinearLayout parentlayout = (LinearLayout) v;
				
				String item = txt1.getText().toString();
				int id = 0;
				for (Paper listItem : mylist) {
					if (item.equals(listItem.getTitle())) {
						// check authentication and authorization
						id = listItem.getId();
					}
					FragmentTransaction trans =  ((MainActivity) context)
							.getSupportFragmentManager().beginTransaction();
					PaperFragment fragment = new PaperFragment();
					Bundle bundle = new Bundle();
					bundle.putString("Id", String.valueOf(id));
	 				fragment.setArguments(bundle);
				}
		
	
			}
			
		});

		return convertView;
	}

	
	

}
