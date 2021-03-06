package com.project.mechanic.fragment;

import ir.noghteh.JustifiedTextView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.project.mechanic.R;
import com.project.mechanic.utility.Utility;


public class FragmentAboutUs extends Fragment {

    private JustifiedTextView mJTv;
    private Utility util;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View V = getActivity().getLayoutInflater().inflate(R.layout.fragment_menu_about_us, null);
        mJTv = (JustifiedTextView) V.findViewById(R.id.activity_main_jtv);
        util = new Utility(getActivity());
        

        mJTv.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
		mJTv.setLineSpacing(15);
        mJTv.setTypeFace(util.SetFontIranSans());
        
        return V;
    }
}
