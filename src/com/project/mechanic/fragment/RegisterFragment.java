package com.project.mechanic.fragment;


import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.jar.Attributes.Name;

import com.project.mechanic.R;
import com.project.mechanic.model.DataBaseAdapter;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


public class RegisterFragment extends Fragment {

	protected static final Context Contaxt = null;
	int resourceId;
	 Context context;
	 Fragment fragment;
    int ticketTypeID;
	 int ProvinceId;
	 
	 
	 String picturePath;

//		byte[] byteImage1 = null;
//		ContentValues newValues = new ContentValues();
//	public RegisterFragment(Context context, int resourceId, Fragment fragment,
//			int ticketTypeID, int ProvinceId) {
//		
//		// TODO Auto-generated constructor stub
//		this.resourceId = resourceId;
//		this.context = context;
//		this.fragment = fragment;
//		this.ticketTypeID = ticketTypeID;
//		this.ProvinceId = ProvinceId;
//	}

	protected static final int RESULT_LOAD_IMAGE = 1;
	private static final int PICK_IMAGE = 0;
	DataBaseAdapter dbAdapter;
	private Activity view;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 View view = inflater.inflate(R.layout.fragment_register, null);
		 
			dbAdapter = new DataBaseAdapter(getActivity());
			
			
	Button   btncan    = (Button) view.findViewById(R.id.btncancle2);
	Button   btnreg    =(Button) view.findViewById(R.id.btnreg2);
	ImageButton   btnaddpic = (ImageButton) view.findViewById(R.id.btnaddpic);
	final EditText editname  = (EditText)view.findViewById(R.id.editTextname);		 
    final EditText edituser  = (EditText)view.findViewById(R.id.editTextuser);		 
	final EditText editpass  = (EditText)view.findViewById(R.id.editTextpass);
		 
	
	 
	btnreg.setOnClickListener(new OnClickListener(){

	
		public void onClick(View arg0) {
			final String Name = editname.getText().toString(); 
			 final String user = edituser.getText().toString();  
			 final String pass = editpass.getText().toString(); 
			if (Name.equals("")&& user.equals("")&& pass.equals("")) {
				
				
				Toast.makeText(getActivity(), "لطفا فیلدهای مورد نظر را پر کنید  ", Toast.LENGTH_SHORT).show();
				
				
			} 
			
			else {

				
				
				
//				
////			        String name = "CoderzHeaven";
////			        newValues.put("name", name);
//			        try {
//			            FileInputStream instream = new FileInputStream(picturePath);
//			            BufferedInputStream bif = new BufferedInputStream(instream);
//		            byteImage1 = new byte[bif.available()];
//			            bif.read(byteImage1);
//			            newValues.put("image", byteImage1);
////			            long ret = dbAdapter.insert(TABLE_NAME, null, newValues);
//			           
//		        } catch (IOException e) {
////			            textView.append("Error Exception : " + e.getMessage());
//		        }
//			        dbAdapter.close();
			 //first Insert user to WS then insert to local
			        dbAdapter.open();
					dbAdapter.inserUserToDb( Name,user, pass);
			
			dbAdapter.close();
			
Toast.makeText(getActivity(), "اطلاعات مورد نظر ثبت شد", Toast.LENGTH_SHORT).show();
			
			
			}
			
			
		}
	 });
		 
		 
		 
		 
		 
	 
	 btncan.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			
			
			FragmentTransaction trans = getActivity()
					.getSupportFragmentManager().beginTransaction();
			trans.replace(R.id.content_frame, new LoginFragment());
			trans.commit();
		}
	 });
		 	 
		 
		 
	btnaddpic.setOnClickListener(new OnClickListener() {
		
		

	

		@Override
		public void onClick(View arg0) {
		Toast.makeText(getActivity(), "ok", Toast.LENGTH_LONG).show();
		Intent i = new Intent(
				Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

	getActivity().startActivityFromFragment(RegisterFragment.this, i,
			RESULT_LOAD_IMAGE);	
		}
	}); 
		 
		 
		 
		 
		 return view;
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
		 picturePath = cursor.getString(columnIndex);
		cursor.close();

		ImageView imageView = (ImageView) view.findViewById(R.id.btnaddpic);
	imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
		}

	}

	
	
	private EditText findViewById(int edittextuser) {
		
		// TODO Auto-generated method stub
		return null;
	}


}
