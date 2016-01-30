package com.project.mechanic.service;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;

import android.content.Context;
import android.os.AsyncTask;

import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.inter.DataPersonalInterface;

//import org.ksoap2.transport.HttpTransportSE;
//import org.ksoap2.transport.HttpTransportSE;

public class UpdatingPersonalPage extends AsyncTask<String, Integer, String> {

	public String SOAP_ACTION = "http://tempuri.org/";

	public String OPERATION_NAME = "";

	public final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";

	public final String SOAP_ADDRESS = "http://srv.mechanical0098.com/MyService.asmx";

	public String response = "";

	public DataPersonalInterface delegate = null;

	public UpdatingPersonalPage(Context context) {
	}

	@Override
	protected String doInBackground(String... arg0) {
		OPERATION_NAME = "getAll" + arg0[0]+"ByUserId";
		SOAP_ACTION += OPERATION_NAME;

		try {

			SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,
					OPERATION_NAME);
			PropertyInfo pi = null;
			pi = new PropertyInfo();
			pi.setName("fromDate");
			pi.setValue(arg0[1]);
			pi.setType(String.class);
			request.addProperty(pi);

			pi = new PropertyInfo();
			pi.setName("endDate");
			pi.setValue(arg0[2]);
			pi.setType(String.class);
			request.addProperty(pi);

			pi = new PropertyInfo();
			pi.setName("isRefresh");
			pi.setValue(arg0[3]);
			pi.setType(Integer.class);
			request.addProperty(pi);
			
			pi = new PropertyInfo();
			pi.setName("userId");
			pi.setValue(arg0[4]);
			pi.setType(Integer.class);
			request.addProperty(pi);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setAddAdornments(false);
			envelope.implicitTypes = true;
			envelope.setOutputSoapObject(request);
			MyTransport httpTransport = new MyTransport(SOAP_ADDRESS);
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
			delegate.ResultServer(res);
	}
}
