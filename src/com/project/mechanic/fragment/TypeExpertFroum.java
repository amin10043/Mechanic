package com.project.mechanic.fragment;

import com.project.mechanic.R;
import com.project.mechanic.utility.Utility;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TypeExpertFroum extends Fragment {
	Utility util;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_type_expert_froum,
				null);

		util = new Utility(getActivity());
		
		TextView labletabrid = (TextView)rootView.findViewById(R.id.tabridlable);
		TextView garmayesh = (TextView)rootView.findViewById(R.id.garmayeshlable);
		TextView azkaflable = (TextView)rootView.findViewById(R.id.azkaflable);
		TextView bmslable = (TextView)rootView.findViewById(R.id.bmslable);
		
		labletabrid.setTypeface(util.SetFontCasablanca());
		garmayesh.setTypeface(util.SetFontCasablanca());
		azkaflable.setTypeface(util.SetFontCasablanca());
		bmslable.setTypeface(util.SetFontCasablanca());
		

		return rootView;
	}

}
