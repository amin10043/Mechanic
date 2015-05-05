package com.project.mechanic.fragment;

import java.io.ByteArrayOutputStream;
import java.util.List;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mechanic.R;
import com.project.mechanic.entity.News;
import com.project.mechanic.entity.Ticket;
import com.project.mechanic.entity.Users;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;



public class DisplayeRullseFragment extends Fragment{
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_displayerullse, null);

		return view;
	}

	
	
	

	
	

}
