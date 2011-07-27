package br.org.casamonitor.arduino.gerencia;

import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Menu;
import android.view.MenuInflater;
import br.org.casamonitor.arduino.Sensor;
import br.org.casamonitor.sensores.ISensorActivity;

public class ArduinoUtils extends Activity {

	public static void sincronizar(Activity activity) {
		ProgressDialog progressDialog = new ProgressDialog(activity);
		progressDialog.setTitle("Sincronização");
		progressDialog.setMessage("Sincronizando...");
		progressDialog.show();
	}

	public Sensor getSensor(ISensorActivity sensor) {
		return ArduinoJson.get().getSensor(sensor.subUrl());
	}

	public static boolean montaMenuInflater(Menu menu, Activity activity,
			int codigoMenu) {
		MenuInflater menuInflater = activity.getMenuInflater();
		menuInflater.inflate(codigoMenu, menu);
		return true;
	}

}
