package com.project.mechanic.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.model.DataBaseAdapter;

public class SearchFragment extends Fragment {

	DataBaseAdapter adapter;

	private String[] Name;
	private String[] Sea;
	private String[] cPage;

	private String[] Page;

	private EditText word;

	private RadioButton rbname;
	private RadioButton rbmatn;

	private TextView status;

	// @SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		((MainActivity) getActivity()).setActivityTitle(R.string.search);

		View view = inflater.inflate(R.layout.fragment_search, null);
		adapter = new DataBaseAdapter(getActivity());

		// DB = new DataBaseAdapter(this);

		word = (EditText) view.findViewById(R.id.search_word);
		rbname = (RadioButton) view.findViewById(R.id.search_rb_name);
		rbmatn = (RadioButton) view.findViewById(R.id.search_rb_matn);
		status = (TextView) view.findViewById(R.id.search_status);

		refresh(word.getText().toString(), "Name");

		word.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub

				if (rbname.isChecked()) {
					refresh(word.getText().toString(), "Name");
				} else if (rbmatn.isChecked()) {

					refresh(word.getText().toString(), "Matn");

				}
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

	// protected void onListItemClick(ListView l, View v, int position, long id)
	// {
	//
	// Intent i = new Intent(search.this, main_matn.class);
	// i.putExtra("sea", Sea[position]);
	// i.putExtra("name", Name[position]);
	// i.putExtra("page", cPage[position]);
	// startActivity(i);

	// }

	// class AA extends ArrayAdapter<String> {
	//
	// // public AA() {
	// // super(getActivity() R.layout.row_ostan, Name);
	// }

	// public View getView(final int position, View convertView,
	// ViewGroup parent) {
	//
	// LayoutInflater in = getLayoutInflater();
	// View row = in.inflate(R.layout.row_season, parent, false);
	//
	// TextView name = (TextView) row.findViewById(R.id.row_season_name);
	//
	// name.setText(Sea[position] + ": " + Name[position] + Page[position]);
	// name.setTypeface(Main.font);
	//
	// return (row);
	// }
	//
	// }

	private void refresh(String word1, String field) {

		adapter.open();

		int s = adapter.count_serach(word1, field);

		if (word.getText().toString().equals("")) {
			s = 0;
			status.setText("لطفا کلمه مورد جستجو را وارد نمایید");
		} else {

			status.setText("تعداد " + s + " نتیجه یافت شد");
		}

		Name = new String[s];
		Sea = new String[s];
		cPage = new String[s];
		Page = new String[s];

		for (int i = 0; i < s; i++) {

			Name[i] = adapter.serach(i, 1, word1, field);
			Sea[i] = adapter.serach(i, 4, word1, field);
			// cPage[i] = adapter.Story_page_count("content", Sea[i], Name[i])
			// + "";

			if (field.equals("Name")) {
				Page[i] = "";
			} else {
				Page[i] = " > " + adapter.serach(i, 3, word1, field);

			}
		}

		// setListAdapter(new AA());
		adapter.close();

		// return view;

	}

}
