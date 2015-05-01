package com.project.mechanic.fragment;

import java.io.ByteArrayOutputStream;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

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
	protected byte[] img;
	String TABLE_NAME = "Ticket";

	public DialogAnadimg(Context context, int resourceId, Fragment fragment,
			int ticketTypeID, int ProvinceId) {
		super(context);
		// TODO Auto-generated constructor stub
		this.resourceId = resourceId;
		this.context = context;
		this.fragment = fragment;
		this.ticketTypeID = ticketTypeID;
		this.ProvinceId = ProvinceId;

	}

	public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 0, outputStream);
		return outputStream.toByteArray();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.dialog_addcomment);

		// id = Integer.valueOf(getArguments().getString("Id"));

		setContentView(resourceId);

		TextView txtdesc = (TextView) findViewById(R.id.txtdescdialog);
		CheckBox checkshart = (CheckBox) findViewById(R.id.checkBoxshart);
		final Button btndarj = (Button) findViewById(R.id.darjtabligh);

		checkshart.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				btndarj.setVisibility(1);

			}
		});
		btndarj.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// FragmentTransaction trans = ((MainActivity)
				// context).getSupportFragmentManager().beginTransaction();
				// ShowAdFragment fragment = new ShowAdFragment();
				// Bundle bundle = new Bundle();
				// bundle.putString("Id", String.valueOf(id));
				// fragment.setArguments(bundle);
				// trans.replace(R.id.content_frame, fragment);
				// trans.addToBackStack(null);
				// trans.commit();
				//
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
