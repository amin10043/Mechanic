package com.project.mechanic.fragment;




import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;






public class NewsFragment extends Fragment {

	
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		((MainActivity) getActivity()).setActivityTitle(R.string.News);
		View view = inflater.inflate(R.layout.fragment_news, null);


		

		
		
		return view;
	
		}


	
	
	}




	
	

	
	

