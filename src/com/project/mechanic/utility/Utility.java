package com.project.mechanic.utility;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.project.mechanic.entity.Users;
import com.project.mechanic.model.DataBaseAdapter;

public class Utility {

	private Context context;
	private DataBaseAdapter adapter;

	public Utility(Context context) {
		this.context = context;
		adapter = new DataBaseAdapter(context);
	}

	public boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni == null) {
			// There are no active networks.
			return false;
		} else
			return true;
	}

	public void showYesNoDialog(Context context, String Title, String message) {
		new AlertDialog.Builder(context)
				.setTitle(Title)
				.setMessage(message)
				.setPositiveButton("بلی",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {

							}
						})
				.setNegativeButton("خیر",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {

							}
						}).setIcon(android.R.drawable.ic_dialog_alert).show();
	}

	public void showOkDialog(Context context, String Title, String message) {
		new AlertDialog.Builder(context)
				.setTitle(Title)
				.setMessage(message)
				.setPositiveButton("تایید",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						}).setIcon(android.R.drawable.ic_dialog_alert).show();
	}

	public Users getCurrentUser() {

		Users u = null;
		SharedPreferences settings = context.getSharedPreferences("user", 0);

		boolean isLogin = settings.getBoolean("isLogin", false);
		if (isLogin) {

			adapter.open();
			u = adapter.getUserById(0); // FOR TESTING !!!!
			adapter.close();
			return u;
		} else {
			return null;
		}

	}
}
