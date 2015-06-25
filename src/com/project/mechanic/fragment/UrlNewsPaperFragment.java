package com.project.mechanic.fragment;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

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

	String html;

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

						// ////////////////////////////////////////////////////
						String html = "<html>"
								+ "<body><h1>Yay, Mobiletuts+!</h1></body>"
								+ "</html>";

						// ////////////////////////////////////////////////////////////
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
				// ////////////////////////////////////////
				// webview.getSettings().setCacheMode(
				// WebSettings.LOAD_CACHE_ELSE_NETWORK);
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

	String fetchContent(WebView view, String url) throws IOException {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		HttpResponse response = httpClient.execute(get);
		StatusLine statusLine = response.getStatusLine();
		int statusCode = statusLine.getStatusCode();
		HttpEntity entity = response.getEntity();
		String html = EntityUtils.toString(entity); // assume html for //
													// simplicity
		view.loadDataWithBaseURL(url, html, "text/html", "utf-8", url); // todo:
		if (statusCode != 200) {
			// handle fail
		}
		return html;
	}
}
