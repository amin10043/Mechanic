package com.project.mechanic.fragment;

import java.util.List;

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
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class DialogcmtInfroum extends Dialog {

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
		btncmt.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				user = utility.getCurrentUser();
				if (user == null) {
					(Toast.makeText(context,
							"برای ثبت نظر ابتدا باید وارد شوید.",
							Toast.LENGTH_LONG)).show();
				} else {

					int userid = user.getId();
					PersianDate date = new PersianDate();
					String currentDate = date.todayShamsi();

					dbadapter.open();

					dbadapter.insertCommentInFroumtoDb(Cmttxt.getText()
							.toString(), Froumid, userid, currentDate,
							Commentid, "0", "0");

					dbadapter.close();

					((FroumFragment) f).updateList();
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

}
