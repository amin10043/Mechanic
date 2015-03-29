package com.project.mechanic.adapter;

import java.util.ArrayList;
import java.util.List;

import com.project.mechanic.R;
import com.project.mechanic.entity.City;
import com.project.mechanic.entity.Froum;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.row_items.FroumtitleItem;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FroumtitleListadapter  extends ArrayAdapter<Froum>{

	Context context;
	List<Froum>  mylist;
	DataBaseAdapter adapter;
	public FroumtitleListadapter(Context context, int resource,
			List<Froum> objects) {
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
		

		convertView = myInflater.inflate(R.layout.froumtitleitem, parent, false);

		TextView txt1 = (TextView) convertView.findViewById(R.id.rawTitletxt);
		TextView txt2 = (TextView) convertView.findViewById(R.id.rawtxtDescription);
		TextView txt3 = (TextView) convertView.findViewById(R.id.rawtxtUsername);
	
	    Froum person1 = mylist.get(position);
		
		txt1.setText(person1.getTitle());
		txt2.setText(person1.getDescription());
		txt3.setText("shaghayegh");
	
		

		return convertView;
	}

	
	

}
