package br.org.casamonitor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import br.org.casamonitor.arduino.ConfigArduinoActivity;
import br.org.casamonitor.arduino.gerencia.ArduinoUtils;
import br.org.casamonitor.sensores.ConsumoActivity;
import br.org.casamonitor.sensores.LuminosidadeActivity;
import br.org.casamonitor.sensores.TemperaturaActivity;

public class CasaMonitorActivity extends Activity {

	private static final String CASA_MONITOR = "CasaMonitor";

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return ArduinoUtils.montaMenuInflater(menu, this, R.menu.menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.configarduino:
			Log.i(CASA_MONITOR, "Activity Configuracao Ardruino");
			this.configurarArduino();
			return true;
		case R.id.sincarduino:
			Log.i(CASA_MONITOR, "Activity Sincronizar Ardruino");
			this.sincronizarArduino();
			return true;
		case R.id.ajuda:
			Log.i(CASA_MONITOR, "Activity Ajuda");
			this.chamaActivityAjuda();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void sincronizarArduino() {
		ArduinoUtils.sincronizar(this);
	}

	private void chamaActivityAjuda() {
		Intent intent = new Intent(this.getApplicationContext(),
				AjudaActivity.class);
		startActivity(intent);
	}

	private void configurarArduino() {
		Intent intent = new Intent(this.getApplicationContext(),
				ConfigArduinoActivity.class);
		startActivity(intent);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i(CASA_MONITOR, "Activity Incial");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		trataInterfaceBotoes();
	}

	private void trataInterfaceBotoes() {
		Log.i(CASA_MONITOR, "Configurando botões da interface");
		ImageButton botaoConsumo = (ImageButton) findViewById(R.id.imgConsumoEletricidade);
		ImageButton botaoLuminosidade = (ImageButton) findViewById(R.id.imgLuminosidade);
		ImageButton botaoTemperatura = (ImageButton) findViewById(R.id.imgTemperatura);

		View.OnClickListener listenerConsumo = new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Log.i(CASA_MONITOR, "Clique botão Consumo");
				startActivity(new Intent(CasaMonitorActivity.this,
						ConsumoActivity.class));
			}

		};
		View.OnClickListener listenerLuminosidade = new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Log.i(CASA_MONITOR, "Clique botão Luminosidade");
				startActivity(new Intent(CasaMonitorActivity.this,
						LuminosidadeActivity.class));
			}
		};

		View.OnClickListener listenerTemperatura = new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Log.i(CASA_MONITOR, "Clique botão Temperatura");
				startActivity(new Intent(CasaMonitorActivity.this,
						TemperaturaActivity.class));
			}
		};

		botaoConsumo.setOnClickListener(listenerConsumo);
		botaoLuminosidade.setOnClickListener(listenerLuminosidade);
		botaoTemperatura.setOnClickListener(listenerTemperatura);
	}
}