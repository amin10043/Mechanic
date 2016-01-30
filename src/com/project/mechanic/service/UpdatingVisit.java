package com.project.mechanic.service;

import java.util.Map;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;

import android.content.Context;
import android.os.AsyncTask;

import com.project.mechanic.inter.AsyncInterfaceVisit;

public class UpdatingVisit extends AsyncTask<Map<String, String>, Integer, String> {

	public String SOAP_ACTION = "http://tempuri.org/";

	public String OPERATION_NAME = "";

	public final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";

	public final String SOAP_ADDRESS = "http://srv.mechanical0098.com/MyService.asmx";

	public String response = "";

	public AsyncInterfaceVisit delegate = null;

	public UpdatingVisit(Context context) {
	}

	@Override
	protected String doInBackground(Map<String, String>... arg0) {
		Map<String,String> map = arg0[0];
		OPERATION_NAME = "getAll" + map.get("tableName");
		SOAP_ACTION += OPERATION_NAME;

		try {
			SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,
					OPERATION_NAME);
			PropertyInfo pi = null;
			pi = new PropertyInfo();
			pi.setName("objectId");
			pi.setValue(map.get("objectId"));
			pi.setType(Integer.class);
			request.addProperty(pi);

			pi = new PropertyInfo();
			pi.setName("typeId");
			pi.setValue(map.get("typeId"));
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
			delegate.processFinishVisit(res);
	}
}
