package br.org.casamonitor.sensores;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import br.org.casamonitor.R;
import br.org.casamonitor.arduino.gerencia.ArduinoUtils;

public class ConsumoActivity extends Activity implements ISensorActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public String subUrl() {
		return null;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return ArduinoUtils.montaMenuInflater(menu, this, R.menu.menu_sensores);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.sincronizarSensor:
			Intent intent = new Intent(this.getApplicationContext(),
					this.getClass());
			startActivity(intent);
			this.finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
