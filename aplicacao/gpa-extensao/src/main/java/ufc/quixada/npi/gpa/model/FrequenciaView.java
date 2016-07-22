package ufc.quixada.npi.gpa.model;

import java.util.HashMap;
import java.util.Map;

public class FrequenciaView {

	private Bolsa bolsa;

	private Map<Integer, FrequenciaBolsista> frequencia;

	public FrequenciaView(Bolsa bolsa) {
		this.bolsa = bolsa;
		this.frequencia = new HashMap<Integer, FrequenciaBolsista>();
	}

	public Bolsa getBolsa() {
		return bolsa;
	}

	public void setBolsa(Bolsa bolsa) {
		this.bolsa = bolsa;
	}

	public Map<Integer, FrequenciaBolsista> getFrequencia() {
		return frequencia;
	}

	public void addFrequencia(Integer mes, FrequenciaBolsista frequenciaBolsista){
		this.frequencia.put(mes, frequenciaBolsista);
	}

}
