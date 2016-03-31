package com.project.mechanic.server;

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

import com.project.mechanic.inter.CommInterface;
import com.project.mechanic.interfaceServer.HappySadInterface;

public class GetHappySadServer extends AsyncTask<Map<String, String>, Integer, String> {

	public String SOAP_ACTION = "http://tempuri.org/";

	public String OPERATION_NAME = "";

	public final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";

	public final String SOAP_ADDRESS = "http://srv.mechanical0098.com/MyService.asmx";

	public String response = "";

	public HappySadInterface delegate = null;

	public GetHappySadServer(Context context) {
	}

	protected String doInBackground(Map<String, String>... action) {
		try {

			Iterator<Entry<String, String>> it = action[0].entrySet().iterator();
			Entry<String, String> item1 = it.next();

			OPERATION_NAME = item1.getValue();
			SOAP_ACTION += OPERATION_NAME;
			PropertyInfo pi = null;

			SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);
			Map.Entry<String, String> arg;
			while (it.hasNext()) {
				arg = it.next();
				if (arg != null) {
					pi = new PropertyInfo();
					pi.setName(arg.getKey());
					pi.setValue(arg.getValue());
					pi.setType(String.class);
					request.addProperty(pi);
				}
			}
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setAddAdornments(false);
			envelope.implicitTypes = true;
			envelope.setOutputSoapObject(request);
			HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
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
			delegate.ResultHappySad(res);
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}

}