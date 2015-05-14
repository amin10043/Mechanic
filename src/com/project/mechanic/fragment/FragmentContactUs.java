package com.project.mechanic.fragment;

/*
 * Copyright 2013 Csaba Szugyiczki
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.TextView;
import android.widget.Toast;
import com.project.mechanic.R;
import com.project.mechanic.view.CircleImageView;
import com.project.mechanic.view.CircleLayout;
import com.project.mechanic.view.CircleLayout.OnCenterClickListener;
import com.project.mechanic.view.CircleLayout.OnItemClickListener;
import com.project.mechanic.view.CircleLayout.OnItemSelectedListener;
import com.project.mechanic.view.CircleLayout.OnRotationFinishedListener;


public class FragmentContactUs extends Fragment implements OnItemSelectedListener,
        OnItemClickListener, OnRotationFinishedListener, OnCenterClickListener {

    public static final String ARG_LAYOUT = "layout";

    private TextView           selectedTextView;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //View V = inflater.inflate(R.layout.sample_fast, container, false);
        View V = getActivity().getLayoutInflater().inflate(R.layout.fragment_menu_circle_style_fast, null);
        // Set listeners
        CircleLayout circleMenu = (CircleLayout) V.findViewById(R.id.main_circle_layout);
        circleMenu.setOnItemSelectedListener(this);
        circleMenu.setOnItemClickListener(this);
        circleMenu.setOnRotationFinishedListener(this);
        circleMenu.setOnCenterClickListener(this);

        selectedTextView = (TextView) V.findViewById(R.id.main_selected_textView);
        selectedTextView.setText(((CircleImageView) circleMenu
                .getSelectedItem()).getName());
        return V;
    }


    @Override
    public void onItemSelected(View view, String name) {
        selectedTextView.setText(name);

        switch (view.getId()) {
            case R.id.instagram:
                // Handle calendar selection
                break;
            case R.id.site:
                // Handle cloud selection
                break;
            case R.id.google_plus:
                // Handle facebook selection
                break;
            case R.id.facebook:
                // Handle key selection
                break;
            case R.id.twitter:
                // Handle profile selection
                break;
            case R.id.linkedin:
                // Handle tap selection
                break;
        }
    }


    @Override
    public void onItemClick(View view, String name) {
        Toast.makeText(getActivity(),
                getResources().getString(R.string.start_app) + " " + name,
                Toast.LENGTH_SHORT).show();

        switch (view.getId()) {
            case R.id.instagram:
                // Handle calendar click
                break;
            case R.id.site:
                // Handle cloud click
                break;
            case R.id.google_plus:
                // Handle facebook click
                break;
            case R.id.facebook:
                // Handle key click
                break;
            case R.id.twitter:
                // Handle profile click
                break;
            case R.id.linkedin:
                // Handle tap click
                break;
        }
    }


    @Override
    public void onRotationFinished(View view, String name) {
        Animation animation = new RotateAnimation(0, 360, view.getWidth() / 2,
                view.getHeight() / 2);
        animation.setDuration(250);
        view.startAnimation(animation);
    }


    @Override
    public void onCenterClick() {
        Toast.makeText(getActivity(), R.string.center_click,
                Toast.LENGTH_SHORT).show();
    }
}
