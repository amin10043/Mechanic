package com.project.mechanic.service;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.content.Context;
import android.os.AsyncTask;

import com.project.mechanic.inter.GetAsyncInterface;
import com.project.mechanic.utility.Utility;

public class UpdatingImage extends
		AsyncTask<Map<String, String>, Integer, byte[]> {
	public String SOAP_ACTION = "http://tempuri.org/";

	public String OPERATION_NAME = "";

	public final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";

	public final String SOAP_ADDRESS = "http://srv.mechanical0098.com/MyService.asmx";

	public String response = "";
	private Context context;
	private Utility util;
	byte[] res = null;

	public GetAsyncInterface delegate = null;

	public UpdatingImage(Context context) {
		this.context = context;
		util = new Utility(context);
	}

	@Override
	protected byte[] doInBackground(Map<String, String>... action) {
		try {
			Iterator<Entry<String, String>> it = action[0].entrySet()
					.iterator();
			Entry<String, String> item1 = it.next();

			OPERATION_NAME = "get" + item1.getValue() + "Image";
			SOAP_ACTION += OPERATION_NAME;
			SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,
					OPERATION_NAME);

			PropertyInfo pi = null;

			pi = new PropertyInfo();
			pi.setName("tableName");
			pi.setValue(item1.getValue());
			pi.setType(String.class);
			request.addProperty(pi);

			Entry<String, String> item2 = it.next();
			pi = new PropertyInfo();
			pi.setName("id");
			pi.setValue(item2.getValue());
			pi.setType(String.class);
			request.addProperty(pi);

			Entry<String, String> item3 = it.next();
			pi = new PropertyInfo();
			pi.setName("fromDate");
			pi.setValue(item3.getValue());
			pi.setType(String.class);
			request.addProperty(pi);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setAddAdornments(false);
			envelope.implicitTypes = true;
			envelope.setOutputSoapObject(request);
			HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
			// Object response = null;
			httpTransport.call(SOAP_ACTION, envelope);
			Object response = envelope.getResponse();
			res = (byte[]) response;
		} catch (Exception e) {
			response = e.toString();
		}
		return res;

	}

	protected void onPostExecute(byte[] res) {

		if (delegate != null)
			delegate.processFinish(res);
	}
}
