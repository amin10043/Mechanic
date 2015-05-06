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

	public DialogcmtInfroum(Fragment f, int Commentid, Context context,
			int froumId, int resourceId) {
		super(context);
		this.context = context;
		this.f = f;
		this.Commentid = Commentid;
		this.Froumid = froumId;
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
				utility = new Utility(context);
				Users user = new Users();
				user = utility.getCurrentUser();
				int userid = user.getId();
				dbadapter = new DataBaseAdapter(context);
				dbadapter.open();
				// int id = Integer.valueOf(f.getArguments().getString("Id"));
				dbadapter.insertCommentInFroumtoDb(Cmttxt.getText().toString(),
						Froumid, userid, "1", Commentid, "0", "0");

				dbadapter.close();
				((FroumFragment) f).updateView2();
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
