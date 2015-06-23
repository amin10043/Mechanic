package com.project.mechanic.service;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.content.Context;
import android.os.AsyncTask;

import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.utility.Utility;

public class UpdatingAllDetail extends AsyncTask<String, Integer, String> {

	public String SOAP_ACTION = "http://tempuri.org/";

	public String OPERATION_NAME = "";

	public final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";

	public final String SOAP_ADDRESS = "http://srv.mechanical0098.com/MyService.asmx";

	public String response = "";
	private Context context;
	private Utility util;

	public AsyncInterface delegate = null;

	public UpdatingAllDetail(Context context) {
		this.context = context;
		util = new Utility(context);
	}

	@Override
	protected String doInBackground(String... arg0) {
		OPERATION_NAME = "getAllUpdateDetails";
		SOAP_ACTION += OPERATION_NAME;

		try {

			SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,
					OPERATION_NAME);
			PropertyInfo pi = null;
			pi = new PropertyInfo();
			pi.setName("fromDate");
			pi.setValue(arg0[0]);
			pi.setType(String.class);
			request.addProperty(pi);

			pi = new PropertyInfo();
			pi.setName("from");
			pi.setValue(arg0[1]);
			pi.setType(Integer.class);
			request.addProperty(pi);

			pi = new PropertyInfo();
			pi.setName("to");
			pi.setValue(arg0[2]);
			pi.setType(Integer.class);
			request.addProperty(pi);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setAddAdornments(false);
			envelope.implicitTypes = true;
			envelope.setOutputSoapObject(request);
			// MyTransport httpTransport = new MyTransport(SOAP_ADDRESS);
			HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
			httpTransport
					.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
			// Object response = null;
			httpTransport.call(SOAP_ACTION, envelope);
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
