package br.org.casamonitor.arduino;

public class Sensor {

	private String estado;
	private Double valor;

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public void setEstado(Double valor) {
		this.valor = valor;
	}

}
