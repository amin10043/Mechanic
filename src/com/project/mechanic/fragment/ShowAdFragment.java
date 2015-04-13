package com.project.mechanic.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;

public class ShowAdFragment extends Fragment {

private ImageView showad_img1;
 private TextView showad_txt1;
	

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		((MainActivity) getActivity()).setActivityTitle(R.string.showad);

		View view = inflater.inflate(R.layout.fragment_showad, null);

		showad_img1=(ImageView)view.findViewById(R.id.showad_img1);
		showad_txt1=(TextView)view.findViewById(R.id.showad_txt1);
		
		
		showad_txt1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Intent sharingIntent = new Intent(
						android.content.Intent.ACTION_SEND);
				sharingIntent.setType("text/plain");
				String shareBody = "Here is the share content body";
				sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
						"Subject Here");
				sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
						shareBody);
				startActivity(Intent.createChooser(sharingIntent,
						"اشتراک از طریق"));
				
				
			}
		});
		
		showad_img1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Intent sharingIntent = new Intent(
						android.content.Intent.ACTION_SEND);
				sharingIntent.setType("text/plain");
				String shareBody = "Here is the share content body";
				sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
						"Subject Here");
				sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
						shareBody);
				startActivity(Intent.createChooser(sharingIntent,
						"اشتراک از طریق"));
				
				
				
			}
		});
		
		

		return view;
	}
}
