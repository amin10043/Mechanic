package com.project.mechanic.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.entity.News;
import com.project.mechanic.fragment.NewsmoreFragment;
import com.project.mechanic.fragment.RegisterFragment;
import com.project.mechanic.model.DataBaseAdapter;

public class NewspaperListAdapter extends ArrayAdapter<News> {

	Context context;
	List<News> list;
	DataBaseAdapter adapter;
	int lastPosition = 0;

	public NewspaperListAdapter(Context context, int resource,List<News> objact) {
		super(context, resource, objact);

		this.context = context;
		this.list = objact;
		adapter = new DataBaseAdapter(context);
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		LayoutInflater myInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		

		convertView = myInflater.inflate(R.layout.main_item_list, parent, false);

		convertView = myInflater.inflate(R.layout.row_newspaper, parent, false);

		Animation animation = AnimationUtils.loadAnimation(getContext(),
				(position > lastPosition) ? R.anim.up_from_bottom
						: R.anim.down_from_top);
		convertView.startAnimation(animation);
		lastPosition = position;

		TextView tx1 = (TextView) convertView.findViewById(R.id.rownews_Titletxt);
		TextView tx2 = (TextView) convertView.findViewById(R.id.rownews_txtDescription);
    	TextView txt3 = (TextView) convertView.findViewById(R.id.newsmoretxt);
      
		News News = list.get(position);

		tx1.setText(News.getTitle());
		tx2.setText(News.getDescription());
		
	
		
txt3.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		Toast.makeText(context, "ok", Toast.LENGTH_SHORT).show();
	FragmentTransaction trans = ((MainActivity) context)
				.getSupportFragmentManager().beginTransaction();
		trans.replace(R.id.content_frame, new NewsmoreFragment());
		trans.addToBackStack(null);
		trans.commit();
	}
});
return convertView;	
	}
}
