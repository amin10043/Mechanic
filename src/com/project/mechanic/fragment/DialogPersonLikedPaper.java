package com.project.mechanic.fragment;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.adapter.PersonLikedPaperAdapter;
import com.project.mechanic.entity.LikeInPaper;
import com.project.mechanic.entity.Users;
import com.project.mechanic.model.DataBaseAdapter;

public class DialogPersonLikedPaper extends Dialog {
	int PaperId;
	ArrayList<LikeInPaper> listLike;
	DataBaseAdapter adapter;
	ListView lv;
	Context context;

	public DialogPersonLikedPaper(Context context, int PaperId,
			ArrayList<LikeInPaper> likelist) {
		super(context);
		this.context = context;
		this.PaperId = PaperId;
		this.listLike = likelist;
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

		PersonLikedPaperAdapter listadapter = new PersonLikedPaperAdapter(
				context, R.layout.row_person_liked, listLike);
		lv.setAdapter(listadapter);

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

				LikeInPaper likes = listLike.get(position);
				adapter.open();
				Users user = adapter.getUserById(likes.getUserid());
				adapter.close();
				int userId = user.getId();
				FragmentTransaction trans = ((MainActivity) context)
						.getSupportFragmentManager().beginTransaction();
				InformationUser fragment = new InformationUser();
				Bundle bundle = new Bundle();
				bundle.putInt("userId", userId);
				fragment.setArguments(bundle);
				trans.replace(R.id.content_frame, fragment);
				trans.commit();
				dismiss();
			}
		});

	}

}
