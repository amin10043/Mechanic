package com.project.mechanic.fragment;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.project.mechanic.R;
import com.project.mechanic.entity.Users;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.Saving;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.utility.Utility;

public class DialogcmtInfroum extends Dialog implements AsyncInterface {

	private ImageButton btncmt;
	private EditText Cmttxt;
	OnMyDialogResult mDialogResult;
	private DataBaseAdapter dbadapter;
	private Utility utility;
	Context context;
	Fragment f;
	List<Users> list;
	private int Commentid;
	private int Froumid;
	Users currentUser;
	Saving saving;
	Map<String, String> params;
	ProgressDialog ringProgressDialog;

	String serverDate = "";
	ServerDate date;

	public DialogcmtInfroum(Fragment f, int Commentid, Context context,
			int froumId, int resourceId) {
		super(context);
		this.context = context;
		this.f = f;
		this.Commentid = Commentid;
		this.Froumid = froumId;
		utility = new Utility(context);
		dbadapter = new DataBaseAdapter(context);

		currentUser = utility.getCurrentUser();

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		setContentView(R.layout.dialog_addcomment);
		btncmt = (ImageButton) findViewById(R.id.btnComment);
		Cmttxt = (EditText) findViewById(R.id.txtCmt);

		// PersianDate date = new PersianDate();
		// final String currentDate = date.todayShamsi();

		btncmt.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (currentUser == null) {
					(Toast.makeText(context,
							"برای ثبت نظر ابتدا باید وارد شوید.",
							Toast.LENGTH_LONG)).show();
				} else {
					date = new ServerDate(context);
					date.delegate = DialogcmtInfroum.this;
					date.execute("");

				}

			}
		});
	}

	public interface OnMyDialogResult {
		void finish(String result);
	}

	public void setDialogResult(OnMyDialogResult dialogResult) {
		mDialogResult = dialogResult;
	}

	@Override
	public void processFinish(String output) {
		if (ringProgressDialog != null) {
			ringProgressDialog.dismiss();
		}

		if (!"".equals(output) && output != null
				&& !(output.contains("Exception") || output.contains("java"))) {

			int id = -1;
			try {
				id = Integer.valueOf(output);
				dbadapter.open();

				dbadapter.insertCommentInFroumtoDb(Cmttxt.getText().toString(),
						Froumid, currentUser.getId(), serverDate, Commentid,
						"0", "0");

				dbadapter.close();

				SharedPreferences expanding = context.getSharedPreferences(
						"Id", 0);
				final int expandPosition = expanding.getInt("main_Id", -1);

				((FroumFragment) f).updateList();
				DialogcmtInfroum.this.dismiss();

			} catch (NumberFormatException ex) {
				if (!"".equals(output)
						&& output != null
						&& !(output.contains("Exception") || output
								.contains("java"))) {

					params = new LinkedHashMap<String, String>();
					saving = new Saving(context);
					saving.delegate = DialogcmtInfroum.this;

					params.put("TableName", "CommentInFroum");

					params.put("Desk", Cmttxt.getText().toString());
					params.put("FroumId", String.valueOf(Froumid));
					params.put("UserId", String.valueOf(currentUser.getId()));
					params.put("CommentId", String.valueOf(Commentid));
					params.put("NumofDisLike", String.valueOf(0));
					params.put("NumofLike", String.valueOf(0));
					serverDate = output;

					saving.execute(params);

					ringProgressDialog = ProgressDialog.show(context, "",
							"لطفا منتظر بمانید...", true);

					ringProgressDialog.setCancelable(true);

				} else

					Toast.makeText(context, "خطا در ثبت. پاسخ نا مشخص از سرور",
							Toast.LENGTH_SHORT).show();
			}

		} else
			Toast.makeText(context, "خطا در ثبت", 0).show();
	}
}
