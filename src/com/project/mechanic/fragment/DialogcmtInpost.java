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
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.entity.Users;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.Saving;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.utility.Utility;

public class DialogcmtInpost extends Dialog implements AsyncInterface {

	private ImageButton btncmt;
	private EditText Cmttxt;
	OnMyDialogResult mDialogResult;
	private DataBaseAdapter dbadapter;
	private Utility utility;
	Context context;
	Fragment f;
	List<Users> list;
	private int Commentid;
	private int Postid;
	Users currentUser;
	Saving saving;
	Map<String, String> params;
	ProgressDialog ringProgressDialog;

	String serverDate = "";
	ServerDate date;
	// متغیر کد برای پیدا کردن مبدا ارسالی برای آپدیت کردن لیست می باشد

	int code;

	public DialogcmtInpost(Fragment f, int Commentid, Context context,
			int postId, int resourceId, int code) {
		super(context);
		this.context = context;
		this.f = f;
		this.Commentid = Commentid;
		this.Postid = postId;
		utility = new Utility(context);
		dbadapter = new DataBaseAdapter(context);

		currentUser = utility.getCurrentUser();
		this.code = code;

	}

	protected void onCreate(Bundle savedInstanceState) {
		
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
					if (context != null) {
						date = new ServerDate(context);
						date.delegate = DialogcmtInpost.this;
						date.execute("");

					}
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

		if (!"".equals(output) && output != null
				&& !(output.contains("Exception") || output.contains("java"))) {

			int id = -1;
			try {
				id = Integer.valueOf(output);
				dbadapter.open();

				dbadapter.insertCommentInPosttoDb(id, Cmttxt.getText()
						.toString(), Postid, currentUser.getId(), serverDate,
						Commentid);

				dbadapter.close();
				// از پرینفسس استفده شده در زیر برای پیدا کردن آیدی گروه کامنت
				// ثبت شده در صفحه فروم استفاده می شود
				SharedPreferences groupId = context.getSharedPreferences("Id",
						0);
				int IdGroup = groupId.getInt("main_Id", -1);

				// از پریفرنسس استفاده شده در زیر برای رفتن به صفحه فروم استفاده
				// می شود تا در انجا با شناسایی مبدا ارسالی صفحه را آپدیت کرد

				final SharedPreferences realizeIdComment = context
						.getSharedPreferences("Id", 0);

				if (code == 1) {
					// az postWithoutComment vared shode

					((PostWithoutComment) f).setcount();
					realizeIdComment.edit().putInt("main_Id", 1371).commit();

					FragmentTransaction trans = ((MainActivity) context)
							.getSupportFragmentManager().beginTransaction();
					PostFragment fragment = new PostFragment();
					trans.setCustomAnimations(R.anim.pull_in_left,
							R.anim.push_out_right);
					Bundle b = new Bundle();
					b.putString("Id", String.valueOf(Postid));
					fragment.setArguments(b);

					trans.replace(R.id.content_frame, fragment);
					trans.commit();
					if (ringProgressDialog != null) {
						ringProgressDialog.dismiss();
					}
				}
				if (code == 2) {
					// az postFragment vared shode

					((PostFragment) f).updateList();
					if (ringProgressDialog != null) {
						ringProgressDialog.dismiss();
					}
				}
				if (code == 3) {
					// az expandableCommentFroum vared shode

//					((PostFragment) f).expanding(IdGroup);
					if (ringProgressDialog != null) {
						ringProgressDialog.dismiss();
					}

				}

				DialogcmtInpost.this.dismiss();

			} catch (NumberFormatException ex) {
				if (!"".equals(output)
						&& output != null
						&& !(output.contains("Exception") || output
								.contains("java"))) {

					params = new LinkedHashMap<String, String>();

					if (context != null) {
						saving = new Saving(context);
						saving.delegate = DialogcmtInpost.this;

						params.put("TableName", "CommentInPost");

						params.put("Desk", Cmttxt.getText().toString());
						params.put("PostId", String.valueOf(Postid));
						params.put("UserId",
								String.valueOf(currentUser.getId()));
						params.put("CommentId", String.valueOf(Commentid));
						/*params.put("NumofDisLike", String.valueOf(0));
						params.put("NumofLike", String.valueOf(0));*/
						params.put("Date", output);
						params.put("ModifyDate", output);
						params.put("IsUpdate", "0");
						params.put("Id", "0");
						serverDate = output;

						saving.execute(params);
					}
					ringProgressDialog = ProgressDialog.show(context, "",
							"لطفا منتظر بمانید...", true);

					ringProgressDialog.setCancelable(true);
					new Thread(new Runnable() {

						@Override
						public void run() {

							try {

								Thread.sleep(10000);

							} catch (Exception e) {

							}
						}
					}).start();

				} else

					Toast.makeText(context, "خطا در ثبت. پاسخ نا مشخص از سرور",
							Toast.LENGTH_SHORT).show();
			}

		} else
			Toast.makeText(context, "خطا در ثبت", 0).show();
	}
}
