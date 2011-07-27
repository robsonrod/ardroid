package br.org.casamonitor.arduino.gerencia;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import br.org.casamonitor.arduino.Sensor;

public class ArduinoJson {

	public static final String URL_ARDUINO = "http://192.168.0.177";
	protected static ArduinoJson jsonUtils;
	public static final String LOG_INFO = "Json";

	protected ArduinoJson() {

	}

	public static ArduinoJson get() {
		if (jsonUtils == null) {
			jsonUtils = new ArduinoJson();
		}
		return jsonUtils;

	}

	public JSONArray getResultadoEmArray() {
		JSONArray array = null;

		String result = recuperaTodosNaUrl(URL_ARDUINO);
		try {
			JSONObject object = new JSONObject(result);
			array = object.getJSONArray("devices");
		} catch (JSONException e) {
			Log.e("Não foi possivel fazer o parsing", e.getMessage());
		}
		return array;
	}

	public Sensor getSensor(final String url) {
		Sensor sensor = new Sensor();
		// String result = recuperaTodosNaUrl(URL_ARDUINO + url);
		String result = "{device: [{estado: ok, value: 28.66}]}";
		try {
			JSONObject object = new JSONObject(result);
			JSONObject jsonObject = object.getJSONArray("device")
					.getJSONObject(0);
			sensor.setEstado(jsonObject.getString("estado"));
			sensor.setEstado(jsonObject.getDouble("value"));
		} catch (JSONException e) {
			Log.e("Não foi possivel fazer o parsing", e.getMessage());
		}

		return sensor;
	}

	private String recuperaTodosNaUrl(final String url) {
		Log.i(LOG_INFO, "Buscando Url");

		String resultado = null;

		try {
			Log.i(LOG_INFO, "Montando URL");
			URI uri = new URI(url);
			HttpGet httpget = new HttpGet(uri);

			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, 0);
			HttpConnectionParams.setSoTimeout(httpParameters, 0);
			HttpClient httpClient = new DefaultHttpClient(httpParameters);
			HttpResponse response = httpClient.execute(httpget);
			HttpEntity entity = response.getEntity();

			Log.i(LOG_INFO, "URL montada, acessando componente");
			if (entity != null) {
				InputStream inputStream = entity.getContent();
				resultado = converteStreamEmString(inputStream);

			}

		} catch (URISyntaxException e) {
			e.printStackTrace();
			Log.e(LOG_INFO, "Exceção 1", e);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			Log.e(LOG_INFO, "Exceção 2", e);
		} catch (IOException e) {
			e.printStackTrace();
			Log.e(LOG_INFO, "Exceção 3", e);
		}

		return resultado;
	}

	private static String converteStreamEmString(InputStream inputStream) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				inputStream));
		StringBuilder sb = new StringBuilder();
		String line = null;

		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

}