package com.project.mechanic.fragment;

import com.project.mechanic.R;
import com.project.mechanic.model.DataBaseAdapter;

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
import android.widget.TextView;
import android.os.Build;

public class PaperFragment extends Fragment {
	DataBaseAdapter adapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_paper, null);
		TextView txttitle=(TextView) view.findViewById(R.id.txtTitleP);
		TextView txtcontext=(TextView) view.findViewById(R.id.txtContext);
		TextView txtcomment=(TextView) view.findViewById(R.id.txtComment);
		TextView txtlike=(TextView) view.findViewById(R.id.txtLike);
		Button btncomment1=(Button) view.findViewById(R.id.btnComment);
		Button btnlike=(Button) view.findViewById(R.id.btnLike);
		btncomment1.setOnClickListener(new OnClickListener() {
			
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
