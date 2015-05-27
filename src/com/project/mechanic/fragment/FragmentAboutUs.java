package com.project.mechanic.fragment;

import ir.noghteh.JustifiedTextView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.project.mechanic.R;


public class FragmentAboutUs extends Fragment {

    private JustifiedTextView mJTv;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View V = getActivity().getLayoutInflater().inflate(R.layout.fragment_menu_about_us, null);
        mJTv = (JustifiedTextView) V.findViewById(R.id.activity_main_jtv);

        return V;
    }
}
