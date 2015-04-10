package com.project.mechanic.fragment;

import com.project.mechanic.R;

import android.support.v4.*;
import android.support.v4.app.Fragment;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.os.Build;

public class PaperFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_paper, null);	
		Button btncomment=(Button) view.findViewById(R.id.btnCmt);
		btncomment.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				  DialogcmtInfroum dialog = new DialogcmtInfroum(PaperFragment.this,getActivity(),R.layout.dialog_addcomment);
				  dialog.show();
				
			}
		});
		return view;
		
		
	}

}