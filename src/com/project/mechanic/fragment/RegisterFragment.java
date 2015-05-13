package com.project.mechanic.fragment;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mechanic.R;
import com.project.mechanic.entity.Users;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.ServiceComm;
import com.project.mechanic.utility.Utility;

public class RegisterFragment extends Fragment implements AsyncInterface {

	protected static final Context Contaxt = null;
	int resourceId;
	Context context;
	Fragment fragment;
	int ticketTypeID;
	int ProvinceId;
	ImageView btnaddpic1;
	List<Users> list;
	Utility utile;
	LinearLayout.LayoutParams lp;
	ServiceComm service;
	// byte[] byteImage1 = null;
	// ContentValues newValues = new ContentValues();
	// public RegisterFragment(Context context, int resourceId, Fragment
	// fragment,
	// int ticketTypeID, int ProvinceId) {
	//
	// // TODO Auto-generated constructor stub
	// this.resourceId = resourceId;
	// this.context = context;
	// this.fragment = fragment;
	// this.ticketTypeID = ticketTypeID;
	// this.ProvinceId = ProvinceId;
	// }

	protected static final int RESULT_LOAD_IMAGE = 1;
	DataBaseAdapter dbAdapter;
	private Activity view;

	TextView txtclickpic;

	public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 0, outputStream);
		return outputStream.toByteArray();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_register, null);
		utile = new Utility(getActivity());
		service = new ServiceComm(getActivity());
		// dbAdapter = new DataBaseAdapter(getActivity());
		btnaddpic1 = (ImageView) view.findViewById(R.id.btnaddpic);
		Button btncan = (Button) view.findViewById(R.id.btncancle2);
		final Button btnreg = (Button) view.findViewById(R.id.btnreg2);
		final TextView comregtxt = (TextView) view
				.findViewById(R.id.compeletereg);
		final EditText editname = (EditText) view
				.findViewById(R.id.editTextname);
		final EditText edituser = (EditText) view
				.findViewById(R.id.editTextuser);
		final EditText editpass = (EditText) view
				.findViewById(R.id.editTextpass);
		txtclickpic = (TextView) view.findViewById(R.id.txtclickpic);

		final LinearLayout lin1 = (LinearLayout) view.findViewById(R.id.lin1);

		btnaddpic1.setBackgroundResource(R.drawable.i13);
		// columnWidth = (int) (getScreenWidth() /3);

		lp = new LinearLayout.LayoutParams(lin1.getLayoutParams());
		lp.width = utile.getScreenwidth() / 3;
		lp.height = utile.getScreenwidth() / 3;
		btnaddpic1.setLayoutParams(lp);
		// btnaddpic1.setLayoutParams(lp);
		// l1.addView(btnaddpic1);
		// btnaddpic1.getLayoutParams().height = 150;
		// btnaddpic1.getLayoutParams().width = 150;
		// btnaddpic1.requestLayout();

		// ///////////////////////////////////
		// if (editname.getText().toString().equals("") &&
		// editpass.getText().toString().equals(""))
		// {
		//
		//
		//
		// comregtxt.setVisibility(View.GONE);
		// }
		//
		// else {
		//
		//
		//
		//
		// comregtxt.setVisibility(View.VISIBLE);
		// Toast.makeText(getActivity(),
		// "link faal shavad ",
		// Toast.LENGTH_SHORT).show();
		//
		//
		//
		// }

		// ///////////////////////////////////////////////////

		btnreg.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("unchecked")
			public void onClick(View arg0) {
				final String Name = editname.getText().toString();
				final String Email = edituser.getText().toString();
				final String Pass = editpass.getText().toString();

				PersianDate date = new PersianDate();
				String txtdate = date.todayShamsi();
				// Toast.makeText(getActivity(), txtdate,Toast.LENGTH_SHORT);

				TelephonyManager tm = (TelephonyManager) getActivity()
						.getSystemService(Context.TELEPHONY_SERVICE);
				String number = tm.getLine1Number();

				Toast.makeText(getActivity(), "" + number, Toast.LENGTH_SHORT)
						.show();

				if (Name.equals("") || Pass.equals("")) {

					Toast.makeText(getActivity(),
							"لطفا فيلدهاي اجباری را پر کنيد  ",
							Toast.LENGTH_SHORT).show();

				}

				else {

					txtclickpic.setVisibility(View.INVISIBLE);
					comregtxt.setVisibility(View.VISIBLE);
					btnreg.setEnabled(false);
					dbAdapter = new DataBaseAdapter(getActivity());
					dbAdapter.open();

					if ((btnaddpic1.getDrawable() == null)) {

						dbAdapter.inserUsernonpicToDb(Name, Email, Pass, null,
								number, null, null, 0, txtdate);

						Map<String, String> items = new HashMap<String, String>();
						items.put("register", "register");
						items.put("username", Name);
						items.put("email", Email);
						items.put("password", Pass);
						items.put("phone", "");
						items.put("mobile", "123");
						items.put("fax", "0");
						items.put("address", "");
						items.put("date", txtdate);

						service.delegate = RegisterFragment.this;
						service.execute(items);

						Toast.makeText(getActivity(),
								"اطلاعات مورد نظر بدون عکس ثبت شد",
								Toast.LENGTH_SHORT).show();

					} else {
						Bitmap bitmap = ((BitmapDrawable) btnaddpic1
								.getDrawable()).getBitmap();

						Bitmap emptyBitmap = Bitmap.createBitmap(
								bitmap.getWidth(), bitmap.getHeight(),
								bitmap.getConfig());

						if (bitmap.sameAs(emptyBitmap)) {
							dbAdapter.inserUsernonpicToDb(Name, Email, Pass,
									null, number, null, null, 0, txtdate);

						} else {

							byte[] Image = getBitmapAsByteArray(bitmap);

							dbAdapter.inserUserToDb(Name, Email, Pass, null,
									number, null, null, Image, 0, txtdate);

							dbAdapter.close();

							Toast.makeText(getActivity(),
									"اطلاعات مورد نظر ثبت شد",
									Toast.LENGTH_SHORT).show();

							// editname.setText("");
							// edituser.setText("");
							// editpass.setText("");
							//
						}
					}

				}

			}

		});

		comregtxt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				final String Name = editname.getText().toString();

				dbAdapter = new DataBaseAdapter(getActivity());
				dbAdapter.open();

				int id = dbAdapter.getcount();

				// Toast.makeText(getActivity(),
				// id+"",
				// Toast.LENGTH_SHORT).show();
				dbAdapter.close();
				// for (Users u: list) {
				// if (Name.equals(u.getName())) {
				// // check authentication and authorization
				// id = u.getId();
				// }
				// }

				FragmentTransaction trans = getActivity()
						.getSupportFragmentManager().beginTransaction();
				CompeleteRegisterFragment fragment = new CompeleteRegisterFragment();

				Bundle bundle = new Bundle();

				bundle.putString("Id", String.valueOf(id));
				fragment.setArguments(bundle);
				trans.replace(R.id.content_frame, fragment);
				trans.commit();

			}
		});

		btncan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				FragmentTransaction trans = getActivity()
						.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new LoginFragment());
				trans.commit();
			}
		});

		btnaddpic1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// Toast.makeText(getActivity(), "ok",
				// Toast.LENGTH_LONG).show();

				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				getActivity().startActivityFromFragment(RegisterFragment.this,
						i, RESULT_LOAD_IMAGE);
			}
		});

		return view;
	}

	@Override
	public void processFinish(String output) {
		if ("0".equals(output)) {
			Toast.makeText(getActivity(), "khata", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(getActivity(), "sabt shod ", Toast.LENGTH_SHORT)
					.show();
		}

	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RESULT_LOAD_IMAGE
				&& resultCode == Activity.RESULT_OK && null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getActivity().getContentResolver().query(
					selectedImage, filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();

			// ImageView btnaddpic1 = (ImageView) view
			// .findViewById(R.id.btnaddpic);
			btnaddpic1.setImageBitmap(BitmapFactory.decodeFile(picturePath));
			btnaddpic1.setBackgroundColor(getResources().getColor(
					android.R.color.transparent));

			btnaddpic1.setLayoutParams(lp);
			txtclickpic.setVisibility(View.INVISIBLE);
		}

	}

}
