package com.project.mechanic.fragment;

import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.model.DataBaseAdapter;

public class DialogAnadimg extends Dialog {

	protected static final Context Contaxt = null;

	protected static final EditText number = null;

	private static int RESULT_LOAD_IMAGE2 = 2;
	private static final int SELECT_PICTURE = 1;
	private ImageView imgdialoganad;
	private ImageButton sabt, enseraf;
	private Spinner sp;
	OnMyDialogResult mDialogResult;
	private DataBaseAdapter dbadapter;
	int resourceId;
	Context context;
	Fragment fragment;
	int ticketTypeID;
	int emailCheck = 0;
	int nameCheck = 0;
	int faxCheck = 0;
	List<Object> list;
	Object tempItem;
	int phoneCheck = 0;
	int mobileCheck = 0;
	String titel;
	String Bytimage;
	int ProvinceId;
	int id;
	int i;
	protected byte[] img;
	String TABLE_NAME = "Ticket";

	public DialogAnadimg(Context context, int resourceId, Fragment fragment,
			int ticketTypeID, int ProvinceId, int i) {
		super(context);
		// TODO Auto-generated constructor stub
		this.resourceId = resourceId;
		this.context = context;
		this.fragment = fragment;
		this.ticketTypeID = ticketTypeID;
		this.ProvinceId = ProvinceId;
		this.i = i;

	}

	// public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
	// ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	// bitmap.compress(CompressFormat.PNG, 0, outputStream);
	// return outputStream.toByteArray();
	// }

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));

		setContentView(resourceId);

		TextView txtdesc = (TextView) findViewById(R.id.txtdescdialog);
		CheckBox checkshart = (CheckBox) findViewById(R.id.checkBoxshart);
		final LinearLayout btndarj = (LinearLayout) findViewById(R.id.darjtabligh);

		txtdesc.setText(Html
				.fromHtml("<html>1-انتخاب عکس جهت آگهی باید مناسب و هماهنگ با فعالیت شما باشد <br><br>2-سایر کاربران با لمس کردن آگهی مربوطه شما مستقیما به لینک صفحه شما در این نرم افزار هدایت می شوند <br><br>3-این آگهی در 6 تالار مربوطه این لینک همزمان اجرا می شود <br><br>4-هزینه این آگهی به مبلغ 5.000.000 ریال و از تاریخ ثبت به مدت  یک سال می باشد <br><br></html>"));

		btndarj.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				FragmentTransaction trans = ((MainActivity) context)
						.getSupportFragmentManager().beginTransaction();
				show_pay_fragment fragment = new show_pay_fragment();
				Bundle bundle = new Bundle();
				Bundle bundlei = new Bundle();
				// bundle.putString("Id", String.valueOf(id));
				if (ProvinceId >= 0)
					// bundle.putString("ProID", String.valueOf(ProvinceId));
					bundlei.putString("I", String.valueOf(i));
				fragment.setArguments(bundle);
				fragment.setArguments(bundlei);
				trans.replace(R.id.content_frame, fragment);
				trans.addToBackStack(null);
				trans.commit();
				DialogAnadimg.this.dismiss();
				
				Toast.makeText(context, "here = " + i + "\n ostan = "+ ProvinceId, 0).show();

			}
		});

	}

	protected Resources getResources() {
		// TODO Auto-generated method stub
		return null;
	}

	protected Intent getIntent() {
		// TODO Auto-generated method stub
		return null;
	}

	public interface OnMyDialogResult {
		void finish(String result);
	}

	public void setDialogResult(OnMyDialogResult dialogResult) {
		mDialogResult = dialogResult;
	}

	public View getView() {
		return this.getLayoutInflater().inflate(resourceId, null);
	}

}
