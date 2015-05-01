package com.project.mechanic.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.project.mechanic.R;
import com.project.mechanic.model.DataBaseAdapter;

public class IntroductionEditFragment extends Fragment {

	DataBaseAdapter DBAdapter;
	ImageButton btnSave;
	String phoneValue, faxValue, mobileValue, emailValue, addressValue;
	EditText phoneEnter, faxEnter, mobileEnter, emailEnter, addressEnter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_introduction_edit, null);

		DBAdapter = new DataBaseAdapter(getActivity());

		btnSave = (ImageButton) view.findViewById(R.id.btnsave);

		phoneEnter = (EditText) view.findViewById(R.id.editTextphone);
		faxEnter = (EditText) view.findViewById(R.id.editTextfax);
		mobileEnter = (EditText) view.findViewById(R.id.editTextmob);
		emailEnter = (EditText) view.findViewById(R.id.editTextemail);
		addressEnter = (EditText) view.findViewById(R.id.editTextaddres);

		SharedPreferences sendDataID = getActivity().getSharedPreferences("Id",
				0);
		final int id = sendDataID.getInt("main_Id", -1);

		Toast.makeText(getActivity(), id + "", Toast.LENGTH_SHORT).show();

		btnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				phoneValue = phoneEnter.getText().toString();
				faxValue = faxEnter.getText().toString();
				mobileValue = mobileEnter.getText().toString();
				emailValue = emailEnter.getText().toString();
				addressValue = addressEnter.getText().toString();

				DBAdapter.open();
				DBAdapter.UpdateObjectProperties(id, phoneValue, faxValue,
						mobileValue, emailValue, addressValue);
				DBAdapter.close();
				Toast.makeText(getActivity(), "تغییرات با موفقیت ذخیره شد",
						Toast.LENGTH_SHORT).show();
				getActivity().getSupportFragmentManager().popBackStack();
			}
		});

		return view;
	}
}
