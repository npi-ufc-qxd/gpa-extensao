package ufc.quixada.npi.gpa.service;

import java.util.List;

import ufc.quixada.npi.gpa.model.FrequenciaView;

public interface BolsaService {

	List<FrequenciaView> getBolsas(Integer ano);

	void adicionarFrequencia(Integer bolsaId, Integer mes, Integer ano);

}
