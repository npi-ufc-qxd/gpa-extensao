package ufc.quixada.npi.gpa.service;

import ufc.quixada.npi.gpa.exception.GpaExtensaoException;

import java.util.Date;

import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.Pendencia;

public interface NotificationService {

	void notificar(AcaoExtensao acaoExtensao);
	
	void notificarSolicitacaoResolucaoPendenciasParecer(AcaoExtensao acaoExtensao, Pendencia pendencia) throws GpaExtensaoException;

	void notificarPareceristaRelatorPrazo(Date agora);
	
	void notificarResolucaoPendenciasParecer(AcaoExtensao acaoExtensao);
	
	void notificarResolucaoPendenciasRelato(AcaoExtensao acaoExtensao);
}
