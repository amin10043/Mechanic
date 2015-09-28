package com.project.mechanic.fragment;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.project.mechanic.R;
import com.project.mechanic.adapter.AnadListAdapter;
import com.project.mechanic.entity.Ticket;
import com.project.mechanic.entity.Users;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.inter.GetAsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.service.UpdatingImage;
import com.project.mechanic.utility.Utility;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DialogManagementTicket extends Dialog implements
		GetAsyncInterface, AsyncInterface {

	Context context;
	List<Ticket> listTicket;
	GridView ticketGrid;
	Fragment fragment;
	TextView namePage;
	DataBaseAdapter dbAdapter;

	UpdatingImage update;
	Map<String, String> maps;
	Utility util;

	int TicketId, counterTicketList;

	ServerDate date;

	String todayDate;

	RelativeLayout noItem;

	AnadListAdapter anadGridAdapter;

	public DialogManagementTicket(Context context, List<Ticket> listTicket,
			Fragment fragment) {
		super(context);
		this.context = context;
		this.listTicket = listTicket;
		this.fragment = fragment;
		dbAdapter = new DataBaseAdapter(context);
		util = new Utility(context);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));

		setContentView(R.layout.dialog_managment_ticket);

		ticketGrid = (GridView) findViewById(R.id.anad_grideView);
		namePage = (TextView) findViewById(R.id.namepageList);
		noItem = (RelativeLayout) findViewById(R.id.noPage);

		if (listTicket.size() == 0) {
			noItem.setVisibility(View.VISIBLE);
		} else {

			anadGridAdapter = new AnadListAdapter(context, R.layout.row_anad,
					listTicket, -1, fragment, true);

			ticketGrid.setAdapter(anadGridAdapter);
		}

		Users currentUser = util.getCurrentUser();
		namePage.setText(currentUser.getName());

		if (context != null) {
			date = new ServerDate(context);
			date.delegate = DialogManagementTicket.this;
			date.execute("");
		}
	}

	private void getTicketImageFromServer(List<Ticket> ticketList,
			int counterTicketList) {

		Ticket ticketItem;

		if (counterTicketList < ticketList.size()) {

			ticketItem = ticketList.get(counterTicketList);
			TicketId = ticketItem.getId();
			String t = ticketItem.getImageServerDate();
			if (ticketItem.getImageServerDate() == null
					|| ticketItem.getImageServerDate().equals("")) {
				t = "";

				if (context != null) {
					update = new UpdatingImage(context);
					update.delegate = DialogManagementTicket.this;
					maps = new LinkedHashMap<String, String>();
					maps.put("tableName", "Ticket");
					maps.put("Id", String.valueOf(TicketId));
					maps.put("fromDate", t);
					update.execute(maps);
				}
			}
		} else {
			AnadListAdapter anadGridAdapter = new AnadListAdapter(context,
					R.layout.row_anad, listTicket, -1, fragment, false);

			ticketGrid.setAdapter(anadGridAdapter);
			anadGridAdapter.notifyDataSetChanged();
		}
	}

	@Override
	public void processFinish(byte[] output) {

		if (output != null) {

			boolean IsEmptyByte = util.IsEmptyByteArrayImage(output);
			if (IsEmptyByte == false) {
				util.CreateFile(output, TicketId, "Mechanical", "Ticket",
						"ticket", "Ticket");
			}
		}

		counterTicketList++;
		getTicketImageFromServer(listTicket, counterTicketList);

	}

	@Override
	public void processFinish(String output) {

		todayDate = output;
		anadGridAdapter = new AnadListAdapter(context, R.layout.row_anad,
				listTicket, -1, fragment, true);
		anadGridAdapter.notifyDataSetChanged();

		// if (listTicket.size() == 0) {
		// noItem.setVisibility(View.VISIBLE);
		// } else {
		//
		AnadListAdapter anadGridAdapter = new AnadListAdapter(context,
				R.layout.row_anad, listTicket, -1, fragment, true);

		ticketGrid.setAdapter(anadGridAdapter);
		getTicketImageFromServer(listTicket, 0);

		// }
		//
		// getTicketImageFromServer(listTicket, 0);
		//
		// Users currentUser = util.getCurrentUser();
		// namePage.setText(currentUser.getName());
		// }
	}
}
