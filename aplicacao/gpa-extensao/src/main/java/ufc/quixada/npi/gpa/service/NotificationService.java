package ufc.quixada.npi.gpa.service;

import java.util.Date;

import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.Pendencia;

public interface NotificationService {

	void notificar(AcaoExtensao acaoExtensao);

	void notificarPareceristaRelatorPrazo(Date agora);
	
	void notificarSolicitacaoResolucaoPendenciasParecer(AcaoExtensao acaoExtensao, Pendencia pendencia);
}
