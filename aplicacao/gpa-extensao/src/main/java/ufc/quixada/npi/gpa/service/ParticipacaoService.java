package ufc.quixada.npi.gpa.service;

import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.Participacao;

public interface ParticipacaoService {

	Participacao participacaoCoordenador(AcaoExtensao acaoExtensao, Integer cargaHoraria);

}
