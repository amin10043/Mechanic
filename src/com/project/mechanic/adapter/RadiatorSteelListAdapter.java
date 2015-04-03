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
import com.project.mechanic.fragment.ProvinceFragment;
import com.project.mechanic.model.DataBaseAdapter;

public class RadiatorSteelListAdapter extends ArrayAdapter<ListItem> {

	Context context;
	List<ListItem> list;
	ListItem tempItem;
	DataBaseAdapter adapter;

	public RadiatorSteelListAdapter(Context context, int resource, List<ListItem> objact) {
		super(context, resource, objact);

		this.context = context;
		this.list = objact;
		adapter = new DataBaseAdapter(context);

	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater myInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		convertView = myInflater.inflate(R.layout.row_radiatorsteel, parent, false);

		TextView txtName = (TextView) convertView.findViewById(R.id.row_radiatorsteel_txt);

		tempItem = list.get(position);
		txtName.setText(tempItem.getName());


		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				LinearLayout parentlayout = (LinearLayout) v;
				TextView txtName = (TextView) parentlayout.findViewById(R.id.row_radiatorsteel_txt);
				String item = txtName.getText().toString();

				adapter.open();
				ArrayList<ListItem> allItems = adapter.getListItemsById(78);
				int id = 0;
				for (ListItem listItem : allItems) {
					if (item.equals(listItem.getName())) {
						// check authentication and authorization
						id = listItem.getId();
					}
				}
				adapter.close();

				if (id == 91) {
					FragmentTransaction trans = ((MainActivity) context)
							.getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame, new ProvinceFragment());
					trans.addToBackStack(null);
					trans.commit();
    
				} else if (id == 92) {
					FragmentTransaction trans = ((MainActivity) context)
							.getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame, new ProvinceFragment());
					trans.addToBackStack(null);
					trans.commit();


				} else if (id == 93) {


					FragmentTransaction trans = ((MainActivity) context)
							.getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame,new ProvinceFragment());
					trans.addToBackStack(null);
					trans.commit();


				}
			}
		});

		return convertView;

	}
}
