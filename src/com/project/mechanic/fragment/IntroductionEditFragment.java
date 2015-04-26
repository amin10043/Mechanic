package com.project.mechanic.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.project.mechanic.R;

public class IntroductionEditFragment extends Fragment {
	ImageButton btnSave;
	String phoneValue, faxValue, mobileValue, emailValue, addressValue;
	EditText phoneEnter, faxEnter, mobileEnter, emailEnter, addressEnter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_introduction_edit, null);

		btnSave = (ImageButton) view.findViewById(R.id.btnsave);

		phoneEnter = (EditText) view.findViewById(R.id.editTextphone);
		faxEnter = (EditText) view.findViewById(R.id.editTextfax);
		mobileEnter = (EditText) view.findViewById(R.id.editTextmob);
		emailEnter = (EditText) view.findViewById(R.id.editTextemail);
		addressEnter = (EditText) view.findViewById(R.id.editTextaddres);

		phoneValue = phoneEnter.getText().toString();
		faxValue = faxEnter.getText().toString();
		mobileValue = mobileEnter.getText().toString();
		emailValue = emailEnter.getText().toString();
		addressValue = addressEnter.getText().toString();

		Toast.makeText(getActivity(), "masoud", Toast.LENGTH_SHORT).show();

		return view;
	}

}
