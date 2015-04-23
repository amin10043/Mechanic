package com.project.mechanic.adapter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
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
import com.project.mechanic.entity.AdvisorType;
import com.project.mechanic.fragment.ExecutertypeFragment;
//import com.project.mechanic.fragment.ExecutertypeFragment;
import com.project.mechanic.fragment.ProvinceFragment;
import com.project.mechanic.model.DataBaseAdapter;

public class AdvisorTypeListAdapter extends ArrayAdapter<AdvisorType> {

	Context context;
	List<AdvisorType> list;
	DataBaseAdapter adapter;
	protected Object item;

	public AdvisorTypeListAdapter(Context context, int resource,
			List<AdvisorType> objact) {
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

		convertView = myInflater
				.inflate(R.layout.main_item_list, parent, false);

		convertView = myInflater.inflate(R.layout.row_advisortype, parent,
				false);

		TextView tx1 = (TextView) convertView
				.findViewById(R.id.RowAdvisortypetxt);

		AdvisorType AdvisorType = list.get(position);

		tx1.setText(AdvisorType.getName());

		Typeface typeFace = Typeface.createFromAsset(context.getAssets(),
				"fonts/BROYA.TTF");
		tx1.setTypeface(typeFace);

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				LinearLayout parentlayout = (LinearLayout) v;
				TextView txtName = (TextView) parentlayout
						.findViewById(R.id.RowAdvisortypetxt);
				String item = txtName.getText().toString();

				// Toast.makeText(context, item , Toast.LENGTH_LONG).show();

				adapter.open();
				ArrayList<AdvisorType> allAdvisorType = adapter
						.getAdvisorTypes();
				int id = 0;
				for (AdvisorType AdvisorType1 : allAdvisorType) {
					if (item.equals(AdvisorType1.getName())) {
						// check authentication and authorization
						id = AdvisorType1.getId();
					}
				}
				adapter.close();

				if (id == 1 || id == 2) {
					FragmentTransaction trans = ((MainActivity) context)
							.getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame, new ProvinceFragment());
					trans.addToBackStack(null);
					trans.commit();

				} else if (id == 3) {
					FragmentTransaction trans = ((MainActivity) context)
							.getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame,
							new ExecutertypeFragment());
					trans.addToBackStack(null);
					trans.commit();
				}

			}
		});
		return convertView;
	}
}
