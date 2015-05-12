package com.project.mechanic.utility;

import java.util.Map;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.content.Context;
import android.os.AsyncTask;

import com.project.mechanic.inter.AsyncInterface;

public class ServiceComm extends
		AsyncTask<Map.Entry<String, String>, Integer, String> {

	public String SOAP_ACTION = "http://tempuri.org/";

	public String OPERATION_NAME = "";

	public final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";

	public final String SOAP_ADDRESS = "http://srv.mechanical0098.com/MyService.asmx";

	public String response = "";

	// private Context context;

	public AsyncInterface delegate = null;

	public ServiceComm(Context context) {
		// this.context = context;
	}

	protected String doInBackground(Map.Entry<String, String>... action) {
		try {

			OPERATION_NAME = action[0].getValue();
			SOAP_ACTION += OPERATION_NAME;
			PropertyInfo pi = null;

			SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,
					OPERATION_NAME);
			Map.Entry<String, String> arg;
			for (int i = 1; i < action.length; ++i) {
				arg = action[i];
				if (arg != null) {
					pi = new PropertyInfo();
					pi.setName(arg.getKey());
					pi.setValue(arg.getValue());
					pi.setType(String.class);
					request.addProperty(pi);
				}
			}
			// String arg0 = action[1];
			// if (arg0 != null) {
			// pi = new PropertyInfo();
			// pi.setName("arg0");
			// pi.setValue(arg0);
			// pi.setType(Integer.class);
			// request.addProperty(pi);
			// }
			//
			// arg0 = action[2];
			// if (arg0 != null) {
			// pi = new PropertyInfo();
			// pi.setName("arg1");
			// pi.setValue(arg0);
			// pi.setType(Integer.class);
			// request.addProperty(pi);
			// }

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
			return response.toString();
		} catch (Exception e) {
			response = e.toString();
		}
		return response.toString();
	}

	protected void onPostExecute(String res) {
		// Toast.makeText(context, res, Toast.LENGTH_SHORT).show();
		delegate.processFinish(res);
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