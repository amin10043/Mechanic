package com.project.mechanic.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.entity.ListItem;
import com.project.mechanic.entity.News;
import com.project.mechanic.entity.NewsPaper;
import com.project.mechanic.fragment.NewsBuildingFragment;
import com.project.mechanic.fragment.NewsFragment;
import com.project.mechanic.fragment.NewsmoreFragment;
import com.project.mechanic.fragment.NewspaperFragment;
import com.project.mechanic.fragment.RegisterFragment;
import com.project.mechanic.fragment.UrlNewsPaperFragment;
import com.project.mechanic.model.DataBaseAdapter;

public class NewspaperAdapter extends ArrayAdapter<NewsPaper> {

	Context context;
	List<NewsPaper> list;
	DataBaseAdapter adapter;
	NewsPaper tempItem;
	int lastPosition = 0;
	int TypeId;

	public NewspaperAdapter(Context context, int resource,List<NewsPaper> objact,int id) {
		super(context, resource,objact);

		this.context = context;
		this.list = objact;
		adapter = new DataBaseAdapter(context);
		this.TypeId = id;
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		LayoutInflater myInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		

		//convertView = myInflater.inflate(R.layout.main_item_list, parent, false);

		convertView = myInflater.inflate(R.layout.row_city, parent, false);

		Animation animation = AnimationUtils.loadAnimation(getContext(),
				(position > lastPosition) ? R.anim.up_from_bottom
						: R.anim.down_from_top);
		convertView.startAnimation(animation);
		lastPosition = position;

		TextView tx1 = (TextView) convertView.findViewById(R.id.RowCitytxt);
		//TextView tx2 = (TextView) convertView.findViewById(R.id.rownews_txtDescription);
    	//TextView txt3 = (TextView) convertView.findViewById(R.id.newsmoretxt);
		tempItem = list.get(position);
		//NewsPaper NewsPaper = list.get(position);
		tx1.setText(tempItem.getName());
		//tx1.setText(tempItem.getId());
		//tx2.setText(tempItem.getId());
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
                
				LinearLayout parentlayout = (LinearLayout) v;
				TextView txtName = (TextView) parentlayout
						.findViewById(R.id.RowCitytxt);
				String item = txtName.getText().toString();

				int typeId = 0;
				int id=0;
				for (NewsPaper NewsPaper : list) {
					if (item.equals(NewsPaper.getName())) {
						// check authentication and authorization
						typeId = NewsPaper.getTypeId();
						id = NewsPaper.getId();
					}
				}
				if (typeId==169) {
					FragmentTransaction trans = ((MainActivity) context)
							.getSupportFragmentManager().beginTransaction();
					UrlNewsPaperFragment fragment = new UrlNewsPaperFragment();
					Bundle bundle = new Bundle();
					bundle.putString("Id", String.valueOf(id));
					fragment.setArguments(bundle);
					trans.replace(R.id.content_frame, fragment);
					trans.addToBackStack(null);
					trans.commit();
					
					
				}
				if (typeId==170) {
					FragmentTransaction trans = ((MainActivity) context)
							.getSupportFragmentManager().beginTransaction();
					UrlNewsPaperFragment fragment = new UrlNewsPaperFragment();
					Bundle bundle = new Bundle();
					bundle.putString("Id", String.valueOf(id));
					fragment.setArguments(bundle);
					trans.replace(R.id.content_frame, fragment);
					trans.addToBackStack(null);
					trans.commit();
					
					
				}
				if (typeId==171) {
					FragmentTransaction trans = ((MainActivity) context)
							.getSupportFragmentManager().beginTransaction();
					UrlNewsPaperFragment fragment = new UrlNewsPaperFragment();
					Bundle bundle = new Bundle();
					bundle.putString("Id", String.valueOf(id));
					fragment.setArguments(bundle);
					trans.replace(R.id.content_frame, fragment);
					trans.addToBackStack(null);
					trans.commit();
					
					
				}
				if (typeId==291) {
					FragmentTransaction trans = ((MainActivity) context)
							.getSupportFragmentManager().beginTransaction();
					UrlNewsPaperFragment fragment = new UrlNewsPaperFragment();
					Bundle bundle = new Bundle();
					bundle.putString("Id", String.valueOf(id));
					fragment.setArguments(bundle);
					trans.replace(R.id.content_frame, fragment);
					trans.addToBackStack(null);
					trans.commit();
					
					
				}
				if (typeId==292) {
					FragmentTransaction trans = ((MainActivity) context)
							.getSupportFragmentManager().beginTransaction();
					UrlNewsPaperFragment fragment = new UrlNewsPaperFragment();
					Bundle bundle = new Bundle();
					bundle.putString("Id", String.valueOf(id));
					fragment.setArguments(bundle);
					trans.replace(R.id.content_frame, fragment);
					trans.addToBackStack(null);
					trans.commit();
					
					
				}
				
				
			
			
		
//txt3.setOnClickListener(new OnClickListener() {
//	
//	@Override
//	public void onClick(View v) {
//		Toast.makeText(context, "ok", Toast.LENGTH_SHORT).show();
//	FragmentTransaction trans = ((MainActivity) context)
//				.getSupportFragmentManager().beginTransaction();
//		trans.replace(R.id.content_frame, new NewspaperFragment());
//		trans.addToBackStack(null);
//		trans.commit();
//	}
//});
			}
		});
return convertView;	
			
}
}
