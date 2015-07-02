package com.project.mechanic.service;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.content.Context;
import android.os.AsyncTask;

import com.project.mechanic.inter.SaveAsyncInterface;
import com.project.mechanic.utility.Utility;

public class SavingImage3Picture extends
		AsyncTask<Map<String, Object>, Integer, String> {

	public String SOAP_ACTION = "http://tempuri.org/";

	public String OPERATION_NAME = "";

	public final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";

	public final String SOAP_ADDRESS = "http://srv.mechanical0098.com/MyService.asmx";

	public String response = "";
	private Context context;
	private Utility util;

	public SaveAsyncInterface delegate = null;

	public SavingImage3Picture(Context context) {
		this.context = context;
		util = new Utility(context);
	}

	@Override
	protected String doInBackground(Map<String, Object>... action) {

		OPERATION_NAME = "savingImage";
		SOAP_ACTION += OPERATION_NAME;
		SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,
				OPERATION_NAME);

		Iterator<Entry<String, Object>> it = action[0].entrySet().iterator();
		Entry<String, Object> item1 = it.next();

		PropertyInfo pi = null;

		pi = new PropertyInfo();
		pi.setName("tableName");
		pi.setValue(item1.getValue());
		pi.setType(String.class);
		request.addProperty(pi);

		Entry<String, Object> itemf1 = it.next();
		pi = new PropertyInfo();
		pi.setName("fieldName1");
		pi.setValue(itemf1.getValue());
		pi.setType(String.class);
		request.addProperty(pi);

		Entry<String, Object> itemf2 = it.next();
		pi = new PropertyInfo();
		pi.setName("fieldName2");
		pi.setValue(itemf2.getValue());
		pi.setType(String.class);
		request.addProperty(pi);

		Entry<String, Object> itemf3 = it.next();
		pi = new PropertyInfo();
		pi.setName("fieldName3");
		pi.setValue(itemf3.getValue());
		pi.setType(String.class);
		request.addProperty(pi);

		Entry<String, Object> item3 = it.next();
		pi = new PropertyInfo();
		pi.setName("id");
		pi.setValue(item3.getValue());
		pi.setType(String.class);
		request.addProperty(pi);

		Entry<String, Object> itemi1 = it.next();
		pi = new PropertyInfo();
		pi.setName("image1");
		pi.setValue(itemi1.getValue());
		pi.setType(byte[].class);
		request.addProperty(pi);

		Entry<String, Object> itemi2 = it.next();
		pi = new PropertyInfo();
		pi.setName("image2");
		pi.setValue(itemi2.getValue());
		pi.setType(byte[].class);
		request.addProperty(pi);

		Entry<String, Object> itemi3 = it.next();
		pi = new PropertyInfo();
		pi.setName("image3");
		pi.setValue(itemi3.getValue());
		pi.setType(byte[].class);
		request.addProperty(pi);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		new MarshalBase64().register(envelope);
		envelope.dotNet = true;
		envelope.setAddAdornments(false);
		envelope.implicitTypes = true;
		envelope.setOutputSoapObject(request);
		HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
		try {
			httpTransport.call(SOAP_ACTION, envelope);
			Object res = envelope.getResponse();
			response = res.toString();
		} catch (Exception e) {
			response = e.getMessage();
		}
		return response.toString();
	}

	protected void onPostExecute(String res) {

		if (delegate != null)
			delegate.processFinishSaveImage(res);
	}

}
