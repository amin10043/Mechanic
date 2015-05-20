package com.project.mechanic.fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.project.mechanic.R;


public class FragmentAboutUs extends Fragment {

    private ViewGroup txt;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View V = getActivity().getLayoutInflater().inflate(R.layout.fragment_menu_about_us, null);

        txt = (ViewGroup) V.findViewById(R.id.book);
        testTextView();

        return V;
    }


    private void testTextView() {
        String content = readContent("content/AboutUs.html");
        final TextView textView = new TextView(getActivity());
        //        textView.setGravity(Gravity.RIGHT);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        textView.setPadding(10, 10, 10, 10);
        textView.setBackgroundColor(Color.BLACK);
        textView.setText(Html.fromHtml(content));
        txt.addView(textView);
    }


    private String readContent(String contentUrl) {
        InputStream inputStream = null;
        try {
            inputStream = getResources().getAssets().open(contentUrl);
            String sample = streamToString(inputStream);
            return sample;
        }
        catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;

    }


    private String streamToString(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuffer = new StringBuilder();

        String line = null;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append((line + "\n"));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringBuffer.toString();
    }

}
