package com.project.mechanic.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.project.mechanic.R;
import com.project.mechanic.PushNotification.SelectUserFragment;
import com.project.mechanic.entity.Users;
import com.project.mechanic.utility.Utility;

public class SelectUsersAdapter extends ArrayAdapter<Users> {
	List<Users> usersList;
	Fragment fr;
	Context context;
	Users user;
	boolean isAllChecked;
	int count = 0;
	boolean flag;

	public SelectUsersAdapter(Context context, int resource, List<Users> users,
			Fragment fr, boolean isAllChecked) {
		super(context, resource, users);
		this.usersList = users;
		this.fr = fr;
		this.context = context;
		this.isAllChecked = isAllChecked;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater myInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		convertView = myInflater.inflate(R.layout.row_select_users, parent,
				false);
		ImageView profileImg = (ImageView) convertView
				.findViewById(R.id.iconUsers);
		TextView name = (TextView) convertView.findViewById(R.id.textView1);

		final CheckBox checkBox = (CheckBox) convertView
				.findViewById(R.id.checkBox1);
		user = usersList.get(position);
		Bitmap bmp = null;
		String path = user.getImagePath();
		if (path != null)
			bmp = BitmapFactory.decodeFile(path);
		profileImg.setImageBitmap(Utility.getclip(bmp));

		name.setText(user.getName());

		if (isAllChecked == true)
			checkBox.setChecked(true);

		checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked == true) {
					count++;
					checkBox.setChecked(true);
					flag = true;
				}
				if (isChecked == false) {
					if (count > 0)
						count--;
					checkBox.setChecked(false);
					flag = false;

				}
			}
		});

		return convertView;
	}

	public int selectedItem() {
		return count;
	}
	
	public boolean CheckedOK() {
		return flag;
	}

}
