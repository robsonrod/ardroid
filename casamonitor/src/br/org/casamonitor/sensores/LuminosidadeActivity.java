package br.org.casamonitor.sensores;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import br.org.casamonitor.R;
import br.org.casamonitor.arduino.Sensor;
import br.org.casamonitor.arduino.gerencia.ArduinoUtils;

public class LuminosidadeActivity extends Activity implements ISensorActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Sensor sensor = new ArduinoUtils().getSensor(this);
		setContentView(R.layout.luminosidade);
		ImageView imageView = (ImageView) findViewById(R.id.imgEstadoLuminosidade);
		TextView textView = (TextView) findViewById(R.id.txtEstadoLuminosidade);
		if (sensor.getValor() == 1L) {
			imageView.setImageResource(R.drawable.bulb_yellow);
			textView.setText("LIGADA");
		} else {
			imageView.setImageResource(R.drawable.bulb);
			textView.setText("DESLIGADA");
		}
	}

	@Override
	public String subUrl() {
		return "/?out=light";
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return ArduinoUtils.montaMenuInflater(menu, this, R.menu.menu_sensores);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return validaItemSelecionado(item);

	}

	private boolean validaItemSelecionado(MenuItem item) {
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
