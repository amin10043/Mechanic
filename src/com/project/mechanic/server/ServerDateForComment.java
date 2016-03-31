package com.project.mechanic.server;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.project.mechanic.interfaceServer.DateForCommentInterface;
import com.project.mechanic.utility.Utility;

import android.content.Context;
import android.os.AsyncTask;

public class ServerDateForComment extends AsyncTask<String, Integer, String> {

	public String SOAP_ACTION = "http://tempuri.org/";

	public String OPERATION_NAME = "";

	public final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";

	public final String SOAP_ADDRESS = "http://srv.mechanical0098.com/MyService.asmx";

	public String response = "";
	private Context context;
	private Utility util;

	// private Context context;

	public DateForCommentInterface delegate = null;

	public ServerDateForComment(Context context) {
		this.context = context;
		util = new Utility(context);
	}

	@Override
	protected String doInBackground(String... arg0) {
		OPERATION_NAME = "getServerDateMilis";
		SOAP_ACTION += OPERATION_NAME;

		try {

			SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			// envelope.setAddAdornments(false);
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
			delegate.ReultDateComment(res);
	}

}
