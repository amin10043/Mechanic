package com.project.mechanic.fragment;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.app.Dialog;
import android.content.Context;
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
	Users user;
	Saving saving;
	Map<String, String> params;
	int userid;

	public DialogcmtInfroum(Fragment f, int Commentid, Context context,
			int froumId, int resourceId) {
		super(context);
		this.context = context;
		this.f = f;
		this.Commentid = Commentid;
		this.Froumid = froumId;
		utility = new Utility(context);
		dbadapter = new DataBaseAdapter(context);

		user = new Users();

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_addcomment);
		btncmt = (ImageButton) findViewById(R.id.btnComment);
		Cmttxt = (EditText) findViewById(R.id.txtCmt);

		PersianDate date = new PersianDate();
		final String currentDate = date.todayShamsi();

		btncmt.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				user = utility.getCurrentUser();
				userid = user.getId();

				if (user == null) {
					(Toast.makeText(context,
							"برای ثبت نظر ابتدا باید وارد شوید.",
							Toast.LENGTH_LONG)).show();
				} else {

					params = new LinkedHashMap<String, String>();
					saving = new Saving(context);
					saving.delegate = DialogcmtInfroum.this;

					params.put("TableName", "CommentInFroum");

					params.put("Desk", Cmttxt.getText().toString());
					params.put("FroumID", String.valueOf(Froumid));
					params.put("UserId", String.valueOf(userid));
					params.put("Date", currentDate);
					params.put("CommentId", String.valueOf(Commentid));
					params.put("NumOfDislike", String.valueOf(0));
					params.put("NumOfLike", String.valueOf(0));

					saving.execute(params);

				}
				DialogcmtInfroum.this.dismiss();

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
		int id = -1;
		try {
			id = Integer.valueOf(output);
			int userid = user.getId();
			PersianDate date = new PersianDate();
			String currentDate = date.todayShamsi();

			dbadapter.open();

			dbadapter.insertCommentInFroumtoDb(Cmttxt.getText().toString(),
					Froumid, userid, currentDate, Commentid, "0", "0");

			dbadapter.close();

			((FroumFragment) f).updateList();

		} catch (Exception ex) {
			Toast.makeText(context, "خطا در ارتباط با سرور", Toast.LENGTH_SHORT)
					.show();

		}

	}

}
