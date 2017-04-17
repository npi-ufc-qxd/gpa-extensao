package ufc.quixada.npi.gpa.service;

import ufc.quixada.npi.gpa.model.ParceriaExterna;

public interface ParceriaExternaService {
	
	
	void adicionarParceriaExterna(ParceriaExterna parceriaExterna, Integer acaoExtensao, Integer parceiro);
	
}
