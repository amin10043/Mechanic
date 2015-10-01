package com.project.mechanic.fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mechanic.R;
import com.project.mechanic.utility.Utility;
import com.project.mechanic.zoomImage.TouchImageView;
import com.project.mechanic.zoomImage.TouchImageView.OnTouchImageViewListener;

public class DialogShowImage extends Dialog {
	Context context;
	String pathImage, namePage;
	Utility utility;
	byte[] imageByte;
	private TouchImageView image;

	public DialogShowImage(Context context, String pathImage, String namePage) {
		super(context);
		this.pathImage = pathImage;
		this.namePage = namePage;
		utility = new Utility(context);
	}

	public DialogShowImage(Context context, byte[] imageByte, String nameUser) {
		super(context);
		this.imageByte = imageByte;
		this.namePage = nameUser;

		utility = new Utility(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));

		setContentView(R.layout.dialog_show_image);

		image = (TouchImageView) findViewById(R.id.imggg);
		TextView name = (TextView) findViewById(R.id.person_picture);

		name.setText(namePage);

		if (pathImage != null) {
			Bitmap imageBitmap = BitmapFactory.decodeFile(pathImage);
			image.setImageBitmap(imageBitmap);

		} else
			image.setImageResource(R.drawable.no_image_header);

		setSizeImage(image);

		if (imageByte != null) {
			Bitmap bmp = BitmapFactory.decodeByteArray(imageByte, 0,
					imageByte.length);
			image.setImageBitmap(bmp);
		} else {
			if (pathImage != null) {
				Bitmap imageBitmap = BitmapFactory.decodeFile(pathImage);
				image.setImageBitmap(imageBitmap);

			} else
				image.setImageResource(R.drawable.no_image_header);
		}

	}

	private void setSizeImage(ImageView img) {

		RelativeLayout layout = (RelativeLayout) findViewById(R.id.layout_image);

		RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(
				layout.getLayoutParams());

		rl.width = utility.getScreenwidth();
		rl.height = utility.getScreenwidth();
		rl.addRule(RelativeLayout.CENTER_IN_PARENT);

		img.setLayoutParams(rl);

	}

}
