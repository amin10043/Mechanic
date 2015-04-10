package com.project.mechanic.utility;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utility {

	private Context context;

	public Utility(Context context) {
		this.context = context;
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
}
