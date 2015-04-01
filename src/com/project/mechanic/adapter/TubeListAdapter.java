package com.project.mechanic.adapter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.entity.ListItem;
import com.project.mechanic.fragment.AdvisorTypeFragment;
import com.project.mechanic.fragment.ExecutertypeFragment;
import com.project.mechanic.fragment.FroumtitleFragment;
import com.project.mechanic.fragment.NewspaperFragment;
import com.project.mechanic.fragment.ProvinceFragment;
import com.project.mechanic.fragment.SmartTubeFragment;
import com.project.mechanic.model.DataBaseAdapter;

public class TubeListAdapter extends ArrayAdapter<ListItem> {

	Context context;
	List<ListItem> list;
	ListItem tempItem;
	DataBaseAdapter adapter;

	public TubeListAdapter(Context context, int resource, List<ListItem> objact) {
		super(context, resource, objact);

		this.context = context;
		this.list = objact;
		adapter = new DataBaseAdapter(context);

	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater myInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		convertView = myInflater.inflate(R.layout.row_tube, parent, false);

		TextView txtName = (TextView) convertView.findViewById(R.id.row_tube_txt);

		tempItem = list.get(position);
		txtName.setText(tempItem.getName());


		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				LinearLayout parentlayout = (LinearLayout) v;
				TextView txtName = (TextView) parentlayout.findViewById(R.id.row_tube_txt);
				String item = txtName.getText().toString();

				adapter.open();
				ArrayList<ListItem> allItems = adapter.getListItemsById(9);
				int id = 0;
				for (ListItem listItem : allItems) {
					if (item.equals(listItem.getName())) {
						// check authentication and authorization
						id = listItem.getId();
					}
				}
				adapter.close();

				if (id == 15) {
					FragmentTransaction trans = ((MainActivity) context)
							.getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame, new SmartTubeFragment());
					trans.addToBackStack(null);
					trans.commit();
    
				} else if (id == 16) {
					FragmentTransaction trans = ((MainActivity) context)
							.getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame, new AdvisorTypeFragment());
					trans.addToBackStack(null);
					trans.commit();


				} else if (id == 17) {


					FragmentTransaction trans = ((MainActivity) context)
							.getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame,
							new ExecutertypeFragment());
					trans.addToBackStack(null);
					trans.commit();


				} else if (id == 18) {

					FragmentTransaction trans = ((MainActivity) context)
							.getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame, new NewspaperFragment());
					trans.addToBackStack(null);
					trans.commit();

				} else if (id == 19) {

					FragmentTransaction trans = ((MainActivity) context)
							.getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame, new NewspaperFragment());
					trans.addToBackStack(null);
					trans.commit();
					
					
					
				} else if (id == 20) {
					FragmentTransaction trans = ((MainActivity) context)
							.getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame, new FroumtitleFragment());
					trans.addToBackStack(null);
					trans.commit();
				}
			}
		});

		return convertView;

	}
}
