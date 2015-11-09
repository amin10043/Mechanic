package com.project.mechanic.fragment;

import java.util.LinkedHashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.service.Saving;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.utility.Utility;

@SuppressLint("ValidFragment")
public class ReportAbuseFragment extends Fragment implements AsyncInterface {
	Utility util;
	ServerDate date;
	ProgressDialog ringProgressDialog;
	Map<String, String> params;
	Saving saving;
	int source, userIdSender, itemId;
	String content, serverDate;
	boolean flag = false;
	int backId;
	int ticketType, proviceId;

//	public ReportAbuseFragment(int ticketType, int provinceId) {
//		
//		this.ticketType = ticketType;
//		this.proviceId = provinceId;
//	}

	public ReportAbuseFragment(int backId) {
		this.backId = backId;
	}

	public ReportAbuseFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		util = new Utility(getActivity());

		userIdSender = getArguments().getInt("userIdSender");
		source = getArguments().getInt("source");
		itemId = getArguments().getInt("itemId");
		content = getArguments().getString("content");
		
		

		View rootView = inflater.inflate(R.layout.fragment_report_abuse, null);

		final RadioGroup rdGroup = (RadioGroup) rootView.findViewById(R.id.rb1);

		RadioButton r1 = (RadioButton) rootView.findViewById(R.id.r1);
		RadioButton r2 = (RadioButton) rootView.findViewById(R.id.r2);
		RadioButton r3 = (RadioButton) rootView.findViewById(R.id.r3);
		RadioButton r4 = (RadioButton) rootView.findViewById(R.id.r4);
		RadioButton r5 = (RadioButton) rootView.findViewById(R.id.r5);
		if (source != 3) {
			r2.setVisibility(View.GONE);
			r3.setVisibility(View.GONE);
			r4.setVisibility(View.GONE);
			r1.setText("محتوا نامناسب است");
			r5.setText("مطلب در دسته بندی نامربوط قرار گرفته است");
		}
		Button sendBtn = (Button) rootView.findViewById(R.id.report_item);

		sendBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				date = new ServerDate(getActivity());
				date.delegate = ReportAbuseFragment.this;
				date.execute("");

				ringProgressDialog = ProgressDialog.show(getActivity(), "",
						"لطفا منتظر بمانید...", true);

				ringProgressDialog.setCancelable(true);
				new Thread(new Runnable() {

					@Override
					public void run() {

						try {

							Thread.sleep(10000);

						} catch (Exception e) {

						}
					}
				}).start();

			}
		});

		util.ShowFooterAgahi(getActivity(), false, 0);
		return rootView;
	}

	@Override
	public void processFinish(String output) {

		if (ringProgressDialog != null) {
			ringProgressDialog.dismiss();
		}
		// try {

		if (flag == true) {
			switch (source) {
			case 1: {

				FragmentTransaction trans = ((MainActivity) getActivity())
						.getSupportFragmentManager().beginTransaction();
				FroumtitleFragment fragment = new FroumtitleFragment();
				trans.setCustomAnimations(R.anim.pull_in_left,
						R.anim.push_out_right);

				trans.replace(R.id.content_frame, fragment);
				trans.commit();

				break;
			}
			case 2: {

				FragmentTransaction trans = ((MainActivity) getActivity())
						.getSupportFragmentManager().beginTransaction();
				TitlepaperFragment fragment = new TitlepaperFragment();
				trans.setCustomAnimations(R.anim.pull_in_left,
						R.anim.push_out_right);

				trans.replace(R.id.content_frame, fragment);
				trans.commit();

				break;
			}
			case 3: {

				ticketType =  getArguments().getInt("ticketTypeId");
				proviceId =  getArguments().getInt("provinceId");

				FragmentTransaction trans = ((MainActivity) getActivity())
						.getSupportFragmentManager().beginTransaction();
				AnadFragment fragment = new AnadFragment();
				trans.setCustomAnimations(R.anim.pull_in_left,
						R.anim.push_out_right);
				
				Bundle bundle = new Bundle();

				bundle.putString("Id", String.valueOf(ticketType));
				bundle.putString("ProID", String.valueOf(proviceId));
				fragment.setArguments(bundle);

				trans.replace(R.id.content_frame, fragment);
				trans.commit();

				break;
			}
			case 5: {

				FragmentTransaction trans = ((MainActivity) getActivity())
						.getSupportFragmentManager().beginTransaction();
				FroumFragment fragment = new FroumFragment();
				trans.setCustomAnimations(R.anim.pull_in_left,
						R.anim.push_out_right);

				Bundle bundle = new Bundle();

				bundle.putString("Id", String.valueOf(backId));
				fragment.setArguments(bundle);

				trans.replace(R.id.content_frame, fragment);
				trans.commit();

				break;
			}
			case 6: {

				FragmentTransaction trans = ((MainActivity) getActivity())
						.getSupportFragmentManager().beginTransaction();
				PaperFragment fragment = new PaperFragment();

				Bundle bundle = new Bundle();
				bundle.putString("Id", String.valueOf(backId));
				fragment.setArguments(bundle);

				trans.setCustomAnimations(R.anim.pull_in_left,
						R.anim.push_out_right);

				trans.replace(R.id.content_frame, fragment);
				trans.commit();

				break;
			}

			default:
				break;
			}

		} else {
			// } catch (NumberFormatException e) {

			serverDate = output;

			if (output != null
					&& !(output.contains("Exception") || output
							.contains("java"))) {

				params = new LinkedHashMap<String, String>();
				saving = new Saving(getActivity());
				saving.delegate = ReportAbuseFragment.this;

				params.put("TableName", "Report");

				params.put("[Desc]", content);
				params.put("UserIdSender", String.valueOf(userIdSender));
				params.put("UserIdReporter",
						String.valueOf(util.getCurrentUser().getId()));
				params.put("SourceId", String.valueOf(itemId));
				params.put("TypeId", String.valueOf(source));
				params.put("Date", serverDate);

				params.put("IsUpdate", "0");
				params.put("Id", "0");

				saving.execute(params);

				ringProgressDialog = ProgressDialog.show(getActivity(), "",
						"لطفا منتظر بمانید...", true);

				ringProgressDialog.setCancelable(true);
				new Thread(new Runnable() {

					@Override
					public void run() {

						try {

							Thread.sleep(10000);

						} catch (Exception e) {

						}
					}
				}).start();

				flag = true;

			} else {
				Toast.makeText(getActivity(),
						"خطا در ثبت. پاسخ نا مشخص از سرور", Toast.LENGTH_SHORT)
						.show();
			}
		}

	}
}
