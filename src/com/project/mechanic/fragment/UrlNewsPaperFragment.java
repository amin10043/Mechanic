package com.project.mechanic.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.entity.JavaScriptInterface;
import com.project.mechanic.entity.NewsPaper;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class UrlNewsPaperFragment extends Fragment {

	DataBaseAdapter dbAdapter;
	int id;
	String a;
	public ProgressDialog ringProgressDialog1;
	WebView webview;
	Utility util;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		id = Integer.valueOf(getArguments().getString("Id"));

		((MainActivity) getActivity()).setActivityTitle(R.string.News);
		View view = inflater.inflate(R.layout.fragment_urlnewspaper, null);
		final WebView webview = (WebView) view.findViewById(R.id.webView1);
		final JavaScriptInterface myJavaScriptInterface = new JavaScriptInterface();
		util = new Utility(getActivity());
		ringProgressDialog1 = ProgressDialog.show(getActivity(), "",
				"لطفا منتظر بمانید...", true);

		ringProgressDialog1.setCancelable(true);

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(10000);
				} catch (Exception e) {
				}
			}
		}).start();
		// //////////////////////////////////////////////////////////////////////////////
		dbAdapter = new DataBaseAdapter(getActivity());

		dbAdapter.open();

		final NewsPaper newsPaper = dbAdapter.getNewsPaperId(id);

		if (util.isNetworkConnected()) {
			if (newsPaper != null) {
				/* JavaScript must be enabled if you want it to work, obviously */
				// webview.getSettings().setJavaScriptEnabled(true);

				/* Register a new JavaScript interface called HTMLOUT */
				// webview.addJavascriptInterface(myJavaScriptInterface,
				// "HTMLOUT");
				webview.loadUrl(newsPaper.getUrl());
				webview.setWebViewClient(new WebViewClient()

				{

					public void onPageFinished(WebView view, String url) {

						ringProgressDialog1.dismiss();

						// webview.loadUrl("javascript:window.HTMLOUT.processHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");

						String html = "<html>"
								+ "<body><h1>Yay, Mobiletuts+!</h1></body>"
								+ "</html>";
						dbAdapter.open();

						dbAdapter.UpdateHtmlStringInNewspaper(id, html);
						dbAdapter.close();
						// do your stuff here
					}

				});

			}
		} else if (!util.isNetworkConnected()) {
			if (newsPaper != null) {
				// rei=ad db html
				//

				webview.loadData(newsPaper.getHtmlString(),
						"text/html; charset=UTF-8", null);

				webview.setWebViewClient(new WebViewClient()

				{

					public void onPageFinished(WebView view, String url) {

						ringProgressDialog1.dismiss();

					}

				});

			}

		} // /

		return view;

	}
}
