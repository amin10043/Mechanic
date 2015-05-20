package com.project.mechanic.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.project.mechanic.R;
import com.project.mechanic.model.DataBaseAdapter;

public class SearchFragment extends ListFragment {

	private DataBaseAdapter db;
	private String[] Name;
	private String[] cPage;
	private String[] Page;
	private EditText word;
	String tableName = "Province";

	private TextView status;
	Fragment fragment;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// ((MainActivity) getActivity()).setActivityTitle(R.string.showad);

		View view = inflater.inflate(R.layout.activity_search, null);
		db = new DataBaseAdapter(getActivity());

		// tableName = getIntent().getExtras().getString("table");

		word = (EditText) view.findViewById(R.id.search_word);

		status = (TextView) view.findViewById(R.id.search_status);

		refresh(word.getText().toString(), "Name");

		// Button btn = (Button) findViewById(R.id.search_btn);
		// btn.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// refresh(word.getText().toString(), "Name");
		//
		// }
		// });

		word.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub

				refresh(word.getText().toString(), "Name");

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub

			}
		});
		return view;

	}

	public void onListItemClick(ListView l, View v, int position, long id) {
		//

		//
		// android.app.FragmentManager fragmentManager;
		// switch (position) {
		// case 0:
		// fragment = new ProvinceFragment();
		// fragmentManager = getFragmentManager();
		// fragmentManager.beginTransaction()
		// .replace(R.id.content_frame, fragment).commit();
		// break;
		//
		// case 1:
		// }

		// Intent i = new Intent(Search.this, Province.class);
		//
		// i.putExtra("name", Name[position]);
		//
		// startActivity(i);

	}

	private void startAnimation(Animation animation) {
		// TODO Auto-generated method stub

	}

	class AA extends ArrayAdapter<String> {

		public AA() {
			super(getActivity(), R.layout.row_search, Name);
		}

		@SuppressLint("ViewHolder")
		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {

			LayoutInflater in = getActivity().getLayoutInflater();
			View row = in.inflate(R.layout.row_search, parent, false);

			TextView name = (TextView) row.findViewById(R.id.row_search_name);
			//
			name.setText(Name[position] + Page[position]);
			// name.setTypeface(Main.font);

			return (row);
		}

		private LayoutInflater getLayoutInflater() {
			// TODO Auto-generated method stub
			return null;
		}

	}

	private void refresh(String word1, String field) {

		db.open();

		int s = db.Mechanical_serach(tableName, word1, field);

		if (word.getText().toString().equals("")) {
			s = 0;
			status.setText("لطفا کلمه مورد جستجو را وارد نمایید");
		} else {

			status.setText("تعداد " + s + " نتیجه یافت شد");
		}

		Name = new String[s];
		cPage = new String[s];
		Page = new String[s];

		for (int i = 0; i < s; i++) {

			Name[i] = db.serach(tableName, i, 1, word1, field);

			// cPage[i] = db.Story_page_count("content", Name[i]) + "";

			if (field.equals("Name")) {
				Page[i] = "";
			} else {
				Page[i] = " > " + db.serach(tableName, i, 3, word1, field);

			}
		}

		setListAdapter(new AA());
		db.close();

	}
}
