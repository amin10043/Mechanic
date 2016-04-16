package com.project.mechanic.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.content.Context;
import android.os.AsyncTask;

import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.utility.Utility;

public class Saving extends AsyncTask<Map<String, String>, Integer, String> {

	public String SOAP_ACTION = "http://tempuri.org/";

	public String OPERATION_NAME = "";

	public final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";

	public final String SOAP_ADDRESS = "http://srv.mechanical0098.com/MyService.asmx";

	public String response = "";
	private Context context;
	private Utility util;

	public AsyncInterface delegate = null;

	public Saving(Context context) {
		this.context = context;
		util = new Utility(context);
	}

	@Override
	protected String doInBackground(Map<String, String>... action) {
		OPERATION_NAME = "Saving";
		SOAP_ACTION += OPERATION_NAME;

		String isUpdate = action[0].get("IsUpdate");
		String Id = action[0].get("Id");

		action[0].remove("IsUpdate");
		action[0].remove("Id");

		Iterator<Entry<String, String>> it = action[0].entrySet().iterator();
		Entry<String, String> item1 = it.next();

		String param = "";
		Entry<String, String> item2;
		while (it.hasNext()) {
			item2 = it.next();
			param += item2.getKey() + "***:***" + item2.getValue() + "***-***";
		}

		try {
			SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,
					OPERATION_NAME);
			PropertyInfo pi = null;

			pi = new PropertyInfo();
			pi.setName("tableName");
			pi.setValue(item1.getValue());
			pi.setType(String.class);
			request.addProperty(pi);

			pi = new PropertyInfo();
			pi.setName("param");
			pi.setValue(param);
			pi.setType(String.class);
			request.addProperty(pi);

			pi = new PropertyInfo();
			pi.setName("IsUpdate");
			pi.setValue(isUpdate);
			pi.setType(String.class);
			request.addProperty(pi);

			pi = new PropertyInfo();
			pi.setName("Id");
			pi.setValue(Id);
			pi.setType(String.class);
			request.addProperty(pi);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setAddAdornments(false);
			envelope.implicitTypes = true;
			envelope.setOutputSoapObject(request);
			HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
			httpTransport
					.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
			// Object response = null;
			ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
			headerPropertyArrayList.add(new HeaderProperty("Connection",
					"close"));
			httpTransport.call(SOAP_ACTION, envelope, headerPropertyArrayList);
			Object response = envelope.getResponse();
			return response.toString();
		} catch (Exception e) {
			response = e.toString();
		}
		return response.toString();
	}

	protected void onPostExecute(String res) {

		if (delegate != null)
			delegate.processFinish(res);
	}

}
