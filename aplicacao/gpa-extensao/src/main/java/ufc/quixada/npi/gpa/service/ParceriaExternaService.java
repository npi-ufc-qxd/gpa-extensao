package ufc.quixada.npi.gpa.service;

import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.Parceiro;
import ufc.quixada.npi.gpa.model.ParceriaExterna;
import ufc.quixada.npi.gpa.model.Pessoa;

public interface ParceriaExternaService {
	
	
	void adicionarParceriaExterna(Pessoa coordenador, ParceriaExterna parceriaExterna, AcaoExtensao acaoExtensao, Parceiro parceiro) throws GpaExtensaoException;

	void excluirParceriaExterna(Pessoa coordenador, ParceriaExterna parceria) throws GpaExtensaoException;


}
