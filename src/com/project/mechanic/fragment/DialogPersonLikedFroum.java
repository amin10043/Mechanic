package com.project.mechanic.fragment;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;

import com.project.mechanic.R;
import com.project.mechanic.adapter.PersonLikedAdapter;
import com.project.mechanic.entity.LikeInFroum;
import com.project.mechanic.model.DataBaseAdapter;

public class DialogPersonLikedFroum extends Dialog {

	Context context;
	DataBaseAdapter adapter;
	int FroumId;
	ListView lv;
	DialogPersonLikedFroum dia;
	ArrayList<LikeInFroum> likedist;

	public DialogPersonLikedFroum(Context context, int FroumId,
			ArrayList<LikeInFroum> list) {
		super(context);

		this.context = context;
		this.FroumId = FroumId;
		this.likedist = list;
		adapter = new DataBaseAdapter(context);
	}
	
	public DialogPersonLikedFroum(Context context) {
		super(context);

			adapter = new DataBaseAdapter(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));

		setContentView(R.layout.dialog_person_liked);
		lv = (ListView) findViewById(R.id.listPeronLiked);

		PersonLikedAdapter listadapter = new PersonLikedAdapter(context,
				R.layout.row_person_liked, likedist);
		lv.setAdapter(listadapter);	

	}
	
}
