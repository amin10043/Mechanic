package com.project.mechanic.fragment;

import java.io.ByteArrayOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.mechanic.R;
import com.project.mechanic.model.DataBaseAdapter;

public class CompeleteRegisterFragment extends Fragment {

	protected static final int RESULT_LOAD_IMAGE = 1;
	DataBaseAdapter dbAdapter;

	public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 0, outputStream);
		return outputStream.toByteArray();
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.fragment_compeleteregister,
				null);

		dbAdapter = new DataBaseAdapter(getActivity());

		final EditText Addresstxt = (EditText) view.findViewById(R.id.Addtxt);
		final EditText Phonetxt = (EditText) view.findViewById(R.id.phonetxt);
		final EditText Emailtxt = (EditText) view.findViewById(R.id.Textemail);
		final EditText Faxtxt = (EditText) view.findViewById(R.id.faxtxt);
		final Button Compeletebtn = (Button) view
				.findViewById(R.id.compeleteregisterbtn);
		Button Backbtn = (Button) view.findViewById(R.id.backbtn);

		final EditText editname = (EditText) view.findViewById(R.id.Textname);
		final EditText edituser = (EditText) view.findViewById(R.id.mobiletxt);
		final EditText editpass = (EditText) view.findViewById(R.id.Textpass);

		// textrules.setText(Html.fromHtml("<html><a href=\"http://example.com/\">قوانین</a></html>"));
		// textrules.setMovementMethod(LinkMovementMethod.getInstance());
		// TelephonyManager tm
		// =(TelephonyManager)getActivity().getSystemService(Context.TELEPHONY_SERVICE);
		// String number = tm.getLine1Number();
		// Emailtxt.setText(number);

		Backbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				FragmentTransaction trans = getActivity()
						.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new MainFragment());
				trans.commit();

			}
		});

		Compeletebtn.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {

				dbAdapter = new DataBaseAdapter(getActivity());
				dbAdapter.open();

				// int id = Integer.valueOf(getArguments().getString("Id"));
				// Toast.makeText(getActivity(), ""+id,
				// Toast.LENGTH_LONG).show();
				// Users x =dbAdapter.getUserById(id);

				final String Email = Emailtxt.getText().toString();

				// if (!isValidEmail(Email)) {
				// Emailtxt.setError("Invalid Email");
				// }
				SharedPreferences server = getActivity().getSharedPreferences(
						"sId", 0);
				int item = server.getInt("srv_id", -1);
				// int item= Integer.valueOf(getArguments().getString("Id"));
				// int item=0;
				Toast.makeText(getActivity(), "" + item, Toast.LENGTH_SHORT)
						.show();

				// int item=1;

				dbAdapter.UpdateUserToDb(item, Emailtxt.getText().toString(),
						Phonetxt.getText().toString(), Faxtxt.getText()
								.toString(), Addresstxt.getText().toString());
				dbAdapter.close();
				Toast.makeText(getActivity(), "ثبت انجام شد  ",
						Toast.LENGTH_SHORT).show();

				Addresstxt.setText("");
				Phonetxt.setText("");
				Emailtxt.setText("");
				Faxtxt.setText("");
			}

		});
		return view;

	}

	private boolean isValidEmail(String email) {
		String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

}
