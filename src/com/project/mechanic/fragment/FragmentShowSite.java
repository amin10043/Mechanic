package com.project.mechanic.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.project.mechanic.R;

public class FragmentShowSite extends Fragment {
	WebView webview;
	String url;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_show_site, null);
		webview = (WebView) view.findViewById(R.id.webview_social_show);
		webview.loadUrl(url);

		return view;
	}

	public FragmentShowSite(String url) {
		super();
		this.url = url;
	}

}
